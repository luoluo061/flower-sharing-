# Product Center — 商品中心设计文档（V1.0）

美立七中心架构（MEILI Center Architecture） · Phase 1 输出
Author: 总架构师 GPT

🎯 1. 商品中心的核心职责（What Product Center Must Do）

Product Center 是整个美立市集所有“可交易资产”的统一源头。
在任何平台（花店电商、农贸市场、智慧商户）中，只要出现：

商品

规格

SKU

类目

标签

属性

库存

都必须归属商品中心。

商品中心必须负责：
✔ 1. 商品主信息（SPU）

商品名称

商品封面图

描述

类目

属性（产地、颜色、材质等）

✔ 2. SKU（规格项）

规格（如 单枝 / 10枝 / 箱装）

属性组合（长短、颜色等）

独立的 SKU 编码

SKU 可独立定价

SKU 可独立库存

✔ 3. 类目体系

树形类目（如 玫瑰 → 单头 / 多头）

类目的启用/禁用

类目属性模板（花卉行业常见）

✔ 4. 库存管理（基础）

可用库存

锁定库存（Order Center 使用）

安全库存（预警）

注意：库存冻结与扣减由 Order Center 触发，但是库存本身属于 Product Center。

✔ 5. 商品内容（详情页 material）

详情图集

商品参数（Key-Value）

多语言（可选）

✔ 6. 商品评价（Review）

用户评论

图片评论

星级评分

评论审核

（尽管属于交易体系，但仍属于 Product Center）

❌ 2. 商品中心不负责的内容（Boundary）

为了保证复用能力，商品中心绝对不能承担以下业务：

❌ 不负责订单逻辑

不做下单检查

不做配送规则

不做支付逻辑

❌ 不负责会员逻辑

不做会员价

不做会员等级折扣

不查用户资料

❌ 不负责营销逻辑

不做优惠券

不做促销价格计算

不做积分兑换价

❌ 不负责交易推荐（Recommendation）

这属于后期 BI / 推荐系统（可依赖 Product Center）。

🧱 3. 商品中心的主要子模块（Subdomains）
✔ 1. Category（类目体系）

分类树、类目属性、启停控制。

✔ 2. Product（SPU 商品）

商品基本信息。

✔ 3. SKU（规格）

独立的 SKU + 定价 + 库存位。

✔ 4. Product Detail（内容素材）

详情图集、多图文、附属参数。

✔ 5. Review（评价）

评论、回复、审核。

📦 4. 商品中心的“数据拥有权”（Database Ownership）

必须归属于 Product Center 的表包括：

product_category

product（SPU）

product_sku

product_detail

product_review

product_review_image

product_spec（规格/属性模板）

现阶段 ruoyi-flower 使用的命名如 FolwerSku 等，将在 Phase 2 标准化。

🔗 5. 跨中心依赖规则（Dependency Rules）
✔ 被其他中心查询（Read-only）

允许：

Order Center（查 SKU 价格、库存、类目）

Marketing Center（查 SKU 基础价做优惠计算）

Community Center（加载商品图信息）

❌ 不允许调用其他业务中心

Product Center 禁止依赖：

Member Center

Order Center

Payment Center

Marketing Center

所有“跨中心业务判断”必须通过事件或外部调用完成。

🧩 6. 商品中心与订单中心（Order Center）的关系

订单中心是消费者
商品中心是数据源

事件流参考：

订单发起 → OrderCenter
↑ 查询 SKU 价格、库存
下单成功 → OrderCenter emit(OrderCreated)
↓ ProductCenter.freezeStock(sku, count)
支付成功 → OrderCenter emit(Paid)
↓ ProductCenter.reduceStock
退款成功 → OrderCenter emit(RefundDone)
↓ ProductCenter.releaseStock

🚀 7. 商品中心 API 草案（Future）

示例：

类目 API

GET /product/category/tree
POST /product/category
DELETE /product/category/{id}

商品 API

GET /product/{id}
POST /product
PUT /product
DELETE /product/{id}

SKU API

GET /product/sku/{id}
POST /product/sku
POST /product/sku/batch
POST /product/sku/checkStock
POST /product/sku/freeze
POST /product/sku/release

评价 API

GET /product/review/list/{productId}
POST /product/review
POST /product/review/audit

🛠 8. 与现有 ruoyi-flower 的映射（Phase 1）

映射自你的 DOMAIN_COMPONENT_MAPPING.md：

实际属于 Product Center 的控制器：

FolwerCategoryController

FolwerProductController

FolwerSkuController

FolwerProductDetailController

FolwerAnnouncementController

PC + 小程序端全部对应控制器

下一步 Phase 1.5 要移动到：

org.dromara.flower.product.*

🔥 9. Phase 1.5 商品中心清洗计划（可执行）
Step 1

创建新包：

org/dromara/flower/product/controller
org/dromara/flower/product/service
org/dromara/flower/product/domain
org/dromara/flower/product/mapper

Step 2

移动所有 Product 相关 Java 文件（不动逻辑）

Step 3

在每个类顶部添加：

/**
* @Domain(Product)
  */

Step 4

标注跨域调用（若有）：

// TODO Phase2: Product→Order should be removed