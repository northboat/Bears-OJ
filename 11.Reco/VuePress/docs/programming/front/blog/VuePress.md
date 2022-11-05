# 用VuePress搭建个人博客

## 1、vuepress初始化

直接用npm全局安装vuepress（需要node环境和npm支持）

~~~bash
npm install -g vuepress
~~~

创建一个文件夹作为整本书的项目目录并初始化项目

~~~bash
mkdir blog_vuepress
cd blog_vuepress
npm init -y
~~~

在项目目录创建docs目录存放博客书籍内容

~~~bash
mkdir docs
~~~

在docs目录下创建README.md 并手写首页内容

~~~markdown
---
home: true

//主页logo
heroImage: /logo.jpg

//按钮连接
actionText: 快速上手 →
actionLink: /zh/guide/

//简介
features:
- title: 简洁至上
  details: 以 Markdown 为中心的项目结构，以最少的配置帮助你专注于写作。
- title: Vue驱动
  details: 享受 Vue + webpack 的开发体验，在 Markdown 中使用 Vue 组件，同时可以使用 Vue 来开发自定义主题。
- title: 高性能
  details: VuePress 为每个页面预渲染生成静态的 HTML，同时在页面被加载的时候，将作为 SPA 运行。

//脚标
footer: Created by NorthBoat
---
~~~

## 2、核心配置

在docs目录下创建 .vuepress 目录用于存放配置

~~~bash
cd docs
mkdir .vuepress
~~~

新建中配置文件 config.js

~~~bash
cd .vuepress
touch config.js
~~~

编写 config.js

~~~js
module.exports = {
    //主页标题
    title: '知码学院',
    //标题描述
    description: '君哥带你上王者',
    dest: './dist',
    //本地开放端口
    port: '7777',
    head: [
        ['link', {rel: 'icon', href: '/logo.jpg'}]
    ],
    markdown: {
        lineNumbers: true
    },
    themeConfig: {
        //导航栏
        nav: [{
            text: '懵逼指南', link: '/guide/'
        }],
        //侧边栏
        sidebar: {'/guide/':[
            {
                  title:'新手指南',
                  collapsable: true,
                  children:[
                    '/guide/notes/one',
                  ]
                },
                {
                  title:'知码学院',
                  collapsable: true,
                  children:[
                    '/guide/notes/two',
                  ]
                }
            ]
        },
        sidebarDepth: 2,
        lastUpdated: 'Last Updated',
        searchMaxSuggestoins: 10,
        serviceWorker: {
            updatePopup: {
                message: "有新的内容.",
                buttonText: '更新'
            }
        },
        editLinks: true,
        editLinkText: '在 GitHub 上编辑此页 ！'
    }
}

~~~

运行blog

~~~bash
vuepress dev docs
~~~

## 3、导航栏配置（nav）

在 config.js 的同级目录下创建nav.js，将config.js中nav项修改为

~~~js
nav: require("./nav.js"),
~~~

即添加导航栏依赖，再配置nav.js文件

~~~js
//主管控导航栏

module.exports = [
	{
        //栏标题
		text: '啊哈', link: '/guide/'
	},
    {
        text: 'blog',
        //栏下的副标题
        //此时主标题无法链接(link)
		items: [
			{text: 'Hexo', link: '/blog/hexo/'},
			{text: 'VuePress', link: '/blog/vuepress/'}
		]
    },
    {
        text: 'program',
		items: [
            {text: 'C++', link: '/programe/c++/'},
            {text: 'Java', link: '/programe/java/'},
			{text: 'Python', link: '/programe/python/'}
        ]
    },
	{
		text: 'docker',
		items: [
			{text: 'docker部署', link: '/docker/deployment/'},
			{text: 'docker使用', link: '/docker/usage/'},
			{text: 'docker-java', link: '/docker/docker-java/'}
		]
	},
	{
		text: 'LeetCode',
		items: [
			{text: '力扣题单', link: '/leetcode/leetcode题单.md'},
			{text: '刷题笔记', link: '/leetcode/leetcode刷题笔记.md'},
			{text: '动态规划', link: '/leetcode/动态规划.md'}
		]
	}
]

~~~

## 4、侧边栏配置（sidebar）

在.vuepress下创建sidebar.js，修改config.js中sidebar依赖

~~~js
sidebar: require("./sidebar.js"),
~~~

配置.vuepress下的sidebar.js文件，在这里实现对各个侧边栏的主管控，即依赖到各个目录下的sidebar.js文件

~~~js
//对侧边栏的主管控
//每句最后都要加逗号
module.exports = {
	'/guide/': require('../guide/sidebar'),
	'/blog/hexo/': require('../blog/hexo/sidebar'),
	'/blog/vuepress/': require('../blog/vuepress/sidebar')，
}	
~~~

如 ../blog/vuepress/sidebar.js（../ 表示上一级目录，当不写目录开头时，默认为在 .vuepress 的同级目录下）

~~~js
//blog.vuepress的侧边栏
module.exports = [
	{
		title: 'blog_vuepress',
		collapsable: false,
		children: [
			'/blog/hexo/notes/vuepress',
		]
	}
]
~~~

## 5、静态资源

静态资源（图片、js等）默认读取位置为.vuepress/public/ 文件夹下，在docs的README中改写配置

~~~shell
---
home: true
//主页图片
heroImage: /imgae/logo.jpg
actionText: 快速上手 →
actionLink: /zh/guide/
features:
- title: 简洁至上
  details: 以 Markdown 为中心的项目结构，以最少的配置帮助你专注于写作。
- title: Vue驱动
  details: 享受 Vue + webpack 的开发体验，在 Markdown 中使用 Vue 组件，同时可以使用 Vue 来开发自定义主题。
- title: 高性能
  details: VuePress 为每个页面预渲染生成静态的 HTML，同时在页面被加载的时候，将作为 SPA 运行。
footer: Created by NorthBoat 2021/5/3
---
~~~

## 6、远端部署

1、配置核心配置文件config.js

~~~
//生成静态网页文件夹（./为docs所在目录下）
dest: './dist',
//base仓库名为github上仓库名称（注意斜杠）
base: '/Blog',
~~~

2、在docs所在目录下配置.gitignore文件

~~~
node_modules/
docs/.vuepress/theme
package-lock.json
~~~

3、在package.json中添加脚本

~~~json
{
  "scripts": {
    "docs:dev": "vuepress dev docs",
    "docs:build": "vuepress build docs"
  }
}
~~~

4、手写部署脚本deploy.sh

~~~sh
#!/usr/bin/env sh

# 确保脚本抛出遇到的错误
set -e

# 生成静态文件
npm run docs:build

# 进入生成的文件夹（该文件夹名称路径配置在config.js中）
cd dist

# 如果是发布到自定义域名
# echo 'www.example.com' > CNAME

git init
git add -A
git commit -m 'deploy'

# 如果发布到 https://<USERNAME>.github.io
# git push -f git@github.com:<USERNAME>/<USERNAME>.github.io.git master

# 如果发布到 https://<USERNAME>.github.io/<REPO>
git push -f git@github.com:NorthBoat/Blog.git master

cd ../
#删除目标文件夹
rm -rf dist
#删除临时缓存文件
rm -rf node_modules
~~~

运行脚本即可

## 7、踩坑指南

1、记得部署config文件中的base

2、上传 .md 文件，要保证文本中（不包括代码块）没有html标签，不然将导致网页body无法显示