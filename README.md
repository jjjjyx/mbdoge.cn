
# mbdoge.cn

## 项目说明
这已经不是一个博客项目，是一个一博客项目为目的，做项目架构练手的项目。回首我的博客项目，从最开始采用nodejs，自己构建项目框架，到先使用spring boot,也是经历丰富。
项目的成型最完善的时候是在nodejs 的时代，后续使用spring boot 重构的时候，太忙就给漏下了。    
也好在没有在那时候写，那时候是刚学spring boot 到时候，啥都不知道，只是觉得很香，就想用。    
而现在经历了这段时间的公司项目使用，对spring boot也了解很多了，并且也写了一个stater 项目，抽出来很多公共的代码。这个项目将会与starter项目共同发展。    
本次项目的构造使用最近使用技术，（2020年4月6日）

## 项目架构
1. 构建工具：gradle #配置简单
2. 语言后端 Java，前端 Typescript
3. 后端框架 spring boot, 具体采用了什么依赖就不写了
4. 前端框架 nuxt.js, ui: Element, css: dart-scss
5. 整体项目使用一个仓库，分为多模块（多模块这里用的不是很好，欢迎大佬指正）

## 2020年4月27日 -- 小结
photoswipe 这个插件真是给我难着了，这该死的插件必须要高宽，七牛云又不给高宽 ，有高宽，但是只能单张单张获取，
七牛的saveKey中又不能放高宽,最终绕了一圈，在获取列表后后台批量查询信息并缓存，在提供接口批量获取信息，最终实现

## 2020年4月26日 --
* 完成右键菜单，比较简易，没有多级
* 完成了图片的框选，很nice 到时候写一个文章

框选陪右键，图片的操作容易多了
 
## 2020年4月25日 -- 
这几天受了点伤，耽误了。

## 2020年4月20日 -- 计划
整一个右键菜单组件，之前利用iview 做了一个右键菜单组件，但是现在使用了了element 就觉得耦合太大了，所以打算从新写个不耦合的
```vue
<!-- 基础调用方式 -->
<button v-contextmenu:menu></button>
<ul ref="menu">
    <li>菜单一</li>
    <li>菜单一</li>
</ul>
```

调用方式灵感来自 elementUI 的 `popover` 组件
这样的封装就完全可以在调用者绑定菜单事件，调用者控制菜单样式。指令只需要控制菜单的显示，以及位置即可，可预见的问题，多级菜单的弹出位置可能存在文件，
并且不好控制。整复杂点就像下拉菜单的那种提供上菜单项组件


## 2020年4月18日 -- 小结
本来想有限做一下图片管理模块，但是考虑到没有基础的表格管理封装，会产生很多重复代码。
所以先做了基础的管理部分，在将表格复杂化做出图片管理。 等图片管理，上传完成在补上效果图吧


## 2020年4月16日 -- 小结
突然发现后端就写的差不多了。惊了？列一些功能当备注。
- [x] 文章管理 
    - 文章的展示类型修改（预计做3种板式的文章查看页，要支持修改）
- [x] 分类管理
- [x] 评论管理
- [ ] 用户管理 （就我一个用户管理个啥 - api 不写了）
- [ ] 七牛云图片资源管理 (方便在各个模块中插入，编辑文章，文章封面..等等)
    - 上传 token 获取 api
    - 图片管理
- [ ] 在线用户管理 （这里可以考虑写写）
- [ ] 前台展示相关api
    - 置顶文章
    - 热门文章
    - 推荐
    - 标签云
    - 标签查询文章列表
    - 分类文章列表
    - 热门分类功能 （考虑吧一下比较好的分类置顶在首页，可以在后台动态的修改）
- [ ] 站点设置
    - 网站标题，说明，关键词等说明的设置

> 列了下功能，脑子清洗了，还有很多要做


## 目前有问题的部分

* 编译部署    
  由于nuxt.js 也会开启一个端口，所以我猜想最后的打包中是一个jar，以及nuxt build的产物，但是nuxt.js 的编译产物还需要 node_modules 所以这个编译后还需要 package.json，
  初步解决方案是在gradle的编译后进行一些配置，将jar 与这些东西进行一些整理，整理到一起，方便拷贝到服务器部署，这样服务上就不需要留源代码。
  其他方案，研究下`docker` 看看有没有更加好的方案

## 给自己的警示
* 坚持下去
* 多写测试代码，测试用例 (nodejs 版本放弃，有一部分原因是代码维护性太差，没测试)
* 多写注释，遵循标准
