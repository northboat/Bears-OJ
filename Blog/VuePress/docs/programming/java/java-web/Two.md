# JavaWeb基础学习第二周

## 1、Servlet

### 1.1、Servlet架构

BS架构

浏览器 <——> 服务器 <——> servlet：service（resp, resq）

### 1.2、Mapping

#### 1、不同的映射情况

1、一个servlet指定一个映射

2、一个servlet可以指定多个映射路径

3、指定通用映射

~~~xml
<url-pattern>/*</url-pattern>

<!--效果为，你在网页上根路径中瞎几把乱输入一个未经映射的地址，页面将跳转到 /* 所映射的页面-->
~~~

4、自定义后缀配置请求映射

~~~xml
<url-pattern>*.NorthBoat</url-pattern>
    
<!--这样的效果就是，你在网页上打出任何 名字.NorthBoat 都会跳转到你在这儿规定的页面，只要以.NorthBoat结尾-->
~~~

**5、利用通配符设置404界面：**

由于通配符的优先级小于已指定的mapping映射

~~~java
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<h1>404</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
~~~

~~~xml
<!--404-->
<servlet>
    <servlet-name>error</servlet-name>
    <servlet-class>Servlet.ErrorServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>error</servlet-name>
    <url-pattern>/*</url-pattern>
</servlet-mapping>
~~~

#### 2、wab-app编写顺序报错

报错 The content of element type "web-app" must match "(icon?,display-name?,description?

将web.xml文件前五行换为

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee 
	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
~~~



#### 3、设置网页编码格式

设置响应（response）的ContentType、CharacterEncoding

在f12网络消息头中可以看到ContentType元素，其中用分号隔开了该网页的ContentType和ChacaterEncoding

在doGet方法中设置页面文字格式

~~~java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
    }
~~~

resp.setCharacterEncoding(String charset) 将把 charset 在已有选项中匹配对应字符格式，若未匹配到将标红报错

### 1.3、ServletContext

web在启动时，它会为每个web程序都创建一个对应的ServletContext对象，它代表了当前的web应用，凌驾于Servlet之上

#### 1、数据共享

ServletContext作为一个数据互通的容器，在这个Servlet中保存的数据，可以在另外一个Servlet中获取

共享三部曲：

1、创建一个放置数据的类

~~~java
public class Study extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //this.getInitParameterNames() 返回初始化参数
        //this.getServletConfig()   获得Servlet配置
        ServletContext context = this.getServletContext();   //servlet上下文容器
        String name = "NorthBoat";  //数据
        //将一个数据保存在ServletContext中，setAttribute：设置属性
        context.setAttribute("username", name);
    }
}
~~~

2、需要一个读取数据的类

~~~java
public class Hello extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = (String) this.getServletContext().getAttribute("username");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().println("<h1>你好啊，" + name + "</h1>");   //相应流
    }
}
~~~

3、配置web.xml

~~~xml
  <!--读取数据的Servlet-->
  <servlet>
    <servlet-name>hello</servlet-name>
    <servlet-class>Servlet.Hello</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>hello</servlet-name>
    <url-pattern>/hello</url-pattern>
  </servlet-mapping>

  <!--存储数据的Servlet-->
  <servlet>
    <servlet-name>get</servlet-name>
    <servlet-class>Servlet.Study</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>get</servlet-name>
    <url-pattern>/get</url-pattern>
  </servlet-mapping>
~~~

在运行时，直接打开  /hello 将显示 ”你好啊，null“

而先打开 /get 再打开 /hello 将显示大写的 “你好啊，NorthBoat”

即先经过 get 将数据储存到了 ServletContext 中，所以 hello 能够从 ServletContext 中取到 username

#### 2、获取初始化参数

在web.xml中配置context初始化参数：如 stu:student，tea:teacher，adm:admin，在网页中获取数据组成 account

~~~xml
<context-param>
    <param-name>mysql</param-name>
    <param-value>jdbc:mysql://localhost:3306</param-value>
  </context-param>
~~~

获取context参数，getInitParameter()，以字符串返回

~~~java
public class Hello extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        //获取初始化参数
        String param = context.getInitParameter("mysql");
        //打印初始化参数
        resp.getWriter().println("初始化参数：" + param);
    }
}
~~~

#### 3、请求转发

将Study映射页面的请求转发到 /hello，url不发生改变，但页面内容显示 /hello 的内容

~~~java
public class Study extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();   //servlet上下文容器
        String name = "NorthBoat";  //数据

        //将一个数据保存在ServletContext中，setAttribute：设置属性
        context.setAttribute("username", name);

        //请求转发到 /hello
        context.getRequestDispatcher("/hello").forward(req, resp);
    }
}
~~~

/hello 中能读到 username，即在请求转发前已把username存到了context中，请求转发后，将不再执行 Study 中 doGet 的后续内容

~~~java
public class Hello extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        String name = (String)context.getAttribute("username");
        String param = context.getInitParameter("mysql");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        resp.getWriter().println("<h1>你好啊，" + name + "</h1>");
        resp.getWriter().println("<h1>初始化参数：" + param + "</h1>");
    }
}
~~~

#### 4、读取资源文件

~~~java
public class Hello extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取静态资源
        Properties prop = new Properties();
        //资源路径，以流的形式加载
        InputStream is = context.getResourceAsStream("/WEB-INF/classes/database.properties");
        prop.load(is);
        String table = prop.getProperty("student");
        resp.getWriter().println("<h1>表：" + table + "</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
~~~

database.properties

~~~properties
student=student
teacher=teacher
admin=admin
~~~



### 1.4、Response

响应：web服务器接收到客户端的http请求，针对这个请求，分别创建请求的HttpServletRequest对象，代表响应的一个HttpServletResponse:

- 如果要获取客户端请求过来的参数，找Request
- 如果要给客户端响应一些信息，找Response

##### 1、负责向浏览器发送数据的方法

~~~java
ServletOutputStream getOutputStream() throws IOException;
PrintWriter getWriter() throws IOException;
~~~

##### 2、负责向浏览器发送响应头的方法

~~~java
//from ServletResponse
void setContentType(String type);
void setCharacterEncoding(String encoding);

//from HttpServletResponse
void setDateHeader(String var1, long var2);
void addDateHeader(String var1, long var2);
void setHeader(String var1, String var2);
void addHeader(String var1, String var2);
void setIntHeader(String var1, int var2);
void addIntHeader(String var1, int var2););
~~~

##### 3、响应的状态码

~~~java
//运行良好
int SC_OK = 200;
//浏览器错误
int SC_FORBIDDEN = 403;
int SC_NOT_FOUND = 404;
int SC_METHOD_NOT_ALLOWED = 405;
//服务器错误
int SC_INTERNAL_SERVER_ERROR = 500;
int SC_NOT_IMPLEMENTED = 501;
int SC_BAD_GATEWAY = 502;
int SC_SERVICE_UNAVAILABLE = 503;
int SC_GATEWAY_TIMEOUT = 504;
int SC_HTTP_VERSION_NOT_SUPPORTED = 505;
                                                                                       
~~~

##### 4、应用

###### 1、向浏览器输出消息

###### 2、下载文件、上传文件

- 下载文件路径
- 文件名
- 设置浏览器支持下载我们所需的东西
- 获取下载文件的输入流
- 创建缓冲区
- 获取OutputStream对象
- 向FileOutputStream流写入buffer缓冲区
- 将缓存区中的数据输出到客户端

###### 3、以图片形式生成验证码

生成随机“数”，当生成的数不足7位时，用0填充（老程序员的常用套路）

编辑图片并映射到网页上

###### 4、重定向

~~~java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
    resp.sentRedirect("/NEUQHelper/hello/Login");
}
~~~

重定向的实现原理

~~~java
protected void doGet(HttpServletRequest req, HttpServletResponse resp){
    resp.sentHeader("Location", "/NEUQHelper/hello/Login");
    resp.setStatus(302);	//重定向状态码
}
~~~



重定向和转发的区别

相同点：实现页面的跳转，信息处理的迁移

不同点：

- 转发是将请求转发到另一地址，重定向是用另一个地址去响应
- 重定向url栏会改变，而转发不发生改变

###### 5、JSP入门

导入jsp依赖

~~~xml
<dependency>
      <groupId>org.glassfish.web</groupId>
      <artifactId>jstl-impl</artifactId>
      <version>1.2</version>
      <exclusions>
        <exclusion>
          <groupId>javax.servlet</groupId>
          <artifactId>servlet-api</artifactId>
        </exclusion>
        <exclusion>
          <groupId>javax.servlet.jsp</groupId>
          <artifactId>jsp-api</artifactId>
        </exclusion>
        <exclusion>
          <groupId>javax.servlet.jsp.jstl</groupId>
          <artifactId>jstl-api</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
~~~

在 .jsp中获取当前项目路径

~~~jsp
<form action="${pageContext.request.contextPath}/hello/Login" method="post"></form>
~~~

### 1.5、Resquest

HttpServletRequest代表客户端的请求，用户通过Http协议访问服务器，HTTP请求中的所有信息被封装到HttpServletRequest中，通过这个HttpServletRequest的方法，可以获得客户端（网页）的所有信息

#### 1、获取前端传递参数

#### 2、请求转发

ServletContext的请求转发

~~~java
this.getContext.getRequestDispatcher("/hello").forward(req, resp);
~~~

res的请求转发

~~~java
public class Get extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resq.getRequestDispatcher("path").forward(req, resp);
    }
}
~~~

重定向和转发的区别

相同点：实现页面的跳转，信息处理的迁移

不同点：

- 转发是将请求转发到另一地址，重定向是用另一个地址去响应
- 重定向url栏会改变（307），而转发不发生改变（302）



## 2、Cookie

~~~java
public class Get extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	    
		//新建一个cookie，以字符串键值对的形式储存数据
        Cookie c = new Cookie("name", "NorthBoat");
        //设置cookie最大保存时间
        c.setMaxAge(24*60*60);
        //将cookie响应到客户端
        resp.addCookie(c);
        //获取客户端的cookies（多个）
        Cookie[] cookies = req.getCookies();
        //依次打印cookie信息
        if(cookies.length > 0){
            for (int i = 0; i < cookies.length; i++) {
                String name1 = cookies[i].getName();
                String value = cookies[i].getValue();
                resp.getWriter().println(name1 + ":" + value + "\n");
            }
        }
    }
}
~~~

Cookie一般会保存在本地的用户目录下appdata

Cookie的一些细节：

- 一个cookie只能保存一个信息
- 一个web站点可以给浏览器发送多个cookie，最多存放20个cookie
- Cookie大小有限制：4kb
- 300个cookie为浏览器上线

如何让cookie瞬间消失：

- 不设置有效期，关闭浏览器，自动失效
- 设置有效期为0

编码问题：

全局设置编码

~~~java
resp.setCharacterEncoding("utf-8");
resp.setContentType("text/html");
req.setCharacterEncoding("utf-8");
~~~

设置单个字符串编码

~~~java
//将str转化为指定编码，返回一个String
URLEncoder.encode(String str, String encodeType);
~~~















