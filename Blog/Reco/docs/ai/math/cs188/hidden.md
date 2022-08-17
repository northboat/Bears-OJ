---
title: 隐马尔科夫模型和机器学习
date: 2022-5-9
tags:
  - AI
---

## HMMs

> 隐式马尔科夫模型：Hidden Markov Models
>
> 最常用的贝叶斯模型

通过结果推断中间某一过程条件概率分布

条件概率：`P(x|y) = P(x,y)/P(y)`

产品规则：`P(x|y)P(y) = P(x,y)`

链规则：`P(X1...Xn) = P(X1)P(X2|X1)P(X3|X1,X2)...`

独立：`P(x,y) = P(x)P(y)`

条件独立：`P(x,y|z) = P(x|z)P(y|z)`

在隐马尔可夫链中，我们通常关注观察对象暗含的隐式序列

- 语音解码
- 机器人坐标感知
- 推荐系统
- 医疗监控

### Markov Models

> 马尔可夫模型

MDPs：马尔可夫决定过程

- a set of states s

- a set of actions a

- a transition function T(s, a, s')

  - Probability that a from s leads to s', called P(s'| s, a)

    在s状态执行a行为到达s'的代价

  - Also called the model or the dynamics

    不同于搜索，这个后继函数有很多个，如在每个地点都可以向东南西北移动

- a reward function R(s, a, s')

  - sometimes just R(s) or R(s')

    奖惩制度，有时只取决起点或终点

- a start state

- maybe a terminal state

基础条件独立：Basic Conditional Independence

基于现在，过去和未来是独立的；每个步骤只取决于上一各步骤；这被称为一阶马尔可夫属性

可以用贝叶斯网或有限状态自动机来表示一个马尔可夫模型

贝叶斯网络：

~~~
X1———X2———X3———X4
~~~

#### Mini-Forward Algorithm

正向模拟：当我们要知道马尔可夫链中第k个元素的概率时，且已知第一个元素概率，可以使用递归的方式，即k-1获得任一元素的条件概率，最终获得一整个概率序列

- 这一结果也可以从第一个元素开始，进行变量消除得到

当k->∞，P(Xk)将收敛到一个固定值，如同抛硬币收敛到0.5

从平均概率开始，这一状态称为静止分布，当没有证据加入时，不管如何迭代概率分布始终不变

当加入条件，不断迭代，变量消除，概率分布偏移

#### Fixed distribution

固定分布：即马尔科夫链长度为无限时其值不再变动时的分布

有时可由线性方程组解出，如有概率分布表，X0表示第一天天气，X1表示第二天天气

| X0   | X1   | P(X1\|X0) |
| ---- | ---- | --------- |
| sun  | sun  | 0.9       |
| sun  | rain | 0.1       |
| rain | sun  | 0.3       |
| rain | rain | 0.7       |

可得无穷时关于第二天天气`P∞(x2)`的线性方程组

- `P∞(sun) = 0.9P∞(sun)+0.3P∞(rain)`
- `P∞(rain) = 0.1P∞(sun)+0.7P∞(rain)`

可得：`P(sun) = 3P(rain)`

又因为：`P(sun)+P(rain) = 1`

所以：`P(sun) = 0.75, P(rain) = 0.25`

这就是极限状态下的固定分布

**应用**

谷歌：搜索引擎，页面排名的想法

1. 手动排名，费时费力
2. 当前页面是当前状态，从当前页面的链接跳转至下一状态（页面），正符合一个马尔可夫链，以此为基础排名

由此构建一个马尔可夫模型，随着点击越来越多，排名将越来越精确

#### Gibbs Sampling

> 最常使用的抽样方法之一

吉布斯抽样：先确定一组变量值，固定部分条件，选择单个变量进行抽样，重复如此，最终将汇聚到一个固定的概率

从抽样中改造马尔可夫模型

### Hidden Markov Models

天气HHM：一群住在地下室的学生通过观察教授是否带伞来推测今日外面是否下雨

| R0（第一天） | R1（第二天） | P(R1\|R0) |
| ------------ | ------------ | --------- |
| +r           | +r           | 0.7       |
| +r           | -r           | 0.3       |
| -r           | +r           | 0.3       |
| -r           | -r           | 0.7       |

| R（是否下雨） | U（是否带伞） | P(U\|R) |
| ------------- | ------------- | ------- |
| +r            | +u            | 0.9     |
| +r            | -u            | 0.1     |
| -r            | +u            | 0.2     |
| -r            | -u            | 0.8     |

我们不能仅仅因为教授带伞便推断下雨，因素是多方面的且不确定的

证据变量之间并不保证独立，D分离后的三元组显示其有关联

一个简单的隐式马尔科夫模型的贝叶斯网

~~~
X1———X2———X3———X4
|	 |	  |	   |
E1	 E2	  E3   E4
~~~

**examples**

语音识别

- Observations：声波
- States：字符

机器翻译

- Observations：字符
- States：最佳翻译

机器人跟踪

- Observations：激光测距读数
- States：位置数据

**Inference**

`P(X1|e1) = P(x1,e1) / P(e1) = αP(x1|e1)`

使其规范化（概率和化为1），这样就摆脱了证据变量e1
$$
\begin{align}
P(X_{t+1}|e_t) &= \sum P(X_{t+1},x_t|e_t)\\&=\sum P(X_{t+1}|x_t,e_t)P(x_t|e_t)\\&=\sum
P(X_{t+1}|x_t)P(x_t|e_t)
\end{align}
$$
吃豆人的隐式马尔可夫模型：只知道鬼魂和自己的距离，推断鬼魂的位置并吃掉它

Haven't figure out

- Belief Update
- Observation
- Online Belief Update
- The Forward Algorithm

在隐马尔可夫模型中，证据驱动着结果推进

当没有证据时，就是一个普通的马尔可夫模型，各状态概率随时间推移将收敛至定值

### Particle Filtering

> 粒子滤波

#### 对于单独的变量X

`P(x2) = Σ(x1)P(x1,x2) = Σ(x1)P(x1)P(x2|x1)`

对于x1的所有取值，x2发生的概率之和，我们对所有x2的取值做这一操作，那么就得到x2的概率分布

现在对变量X加上证据E

`B(Xt) = P(Xt|e(1:t))`

- B指对变量`Xt`的信念（Belief），其中`t`为时间坐标，即第几个`X`
- 称为信念分布
- 它表示变量`Xt`的概率确实与证据`e(1:t)`有关

我们想知道的是：`P(X(t+1)|e(1:t))`

- 通过上一步的证据推断现在的概率

公式变换：

`P(X(t+1)|e(1:t)) = ΣP(X(t+1),xt|e(1:t))`因为xt是已经发生的事，所以加在左边不影响概率

​                             `= ΣP(X(t+1)|xt,e(1:t)) P(xt|e(1:t))`条件概率分解

​							`= ΣP(X(t+1)|xt) P(xt|e(1:t))`当`xt`发生时，`X(t+1)`和`e(1:t)`独立

概率转移房产：`P(X(t+1)|e(1:t)) = ΣP(X(t+1)|xt) P(xt|e(1:t))`

信念转移方程：`B(X(t+1)) = ΣP(X(t+1)|xt) B(xt)`（`B(X(t+1)) = P(X(t+1)|e(1:t+1))`）

#### 对于有证据的变量X|E

已知`P(x1)`

`P(x1|e1) = P(x1,e1) / P(e1) `

​				`α P(x1,e1)`将P(e1)看做常数

​				`= P(x1)P(e1|x1)`转换x1为条件

为什么这么做，对于每一个x1都存在一个P(e1)，求出所有的P(e1)，对他们进行规范化，得到一张证据概率分布表

**Observation**

如何工作？

假设我们知道`B(X(t+1)) = P(X(t+1)|e(1:t))`

当新的证据进入

`P(X(t+1)|e(1:t+1)) = P(X(t+1), e(t+1)|e(1:t)) / P(e(t+1)|e(1:t))`

​								`α P(X(t+1), e(t+1)|e(1:t))`

​								`= P(X(t+1)|e(1:t)) P(e(t+1)|X(t+1),e(1:t))`

​								`= P(X(t+1)|e(1:t)) P(e(t+1)|X(t+1))`当前`e(1:t)`已经是事实

得到：`B(X(t+1)) α P(e(t+1)|X(t+1)) B(X(t+1))`

新的信念分布是由旧的信念分布根据所有证据加权处理并重新规范化得出的

总结上面两个例子：

- `P(Xt|e(1:t-1)) = ΣP(X(t-1)|e(1:t-1)) P(xt|x(t-1))`

- `P(Xt|e(1:t)) α P(X(t)|e(1:t-1)) P(et|xt))`

  α 指正比例，即需要规范化为1

#### Filtering

> `xt`是隐藏变量，`et`是当前证据
>
> Filtering：approximate solution
>
> Particle：粒子，样本

有时|X|过大无法准确推断，我们只能使用近似推断：

- 追踪X的样本
- 样本被称为粒子
- 花费的时间是粒子数量的线性
- 样本数量需要够大
- 储存的是样本列表，而不是所有可能性的总表

对一堆粒子，带着证据对他们进行测试，观察`P(xi|e)`大小，并进行加权

当某一粒子权重过小（不符合证据），将新增粒子将其代替（未加权的粒子），再重新根据证据进行加权

注意我们始终关心的是那个粒子列表，而不是概率分布表

当得到新的证据时，将重新进行加权，根据特定的证据，权重总会集中在一起，当没有证据加入，粒子将发生分歧逐渐分散

我的理解就是：

有一大堆测试粒子，你不断给出证据去推翻他们，在幸存的粒子中找到答案

### Applications of HMMs

> 隐式马尔科夫应用

#### Robot localization

确定自己在地图中的方位

- 知道地图，但不知道位置
- 观察值可能是一系列距边缘距离
- 读数通常是连续的
- 粒子过滤是主要解题手段

一开始粒子分布在整个地图中，随着机器人不断发现证据，如右边是一堵墙，那么弱化所有右边没有墙的粒子，直到只剩一坨或一个粒子

#### Robat Mapping

画图机器人

- 不知道地图和自身方位
- 状态由位置和地图构成
- 主要使用技术：Kalman filtering（Gaussian HMMs）and paricle methods

SLAM：simultanrous localization and mapping（同步本地化和映射）

#### Dynamic Bayes Nets

动态贝叶斯网络

同时存在多个变量，我们将变量分开分成不同的HMM问题，分别进行粒子过滤

## ML

> 机器学习：machine learning
>
> 神经网络

在之前的例子中，世界的规则都是给定的：游戏规则，条件概率分布等等

当我们没有给定的模型时，如何去建立一个相对精确的AI代理

从数据中获取模型，以希望建立准确的系统

### Naive Bayes

> 朴素贝叶斯
>
> 基于模型的分类

#### Classification

> 分类
>
> 分类不是唯一的机器学习，但可能是最大的一类

**Examples**

1、Spam Filter

垃圾邮件过滤

通常来说，获得正确的数据是构建和部署中最难的部分，比如在学习过滤邮件的过程中，需要手动标注垃圾邮件让机器进行学习；部署机器学习系统的难度取决于收集数据和发现数据是否自然

垃圾邮件检测不一定来自于内容或单个词语，还需要考虑发件人、发送环境等一系列问题

2、Digit Recognition

识别手写阿拉伯数字

检查一个像素网络，需要收集大量贴上标签的实例图像

- 医疗诊断
- 投资风险诊断
- 自动论文评分
- 自然语言处理

#### Model-Based Classification

建立一个模型，一个最简单的贝叶斯网络：朴素贝叶斯网络

一对多的贝叶斯网，单个类y对应多个功能f(i)

- 一个非常激进的假设

在分类模型中Y对应的是类，而Fi对应类的诸多功能或特点

在朴素贝叶斯网络中，我们要计算的其实是概率向量

`P(Y,f1...fn) = [P(y1,f1...fn), P(y2,f1...fn)...](T)`

即各种特征对应类的条件概率大小，根据条件概率可得公式：`P(y1,f1...fn) = P(y1)ΠP(fi|y1)`（变量消除）

f是特征，y是类，上式表示类y1和特征fi们同时出现的概率大小，这取决于特征的条件概率大小和类的占比

各个特征之间的概率是独立的

如垃圾邮件过滤，W表示words，ham表示有用的邮件，spam表示垃圾邮件

对于`P(W|spam)`

- the: 0.0156
- to: 0.0153
- and: 0.0115
- of: 0.0095
- you: 0.0093
- a: 0.0086
- with: 0.008
- from: 0.0075

而对于`P(W|ham)`

- the: 0.021
- to: 0.133
- of: 0.119
- 2002: 0.011
- from: 0.107
- and: 0.105
- a: 0.1

相同的单词，给予不同的条件，其出现的概率不同

每当有新的单词`wi`录入时，原先的`P(W|ham)/P(W|spam)`都将乘上这个新`P(wi|ham)/P(wi|spam)`，更新分类概率

对于求得的`P(W|Y)`重新规范化，在`P(W|Y)`中选取概率最大的`P(W|y)`，作为当前邮件的分类`y`

#### Training & Testing

我们应该从数据中构建朴素贝叶斯网络，只有有了相对准确的模型才能进行推理或分类

How to make progress?

Training and generalize（训练和概括）

训练代表了概率分布，数据越多，概率将越精确，数据不够时，将不能进行合适的模拟；如果使用一种不能概括的方式来学习，将无法对数据进行合理的分配，也就不能处理多变的情况

也不能过度追求精确而导致过度拟合，失去一般性

一些重要的组成部分：

Data：带标签的实例

- Training set
- Held out set
- Test set

Features：特征属性

Experimentation cycle：循环测试

- Learn parameters on training set
- Compute accuracy of test set
- Vary important: never "peek" at the test set

Evaluation：精确拟合

- Accuracy: fraction of instances predicted correctly

Overfitting and generalization：过度拟合和概括

我们真正想要的是实用程序，而不仅仅是精确性，这意味着能够适用于各种不同情况

垃圾邮件是一个糟糕的例子，因为垃圾邮件是人为制造的，这里存在了一个军备竞赛，他是对抗性的

#### Generalization & Overfitting

我们希望程序适应恒定的功能而不是某一个特定的情况

UnderFitting：不合适

- 用下降曲线模拟上升曲线

OverFitting：过度拟合

- 如用一个15项多项式函数去细致模拟一堆看起来像二次函数的点
- 又如当某一个条件概率过度拟合为0，即使分类C1在其余概率上远大于C2，但因为这个特征概率为0，总概率变为0，这是不合理的

将概率设为0是很危险的行为

#### Parameter Estimation

> 参数估计

use training data to learning:：`P(ML)(x) = count(x) / total samples`

在这里我们选择最大化数据估计可能性

- maximizes the likelihood of the data

**Smoothing**

对于最大可能性估计：`P(ML)(x) = count(x) / total samples`

给予一些缓冲

`P = arg maxP(α|X) = arg maxP(X|α) P(α) P(X) = arg maxP(X|α) P(α)`

**Laplace Somoothing**

Unseen Events

考虑没有观察到的事物

如对于三个球：red、red、blue

最大概率估计

- `P(ML)(X) = [2/3, 1/3]`

而拉普拉斯扩展方法将加上一个隐藏的红球和一个隐藏的蓝球，是概率更加平滑

- `P(LAP,1)(X) = [3/5, 2/5]`

我们也可以假设有100个隐藏的红球和蓝球

- `P(LAP,100)(X) = [102/203, 101/203]`

使用更加平滑的概率来控制过度拟合

Tuning：调整

从训练数据中获取参数

Features：特征

Errors：模型发生错误时该怎么做

- 需要更多特征和训练数据
- 在朴素贝叶斯网络中增加变量

### Perceptrons

> 感知器

### Logistic Regression

> 逻辑回归

## Supplement

> 一些关于算法和模型的补充

### Beam Search

> 束搜索，属于局部搜索

[如何通俗的理解beam search？ 知乎](https://zhuanlan.zhihu.com/p/82829880)

假设现在有一个简易的翻译任务，将中文翻译成英文简写，如

- 我恨你 ——> i hate you ——> i h u
- 我爱你 ——> i love you ——> i l u

为了简化问题，现在我们的英文字典里只有三个字母，如

- I
- H
- U

现在我们要把“我恨你”通过字典翻译成最佳的英文简写

#### Exhaustive Search

> 穷举搜索

穷举所有的排列可能性：`A(3,3) = 3*3*3 = 27`

~~~
I-I-I
I-I-H
I-I-U
I-H-I
I-H-H
I-H-U
I-U-I
I-U-H
I-U-U

H-I-I
H-I-H
H-I-U
H-H-I
H-H-H
H-H-U
H-U-I
H-U-H
H-U-U

U-I-I
U-I-H
U-I-U
U-H-I
U-H-H
U-H-U
U-U-I
U-U-H
U-U-U
~~~

即在每次扩展树时，都完全扩展，完全扩展指每个节点都进行所有可能性的扩展

这样一定能找到全局最优解：`IHU`

#### Greedy Search

> 贪心搜索

贪心算法有点像本地搜索：即在每步都选取当前最优解（条件概率最大的节点）进行扩展

如第一步`I`最优，那我们直接确定第一个字幕为`I`；第二步`H`最优，同样确定下来；第三步`U`最优，完成搜索

贪心算法本质上没有从整体最优上加以考虑，并不能保证最终的结果一定是全局最优的。但是相对穷举搜索，搜索效率大大提升

#### Beam Search

`beam search`是对`greedy search`的一个改进算法。相对`greedy search`扩大了搜索空间，但远远不及穷举搜索指数级的搜索空间，是二者的一个折中方案

`beam search`有一个参数`beam size`（束宽），设为`K`，它的搜索过程如下

- 在首次选取节点进行扩展时，留下条件概率最高的`K`个节点进行扩展，剩下的节点被修剪掉
- 之后的每次选取，基于上次选取的高概率项，挑选出所有组合中条件概率最大的`K`个，作为下次选取的候选序列
- 就这样始终保持`K`个候选。直到遍历到叶子，在最后的`K`个节点中选择概率最高的作为搜索结果

注意：

`beam search`不保证全局最优，但是比`greedy search`搜索空间更大，一般结果比`greedy search`要好

`greedy search`可以看做是`beam size = 1`时的`beam search`

### Genetic Algorithm

> GA：遗传算法

[遗传算法入门详解 - 知乎](https://zhuanlan.zhihu.com/p/100337680)

[遗传算法(Genetic Algorithm)解析 - 简书](https://www.jianshu.com/p/ae5157c26af9)

### Iterative Deepening A*

> 迭代加深的A*算法
>
> A*搜索把状态视为内部无结构的黑盒

A*算法和迭代加深的深度优先搜索的结合

### HMM

> 隐马尔可夫模型
>
> 属于时序模型

[一文搞懂HMM（隐马尔可夫模型）skyme  博客园](https://www.cnblogs.com/skyme/p/4651331.html)

