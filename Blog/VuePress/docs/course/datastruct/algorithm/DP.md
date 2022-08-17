# DP

dynamic programming

### 1、最大子序和（53）

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

### 2、解码方法（91）

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

### 3、最长回文子串（5）

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

### 4、回文子串（647）

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

### 5、最长递增子序列（300）

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

### 6、消除游戏（390）

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

### 7、猫和老鼠（913）

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

### 8、最长递增子序列的个数（673）

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

### 9、统计元音字母序列的数目（1220）

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

