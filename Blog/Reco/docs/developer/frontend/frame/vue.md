---
title: Vue
date: 2022-1-17
categories:
  - WebApp
tags:
  - FrontEnd
---

> MVVM架构：Model-View-ViewModel，源自MVC架构（Model-View-Controller）
>
> 事件驱动编程

## vue基础

### 第一个vue程序

> 七大对象，`el、data、methods、computed、template、render、watch`

`cdn`引入

~~~html
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
~~~

首个`vue`页面

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <div id="app">
        {{message}}
    </div>
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
            el: "#app",
            data: {
                message: "Hello, Vue!"
            }
        });
    </script>
</body>
</html>
~~~

绑定，其中`data`即为`Model`（数据）

~~~html
<script>
    var vm = new Vue({
        el: "#app",
        data: {
            message: "Hello, Vue!"
        }
    });
</script>
~~~

取值：所谓`View`层

~~~html
<div id="app">{{message}}</div>
~~~

双向绑定，在控制台直接修改`data`值，将动态打印`message`，所谓`View-Model`

~~~js
vm.message = "hahaha"
~~~

### 基础语法

用指令绑定事件

#### v-bind

`v-bind`：绑定，直接使用`el`中的`data`，用双引号引入

~~~html
<span v-bind:title="message">鼠标悬停显示提示信息</span>
~~~

绑定组件

~~~html
<div id="app">
    <my-item v-for="node in list" v-bind:item="node"></my-item>
</div>

<script>
    Vue.component("my-item", {
        props: ['item'],
        template: '<li>{{item}}</li>'
    })

    var vue = new Vue({
        el: "#app",
        data: {
            list: ["hahaha", "sad", "northboat"]
        },
    });
</script>
~~~

#### v-if

`v-if/v-else`

~~~html
<h1 v-if="ok">{{yes}}</h1>
<h1 v-else>{{no}}</h1>

<script>
    var vm = new Vue({
        el: "#app",
        data: {
            message: "Hello, Vue!",
            ok: true,
            yes: "Yes~",
            no: "No~"
        }
    });
</script>
~~~

在控制台修改

~~~js
vm.ok = false
~~~

页面将打印`No~`

v-else-if

~~~html
<h1 v-if="type==='A'">A</h1>
<h1 v-else-if="type==='B'">B</h1>
<h1 v-else>other type</h1>

<script>
    var vm = new Vue({
        el: "#app",
        data: {
            message: "Hello, Vue!",
            type: 'A'
        }
    });
</script>
~~~

通过在前端修改`vm.type`，可以动态修改页面内容

#### v-for

`v-for`展示

~~~html
<li v-for="hahaha in items">{{hahaha.message}}</li>
~~~

在`data`中定义数组，用`[]`定义数组

~~~html
<script>
    var vm = new Vue({
        el: "#app",
        data: {
            items: [
                {message: "java"},
                {message: "vue"},
            ],
        }
    });
</script>
~~~

传入两种参数`v-for="(hahaha, index) in items"`其中`index`为数组中元素下标，`hahaha`为数组元素

~~~html
<li v-for="(hahaha, index) in items">{{index}} : {{hahaha.message}}</li>
~~~

在控制台里，输入 `app.items.push({ message: '新项目' })`，动态增加元素

#### v-on

添加事件监听

~~~html
<div>
    <p>{{message}}</p>
    <button v-on:click="reverseMsg">反转message</button>
</div>

<script>
    var vm = new Vue({
        el: "#app",
        data: {
            message: "Hello, Vue!",
        },
        methods: {
            reverseMsg: function(){
                this.message = this.message.split('').reverse().join('');
            }
        },
    });
</script>
~~~

#### v-model

> 实现双向绑定，灰常重要

model：信息，实现实时更新信息

可作用于`input.type = text\textarea\radio`，注意`radio`用同样的`name`绑定在一起

~~~html
<p>{{inputMsg}}</p>
<input v-model="inputMsg" type="text">

<script>
    var vm = new Vue({
        el: "#app",
        data: {
            inputMsg: "wdnmd",
        }
    });
</script>
~~~

在`input`中输入的信息将实时更新到`<p>{{inputMsg}}</p>`中

~~~html
<input type="radio" name="gender" value="男" v-model="gender">男
<input type="radio" name="gender" value="女" v-model="gender">女
<input type="radio" name="gender" value="男酮" v-model="gender">♂

<h3>你选中了：{{gender}}</h3>

<script>
    var v = new Vue({
        el: "#app", 
        data: {
            gender: '',
        }
    });
</script>
~~~

下拉框：select，用v-modle进行初始化，在script中赋值

~~~html
<select v-model="grade">
    <option>A</option>
    <option>B</option>
    <option>C</option>
</select>
<h3>评级：{{grade}}</h3>

<script>
    var v = new Vue({
        el: "#app", 
        data: {
            msg: "nmsl",
            gender: '',
            grade: 'A'
        }
    });
</script>
~~~

一般会这样处理，留一个disable的下拉框提示用户请选择

~~~html
<select v-model="grade">
    <option value="" disabled>--请选择--</option>
    <option>A</option>
    <option>B</option>
    <option>C</option>
</select>
<h3>评级：{{grade}}</h3>

<script>
    var v = new Vue({
        el: "#app", 
        data: {
            msg: "nmsl",
            gender: '',
            grade: ''
        }
    });
</script>
~~~

- v-model的优先级高于value、checked、selected，直接从js中拿数据

### 组件

#### 自定义标签

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Document</title>
</head>
<body>

    <div id="app">
        <my-item v-for="node in list" v-bind:item="node"></my-item>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    
    <script>
        Vue.component("my-item", {
            props: ['item'],
            template: '<li>{{item}}</li>'
        })

        var vue = new Vue({
            el: "#app",
            data: {
                list: ["hahaha", "sad", "northboat"]
            }
        });
    </script>
</body>
</html>
~~~

#### 插槽

slot

~~~html
<div id="app">
    <test>
        <!-- v-bind简写为 :         v-on简写为 @click -->
        <!-- 绑定插槽，绑定数据 -->
        <test-title slot="t" :title="hahaha"></test-title>
        <test-items slot="is" v-for="i in items" :item="i"></test-items>
    </test>
</div>

<script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
<script>
    Vue.component("test", {
        // 设置插槽名字，用于以外元素绑定插槽
        template:  '<div>\
					<slot name="t"></slot>\
					<ul>\
						<slot name="is"></slot>\
    				</ul>\
    			  </div>'
    });

    Vue.component("test-title", {
        props: ["title"],
        template:   '<h3>{{title}}</h3>'
    });

    Vue.component("test-items", {
        props: ["item"],
        template:   '<li>{{item}}</li>'
    })

    var v = new Vue({
        el: "#app",
        data: {
            hahaha: "NorthBoat",
            items: ["java", "vue", "bootstrap"],
        }
    });
</script>
~~~

定义插槽

~~~js
Vue.component("test", {
    // 设置插槽名字，用于以外元素绑定插槽
    template:  '<div>\
				  <slot name="t"></slot>\
				  <ul>\
					  <slot name="is"></slot>\
    			   </ul>\
    		    </div>'
});
~~~

设置插槽名字，用于外来元素绑定插槽

~~~js
<slot name="t"></slot>
~~~

绑定插槽，绑定数据

`:title="hahaha"`绑定的是`test-title:title`，即当前组件的`pros`，`slot="t"`是将当前组件与插槽`t`绑定，实现自定义数据、组件动态交互

~~~html
<test>
    <!-- 绑定插槽，绑定数据 -->
    <test-title slot="t" :title="hahaha"></test-title>
    <test-items slot="is" v-for="i in items" :item="i"></test-items>
</test>
~~~

- v-bind简写`:`
- v-on简写`@`

~~~js
Vue.component("test-title", {
    props: ["title"],
    template:   '<h3>{{title}}</h3>'
});

Vue.component("test-items", {
    props: ["item"],
    template:   '<li>{{item}}</li>'
})

var v = new Vue({
    el: "#app",
    data: {
        hahaha: "NorthBoat",
        items: ["java", "vue", "bootstrap"],
    }
});
~~~

#### 自定义事件分发

设置组件

~~~js
//div、slot
Vue.component("test", {
    // 设置插槽名字，用于以外元素绑定插槽
    template:  '<div><slot name="t"></slot><ul><slot name="is"></slot></ul></div>'
});

//title
Vue.component("test-title", {
    props: ["title"],
    template:   '<h3>{{title}}</h3>'
});
~~~

定义方法，与外界方法进行绑定

~~~js
//li
Vue.component("test-items", {
    props: ["item", "index"],
    template:   '<li>{{item}}<button @click="remove">删除</button></li>',
    methods:{
        remove: function(index){
            this.$emit("remove", index)
        }
    }
})
~~~

定义vue对象

~~~js
var v = new Vue({
    el: "#app",
    data: {
        hahaha: "NorthBoat",
        items: ["java", "vue", "bootstrap"],
    },
    methods: {
        removeItem: function(index){
            this.items.splice(index, 1);
        }
    }
});
~~~

进行一系列绑定

- 标签与插槽绑定
- 插槽中props与data数据进行绑定，可以多个（`v-for`遍历出来的数据可以为`(data, index)`的形式）
- 插槽中方法与vue对象中方法进行绑定，这一步需要绑定三次，一次在body的标签中，一次在插槽的方法里用emit绑定，一次在插槽template中绑定插槽的方法

~~~html
<div id="app">
    <test>
        <!-- v-bind简写为:  v-on简写为@ -->
        <!-- 绑定插槽，绑定数据 -->
        <test-title slot="t" :title="hahaha"></test-title>
        <test-items slot="is" v-for="(item,index) in items" :item="item" :index="index" @remove="removeItem"></test-items>
    </test>
</div>
~~~

### 网络通信

Axios：整合jQuery.ajax实现网络通信，异步通信

在钩子函数中实现资源加载功能，如重写`mounted`()，请求回的数据用`data()`函数接收，存于`return`的变量中，如以下代码存于`info`，在`view`层直接用`info`取数据

   ~~~html
   <div id="app">
       {{info.name}}
   </div>
   
   
   <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
   <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
   <script>
       var v = new Vue({
           el: "#app",
           data(){
               return{
                   info:{
                       name: null,
   
                   }
               } 
           },
   
           mounted(){
               axios.get("data.json").then(response=>{this.info = response.data});
           }
       });
   </script>
   ~~~

post方法

~~~js
mounted(){
    axios.post("39.106.160.174:8084/getMainPage").then(response=>{this.info = response.data});
}
~~~

### 计算属性

重写`vue`的`computed`属性

与`methods`的区别

- 通过computed返回的值将储存在内存中，如以下代码Date.now()将储存在getTimeVal中，我们可以直接通过getTimeVal进行变量取值
- 而methods只作用一次，并不储存

~~~html
<div id="app">
    <h3>currentTimeByMethod:{{getTime()}}</h3>
    <h3>currentTimeByValue:{{getTimeVal}}</h3>
</div>

<script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
<script>
    var v = new Vue({
        el: "#app",
        methods: {
            getTime: function(){
                return Date.now();
            }
        },
        computed: {
            getTimeVal: function(){
                return Date.now();
            }
        }
    });
</script>
~~~

## vue-cli

类似于maven

### 准备

npm：包管理工具

1. 下载nodejs，官网下载即可

   ~~~bash
   node -v
   npm -v
   ~~~

2. 下载cnpm（淘宝代理）

   ~~~bash
   npm install -g cnpm --registry=https://registry.npm.taobao.org
   ~~~

3. 下载vue-cli

   ~~~bash
   cnpm install vue-cli -g
   ~~~

### 第一个vue-cli程序

初始化

~~~bash
vue init webpack myvue	//myvue项目名
cd myvue

npm install	//根据myvue目录下package.json安装依赖

npm run dev	//运行项目
~~~

依赖都放在`node_modules`目录下，相当于`java`的`lib`

一个问题：

在vscode的终端中运行`npm run dev`报错无法在此系统运行脚本

在管理员权限的`windows powershell`中将执行策略设置为`RemoteSigned`

~~~shell
set-ExecutionPolicy RemoteSigned
~~~

查看是否更改成功

~~~shell
get-ExecutionPolicy
~~~

- 所有东西都变成了组件

端口号在`config/index.js`中配置

### webpack

> 服务器端的nodejs遵循CommonsJS规范，该规范的核心思想是允许模块通过require方法来同步加载所需以来的其他模块，然后通过exports来导出需要暴露的接口
>
> ~~~js
> require("module");
> require("../module.js");
> export.doStuff = function(){};
> module.exports = someValue;
> ~~~
>
> 限定了作用域，防止项目中不同js的重名冲突
>
> 但同步 ——> 阻塞
>
> AMD（Asynchronous Module Definition）异步加载模块
>
> CMD（Commons Module Definition）规范和AMD类似，但尽量保持简单 ——> sea.js
>
> ES6规范：模块化思想
>
> ~~~js
> import "jquery";
> export function doStuff(){};
> module "localModule"{};
> ~~~

静态模块打包器，它会递归的构造一个依赖关系图（dependency graph），将ES6规范的代码打包降级为ES5规范给浏览器使用

安装webpack

~~~bash
npm install webpack -g
npm install webpack-cli -g
~~~

查看安装

~~~bash
webpack -v
webpack-cli -v
~~~

配置webpack.config.js文件

- entry：入口文件，main.js
- output：输出，指定webpack把处理后的文件放置在指定目录
- module：模块，用于处理各种类型的文件
- plugins：插件，如热更新、代码重用等
- resolve：设置路径指向
- watch：监听，用于设置文件改动后直接打包

初试webpack

1. 新建项目

   - modules：放置js文件
   - webpack.config.js：webpack打包配置文件
   - index.html：展示页面

2. js文件

   - hello.js

     ~~~js
     //暴露一个方法
     exports.sayHey =  function(){
         document.write("<h1>ES6规范</h1>");
     }
     ~~~

   - main.js

     ~~~js
     var hello = require("./hello");
     hello.sayHey();
     ~~~

3. 使用`webpack`命令打包，生成ES5规范的一个统一的js文件（压缩过的）

webpack.config.js

~~~js
module.exports = {
    entry: "./modules/main.js",
    output: {
        filename: "./js/bundle.js"
    }
};
~~~

index.html

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
 
    <script src="./dist/js/bundle.js"></script>

</body>
</html>
~~~

效果：在网页打印一个`<h1>ES6规范</h1>`

### vue-router

路由，控制页面跳转

#### 新建组件

> 在vscode中使用vueter插件敲`<vue>+tab`可以自动补全vue文件模板

均放在组件目录下

NorthBoat.vue

~~~vue
<template>
  <h1>NorthBoat</h1>
</template>

<script>
export default {
    name: "NorthBoat"
}
</script>

<style>
</style>
~~~

Main.vue

~~~vue
<template>
  <h1>首页</h1>
</template>



<script>

export default {
    name: "Main"
}

</script>

<style>
</style>
~~~

Content.vue

~~~vue
<template>
    <h1>内容页</h1>
</template>

<script>


export default {
    name: "Content"
}

</script>

<style scoped>
</style>>
~~~

- `<style scoped>`中scoped为限制样式作用域仅为当前vue文件

#### 安装router

在当前项目下安装`vue-router`

~~~shell
npm install vue-router --save-dev
~~~

#### 配置router

新建router目录，新建index.js为router配置文件

1. 导入组件

   ~~~js
   import Vue from 'vue'
   import Router from 'vue-router'
   import Content from '../components/Content'
   import Main from '../components/Main'
   import NorthBoat from '../components/NorthBoat'
   ~~~

2. 安装路由

   ~~~js
   import Router from 'vue-router'
   Vue.use(Router);
   ~~~

3. 配置路由

   ~~~js
   export default new Router({
       routes:[
           {
               //路由路径
               path: "/content",
               //跳转组件
               component: Content,
               name: "content"
           },
           {
               path: "/main",
               component: Main,
               name: "main"
           },
           {
               path: "/home",
               //跳转组件
               component: NorthBoat,
               name: "home"
           },
       ]
   });
   ~~~

#### 注入router

在main.js中导入路由目录

~~~js
import router from './router' //将自动扫描目录下的路由
~~~

在Vue对象中注入路由

~~~js
new Vue({
  el: '#app',
  //配置路由
  router: router,
  components: { App },
  template: '<App/>'
})
~~~

#### 使用router

在App.vue中使用router进行跳转

~~~vue
<template>
  <div id="app">
    <h1>Vue-Router</h1>
    <router-link to="/main">首页</router-link>
    <router-link to="/content">内容页</router-link>
    <router-link to="/home">home页</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
~~~

其中`router-link`为链接，相当于`<a></a>`；`to`为跳转页面，相当于`@RequestMapper`；`router-view`为展示跳转内容，即在`<h1>`之下以一个组件的形式展示跳转页内容

- 导入axios

  ~~~shell
  npm install --save axios vue-axios
  ~~~

  ~~~js
  import axios from 'axios'
  import vueAxios from 'vue-axios'
  Vue.use(axios, vueAxios);
  ~~~

## elementUI

网站快速成型工具，类似于bootstrap

### 准备

新建项目

~~~shell
vue init webpack hello-element-ui
~~~

安装element-ui

~~~shell
npm i element-ui -S
~~~

安装router、sass加载器（element-ui中用到了sass）

~~~shell
npm install vue-router --save-dev
cnpm intall sass-loader node-sass --save-dev
~~~

[Element - 中文文档](https://element.eleme.cn/#/zh-CN)

### 入门

写一个简单的登录界面

1. 编写组件，暴露接口
2. 引入接口，编写路由
3. 将路由、ElementUI注入main.js

编写组件

Login.vue

~~~js
<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎 登录</h3>
      <el-form-item label=" 账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label=" 密码" prop="password">
        <el-input type="password" placeholder=" 请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog>
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handLeClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog- footer">
        <el-button type="primary" @click="dialogVisible = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        form: {
          username: '',
          password: ''
        },
        //表单验证，需要在el-form-item 元素中增加prop 属性
        rules: {
          username: [
            {required: true, message: " 账号不可为空", trigger: 'blur'}
          ],
          password: [
            {required: true, message: " 密码不可为空 ", trigger: 'blur'}
          ]
        },
        //对话框显示和隐藏
        dialogVisible: false
      }
    },
    methods: {
      onSubmit(formName) {
        //为表单绑定验证功能
        this.$refs [formName].validate((valid) => {
          if (valid) {
            //使用vue-router路由到指定页面，该方式称之为编程式导航
            this.$router.push("/main");
          } else {
            this.dialogVisible = true;
            return false;
          }
        });
      }
    }
  }
</script>

<style lang="scss" scoped>
  .login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }
</style>
~~~

Main.vue

~~~vue
<template>
  <h1>NorthBoat</h1>
</template>

<script>
export default {
    name: "Main"
}
</script>

<style>

</style>
~~~

App.vue

~~~vue
<template>
  <div id="app">
    <h1>hahaha</h1>
    <router-link to="/login">登录</router-link>
    <router-link to="/main">主页</router-link>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
~~~

配置路由

~~~js
import Vue from 'vue'
import Router from 'vue-router'

import Main from '../views/Main'
import Login from '../views/Login'

Vue.use(Router);

export default new Router({
    routes:[
        {
            path: '/main',
            component: Main
        },
        {
            path: '/login',
            component: Login
        }
    ]
});
~~~

注入main.js

~~~js
import Vue from 'vue'
import App from './App'

import router from './router'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);
Vue.use(router);


new Vue({
  el: '#app',
  router: router,
  render: h => h(App) //ElementUI
});
~~~

sass-loader、node-sass、nodejs版本降级问题

[Module build failed: Error: Node Sass version 7.0.0 is incompatible with ^4.0.0.@node-sass版本问题解决总汇](https://blog.csdn.net/a2986467829/article/details/121788853)

### 路由嵌套及重定向

> 使用`children`进行路由嵌套

在index.js中进行路由嵌套

~~~js
import Vue from 'vue'
import Router from 'vue-router'

import Main from '../views/Main'
import UserList from '../views/user/List'
import UserProfile from '../views/user/Profile'

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes:[
        {
            path: '/main',
            component: Main,
            children:[
                {path: '/user/profile', component: UserProfile},
                {path: '/user/list', component: UserList}
            ]
        }      
    ]
});
~~~

引入vue，在route.children属性中定义重定向页面

- `mode: 'history'`用于去除网页的`#`号

重定向，直接在index.js中进行路由的重定向，以下js将`/`重定向到`/login`页，当访问`/`时自动跳转到`/login`

~~~js
routes:[
	{
    	path: '/',
    	redirect: '/login'
	}
]
~~~

引入404，编写`NotFound.vue`，在index.js中引入，定义路由

~~~js
import NotFound from '../views/NotFound'
Vue.use(Router);
export default new Router({
	routes:[
		{
        	path: '/*',
        	component: NotFound
    	}
	]
});
~~~

注意：路由是按照上下次序进行检索，你应将所有有用的路由定义在`/*`上方，否则会直接跳到404页面

### 传参

template，其中`name`为路由的名字，在index.js中唯一定义

~~~html
<el-menu-item-group>
    <router-link :to="{name: 'Profile', params: {username: 'NorthBoat'}}"><el-menu-item index="1-1">个人信息</el-menu-item></router-link>
    <router-link :to="{name: 'List', params: {id: 4}}"><el-menu-item index="1-2">用户列表</el-menu-item></router-link>
</el-menu-item-group>
~~~

index.js，profile可以理解为get，在url后通过`/:key`的形式传入参数；list可以理解为post，通过props传参，要在index.js中令该route的props为true

~~~js
export default new Router({
    mode: 'history',
    routes:[
        {
            path: '/main',
            component: Main,
            props: true,
            children:[
                {path: '/user/profile/:username', component: UserProfile, name: 'Profile'},
                {path: '/user/list', component: UserList, name: 'List', props: true}
            ]
        },
    ]
})
~~~

Profile.vue，直接通过route对象拿到参数

~~~vue
<template>
  <h1>{{$route.params.username}}</h1>
</template>

<script>
export default {
    name: "Profile"
}
</script>

<style>
</style>
~~~

List.vue，通过props接收参数，再从props中拿取参数

~~~vue
<template>
  <h1>{{id}}</h1>
</template>

<script>
export default {
    props:['id'],
    name: "List"
}
</script>

<style>

</style>
~~~

### 钩子函数及Axios

#### beforeRouteEnter

> 在进入路由前

常从某一路由拿取数据，可以通过`data()`的方式将数据保存在当前页面

~~~js
data(){
    return{
        info:{
        	name: null,
            id: -1,
            pwd: null
    	}
    }
},
methods: {
  	getData: function(){
        this.axios({
            method: 'get',
            url: 'http://localhost:8080/static/mock/data.json'
        }).then(response => {this.info = response.data});
    }  
},
beforeRouteEnter: (to, from, next)=>{
    console.log("进入路由之前");
    next(vm => {
        vm.getData();
    })
}
~~~

- 在vue中，静态数据通常放置在`/static/mock/`目录下

next()的几种用法：

1. next()：直接跳转到下一页面，如，若是`beforeRouteEnter`，则为进入当前页；若是`beforeRouteLeave`，则为进入跳转页面
2. next(false)：回到上一页面，从哪来回哪去
3. next(vm => {})：只能在`beforeRouteEnter`中使用，vm为当前vue对象，可以调用其中一些方法，然后进入当前页面

#### beforeRouteLeave

与上相同，通常用于将参数传递到下一页面，注意作用时间

### `.vue`中引入`.vue`

1. 在`MyNav.vue、MySidebar.vue`中导出

2. 在Main.vue的script中导入

   ~~~vue
   <script>
   import MyNav from './MyNav.vue'
   import MySidebar from './MySidebar.vue'
   export default {
     name: "Main",
     components: { MyNav, MySidebar }
   }
   
   </script>
   ~~~

3. 在Main.vue的template中使用

   ~~~vue
   <template>
     <div>
       <my-nav id="nav"></my-nav>
       <my-sidebar id="sidebar"></my-sidebar>
       <router-view></router-view>
     </div>
   </template>
   ~~~


## 更多

[Bootstrap-Vue ](https://bootstrap-vue.org/)

[Layui-Vue ](http://layui-vue.pearadmin.com/zh-CN/components/button)

[docsify](https://docsify.js.org/#/)

