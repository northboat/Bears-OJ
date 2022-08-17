---
title: Common Algorithm Summary
date: 2021-6-22
tags:
  - Algorithm
---

## 宽度优先搜索

Breadth First Search

### 二叉树的层序遍历

力扣102

> 借助队列这一数据结构辅助实现

~~~java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if(root==null){
            return list;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while(!q.isEmpty()){
            List<Integer> row = new ArrayList<>();
            //记录该层节点个数
            int n = q.size();
            for(int i = 0; i < n; i++){
                //依次出列
                TreeNode p = q.poll();
                row.add(p.val);
                //依次入列，左——>右
                if(p.left!=null){
                    q.offer(p.left);
                }
                if(p.right!=null){
                    q.offer(p.right);
                }
            }
            list.add(row);
        }
        return list;
    }
}
~~~

### 路径总和

力扣112

~~~java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    private boolean flag = false;

    public void bfs(TreeNode root, int targetSum){
        if(root == null){
            return;
        }
        Queue<TreeNode> nodeQue = new LinkedList<>();
        Queue<Integer> valQue = new LinkedList<>();
        nodeQue.offer(root);
        valQue.offer(root.val);
        while(nodeQue.size() != 0){
            TreeNode cur = nodeQue.poll();
            int temp = valQue.poll();
            if(cur.left == null && cur.right == null){
                if(temp == targetSum){
                    flag = true;
                    break;
                }
                continue;
            }
            if(cur.left != null){
                nodeQue.offer(cur.left);
                valQue.offer(temp + cur.left.val);
            }
            if(cur.right != null){
                nodeQue.offer(cur.right);
                valQue.offer(temp + cur.right.val);
            }
        }
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        bfs(root, targetSum);
        return flag;
    }
}
~~~

### 奇偶树

力扣1609

~~~java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isEvenOddTree(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int level = 0;
        while(!q.isEmpty()){
            int n = q.size();
            if(level % 2 == 0){
                int temp = Integer.MIN_VALUE;
                for(int i = 0; i < n; i++){
                    TreeNode cur = q.poll();
                    int val = cur.val;
                    if(val%2 == 0 || val <= temp){
                        return false;
                    }
                    if(cur.left != null){
                        q.offer(cur.left);
                    }
                    if(cur.right != null){
                        q.offer(cur.right);
                    }
                    temp = val;
                }
            } else {
                int temp = Integer.MAX_VALUE;
                for(int i = 0; i < n; i++){
                    TreeNode cur = q.poll();
                    int val = cur.val;
                    if(val%2 == 1 || val >= temp){
                        return false;
                    }
                    if(cur.left != null){
                        q.offer(cur.left);
                    }
                    if(cur.right != null){
                        q.offer(cur.right);
                    }
                    temp = val;
                }
            }
            level++;
        }
        return true;
    }
}
~~~

## 深度优先搜索

Deep First Search

### 递增顺序搜索树

力扣897

~~~c
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {

private:
    TreeNode* pre = new TreeNode();
    TreeNode* head = new TreeNode();
public:
    void inorder(TreeNode* node)
    {
        //当当前指针不为空
        if(node == nullptr)
        {
            return;
        }
        inorder(node->left);
        //令pre的右指针指向当前节点
        pre->right = node;
        //令当前节点的左指针为空
        node->left = nullptr;
        //令pre为当前指针，即下一步的前驱
        pre = node;
        inorder(node->right);
    }

    //寻找最左节点：即新生成链表的表头
    void findHead(TreeNode* root)
    {
        TreeNode* p = root;
        while(p->left != nullptr)
        {
            p = p->left;
        }
        head = p;
    }

    TreeNode* increasingBST(TreeNode* root)
    {
        findHead(root);
        inorder(root);
        return head;
    }
};
~~~

### 二叉搜索树的范围和

力扣938

~~~c
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
private:
    int res = 0;

public:
    void inorder(TreeNode* root, int low, int high)
    {
        if(root == nullptr)
        {
            return;
        }
        inorder(root->left, low, high);
        if(root->val >= low && root->val <= high)
        {
            res += root->val;
        }
        inorder(root->right, low, high);
    }


    int rangeSumBST(TreeNode* root, int low, int high) 
    {
        inorder(root, low, high);
        return res;
    }
};
~~~

### 二叉树的中序遍历

力扣94

~~~java
 /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    private List<Integer> list = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        if(root!=null && root.left!=null)
            inorderTraversal(root.left);
        if(root!=null)
            list.add(root.val);
        if(root!=null && root.right!=null)
            inorderTraversal(root.right);
        return list;
    }
}
~~~

### 二叉树的最近祖先

力扣236

> 递归，深度优先搜索
>
> 明确 root “是 q 和 p 公共祖先” 的条件：(l&&r) || ((root==p||root==q)&&(l||r)
>
> l：指左子树为 p 或 q 的祖先；r：指右子树为 p 或 q 的祖先

~~~java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    private TreeNode res;

    public boolean dfs(TreeNode root, TreeNode p, TreeNode q){
        if(root==null){
            return false;
        }
        boolean l = dfs(root.left, p, q);
        boolean r = dfs(root.right, p, q);
        if((l&&r) || ((root==p||root==q)&&(l||r))){
            res = root;
        }
        if(l || r || root==p || root==q){
            return true;
        }
        return false;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return res;
    }
}
~~~

### 路径总和

力扣112

~~~java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    private boolean flag = false;

    public void dfs(TreeNode root, int targetSum){
        if(root == null){
            return;
        }
        if(root.left == null && root.right == null && root.val == targetSum){
            flag = true;
        }
        int newTarget = targetSum - root.val;
        dfs(root.left, newTarget);
        dfs(root.right, newTarget);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return flag;
    }
}
~~~

### 连接词

力扣472

~~~java
public class Solution {
    static class Trie{
        public Trie[] children;
        public boolean isEnd;

        public Trie(){
            children = new Trie[26];
            isEnd = false;
        }
    }
	//字典树
    private Trie trie = new Trie();
	//将单词插入字典树
    public void insert(String word){
        Trie p = trie;
        int n = word.length();
        for(int i = 0; i < n; i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(p.children[index] == null){
                p.children[index] = new Trie();
            }
            p = p.children[index];
        }
        p.isEnd = true;
    }

    public boolean dfs(String word, int start){
        //当已经搜索到最后一位，说明该词被连接而成
        if(word.length() == start){
            return true;
        }
        Trie p = trie;
        for(int i = start; i < word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(p.children[index] == null){
                return false;
            }
            p = p.children[index];
            if(p.isEnd){
                //深度优先搜索
                if(dfs(word, i+1)){
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> findAllConcatenatedWordsInADict(String[] words){
        List<String> res = new ArrayList<>();
        Arrays.sort(words, (a, b)-> {
            return a.length()-b.length();
        });
        for(String word: words){
            if(word.length() == 0){
                continue;
            }
            if(dfs(word, 0)){
                res.add(word);
            } else {
                insert(word);
            }
        }
        return res;
    }
}
~~~

### 猫和老鼠

力扣913

> 在一场信息公开的游戏中，总有一方有一种方法使之不会输

~~~java
package com.solution;

import java.util.Arrays;

public class MouseCatGame {

    private static final int catWin = 2;
    private static final int draw = 0;
    private static final int mouseWin = 1;

    private int n;
    private int[][][] dp;
    private int[][] graph;

    public int mouseCatGame(int[][] graph){
        n = graph.length;
        dp = new int[n][n][2*n];
        for(int[][] i: dp){
            for(int[] j: i){
                Arrays.fill(j, -1);
            }
        }
        this.graph = graph;
        return getRes(1, 2, 0);
    }

    public int getRes(int mouse, int cat, int steps){
        if(steps >= 2*n){
            return draw;
        }
        if(dp[mouse][cat][steps] < 0){
            if(mouse == 0){
                dp[mouse][cat][steps] = mouseWin;
            } else if(mouse == cat){
                dp[mouse][cat][steps] = catWin;
            } else{
                getNextRes(mouse, cat, steps);
            }
        }
        return dp[mouse][cat][steps];
    }

    public void getNextRes(int mouse, int cat, int steps){
        int curMove = steps%2 == 0 ? mouse:cat;
        int defaultRes = curMove==mouse ? catWin:mouseWin;
        int res = defaultRes;
        for(int nextStep: graph[curMove]){
            if(curMove == cat && nextStep == 0){
                continue;
            }
            int mouseNextStep = curMove==mouse ? nextStep:mouse;
            int catNextStep = curMove==cat ? nextStep:cat;
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

### 最长递增子序列的个数

力扣673

> `dp[i]`记录当前位置能构成的最长递增子序列的长度
>
> 对`dp[i]==maxLength`的位置进行深度优先搜索，找到能构成其最长递增子序列的道路总数，返回条件为`dp[j]==1 && nums[j]<pre`，其中`pre`为上一层的数大小

~~~java
class Solution {
    private int[] dp;
    private int res;

    public int buildDp(int[] nums){
        res = 0;
        int n = nums.length;
        dp = new int[n];
        dp[0] = 1;
        int maxLength = 1;
        for(int i = 1; i < n; i++){
            int d = 0, j;
            for(j = i-1; j >= 0; j--){
                if(nums[j] < nums[i] && dp[j] > d){
                    d = dp[j];
                }
            }
            dp[i] = d+1;
            maxLength = Math.max(maxLength, dp[i]);
        }
        return maxLength;
    }

    public void dfs(int index, int pre, int[] nums){
        if(dp[index] == 1){
            if(nums[index] < pre){
                res++;
            }
            return;
        }
        for(int i = 0; i < index; i++){
            if(dp[i] == dp[index]-1 && nums[i] < nums[index]){
                dfs(i, nums[index], nums);
            }
        }
    }

    public int findNumberOfLIS(int[] nums){
        int n = nums.length;
        int maxLength = buildDp(nums);
        if(maxLength == 1){
            return n;
        }
        for(int i = 1; i < n; i++){
            if(dp[i] == maxLength){
                dfs(i, maxLength, nums);
            }
        }
        return res;
    }

}
~~~

### 累加数

力扣306

> 外两层循环枚举第一、第二结束点控制变量（`第一结束点+1==第二起始点`）
>
> 内一层循环枚举第三结束点（`第二结束点+1==第三起始点`）
>
> 若`pre+cur==next`，向后搜索下一组数，直到`index==n-1`，即第三结束点为串末尾，返回`true`
>
> 若`pre+cur<next`，跳出本次循环，因为在第三结束点向后移动的过程中，`next`越来越大
>
> 若`pre+cur>next`，向后循环遍历第三结束点，增大`next`

~~~java
package com.solution;

public class IsAdditiveNumber {
    public boolean isAdditiveNumber(String nums){
        int n = nums.length();
        char[] charNums = nums.toCharArray();
        for(int i = 0; i < n-1; i++){
            if(charNums[0] == '0' && i > 0){ return false; }
            long pre = Long.parseLong(nums.substring(0, i+1));
            for(int j = i+1; j < n-1; j++){
                if(charNums[i+1] == '0' && j > i+1){
                    continue;
                }
                long cur = Long.parseLong(nums.substring(i+1, j+1));
                if(dfs(nums, pre, cur, n, j)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(String nums, long pre, long cur, int length, int index) {
        //退出条件
        if (index == length-1) {
            return true;
        }
        for (int i = index + 1; i < length; i++) {
            if (nums.charAt(index + 1) == '0' && i > index + 1) { return false; }
            long next = Long.parseLong(nums.substring(index + 1, i + 1));
            System.out.println(pre + "+" + cur + " " + next + "  " + i);
            if (next > pre + cur) { return false; }
            if (next == pre + cur) { return dfs(nums, cur, next, length, i); }
        }
        return false;
    }

    public static void main(String[] args) {
        IsAdditiveNumber ian = new IsAdditiveNumber();
        System.out.println(ian.isAdditiveNumber("112358"));
    }
}

~~~

## 动态规划

dynamic programming

### 最大子序和

力扣53

~~~java
public class MaxSumSubArray {
	public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] f = new int[n];
        f[0] = nums[0];
        //动态规划
        //当前者和大于0，将其状态转移到后者
        //当前者和小于0，放弃掉前者和，重新计算最大和，即重新进行转移
        //对和的数组排序，返回其最大和
        for(int i = 1; i < n; i++){
            if(f[i-1] > 0){
                f[i] += f[i-1] + nums[i];
            }
            else{
                f[i] = nums[i];
            }
        }
        Arrays.sort(f);
        return f[n-1];
    }
}
~~~

### 解码方法

力扣91

~~~java
//动态规划：状态转移方程
public class NumDecodings {
	public int decodings(String s) {
		
		int n = s.length();
		int f[] = new int[n+1];
		f[0] = 1;
		//该在for循环中++i和i++等价
		for(int i = 1; i < n+1; i++) {
			f[i] = 0;
			//状态转移方程1
			//其实在12行不初始化，f[i]也默认为0
			if(s.charAt(i-1) != '0') {
				f[i] += f[i-1];
			}	
			//状态转移方程2
			//转换类型，判断两位数是否 <= 26
			if(i > 1 && s.charAt(i-2) != '0' && (s.charAt(i-2)-'0')*10 + (s.charAt(i-1)-'0') <= 26) {
				f[i] += f[i-2];
			}			
		}
		return f[n];
	}
	
	
	public static void main(String[] args) {
		NumDecodings nd = new NumDecodings();
		String str = "226712";
		System.out.println(nd.decodings(str));
	}
}
~~~

### 最长回文子串

力扣5

~~~java
//当s[i]==s[j]，dp[i][j]是否回文取决于dp[i+1][j-1]是否回文
//所以用boolean数组dp[i][j]记录回文子串s[i]到s[j]的状态
//第一步初始化dp[i][i]=true，即单个字符均回文
//Len为子串长度，i为左边界，j为右边界

class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        if(n<2){
            return s;
        }
        boolean[][] dp = new boolean[n][n];
        for(int i = 0; i < n; i++){
            dp[i][i] = true;
        }
        int maxLength = 1;
        int begin = 0;
        for(int Len = 2; Len <= n; Len++){
            for(int i = 0; i < n; i++){
                int j = i+Len-1;            
                if(j>=n){
                    break;
                }
                if(s.charAt(i)==s.charAt(j)){
                    if(j-i<3){
                        dp[i][j] = true;
                    }else{
                        dp[i][j] = dp[i+1][j-1];
                    }
                }else{
                    dp[i][j] = false;
                }
                if(dp[i][j] && j-i+1>maxLength){
                    begin = i;
                    maxLength = j-i+1;
                }
            }
        }
        return s.substring(begin, begin+maxLength);
    }
}
~~~

### 回文子串

力扣647

~~~java
class Solution {
    public int countSubstrings(String s) {
        int n = s.length(), count = 0;
        boolean dp[][] = new boolean[n][n];
        for(int i = 0; i < n; i++){
            dp[i][i] = true;
        }
        for(int Len = 2; Len <= n; Len++){
            for(int i = 0; i < n; i++){
                int j = i+Len-1;
                if(j>=n){
                    break;
                }
                if(s.charAt(i)==s.charAt(j)){
                    if(Len <= 3){
                        dp[i][j] = true;
                    }else{
                        dp[i][j] = dp[i+1][j-1];
                    }
                }else{
                    dp[i][j] = false;
                }
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(dp[i][j]){
                    count++;
                }
            }
        }
        return count;
    }
}
~~~

### 最长递增子序列

力扣300

> 用 dp[i] 记录第 i 个元素能构成的递增子序列大小：初始化为 1，对小于 i 的元素遍历，若当前元素大于 nums[j]，则 dp[i] = dp[j]+1，此为状态转移方程

~~~java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        if(n == 0){
            return 0;
        }
        int maxLength = 1;
        int[] dp = new int[n];
        dp[0] = 1;
        for(int i = 1; i < n; i++){
            dp[i] = 1;
            for(int j = 0; j < i; j++){               
                if(nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            maxLength = Math.max(dp[i], maxLength);
        }
        return maxLength;
    }
}
~~~

### 消除游戏

力扣390

> `1 2 3 4 5 6 7 8 9`
>
> 消除一轮:`2 4 6 8`（从头开始消除单数序号的数）
>
> 消除二轮:`2 6`（从尾开始消除单数序号的数）
>
> 消除三轮:`6 ——> 最终结果`（从头消除单数序号）
>
> 先从最简单的情况入手：n=1时，答案为1。n=2时，答案为2。
>
> 可以发现，答案一定不会是奇数，因为第一轮操作一定会将所有的奇数删除。这就提示出一个规律：
> 如果n为奇数，那么以n结尾和以n-1结尾是完全一样的。
> 例如1,2,3,4,5,6,7,8,9，操作一轮后剩2,4,6,8，下一轮从8开始；
> 而1,2,3,4,5,6,7,8，操作一轮后也剩2,4,6,8，下一轮也从8开始。
> 从而得到第一个递推公式：当n为奇数时，`dp[n] = dp[n-1]`;
>
> 2.3 接下来就只剩n为偶数的情况了。
> 仍以1,2,3,4,5,6,7,8为例，操作一轮后剩2,4,6,8， 下一轮从8开始。
> 那么2,4,6,8从8开始，和2,4,6,8从2开始有什么区别呢？
> 很明显就是轴对称的关系，前者剩6，后者就剩(8+2 - 6);
>
> 那么2,4,6,8从2开始，和1,2,3,4从1开始有什么区别呢？
> 这个关系更明显，就是2倍的关系。
>
> 写到这里，大家应该明白了，1,2,3,4从1开始不就是`dp[4]`吗！
> 也就是说，`dp[8] = 2*(1+4-dp[4])` 这里的`1+4-dp[4]`起的就是轴对称的作用。
> 推而广之，n为偶数时，`dp[n] = 2*(1+n/2-dp[n/2])`
> 这样完整的递推公式就完成了：
> n为奇数时，`dp[n] = dp[n-1]`
> n为偶数时，`dp[n] = 2(1+n/2-dp[n/2])`

由于`dp[1000000]`超出内存限制，采用递归的写法，思路一样，只是未构造dp数组

~~~java
class Solution {

    public int dp(int n){
        if(n == 1){
            return 1;
        }
        if(n == 2){
            return 2;
        }
        if(n % 2 == 1){
            return dp(n-1);
        }
        if(n % 2 == 0){
            return 2*(1+n/2-dp(n/2));
        }
        return -1;
    }
    
    public int lastRemaining(int n) {
        return dp(n);
    }
}
~~~

### 猫和老鼠

力扣913

> 数据结构：无向图
>
> 算法：动态规划、深度优先搜索、递归
>
> 算尽从初始点穷尽努力的结果，没有变数

~~~java
package com.solution;

import java.util.Arrays;

public class MouseCatGame {

    private static final int catWin = 2;
    private static final int draw = 0;
    private static final int mouseWin = 1;

    private int n;
    private int[][][] dp;
    private int[][] graph;

    public int mouseCatGame(int[][] graph){
        n = graph.length;
        dp = new int[n][n][2*n];
        for(int[][] i: dp){
            for(int[] j: i){
                Arrays.fill(j, -1);
            }
        }
        this.graph = graph;
        return getRes(1, 2, 0);
    }

    public int getRes(int mouse, int cat, int steps){
        if(steps >= 2*n){
            return draw;
        }
        if(dp[mouse][cat][steps] < 0){
            if(mouse == 0){
                dp[mouse][cat][steps] = mouseWin;
            } else if(mouse == cat){
                dp[mouse][cat][steps] = catWin;
            } else{
                getNextRes(mouse, cat, steps);
            }
        }
        return dp[mouse][cat][steps];
    }

    public void getNextRes(int mouse, int cat, int steps){
        int curMove = steps%2 == 0 ? mouse:cat;
        int defaultRes = curMove==mouse ? catWin:mouseWin;
        int res = defaultRes;
        for(int nextStep: graph[curMove]){
            if(curMove == cat && nextStep == 0){
                continue;
            }
            int mouseNextStep = curMove==mouse ? nextStep:mouse;
            int catNextStep = curMove==cat ? nextStep:cat;
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

### 最长递增子序列的个数

力扣673

> `dp[i]`记录下标`i`元素能构成的最长递增子序列的长度
>
> `count[i]`记录下标`i`元素构成最长递增子序列的道路总数

~~~java
package com.solution;

import java.util.Arrays;

//1073741824
public class FindNumberOfLIS {
    private int[] dp;
    private int[] count;

    public int buildDp(int[] nums){
        int n = nums.length;
        dp = new int[n];
        count = new int[n];
        int maxLength = 1;
        for(int i = 0; i < n; i++){
            dp[i] = 1;
            count[i] = 1;
            for(int j = 0; j < n; j++){
                if(nums[j] < nums[i]){
                    if(dp[j] >= dp[i]){
                        dp[i] = dp[j]+1;
                        //重置计数
                        count[i] = count[j];
                    } else if(dp[i] == dp[j]+1){
                        count[i] += count[j];
                    }
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }
        return maxLength;
    }

    public int findNumberOfLIS(int[] nums){
        int res = 0;
        int n = nums.length;
        int maxLength = buildDp(nums);
        if(maxLength == 1){
            return n;
        }
        for(int i = 1; i < n; i++){
            if(dp[i] == maxLength){
                res += count[i];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        FindNumberOfLIS findNumberOfLIS = new FindNumberOfLIS();
        int[] nums = {0,2,1,4,3,6,5,8,7,10,9,12,11,14,13,16,15,18,17,20,19,22,21,24,23,26,25,28,27,30,29,32,31,34,33,36,35,38,37,40,39,42,41,44,43,46,45,48,47,50,49,52,51,54,53,56,55,58,57,60,59,61};
        System.out.println(findNumberOfLIS.findNumberOfLIS(nums));
    }
}
~~~

### 统计元音字母序列的数目

力扣1220

字符串中的每个字符都应当是小写元音字母（`a, e, i, o, u`）
每个元音`a`后面都只能跟着`e`
每个元音`e`后面只能跟着`a`或者是`i`
每个元音`i`后面 不能 再跟着另一个`i`
每个元音`o`后面只能跟着`i`或者是`u`
每个元音`u`后面只能跟着`a`

> 动态规划，`dp[i][j]`表示长度为`i`、以`j`结尾的元音字母序列的数目
>
> 其中`j=0—>a, j=1->e, j=2->i, j=3->o, j=4->u`

~~~java
class Solution {
    public int countVowelPermutation(int n) {
        int mod = 1000000007;
        long[] dp = new long[5];
        long[] next = new long[5];
        //初始化dp[1][i]
        for(int i = 0; i < 5; i++){
            dp[i] = 1;
        }
        //通过dp[cur][i]逐步构造dp[next][i]
        for(int i = 2; i <= n; i++){
            next[0] = (dp[1]+dp[2]+dp[4]) % mod;
            next[1] = (dp[0]+dp[2]) % mod;
            next[2] = (dp[1]+dp[3]) % mod;
            next[3] = dp[2];
            next[4] = (dp[2]+dp[3]) % mod;
            System.arraycopy(next, 0, dp, 0, 5);
        }
        //至此dp[n][i]构造完毕，直接将五种序列（根据末尾区分）数目相加即可
        long ans = 0;
        for(int i = 0; i < 5; i++){
            ans = (dp[i]+ans) % mod;
        }
        return (int)ans;
    }
}
~~~

数组总和Ⅳ（377）

矩阵区域不超过k的最大数值和（363）

最小操作次数使数组元素相等（453）

## KMP算法

Knuth-Morris-Pratt

### 已知next数组匹配字符串

~~~c
#include <iostream>
#include <string.h>
#include <algorithm> 
using namespace std;


int main(){
	
	char str[1000];
    char dist[1000];
	cin >> str >> dist;
    
    //cout << str << " " << dist << endl;
	int count = 0, m = strlen(str), n = strlen(dist);
	int pos[m];
	
	
    int next[n];
    for(int i = 0; i < n; i++){
    	cin >> next[i];
	}
	


	//cout << m << " " << n << endl;
	int i = 0, j = 0;
	while(i < m && j < n){
		if(j == -1 || tolower(str[i])==tolower(dist[j])){
			i++;
			j++;
		} else {
			j = next[j];
		}
		if(j == n){
			pos[count++] = i-j+1;
			i = i-1-next[j-1];
			j = 0;
		}		
	}
	
	
	cout << count << endl;
	for(i = 0; i < count; i++){
		cout << pos[i] << endl;
	}

	return 0;
}

~~~

实现strStr()（28）

重复叠加字符串匹配（686）

重复的子字符串（459）

最短回文串（214）

## 前缀和

> ProfixSum

### 区域和检索-数组不可变

力扣303

~~~java
//优化解法：前缀和
class NumArray {

    private int[] nums;

    public NumArray(int[] nums) {
        int n = nums.length;
        this.nums = new int[n+1];
        for(int i = 0; i < n; i++){
            //储存前 i 个的和在数组 this.nums 中，用 nums[j+1]-nums[i] 可以得到第i个数到第j个数的和
            this.nums[i+1] = this.nums[i] + nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
        int res = nums[right + 1] - nums[left]; 
        return res;
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
~~~

### 二维区域和检索-矩阵不可变

力扣304

~~~java
//采用303的优化解法：一维前缀和
class NumMatrix {

    private int[][] matrix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        this.matrix = new int[m][n+1];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                this.matrix[i][j+1] = this.matrix[i][j] + matrix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        int res = 0;
        for(int i = row1; i <= row2; i++){
            res += matrix[i][col2+1] - matrix[i][col1];
        }
        return res;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * int param_1 = obj.sumRegion(row1,col1,row2,col2);
 */
~~~

环绕字符串中唯一的子字符串（467）

区间子数组个数（795）

水果成篮（904）

K个不同的整数的子数组（992）

航班预订统计（1109）

连续数组（525）

## 排序算法

> 排序数组（912）

### 冒泡排序

> 最呆的解法，碰到大的就交换位置

~~~java
class Solution {
    public int[] sortArray(int[] nums) {
        //两层遍历整个数组
        for(int i = 0; i < nums.length; i++){
            //用布尔变量记录是否还需排序，若本轮未排序，则直接返回nums
            boolean flag = false;
            for(int j = i+1; j < nums.length; j++){
                //若更大，直接交换位置
                if(nums[i]>nums[j]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    flag = true;
                }
            }
            if(!flag){
                return nums;
            }
        }
        return nums;
    }
}
~~~

### 选择排序

> 从首位开始，选择当前位之后最小的元素与当前位交换，直到交换完倒数第二个元素，排序完成

~~~java
public class Solution {
	
	public int[] sortArray(int[] nums){
        selectSort(nums);
        return nums;
    }

    public void selectSort(int[] nums){
        int n = nums.length;
        for(int i = 0; i < n-1; i++){
            int minIndex = i;
            for(int j = i+1; j < n; j++){
                if(nums[j] < nums[minIndex]){
                    minIndex = j;
                }
            }
            swap(nums, i, minIndex);
        }
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

### 插入排序

> 顾名思义，插入排序即将当前元素插入到某个位置，该位置左边元素均小于当前元素，右边元素均大于当前元素
>
> 为了实现这一功能，必须“由小及大”，即先满足两个元素的插入，再三个、四个...进而实现整个数组的插入
>
> 从队首开始，和第二个元素比较，插入排序；第二个元素和第三个元素比较，将第三个元素插入到适当位置，排序；第三个与第四个......

~~~java
class Solution {
    public int[] sortArray(int[] nums) {
        int n = nums.length;
        for(int i = 1; i < n; i++){
            //记录当前元素
            int cur = nums[i], j;
            //从第j=i-1位元素开始，若目标元素大于当前元素，则将目标元素向右移一位，继续比较直到目标元素小于当前元素或到数组首位，此时将当前元素赋给j+1位，可保证(0, j+1)位元素都小于等于当前元素，(j+2, i+1)位元素都大于当前元素，实现元素nums[i]的“插入”
            //另外为了方便移位，要从大往小遍历，遇到大的将其向后移一位
            for(j = i-1; j >= 0; j--){
                if(nums[j]>cur){
                    nums[j+1] = nums[j];
                }else{
                    break;
                }
            }
            nums[j+1] = cur;
        }
        return nums;
    }
}
~~~

### 二分插入排序

> 在插入的基础上，在寻找插入点时，使用mid来代替从头至尾的遍历，寻找满足条件的左右边界，直到左边界超出有边界，此时左边界即为插入点

~~~java
class Solution {
    public int[] sortArray(int[] nums) {
        int n = nums.length;
        for(int i = 1; i < n; i++){
            //初始左边界和右边界，记录当前元素
            int left = 0, right = i-1, cur = nums[i];
            while(right>=left){
                //记录中间元素
                int mid = (right+left)/2;
                //当中间元素大于当前元素，将右边界记为中间-1
                if(nums[mid]>cur){
                    right = mid-1;;
                }else{ //当中间元素小于当前元素，将左边界记为中间+1
                    left = mid+1;
                }
            }
            for(int j = i; j > left; j--){
                nums[j] = nums[j-1];
            }
            nums[left] = cur;
        }
        return nums;
    }
}
~~~

### 快速排序

> 在一个数组中**随便**（可以取随机数，可以取中间元素，也可以直接取右边界）找一个元素作为标准，将小于等于该数的元素放在该元素左边，剩余的放在右边，再将该数左边（右边）所有元素作为一个数组重新进行这一过程，直到左边界大于等于右边界直接 return

~~~java
class Solution {
    public int[] sortArray(int[] nums){
        quickSort(nums, 0, nums.length-1);
        return nums;
    }


    public void quickSort(int[] nums, int left, int right){
        if(left>=right){
            return;
        }
        swap(nums, right, (left+right)/2);
        int pivot = nums[right];
        int position = left;
        for(int i = left; i < right; i++){
            if(nums[i] <= pivot){
                swap(nums, i, position);
                position++;
            }
        }
        swap(nums, position, right);
        quickSort(nums, left, position-1);
        quickSort(nums, position+1, right);
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

### 堆排序

> 满二叉树：除最后一层外的每层上的所有节点都有两个子节点（形状如三角形，叶子分布在同一层），这样自然会导致其深度为 k 时有 2^k-1 个结点
>
> 完全二叉树：叶子结点只能出现在最下层和次下层，且最下层的叶子结点集中在树的左部。
>
> - 满二叉树一定是完全二叉树，完全二叉树不一定是满二叉树
>
> 堆：按顺序储存的完全二叉树。当父节点的键值总是大于等于其子节点的键值，为大根（顶）堆，反之称为小根（顶）堆

~~~java
class Solution {
    public int[] sortArray(int[] nums){
        heapSort(nums);
        return nums;
    }


    public void heapSort(int[] nums){
        int n = nums.length-1; 
        //一定要从后往前构造
        //这样在树中为从下层向上层构造，逐步将大根上移，防止漏移
        for(int i = n/2; i >= 0; i--){
            heapAdjust(nums, i, n);
        }
        
        for(int i = n; i > 0; i--){
            swap(nums, 0, i);
            heapAdjust(nums, 0, i-1);
        }
    }

    public void heapAdjust(int nums[], int parent, int length){
        int child = parent*2+1;
        while(child <= length){
            if(child+1 <= length && nums[child] < nums[child+1]){
                child++;
            }
            if(nums[parent] > nums[child]){
                break;
            }
            swap(nums, parent, child);
            parent = child;
            child = parent*2+1;
        }
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

### 归并排序

> 利用数组 temp 作为中间转换，将 [ left, mid ] 和 [ mid+1, right ] 的数排序在 temp 里，再将 temp 中的值重新赋给 nums，完成一次排序
>
> Fork/Join 思想

~~~java
public class Solution {
	
	public int[] sortArray(int[] nums){
        temp = new int[nums.length];
        mergeSort(nums, 0, nums.length-1);
        return nums;
    }

    private int[] temp;

    public void mergeSort(int[] nums, int left, int right){
        if(left >= right){
            return;
        }
        int mid = (left+right)/2;
        mergeSort(nums, left, mid);
        mergeSort(nums, mid+1, right);
        int i = left, j = mid+1, count = 0;
        while(i<=mid && j<=right){
            if(nums[i]<nums[j]){
                temp[count++] = nums[i++];
            }else{
                temp[count++] = nums[j++];
            }
        }
        while(i<=mid){
            temp[count++] = nums[i++];
        }
        while(j<=right){
            temp[count++] = nums[j++];
        }
        for(int k = 0; k <= right-left; k++){
            nums[k+left] = temp[k];
        }
    }
}
~~~

## 贪婪算法

> 贪心算法

### 文本左右对齐

力扣68















