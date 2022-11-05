---
title: 环境搭建和基础语法
date: 2021-5-15
tags:
  - Python
---

## 环境搭建

用Anaconda+VSCode搭建python环境

### Anaconda

检查是否安装成功

~~~bash
conda --version
~~~

进入anaconda环境

~~~bash
conda activate
~~~

创建Anaconda的Python子环境，子环境均安装再anaconda3\envs目录下

创建3.6.6python子环境（-n：环境名称）：

~~~bash
conda create -n test python=3.6.6
~~~

切换到子环境

~~~bash
conda activate test
~~~

退出子环境

~~~bash
aonda deactivate
~~~

### VSCode

插件：

- Python

- YAML
- Jupyter

- Pylance

配置python路径：

File ——> Preferences ——> Settings 里面，选择Extensions ——> Python Configuration，点击 Edit in settings.json，配置Anaconda子环境中的python路径

~~~json
"python.autoComplete.extraPaths":[
	"D:\\Anaconda\\envs\\test\\python.exe"
]
~~~

## 常用函数

### 计算

| 函数                                                         | 返回值 ( 描述 )                                              |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [abs(x)](https://www.runoob.com/python/func-number-abs.html) | 返回数字的绝对值，如abs(-10) 返回 10                         |
| [ceil(x)](https://www.runoob.com/python/func-number-ceil.html) | 返回数字的上入整数，如math.ceil(4.1) 返回 5                  |
| [cmp(x, y)](https://www.runoob.com/python/func-number-cmp.html) | 如果 x < y 返回 -1, 如果 x == y 返回 0, 如果 x > y 返回 1    |
| [exp(x)](https://www.runoob.com/python/func-number-exp.html) | 返回e的x次幂(ex),如math.exp(1) 返回2.718281828459045         |
| [fabs(x)](https://www.runoob.com/python/func-number-fabs.html) | 返回数字的绝对值，如math.fabs(-10) 返回10.0                  |
| [floor(x)](https://www.runoob.com/python/func-number-floor.html) | 返回数字的下舍整数，如math.floor(4.9)返回 4                  |
| [log(x)](https://www.runoob.com/python/func-number-log.html) | 如math.log(math.e)返回1.0,math.log(100,10)返回2.0            |
| [log10(x)](https://www.runoob.com/python/func-number-log10.html) | 返回以10为基数的x的对数，如math.log10(100)返回 2.0           |
| [max(x1, x2,...)](https://www.runoob.com/python/func-number-max.html) | 返回给定参数的最大值，参数可以为序列。                       |
| [min(x1, x2,...)](https://www.runoob.com/python/func-number-min.html) | 返回给定参数的最小值，参数可以为序列。                       |
| [modf(x)](https://www.runoob.com/python/func-number-modf.html) | 返回x的整数部分与小数部分，两部分的数值符号与x相同，整数部分以浮点型表示。 |
| [pow(x, y)](https://www.runoob.com/python/func-number-pow.html) | x**y 运算后的值。                                            |
| [round(x n](https://www.runoob.com/python/func-number-round.html) | 返回浮点数x的四舍五入值，如给出n值，则代表舍入到小数点后的位数。 |
| [sqrt(x)](https://www.runoob.com/python/func-number-sqrt.html) | 返回数字x的平方根                                            |

### 随机数

需要导包：`import random`

| 函数                                                         | 描述                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [choice(seq)](https://www.runoob.com/python/func-number-choice.html) | 从序列的元素中随机挑选一个元素，比如random.choice(range(10))，从0到9中随机挑选一个整数。 |
| [randrange (start,stop, step)](https://www.runoob.com/python/func-number-randrange.html) | 从指定范围内，按指定基数递增的集合中获取一个随机数，基数默认值为 1 |
| [random()](https://www.runoob.com/python/func-number-random.html) | 随机生成下一个实数，它在[0,1)范围内。                        |
| [seed(x)](https://www.runoob.com/python/func-number-seed.html) | 改变随机数生成器的种子seed。如果你不了解其原理，你不必特别去设定seed，Python会帮你选择seed。 |
| [shuffle(lst)](https://www.runoob.com/python/func-number-shuffle.html) | 将序列的所有元素随机排序                                     |
| [uniform(x, y)](https://www.runoob.com/python/func-number-uniform.html) | 随机生成下一个实数，它在[x,y]范围内。                        |

### 三角函数

| 函数                                                         | 描述                                              |
| :----------------------------------------------------------- | :------------------------------------------------ |
| [acos(x)](https://www.runoob.com/python/func-number-acos.html) | 返回x的反余弦弧度值。                             |
| [asin(x)](https://www.runoob.com/python/func-number-asin.html) | 返回x的反正弦弧度值。                             |
| [atan(x)](https://www.runoob.com/python/func-number-atan.html) | 返回x的反正切弧度值。                             |
| [atan2(y, x)](https://www.runoob.com/python/func-number-atan2.html) | 返回给定的 X 及 Y 坐标值的反正切值。              |
| [cos(x)](https://www.runoob.com/python/func-number-cos.html) | 返回x的弧度的余弦值。                             |
| [hypot(x, y)](https://www.runoob.com/python/func-number-hypot.html) | 返回欧几里德范数 sqrt(x*x + y*y)。                |
| [sin(x)](https://www.runoob.com/python/func-number-sin.html) | 返回的x弧度的正弦值。                             |
| [tan(x)](https://www.runoob.com/python/func-number-tan.html) | 返回x弧度的正切值。                               |
| [degrees(x)](https://www.runoob.com/python/func-number-degrees.html) | 将弧度转换为角度,如degrees(math.pi/2) ， 返回90.0 |
| [radians(x)](https://www.runoob.com/python/func-number-radians.html) | 将角度转换为弧度                                  |

数学常量：

| 常量 | 描述                                  |
| :--- | :------------------------------------ |
| pi   | 数学常量 pi（圆周率，一般以π来表示）  |
| e    | 数学常量 e，e即自然常数（自然常数）。 |

### 类型转换

| 函数                                                         |                        描述                         |
| :----------------------------------------------------------- | :-------------------------------------------------: |
| int(x [,base\])                                              |                  将x转换为一个整数                  |
| long(x [,base\] )                                            |                 将x转换为一个长整数                 |
| float(x)                                                     |                 将x转换到一个浮点数                 |
| [complex(real ,imag)](https://www.runoob.com/python/python-func-complex.html) |                    创建一个复数                     |
| str(x)                                                       |                将对象 x 转换为字符串                |
| repr(x)                                                      |             将对象 x 转换为表达式字符串             |
| eval(str)                                                    | 用来计算在字符串中的有效Python表达式,并返回一个对象 |
| tuple(s)                                                     |               将序列 s 转换为一个元组               |
| list(s)                                                      |               将序列 s 转换为一个列表               |
| set(s)                                                       |                   转换为可变集合                    |
| dict(d)                                                      |  创建一个字典。d 必须是一个序列 (key,value)元组。   |
| frozenset(s)                                                 |                  转换为不可变集合                   |
| chr(x)                                                       |              将一个整数转换为一个字符               |
| unichr(x)                                                    |             将一个整数转换为Unicode字符             |
| ord(x)                                                       |             将一个字符转换为它的整数值              |
| hex(x)                                                       |         将一个整数转换为一个十六进制字符串          |
| oct(x)                                                       |          将一个整数转换为一个八进制字符串           |

### 字符串

获取长度

~~~python
length = len(cur)
~~~

分割字符串

~~~python
#通过空格分割字符串str为字符串数组
str.split(" ")
~~~

数字转字符串，字符串的拼接只能在字符串之间，若想拼接字符串和数字，需要先用str函数将数字转为字符串

~~~python
str(number)
~~~

### 输入输出

输入函数input可以附带提示信息

~~~python
str = input('请输入字符串:')
~~~

输出并选择后缀

~~~python
print("hahaha", end='\t')
~~~

### ASCII码

字符转ascii码

~~~python
ord(char)
~~~

ascii码转字符

~~~python
chr(ascii)
~~~

### URL

~~~python
import urllib
import urllib.parse
~~~

详见[文档](https://docs.python.org/3/library/urllib.parse.html)

## 特殊之处

### 运算符

身份运算：

- is
- is not

成员运算：相当于`instanceof`

- in
- not in

算术运算

- //：向下取余
- %：向上取余（一般用这个）
- **：幂运算

逻辑运算

- and
- or
- not

### 字符串

用[ : ]截取字符串：

- string[n:m]：可用于截取从n到m的子字符串（字符下标从0开始）
- string[n:]：截取从n开始到结束的子字符串

~~~python
sentence = "我打你妈的"
print(sentence[2:5])
#将会输出 “你妈的”
~~~

注意：截取字符串时，将从下标为n开始，到下标为m前一个元素结束，即含头不含尾

### return和yield

return是阻塞的，单线程的，和Java中return保持一致

而yield是非阻塞的，如下列函数通过yield返回

~~~python
def user_input():
    while True:
        s = input()
        if not s:
            return
        yield s

for line in map(str.upper, user_input()):
    print(line)
~~~

yield非阻塞，这意味着获得值后，将新开辟一条线程立即返回，继续执行之后的代码，同时这个函数继续运行

如上述代码的执行过程为

~~~
hello
HELLO
world
WORLD
~~~

而非

~~~
hello
world
HELLO
WORLD
~~~

### 类的书写

self

类的方法与普通的函数只有一个特别的区别——它们必须有一个额外的**第一个参数名称**, 按照惯例它的名称是 self，代表类的实例，而非类本身

~~~python
class Test:
    def prt(self):
        print(self)
        print(self.__class__)
 
t = Test()
t.prt()
~~~

类函数的定义

头尾双下划线、单下划线、双下划线

1、`__func__`：定义的是特殊方法，一般是系统定义名字 ，类似 **__init__()** 之类的

2、`_foo`：以单下划线开头的表示的是 protected 类型的变量，即保护类型只能允许其本身与子类进行访问，不能用于 from module import 

3、`__foo`：双下划线的表示的是私有类型(private)的变量, 只能是允许这个类本身进行访问了

类的继承

~~~python
class Parent:        # 定义父类
   parentAttr = 100
   def __init__(self):
      print "调用父类构造函数"
 
   def parentMethod(self):
      print '调用父类方法'
 
   def setAttr(self, attr):
      Parent.parentAttr = attr
 
   def getAttr(self):
      print "父类属性 :", Parent.parentAttr
 
class Child(Parent): # 定义子类
   def __init__(self):
      print "调用子类构造方法"
 
   def childMethod(self):
      print '调用子类方法'
 
c = Child()          # 实例化子类
c.childMethod()      # 调用子类的方法
c.parentMethod()     # 调用父类方法
c.setAttr(200)       # 再次调用父类的方法 - 设置属性值
c.getAttr()          # 再次调用父类的方法 - 获取属性值
~~~

### 不定长参数

带星号的参数即为不定长参数（名字随意）

~~~python
def printinfo( arg1, *vartuple ):   
    "打印任何传入的参数"   
    print "输出: "   
    print arg1   
    for var in vartuple:      
        print var   
        return  
# 调用printinfo 函数 
printinfo( 10 ) printinfo( 70, 60, 50 )
~~~

