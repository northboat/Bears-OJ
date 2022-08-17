---
title: Docker I
date: 2021-5-8
categories:
  - WebApp
tags:
  - Middleware
---

## 概述

### Why Happened

> 开发即运维
>

发布一个项目 ( jar + (Redis MySQL jdk ES) )需要项目和环境打包发布

- 环境配置是十分麻烦的，每一个机器都要部署环境，费时费力

传统：开发jar，运维来做

现在：开发打包部署上线，一套流程做完

Docker给以上的问题，提出了解决方案

- Docker的思想来自于集装箱


docker核心思想：**隔离**

- Docker 通过隔离机制，可以将服务器利用到极致


本质：所有的技术都是因为出现了一些问题，我们需要去解决，才去学习

### History

2010年，几个搞it的年轻人，就在美国成立了一家公司 **dotCloud** 做一些 pass 的云计算服务。Linux有关的容器技术。

他们将自己的技术（容器化技术）命名就是Docker。

Docker刚刚诞生的时候，没有引起行业注意。

dotCloud，要恰饭 ------> **开源**

开发源代码

- 2013年，将Docker开源

越来越多的人发现了Docker的优点！火了，几乎每个月都会更新一个版本

2014年4月9日，Docker1.0发布

入门 ------> 精通

Docker为什么这么火？十分的轻巧！

在容器技术出来之前，我们都是使用虚拟机技术

虚拟机：在window中装一个Vmware，通过软件虚拟出来一台或多台计算机，笨重

Docker容器技术，同样是一种虚拟机技术

去掉了虚拟硬件层：Hypeviscr & Guest OS——>Docker Engine

虚拟机面向硬件，Docker面向软件

### System

Client-Server（CS）结构

运行主机（Server）中运行着一个个容器（集装箱）以及后台守护进程（Daemon），保存着镜像，客户机（Client）通过下达命令（API），连接后台守护进程（Daemon），对容器进行操作

容器相当于一个简易版的Linux系统（集装箱），运行在宿主机上（鲸背），巨大的仓库像是大海一般支撑着鲸鱼

docker镜像：

联合文件系统（UnionFS）：一层一层叠加，拢和成一个文件，即镜像

bootfs（boot file system）& rootfs（root file system）

kernel（内核）：宿主机内核

容器层 ——> 镜像

分层 ——> 共享资源

## 搭建

在CentOS 7上配置Docker

1、安装之前现卸载系统上原有的Docker

~~~bash
yum remove docker docker-client  docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine
~~~

2、安装需要的安装包yum-utils

~~~java
[root@instance-h9cwbr8m ~]# yum install -y yum-utils

Loaded plugins: langpacks, versionlock
Excluding 1 update due to versionlock (use "yum versionlock status" to show it)
Package yum-utils-1.1.31-54.el7_8.noarch already installed and latest version
Nothing to do
[root@instance-h9cwbr8m ~]# 
~~~

此主机已安装最新的yum-utils

3、设置镜像仓库地址

添加docker默认的官方仓库地址

~~~java
yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo 
~~~

此地址为官方的仓库地址，在国内建议不要用

阿里云的镜像仓库地址

~~~java
yum-config-manager \
  --add-repo \
   http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
~~~

4、安装docker相关的引擎

先更新yum软件包索引

 	yum makecache fase 
- docker社区版、企业版。`ce`为社区版，`ee`为企业版。官方推荐使用`ce`版，默认安装最新的docker版本，也可以指定版本安装


~~~java
yum install docker-ce docker-ce-cli containerd.io
~~~

5、启动docker

~~~java
systemctl start docker
~~~

6、使用docker version 查看dockers是否启动

~~~java
docker version
~~~

原文链接：https://blog.csdn.net/qq_26400011/article/details/113856681

7、设置阿里云镜像加速

在阿里云官网获得加速地址：[容器镜像服务 (aliyun.com)](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)

配置/etc/docker/daemon.json文件

~~~java
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://wvbyitta.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
~~~

8、拉取`hello-world`镜像进行测试

~~~bash
sudo docker pull hello-world
sudo docker run hello-world
~~~

## 简单命令

- service docker restart：重启docker服务
- docker run + 镜像名m：用镜像m生成容器

- docker run -it：交互式运行

- docker run -p 8000:8080：设置对外开放端口和部署端口

- docker run -P：随机分配端口

- docker run -d：后台运行

- docker run --name：给容器命名
- exit：退出容器

- ctrl+q+p：将容器挂起

- docker start：开启容器

- docker restart：重启容器

- docker stop：停止容器

- docker kill：强制停止容器

- docker exec -it 容器id /bin/bash 交互式运行容器

### 查询

- docker version：查看docker版本
- docker info：查看docker信息

- docker --help：查询命令

- docker ps：正在运行的容器

- docker ps -l：最近运行的容器信息

- docker ps -a：所有运行过的容器信息

- docker ps -q：正在运行的容器ID

- docker images：查看镜像（-l -a -q 与5、6、7同理）

- docker search：在远端查询镜像

- docker inspect：查看容器具体信息

- docker top：查看容器内运行进程

### 删除

- docker rm：删除容器
- docker rm -f：强制删除容器

- docker rm -f $ (docker ps -qa)：删除所有容器

- docker rmi：删除镜像

- docker rmi -f：强制删除镜像

- docker rmi -f $ (docker images -qa)

### 构建并使用

- docker pull：从远端获取镜像
- docker commit -m="镜像名"   -a="作者名 "：用容器生成镜像

- docker build -f   /.../.../DockerFile (dockerfile路径) -t+镜像名：用dockerfile生成镜像

- docker run -it -v  /.../.../... (主机目录) :/...(容器目录)  + 容器c：在主机和容器c之间生成数据共享卷

- docker run it  --volumes-from+容器名/ID  -name+容器名  镜像名m：用m生成容器，用容器联通容器

## SSL 证书连接

> 原文链接：https://blog.csdn.net/oceanyang520/article/details/101563309

1、新建一个目录certs，在目录执行以下命令，输入两次密码，需要记住后面会用到

~~~bash
openssl genrsa -aes256 -out ca-key.pem 4096 
~~~

2、执行以下命令，输入密码，然后依次输入国家是 CN，省例如是Shanghai、市Shanghai、组织名称、组织单位、姓名或服务器名、邮件地址，都可以随意填写

~~~bash
openssl req -new -x509 -days 365 -key ca-key.pem -sha256 -out ca.pem
~~~

3、执行生成服务器端key证书文件

~~~bash
openssl genrsa -out server-key.pem 4096
~~~

4、ip需要换成自己服务器的外网ip地址，或者域名都可以

~~~bash
openssl req -subj "/CN=公网ip" -sha256 -new -key server-key.pem -out server.csr
~~~

5、配置白名单，多个用逗号隔开，例如： `IP:192.168.1.111,IP:0.0.0.0`，这里需要注意，虽然`0.0.0.0`可以匹配任意，但是仍然需要配置你的服务器外网ip，如果省略会造成错误，后面会讲到

~~~bash
echo subjectAltName = IP:**公网ip**,IP:0.0.0.0 >> extfile.cnf
~~~

6、把 extendedKeyUsage = serverAuth 键值设置到extfile.cnf文件里，限制扩展只能用在服务器认证

~~~bash
echo extendedKeyUsage = serverAuth >> extfile.cnf
~~~

7、执行以下命令，输入之前设置的密码，然后会生成签名的证书

~~~bash
openssl x509 -req -days 365 -sha256 -in server.csr -CA ca.pem -CAkey ca-key.pem \-CAcreateserial -out server-cert.pem -extfile extfile.cnf
~~~

8、生成例如idea等客户端需要用到的密钥文件

~~~bash
openssl genrsa -out key.pem 4096
~~~

9、生成客户端签名请求需要用到的临时文件

 ~~~bash
 openssl req -subj '/CN=client' -new -key key.pem -out client.csr
 ~~~

10、继续设置证书扩展属性

~~~bash
echo extendedKeyUsage = clientAuth >> extfile.cnf
~~~

11、输入之前的密码生成认证证书，生成正式签名证书

~~~bash
openssl x509 -req -days 365 -sha256 -in client.csr -CA ca.pem -CAkey ca-key.pem \-CAcreateserial -out cert.pem -extfile extfile.cnf 
~~~

12、删除生成的临时文件

~~~bash
rm -rf client.csr server.csr
~~~

13、修改证书为只读权限保证证书安全

~~~bash
chmod -v 0400 ca-key.pem key.pem server-key.pem
chmod -v 0444 ca.pem server-cert.pem cert.pem
~~~

14、复制服务端需要用到的证书到docker配置目录下便于识别使用：

~~~bash
cp server-cert.pem ca.pem server-key.pem /etc/docker/
~~~

15、修改docker配置

如果是离线手动安装的docker，通常情况下是这个文件：

~~~bash
vim /etc/systemd/system/docker.service
~~~

然后在`ExecStart=/usr/bin/dockerd`后面追加一个 \ ,然后黏贴证书的配置，大概如下

---

~~~
ExecStart=/usr/bin/dockerd \
~~~

加上以下部分

~~~
--tlsverify \ 
--tlscacert=/etc/docker/ca.pem \ 
--tlscert=/etc/docker/server-cert.pem \ 
--tlskey=/etc/docker/server-key.pem \ 
-H tcp://0.0.0.0:2375 \ 
-H unix:///var/run/docker.sock \  
~~~

---

但是，如果是自动使用yum安装的，服务文件地址可能是这个：

~~~bash
vim /lib/systemd/system/docker.service
~~~

然后加上加粗的部分即可

---

~~~
ExecStart=/usr/bin/dockerd-current \

--add-runtime docker-runc=/usr/libexec/docker/docker-runc-current \
--default-runtime=docker-runc \
--exec-opt native.cgroupdriver=systemd \
--userland-proxy-path=/usr/libexec/docker/docker-proxy-current \
--init-path=/usr/libexec/docker/docker-init-current \
--seccomp-profile=/etc/docker/seccomp.json \
$OPTIONS \
$DOCKER_STORAGE_OPTIONS \
$DOCKER_NETWORK_OPTIONS \
$ADD_REGISTRY \
$BLOCK_REGISTRY \
$INSECURE_REGISTRY \
$REGISTRIES
~~~

在首行后加上以下部分

~~~
--tlsverify \ 
--tlscacert=/etc/docker/ca.pem \ 
--tlscert=/etc/docker/server-cert.pem \ 
--tlskey=/etc/docker/server-key.pem \ 
-H tcp://0.0.0.0:2375 \ 
-H unix:///var/run/docker.sock \  
~~~

---

16、开放防火墙的2375的端口，若为阿里云服务器，需在网页上防火墙自定义添加规则

~~~bash
firewall-cmd --zone=public --add-port=2375/tcp --permanent

firewall-cmd --reload
~~~

17、重载服务并重启docker

~~~bash
systemctl daemon-reload && systemctl restart docker 
~~~

18、保存证书客户端文件到本地用于连接docker，使用winscp连接服务器复制即可 

19、测试一下证书是否配置成功，如果成功，会输出证书相关信息，如果有fail，请检查证书（在certs文件夹下运行）

~~~bash
docker --tlsverify --tlscacert=ca.pem --tlscert=cert.pem --tlskey=key.pem -H=16.21.2.234:2375 version 
~~~

20、配置idea

点击idea第一行菜单`run -> edit configurations`，然后点击+号，添加docker配置，选择`Dockerfile`，

然后选择server属性，弹出docker server配置，

engine api url：https://公网IP:2375

certificates folder：D:\buy\ca-docker

连接成功将显示seccussful

21、生成证书shell脚本

上述命令脚本（cert.sh）：

~~~sh
SERVER="172.16.75.201" 
PASSWORD="2084team-docker-tls" 
echo "shell script is doing"
echo "start file"
mkdir /mydata/docker-ca && cd /mydata/docker-ca
openssl genrsa -aes256 -passout pass:$PASSWORD -out ca-key.pem 4096
openssl req -new -x509 -passin "pass:$PASSWORD" -days 365 -key ca-key.pem -sha256 -subj "/CN=$SERVER" -out ca.pem
openssl genrsa -out server-key.pem 4096
openssl req -subj "/CN=$SERVER" -sha256 -new -key server-key.pem -out server.csr
sh -c  'echo "subjectAltName = IP:'$SERVER',IP:0.0.0.0" >> extfile.cnf'
sh -c  'echo "extendedKeyUsage = serverAuth" >> extfile.cnf'
openssl x509 -req -days 365 -sha256 -passin "pass:$PASSWORD" -in server.csr -CA ca.pem -CAkey ca-key.pem -CAcreateserial -out server-cert.pem -extfile extfile.cnf
openssl genrsa -out key.pem 4096
openssl req -subj "/CN=client" -new -key key.pem -out client.csr
echo extendedKeyUsage = clientAuth > extfile-client.cnf
openssl x509 -req -days 365 -sha256 -passin "pass:$PASSWORD" -in client.csr -CA ca.pem -CAkey ca-key.pem -CAcreateserial -out cert.pem -extfile extfile-client.cnf
rm -rf ca.srl server.csr client.csr extfile-client.cnf extfile.cnf
echo "end file"
echo "shell script is done"
~~~

