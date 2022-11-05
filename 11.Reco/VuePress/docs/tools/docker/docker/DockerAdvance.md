# Docker进阶命令

## 1、挂载数据卷

~~~bash
docker run -v /myDataVolume:/containerVolume 镜像名称
~~~

## 2、拷贝指定文件到指定目录

~~~
docker cp 容器名:/containerVolume/  /myDataVolume/
~~~

~~~bash
docker cp /myVolume/  容器名:/containerVolume/
~~~

## 3、监控容器资源消耗

~~~bash
docker stats --no-stream --format "{}" 容器名
~~~

--no-stream：不持续输出，即打印当前状态

--format：自定义输出格式（json）

## 4、重连容器

~~~bash
exec -it 容器名 /bin/bash
~~~

## 5、将Web项目挂在tomcat容器内

启动tomcat容器，将war包复制进容器 /usr/local/tomcat/webapps/ 目录即可

~~~bash
docker cp /java/NEUQHelper.war de9dc1076633:/usr/local/tomcat/webapps/
~~~

容器会自动解压war包，然后通过 ip:8080/NEUQHelper 即可访问项目

