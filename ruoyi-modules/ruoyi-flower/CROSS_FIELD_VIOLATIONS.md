# ruoyi-flower 跨领域依赖扫描（Phase 0）

> 依据 DOMAIN_CLASSIFICATION.md 的 7 个领域（Member / Product / Order / Payment / Marketing / Community / Edu）对跨领域引用进行标注。

[ORDER → MEMBER]
- File: src/main/java/org/dromara/flower/service/impl/FolwerOrderServiceImpl.java
- Line: 18-114
- Reason: 订单服务通过 `IMemberLevelService` 与 `IAppletUserInformationService` 读取会员等级与用户信息（Member 领域）来补充订单视图。

[ORDER → PRODUCT]
- File: src/main/java/org/dromara/flower/service/impl/FolwerDeliveryBoxServiceImpl.java
- Line: 12-158
- Reason: 配送箱型服务调用 `IFolwerSkuService`（Product 领域）检查箱型是否被 SKU 绑定。

[PAYMENT → ORDER]
- File: src/main/java/org/dromara/flowerapplet/controller/WxPayCallbackController.java
- Line: 13-43
- Reason: 微信支付回调控制器（Payment 领域）依赖 `IFolwerAppletOrderService`（Order 领域）完成支付回调订单处理。
