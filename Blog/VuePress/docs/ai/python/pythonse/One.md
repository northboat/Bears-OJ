# Python基础第一周

> python 3

## 一、环境搭建

用Anaconda+VSCode搭建python环境

### 1、配置Anaconda

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

### 2、配置VSCode

所需插件：

1、Python

2、YAML

3、Jupyter

4、Pylance

配置python路径：

File ——> Preferences ——> Settings 里面，

选择Extensions ——> Python Configuration，

点击 Edit in settings.json，配置Anaconda子环境中的python路径

~~~json
"python.autoComplete.extraPaths":[
	"D:\\Anaconda\\envs\\test\\python.exe"
]
~~~

## 二、基础语法

1、以新行作为结束符

2、无需在定义变量时先声明类型

3、if/else 语句不用括号将条件括住，在 if/else 后需用冒号作为 if 语句的开始

4、一个 # 为单行注释，前后三个 " 为多行注释

5、同一行显示多条语句，语句间用分号隔开

6、多行语句可用 \（换行符）连接

7、print()、input() 分别为输出输入函数（2.0为raw_input），在print()后加上逗号可取消其默认换行

~~~python
#注释
"""
多行注释
"""

num = 9
str = "wdnmd"

c = 'c'
a = 1; b = 4; c = 7
sum = a + \
	  b + \
      c
        
if sum > 4:
    print("哈哈哈哈哈")
elif sum == 4:
    print("2084四组")
else:
    input("请输入：")
~~~

## 三、变量类型

### 1、变量赋值

~~~python
#在python中，这样的赋值是被允许的
a = b = c = 4
~~~

~~~python
#这样的赋值也是可以的
a, b, c = 4, 5.2, "john"
~~~

### 2、数据类型

#### 2.1、**Numbers（数字）**

- int（有符号整型）
- long（长整型[也可以代表八进制和十六进制]）
- float（浮点型）
- complex（复数）

**del value** 用于删除引用

**math 模块：**

~~~python
import math
~~~

**数学函数：**

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
| [round(x [,n\])](https://www.runoob.com/python/func-number-round.html) | 返回浮点数x的四舍五入值，如给出n值，则代表舍入到小数点后的位数。 |
| [sqrt(x)](https://www.runoob.com/python/func-number-sqrt.html) | 返回数字x的平方根                                            |

**随机数函数：**

随机数可以用于数学，游戏，安全等领域中，还经常被嵌入到算法中，用以提高算法效率，并提高程序的安全性。

Python包含以下常用随机数函数：

| 函数                                                         | 描述                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [choice(seq)](https://www.runoob.com/python/func-number-choice.html) | 从序列的元素中随机挑选一个元素，比如random.choice(range(10))，从0到9中随机挑选一个整数。 |
| [randrange ([start,\] stop [,step])](https://www.runoob.com/python/func-number-randrange.html) | 从指定范围内，按指定基数递增的集合中获取一个随机数，基数默认值为 1 |
| [random()](https://www.runoob.com/python/func-number-random.html) | 随机生成下一个实数，它在[0,1)范围内。                        |
| [seed([x\])](https://www.runoob.com/python/func-number-seed.html) | 改变随机数生成器的种子seed。如果你不了解其原理，你不必特别去设定seed，Python会帮你选择seed。 |
| [shuffle(lst)](https://www.runoob.com/python/func-number-shuffle.html) | 将序列的所有元素随机排序                                     |
| [uniform(x, y)](https://www.runoob.com/python/func-number-uniform.html) | 随机生成下一个实数，它在[x,y]范围内。                        |

**三角函数：**

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

**常量：**

| 常量 | 描述                                  |
| :--- | :------------------------------------ |
| pi   | 数学常量 pi（圆周率，一般以π来表示）  |
| e    | 数学常量 e，e即自然常数（自然常数）。 |



#### 2.2、**String（字符串）**

**用[ : ]截取字符串：**

string[n:m]：可用于截取从n到m的子字符串（字符下标从0开始）

string[n:]：截取从n开始到结束的子字符串

~~~python
sentence = "我打你妈的"

print(sentence[2:5])

#将会输出 “你妈的”
~~~

注意：截取字符串时，将从下标为n开始，到下标为m前一个元素结束，相当于

~~~c++
for(int i = n; n < m; n++){
    newStr[i] = oldStr[n];
}
return newStr;
~~~

**对于print函数的使用：**

~~~python
#将会输出两次sentence
print(sentence*2)

#将会连接nmsl一起输出
print(sentence + "nmsl")
~~~

**三引号的使用：**

Python 中三引号可以将复杂的字符串进行赋值。

Python 三引号允许一个字符串跨多行，字符串中可以包含换行符、制表符以及其他特殊字符。

三引号的语法是一对连续的单引号或者双引号（通常都是成对的用）。

```
 >>> hi = '''hi 
there'''
>>> hi   # repr()
'hi\nthere'
>>> print hi  # str()
hi 
there  
```

三引号让程序员从引号和特殊字符串的泥潭里面解脱出来，自始至终保持一小块字符串的格式是所谓的WYSIWYG（所见即所得）格式的。

一个典型的用例是，当你需要一块HTML或者SQL时，这时当用三引号标记，使用传统的转义字符体系将十分费神。

#### 2.3、**List（列表）**（数组）

~~~python
list = [ 'runoob', 786 , 2.23, 'john', 70.2 ]
tinylist = [123, 'john']
 
print list               # 输出完整列表
print list[0]            # 输出列表的第一个元素
print list[1:3]          # 输出第二个至第三个元素 
print list[2:]           # 输出从第三个开始至列表末尾的所有元素
print tinylist * 2       # 输出列表两次
print list + tinylist    # 打印组合的列表
~~~

使用：

1、append()：尾插法

2、pop()：返回并删除末尾元素

#### 2.4、**Tuple（元组）**（只读数组）

与list类似，语法上用 () 括住元素，元素间用 , 隔开

但元组是只读的，即只能被一次定义，之后不能修改，可以理解为只读列表

#### 2.5、**Dictionary（字典）**（map）

~~~python
dict = {}
dict['one'] = "This is one"
dict[2] = "This is two"

#以键值对的形式储存，语法如下
tinydict = {'name': 'runoob','code':6734, 'dept': 'sales'}
 
 
print dict['one']          # 输出键为'one' 的值
print dict[2]              # 输出键为 2 的值
print tinydict             # 输出完整的字典
print tinydict.keys()      # 输出所有键
print tinydict.values()    # 输出所有值
~~~

### 3、数据转换

有时候，我们需要对数据内置的类型进行转换，数据类型的转换，你只需要将数据类型作为函数名即可。

以下几个内置的 **函数** 可以执行数据类型之间的转换。这些函数返回一个新的对象，表示转换的值。

| 函数                                                         |                        描述                         |
| :----------------------------------------------------------- | :-------------------------------------------------: |
| int(x [,base\])                                              |                  将x转换为一个整数                  |
| long(x [,base\] )                                            |                 将x转换为一个长整数                 |
| float(x)                                                     |                 将x转换到一个浮点数                 |
| complex(real [,imag\])](https://www.runoob.com/python/python-func-complex.html) |                    创建一个复数                     |
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

## 四、运算符

### 1、算术运算

1、//：取模

2、**：幂运算

### 2、逻辑运算

1、and：&&

2、or：||

3、not：!

### 3、成员运算（instanceof）

1、in

2、not in

~~~python
#常用于列表、元组、字典

a = 10
b = 20
list = [1, 2, 3, 4, 5 ];
 
if ( a in list ):
   print "1 - 变量 a 在给定的列表中 list 中"
else:
   print "1 - 变量 a 不在给定的列表中 list 中"
 
if ( b not in list ):
   print "2 - 变量 b 不在给定的列表中 list 中"
else:
   print "2 - 变量 b 在给定的列表中 list 中"
 
# 修改变量 a 的值
a = 2
if ( a in list ):
   print "3 - 变量 a 在给定的列表中 list 中"
else:
   print "3 - 变量 a 不在给定的列表中 list 中"
~~~

### 4、身份运算

1、is

2、is not

~~~python
# 用于判断是否引用（指向）同一个对象，即判断地址
# ==和!=判断其值

a = 20
b = 20
 
if ( a is b ):
   print "1 - a 和 b 有相同的标识"
else:
   print "1 - a 和 b 没有相同的标识"
 
if ( a is not b ):
   print "2 - a 和 b 没有相同的标识"
else:
   print "2 - a 和 b 有相同的标识"
 
# 修改变量 b 的值
b = 30
if ( a is b ):
   print "3 - a 和 b 有相同的标识"
else:
   print "3 - a 和 b 没有相同的标识"
 
if ( a is not b ):
   print "4 - a 和 b 没有相同的标识"
else:
   print "4 - a 和 b 有相同的标识"
~~~

## 五、条件语句

~~~python
if death:
    print("death")
elif illness:
    print("illness")
else:
 	print("live")
# 当if语句后只有一条执行语句，可将二者写在一行，即
if death: print("death")
~~~

## 六、循环语句

### 1、while循环

while / else 语句

~~~python
# 当while条件不满足时，执行else中语句
while a > 0:
    print(a)
    a -= 1
else:
    print("a <= 0")
~~~

### 2、for循环

~~~java
for letter in 'Python':     # 第一个实例
   print '当前字母 :', letter
 
fruits = ['banana', 'apple',  'mango']
for fruit in fruits:        # 第二个实例
   print '当前水果 :', fruit
 
print "Good bye!"
~~~

### 3、循环中常用关键字

1、pass

```
def sample(n_samples):
    pass
```

该处的 pass 便是占据一个位置，因为如果定义一个空函数程序会报错，当你没有想好函数的内容是可以用 pass 填充，使程序可以正常运行。

但pass语句不做任何事情，只是为了保持程序结构的完整性

2、break

~~~python
for letter in 'Python':     # 第一个实例
   if letter == 'h':
      break
   print '当前字母 :', letter
  
var = 10                    # 第二个实例
while var > 0:              
   print '当前变量值 :', var
   var = var -1
   if var == 5:   # 当变量 var 等于 5 时退出循环
      break
 
print "Good bye!"
~~~

3、continue

~~~python
for letter in 'Python':     # 第一个实例
   if letter == 'h':
      continue
   print '当前字母 :', letter
 
var = 10                    # 第二个实例
while var > 0:              
   var = var -1
   if var == 5:
      continue
   print '当前变量值 :', var
print "Good bye!"
~~~

