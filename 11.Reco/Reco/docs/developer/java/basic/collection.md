---
title: Generic & Collection
date: 2021-2-20
tags:
  - Java
---

## 集合类

### 泛型

> Generic

#### 泛型接口

~~~java
interface MyInterface<T> {
    //接口可创建静态常量
    String name = "NorthBoat";

    void build(T t);

    void show();
}

class MyInterfaceImpl1 implements MyInterface<String> {
    private String str;

    @Override
    public void build(String s) {
        str = s;
    }


    @Override
    public void show() {
        System.out.println(str.toString());
    }
}

class MyInterfaceImpl2<T> implements MyInterface<T> {
    T skt;

    @Override
    public void build(T t) {
        skt = t;
    }

    @Override
    public void show() {
        System.out.println(skt);
    }
}
~~~

#### 泛型类

~~~java
class MyGeneric<T> {

    T t;

    void setT(T t1) {
        t = t1;
    }

    void show() {
        System.out.println(t.toString());
    }
}
~~~

### Collection 

#### List

有序、有下标

##### ArrayList

内部维护一个数组（查找快，增删慢）

默认初始容量：10

add方法：当达到数组上限时创建一个1.5倍大小的新数组，用System中arrayCopy方法复制原来元素至新数组

remove方法：定位要删除元素，创建新数组，去掉要删除元素

get方法：获取对应下标的元素

isEmpty()

contains(Object obj)

listIterator：list的迭代器

~~~java
 ListIterator lt = list.listIterator();
 while(lt.hasNext()){
      System.out.println(lt.next().toString());
 }
~~~

##### Vector

内部同样维护一个数组

有独有的枚举器用于遍历：Enumeration en = vector.elements();

##### LinkedList

内部维护一个双向链表（查找慢，增删快）





#### Set

**无序、无下标、无重复元素**

Set常用方法（与List基本相同）：

1、add()

2、size()

3、isEmpty()

4、remove()

5、iterator()

6、contains()

##### HashSet

内部维护一个哈希表（数组+链表+红黑树）

利用hashcode()和equals()实现不重复

##### TreeSet

1、存储结构：红黑树——>中序遍历 (二叉搜索)

2、基于排序顺序实现元素不重复

3、实现SortedSet接口，对集合元素自动排序

4、排序规则

4.1、用Comparable接口中CompareTo()方法作为排序标准：在类中重写，当 CompareTo() 返回值为0时，判断两者相同

4.2、利用TreeSet含Comparator接口 (比较器) 的构造方法，在Comparator的匿名内部类中重写compare() 方法，实现比较规则的定义

~~~java
TreeSet<String> tree = new TreeSet<>(new Comparator<String>(){
        @Override
        public int compare(String s1, String s2){

            int n1 = s1.length()-s2.length();
            int n2 = s1.compareTo(s2);

            return n1==0?n2:n1;
        }
    });
~~~





### Map

**Interface**

Map常用方法：

- put (k key, v value)
- remove(k key)
- keySet() :返回存有key值的set集合

- entrySet(): 返回存有entry值的set集合(效率高于keySet())

- size(): 返回键值对个数

- containsKey(k key): 是否存在key键

- containsValue(v value): 是否存在value值

Entry: Map中的静态内部类，储存key和value的键值对 <key, value>

Entry常用方法：getKey() / getValue()

#### HashMap

使用

~~~java
/**
 * 存储结构：哈希表
 * 默认初始容量：16(1<<4)	加载因子：0.75（扩容比例）
 * 存储结构：哈希表（数组+链表+红黑树）
 * 线程不安全，效率高，允许null作为键和值
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class TestHashMap {
    public static void main(String[] args) {

        HashMap<Student, Integer> hashmap = new HashMap<>();

        Student s1 = new Student("Rose", 32);
        Student s2 = new Student("Harden", 33);
        Student s3 = new Student("Wade", 38);

        hashmap.put(s1, 1);
        hashmap.put(s2, 13);
        hashmap.put(s3, 3);
        //覆盖了第一个s1
        hashmap.put(s1, 25);
        //加进了hashmap ——> 改写掉Student的hashcode和equals方法规定重复规则 ——> 又覆盖了s1
        hashmap.put(new Student("Rose", 32), 4);

        System.out.println("元素个数：" + hashmap.size());
        System.out.println(hashmap.toString());

        //删除
        hashmap.remove(s3);
        System.out.println(hashmap.toString());
        System.out.println("-------------");

        //遍历(重点)
        for(Student stu: hashmap.keySet()){
            System.out.println(stu.toString() + ", " + hashmap.get(stu));
        }
        System.out.println("-------------");

        for(Map.Entry<Student, Integer> entry: hashmap.entrySet()){
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println("-------------");

        //判断
        System.out.println(hashmap.containsKey(s1));
        System.out.println(hashmap.containsValue(1));

    }
}
~~~

基本属性

~~~java
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // 16

static final int MAXIMUM_CAPACITY = 1 << 30;

static final float DEFAULT_LOAD_FACTOR = 0.75f;

static final int TREEIFY_THRESHOLD = 8;

static final int UNTREEIFY_THRESHOLD = 6;

static final int MIN_TREEIFY_CAPACITY = 64;

Node<K, V> next;//单向指针

transient Node<K,V>[] table;//数组

transient int size;//元素个数
~~~

方法分析

~~~java
//构造方法
//刚创建好之后table为null，size为0——>节省空间



//put(k key, v value)方法
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}



//resize()方法节选
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        //移位运算，当容量达到75%，oldCap容量*2赋给newCap
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    //当oldCap为空，将初始容量(16)赋给newCap
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
}
~~~

源码总结

- 初始容量：1<<4 (16)
- 最大容量：1<<30 (2的30次方)

- 当容量达到75％时开始扩容

- 当链表(单向)长度大于8并且数组长度大于64时，开始形成红黑树——>提高查找效率

- 当链表长度小于6时，将红黑树重新展开为链表

- jdk1.8之前，链表为头插入，1.8之后为尾插入

HashMap和HashSet

- HashSet内部维护了一个HashMap


#### Hashtable

存储结构：哈希表

可以用枚举器、keySet、entrySet遍历

#### Properties

Properties为Hashtable的子类，要求key和value均为String类

与"流"密切相关

#### TreeMap

存储结构：红黑树

可以对key进行自动排序，与TreeSet类似，同样要求实现Comparable接口中compareTo方法，或使用Comparator的匿名内部类定制比较器

~~~java
//实现Comparable接口
public class Student implements Comparable<Student>{

    private String name;
    private int age;

@Override
    public int compareTo(Student o) {
        int n1 = this.name.compareTo(o.getName());
        int n2 = this.age-o.getAge();
        return n1==0?n1:n2;
    }
}

//定制比较器
        TreeMap<Teacher, String> treemap = new TreeMap<>(new Comparator<Teacher>(){
            @Override
            public int compare(Teacher o1, Teacher o2) {
                int n1 = o1.getName().compareTo(o2.getName());
                int n2 = o1.getAge()- o2.getAge();
                return n1==0?n1:n2;
            }
        });
~~~

与TreeSet关系：在TreeSet内部维护了一个NavigableMap对象m，即TreeMap，TreeSet的add方法实际上调用了m的put方法......

## Collections工具类

实际上由Collection中一系列静态方法组成

### sort

对集合升序排列，必须实现Comparable接口

~~~java
//sort()排序
Collections.sort(list);
System.out.println(list.toString());
~~~

### binarySearch

二分查找，返回元素位置

~~~java
//binarySearch()二分查找:返回下标   必须要sort后才能二分查找
int i = Collections.binarySearch(list, 95);
System.out.println(i);
~~~

### copy

~~~java
//copy(dest, sec)复制    必须要集合大小一样才能复制
List<Integer> dest = new ArrayList<>();
for(int j = 0; j < list.size(); j++){
    dest.add(j);
}
Collections.copy(dest, list);
System.out.println(dest.toString());
~~~

### reverse

~~~java
 //reverse(list)反转
Collections.reverse(list);
System.out.println(list.toString());
~~~

### shuffle

洗牌：打乱元素顺序

~~~java
//shuffle(list)洗牌：将元素顺序打乱
Collections.shuffle(list);
System.out.println(list.toString());
~~~

### list.toArray

集合转成数组

~~~java
//集合转成数组
Integer[] arr = list.toArray(new Integer[0]);
for(int z = 0; z < arr.length; z++){
    System.out.println(arr[z]);
}
~~~

### asList

数组转成集合

~~~java
 //数组转成集合
        String[] str = {"张三", "李四", "王五"};
        List<String> list1 = Arrays.asList(str);
        System.out.println(list1.toString());
~~~

该集合是一个受限集合，不能进行添加和删除

把基本类型转化成集合时，要把其修改为包装类