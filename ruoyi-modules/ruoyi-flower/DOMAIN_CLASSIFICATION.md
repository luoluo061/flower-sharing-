# ruoyi-flower 模块 Phase 0 领域标注

> 目标：在不改动业务逻辑、不移动文件的前提下，对现有 Controller 进行领域归类，便于后续拆分为 Member / Product / Order / Payment / Marketing / Community / Edu 七个中心。
>
> 方法：优先依据包路径、类名、注释（如“积分”“配送”“课程”等）和请求前缀进行推断；若语义模糊，则按最主要业务词归类。

## Member（会员与账号）
- `org/dromara/flower/controller/AppletUserAuthController` — 小程序用户实名认证。  
- `org/dromara/flower/controller/AppletUserAuthlogController` — 小程序用户认证日志。  
- `org/dromara/flower/controller/MemberExchangeRecordController` — 会员兑换记录。  
- `org/dromara/flower/controller/MemberLevelController` — 会员等级。  
- `org/dromara/flower/controller/MemberLevelPrivilegeController` — 会员等级权益。  
- `org/dromara/flower/controller/OneselfMemberLevelPrivilegeController` — 个人会员等级权益。  
- `org/dromara/flower/controller/MemberPointsExchangeGoldController` — 积分兑换金币。  
- `org/dromara/flower/controller/MemberPurchaseRecordController` — 会员购买记录。  
- `org/dromara/flower/platform/controller/AppletUserInformationController` — 平台端小程序用户信息。  
- `org/dromara/flowerapplet/controller/MemberAppletLevelController` — 小程序会员等级。  
- `org/dromara/flowerapplet/controller/MemberAppletLevelPrivilegeController` — 小程序会员权益。  
- `org/dromara/flowerapplet/controller/MemberAppletPurchaseRecordController` — 小程序会员购买记录。  
- `org/dromara/flowerapplet/controller/FlowerAppletUserAuthController` — 小程序用户认证。  
- `org/dromara/flowerapplet/controller/FlowerAppletUserAuthlogController` — 小程序用户认证日志。  
- `org/dromara/flowerapplet/controller/FlowerAppletUserInformationController` — 小程序用户信息。  

## Product（商品与类目）
- `org/dromara/flower/controller/FolwerCategoryController` — 商品类目。  
- `org/dromara/flower/controller/FolwerProductController` — 商品主表。  
- `org/dromara/flower/controller/FolwerProductDetailController` — 商品详情。  
- `org/dromara/flower/controller/FolwerSkuController` — 商品 SKU。  
- `org/dromara/flower/controller/FolwerProductCommController` — 商品评论。  
- `org/dromara/flower/controller/FolwerAnnouncementController` — 商品/商城公告。  
- `org/dromara/flowerapplet/controller/FolwerAppletProductController` — 小程序商品。  
- `org/dromara/flowerapplet/controller/FolwerAppletProductDetailController` — 小程序商品详情。  
- `org/dromara/flowerapplet/controller/FolwerAppletSkuController` — 小程序商品 SKU。  
- `org/dromara/flowerapplet/controller/FolwerAppletAnnouncementController` — 小程序公告/营销位。  
- `org/dromara/flowerapplet/controller/FolwerAppletCategoryController` — 小程序商品类目。  

## Order（下单、配送、售后）
- `org/dromara/flower/controller/FolwerOrderController` — 订单主表。  
- `org/dromara/flower/controller/FolwerOrderDetailController` — 订单明细。  
- `org/dromara/flower/controller/FolwerOrderRefundController` — 订单退款。  
- `org/dromara/flower/controller/FolwerOrderDvyController` — 订单配送记录。  
- `org/dromara/flower/controller/FolwerOrderSetController` — 订单配置。  
- `org/dromara/flower/controller/FolwerDeliveryController` — 配送任务。  
- `org/dromara/flower/controller/FolwerDeliveryPriceController` — 配送计费。  
- `org/dromara/flower/controller/FolwerDeliveryRuleController` — 配送规则。  
- `org/dromara/flower/controller/FolwerDeliveryTemplateController` — 配送模板。  
- `org/dromara/flower/controller/FolwerDeliveryTemperatureController` — 运输温控。  
- `org/dromara/flower/controller/FolwerDeliveryBoxController` — 配送箱。  
- `org/dromara/flower/controller/FolwerDeliverySetController` — 配送设置。  
- `org/dromara/flower/controller/FolwerPickAddrController` — 自提/收货地址。  
- `org/dromara/flowerapplet/controller/FolwerAppletOrderController` — 小程序订单。  
- `org/dromara/flowerapplet/controller/FolwerAppletOrderDetailController` — 小程序订单明细。  
- `org/dromara/flowerapplet/controller/FolwerAppletOrderRefundController` — 小程序退款。  
- `org/dromara/flowerapplet/controller/FolwerAppletOrderDvyController` — 小程序配送记录。  
- `org/dromara/flowerapplet/controller/FolwerAppletOrderSetController` — 小程序订单配置。  
- `org/dromara/flowerapplet/controller/FolwerAppletDeliveryController` — 小程序配送。  
- `org/dromara/flowerapplet/controller/FolwerAppletDeliveryPriceController` — 小程序配送计费。  
- `org/dromara/flowerapplet/controller/FolwerAppletPickAddrController` — 小程序收货地址。  
- `org/dromara/flowerapplet/controller/FolwerAppletBasketController` — 小程序购物车。  

## Payment（支付/回调）
- `org/dromara/flowerapplet/controller/WxPayCallbackController` — 微信支付/退款回调入口。  

## Marketing（营销、优惠、积分）
- `org/dromara/flower/controller/FolwerCouponController` — 平台优惠券。  
- `org/dromara/flower/controller/FolwerCouponReceiveController` — 优惠券领取。  
- `org/dromara/flower/controller/MarketingCouponController` — 营销优惠券主表。  
- `org/dromara/flower/controller/MarketingCouponReceiveController` — 营销优惠券领取。  
- `org/dromara/flower/controller/MarketingAdvertisementController` — 营销广告位。  
- `org/dromara/flower/controller/MarketingLogisticsExpressController` — 营销相关物流模板/快递公司。  
- `org/dromara/flower/controller/MarketingMemberPromotionPlanController` — 会员促销方案。  
- `org/dromara/flower/controller/MarketingMemberPromotionPecordController` — 会员促销记录。  
- `org/dromara/flower/controller/FolwerCreditCategoryController` — 积分商品类目。  
- `org/dromara/flower/controller/FolwerCreditProductController` — 积分商品。  
- `org/dromara/flower/controller/FolwerCreditSetController` — 积分设置。  
- `org/dromara/flower/controller/FolwerCreditGetrecordsController` — 积分获取记录。  
- `org/dromara/flower/controller/FolwerCreditOrderController` — 积分兑换订单。  
- `org/dromara/flower/controller/FolwerCreditOrderDetailController` — 积分兑换订单明细。  
- `org/dromara/flowerapplet/controller/FolwerAppletCreditCategoryController` — 小程序积分商品类目。  
- `org/dromara/flowerapplet/controller/FolwerAppletCreditProductController` — 小程序积分商品。  
- `org/dromara/flowerapplet/controller/FolwerAppletCreditSetController` — 小程序积分设置。  
- `org/dromara/flowerapplet/controller/FolwerAppletCreditGetrecordsController` — 小程序积分获取记录。  
- `org/dromara/flowerapplet/controller/FolwerAppletCreditOrderController` — 小程序积分订单。  
- `org/dromara/flowerapplet/controller/FolwerAppletCreditOrderDetailController` — 小程序积分订单明细。  

## Community（社区互动）
- `org/dromara/flower/controller/FlowerFriendsCommunityController` — 社区动态。  
- `org/dromara/flower/controller/FlowerFriendsCommunityCommentController` — 社区评论。  
- `org/dromara/flower/controller/FlowerFriendsCommunityLikeController` — 社区点赞。  
- `org/dromara/flowerapplet/controller/FlowerAppletFriendsCommunityController` — 小程序社区动态。  
- `org/dromara/flowerapplet/controller/FlowerAppletFriendsCommunityCommentController` — 小程序社区评论。  
- `org/dromara/flowerapplet/controller/FlowerAppletFriendsCommunityLikeController` — 小程序社区点赞。  

## Edu（课程/学习）
- `org/dromara/flower/controller/CoursesManagerController` — 课程管理。  
- `org/dromara/flower/controller/CoursesManagerDetailController` — 课程详情。  
- `org/dromara/flower/controller/CoursesManagerVideoController` — 课程视频。  
- `org/dromara/flower/controller/CoursesTypeController` — 课程分类。  
- `org/dromara/flower/controller/CoursesPurchaseRecordsController` — 课程购买记录。  
- `org/dromara/flower/controller/CoursesPlayedLogController` — 课程播放日志。  
- `org/dromara/flowerapplet/controller/CoursesAppletManagerController` — 小程序课程管理。  
- `org/dromara/flowerapplet/controller/CoursesAppletPurchaseRecordsController` — 小程序课程购买记录。  
- `org/dromara/flowerapplet/controller/CoursesAppletTypeController` — 小程序课程分类。  
