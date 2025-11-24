📘 Member Center（会员中心）设计文档 V1.0

路径：docs/centers/member-center.md

# Member Center — 会员中心设计文档（V1.0）

美立统一架构（MEILI Center Architecture） · Phase 1 输出
Author: 总架构师 GPT

🎯 1. 会员中心的核心职责（What Member Center Must Do）

Member Center 是整个美立市集体系中最“基础、通用、可复用”的业务中心之一。它的目标是：

让所有业务系统都使用同一套“会员资料、等级、权益、积分账户、认证信息、历史行为”能力。

它必须负责：
1. 会员身份信息

会员基本资料（昵称、手机号、注册方式）

小程序用户信息（微信 UID、openid、unionid 等）

认证信息（实名、身份校验、审核日志）

2. 会员等级体系

等级定义

等级成长条件

等级权益（如折扣、专属商品、赠券等）

3. 会员积分体系

积分账户

积分明细（获取 / 消耗）

积分兑换逻辑（基础逻辑，不负责商城兑换业务）

4. 会员行为记录

注册记录

认证/登录日志

消费记录（仅做记录，不做订单逻辑）

活动参与记录（可选）

5. 会员标签（Tagging）

用于后期营销体系使用（但标签本身属于会员中心）。

🔥 2. 会员中心绝对不负责的内容（边界 Boundary）

为了保持中心通用性，Member Center 必须保持“纯粹”，所以：

❌ 不做订单业务

不能判断订单是否可下、订单价格、订单优惠等。

❌ 不做商品业务

不涉及 SKU、库存、配送等。

❌ 不做支付业务

不做支付结果判断、不做账单。

❌ 不做营销策略

积分兑换商品、优惠券发放属于“营销中心”。

🧱 3. Member Center 的主要业务子模块（Subdomains）
✔ 1. User Profile（用户档案）

负责基础资料、认证资料、小程序信息。

✔ 2. Level System（等级系统）

等级、成长值、权益配置。

✔ 3. Points System（积分系统）

积分账户、积分流水。

✔ 4. User Auth（认证）

实名认证、认证日志。

✔ 5. User Activity（行为日志）

登录、访问、购买记录（记录，不做业务判断）。

🔗 4. Member Center 的跨中心依赖规则（Dependency Rules）
✔ 可以被其他中心查询（Read-only）

商品中心（查看用户信息做个性化推荐）

订单中心（下单需要知道会员等级、积分账户）

营销中心（发券、算折扣需要知道等级/积分）

支付中心（账单抬头、账户信息）

✔ 可以向下依赖 Auth Center（获取 token 用户）

但不能反向依赖任何业务中心。

❌ 不允许依赖其他业务中心

不能访问 Order、Product、Payment、Marketing。

🧩 5. Member Center 与数据库（Data Ownership）
Member Center 必须完全拥有以下表：

member_user

member_auth

member_auth_log

member_level

member_level_privilege

member_points

member_points_log

member_purchase_record（仅做记录，归属 Member）

若现有表命名混乱，Phase 2 再重构，不影响 Phase 1。
🚀 6. Member Center 的 API 草案（Future API Design）

假设未来所有中心都是组件化：

用户资料 API
GET /member/user/{id}

查询用户资料。

POST /member/user

注册用户。

等级体系 API
GET /member/level/list

获取全部等级。

POST /member/level

新增等级。

积分 API
GET /member/points/{userId}

查询用户积分账户。

POST /member/points/add

增加积分。

POST /member/points/reduce

扣减积分（营销中心调用）。

认证 API
POST /member/auth

发起认证。

GET /member/auth/log/{userId}

认证日志。

🔥 7. Member Center 在美立体系中的战略地位

会员中心是美立市集最重要的复用能力之一。

它的设计目标不是“给花店用”，不是“给农贸市场用”，
而是：

任何业务，只要有“用户”，就必须使用这一套。


你未来的平台：

美立市集

花享家

智慧农贸

聚合支付（商户/客户）

低空经济协会会员

线上课程系统

社区互动系统

都将 复用同一个 Member Center。

这就是你在做“可复用平台”的核心价值。

🛠 8. 当前 ruoyi-flower 中的映射（来自 DOMAIN_COMPONENT_MAPPING.md）

如下子模块属于会员中心：

AppletUserAuth

AppletUserAuthlog

MemberLevel

MemberLevelPrivilege

MemberPurchaseRecord

MemberPointsExchangeGold

MemberExchangeRecord

小程序会员体系（Appletxxx）

平台端会员信息（Platformxxx）

Phase 1.5 阶段将整理到统一包结构：

org.dromara.flower.member.controller
org.dromara.flower.member.service
org.dromara.flower.member.domain
org.dromara.flower.member.mapper

🧭 9. 下一阶段（Phase 1.5）Member Center 重构计划
仅涉及包整理（不动业务、不动数据库）

创建 member/ 包结构

移动所有相关 Controller

移动所有 Service / Domain / Mapper

补充 README

为所有组件添加 @Domain(Member) 注释

特殊跨域调用标注 TODO Phase 2