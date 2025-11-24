# Edu Center — 课程中心设计文档（V1.0）

美立七中心架构（MEILI Center Architecture） · Phase 1 输出

🎯 1. 课程中心的核心使命（Core Responsibilities）

Edu Center 负责所有与“课程 / 内容学习”相关的能力，是平台的 培训 / 课程 / 知识付费 能力中心。

一句话概括：

Edu Center = “课程 + 章节 + 视频 + 学习记录 + 购买记录” 的统一归属地。

无论业务是：

花艺培训 / 花店老板课程

农贸市场经营培训

无人机 / 体育培训课程

协会培训班、线上精品课

都应该复用同一套 Edu Center。

🧩 2. Edu Center 必须负责的内容

按你现有 ruoyi-flower 里的结构，Edu 范围大致如下：

✔ 2.1 课程主表（Course）

负责管理课程本身的信息：

课程名称

课程封面图

课程简介

授课讲师（teacherId）

课程类型（录播 / 直播 / 图文）

所属分类（如 花艺课程 / 农批经营 等）

价格（如有付费）

注意：课程定价本身可以由 Edu 持有，具体优惠由 Marketing Center 处理。

✔ 2.2 课程详情 / 大纲（Course Detail / Syllabus）

对应你代码中的：

CoursesManagerDetail

CoursesManagerVideo 等

需要管理：

课程章节列表（Chapter）

章节下的视频/内容列表（Section/Lesson）

每节课的时长 / 顺序 / 预览权限

✔ 2.3 课程分类（Course Category）

类似商品类目，但专属 Edu：

课程分类树（如：花艺 → 入门 / 进阶 / 店长课）

分类启用 / 禁用

分类在前端展示时的排序

✔ 2.4 课程购买记录（Course Purchase Record）

对应你现有的：

CoursesPurchaseRecords

小程序端相关 xxxAppletPurchaseRecords

负责记录：

userId

courseId

支付金额 / 是否免费获得

购买时间

有效期（如有）

业务上，这里是“教育侧订单”，但不等于平台的主订单 Order Center。
可以直接使用 Payment Center 完成支付，或者通过 Order Center 做统一订单。

✔ 2.5 学习记录 / 播放日志（Learning Log）

对应你有的：

CoursesPlayedLog

负责：

用户观看进度

学习时长

最近一次观看时间

是否学完（completed）

这些数据可用于后续：

学习报告

课程完课率统计

教学效果评估（给老师或合作方用）

🚫 3. Edu Center 明确不负责的内容（Boundary）

为了做到“课程中心可以在任意系统中复用”，必须把边界划清。

❌ 不负责会员资料（Member Center 做）

不存储用户手机号、姓名等，仅使用 userId 作为外键

不自己维护会员等级、积分等信息

❌ 不维护商品库存（Product Center 做）

即使课程是付费商品，也 不和 SKU 库存耦合（课程本身没有库存的概念）。

❌ 不处理支付渠道逻辑（Payment Center 做）

课程收费：

可以通过 Order Center 统一生成订单 + 支付

或在早期版本由 Edu Center 直接调用 Payment Center

但 Edu 本身不做支付通道的接入

❌ 不做优惠券和营销策略（Marketing Center 做）

“课程优惠券 / 课程限时折扣” → 归 Marketing Center

Edu 只做“标价 + 课程信息”，实际应付价格交由 Marketing 计算

🧱 4. Edu Center 的内部子域（Subdomains）

建议内部结构：

/edu
/course           课程主表
/course-detail    课程详情 / 章节
/course-video     课程视频资源
/course-type      课程分类
/purchase-record  课程购买记录
/played-log       播放 / 学习记录


对应你现有类名：

CoursesManager*

CoursesType

CoursesPurchaseRecords

CoursesPlayedLog

小程序端 CoursesApplet* 等

🔗 5. 跨中心依赖规则（Dependency Rules）

Edu Center 有几个常见依赖，但要保证“软耦合”。

✔ 可以 Read-only 依赖：
依赖中心	用途
Member Center	根据 userId 查询用户展示信息（头像、昵称）
Product Center（可选）	若课程按商品方式售卖，读取商品信息
Marketing Center	获取优惠信息（针对课程的促销）
✔ 通过事件或接口配合的场景：

用户购买课程成功

支付完成后，Order Center 或 Payment Center 发出事件：CoursePurchased(userId, courseId, amount)

Edu Center 创建课程购买记录。

用户完成课程学习

Edu Center 在学习日志中判断某课程完成 → 发出 CourseCompleted(userId, courseId) 事件

Member Center 或 Marketing Center 监听该事件，发放证书、积分或优惠券。

❌ 禁止的直接依赖：

Edu Center 不得直接修改 Member Center 的积分 / 等级

不直接向 Marketing Center 发券

不直接操作 Order Center 的订单表

所有这些都要通过 “事件（Event）”或“上层应用服务” 来协同。

🛠 6. API 草案（未来统一接口）
📌 课程管理（后台）
GET  /edu/course/list
GET  /edu/course/{courseId}
POST /edu/course/create
PUT  /edu/course/update
DELETE /edu/course/{courseId}

📌 课程详情 / 章节
GET  /edu/course/{courseId}/chapters
POST /edu/course/{courseId}/chapter/create

📌 课程分类
GET  /edu/course-type/tree
POST /edu/course-type/create

📌 课程购买记录

后台管理：

GET  /edu/purchase-record/list


前台用户查询：

GET /edu/my/courses

📌 播放 / 学习记录
POST /edu/played-log/report    （前端周期上报进度）
GET  /edu/played-log/{courseId}

📦 7. 与 ruoyi-flower 的映射（真实代码对应）

根据 DOMAIN_COMPONENT_MAPPING.md，你现有系统中属于 Edu Center 的组件包括：

Controller

org.dromara.flower.controller.CoursesManagerController

org.dromara.flower.controller.CoursesManagerDetailController

org.dromara.flower.controller.CoursesManagerVideoController

org.dromara.flower.controller.CoursesTypeController

org.dromara.flower.controller.CoursesPurchaseRecordsController

org.dromara.flower.controller.CoursesPlayedLogController

小程序端：

org.dromara.flowerapplet.controller.CoursesAppletManagerController

org.dromara.flowerapplet.controller.CoursesAppletPurchaseRecordsController

org.dromara.flowerapplet.controller.CoursesAppletTypeController

Service

ICoursesManagerService / Impl

ICoursesManagerDetailService / Impl

ICoursesManagerVideoService / Impl

ICoursesTypeService / Impl

ICoursesPurchaseRecordsService / Impl

ICoursesPlayedLogService / Impl

以及对应的 Applet 版本：

ICoursesAppletManagerService / Impl

ICoursesAppletPurchaseRecordsService / Impl

ICoursesAppletTypeService / Impl

Domain & Mapper

CoursesManager / Bo / Vo

CoursesManagerDetail / Bo / Vo

CoursesManagerVideo / Bo / Vo

CoursesType / Bo / Vo

CoursesPurchaseRecords / Bo / Vo

CoursesPlayedLog / Bo / Vo

对应的 Mapper 和 XML 都属于 Edu Center。

🧹 8. Phase 1.5 Edu Center 清洗计划（可执行）
Step 1：创建 EduCenter 包结构
org/dromara/flower/edu/controller
org/dromara/flower/edu/service
org/dromara/flower/edu/domain
org/dromara/flower/edu/mapper

Step 2：移动所有 Edu 相关类

Codex 执行：将 Courses* 与 CoursesApplet* 相关类移动到对应 edu 包下

保持现有逻辑不变，仅调整包路径

Step 3：为所有类加上领域标记
/**
* @Domain(Edu)
  */

Step 4：检查跨域调用

如果课程里直接依赖了 Member / Order / Marketing 的服务

在代码处加上 TODO Phase 2 标记

后续再重构为事件 / 上层编排服务

✅ 9. 小结：Edu Center 的地位

Edu Center = 所有“课程 / 学习”业务的底座。

可以支撑：花艺培训、电商商户培训、无人机培训、协会继续教育等

与订单、支付、营销解耦

未来你只要换一个“课程模板 + 文案”，这套中心就能复用到任何项目上

到这里，“七大中心”的架构文档就已经全部齐了：

Auth（前面隐含在 Member / System 中，后续可单独抽出）

Member Center

Product Center

Order Center

Payment Center

Marketing Center

Community Center

Edu Center# Edu Center — 课程中心设计文档（V1.0）

美立七中心架构（MEILI Center Architecture） · Phase 1 输出

🎯 1. 课程中心的核心使命（Core Responsibilities）

Edu Center 负责所有与“课程 / 内容学习”相关的能力，是平台的 培训 / 课程 / 知识付费 能力中心。

一句话概括：

Edu Center = “课程 + 章节 + 视频 + 学习记录 + 购买记录” 的统一归属地。

无论业务是：

花艺培训 / 花店老板课程

农贸市场经营培训

无人机 / 体育培训课程

协会培训班、线上精品课

都应该复用同一套 Edu Center。

🧩 2. Edu Center 必须负责的内容

按你现有 ruoyi-flower 里的结构，Edu 范围大致如下：

✔ 2.1 课程主表（Course）

负责管理课程本身的信息：

课程名称

课程封面图

课程简介

授课讲师（teacherId）

课程类型（录播 / 直播 / 图文）

所属分类（如 花艺课程 / 农批经营 等）

价格（如有付费）

注意：课程定价本身可以由 Edu 持有，具体优惠由 Marketing Center 处理。

✔ 2.2 课程详情 / 大纲（Course Detail / Syllabus）

对应你代码中的：

CoursesManagerDetail

CoursesManagerVideo 等

需要管理：

课程章节列表（Chapter）

章节下的视频/内容列表（Section/Lesson）

每节课的时长 / 顺序 / 预览权限

✔ 2.3 课程分类（Course Category）

类似商品类目，但专属 Edu：

课程分类树（如：花艺 → 入门 / 进阶 / 店长课）

分类启用 / 禁用

分类在前端展示时的排序

✔ 2.4 课程购买记录（Course Purchase Record）

对应你现有的：

CoursesPurchaseRecords

小程序端相关 xxxAppletPurchaseRecords

负责记录：

userId

courseId

支付金额 / 是否免费获得

购买时间

有效期（如有）

业务上，这里是“教育侧订单”，但不等于平台的主订单 Order Center。
可以直接使用 Payment Center 完成支付，或者通过 Order Center 做统一订单。

✔ 2.5 学习记录 / 播放日志（Learning Log）

对应你有的：

CoursesPlayedLog

负责：

用户观看进度

学习时长

最近一次观看时间

是否学完（completed）

这些数据可用于后续：

学习报告

课程完课率统计

教学效果评估（给老师或合作方用）

🚫 3. Edu Center 明确不负责的内容（Boundary）

为了做到“课程中心可以在任意系统中复用”，必须把边界划清。

❌ 不负责会员资料（Member Center 做）

不存储用户手机号、姓名等，仅使用 userId 作为外键

不自己维护会员等级、积分等信息

❌ 不维护商品库存（Product Center 做）

即使课程是付费商品，也 不和 SKU 库存耦合（课程本身没有库存的概念）。

❌ 不处理支付渠道逻辑（Payment Center 做）

课程收费：

可以通过 Order Center 统一生成订单 + 支付

或在早期版本由 Edu Center 直接调用 Payment Center

但 Edu 本身不做支付通道的接入

❌ 不做优惠券和营销策略（Marketing Center 做）

“课程优惠券 / 课程限时折扣” → 归 Marketing Center

Edu 只做“标价 + 课程信息”，实际应付价格交由 Marketing 计算

🧱 4. Edu Center 的内部子域（Subdomains）

建议内部结构：

/edu
/course           课程主表
/course-detail    课程详情 / 章节
/course-video     课程视频资源
/course-type      课程分类
/purchase-record  课程购买记录
/played-log       播放 / 学习记录


对应你现有类名：

CoursesManager*

CoursesType

CoursesPurchaseRecords

CoursesPlayedLog

小程序端 CoursesApplet* 等

🔗 5. 跨中心依赖规则（Dependency Rules）

Edu Center 有几个常见依赖，但要保证“软耦合”。

✔ 可以 Read-only 依赖：
依赖中心	用途
Member Center	根据 userId 查询用户展示信息（头像、昵称）
Product Center（可选）	若课程按商品方式售卖，读取商品信息
Marketing Center	获取优惠信息（针对课程的促销）
✔ 通过事件或接口配合的场景：

用户购买课程成功

支付完成后，Order Center 或 Payment Center 发出事件：CoursePurchased(userId, courseId, amount)

Edu Center 创建课程购买记录。

用户完成课程学习

Edu Center 在学习日志中判断某课程完成 → 发出 CourseCompleted(userId, courseId) 事件

Member Center 或 Marketing Center 监听该事件，发放证书、积分或优惠券。

❌ 禁止的直接依赖：

Edu Center 不得直接修改 Member Center 的积分 / 等级

不直接向 Marketing Center 发券

不直接操作 Order Center 的订单表

所有这些都要通过 “事件（Event）”或“上层应用服务” 来协同。

🛠 6. API 草案（未来统一接口）
📌 课程管理（后台）
GET  /edu/course/list
GET  /edu/course/{courseId}
POST /edu/course/create
PUT  /edu/course/update
DELETE /edu/course/{courseId}

📌 课程详情 / 章节
GET  /edu/course/{courseId}/chapters
POST /edu/course/{courseId}/chapter/create

📌 课程分类
GET  /edu/course-type/tree
POST /edu/course-type/create

📌 课程购买记录

后台管理：

GET  /edu/purchase-record/list


前台用户查询：

GET /edu/my/courses

📌 播放 / 学习记录
POST /edu/played-log/report    （前端周期上报进度）
GET  /edu/played-log/{courseId}

📦 7. 与 ruoyi-flower 的映射（真实代码对应）

根据 DOMAIN_COMPONENT_MAPPING.md，你现有系统中属于 Edu Center 的组件包括：

Controller

org.dromara.flower.controller.CoursesManagerController

org.dromara.flower.controller.CoursesManagerDetailController

org.dromara.flower.controller.CoursesManagerVideoController

org.dromara.flower.controller.CoursesTypeController

org.dromara.flower.controller.CoursesPurchaseRecordsController

org.dromara.flower.controller.CoursesPlayedLogController

小程序端：

org.dromara.flowerapplet.controller.CoursesAppletManagerController

org.dromara.flowerapplet.controller.CoursesAppletPurchaseRecordsController

org.dromara.flowerapplet.controller.CoursesAppletTypeController

Service

ICoursesManagerService / Impl

ICoursesManagerDetailService / Impl

ICoursesManagerVideoService / Impl

ICoursesTypeService / Impl

ICoursesPurchaseRecordsService / Impl

ICoursesPlayedLogService / Impl

以及对应的 Applet 版本：

ICoursesAppletManagerService / Impl

ICoursesAppletPurchaseRecordsService / Impl

ICoursesAppletTypeService / Impl

Domain & Mapper

CoursesManager / Bo / Vo

CoursesManagerDetail / Bo / Vo

CoursesManagerVideo / Bo / Vo

CoursesType / Bo / Vo

CoursesPurchaseRecords / Bo / Vo

CoursesPlayedLog / Bo / Vo

对应的 Mapper 和 XML 都属于 Edu Center。

🧹 8. Phase 1.5 Edu Center 清洗计划（可执行）
Step 1：创建 EduCenter 包结构
org/dromara/flower/edu/controller
org/dromara/flower/edu/service
org/dromara/flower/edu/domain
org/dromara/flower/edu/mapper

Step 2：移动所有 Edu 相关类

Codex 执行：将 Courses* 与 CoursesApplet* 相关类移动到对应 edu 包下

保持现有逻辑不变，仅调整包路径

Step 3：为所有类加上领域标记
/**
* @Domain(Edu)
  */

Step 4：检查跨域调用

如果课程里直接依赖了 Member / Order / Marketing 的服务

在代码处加上 TODO Phase 2 标记

后续再重构为事件 / 上层编排服务

✅ 9. 小结：Edu Center 的地位

Edu Center = 所有“课程 / 学习”业务的底座。

可以支撑：花艺培训、电商商户培训、无人机培训、协会继续教育等

与订单、支付、营销解耦

未来你只要换一个“课程模板 + 文案”，这套中心就能复用到任何项目上

到这里，“七大中心”的架构文档就已经全部齐了：

Auth（前面隐含在 Member / System 中，后续可单独抽出）

Member Center

Product Center

Order Center

Payment Center

Marketing Center

Community Center

Edu Center