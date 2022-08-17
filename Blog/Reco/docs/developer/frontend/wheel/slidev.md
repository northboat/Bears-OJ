---
title: Slidev
date: 2022-2-14
tags:
  - Wheel
  - FrontEnd
---

> Slidev 是可以让我们用 Markdown 写 PPT 的一个工具库，基于 Node.js、Vue.js
>
> 利用它我们可以简单地把 Markdown 转化成 PPT，而且它可以支持各种好看的主题、代码高亮、公式、流程图、自定义的网页交互组件，还可以方便地导出成 pdf 或者直接部署成一个网页使用

官方主页：https://sli.dev/

GitHub：https://github.com/slidevjs/slidev

## 安装启动

首先要求`nodejs、npm`环境

初始化仓库

~~~bash
npm init slidev
~~~

设置仓库名称、作者等

该过程会自动安装依赖包，并在当前目录创建一个slidev的目录

slidev将渲染slides.md为一个PPT并展示在本地端口3000

由于基于vue开发，像常用的vue命令都是通用的

~~~bash
npm run dev
npm run build
~~~

## 编写

### 快捷键操作

[操作文档](https://sli.dev/guide/navigation.html)

- f：切换全屏
- right / space：下一动画或幻灯片
- left：上一动画或幻灯片
- up：上一张幻灯片
- down：下一张幻灯片
- o：切换幻灯片总览
- d：切换暗黑模式
- g：显示“前往...”

### 关键字设置

每一页以前可以通过内置的一些关键字，设置这一页的样式

比如第一页设置了theme为default，background是unsplash提供的随机的一张图，文本居中等等

[更多主题](https://sli.dev/themes/gallery.html)

可以直接在markdown中插入web组件以达到更高的自定义程度：

~~~markdown
---
# try also 'default' to start simple
theme: default
# random image from a curated Unsplash collection by Anthony
# like them? see https://unsplash.com/collections/94734566/slidev
background: https://source.unsplash.com/collection/94734566/1920x1080
# apply any windi css classes to the current slide
class: 'text-center'
# https://sli.dev/custom/highlighters.html
highlighter: shiki

---

# Welcome to Slidev

Presentation slides for developers

<div class="pt-12">
  <span @click="$slidev.nav.next" class="px-2 p-1 rounded cursor-pointer" hover="bg-white bg-opacity-10">
    Press Space for next page <carbon:arrow-right class="inline"/>
  </span>
</div>

---
~~~

### 自定义CSS

可以用css自定义当前页的一些内容的样式

~~~markdown
---

# What is Slidev?

Slidev is a slides maker and presenter designed for developers, consist of the following features


<style>
h1 {
  background-color: #2B90B6;
  background-image: linear-gradient(45deg, #4EC5D4 10%, #146b8c 20%);
  background-size: 100%;
  -webkit-background-clip: text;
  -moz-background-clip: text;
  -webkit-text-fill-color: transparent;
  -moz-text-fill-color: transparent;
}
</style>

---
~~~

### 代码和图像

插入代码和图像：代码就是markdown语法，图像使用arrow标签插入

~~~markdown
---
layout: image-right
image: https://source.unsplash.com/collection/94734566/1920x1080
---

# Code

Use code snippets and get the highlighting directly!

```ts {all|2|1-6|9|all}
interface User {
  id: number
  firstName: string
  lastName: string
  role: string
}

function updateUser(id: number, update: User) {
  const user = getUser(id)
  const newUser = {...user, ...update}
  saveUser(id, newUser)
}
```

<arrow v-click="3" x1="400" y1="420" x2="230" y2="330" color="#564" width="3" arrowSize="1" />

---
~~~

### Latex公式和图表

可以插入Latex公式，行内公式用一对$包起来，一整块公式块用一对$$包起来：

~~~markdown
---
# LaTeX

LaTeX is supported out-of-box powered by [KaTeX](https://katex.org/).

<br>

Inline $\sqrt{3x-1}+(1+x)^2$

Block
$$
\begin{array}{c}

\nabla \times \vec{\mathbf{B}} -\, \frac1c\, \frac{\partial\vec{\mathbf{E}}}{\partial t} &
= \frac{4\pi}{c}\vec{\mathbf{j}}    \nabla \cdot \vec{\mathbf{E}} & = 4 \pi \rho \\

\nabla \times \vec{\mathbf{E}}\, +\, \frac1c\, \frac{\partial\vec{\mathbf{B}}}{\partial t} & = \vec{\mathbf{0}} \\

\nabla \cdot \vec{\mathbf{B}} & = 0

\end{array}
$$

<br>

[Learn more](https://sli.dev/guide/syntax#latex)

---
~~~

可以用mermaid插入流程图

~~~markdown
---

# Diagrams

You can create diagrams / graphs from textual descriptions, directly in your Markdown.

<div class="grid grid-cols-2 gap-4 pt-4 -mb-6">

```mermaid {scale: 0.9}
sequenceDiagram
    Alice->John: Hello John, how are you?
    Note over Alice,John: A typical interaction
```

```mermaid {theme: 'neutral', scale: 0.8}
graph TD
B[Text] --> C{Decision}
C -->|One| D[Result 1]
C -->|Two| E[Result 2]
```

</div>

[Learn More](https://sli.dev/guide/syntax.html#diagrams)


---
~~~

### 引入组件

甚至可以通过vue自定义组件：这里引入了 Counter、Tweet 组件

这就是一个标准的基于 Vue.js 3.x 的组件，都是标准的 Vue.js 语法，所以如果我们要添加想要的组件，直接自己写就行了

~~~markdown
# Components
 
<div grid="~ cols-2 gap-4">
<div>
 
You can use Vue components directly inside your slides.
 
We have provided a few built-in components like `<Tweet/>` and `<Youtube/>` that you can use directly. And adding your custom components is also super easy.
 
```html
<Counter :count="10" />
```
 
<!-- ./components/Counter.vue -->
<Counter :count="10" m="t-4" />
 
Check out [the guides](https://sli.dev/builtin/components.html) for more.
 
</div>
<div>
 
```html
<Tweet id="1390115482657726468" />
```
 
<Tweet id="1390115482657726468" scale="0.65" />
 
</div>
</div>
~~~

### 页面分割

用三条横线分割每个PPT页面

~~~markdown
---
layout: cover
---
 
# 第 1 页
 
This is the cover page.
 
---
 
# 第 2 页
 
The second page
~~~

除了使用三横线，我们还可以使用更丰富的定义模式，可以给每一页制定一些具体信息，就是使用两层三横线

~~~markdown
---
theme: seriph
layout: cover
background: 'https://source.unsplash.com/1600x900/?nature,water'
---
~~~

### 备注

备注同HTML

~~~markdown
---
layout: cover
---
 
# 第 1 页
 
This is the cover page.
 
<!-- 这是一条备注 -->
~~~

### 更多

[录制功能](https://sli.dev/guide/recording.html)

演讲者头像：使用网页通过摄像头捕获，在右下角显示

## 部署

### PDF导出

一般我们演示slides都是在其他陌生环境，没法用npm之类的来演示，所以就需要导出成pdf之类的：

需要先下载依赖，slidev的导出依赖于此

~~~bash
npm i -D playwright-chromium
~~~

导出，将在当前目录生成一个slides_export.pdf

~~~bash
npx slidev export
~~~

### 网页部署

构建

~~~bash
npm run build
~~~

作为一个正常的前端vue项目通过nginx部署即可
