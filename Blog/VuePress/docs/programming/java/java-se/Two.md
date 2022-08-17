# Java后端学习第二周



## 1、个人博客(Hexo)

[我的第一篇博客](NorthBoat.github.io)

~~~
初始化博客：hexo init
    
启动预览：hexo s(start)

创建博文：hexo n(new) "我的第一篇博客文章"
    
编写博文"我的第一篇博客文章.md"

清理文件夹：hexo clean
   
生成：hexo g(generate)
    
将本地博客布署在GitHub(配置好插件以及deploy后)：
hexo d(deploy)
    
更换主题：将主题下载到themes文件夹，修改_config.yml中themes配置
~~~





## 2、Java基础



### 2.1、常用类

#### 1、内部类

成员内部类：与成员属性同级

静态内部类：和外部类同级，但要通过外部类创建

局部内部类：存在于外部类的方法中

匿名内部类：在实现接口或使用抽象类时使用

#### 2、Object类

简介：父类、基类(祖宗类)，所有类直接或间接继承于Object

常用方法：

1、getClass:返回当前类型的Class类

2、hashCode:返回当前对象的哈希值

3、toString:返回当前类的字符串描述(多重写)

4、equals:

~~~java
equals改写：判断类中各属性相同即相等
    
@Override
public boolean equals(Object obj) {
        //若引用同一个对象，直接返回true
        if (this == obj) return true;
        //判断o是否为空
        if (obj == null) return false;
        //判断类型是否一致
        if(obj instanceof Student) {
            //强制转换类型
            Student student = (Student) obj;
            //比较属性
            if (this.age == student.getAge() && this.name.equals(student.getName())) {
                return true;
            }
        }
        return false;
    }
~~~

(instanceof关键字：判断前者类型是否直接或间接属于后者)

5、finalize:垃圾(不用的对象)回收方法 (可重写)

#### 3、包装类(Integer)与类型转换

1、装箱与拆箱

概念：装箱即把基本类型变量转换为引用类型变量，将存放在栈中的数	   	据转移到堆中，完成基本类型到对象的转换。拆箱即把引用类型	       转化为基本类型



实现：1、拆箱：调用Number类中的  ----Value()方法

​			2、装箱：调用包装类的构造方法，如 Integer(int value)

​							  调用 valueOf(int value) 方法 (返回值为Integer对象)

~~~java
自动拆装箱：
    
int num4 = 10;
        //底层实现valueOf()方法，即Integer integer4 = Integer.valueOf(num4)
        Integer integer4 = num4;
        //底层实现intValue()方法，即int num5 = integer4.intValue()
        int num5 = integer4;
~~~

2、字符串与数字类型转换

使用包装类中toString方法和parseXXX方法进行转换



3、Integer.valueOf(int i)方法的缓冲区

为了减少内存的消耗，在value()方法内部存在一个数组，当传入的 i 在数组范围时，方法将直接从数组中提出对应的i赋给新的Integer对象，这样new出来的Integer实际上指向的是堆中的同一片区域，即数组中 i 的地址。当 i 超出缓冲区时，将重新在堆中开辟一个空间赋给Integer对象（Integer缓冲区为[-128,127]）



#### 4、String类

1、常用方法

```java
1、length():返回字符串长度
2、charAt(int index):返回某个位置的字符
3、contains(String str):判断是否包含某个子字符串

4、toCharArray():将字符串转换为字符数组
5、indexOf(String str):查找str首次出现的下标索引 (index:索引)
6、lastIndexOf(String str):查找str最后一次出现的下表索引

7、trim():去掉字符串前后的空格 (trim:修剪)
8、toUpperCase():将小写转成大写 (upper:上面的、上部的、较高的)
9、endWith(String str):判断字符串是否以str结尾

10、replace(char oldChar, char newChar):将旧字符(串)替换为新字符(串)
11、String[] split(String str):根据str做拆分

12、equals(String str):比较两个字符串值是否相等
13、equalsIgnoreCase(String str):忽略大小写比较两个字符串值是否相等
14、compareTo(String str):优先返回首个ASCII码不同的字符的ASCII码大小的差，若原字符串是str从前向后的子串，则返回两个字符串长度的差(前减后)
15、substring(int begin, int end)/substring(int begin):截取字符串，从第begin个字符截取到第end个字符或从begin截取到最后
```



2、可变字符串(较于重新在池中储存字符串更节省内存、更高效)

~~~java
//StringBuffer:效率较高，线程安全
//StringBuilder:效率更高，线程不太安全(单线程随便搞)

//常用方法
append();	//追加
insert(int start);	//插入
delete(int start, int end);	//删除
reverse();	//翻转
replace(int start, int end, String str);	//替换
~~~







#### 5、BigDecimal类

原因：double、float类型储存的是近似值，在精确计算时会出较大偏差，需要一个高精度类来进行计算

方法:

1、add(BigDecimal bd):加法 

2、substract(BigDecimal bd):减法(前减后)

3、multiply(BigDecimal bd):乘法

4、divide(BigDecimal bd):除法(前除以后)



注:

当除法碰到除不尽的情况，使用其重载 divide (BigDecimal bd, int scal, RoundingMode mode)。其中，scal为精确位数，RoundingMode为取舍模式 (一般为四舍五入: ROUND_HALF_UP)



#### 6、时间类型

##### 6.1、Date类

常用方法:

0、Date() / Date(long date):构造方法，前者获取当前时间，后者获取输入时间(以毫秒为单位)

1、void toString():打印字符串时间

2、void toLocalString():以中文习惯打印(已过时)

3、int getTime():返回当前Date对象的时间

4、int compareTo(Date d):返回前后天数之差

5、boolean equals(Date d):判断两个时间是否相等

6、boolean before/after(Date d):判断当前Date是否在d之前(后)



##### 6.2、Calendar类

1、Calendar.instance():构造方法被保护，使用该静态方法构造Calendar对象

2、Date getTime():返回一个Date对象，与原时间相同

3、int get(field f):获取诸如年、月、日、小时、分钟、秒等时间

4、void add():在原对象上添加或减少时间

5、void set(field f, int d):设置时间，field设置时间类型(年月日时分秒)，d设置时间长度

6、int getActualMaximum(field f) / int getActualMinimum(field f):返回f类型在该时间的最大值，如三月的最大天数为31



##### 6.3、SimpleDateFormat类

~~~java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {
    public static void main(String[] args) {
        //创建SimpleDateFormat对象，限定格式
        //y代表年，M代表月，d代表天，H代表小时，m代表分钟，s代表秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        //创建Date对象
        Date date1 = new Date();
        //格式化Date，把时间转化为字符串
        String str = sdf.format(date1);
        System.out.println(str);

        Date date2 = new Date();
        //把字符串转化为日期
        try{
            date2 = sdf.parse("1990年12月12日");
        }
        catch(ParseException p){
            System.out.println("捕获到异常");
        }finally{
            System.out.println(date2.toLocaleString());
        }
    }
}
~~~





#### 7、System类

常用方法：

0、构造方法私有

1、static void arraycopy():复制数组

2、static long currentTimeMillis():获取当前系统时间

3、static void gc():建议jvm启动垃圾回收器回收垃圾

4、static void exit(int status):手动退出jvm，如果参数是0表示正常退出jvm，非0则异常退出jvm



### 2.2、集合框架

##### Collection(interface)

常用方法：(用ArrayList实例化)

1、size(): 返回元素个数

2、add(Object obj): 添加元素

3、remove(Object obj): 删除元素

4、clear(): 清空容器

5、toString(): 返回元素的字符串表现形式

6、equals(Object obj): 判断两个容器是否相等

7、isEmpty(): 判断容器是否为空

7、Iterator(): 返回当前容器的迭代器



遍历方法：

1、用增强版的for循环(Collection无下标：不能用for) : for(Object obj: arraylist){}

2、用迭代器遍历(专门用来遍历集合的一种方式):

~~~java
Iterator it = collection.iterator();//创建迭代器
while(it.hasNext){
    Student s = (Student)it.next();//类型强制转换(Obj到具体类)
    sout(s.toString);//打印
}
~~~

Iterator方法：

1、hasNext():判断容器中是否有下一个元素

2、next(): 返回容器中下一个元素

3、remove(): 把当前元素删除



###### List(interface)

1、ArrayList(class)

2、LinkedList(class)

3、Vector(class)



###### Set(interface)

1、HashSet(class)

2、SortedSet(interface) ——>TreeSet(class)













