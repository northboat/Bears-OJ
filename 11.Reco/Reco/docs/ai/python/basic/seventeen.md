---
title: Python 100 III
date: 2022-7-5
tags:
  -	Python
---

## Day17

### 断言

定义断言以及输出语句

~~~python
data = [2, 4, 5, 6]
for i in data:
    assert i % 2 == 0, "{} is not an even number".format(i)
~~~

~~~
---------------------------------------------------------------------------
AssertionError                            Traceback (most recent call last)
/tmp/ipykernel_3801/1250983935.py in <module>
      1 data = [2, 4, 5, 6]
      2 for i in data:
----> 3     assert i % 2 == 0, "{} is not an even number".format(i)

AssertionError: 5 is not an even number
~~~

捕获断言异常

~~~python
data = [2, 4, 5, 6]
for i in data:
    try:
        assert i % 2 == 0, "{} is not an even number".format(i)
    except AssertionError as e:
        print("AssertionError raised: " + str(e))
~~~

~~~
AssertionError raised: 5 is not an even number
~~~

- 这里要将异常`e`字符串化后才能直接连接

### 字符串计算

计算一个以字符串形式的算式，使用内置函数`eval`

~~~python
expression = input()
res = eval(expression)
print(res)
~~~

### 二分搜索

二分搜索，返回下标

~~~python
def binarySearch(arr, target):
    left, right = 0, len(arr)
    while left <= right:
        mid = int((left+right)/2)
        if arr[mid] < target:
            left = mid+1
            continue
        if arr[mid] > target:
            right = mid-1
            continue
        return mid

arr = [1, 5, 8, 10, 12, 13, 55, 66, 73, 78, 82, 85, 88, 99]
binarySearch(arr, 1)
~~~

## Day18

### random.uniform()

生成随机浮点数，导库`random`，函数`random.uniform(start,end)`含头含尾，若想要整数加个`int()`转一下就行

~~~python
import random
rand_float = random.uniform(10, 100)
print(rand_float)
~~~

也可以用`random.random()`函数，将生成一个`(0,1)`的浮点数，乘以90加10也可以做到

~~~python
import random

rand_float = random.random()*90+10
print(rand_num)
~~~

生成5-95的随机数

~~~python
import random
rand_float1 = random.uniform(5, 95)
rand_float2 = random.random()*90+5
print(rand_float1)
print(rand_float2)
~~~

### random.choice()

随机抽取数组中元素

~~~python
import random
arr = [i for i in range(0,11,2)]
rand = random.choice(arr)
print(arr)
print(rand)
~~~

随机抽取一个在`10-150`之间被5和7整除的数

~~~python
import random
arr = [i for i in range(10,151) if i%5==0 and i%7==0]
rand = random.choice(arr)
print(rand)
~~~

### random.sample()

随机抽样函数，第一个参数为总体，第二个为样本容量

在`[100,200]`中随机抽取5各元素组成数组

~~~python
import random
sample = random.sample(range(100,201), 5)
print(sample)
~~~

在`[100,200]`中随机抽取5个偶数组成数组

~~~python
import random
even = random.sample(range(100,201,2), 5)
print(even)
~~~

在`[1,1000]`中随机抽取5个被5和7整除的数

~~~python
import random
arr = [i for i in range(1,1001) if i%35==0]
sample = random.sample(arr, 5)
print(sample)
~~~

### random.randrange()

和`range()`一样含头不含尾，返回一个范围内的整数

~~~python
import random
rand = random.randrange(7,16)
print(rand)
~~~

### random.shuffle()

洗牌数组

~~~python
import random
l = [1,23,5,2,1]
random.shuffle(l)
print(l)
~~~

注意该函数并不返回一个新的数组，只在原地打乱原数组元素顺序

## Day19

### 压缩、解压缩字符串

- 字符串前加`b`表示这是一个`bytes`对象
- `u`表示使用`Unicode`编码，默认为`Unicode`编码
- `r`常用于正则表达式或文件绝对地址等，该字母后面一般一般接转义字符，有特殊含义的字符。所以，要使用转义字符，通常字符串前面要加`r`

~~~python
import zlib
s = b'kldsjankfjdsalacf'
c = zlib.compress(s)
print(c)
print(s)
~~~

### 计算运行时长

和`java`那个`CurrentTime`啥的一个道理，记录当下的毫秒值

~~~python
import datatime
start = datetime.datetime.now()
for i in range(100):
    x = 1+1
end = datetime.datetime.now()
during = end-start
print(during.microseconds)
~~~

输出时可以限定时间单位，默认为秒

### 根据单词构造句子

给定有限的主语、谓语、宾语，输出所有能构造的句子

~~~python
subjects = ['I','You']
verbs = ['Play', 'Love']
objects = ['Hockey','Football']

for sub in subjects:
    for verb in verbs:
        for obj in objects:
            print("{} {} {}".format(sub,verb,obj))
~~~

## Day20

### 删去数组中偶数

删除数组中的偶数，使用`filter`函数实现

~~~python
src = [5, 6, 77, 45, 22, 12, 24]
dist = list(filter(lambda x:x%2!=0, src))
print(dist)
~~~

- 注意`filter`保留的是返回为`True`的元素，所有应该是当`x`为奇数时返回真

### 删除数组被5/7整除的数

~~~python
src = [12,24,35,70,88,120,155]
dist = list(filter(lambda x:x%35!=0, src))
print(dist)
~~~

### 删除数组下标0246的数

~~~python
def target(x):
    if x!=0 and x!=2 and x!=4 and x!=6:
        return True
    return False

arr = [12, 24, 35, 70, 88, 120, 155]
new = [arr[i] for i in range(len(arr)) if target(i)]
print(new)
~~~

- 同理构造器保留的也是`if`语句为`True`的元素

### 删除数组中下标2-4元素

~~~python
arr = [12, 24, 35, 70, 88, 120, 155]
new = [arr[i] for i in range(len(arr)) if i<2 or i>4]
print(new)
~~~

### 创建3维空数组

创建一个`3x5x8`的三维数组，元素均为`0`

~~~python
arr = [[[0 for k in range(8) for j in range(5) for i in range(3)]]]
print(arr)
~~~

## Day21

### 删除数组下标045的数

成员运算：`not in (数组)`，当然也可以`in (数组)`

~~~python
arr = [12,24,35,70,88,120,155]
new = [arr[i] for i in range(len(arr)) if i not in(0,4,5)]
print(new)
~~~

### 找出数组间相同的元素

使用`&`对`set`进行交运算，或使用`set.intersection()`函数

~~~python
arr1 = [1, 3, 6, 78, 35, 55,155]
arr2 = [12, 24, 35, 24, 88, 120, 155]
set1 = set(arr1)
set2 = set(arr2)
intersection = set1 & set2
print(intersection)

intersection = set.intersection(set1,set2)
print(intersection)
~~~

- `&`和函数`set.intersection()`效果一样，将返回一个`set`

### 删除数组中重复元素

先转`set`，再转`list`，记得排序

~~~python
arr = [12, 24, 35, 24, 88, 120, 155, 88, 120, 155]
new = sorted(list(set(arr)))
print(new)
~~~

也可以使用数组的`remove()`函数

~~~python
arr = [12, 24, 35, 24, 88, 120, 155, 88, 120, 155]
for i in arr:
    if arr.count(i) > 1:
        arr.remove(i)
print(arr)
~~~

### 定义子类

~~~python
class Person:
    def getGender(self):
        return "Unknwon"

class Male(Person):
    def __init__(self):
        pass
    def getGender(self):
        return "Male"

class Female(Person):
    def __init__(self):
        pass
    def getGender(self):
        return "Female"
male = Male()
female = Female()
print(male.getGender())
print(female.getGender())
~~~

## Day22

### 统计小写字母出现次数

按顺序输出小写字母`range(ord('a'), ord('z')+1)`

~~~python
s = input()
for letter in range(ord('a'), ord('z')+1):
    letter = chr(letter)
    count = s.count(letter)
    if count >= 1:
        print("{},{}".format(letter, count))
~~~

### 完全倒序输出句子

`hello world ——> dlrow olleh`

~~~python
s = input()
s = ''.join(reversed(s))
print(s)
~~~

- `reversed()`函数返回一个反转的迭代器：数组、元组、字符串
- 这里的`reversed(s)`仍是一个迭代器对象，通过`join`函数转为字符串

### 接收字符串偶数下标元素

将一个字符串偶数下标元素提取成一个新字符串

~~~python
s = input()
new = []
for i in range(len(s)):
    if i%2 == 0:
        new.append(s[i])
new = ''.join(new)
print(new)
~~~

呃其实可以直接连接字符串，没有必要先`append`数组再转字符串

~~~python
ans = ""
new = []
for i in range(len(s)):
    if i%2 == 0:
        ans += new[i]
~~~

如果用数组的话，不妨使用构造器

~~~python
s = input()
new = [s[i] for i in range(len(s)) if i%2==0]
new = ''.join(new)
~~~

### 列出1/2/3的全排列

调用`itertools`库函数`permutations`，该函数接收一个迭代器，返回一个迭代器

~~~python
import itertools
print(list(itertools.permutations([1,2,3])))
~~~

### 穷距法解鸡兔同笼

~~~python
def solve(heads, legs):
    for i in range(heads+1):
        j = heads-i
        if i*2+j*4 == legs:
            return i,j
    return '无解'


heads = 35
legs = 94
solutions = solve(heads, legs)
print(solutions)
~~~

## Day23

### 找出数组中第二大的数

用`set()`去除重复元素，用`arr.sort()/sorted()`函数进行排序

~~~python
arr = map(int, input().split())
arr = list(set(arr)) # 去除相同元素
arr.sort() # sorted(arr)
print(arr[-2])
~~~

- python的数组很有意思，下标`-1`为末尾元素，`-2`为倒数第二个元素，以此类推，只有当`-len(arr)-1`时才会报错超出边界

当然，也可以正着数

~~~python
print(arr[len(arr)-2])
~~~

### 将字符串按步长空格分割

调用库`textwrap`函数`wrap(text, width)`，按照步长`width`将字符串`text`分割成迭代器

~~~python
import textwrap
def wrap(text, width):
    text = textwrap.wrap(text, width)
    return ' '.join(text)

text = input()
width = int(input())
new = wrap(text, width)
print(new)
~~~

好像有现成的方法，不用封装

~~~python
""" Solution by: mishrasunny-coder """
import textwrap

string = input()
width = int(input())

print(textwrap.fill(string, width))
~~~

### 今天星期几

给定年月日`MM-DD-YYYY`，今天是星期几？

调用库`calendar`，使用其`weekday(year,month,day)`方法，将返回星期几的下标，`0`即为周一

通过`calendar.day_name[]`数组加获得的下标可以获取星期几的字符串

~~~python
import calendar
month, day, year = map(int, input().split())
id = calendar.weekday(year, month, day)
print(calendar.day_name[id])
~~~

### 输出数组的差

给定两个数组，输出在数组`a`不在数组`b`且在数组`b`不在数组`a`的元素

~~~python
set1 = set(map(int, input().split()))
set2 = set(map(int, input().split()))

res = list(set1 ^ set2)
print(res)
~~~

- `^`求迭代器的差
- `&`求迭代器的交

## Day24

### 统计字符串出现次数

要按照字符串的输入顺序输出其出现次数，字典是无序的，因而需要维护一个数组用以记录进入顺序

~~~python
n = int(input())
word_dict = {}
word_list = []

for i in range(n):
    word = input()
    if word not in word_list:
        word_list.append(word)
    word.dict[word].get(word, 0)+1
    
print(len(word_list))
for i in word_list:
    print(word_dict(i))
~~~

- 字典的`get`方法有两种重载，一个入参即为单纯的取元素，两个入参即`java`中的`getOrDefault`，若不存在则返回第二个入参

### 统计字符串中字母个数

使用字典进行统计，排序时注意排序的是字典的`items()`，规定`key=`为排序基准，`x[0]`为键，`x[1]`为值，若想倒序，加个`-`号即可

~~~python
word = input()
d = {}
for c in word:
    d[c] = d.get(c, 0)+1
d = sorted(d.items(), key=lambda x:-x[1])
for i in d:
    print(i[0],i[1])
~~~

字典的`items()`打印出来是这样的

~~~python
dict_items([('d', 3), ('s', 3), ('a', 3), ('f', 3)])
~~~

### 统计字母数字个数

使用`isdigit()/isalpha()`函数判断是否是数字/字母

~~~python
word = input()
digit, letter = 0, 0
for i in word:
    if i.isdigit():
        digit += 1
    if i.isalpha():
        letter += 1
print("Digit - ", digit)
print("Letter - ", letter)
~~~

### 1+2+...+n

递归写法

~~~python
def rec(n):
    if n == 1:
        return 1
    return n+rec(n-1)

n = int(input())
print(rec(n))
~~~

