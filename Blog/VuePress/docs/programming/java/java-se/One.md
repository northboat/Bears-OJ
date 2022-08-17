# Java后端学习第一周



# 1、Markdwon



# 2、Java基础



## 1、初识类

### 1.1、ArrayList

~~~java
package com.CommonClass;

import java.util.ArrayList;

public class TestArrayList {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        //添加元素
        list.add("HelloWorld");
        list.add("wdnmd");
        list.add("c++ yyds");
		
        //遍历ArrayList
        for(String str: list){	//特殊的for循环
            System.out.println(str);
        }
        
        //查询元素个数
        System.out.println(list.size());
        
        //查询特定元素是否存在
        boolean isIn = list.contains("wdnmd");
        if(isIn){
            System.out.println("wdnmd");
        }
        
        //判断数表是否为空
        //移除元素
        int s = 0;
        while(!list.isEmpty()){
            list.remove(s);
            s++;
        }
    }
}
~~~

### 1.2、Scanner

~~~java
import java.util.Scanner;
/**
 * @author NorthBoat
 * @version 1.8
 */
public class TestScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入数据：");
    /*
        if(scanner.hasNext()){  //判断是否有数据输入，以空格作为结束符
             String str = scanner.next();
             System.out.println(str);
        }
    */
        int a = scanner.nextInt();
        String b = scanner.nextString();
        double c = scanner.nextDouble();

        if(scanner.hasNextLine()){  //判断是否有数据输入，以回车作为结束符(平时常用nextLine())
            String str = scanner.nextLine();
            System.out.println(str);
        }

        scanner.close();
    }
}
~~~





## 2、JavaDoc



### 2.1、使用命令行生成java.doc文档

~~~java
javadoc -encoding UTF-8 -charset UTF-8 文件名.java
~~~

### 2.2、使用idea生成java.doc文档

~~~java
用idea生成java.doc文档: Tools -> Generate JavaDoc
~~~







## 3、异常处理

### 3.1、异常处理基础

~~~java
try{
    if(条件){
        //当满足条件时主动抛出异常给下方捕获
        throw new Exception
    }
    //监控区
}catch(Exception e){
    //捕获异常
	//处理区
}finally{
    //善后区
}


void calculate(int a, int b)	throws Exception //从方法内部将异常抛出
{
    try{
        if(b==0){
            throw new Exception();
        }
        System.out.println(a/b);
    }catch(Exception e){
        return;
    }
}
~~~

### 3.2、自定义异常类

~~~ java
package com.Exception;

//自定义的异常类，继承java自带的Exception
public class MyException extends Exception
{
    private int detail;

    public MyException(int x)
    {
        this.detail = x;
    }

    //打印异常信息
    @Override
    public String toString()
    {
        return "MyException{" + "detail=" + detail + '}';
    }
}

class Test
{
    static void test(int x) //throws MyException(也可以用throws将异常抛出函数外)
    {
        System.out.println("传递的数为：" + x);
        try
        {
            if(x > 10)
            {
                throw new MyException(x);
            }
        } //在函数内部捕获监控区内异常并处理
        catch (MyException e)
        {
            System.out.println("MyException=>" + e);
        }
        System.out.println("OK");
    }

    public static void main(String[] args)
    {
        int a = 11;
        // try
        //    {
        new Test().test(a);
     /*   }
        catch (MyException e)
        {
            System.out.println("MyException=>" + e);
        }*/
    }
}
~~~





## 4、Gui编程

### 1、awt

~~~java
package com.GUI;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Layout {
    public static void main(String[] args) {

        Frame frame = new Frame();
        frame.setSize(700,500);
        frame.setLocation(600, 300);

        ArrayList<Button> btn = new ArrayList();
        for(int i = 0; i < 10; i++){
            btn.add(i, new Button("Button " + (i+1)));
        }

        frame.setLayout(new GridLayout(2, 5));

        for(int j = 0; j < 10; j++){
            frame.add(btn.get(j));
        }

        frame.addWindowListener(new WindowAdapter() {	//监听器：用于关闭程序
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
~~~





# 3、Gitee













