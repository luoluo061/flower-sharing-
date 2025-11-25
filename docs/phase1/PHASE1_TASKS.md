# Phase 1 整理清单

## 1. Phase 1 要求摘要
- 未在仓库中找到 `MEILI-CENTER-DESIGN-OVERVIEW.md` 以及 `docs/centers/` 下的 Phase 1 章节内容，需等待上游补充后才能输出正式的要求小结。

## 2. Phase 1 领域范围
- Member（会员与账号）：含认证、等级、权益、兑换与用户信息相关的控制器及配套服务/领域对象，详见领域映射清单。
- Product（商品与类目）：覆盖类目、商品、SKU、公告及评论等前后台接口及配套组件。
- Order（下单、配送、售后）：订单、配送、配置、自提地址、购物车等控制器与服务。
- Payment（支付回调）：支付回调控制器目前集中在微信支付回调入口。
- Marketing（营销与积分）：优惠券、积分商品、广告位、促销方案等接口与服务。
- Community（社区互动）：社区动态、评论、点赞相关组件。
- Edu（课程/学习）：课程、视频、购买记录与分类相关的控制器和服务。

> 以上范围来自 `DOMAIN_CLASSIFICATION.md` 与 `DOMAIN_COMPONENT_MAPPING.md` 的 Phase 0 标注；具体 Phase 1 处理顺序需参考缺失的设计总览文档确认。

## 3. Phase 1 关注的跨领域依赖
- Order → Member：`src/main/java/org/dromara/flower/service/impl/FolwerOrderServiceImpl.java` 依赖会员等级与用户信息服务来补充订单视图。
- Order → Product：`src/main/java/org/dromara/flower/service/impl/FolwerDeliveryBoxServiceImpl.java` 调用商品 SKU 服务校验配送箱型关联。
- Payment → Order：`src/main/java/org/dromara/flowerapplet/controller/WxPayCallbackController.java` 在支付回调中依赖订单服务处理回调逻辑。

## 4. Phase 1 Checklist
- [ ] 获取并研读 `MEILI-CENTER-DESIGN-OVERVIEW.md` 中的 Phase 1 章节，形成正式需求小结。
- [ ] 同步 `docs/centers/*.md` 中 Phase 1 分中心要求，补充到本清单。
- [ ] 根据设计文档指示，明确各中心（如 Order、Payment、Member 等）的处理顺序与范围，并在此文档更新。
- [ ] 对照领域映射清单，梳理 Phase 1 涉及的 Controller/Service/Domain/Mapper 清单，确保记录完整性。
- [ ] 针对已标记的跨领域依赖，按设计文档要求确定 Phase 1 需要关注或隔离的项，更新关注点与处理计划。
- [x] 梳理并文档化已知 Order→Member / Order→Product / Payment→Order 的代码级触点（见 CROSS_DOMAIN_TOUCHPOINTS_DETAIL.md，本次任务已完成）
- [x] 完成 Order Center 组件梳理与 MEILI-DOMAIN 注释扩展（见 ORDER_CENTER_COMPONENTS.md，本次任务已完成）
- [x] 抽取订单视图拼装逻辑到 OrderViewAssembler，收敛 Order 领域的视图装配（Phase 1 Task 4，本次任务已完成）
