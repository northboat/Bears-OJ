---
title: CSS 基础
date: 2021-10-21
tags:
  - FrontEnd
---

## 概述

cascading style sheet 层叠级联样式表

CSS:表现层 ——> 美化网页

字体、颜色、边距、高度、宽度、背景图片......

浏览器开发人员工具，捕获元素，elements、style

右键查看页面源代码

> 发展史

CSS1.0

CSS2.0：DIV+CSS，HTML与CSS结构分离的思想，SEO

CSS2.1：浮动、定位

CSS3.0：圆角，阴影，动画......浏览器兼容性



## 快速入门

### 基础语法

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>index</title>

    <!-- 规范，<style>在head中
        
        语法：
            选择器{
                声明1: ;
                声明2: ;
            }
    -->
    <style>
        h1{
            color: blue;
        }
    </style>

</head>

<body style="text-align:center" marginheiht="300px">
    <h1>Hello World!</h1>
</body>

</html>
~~~

分文件link保持html的纯净

~~~css
h1{
    color: blue;
}
~~~

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>index</title>

    <link rel="stylesheet" href="css/style1.css">

</head>

<body style="text-align:center" marginheiht="300px">
    <h1>Hello World!</h1>
</body>

</html>
~~~

优势：

1. 内容和表现分离
2. 网页结构表现同意，可以实现复用
3. 样式十分丰富
4. 建议使用独立于html的css文件
5. 利用SEO，容易被搜索引擎收录

Vue极其不容易被搜索引擎收录

### 三种导入方式

行内样式：在标签样式中直接使用style属性，不推荐使用

~~~html
<h1 style="color: red;">行内样式</h1>
~~~

内部样式标：style标签

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>导入方式</title>

    <style>
        h1{
            color: blue; /*内部样式*/
        }
    </style>
</head>
<body>
    <h1 style="color: red;">行内样式</h1>
    <h1>内部样式</h1>
</body>
</html>
~~~

外部样式标：link

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>导入方式</title>
    
    <link rel="stylesheet" href="css/style1.css">
</head>
<body>
    <h1 style="color: red;">行内样式</h1>
    <h1>内部样式</h1>
</body>
</html>
~~~

优先级：就近原则，谁靠的近用谁

拓展：外部样式两种写法

- 链接式：link，是一个html标签

- 导入式：@import，CSS2.1特有，css语法，必须放在style标签中

  ~~~
  <!DOCTYPE html>
  <html lang="en">
  <head>
      <meta charset="UTF-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Document</title>
  
      <style>
          @import url(css/style1.css);
      </style>
  </head>
  <body>
  
      <h1>hahaha</h1>
      
  </body>
  </html>
  ~~~

导入式会导致先展现结构再进行渲染 ——> 不好看

## CSS选择器

> 作用：选择页面上的某一个或某一类元素

### 基本选择器

1、标签选择器：对所有同类标签同时作用

~~~html
    <style>
        h1{
            color:white;
            background-color:cadetblue;
            border-radius: 12px;
        }

        p{
            font-size:40px;
        }
    </style>
~~~

2、类选择器：设置class属性，在style中用.class的形式定向选择该类标签，可以为标签归类，跨标签自定义分类，可复用

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        /* 类选择器的格式 .class的名称 */
        .hahaha{
            color:chartreuse;
        }

        .eiheihei{
            color:gray;
        }

        .oh{
            color:hotpink;
        }
    </style>
</head>
<body>
    
<h1 class="hahaha">标题1</h1>
<h1 class="eiheihei">标题2</h1>
<h1 class="oh">标题3</h1>

<P class="oh">P标签</P>

</body>
</html>
~~~

3、id选择器：定义标签属性id，在style中用#连接id定位指定标签，全局唯一

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        #hahaha{
            color:pink;
        }
    </style>
</head>
    
<body>
<h1 id="hahaha">标题1</h1> 
</body>
</html>
~~~

优先级：id选择器>类选择器>标签选择器，不遵循就近原则

### 层次选择器

1、后代选择器：作用于body标签后的所有p标签

~~~html
<style>
        body p{
            background-color: indigo;
        }
</style>
~~~

2、子选择器：作用于body后的第一代p标签，第一代指同一层（树形结构）的标签

~~~html
<style>
        body>p{
            background-color: lightcoral;
        }
    
    	body>ul>li{
            background-color: lightcoral;
        }
</style>
~~~

如前三个p和ul位于同一层，ul中的三个li位于同一层

~~~html
<body>

    <p>p1</p>
    <p>p2</p>
    <p>p3</p>

    <ul>
        <li>
            <p>p4</p>
        </li>

        <li>
            <p>p5</p>
        </li>

        <li>
            <p>p6</p>
        </li>
    </ul>
</body>
~~~

3、相邻兄弟选择器：.class/#id + a作用于同层的相邻下一个指定标签a，如p1作用于p2，p2作用于p3，对下不对上

~~~html
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        
        .hahaha + p{
            background-color: lightcoral;
        }

        #oh + p{
            background-color: lightskyblue;
        } 


    </style>

</head>
<body>

    <p>p0</p>
    <p class="hahaha">p1</p>
    <p id="oh">p2</p>
    <p>p3</p>

</body>
~~~

4、通用兄弟选择器：.class~a，作用于当前选中元素同级的向下的所有a标签，如下.hahaha~p作用于p2\3\7\8

~~~html
.hahaha~p{
            background-color: green;
}

<p>p0</p>
    <p class="hahaha">p1</p>
    <p id="oh">p2</p>
    <p>p3</p>

    <ul>
        <li>
            <p>p4</p>
        </li>

        <li>
            <p>p5</p>
        </li>

        <li>
            <p>p6</p>
        </li>
    </ul>

<p>p7</p>
<p>p8</p>
~~~

- 通用兄弟选择器与相邻兄弟选择器优先级遵循就近原则

### 结构伪类选择器

伪类：条件判断，通过条件判断形成一个类，所谓伪类

特征：冒号

~~~html
<style>
        /* 选择ul子类的第一个元素 */
        ul li:first-child{
            background-color: blue;
        }

        /* 选择ul子类的最后一个元素 */
        ul li:last-child{
            background-color: red;
        }

        /* p标签的父标签的第n个子类，若第n个为p标签，则生效 */
        p:nth-child(2){
            background-color: rosybrown;
        }

        /* 选中p的父元素，并在父元素的子类中找到第二个类型为p的元素作用于该p标签上 */
        p:nth-of-type(2){
            background:yellow;
        }

        /* 选中所有a标签，设置其被选中时的状态 */
        a:hover{
            background-color: hotpink;
        }
</style>
~~~

### 属性选择器

作用：选择具有特定属性的标签

语法：标签 [ 属性值满足的条件 ] { 操作 }

- =      绝对等于
- *=    包含  
- ^=    以开头
- $=    以结尾

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        /* 后代选择器 */
        .demo a{
            /* 浮动 */
            float: left;
            /* 块状 */
            display: block;
            /* 块儿的高、宽 */
            height: 50px;
            width: 50px;
            /* 背景颜色 */
            background-color: slateblue;
            /* 圆角边框 */
            border-radius: 12px;
            /* 字体居中 */
            text-align: center;
            /* 字体颜色 */
            color:slategray;
            /* 字体下划线 */
            text-decoration: none;
            /* 设置文字位置 */
            margin-right: 5px;
            font: bold 20px/50px Arial;
        }


        /* 
            a[id] ——> 存在id
            正则:
            =  绝对等于
            *= 包含
            ^= 以开头
            $= 以结尾
        */
        
        /* 选中有id的a标签 */
        a[id]{
            background-color: pink;
        }
        /* 选中class为links item的a元素，绝对等于 */
        a[class="links item"]{
            background-color: rebeccapurple;
        }

        /* 选择class中含有last的a标签 */
        a[class*="last"]{
            background-color: red;
        }

        /* 选中href中以http开头的a元素 */
        a[href^=http]{
            background-color: yellow;
        }

        a[href$=pdf]{
            background-color: green;
        }

    </style>
</head>
<body>
    
<p class="demo">
    <a href="http://www.baidu.com" class="links item first" id="first">1</a>
    <a href="" class="links item active" target="_blank" title="test">2</a>
    <a href="http://www.bilibli.com" class="links item">3</a>
    <a href="images.png" class="links item">4</a>
    <a href="hahaha.pdf" class="links item">5</a>
    <a href="eiheihei.jpg" class="links item">6</a>
    <a href="ohh.doc" class="links item">7</a>
    <a href="abc.md" class="links item last">8</a>
</p>

</body>
</html>
~~~

## 美化网页元素

> 为什么要美化？
>
> - 有效传递信息
> - 美化网页
> - 凸显页面主题
> - 提高用户体验

### span标签

重点要突出的字段用span标签套起来（约定俗成），便于操作

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        #hahaha{
            font-size: 50px;
        }
    </style>
</head>
<body>
    
    欢迎学习<span id="hahaha">CSS</span>，我们将共同进步
</body>
</html>
~~~

### 字体样式

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        body{
            font-family: Arial, 楷体;
            color: black;
        }
        h1{
            font-size: 50px;
        }

        /* 字体粗细 */
        .p1{
            font-weight: lighter;
        }
    </style>
</head>
<body>
    
    <h1>四月是你的谎言</h1>

    <p class="p1">
        《四月是你的谎言》是由日本漫画家新川直司作画的少年漫画，
        曾获2012年度“漫画大奖”提名、2013年讲谈社漫画赏少年部门奖。
        作品亦被改编TV动画与电影等作品。
    </p>

    <p>
        作品于2011年5月号在讲谈社发行的杂志《月刊少年Magazine》
        上开始连载，至2015年3月号完结，单行本共发行11卷。
        截止2016年2月，作品销量突破400万册。在完结一年左右后较
        外传性质的Coda短篇集于2016年8月17日发售，讲述角色们过去
        和以后的故事，全5话。同时也被收录在动画《四月是你的谎言》
        Blu-ray和DVD中。漫画由日本的讲谈社出版，繁体中文版由中国
        台湾的东立出版社代理发行，简体中文版由讲谈社（北京）代理、
        翻译并授权网站哔哩哔哩漫画、腾讯动漫、快看漫画、漫番漫画刊登。
    </p>

    <p>
        故事讲述了拥有少见才能的中学男生（钢琴家）和女生（小提琴家）
        共同努力、成长的青春物语。
    </p>
    

    <p>
        The most luxurious thing in my life is to meet you on 
        the way, and then help each other, smell the fragrance 
        of flowers together. In my lifetime, I will only tell
        you the warmth, but not the sorrow. I will meet you and 
        accompany you in peace and warmth.
    </p>
</body>
</html>
~~~

也可在一行内设置font属性，各属性间用空格隔开

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        p{
            font: oblique bolder 20px "楷体";
        }
    </style>
</head>
<body>
    <p>
        作品于2011年5月号在讲谈社发行的杂志《月刊少年Magazine》 上开始连载，至2015年3月号完结，单行本共发行11卷。 截止2016年2月，作品销量突破400万册。在完结一年左右后较 外传性质的Coda短篇集于2016年8月17日发售，讲述角色们过去 和以后的故事，全5话。同时也被收录在动画《四月是你的谎言》 Blu-ray和DVD中。漫画由日本的讲谈社出版，繁体中文版由中国 台湾的东立出版社代理发行，简体中文版由讲谈社（北京）代理、 翻译并授权网站哔哩哔哩漫画、腾讯动漫、快看漫画、漫番漫画刊登。
    </p>
</body>
</html>
~~~

### 文本样式

颜色

- 单词
- RGB         	 三原色
- RGBA        	透明度

文本对齐t

- ext-align   	排版，居中
- text-indent 	段落首行缩进
- height      	  段落高度
- line-height 	文本高度

划线：text-decoration

- 上划线      	overline
- 中划线      	line-though
- 下划线      	underline
- 无装饰	   	none

水平对齐vertical-align: middle

- 要选择参照物，如 img, span{ vertical-align: middle; }

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <!-- 
        颜色：
            单词
            RGB         三原色
            RGBA        透明度
        文本对齐
            text-align  排版，居中
            text-indent 段落首行缩进
            height      段落高度
            line-height 文本高度
        划线：text-decoration
            上划线      overline
            中划线      line-though
            下划线      underline
		   无装饰	    none
     -->
    <style>
        h1{
            color: rgba(0, 255, 255, 0.9);
            text-align: center;
        }
        
        /* 首行缩进 */
        .p1{
            text-indent: 2em;
        }

        .p3{
            background-color: palegoldenrod;
            /* 设置段落的高度，若不设置自动为文本的高度 */
            height: 300px;
            /* 若设置文字和段落的高度一样，文字将自动上下居中 */
            line-height: 300px;
        }


        /* 划线 */
        /* 下划线 */
        .l1{
            text-decoration: underline;
        }
        /* 中划线 */
        .l2{
            text-decoration: line-through;
        }
        /* 上划线 */
        .l3{
            text-decoration: overline;
        }
        /* 超链接去下划线 */
        a{
            text-decoration: none;
        }
    </style>
</head>
<body>

    <p class="l1">eiheihei </p>
    <p class="l2">hahaha</p>
    <p class="l3">ohuohuo</p>

    <a href="http://39.106.160.174:8081/NEUQHelper">HAHAHA</a>

    <h1>四月是你的谎言</h1>

    <p class="p1">
        《四月是你的谎言》是由日本漫画家新川直司作画的少年漫画，
        曾获2012年度“漫画大奖”提名、2013年讲谈社漫画赏少年部门奖。
        作品亦被改编TV动画与电影等作品。
    </p>

    <p class="p2">
        作品于2011年5月号在讲谈社发行的杂志《月刊少年Magazine》
        上开始连载，至2015年3月号完结，单行本共发行11卷。
        截止2016年2月，作品销量突破400万册。在完结一年左右后较
        外传性质的Coda短篇集于2016年8月17日发售，讲述角色们过去
        和以后的故事，全5话。同时也被收录在动画《四月是你的谎言》
        Blu-ray和DVD中。漫画由日本的讲谈社出版，繁体中文版由中国
        台湾的东立出版社代理发行，简体中文版由讲谈社（北京）代理、
        翻译并授权网站哔哩哔哩漫画、腾讯动漫、快看漫画、漫番漫画刊登。
    </p>

    <p class="p3">
        故事讲述了拥有少见才能的中学男生（钢琴家）和女生（小提琴家）
        共同努力、成长的青春物语。
    </p>
</body>
</html>
~~~

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        /* 将span放在img的中间，垂直方向的中间 */
        img,span{
            vertical-align: middle;
        }
    </style>
</head>
<body>
    <p>
        <img src="img/slp.jpg" alt="">
        <span>沙梨胖</span>
    </p>
    
</body>
</html>
~~~

### 超链接

去下划线

鼠标悬停状态

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <style>
        a{
            text-decoration: none;
            color: black;
        }

        /* hover：鼠标悬浮时的状态 */
        a:hover{
            color: blue;
            font-size: 24px;
        }
        /* 已访问的颜色 */
        a:visited{
            color: gray;
        }
        /* 鼠标已点击但未松开时的状态 */
        a:active{
            color: green;
        }

        /* 阴影：颜色 水平偏移距离 垂直偏移距离 阴影半径 */
        #price{
            text-shadow: hotpink 10px 10px 10px;
        }
    </style>
</head>
<body>
    <a href="#">
        <img src="img/slp.jpg" alt="">
    </a>

    <p>
        <a href="#">名字：沙梨胖</a>
    </p>

    <p>
        <a href="">作者：舟桐</a>
    </p>

    <p id="price">
        ￥99
    </p>
</body>
</html>
~~~

### 列表

去小圆点

统一背景颜色假装去掉空格

~~~html
<body>
    <div id="nav"> 
        <h2 class="title">全部商品分类</h2>    
    
        <ul>
            <li><a href="#">图书</a>&nbsp;&nbsp;<a href="#">音像</a>&nbsp;&nbsp;<a href="#">数字商品</a></li>
            <li><a href="#">家用电器</a>&nbsp;&nbsp;<a href="#">手机</a>&nbsp;&nbsp;<a href="#">数码</a></li>
            <li><a href="#">电脑</a>&nbsp;&nbsp;<a href="#">办公</a></li>
            <li><a href="#">家具</a>&nbsp;&nbsp;<a href="#">家装</a>&nbsp;&nbsp;<a href="#">数字商品</a></li>
            <li><a href="#">服饰鞋帽</a>&nbsp;&nbsp;<a href="#">个护化妆</a>&nbsp;&nbsp;<a href="#">厨具</a></li>
            <li><a href="#">礼品箱包</a>&nbsp;&nbsp;<a href="#">钟表</a>
            <li><a href="#">食品饮料</a>&nbsp;&nbsp;<a href="#">保健食品</a>&nbsp;&nbsp;<a href="#">珠宝</a></li>
            <li><a href="#">彩票</a>&nbsp;&nbsp;<a href="#">旅行</a>&nbsp;&nbsp;<a href="#">票务</a></li>
            <li><a href="#">图书</a>&nbsp;&nbsp;<a href="#">音像</a>&nbsp;&nbsp;<a href="#">充值</a></li>
            </li>>
        </ul>
        
    </div>

    
</body>
</html>
~~~

~~~css
#nav{
    background-color: gray;
    width: 300px;
}

.title{
    color: lightblue;
    background-color: black;
    font-size: 24px;
    font-weight: bold;
    text-indent: 3em;
    line-height: 40px;
} 

/* ul-li
    none    去掉原点
    circle  空心圆
    decimal 数字
    square  正方形
*/
ul{
    background: gray;
}

li{
    height: 35px;
    list-style: none;
    text-indent: 1em;
}

a{
    text-decoration: none;
    font-size: 19px;
    color: black;
}

a:hover{
    color: orange;
    text-decoration: underline;
}

~~~

### 背景

背景颜色

背景图片：大小，平铺、位置

~~~css
#nav{
    background-color: gray;
    width: 300px;
}

.title{
    color: lightblue;
    font-size: 24px;
    font-weight: bold;
    text-indent: 2em;
    line-height: 40px;
    background: black url("../img/home.png") no-repeat 248px 1px;
    background-size: 52px;
} 

/* ul-li
    none    去掉原点
    circle  空心圆
    decimal 数字
    square  正方形
*/
ul{
    background: gray;
}

li{
    height: 35px;
    list-style: none;
    text-indent: 1em;
    background-image: url("../img/r.png");
    background-repeat: no-repeat;
    background-size: 15px;
    background-position: 240px 7px;  
}

a{
    text-decoration: none;
    font-size: 19px;
    color: black;
}

a:hover{
    color: orange;
    text-decoration: underline;
}
~~~

### 渐变

[Grabient](https://www.grabient.com/)

在网页上选择并copy贴在自己的css中

~~~css
background-color: #4158D0;
background-image: linear-gradient(43deg, #4158D0 0%, #C850C0 46%, #FFCC70 100%);
~~~

## 盒子

### 什么是盒子

div：盒子标签

margin：外边距

border：边框

padding：内边距

### 边框

> 大部分标签都有默认边距，需手动设置为0去边距

- 粗细
- 样式
- 颜色

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Document</title>
    <style>
        body{
            /* body总有一个默认的外边距margin */
            /* 我们习惯性进行一些初始化操作 */
            margin: 0;
            padding: 0;
            text-decoration: none;
            /* background-color: #D9AFD9;
            background-image: linear-gradient(90deg, #D9AFD9 0%, #97D9E1 100%); */
            background-color: #4158D0;
            background-image: linear-gradient(260deg, #4158D0 0%, #C850C0 46%, #FFCC70 100%);
        }

        h2{
            margin: 0;
            color: white;
            font-size: 24px;
        }

        #app{
            width: 300px;
            border: 1px solid red;

        }

        div input{
            border: 3px solid black;
        }

    </style>
</head>
<body>
   <div id="app">
       <h2>会员登陆</h2>
       <form action="#" method="post">
           <div>
                <span>账号:</span>
                <input id="hahaha" type="text">
           </div>

           <div>
                <span>密码:</span>
                <input type="text">
        </div>
       </form>
   </div> 
</body>
</html>
~~~

### 内外边距

外边距妙用：使居中

- margin: x，四边均为x
- margin: x y，上下为x，左右为y
- margin: x y z k，上右下左，顺时针顺序

~~~css
margin: 0 auto;
~~~

很多时候这样居中不起作用，使用

~~~css
text-align: center;
~~~

可使内容居中

内边距：参数顺序与margin一样

~~~css
padding: 7px;
~~~

一个元素的大小 = 内容大小 + 内边距 + 边框大小 + 外边距

### 盒子阴影

颜色	宽度	高度	透明度

~~~css
box-shadow: rebeccapurple 40px 40px 50px;
~~~

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Document</title>
    <style>
        body{
            /* body总有一个默认的外边距margin */
            /* 我们习惯性进行一些初始化操作 */
            margin: 0;
            padding: 0;
            text-decoration: none;
            /* background-color: #D9AFD9;
            background-image: linear-gradient(90deg, #D9AFD9 0%, #97D9E1 100%); */
            background-color: #4158D0;
            background: linear-gradient(260deg, #4158D0 0%, #C850C0 46%, #FFCC70 100%);
        }

        h2{
            text-align: center;
            margin: 4px;;
            padding: 3px;
            color: white;
            font-size: 24px;
        }

        #app{
            width: 300px;
            /* 对盒子使用0，auto可自动使其居中，分别代表上下外边距，前提是得有宽度width */
            /* margin:x，四边均为x      margin:x y，上下为x，左右为y    margin:x y z k，上右下左，顺时针顺序 */
            margin: 0 auto;
            /* 盒子阴影 */
            box-shadow: rebeccapurple 40px 40px 50px;
        }


        /* 选择div后的input */
        div input{
            /* 圆角边框 */
            border-radius: 10px;;
            border: 3px solid black;
            /* 内边距，作用于贴近文本的一层，在border内文本外，margin在border外 */
            padding: 7px;
            margin: 3px;
        }

        img{
            margin-top: 150px;
        }

    </style>
</head>
<body>

    <!-- 对于包含图片的盒子，用border:0 auto无用，用text-align:center可使其居中 -->
    <div style="text-align: center;">
        <img src="img/slp.jpg">
    </div>

    <div id="app"> 

        <h2>用户登陆</h2>
        <form action="#" method="post" style="text-align: center;">
            <div>
                <span>账号:</span>
                <input id="hahaha" type="text">
            </div>

            <div>
                <span>密码:</span>
                <input type="text">
            </div>

        
        </form>
    </div>               
</body>
</html>
~~~



## 浮动

### display

块级元素（内联元素）：独占一行

- h1~h6
- p
- div
- ul-li

行内元素

- span
- a
- img
- strong

行内元素可以包含在块级元素中，反之不可以

改变元素的块状属性 display: 

- inline
- block
- inline-block

~~~css
<head>
    <title>Document</title>

    <!-- 
        display:
            block: 块元素
            inline: 行内元素
            inline-block: 是块元素，但可内联在一行
        将ul-li设为inline-block，可实现横向导航栏
     -->

    <style>
        p{
            display: none;
        }


        div{
            width: 100px;
            height: 100px;
            border: 1px solid black;
            /* 转换为行内元素，不受宽度高度约束，内容多大，元素即多大 */
            display: inline;
        }

        span{
            width: 100px;
            height: 100px;
            border: 1px solid black;
            /* 转换为块元素 */
            display: block;
        }

        li{
            margin: 7px;
            text-decoration: none;
            /* 内联块元素，独立的块，但不独占一行 */
            display: inline-block;
        }
    </style>
</head>
<body>
    
    <p>wdnmd</p>
    <div>div块元素</div>
    <span>span行内元素</span>


    <ul>
        <li>c++</li>
        <li>java</li>
        <li>python</li>
        <li>golang</li>
    </ul>

</body>
~~~

应用：设计导航栏

- display是实现行内排布的一种方式，更多情况下我们用float实现

### float

为什么要浮动：方便控制元素位置，但会造成一些超出边框的问题

浮动元素，使网页分层

缩放网页时边框会塌陷

左右浮动 float:

- left
- right

~~~css
<head>
    <title>Document</title>

    <style>
        img{
            width: 550px;
            height: 500px;
            /* 使之变成块级元素独占一行 */
            /* display: block; */
        }

        .img1{
            float: right;
        }

        .img2{
            float: left;
        }
    </style>
</head>
<body>
      <img src="img/1.jpg" class="img1" alt="">  
      <img src="img/2.jpg" class="img2" alt="">
</body>
~~~

### 父级边框塌陷问题

clear：得作用于块级元素

~~~html
<body>
    <div class="father">
        <div class="img1"><img src="img/1.jpg" alt=""></div>    
        <div class="img2"><img src="img/2.jpg" alt=""></div> 
        <div id="hahaha"><p><h1>hahaha</h1></p></div>
    </div>      
</body>
~~~

~~~css
<style>        
	/* 当元素浮动后，div的边框border将无法框住它，在网页上形成不相关的两层 */
    .img1{
        float: right;
    }

    .img2{
        float: left;
    }

    /*  clear: right 
        右侧不允许有浮动元素，有则将自己换至下一行
        clear: left
        左侧不允许有浮动元素
        clear: both
        左右均不允许有浮动元素，可实现浮动元素的块状排列
    */
    #hahaha{
        float: left;
    }
</style>
~~~

如何消除塌陷？

1、对浮动元素 clear: both，然后增加父级元素高度

~~~css
.father{
    margin-top: 50px;
    height: 800px;
    border: 5px solid black;
}
~~~

2、增加一个空的div，清除浮动

~~~html
<body>
    <div class="father">
        <div class="img1"><img src="img/1.jpg" alt=""></div>    
        <div class="img2"><img src="img/2.jpg" alt=""></div> 
        <div id="hahaha"><p><h1>hahaha</h1></p></div>
    </div>   

    <div class="clear"></div>     
</body>
~~~

~~~css
.clear{
    clear: both;
    margin: 0;
    padding: 0;
}
~~~

3、overflow：在父级元素中设置，超过高度时处理

~~~css
overflow: hidden; /*隐藏*/
overflow: scroll /*产生滚动条*/
~~~

4、对父级元素使用伪类选择器

~~~css
father:after{
    content: '';
    display: block;
    clear: both;
}
~~~

## 定位

### 默认定位

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Document</title>
    <style>
        div{
            margin: 10px;
            padding: 5px;
            font-size: 14px;
            line-height: 25px;
        }

        #father{
            border: 5px solid black;
        }
        #first{
            background-color: blanchedalmond;
            border: 5px deshed rgb(112, 241, 151);
        }
        #second{
            background-color: orange;
            border: 5px deshed rgb(46, 7, 7);
        }
        #third{
            background-color: orchid;
            border: 5px deshed rgb(85, 31, 173);
        }

    </style>
</head>
<body>
    <div id="father">
        <div id="first">第一个盒子</div>
        <div id="second">第二个盒子</div>
        <div id="third">第三个盒子</div>
    </div>
</body>
</html>
~~~

### 相对定位

相对于原来的位置进行指定的偏移

- 相对定位后仍然在标准文档流中，不会造成父级边框塌陷

position: relative;

~~~css
#first{
        background-color: blanchedalmond;
        border: 5px deshed rgb(112, 241, 151);
        /* 相对定位 */
        position: relative;
        /* 向上偏移15px == bottom: 10px */
        top: -10px;
        /* 向右偏移15px == left: 15px */
        right: -15px; 
}
~~~

相对定位练习：链接卡

1. 若具有同样的css，可以用逗号隔开一次定义多个元素
2. 上下居中：line-height 与 height 定义相同高度
3. 左右居中：text-align: center;
4. 对于盒子中内容左右居中：margin: 0 auto;

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Document</title>
    <style>

        .father{
            border: 5px solid black;
            padding: 4px;
            width: 300px;
            height: 300px;
            margin: 0 auto;
            background-color: white;
        }

        a{
            text-decoration: none;
            background-color: pink;
            width: 100px;
            height: 100px;
            line-height: 100px;
            text-align: center;
            display: block;
        }

        a:hover{
            background: orange;
        }

        span{
            font-size: 28px;
            font-family: 楷体;
        }

        #link2, #link4{
            position: relative;
            top: -100px;
            right: -200px;
        }

        #link5{
            position: relative;
            top: -300px;
            right: -100px;
        }

       
    </style>
</head>
<body>
   
    <div class="father">
        <a id="link1" href="#"><span>链接1</span></a>
        <a id="link2" href="#"><span>链接2</span></a>

        <a id="link3" href="#"><span>链接3</span></a>
        <a id="link4" href="#"><span>链接4</span></a>  
        <a id="link5" href="#"><span>链接5</span></a>    
    </div>

</body>
</html>
~~~

### 绝对定位

定位：基于某某定位，上下左右

如下代码意思是与参照物右侧相距30px

~~~css
#second{
        background-color: orange;
        border: 5px deshed rgb(46, 7, 7);
        position: absolute;
        right: 30px;
}
~~~

1、当父级元素没有设置定位（相对定位或绝对定位）时，基于浏览器定位

2、当父级元素设置了，则基于父级元素定位

~~~css
#father{
        border: 5px solid black;
        position: relative;
}
~~~

- 只需有position属性，即使什么也不做，也依据父级元素定位

3、在父级元素范围内移动，不会超出，不会造成父级边框塌陷



### 固定定位 fixed

~~~css
<style>
        body{
            height: 1000px;
        }

        div:nth-of-type(1){
            width: 100px;
            height: 100px;
            background: red;
            /*定在body的右下方*/
            position: absolute;
            right: 0;
            bottom: 0;
        }

        div:nth-of-type(2){
            width: 50px;
            height: 50px;
            background: yellow;
            /* 定死在浏览器右下方 */
            /* 导航栏、回到顶部按钮 */
            position: fixed;
            right: 0;
            bottom: 0;
        }

</style>
~~~

### z-index

层级概念：图层

透明度：opacity

浮动元素分层：z-index

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Document</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div id="app">
        <ul>
            <li><img src="img/1.jpg" alt=""></li>
            <li class="tipText">图片一，哈哈哈</li>
            <li class="tipBg"></li>
            <li>时间:2021.11.14</li>
            <li>地点:工学馆329</li>
        </ul>
    </div>
</body>
</html>
~~~

~~~css
#app{
    padding: 0;
    margin: 0 auto;
    width: 500px;
    overflow: hidden;
    font-size: 12px;
    line-height: 25px;
    border: 2px solid black;
}

ul,li{
    /* 去小圆点 */
    list-style: none;
}

img{
    margin: 0;
    padding: 0;
    width: 500px;
    height: 300px;
} 

/* 父级元素相对定位 */
#app ul{
    position: relative;
}

.tipText, .tipBg{
    position: absolute;
    width: 500px;
    height: 25px;
    top: 50px;
}

.tipBg{
    background: black;
    /* 设置透明度0-1 */
    opacity: 0.5;
}

.tipText{
    color: white;
    /* 只有浮起来才有层级概念，否则全部是平铺的 */
    /* 层级从0开始，默认为0，最高无限，浮于tipBg上方 */
    z-index: 999;
}
~~~

## 更多

### 动画特效

less：将less代码编译为css语言，用编程的思想写css

百度canvas动画，下载源码

### Bootstrap

样式开发 ——> 组件开发（element-ui、layui）

[Bootstrap](https://www.bootcss.com/)

[BootstrapVue](https://bootstrap-vue.org/)

