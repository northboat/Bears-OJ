# Git部署

## Linux

### 下载

~~~bash
sudo apt-get install git
~~~

### 连接远程仓库

1、生成.ssh密匙

~~~shell
ssh-keygen -t rsa -C "youremail@example.com"
~~~

2、在GitHub —> setting —> Add SSH Key 粘贴 id_rsa.pub文件内容

## Windows

### 下载

[git下载地址](https://git-scm.com/downloads)

~~~bash
 //绑定github/gitee账号
 git config --global user.name "Your Name"
 git config --global user.email "email@example.com"
~~~

### 连接远程仓库

与linux同理，不过命令在 gitbash 中执行，若无法打开 id_rsa.pub，在命令行执行 type id_rsa.pub 命令获取密钥内容