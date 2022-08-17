# Linux基础命令

> 基于Red Hat Linux

## 1、基本命令

~~~
pwd
    
ls

sudo/su

vi/vim:开始编辑（i） 退出（esc: q!(退出)/wq!(保存并退出)）
    
touch

cat

mkdir

rm -rf

mv A B

tar -zxvf -C

cd /

cd ..

//解压
tar xvf mysql-8.0.16-2.el7.x86_64.rpm-bundle.tar
    
systemctl start .service
    
systemctl restart .service
    
systemctl stop .service
    
//赋给权限
sudo chmod XXX /文件目录


~~~

## 2、连接本地机

**用WinSCP和PuTTY连接远程服务器：**

1、下载WinSCP和PuTTY，

2、在WinSCP用服务器外网ip、linux用户名（一般为root）以及登录密码远程连接服务器文件系统

3、在WinSCP中开启PuTTY，远程连接linux终端

## 3、自定义命令

Linux设置快捷键：

cd ~

vim .bashrc

source .bashrc

## 4、服务

在最新的linux中，启动一项服务时，系统会首先找 /etc/systemd/system/ 中的 .service文件，通过该文件连接到 /lib/systemd/system 中

即为

~~~bash
systemctl start docker
~~~

的一部分执行过程

## 5、更多

[Linux命令手册](https://www.linuxcool.com/)

[第一个Shell脚本](http://c.biancheng.net/view/735.html)
