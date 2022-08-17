---
title: 造梦III踩云朵小游戏
date: 2021-11-11
tags:
  - FrontEnd
categories:
  - Toy
---

> 造梦三开头的网页小游戏

## 碰撞检测

~~~js
// node1为云，node2为人
function crash(node1, node2){
    var l1 = node1.offsetLeft;
    var r1 = node1.offsetLeft + node1.offsetWidth;
    var t1 = node1.offsetTop;            
    var b1 = node1.offsetTop + node1.offsetHeight;

    var l2 = node2.offsetLeft;
    var r2 = node2.offsetLeft + node2.offsetWidth;
    var t2 = node2.offsetTop;
    var b2 = node2.offsetTop + 100;

    console.log("云：" + l1 + "," + r1 + "," + t1 + "," + b1);
    console.log("人：" + l2 + "," + r2 + "," + t2 + "," + b2)
    if (l2 > r1 || r2 < l1 || t2 > b1 || b2 < t1) {
        return false;
    }
    return true;
}
~~~

## 画云朵

div

~~~css
.cloud{
    background-color: white;
    width: 50px;
    height: 50px;
    border-radius: 50%; 
    background-color: #fff; 
    box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px; 
    position: absolute;
    top: 430px;
    left: 185px;
}
~~~

## 渐变色背景

~~~css
background-color: #0093E9;
background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
~~~

## 超出隐藏

~~~css
overflow: hidden;
~~~

## 用js设置css

~~~js
cloud.setAttribute("class", "cloud");
cloud.style.cssText = "background-color: white;\
width: 50px; height: 50px;\
border-radius: 50%;\
background-color: #fff;\
box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px;\
position: absolute;";
cloud.style.top = -5 + "px";
cloud.style.left = rand(0, 400) + "px";
~~~

## 人物移动

- 根据鼠标位置和人物位置移动人物
- 根据鼠标人物位置翻转图片，达到转身效果

~~~js
// 控制人物移动以及动画
$(document).ready(function(){
    $("#app").mousemove(function(e){
        if(e.pageX >= 560 && e.pageX <= 1100 && e.pageY >= 40 && e.pageY <=740){
            var loc = wukong.offsetLeft+642;
            if(e.pageX > loc && loc <= 1020 ){
                $("#img").attr("src", "img/C-R.png");
                wukong.style.left = wukong.offsetLeft+9+"px";
            } else if(e.pageX < loc && loc >= 595){
                $("#img").attr("src", "img/R-C.png");
                wukong.style.left = wukong.offsetLeft-9+"px";
            }
        }
    })                    
})
~~~

## 随机数生成

~~~js
//随机生成n~m的随机数
//原random函数含头不含尾
function rand(n, m) {
    return n+parseInt(Math.random() * (m-n+1));
}
~~~

## 计时器

~~~js
var timer;
// 开始游戏
$("#begin").click(function(){
    clearInterval(timer);
    timer = setInterval(function(){})//在此处作动作
}
~~~

## 源码

### HTML

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>


    <link rel="stylesheet" href="css/style.css">


    <script src="jquery-3.6.0/jquery-3.6.0.min.js"></script>



</head>
<body style="position: relative">

    <div id="app">
        <div class="cloud"></div>
        <div id="hahaha" class="cloud"></div>
        <div id="nmsl" class="cloud"></div>
        <div id="wukong"><img id="img" src="img/C-R.png" alt="#"></div>
    </div>

    <div id="tips">
        <h1>高度：</h1>
        <h1 name="grade"></h1><h1>km</h1><br><br>
        <p>游戏方法：用鼠标移动（画圈圈）控制悟空的左右移动</p>
        <p>当碰到云朵时将向上跳跃，当悟空摔死即为游戏结束</p><br><br><br>
        <a href="#" id="begin">开始游戏</a>
    </div>

    


    <script>
        var wukong = document.getElementById("wukong");
        var box = document.getElementById("app");
        var speed = 2;
        var isRunning = false;
        var grade = 0;


        // 打印分数
        $("#tips h1[name=grade]").text(grade);
        

        // node1为云，node2为人
        function crash(node1, node2){
            var l1 = node1.offsetLeft;
            var r1 = node1.offsetLeft + node1.offsetWidth;
            var t1 = node1.offsetTop;            
            var b1 = node1.offsetTop + node1.offsetHeight;

            var l2 = node2.offsetLeft;
            var r2 = node2.offsetLeft + node2.offsetWidth;
            var t2 = node2.offsetTop;
            var b2 = node2.offsetTop + 100;

            console.log("云：" + l1 + "," + r1 + "," + t1 + "," + b1);
            console.log("人：" + l2 + "," + r2 + "," + t2 + "," + b2)
            if (l2 > r1 || r2 < l1 || t2 > b1 || b2 < t1) {
                return false;
            }
            return true;
        }


        function createCloud(n){
            console.log(n);
            for(var i = 0; i < n; i = i+1){
                var cloud = document.createElement("div");
                cloud.setAttribute("class", "cloud");
                cloud.style.cssText = "background-color: white;\
                                       width: 50px; height: 50px;\
                                       border-radius: 50%;\
                                       background-color: #fff;\
                                       box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px;\
                                       position: absolute;";
                cloud.style.top = -5 + "px";
                cloud.style.left = rand(0, 400) + "px";
                box.appendChild(cloud);
                console.log("wdnmd");
            }
        }

        //随机生成n~m的随机数
        function rand(n, m) {
            return n+parseInt(Math.random() * (m-n+1));
        }

        
        function jump(){
            wukong.style.top = wukong.offsetTop-160 + "px";
        }





        // 控制人物移动以及动画
        $(document).ready(function(){
            $("#app").mousemove(function(e){
                if(e.pageX >= 560 && e.pageX <= 1100 && e.pageY >= 40 && e.pageY <=740){
                    var loc = wukong.offsetLeft+642;
                    if(e.pageX > loc && loc <= 1020 ){
                        $("#img").attr("src", "img/C-R.png");
                        wukong.style.left = wukong.offsetLeft+9+"px";
                    } else if(e.pageX < loc && loc >= 595){
                        $("#img").attr("src", "img/R-C.png");
                        wukong.style.left = wukong.offsetLeft-9+"px";
                    }
                }
            })                    
        })




        var timer;
        // 开始游戏
        $("#begin").click(function(){
            clearInterval(timer);
            if(!isRunning){
                wukong.style.top = wukong.offsetTop-300 + "px";
            }
            isRunning = true;
            timer = setInterval(function(){
                
                var left = wukong.offsetLeft
                var top = wukong.offsetTop;
                if(top > 720){
                    alert("你的高度：" + grade + "km");
                    location.reload();
                    isRunning = false;                   
                }

                wukong.style.top = top+speed + "px";
                var clouds = $(".cloud");
                if(clouds.length == 1){ createCloud(rand(1, 1)); }

               
                for(var i = 0; i < clouds.length; i++){
                    clouds[i].style.top = clouds[i].offsetTop+1 + "px";
                    if(crash(clouds[i], wukong)){
                        box.removeChild(clouds[i]);
                        jump();
                        grade = grade+12;
                        $("#tips h1[name=grade]").text(grade);
                        createCloud(rand(0, 1));
                    }
                    if(clouds[i].top > 720){
                        box.removeChild(clouds[i]);
                    }
                }
                
            });            
        });

        

        
    </script>
</body>
</html>
~~~

### CSS

~~~css
div{
    display: inline-block;
}

p{
    color: black;
}

a{
    display: block;
    height: 50px;
    width: 100px;
    background-color: black;
    text-decoration: none;
    text-align: center;
    line-height: 50px;
    color: white;
}

h1{
    color: black;
}


#tips{
    height: 500px;
    width: 300px;
    position: absolute;
    top: 150px;
    right: 400px;
}

#app{
    border: 1px solid black;
    height: 700px;
    width: 500px;
    position: absolute;
    left: 600px;
    top: 40px;
    background-color: #0093E9;
    background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
    overflow: hidden;
}

#wukong {
    /* opacity: 0.1; */
    position: absolute;
    bottom: 0px;
    left: 225px;
}


.cloud{
    background-color: white;
    width: 50px;
    height: 50px;
    border-radius: 50%; 
    background-color: #fff; 
    box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px; 
    position: absolute;
    top: 430px;
    left: 185px;
}

#hahaha{
    background-color: white;
    width: 50px;
    height: 50px;
    border-radius: 50%; 
    background-color: #fff; 
    box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px; 
    position: absolute;
    top: 230px;
    left: 40px;
}

#nmsl{
    background-color: white;
    width: 50px;
    height: 50px;
    border-radius: 50%; 
    background-color: #fff; 
    box-shadow: #fff 65px -15px 0 -5px,#fff 25px -25px, #fff 30px 10px,#fff 60px 15px 0 -10px,#fff 85px 5px 0 -5px; 
    position: absolute;
    top: 130px;
    left: 340px;
}
~~~

