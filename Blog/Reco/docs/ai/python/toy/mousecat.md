---
title: 信息公开的博弈游戏示例
date: 2022-5-12
tags:
  - AI
  - Algorithm
  - Java
categories:
  - Toy
---

> 来自力扣913题：猫和老鼠
>
> 二维无向图中的猫抓老鼠
>
> 给定双方起始点和规则，计算哪方将胜出

## 题目描述

两位玩家分别扮演猫和老鼠，在一张 无向 图上进行游戏，两人轮流行动

图的形式是：

- graph[a] 是一个列表，这个列表储存了在节点`a`能够到达的其他节点，如：
  - `graph[1]={2,4,5}`，就表示在节点`1`，可以通向节点`2,4,5`，即三者与节点`1`相连
  - `graph[0]={1}`，就表示在节点`0`，可以通向节点`1`，因为是无向图，那么必然`graph[1]`中也包含节点`0`

移动规则如下：

- 老鼠从节点 1 开始，第一个出发；猫从节点 2 开始，第二个出发。在节点 0 处有一个洞
- 在每个玩家的行动中，他们 必须 沿着图中与所在当前位置连通的一条边移动。例如，如果老鼠在节点 1 ，那么它必须移动到 graph[1] 中的任一节点，即沿着弧移动
- 此外，猫无法移动到洞中（节点 0）


然后，游戏在出现以下三种情形之一时结束：

- 如果猫和老鼠出现在同一个节点，猫获胜
- 如果老鼠到达洞中，老鼠获胜
- 如果某一位置重复出现（即，玩家的位置和移动顺序都与上一次行动相同），游戏平局

给你一张图 graph ，并假设两位玩家都都以最佳状态参与游戏：

- 如果老鼠获胜，则返回 1
- 如果猫获胜，则返回 2
- 如果平局，则返回 0 

## 自顶向下的动态规划

> 动态规划、深度优先搜索、递归
>
> 之前写过一次，半天写不出来最后看的答案，过不久又不会辽，太难辽

维护一个三维数组`dp[mouse_pos][cat_pos][result]`，储存鼠、猫位置对应的游戏结果

- 初始化：当猫鼠位置重合，猫获胜；当鼠在节点0，猫在任意节点，鼠获胜
- 对于每个状态进行深度优先搜索，递归完善这个三维数组
- 最后返回`dp[1][2]`，即为游戏结果

~~~java
package com.solution;

import java.util.Arrays;

public class MouseCatGame {

    // 结果状态
    //猫获胜
    private static final int catWin = 2;
    //平局
    private static final int draw = 0;
    //鼠获胜
    private static final int mouseWin = 1;

    //节点数
    private int n;
    //储存猫鼠节点以及对应结果
    private int[][][] dp;
    // 储存地图
    private int[][] graph;
    
    //开始游戏
    public int mouseCatGame(int[][] graph){
        n = graph.length;
        dp = new int[n][n][2*n];
        //初始化dp数组，令结果全为-1，即没有结果
        for(int[][] i: dp){
            for(int[] j: i){
                Arrays.fill(j, -1);
            }
        }
        this.graph = graph;
        return getRes(1, 2, 0);
    }
	
    //获取当前结果
    public int getRes(int mouse, int cat, int steps){
        //当移动步数大于2*n时，说明绝对有重复的步骤，直接返回平局0
        if(steps >= 2*n){
            return draw;
        }
        //当当前节点为-1，即没有结果
        if(dp[mouse][cat][steps] < 0){
            //若老鼠在洞里，返回老鼠获胜1
            if(mouse == 0){
                dp[mouse][cat][steps] = mouseWin;
            } else if(mouse == cat){//若猫鼠位置重合，返回猫获胜2
                dp[mouse][cat][steps] = catWin;
            } else{//否则进入深度优先搜索
                getNextRes(mouse, cat, steps);
            }
        }
        return dp[mouse][cat][steps];
    }

    public void getNextRes(int mouse, int cat, int steps){
        //判断当前是谁在移动
        int curMove = steps%2 == 0 ? mouse:cat;
        int defaultRes = curMove==mouse ? catWin:mouseWin;
        int res = defaultRes;
        //遍历可以移动的节点
        for(int nextStep: graph[curMove]){
            if(curMove == cat && nextStep == 0){
                continue;
            }
            int mouseNextStep = curMove==mouse ? nextStep:mouse;
            int catNextStep = curMove==cat ? nextStep:cat;
            //进入下一层dfs
            int nextRes = getRes(mouseNextStep, catNextStep, steps+1);
            if(nextRes != defaultRes){
                res = nextRes;
                if(res != draw){
                    break;
                }
            }
        }
        dp[mouse][cat][steps] = res;
    }
}
~~~

这种暴力的`dfs`和动态规划太费资源了，地图一大时间和空间上都受不了

## 拓扑排序



