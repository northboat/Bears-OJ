# 用docker运行tomcat

## 1、拉取镜像

~~~bash
docker pull tomcat
~~~

## 2、生成容器

~~~bash
docker run -it -d --name mycat -p 8080:8080 tomcat
~~~

--name 自定义设置容器名称

-d 后台启动

-p 设置端口（8080）

## 3、本地访问tomcat首页

~~~bash
localhost:8080
~~~

## 4、Issue

通常情况下，8080端口访问的首页找不到，即显示404，原因是tomcat容器中默认ROOT目录在webapps.dist文件夹中，而webapps目录为空，但配置文件又约定在 webapps/ROOT/ 中去找首页 index.html，于是报错

解决办法：

### 1、进入tomcat容器

~~~bash
docker exec -it mycat /bin/bash
~~~

### 2、将webapps.dist目录名修改为webapps

~~~bash
mv webapps webapps1
mv webapps.dist webapps
~~~

### 3、ctrl+p+q退出容器，重新访问8080端口
