# Member Center 组件梳理（Phase 1）

## 1. 职责范围概览

- 统一管理会员资料、账号认证、小程序用户信息等基础用户档案能力。
- 定义会员等级体系并维护等级权益配置，供其他业务查询使用。
- 提供积分账户与兑换记录的存储能力，支撑积分增减与兑换流水记载。
- 记录会员相关的认证、购买等行为日志，用于审计与视图装配。

## 2. Member 领域代码组件清单

### 2.1 Controller

- AppletUserAuthController：小程序用户实名认证接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/AppletUserAuthController.java`）。
- AppletUserAuthlogController：小程序用户认证日志接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/AppletUserAuthlogController.java`）。
- MemberExchangeRecordController：会员兑换记录接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MemberExchangeRecordController.java`）。
- MemberLevelController：会员等级接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MemberLevelController.java`）。
- MemberLevelPrivilegeController：会员等级权益接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MemberLevelPrivilegeController.java`）。
- MemberPointsExchangeGoldController：积分兑换金币接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MemberPointsExchangeGoldController.java`）。
- MemberPurchaseRecordController：会员购买记录接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/MemberPurchaseRecordController.java`）。
- OneselfMemberLevelPrivilegeController：个人会员等级权益接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/controller/OneselfMemberLevelPrivilegeController.java`）。
- AppletUserInformationController：平台端小程序用户信息接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/platform/controller/AppletUserInformationController.java`）。
- FlowerAppletUserAuthController：小程序端用户认证接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FlowerAppletUserAuthController.java`）。
- FlowerAppletUserAuthlogController：小程序端用户认证日志接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FlowerAppletUserAuthlogController.java`）。
- FlowerAppletUserInformationController：小程序端用户信息接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/FlowerAppletUserInformationController.java`）。
- MemberAppletLevelController：小程序会员等级接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/MemberAppletLevelController.java`）。
- MemberAppletLevelPrivilegeController：小程序会员权益接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/MemberAppletLevelPrivilegeController.java`）。
- MemberAppletPurchaseRecordController：小程序会员购买记录接口（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/controller/MemberAppletPurchaseRecordController.java`）。

### 2.2 Service

- IAppletUserInformationService / AppletUserInformationServiceImpl：管理平台端小程序用户信息（接口与实现均位于 `ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/platform/service/`）。
- IAppletUserAuthService / AppletUserAuthServiceImpl：处理小程序用户实名认证（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IAppletUserAuthlogService / AppletUserAuthlogServiceImpl：维护小程序用户认证日志（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IMemberExchangeRecordService / MemberExchangeRecordServiceImpl：管理会员兑换记录（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IMemberLevelPrivilegeService / MemberLevelPrivilegeServiceImpl：维护会员等级权益配置（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IMemberLevelService / MemberLevelServiceImpl：管理会员等级定义（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IMemberPointsExchangeGoldService / MemberPointsExchangeGoldServiceImpl：处理积分兑换金币记录（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IMemberPurchaseRecordService / MemberPurchaseRecordServiceImpl：记录会员购买行为（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IOneselfMemberLevelPrivilegeService / OneselfMemberLevelPrivilegeServiceImpl：处理个人会员权益（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/service/`）。
- IFlowerAppletUserAuthService / FlowerAppletUserAuthServiceImpl：小程序端用户认证服务（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/`）。
- IFlowerAppletUserAuthlogService / FlowerAppletUserAuthlogServiceImpl：小程序端认证日志服务（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/`）。
- IFlowerAppletUserInformationService / FlowerAppletUserInformationServiceImpl：小程序端用户信息服务（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/`）。
- IMemberAppletLevelPrivilegeService / MemberAppletLevelPrivilegeServiceImpl：小程序会员等级权益服务（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/`）。
- IMemberAppletLevelService / MemberAppletLevelServiceImpl：小程序会员等级管理（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/`）。
- IMemberAppletPurchaseRecordService / MemberAppletPurchaseRecordServiceImpl：小程序会员购买记录服务（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/service/`）。

### 2.3 Domain（Bo / Vo / Entity）

- AppletUserAuth（Entity）：小程序用户认证实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/AppletUserAuth.java`）。
- AppletUserAuthlog（Entity）：小程序用户认证日志实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/AppletUserAuthlog.java`）。
- MemberExchangeRecord（Entity）：会员兑换记录实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/MemberExchangeRecord.java`）。
- MemberLevel（Entity）：会员等级实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/MemberLevel.java`）。
- MemberLevelPrivilege（Entity）：会员等级权益实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/MemberLevelPrivilege.java`）。
- MemberPointsExchangeGold（Entity）：积分兑换金币记录实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/MemberPointsExchangeGold.java`）。
- MemberPurchaseRecord（Entity）：会员购买记录实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/MemberPurchaseRecord.java`）。
- OneselfMemberLevelPrivilege（Entity）：个人会员等级权益实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/OneselfMemberLevelPrivilege.java`）。
- AppletUserAuthBo / AppletUserAuthlogBo（Bo）：认证与认证日志入参对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/bo/`）。
- MemberExchangeRecordBo / MemberLevelBo / MemberLevelPrivilegeBo / MemberPointsExchangeGoldBo / MemberPurchaseRecordBo / OneselfMemberLevelPrivilegeBo（Bo）：会员兑换、等级、权益、积分兑换、购买记录相关业务对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/bo/`）。
- AppletUserAuthVo / AppletUserAuthlogVo（Vo）：认证与认证日志视图对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/vo/`）。
- MemberExchangeRecordVo / MemberLevelPrivilegeVo / MemberLevelVo / MemberPointsExchangeGoldVo / MemberPurchaseRecordVo / OneselfMemberLevelPrivilegeVo（Vo）：会员兑换、等级、权益、积分兑换、购买记录等视图对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/domain/vo/`）。
- AppletUserInformation（Entity）/ AppletUserInformationBo / AppletUserInformationVo：平台端小程序用户信息相关对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/platform/domain/`）。
- FlowerAppletUserAuth / FlowerAppletUserAuthlog / FlowerAppletUserInformation（Entity）：小程序端用户认证与信息实体（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/domain/`）。
- FlowerAppletUserAuthBo / FlowerAppletUserAuthlogBo / FlowerAppletUserInformationBo（Bo）：小程序端认证与用户信息业务对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/domain/bo/`）。
- FlowerAppletUserAuthVo / FlowerAppletUserAuthlogVo / FlowerAppletUserInformationVo（Vo）：小程序端认证与用户信息视图对象（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/domain/vo/`）。

### 2.4 Mapper

- AppletUserAuthMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/AppletUserAuthMapper.java`） / AppletUserAuthMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/AppletUserAuthMapper.xml`）：小程序用户认证表映射。
- AppletUserAuthlogMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/AppletUserAuthlogMapper.java`） / AppletUserAuthlogMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/AppletUserAuthlogMapper.xml`）：小程序用户认证日志映射。
- MemberExchangeRecordMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/MemberExchangeRecordMapper.java`） / MemberExchangeRecordMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/MemberExchangeRecordMapper.xml`）：会员兑换记录表映射。
- MemberLevelMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/MemberLevelMapper.java`） / MemberLevelMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/MemberLevelMapper.xml`）：会员等级表映射。
- MemberLevelPrivilegeMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/MemberLevelPrivilegeMapper.java`） / MemberLevelPrivilegeMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/MemberLevelPrivilegeMapper.xml`）：会员等级权益表映射。
- MemberPointsExchangeGoldMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/MemberPointsExchangeGoldMapper.java`） / MemberPointsExchangeGoldMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/MemberPointsExchangeGoldMapper.xml`）：积分兑换金币记录表映射。
- MemberPurchaseRecordMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/MemberPurchaseRecordMapper.java`） / MemberPurchaseRecordMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/MemberPurchaseRecordMapper.xml`）：会员购买记录表映射。
- OneselfMemberLevelPrivilegeMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/mapper/OneselfMemberLevelPrivilegeMapper.java`） / OneselfMemberLevelPrivilegeMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/OneselfMemberLevelPrivilegeMapper.xml`）：个人会员等级权益表映射。
- AppletUserInformationMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flower/platform/mapper/AppletUserInformationMapper.java`） / AppletUserInformationMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flower/AppletUserInformationMapper.xml`）：平台端小程序用户信息表映射。
- FlowerAppletUserAuthMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/mapper/FlowerAppletUserAuthMapper.java`） / FlowerAppletUserAuthMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flowerapplet/FlowerAppletUserAuthMapper.xml`）：小程序端用户认证表映射。
- FlowerAppletUserAuthlogMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/mapper/FlowerAppletUserAuthlogMapper.java`） / FlowerAppletUserAuthlogMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flowerapplet/FlowerAppletUserAuthlogMapper.xml`）：小程序端用户认证日志映射。
- FlowerAppletUserInformationMapper（`ruoyi-modules/ruoyi-flower/src/main/java/org/dromara/flowerapplet/mapper/FlowerAppletUserInformationMapper.java`） / FlowerAppletUserInformationMapper.xml（`ruoyi-modules/ruoyi-flower/src/main/resources/mapper/flowerapplet/FlowerAppletUserInformationMapper.xml`）：小程序端用户信息表映射。

## 3. 跨领域触点概览

- Order → Member：订单查询与分页装配中调用会员等级与小程序用户信息服务，补充订单视图所需的会员昵称、手机号及等级信息。
