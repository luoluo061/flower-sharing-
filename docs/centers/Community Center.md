📘 Community Center（社区中心）设计文档 V1.0

美立七中心架构（MEILI Center Architecture） · Phase 1 输出

# Community Center — 社区中心设计文档（V1.0）
🎯 1. 社区中心的核心使命（Core Responsibilities）

Community Center 的功能定位非常清晰：

围绕“内容 + 社交”的一切功能都归社区中心负责。

它为平台提供“用户互动层”，提升粘性和内容生态。

🧩 2. Community Center 必须负责的内容

社区模块主要涉及三类核心对象：

✔ 1. 动态（Post / Feed）

类似于微博、朋友圈、小红书“Notes”。

负责：

用户发布动态（文字、图片、视频）

动态的审核、状态管理（审核中、已发布、屏蔽）

动态的可见性（公开、仅自己、粉丝可见 — 后续可扩展）

动态的阅读统计（浏览量）

动态是整个社区中心的基础。

✔ 2. 评论（Comment）

负责：

一级评论、二级评论

评论审核

评论删除

评论统计（评论数）

评论必须与“动态”进行解耦，未来可以复用于课程中心（课程评论）、商品中心（商品评价）。

Phase 3 会把评论做成“统一评论系统”。

✔ 3. 点赞（Like / Favor）

负责：

点赞记录

点赞/取消赞

点赞计数

与评论类似，Like 未来可以扩展到课程点赞、商品点赞。

Phase 3 会升级为“统一点赞服务”。

🚫 3. 社区中心明确不负责的内容（Boundary）

为了保持独立可复用，禁止社区中心混入以下职责：

❌ 用户信息（由 Member Center 提供）

社区中心的帖子、评论，只能引用：

userId

avatarUrl（只读）

nickname（只读）

不允许调用或更新用户资料。

❌ 订单信息（Order Center）

不要让社区帖子“绑定订单”，除非订单中心通过事件通知。

❌ 商品信息（Product Center）

社区动态可以“引用商品”，但不能写入商品中心。

❌ 营销逻辑（Marketing Center）

例如“发布动态送积分”，必须通过 事件 → 营销中心进行处理。

🌐 4. Community Center 子域结构（Subdomains）
/community
/post
/comment
/like
/audit


说明：

/post = 动态主表

/comment = 评论系统

/like = 点赞系统

/audit = 内容审核（未来可以接入 AI 文本/图片审核）

🔗 5. 跨中心依赖规则（Dependency Rules）

社区中心依赖非常少，是七中心中最干净的一个。

✔ 允许依赖（Read-only）
依赖中心	用途
Member Center	获取用户展示信息（只读）
❌ 禁止依赖

社区中心不得写入或调用：

Order Center

Product Center

Marketing Center

Edu Center

所有扩展能力必须通过 事件（Event Bus） 实现：

示例事件：
PostCreated → Marketing Center（发放积分）
CommentCreated → Member Center（用户贡献度增加）
LikeCreated → Marketing Center（触发活动奖励）

🛠 6. API 草案（未来统一接口）
动态（Post）
POST /community/post/create
GET  /community/post/list
GET  /community/post/{postId}
POST /community/post/audit
DELETE /community/post/{postId}

评论（Comment）
POST /community/comment/create
GET  /community/comment/list
DELETE /community/comment/{commentId}

点赞（Like）
POST /community/like/toggle
GET  /community/like/list

内容审核（Audit）
POST /community/audit/submit
GET  /community/audit/result

📦 7. 与 ruoyi-flower 代码的映射（Phase 0 → Phase 1）

根据 DOMAIN_COMPONENT_MAPPING.md：

属于 Community Center 的真实控制器有：
后台端（平台）

FlowerFriendsCommunityController

FlowerFriendsCommunityCommentController

FlowerFriendsCommunityLikeController

小程序端（App）

FlowerAppletFriendsCommunityController

FlowerAppletFriendsCommunityCommentController

FlowerAppletFriendsCommunityLikeController

Service 映射

如：

IFlowerFriendsCommunityService

IFlowerFriendsCommunityCommentService

IFlowerFriendsCommunityLikeService
（以及所有对应的 Impl）

Domain & Mapper 映射

包括：

FlowerFriendsCommunity

FlowerFriendsCommunityComment

FlowerFriendsCommunityLike
（及其 BO/VO）

对应的 Mapper 和 XML 都属于社区域。

🧹 8. Phase 1.5 清洗计划（可执行计划）
✔ Step 1：创建官方目录
org/dromara/flower/community/controller
org/dromara/flower/community/service
org/dromara/flower/community/domain
org/dromara/flower/community/mapper

✔ Step 2：移动所有社区相关类

（该步骤由 Codex 执行文件移动）

✔ Step 3：为所有类添加领域标签
/**
* @Domain(Community)
  */

✔ Step 4：拆除非法依赖

（目前社区中心无明显跨领域依赖，是最干净的中心之一）

✔ Step 5：准备 Phase 2 升级

将评论与点赞独立为：

Comment Center
Like Center


（类似 B 站、抖音的做法）