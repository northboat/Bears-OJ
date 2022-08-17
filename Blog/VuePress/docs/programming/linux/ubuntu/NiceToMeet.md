# Ubuntu基础

> 基于Debian发行版

## 一、双系统

### 1、准备

下载镜像和U盘制作工具

分盘

使用Ultraiso制作U盘（写入方式：USB-HDD+）

### 2、安装

跳过wifi选项，在安装界面选择：something else

点击+号对ubuntu系统进行分区
- primary，Mount point选择 / 目录，该分区类似于win的c盘
- logical，use as选择swap area，为内存大小，以电脑为准
- logical，Mount point选择 /boot 目录，为启动盘，500MB即可；若启动方式为uefi，该处需要选择 /efi 目录
- logical，Mount point选择 /home，你的所有软件将下在该目录

选中boot区，continue

选择时区、设置密码，等待安装

### 3、驱动

linux对硬件的支持较差

检查是否有驱动

~~~bash
ifconfig -a
~~~

若出现 l0 字样，则为驱动缺失

首先，在终端输入如下内容，查看网卡型号

~~~bash
lspci
~~~

下载对应驱动，船船为 rtl8821ce ，解压驱动压缩文件，修改文件 Makefile

~~~
export TopDIR ?= /home/rtl8821ce
~~~

在 /home/rtl8821ce 目录下分别执行

~~~bash
make
sudo make install
sudo modprobe -a 8821ce
~~~

有时提示找不到 package，尝试使用以下命令更新软件源

~~~bash
sudo apt-get update
sudo apt-get upgrade
~~~

