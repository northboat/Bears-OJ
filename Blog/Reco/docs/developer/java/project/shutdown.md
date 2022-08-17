---
title: 基于Java-Socket的远程关机程序
date: 2021-10-15
tags:
  - Web
  - Java
categories:
  - WebApp
---

## 设计思路

- Socket网络通信
- Runtime类

将Socket服务端部署在云服务器上；控制客户端部署在网页，负责给服务端发送命令指示；被控制客户端部署在本地，时刻监听服务端给自己传达的信息

## 源码

### 服务端

SocketThread类，通信主要功能实现

~~~java
public class ServerThread extends Thread{

    private InputStream in = null;
    private OutputStream out = null;

    private Socket socket = null;

    private String command = null;

    public ServerThread(Socket socket, String command){ this.socket = socket; this.command = command; }

    public String getCommand(){
        return command;
    }

    public void getMessage() throws IOException {
        in = socket.getInputStream();
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        byte[] buffer = new byte[2084];
        int len = 0;
        while((len = in.read(buffer)) != -1){
            byteOut.write(buffer, 0, len);
        }
        command = byteOut.toString();
        System.out.println("这里是服务器，接收到命令：" + command);
        byteOut.close();
        socket.shutdownInput();
    }

    public void sendMessage() throws IOException {
        out = socket.getOutputStream();
        out.write(command.getBytes("GBK"));
        socket.shutdownOutput();
    }


    @Override
    public void run() {
        try {
            //读取客户端信息
            if(command == null){
                getMessage();
            }else{
                sendMessage();
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("该程序靠此bug运行");
            //e.printStackTrace();
        } finally{
            //关闭资源
            try {
                if(out != null)
                    out.close();
                if(in != null)
                    in.close();
                if(socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
~~~

执行代码

其中count很重要，用于记录接收到命令的次数，当上个任务以及完成后重置命令为空，等待下次命令送达

~~~java
public class Server {

    private static int count = 0;
    //防止无限循环警告
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        try {

            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);
            // 创建客户端socket
            Socket socket;
            //储存命令
            String command = null;
            //循环监听等待客户端的连接
            while(true){
                if(count % 2 == 0){
                    command = null;
                    count = 0;
                }
                // 监听客户端
                socket = serverSocket.accept();

                ServerThread thread = new ServerThread(socket, command);
                thread.start();
                System.out.println(count);
                TimeUnit.SECONDS.sleep(2);
                if(thread.getCommand() != null){
                    count++;
                    command = thread.getCommand();
                    System.out.println(command);
                }
                
                //System.out.println(command);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP：" + address.getHostAddress() + ":" + socket.getPort());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
~~~

### 控制端

#### Servlet

~~~java
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;

public class Controller extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String pwd = req.getParameter("pwd");
        if(pwd.equals("011026")){
            try{
                //向服务端发送信息，想办法把这个字符串存起来
                Socket socket = new Socket("39.106.160.174", 8088);
                //Socket socket = new Socket("localhost", 8088);
                socket.getOutputStream().write("shutdown".getBytes());
                socket.shutdownOutput();
                resp.sendRedirect("/Remote-Controller/ShutdownSuccessfully.jsp");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("密码错误");
            resp.sendRedirect("/Remote-Controller");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
~~~

#### JSP

index.jsp

~~~html
<html>
<body style="text-align: center" marginheight="300px">
<title>Remote Controller</title>
<form action="${pageContext.request.contextPath}/exec" method="post">
    <h1>Welcome To NorthernBoat's Remote-Controller</h1><br><br>

    <br><h4>ShutDown U PC</h4><input type="password" name="pwd"><br><br>

    <input type="submit" value="exec">
</form>
</body>
</html>
~~~

ShutdownSuccessfully.jsp

~~~html
<%--
  Created by IntelliJ IDEA.
  User: NorthBoat
  Date: 2021/10/5
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ShutdownSuccessfully</title>
</head>
<body style="text-align: center" marginheight="350px">
<h1>Shutdown Successfully!</h1>
<a href="/Remote-Controller">Go Back</a>
</body>
</html>
~~~

#### xml

web.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.0" xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
  <display-name>Archetype Created Web Application</display-name>


  <servlet>
    <servlet-name>Controller</servlet-name>
    <servlet-class>com.Controller</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>Controller</servlet-name>
    <url-pattern>/exec</url-pattern>
  </servlet-mapping>

</web-app>
~~~

pom.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.example</groupId>
  <artifactId>controller</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>controller Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>



  <build>
    <finalName>controller</finalName>
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

### 客户端

执行代码

~~~java
public class Client {
    public static Socket socket;
    public static InputStream is;
    public static BufferedReader reader;

    public static void exec() throws IOException {
        try{
            // 和服务器创建连接并获取命令
            while(true) {
                System.out.println("hahaha");
                socket = new Socket("39.106.160.174", 8088);
                //socket = new Socket("localhost", 8088);
                socket.setSoTimeout(3000);
                /*socket.getOutputStream().write("Client请求指示".getBytes());
                socket.shutdownOutput();*/
                // 从服务器接收的信息
                is = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                String info = null;
                if ((info = reader.readLine()) != null) {
                    if (info.equals("shutdown")) {
                        break;
                    }
                }
            }
            reader.close();
            is.close();
            socket.close();
        }catch (SocketTimeoutException e){
            exec();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            exec();
        }  catch (IOException e) {
            e.printStackTrace();
        }  finally {
            //exec.shutdown();
            System.out.println("shutdown");
        }
    }
}
~~~

“关机代码”实现类

~~~java
public class exec {
    public static void shutdown(){
        try{
            Runtime.getRuntime().exec("shutdown -s -t 0");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            System.out.println("bye");
        }
    }
}
~~~

## 一些问题

1、网页的部署：同样使用docker部署

~~~bash
docker run -it -d --name controller -p 8082:8080 tomcat
~~~

其中-p指令，第一个是宿主机端口，第二个是容器端口，该命令将二者映射

2、jar包打包：使用idea集成的功能对代码进行打包

~~~
File ——> Project Structure ——> Artifacts
~~~

注意配置Main函数入口

3、jar包转exe文件

4、Linux后台运行jar包

~~~bash
nohup java -jar Server.jar & > log.txt 
~~~

后台运行jar包并把控制台消息输出到log.txt文件中

5、关闭后台运行的jar包

查找后台运行的jar包

~~~bash
jps -l
~~~

安全退出（pid为进程号）

~~~bash
kill pid
~~~

