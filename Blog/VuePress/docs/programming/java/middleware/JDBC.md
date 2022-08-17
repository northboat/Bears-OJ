# JDBC

## 一、基本操作

### 1、连接数据库

导入jar包：mysql-connector-java-8.0.25.jar（百度下载即可）

调用 connector 中 api 即可

~~~java
public void connect(){
        try{
            //在 uri 处便定义了所要进入的数据库，此处为 Book
            String uri = "jdbc:mysql://localhost:3306/Book?user=root&password=011026&useSSL=false" + "&serverTimezone = GMT";
            String user = "root";
            String password = "011026";
            con = DriverManager.getConnection(uri, user, password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~

当未设置密码时，还提供了另一种连接方式

~~~java
public void connect(){
        try{
            String uri = "jdbc:mysql://localhost:3306/Book?user=root&password=011026&useSSL=false" + "&serverTimezone = GMT";
            con = DriverManager.getConnection(uri);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~

### 2、关闭连接

~~~java
public void stop(){
        try{
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~

### 3、SQL操作

~~~java
//传入参数 table 为表的名字 （table要在库中，否则res.next()将返回false）
public void search(String table){
        try{
            //获取SQL语句
            Statement sql = con.createStatement();
            //获取整个表中结果
            ResultSet res = sql.executeQuery("SELECT * FROM " + table);
            //打印结果
            while(res.next()){
                for (int i = 1; i <= 4; i++) {
                    System.out.println(res.getString(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
}
~~~

注意：

- ResultSet 提供了很多返回函数，如 getInt(), getDouble()，这些数据都可以通过 getString() 以字符串的形式打印出来
- getString(int index) 中 index 表示数据在表中的列数
- next()函数将返回一个布尔值，即该行是否为空，同时将 res 移到下一行

## 二、JDBC连接池

获取连接：getConnection()

归还连接：close()

### 1、C3P0

#### 1.1、导包

c3p0 + machange + jdbc驱动包

#### 1.2、配置文件

- c3p0-config.xml 或 c3p0.properties

- ~~~xm
  <c3p0-config>
      <default-config>
          <property name="driverClass">com.mysql.jdbc.Driver</property>
          <property name="jdbcUrl">jdbc:mysql://localhost:3306</property>
          <property name="user">root</property>
          <property name="password">011026</property>
  
          <!--初始化申请连接数量-->
          <property name="initialPoolSize">5</property>
          <!--最大连接数量-->
          <property name="maxPoolSize">10</property>
          <!--超时时间-->
          <property name="checkoutTimeout">3000</property>
      </default-config>
  
      <named-config name="otherc3p0">
          <property name="driverClass">com.mysql.jdbc.Driver</property>
          <property name="jdbcUrl">jdbc:mysql://localhost:3306/account</property>
          <property name="user">root</property>
          <property name="password">011026</property>
  
          <property name="initialPoolSize">5</property>
          <property name="maxPoolSize">8</property>
          <property name="checkoutTimeout">1000</property>
      </named-config>
  </c3p0-config>
  ~~~

- 

- 路径：java项目在src目录下，web项目在src/main/resource目录下

#### 1.3、创建核心对象

~~~java
DataSource ds = new ComboPooledDataSource();
~~~

#### 1.4、获取连接

getConnection

~~~java
Connection con = ds.getConnection();
System.out.println(con);
~~~

#### 1.5、最大连接数量

~~~java
for(int i = 0; i < 10; i++){
    Connection con = ds.getConnection();
	System.out.println(con);
}
//当获取连接的数量超过xml文件中配置的最大数量时，将报错An attempt by a client to checkout a Connection has timed out.
~~~

#### 1.6、归还连接

~~~java
ds.close();
//将连接归还，此时连接数归零，又可以重新连接

~~~

####  1.7、自定义配置

在c3p0.xml中进行配置

~~~xml
<named-config name="database name">
    
</named-config>named-config name="database name">
~~~

在获取连接时传入参数(String databaseName)

~~~java
DataSource ds = new ComboPooledDataSource("databaseName");
//该语句将自动找到xml文件中对应数据库的配置
~~~

## 

