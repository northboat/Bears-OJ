# JDBC

## 1、基本操作

### 1.1、连接数据库

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



### 1.2、关闭连接

~~~java
public void stop(){
        try{
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
~~~



### 1.3、查询操作

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

