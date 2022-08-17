---
title: Numpy
date: 2022-7-6
tags:
  - Python
  - DataScience
---

> 一个用python实现的用于科学计算的扩展库

## Numpy For Beginner

### 基本操作

first numpy program

将二维列表转为矩阵

~~~python
import numpy as np
matrix = np.array([
    [1,3,5],
    [4,6,9],
    [2,7,8]
])
print(matrix)

# 维度
print("dim of matrix: ", matrix.ndim)
# (行,列)
print('shape of matrix: ', matrix.shape)
# 元素个数
print('size of matrix: ', matrix.size)
~~~

这里的维度非常智能，可以自行判断其向量间是否线性无关

注意通常的`python`环境并不包含这个库，要自己安装，`Anaconda`的`base`环境中自带，需要在`VSCode`中手动选择默认`python`环境：`ctrl+shift+p`输入`select Interpreter`切换环境

### 创建矩阵

创建一维矩阵

~~~python
import numpy as np
a = np.array([1,2,3], dtype=np.int32)
print(a)
print(type(a))
print(a.dtype)
~~~

- 通过输出结果可知`a`实际上是一个类，叫作`numpy.ndarray`
- `dtype`是矩阵中元素类型，默认为`int32`

创建二维矩阵

~~~python
b = np.array([[2,3,4],[3,4,5]])
print(b)
~~~

创建零阵

~~~python
c = np.zeros((3,4))
print(c)
~~~

- 调用`zeros((row, colume))`函数，入参为一个长度为2的元组，表示零阵的行列大小

创建全1阵（不是单位阵捏），同时规定元素类型

~~~python
d = np.ones((3,4), dtype=np.int32)
print(d)
~~~

创建空阵，打印结果每个元素都是0，但实际上其实是接近于零的浮点数

~~~python
e = np.empty((3,4))
print(e)
~~~

在范围内按照步长创建值等距的一维数组

~~~python
f = np.arange(10, 21, 2)
print(f)
~~~

- 参数分别为`(start, end, step)`，和`range`函数一样含头不含尾

重塑矩阵，改变矩阵的行列数目

~~~python
g = f.reshape((2,3))
print(g)
~~~

- 当然要总元素数相同才可以重塑，不同将报错`ValueError: cannot reshape array of size 6 into shape (2,4)`

创建线段型数据，即将某一范围内数据分割称`n`份，组成一维矩阵

这里的分割是均分的，含头也含尾，即`start`将是向量第一个元素，`end`将是向量最后一个元素，若想按`1`的步长进行分割，则`n`要等于`end-start+1`（分得元素为间隔数+1）

~~~python
h = np.linspace(0,11,12)
print(h)
~~~

同样通过`linspace()`得到的向量也可以重塑

~~~python
i = h.reshape((3,4))
~~~

### 基本运算

一维乘法

~~~python
import numpy as np
a = np.array([5,6,7,8])
b = np.arange(4)
print(a,b)

print(a*b, a.dot(b))
~~~

- `a=[5,6,7,8], b=[0,1,2,3]`，`a*b`是列向量乘行向量，结果仍为一个一维的含四个元素的向量，`a.dot(b)`是维之和，即行向量乘以列向量，得到单个值

乘方，矩阵的幂运算

~~~python
c = b**2
print(c)
~~~

三角函数，将作用于矩阵中每个元素

~~~python
d = np.sin(a)
e = np.cos(b)
print(d, e)
~~~

数乘，加法运算

~~~python
f = b*3
print(f)

g = b+3
print(g)
~~~

- 这里的乘3和加3都将作用于矩阵中每个元素

布尔矩阵

~~~python
i = b>2
print(i)

j = a==b
print(j)
~~~

- 实际上就是将这个条件判断作用于每个元素上，若为真，该位置则为`True`，为假反之，这样构成的一个新的元素均为布尔值的矩阵

二维乘法，当然要保证二者能够相乘

~~~python
k = np.array([1,1],[0,1])
l = np.arange(4).reshape((2,2))
print(k)
print(l)

print(k.dot(l))
print(l.dot(k))
print(np.dot(k,l))
print(np.dot(l,k))
~~~

- 由于这里是两个`2x2`的矩阵，左乘和右乘是有明显区别的，这里矩阵乘法的先后顺序也就表示了矩阵乘法的左右，如`k.dot(l)/np.dot(k,l)`即为`k`右乘`l`，结果一样
- 多维矩阵乘法不能之久使用`*`号

矩阵元素求和

~~~python
m = np.random.random((2,4))
print(m)
s = np.sum(m)
print("sum: ", s)
~~~

- `np.random.random((i, j))`将生成一个`i`行`j`列的矩阵，其元素为`(0,1)`的随机浮点数
- 使用`np.sum()`函数计算一个矩阵所有元素之和

矩阵最大/小元素

~~~python
m2 = np.max(m)
m1 = np.min(m)
print("max: ", m2)
print("min: ", m1)
~~~

通过`axis=1/0`参数可以控制求每行/列的和/最大值/最小值

- `axis=1`表示行
- `axis=0`表示列

~~~python
print("row sum: ", np.sum(m, axis=1))
print("colume min: ", np.min(m, axis=0))
print("row max: ", np.max(m, axis=1))
~~~

索引，`aij`的索引按照从左到右，从上到下的顺序计数，从`0`开始，如左上角元素索引为`0`，右下角元素索引为`元素总数-1`

~~~python
A = np.arange(2,14).reshape((3,4))
print(A)

# 最小元素索引
print(np.argmin(A))
# 最大元素索引
print(np.argmax(A))
~~~

矩阵均值

~~~python
print(np.mean(A))
print(np.average(A))
print(A.mean())
~~~

矩阵中位数（按照索引来排）

~~~python
print(np.median(A))
~~~

排序，对每个向量进行从小到大的排序，返回一个排序好的矩阵

~~~python
print(np.sort(A))
~~~

转置

~~~python
print(np.transpose(A))
print(A.T)
~~~

规范化矩阵元素大小

~~~python
print(np.clip(A,5,9))
~~~

- 当元素小于`5`时，将变成`5`，将元素大于`9`，将变成`9`

### 索引和切片

对于一维矩阵（向量），直接通过索引取得的将是单个元素

~~~python
B = np.array([1,2,3])
print(B[0]==1) # True
print(B[2]==3) # True
~~~

对于多维矩阵，其实就相当于多维数组取下标

~~~python
B = np.arange(1,13).reshape((3,4))
print(B[0]) # [1,2,3,4]
print(B[0][0]) # 1

for row in B: # 一行一行打印
    print(row)
for colume in B.T: # 一列一列打印
    print(colume)
~~~

切片，有点像切片字符串

~~~python
print(B[1,1:3]) # [6,7]
~~~

- 第`1`行的第`1-2`元素组成的向量（这里的数字均为下标）

多维转一维，按照索引构成一个一维矩阵

~~~python
C = B.flatten()
D = list(B.flat)
print(C)
print(D)
~~~

- 这里`B.flat`得到的是一个迭代器，为`Object`，通过`list`转为一维数组

### 矩阵合并

~~~python
import numpy as np

A = np.array([4,5,6])
B = np.array([1,2,3])
print(A)
print(B)
~~~

- 这里的`A，B`都是向量，而不是矩阵
- `[[1,2,3]]`和`[1,2,3]`有质的区别

向量上下合并，要求元素个数一样

~~~python
C = np.vstack((A,B))
print(C)
~~~

向量左右合并

~~~python
D = np.hstack((A,B))
print(D)
~~~

将向量转为矩阵

~~~python
A1 = A[np.newaxis, :]
A2 = A[:, np.newaxis]
print(A1)
print(A2)
~~~

- 通过`A[np.newaxis, :]`将向量转为一维行矩阵
- 通过`A[:, np.newaxis]`将向量转为一维列矩阵

多个矩阵合并

~~~python
A1 = A[np.newaxis, :] # [[4,5,6]]
A2 = A[:, np.newaxis] # [[4,5,6]]T
B1 = B[np.newaxis, :] # [[1,2,3]]
B2 = B[:, np.newaxis] # [[1,2,3]]T

E1 = np.hstack((A1,B1)) # 横向合并 [[4,5,6,1,2,3]]
E2 = np.vstack((A1,B1)) # 纵向合并 [[4,5,6],[1,2,3]]
E3 = np.vstack((A2,B2)) # 纵向合并 [[4,1],[5,2],[6,3]]
~~~

也可以通过`np.concatenate((A,B), axis=1/0)`进行合并，当`axis=1`横向合并，为`0`纵向合并，合并顺序和入参顺序保持一致

### 矩阵分割

构造3行4列矩阵

~~~python
import numpy as np

A = np.arange(12).reshape(3,4)
print(A)
~~~

等量分割，即均等分，分为纵向分割和横向分割

- `axis=1`纵向分割，第二个参数为分成的份数（`axis=1`为横向合并）
- `axis=0`横向分割，第二个参数为分成的份数（`axis=0`为纵向合并）
- 当无法等量分时将报错
- 分割的结果为一个矩阵迭代器，每个元素都是一个矩阵

~~~python
A1 = np.split(A, 2, axis=1)
for item in A1:
    print(item)
    
A2 = np.split(A, 3, axis=0)
for item in A2:
    print(item)
~~~

不等量分割

~~~python
A3= np.array_split(A,3,axis=1)
for item in A3:
    print(item)
~~~

- 第一个元素为`3x2`矩阵，第二、三个元素为`3x1`矩阵

### Copy和=

使用等于号对矩阵进行赋值，如

~~~pPython
import numpy as np

A = np.arange(4)
print(A)
B = A
C = A
D = A
A[0] = 11
print(A)
print(B)
print(C)
~~~

这里`A[0]`改变后，矩阵`B，C，D`的第一位元素均会改变，就像是`A，B，C，D`是不同的指针，但指向同一块内存空间

同理改变`B[1:3]=[22,33]`，所有的矩阵`A,C,D`的第二、三个元素都会变成`22,33`

进行成员运算时，均会得到`True`

~~~python
print(B is A) # True
print(C is A) # True
print(D is A) # True
~~~

通过`copy`函数赋值将重新开辟空间

~~~python
E = A.copy()
A[1] = 7
print(A)
print(E)
~~~

这时二者指向不同的内存空间，互不影响

### 广播机制

~~~python
import numpy as np

A = np.array([[1,2,3],[4,5,6],[7,8,9]])
print(A)
print(A+3)
B = np.array([1,2,0])
print(B)
print(A+B)
print(A.dot(B))
print(B.dot(A))
~~~

上述代码都是能够正常运行的

怎么说，就是在做矩阵运算的时候，若两个矩阵不匹配，将对不匹配的部分自动进行广播，如
$$
[1,2,3]+3=[1,2,3]+[3,3,3]=[4,5,6]
$$
又如


$$
\begin{bmatrix}
1,2,3\\
4,5,6\\
7,8,9
\end{bmatrix}
+
\begin{bmatrix}
0,1,2
\end{bmatrix}
=
\begin{bmatrix}
1,2,3\\
4,5,6\\
7,8,9
\end{bmatrix}
+
\begin{bmatrix}
0,1,2\\
0,1,2\\
0,1,2
\end{bmatrix}
=
\begin{bmatrix}
1,3,5\\
4,6,8\\
7,9,11
\end{bmatrix}
$$
在做乘法的时候，不合规范的乘法`numpy`会想办法让他符合规范
$$
\begin{bmatrix}
1,2,3\\
4,5,6\\
7,8,9
\end{bmatrix}
*
\begin{bmatrix}
0,1,2
\end{bmatrix}
\rightarrow
\begin{bmatrix}
1,2,3\\
4,5,6\\
7,8,9
\end{bmatrix}
*
\begin{bmatrix}
0\\1\\2
\end{bmatrix}
$$

### 常用函数

#### np.bincount()

统计索引出现的次数，索引可以视作下标`0,1,2...`

如向量`[0,0,1,1,2,2]`的`bincount`为`[2,2,2]`，因为`0`出现两次，`1`出现两次，`2`出现两次，所以`bincount[0]=2,bincount[1]=2,bincount[2]=2`

对`bincount()`进行加权，即`bincount(arr, weights=weight)`，这里的`arr`和`weight`都是向量且必须长度相等，`arr[i]`的权重即为`weight[i]`，在没有权重的时候，每个`arr[i]`的权重都为`1`，在计算`bincount[i]`的时候，`i`每出现一次，`bincount[i]`就`+1`，当人为添加了权重，这时就不是加`1`了，而是加上`arr[i]`对应的权重`weight[i]`

如

~~~python
import numpy as np

arr = np.array([0,0,1,1,2,2])
weight = np.array([0.1,0.3,0.3,0.5,0.6,0.2])

print(np.bincount(arr, weights=weight))
~~~

- `bincount[0] = weight[0]+weight[1] = 0.1+0.3 = 0.4`，为什么加这两个权重，因为`arr[0] = arr[1] = 0`
- 同理`bincount[1] = weight[2]+weight[3] = 0.3+0.5 = 0.8`
- `bincount[2] = weight[4]+weight[5] = 0.6+0.2 = 0.8`

#### np.argmax()

返回矩阵中最大元素的索引

~~~python
x = [[1,3,3],
     [7,5,2]]
print(np.argmax(x)) # 3，因为元素7的索引为3
~~~

可以规定按列或行进行操作

- `axis=0`按列操作
- `axis=1`按行操作

~~~python
x = [[1,3,3],
     [7,5,2]]
print(np.argmax(x,axis=0)) # [1,1,0]
print(np.argmax(x,axis=1)) # [1,0]
~~~

结合上述两个函数

~~~python
x = np.array([1, 2, 3, 3, 0, 1, 4])
print(np.argmax(np.bincount(x)))
~~~

结果为`1`，首先`bincount = [1,2,1,2,1]`，其最大值`2`的索引为`1`，故输出`1`

#### 求取精度

`np.around()`取精度函数，当精度为`0`时即为取整，负数向下取整，正数向上取整

- 第一个参数为矩阵/向量
- 第二个参数为精度

~~~python
np.around([-0.6,1.2798,2.357,9.67,13], decimals=0)
# [-1,2,3,10,13]
~~~

当精度为`1`时，精确到小数点后一位，同样保持向下取整，正数向上取整

~~~python
y = np.around([-1.2798,1.2798,2.357,9.67,13], decimals=1)
print(y) # [-1.3  1.3  2.4  9.7 13. ]
~~~

当精度为负数时，如`-1`，表示精确到`10`位，`-2`表示精确到`100`位，满足四舍五入的规则

- 注意是必须要超过`"5"`，如`5`精确到`10`位将变成`0`，`5.1`将变成`10`，同理`55`变成`50`，而`56`变成`60`

`np.floor(arr)`表示取下限，`-1.3 —> -2, 1.3 —> 1`

`np.ceil(arr)`表示取上限，`-1.3 —> -1, 1.3 —> 2`

`np.where(x>0, x, 0)`，表示用`0`填充负数，第一个入参是一个布尔值，若为真，则用第二个入参填充，若为假，则用第三个入参填充

~~~python
x = np.array([[1, 0], [2, -2], [-2, 1]])
print(np.where(x>0, x, 0))
~~~

结果为
$$
\begin{bmatrix}
1,0\\
2,0\\
0,1
\end{bmatrix}
$$

## Numpy 100

