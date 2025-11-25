# Order Center 组件梳理（Phase 1）

## 1. 职责范围概览

- 负责下单到履约的完整链路，包括订单生成、校验与状态流转，确保交易生命周期可控。
- 管理配送体系（任务、计费、规则、箱型、温控、模板等）并支持自提/收货地址维护，保障履约与交付体验。
- 处理退款/售后流程，记录审核与状态变化，协同其他中心完成库存与支付后续动作。
- 提供购物车能力，负责加入、删除与校验商品状态，为下单做准备。

## 2. Order 领域代码组件清单

### 2.1 Controller

- FolwerOrderController — 订单主表管理（后台）`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderController.java`
- FolwerOrderDetailController — 订单明细管理（后台）`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderDetailController.java`
- FolwerOrderRefundController — 订单退款管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderRefundController.java`
- FolwerOrderDvyController — 订单配送记录管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderDvyController.java`
- FolwerOrderSetController — 订单配置管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerOrderSetController.java`
- FolwerDeliveryController — 配送任务管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliveryController.java`
- FolwerDeliveryPriceController — 配送计费管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliveryPriceController.java`
- FolwerDeliveryRuleController — 配送规则维护`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliveryRuleController.java`
- FolwerDeliveryTemplateController — 配送模板管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliveryTemplateController.java`
- FolwerDeliveryTemperatureController — 运输温控配置`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliveryTemperatureController.java`
- FolwerDeliveryBoxController — 配送箱管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliveryBoxController.java`
- FolwerDeliverySetController — 配送设置管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerDeliverySetController.java`
- FolwerPickAddrController — 自提/收货地址管理`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/FolwerPickAddrController.java`
- FolwerAppletOrderController — 小程序订单接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderController.java`
- FolwerAppletOrderDetailController — 小程序订单明细接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderDetailController.java`
- FolwerAppletOrderRefundController — 小程序退款接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderRefundController.java`
- FolwerAppletOrderDvyController — 小程序配送记录接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderDvyController.java`
- FolwerAppletOrderSetController — 小程序订单配置`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletOrderSetController.java`
- FolwerAppletDeliveryController — 小程序配送任务接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletDeliveryController.java`
- FolwerAppletDeliveryPriceController — 小程序配送计费接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletDeliveryPriceController.java`
- FolwerAppletPickAddrController — 小程序收货地址接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletPickAddrController.java`
- FolwerAppletBasketController — 小程序购物车接口`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FolwerAppletBasketController.java`

### 2.2 Service

- IFolwerOrderService / FolwerOrderServiceImpl — 订单主表查询、增删改与状态处理 `src/main/java/org/dromara/flower/service/...`
- IFolwerOrderDetailService / FolwerOrderDetailServiceImpl — 订单明细的分页、导出与维护 `src/main/java/org/dromara/flower/service/...`
- IFolwerOrderRefundService / FolwerOrderRefundServiceImpl — 退款申请与审核流程处理 `src/main/java/org/dromara/flower/service/...`
- IFolwerOrderDvyService / FolwerOrderDvyServiceImpl — 订单配送记录维护 `src/main/java/org/dromara/flower/service/...`
- IFolwerOrderSetService / FolwerOrderSetServiceImpl — 订单相关配置维护 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliveryService / FolwerDeliveryServiceImpl — 配送任务管理 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliveryPriceService / FolwerDeliveryPriceServiceImpl — 配送计费规则管理 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliveryRuleService / FolwerDeliveryRuleServiceImpl — 配送规则管理 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliveryTemplateService / FolwerDeliveryTemplateServiceImpl — 配送模板定义 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliveryTemperatureService / FolwerDeliveryTemperatureServiceImpl — 运输温控配置 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliveryBoxService / FolwerDeliveryBoxServiceImpl — 配送箱型维护 `src/main/java/org/dromara/flower/service/...`
- IFolwerDeliverySetService / FolwerDeliverySetServiceImpl — 配送设置（开关、范围等） `src/main/java/org/dromara/flower/service/...`
- IFolwerPickAddrService / FolwerPickAddrServiceImpl — 自提/收货地址管理 `src/main/java/org/dromara/flower/service/...`
- IFolwerAppletOrderService / FolwerAppletOrderServiceImpl — 小程序端订单创建、支付与列表 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletOrderDetailService / FolwerAppletOrderDetailServiceImpl — 小程序订单明细处理 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletOrderRefundService / FolwerAppletOrderRefundServiceImpl — 小程序退款流程 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletOrderDvyService / FolwerAppletOrderDvyServiceImpl — 小程序配送记录管理 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletOrderSetService / FolwerAppletOrderSetServiceImpl — 小程序订单配置管理 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletDeliveryService / FolwerAppletDeliveryServiceImpl — 小程序配送任务处理 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletDeliveryPriceService / FolwerAppletDeliveryPriceServiceImpl — 小程序配送计费规则 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletPickAddrService / FolwerAppletPickAddrServiceImpl — 小程序收货地址维护 `src/main/java/org/dromara/flowerapplet/service/...`
- IFolwerAppletBasketService / FolwerAppletBasketServiceImpl — 小程序购物车操作 `src/main/java/org/dromara/flowerapplet/service/...`

### 2.3 Domain（Bo / Vo / Entity）

- FolwerOrder / FolwerOrderBo / FolwerOrderVo / FolwerOrderInfoVo — 订单主表实体与视图 `src/main/java/org/dromara/flower/domain/...`
- FolwerOrderDetail / FolwerOrderDetailBo / FolwerOrderDetailVo — 订单明细 `src/main/java/org/dromara/flower/domain/...`
- FolwerOrderRefund / FolwerOrderRefundBo / FolwerOrderRefundVo — 退款记录 `src/main/java/org/dromara/flower/domain/...`
- FolwerOrderDvy / FolwerOrderDvyBo / FolwerOrderDvyVo — 配送记录 `src/main/java/org/dromara/flower/domain/...`
- FolwerOrderSet / FolwerOrderSetBo / FolwerOrderSetVo — 订单配置 `src/main/java/org/dromara/flower/domain/...`
- FolwerDelivery / FolwerDeliveryBo / FolwerDeliveryVo — 配送任务 `src/main/java/org/dromara/flower/domain/...`
- FolwerDeliveryPrice / FolwerDeliveryPriceBo / FolwerDeliveryPriceVo — 配送计费规则 `src/main/java/org/dromara/flower/domain/...`
- FolwerDeliveryRule / FolwerDeliveryRuleBo / FolwerDeliveryRuleVo — 配送规则 `src/main/java/org/dromara/flower/domain/...`
- FolwerDeliveryTemplate / FolwerDeliveryTemplateBo / FolwerDeliveryTemplateVo — 配送模板 `src/main/java/org/dromara/flower/domain/...`
- FolwerDeliveryTemperature / FolwerDeliveryTemperatureBo / FolwerDeliveryTemperatureVo — 温控配置 `src/main/java/org/dromara/flower/domain/...`
- FolwerDeliveryBox / FolwerDeliveryBoxBo / FolwerDeliveryBoxVo — 配送箱型定义 `src/main/java/org/dromara/flower/domain/...`
- FolwerDeliverySet / FolwerDeliverySetBo / FolwerDeliverySetVo — 配送设置 `src/main/java/org/dromara/flower/domain/...`
- FolwerPickAddr / FolwerPickAddrBo / FolwerPickAddrVo — 自提/收货地址 `src/main/java/org/dromara/flower/domain/...`
- FolwerAppletOrder / FolwerAppletOrderBo / FolwerAppletOrderVo — 小程序订单主表 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletOrderDetail / FolwerAppletOrderDetailBo / FolwerAppletOrderDetailVo — 小程序订单明细 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletOrderRefund / FolwerAppletOrderRefundBo / FolwerAppletOrderRefundVo — 小程序退款记录 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletOrderDvy / FolwerAppletOrderDvyBo / FolwerAppletOrderDvyVo — 小程序配送记录 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletOrderSet / FolwerAppletOrderSetBo / FolwerAppletOrderSetVo — 小程序订单配置 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletDelivery / FolwerAppletDeliveryBo / FolwerAppletDeliveryVo — 小程序配送任务 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletDeliveryPrice / FolwerAppletDeliveryPriceBo / FolwerAppletDeliveryPriceVo — 小程序配送计费规则 `src/main/java/org/dromara/flowerapplet/domain/...`
- FolwerAppletBasket / FolwerAppletBasketBo / FolwerAppletBasketVo — 小程序购物车 `src/main/java/org/dromara/flowerapplet/domain/...`

### 2.4 Mapper / XML

- FolwerOrderMapper / resources/mapper/flower/FolwerOrderMapper.xml — 订单主表数据访问
- FolwerOrderDetailMapper / resources/mapper/flower/FolwerOrderDetailMapper.xml — 订单明细数据访问
- FolwerOrderRefundMapper / resources/mapper/flower/FolwerOrderRefundMapper.xml — 退款记录数据访问
- FolwerOrderDvyMapper / resources/mapper/flower/FolwerOrderDvyMapper.xml — 配送记录数据访问
- FolwerOrderSetMapper / resources/mapper/flower/FolwerOrderSetMapper.xml — 订单配置数据访问
- FolwerDeliveryMapper / resources/mapper/flower/FolwerDeliveryMapper.xml — 配送任务数据访问
- FolwerDeliveryPriceMapper / resources/mapper/flower/FolwerDeliveryPriceMapper.xml — 配送计费规则数据访问
- FolwerDeliveryRuleMapper / resources/mapper/flower/FolwerDeliveryRuleMapper.xml — 配送规则数据访问
- FolwerDeliveryTemplateMapper / resources/mapper/flower/FolwerDeliveryTemplateMapper.xml — 配送模板数据访问
- FolwerDeliveryTemperatureMapper / resources/mapper/flower/FolwerDeliveryTemperatureMapper.xml — 温控配置数据访问
- FolwerDeliveryBoxMapper / resources/mapper/flower/FolwerDeliveryBoxMapper.xml — 配送箱型数据访问
- FolwerDeliverySetMapper / resources/mapper/flower/FolwerDeliverySetMapper.xml — 配送设置数据访问
- FolwerPickAddrMapper / resources/mapper/flower/FolwerPickAddrMapper.xml — 自提/收货地址数据访问
- FolwerAppletDeliveryMapper / resources/mapper/flower/FolwerAppletDeliveryMapper.xml — 小程序配送任务数据访问
- FolwerAppletBasketMapper / resources/mapper/flowerapplet/FolwerAppletBasketMapper.xml — 小程序购物车数据访问
- FolwerAppletDeliveryPriceMapper / resources/mapper/flowerapplet/FolwerAppletDeliveryPriceMapper.xml — 小程序配送计费数据访问
- FolwerAppletOrderMapper / resources/mapper/flowerapplet/FolwerAppletOrderMapper.xml — 小程序订单主表数据访问
- FolwerAppletOrderDetailMapper / resources/mapper/flowerapplet/FolwerAppletOrderDetailMapper.xml — 小程序订单明细数据访问
- FolwerAppletOrderRefundMapper / resources/mapper/flowerapplet/FolwerAppletOrderRefundMapper.xml — 小程序退款数据访问
- FolwerAppletOrderDvyMapper / resources/mapper/flowerapplet/FolwerAppletOrderDvyMapper.xml — 小程序配送记录数据访问
- FolwerAppletOrderSetMapper / resources/mapper/flowerapplet/FolwerAppletOrderSetMapper.xml — 小程序订单配置数据访问

## 3. 已知跨领域触点概览（引用 CROSS_DOMAIN_TOUCHPOINTS_DETAIL）

- Order → Member：订单查询会调用会员信息与等级服务，为订单视图补充昵称、手机号和等级名称。
- Order → Product：删除配送箱型前调用商品 SKU 服务校验是否仍被绑定，避免删除在用配置。
- Payment → Order：支付回调由支付中心触发，调用小程序订单服务完成支付确认与订单状态更新。

## 4. 视图装配与辅助组件

- OrderViewAssembler — 订单视图装配器，聚焦订单视图对象与会员/用户/地址等扩展信息的字段映射，位于 `org.dromara.flower.domain.order.assembler`。
