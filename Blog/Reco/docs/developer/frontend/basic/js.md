---
title: JS 基础
date: 2021-11-7
tags:
  - FrontEnd
---

## 概述

### 什么是javascript

脚本语言

语法与java类似

ECMAScript：javascript标准，最新为6，普遍适用5

### 引入

html 内部使用，一般定义在 html 末尾，其实放哪无所谓

~~~html
<script>
        var num = 1;
        alert(num);
</script>
~~~

外部引用 .js 文件，一般放在 html 的最下方

~~~html
<script src="javascript/hello.js"></script>
~~~

### 基础

与java基本保持一致，无数据类型

~~~html
<!-- 语法和java基本保持一致 -->
    <script>
        //定义变量
        var score = 71;
        var name = "hello";
        //条件控制
        if(score > 60){
            alert("successed");
        } else {
            alert("failed");
        }
</script>
~~~

- 变量名规则基本与java保持一致

### 控制台操作

~~~css
console.log(value);
//在控制台打印一个变量value
~~~

或直接输入执行 js 代码，类似于 python 的解释性编程

### 数据类型

> 数值，文本，图形，音频，视频......

#### number

js不区分小数和整数，数字全为number类型

~~~js
123 //整数123
123.1 //浮点数123.1
1.123e3 //科学计数法
-99 //负数
NaN //not a number
Infinity //表示无限大
~~~

#### 字符串

'abc'	"abc"	'\n'

#### 布尔值

true	false

#### 逻辑运算

~~~js
&&	与
||	或
!	非
~~~

#### 比较运算符

在 js 中绝大部分判断等于时使用 === 绝对等于

~~~js
=	赋值
==	等于（类型不一样，值一样会判断为true，如1和'1'）
===	绝对等于（类型一样&&值一样）
~~~

属于 javascript 的缺陷

须知

- NaN与所有数据都不相等，包括自己

- 可通过 isNaN() 函数判断是否为NaN

- 浮点数问题

  ~~~js
  // 值为false，不相等，浮点数会有精度损失，尽量避免使用浮点数进行运算
  console.log((1/3) === (1-2/3));
  ~~~

#### null 和 undefined

null：空

undefined：用了不存在的变量

#### 数组

一系列相同类型的对象，js中不分类型（全为var），可以直接打印出来

~~~js
var arr = [1, 2, 3, 4, 5,"hello", null, true];
alert(arr);
alert(arr[0]);
~~~

数组越界：undefined

~~~js
//超出数组长度会报 undefined
alert(arr[12]);
~~~

也可以使用这样的形式，但可读性很低，不建议使用

~~~js
var arr = new Array(1, 2, 3, 4, "hello, world");
alert(arr);
~~~

#### 对象

对象式大括号，数组为中括号

~~~js
// 对象，每个属性之间用逗号隔开
var person = {
    name: "northboat",
    age: 2,
    tags: ["javascript", "java", "c++", "golang"]
}

console.log(person.name + " " + person.age + " " + person.tags);
~~~

### 严格检查模式

在 js 中，下列代码也是可以的，但很不好（太随意了）

~~~js
i = 1;
~~~

为了预防这种操作，在script的第一行启用严格检查模式，预防此类问题

~~~js
'use strict';
~~~

注意在 js 中，用 var 定义的均为全局变量，当一个html引入多个js文件时很不安全，我们用 let 定义局部变量，只作用于当前js文件

- ES6支持

~~~js
let a = 1;
~~~

## 数据类型与函数

### 数据类型

#### 字符串

多行编写 ``

~~~js
`多行编写\n我遍尼玛`
~~~

模板字符串 ${}

~~~js
let name = xzt;
let hello = wdnmd;
let msg = `我${name},${hello}`;
~~~

样例

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <script>
        // 严格检查模式
        'use strict';
        let a = "hahaha";
        let d = "wdnmd";
        // 内建函数
        let l = a.length;
        let b = a[0];
        let c = a.charAt(0);
        let f = a.substring(0, 4);
        console.log(a + '\n');
        console.log(l + '\n');
        if(b === c){
            console.log("他们一样噢~");
        }

        // 多行编写 模板字符串
        let msg = `${a}\n${d}`;
        console.log(msg);
        console.log(f);
    </script>
</head>
<body>
    
</body>
</html>
~~~

#### 数组

数组函数

~~~js
let arr = [1,2,3,4,5,a,b]
~~~

1. arr.length()
2. arr.indexOf(9)：越界将报undefined
3. slice(0, 5)：切割，同subString
4. push(...)：追加，可追加无限元素
5. pop()：弹出末尾元素
6. unshift(...)：头插
7. shift()：弹出头部元素
8. sort()
9. reverse()
10. concat("hahaha")：拼接，返回拼接结果，不修改原数组
11. join('-')：用 - 连接数组中元素，返回字符串
12. fill('+')：用+填充数组
13. find(1)：查找元素

多维数组

~~~js
let arr = [[a,2], ['hahaha',1],["wdnmd",'hello']];
~~~

#### 对象

对象定义

~~~js
let person = {
    name: "xzt",
    age: 3,
    socre: 59.5,
	tag: fw
}
~~~

使用不存在的属性：报错undefined

~~~js
person.hahaha;
~~~

动态删减属性

~~~js
delete person.tag;
~~~

动态添加属性

~~~js
person.hahaha = "wdnmd";
~~~

一些函数

~~~js
'age' in person		//true
'toString' in person	//true
person.hasOwnProperty('toString')	//false
~~~

- 'toString' 是对象继承而来的方法，使用in为true
- hasOwnProperty()只检查对象自己的方法，为false

### 流程控制

if判断

~~~js
let age = 3;
if(age < 3){
    console.log("呜呜呜");
} else if(age >= 3 && age <= 19){
    console.log("哈哈哈");
} else {
    console.log("...");
}
~~~

while循环

~~~js
while(age < 60){
    age = age+1;
    console.log(age);
}
~~~

for循环

~~~js
for(let i = 0; i < 9; i++){
    console.log(i + " wdnmd");
}
~~~

数组循环

- 在不知道数组大小时，使用for-in循环比较稳妥
- 更多时候我们使用函数遍历

~~~js
//数组循环
let arr = [1,2,3,4,5,6,7,8,9];
// 函数
arr.forEach(function(value){
    console.log(value);
})
// for-in:index为索引
for(let index in arr){
    console.log(arr[index]);
}
~~~

注意在使用for-in时，如给数组新增属性，x会输出"hahaha"，bug属于是

~~~js
arr.name = "hahaha";
for(let x in arr){
    console.log(x);
}
~~~



### Map和Set

1、map

~~~js
var map = new Map([["tom", 60], ["jack", 90], ["hahaha", 100]]);
// 通过key获得value
let score = map.get("tom");
map.set("admin", 123456);
map.delete("hahaha");
// 判断键是否存在
console.log(map.has("jack"));
~~~

2、set

~~~js
var set = new Set([3,1,1,1,1,1,2]); //Set可以去重
set.add(3);
set.delete(1);
// 以数组形式输出Set
let arr = Array.from(set);
console.log(set.has(3));
~~~

### Iterator

遍历器，ES6新特性

遍历数组

~~~js
'use strict';
let arr = [99,2,3,4,5];
// 遍历数组
for(let x of arr){
    console.log(x);
}
~~~

遍历map

~~~js
// 遍历map
let map = new Map([["tom", 60], ["jack", 90], ["hahaha", 100]]);
for(let x of map){
    console.log(x);
}
~~~

遍历set

~~~js
// 遍历set
let set = new Set([1,2,3,4,5]);
for(let x of set){
    console.log(x);
}
~~~

### 函数

#### 定义

推荐方法

~~~js
function abs(x){
    if(x < 0){
        return -x;
    } else {
        return x;
    }
}

let x = abs(-2);
~~~

方法二：定义abs为函数

~~~js
var abs = function(x){
    if(x < 0){
        return -x;
    } else {
        return x;
    }
}
~~~

参数问题：

- 若abs(1,2,3,4,5,6)，将返回第一个参数的结果
- 若abs()，即不传参，将返回undefined

不存在参数，手动抛出异常

- typeof x：x的类型
- throw：抛出异常

~~~js
function abs1(x){
    if(typeof x !== 'number'){
        throw "Not a Number";
    }
    return abs(x);
}
~~~

参数过多

- arguments：关键字 —— 参数
- 可以通过argument拿到多余参数
- arguments有自己的内置属性

~~~js
function abs2(x){

    for(let i = 0; i < arguments.length; i++){
        console.log(arguments[i]);
    }

    // 手动抛出参数过多异常
    if(arguments >= 2){
        throw "Too much arguments";
    }
	return abs1(x);
}
~~~

问题：arguments包含所有的参数（以数组的形式），有时候我们想操作多余参数，需要排除已有参数

> rest：剩余

ES6引入的新特性，获取除了已定义的参数之外的所有参数

- 必须按照以下格式，rest参数为rest，省略号模仿的java

~~~js
function fun(a, b,...rest){
    console.log("a=>" + a);
    console.log("b=>" + b);
    console.log(rest);
}
~~~

#### 作用域

类比C语言

变量未定义报错，当你试图用函数内定义的变量

- Uncaught ReferenceError: x is not defined

~~~js
function ha(){
    var x = 1;
    x++;
}
x = x+2;
~~~

内部函数能访问外部函数成员，反之不行

~~~js
function ha(){
    var x = 1;
    x++;
    function ha2(){
        console.log(x);
        var y = 0;
    }
    //console.log(y);将报错
}
~~~

当内部函数变量与外部函数重名，使用内部的env中函数，递归从内向外查找env->fatherEnv

~~~js
function ha(){
    var x = 1;
    x++;
    function ha2(){
        var x = 95;
        console.log(x);
        var y = 0;
    }
    console.log(x);
    ha2();
    //console.log(y);
}
ha();
~~~

此时输出

~~~
95
2
~~~

提升作用域

~~~js
function hei(){
    var y;
    var x = "x" + y;
    console.log(x);
    y = "y";
}
hei();
~~~

结果：xundefined

说明：自动提高了y的声明，不会报错

- 我们习惯在头部声明好变量，便于代码维护，无论是否赋值

> 全局变量

~~~js
var x = "xzt";
function f(){
    console.log(x);
}
f();
~~~

> window

~~~js
var x = "xzt";
alert(window.x);
~~~

默认所有的全局变量，都会自动绑定在window对象下，包括内建函数如 alert

~~~js
window.alert(window.x);
~~~

window => 浏览器本身，我们甚至可以通过window修改内建函数

~~~js
alert("hahaha");
var old_alert = window.alert;
window.alert = function(){

};
//alert失效
alert(window.x);
~~~

javascript实际上只有一个全局作用域，任何变量（函数也视为变量），假设没有在函数作用域内找到，就会向外查找，最终在window中找，若仍未找到，则报错 ReferenceError 引用异常

> 规范

由于全局变量都绑定在window上，如果不同的js文件同名将会产生冲突

将自定义函数、变量全绑定在唯一一个对象（变量）中

~~~js
var app = {};
app.name = "xzt";
app.fun = function(a, b){
    return a+b
}
~~~

> 局部作用域let

这样的代码在for代码块外也能读到a

~~~js
function aaa(){
    for(var a = 0; a < 100; a++){
        console.log(a);
    }
    console.log(a+1);
}
~~~

推荐以下写法

~~~js
function aaa(){
    for(let a = 0; a < 100; a++){
        console.log(a);
    }
    //console.log(a+1);
}
~~~

> 常量const

在ES6之前，这样定义常量，全部大写命名的变量即为常量，建议不要修改这样的值（但实际上是能改的）

~~~JS
var PI = 3.14;
~~~

在ES6中引入常量关键字const

~~~js
const PI = 3.14;
console.log(PI);
PI = 3.15;
~~~

将报错 invalid assignment to const 'PI'

#### 方法

对象中的函数

~~~js
var xzt = {
    name: "xzt",
    birth: 2001,
    age: function(){
        let cur = new Date().getFullYear();
        return cur-this.birth;
    }
}

console.log(xzt.age()); //20
~~~

this：指向自己 ——> 可以这样调用，但还是要加括号，只要调用函数，必有括号

~~~js
function getAge(){
    let cur = new Date().getFullYear();
    return cur-this.birth;
}
var xzt = {
    name: "xzt",
    birth: 2001,
    age: getAge
}

console.log(xzt.age()); //20
~~~

但不能直接 getAge()，因为此时 this 指向 window，window 无 birth 对象，将输出NaN

> apply(this, arugements)

~~~js
let x = getAge.apply(xzt, [])
console.log(x); //20
~~~

apply第一个参数为this指向对象，第二个参数为数组，即你要调用的函数参数，如此实现外部函数对某一特定对象的使用

## 对象

> 标准对象
>
> typeof 1 => "number"
>
> typeof "1" => "string"
>
> typeof true => "boolean"
>
> typeof [] => "object"
>
> typeof Math.abs => "function"

### 内部对象

#### Date

基本使用

~~~js
var now = new Date();
now.getFullYear();
now.getDate();  //日期（几号）
now.getDay()    //星期几
now.getMonth(); //0~11月
now.getHours();
now.getMinutes();
now.getSeconds();
~~~

转化时区，也可以toDateString(地区)

~~~js
//转化为当前地区时间
now.toLocaleDateString();
~~~

时间戳

~~~js
//获得时间戳，全世界统一，从1970.1.1 0:00:00到现在的毫秒数
let curTime = now.getTime();
for(let i = 0; i < 100000000; i++){
    i *= i;
}
let time = now.getTime();
console.log(time-curTime);
~~~

#### JSON

轻量级的数据交换格式 —— 键值对数据

早期，所有数据传输习惯使用xml文件

现在还有BSON，主要被用在MongoDB中，二进制的JSON

- 简洁和清晰的层次结构
- 易于人阅读编写，有效提升网络传输效率

任何js支持的类型都可以用JSON来表示：number, string, object, function

格式

- 对象都用{}
- 数组都用[]
- 所有的键值对，都是用 key-value

JS字符串和JSON的转化

~~~js
var person = {
    name: "xzt",
    age: 3,
    gender: "男"
}

// 将对象转化为JSON
var jsonPerson = JSON.stringify(person);

// 字符串转化为对象
var obj = JSON.parse('{"hahaha": 1, "age": 3, "eihei": "wdnmd"}');
~~~

JSON和JS对象的区别

~~~js
var obj = {a: "hello", b: 123};
var json = "{"a":"hello", "b": "123"}"
~~~

#### Ajax

- 原生的js写法：xhr 异步请求
- jQury 封装好的方法 $("#name").ajax("")
- axios 请求

### 面向对象

> 继承

#### proto继承

~~~js
var person = {
    name: "xzt",
    age: 3,
    gender: "男",
    run: function(){
        console.log(this.name + " is running");
    }
}

var bird = {
    fly: function(){
        console.log(this.name + " is flying");
    }
}

// 将对象转化为JSON
var jsonPerson = JSON.stringify(person);

// 字符串转化为对象
// var obj = JSON.parse({"hahaha": 1, "age": 3, "eihei": 99});

var xiaoming = {
    name: "xiaoming"
}

// 另类的继承，proto指模板，即xiaoming的模板为person
xiaoming.__proto__ = person;
xiaoming.run();

xiaoming.__proto__ = bird;
xiaoming.fly();
~~~

#### class继承

class关键字在ES6中引入

~~~html
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>

        <script>

            class Student{
                constructor(name){
                    this.name = name;
                }

                hello(){
                    alert("wdnmd");
                }
            }

            class XiaoStudent extends Student{
                constructor(name, grade){
                    // super实现父类同名方法
                    super(name);
                    this.grade = grade;
                }

                myHello(){
                    alert("我是一个小学生")
                }
            }

            var xiaoming = new Student("xiaoming");
            var xiaohong = new XiaoStudent("xiaohong", 86);
        </script>
    </head>
    <body>

    </body>
</html>
~~~



> 原型链：proto

在javascript中，每一个函数都有一个prototype属性，指向函数的原型对象

Object.prototype为null，停止查找，但实际上构成一个死循环，有点像jdk



## BOM和DOM

> BOM：浏览器对象模型
>
> B：Browser

### BOM

#### window

~~~js
// window
let windowInnerWidth = window.innerWidth;
let windowInnerHeight = window.innerHeight;
let windowOuterWidth = window.OuterWidth;
let windowOuterHeight = window.outerHeight;
~~~

#### navigator

储存了浏览器信息，可被修改，不推荐使用

~~~js
// navigator:浏览器信息 ——> 可被修改
let appName = navigator.appName;
let version = navigator.version;
let userAgent = navigator.userAgent;
let platform = navigator.platform;
~~~

#### screen

屏幕

~~~js
// screen:屏幕大小 ——> 电脑分辨率
let screenWidth = screen.width;
let screenHeight = screen.height;
~~~

#### location

网页地址

~~~js
// location
let host = location.host;
let href = location.href;
let protocal = location.protocal;
// 重新加载函数
location.reload();
// 重定向函数
location.assign("http://www.baidu.com");
~~~

#### history

主要控制网页前进\后退

~~~js
// history:网页历史
history.back(); //后退
history.forward();  //前进
~~~

#### document

html网页文档

~~~js
// ducoment ——> 当前HTML页面文档树
document.getElementsByClassName("hahaha");
document.getElementById("app");
document.getElementsByTagName("span");
document.cookie;
~~~

### DOM

> 文本对象模型
>
> D：Document

#### 获取结点

~~~js
// 获取结点
var ul = document.getElementById("oh");
var a = document.getElementsByClassName("hahaha");
var p = document.getElementById("ah")
~~~

#### 删除结点

~~~js
// 删除结点
var father = ul.parentElement;
father.removeChild(ul);
father.removeChild(father.children[0]);
~~~

#### 更新结点

注意此处要用id获取唯一结点

~~~js
// 修改结点内容，注意必须要用id获得的结点才能生效
p.innerText = "hahaha";
p.innerHTML = "<h1>wdnmd</h1>"
~~~

#### 添加结点

~~~js
// 添加结点
father.append(ul);
~~~

#### 创建并添加结点

~~~js
// 创建结点，参数为标签名
var btn = document.createElement("button");
// 创建文本结点
var text = document.createTextNode("wdnmd");
btn.append(text);
father.append(btn);
~~~

## 表单提交和加密

### 表单提交

onsubmit：在提交时可以设置js动作，一个函数

~~~html
<form action="https://www.baidu.com" method="post" onsubmit="return aaa()"></form>
~~~

onclick：与onsubmit同理，在点击提交时，设置一个js动作

~~~html
<button type="submit" onclick="aaa()">提交</button>
~~~

利用md5和hidden框进行加密

引入md5库，或下载下来用相对路径引用

~~~html
<!-- 引入md5 ——> 加密 -->
<script src="https://cdn.bootcdn.net/ajax/libs/blueimp-md5/2.18.0/js/md5.js"></script>
~~~

### 加密

设置隐藏输入框

~~~html
<input type="password" id="pwd"><br>
~~~

~~~html
<input type="hidden" id="hidden-pwd" name="password">
~~~

前端的payload有效负载将根据name属性接收识别其value，我们让真正的密码框没有name，在后台通过id调用真正的密码

同时将真正的密码用md5加密后赋值给后者，即给前端展示的name=password的虚假密码，实现加密

~~~html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- 引入md5 ——> 加密 -->
    <script src="https://cdn.bootcdn.net/ajax/libs/blueimp-md5/2.18.0/js/md5.js"></script>
</head>
    
<body>

    <div id="app">
        <form action="https://www.baidu.com" method="post" onsubmit="return aaa()">
            <span>账号：</span>
            <input type="text" id="name" name="username"><br>
            <span>密码：</span>
            <input type="password" id="pwd"><br>

            <!-- 单选框用name属性去约束为同一个 -->
            <!-- <span>性别：</span>       
            <input type="radio" name="gender" value="man"><span>男</span>
            <input type="radio" name="gender" value="woman"><span>女</span><br> -->


            <input type="hidden" id="hidden-pwd" name="password">

            <button type="submit">提交</button>
        </form>


    </div>

    <script>
        function aaa() {
            alert(1);
            var username = document.getElementById("name");
            var pwd = document.getElementById("pwd");
            var md5Pwd = document.getElementById("hidden-pwd");

            
            md5Pwd.value = md5(pwd.value);
            return true;
        }

    </script>
</body>
</html>
~~~

前端加密展示效果

<img src="../../../../.vuepress/public/img/image-20211124125426069.png">

## jQury

### 获取和使用

使用CDN引入JQury

~~~html
<!-- 引入jQuery CDN-->
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.js"></script>
~~~

下载在目录下，相对路径引用

~~~html
<script src="jquery-3.6.0/jquery-3.6.0.min.js"></script>
~~~

公式：$(selector).action()

~~~js
//id选择器
$('#a').click(function(){
    alert("康nm呢");
});
~~~

### 选择器

与css选择器一模一样，用单引号或双引号括起来

标签选择器

~~~js
$('p')
~~~

id选择器

~~~js
$("#id")
~~~

类选择器

~~~js
$(".class")
~~~

层次选择器：div后的p标签

~~~js
$("div p")
~~~

子选择器：ul后的第一代li标签

~~~js
$("ul>li")
~~~

相邻兄弟选择器：id为hahaha元素的同级的下一个p标签

~~~js
$("#hahaha + p")
~~~

通用兄弟选择器：id为hahaha元素的同级向下所有p标签

~~~js
$("#hahaha~p")
~~~

结构伪类选择器

~~~js
$("a:hover")
~~~

### 事件

鼠标事件

- mousedown：鼠标按下
- mouseleave：离开
- mouseover：点击结束
- mousemove：移动

鼠标移动

~~~js
$(document).ready(function(){
    $("#app").mousemove(function(e){
        $("#app").text("x:" + e.pageX + ", y:" + e.pageY);
    })   
})
~~~

等同于以下写法

~~~js
// $(document).ready(function(){}) == $(function(){})
$(function(){
    $("#app").mousemove(function(e){
        $("#app").text("x:" + e.pageX + ", y:" + e.pageY);
    }) 
})
~~~

鼠标点击

~~~js
$('#a').click(function(){
    alert("康nm呢");
    $("body").append(h);
});
~~~

键盘事件、其他事件...详见文档

### 操作DOM

文本操作

~~~js
$("#text-ul").text(); //获得值
$("#text-ul").text("hahaha");	//设置值

$("#text-ul li[name=java]").html();	//获得值
$("#text-ul li[name=java]").html("<span>123</span>");//设置值	
~~~

css操作

<img src="../../../../.vuepress/public/img/image-20211124210527473.png">

使消失，本质 display: none

~~~js
$("#app").show()
$("#app").hidden()
~~~

## Ajax

> 网络通信框架

~~~js
$("#from").ajax();

$.ajax({url: "text.html", context: document.body, success: function(){$(this).addClass("done")}});
~~~

