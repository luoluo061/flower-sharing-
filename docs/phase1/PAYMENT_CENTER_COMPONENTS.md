# Payment Center 组件梳理（Phase 1）

## 1. 职责范围概览

- 统一封装微信等支付通道的下单、回调、退款等能力，向业务侧暴露标准支付接口。
- 维护支付单、退款单及回调日志等支付侧账务记录，确保“收钱/退款/对账”链路闭环。
- 处理支付与退款回调，校验签名并更新支付状态后，将结果干净地通知订单中心。
- 专注支付通道与账务，不介入订单、营销、会员等业务逻辑，保持领域边界清晰。

## 2. Payment 领域代码组件清单

### 2.1 Controller

- `WxPayCallbackController` — 微信支付/退款回调入口，处理支付回调并转交订单更新逻辑。路径：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/WxPayCallbackController.java`。

### 2.2 Service

- `IPayService`（接口）— 封装微信支付下单、查询、回调确认、退款等通道能力。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/server/IPayService.java`。
- `WxPayService`（实现）— 基于微信支付 SDK 实现预下单、订单查询、回调确认与退款处理。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/server/impl/WxPayService.java`。
- `SharingService`（接口）— 支撑微信分账接收方管理、分账执行及账单查询。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/server/SharingService.java`。
- `SharingServiceImpl`（实现）— 实现分账接收方维护、分账请求、回退及账单下载等逻辑。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/server/impl/SharingServiceImpl.java`。

### 2.3 Domain（Bo / Vo / Entity）

- `PayParam`（Vo）— 小程序端下单支付参数载体，包含订单号与支付方式。路径：`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/domain/PayParam.java`。
- `WxPayRequest`（Request Vo）— 微信支付下单参数模型，承载 openId、金额、描述等字段。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/domain/WxPayRequest.java`。
- `WxJsapiResponse`（Vo）— JSAPI 预支付响应参数对象，生成前端调起所需签名信息。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/domain/WxJsapiResponse.java`。
- `WxRefundRequest`（Request Vo）— 微信退款请求参数对象，包含退款单号、原因、金额等信息。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/domain/WxRefundRequest.java`。
- `RefundAmount`（Vo）— 退款金额与币种信息载体，描述退款金额、原订单金额等字段。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/domain/RefundAmount.java`。
- `PayProfitsharingParam`（Vo）— 微信分账请求参数模型，包含订单号、分账接收方、金额等字段。路径：`ruoyi-common/ruoyi-common-pay/src/main/java/org/dromara/common/mypay/domain/PayProfitsharingParam.java`。

### 2.4 Mapper / XML

- 暂未发现 Payment 领域专用的 Mapper 接口或 XML 映射文件。

## 3. 已知跨领域触点概览（引用 CROSS_DOMAIN_TOUCHPOINTS_DETAIL）

- `WxPayCallbackController.callBack(HttpServletRequest, HttpServletResponse)` — 调用 `IFolwerAppletOrderService.payCallbackOrder` 以在订单服务中完成支付确认与订单状态更新，体现 Payment → Order 的回调触点。
