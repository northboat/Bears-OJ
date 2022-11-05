---
title: Manjaro
date: 2022-4-12
tags:
  - Linux
---

> 基于Arch

## Linux 安装

### 双系统

> 大一懵懵懂懂捏

#### 准备

下载镜像和U盘制作工具

分盘

使用Ultraiso制作U盘（写入方式：`USB-HDD+`）

#### 安装

跳过wifi选项，在安装界面选择：something else

点击+号对ubuntu系统进行分区

- primary，挂在目录选择`/`，该分区类似于win的c盘
- logical，use as为`swap area`，为内存大小，以电脑为准
- logical，挂在目录选择`/boot`目录，为启动盘，`300MB`即可；若启动方式为`uefi`，该处需要选择`/efi`目录
- logical，Mount point选择 /home，你的所有软件将下在该目录

选中boot区，continue

选择时区、设置密码，等待安装

#### 驱动

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

### 移动系统

> [Manjaro安装教程](https://blog.csdn.net/qq_27525611/article/details/109269569)

#### 准备

下载镜像，需要去官网下载，速度并不慢

下载`Rufus`

制作启动盘

#### 安装

熟悉的安装以及分区，注意可以像那篇博文一样分的很细，也可以只分`/home、/boot、/、/swap`分区，其余会自动分好

#### Surface

下载驱动：[github/linux-surface](https://github.com/linux-surface/linux-surface/releases/tag/arch-5.10.10-1)

B乎教程：[在Surface上安装Manjaro系统](https://zhuanlan.zhihu.com/p/345302643)

关闭`bitlocker`，选择`usb`启动，扩展坞显得尤为重要

## Pacman & Yay

### 同步库及配置源

在软件与安装中勾选AUR源

更换`pacman`下载源

~~~bash
sudo pacman-mirrors -c China
~~~

同步数据库

~~~
sudo pacman -Syy
~~~

错误：无法提交处理 (无效或已损坏的软件包 (PGP 签名))：修改 /etc/pacman.conf，将 repo 中的 SigLevel = PackageRequired 修改为 SigLevel = Never

下载密匙

~~~bash
sudo pacman -S archlinuxcn-keyring
~~~

若失败在 /etc/pacman.conf 中手动添加

~~~bash
[archlinuxcn]
Server = https://mirrors.ustc.edu.cn/archlinuxcn/$arch
~~~

~~~bash
[core]
SigLevel = Never
Include = /etc/pacman.d/mirrorlist

[extra]
SigLevel = Never
Include = /etc/pacman.d/mirrorlist

[community]
SigLevel = Never
Include = /etc/pacman.d/mirrorlist

# If you want to run 32 bit applications on your x86_64 system,
# enable the multilib repositories as required here.

[multilib]
SigLevel = Never
Include = /etc/pacman.d/mirrorlist
~~~

下载`yay`

~~~bash
pacman -Sy yay
~~~

yay的配置文件在`~/.config/yay/config.json`

这里不要换清华源，很多资源都已失效，不如不换

### 常用软件下载

下载vim

~~~
yay -S vim
~~~

vim 缺少 libperl.so 问题

~~~bash
sudo pacman -S perl
~~~

中文输入法

~~~
sudo pacman -S fcitx-im
pacman -S fcitx-configtool
pacman -S fcitx-googlepinyin
~~~

添加配置

~~~
vim ~/.xprofile

export GTK_IM_MODULE=fcitx
export QT_IM_MODULE=fcitx
export XMODIFIERS="@im=fcitx"
~~~

保存退出重启即可

将主目录下文件夹名称改为英文

~~~
sudo pacman -S xdg-user-dirs-gtk
export LANG=en_US
xdg-user-dirs-gtk-update
~~~

此时将文件夹全改为英文，再将默认语言改回中文

~~~
export LANG=zh_CN.UTF-8
~~~

下载vscode

~~~bash
yay -S visual-studio-code-bin
~~~

下载网易云音乐

~~~
yay -S netease-cloud-music
~~~

下载edge

~~~
yay -S microsoft-edge-dev-bin
~~~

下载QQ/Wechat

~~~
yay -S deepin-wine-qq
yay -S com.qq.weixin.deepin
~~~

git初始化

~~~
git config --global user.name "name" 设置 git 全局用户名
git config --global user.email "emai@qq.com" 设置 git 全局邮箱
ssh-keygen -t rsa -C "email@qq.com" 生成秘钥
~~~

用cat命令或去id.rsa.pub的内容并复制添加到github的ssh-key

下载钉钉

~~~
yay -S dingtalk-electron
~~~

- 我真的是服了，linux的钉钉没有直播功能

下载截屏工具并设置快捷键

~~~
yay -S deepin-screenshot
~~~

- 在键盘设置中有图形界面以供设置快捷键

下载uget

~~~
yay -S uget
~~~

下载pdf阅读器

~~~
yay -S foxitreader
~~~

下载ifconfig

~~~bash
yay -S net-tools
~~~

临时修改ip，改了之后断网了草，要加一个网关

~~~bash
ifconfig [网卡名] [要改的ip]
~~~

### 博客环境搭建



~~~
yay -S nodejs
yay -S npm

npm config set registry https://registry.npm.taobao.org
npm config get registry

sudo npm install cnpm -g

sudo npm install vue -g
sudo cnpm install vuepress -g
sudo npm install @vuepress-reco/theme-cli -g
~~~

## 杂项

### 调整时间

[Manjaro配置准确时间](https://www.jianshu.com/p/92a2de6d9862)

~~~bash
timedatectl set-local-rtc 1 --adjust-system-clock
timedatectl set-ntp 0
~~~

1.显示系统的当前时间和日期

~~~bash
timedatectl status
~~~

- 结果中RTC time就是硬件时钟的时间

2.Linux系统上的time总是通过系统的timezone设置的，查看当前时区：

~~~bash
timedatectl | grep Time
~~~

3.查看所有可用的时区：

~~~bash
timedatectl list-timezones
~~~

4.根据地理位置找到本地的时区：

~~~bash
timedatectl list-timezones | egrep -o “Asia/B.*”
timedatectl list-timezones | egrep -o “Europe/L.*”
timedatectl list-timezones | egrep -o “America/N.*”
~~~

5.在Linux中设置本地时区，使用set-timezone开关：

~~~bash
timedatectl set-timezone “Asia/shagnhai”
~~~

### 桌面

用`*.desktop`创建桌面快捷方式

~~~
#godotengine.desktop
[Desktop Entry]
Name=godot engine
GenericName=Game Engine
Exec=~/tool/godotengine/godotengine
Icon=godot.png
Terminal=false
Type=Application
StartupNotify=false
Categories=Development;
~~~

### Timeshift 快照

使用 timeshift 创建 rsync 快照，将在第一次储存的基础上不断更新，一个快照大概7-9G

### Shell 脚本

~~~shell
npm run build
cd public
git init
git add .
git commit -m "reco"
git push -f git@github.com:NorthBoat/NorthBoat.github.io.git master
~~~

### 添加环境变量

在`~/.bashrc`中添加

~~~bash
export PATH=/opt/anaconda/bin:$PATH
~~~

### 更改多余启动项

查看启动项

~~~
sudo efibootmgr //显示efi的启动项
~~~

删除多余启动项

~~~
efibootmgr -b 000C -B
~~~

其中 000C是要删除的引导项编号，通过 efibootmgr命令可以直接查看

- 没有屁用，还得是格式化引导分区

发生了其他的问题，就是说我把引导分区格了之后，uuid变了，从manjaro进不了windows，这个时候要修改`/boot/grub/grub.cfg`文件的windows启动设置

将`set=root uuid=`后的内容改成新的uuid保存退出即可

查看分区信息（包括但不限于uuid）

~~~
blkid
~~~

- 改得头疼

有智能的方法

~~~
sudo update-grub
~~~

让linux系统自动生成合适的grub.cfg文件
