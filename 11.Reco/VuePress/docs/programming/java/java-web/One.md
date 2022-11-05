# JavaWeb基础学习第一周

## 1、Web基本概念

### 1.1、前言

web：网页

- 静态网页：内容不发生变化，如博客
- 动态网页：内容时刻更新：如淘宝

在java中，动态web资源开发的基础统称为javaweb

### 1.2、web应用程序

由dos访问的程序：CS结构

web应用程序：可以提供浏览器访问的程序

- a.html、b.html.......多个web资源，这些web资源都可以被外界访问，对外界提供服务
-  我们能访问到的任何一个页面或者资源，都存在于这个世界的某一个角落的计算机上
-  通过URL访问到该计算机资源
-  这个统一的web资源会被放在同一个文件夹下，web应用程序 ——> Tomcat：服务器
- 一个web应用又多部分组成（静态web、动态web）：html，css，js；jsp、servlet；java程序；jar包；配置文件（Properties）

web应用程序编写完毕后，若想提供给外界访问，需要一个服务器来统一管理

### 1.3、静态web

1、htm、html都是这些网页的后缀，如果服务器上一直存在这些东西，我们就可以直接进行读取、通络

一次请求（Request）：

客户端 —— Network ——> 服务器

一次相应（Response）：

服务器 —— Network ——> 客户端

2、静态web的缺点：

- web页面无法动态更新，所有用户看到的都是同一个页面 ——> 轮播图、javaScript、VBScript
- 他无法和数据库交互（数据无法持久化，用户无法交互）

### 1.4、动态web

页面会动态展示：“Web的页面展示的效果因人而异”

在返回相应时经过动态资源：更新资源

缺点：

- 加入服务器的动态web资源出现错误，我们需要重新编写我们的后台程序 ——> 停机维护

优点：

- web页面可以动态更新
- 它可以与数据库交互（JDBC）：通过java程序去连接数据库 ——>数据持久化：注册、商品信息、用户信息......

## 2、Web服务器

### 2.1、技术讲解

ASP

- 微软：国内最早流行的就是ASP

- 在HTML中潜入了VB的脚本，ASP + C

- 在ASP开发中，基本一个页面都要几千行的业务代码

- ~~~html
  <h1>
      <h1>
          <%
          	System.out.println("wdnmd");
          %>
      </h1>
  </h1>
  ~~~

PHP

- PHP开发速度很快，功能很强大，跨平台，代码很简单（现在大部分网站都用php实现）
- 无法承载打访问量的情况（局限性）：限制在中量型网站

B/S：浏览器和服务器

C/S：客户端和服务器

JSP/Servlet

- sun公司（被甲骨文收购了）主推的B/S架构
- 基于java语言（所有的大公司或一些开源的组件都是用java写的）
- 可以承载三高问题带来的影响：高并发、高可用、高性能
- 语法像ASP，ASP ——> JSP，加强市场强度

### 2.2、web服务器

服务器时一种被动操作，用来处理用户的一些请求和给用户一些相应信息

**IIS**

微软的：ASP...Windows中自带的

**Tomcat**

~~~
Tomcat是Apache 软件基金会（Apache Software Foundation）的Jakarta 项目中的一个核心项目，由[Apache]、Sun 和其他一些公司及个人共同开发而成。由于有了Sun 的参与和支持，最新的Servlet 和JSP 规范总是能在Tomcat 中得到体现，Tomcat 5支持最新的Servlet 2.4 和JSP 2.0 规范。因为Tomcat 技术先进、性能稳定，而且免费，因而深受Java 爱好者的喜爱并得到了部分软件开发商的认可，成为目前比较流行的Web 应用服务器。

Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用[服务器]，在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试JSP 程序的首选。对于一个初学者来说，可以这样认为，当在一台机器上配置好Apache 服务器，可利用它响应[HTML]（[标准通用标记语言]下的一个应用）页面的访问请求。实际上Tomcat是Apache 服务器的扩展，但运行时它是独立运行的，所以当你运行tomcat 时，它实际上作为一个与Apache 独立的进程单独运行的。

诀窍是，当配置正确时，Apache 为HTML页面服务，而Tomcat 实际上运行JSP 页面和Servlet。另外，Tomcat和[IIS]等Web服务器一样，具有处理HTML页面的功能，另外它还是一个Servlet和JSP容器，独立的Servlet容器是Tomcat的默认模式。不过，Tomcat处理静态[HTML]的能力不如Apache服务器。目前Tomcat最新版本为10.0.5。
~~~

工作3-5年之后，可以尝试手写Tomcat

下载tomcat：

- 安装 / 解压
- 了解配置文件及目录结构
- 这个东西的作用

## 3、Tomcat

### 3.1、安装tomcat

tomcat官网：download

### 3.2、tomcat启动和配置

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

### 3.3、发布一个web网页

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

## 4、Http

### 4.1、什么是http

超文本传输协议（Hypertext Transfer Protocol，HTTP）是一个简单的请求-响应协议，它通常运行在 TCP 之上

- 文本：html、字符串......
- 超文本：图片、音乐、视频、定位、地图......
- http默认端口：80
- https（s：安全的）：443

### 4.2、两个时代

1、http1.0

- HTTP/1.0：客户端可以与web服务器连接后，只能获得一个web资源，获得后断开连接

2、http2.0

- HTTP/1.1：客户端与web服务器连接后，可以获得多个web资源

### 4.3、Http请求

客户端 —— 发请求（request） ——> 服务器

以百度为例

通用

~~~java
//刷新百度页面时抓包
//f12 ——> network

Request URL: https://www.baidu.com/	 请求地址
Request Method: GET		请求方法（get / post）
Status Code: 200 OK		状态码：200
Remote Address: 110.242.68.3:443	ip地址
Referrer Policy: strict-origin-when-cross-origin
~~~

#### 1、请求行

- 请求行中的请求方式：GET
- 请求方式：GET、post、head、delete、put、tract...
  - get：请求能携带的参数较少，大小有限制，会在浏览器URL栏中显示数据，不安全
  - post：参数、大小没有限制，不会在URL中显示，但不高效

#### 2、消息头

~~~java
Accept: text/html：接受文件类型
Accept-Encoding: gzip, deflate, br：编码方式
Accept-Language: zh-CN,zh;q=0.9,en;q=0.8：语言
Cache-Control: max-age=0：缓存控制
Connection: keep-alive：告诉浏览器，请求完成时断开开始保持连接
Refresh：告诉客户端多久刷新一次
~~~



### 4.4、Http响应

服务器 —— 响应 ——> 客户端

响应：

~~~java
Cache-Control: private		缓存控制
Connection: keep-alive		连接：保持连接
Content-Encoding: gzip		编码方式
Content-Type: text/html;charset=utf-8		文本格式
Date: Sat, 22 May 2021 09:21:43 GMT		响应时间
Strict-Transport-Security: max-age=172800		一个保证安全的脚本
~~~

#### 1、响应体

~~~java
Accept: text/html：接受文件类型
Accept-Encoding: gzip, deflate, br：编码方式
Accept-Language: zh-CN,zh;q=0.9,en;q=0.8：语言
Cache-Control: max-age=0：缓存控制
Connection: keep-alive：告诉浏览器，请求完成时断开开始保持连接
Refresh：告诉客户端多久刷新一次
~~~

#### 2、响应状态码

200：请求响应成功

3xx：请求重定向

4xx：找不到资源（404）

5xx：服务器代码错误（500、502：网关错误）



**当你的浏览器中地址栏输入地址并回车的一瞬间到页面能够展示回来，经历了什么？**



## 5、Maven



### 5.1、Why Maven？

1、在javaweb开发中，需要使用大量的jar包，手动导入很麻烦

2、maven正是一个自动化导入jar包的工具



### 5.2、Maven项目架构管理工具

目前用来方便导入jar包

核心思想：**约定大于配置**

Maven会规定好你该如何去编写我们的java代码



### 5.3、下载和安装

去官网下载压缩包，解压即可

### 5.4、配置环境变量

配置如下配置

- M2_HOME
- MAVEN_HOME
- 在系统的path中配置 %MAVEN_HOME%\bin

测试Maven是否安装成功

~~~bash
C:\Users\NorthBoat>mvn --version
~~~

### 5.5、阿里云镜像

加速我们的下载

~~~xml
<mirror>
    <id>nexus-aliyun</id>
    <mirrorOf>*,!jeecg,!jeecg-snapshots</mirrorOf>
    <name>Nexus aliyun</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public</url> 
</mirror>
~~~

将 conf 文件夹中 settings.xml 文件镜像仓库改为上述mirror

### 5.6、建立本地仓库

本地仓库 <——> 远程仓库

建立一个本地仓库：

在bin同级目录下创建文件夹repo

然后在 setting.xml 中，修改repo路径为自定义路径

~~~xml
<localRepository>D:\Maven\apache-maven-3.8.1\maven-repo</localRepository>
~~~



### 5.7、在idea中使用maven

new project ——> maven项目 ——> 勾选模板、勾选maven-javaweb-app ——> 选仓库、配置文件

可能会出现报错：No archetype found in remote catalog. Defaulting to internal catalo

此时在Maven创建项目时设置属性 archetypeCatalog = internal 即可

标准的maven项目目录结构

~~~java
-src
    -main
    	-java
    	-rources
    	-webapp
    		-WEB-INF
    			-web.xml
    		-index.jsp
~~~

设置文件夹属性：

1、右键文件夹：mark directory  as ...

2、Project Structure ——> Modules：设置文件夹类型

Maven功能栏（Plugins可删除）：

### 5.8、在idea中配置tomcat

先在project structure中添加Artifacts：webapp application archive，重命名war包，生成在target目录

点击该处处，配置tomcat

注意：

1、tomcat 10 idea将无法识别其 lib 库

2、报错 No artifacts marked for deployment，在deployment中加一个 javaweb-maven:war 即可

- 我们访问一个网站，必须要一个默认文件夹接管tomcat的webapps

3、虚拟路径映射

4、提示信息乱码

~~~java
淇℃伅 [main] org.apache.catalina.startup.VersionLoggerListener.log Server.鏈嶅姟鍣ㄧ増鏈�:
~~~

到tomcat/conf/目录下 

修改logging.properties 找到 java.util.logging.ConsoleHandler.encoding = utf-8这行 更改为 java.util.logging.ConsoleHandler.encoding = GBK，保存退出即可

### 5.9、pom.xml文件

pom.xml 是maven的核心配置文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <!--这是初始化配置项目时的GVA:group version artifactId-->
  <groupId>org.example</groupId>
  <artifactId>javaweb-maven</artifactId>
  <version>1.0-SNAPSHOT</version>
  <!--打包方式：war（javaWeb应用）-->
  <packaging>war</packaging>

  <!--配置-->
  <properties>
    <!--项目默认编码-->
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <!--编译版本-->
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
  </properties>

  <!--项目依赖：具体依赖的jar包依赖文件-->
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <!--项目构建用的东西-->
  <build>
    <finalName>javaweb-maven</finalName>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-war-plugin</artifactId>
          <version>3.2.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
~~~

maven的高级之处在于，当你导入一个jar包时，它将自动帮你导入该jar包所依赖的其他jar包

当你要导入jar包时，只需要百度maven仓库，选择所需jar包的相应版本中找到其maven配置，复制粘贴进dependencies中即可

~~~xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc
         百度搜索maven仓库，在所需的jar包响应版本中找到其maven依赖，复制粘贴即可-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
~~~

有的依赖中有作用域的限定，目前阶段可以删掉

~~~xml
<scope>provided</scope>
~~~

有时maven自动导入jar包会失败，我们需要手动去maven仓库下载jar包拷到指定目录下

**之后可能会遇到的问题：**

由于maven的约定大于配置，我们之后在导出资源时会碰到问题

我们在build中配置resources可解决该问题

~~~xml
<build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
</build>
~~~



## 6、servlet

### 6.1、why servlet

1、servlet是sun公司用于开发动态web的一门技术

2、sun公司在这些api中提供了一个接口：Servlet，要想开发一个servlet程序只需实现两步

- 编写一个java类实现Servlet接口
- 把开发好的java类部署到web上

**我们把实现了Servlet接口的java程序叫做 Servlet**



### 6.2、Hello Servlet

##### 1、构建工程

构建一个干净的maven项目，删掉里面的src目录，便于在这个项目里建立Moudel，将该工程作为Maven的主工程

在maven pom文件中添加servlet依赖

~~~xml
<dependencies>
        <!--Servlet依赖-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>
    </dependencies>
~~~

##### 2、Maven父子工程：

在主工程下new module，父为干净的maven项目，子为mavenweb项目

在用模板构建maven-webapp时可能报错 No archetype found in remote catalog. Defaulting to internal catalog，即在目录中未找到模板，解决办法为

**在创建maven项目时，设置属性 archetypeCatalog = internal**

- 在主工程中可以建很多的module（模板），在主工程的pom.xml中会有

- ~~~xml
  <modules>
          <module>servlet-01</module>
  </modules>
  ~~~

- 而子工程的pom.xml中会有（好像也可以没有）

- ~~~xml
  <parent>
      <groupId>org.example</groupId>
      <artifactId>javaweb-servlet</artifactId>
      <version>1.0-SNAPSHOT</version>
  </parent>
  ~~~

- 父工程和子工程的关系与父类和子类相似

##### 3、maven环境优化

- 修改web.xml为最新的
- 将maven的结构搭建完整

##### 4、编写一个Servlet程序

- 编写一个普通类
- 实现Servlet接口：Servlet接口 ——> GenericServlet抽象类 ——> HttpServlet抽象类 ——> 继承HttpServlet ——> 实现Servlet接口

继承HttpServlet，重写doGet方法（doGet是当请求(表单提交方式)为GET时，service方法中调用的方法）

~~~java
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    //由于 GET 和 POST 只是请求实现的不同方式，可以相互调用，因为业务逻辑相同
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();  //响应流
        writer.println("Hello Servlet!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
~~~



##### 5、编写Servlet映射

why refect? 我们写的java程序要通过浏览器访问，而浏览器需要连接web服务器，所以我们需要在web服务中注册我们写的servlet，还需要给他一个浏览器能访问的路径

在web.xml中配置servlet映射

~~~xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>

  <!--配置servlet映射-->
  <servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>com.servlet.HelloServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello</url-pattern>
  </servlet-mapping>

</web-app>
~~~

servlet-name：命名该servlet

servlet-class：你所写的servlet的包路径

servlet-name：链接你所命名的servlet（servlet-mapping中）

url-pattern：映射在localhost上的路径，如上述代码将执行在 localhost:8080/s1/hello（s1为tomcat的映射路径）（注意一定要加 / ，否则会报错子级失败啥啥）

##### 6、配置tomcatsdfsda 

点右上角锤锤旁边的小格格，点击+号配置tomcat，注意配置虚拟映射路径，即配置 deployment 中 application context：同样需要反斜杠，如： /s1

##### 7、启动测试



##### 8、servlet族系部分源码

**Servlet（interface）源码：**

~~~java
public interface Servlet {
    //初始化
    void init(ServletConfig var1) throws ServletException;

    //获取servlet配置
    ServletConfig getServletConfig();

    //请求和响应
    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    //获取servlet信息
    String getServletInfo();

    //销毁servlet
    void destroy();
}
~~~

**GenericServlet（abstract）源码：**

~~~java
public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.LocalStrings");
    private transient ServletConfig config;
	
    //这一层尚未处理的方法
    public GenericServlet() {
    }

    public void destroy() {
    }

    //重中之重
    public abstract void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;
    
    //重写了一个带配置参数的初始化函数
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    public void init() throws ServletException {
    }
    
    
    //下述一系列方法实现了servlet信息的获取
    public String getServletInfo() {
        return "";
    }
    
    public ServletConfig getServletConfig() {
        return this.config;
    }
    
    public String getInitParameter(String name) {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getInitParameter(name);
        }
    }

    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getInitParameterNames();
        }
    }

    public ServletContext getServletContext() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getServletContext();
        }
    }

    public void log(String msg) {
        this.getServletContext().log(this.getServletName() + ": " + msg);
    }

    public void log(String message, Throwable t) {
        this.getServletContext().log(this.getServletName() + ": " + message, t);
    }
    
    public String getServletName() {
        ServletConfig sc = this.getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        } else {
            return sc.getServletName();
        }
    }
}
~~~

**HttpServlet（abstract）service方法源码：**

~~~java
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        long lastModified;
    
    	//如果html表单提交方法（method）为GET
        if (method.equals("GET")) {
            lastModified = this.getLastModified(req);
            if (lastModified == -1L) {
                this.doGet(req, resp);
            } else {
                long ifModifiedSince = req.getDateHeader("If-Modified-Since");
                if (ifModifiedSince < lastModified) {
                    this.maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(304);
                }
            }
        } else if (method.equals("HEAD")) {		//如果提交方式为HEAD
            lastModified = this.getLastModified(req);
            this.maybeSetLastModified(resp, lastModified);
            this.doHead(req, resp);
        } else if (method.equals("POST")) {		//如果提交方式为POST
            this.doPost(req, resp);
        } else if (method.equals("PUT")) {		//如果提交方式为PUT
            this.doPut(req, resp);
        } else if (method.equals("DELETE")) {		//如果提交方式为DELETE
            this.doDelete(req, resp);
        } else if (method.equals("OPTIONS")) {		//如果提交方式为OPTIONS
            this.doOptions(req, resp);
        } else if (method.equals("TRACE")) {		//如果提交方式为TRACE
            this.doTrace(req, resp);
        } else {
            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[]{method};
            errMsg = MessageFormat.format(errMsg, errArgs);
            resp.sendError(501, errMsg);
        }

    }
~~~















