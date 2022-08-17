---
title: 马尔可夫决定过程和强化学习
date: 2022-4-24
tags:
  - AI
---

## MDPs

> Markov Decision Processes
>
> 马尔可夫决定过程

### What is MDPs

进程：对搜索的概括

计算可能的结果

在`GridWorld`中，你决定向北走（因为这是最佳策略），但可能会执行失败（撞墙）

MDP：Reward ——> 结果

- happy reward
- bad reward

目标很松散 ——>为了最大化奖励的总和

An MDP is defined by

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

MDPs是不确定性搜索问题

- 强化学习的基础

expectimax（最大期望算法）算法可以MDP问题

action outcomes depend on

- 未来要到达的状态
- 你要执行的行动

MDPs适合嘈杂的世界

**Grid World**

Policy：策略

- 通过状态告诉你动作的功能
- 如在地图上每个点标好你该往哪个方向走

optimal policy：最优策略

- 或许存在很多等效策略

competition

- 移动奖励（负0.1）是那么微不足道而不值得冒险去坑附近
- 宁愿什么都不做，也不愿犯错

当移动代价变得更大，策略将更倾向与冒险在坑附近

当更更大时，甚至有可能直接跳坑而避免移动花销

**Racing**

states：

- cool
- warm
- overheated：risk the danger of breaking

跟据当前的温度决定是加速还是减速

**Racing Search Tree**

> tool：epectimax search

actions：

- slower
- faster

state：

- warm
- cool
- over heated

这棵树是无限的

- Q state：选择了但还没行动的过度状态

**Utilities of Sequences**

实用程序的选择顺序

- more or less
- now or later

隐含的权衡

**discount**

对奖励的贬值，对晚来的价值施以惩罚，如每走一步，未得到的价值便腐朽0.8，0.8便是折扣

当折扣越大，即`λ`越小，agent将变得越贪婪，越在意眼前的价值，而不是以后获得更大的利益

**Preferences**

假设偏好是固定的

two ways to define utilities

- additive utility
- discounted utility

如何处理无限的问题

- Finite horizen：similar to depth-limited search，即限定树的深度
- Discounting：价值总是贬值，将无限接近于0
- Absorbing state：使用一系列终止状态，即

Markov decision processes：

- 状态集
- 初始状态
- 行为集
- 过渡函数：提供的是概率
- 奖惩机制

它的输出是每个state上对应的action，他实际上并没有真正在试错，而是去给每个状态分配最佳的行动，这就是MDP

### Solving MDPs

Quantities：

- Policy：map of states of actions
- Utility：sum of discounted reward
- Values：expected future utility from a state（max node）
- Q Values：expected future from a q-state（chance node）

Optimal Quantities

- V(s)*：状态的期望值（或许是平均值）
- Q*(s, a)：在状态s执行动作a后起的最佳作用
- P*(s)：当前状态的最佳策略（算法产出）

expectimax search可以解决这一问题，估算价值，选出最大价值，赋值

考虑一下其他的算法

- `V*(s) = maxQ*(s, a)`

  虽然Q*(s, a)还不知道怎么算

- `Q*(s, a) = avg(sum(R(s, a, s') + λV*(s'))`

  这是一个递归的定义，因为你并不知道V*(s')直到搜索到终点

此之谓贝尔曼方程：Bellman Equations

- take correct first action
- kepp being optimal

回顾一下Racing Search Tree

他是无限的，并且只有三种状态，如果用expectimax search，将会有指数级的重复工作（子树）

#### Value Iteration

价值迭代算法

- from the bottom（deep enough）, recur the top
- `V*(s) = maxΣT(s,a,s')[R(s,a,s') + λV*(s')]`

利用贝尔曼方程确实可以搜索到底部并且递归回顶部，在这个递归过程中，各节点的值是不断更新的，且更加准确，直到保持稳定，即递归完毕

- 这个收敛的过程称作`bellman update`

**Computing Time-Limited Values**

对于一颗无限树，采用时间限制其递归深度，令V*(s)尽可能准确

因为条件有限，我们无法完整进行贝尔曼算法，即只能尽可能的接近V*(s)

- `Vk(s) = avg(sum(R(s, a, s') + λVk(s')))`

其中`Vk(s)、Vk(s')`都取其均值

- take average
- 像一个单层的expectimax搜索，但不同的是，他会由于递归深度的增加不断调整Vk值

**Convergence**

VK compute

一个k层树和一个k+1层树

由于搜索深度增加，对于未来某节点的折扣也增加，也就是说越往后对总值的影响应是越小，细微调整

当discount>=1，没有趋同保证

#### Policy Evaluation

策略评估方法

##### fixed Policies

固定的策略

- do the optimal action
- do what Pi says
- easier than the optimal

假设你的固定策略选出的后继节点是最佳的

- `VΠ(s) = ΣT(s,Π(s),s')[R(s,Π(s),s') + λVΠ(s')]`

固定策略例如：一直向右走；一直向前走

列举所有策略，评估所有策略，选择得分最高的策略

##### policy evaluation

输入一个策略，执行策略，得到该策略的值向量

`VΠ = ΣT(s,Π(s),s')[R(s,Π(s),s') + λVΠ(s')]`

##### Policy Extraction

即使当找到了相邻的最佳值，仍然要做一次expectimax去找到导致这个最佳值的行动

- 从值中找出行动，以更新策略

价值驱动决策

Computing Actions from Q-Values

#### Policy Iteration

价值迭代的问题

- 每次迭代将会耗费`O(s^2*A)`，这很慢
- 每个状态的最大值很少改变，这意味着做了很多低效工作

正确策略下的无用选择 ——> 错误的策略试错

我们采用策略迭代

- 首先选择一些策略，并执行他们，估算状态价值
- 改善你的策略，再次考虑之前的行动，重复估值
- 直到策略收敛

可以证明它是最佳且收敛的，并且在很多情况比价值迭代收敛得更快

VΠ是由当前策略得到的当前“最佳值”

`VΠ = ΣT(s,Π(s),s')[R(s,Π(s),s') + λVΠ(s')]`

根据这个当前最佳值，更新上一步的策略，比如我上一步策略原来是往北走，但这个最佳值得往东走，那么我更新上一步的策略为向东走

- MDPs本质上便是找到每步的最佳策略，值迭代同时考虑策略和价值，在每步做出最佳选择；策略迭代通过值去找到更优的策略
- 二者都是迭代，从叶子回溯到顶部

通常根据最后值的变化来确定是否已经收敛

### Summary

- compute optimal values：both can
- compute values for partivular policy：policy evaluation（策略评估）
- turn your values into policy：use policy extraction（策略抽取）

通常Policy Iteration是policy evaluation和policy improvement交替执行直到收敛

Value Iteration是寻找Optimal value function和执行一次policy extraction

- 均属于动态规划算法

**Double-Bandit MDP**

两台老虎机，一台（blue）拉一次给一块钱；另一台（red）拉一次给0元或2元。共拉一百次

更优的策略？

- `red one`获得2元的概率为0.75

平均上

- blue：100元
- red：150元

当获得2元的概率未知，尝试red one去获得信息

core of reinfocement Learning：exploraton

只能探索才能获取更多信息

pay for the infomation and get return

甚至不需要MDP算法，只需要不断探索和基本的数学直觉，试出概率

## RL

> Reinforcement learning：强化学习
>
> It's about how to learn behaviors

- Agent —actions—> Environment
- Environment —state/reward—> Agent

Agent和Environment都是动态变化的

Basic idea：

- agent接收奖惩反馈
- 奖惩函数决定agent的效用
- 为了最大化奖励，必须去学习最优行动
- 所有的学习基于观察样例后的结果

learning rather than plan

Examples：

- Robot dog learning to Walk
- Snake rebot sidewingding（爬墙）

因为真实世界的规则并不是确定的，难以建模，这时让程序根据概率学习正确的行为显得更加高效

- Toddler Robot（幼儿机器人）

  know how to stand after fall down

机器学习的最开始，他是不知道怎么做的，只是来回摆动，因为他不知道怎么获取奖励，于是开始瞎几把试，当偶然获取奖励后，他将根据奖惩制度完善自己的行动策略，从而行动得更加高效

Still assume a Markov decision process

- a set of states

- a set of actions

- a model T(s,a,s')

  原为 a successor function T(s,a,s')

- a reward function R(s,a,s')

Still looking for a policy

The defference：We don't know T or R

- 不知道哪个状态是好的或哪个动作是好的

  就像那个老虎机不知道掉落概率

- 必须真正去行动和访问状态去学习，去获取必要信息

Offline（MDPs） vs. Online（RL）

- Offline Solution
- Online Learning

### Model-Based Learning

Basic idea：

- learn an approximate model based on experiences
- solve for values as if the learned model were correct

现根据经验构建模型，再使用问题求解方法去计算当前模型

就像一个CSP问题我们不知道联系，得先建立相邻状态联系

step1：learn empirical MDP model

- 为每个状态和动作做产出（outcomes）统计
- 常态化评估函数T(s,a,s')
- 每当经历`s—a—>s'`时计算回报函数R(s,a,s')

step2：solve the learned MDP（近似的MDP问题）

- use value iteration
- use policy iteration
- ......

T和R是未知的，但状态空间和行为空间被分配了，要做的就是收集更多数据，动态改善你的模型，估计T和R函数

where the reward function come from

- depend on the human designer

how to calculate T function

- in a simple example, may just looking at the frequencies（频率）

计算概率权值：E（概率x值）

- Known P(A)：E(A) = ΣP(a)*a

- Unknown P(A)

  - Model Based：E(A) = avg(sum(P(a)*a))

    以某种策略重新计算概率

  - Model free：E(A) = (1/N)*sum(a)

    我们认为各种可能概率是相等的，因为尚未总结出规律

  - 二者区别在于是否按概率加权计算均值

### Model-Free Learning

#### Value Learning

> Passive Reinforcement Learning
>
> 我们不担心如何在世界模型中行动，只是观察行动并视图估计此代理的状态值

Simplified task：policy evaluation

- input：a fixed policy（遵循某一策略）
- don't know T(s,a,s')
- don't know R(s,a,s')
- goal：learn the state values

Direct & Indirect Evaluation

> 直接估值和间接估值

直接估值平均观察到的样本值，直接问这一步会有多少`reward`，仅仅依据实验出的结果的各状态值

如直接对于个节点的可能取值求均值作为其状态值，如对C节点使用四次策略

- C向D -1，D退出+10

  C向D -1，D退出+10

  C向D -1，D退出+10

  C向A -1，A退出-10

  那么取均值则为`(9+9+9-11)/4=4`

不需要对T/R做任何事，求均值就行了，只关注值；这不能达到超精确，但随着数据增加总会愈加接近

要做的事很明确：

- 选择一个节点
- 多次使用策略进行扩展
- 对扩展结果进行分析取均
- 对该节点赋值得到`V(s)`
- 更新值和策略

这一过程始终没用到T/R函数

- `VΠ(s) <-- (1/n)Σsample(i)`

注意这里所有的`V(s')`都应乘上一个`λ(<=1)`作为时间惩罚（贬值）

Temporal difference learning：

- `sample = R(s,Π(s), s') + λV(s')`
- `VΠ(s) <-- (1-a)VΠ(s) + (a)sample`

以上为更新已走过节点的方法

每次获得新的sample，都对刚走过的状态`s`进行更新，以接近精确值

在这一过程中，我们从未建立世界模型，即T/R函数，只是根据样例值不断更新状态值，随着时间的推移，将得到精确值

优化求均值的方法，让越接近的经历比以前的经历更重要，因为我们后来计算的结果总是更加准确

- `xn = (xn + (1-a)*xn-1 + (1-a)^2*xn-2+...) / 1+(1-a)+(1-a)^2+...`

  xn为第n个样例

- 这里的a为学习率，应用于迭代方程中

由于我们从未构建模型，也没有T/R函数，根本无从进行策略迭代

为什么不学习`Q-Value`而是`V-Value`？

没有理由，他不仅同样能实现更新Value，而且可以用于策略更新，属于积极的学习

#### Q-Learning

> Active Reinforcement Learning
>
> 担心数据从何处收集，担心采取行动

also

- don't know the transitions T
- don't know the reward R
- choose the actions now（当前做的）
- goal：learn the optimal policy/values

不同于MDPs，这不是离线测试（毕竟不知道T/R，无法进行推测），而是真切地采取行动

iteration

- 从一个确定状态值开始
- 计算该状态值下一层每个状态的Q-Value和Value
- 通过下一层的Q-Value/Value更新该层的Q-Value/Value
- 迭代这一过程，更新所有Q-Value/Value

Value Iteration

- `Vk+1(s) <-- maxΣT(s,a,s')[R(s,a,s') + λVk(s')]`

Q-Value Iteration

- `Qk+1(s,a) <-- ΣT(s,a,s')[R(s,a,s') + λmaxQk(s',a')]`

在这里使用的样例和更新策略

- `sample = R(s,a,s') + λmaxQk(s',a')`

- `Q(s,a) <-- (1-a)Q(s,a) + (a)sample`

  这个常量a称为学习率

举例：crawler bot（爬虫机器人）

Q-Learning is called off-policy learning

Caveats（警告）

- have to explore enough
- have to eventually make the learning rate small enough（收敛）
- ...but not decrease it too quickly
- it doesn't matter how you select actions

| Problem                  | Goal                                        | Technique                                       |
| ------------------------ | ------------------------------------------- | ----------------------------------------------- |
| Known MDP                | Compute` V*,Q*,Π*`; Evaluate a fixed policy | Value/Policy iteration; Policy evaluation       |
| Unknown MDP: Model-Based | Compute` V*,Q*,Π*`; Evaluate a fixed policy | VI/PI on approximate MDP; PE on approximate MDP |
| Unknown MDP: Model-Free  | Compute` V*,Q*,Π*`; Evaluate a fixed policy | Q-learning; Value learning                      |

均使用贝尔曼方程进行递归计算

Exploration（探索）vs. exploitation（开发）

**Epsilon Greedy**

Exploration function

- 探索未知节点，收集更多经验：random actions（ε epsilon-greedy）

  当ε越大，随机度越高，当为0，策略确定

- 探索方程将根据一个节点的“经验”，如访问过多少次，来给予相应的奖励（访问越多，奖励越低）

- `f(u,n) = u + k/n`（基数+奖励/访问次数）

- 这样能有效腐烂一些无用的节点（越多访问奖励越少）

Q-Update：加入探索方程

- Regular Q-Update:

  `Q(s,a) <-- ΣT(s,a,s')[R(s,a,s') + λmaxQ(s',a')]`

- Modified Q-Update:

  `Q(s,a) <-- ΣT(s,a,s')[R(s,a,s') + λmaxf(Q(s',a'),N(s',a'))]`

Regret

#### Approximate Q-Learning

在实际问题中，状态数、动作会很多很多，很难在Q-Table中去储存每一个Q-Value，这个时候只能做估计

w为权重，f为特征值（features）

- `V(s) = w1*f1(s)+w2*f2(s)+...+wn*fn(s)`
- `Q(s,a) = w1*f1(s,a)+w2*f2(s,a)+...+wn*fn(s,a)`

你的Q值将是很多经验的加权和，如f1为跳楼的特征值，f2为纵火的特征值，Q将这些情况的经验汇总以某些权重组合

当特征值`>1`说明他鼓励这种差异，反之对差异持消极态度

a仍是学习率

- `Q(s,a) <-- Q(s,a) + a[diff]`

  准确的Q值

- `wi <-- wi + a[diff]f(s,a)`

  近似的Q值

当权重降低，其对应的多项式变低，Q得到调整，那么更新权重成为现在的问题

这么做的目的无非是想用相对少的数据得到一个相对好的Q函数

**Optimization**

最小二乘法处理特征值`features`

- `Q(s,a) = w1*f1(s,a)+w2*f2(s,a)+...+wn*fn(s,a)`
- `Q(s,a)=w0 + w1f1(s,a)`

Minimizing Error

- `error(w) = (1/2)*(y-Σwk*fk(x))½`

对该函数对w求导得

- `-(y-Σwk*fk(x))fm(x)`

Why limiting capacity can help?

功能越多并不一定越好，这意味着更高阶的多项式，在函数曲线上更加符合

这有可能造成过度拟合（overfitting），即为了满足一些离谱的数据，做出疯狂的拟合

#### Policy Search

尝试不同的策略，看哪一个更好

`Q-Learning`：Q值接近，无法确定这是最好的行动

让我们关注行动

我们有一些Qvalue，向上向下调整特征值权重，看看有什么变化，好则接收，坏则丢弃，然后继续调整，就像CSP的本地搜索

> 直升飞机倒挂着飞会省四倍阻力
>
> ai vs. ai and train each other
