---
title: 极小极大搜索的井字棋
date: 2022-4-24
tags:
  - AI
  - C/C++
  - Algorithm
categories:
  - Toy
---

> Tic-Tac-Toe
>
> - MinimaxSearch算法
> - C语言实现
>
> 最深只有9层：has been solved
>
> 不得不说这是一个无聊的游戏：先落子者随便下不会输，后落子者占据中心后随便下不会输

## 核心实现

### 极小极大搜索

~~~c
//极小极大搜索
int minimaxSearch(int depth){	//传入当前深度
	int value = 0;
	//初始化value，man最差情况为min 
	if (player == MAN) value = INT_MIN;
	//初始化ai value，最差情况为max 
	if (player == AI) value = INT_MAX;
	//如果游戏结束或深度耗尽，直接返回估值
	if (isEnd() != 0 || depth == MAXDEPTH){
		return evaluate();
	}

	for (int i = 0; i < ROW; i++){
		for (int j = 0; j < COL; j++){
			//遍历可以落子的点
			//planing过程，假设落子，扩展下一层搜索 
			if (board[i][j] == 0){
				//若当前为man落子
				if (player == MAN){
					//落子当前空位，判定空位已被占 
					board[i][j] = MAN;
					//切换选手 
					player = AI;
					//递归调用
					int nextValue = minimaxSearch(depth + 1);
					//切回当前选手 
					player = MAN;
					//此时落子手为人，为max节点，要求下一值大于当前值继续行动 
					if (value < nextValue){
						value = nextValue;
						if (depth == curDepth){
							bestPos.x = i;
							bestPos.y = j;
						}
					}
				}else if (player == AI){	//若当前为ai落子
					board[i][j] = AI;
					//切换选手 
					player = MAN;
					int nextValue = minimaxSearch(depth + 1);
					player = AI;
					//此时落子手为ai，为min节点，要求下一值小于当前值继续行动 
					if (value > nextValue){
						value = nextValue;
						if (depth == curDepth){
							bestPos.x = i;
							bestPos.y = j;
						}
					}
				}
				//重置ij状态，寻找下一空位 
				board[i][j] = 0;
			}
		}
	}
	//返回本层值，人赢为极大值，ai赢为极小值 
	return value;
}
~~~

### 评估方法

~~~c
//评估方法
int evaluate(){
	//isEnd判断是谁赢了，1则人，0则平，-1则ai 
	//MAN=1, AI=-1
	int value = isEnd();
	//若人赢了，返回最大值 
	if (value == MAN) return INT_MAX;
	//若AI赢了返回最小值 
	if (value == AI) return INT_MIN;
	//否则返回0，即和棋 
	return value;
}
~~~

## 功能实现

### 选手落子

~~~c
//ai落子 
void ai_play(){
	minimaxSearch(curDepth);
	board[bestPos.x][bestPos.y] = AI;
	curDepth++;
	player = MAN;
}

//玩家落子
void man_play(int x, int y){
	board[x][y] = MAN;
	curDepth++;
	player = AI;
}
~~~

### 输入函数

一个回合，人先落子，ai跟着落子

~~~c
//输入坐标
bool drop(){
	while(true){
		char c = _getch();
		if (!gameover){
			if (c >= '1' && c <= '9'){
				int posNum = c - '1';
				if(isFilled(posNum)){
					cout << "该点已经落子, 请重新选择\n\n";
					continue;
				}
				int x = posNum / 3;
				int y = posNum % 3;
				man_play(x, y);
				if (isEnd() == 0 && curDepth <= 8){
					ai_play();
					//cout << curDepth << endl;
                      //当ai落子后深度达到9说明游戏已结束，但有可能未分胜负
					if (isEnd() != 0 || curDepth == MAXDEPTH){
						//cout << "hahaha";
						gameover = true;
					}
				}else{
					//cout << "hahaha";
					gameover = true;
				}
				return false;
			}
		}else{
			if (c == 'r' || c == 'R'){
				init();
				return false;
			}
			return true;
		}
		if (c == 'q' || c == 'Q'){
			return true;
		}
		cout << "\n输入不合规范, 请重新输入, 若想退出程序请输入Q\n";
	}
}
~~~

### 判断功能

#### 判断是否落子

~~~c
//判断该点是否落子
bool isFilled(int pos){
	int x = pos / 3;
	int y = pos % 3;
	if(board[x][y] != 0){
		return true;
	}
	return false;
}
~~~

#### 判断游戏是否结束

~~~c
//判断是否结束，即有没有三个连着的同方落子，并返回count/3
//ai填充的是-1，man填充的是1，若以ai结束（ai胜利），将返回-1；若以man结束，则返回1
//若游戏未结束，返回0
int isEnd(){
	int i, j;
	int count;
	for (i = 0; i < ROW; i++){   //检查横着的每行是否有三个连着 
		count = 0;
		for (j = 0; j < COL; j++)
			count += board[i][j];
		if (count == 3 || count == -3)
			return count / 3;
	}
	for (j = 0; j < COL; j++){   //检查竖着的每列是否有三个连着 
		count = 0;
		for (i = 0; i < ROW; i++){
			count += board[i][j];
		}
		if (count == 3 || count == -3){
			return count / 3;
		}	
	}
	
	//检查两个对角线是否三个连着 
	count = board[0][0] + board[1][1] + board[2][2];
	if (count == 3 || count == -3){
		return count / 3;
	}
		
	count = board[0][2] + board[1][1] + board[2][0];
	if (count == 3 || count == -3){
		return count / 3;
	}	
	return 0;
}
~~~

#### 判断获胜信息

并打印显示

~~~c
//判断获胜信息 
void win(){
	if (gameover){
		if (isEnd() == MAN){
			cout << "游戏结束, 玩家胜利!\n\n";
		}else if (isEnd() == AI){
			cout << "游戏结束, 电脑胜利捏\n\n";
		}else{
			cout << "游戏结束, 平局\n\n";
		}
		cout << "按R键重开, 按任意键退出游戏\n\n游戏结果\n";
	}
}
~~~

### 初始化

~~~c
//玩家编号
#define MAN 1
#define AI -1

//搜索深度
#define MAXDEPTH 9

//棋盘行列
#define ROW 3
#define COL 3

//棋盘
int board[3][3] = {{0,0,0}, {0,0,0}, {0,0,0}};
//默认玩家先手
int player;
//当前最佳落子位置 
Pos bestPos;
//当前搜索深度
int curDepth;
//游戏是否结束
bool gameover;

//初始化游戏：棋盘、先行玩家、搜索深度(0)、游戏是否结束(false)
void init(){
	cout << "准备初始化棋盘，在任意过程中按 Q/q 可退出程序\n使用数字 1-9 选择落子方位，按从左往右从上往下的顺序递增\n如左上角为 1，中间为 5，右下角为 9\n";
	for (int i = 0; i < COL; i++){
		for (int j = 0; j < ROW; j++){
			board[i][j] = 0;
		}
	}
	//谁先走并设置搜索深度
	if(ai_first()){
         //若ai先手，深度+1
		curDepth = 1;
	}else{
		curDepth = 0;
	}
	//切换玩家落子
	player = MAN;
	//游戏未结束
	gameover = false;
	
}
~~~

~~~c
bool ai_first(){	
	cout << "\nAI先行? y/n\n\n";
	while(true){
		char c = _getch();
		if(c == 'y' || c=='Y'){
			ai_play();
			cout << "\n棋局开始，请落子\n";
			return true;
		}
		if(c == 'n' || c == 'N'){
			cout << "\n棋局开始，请落子\n";
			return false;
		}
		cout << "未能识别的字符\n";
	}
	
} 
~~~

### 打印棋盘

~~~c
//打印棋盘，玩家为 O，ai为X
void printBoard(){
	int i, j;
	for (i = 0; i < ROW; i++){
		cout << "-------------\n";
		for (j = 0; j < COL; j++){
			if (board[i][j] == AI){
				cout << "| X ";
			}else if (board[i][j] == MAN){
				cout << "| O ";
			}else{
				cout << "|   ";
			}
		}
		cout << "|\n";
	}
	cout << "-------------\n";
}
~~~

## 完整代码

~~~c
#include<iostream>
#include<stdlib.h>
#include<conio.h>
#include<algorithm>
using namespace std;


//玩家编号
#define MAN 1
#define AI -1

//搜索深度
#define MAXDEPTH 9

//棋盘行列
#define ROW 3
#define COL 3


int isEnd();
int evaluate();
int minimaxSearch(int depth);
void init();
bool ai_first();
void win();


//position:{x, y}
struct Pos{
	int x;
	int y;
};

//棋盘
int board[3][3] = {{0,0,0}, {0,0,0}, {0,0,0}};
//默认玩家先手
int player;
//当前最佳落子位置 
Pos bestPos;
//当前搜索深度
int curDepth;
//游戏是否结束
bool gameover;


//初始化游戏：棋盘、先行玩家、搜索深度(0)、游戏是否结束(false)
void init(){
	cout << "准备初始化棋盘，在任意过程中按 Q/q 可退出程序\n使用数字 1-9 选择落子方位，按从左往右从上往下的顺序递增\n如左上角为 1，中间为 5，右下角为 9\n";
	for (int i = 0; i < COL; i++){
		for (int j = 0; j < ROW; j++){
			board[i][j] = 0;
		}
	}
	//谁先走并设置搜索深度
	if(ai_first()){
		curDepth = 1;
	}else{
		curDepth = 0;
	}
	//在搜索函数中默认玩家先走
	//若想让ai先走调用ai_first函数即可 
	player = MAN;
	//游戏未结束
	gameover = false;
	
}


//打印棋盘，玩家为 O，ai为X
void printBoard(){
	int i, j;
	//cout << endl;
	for (i = 0; i < ROW; i++){
		cout << "-------------\n";
		for (j = 0; j < COL; j++){
			if (board[i][j] == AI){
				cout << "| X ";
			}else if (board[i][j] == MAN){
				cout << "| O ";
			}else{
				cout << "|   ";
			}
		}
		cout << "|\n";
	}
	cout << "-------------\n\n";
}


//评估方法
int evaluate(){
	//isEnd判断是谁赢了，1则人，0则平，-1则ai 
	//MAN=1, AI=-1
	int value = isEnd();
	//若人赢了，返回最大值 
	if (value == MAN) return INT_MAX;
	//若AI赢了返回最小值 
	if (value == AI) return INT_MIN;
	//否则返回0，即和棋 
	return value;
}


//判断是否结束，即有没有三个连着的同方落子，并返回count/3
//ai填充的是-1，man填充的是1，若以ai结束（ai胜利），将返回-1；若以man结束，则返回1
//若游戏未结束，返回0
int isEnd(){
	int i, j;
	int count;
	for (i = 0; i < ROW; i++){   //检查横着的每行是否有三个连着 
		count = 0;
		for (j = 0; j < COL; j++)
			count += board[i][j];
		if (count == 3 || count == -3)
			return count / 3;
	}
	for (j = 0; j < COL; j++){   //检查竖着的每列是否有三个连着 
		count = 0;
		for (i = 0; i < ROW; i++){
			count += board[i][j];
		}
		if (count == 3 || count == -3){
			return count / 3;
		}	
	}
	
	//检查两个对角线是否三个连着 
	count = board[0][0] + board[1][1] + board[2][2];
	if (count == 3 || count == -3){
		return count / 3;
	}
		
	count = board[0][2] + board[1][1] + board[2][0];
	if (count == 3 || count == -3){
		return count / 3;
	}	
	return 0;
}


//极小极大搜索
int minimaxSearch(int depth){	//传入当前深度
	int value = 0;
	//初始化value，man最差情况为min 
	if (player == MAN) value = INT_MIN;
	//初始化ai value，最差情况为max 
	if (player == AI) value = INT_MAX;
	//如果游戏结束或深度耗尽，直接返回估值
	if (isEnd() != 0 || depth == MAXDEPTH){
		return evaluate();
	}

	for (int i = 0; i < ROW; i++){
		for (int j = 0; j < COL; j++){
			//遍历可以落子的点
			//planing过程，假设落子，扩展下一层搜索 
			if (board[i][j] == 0){
				//若当前为man落子
				if (player == MAN){
					//落子当前空位，判定空位已被占 
					board[i][j] = MAN;
					//切换选手 
					player = AI;
					//递归调用
					int nextValue = minimaxSearch(depth + 1);
					//切回当前选手 
					player = MAN;
					//此时落子手为人，为max节点，要求下一值大于当前值继续行动 
					if (value < nextValue){
						value = nextValue;
						if (depth == curDepth){
							bestPos.x = i;
							bestPos.y = j;
						}
					}
				}else if (player == AI){	//若当前为ai落子
					board[i][j] = AI;
					//切换选手 
					player = MAN;
					int nextValue = minimaxSearch(depth + 1);
					player = AI;
					//此时落子手为ai，为min节点，要求下一值小于当前值继续行动 
					if (value > nextValue){
						value = nextValue;
						if (depth == curDepth){
							bestPos.x = i;
							bestPos.y = j;
						}
					}
				}
				//重置ij状态，寻找下一空位 
				board[i][j] = 0;
			}
		}
	}
	//返回本层值，人赢为极大值，ai赢为极小值 
	return value;
}

//ai落子 
void ai_play(){
	minimaxSearch(curDepth);
	board[bestPos.x][bestPos.y] = AI;
	curDepth++;
	player = MAN;
}


//玩家落子
void man_play(int x, int y){
	board[x][y] = MAN;
	curDepth++;
	player = AI;
}


//判断该点是否落子
bool isFilled(int pos){
	int x = pos / 3;
	int y = pos % 3;
	if(board[x][y] != 0){
		return true;
	}
	return false;
}


//输入坐标
bool drop(){
	while(true){
		char c = _getch();
		if (!gameover){
			if (c >= '1' && c <= '9'){
				int posNum = c - '1';
				if(isFilled(posNum)){
					cout << "该点已经落子, 请重新选择\n\n";
					continue;
				}
				int x = posNum / 3;
				int y = posNum % 3;
				man_play(x, y);
				if (isEnd() == 0 && curDepth <= 8){
					ai_play();
					//cout << curDepth << endl;
					if (isEnd() != 0 || curDepth == MAXDEPTH){
						//cout << "hahaha";
						gameover = true;
					}
				}else{
					//cout << "hahaha";
					gameover = true;
				}
				return false;
			}
		}else{
			if (c == 'r' || c == 'R'){
				init();
				return false;
			}
			return true;
		}
		if (c == 'q' || c == 'Q'){
			return true;
		}
		cout << "\n输入不合规范, 请重新输入, 若想退出程序请输入Q\n";
	}
}

//判断获胜信息 
void win(){
	if (gameover){
		if (isEnd() == MAN){
			cout << "游戏结束, 玩家胜利!\n\n";
		}else if (isEnd() == AI){
			cout << "游戏结束, 电脑胜利捏\n\n";
		}else{
			cout << "游戏结束, 平局\n\n";
		}
		cout << "按R键重开, 按任意键退出游戏\n\n游戏结果\n";
	}
}

bool ai_first(){	
	cout << "\nAI先行? y/n\n\n";
	while(true){
		char c = _getch();
		if(c == 'y' || c=='Y'){
			ai_play();
			cout << "\n棋局开始，请落子\n";
			return true;
		}
		if(c == 'n' || c == 'N'){
			cout << "\n棋局开始，请落子\n";
			return false;
		}
		cout << "未能识别的字符\n";
	}
	
} 

int main(){
	init();
	while(true){
		printBoard();
		if(drop()){
			cout << "已退出游戏\n";
			break;
		}
		win();
	}
	system("pause");
	return 1;
}
~~~
