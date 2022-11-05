---
title: Python 100 II
date: 2022-7-4
tags:
  -	Python
---

## Day9

> 掌握`lambda`函数用法
>
> 这个函数定义的方式有点像`javascript`

### 加法函数

用`lambda`函数实现

~~~python
sum = lambda a,b:a+b
print(sum(1,2))
~~~

### 整型转字符串函数

用`lambda`函数实现

~~~python
tran = lambda n:str(n)
s = tran(10)
print(s)
print(type(s)) # str
~~~

### 字符串加法函数

~~~python
strsum = lambda s1,s2:int(s1)+int(s2)
n = strsum("123","321")
print(n)
print(type(n)) # int
~~~

### 连接字符串函数

~~~python
concat = lambda s1,s2:s1+s2
print(concat("20","22"))
~~~

### 打印较长的字符串

~~~python
def printmax(s1, s2):
    len1 = len(s1)
    len2 = len(s2)
    if len1>len2:
        print(s1)
    elif len2>len1:
        print(s2)
	else:
        print(s1)
        print(s2)

s1, s2 = input().split()
printmax(s1, s2)
~~~

高级的`lambda`函数

~~~python
# by: yuan1z
func = (
    lambda a, b: print(max((a, b), key=len))
    if len(a) != len(b)
    else print(a + "\n" + b)
)
~~~

## Day10

### 构建并打印字典

构建并打印整个字典

~~~python
def buildDict():
    dict = {i:i**2 for i in range(1,21)}
    print(dict)
buildDict()
~~~

构建并打印字典键

~~~python
def buildDict():
    dict = {i:i**2 for i in range(1,21)}
    print(dict.keys())
buildDict()
~~~

### 构建并截取打印数组

构建并打印整个数组

~~~python
def buildList():
    l = [i**2 for i in range(1,21)]
    print(l)
buildList()
~~~

构建并打印数组前五元素

~~~python
def printFive():
    l = [i**2 for i in range(1,21)]
    for i in range(5):
        print(l[i]) # 通过下标遍历数组
printFive()
~~~

构建并打印数组后五元素

~~~python
def printReverseFive():
    l = [i**2 for i in range(1,21)]
    for i in range(len(l)-1, len(l)-6, -1):
        print(l[i])
printReverseFive()
~~~

- `range()`函数其实有三个参数，第一个为起始点，第二个为终止点，第三个为步长（默认为1），步长即每次`loop`后加的数，-1即为倒序

构建并打印除开前五元素的其他元素

~~~python
def printReverseFive():
    l = [i**2 for i in range(1,21)]
    for i in range(5, len(l)):
        print(l[i])
printReverseFive()
~~~

### 构建并打印元组

因为元组`only read`，所以要先构造相应数组，用`tuple`函数将数组转为元组

~~~python
def printTupple():
    l = [i**2 for i in range(1,21)]
    print(tuple(l))
printTupple()
~~~

## Day11

### 分段打印元组

~~~python
tpl = (1,2,3,4,5,6,7,8,9,10)

def printTupple(t):
    n = len(t)
    for i in range(int(n/2)):
        print(t[i],end=',')
    print()
    for i in range(int(n/2), n):
        print(t[i],end=',')

printTupple(tpl)
~~~

- 有点问题，题目本意是将一个元组平分为俩，而且这样写末尾会多个逗号，懒得处理

~~~python
tpl = (1,2,3,4,5,6,7,8,9,10)
n = len(tpl)
l1, l2 = [], []
for i in range(int(n/2)):
    l1.append(tpl[i])
for i in range(int(n/2),n):
    l2.append(tpl[i])
print(l1)
print(l2)
~~~

### 按要求构造元组

~~~python
tpl = (1,2,3,4,5,6,7,8,9,10)
t = tuple(i for i in tpl if i%2==0)
print(t)
~~~

注意这里的`(i for i in tpl if i%2==0)`是一个构造器，这个圆括号不代表元组，所以需要使用`tuple`函数将其转化为一个元组

### 识别字符串

使用`or`连接条件，`else`处理`其他情况`

~~~python
text = input("Type sth: ")
if text=="yes" or text=="YES" or text=="Yes":
    print("Yes")
else:
    print("No")
~~~

### map(lambda, src)

返回一个数组的平方构成的数组

使用`map`函数将数组值映射为其平方，映射规则使用`lambda`函数定义

~~~python
l = [1,2,3,4,5,6,7,8,9,10]
square = list(map(lambda x:x**2, l))
print(square)
~~~

### filter(lambda, src)

返回一个数组的平方构成的数组，要求新数组元素均为偶数，奇数省去

使用`filter`函数过滤奇数，使用`map`函数构造数组，用两种不同返回值的`lambda`函数定义前二者的匿名函数

~~~python
l = [1,2,3,4,5,6,7,8,9,10]
square = list(map(lambda x:x**2, l))
odd = list(filter(lambda x:x%2==0, square))
print(odd)
~~~

这个`lambda`函数和`java`的`lambda`表达式很像

- 可以是有入参有出参的匿名函数
- `filter`的第一个参数必须是有入参，出参为布尔的匿名函数（当然也可以不是匿名函数，但要求返回值为布尔）

### filter(lambda, range)

将布尔函数通过`filter`对某一范围数值进行过滤，滤去奇数，保留偶数

~~~python
odd = list(filter(lambda x:x%2==0, range(1,21)))
print(odd)
~~~

这里`filter`的第二个入参可以是一个数值范围

## Day12

### map(func, range)

将函数通过map作用于某一数值范围

~~~python
def sqr(x):
    return x*x
nums = list(map(sqr, range(1,21)))
print(nums)
~~~

### 类静态函数

当实例在调用静态函数的时候，实际上调用的是`Class().staticmethod()`，而不是实例中的函数

~~~python
class China:
    @staticmethod
    def printNationality():
        print("I'm China")
c = China()
c.printNationality()

China().printNationality()
~~~

### 子类

~~~python
class China:
    pass
class TaiWan(China):
    pass

c = China()
t = TaiWan()

print(China)
print(TaiWan)
~~~

## Day13

### 构造类

圆形，使用半径进行构造，该类能够返回面积

~~~python
class Circle:
    def __init__(self, n):
        self.radius = n
    def arer(self):
        return 3.14159*self.radius**2
    
c = Circle(2)
c.arer()
~~~

长方形，长宽进行构造，能够返回面积

~~~python
class Rectangle:
    def __init__(self, n, m):
        self.width = n
        self.length = m
    def arer(self):
        return self.width*self.length

r = Rectangle(4,5)
r.arer()
~~~

形状和其子类正方形

~~~python
class Shape:
    def __init__(self):
        pass
    def arer(self):
        return 0
class Square(Shape):
    def __init__(self, length):
        # i don't know why
        Shape.__init__(self)
        self.length = length
    def arer(self):
        return self.length**2
~~~

- 为什么在初始化子类时要初始化一遍父类

### 抛出错误

~~~python
raise RuntimeError("nmsl")
~~~

## Day14

### try/except

- `try: 可能发生异常的语句`
- `except 异常类型 as 异常实例: 处理办法`
- `except: 处理办法`

~~~python
def div():
    return 5/0
try:
    div()
except ZeroDivisionError as z:
    print(z)
except:
    print("some other things wrong happened")
~~~

### 自定义异常类

继承类`Exception`即可

~~~python
class CustomException(Exception):
    def __init__(self, msg):
        self.massage = msg
num = int(input())
try:
    if num < 10:
        raise CustomException("num less than 10")
    else:
        raise CustomException("num more than 9")
except CustomException as e:
    print("The error raised: " + e.massage)
~~~

- 先定义自定义异常类
- 在`try`代码块中主动`raise`异常
- 在`except`语句中捕获自定义异常并处理

### 找出邮箱中的用户名

邮箱格式为`username@company.com`，给定若干邮箱，请返回各自的`username`

将输入用`@`隔开，输出第一个元素

~~~python
while True:
    e = input().split("@")
    if len(e) < 2:
        break
    print(e[0])
~~~

通过字符串匹配

~~~python
import re
email = "john@google.com elise@python.com"
pattern = "(\w+)@\w+.com"
username = re.findall(pattern, email)
print(username)
~~~

- 引入库`re`
- `\w`表示不定长任意字符串（whatever），在要找出的部分用小括号括住
- 用`re.findall()`函数找出所有符合的部分，返回一个字符串数组，第一个入参为正则表达式，第二个为要找的字符串

## Day15

### 找出邮箱中的公司名

> 正则匹配`re`库

邮箱格式为`username@company.com`，给定若干邮箱，请返回各自的`company`

~~~python
import re
email = "john@google.com elise@python.com"
pattern = "\w+@(\w+).com"
company = re.findall(pattern, email)
print(company)
~~~

### 找出仅由数字构成的单词

朴素想法

~~~python
sentence = input().split()
nums = []
for word in sentence:
    flag = True
    for c in word:
        if c>'9' or c<'0':
            flag = False
            break
    if flag:
        nums.append(word)
print(nums)
~~~

改进一下朴素想法

~~~python
sentence = input().split()
nums = []
for word in sentence:
    if word.isdigit():
        nums.append(word)
print(nums)
~~~

使用构造器

~~~python
sentence = input().split()
nums = [word for word in sentence if word.isdigit()]
print(nums)
~~~

用`'\d'`进行数字的字符串匹配（digit）

~~~python
import re
sentence = input()
pattern = "\d+"
nums = re.findall(pattern, sentence)
print(nums)
~~~

- 注意这里`re`只能处理字符串，所以不能对输入进行分割

### Unicode String

> 万国码

定义`unicode string`

~~~python
s = u'hello world'
print(s)
~~~

将`ASCII string`转为`Unicode string`

~~~python
s = input()
s.encode("utf-8")
print(s)
~~~

使用注释表明python程序所用编码

~~~python
# -*- coding: utf-8 -*-
~~~

### 1/2+2/3+3/4+...+n/n+1

浮点数确定精准度，使用`round(浮点数, 精确位数)`函数

~~~python
n = int(input())
total = 0.00
for i in range(1, n+1):
    total += i/(i+1)
print(total)
print(round(total, 2))
~~~

## Day16

### f(n) = f(n-1)+100, f(0)=0

简单的递归程序

~~~python
def compute(n):
    if n==0:
        return 0
    return compute(n-1)+100
n = int(input())
print(n)
~~~

### Fibonacci

`f(0)=0,f(1)=1,f(n)=f(n-1)+f(n-2)`

计算斐波那契数列第`n`位值

~~~python
def compute(n):
    if n <= 1:
        return n
    return compute(n-1)+compute(n-2)
n = int(input())
compute(n)
~~~

打印`n`以内的斐波那契数列

~~~python
def f(n, fibo):
    if n <= 1:
        fibo[n] = n
        return fibo[n]
    fibo[n] = f(n-1,fibo)+f(n-2,fibo)
    return fibo[n]

n = int(input())
fibo = [0]*(n+1)
f(n, fibo)
fibo = [str(i) for i in fibo]
print(','.join(fibo))
~~~

- 预先确定数组大小`fibo = [0]*(n+1)`，该数列长度为`n+1`，下标范围为`[0,n]`
- `join`函数作用于字符串数组，要先把`fibo`数列转为字符串数组

### yield & generator

找偶数

~~~python
def findEven(n):
    for i in range(0, n+1):
        if i%2 == 0:
            yield i
n = int(input())
arr = [str(i) for i in findEven(n)]
print(','.join(arr))
~~~

找出在`[0,n]`中被5和7整除的数

~~~python
def findNum(n):
    for i in range(0, n+1):
        if i%5==0 and i%7==0:
            yield i
n = int(input())
arr = [str(i) for i in findNum(n)]
print(','.join(arr))
~~~

