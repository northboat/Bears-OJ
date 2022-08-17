---
title: Hexo
date: 2021-2-14
tags:
  - Wheel
  - FrontEnd
---

## 环境搭配

### nodejs

### cnpm和hexo

~~~
//查看版本号：检查node是否安装成功
C:\Windows\system32>node -v



//检查npm是否安装成功
C:\Windows\system32>npm -v



//用npm下载cnpm
    
C:\Windows\system32>npm install -g cnpm --registry=https://registry.npm.taobao.org



//检查cnpm是否安装成功
    
C:\Windows\system32>cnpm



      
//查看cnpm版本号
          
C:\Windows\system32>cnpm -v




//用cnpm下载hexo

C:\Windows\system32>cnpm install -g hexo -cli



 
//查看hexo版本号
    
C:\Windows\system32>hexo -v

   
   
//用cnpm在blog目录下安装git插件

cnpm install --save hexo-deployer-git
   
   
   
   
//配置_config.yml文件

用notepad++打开_config.yml文件修改deploy配置：

deploy:
  type: git
  repo: https://github.com/NorthBoat/NorthBoat.github.io
  branch: master
  
  
  
//更换主题

将主题下载在themes文件夹，修改_config.yml文件themes为新主题文件夹名字(无后缀)
~~~

## Hexo 使用

~~~
初始化博客：hexo init
    
启动预览：hexo s(start)

创建博文：hexo n(new) "我的第一篇博客文章"
    
编写博文"我的第一篇博客文章.md"

清理文件夹：hexo clean
   
生成：hexo g(generate)
    
将本地博客布署在GitHub(配置好插件以及deploy后)：
hexo d(deploy)
    
更换主题：将主题下载到themes文件夹，修改_config.yml中themes配置
~~~

访问博客：`https://NorthBoat.github.io/`

若在配置文件`_config.yml`中如果仓库写错了，在部署时将报错10054

## 更换主题

> 以`maupassant`为例
>

### 安装主题

安装主题以及渲染器

~~~bash
git clone https://github.com/tufu9441/maupassant-hexo.git themes/maupassant  
npm install hexo-renderer-pug --save  
npm install hexo-renderer-sass --save  
~~~

编辑Hexo目录下的 `_config.yml`，将`theme`的值改为`maupassant`

在博客的根目录下有一个`theme`文件夹，你的主题将下载在那里面

注：依赖`hexo-renderer-sass`安装时容易报错，很可能是国内网络问题，请尝试使用代理或者切换至NPM的国内镜像源安装。

### 功能配置

默认配置

~~~yaml
disqus:
  enable: false ## If you want to use Disqus comment system, please set the value to true.
  shortname: ## Your disqus_shortname, e.g. username
  api: ## You can visit Disqus comments in China mainland without barriers using Disqus API, e.g. https://disqus.skk.moe/disqus/
  apikey: ## Your API key obtained in Disqus API Application, e.g. yk00ZB1fjYGRkrCrDDRYDUjpp26GJWJiJRZQZ5SY0r3th5FMW6pnSzQMnWH7ua7r
  admin: ## Username of your Disqus moderator, e.g. username
  admin_label: ## The text of Disqus moderator badge, e.g. Mod
uyan: ## Your uyan_id. e.g. 1234567
livere: ## Your livere data-uid, e.g. MTAyMC8zMDAxOC78NTgz
changyan: ## Your changyan appid, e.g. cyrALsXc8
changyan_conf: ## Your changyan conf, e.g. prod_d8a508c2825ab57eeb43e7c69bba0e8b
gitalk: ## See: https://github.com/gitalk/gitalk
  enable: false ## If you want to use Gitment comment system please set the value to true.
  owner: ## Your GitHub ID, e.g. username
  repo: ## The repository to store your comments, make sure you're the repo's owner, e.g. gitalk.github.io
  client_id: ## GitHub client ID, e.g. 75752dafe7907a897619
  client_secret: ## GitHub client secret, e.g. ec2fb9054972c891289640354993b662f4cccc50
  admin: ## Github repo owner and collaborators, only these guys can initialize github issues.
valine: ## See: https://valine.js.org
  enable: false ## If you want to use Valine comment system, please set the value to true.
  appid: ## Your LeanCloud application App ID, e.g. pRBBL2JR4N7kLEGojrF0MsSs-gzGzoHsz
  appkey: ## Your LeanCloud application App Key, e.g. tjczHpDfhjYDSYddzymYK1JJ
  notify: false ## Mail notifier, see https://github.com/xCss/Valine/wiki/Valine-评论系统中的邮件提醒设置
  verify: false ## Validation code.
  placeholder: Just so so ## Comment box placeholders.
  avatar: "mm" ## Gravatar type, see https://github.com/xCss/Valine/wiki/avatar-setting-for-valine
  pageSize: 10 ## Number of comments per page.
  guest_info: nick,mail,link ## Attributes of reviewers.
minivaline: ## See: https://github.com/MiniValine/MiniValine
  enable: false ## If you want to use MiniValine comment system, please set the value to true.
  appId: ## Your LeanCloud application App ID, e.g. pRBBL2JR4N7kLEGojrF0MsSs-gzGzoHsz
  appKey: ## Your LeanCloud application App Key, e.g. tjczHpDfhjYDSYddzymYK1JJ
  placeholder: Write a Comment ## Comment box placeholder.
  adminEmailMd5: ## The MD5 of Admin Email to show Admin Flag.
  math: true ## Support MathJax.
  md: true ## Support Markdown.
  # MiniValine's display language depends on user's browser or system environment
  # If you want everyone visiting your site to see a uniform language, you can set a force language value
  # Available values: en  | zh-CN | (and many more)
  # More i18n info: https://github.com/MiniValine/minivaline-i18n
  lang:
waline: ## See: https://waline.js.org/
  enable: false ## If you want to use Waline comment system, please set the value to true.
  serverURL: ## Your server url, e.g. https://your-domain.vercel.app
  pageSize: ## The desired number of comments shown in each page.
utterances: ## See: https://utteranc.es
  enable: false ## If you want to use Utterances comment system, please set the value to true.
  repo: ## The repository utterances will connect to, e.g. tufu9441/comments
  identifier: title ## The mapping between blog posts and GitHub issues.
  theme: github-light ## Choose an Utterances theme which matches your blog.
twikoo: ## See: https://twikoo.js.org
  enable: false ## If you want to use twikoo comment system, please set the value to true.
  envId: ## Tencent CloudBase envId
  region: ## Tencent CloudBase region, e.g. ap-shanghai
  path: ## Article path, e.g. window.location.pathname

google_search: true ## Use Google search, true/false.
baidu_search: false ## Use Baidu search, true/false.
swiftype: ## Your swiftype_key, e.g. m7b11ZrsT8Me7gzApciT
self_search: false ## Use a jQuery-based local search engine, true/false.
google_analytics: ## Your Google Analytics tracking id, e.g. UA-42425684-2
baidu_analytics: ## Your Baidu Analytics tracking id, e.g. 8006843039519956000
fancybox: true ## If you want to use fancybox please set the value to true.
show_category_count: false ## If you want to show the count of categories in the sidebar widget please set the value to true.
toc_number: true ## If you want to add list number to toc please set the value to true.
shareto: false ## If you want to use the share button please set the value to true, and you must have hexo-helper-qrcode installed.
busuanzi: false ## If you want to use Busuanzi page views please set the value to true.
wordcount: false ## If you want to display the word counter and the reading time expected to spend of each post please set the value to true, and you must have hexo-wordcount installed.
widgets_on_small_screens: false ## Set to true to enable widgets on small screens.
canvas_nest:
  enable: false ## If you want to use dynamic background please set the value to true, you can also fill the following parameters to customize the dynamic effect, or just leave them blank to keep the default effect.
  color: ## RGB value of the color, e.g. "100,99,98"
  opacity: ## Transparency of lines, e.g. "0.7"
  zIndex: ## The z-index property of the background, e.g. "-1"
  count: ## Quantity of lines, e.g. "150"
donate:
  enable: false ## If you want to display the donate button after each post, please set the value to true and fill the following items on your need. You can also enable donate button in a page by adding a "donate: true" item to the front-matter.
  github: ## GitHub URL, e.g. https://github.com/Kaiyuan/donate-page
  alipay_qr: ## Path of Alipay QRcode image, e.g. /img/AliPayQR.png
  wechat_qr: ## Path of Wechat QRcode image, e.g. /img/WeChatQR.png
  btc_qr: ## Path of Bitcoin QRcode image, e.g. /img/BTCQR.png
  btc_key: ## Bitcoin key, e.g. 1KuK5eK2BLsqpsFVXXSBG5wbSAwZVadt6L
  paypal_url: ## Paypal URL, e.g. https://www.paypal.me/tufu9441
post_copyright:
  enable: false ## If you want to display the copyright info after each post, please set the value to true and fill the following items on your need.
  author: ## Your author name, e.g. tufu9441
  copyright_text: ## Your copyright text, e.g. The author owns the copyright, please indicate the source reproduced.
love: false ## If you want the peach heart to appear when you click anywhere, set the value to true.
plantuml: ## Using PlantUML to generate UML diagram, must install hexo-filter-plantuml (https://github.com/miao1007/hexo-filter-plantuml).
  render: "PlantUMLServer" ##  Local or PlantUMLServer.
  outputFormat: "svg" ## common options: svg/png
copycode: true ## If you want to enable one-click copy of the code blocks, set the value to true.
dark: false ## If you want to toggle between light/dark themes, set the value to true.
totop: true ## If you want to use the rocketship button to return to the top, set the value to true.
external_css: false ## If you want to load an external CSS file, set the value to true and create a file named "external.css" in the source/css folder.

menu:
  - page: home
    directory: .
    icon: fa-home
  - page: archive
    directory: archives/
    icon: fa-archive
  - page: about
    directory: about/
    icon: fa-user
  - page: rss
    directory: atom.xml
    icon: fa-rss

widgets: ## Six widgets in sidebar provided: search, category, tag, recent_posts, recent_comments and links.
  - search
  - category
  - tag
  - recent_posts
  - recent_comments
  - links

links:
  - title: site-name1
    url: http://www.example1.com/
  - title: site-name2
    url: http://www.example2.com/
  - title: site-name3
    url: http://www.example3.com/

timeline:
  - num: 1
    word: 2014/06/12-Start
  - num: 2
    word: 2014/11/29-XXX
  - num: 3
    word: 2015/02/18-DDD
  - num: 4
    word: More

# Static files
js: js
css: css

# Theme version
version: 1.0.0
~~~

- disqus - [Disqus](https://disqus.com/)评论系统，支持[DisqusJS](https://github.com/SukkaW/DisqusJS) API
- uyan - [友言](http://www.uyan.cc/)评论系统
- livere - [来必力](https://livere.com/)评论系统
- changyan - [畅言](http://changyan.kuaizhan.com/)评论系统
- gitment - [Gitment](https://github.com/imsun/gitment)评论系统相关参数
- gitalk - [Gitalk](https://github.com/gitalk/gitalk)评论系统相关参数
- valine - [Valine](https://valine.js.org/)评论系统相关参数
- minivaline - [MiniValine](https://github.com/MiniValine/MiniValine)评论系统相关参数
- waline - [Waline](https://waline.js.org/)评论系统相关参数
- utterances - [Utterances](https://utteranc.es/)评论系统相关参数
- google_search - 默认使用Google搜索引擎
- baidu_search - 若想使用百度搜索，将其设定为`true`。
- swiftype - [Swiftype](https://swiftype.com/) 站内搜索key
- self_search - 基于jQuery的[本地搜索引擎](https://www.hahack.com/codes/local-search-engine-for-hexo/)，需要安装[hexo-generator-search](https://github.com/wzpan/hexo-generator-search)插件使用。
- google_analytics - [Google Analytics](https://www.google.com/analytics/) 跟踪ID
- baidu_analytics - [百度统计](http://tongji.baidu.com/) 跟踪ID
- fancybox - 是否启用[Fancybox](http://fancyapps.com/fancybox/)图片灯箱效果
- show_category_count - 是否显示侧边栏分类数目
- toc_number - 是否显示文章中目录列表自动编号
- shareto - 是否使用分享按鈕，需要安装[hexo-helper-qrcode](https://github.com/yscoder/hexo-helper-qrcode)插件使用
- busuanzi - 是否使用[不蒜子](http://busuanzi.ibruce.info/)页面访问计数
- wordcount - 是否使用[hexo-wordcount](https://github.com/willin/hexo-wordcount)文章字数统计
- widgets_on_small_screens - 是否在移动设备屏幕底部显示侧边栏
- canvas_nest - 是否使用[canvas_nest.js](https://github.com/hustcc/canvas-nest.js/blob/master/README-zh.md)动态背景
- donate - 是否启用捐赠按钮
- post_copyright - 是否在每篇文章后显示版权信息
- love - 是否在任意点击处出现桃心
- plantuml - 是否使用PlantUML生成UML图表
- copycode - 是否为代码块启用一键复制功能
- dark - 是否使用夜间模式切换功能
- totop - 是否使用返回顶部小火箭图标
- external_css - 是否加载外部CSS文件
- menu - 自定义页面及菜单，依照已有格式填写。填写后请在`source`目录下建立相应名称的文件夹，并包含`index.md`文件，以正确显示页面。导航菜单中集成了[FontAwesome](http://fontawesome.io/)图标字体，可以在[这里](http://fontawesome.io/icons/)选择新的图标，并按照相关说明使用。
- widgets - 选择和排列希望使用的侧边栏小工具。
- links - 友情链接，请依照格式填写。
- timeline - 网站历史时间线，在页面`front-matter`中设置`layout: timeline`可显示。
- Static files - 静态文件存储路径，方便设置CDN缓存。
- Theme version - 主题版本，便于静态文件更新后刷新CDN缓存。

### 主题特性

#### 网站图标

若要设置网站Favicon，可以将**favicon.ico**放在Hexo根目录的`source`文件夹下，建议的大小：32px*32px。

若要为网站添加苹果设备图标，请将命名为**apple-touch-icon.png**的图片放在同样的位置，建议的大小：114px*114px。

#### 文章摘要

首页默认显示文章摘要而非全文，可以在文章的`front-matter`中填写一项`description:`来设置你想显示的摘要，或者直接在文章内容中插入`<!--more-->`以隐藏后面的内容，若两者都未设置，则自动截取文章第一段作为摘要。

#### 添加页面

在`source`目录下建立相应名称的文件夹，然后在文件夹中建立`index.md`文件，并在`index.md`的`front-matter`中设置layout为`layout: page`。现已支持添加标签页面，将页面的layout设置为`layout: tagcloud`即可。若需要单栏页面，就将layout设置为 `layout: single-column`。

#### 文章目录

在文章的`front-matter`中添加`toc: true`即可让该篇文章显示目录。

#### 文章评论

文章和页面的评论功能可以通过在`front-matter`中设置`comments: true`或`comments: false`来进行开启或关闭（默认开启）。

#### 语法高亮

要启用代码高亮，请在Hexo目录的`_config.yml`中将`highlight`选项按照如下设置：

```
highlight:  
 enable: true  
 auto_detect: true  
 line_number: true  
 tab_replace:  
```

#### 数学公式

要启用数学公式支持，请在Hexo目录的`_config.yml`中添加：

```
mathjax: true  
```

并在相应文章的`front-matter`中添加`mathjax: true`，例如：

```
title: Test Math  
date: 2016-04-05 14:16:00  
categories: math  
mathjax: true  
---
```

数学公式的默认定界符是`$$...$$`和`\\[...\\]`（对于块级公式），以及`$...$`和`\\(...\\)`（对于行内公式）。

但是，如果你的文章内容中经常出现美元符号“`$`”, 或者说你想将“`$`”用作美元符号而非行内公式的定界符，请在Hexo目录的`_config.yml`中添加：

```yml
mathjax2: true  
```

而不是`mathjax: true`。 相应地，在需要使用数学公式的文章的`front-matter`中也添加`mathjax2: true`。

#### 支持语言

目前支持简体中文（zh-CN），繁体中文（zh-TW），英语（en），法语（fr-FR），德语（de-DE），韩语（ko）和西班牙语（es-ES），欢迎翻译至其它语言。
