# 数据库连接池

获取连接：getConnection()

归还连接：close()

## 1、c3p0

#### 1、导包

c3p0 + machange + jdbc驱动包

#### 2、配置文件

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

#### 3、创建核心对象：

~~~java
DataSource ds = new ComboPooledDataSource();
~~~

#### 4、获取连接

getConnection

~~~java
Connection con = ds.getConnection();
System.out.println(con);
~~~

#### 5、最大连接数量

~~~java
for(int i = 0; i < 10; i++){
    Connection con = ds.getConnection();
	System.out.println(con);
}
//当获取连接的数量超过xml文件中配置的最大数量时，将报错An attempt by a client to checkout a Connection has timed out.
~~~

#### 6、归还连接

~~~java
ds.close();
//将连接归还，此时连接数归零，又可以重新连接

~~~

####  7、自定义配置

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

## 2、Druid

