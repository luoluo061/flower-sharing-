# Payment Center — 支付中心设计文档（V1.0）

美立七中心架构（MEILI Center Architecture） · Phase 1 输出

🎯 1. 支付中心的核心使命

Payment Center 只做一件事：

把各种支付通道（微信、支付宝、银行聚合、线下扫码等）统一封装成“标准支付能力”，并把支付结果“干干净净”通知给订单中心。

关键点：

不做订单逻辑

不做营销逻辑

不改会员信息

专注：“收钱 & 回调 & 对账 & 账单”

🧩 2. Payment Center 必须负责的内容
✔ 2.1 支付通道接入（Channel Integration）

微信支付（JSAPI、小程序、Native、H5）

支付宝支付

银行聚合支付（如富滇银行“聚合收款”）

第三方聚合支付（拉卡拉等）

线下扫码（POS/收款码）

所有通道统一抽象为一个内部模型：

PayChannel:
- channelCode: WECHAT, ALIPAY, BANK_FUDIAN, LAKALA...
- appId / mchId / key
- notifyUrl
- returnUrl  (H5/网页用)

✔ 2.2 支付订单（PayOrder）

Payment Center 管理自己的支付单（不是订单中心的“业务订单”）：

字段示例：

payOrderNo（支付单号，内部唯一）

bizOrderNo（业务订单号，对应 Order Center 的 orderNo）

channelCode

amount

currency

status（CREATED, PAYING, SUCCESS, FAILED, CLOSED, REFUNDING, REFUNDED）

notifyTimes / lastNotifyTime

✔ 2.3 回调处理（Callback & Notify）

接收各支付通道的异步通知（如微信的 payCallback / refundCallback）

校验签名、金额、订单号

更新 pay_order 状态

以事件或者回调接口的形式通知 Order Center：

支付成功：PaymentSuccess

支付失败：PaymentFailed

退款成功：RefundSuccess

你现有的 WxPayCallbackController 就是这一块的雏形。

✔ 2.4 退款（Refund）

创建退款单 refund_order

调用支付通道退款接口

接收退款异步通知

更新退款单状态

通知 Order Center 进行业务后续处理（恢复库存、变更订单状态等）

✔ 2.5 账单 & 对账（可后续增强）

通道侧账单文件下载

对账异常记录（多扣、少扣、漏单）

提供对账报表导出接口

🚫 3. Payment Center 明确不负责的内容（边界）

为保证“高度通用 + 安全”，Payment Center 必须保持“业务中立”。

❌ 不维护业务订单内容

只能识别 bizOrderNo，不关心订单里买了什么、买了几件。

❌ 不做价格计算

实际应付金额由 Order Center/Marketing Center 事先计算好，并在创建支付单时写入。

❌ 不做会员积分 / 发券

支付成功后是否加积分、送券 → 由其他中心监听 PaymentSuccess 事件来完成。

❌ 不直接操作商品库存

与库存相关的业务全部在 Order Center + Product Center 完成，Payment 只负责“钱到没到”。

🧱 4. Payment Center 的子模块（Subdomains）

建议内部结构：

/payment
/channel        支付通道配置（微信/支付宝/银行等）
/pay-order      支付单
/refund-order   退款单
/callback       回调处理（Controller）
/notify         通知 Order Center/事件总线的逻辑
/bill           账单 & 对账（可选）

🔗 5. 跨中心依赖规则（很重要）
✔ Payment Center 可以依赖谁？

Auth Center：用来识别当前是哪个商户 / 租户的支付请求

Order Center：仅通过接口或事件进行“结果通知”，不应直接操作订单表

最推荐方式：
Payment Center 不直接调用 Order Service，而是：

内部更新 pay_order 状态后

发送领域事件 PaymentSuccess(bizOrderNo=xxx, amount=yyy)

由 Order Center 订阅事件并更新业务订单状态

如果目前项目还没事件总线，可以先用：

接口回调：OrderCenter.handlePaymentCallback(...)

但要在代码里写清楚这是“技术债”，Phase 2 以后再切事件化。

❌ Payment Center 不允许依赖的中心

Member Center（不直接改用户的积分/余额）

Product Center（不动库存）

Marketing Center（不计算优惠）

Community / Edu Center（完全无关）

🗺 6. Payment 相关数据库所有权

Payment Center 必须拥有的表：

pay_channel（支付通道配置）

pay_order（支付单）

pay_order_extend（可选，通道扩展字段）

refund_order（退款单）

pay_notify_log（回调记录）

pay_bill / pay_bill_diff（账单 & 对账，可后续）

与业务订单的关系：

pay_order.biz_order_no 外键到 Order Center 的 order_no（逻辑外键，不一定数据库 FK）

由 Order Center 在创建支付单时传入

🧩 7. Payment Center API 草案
7.1 创建支付单（由 Order Center 调用）

POST /payment/order/create

请求示例：

{
"bizOrderNo": "O202511240001",
"channelCode": "WECHAT_MINIAPP",
"subject": "鲜花订单 O202511240001",
"amount": 12800,
"currency": "CNY",
"notifyUrl": "...",
"returnUrl": "..."
}


响应示例（给前端用的支付参数）：

{
"payOrderNo": "P202511240001",
"payParams": {
"appId": "...",
"timeStamp": "...",
"nonceStr": "...",
"package": "prepay_id=xxx",
"signType": "MD5",
"paySign": "..."
}
}

7.2 支付结果回调（由微信/支付宝/银行调用）

比如微信小程序：

POST /payment/callback/wechat

内部流程：

校验签名、金额

更新 pay_order 状态为 SUCCESS / FAILED

记录回调日志

发送 PaymentSuccess 事件或调用 OrderCenter 的回调接口

7.3 退款申请（由 Order Center 发起）

POST /payment/refund/create

请求：

{
"bizOrderNo": "O202511240001",
"refundAmount": 12800,
"reason": "用户取消"
}

🧬 8. 与当前 ruoyi-flower 的映射

在 DOMAIN_CLASSIFICATION 和 DOMAIN_COMPONENT_MAPPING 中，目前只有一块明显属于 Payment：

org.dromara.flowerapplet.controller.WxPayCallbackController

前缀：/wxpayback

用于处理微信支付和退款的回调

现在它是散落在 flowerapplet 名字空间里，Phase 1.5 建议你：

新建包：org.dromara.flower.payment.controller

将 WxPayCallbackController 移进去

后续逐步补齐：pay_order、refund_order、PayChannel 等模型

🔥 9. Phase 1.5 Payment Center 清洗计划（可执行）

新建包结构：

org/dromara/flower/payment/controller
org/dromara/flower/payment/service
org/dromara/flower/payment/domain
org/dromara/flower/payment/mapper


把所有支付回调类移动进 payment.controller

为回调类加注释：

/**
* 支付中心 - 微信支付回调入口
* @Domain(Payment)
  */


在回调内部调用 Order Service 的地方，加上 TODO：

// TODO Phase 2: Payment 不应直接依赖 OrderService，后续改成事件通知
orderService.handlePaySuccess(...);
