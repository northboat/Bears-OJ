# 用docker运行mysql

## 1、拉取镜像

~~~bash
docker pull mysql:5.7
~~~

## 2、生成容器

~~~bash
docker run -it --name My-mysql -p 13306:3306 -e MYSQL_ROOT_PASSWORD=123456 84164b03fa2e（镜像id）
~~~

--name 自定义设置容器名称

-p 后为映射端口 从linux上的 13306 映射为容器中的 3306端口

-e 后设置 mysql 登录密码

## 3、连接容器

~~~bash
docker exec -it 064c6bea326d /bin/bash
~~~

## 4、登录mysql

~~~bash
mysql -h localhost -u root -p
~~~

输入密码，登录成功
