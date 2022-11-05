---
title: Python 100 I
date: 2022-7-2
tags:
  -	Python
---

> 来自[fengdu78](https://github.com/fengdu78/Data-Science-Notes)的机器学习教程，收集了一百道python基础题目

## Day1

### 输出整除数

输出2000-3201的能被7整除不被5整除的数

~~~python
# for循环
for i in range(2000, 3201):
    if i%7!=0 and i%5!=0:
        print(i, end=',')
print()

# 构造数组，打印数组
arr = [i for i in range(2000, 3201) if i%7==0 and i%5!=0] # arr是构造的一个数组，当然也可以用()元组
print(*arr, sep=',') # 输出数组元素，等价于for i in list: print(i, end=',')
~~~

### 输出阶乘

输出输入数字的阶乘

~~~python
# for循环
n = int(input())
res = 1
for i in range(1, n+1):
    res *= i
print(res)

# 递归
n = int(input())
def rec(x):
    # if x <= 1:
    #     return 1
    # return x*rec(x-1)
    # 这个if/else可以缩写成一行
    return 1 if x<=1 else x*rec(x-1)
print(rec(n))
~~~

### 构造平方字典

构造一个由按1递增的由自身和自身平方构成的字典

~~~python
# for循环
dict={}
n = int(input())
for i in range(1,n+1):
    dict[i] = i*i
print(dict)

# 构造器
n = int(input())
dict = {i:i*i for i in range(1, n+1)}
print(dict)
~~~

## Day2

### 数组转元组

input()函数构造数组，数组转化称元组`[]`（只读数组`()`）

~~~python
l = input().split(',') # 通过,分割输入字符串为数组
t = tuple(l) # 将数组l转化成元组t，同理可以l=list(t)
print(l)
print(t)
~~~

- 同理有函数`list`

### 类内置set/get函数

定义一个类，具有获取字符串和打印字符串的函数

~~~python
class IOString:
    def __init__(self):
        pass
    def get_string(self):
        self.str = input()
    def print_string(self):
        print(self.str)
ios = IOString()
ios.get_string()
ios.print_string()
~~~

- pass的作用：占位，使代码完整，无逻辑任何功能，当尚未想好怎么写的时候可以用`pass`代替

### 方程求解

输入数字字符串，数字间用逗号隔开，输出函数计算结果，结果同样是字符串，答案之间用逗号隔开

函数为
$$
\sqrt{\frac{2\times C\times D}{H}},\quad C=50,H=30
$$

~~~python
# 引入库函数'开根'
from math import sqrt

# 定义常数
C,H = 50,30

# 定义计算函数
def cal(num):
    return sqrt((2*num*C)/H)

D = input().split(',') # 获取输入字符串
D = [int(i) for i in D] # 将输入转为整数
D = [cal(i) for i in D] # 计算结果并存入数组D
D = [round(i) for i in D] # 小数取整
D = [str(i) for i in D] # 转为字符串数组

# join函数，将元组、数组通过某个连接符转化为字符串，如','.join(['ds','das']) = 'ds,das'
print(",".join(D))
~~~

- `f = [i*2 for i in l if i > 0]`这行代码的意思为提取数组`l`中大于`0`的元素，并乘以2，构成一个新的数组，记为`f`

上述操作可以集中为一句：

~~~python
D = input().split(',') # 获取输入字符串
D = [str(round(cal(int(i)))) for i in D] # 完成所有计算后转为字符串数组
print(",".join(D)) # 将字符串数组合成字符串输出
~~~

更高级的写法：

~~~python
from math import *  # 引入math所有函数
C, H = 50, 30

def calc(D):
    D = int(D)
    return str(int(sqrt((2 * C * D) / H)))

D = input().split(",")
D = list(map(calc, D))  # applying calc function on D and storing as a list
print(",".join(D))
~~~

- map函数：第一个参数为函数，第二个参数为迭代器（数组、元组），这个函数将应用于迭代器中每个元素并返回作用后的迭代器

### 矩阵相乘

输入两个数字`x,y`作为上界，下界为`0`，输出二者乘法组成二维表，如`x=2,y=3`

| x    | y       | x*y     |
| ---- | ------- | ------- |
| 0    | [0,1,2] | [0,0,0] |
| 1    | [0,1,2] | [0,1,2] |

~~~python
x, y = map(int, input().split(','))

table = []
for i in range(0,x):
    temp = []
    for j in range(0,y):
        temp.append(i*j)
    table.append(temp)
print(table)
~~~

- 同样使用了`map(func, arr)`函数，一行解决输入数字

### 字典序排序

将用逗号分割的输入元素按字典序排序输出

~~~python
arr = input().split(',')
arr.sort()
print(','.join(arr))
~~~

### 小写输入大写输出

小写输入多个字符串，用换行分隔各个字符串，大写输出

~~~python
arr = []

while True:
    line = input()
    if len(line) == 0:
        break
    arr.append(line.upper())

for line in arr:
    print(line)
~~~

- input()函数接收空值，即回车时，可以视为返回一个`False`
- 字符串的`upper()`函数：将小写字母转为大写；同理有`lower`函数将大写转小写

通过yield返回

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

- yield不是阻塞的，这意味着获得值后，将新开辟一条线程立即返回，继续执行之后的代码，同时这个函数继续运行

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

## Day3

### 字符串删减、排序

输入字符串，删除出现次数多余1次的单词（即只允许每个单词出现一次），将剩余单词按字典序排序后组成新的句子打印输出

~~~python
word = input().split(' ') # 根据空格分割单词
for i in word:
    if word.count(i)>1:
        word.remove(i)
word.sort()
print(' '.join(word))
~~~

- 迭代器的`count(i)`函数：返回元素`i`出现次数
- 当以空格分割时，可以省略，即`word.input().split()`

通过构造器构造：怪怪的

通过set函数构造：set构造一个无序不重复的元素集，通过sorted函数按照字典序排序

~~~python
word = sorted(list(set(input().split)))
~~~

### 返回被5整除的二进制数

给若干个二进制数，返回能被5整除的数，要先进行十进制的转换

~~~python
def cal(b):
    total, fact = 0, 1
    for i in b:
        total += (ord(i)-48)*fact
        fact *= 2
    return total

arr = input().split(',')
l = []
for e in arr:
    if(cal(e)%5 == 0):
    	l.append(e)
print(','.join(l))
~~~

`ord()`函数将字符转为相应ascii码，`chr()`将ascii码转为字符

### 找出偶数

找出1000-3001中各位均为偶数的数，并打印输出

~~~python
ans = []
for i in range(1000,3002):
    flag = True
    for j in str(i):
        if ord(j)%2 != 0:
            # 不可以用continue，这里只能continue到下层内层循环
            flag = False
    if flag:
        ans.append(str(i))
print(','.join(ans))
~~~

- `str()`将数据转化为字符串
- `ord()`，数字的`ASCII码-48`为数字本身`(ascii-48)%2 = ascii%2`

### 统计字母和数字

统计一个字符串中数字和字母的个数

~~~python
def cal(x):
    res = [0,0]
    for i in x:
        ascii = ord(i)
        if ascii>=48 and ascii<=57:
            res[1] += 1
        elif (ascii>=65 and ascii<=90) or (ascii>=97 and ascii<=122):
            res[0] += 1
    return res

s = input()
res = cal(s)
print('LETTERS\t'+str(res[0])+'\nDIGITS\t'+str(res[1]))
~~~

其实可以直接比较字符的大小（也是比较的ascii码）

~~~python
def cal(x):
    res = [0,0]
    for i in x:
       if i>="0" and i<="9":
            res[1] += 1
        elif (i>="a" and i<="z") or (i>="A" and i<="Z"):
            res[0] += 1
    return res
~~~

也可以调用内置函数

~~~python
# i为某个字符
# 判断是否是字母
i.isalpha()
# 判断是否是数字
i.isnumeric()
~~~

## Day4

### 统计大小写字母个数

~~~python
word = input()
lower, upper = 0, 0
for c in word:
    if c>='a' and c<='z':
        lower += 1
    elif c>='A' and c<= 'Z':
        upper += 1
print("UPPER CASE " + str(upper) + "\nLOWER CASE " + str(upper))
~~~

或者用`isupper()/islower()`函数代替

~~~python
for c in word:
    lower += c.islower()
    upper += c.isupper()
~~~

使用`sum()`函数

~~~python
upper = sum(1 for i in word if i.isupper())
lower = sum(1 for i in word if i.islower())
~~~

### a+aa+aaa+aaaa

输入一个数`a`，输出`a+aa+aaa+aaaa`

~~~python
a = str(input())
res = int(a)+int(a+a)+int(a+a+a)+int(a+a+a+a)
print(res)
~~~

也可以写成

~~~python
res = int(a)+int(2*a)+int(3*a)+int(4*a)
~~~

利用字符串加法和字符串转整数实现，当然也可以一个一个乘了加

## Day5

### 找到奇数并平方

将输入的奇数的平方构成一个数组输出，输入的数字用逗号分隔

~~~python
l = input().split(',')
res = []
for i in l:
    i = int(i)
    if i%2 != 0:
        res.append(str(i**2))
        
print(','.join(res))
~~~

高级的写法

~~~python
res = [str(int(i)**2) for i in input().split(',') if int(i)%2 != 0]
print(','.join(res))
~~~

要注意整型和字符串的转换

- join只可以作用于字符串数组
- 乘方、取余等运算只能作用于数字型变量

### 记账

实现一个加减法程序，`D`代表加，`W`代表减，连续输入回车则退出程序，如

~~~
D 300
W 200

100
~~~

~~~python
total = 0
while True:
    s = input().split(' ')
    if len(s) < 2:
        break
    exec, num = map(str, s)
    if exec == "D":
        total += int(num)
    elif exec == "W":
        total -= int(num)
        
print(num)
~~~

以空格作为标识分割，输入回车时，即`s='\n'`，经分割后仍为一个字符，即`len(s)==1`，需要判断这种情况并及时退出

同样利用字符串分割

~~~python
"""Solution by: leonedott"""
lst = []
while True:
    x = input()
    if len(x) == 0:
        break
    lst.append(x)

balance = 0
for item in lst:
    if "D" in item:
        balance += int(item.strip("D "))
    if "W" in item:
        balance -= int(item.strip("W "))
print(balance)
~~~

利用构造器和`sum`函数

~~~python
"""Solution by: ShalomPrinz"""
lines = []
while True:
    loopInput = input()
    if loopInput == "done":
        break
    else:
        lines.append(loopInput)

lst = list(int(i[2:]) if i[0] == "D" else -int(i[2:]) for i in lines)
print(sum(lst))
~~~

## Day6

### 检测密码

判断密码是否符合要求

- 至少一个小写字母
- 至少一个大写字母
- 至少一个数字
- 至少有`'$','@','#'`之中一个
- 长度在`[6,12]`之间

~~~python
def check(pwd):
    length = len(pwd)
    if length<6 or length>12:
        return False
    flag = [False, False, False]
    for i in pwd:
        if i.islower():
            flag[0] = True
        if i.isupper():
            flag[1] = True
        if i=='$' or i=='#' or i=='@':
            flag[2] = True
    for f in flag:
        if f is False:
            return False
    return True

legal = []
password = input().split(',')
for pwd in password:
    if check(pwd):
        legal.append(pwd)
print(','.join(legal))
~~~

### 排序结构体

输入若干学生（姓名、年龄、成绩），按`姓名>年龄>成绩`的顺序进行排序并输出，即先比较名字的字典序，名字一样再比较年龄，年龄也一样则比较成绩

~~~python
l = []
while True:
    s = input().split(',')
    if len(s)<2:
        break
    l.append(tuple(s))
sorted(l,  key=lambda x:(x[0],x[1],x[2]), reverse=False)
print(l)
~~~

- `lambda`函数：匿名函数，冒号前为入参，后为出参
- `sorted(iterable, cmp=None, key=None, reverse=False)`

## Day7

### 7的倍数

输出`n`以内为7的倍数的整数

~~~python
class MyGen:
    def by_seven(self, n):
        for i in range(0, int(n/7)+1):
            yield i*7

n = int(input())
for i in MyGen().by_seven(n):
    print(i)
~~~

### 计算距原点距离

在`x-y`坐标系中移动，`DOWN 5`表示向下走五格，以此类推，求出移动后和原点距离（取整）

~~~python
from math import sqrt

x, y = 0, 0
while True:
    command = input().split(' ')
    if len(command)<2:
        break
    if(command[0] == "UP"):
        y += int(command[1])
    if(command[0] == "DOWN"):
        y -= int(command[1])
    if(command[0] == "LEFT"):
        x -= int(command[1])
    if(command[0] == "RIGHT"):
        x += int(command[1])

distance = int(sqrt(x**2+y**2))
print(distance)
~~~

## Day8

### 统计单词出现次数

构造一个单词的`set`（不重复），再在原句子中用`count`函数数出数量

~~~python
sentence = input().split(' ')
word = set(sentence)
for i in word:
    print("{0}:{1}".format(i, sentence.count(i)))
~~~

直接构造一个字典`dict`，但仍用`count`函数计数

~~~python
sentence = input().split(' ')
dict = {}
for word in sentence:
    dict.setdefault(word, sentence.count(word))
dict = sorted(dict.items())
for i in dict:
    print("{0}:{1}".format(i[0],i[1]))
~~~

### 将数平方

~~~python
n = int(input())
print(n**2)
~~~

### 内置文档函数

~~~python
print(str.__doc__)
print(sorted.__doc__)

def pow(n, p):
    """
    param n: This is any integer number
    param p: This is power over n
    return:  n to the power p = n^p
    """
    return n ** p


print(pow(3, 4))
print(pow.__doc__)
~~~

### 实例和类的属性

~~~python
class Person:
    name = "Person"
    
    def __init__(self, name=None):
        self.name = name

m = Person("NorthBoat")
print(Person.name + " name is " + m.name)
m.name = "HAHAHA"
print(Person.name + " name is " + m.name)
~~~

类的属性`Person.name`是静态的，而每个实例的`name`属性是动态的
