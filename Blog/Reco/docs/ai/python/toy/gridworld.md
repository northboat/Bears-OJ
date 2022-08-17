---
title: 网格世界的值迭代和Q-Learning
date: 2022-5-17
tags:
  - AI
  - Algorithm
  - C/C++
categories:
  - Toy
---

## Grid World

> 网格世界

在一个4x3的二维数组中，存在墙体和退出点

|           |          |      |   +1   |
| :-------: | :------: | ---- | :----: |
|           | **墙体** |      | **-1** |
| **Start** |          |      |        |

在每个空格上，智能代理可以选择上下左右四个方向移动，但并不是严格执行，如

- 发出向上移动指令时，有10%概率向左移动，10%概率向右移动，80%概率向上移动
- 发出向左移动指令时，有10%概率向上移动，10%概率向下移动，80%概率向左移动

当碰到墙体或边界，奖励为0，请根据退出点做出每个空格上的最佳决策并返回各点奖励

## Value-Iteration

> 值迭代的C语言实现

贝尔曼方程
$$
V^*(s)=maxQ^*(s,a)\\Q^*(s,a)=avg(\sum(R(s,a,s')+\lambda V^*(s')))
$$
一个迭代的过程，直到找到退出条件向前进行值迭代，完善整个网格

### 初始化

节点结构体

- maxVal：最大价值
- decision：最佳决策
- vals：上下左右四个Q-Value
- visited：是否被访问
- end：是否是墙体或退出点

~~~c
struct Node{
	//当前状态最大奖励
	double maxVal;
	//当前状态最佳决策 
	string decision;
	//当前状态分别向 上、下、左、右所获奖励 
	double vals[4];
	//是否被访问 
	bool visited;
	//是否是墙体或退出点 
	bool end;
};
~~~

初始化边界、图以及统计变量

- count：记录递归次数
- times：记录迭代次数

~~~c
#define LEFT 0
#define RIGHT 3
#define TOP 0
#define BOTTOM 2

Node graph[3][4];
int count = 0;
int times = 0;
~~~

初始化地图

~~~c
//初始化地图 
void initGraph(){
	//墙体 
	setEnd(1, 1, 0);
	//得分点 
	setEnd(0, 3, 1);
	//失分点 
	setEnd(1, 3, -1);
}
~~~

### 辅助函数

#### 设置函数

设置退出点或墙体

~~~c
//设置退出点和墙体 
void setEnd(int row, int colume, double val){
	graph[row][colume].maxVal = val;
	graph[row][colume].end = true;
	graph[row][colume].decision = "无";
}
~~~

设置节点最佳策略

~~~c
//根据Q值设置最佳策略 
void setDecision(){
	for(int i = TOP; i <= BOTTOM; i++){
		for(int j = LEFT; j <= RIGHT; j++){
			for(int k = 0; k < 4; k++){
				if(graph[i][j].vals[k] == graph[i][j].maxVal && graph[i][j].maxVal != 0){
					switch(k){
						case 0: graph[i][j].decision = "上"; break;
						case 1: graph[i][j].decision = "下"; break;
						case 2: graph[i][j].decision = "左"; break;
						case 3: graph[i][j].decision = "右"; break; 
					}
					break;
				}
			}
		}
	}
	
}
~~~

设置节点最大价值

~~~c
//Q值设置当前状态最大奖励 
void setMaxVal(int row, int colume){

	for(int i = 0; i < 4; i++){
		if(graph[row][colume].vals[i] > graph[row][colume].maxVal){
			graph[row][colume].maxVal = graph[row][colume].vals[i];
		}
	}
//	if(row == 2 && colume == 3){
//		cout << "hahaha\n";
//	}
	
}
~~~

#### 打印函数

打印地图

~~~c
//打印地图 
void printGraph(){
	cout << "\n----------------------------------------\n";
	for(int i = TOP; i <= BOTTOM; i++){
		for(int j = LEFT; j <= RIGHT; j++){
//			for(int k = 0; k < 4; k++){
//				cout << graph[i][j].vals[k] << "\t";
//			}
			cout << fixed << setprecision(4) << graph[i][j].maxVal;
			cout << " " << graph[i][j].decision << "|";
		}
		cout << "\n----------------------------------------\n";
	}
}

~~~

打印统计值

~~~c
//打印统计 
void printStatistics(){
	cout << "已迭代: " << ++times << "次\t" << "共递归: " << count << "次\n";
}
~~~

#### 刷新访问状态

~~~c
//刷新被访问状态 
void flushGraph(){
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 4; j++){
			graph[i][j].visited = false;
		}
	}
	//根据当前Q(S,A)设置最佳策略 
	setDecision();
	//打印地图 
	printGraph();
	//打印统计 
	printStatistics();
}
~~~

### 核心实现

值迭代函数

~~~c
//值迭代函数 
double value_iterate(int row, int colume){
	count++;
	//当碰到边界，直接返回0 
	if(row < TOP || row > BOTTOM || colume < LEFT || colume > RIGHT){
		return 0;
	}
	
	//当节点已经被访问过，说明已经赋值了，于是返回最大值 
	//或当碰到墙体或退出点，直接返回 
	if(graph[row][colume].visited || graph[row][colume].end){
		return graph[row][colume].maxVal;
	}
	
	graph[row][colume].visited = true;

	//向上递归 
	graph[row][colume].vals[0] = 0.9*(0.8*value_iterate(row-1,colume) + 0.1*value_iterate(row,colume-1) + 0.1*value_iterate(row,colume+1));
	
	//向下递归 
	graph[row][colume].vals[1] = 0.9*(0.8*value_iterate(row+1,colume) + 0.1*value_iterate(row,colume-1) + 0.1*value_iterate(row,colume+1));
	
	//向左递归 
	graph[row][colume].vals[2] = 0.9*(0.8*value_iterate(row,colume-1) + 0.1*value_iterate(row+1,colume) + 0.1*value_iterate(row-1,colume));
	
	//向右递归 
	graph[row][colume].vals[3] = 0.9*(0.8*value_iterate(row,colume+1) + 0.1*value_iterate(row+1,colume) + 0.1*value_iterate(row-1,colume));
	
	//设置最佳策略、最大奖励、标记已访问 
	setMaxVal(row, colume);
	
	//cout << "已递归" << count << "次\n";
	
	return graph[row][colume].maxVal;
	
}
~~~

main函数

- 记录上一轮起点的最大价值，在下一轮与新值比较，若相同则说明迭代完成，退出循环；若不同则继续迭代

~~~c
int main(){
	//设置起始点 
	int row = 1, colume = 0;
	//初始化地形：墙体和退出点 
	initGraph();
	//记录上一轮初始点值 
	double pre = graph[row][colume].maxVal;
	//开始迭代 
	while(1){	
		//从初识点开始一轮值迭代	
		value_iterate(row, colume);
		//刷新节点均为被访问，同时打印统计数据和地图 
		flushGraph();				
		//当迭代后值未变化，说明迭代完成，退出循环 
		if(graph[row][colume].maxVal == pre){
			break;
		}
		//否则将当前值赋给pre，进行下一轮迭代 
		pre = graph[row][colume].maxVal;
	}
	
	cout << "\n\n最终结果:";
	printGraph();
	

	return 0;
}
~~~

### 完整源码

~~~c
#include <iostream>
using namespace std;
#include <iomanip>

#define LEFT 0
#define RIGHT 3
#define TOP 0
#define BOTTOM 2

struct Node{
	//当前状态最大奖励
	double maxVal;
	//当前状态最佳决策 
	string decision;
	//当前状态分别向 上、下、左、右所获奖励 
	double vals[4];
	//是否被访问 
	bool visited;
	//是否是墙体或退出点 
	bool end;
};

Node graph[3][4];

//Q值设置当前状态最大奖励 
void setMaxVal(int row, int colume){

	for(int i = 0; i < 4; i++){
		if(graph[row][colume].vals[i] > graph[row][colume].maxVal){
			graph[row][colume].maxVal = graph[row][colume].vals[i];
		}
	}
//	if(row == 2 && colume == 3){
//		cout << "hahaha\n";
//	}
	
}

//根据Q值设置最佳策略 
void setDecision(){
	for(int i = TOP; i <= BOTTOM; i++){
		for(int j = LEFT; j <= RIGHT; j++){
			for(int k = 0; k < 4; k++){
				if(graph[i][j].vals[k] == graph[i][j].maxVal && graph[i][j].maxVal != 0){
					switch(k){
						case 0: graph[i][j].decision = "上"; break;
						case 1: graph[i][j].decision = "下"; break;
						case 2: graph[i][j].decision = "左"; break;
						case 3: graph[i][j].decision = "右"; break; 
					}
					break;
				}
			}
		}
	}
	
}

//设置退出点和墙体 
void setEnd(int row, int colume, double val){
	graph[row][colume].maxVal = val;
	graph[row][colume].end = true;
	graph[row][colume].decision = "无";
}

//初始化地图 
void initGraph(){
	//墙体 
	setEnd(1, 1, 0);
	//得分点 
	setEnd(0, 3, 1);
	//失分点 
	setEnd(1, 3, -1);
}


int count = 0;
int times = 0;
//值迭代函数 
double value_iterate(int row, int colume){
	count++;
	//当碰到边界，直接返回0 
	if(row < TOP || row > BOTTOM || colume < LEFT || colume > RIGHT){
		return 0;
	}
	
	//当节点已经被访问过，说明已经赋值了，于是返回最大值 
	//或当碰到墙体或退出点，直接返回 
	if(graph[row][colume].visited || graph[row][colume].end){
		return graph[row][colume].maxVal;
	}
	
	graph[row][colume].visited = true;

	//向上递归 
	graph[row][colume].vals[0] = 0.9*(0.8*value_iterate(row-1,colume) + 0.1*value_iterate(row,colume-1) + 0.1*value_iterate(row,colume+1));
	
	//向下递归 
	graph[row][colume].vals[1] = 0.9*(0.8*value_iterate(row+1,colume) + 0.1*value_iterate(row,colume-1) + 0.1*value_iterate(row,colume+1));
	
	//向左递归 
	graph[row][colume].vals[2] = 0.9*(0.8*value_iterate(row,colume-1) + 0.1*value_iterate(row+1,colume) + 0.1*value_iterate(row-1,colume));
	
	//向右递归 
	graph[row][colume].vals[3] = 0.9*(0.8*value_iterate(row,colume+1) + 0.1*value_iterate(row+1,colume) + 0.1*value_iterate(row-1,colume));
	
	//设置最佳策略、最大奖励、标记已访问 
	setMaxVal(row, colume);
	
	//cout << "已递归" << count << "次\n";
	
	return graph[row][colume].maxVal;
	
}

//打印地图 
void printGraph(){
	cout << "\n----------------------------------------\n";
	for(int i = TOP; i <= BOTTOM; i++){
		for(int j = LEFT; j <= RIGHT; j++){
//			for(int k = 0; k < 4; k++){
//				cout << graph[i][j].vals[k] << "\t";
//			}
			cout << fixed << setprecision(4) << graph[i][j].maxVal;
			cout << " " << graph[i][j].decision << "|";
		}
		cout << "\n----------------------------------------\n";
	}
}

//打印统计 
void printStatistics(){
	cout << "已迭代: " << ++times << "次\t" << "共递归: " << count << "次\n";
}

//刷新被访问状态 
void flushGraph(){
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 4; j++){
			graph[i][j].visited = false;
		}
	}
	//根据当前Q(S,A)设置最佳策略 
	setDecision();
	//打印地图 
	printGraph();
	//打印统计 
	printStatistics();
}

int main(){
	//设置起始点 
	int row = 1, colume = 0;
	//初始化地形：墙体和退出点 
	initGraph();
	//记录上一轮初始点值 
	double pre = graph[row][colume].maxVal;
	//开始迭代 
	while(1){	
		//从初识点开始一轮值迭代	
		value_iterate(row, colume);
		//刷新节点均为被访问，同时打印统计数据和地图 
		flushGraph();				
		//当迭代后值未变化，说明迭代完成，退出循环 
		if(graph[row][colume].maxVal == pre){
			break;
		}
		//否则将当前值赋给pre，进行下一轮迭代 
		pre = graph[row][colume].maxVal;
	}
	
	cout << "\n\n最终结果:";
	printGraph();
	

	return 0;
}
~~~

对于这样一个`3x4`的网格，概率精确到小数点后六位，使用值迭代函数需要递归两千一百多次，所有概率都趋于稳定，迭代21轮

## Q-Learning

对于同样的网格，对各状态Q-Value进行**学习**
$$
Q(s,a)=(1-\alpha)Q(s,a)+\alpha(R(s')+\lambda maxQ(s',a'))
$$

- α：学习率
- λ：折损率
- `s'`为状态`s`经过`a`到达的次状态

什么意思呢，就是说当前状态`s`执行行动`a`到达状态`s'`的价值等于经验`Q(s,a)`的一部分和目的状态`s'`的回报加`s'`最大的`Q-Value`的一部分

- 每执行一次行动，都要乘以一个折损率
- 对于每一次更新，都按学习率进行，即经验和新知识按这一比率构成新的经验

要注意的是，这并不是一个递归的过程，只是在当前基础上选择一个策略，执行，并获得结果并更新罢了

同样使用布尔变量`visited`表示访问状态，每轮学习时优先访问奖赏最大的节点，有概率（`epsilon`）访问其他节点

- `epsilon greedy`

### 初始化

节点结构体

~~~c
#include <iostream>
using namespace std;
#include <iomanip>
#include <stdlib.h>
#include <time.h>
//图边界 
#define left 0
#define right 3
#define top 0
#define bottom 2
//学习率 
#define rate 0.5
//折损率 
#define discount 0.9
//随即率
#define epsilon 0.2 

struct Node{
	//当前状态奖励
	double reward;
	//当前状态最佳决策 
	string decision;
	//当前状态分别向 上、下、左、右所获奖励 
	double vals[4];
	//是否被访问 
	bool visited;
	//是否是墙体或退出点 
	bool end;
};

Node graph[3][4];
~~~

方位类

- move函数便于移动至下一访问节点

  对于参数 step

  - 0表示向上移动
  - 1表示向下移动
  - 2表示向左移动
  - 3表示向右移动

~~~c
class Pos{
public:
	int row;
	int colume;
	
	Pos(int r, int c){
		row = r;
		colume = c;
	}
	
	Pos move(int step){
		switch(step){
			case 0:{
				Pos p(row-1, colume);
				return p;
			} 
			case 1:{
			 	Pos p(row+1, colume);
				return p;
			}
			case 2:{
				Pos p(row, colume-1);
				return p;
			}
			case 3:{
				Pos p(row, colume+1);
				return p;
			} 
		}
	}
};
~~~

初始化地图并设置退出点和墙体

~~~c
//设置退出点和墙体 
void setEnd(Pos p, double val){
	graph[p.row][p.colume].reward = val;
	graph[p.row][p.colume].end = true;
	graph[p.row][p.colume].decision = "无";
}
~~~

~~~c
//初始化地图 
void initGraph(){
	Pos p1(1, 1);
	Pos p2(0, 3);
	Pos p3(1, 3);
	
	//墙体 
	setEnd(p1, 0);
	//得分点 
	setEnd(p2, 1);
	//失分点 
	setEnd(p3, -1);
}
~~~

### 核心实现

`q-learning`其实很好实现，代工式就行了，主要是如何去选择下一步位置，这很关键

在当前节点`learning`后，继续在下一节点执行`q-learning`，这个节点的选取取决于附近节点的奖赏和随机概率

- 若抽中概率，在除去最大节点之外的可选区节点随机选一个作为下一访问节点

- count用于统计函数执行次数
- 如果学习到墙壁或退出点，该点下一步将推出，我这里处理为`if(node.end){ next = (-1,-1) }`即将下一步移到`(-1,-1)`，下一步必退出；另外将这样的点的`Q-Value`统一存储在`vals[0]`，因为他的`reward`和`q-value`是完全分开的

~~~c
int count = 0;
//Q-Learning 
//并非是递归过程，只是在向前探索罢了 
void q_learning(Pos p){
	count++;
	int row = p.row, colume = p.colume;
	//cout << row << "  " << colume << endl;
	//当碰到边界，直接返回0 
	if(row < top || row > bottom || colume < left || colume > right){
		return;
	}
	Node node = graph[row][colume];
	if(node.visited){
		return;
	}
	graph[row][colume].visited = true;
	//随机移动 
	int step = nextStep(p);
	//cout << step << endl;
	//下一步位置 
	Pos next = p.move(step);
	//当node为退出点，下一步置为(-1,-1)，maxQ接收这一坐标返回零 
	//同时下一次q-learning将超出边界直接退出 
	//将退出点的QValue存在val[0] 
	if(node.end){
		next = Pos(-1, -1);
		step = 0;
	}
	//cout << step << endl;
	graph[row][colume].vals[step] = (1-rate)*node.vals[step] + rate*(node.reward + discount*maxQ(next));

	q_learning(next);
}
~~~

下一步选取

返回下一步方位

- 0表示上，1表示下，2表示左，3表示右

~~~c
int nextStep(Pos p){
	int row = p.row, colume = p.colume;
	return maxR(row, colume);
}
~~~

选取当前位置附近的奖励最大的节点并返回，有一定随机概率返回的不是最大值，而是除最大值之外能到达的节点，避免陷于局部最大

- `epsilon greedy`

~~~c
int maxR(int row, int colume){
	int reward[4];
	
	if(row-1 < top){ reward[0] = -2; } 
	else if(graph[row-1][colume].visited){ reward[0] = -1; }
	else { reward[0] = graph[row-1][colume].reward; }
	
	if(row+1 > bottom){ reward[1] = -2; }
	else if(graph[row+1][colume].visited){ reward[1] = -1; }
	else { reward[1] = graph[row+1][colume].reward; }
	
	if(colume-1 < left){ reward[2] = -2; } 
	else if(graph[row][colume-1].visited){ reward[2] = -1; }
	else { reward[2] = graph[row][colume-1].reward; }
	
	if(colume+1 > right){ reward[3] = -2; }
	else if(graph[row][colume+1].visited){ reward[3] = -1; }
	else { reward[3] = graph[row][colume+1].reward; }
	
	//cout << r1 << " " << r2 << " "<< r3 << " "<< r4 << endl;
	int step = compare(reward[0], reward[1], reward[2], reward[3]);
	double random =  rand()%10 * 0.1;
	if(random <= epsilon){
		for(int i = 0; i < 4; i++){
			if(i != step && reward[i] != -2){
				step = i;
				break;
			}
		}
	}
	return step;
	
}
~~~

比较传入的四个值，根据大小返回最大值的下标

~~~c
int compare(int r1, int r2, int r3, int r4){
	//向上 
	if(r1 >= r2 && r1 >= r3 && r1 >= r4){ return 0; }
	//向右 
	if(r4 >= r1 && r4 >= r2 && r4 >= r3){ return 3; }
	//向下 
	if(r2 >= r1 && r2 >= r3 && r2 >= r4){ return 1; }
	//向左 
	if(r3 >= r1 && r3 >= r2 && r3 >= r4){ return 2;	}
	
}
~~~

根据当前节点`P`的`Q-Value`设置最佳策略

~~~c
void setDecision(Pos p, int index){
	int row = p.row, colume = p.colume;
	switch(index){
		case 0: graph[row][colume].decision = "上"; break;
		case 1: graph[row][colume].decision = "下"; break;
		case 2: graph[row][colume].decision = "左"; break;
		case 3: graph[row][colume].decision = "右"; break;
	}
} 

double maxQ(Pos p){
	int row = p.row, colume = p.colume;
	if(row == -1 && colume == -1){
		return 0;
	}
	Node node = graph[row][colume];
	if(node.end){
		return node.vals[0];
	}
	double max = 0;
	for(int i = 0; i < 4; i++){
		if(node.vals[i] > max){
			max = node.vals[i];
			if(!node.end)
				setDecision(p, i); 
		}
	}
	return max;
}
~~~

### 辅助函数

打印地图和刷新节点访问状态

~~~c
//打印图 
void printGraph(){
	cout << "\n----------------------------------------\n";
	for(int i = top; i <= bottom; i++){
		for(int j = left; j <= right; j++){
			Pos p(i, j);
			cout << fixed << setprecision(4) << maxQ(p);
			cout << " " << graph[i][j].decision << "|";
		}
		cout << "\n----------------------------------------\n";
	}
}

//刷新被访问状态 
void flushGraph(){
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 4; j++){
			graph[i][j].visited = false;
		}
	}
	//打印地图 
	printGraph();
}
~~~

### 完整源码

~~~c
#include <iostream>
using namespace std;
#include <iomanip>
#include <stdlib.h>
#include <time.h>
//图边界 
#define left 0
#define right 3
#define top 0
#define bottom 2
//学习率 
#define rate 0.5
//折损率 
#define discount 0.9
//随即率
#define epsilon 0.2 

struct Node{
	//当前状态奖励
	double reward;
	//当前状态最佳决策 
	string decision;
	//当前状态分别向 上、下、左、右所获奖励 
	double vals[4];
	//是否被访问 
	bool visited;
	//是否是墙体或退出点 
	bool end;
};

class Pos{
public:
	int row;
	int colume;
	
	Pos(int r, int c){
		row = r;
		colume = c;
	}
	
	Pos move(int step){
		switch(step){
			case 0:{
				Pos p(row-1, colume);
				return p;
			} 
			case 1:{
			 	Pos p(row+1, colume);
				return p;
			}
			case 2:{
				Pos p(row, colume-1);
				return p;
			}
			case 3:{
				Pos p(row, colume+1);
				return p;
			} 
		}
	}
};

Node graph[3][4];



//设置退出点和墙体 
void setEnd(Pos p, double val){
	graph[p.row][p.colume].reward = val;
	graph[p.row][p.colume].end = true;
	graph[p.row][p.colume].decision = "无";
}

//初始化地图 
void initGraph(){
	Pos p1(1, 1);
	Pos p2(0, 3);
	Pos p3(1, 3);
	
	//墙体 
	setEnd(p1, 0);
	//得分点 
	setEnd(p2, 1);
	//失分点 
	setEnd(p3, -1);
}

void setDecision(Pos p, int index){
	int row = p.row, colume = p.colume;
	switch(index){
		case 0: graph[row][colume].decision = "上"; break;
		case 1: graph[row][colume].decision = "下"; break;
		case 2: graph[row][colume].decision = "左"; break;
		case 3: graph[row][colume].decision = "右"; break;
	}
} 

double maxQ(Pos p){
	int row = p.row, colume = p.colume;
	if(row == -1 && colume == -1){
		return 0;
	}
	Node node = graph[row][colume];
	if(node.end){
		return node.vals[0];
	}
	double max = 0;
	for(int i = 0; i < 4; i++){
		if(node.vals[i] > max){
			max = node.vals[i];
			if(!node.end)
				setDecision(p, i); 
		}
	}
	return max;
}

int compare(int r1, int r2, int r3, int r4){
	//向上 
	if(r1 >= r2 && r1 >= r3 && r1 >= r4){ return 0; }
	//向右 
	if(r4 >= r1 && r4 >= r2 && r4 >= r3){ return 3; }
	//向下 
	if(r2 >= r1 && r2 >= r3 && r2 >= r4){ return 1; }
	//向左 
	if(r3 >= r1 && r3 >= r2 && r3 >= r4){ return 2;	}
	
}

int maxR(int row, int colume){
	int reward[4];
	
	if(row-1 < top){ reward[0] = -2; } 
	else if(graph[row-1][colume].visited){ reward[0] = -1; }
	else { reward[0] = graph[row-1][colume].reward; }
	
	if(row+1 > bottom){ reward[1] = -2; }
	else if(graph[row+1][colume].visited){ reward[1] = -1; }
	else { reward[1] = graph[row+1][colume].reward; }
	
	if(colume-1 < left){ reward[2] = -2; } 
	else if(graph[row][colume-1].visited){ reward[2] = -1; }
	else { reward[2] = graph[row][colume-1].reward; }
	
	if(colume+1 > right){ reward[3] = -2; }
	else if(graph[row][colume+1].visited){ reward[3] = -1; }
	else { reward[3] = graph[row][colume+1].reward; }
	
	//cout << r1 << " " << r2 << " "<< r3 << " "<< r4 << endl;
	int step = compare(reward[0], reward[1], reward[2], reward[3]);
	double random =  rand()%10 * 0.1;
	if(random <= epsilon){
		for(int i = 0; i < 4; i++){
			if(i != step && reward[i] != -2){
				step = i;
				break;
			}
		}
	}
	return step;
	
}

int nextStep(Pos p){
	int row = p.row, colume = p.colume;
	return maxR(row, colume);
}

int count = 0;
//Q-Learning 
//并非是递归过程，只是在向前探索罢了 
void q_learning(Pos p){
	count++;
	int row = p.row, colume = p.colume;
	//cout << row << "  " << colume << endl;
	//当碰到边界，直接返回0 
	if(row < top || row > bottom || colume < left || colume > right){
		return;
	}
	Node node = graph[row][colume];
	if(node.visited){
		return;
	}
	graph[row][colume].visited = true;
	//随机移动 
	int step = nextStep(p);
	//cout << step << endl;
	//下一步位置 
	Pos next = p.move(step);
	//当node为退出点，下一步置为(-1,-1)，maxQ接收这一坐标返回零 
	//同时下一次q-learning将超出边界直接退出 
	//将退出点的QValue存在val[0] 
	if(node.end){
		next = Pos(-1, -1);
		step = 0;
	}
	//cout << step << endl;
	graph[row][colume].vals[step] = (1-rate)*node.vals[step] + rate*(node.reward + discount*maxQ(next));

	q_learning(next);
}

//打印图 
void printGraph(){
	cout << "\n----------------------------------------\n";
	for(int i = top; i <= bottom; i++){
		for(int j = left; j <= right; j++){
			Pos p(i, j);
			cout << fixed << setprecision(4) << maxQ(p);
			cout << " " << graph[i][j].decision << "|";
		}
		cout << "\n----------------------------------------\n";
	}
}

//刷新被访问状态 
void flushGraph(){
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 4; j++){
			graph[i][j].visited = false;
		}
	}
	//打印地图 
	printGraph();
}

int main(){
	//设置起始点 
	int row = 2, colume = 0;
	Pos p(row, colume);
	//初始化地形：墙体和退出点 
	initGraph();
	cout << graph[1][3].reward << endl;
	//记录上一轮初始点值 
	for(int i = 0; i < 100; i++){
		q_learning(p);
		flushGraph();
	}
	
	cout << "共学习" << count << "次" << endl;
	//printGraph();

	system("pause"); 
	return 0;
}
~~~

相较于递归两千余次的值迭代，`q-learning`函数执行400余次后最佳策略已然可以得出并且不改变，执行1000余次后小数点后四位概率趋于稳定





