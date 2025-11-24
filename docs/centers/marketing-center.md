📘 Marketing Center（营销中心）设计文档 V1.0

美立七中心架构（MEILI Center Architecture） · Phase 1 输出

# Marketing Center — 营销中心设计文档（V1.0）
🎯 1. 营销中心的核心使命（Core Responsibilities）

Marketing Center 是连接“交易”与“用户粘性”的能力中心，负责：

优惠、积分、促销、活动、广告位等与交易价格相关的策略体系。

营销不是“锦上添花”，而是强复用能力：

电商业务（花店、小程序商城）

农贸市场业务（营销活动、促销）

租金缴纳平台（如按时缴费送积分/赠券）

线下门店活动

B 端商户优惠策略

未来所有业务线都可以复用 Marketing Center。

🧩 2. Marketing Center 必须负责的内容

Marketing Center =「如何让用户消费更多」的策略引擎。

它的职责包括：

✔ 1. 优惠券（Coupon System）

优惠券模板

优惠券配置（使用门槛／有效期）

优惠券发放记录

用户领券记录

优惠券核销逻辑（由订单中心调用）

常见券类型：

满减券

折扣券

包邮券（对订单中心运输逻辑有影响）

品类券（基于商品类目）

新人券、生日券（基于会员中心）

✔ 2. 促销活动（Promotion System）

可以覆盖：

满减活动

秒杀（未来可扩展）

团购（可扩展）

专题活动（如 38 女神节）

会员日优惠

阶梯价（买多更划算）

促销计算由 Marketing Center 完成，并返回给 Order Center 用于最终价格计算。

✔ 3. 积分商城（Points Mall）

与会员中心分工如下：

功能	归属	说明
积分账户/积分流水	Member Center	用户拥有的积分
使用积分兑换商品	Marketing Center	“积分商品”“积分订单” 属于营销
积分规则	Marketing Center	获取积分的策略属于营销业务
✔ 4. 广告位（Ad Slot）与营销内容

负责：

首页 Banner
-“运营位”配置

营销图标／模块排序

可多端配置（PC、H5、小程序）

✔ 5. 会员促销策略（Member Promotion）

依赖会员中心的等级体系：

会员等级折扣

会员专属商品

会员礼包

生日券

🚫 3. Marketing Center 明确不负责的内容（Boundary）

营销中心不能做：

❌ 下单（Order Center 做）

营销中心不创建订单、不校验库存。

❌ 用户积分账户（Member Center 做）

营销中心只设计策略，不存储积分账户。

❌ 商品基础信息（Product Center 做）

不能修改商品价格，只能应用“优惠逻辑”。

❌ 支付逻辑（Payment Center 做）

不能把优惠券作为“支付渠道”。

🧱 4. Marketing Center 子域结构（Subdomains）
/marketing
/coupon            优惠券模板
/coupon-receive    用户领券记录
/promotion         活动体系
/credit-product    积分商品
/credit-order      积分兑换订单
/credit-record     积分获取记录
/advertisement     广告位配置
/logistics         营销相关的快递公司（已存在）
/member-promotion  会员促销策略

🔗 5. 跨中心依赖规则（Dependency Rules）

Marketing Center 依赖关系最复杂，但必须保持简单：

✔ 可以 Read-only 依赖：
依赖中心	用途
Product Center	获取商品基础价格、类目、SKU 信息
Member Center	获取会员等级、积分账户余额（只读）
✔ 必须通过 事件或接口 调用：
操作	责任中心
使用优惠券	Order Center
兑换积分商品	Order Center
使用积分抵扣	Order Center
积分发放	Member Center
❌ 营销中心禁止调用：

Order Center 的写操作

Payment Center

任何库存逻辑（Product Center）

🔁 6. 营销事件流（Event Bus）

营销中心必须通过事件实现策略驱动：

UserRegistered
→ 发放新人券

OrderPaid
→ 计算积分 → 发放积分
→ 发放会员活动券
→ 记录促销参与行为

MemberLevelUpgraded
→ 自动发放提升礼包

🗺 7. API 草案（未来统一接口）
📌 优惠券 API
GET  /marketing/coupon/list
POST /marketing/coupon/create
POST /marketing/coupon/issue
POST /marketing/coupon/receive
POST /marketing/coupon/check   (订单中心调用，用于计算优惠)

📌 积分商品
GET  /marketing/credit/product/list
POST /marketing/credit/product/create

📌 积分订单
POST /marketing/credit/order/submit
GET  /marketing/credit/order/{id}

📌 会员促销
GET /marketing/member/promotion/list
POST /marketing/member/promotion/create

📌 广告位
GET /marketing/advertisement/list
POST /marketing/advertisement/create

🛠 8. 与 ruoyi-flower 的映射（真实代码对应）

根据你的 DOMAIN_COMPONENT_MAPPING.md，属于 Marketing Center 的模块包括：

优惠券系列：

FolwerCouponController

FolwerCouponReceiveController

MarketingCouponController

MarketingCouponReceiveController

积分商城：

FolwerCreditProductController

FolwerCreditCategoryController

FolwerCreditSetController

FolwerCreditGetRecordsController

FolwerCreditOrderController

FolwerCreditOrderDetailController

Applet 小程序同类控制器（Credit 相关）

广告位：

MarketingAdvertisementController

营销物流：

MarketingLogisticsExpressController

会员促销策略：

MarketingMemberPromotionPlanController

MarketingMemberPromotionPecordController

Phase 1.5 将把它们移动到：

org.dromara.flower.marketing.*

🔥 9. Phase 1.5 Marketing Center 清洗计划（可执行）
Step 1

创建包结构：

org/dromara/flower/marketing/controller
org/dromara/flower/marketing/service
org/dromara/flower/marketing/domain
org/dromara/flower/marketing/mapper

Step 2

移动所有 Marketing 相关 Java 文件

Step 3

为每个类添加：

/**
* @Domain(Marketing)
  */

Step 4

标记跨中心依赖点：

// TODO Phase 2: Marketing→Order should be refactored to event-based call