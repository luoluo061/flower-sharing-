# Order Center — Phase 1 跨领域触点清单

## 1. Order → Member
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/impl/FolwerOrderServiceImpl.java` — `queryById` / `queryPageList`：补充订单视图所需的会员基础信息与等级。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/impl/FolwerOrderRefundServiceImpl.java` — `queryById` / `queryInfoById`：查询退款单时获取会员手机号等资料。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderServiceImpl.java` — `insertByBo` / `createByOrder` / `submitOrders` / `queryOrder` / `payCallbackOrder`：下单、支付与回调过程中读取会员基本信息、积分与分账关系。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletCreditOrderServiceImpl.java` — `insertByBo`：校验积分订单用户的基础信息与积分余额。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/impl/FolwerOrderRefundServiceImpl.java` — `submitRefundOrders`：退款成功后补充用户信息。

## 2. Order → Product
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/impl/FolwerOrderDetailServiceImpl.java` — `queryPageList`：查询订单明细时补充 SKU 信息。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/impl/FolwerDeliveryBoxServiceImpl.java` — `deleteWithValidByIds`：删除箱型前校验是否被商品 SKU 绑定。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderDetailServiceImpl.java` — `queryById` / `queryList`：查询小程序订单明细的 SKU 名称。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderServiceImpl.java` — `insertByBo` / `createByOrder` / `submitOrders` / `getShopCartItemsByOrderItems`：下单、扣减库存与购物车预览时查询商品与 SKU 信息。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderDvyServiceImpl.java` — `insertByBo` / `updateByBo`：物流费用计算时读取 SKU 体积与重量。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderServiceImpl.java` — `queryOrder` / `payCallbackOrder`：支付后更新商品销量与库存。

## 3. Order → Marketing
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderServiceImpl.java` — `sharingResult`：查询会员推广分佣记录以确定分账接收方。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletCreditOrderServiceImpl.java` — `insertByBo`：获取积分商品兑换信息。

## 4. Order → Payment
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/impl/FolwerOrderRefundServiceImpl.java` — `submitRefundOrders`：调用支付中心发起退款。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletOrderServiceImpl.java` — `submitOrders` / `refundOrder` / `queryOrder` / `payCallbackOrder` / `sharingResult`：构建支付、退款、交易查询与分账相关请求。
- `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/impl/FolwerAppletCreditOrderServiceImpl.java` — `insertByBo` / `queryCreditOrder`：积分订单支付与交易状态查询。
