# JUC学习第二周

## 1、集合类不安全

> 初识函数式接口

~~~java
public class ListTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3");
        list.forEach(s->{System.out.print(s + " ");});
    }
}
~~~

### 1.1、List不安全

数据结构：顺序表或链表

> 写入时复制	COW	计算机程序设计领域的一种优化策略

> 以下代码将报错 java.util.ConcurrentModificationException：并发修改异常

~~~java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ListTest {
    public static void main(String[] args) {
        List<String> l = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                l.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(l);
            }, String.valueOf(i)).start();
        }
    }
}
~~~

解决方案：

> Vector

~~~java
List<String> list = new Vector<>();
~~~

> Collections.synchronizedList

~~~java
List<String> l = Collections.synchronizedList(new ArrayList());
~~~

> CopyOnWriteArrayList

~~~java
List<String> l = new CopyOnWriteArrayList<>();
~~~

与 ArrayList 相同，在 CopyOnWriteArrayList 内部同样维护了一个数组，不同的是该数组有 transient volatile 修饰

~~~java
private transient volatile Object[] array;
~~~

其 add 方法采取写入时复制的方式，即将原数组复制一份（长度+1），再插入新元素，线程安全性由 **lock** 锁保证

与 Vector 不同，CopyOnWriteArrayList 中并无 synchronized 修饰，故其效率较高

> ~~~java
> public boolean add(E e) {
>     final ReentrantLock lock = this.lock;
>     lock.lock();
>     try {
>         Object[] elements = getArray();
>         int len = elements.length;
>         Object[] newElements = Arrays.copyOf(elements, len + 1);
>         newElements[len] = e;
>         setArray(newElements);
>         return true;
>     } finally {
>         lock.unlock();
>     }
> }
> ~~~

COW：计算机程序设计领域的一种优化策略

在写入时避免覆盖造成数据问题 ——> 读写分离

### 1.2、Set不安全

Iterable ——> Collection ——> List + Set + BlockingQueue（阻塞队列）

与 List 同理可得 Set 同样线程不安全，报错 java.util.ConcurrentModificationException

~~~java
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SetTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        for(int i = 0; i < 10; i++){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(Thread.currentThread().getName() + set);
            }, String.valueOf(i)).start();
        }
    }
}
~~~

解决方案：

> Collections.synchronizedSet

~~~java
Set<String> set = Collections.synchronizedSet(new HashSet<>());
~~~

> CopyOnWriteArraySet

~~~java
Set<String> set = new CopyOnWriteArraySet<>();
~~~

HashSet 的底层：HashMap

~~~java
public HashSet() {
    map = new HashMap<>();
}

//CopyOnWriteArraySet维护的map由transient修饰
private transient HashMap<E,Object> map;

public boolean add(E e) {
    return map.put(e, PRESENT)==null;
}

//PRESENT是一个不变的值
private static final Object PRESENT = new Object();
~~~

### 1.3、Map不安全

数据结构：哈希表（散列表）

默认初始容量：16

默认加载因子：0.75

解决方案：

> Collections.synchronizedMap

~~~java
Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
~~~

> ConcurrentHashMap

~~~java
Map<String, String> map = new ConcurrentHashMap<>();
~~~

其底层数据多由 transient volatile 修饰，方法关键步骤由 synchronized 修饰，在下无法理解

## 2、Callable

Callable 和 Runnable 的区别

- Callable 有返回值
- Callable 可以抛出异常
- 方法不同，run() ——> call()

Thread 只能 start 继承了 Runnable（重写了run方法） 的类，怎么用 Thread 去跑继承了 Callable 的类呢？

阅读jdk文档可以发现 Runnable 有一个实现类：FutureTask

![image-20210730115727668](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210730115727668.png)

它既是 Runnable 的实现类，又可以由 Callable 进行构造，如此便实现了 Callable 和 Thread 的连接

~~~java
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) {
        new Thread(new FutureTask<>(new MyThread())).start();
        System.out.println();
    }
}

class MyThread implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println("wdnmd");
        return "Hello Callable";
    }
}
~~~

如何获取 Callable 的返回值？

~~~java
MyThread mythread = new MyThread();
FutureTask<String> futuretask = new FutureTask<>(t);
new Thread(futuretask).start();
String res = futuretask.get();
~~~

注意

- FutureTask 的 get 方法是阻塞的，也就是说，只有 call() 方法跑完之后，才会 get 到值，若 get 在 start 前，则程序死锁
- call() 方法是有缓存的，开两条线程跑同一个 call 函数，只会有一个结果，如上述代码若开两条线程只会输出一句 wdnmd

## 3、常用辅助类

### 3.1、CountDwonLatch

latch：门栓

> 用于倒计时，count 为倒计的数量

- 构造方法

~~~java
CountDownLatch(int count);
~~~

- 倒数，令 count 减一

~~~java
countDown()
~~~

- 等待方法，阻塞，当 count 为0时唤醒

~~~java
await();
~~~

测试代码：

需要注意的是，我开启了一条线程输出 “All Out”，它将在 count = 0 时才会被执行，即等待计数结束

~~~java
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch c = new CountDownLatch(10);

        new Thread(()->{
            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("All Out");
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " Go Out");
                c.countDown();
            }, String.valueOf(i+1)).start();
        }
    }
}
~~~

结果：

~~~java
1 Go Out
3 Go Out
2 Go Out
6 Go Out
8 Go Out
9 Go Out
7 Go Out
5 Go Out
4 Go Out
10 Go Out
All Out

Process finished with exit code 0
~~~

### 3.2、CycilcBarrier

- 构造方法
  1. parties 为所经历的线程最大数
  2. lambda 表达式中为当经历线程数达到 parties 时执行的语句

~~~java
CyclicBarrier(int parties, Lambda表达式)
~~~

- 计数、等待方法
  - 在线程中执行 await 时，parties+1，同时判断是否达到初始化时的最大值，达到则执行构造方法中的 λ 表达式

~~~java
await();
~~~

测试代码：

~~~java
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier c = new CyclicBarrier(7, ()->{
            System.out.println("集齐七颗龙珠，召唤神龙");
        });

        for (int i = 0; i < 7; i++) {
            final int temp = i+1;
            new Thread(()->{
                System.out.println("收集到第" + temp + "颗龙珠");
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i+1)).start();
        }
    }
}
~~~

结果：

~~~java
收集到第1颗龙珠
收集到第7颗龙珠
收集到第4颗龙珠
收集到第3颗龙珠
收集到第5颗龙珠
收集到第2颗龙珠
收集到第6颗龙珠
集齐七颗龙珠，召唤神龙

Process finished with exit code 0
~~~

### 3.3、Semaphore

信号量

```java
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        //初始化线程数量（停车位）
        Semaphore semaphore = new Semaphore(4);

        for (int i = 0; i < 7; i++) {
            new Thread(()->{
                try {
                    //acquire()：获得许可，阻塞，判断当前是否有闲置的许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 停车");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " 离开车位");
                    //释放许可
                    semaphore.release();
                }
            }, String.valueOf(i+1)).start();
        }
    }
}
```

- acquire()：获得，如果信号量满了，等待，直到被释放为止
- release()：释放，会将信号量释放+1，然后唤醒等待的线程

作用：

1. 多个共享资源互斥的使用（如停车位）
2. 并发限流，控制最大的线程数

## 4、读写锁

- 读可以被多个线程读，写只能由单线程写
- 写的时候不能读，读的时候不能写

**ReadWriteLock**

与 Lock 锁的用法类似，进入方法时加锁，将业务代码放在 try / catch 语句中，在 finally 中解锁

注意读写锁并没有 Condition，它自身在加锁解锁的过程中完成了 Condition 的精确控制，提高了锁细粒度

~~~java
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.*;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        MyCacheLock myCache = new MyCacheLock();

        for (int i = 0; i < 10; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(temp, UUID.randomUUID().toString().substring(0, 3));
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(temp);
            }, String.valueOf(i)).start();
        }
    }
}

class MyCacheLock{

    private volatile Map<Integer, String> map = new HashMap<>();

    //读写锁，更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //写
    public void put(Integer key, String val){
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "正在写入");
            map.put(key, val);
            System.out.println(Thread.currentThread().getName() + "写入完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }
    //读
    public void get(Integer key){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "正在读取");
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取完毕");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}


class MyCache{
    private Map<String, String> map = new HashMap<>();

    public void put(String key, String val){
        System.out.println(Thread.currentThread().getName() + "正在写入");
        map.put(key, val);
        System.out.println(Thread.currentThread().getName() + "写入完毕");
    }

    public void get(String key){
        System.out.println(Thread.currentThread().getName() + "正在读取");
        map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取完毕");
    }
}
~~~

用 MyCache 跑多线程时，会出现多个线程同时 “正在写入” 的情况，这样很明显很不安全，加锁后解决这一问题，即同时只会有单条线程进行 “写” 的操作

我的代码和kuangshen一模吊样，但就是先读后写，我很烦

## 5、阻塞队列

> 阻塞：写入队列已满、读取队列为空时，不得不阻塞等待
>
> 队列：FIFO（I => 写入，O => 读取），先进先出

继承关系：

![image-20210730232535767](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210730232535767.png)

什么情况我们会用阻塞队列？ 多线程，线程池

### 5.1、ArrayBlockingQueue

**队列的使用**

添加、移除

**四组API**

| 方式         | 抛出异常  | 有返回值，不抛出异常 | 阻塞等待（一直阻塞） | 等待超时   |
| ------------ | --------- | -------------------- | -------------------- | ---------- |
| 添加         | add()     | offer()              | put()                | offer(...) |
| 移除         | remove()  | poll()               | take()               | poll(..)   |
| 返回队首元素 | element() | peek()               | -                    | -          |

**在编写代码时，一定要将泛型写上，这是编程的一个规范，减少检查时间**

异常：

~~~java
/**
* 抛出异常
*/
public void test1(){
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    //返回 boolean，添加成功则返回 true
    System.out.println(blockingQueue.add("a")
    System.out.println(blockingQueue.add("b"));
    System.out.println(blockingQueue.add("c"));

    //当队列已满，继续 add()，将报错 java.lang.IllegalStateException: Queue full
    //System.out.println(blockingQueue.add("d"));

    for (int i = 0; i < 3; i++) { 
        //element 仅返回队首元素
        System.out.println(blockingQueue.element());
        //remove 同样返回队首元素，同时移除
        blockingQueue.remove();
    }
	//当队列已空，继续 remove，将报错 java.util.NoSuchElementException
    //System.out.println(blockingQueue.remove());
}
/**
* 不抛出异常，有返回值
*/
public void test2(){
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
    System.out.println(blockingQueue.offer("a"));
    System.out.println(blockingQueue.offer("b"));
    System.out.println(blockingQueue.offer("c"));

    //当队列已满，继续 offer，将不成功，并且返回 false，不抛出异常
    System.out.println(blockingQueue.offer("d"));

    for (int i = 0; i < 3; i++) {
        System.out.println(blockingQueue.peek());
        blockingQueue.poll();
    }
    //当队列已空，继续 poll，将得到 null，不抛出异常
    System.out.println(blockingQueue.poll());
}
                       
/**
* 等待，阻塞
*/
public void test3() throws InterruptedException {
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
    blockingQueue.put("a");
    blockingQueue.put("b");
    blockingQueue.put("c");

    //此时队列已满，若继续向里put，将一直阻塞，直到队列中有空余位置
    //blockingQueue.put("d");

    for (int i = 0; i < 3; i++) {
        System.out.println(blockingQueue.take());
    }
    //此时队列已空，若继续take，同样会一直阻塞，直到队列中由元素了被唤醒执行
    //System.out.println(blockingQueue.take());
}                     
                       
/**
* 超时等待
* long timeLimit：超时等待时间 
* TimeUnit timeunit：时间单位
*/
public void test4() throws InterruptedException {
    BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
    System.out.println(blockingQueue.offer("a"));
    System.out.println(blockingQueue.offer("b"));
    System.out.println(blockingQueue.offer("c"));

    //超时等待 2 秒，超出 2 秒返回false，不继续等待
    System.out.println(blockingQueue.offer("d", 2, TimeUnit.SECONDS));

    for (int i = 0; i < 3; i++) {
        System.out.println(blockingQueue.poll());
    }
    //同理等待 2 秒，超出 2 秒则返回null
    System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
}
~~~

### 5.2、SynchronousQueue

**同步队列**

没有容量：进去一个元素，必须等待取出来以后，才可以继续往里放一个元素（有点像信号量）

方法：put()、take()

~~~java
/**
 * 同步队列
 */
public class SynchronizedQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+" put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+" put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1").start();

        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+" take "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+" take "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName()+" take "+blockingQueue.take());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }, "T2").start();
    }
}
~~~

结果：

~~~java
T1 put 1
T2 take 1
T1 put 2
T2 take 2
T1 put 3
T2 take 3

Process finished with exit code 0
~~~

## 6、线程池

> 池化技术
>
> 三大方法七大参数

程序的运行，本质：占用系统的资源 ——> 优化资源的使用 ——> 池化技术

线程池、连接池、内存池、对象池......

池化技术：事先准备好一些资源，需要使用则从池中取，用完后还回



线程池的优点：

1、降低资源的消耗

2、提高响应的速度

3、方便管理

4、线程复用、可以控制最大并发



### 6.1、三大方法

单例线程池

~~~java
ExecutorService threadPool = Executors.newSingleThreadExecutor();
~~~

自定义最大线程池

~~~java
ExecutorService threadPool = Executors.newFixedThreadPool(5);
~~~

自适应线程池

~~~java
ExecutorService threadPool = Executors.newCachedThreadPool();
~~~

测试代码

- 在业务跑完后关闭线程池：shutdown()，否则会一直阻塞

~~~java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test1 {
    public static void main(String[] args) {
        //ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try{
            for (int i = 0; i < 100; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
~~~



### 6.2、七大参数

通过源码可以发现，三大方法中底层返回的都与一个 ThreadPoolExecutor 有关

~~~java
public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
}

public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
}

public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,	//约为21亿 ——> oom(out of memory)
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
}
~~~

**ThreadPoolExcutor**

~~~java
public ThreadPoolExecutor(int corePoolSize,	//核心线程数
                              int maximumPoolSize,	//最大线程数
                              long keepAliveTime,	//超时时间，超时未调用则还回线程
                              TimeUnit unit,	//超时时间单位
                              BlockingQueue<Runnable> workQueue,	//阻塞队列
                              ThreadFactory threadFactory,	//线程工厂，创建线程的，一般不动
                              RejectedExecutionHandler handler	//拒绝策略) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
}
~~~

### 6.3、自定义线程池

~~~java
//自定义线程池
ExecutorService threadPool = new ThreadPoolExecutor(
        2,	//核心线程数
        5,	//最大线程数
        2,	//超时等待时间
        TimeUnit.SECONDS,	//时间单位
        new ArrayBlockingQueue<>(3),	//阻塞队列（等候区）
        Executors.defaultThreadFactory(),	//默认线程工厂
        new ThreadPoolExecutor.DiscardOldestPolicy()	//拒绝策略
);
~~~

### 6.4、四种拒绝策略

 * 1、new ThreadPoolExecutor.AbortPolicy()：当线程已满、队列已满，再进任务时，不执行该任务并且抛出移除
 * 2、new ThreadPoolExecutor.CallerRunsPolicy()：当满员时再进任务，将该任务打发回其发起的地方，如此处将多余的线程返回 main 线程执行
 * 3、new ThreadPoolExecutor.DiscardPolicy()：当满员时再进任务，不执行该任务且不抛出异常
 * 4、new ThreadPoolExecutor.DiscardOldestPolicy()：与(3)相似，但多出的任务将和线程池中最早的任务竞争一个线程，若竞争成功则执行，失败则不执行，

~~~java
import java.util.concurrent.*;
public class Test2 {
    public static void main(String[] args) {
        //自定义线程池
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        try{
            for (int i = 0; i < 9; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭线程池
            threadPool.shutdown();
        }
    }
}
~~~

### 6.5、最大线程

最大线程如何定义

1、CPU密集型

~~~java
ExecutorService threadPool = new ThreadPoolExecutor(
        2,
    	//获取CPU核数，最大利用CPU效率
        Runtime.getRuntime().availableProcessors(),
        2,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(3),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.DiscardOldestPolicy()
);
~~~

2、IO密集型

判断程序中十分耗 IO 资源的线程数量，将最大线程数一般设置为两倍于此

## 7、四大函数式接口

新时代的程序员：lambda表达式、链式编程、函数式接口、Stream流式计算

> 函数式接口：只有一个方法的接口 ——> Runnable / Callable

~~~java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}

//新版java中有很多很多FunctionalInterface
//简化编程模型，再新版本的框架底层大量应用
//forEach(消费者类的函数式接口)
~~~

### 7.1、Function函数型接口

~~~java
@FunctionalInterface
public interface Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
}
~~~

有一个传入参数，有一个返回值（均为泛型，默认Object），仅含一个方法：apply (Object o, Object b)

~~~java
import java.util.function.Function;

public class FunctionTest {
    public static void main(String[] args) {
        /**用匿名内部类重写apply函数
         * Function<String, String> f = new Function<String, String>() {
         *         @Override
         *         public String apply(String str) {
         *             return str;
         *         }
         *     };
        */
        //用lambda表达式简化
        Function<String, String> function = (str)->{return str;};
        System.out.println(function.apply("daslkfj"));
    }
}
~~~

### 7.2、Predicate断定型接口

~~~java
@FunctionalInterface
public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);
}
~~~

传入一个参数（默认Object），返回一个布尔值，所含方法为 test

~~~java
import java.util.function.Predicate;

public class PredicateTest {
    public static void main(String[] args) {
        /**
         * Predicate p = new Predicate() {
         *     @Override
         *     public boolean test(Object o) {
         *         return o.toString().isEmpty();
         *     }
         * };
         */
        
        Predicate<String> predicate = (str)->{return str.isEmpty();};
        System.out.println(predicate.test("alksd"));
    }
}
~~~

### 7.3、Consumer消费型接口

~~~java
@FunctionalInterface
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);
}
~~~

消费者只有输入，没有返回值（消费）

~~~java
import java.util.function.Consumer;

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer<String> consumer = (str) -> {System.out.println(str);};
        consumer.accept("daskfj");
    }
}
~~~

### 7.4、Supplier供给型接口

~~~java
@FunctionalInterface
public interface Supplier<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();
}
~~~

供给者没有输入，只有返回值（供给）

~~~java
import java.util.concurrent.*;
import java.util.function.Supplier;

public class SupplierTest {
    public static void main(String[] args) {
        Supplier<String> supplier = ()->{return Thread.currentThread().getName();};
        ExecutorService executorService = new ThreadPoolExecutor(
                3,
                12,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue(9),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (int i = 0; i < 12; i++) {
            executorService.execute(()->{
                System.out.println(supplier.get());
            });
        }
        executorService.shutdown();
    }
}
~~~

## 8、Stream流式计算

> 什么是Stream流式计算

> 大数据：存储+计算
>
> 存储：集合框架、MySQL（本质就是存储东西的）
>
> 计算：流（计算都交给流来操作）

- 已知有Person类，用一行代码完成下列筛选操作
   * 1、ID为偶数
   * 2、年龄大于等于23
   * 3、名字转换为大写字母
   * 4、倒序输出
   * 5、只输出一个用户

Person类：

~~~java
public class Person {
    private int id;
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
~~~

lambda表达式+链式编程+函数式接口+stream计算：

~~~java
public class StreamTest {
    public static void main(String[] args) {
        Person p1 = new Person(1, "a", 23);
        Person p2 = new Person(2, "b", 27);
        Person p3 = new Person(3, "c", 17);
        Person p4 = new Person(4, "d", 41);
        Person p5 = new Person(5, "e", 25);

        List<Person> list = Arrays.asList(p1, p2, p3, p4, p5);
        list.stream()
                .filter((u)-> {return u.getAge()>=23;})
                .filter((u)-> {return u.getId()%2==0;})
                .map((u)-> {return u.getName().toUpperCase();})
                .sorted((u1, u2)->{return u2.compareTo(u1);})
                .limit(1)
                .forEach((u)->{System.out.println(u);});
    }
}
~~~

## 9、ForkJoin

> 什么是ForkJoin？分支 合并

再jdk1.7中出现，并发执行任务，在大数据量时提高效率

大数据：Map Reduce（把大任务拆分为小任务，再把小任务结果合并 ——> 递归）

ForkJoin特点：工作窃取

维护的都是双端队列，当 B 线程结束后而 A 线程未结束，B 将窃取 A 的工作，从后向前执行

![image-20210731162719156](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210731162719156.png)

具体实现：

![image-20210731192752437](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210731192752437.png)

1、将运算类继承 RecursiveAction 或 RecursiveTask，重写 computer 方法（注意泛型：此处泛型类型规定了 compute 方法的返回值）

~~~java
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Long> {

    private long start;
    private long end;

    private long temp = 10000L;

    public ForkJoinTest(long start, long end) {
        this.start = start;
        this.end = end;
    }
	
    //重写computer
    @Override
    protected Long compute() {
        if(end-start<temp){
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else{
            long middle = (start+end)/2;
            ForkJoinTest task1 = new ForkJoinTest(start, middle);
            task1.fork();
            ForkJoinTest task2 = new ForkJoinTest(middle+1, end);
            task2.fork();
            return task1.join()+task2.join();
        }
    }
}
~~~

2、开辟一个 ForkJoinPool，用池中的 submit 方法或 execute 方法执行 Task

~~~java
public void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
    
    	//创建ForkJoin池
        ForkJoinPool pool = new ForkJoinPool();
    
    	//创建任务
        ForkJoinTask<Long> task = new ForkJoinTest(0, 10_0000_0000);
    
    	//此处 submit.get() 和 execute(task) 后的 task.get() 所得其实是一样的
    	//执行任务
        ForkJoinTask<Long> submit = pool.submit(task);
        pool.execute(task);
        submit.get();
        long end = System.currentTimeMillis();
    
    	//关闭池
        pool.shutdown();
    
    	//获取结果
        System.out.println("和:" + task.get() + " 时间:" + (end-start));
}
~~~

如此大大提高了运算效率，比用普通的 for 循环逐个加约快 130 倍

阅读源码还可知 ForkJoinPool.execute() 还可以执行 Runnable task，即实现了 Runnable 接口的任务，重写 run 方法，同理还可以扩展至 Callable 接口，通过 FutureTask 将 Callable 和 Runnable 连接，放在 execute 中执行

~~~java
public void execute(Runnable task) {
        if (task == null)
            throw new NullPointerException();
        ForkJoinTask<?> job;
        if (task instanceof ForkJoinTask<?>) // avoid re-wrap
            job = (ForkJoinTask<?>) task;
        else
            job = new ForkJoinTask.RunnableExecuteAction(task);
        externalPush(job);
}
~~~

流式求和：

~~~java
public void test3(){
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 10_0000_0000).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("和:" + sum + " 时间:" + (end-start));
}
~~~

## 10、异步回调

> Future设计的初衷：对将来的某个事件的结果进行建模

异步和同步的区别，类似普通方法和同步方法的区别

继承关系：Future ——> **CompletableFuture**

~~~java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //无返回值的异步回调：runAsync
        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
            System.out.println("异步回调");
        });
        System.out.println("111111111");
        completableFuture.get();*/

        //有返回值的异步回调：supplyAsync
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println("哈哈哈");
            //int i = 9/0;
            return "异步回调";
        });

        // t:当正确执行时返回返回值，报错时返回null
        // u:当错误时返回错误信息，正确时返回null
        //exceptionally(e):e为异常，注意需要在该函数接口中重新返回一个值代替错误执行的返回值
        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t:" + t);
            System.out.println("u:" + u);
        }).exceptionally((e) -> {
            System.out.println("Message:" + e.getMessage());
            return "错误了啊";
        }).get());
    }
}
~~~

为什么代码这么奇怪？supplyAsync 和 runAsync 是 CompletableFuture 的静态方法，返回一个 CompletableFuture 对象

常用方法：

| 方法          | supplyAsync                          | runAsync                           |
| ------------- | ------------------------------------ | ---------------------------------- |
| get           | 执行supplier并获取返回值             | 执行runnable                       |
| exceptionally | 捕捉异常并返回错误回调               | 捕捉异常并作出反应                 |
| whenComplete  | 返回（T, U），T为返回值，U为错误信息 | 返回（T, U），T为null，U为错误信息 |

- 注意 get / exceptionally / whenComplete 方法阻塞但异步，不影响其他线程

异步回调：

1、成功回调 

当 CompletableFuture 正常跑 runAsync 或 supplyAsync 后，成功返回结果，称作成功回调

2、错误回调

当跑 runAsync 或 supplyAsync 时发生错误，我们用 exceptionally 捕捉异常并作出反应，在 supplyAsync 中可返回错误回调

## 11、JMM

> 请你谈谈对 Volatile 的理解

Volatile 时 java 虚拟机提供的轻量级的同步机制

1、保证可见性

2、不保证原子性

3、禁止指令重排

> 什么是JMM？可见性与其有什么关系

JMM ——> Java 内存模型（Java Memory Model），不存在的东西，概念、约定

**关于JMM的一些同步的约定：**

1、线程解锁前，必须把共享变量**立刻**刷回主存

- 在线程工作时，实际上是将主存中的变量拷贝一份到线程的工作内存中（值传递），对那份拷贝的变量进行操作，线程解锁前，必须将这份变量刷新回主存，使数据更新

2、线程加锁前，必须读取主存中的最新值到工作内存中

3、加锁和解锁是同一把锁

> 关于线程、主内存、工作内存的八个操作

- lock（锁定）：作用域主内存的变量，把一个变量表示为线程独占状态
- unlock（解锁）：作用于主内存的变量，它把一个处于锁定状态的的变量释放出来，释放后的变量可以被其他线程锁定
- read（读取）：作用于主内存变量，它把一个变量的值从主内存传输到线程的工作内粗那种
- load（载入）：作用于工作内存的变量，它把read操作从主存中变量放入工作内存中
- use（使用）：线程的执行引擎使用工作内存中的变量
- assign（赋值）：它把执行引擎接受到的值重新赋给那个变量
- store（存储）：它把工作内存中的一个变量传送到主存中
- write（写入）：它把store操作从工作内存中得到的变量的值放入主存的变量中

**JMM对这八种指令的使用，制定了如下规则：**

- 不允许read和load、store和write操作之一单独出行先，即read了必须load，store了必须write
- 不允许线程丢弃它最近的assign操作，即工作变量的数据改变了后必须告知主存
- 不允许一个线程将没有assign的数据从工作内存同步回主存
- 一个新变量必须从主存中诞生，不允许工作内存直接使用一个未被初始化的便改良，也就是说对变量实施use、store操作之前必须经过load和assign操作
- 一个变量同一时间只有一个线程能对其进行lock，多次lock（重入锁）后，必须执行相同次数的unlcok才能解锁
- 如果对同一变量进行lock操作，回清空所有工作内存中此变量的值，在执行引擎使用这个变量前，必须重新load或assign操作初始化变量的值
- 如果一个变量没有被lock，就不同对其进行unlock，也不能unlock一个被其他线程锁住的变量
- 对一个变量进行unlock操作之前，必须把此变量同步回主存

问题：当主存中变量更改后，线程的工作内存不可见这种改变，依旧在原来的基础上执行，这样可能会造成如下问题

~~~java
import java.util.concurrent.TimeUnit;

public class JMMTest {
    private static volatile int num = 0;

    public static void main(String[] args) {
        //main线程
        new Thread(()->{while(num==0){}}).start();

        try {
            TimeUnit.SECONDS.sleep(2);
            num = 1;
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
~~~

由于主线程睡眠 2 s，线程 T 读入 num 时，num == 0，即使在 main 线程中改变了 num 的值，线程 T 的工作内存中 num 仍为 0

线程 T 对主存的变化是 **不可见的**

## 12、Volatile

> 保证可见性

在上述代码中把 num 用 volatile 修饰后可解决线程对主存的**不可见性**问题

> 不保证原子性

什么是原子性？不可分割（ACID原则）

线程A在执行任务的时候，是不能被打扰的，也不能被分割。要么同时成功，要么同时失败

~~~java
public class VDemo01 {

    private static int num = 0;

    public static void add(){
        num++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while(Thread.activeCount()>2){
            Thread.yield();
        }

        System.out.println(num);
    }
}
~~~

预期结果为 20000，但实际结果总达不到 20000，由于有多个线程同时同步工作内存和主存同步的数据，造成两次 num++ 只有一次的效果，不满足原子性。

对 num 加上 volatile 修饰，仍无济于事

解决方案：

- 对 add 方法用 synchronized 修饰

  ~~~java
  public static synchronized void add(){num++;)
  ~~~

- 用 lock 锁把 add 方法锁住

  ~~~java
  private static Lock lock = new ReentrantLock();
  
  public static void add(){
      lock.lock();
      try{
          num++;
      }catch (Exception e){
          System.out.println(e.getMessage());
      }finally {
          lock.unlock();
      }
  }
  ~~~

- 使用原子类解决原子性问题

  ~~~java
  import java.util.concurrent.atomic.AtomicInteger;
  
  public class VDemo01 {
  
      private static volatile AtomicInteger num = new AtomicInteger(0);
  
      public static void add(){
          //调用了本地底层的CAS方法，保证原子性（在汇编中也仅有一步，保证原子性）
          num.getAndIncrement();
      }
  
      public static void main(String[] args) {
          for (int i = 0; i < 20; i++) {
              new Thread(()->{
                  for (int j = 0; j < 1000; j++) {
                      add();
                  }
              }).start();
          }
  
          while(Thread.activeCount()>2){
              Thread.yield();
          }
  
          System.out.println(num);
      }
  }
  ~~~

> 原子类：atomic

~~~java
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}
~~~

原子类的底层都直接和操作系统挂钩，在内存中修改值！Unsafe类是一个很特殊的存在

> 禁止指令重排

什么是指令重排？我们写的程序，计算机并不是按照我们写的那样去执行的

源代码 ——> 编译器优化的重排 ——> 指令并行也可能会重排 ——> 内存系统也会重排 ——> 执行

~~~java
int x = 1; 	// 1
int y = 2;	// 2
x = x + 5;	// 3
y = x * x;	// 4

我们所期望的：1234
也可能是：2134、1324
不可能是：4123
~~~

我们假设 a、b、x、y 这四个值默认都是0

| 线程A | 线程B |
| ----- | ----- |
| x=a   | y=b   |
| b=1   | a=2   |

经过线程A、B，正常的结果：x=0，y=0

对于单个线程A或者B，这两条语句的先后顺序似乎并不影响，于是编译器做了这样的指令重排：

| 线程A | 线程B |
| ----- | ----- |
| b=1   | a=2   |
| x=a   | y=b   |

指令重排导致的诡异结果：x=2，y=1

**volatile 利用底层的内存屏障可以避免指令重排**

1、保证特定的操作的执行顺序

2、可以保证某些变量的内存可见性（利用这些特性volatile实现了可见性）



## 13、深入单例模式

### 13.1、单例模式

> 单例模式是什么？

单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。

这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。

**注意：**

- 1、单例类只能有一个实例
- 2、单例类必须自己创建自己的唯一实例
- 3、单例类必须给所有其他对象提供这一实例

**意图：**保证一个类仅有一个实例，并提供一个访问它的全局访问点。

**主要解决：**一个全局使用的类频繁地创建与销毁。

**何时使用：**当您想控制实例数目，节省系统资源的时候。

**如何解决：**判断系统是否已经有这个单例，如果有则返回，如果没有则创建。

**关键代码：**构造函数是私有的。

**应用实例：**

- 1、一个班级只有一个班主任
- 2、Windows 是多进程多线程的，在操作一个文件的时候，就不可避免地出现多个进程或线程同时操作一个文件的现象，所以所有文件的处理必须通过唯一的实例来进行
- 3、一些设备管理器常常设计为单例模式，比如一个电脑有两台打印机，在输出的时候就要处理不能两台打印机打印同一个文件

**优点：**

- 1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）
- 2、避免对资源的多重占用（比如写文件操作）

**缺点：**没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化

**使用场景：**

- 1、要求生产唯一序列号
- 2、WEB 中的计数器，不用每次刷新都在数据库里加一次，用单例先缓存起来
- 3、创建的一个对象需要消耗的资源过多，比如 I/O 与数据库的连接等

**注意事项：**getInstance() 方法中需要使用同步锁 synchronized (Singleton.class) 防止多线程同时进入造成 instance 被多次实例化

### 13.2、enum

> enum 是什么？其本身也是一个Class类

### 13.3、饿汉式

线程安全，但会造成资源浪费

~~~java
//饿汉式单例模式
public class Hungry {
    private Hungry() {}

    private static Hungry HUNGRY = new Hungry();

    private byte[] data1 = new byte[1024*1024];
    private byte[] data2 = new byte[1024*1024];
    private byte[] data3 = new byte[1024*1024];
    private byte[] data4 = new byte[1024*1024];

    public static Hungry getInstance(){
        return HUNGRY;
    }

    public static void main(String[] args) {
        Hungry hungry = Hungry.getInstance();
        Hungry hungry1 = Hungry.getInstance();
        System.out.println(hungry.hashCode());
        System.out.println(hungry1.hashCode());
    }
}
~~~

### 13.4、懒汉式

线程不安全，多线程跑时将发生多个线程同时跑一个 LazyMan() 方法，即使返回同一个 LazyMan 对象。对 getInstance() 方法用 synchronized 修饰可以解决此问题，但会大大降低效率

~~~java
public class LazyMan {
    private LazyMan(){
        System.out.println(Thread.currentThread().getName() + " ok");
    }

    private static LazyMan LazyMan;

    public static LazyMan getInstance(){
        if(LazyMan==null){
            LazyMan = new LazyMan();
        }
        return LazyMan;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                LazyMan.getInstance();
            }).start();
        }
    }
}
~~~

### 13.5、DCL懒汉式

推荐使用

Double Check Lock，一般情况下线程安全，但不绝对安全

~~~java
DCLLAZYMAN = new DCLLayzMan();
~~~

这行代码并不是一个原子性操作

1. 分配内存空间
2. 执行构造方法，初始化对象
3. 把这个对象指向这块空间

**需要注意一定要令 DCLLAZYMAN 避免指令重排**

我们希望的顺序为123，但经过指令重排可能为132，如此在进行判断时，有可能 A 线程经过（3）把DACLLAZYMAN 指向了一块空间，B 线程此时判定 DCLLAZYMAN 不为 null 往下执行，直接返回 DCLLAZYMAN，而此时对象未被初始化，造成错误（即使这种概率非常非常小）

~~~java
public class DCLLazyMan {
    private DCLLazyMan(){
        System.out.println(Thread.currentThread().getName() + " ok");
    }

    private static DCLLazyMan DCLLAZYMAN;

    public static DCLLazyMan getInstance(){
        if(DCLLAZYMAN == null){
            synchronized (DCLLazyMan.class){
                if(DCLLAZYMAN == null){
                    DCLLAZYMAN = new DCLLazyMan();
                }
            }
        }
        return DCLLAZYMAN;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                DCLLazyMan.getInstance();
            }).start();
        }
    }
}
~~~

为了防止有人用反射破解该单例，即 setAccessable(true)，可在构造方法中加一把锁，同时抛出异常

~~~java
private DCLLazyMan(){
    synchronzed(DCLLazyMan.class){
        if(LAZYMAN != null){
            Throw new RuntimeException("不要试图用反射破坏单例");
        }
    }
    System.out.println(Thread.currentThread().getName() + " ok");
}
~~~

### 13.6、静态内部类

秀操作

~~~java
public class Holder {

    private Holder(){
        System.out.println(Thread.currentThread().getName() + " ok");
    }

    public static Holder getInstance(){
        return Inner.HOLDER;
    }

    private static class Inner{
        private static Holder HOLDER = new Holder();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                Holder.getInstance();
            }).start();
        }
    }
}
~~~

### 13.7、Enum

简单，较安全

~~~java
public enum EnumSingle {

    INSTANCE;

    public EnumSingle getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                //或用 EnucSingle.getInstance()，一样的
                System.out.println(EnumSingle.INSTANCE.hashCode());
            }).start();
        }
    }
}
~~~

## 14、深入理解CAS

> 什么是CAS？

CAS：compare and swap（比较交换）

cas是一种基于锁的操作，而且是乐观锁。在java中锁分为乐观锁和悲观锁。悲观锁是将资源锁住，等一个之前获得锁的线程释放锁之后，下一个线程才可以访问。而乐观锁采取了一种宽泛的态度，通过某种方式不加锁来处理资源，比如通过给记录加version来获取数据，**性能较悲观锁有很大的提高**

而像 synchronized、Lock、ReadWriteLock 均为悲观锁

原子类就利用了 CAS 操作实现其原子性，但需要注意的是 java 不能直接操作内存，但能直接操作 c++（navtive 方法），通过 c++ 来操作内存

- c++ 是 java 的后门

![image-20210801192553197](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210801192553197.png)

> Unsafe 类 ——> 一扇后门

点开 Unsafe 类可以发现其方法都是些 native 方法

![image-20210801192926744](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210801192926744.png)

**compareAndSet**

~~~java
public class CASDemo {
    public static void main(String[] args) {
        //初始化 AtomicInteger
        AtomicInteger atomicInteger = new AtomicInteger(2021);
        //达到 expect 后 update 数值，即当atomicInteger为2021时更新其为2017，否则更新失败返回false
        System.out.println(atomicInteger.compareAndSet(2021, 2017));
        System.out.println(atomicInteger);
    }
}
~~~

~~~java
public final boolean compareAndSet(int expect, int update) {
    return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
}
~~~

~~~java
public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
~~~

**getAndIncrement**

~~~java
import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        //初始化 AtomicInteger
        AtomicInteger atomicInteger = new AtomicInteger(2017);
        //加一
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger);
    }
}
~~~

~~~java
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}
~~~

~~~java
// 在 AtomicInteger 的 getAndIncrement 方法中 ——> var1=AtomicInteger.class（地址），var2=AtomicInteger.get()（当前值），var4=1
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    //自旋锁
    do {
        //获取当前值：从地址上读取
        var5 = this.getIntVolatile(var1, var2);
    }
    //与上类似，执行一个CAS(比较交换)函数：var1和var2确定当前值(地址+长度)，var5为其先记录的当前值，当二者相同时，将自身替换为var5+var4(1)
    while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

    return var5;
}
~~~

缺点：

1、循环会耗时

2、一次性只能保证一个共享变量的原子性

3、会引起ABA问题

> CAS ——> ABA问题

在以下情形，线程1并不会知道变量A已经被线程2动过了，只会继续执行它的CAS操作，这样是不对的（狸猫换太子）

![image-20210801214045149](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210801214045149.png)

## 15、原子引用

在 java 中，为了解决上述问题，引入原子引用类：

~~~java
AtomicStampedReference<V>
~~~

![image-20210802152221674](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210802152221674.png)

该类用版本号的方式防止ABA问题，即每次cas操作后手动令版本号（stamp）加一，若线程中事先获得的版本号与当前类版本号不符，则无法实现cas操作

不符是指此处在线程 A 中事先记录的 stamp 与 atomicReference.getStamp() 不符，即版本号不符，无法进行 cas 操作

~~~java
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CASDemo02 {
    public static void main(String[] args) throws InterruptedException {
        AtomicStampedReference<Integer> atomicReference = new AtomicStampedReference<>(121, 0);

        new Thread(()->{
            int stamp = atomicReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(121, 1, stamp, atomicReference.getStamp() + 1));
        }, "A").start();

        new Thread(()->{
            System.out.println(atomicReference.compareAndSet(121, 95, atomicReference.getStamp(), atomicReference.getStamp() + 1));
            System.out.println(atomicReference.compareAndSet(95, 121, atomicReference.getStamp(), atomicReference.getStamp() + 1));
        }, "B").start();

        TimeUnit.SECONDS.sleep(3);
        System.out.println(atomicReference.getReference());
    }
}
~~~

## 16、各种锁的理解

### 16.1、公平锁和非公平锁

1、公平锁：很公平的锁，不可以插队，必须先来后到

2、非公平锁：不公平的锁，可以插队（默认都是非公平）

~~~java
Lock lock = new ReentrantLock();

public ReentrantLock() {
    sync = new NonfairSync();
}
~~~

~~~java
Lock lock = new ReentrantLock(true);

public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
~~~

### 16.2、可重入锁（递归锁）

所有的锁都是可重入锁

只要拿到了外面的锁，就自动拿到了内部的锁，如一个同步方法中调用了另一个同步方法，在单个线程拿到外部方法的锁时，也自动同时拿到了内部同步方法的锁

> synchronized

~~~java
public class Demo01 {
    public static void main(String[] args) {
        Phone01 phone = new Phone01();

        new Thread(()->{
            phone.sendMs();
        }, "A").start();

        new Thread(()->{
            phone.call();
        }, "B").start();
    }
}

class Phone01{
    public synchronized void sendMs(){
        call();
        System.out.println(Thread.currentThread().getName() + " 发短信");
    }

    public synchronized void call(){
        System.out.println(Thread.currentThread().getName() + " 打电话");
    }
}
~~~

> Lock

~~~java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo02 {
    public static void main(String[] args) {
        Phone02 phone02 = new Phone02();

        new Thread(()->{
            phone02.sendMs();
        }, "A").start();

        new Thread(()->{
            phone02.sendMs();
        }, "B").start();
    }
}

class Phone02{
    private Lock lock = new ReentrantLock();

    public void sendMs(){
        lock.lock();
        try{
            call();
            System.out.println(Thread.currentThread().getName() + " 发短信");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }

    public void call(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + " 打电话");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }
    }
}
~~~

注意：Lock 锁的 lock 和 unlock 必须配对，否则就会死锁

### 16.3、自旋锁

Spin Lock

AtomicInteger 的 getAndAddInt 方法正是用的自旋锁

- do{} while() 循环

![image-20210802164810192](C:\Users\NorthBoat\AppData\Roaming\Typora\typora-user-images\image-20210802164810192.png)



### 16.4、死锁













