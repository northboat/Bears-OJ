# 用Hexo搭建个人博客

## 1、hexo环境搭配

### 1.1、登录http://nodejs.org下载nodejs





### 1.2、命令行操作

~~~
//查看版本号：检查node是否安装成功
C:\Windows\system32>node -v



//检查npm是否安装成功
C:\Windows\system32>npm -v



//用npm下载cnpm
    
C:\Windows\system32>npm install -g cnpm --registry=https://registry.npm.taobao.org



//检查cnpm是否安装成功
    
C:\Windows\system32>cnpm



      
//查看cnpm版本号
          
C:\Windows\system32>cnpm -v




//用cnpm下载hexo

C:\Windows\system32>cnpm install -g hexo -cli



 
//查看hexo版本号
    
C:\Windows\system32>hexo -v

   
   
//用cnpm在blog目录下安装git插件

cnpm install --save hexo-deployer-git
   
   
   
   
//配置_config.yml文件

用notepad++打开_config.yml文件修改deploy配置：

deploy:
  type: git
  repo: https://github.com/NorthBoat/NorthBoat.github.io
  branch: master
  
  
  
//更换主题

将主题下载在themes文件夹，修改_config.yml文件themes为新主题文件夹名字(无后缀)
~~~

## 2、hexo使用

[我的第一篇博客](NorthBoat.github.io)

~~~
初始化博客：hexo init
    
启动预览：hexo s(start)

创建博文：hexo n(new) "我的第一篇博客文章"
    
编写博文"我的第一篇博客文章.md"

清理文件夹：hexo clean
   
生成：hexo g(generate)
    
将本地博客布署在GitHub(配置好插件以及deploy后)：
hexo d(deploy)
    
更换主题：将主题下载到themes文件夹，修改_config.yml中themes配置
~~~

访问博客：`https://NorthBoat.github.io/`

注意在`2021.8.13`后`github`放弃了密码，转而使用密匙 `Token`，在进行部署时需要输入账号以及密匙，不然会报错

另外在配置文件`_config.yml`中如果仓库写错了，在部署时将报错10054

