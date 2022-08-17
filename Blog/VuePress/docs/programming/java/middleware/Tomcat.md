## Tomcat

> Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用服务器
>
> Apache为HTML页面服务，而Tomcat实际上运行JSP 页面和Servlet。另外，Tomcat和[IIS](https://baike.baidu.com/item/IIS)等Web服务器一样，具有处理HTML页面的功能，另外它还是一个Servlet和JSP容器，独立的Servlet容器是Tomcat的默认模式。不过，Tomcat处理静态HTML的能力不如Apache服务器

### 1、安装tomcat

tomcat官网：download

### 2、tomcat启动和配置

bin：存放着运行脚本 ——> start.sh / shutdown.sh

可能遇到的问题：

- java环境遍历没有配置

- 闪退问题：需要配置兼容性

- 乱码问题：配置文件中设置 startup.bat（尽量不要改动）

webapps：存放着web资源 ——> /ROOT/index.jsp即为8080主页，可在其中修改网页内容

~~~html
<!--如下为导航栏设置，修改Home为NorthBoat-->
<div id="navigation" class="curved container">
                <span id="nav-home"><a href="${tomcatUrl}">NorthBoat</a></span>
                <span id="nav-hosts"><a href="${tomcatDocUrl}">Documentation</a></span>
                <span id="nav-config"><a href="${tomcatDocUrl}config/">Configuration</a></span>
                <span id="nav-examples"><a href="${tomcatExamplesUrl}">Examples</a></span>
                <span id="nav-wiki"><a href="https://wiki.apache.org/tomcat/FrontPage">Wiki</a></span>
                <span id="nav-lists"><a href="${tomcatUrl}lists.html">Mailing Lists</a></span>
                <span id="nav-help"><a href="${tomcatUrl}findhelp.html">Find Help</a></span>
                <br class="separator" />
            </div>
~~~

conf（config文件夹）：server.xml（服务器配置）

通过修改 server.xml 文件中 Host name 和 C:/Windows/system32/drivers/etc/hosts 文件修改主页域名

~~~
server.xml:
<Host name="www.NorthBoat.com"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">
hosts:
# localhost name resolution is handled within DNS itself.
#	127.0.0.1       localhost
#	::1             localhost
	127.0.0.1       www.NorthBoat.com
~~~

- tomcat默认端口号：8080
- http默认端口号：80
- https默认端口号：443
- mysql默认端口号：3306

**网站时如何进行访问的**

1、输入一个域名，回车

2、检查本机的 C:\Windows、System32\drivers\etc\hosts

- 有：直接返回对应的ip地址，即这个地址中有我们需要访问的web程序
- 没有：在DNS（管理全世界的域名）上寻找域名，找到的话就返回对应ip地址（即网页），找不到则返回找不到

**配置环境变量**

### 3、发布一个web网页

- 将自己写的网站，放到服务器（Tomcat）中只指定web应用的文件夹（webapps）下，就可以访问了

网站应有的结构

~~~java
--webapps：Tomcat服务器的web目录
    -ROOT
    -NorthBoat：网站的目录名
    	-WEB-INF
    		-classes：java程序
    		-lib：web应用所依赖的jar包
    		-web.xml
    	-index.html：默认的首页
    	-static
    		-css
    			-style.css
    		-js
    		-img
    	-...
~~~

## 
