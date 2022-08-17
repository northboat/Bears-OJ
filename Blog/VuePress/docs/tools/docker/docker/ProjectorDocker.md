# projector-docker远程开发

## 1、在服务器上拉取镜像

~~~bash
docker pull projectorimages/projector-idea-c
~~~

## 2、运行容器

~~~bash
docker run --rm -p 8887:8887 -it projectorimages/projector-idea-c
~~~

我尝试挂载一个目录，目录下放了jdk1.8以及一个项目文件，不幸的是，配置jdk1.8后，idea报错无法修改配置，在数据卷中创建项目同样不成功，报错“read only”，即使我设置了读写权限

## 3、通过ip:8887访问idea



## 4、将容器内数据拷贝

~~~bash
docker cp 容器名:目录 宿主机目录
~~~

