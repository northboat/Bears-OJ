---
title: Thread & IO
date: 2021-2-28
tags:
  - Java
---

## 多线程基础

Process（进程）与Thread（线程）

进程=指令执行序列+资源

### 线程创建

推荐使用Runnerable接口

#### 继承Thread类

实现了Runnerable接口，每个线程的优先级和操作系统有关

1、自定义线程类继承Thread类

2、重写run()方法，编写线程执行体

3、创建线程对象，调用start()方法启动线程

~~~java
public class TestThread extends Thread{
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++){
            System.out.println("我在看代码");
        }
    }

    public static void main(String[] args) {
        TestThread tt = new TestThread();
        //调用start方法，两个线程是同时执行的
        tt.start();
        for(int i = 0; i < 1000; i++){
            System.out.println("我在拉屎");
        }
    }
}
~~~

如何导入外部jar包

1、在com同级目录下新建lib目录，将jar包粘贴进去

2、添加lib为library

~~~java
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class TestThread2 extends Thread{

    private String url;
    private String name;

    public TestThread2(String url, String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownloader wd = new WebDownloader();
        wd.downloader(this.url, this.name);
        System.out.println("正在下载：" + this.name);
    }

    public static void main(String[] args) {
        TestThread2 t1 = new TestThread2("https://img-blog.csdnimg.cn/2019080416083639.png", "图片1.jpg");
        TestThread2 t2 = new TestThread2("https://img-blog.csdnimg.cn/2019080416083639.png", "图片2.jpg");
        TestThread2 t3 = new TestThread2("https://img-blog.csdnimg.cn/2019080416083639.png", "图片3.jpg");
        TestThread2 t4 = new TestThread2("https://img-blog.csdnimg.cn/2019080416083639.png", "图片4.jpg");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}

//在lib中导入了外部jar包
class WebDownloader{
    public void downloader(String url, String name){
        try{
            FileUtils.copyURLToFile(new URL(url), new File(name));
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}
~~~

#### 实现Runnerable接口

~~~java
public class TestThread3 implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i < 1200; i++){
            System.out.println("我在拉屎" + i);
        }
    }

    public static void main(String[] args) {
        TestThread3 t = new TestThread3();
        new Thread(t).start();
        for(int i = 0; i < 1200; i++){
            System.out.println("我在吃饭" + i);
        }
    }
}
~~~



#### 实现Callable接口

~~~java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestCallable implements Callable<String> {
    private String url;
    private String name;

    public TestCallable(String url, String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public String call() {
        WebDownloader wd = new WebDownloader();
        wd.downloader(this.url, this.name);
        System.out.println("正在下载：" + this.name);
        return name+"下载完成";
    }

    public static void main(String[] args) {
        TestCallable t1 = new TestCallable("https://img-blog.csdnimg.cn/2019080416083639.png", "图片1.jpg");
        TestCallable t2 = new TestCallable("https://img-blog.csdnimg.cn/2019080416083639.png", "图片2.jpg");
        TestCallable t3 = new TestCallable("https://img-blog.csdnimg.cn/2019080416083639.png", "图片3.jpg");
        TestCallable t4 = new TestCallable("https://img-blog.csdnimg.cn/2019080416083639.png", "图片4.jpg");

        //创建线程池服务
        ExecutorService ser = Executors.newFixedThreadPool(4);

        //提交线程服务，在池中执行call方法
        Future<String> f1 = ser.submit(t1);
        Future<String> f2 = ser.submit(t2);
        Future<String> f3 = ser.submit(t3);
        Future<String> f4 = ser.submit(t4);

        //获取call函数的返回结果
        try{
            String res1 = f1.get();
            String res2 = f2.get();
            String res3 = f3.get();
            String res4 = f4.get();
        }catch(Exception e){
            e.printStackTrace();
        }

        //停止线程池
        ser.shutdownNow();


    }
}
~~~



### 并发

多个线程操作一个对象

~~~java
public class TestThread4 implements Runnable{

    private int tickets = 100;

    public void run(){
        while(true){
            if(tickets <= 0)
                break;
            System.out.println(TestThread2.currentThread().getName() + "抢到了第" + tickets-- + "张票");
        }
    }

    public static void main(String[] args) {
        TestThread4 tickets = new TestThread4();
        new Thread(tickets, "小明").start();
        new Thread(tickets, "小红").start();
        new Thread(tickets, "黄牛").start();
    }
}
~~~

在实际运行中，发现居然出现某某抢到了-1张票的情况 ——> 发现问题：多个线程操作同一个对象，线程不安全，数据紊乱

模拟龟兔赛跑：

~~~java
public class Race implements Runnable {

    private static String winner;

    public void run(){
        for (int i = 0; i <= 100; i++) {
            if(Thread.currentThread().getName().equals("兔子") && i >= 80){
                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(gameOver(i)){
                break;
            }
            System.out.println(Thread.currentThread().getName() + "跑了" + i + "米");
        }
    }

    public boolean gameOver(int i){
        if(winner != null){
            return true;
        }
        else{
            if(i >= 100){
                winner = Thread.currentThread().getName();
                System.out.println("获胜者是:" + winner);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Race r = new Race();
        new Thread(r, "兔子").start();
        new Thread(r, "乌龟").start();
    }
}
~~~

### 线程休眠

Thread.sleep()方法：让线程阻塞，传入阻塞时间参数，单位毫秒；抛出异常InterruptedException；sleep时间达到后线程将处于就绪状态；sleep可以模拟网络延时，倒计时等；每个对象都要一个锁，sleep不会释放锁

在游戏中添加一句sleep，充值后去掉这行代码实现“游戏优化”，qtmlgb

### 线程礼让

Thread.yield()方法：礼让线程，让当前正在执行的线程暂停，但不阻塞；将现场从运行状态转为就绪状态；让cpu重新调度，不一定成功

## I/O流基础

### 流的分类

流：数据从内存和存储设备之间的通道

输入输出流：以流的流向分类

- 输入流：从存储设备到内存
- 输出流：从内存到存储设备

字节字符流：以传输单位划分

- 字节流：以字节为单位，可以读写所要数据
- 字符流：以字符为单位，只能读写文本数据

节点（底层）过滤流：按功能划分

- 节点流：具有实际传输数据的读写功能的流
- 过滤流：在节点流的基础上增强了功能

#### 字节流

InputStream：输入流抽象类

~~~java
int available();
//关闭流的资源
void close();
//读取下一个字节
abstract int read();
//读取一定量的字节
int read(byte[] b);
int read(byte[] b, int off, int len);
//跳过和丢弃n个字节
long skip(long n);
~~~

OutputStream：输出流抽象类

~~~java
void close();
//刷新缓冲
void flush();
//将字节写入磁盘
void write(byte[] b);
void write(int b);
~~~

#### 文件字节流

FileInputStream

~~~java
class FileInputStream extends InputStream{
    //从文件系统中的某个文件中获得输入字节，将读到内容存入b数组，返回实际读到的字节数，如果达到文件的尾部，则返回-1
    FileInputStream(String name);
    public int read(byte[] b);
}
~~~

~~~java
public class TestFileInputStream {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream("E:\\Java\\Java SE\\idea\\src\\resource\\a.txt");
        //一次读取一个字节
        /*
         *   int data = 1;
         *   一次读取一个字节
         *   用循环将文件读完（当读到文件末尾将返回-1）
         *   while(data != -1){
         *      data = fis.read();
         *      System.out.println((char)data);
         *   }
         */

        //一次读取多个字节
        byte[] b = new byte[3];
        int count;
        while((count = fis.read(b)) != -1){
            System.out.println(new String(b, 0, count));
        }
        //关闭输入流
        fis.close();
    }
}
~~~

FileOutputStream 

~~~java
class FileOutputStream extends OutputStream
    //一次写入多个字节，将b数组中所有字节写入输出流，存到文件系统的某个文件中
    public void write(byte[] b);
}
~~~

~~~java
import java.io.FileOutputStream;

public class TestFileOutputStream {
    public static void main(String[] args) throws Exception {
        //append参数true会使文件不会被覆盖，即每次运行都会继续储存在上次数据之后
        //不传入参数或传入false，每次写文件，将会覆盖掉之前数据
        FileOutputStream fos = new FileOutputStream("E:\\Java\\Java SE\\idea\\src\\resource\\b.txt", false);
        /*
        * 一次执行一个字符
        * fos.write('a');
        * fos.write('9');
        * fos.write('s');
        */
        String str = "helloWorld\n";
        //调用字符串的 getBytes() 方法将字符串转化为字节数组
        fos.write(str.getBytes());
        fos.close();
    }
}
~~~

