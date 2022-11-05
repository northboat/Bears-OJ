# 字节2018笔试

> From Now Coder
>

## 一、算法纠错

以下函数用于将一颗二叉搜索树转换成一个有序的双向链表。要求不能创建任何新的节点，只能调整树种节点指针的指向。

如输入下图中左边的二叉搜索树，则输出转换后的排序双向链表：

~~~
 		 10
 
         /    \
 
       6        14
 
    /    \     /    \
 
  4       8   12      16
~~~

转换成：`4 <=> 6 <=> 8 <=> 10 <=> 12  <=> 14 <=> 16`

请指出程序代码中错误的地方（问题不止一处，请尽量找出所有你认为错误的地方）

~~~c
#include <stack>
using namespace std;

struct TreeNode {
	int val;  
	TreeNode *left, *right;  
};

TreeNode* Convert(TreeNode* root){  
	if (root == NULL)  
		return root;
	TreeNode* listHead = NULL;
	TreeNode* listLastNode = NULL;
	stack<TreeNode*> s;
	while(root){
		while(root){
			root=root->left;
			s.push(root);
		}
		root=s.top();  
		s.pop();
		if (listHead == NULL){
		listHead = root;
		}else{
			listLastNode->right = root;
		}
		listLastNode = root;
		root= root->right;
	}
	return listHead;
}
~~~

## 二、系统设计

对于广告投放引擎， 广告库索引服务是基础服务，每次广告请求会从广告索引中找出匹配的广告创意列表。假设每一次请求会携带 地域、运营商、设备机型、网络接入方式 等信息，每个广告策略都可以设置 地域、运营商、设备机型、网络接入方式 的投放定向（即只能投放到定向匹配的请求， 比如只投放特定地域）。每个广告策略下包含N(N>=1)个广告创意。设计一个广告库索引模块， 需要支持以下几点：

1. 支持多线程广告请求可以快速的找到匹配的所有广告创意
2. 支持广告库数据的热更新
3.  支持十万级广告策略，百万级广告创意
4. 支持高并发请求

请给出广告库索引服务整体系统设计以及所使用到的数据结构设计

## 三、算法题Ⅰ

有三只球队，每只球队编号分别为球队1，球队2，球队3，这三只球队一共需要进行 n 场比赛。现在已经踢完了k场比赛，每场比赛不能打平，踢赢一场比赛得一分，输了不得分不减分。已知球队1和球队2的比分相差d1分，球队2和球队3的比分相差d2分，每场比赛可以任意选择两只队伍进行。求如果打完最后的 (n-k) 场比赛，有没有可能三只球队的分数打平。 

~~~java
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int t = scanner.nextInt();
    for (int i = 0; i < t; i++) {
        long n = scanner.nextLong();
        long k = scanner.nextLong();
        long d1 = scanner.nextLong();
        long d2 = scanner.nextLong();
 
        if (n % 3 != 0) {
            System.out.println("no");
            continue;
        }
        //如果能完成，每个队伍的最后得分为res
        long res = n / 3;
        //执行完k次可能有4种情况，求出这4种情况是否可能出现
        long[][] dir = new long[][]{{d1, d2}, {d1, -d2}, {-d1, d2}, {-d1, -d2}};
        boolean finished = false;
        for (int j = 0; j < 4; j++) {
            if ((k - dir[j][0] - dir[j][1]) % 3 == 0) {
                //执行完k次 第2只队伍的得分
                long second = (k - dir[j][0] - dir[j][1]) / 3;
                //求出当前k次执行完的情况是否可能出现
                if (second >= 0 && second + dir[j][0] >= 0 && second + dir[j][1] >= 0) {
                    //当前每个组的分数不能比最终的结果高
                    if (res >= second && res >= dir[j][0] + second && res >= dir[j][1] + second) {
                        finished = true;
                        break;
                    }
                }
            }
        }
        if (finished) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
    scanner.close();
}
~~~

## 四、算法题Ⅱ

唯一的20分

有一个仅包含 ’a’ 和 ’b’ 两种字符的字符串s，长度为n，每次操作可以把一个字符做一次转换（把一个 ’a’ 设置为 ’b’ ，或者把一个 ’b’ 置成 ’a’ )；但是操作的次数有上限m，问在有限的操作数范围内，能够得到最大连续的相同字符的子串的长度是多少

~~~java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        String str = scanner.next();
        System.out.println(main.maxSonOfString(n, m, str));
    }
 
    public int[] toInt(String str){
        List<Integer> list = new ArrayList<>();
        int n = str.length();
        int i = 0;
        while(i < n){
            int count = 0;
            while(i < n && str.charAt(i) == 'a'){
                count++;
                i++;
            }
            if(count != 0){
                list.add(count);
            }
            count = 0;
            while(i < n && str.charAt(i) == 'b'){
                count++;
                i++;
            }
            if(count != 0){
                list.add(count);
            }
        }
 
        int[] res = new int[list.size()];
        int c = 0;
        for(int j: list){
            res[c++] = j;
        }
        return res;
    }
 
    public int maxSonOfString(int n, int m, String str){
        if(m == n){
            return n;
        }
        int[] l = toInt(str);
        int t = l.length;
        int res = 0;
        for(int i = 0; i < t; i++){
            int count = 0;
            int down = m;
            count += l[i];
            for(int j = i+1; j < t; j++){
                if((j-i)%2 != 0){
                    if(down >= l[j]){
                        down -= l[j];
                        count += l[j];
                    }else{
                        count += down;
                        break;
                    }
                }else{
                    count += l[j];
                }
            }
            if(count > res){
                res = count;
            }
        }
        return res;
    }
}
~~~

## 五、智力题

附加题

存在`n+1`个房间，每个房间依次为房间`1 2 3...i`，每个房间都存在一个传送门，`i`房间的传送门可以把人传送到房间`pi(1<=pi<=i)`,现在路人甲从房间`1`开始出发(当前房间`1`即第一次访问)，每次移动他有两种移动策略：
  A. 如果访问过当前房间`i`偶数次，那么下一次移动到房间`i+1`
  B. 如果访问过当前房间`i`奇数次，那么移动到房间`pi`
现在路人甲想知道移动到房间`n+1`一共需要多少次移动