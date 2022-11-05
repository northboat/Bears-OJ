# 绝缘Offer

> 谨以纪念字节一面见光死

## 一、剑指Offer

> 剑指Offer二周目，康康是否有自己的思路，是否有进步

### 1、树的子结构

递归，试错三次，耗时10分钟出头

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
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if(B == null && A != null){
            return false;
        }
        return check(A, B, true);
    }

    public boolean check(TreeNode A, TreeNode B, boolean isRoot){
        if(B == null){
            return true;
        } else if(A == null){
            return false;
        }
        if(A.val != B.val){
            if(!isRoot){
                return false;
            }
            return check(A.left, B, true) || check(A.right, B, true);
        } else {
            return (check(A.left, B.left, false) && check(A.right, B.right, false))
                    || (check(A.left, B, true) || check(A.right, B, true));
        }
    }
}
~~~

### 2、二叉树的镜像

仍旧递归，惨遭两分钟秒杀

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
    public TreeNode mirrorTree(TreeNode root) {
        if(root == null){
            return null;
        }
        TreeNode cur = new TreeNode(root.val);
        cur.left = mirrorTree(root.right);
        cur.right = mirrorTree(root.left);
        return cur;
    }
}
~~~

### 3、对称的二叉树

舒服了，连斩三道递归，用时六分钟

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
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return check(root.left, root.right);
    }

    public boolean check(TreeNode a, TreeNode b){
        if((a==null && b!=null) || (a!=null && b==null)){
            return false;
        }
        if(a==null && b==null){
            return true;
        }
        if(a.val != b.val){
            return false;
        }
        return check(a.left, b.right) && check(a.right, b.left);
    }
}
~~~

### 4、斐波那契数列

最简单的动态规划，秒杀

~~~java
class Solution {
    public int fib(int n) {
        if(n < 2){
            return n;
        }
        int mod = 1000000007;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            dp[i] = (dp[i-1]+dp[i-2])%mod;
        }
        return dp[n];
    }
}
~~~

### 5、青蛙跳台阶问题

动态转移方程与斐波那契数列一模一样

~~~java
class Solution {
    public int numWays(int n) {
        if(n < 2){
            return 1;
        }
        int mod = 1000000007;
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            dp[i] = (dp[i-1]+dp[i-2]) % mod;
        }
        return dp[n];
    }
}
~~~

### 6、连续子数组的最大和

简单的动态规划，记录每个位置所能构成的和最大的子串的值

~~~java
class Solution {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        for(int i = 1; i < n; i++){
            if(dp[i-1] <= 0){
                dp[i] = nums[i];
                continue;
            }
            dp[i] = dp[i-1]+nums[i];
        }
        Arrays.sort(dp);
        return dp[n-1];
    }
}
~~~



## 二、CodeTop

> CodeTop二周目