# Order Center — 订单中心设计文档（V1.0）

美立七中心架构（MEILI Center Architecture） · Phase 1 输出

🎯 1. Order Center 的核心使命（Core Responsibilities）

订单中心是交易体系的“心脏”。
所有涉及交易生命周期的动作，都必须归属 Order Center。

一句话总结：
Order Center = “从用户点击下单 → 完成履约” 的全流程掌控者。

🧩 2. Order Center 必须负责什么？
✔ 1. 下单（Order Placement）

校验：库存、商品状态、用户有效性（只读）

校验活动、优惠使用（调用营销中心）

组装订单主表 & 明细

创建订单号（唯一）

注：商品库存不由订单中心维护，但库存冻结动作由订单中心触发。

✔ 2. 订单状态流转（Order State Machine）

订单中心必须控制订单状态机：

待支付 → 已支付 → 待发货 → 配送中 → 完成
↓         ↑
超时取消     退款中 → 已退款


包括：

状态变更

状态变更记录

自动关闭（未支付）

自动确认收货

超时任务

✔ 3. 配送（Delivery）

配送属于订单中心的一部分。包含：

配送任务（Delivery Task）

配送路线（可选）

配送价格（计费规则）

配送箱（需求来自你的系统）

温度控制（花卉行业特有）

注意：配送设计属于“履约”，不应放到商品中心或营销中心。

✔ 4. 退货 & 售后（Refund / After-sales）

提交退款申请

审核

退款状态机

审核记录

扣减/恢复库存（通过 Product Center）

✔ 5. 自提点 / 收货地址

地址表属于订单中心的辅助数据：

自提点

收货地址（用户侧）

地址校验逻辑

✔ 6. 购物车（Basket）

购物车不是订单，但必须归属订单中心：

添加

删除

校验商品状态（只读）

❌ 3. Order Center 不负责什么？

明确边界有助于未来可拆分。

❌ 不负责商品信息

只能读商品中心的 SKU/价格。

❌ 不负责计算优惠

优惠券、积分抵扣都由 Marketing Center 提供计算结果。

❌ 不负责支付逻辑

支付动作属于 Payment Center，只处理支付“结果”。

🧱 4. Order Center 的子模块（Subdomains）
/order
/order-main           订单主表
/order-detail         订单明细
/delivery             配送任务
/delivery-price       配送计费
/delivery-rule        配送规则
/delivery-box         配送箱
/delivery-temp        温控
/delivery-set         配送配置
/refund               售后与退款
/pick-addr            自提点与收货地址
/basket               购物车


这些模块你系统里都已经有了（在 Phase 0 里 Codex 已扫描）。

🔗 5. Order Center 跨域依赖规则（中心最复杂的一部分）

订单中心拥有最多跨域依赖，因此规则必须最严格：

✔ 被允许的依赖（Read-only）
依赖中心	允许原因
Product Center	需要查商品和库存
Member Center	需要查用户等级/积分（只读）
Marketing Center	需要用优惠计算结果
Payment Center	支付成功/失败回调
❌ 禁止的依赖（写入类依赖）

订单中心不允许：

写 Product Center 的库存（必须通过事件）

写 Member Center（不能直接给用户加积分）

写 Marketing Center（不能直接发券）

🔁 6. Order Center 的事件流（推荐方式）

订单中心是事件流的中心点：

OrderCreated
→ ProductCenter.freezeStock

OrderPaid
→ ProductCenter.reduceStock
→ MarketingCenter.consumeCoupon
→ MemberCenter.addPoints
→ DeliveryCenter.createTask

RefundRequested
→ ProductCenter.releaseStock
→ PaymentCenter.refund


你未来所有系统都可以基于这里的事件建立扩展能力。

🗺 7. Order Center 的 API 草案（Future）
创建订单

POST /order/create

查询订单

GET /order/{id}

更新订单状态

POST /order/state/update

配送任务

POST /order/delivery/create
GET /order/delivery/{id}

退款

POST /order/refund/apply
GET /order/refund/{id}

购物车

GET /order/basket/list
POST /order/basket/add
POST /order/basket/delete

🛠 8. 当前 ruoyi-flower 的映射（真实代码对应）

属于 Order Center 的控制器：

FolwerOrderController

FolwerOrderDetailController

FolwerOrderRefundController

FolwerOrderSetController

FolwerDelivery*

FolwerPickAddrController

FolwerAppletBasketController

小程序端的所有订单相关控制器

这些信息已经在你的 DOMAIN_COMPONENT_MAPPING 记录。

Phase 1.5 会移动它们到：

org.dromara.flower.order.*

🔥 9. Phase 1.5 Order Center 清洗（你可以在下一阶段执行）
Step 1

创建 OrderCenter 包结构

Step 2

移动现有 Order 相关类

Step 3

加注释：

@Domain(Order)

Step 4

标注跨域依赖
Codex 就能自动扫描、自动重构。