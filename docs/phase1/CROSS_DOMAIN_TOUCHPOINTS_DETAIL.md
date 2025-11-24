# Phase 1 跨领域触点明细

## 1. Order → Member

- 涉及类：
  - org.dromara.flower.service.impl.FolwerOrderServiceImpl
- 涉及方法列表：
  - 方法签名：public FolwerOrderVo queryById(Long orderId)
    - 调用的 Member 侧接口 / 类：IAppletUserInformationService
    - 触点说明：读取小程序端的会员昵称与手机号，补充订单视图所需的基础会员资料。
    - 领域角色：
      - 调用方领域：Order
      - 被调用领域：Member
  - 方法签名：public TableDataInfo<FolwerOrderVo> queryPageList(FolwerOrderBo bo, PageQuery pageQuery)
    - 调用的 Member 侧接口 / 类：IMemberLevelService、IAppletUserInformationService
    - 触点说明：分页查询订单时补充会员等级名称与昵称，确保订单列表展示完整的会员信息。
    - 领域角色：
      - 调用方领域：Order
      - 被调用领域：Member

## 2. Order → Product

- 涉及类：
  - org.dromara.flower.service.impl.FolwerDeliveryBoxServiceImpl
- 涉及方法列表：
  - 方法签名：public R<List<FolwerSkuVo>> deleteWithValidByIds(Collection<Long> ids, Boolean isValid)
    - 调用的 Product 侧接口 / 类：IFolwerSkuService
    - 触点说明：删除物流箱型前检查是否被 SKU 绑定，避免移除仍被商品占用的箱型配置。
    - 领域角色：
      - 调用方领域：Order
      - 被调用领域：Product

## 3. Payment → Order

- 涉及类：
  - org.dromara.flowerapplet.controller.WxPayCallbackController
- 涉及方法列表：
  - 方法签名：public R<FolwerAppletOrderVo> callBack(HttpServletRequest request, HttpServletResponse response) throws Exception
    - 调用的 Order 侧接口 / 类：IFolwerAppletOrderService
    - 触点说明：处理微信支付回调并驱动订单服务完成支付确认与订单状态更新。
    - 领域角色：
      - 调用方领域：Payment
      - 被调用领域：Order
