---
title: LeetCode in Grade Two
date: 2022-1-3
tags:
  - Algorithm
---

## LeetCode

### 栈与递归

#### 棒球比赛（682）

> 先进后出原则
>
> 用 else 去处理数字，避免对字符串数字的判断

~~~java
class Solution {
    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        for(String str: ops){
            if(str.equals("C")){
                stack.pop();
            }else if(str.equals("D")){
                stack.push(stack.peek()*2);
            }else if(str.equals("+")){
                int temp = stack.pop();
                int dist = temp + stack.peek();
                stack.push(temp);
                stack.push(dist);
            }else{
                stack.push(Integer.valueOf(str));
            }
        }
        int res = 0;
        while(!stack.empty()){
            res += stack.pop();
        }
        return res;
    }
}
~~~

#### 消除游戏（390）

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

#### 简化路径（71）

> 用split分割`"//"`将分出三个空字符串，用`length()==0`略过空字符串去掉路径中的`"//"` 
>
> 用queue（或stack）储存路径，碰到`../`退一个路径，即丢掉最后入队的元素，否则入队，再按顺序出队，用`/`隔开每个路径名称构成简化路径

~~~java
package com.solution;

import java.util.ArrayDeque;
import java.util.Deque;

public class SimplifyPath {
    public String simplifyPath(String path){
        int n = path.length();
        String[] p = path.split("/");
        StringBuffer res = new StringBuffer();
        Deque<String> queue = new ArrayDeque<>();
        for(String str: p){
            if(str.equals("..")){
                if(!queue.isEmpty()){
                    queue.pollLast();
                }
            } else if(str.length()>0 && !str.equals(".")) {
                queue.offer(str);
            }
        }
        if(queue.isEmpty()){
            res.append('/');
        } else {
            while(!queue.isEmpty()){
                res.append('/');
                res.append(queue.poll());
            }
        }
        return res.toString();
    }
}
~~~

#### 括号的最大嵌套深度（1614）

> 有效的括号

~~~java
package com.solution;

import java.util.Stack;

public class MaxDepth {
    public int maxDepth(String s){
        Stack<Character> stack = new Stack<>();
        int res = 0;
        for(char c: s.toCharArray()){
            if(c == '('){
                stack.push(c);
            } else if(c == ')'){
                res = Math.max(stack.size(), res);
                stack.pop();
            }
        }
        if(!stack.empty()){
            return 0;
        }
        return res;
    }
}

~~~

#### 回文数（9）

> 回文串

~~~java
package com.solution;

import java.util.Stack;

public class IsPalindrome {
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        int y = x;
        Stack<Integer> s = new Stack<>();
        while(x != 0){
            s.push(x % 10);
            x /= 10;
        }
        while(!s.isEmpty()){
            if(y%10 != s.pop()){
                return false;
            }
            y /= 10;
        }
        return true;
    }
}
~~~

### 链表

#### 两数相加（2）

~~~java
package com.solution;

import com.pojo.ListNode;

import java.util.ArrayList;
import java.util.List;

public class AddTwoNumbers {
    private boolean flag;
    public int carry(int val){
        if(val >= 10){
            flag = true;
            return val-10;
        }
        flag = false;
        return val;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        flag = false;
        ListNode head = new ListNode();
        ListNode p = head;
        while(l1!=null && l2!=null){
            int val = flag ? l1.val+l2.val+1 : l1.val+l2.val;
            val = carry(val);
            p.next = new ListNode(val);
            p = p.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        while(l1!=null){
            int val = flag ? l1.val+1 : l1.val;
            val = carry(val);
            p.next = new ListNode(val);
            p = p.next;
            l1 = l1.next;
        }

        while(l2!=null){
            int val = flag ? l2.val+1 : l2.val;
            val = carry(val);
            p.next = new ListNode(val);
            p = p.next;
            l2 = l2.next;
        }

        if(flag){
            p.next = new ListNode(1);
        }

        return head.next;
    }

    public static void hahaha(int val){
        val = val-10;
    }
    public static void main(String[] args) {
        int i = 20;
        AddTwoNumbers.hahaha(i);
        System.out.println(i);
    }
}
~~~

#### 链表随机节点（382）

产生随机数：`Math.random()`产生一个`(0, 1]`的随机`double`数

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {

    private List<Integer> list;

    public Solution(ListNode head) {
        list = new ArrayList<>();
        ListNode p = head;
        while(p != null){
            list.add(p.val);
            p = p.next;
        }
    }
    
    //Math.random产生数范围：(0,1]
    public int getRandom() {
        return list.get((int)(Math.random()*list.size()));
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */
~~~

### 树

#### 奇偶树（1609）

层次遍历BFS

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

#### 实现Trie(前缀树)（208）

> 也叫字典树，每个节点有26个子节点（数组形式），如字符串 apple，在字典树中存在即指 ——> 根节点 this.children[0] 不为空，this.children[0].children[16] 不为空......如此类推，这样可以依据相同的前缀向后无限延伸，查找效率贼高

~~~java
class Trie {

    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }
    
    public void insert(String word) {
        Trie p = this;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(p.children[index] == null){
                p.children[index] = new Trie();
            }
            p = p.children[index];
        }
        p.isEnd = true;
    }

    private Trie searchPrefix(String prefix){
        Trie p = this;
        for(int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            int index = c-'a';
            if(p.children[index] == null){
                return null;
            }
            p = p.children[index];
        }
        return p;
    }
    
    public boolean search(String word) {
        Trie n = searchPrefix(word);
        if(n == null || n.isEnd == false){
            return false;
        }
        return true;
    }
    
    public boolean startsWith(String prefix) {
        if(searchPrefix(prefix) == null){
            return false;
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
~~~

#### 连接词（472）

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

### 顺序表

#### Bigram分词（1078）

注意List转数组的泛型方法，比较固定

~~~java
class Solution {
    public String[] findOcurrences(String text, String first, String second) {
        List<String> res = new ArrayList<>();
        String[] s = text.split(" ");
        int n = s.length;
        for(int i = 0; i < n; i++){
            if(s[i].equals(first)){
                if(i+2 < n && s[i+1].equals(second)){
                    res.add(s[i+2]);
                }
            }
        }
        return res.toArray(new String[res.size()]);
    }
}
~~~

#### 适龄的朋友（825）

贪婪法：超时

~~~java
public class NumFriendRequests {
    public int numFriendRequests(int[] ages) {
        int n = ages.length;
        int res = 0;
        for(int i = 0; i < n; i++){
            int cur = ages[i];
            for(int j = 0; j < n; j++){
                int comp = ages[j];
                if(i == j || cur < comp || cur*0.5+7 >= comp){
                    continue;
                }
                res++;
            }
        }
        return res;
    }
}
~~~

排序+快慢指针

> 要寻找在区间 (age*0.5+7, age] 的数的个数，先对数据进行排序，再确定该区间的 left、right 位置，right-left 即为区间内数的个数
>
> 确定区间：左边界简单考虑即可，当 age[left] <= cur，将left向右移动；右边界用 right+1 作为预定边界可以省去重复判断 ages[cur] <= ages[cur]
>
> 当 age <= 14时，age >= age*0.5+7，原区间无整数解 

~~~java
import java.util.Arrays;
//16 16
public class NumFriendRequests {
    public int numFriendRequests(int[] ages) {
        Arrays.sort(ages);
        int n = ages.length;
        int res = 0;
        int left = 0, right = 0;
        for(int cur: ages){
            if(cur <= 14){
                continue;
            }
            while(ages[left] <= cur*0.5+7){
                left++;
            }
            while(right+1 < n && ages[right+1] <= cur){
                right++;
            }
            res += right-left;
        }
        return res;
    }

}
~~~

#### 一维数组转二维数组（2022）

当不能转变时，返回空的二维数组，指`new int[0][]`

~~~java
class Solution {
    public int[][] construct2DArray(int[] original, int m, int n) {
        if(m*n != original.length){
            return new int[0][];
        }
        int[][] res = new int[m][n];
        int count = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                res[i][j] = original[count++];
            }
        }
        return res;
    }
}
~~~

#### 替换所有的问号（1576）

> 注意比较当前位元素时，前要和已经整理好的字符比较，后要和未整理的字符比较（因为是从前往后处理的）
>
> 采用最原始的遍历进行比较，题目规定只有小写字母和`?`

~~~java
class Solution {
    public String modifyString(String s) {
        StringBuffer res = new StringBuffer();
        int n = s.length();
        for(int i = 0; i < n; i++){
            char c = s.charAt(i);
            if(c == '?'){
                char pre = i > 0 ? res.charAt(i-1) : ' ';
                char next = i < n-1 ? s.charAt(i+1) : ' ';
                for(int j = 0; j < 26; j++){ 
                    char cur = (char)(97+j);
                    if(cur != pre && cur != next){
                        res.append(cur);
                        break;
                    }
                }
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }
}
~~~

#### 按键持续时间最长的键（1629）

> 采用上一题`最长递增子序列的个数`的寻找并统计最大元素位置的方法，大则归零并添加，等则添加，小则略过，注意初始化
>
> ~~~java
> for(int i = 1; i < n; i++){
>     if(releaseTimes[i]-releaseTimes[i-1] > maxTime){
>         maxTime = releaseTimes[i]-releaseTimes[i-1];
>         list = new ArrayList<>();
>         list.add(keysPressed.charAt(i));
>     } else if(releaseTimes[i]-releaseTimes[i-1] == maxTime){
>         list.add(keysPressed.charAt(i));
>     }
> }
> ~~~

~~~java
class Solution {
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int n = releaseTimes.length;
        int maxTime = releaseTimes[0];
        List<Character> list = new ArrayList<>();
        list.add(keysPressed.charAt(0));
        for(int i = 1; i < n; i++){
            if(releaseTimes[i]-releaseTimes[i-1] > maxTime){
                maxTime = releaseTimes[i]-releaseTimes[i-1];
                list = new ArrayList<>();
                list.add(keysPressed.charAt(i));
            } else if(releaseTimes[i]-releaseTimes[i-1] == maxTime){
                list.add(keysPressed.charAt(i));
            }
        }
        char res = list.get(0);
        int m = list.size();
        for(int i = 1; i < m; i++){
            char cur = list.get(i);
            if(cur-res > 0){
                res = cur;
            }
        }
        return res;
    }
}
~~~

#### 累加数（306）

> 枚举、dfs向后搜索

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

#### 寻找两个正序数组的中位数

> 处理小数位数（后五位）：
>
> ~~~java
> DecimalFormat format = new DecimalFormat("#.00000");
> format.format(2.5)
> ~~~

~~~java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i = 0, j = 0, n = nums1.length, m = nums2.length;
        List<Integer> list = new ArrayList<>();
        while(i < n && j < m){
            if(nums1[i] <= nums2[j]){ list.add(nums1[i++]); }
            else{ list.add(nums2[j++]); }
        }
        while(i < n){ list.add(nums1[i++]); }
        while(j < m){ list.add(nums2[j++]); }
        int length = list.size();
        DecimalFormat format = new DecimalFormat("#.00000");
        if(length % 2 == 0){ 
            return (double)(list.get(length/2-1)+list.get(length/2)) / 2;
        }
        return (double)list.get(length/2);
    }
}
~~~

#### 递增的三元子序列（334）

> 动态规划，状态转移方程：
>
> `left[i] = Math.min(nums[i], left[i-1])`
>
> `right[i] = Math.max(nums[i], right[i+1])`

将时间复杂度控制在`O(n)`才不会超时，傻瓜式遍历为`O(n^3)`

~~~java
class Solution {
    //构造dp数组
    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if(n < 3){
            return false;
        }
        //记录小于等于当前下标的元素中的最小值
        int[] left = new int[n];
        left[0] = nums[0];
        for(int i = 1; i < n; i++){
            left[i] = Math.min(nums[i], left[i-1]);
        }
        //记录大于等于当前下标的元素中的最大值
        int[] right = new int[n];
        right[n-1] = nums[n-1];
        for(int i = n-2; i >= 0; i--){
            right[i] = Math.max(nums[i], right[i+1]);
        }

        for(int i = 1; i < n-1; i++){
            if(nums[i] > left[i-1] && nums[i] < right[i+1]){
                return true;
            }
        }
        return false;
    }
}
~~~

#### 搜索旋转排序数组（33）

傻瓜解法

~~~java
class Solution {
    public int search(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == target){
                return i;
            }
        }
        return -1;
    }
}
~~~

二分法，因为是两段有序的数组拼接而成，二分之后一定有一边是有序的，我们判断`target`是否在有序的部分中（这样好设置判断条件）。若在，则缩小范围继续二分，若不在，继续对无序的部分二分

~~~java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length-1, n = nums.length;
        while(left <= right){
            int mid = (left+right) / 2;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[0] > nums[mid]){
                if(target <= nums[n-1] && target > nums[mid]){
                    left = mid+1;
                } else {
                    right = mid-1;
                }
            } else {
                if(target >= nums[0] && target < nums[mid]){
                    right = mid-1;
                } else {
                    left = mid+1;
                }
            }
        }
        return -1;
    }
}
~~~

### 统计

#### 统计特殊四元组（1995）

- `nums[a] + nums[b] + nums[c] == nums[d]` ，且
- `a < b < c < d`

用`Map`储存nums[d]及其出现次数，但忽略了`a < b < c < d`的要求，解答错误

~~~java
class Solution {
    public int countQuadruplets(int[] nums) {
        int res = 0;
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0)+1);
        }
        for(int i = 0; i < n-2; i++){
            for(int j = i+1; j < n-1; j++){
                for(int k = j+1; k < n; k++){
                    res += map.getOrDefault(nums[i]+nums[j]+nums[k], 0);
                }
            }
        }
        return res;
    }
}
~~~

考虑`a < b < c < d`，固定`d`和`c`进行遍历，即当前最后一个和倒数第二个，O(n^3)

~~~java
package com.solution;

import java.util.HashMap;
import java.util.Map;

public class CountQuadruplets {
    public int countQuadruplets(int[] nums) {
        int res = 0;
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = n-2; i >= 2; i--){
            map.put(nums[i+1], map.getOrDefault(nums[i+1], 0)+1);
            for(int j = 0; j < i-1; j++){
                for(int k = j+1; k < i; k++){
                    res += map.getOrDefault(nums[i]+nums[j]+nums[k], 0);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        CountQuadruplets cq = new CountQuadruplets();
        int[] nums = {28, 8, 49, 85, 37, 90, 20, 8};
        System.out.println(cq.countQuadruplets(nums));
    }

}
~~~

哈希表储存优化，时间复杂度降低至O(n^2)

> `nums[a]+nums[b]+nums[c]=nums[d] => nums[a]+nums[b]=nums[d]-nums[c]`
>
> 在枚举前两个下标`a, b`时，我们可以先逆序枚举`b`。在 `b` 减小的过程中，`c`的取值范围是逐渐增大的：即从`b+1`减小到`b` 时，`c` 的取值范围中多了 `b+1` 这一项，而其余的项不变。因此我们只需要将所有满足 `c=b+1` 且 `d > c`的`c, d`对应的`nums[d] - nums[c]`加入哈希表即可。
>
> 在这之后，我们就可以枚举`a`并使用哈希表计算答案了
>

~~~java
public int countQuadruplets(int[] nums){
    int res = 0;
    int n = nums.length;
    Map<Integer, Integer> map = new HashMap<>();
    for(int b = n-3; b >= 1; b--){
        for(int d = b+2; d < n; d++){
            map.put(nums[d]-nums[b+1], map.getOrDefault(nums[d]-nums[b+1], 0));
        }
        for(int a = 0; a < b; a++){
            res += map.getOrDefault(nums[a]+nums[b], 0);
        }
    }
    return res;
}
~~~

#### 一手顺子（846）

朴素解法，通过`List、boolean[]、count`记录下标不断`remove`元素和判断是否能连成顺子

~~~java
package com.solution;

import java.util.*;

public class IsNStraightHand {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length;
        if(n%groupSize != 0){
            return false;
        }
        List<Integer> list = new ArrayList<>();
        for(int num: hand){
            list.add(num);
        }
        while(!list.isEmpty()){
            list.sort((a, b) -> a - b);
            int start = list.get(0);

            boolean[] isRemoved = new boolean[groupSize-1];
            List<Integer> removeIndex = new ArrayList<>();
            removeIndex.add(0);

            for(int i = 0; i < list.size(); i++){
                for(int j = 0; j < groupSize-1; j++){
                    if(list.get(i) == start+j+1 && !isRemoved[j]){
                        removeIndex.add(i);
                        isRemoved[j] = true;
                        break;
                    }
                }
            }

            for(int i = 0; i < groupSize-1; i++){
                if(!isRemoved[i]) {
                    return false;
                }
                isRemoved[i] = false;
            }

            //remove后下标动态浮动，通过sort+count精确定位到之前的下标
            int count = 0;
            for(int index: removeIndex){
                list.remove(index-count++);
            }
        }
        return true;
    }
}
~~~

哈希表贪心统计

~~~java
class Solution {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length;
        if (n % groupSize != 0) {
            return false;
        }
        Arrays.sort(hand);
        Map<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int x : hand) {
            cnt.put(x, cnt.getOrDefault(x, 0) + 1);
        }
        for (int x : hand) {
            if (!cnt.containsKey(x)) {
                continue;
            }
            for (int j = 0; j < groupSize; j++) {
                int num = x + j;
                if (!cnt.containsKey(num)) {
                    return false;
                }
                cnt.put(num, cnt.get(num) - 1);
                if (cnt.get(num) == 0) {
                    cnt.remove(num);
                }
            }
        }
        return true;
    }
}
~~~

#### 完美数（507）

简单枚举，二分

~~~java
class Solution {
    public boolean checkPerfectNumber(int num) {
        int target = 0;
        for(int i = 1; i <= num/2; i++){
            if(num % i == 0){
                target += i;
            }
        }
        return target==num;
    }
}
~~~

#### 最长递增子序列的个数（673）

动态规划+DFS：超时

> 先构造dp数组，记录每个下标位置能构成的最长的子序列的长度，并返回最长长度
>
> 对`dp[i]==maxLength`的下标进行`dfs`，逐层寻找`dp[j]==dp[i]-1 && nums[j]<nums[i]`的下标直到`dp[j]==1 && nums[j]<nums[i]`时说明找到一条能构造最长递增子序列的道路，令`res+1`
>
> 这个dfs太耗时了，特别当只有`dp[0]==1`且数据很多时，疯狂套

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

动态规划

> 在`dp`数组记录下标元素能构造的最长长度的基础上，构造`count`数组用于记录当前下标元素构成最长递增子序列的道路总数，即`dp[j]==dp[i]-1 && nums[j]<nums[i]`的个数

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

#### 平方数之和（633）

双指针

> `double d = Math.sqrt(int c)`取根
>
> `b`用`long`储存避免溢出`Integer.maxValue`

~~~java
class Solution {
    public boolean judgeSquareSum(int c) {
        int a = 0;
        long b = (long)Math.sqrt(c);
        while(a <= b){
            if(a*a+b*b < c){
                a++;
            } else if(a*a+b*b > c){
                b--;
            } else{
                return true;
            }
        }
        return false;
    }
}
~~~

遍历`a`，通过`a、c`求`b`，符合条件返回`true`，否则`continue`

> 同样用long避免数据溢出

~~~java
class Solution {
    public boolean judgeSquareSum(int c) {
        for(long a = 0; a*a <= c; a++){
            double b = Math.sqrt(c-a*a);
            if(b == (int)b){
                return true;
            }
        }
        return false;
    }
}
~~~

#### 统计元音字母序列的数目（1220）

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

#### 存在重复元素Ⅱ（219）

> 判断在下标`(i, i+k]`中是否存在元素`nums[j] == nums[i]`

~~~java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        int n = nums.length;
        for(int i = 0; i < n; i++){
            int cur = nums[i];
            int m = Math.min(n, i+k+1);
            for(int j = i+1; j < m; j++){
                if(cur == nums[j]){
                    return true;
                }
            }
        }
        return false;
    }  
}
~~~



### 图

> 图与递归，深度优先搜索

#### 猫和老鼠（913）

> 数据结构：无向图
>
> 算法：动态规划、深度优先搜索、递归

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

### 数与位

#### 最大回文数乘积（479）

> 用函数`palindrome`从大到小遍历所有符合规范的回文数，从大到小遍历所有`n`位数，若能整除，直接返回，此为能构造的最大的回文数

~~~java
class Solution {
    public int largestPalindrome(int n) {
        int mod = 1337;
        int maxNum = (int)Math.pow(10, n);
        int minNum = maxNum/10;
        long largestPow = (long)Math.pow(maxNum-1, 2);
        for(int i = maxNum; i > 0; i--){
            long num = palindrome(i);
            if(num > largestPow){ continue; };
            for(long j = maxNum-1; j >= minNum && j*j > num; j--){
                if(num % j == 0){
                    return (int)(num % mod);
                }
            }
        }
        return 9;
    }

    public long palindrome(int n){
        long temp = n;
        long mirror = 0;
        while(n > 0){
            mirror = mirror*10 + n%10;
            temp *= 10;
            n /= 10;
        }
        return temp+mirror;
    } 
}
~~~

#### 计算力扣银行的钱（1716）

> 等差数列

~~~java
class Solution {
    public int totalMoney(int n) {
        int res = 0;
        int count = n/7;
        int more = n%7;
        res += count*28;
        //公差为7的等差数列
        res += ((count-1)*7)*count/2;
        for(int i = 1; i <= more; i++){
            res += i+count;
        }
        return res;
    }
}
~~~

#### 寻找最近的回文数

暴力解法，超时

~~~java
package com.solution;

import java.util.Stack;

public class NearestPalindromic {
    public String nearestPalindromic(String n){
        int m = Integer.parseInt(n);
        long min = m-1, max = m+1;
        while(!isPalindromic(min)){ min--; }
        while(!isPalindromic(max)){ max++; }
        return m-min<max-m ? min+"":max+"";
    }

    public boolean isPalindromic(long n){
        Stack<Integer> s = new Stack<>();
        long temp = n;
        while(temp > 0){
            s.push((int)temp%10);
            temp /= 10;
        }
        while(!s.isEmpty()){
            int c = s.pop();
            if(c != n%10){ return false; };
            n /= 10;
        }
        return true;
    }
}
~~~

数学方法

> 一般的最近回文数只有三种情况
>
> - `left`的`mirror`
> - `left+1`的`mirror`
> - `left-1`的`mirror`
>
> `mirror`指镜像构成的回文数，如（有奇偶之分）
>
> - 1234 ——> 12 ——> 1221
> - 12345 ——> 123 ——> 12321
>
> 有很多特殊情况需要处理
>
> 1. 个位数
> 2. `<16`的十位数
> 3. `left == 10~`，
> 4. `left == 99~`
>
> 很烦
>
> `flag`用于区分奇偶
>
> 规定当差距相同时，取小值
>
> 数转字符串`num+""`即可

~~~java
package com.solution;

import java.util.ArrayList;
import java.util.List;

public class NearestPalindromic {
    public String nearestPalindromic(String n){
        if(n.length() == 1){ return Integer.parseInt(n)-1+""; }
        if(n.equals("100")){ return "99"; }
        long s = Long.parseLong(n);
        if(s <= 11){ return "9"; }
        if(s < 16){ return "11"; }
        long res = Long.MAX_VALUE;
        List<String> nums = getMirrors(n);
        for(String num: nums){
            //System.out.println(num);
            long l = Long.parseLong(num);
            long gap = Math.abs(l-s);
            long g = Math.abs(res-s);
            if(gap == 0){ continue; }
            if(gap < g){
                res = l;
            } else if(gap == g && l < res){
                res = l;
            }
        }
        return res+"";
    }

    private boolean flag;

    public List<String> getMirrors(String n){
        flag = false;
        List<String> list = new ArrayList<>();
        String s;
        int length = n.length();
        if(length % 2 == 0){ s = n.substring(0, length/2); }
        else { s = n.substring(0, length/2+1); flag = true; }
        long l = Long.parseLong(s);
        //System.out.println(l);
        String s1 = (l+1)+"";
        String s2 = (l-1)+"";
        if(s1.length() != s.length() || s2.length() != s.length()){
            long res = (long)Math.pow(10, length);
            if(s2.length() != s.length()){ res /= 10; }
            list.add(res+1+"");
            list.add(res-1+"");
            return list;
        }
        list.add(mirror(s));
        list.add(mirror((Long.parseLong(s)+1)+""));
        list.add(mirror((Long.parseLong(s)-1)+""));
        return list;
    }

    public String mirror(String s){
        StringBuilder mirror = new StringBuilder();
        mirror.append(s);
        int n = s.length();
        int i = n-1;
        if(flag){ i--; };
        for(; i >= 0; i--){
            mirror.append(s.charAt(i));
        }
        return mirror.toString();
    }

    public static void main(String[] args) {
        NearestPalindromic n = new NearestPalindromic();
        System.out.println(n.nearestPalindromic("12"));
    }
}
~~~

#### 2的幂（231）

蠢逼解法

> 注意用long类型

~~~java
class Solution {
    public boolean isPowerOfTwo(int n) {
        long i = 2;
        while(i < n){
            i *= 4;
        }
        if(i == n || i/2 == n){
            return true;
        }
        return false;
    }
}
~~~

取巧解法

题目中交代了`n<=2^31-1`，即最大的符合条件的数为`2^30`，二进制计算得到`MAX`,所有小于MAX的2的倍数都能将其整除

移位运算：

> <<左移运算，>>右移运算，还有不带符号的位移运算 >>>.
>
> 左移的运算规则：按二进制形式把所有的数字向左移动对应的位数，高位移出（舍弃），低位的空位补零。
>
> 计算过程已1<<30为例,首先把1转为二进制数字 0000 0000 0000 0000 0000 0000 0000 0001
>
> 然后将上面的二进制数字向左移动30位后面补0得到 0010 0000 0000 0000 0000 0000 0000 0000
>
> 最后将得到的二进制数字转回对应类型的十进制

~~~java
class Solution {
    static final int BIG = 1 << 30;

    public boolean isPowerOfTwo(int n) {
        return n > 0 && BIG % n == 0;
    }
}
~~~

正儿八经解法

假设n为2的倍数，n的二进制只有最高位为1，其余位皆为0，而n-1的二进制比n低一位，且所有位皆为1，对二者进行与运算`&`，必得0

~~~java
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n>0 && (n&(n-1))==0;
    }
}
~~~

#### 4的幂（342）

蠢逼解法

~~~java
class Solution {
    public boolean isPowerOfFour(int n) {
        long i = 4;
        while(i < n){
            i *= 4;
        }
        if(i == n || i/4 == n){
            return true;
        }
        return false;
    }
}
~~~

位解法

先判断是否是2的倍数，然后由于4的倍数n的二进制的偶数位皆为0，所以构造一个偶数位皆为1的二进制数mask，让n与mask进行与运算，必得0

> 定义二进制数：`int a = 0b~`
>
> 定义十六进制数：`int b = 0x~`

~~~java
class Solution {
    private int mask = 0b101010101010101010101010101010;
    
    public boolean isPowerOfFour(int n) {
        return n>0 && (n&(n-1))==0 && (n&mask)==0;
    }
}
~~~

#### 3的幂（326）

3的幂的二进制并没有什么特点，所以只能采用蠢逼解法和取巧解法进行解题，以下是取巧解法

~~~java
class Solution {

    public boolean isPowerOfThree(int n) {
        int MAX = (int)Math.pow(3, 19);
        return n>0 && MAX%n==0;
    }
}
~~~

#### 七进制数（504）

> 实现`Integer.toString(num, 7)`

~~~java
class Solution {
    public String convertToBase7(int num) {
        boolean flag = false;
        if(num < 0){
            flag = true;
            num *= -1;
        }
        int res = num%7, count = 1;
        num /= 7;
        while(num > 0){
            res += num%7*Math.pow(10, count);
            num /= 7;
            count++;
        }
        if(flag){
            return "-"+res;
        }
        return res+"";
    }
}
~~~

#### 丑数

丑数n满足：`n = 2^a + 3^b + 5^c`，a、b、c为正整数

在这种情况下，判断一个数是否为丑数，先除以谁是无所谓的

~~~java
class Solution {
    public boolean isUgly(int n) {
        if(n == 0){ return false; }
        int[] factors = {2, 3, 5};
        for(int factor: factors){
            while(n % factor == 0){
                n /= factor;
            }
        }
        return n==1;
    }
}
~~~

#### 颠倒二进制位（190）

##### 依次颠倒赋值

将最低位依次赋给高位

- `(n&1)`取到n的最低位（令除第一位的其他位皆为0）`(n&1)<< (31-i)`是将当前n的最低位移到当前要赋值的位
- `n >>>= 1`是将n向右移一位，即得到新的最低位，这一步类似于通过%10取个位之后除以10的操作

注意：

> 数字的二进制表现形式为 “有符号的二进制补码”。
> 原码：数字的二进制表示法，最高位为符号位， “ 0 ” 为正，“ 1 ” 为负。
> 反码：正数的反码与原码相同，负数的反码对原码逐位取反，符号位除外。
> 补码：正数的补码与原码相同，负数的补码在其反码末位加 1。
> 负数的二进制算法（以 -6 为例）：
> 1）将 -6 的绝对值转化为二进制，即：00000000 00000000 00000000 00000110
> 2）求该二进制数的反码，即：11111111 11111111 11111111 11111001
> 3）对以上求得的二进制数加 1，即：11111111 11111111 11111111 11111010

- 在java中，所有整数都是有符号的，负数采用反码补码的形式表示，而在c、go、js中有无符号整数，无需考虑在移位时出现负数而导致原数大小不一致的情况，所以在判断退出条件时是`n!=0`而不是`n>0`
- 在向右移位时，也应使用`>>>`移位符无视符号用0填充高位
- 注意位运算的优先级，移位运算最高，并、或等次之；位运算的优先级高于赋值符

~~~java
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res = 0;
        for(int i = 0; i < 32 && n != 0; i++){
            res = res | (n&1) << (31-i);
            n = n>>>1;   //n >>>= 1
        }
        return res;
    }
}
~~~

##### 位分治

分治原理如下

- 原数据为:12345678
- 第一轮 奇偶位交换 21436587
- 第二轮 每两位交换 43218765（奇偶组交换位置）
- 第三轮 每四位交换 87654321

与`M[i]`进行与（&）操作是为了令奇/偶数位全为0

~~~java
public class Solution {
    private final int M1 = 0b10101010101010101010101010101010;
    private final int M2 = 0b11001100110011001100110011001100;
    private final int M3 = 0b11110000111100001111000011110000;
    private final int M4 = 0b11111111000000001111111100000000;

    public int reverseBits(int n) {
        n = n << 1 & M1 | (n & M1) >>> 1;
        n = n << 2 & M2 | (n & M2) >>> 2;
        n = n << 4 & M3 | (n & M3) >>> 4;
        n = n << 8 & M4 | (n & M4) >>> 8;
        return n >>> 16 | n << 16;
    }
}
~~~

##### jdk

底层仍使用的位分治

~~~java
public class Solution {
    public int reverseBits(int n) {
        return Integer.reverse(n);
    }
}
~~~

#### UTF-8编码验证（393）

> 字符长度不超过4个字节：`j <= 4`
>
> 单字节必须以0开头，即不存在以`10`开头的字符
>
> 右移注意移动7位将第八位放在第一位

~~~java
class Solution {
    public boolean validUtf8(int[] data) {
        int n = data.length;
        for(int i = 0; i < n; i++){
            int num = data[i], j;
            if(((num>>>7)&1) == 1 && ((num>>>6)&1) == 0) { return false; }
            for(j = 0; j < 8; j++){     	
                int cur = (num>>>(7-j)) & 1;
                //System.out.println(cur);
                if(cur == 0){  break; }
            }
            if(j > 4){ return false; }
            for(int k = 0; k < j-1; k++){
                if(++i >= n){ return false; }
                int cur = data[i];
                //System.out.println(cur);
                if(((cur>>>(7))&1) != 1 || ((cur>>>(6))&1) != 0){ return false; }
            }
        }
        return true;
    }
}
~~~

#### 灯泡开关（319）

模拟过程，超时

~~~java
class Solution {
    public int bulbSwitch(int n){
        boolean[] conditions = new boolean[n];
        for(int i = 0; i < n; i++){
            conditions[i] = true;
        }
        for(int i = 2; i <= n; i++){
            if(i > n/2){
                conditions[i-1] = conditions[i-1]?false:true;
                continue;
            }
            for(int j = 1; j < n; j++){
                if((j+1)%i == 0){
                    conditions[j] = conditions[j]?false:true;
                }
            }
        }
        int res = 0;
        for(boolean con: conditions){
            if(con){
                res++;
            }
        }
        return res;
    }
}
~~~

数论题

~~~java
class Solution {
    public int bulbSwitch(int n) {
        return (int)Math.sqrt(n);
    }
}
~~~

#### 位1的个数（191）

~~~java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0;
        for(int i = 0; i < 32; i++){
            if((n&1) == 1){
                count++;
            }
            n >>>= 1;
        }   
        return count;
    }
}
~~~

#### 数字的补数（476）

> 异或运算，当同为1/0时为假，当前位不同时为真

~~~java
class Solution {
    public int findComplement(int num) {
        int pos = 0;
        int temp = num;
        for(int i = 0; i < 32; i++){
            if((temp&1) == 1){
                pos = i;
            }
            temp >>>= 1;
        }
        temp = num;
        int res = 1;
        for(int i = 0; i < pos; i++){
            res <<= 1;
            res += 1;
        }
        return res^num;
    }
}
~~~

#### 汉明距离（461）

~~~java
class Solution {
    public int hammingDistance(int x, int y) {
        int count = 0;
        for(int i = 0; i < 32; i++){
            int a = x & 1, b = y & 1;
            if(a != b){ count++; }
            x >>>= 1; y >>>= 1;
        }
        return count;
    }
}
~~~

#### 汉明距离总和（477）

暴力枚举，超时

~~~java
class Solution {
    public int totalHammingDistance(int[] nums) {
        int n = nums.length;
        int total = 0;
        for(int i = 0; i < n-1; i++){
            for(int j = i+1; j < n; j++){
                total += hammingDistance(nums[i], nums[j]);
            }
        }
        return total;
    }

    public int hammingDistance(int x, int y){
        int count = 0;
        for(int i = 0; i < 32; i++){
            if((x&1) != (y&1)){
                count++;
            }
            x >>>= 1;
            y >>>= 1;
        }
        return count;
    }
}
~~~

数学法

> 计算当前位上为1的数个数为`c`，那么该位上为0的数则有`n-c`个，那么当前位的汉明距离总和则为`c * (n-c)`，将所有位上的汉明距离总和相加即为最终结果

~~~java
class Solution {
    public int totalHammingDistance(int[] nums) {
        int n = nums.length;
        int total = 0;
        for(int i = 0; i < 32; i++){
            int c = 0;
            for(int num: nums){
                c += (num >> i) & 1;
            }
            total += c * (n-c);
        }
        return total;
    }
}
~~~

#### 交替位二进制数（693）

~~~java
class Solution {
    public boolean hasAlternatingBits(int n) {
        int pre = n & 1;
        int pos = getPos(n);
        for(int i = 0; i < pos; i++){
            n >>>= 1;
            int cur = n & 1;
            if(cur == pre) { return false; }
            pre = cur;
        }
        return true;
    }

    public int getPos(int num){
        int pos = 0;
        for(int i = 0; i < 32; i++){
            if((num&1) != 0){
                pos = i;
            } 
            num >>>= 1;
        }
        return pos;
    }
}
~~~

#### 可怜的小猪（458）

数学题咧

> 假设：总时间 minutesToTest = 60，死亡时间 minutesToDie = 15，pow(x, y) 表示 x 的 y 次方，ceil(x)表示 x 向上取整
> 当前有 1 只小猪，最多可以喝 times = minutesToTest / minutesToDie = 4 次水
> 最多可以喝 4 次水，能够携带 base = times + 1 = 5 个的信息量，也就是（便于理解从 0 开始）：
> (1) 喝 0 号死去，0 号桶水有毒
> (2) 喝 1 号死去，1 号桶水有毒
> (3) 喝 2 号死去，2 号桶水有毒
> (4) 喝 3 号死去，3 号桶水有毒
> (5) 喝了上述所有水依然活蹦乱跳，4 号桶水有毒
>
> | pig1\pig2 | 第一次 | 第二次 | 第三次 | 第四次 | 排除选项 |
> | --------- | ------ | ------ | ------ | ------ | -------- |
> | 第一次    | 0      | 1      | 3      | 4      | 5        |
> | 第二次    | 6      | 7      | 8      | 9      | 10       |
> | 第三次    | 11     | 12     | 13     | 14     | 15       |
> | 第四次    | 16     | 17     | 18     | 19     | 20       |
> | 排除选项  | 21     | 22     | 23     | 24     | 25       |
>
> 如上图，每只猪在四次试毒机会的前提下，可以试出五桶水，那么两只猪同时进行，一共可以试出25桶水中的唯一毒药，得到公式
>
> `buckets = Math.pow(pigNums, tryTimes)`

~~~java
class Solution {
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int times = minutesToTest/minutesToDie + 1;
        int i = 0;
        while(Math.pow(times, i) < buckets){ i++; }
        return i;
    }
}
~~~

#### 各位相加（258）

~~~java
class Solution {
    public int addDigits(int num) {
        while(num >= 10){
            int temp = 0;
            while(num > 0){
                temp += num%10;
                num /= 10;
            }
            num = temp;
        }
        return num;
    }
}
~~~

#### 最接近的三数之和（16）

定下第一个元素控制变量，这很重要，是使用双指针的基础，再用双指针对剩下元素进行枚举

~~~java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        int diff = Integer.MAX_VALUE, res = 0;
        for(int left = 0; left < n-2; left++){
            if(left > 0 && nums[left]==nums[left-1]){
                continue;
            }
            int right = n-1, mid = left+1;
            while(mid < right){
                int sum = nums[left]+nums[mid]+nums[right];
                if(sum == target){
                    return target;
                } else if (sum > target){
                    right--;
                } else {
                    mid++;
                }
                if(Math.abs(target-sum) < diff){
                    res = sum;
                    diff = Math.abs(target-sum);
                }
            }
        }
        return res;
    }
}
~~~

### 贪心算法

#### 种花问题（605）

~~~java
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if(n == 0){ return true; }
        int m = flowerbed.length;
        if(m == 1){
            return flowerbed[0]==0;
        }
        int count = 0;
        for(int i = 0; i < m; i++){
            if(flowerbed[i] == 0){
                if((i>0 && i<m-1 && flowerbed[i-1]==0 && flowerbed[i+1]==0) || (i==0 && flowerbed[i+1]==0) || (i==m-1 && flowerbed[i-1]==0)){
                    count++;
                    flowerbed[i] = 1;
                }
            }
        }
        return count >= n;
    }
}
~~~

熟悉python语法

~~~python
class Solution:
    def canPlaceFlowers(self, flowerbed: List[int], n: int) -> bool:
        m = len(flowerbed)
        if n == 0: return True;
        if m == 1: 
            return flowerbed[0] == 0
        count = 0
        for i in range(m):
            if flowerbed[i] == 0:
                if i>0 and i<m-1 and flowerbed[i-1]==0 and flowerbed[i+1]==0:
                    count += 1
                    flowerbed[i] = 1
                if i==0 and flowerbed[i+1]==0:
                    count += 1
                    flowerbed[i] = 1
                if i==m-1 and flowerbed[i-1]==0:
                    count += 1
                    flowerbed[i] = 1
        return count>=n
~~~

### 双指针

#### 盛最多水的容器（11）

~~~java
class Solution {
    public int maxArea(int[] height) {
        int n = height.length, res = 0;
        int left = 0, right = n-1;
        while(left < right){
            int l = height[left], r = height[right];
            res = Math.max(res, (right-left)*Math.min(l, r));
            if(l <= r){
                left++;
            } else {
                right--;
            }
        }
        return res;
    }
}
~~~

## 剑指Offer

### 栈与队列

#### 用两个栈实现队列

~~~java
class CQueue {

    Stack<Integer> s1;
    Stack<Integer> s2;

    public CQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }
    
    public void appendTail(int value) {
    	s1.push(value);
    }
    
    public int deleteHead() {
    	if(!s2.isEmpty()) {
    		return s2.pop();
    	} else if(!s1.isEmpty()) {
    		while(!s1.isEmpty()) {
    			s2.push(s1.pop());
    		}
    		return s2.pop();
    	}
    	return -1;
    }
}

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
~~~

#### 包含min函数的栈

自然升序排列：`Comparator.naturalOrder()`

降序排列：`Comparator.reverseOrder()`

思路：维护一个`List`，按照升/降序排列栈中元素，调用`min`函数时返回其边缘值即可。但在`push`和`pop`时需要动态调整`List`中元素

~~~java
class MinStack {

    Stack<Integer> stack;
	List<Integer> list;
	
	/** initialize your data structure here. */
    public MinStack() {
    	stack = new Stack<>();
    	list = new ArrayList<>();
    }
    
    public void push(int x) {
    	stack.push(x);
    	list.add(x);
    	list.sort(Comparator.naturalOrder());
    }
    
    public void pop() {
    	int item = stack.peek();
    	for(int i = 0; i < list.size(); i++) {
    		if(list.get(i) == item) {
    			list.remove(i);
    			break;
    		}
    	}
    	stack.pop();
    }
    
    public int top() {
    	return stack.peek();
    }
    
    public int min() {
    	return list.get(0);
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */
~~~

#### 滑动窗口的最大值

双向队列不断向后遍历，入队三个中的最大值，按出队顺序构造数组

唯一要注意遍历到的下标位置：`<= n-k`

~~~java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if(n == 0){
            return new int[]{};
        }
        Deque<Integer> deque = new LinkedList<>();
        for(int i = 0; i <= n-k; i++){
            deque.offer(nums[i]);
            for(int j = i+1; j < i+k; j++){
                if(nums[j] > deque.peekLast()){
                    deque.pollLast();
                    deque.offer(nums[j]);
                }
            }
        }
        int[] res = new int[deque.size()];
        for(int i = 0; i < res.length; i++){
            res[i] = deque.poll();
        }
        return res;
    }
}
~~~

#### 队列的最大值

维护一个降序的双向队列保存最大值，这里巧妙的运用了队列的入队出队顺序

当较大的数后入队时，他一定会比所有比他小的数后出队，在他出队之前，`max`一直会被他占用，所以可以去除比他先入队的且比他小的数，即`pollLast`直到`peekLast > cur`

~~~java
class MaxQueue {

    private Queue<Integer> queue;
    private Deque<Integer> max;

    public MaxQueue() {
        queue = new LinkedList<>();
        max = new LinkedList<>();
    }
    
    public int max_value() {
        if(max.size()==0) { return -1; }
        return max.peek();
    }
    
    public void push_back(int value) {
        while(!max.isEmpty() && max.peekLast()<value){
            max.pollLast();
        }
        max.offer(value);
        queue.offer(value);
    }
    
    public int pop_front() {
        if(queue.isEmpty()){
            return -1;
        }
        int cur = queue.poll();
        //System.out.println(cur);
        if(cur == max.peekFirst()){
            max.pollFirst();
        }
        return cur;
    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */
~~~



### 链表

#### 从尾到头打印链表

递归

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    private List<Integer> list = new ArrayList<>();

    public void buildList(ListNode head){
        if(head == null){
            return;
        }
        if(head.next != null){
            buildList(head.next);
        }
        list.add(head.val);
    }

    public int[] reversePrint(ListNode head) {
        buildList(head);
        int[] res = new int[list.size()];
        for(int i = 0; i < res.length; i++) {
        	res[i] = list.get(i);
        }
        return res;
    }
}
~~~

数组

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {

    private List<Integer> list = new ArrayList<>();

    public int[] reversePrint(ListNode head) {
        while(head != null) {
        	list.add(head.val);
        	head = head.next;
        }
        int n = list.size();
        int[] res = new int[n];
        for(int i = 0; i < res.length; i++) {
        	res[i] = list.get(n-i-1);
        }
        return res;
    }
}
~~~

#### 反转链表

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while(cur != null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
~~~

#### 复杂链表的复制

维护两个`HashMap`

- `src<Node, Integer>`
- `dist<Integer, Node>`

根据当前节点 `cur.random`在`src`中查到`random`编号`i`，令`newCur.random`指向`dist`中编号为`i`的节点，实现`random`节点的复制

~~~java
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
class Solution {

    Map<Node, Integer> src;
    Map<Integer, Node> dist;

    public Node initList(Node head){
        src = new HashMap<>();
        dist = new HashMap<>();

        int count = 1;
        Node p = head;
        Node h = new Node(-1);
        Node q = h;
        while(p != null){
            q.next = new Node(p.val);

            q = q.next;
            dist.put(count, q);

            src.put(p, count++);
            p = p.next;
            
        }
        return h.next;
    }

    public Node copyRandomList(Node head) {
        Node newHead = initList(head);
        Node p = head;
        Node q = newHead;
        while(p != null){
            q.random = dist.get(src.get(p.random));
            p = p.next;
            q = q.next;
        }
        return newHead;
    }
}
~~~

### 字符串

#### 替换空格

~~~java
class Solution {
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();
        char[] arr = s.toCharArray();
        for(int i = 0; i < arr.length; i++){
        	if(arr[i] == ' ') {
        		res.append("%20");
        		continue;
        	}
        	res.append(arr[i]);
        }
        return res.toString();
    }
}
~~~

#### 左旋转字符串

~~~java
class Solution {
    public String reverseLeftWords(String s, int n) {
        StringBuilder res = new StringBuilder();
        char[] arr = s.toCharArray();
        for(int i = n; i < arr.length; i++){
            res.append(arr[i]);
        }
        for(int i = 0; i < n; i++){
            res.append(arr[i]);
        }
        return res.toString();
    }
}
~~~

### 查找算法

#### 数组中的重复数字

HashSet

~~~java
class Solution {
    public int findRepeatNumber(int[] nums) {
        HashSet<Integer> set = new HashSet();
        for(int num: nums){
            if(set.contains(num)){
                return num;
            }
            set.add(num);
        }
        return -1;
    }
}
~~~

排序

~~~java
class Solution {
    public int findRepeatNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for(int i = 0; i < n-1; i++){
            if(nums[i] == nums[i+1]){
                return nums[i];
            }
        }
        return -1;
    }
}
~~~

#### 在排序数组中查找数字Ⅰ*

两次二分分别找到左右边界

二分法，太乱了，用纸画一画

~~~java
class Solution {
    public int search(int[] nums, int target) {
        int left = binarySearch(nums, target, true);
        int right = binarySearch(nums, target, false);
        return right-left;
    }

    public int binarySearch(int[] nums, int target, boolean lower){
        int left = 0, right = nums.length-1;
        while(left <= right){
            int mid = (left+right) / 2;
            if(nums[mid] > target || (lower && nums[mid] >= target)){
                right = mid-1;
            } else {
                left = mid+1;
            }
        }
        return right;
    }
}
~~~

#### 0~n-1中缺失的数字

二分法，用纸画一画

~~~java
class Solution {
    public int missingNumber(int[] nums) {
        int left = 0, right = nums.length-1;
        while(left <= right){
            int mid = (left+right) / 2;
            if(nums[mid] > mid){
                right = mid-1;
            } else {
                left = mid+1;
            }
        }
        return right+1;
    }
}
~~~

#### 二维数组中的查找

1. 根据升序规律确定行数范围
2. 若行最大(右)元素`<target`，排除
3. 利用升序数组对每行进行二分查找 

~~~java
class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int rowLength = matrix.length;
        if(rowLength == 0){
            return false;
        }
        int columnLength = matrix[0].length;
        if(columnLength == 0){
            return false;
        }
        int m = rowLength;
        for(int i = 0; i < rowLength; i++){
            if(matrix[i][0] > target){
                m = i;
                break;
            }
        }
        for(int i = 0; i < m; i++){
            if(matrix[i][columnLength-1] < target){
                continue;
            }
            if(binarySearch(matrix[i], target)){
                return true;
            }
        }
        return false;
    }

    public boolean binarySearch(int[] nums, int target){
        int left = 0, right = nums.length-1;
        while(left <= right){
            int mid = (left+right) / 2;
            if(nums[mid] > target){
                right = mid-1;
            } else if(nums[mid] < target){
                left = mid+1;
            } else {
                return true;
            }
        }
        return false;
    }
}
~~~

#### 第一个只出现一次的字符

利用Map的不重复性找到只出现一次的字符，其value设为true

注意要找第一个出现的，所以按照原数组顺序进行遍历

~~~java
class Solution {

    private Map<Character, Boolean> map;

    public char firstUniqChar(String s) {
        map = new HashMap<>();
        for(char c: s.toCharArray()){
            if(map.containsKey(c)){
                map.put(c, false);
                continue;
            }
            map.put(c, true);
        }
        for(char c: s.toCharArray()){
            if(map.get(c)){
                return c;
            }
        }
        return ' ';
    }
}
~~~

遍历Map的方法

~~~java
for (Map.Entry<String, String> entry : map.entrySet()) {
    String mapKey = entry.getKey();
    String mapValue = entry.getValue();
    System.out.println(mapKey + "：" + mapValue);
}


Iterator<Entry<String, String>> entries = map.entrySet().iterator();
while (entries.hasNext()) {
    Entry<String, String> entry = entries.next();
    String key = entry.getKey();
    String value = entry.getValue();
    System.out.println(key + ":" + value);
}


for (String key : map.keySet()) {
    System.out.println(key);
}
// 打印值集合
for (String value : map.values()) {
    System.out.println(value);
}


for(String key : map.keySet()){
    String value = map.get(key);
    System.out.println(key+":"+value);
}
~~~

### 搜索与回溯算法

#### 从上到下打印二叉树 Ⅰ

注意考虑`root`为空的情况，题目要求返回`new int[0]`

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

    private Deque<TreeNode> queue;
	private List<Integer> list;
	
	public int[] levelOrder(TreeNode root) {
        if(root == null){
            return new int[0];
        }
        queue = new ArrayDeque<>();
        list = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
        	TreeNode cur = queue.poll();
        	if(cur.left != null) { queue.offer(cur.left); }
        	if(cur.right != null) { queue.offer(cur.right); }
        	list.add(cur.val);
        }
        
        int[] res = new int[list.size()];
        for(int i = 0; i < res.length; i++) {
        	res[i] = list.get(i);
        }
        return res;
    }

}
~~~

略改进一点，少一个循环，少用一个`List`

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

    private Deque<TreeNode> queue;

    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null){
            return new ArrayList<>();
        }
        queue = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            List<Integer> row = new ArrayList<>();
            int n = queue.size();
            for(int i = 0; i < n; i++){
                TreeNode node = queue.poll();
                row.add(node.val);
                if(node.left != null) { queue.offer(node.left); }
                if(node.right != null) { queue.offer(node.right); }
            }
            res.add(row);    
        }
        return res;
    }
}
~~~

#### 从上到下打印二叉树Ⅱ*

与上题不同之处在于要把树每层分开，如何分层：用一个`list`将本轮`queue`所有节点出队记录下来，将`list`中节点的值构成一行加入`res`，同时按照顺序将节点的左右儿子入队，进行下一层的记录

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

    private Deque<TreeNode> queue;

    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null){
            return new ArrayList<>();
        }
        queue = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            List<TreeNode> list = new ArrayList<>();
            List<Integer> row = new ArrayList<>();
            while(!queue.isEmpty()){
                list.add(queue.poll());
            }
            for(TreeNode node: list){
                row.add(node.val);
                if(node.left != null) { queue.offer(node.left); }
                if(node.right != null) { queue.offer(node.right); }
            }
            res.add(row);    
        }
        return res;
    }
}
~~~

#### 从上到下打印二叉树Ⅲ

与上题相似，同样是要分层，这里采用一样的方法

但在记录`val`时要求`之`字型走位，用一个`count`变量判断层数奇偶决定是从前往后构造`row`还是从后往前，但注意入队依旧按照从前往后的顺序

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

    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null){
            return new ArrayList<>();
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.offer(root);
        int count = 1;
        while(!queue.isEmpty()){
            List<TreeNode> list = new ArrayList<>();
            List<Integer> row = new ArrayList<>();
            while(!queue.isEmpty()){
                list.add(queue.poll());
            }
            //正
            if(count % 2 == 1){
                for(TreeNode node: list){
                    row.add(node.val);
                    if(node.left != null) { queue.offer(node.left); }
                    if(node.right != null) { queue.offer(node.right); }
                }
            } else { //反
                for(int i = list.size()-1; i >= 0; i--){
                    row.add(list.get(i).val);                  
                }
                for(TreeNode node: list){
                    if(node.left != null) { queue.offer(node.left); }
                    if(node.right != null) { queue.offer(node.right); }
                }
            }
            count++;
            res.add(row);
        }
        return res;
    }
}
~~~

优化了一下

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
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null){
            return new ArrayList<>();
        }
        int count = 0;
        Deque<TreeNode> queue = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int n = queue.size();
            List<Integer> row = new ArrayList<>();
            for(int i = 0; i < n; i++){
                TreeNode cur = queue.poll();
                if(cur.left != null){ queue.offer(cur.left); }
                if(cur.right != null){ queue.offer(cur.right); }
                row.add(cur.val);
            }
            if(count % 2 == 0){
                res.add(row);
            } else {
                res.add(reverseList(row));
            }
            count++;
        }
        return res;
    }

    public List<Integer> reverseList(List<Integer> list){
        List<Integer> reverse = new ArrayList<>();
        int n = list.size();
        for(int i = n-1; i >= 0; i--){
            reverse.add(list.get(i));
        }
        return reverse;
    }
}
~~~

#### 树的子结构*

错解，我想先序遍历得到各自值的`list`，然后比较是否包含，用`-1`代替`null`，但就算父树包含了子树，其`null`的位置也很有可能不同，如：

~~~
[10,12,6,8,3,11]
[10,12,6,8]
~~~

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

    private List<Integer> list;

    public void dfs(TreeNode A){
        if(A == null){
            list.add(-1);
            return;
        }
        list.add(A.val);
        if(A.left != null) { dfs(A.left); }
        if(A.right != null) { dfs(A.right); }
    }

    public List<Integer> getList(TreeNode A){
        list = new ArrayList<>();
        dfs(A);
        return list;
    }

    public boolean isSubStructure(TreeNode A, TreeNode B) {
        List<Integer> a = getList(A);
        List<Integer> b = getList(B);
        if(B == null){ return false; }
        for(int i = 0; i < a.size(); i++){
            int j = 0;
            while(a.get(i) == b.get(j) || b.get(j) == -1){
                if(j == b.size()-1){
                    return true;
                }
                if(i == a.size()-1){
                    return false;
                }
                i++; j++;
            }
        }
        return false;
    }
}
~~~

递归解法

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
        if(A == null || B == null){
            return false;
        }
        return recur(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    public boolean recur(TreeNode A, TreeNode B){
        if(B == null){ return true; }
        if(A == null || A.val != B.val) { return false; }
        return recur(A.left, B.left) && recur(A.right, B.right);
    }
}
~~~

#### 二叉树的镜像*

递归，反复看，用纸画画

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
        TreeNode left = mirrorTree(root.left);
        TreeNode right = mirrorTree(root.right);
        root.left = right;
        root.right = left;

        return root;
    }

}
~~~

#### 对称二叉树*

自己写出来的

1. 明确问题，缩小问题
2. 明确返回条件
3. 合并问题

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

    public boolean recur(TreeNode left, TreeNode right){
        if(left == null && right == null){
            return true;
        }
        if((left==null && right!=null) || (left!=null && right==null)){
            return false;
        }
        if(left.val != right.val){
            return false;
        }
        return recur(left.right, right.left) && recur(left.left, right.right);
    }


    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return recur(root.left, root.right);
    }

    
}
~~~

#### 矩阵中的路径*

dfs，如何使路径的搜索有记忆，在向深处搜索时，令原位置字符为空，这样向原位置匹配时一定会匹配不成功

我一开始想传原来的坐标，但这样只能避免上一层的重复搜索，很有可能重复搜索到很久之前的元素

~~~java
class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(board[i][j] == word.charAt(0)){
                    if(check(board, word, 0, i, j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean check(char[][] board, String word, int index, int x, int y){
        if(index == word.length()){
            return true;
        }
        if(x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != word.charAt(index)){
            return false;
        }               
        board[x][y] = ' ';
        boolean res = check(board, word, index+1, x+1, y) || check(board, word, index+1, x-1, y)
                    ||check(board, word, index+1, x, y+1) || check(board, word, index+1, x, y-1);
        board[x][y] = word.charAt(index);
        return res;

    }
}
~~~

#### 机器人的运动范围*

动态规划

~~~java
class Solution {

    public int movingCount(int m, int n, int k) {
        int count = 0;
        boolean[][] dp = new boolean[m][n];
        initDp(m, k, dp, true);
        initDp(n, k, dp, false);
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                if(getSum(i)+getSum(j) <= k && (dp[i-1][j] || dp[i][j-1])){
                    dp[i][j] = true;
                    continue;
                }
                dp[i][j] = false;
            }
        }
        for(boolean[] bs: dp){ for(boolean b: bs){ if(b){ count++; } } }
        return count;
    }
    
    public void initDp(int border, int k, boolean[][] dp, boolean flag){
        if(flag){
            for(int i = 0; i < border; i++){
                if(getSum(i) > k){ break; }
                dp[i][0] = true;
            }
        } else {
            for(int i = 0; i < border; i++){
                if(getSum(i) > k){ break; }
                dp[0][i] = true;
            }
        }
    }

    public int getSum(int num){
        int res = 0;
        while(num > 0){
            res += num% 10;
            num /= 10;
        }
        return res;
    }
}
~~~

#### 二叉树中和为某一值的路径*

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
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        res = new LinkedList<>();
        row = new LinkedList<>();
        dfs(root, target, 0);
        return res;
    }

    List<List<Integer>> res;
    Deque<Integer> row;

    public void dfs(TreeNode node, int target, int sum){
        if(node == null || (isLeaf(node) && (node.val+sum != target))){
            return;
        }
        if(node.val + sum == target && isLeaf(node)){
            row.add(node.val);
            res.add(new LinkedList(row));
            row.pollLast();
            return;
        }
        row.offer(node.val);
        dfs(node.left, target, sum+node.val);
        dfs(node.right, target, sum+node.val);
        row.pollLast();
    }

    public boolean isLeaf(TreeNode node){
        if(node.left == null && node.right == null){
            return true;
        }
        return false;
    }
}
~~~

#### 二叉搜索树与双向链表*

明确一点，二叉搜索树的中序遍历是升序的

~~~java
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/
class Solution {

    private Node tail;
    private Node head;

    public Node treeToDoublyList(Node root) {
        if(root == null){
            return null;
        }
        dfs(root);
        head.left = tail;
        tail.right = head;
        return head;
    }
    
    /*
    void dfs(Node cur) {
        if(cur == null) return;
        dfs(cur.left);
        if(pre != null) pre.right = cur;
        else head = cur;
        cur.left = pre;
        pre = cur;
        dfs(cur.right);
    }
    */

    public void dfs(Node node){
        if(node == null){
            return;
        }
        dfs(node.left);
        //do something
        if(tail == null){
            head = node;          
        } else {
            tail.right = node;
        }
        node.left = tail;
        tail = node;
        dfs(node.right);
    }
}
~~~

#### 二叉搜索树的第k大节点

自己写的，中序遍历二叉搜索树，从小到大数，这不好，要先统计节点数，算出倒数第k是正数第几

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

    private int nodeNum;
    private int count;
    private int res;

    public int kthLargest(TreeNode root, int k) {
        count = 0; nodeNum = 0;
        countNode(root);
        dfs(root, k);
        return res;
    }

    public void dfs(TreeNode node, int k){
        if(node == null){
            return;
        }
        dfs(node.left, k);
        //do something
        count++;   
        if(k == nodeNum-count+1){
            res = node.val;
        }
        dfs(node.right, k);
    }

    public void countNode(TreeNode node){
        if(node == null){
            return;
        }
        countNode(node.left);
        //do something
        nodeNum++;     
        countNode(node.right);
    }

}
~~~

采用倒中序遍历，即右->中->左，这样直接选到第k个即可

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

    private int count;
    private int res;

    public int kthLargest(TreeNode root, int k) {
        count = 0;
        dfs(root, k);
        return res;
    }

    public void dfs(TreeNode node, int k){
        if(node == null ||  count > k){
            return;
        }
        dfs(node.right, k);
        //do something
        count++;   
        if(k == count){
            res = node.val;
            return;
        }
        dfs(node.left, k);
    }
}
~~~

#### 二叉树的深度

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

    private int count;

    public int maxDepth(TreeNode root) {
        count = 0;
        dfs(root, 1);
        return count;
    }

    public void dfs(TreeNode node, int deep){
        if(node == null){
            return;
        }
        dfs(node.left, deep+1);
        if(deep > count){
            count = deep;
        }
        dfs(node.right, deep+1);
    }
}
~~~

#### 平衡二叉树*

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
    
    public boolean isBalanced(TreeNode root) {
        if(root == null){  return true; }
        return Math.abs(height(root.left)-height(root.right))<=1 && isBalanced(root.left) && isBalanced(root.right);
    }

    public int height(TreeNode node){
        if(node == null){
            return 0;
        }
        return Math.max(height(node.left), height(node.right))+1;
    }

}
~~~

#### 求1+2+3+...+n

参考上一题的`height`递归，惨遭秒杀

~~~java
class Solution {
    public int sumNums(int n) {
        if(n == 0){
            return 0;
        }
        return sumNums(n-1)+n;
    }
}
~~~

#### 二叉搜索树的最近公共祖先*

dfs：判断`node`是否是`q`或`p`的真正祖先

`node`自身可以是自身的祖先

若`dfs(node.left) && dfs(node.right)` 说明`node`同时是`q`和`p`的真正祖先，一定要注意`q`是`p`的祖先或`p`是`q`的祖先的情况，即`node == p && (dfs(node.left) || dfs(node.right))`，因为dfs判断是真正祖先，没有考虑自己是自己的祖先的情况

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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return res;    
    }

    public boolean dfs(TreeNode node, TreeNode p, TreeNode q){
        if(node == null){
            return false;
        }
        boolean left = dfs(node.left, p, q);
        boolean right = dfs(node.right, p, q);
        if((left && right) || (p == node || q == node) && (left || right)){
            res = node;
        }
        if(left || right || node == q || node == p){
            return true;
        }
        return false;
    }
}
~~~

第一种是针对所有二叉树的搜索方法，由于是二叉搜索树，即满足左小右大的规律，我们可以简化搜索过程

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

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p.val, q.val);
        return res;    
    }

    public void dfs(TreeNode node, int q, int p){
        if(node == null){
            return;
        }
        if((node.val < Math.max(q, p) 
        && node.val > Math.min(q, p)) 
        || node.val == Math.max(q, p) 
        || node.val == Math.min(q, p)){
            res = node;
            return;
        }
        if(node.val > Math.max(q, p)){
            dfs(node.left, q, p);
        } else if(node.val < Math.min(q, p)){
            dfs(node.right, q, p);
        }
    }
}
~~~

#### 二叉树的最近公共祖先

找公共祖先的通用方法，同上一题题解一

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

    private TreeNode ans;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return ans;
    }

    public boolean dfs(TreeNode node, TreeNode p, TreeNode q){
        if(node == null){
            return false;
        }
        boolean l = dfs(node.left, p, q);
        boolean r = dfs(node.right, p, q);
        if((l && r) || (node == q || node == p) && (l || r)){
            ans = node;
        }
        if(l || r || node == p || node == q){
            return true;
        }
        return false;
    }
}
~~~

#### 序列化二叉树*

我想用字符串储存二叉树的前序和中序遍历结果，再通过遍历结果重建二叉树，这里有一个很严苛的要求就是节点值不能重复，很恶心，因为这样我不能根据根节点值精确定位到中序遍历的相应下标

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
public class Codec {

    private StringBuilder serializeString;

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        serializeString = new StringBuilder();
        preorderDfs(root);
        serializeString.append("/");
        inorderDfs(root);
        return serializeString.toString();
    }

    public void inorderDfs(TreeNode root){
        if(root == null){
            return;
        }
        inorderDfs(root.left);
        //do something
        serializeString.append(root.val+"");
        inorderDfs(root.right);
    }

    public void preorderDfs(TreeNode root){
        if(root == null){
            return;
        }
        //do something
        serializeString.append(root.val+"");
        preorderDfs(root.left);
        preorderDfs(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        //System.out.println(data);
        if(data.length() == 1){
            return null;
        }
        index = 0;
        String[] d = data.split("/");
        char[] preorder = d[0].toCharArray();
        char[] inorder = d[1].toCharArray();
        int n = preorder.length;


        map = new HashMap<>();
        for(char c: preorder){
            for(int i = 0; i < n; i++){
                if(c == inorder[i]){
                    map.put(c, i);
                    continue;
                }
            }         
        }

        return buildTree(preorder, inorder, 0, n-1);
    }

    private int index;
    private Map<Character, Integer> map;

    public TreeNode buildTree(char[] preorder, char[] inorder, int left, int right){
        if(left > right){
            return null;
        }
        //System.out.println(preorder[index]);
        TreeNode root = new TreeNode(Integer.parseInt(preorder[index]+""));
        int mid = map.get(preorder[index++]);
        root.left = buildTree(preorder, inorder, left, mid-1);
        root.right = buildTree(preorder, inorder, mid+1, right);
        return root;
    }
    
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
~~~

单利用前序遍历构造二叉树，空的地方字符串用`"null"`表示，前序遍历结果用`Integer[]`来储存，空的地方直接存`null`，序列化值用`/`隔开，方便之后转化`Integer.parseInt(String str)`，注意下标移动的规则，参考重建二叉树

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
public class Codec {

    private StringBuilder serializeString;

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        serializeString = new StringBuilder();
        preorderDfs(root);
        return serializeString.toString(); 
    }

    public void preorderDfs(TreeNode root){
        if(root == null){
            serializeString.append("null/");
            return;
        }
        //do something
        serializeString.append(root.val+"/");
        preorderDfs(root.left);
        preorderDfs(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        //System.out.print(data);
        String[] arr = data.split("/");
        // for(String str: arr){
        //     System.out.print(str + " ");
        // }
        int n = arr.length;
        Integer[] values = new Integer[n];
        for(int i = 0; i < n; i++){
            if(arr[i].equals("null")){
                values[i] = null;
                continue;
            }
            values[i] = Integer.parseInt(arr[i]);
        }
        index = 0;

        //return null;
        return preorderDfsBuild(values);
    }
    

    private int index;
    public TreeNode preorderDfsBuild(Integer[] values){
        if(index >= values.length){
            return null;
        }
        if(values[index] == null){
            index++;
            return null;
        }
        int cur = values[index++];
        TreeNode root = new TreeNode(cur);
        root.left = preorderDfsBuild(values);
        root.right = preorderDfsBuild(values);
        return root;
    }
    
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
~~~

#### 字符串的排列*

找出字符串的全排列并且去重，递归的方法，有点像dfs，利用`HashSet`达到去重的目的，一步步确定当前位元素

~~~java
class Solution {

    private List<String> res;
    private char[] str;

    public String[] permutation(String s) {
        res  = new ArrayList<>();
        str = s.toCharArray();
        dfs(0);
        return res.toArray(new String[res.size()]);
    }

    public void dfs(int index){
        int n = str.length;
        if(index == n-1){
            res.add(String.valueOf(str));
            return;
        }
        HashSet<Character> set = new HashSet<>();
        for(int i = index; i < n; i++){
            char cur = str[i];
            if(set.contains(cur)){ continue; }
            set.add(cur);
            swap(i, index);
            dfs(index+1);
            swap(i, index);
        }
    }

    public void swap(int i, int j){
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }
}
~~~

### 动态规划

#### 斐波那契数列

最简单的`dp`，状态转移方程：`dp[i]=dp[i-1]+dp[i-2]`

~~~java
class Solution {
    public int fib(int n) {
        if(n < 2){
            return n;
        }
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i < n+1; i++){
            dp[i] = (dp[i-1]+dp[i-2]) % 1000000007;
        }
        return dp[n];
    }
}
~~~

#### 青蛙跳台阶问题

研究跳的最后一下，只有可能是一阶或二阶，所以`dp[n]=dp[n-1]+dp[n-2]`，`dp[i]`表示`i`层台阶的跳法

~~~java
class Solution {
    public int numWays(int n) {
        //dp[i] = dp[i-1]-1 + dp[i-2]*2
        if(n < 2){
            return 1;
        }
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i <= n; i++){
            dp[i] = (dp[i-1]+dp[i-2]) % 1000000007;
        }
        return dp[n];
    }
}
~~~

#### 股票的最大利润

状态转移方程：`dp[i] = dp[i]+prices[i]-prices[i-1]<0 (dp[i] >= 0)`

~~~java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if(n < 2){
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 0;
        for(int i = 1; i < n; i++){
            dp[i] = dp[i-1]+prices[i]-prices[i-1];
            if(dp[i] < 0){ dp[i] = 0; }
        }
        Arrays.sort(dp);
        return dp[n-1];
    }
}
~~~

#### 礼物的最大价值

只能向右和向下移动，第一行和第一列通过累加进行初始化，其余位置的状态转移方程：`dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + grid[i][j] (i>=1, j>=1)`

~~~java
class Solution {
    public int maxValue(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for(int i = 1; i < n; i++){
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        for(int i = 1; i < m; i++){
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }
}
~~~

#### 最长不含重复字符的子串

~~~java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if(n == 0){
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        char[] arr = s.toCharArray();
        Map<Character, Integer> index = new HashMap<>();
        index.put(arr[0], 0);
        for(int i = 1; i < n; i++){
            int near = index.getOrDefault(arr[i], -1);
            dp[i] = (i-near) > dp[i-1] ? dp[i-1]+1 : i-near;
            index.put(arr[i], i);
        }
        Arrays.sort(dp);
        return dp[n-1];
    }
}
~~~

#### 把数字翻译成字符串

`dp[i]`表示在`i`位置能构成的翻译方法数量，当相邻的两位构成的数字在区间`[10, 25]`，即`nums[i]`和`nums[i-1]`，那么`dp[i]+=dp[i-2]`；必然的，由于每个个位数都可以进行翻译，所以`dp[i]+=dp[i-1]`，此上两个赋值语句便是状态转移方程

~~~java
class Solution {
    public int translateNum(int num) {
        Stack<Integer> stack = new Stack<>();
        while(num > 0){
            stack.push(num%10);
            num /= 10;
        }
        int n = stack.size();
        if(n <= 1){
            return 1;
        }
        int[] nums = new int[n];
        for(int i = 0; i < n; i++){
            nums[i] = stack.pop();
        }
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = nums[0]*10+nums[1] > 25 ? 1 : 2;
        for(int i = 2; i < n; i++){
            dp[i] = dp[i-1];
            if(nums[i-1]*10+nums[i] <= 25 && nums[i-1]*10+nums[i] >= 10){
                dp[i] += dp[i-2];
            }
        }
        return dp[n-1];
    }
}
~~~

#### 最长公共子序列

动态规划，状态转移方程：

~~~java
if(arr1[i] == arr2[j]){ dp[i][j] = dp[i-1][j-1]+1; } else { dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); }
~~~

初始化边界，最大为1

~~~java
for(int i = 1; i < n; i++){
    dp[0][i] = arr1[0]==arr2[i] ? 1:dp[0][i-1];
}
for(int i = 1; i < m; i++){
    dp[i][0] = arr1[i]==arr2[0] ? 1:dp[i-1][0];
}
~~~

完整代码

~~~java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        char[] arr1 = text1.toCharArray(), arr2 = text2.toCharArray();
        int[][] dp = new int[m][n];
        //System.out.println(m + " " + n);
        dp[0][0] = arr1[0]==arr2[0] ? 1:0;
        for(int i = 1; i < n; i++){
        	//System.out.println(arr1[i] + " " + arr2[0]);
            dp[0][i] = arr1[0]==arr2[i] ? 1:dp[0][i-1];
            
        }
        for(int i = 1; i < m; i++){
            dp[i][0] = arr1[i]==arr2[0] ? 1:dp[i-1][0];
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                if(arr1[i] == arr2[j]){
                    dp[i][j] = dp[i-1][j-1]+1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[m-1][n-1];
    }
}
~~~

#### 丑数

动态规划，很巧妙的避免了重复数据，同时筛选了最小值

~~~java
class Solution {
    public int nthUglyNumber(int n) {
		int a = 0, b = 0, c = 0;
        int[] dp = new int[n];
        dp[0] = 1;
        for(int i = 1; i < n; i++){
            dp[i] = Math.min(Math.min(dp[a]*2, dp[b]*3), dp[c]*5);
            if(dp[a]*2 == dp[i]) { a++; }
            if(dp[b]*3 == dp[i]) { b++; }
            if(dp[c]*5 == dp[i]) { c++; }
        }
        return dp[n-1];
    }
}
~~~

很直观的堆排序，超出了`int`范围，要用`Long`来接收，效率很低

~~~java
class Solution {
    public int nthUglyNumber(int n) {
		PriorityQueue<Long> queue = new PriorityQueue<>();
        HashSet<Long> set = new HashSet<>();
        int count = 0;
        long res = -1;
        queue.offer((long)1);
        set.add((long)1);
        while(count < n){
            res = queue.poll();
            count++;
            if(!set.contains(res*2)){
                queue.offer(res*2);
                set.add(res*2);
            }
            if(!set.contains(res*3)){
                queue.offer(res*3);
                set.add(res*3);
            }
            if(!set.contains(res*5)){
                queue.offer(res*5);
                set.add(res*5);
            }
        }
        return (int)res;
    }
}
~~~

#### 正则表达式匹配

~~~java
class Solution {
    public boolean isMatch(String s, String p) {
        char[] x = s.toCharArray(), y = p.toCharArray();
        int m = x.length, n = y.length;

        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for(int i = 0; i <= m; i++){
            for(int j = 1; j <= n; j++){
                //死记状态转移方程
                if(y[j-1] == '*'){
                    dp[i][j] = dp[i][j-2];
                    if(checked(s, p, i-1, j-2)){
                        dp[i][j] = dp[i][j] || dp[i-1][j];
                    }
                } else {
                    if(checked(s, p, i-1, j-1)){
                        dp[i][j] = dp[i-1][j-1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    public boolean checked(String s, String p, int i, int j){
        if(i < 0 || j < 0){
            return false;
        }
        if(p.charAt(j) == '.'){
            return true;
        }
        return s.charAt(i)==p.charAt(j);
    }
}
~~~



### 双指针

 #### 删除链表的节点

快慢指针`pre、cur`

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteNode(ListNode head, int val) {
        
        if(head.val == val){
            return head.next;
        }
        ListNode pre = head, cur = head.next;
        while(cur != null){
            if(cur.val == val){
                pre.next = cur.next;
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        return head;
    }
}
~~~

#### 链表的倒数第k个节点

让快慢指针之间相隔`k`的距离，同步向后移动，知道快指针到达链表末尾，此时慢指针一定是倒数第`k`个节点

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode slow = head;
        ListNode fast = head;
        for(int i = 1; i < k; i++){
            fast = fast.next;
        }
        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
~~~

#### 调整数组顺序使奇数位于偶数前面

快慢指针，`fast`不断后移，碰到奇数则交换`fast`和`slow`位置的元素，使`nums[i], i<=slow`全为奇数

~~~java
class Solution {
    public int[] exchange(int[] nums) {
        int n = nums.length;
        int slow = 0, fast;
        for(fast = 0; fast < n; fast++){
            if(nums[fast] % 2 == 1){
                swap(nums, slow, fast);
                slow++;
            }
        }
        return nums;
    }

    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

#### 剑指 Offer 57：和为s的两个数字

升序数组，用左右指针逐渐逼近`target`，即大了右指针左移，小了左指针右移

~~~java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while(left <= right){
            if(nums[left]+nums[right] > target){
                right--;
            } else if(nums[left]+nums[right] < target){
                left++;
            } else {
                return new int[]{nums[left], nums[right]};
            }
        }
        return new int[]{};
    }
}
~~~

#### 翻转单词*

双指针，一定注意边界条件，很恶心

~~~java
class Solution {
    public String reverseWords(String s) {
        s = s.trim();
        char[] arr = s.toCharArray();
        int n = arr.length;
        StringBuilder res = new StringBuilder();
        for(int right = n-1; right >= 0; right--) {
        	while(arr[right] == ' ') {
        		right--;
        	}
        	int left = right-1;
        	while(left >= 0 && arr[left] != ' ') {
        		left--;
        	}
            if(left == -1) {
        		res.append(s.substring(left+1, right+1));
        		return res.toString();
        	}
        	res.append(s.substring(left+1, right+1) + ' ');
        	right = left;
        }
        
        return res.toString();
    }
}
~~~



### 排序

#### 把数组排成最小的数

这里拼接字符串的排序方法是这样的：`x+y < y+x => x < y`

不信试试

~~~java
class Solution {
    public String minNumber(int[] nums) {
		int n = nums.length;
        String[] strs = new String[n];
        for(int i = 0; i < n; i++) {
        	strs[i] = nums[i]+"";
        }
        quickSort(strs, 0, n-1);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
        	sb.append(strs[i]);
        }
        return sb.toString();
    }

    public void quickSort(String[] nums, int left, int right){
        if(left >= right){
            return;
        }
        int mid = (left+right) / 2;
        swap(nums, mid, right);
        String pivot = nums[right];
        int position = left;
        for(int i = left; i < right; i++){
            if(compare(nums[i], pivot)){
                swap(nums, i, position++);
            }
        }
        swap(nums, position, right);
        quickSort(nums, left, position-1);
        quickSort(nums, position+1, right);
    }

    public void swap(String[] nums, int i, int j){
    	String temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    public boolean compare(String str1, String str2) {
    	long val1 = Long.valueOf(str1+str2);
    	long val2 = Long.valueOf(str2+str1);
    	if(val1 < val2) {
    		return true;
    	}
    	return false;
    }
}
~~~

#### 扑克牌中的顺子

用`0`补差价，补不了就`return false`

~~~java
class Solution {
    public boolean isStraight(int[] nums) {
        List<Integer> temp = new ArrayList<>();
        for(int num: nums){
            if(num != 0){
                temp.add(num);
            }
        }
        int king = nums.length-temp.size();
        temp.sort((a, b) -> a-b);
        for(int i = 0; i < temp.size()-1; i++){
            int diff = temp.get(i+1)-temp.get(i);
            if(diff == 0){
                return false;
            }
            if(diff != 1){
                if(king+1 >= diff){
                    king -= diff;
                    continue;
                }
                return false;
            }
        }
        return true;
    }
}
~~~

#### 最小的k个数

~~~java
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        int[] res = new int[k];
        for(int i = 0; i < k; i++){
            res[i] = arr[i];
        }
        return res;
    }
}
~~~

采用快速排序的思路，将数组分隔，找出其中小的k个数

~~~java
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        quickSort(arr, k, 0, arr.length-1);
        return Arrays.copyOf(arr, k);
    }

    public void quickSort(int[] arr, int k, int left, int right){
        int pos = partition(arr, left, right);
        if(pos+1 == k || pos == -1){
            return;
        } else if(pos+1 < k){
            quickSort(arr, k, pos+1, right);
        } else {
            quickSort(arr, k, left, pos-1);
        }
    }

    public int partition(int[] nums, int left, int right){
        if(left >= right){
            return -1;
        }
        int mid = (left+right) / 2;
        swap(nums, mid, right);
        int pivot = nums[right], pos = left;
        for(int i = left; i < right; i++){
            if(nums[i] <= pivot){
                swap(nums, pos++, i);
            }
        }
        swap(nums, pos, right);
        return pos;
    }

    public void swap(int[] nums, int i, int j){
        if(i == j){
            return;
        }
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

#### 数据流中的中位数

~~~java
class MedianFinder {

    private List<Integer> list;

    /** initialize your data structure here. */
    public MedianFinder() {
        list = new ArrayList<>();
    }
    
    public void addNum(int num) {
        list.add(num);
        list.sort((a, b) -> {
            return a-b;
        });
    }
    
    public double findMedian() {
        int n = list.size();
        double median = -1;
        if(n % 2 == 0){
            median = ((double)(list.get(n/2)+list.get(n/2-1))) / 2;
        } else {
            median = (double)list.get(n/2);
        }
        return median;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
~~~

### 分治算法

#### 重建二叉树*

根据前序、中序遍历结果构造二叉树

有这样的规律：前序遍历按顺序都是根节点先被找到；在相应的中序遍历中，该根节点左侧为他的左子树，右侧为他的右子树

于是，我们按顺序遍历前序结果，构造当前的根节点，通过根节点在中序遍历结果中的位置确定其左右子树的左右边界。返回值为当前的根节点。递归的调用`build`函数构造当前根节点的左右子树，递归返回条件即为子树左边界大于右边界，此时返回空值

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
    private Map<Integer, Integer> map;
	private int index;
	
	public TreeNode buildTree(int[] preorder, int[] inorder) {
        index = 0;
        map = new HashMap<>();
        int n = preorder.length;
        for(int val: preorder) {
        	for(int index = 0; index < n; index++) {
        		if(inorder[index] == val) {
        	      	map.put(val, index);
        	      	continue;
        		}
        	}
  
        }
		return build(preorder, inorder, 0, n-1);
    }
	
	public TreeNode build(int[] preorder, int[] inorder, int left, int right) {
		if(left > right) {
			return null;
		}
		TreeNode root = new TreeNode(preorder[index]);
		int mid = map.get(preorder[index++]);
		root.left = build(preorder, inorder, left, mid-1);
		root.right = build(preorder, inorder, mid+1, right);
		return root;
	}
}
~~~

#### 数值的整数次方

> 快速幂：
>
> ~~~java
> int x = 2;
> int n = 10; // 10 == 1010
> /*x^n == (x^1*0) * (x^2*1) * (x^4*0) * (x^8*0)
>    == x^2 * x^8
> 很明显，乘方可以转化为在二进制位为1时的多项式乘法
> */
> ~~~
>
> 这样能大大降低时间复杂度

另外要注意`-2147483648`在`int`范围内而`2147483647`并不在，所以在进行转换时尽量用`long`类型去接收`int n`

~~~java
class Solution {
    public double myPow(double x, int n) {
        long t = n;
        t = t<0 ? -t:t;      
        double res = 1;
        while(t > 0){
            if((t&1) == 1){
                res *= x;
            }
            x *= x;
            t >>>= 1;
        }
        if(n < 0){
            return 1/res;
        }
        return res;
    }
}
~~~

#### 二叉搜索树的后序遍历序列

是这样的，我观察出来的规律：

由于序列最右的一定是根节点，以其值为分界，可以将序列分成两部分（类似于快排）。若不能成功划分，那么一定不能构成二叉搜索树；若成功划分，继续对剩下的部分继续划分，直到左边界大于等于有边界，返回`true`

~~~java
class Solution {
    public boolean verifyPostorder(int[] postorder) {
        int n = postorder.length;
        return check(postorder, 0, n-1);
    }  

    public boolean check(int[] postorder, int left, int right){
        if(left >= right){
            return true;
        }
        int mid = left;
        boolean flag = false;
        for(int i = left; i < right; i++){
            if(postorder[i] > postorder[right] && !flag){
                flag = true;
                mid = i;
            }
            if(flag && postorder[i]<postorder[right]){
                return false;
            }
        }
        return check(postorder, left, mid-1) && check(postorder, mid, right-1);
    }
}
~~~

### 位运算

#### 二进制中1的个数

~~~java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int res = 0;
        for(int i = 0; i < 32; i++){
            if((n&1) == 1){
                res++;
            }
            n >>>= 1;
        }
        return res;
    }
}
~~~

#### 不用加减乘除做加法*

复习一下位运算

- ^：相同则为0，不同则为1（亦或）
- ~：求反
- |：全为0才为0（或）
- ^：全为1才为1（并）

~~~java
class Solution {
    public int add(int a, int b) {
        while(b != 0){
            int c = (a&b) << 1;
            a ^= b;
            b = c;
        }
        return a;
    }
}
~~~

#### 数组中数字出现的次数Ⅰ*

设只出现一次的数为`a, b`

1. 先用`tag`与数组所有元素求并，出现两次的元素求两次并会变成`0`，所以这步操作会使得`tag`成为`a^b`，也就是说`tag`储存了`a, b`不同的位为`1`
2. 以`tag`随便一位为`1`的作为`flag`区分`a`和`b`
3. 再遍历一次数组，用`flag`区分`a、b`，同样是求并，出现两次的抵消，出现一次的被分开，得到结果

~~~java
class Solution {
    public int[] singleNumbers(int[] nums) {
        int tag = 0;
        for(int num: nums){
            tag ^= num;
        }
        int flag = 1;
        while((tag&flag) == 0){
            flag <<= 1;
        }
        int a = 0, b = 0;
        for(int num: nums){
            if((num&flag) != 0){
                a ^= num;
            } else {
                b ^= num;
            }
        }
        return new int[]{a, b};
    }
}
~~~

#### 数组中数字出现的次数Ⅱ*

笨方法，统计每一位上出现1的次数。还有一种效率很高的办法，可惜没看懂

~~~java
class Solution {
    public int singleNumber(int[] nums) {
        int[] counts = new int[32];
        for(int num: nums){
            for(int i = 0; i < 32; i++){
                counts[i] += (num&1);
                num >>>= 1;
            }
        }
        for(int i = 0; i < 32; i++){
            counts[i] %= 3;
        }
        int res = 0;
        for(int i = 31; i >= 0; i--){
            if(counts[i] != 0){
                res++;
            }
            if(i > 0){
                res <<= 1;
            }        
        }
        return res;
    }
}
~~~

### 数学

#### 数组中出现次数超过一半的数字

Map解，效率极低

~~~java
class Solution {
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int limit = nums.length/2;
        for(int num: nums){
            map.put(num, map.getOrDefault(num, 0)+1);
            if(map.get(num) > limit){
                return num;
            }
        }      
        return -1;
    }  
}
~~~

数学法，由于众数次数超过一半，排序后中位数一定是众数

~~~java
class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }  
}
~~~

#### 构建乘积数组

先计算总乘积，在当前下标时除以当前元素得当前乘积，注意处理0

- 一个0，除0位之外全为0
- 多于一个0，全为0

~~~java
class Solution {
    public int[] constructArr(int[] a) {
        int product = 1, n = a.length, count = 0, index = -1;
        for(int i = 0; i < n; i++){
            int num = a[i];
            if(num == 0){
                if(count++ == 0){
                    index = i;
                }
                continue;
            }
            product *= num;
        }
        int[] res = new int[n];
        if(count >= 2){
            return res;
        }
        if(count == 1){
            res[index] = product;
            return res;
        }
        for(int i = 0; i < n; i++){
            res[i] = product/a[i];
        }
        return res;
    }
}
~~~

#### 剪绳子

是这样，已知绳子长度为`n`，设分成`a`段，每段长度为`x`，已知有`n = a*x`

我们要求的是乘积，设为`y`，有`y = x^a`代换掉`a`，得`y = x^(n/x)`，`n`为常数，于是得到`y`关于`x`的函数

- 求对数 
- 求导
- 移项，代换
- 令为0，求极值

由于`x`必为整数，极大值为`e`，代入`2、3`，`f(3)>f(2)`，故当`x=3`时原函数取最大值

~~~java
class Solution {
    public int cuttingRope(int n) {
        if(n <= 3){
            return n-1;
        }
        int a = n/3, b = n%3;
        if(b == 0){
            return (int)Math.pow(3, a);
        } else if(b == 1){
            return (int)Math.pow(3, a-1)*4;
        } else if(b == 2){
            return (int)Math.pow(3, a)*2;
        }
        return -1;
    }
}
~~~

虚假的动态规划，这里单独提出`4、5、6`的原因是这样的：以`7`为分界线，左边的元素分成两部分的乘积大于分成三部分的乘积，右边则小于，规则并不一样

~~~java
class Solution {
    public int cuttingRope(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i <= n; i++){
            if(i == 4){ dp[i] = 4; continue; }
            if(i == 5){ dp[i] = 6; continue; }
            if(i == 6){ dp[i] = 9; continue; }
            for(int j = 0; j < i; j++){
                dp[i] = Math.max(dp[i], dp[j]*(i-j));
            }
        }
        return dp[n];
    }
}
~~~

#### 和为s的连续正数序列

虚假的动态规划，脑子抽了才这么写

~~~java
class Solution {

    public int[][] findContinuousSequence(int target) {
        List<List<Integer>> res = new ArrayList<>();
        int n = target/2+2;
        int[][] dp = new int[n][n];
        for(int i = 1; i < n; i++){
            dp[i][i] = i;
        }
        for(int i = 1; i < n; i++){
            for(int j = i+1; j < n; j++){
                dp[i][j] = dp[i][j-1]+j;
            }           
        }
        for(int i = 0; i < n; i++){
            for(int j = i+1; j < n; j++){
                if(dp[i][j] > target){
                    continue;
                }
                if(dp[i][j] == target){
                    res.add(getRow(i, j));
                    continue;
                }
            }
        }
        return listToArray(res);
    }

    public int[][] listToArray(List<List<Integer>> list){
        int n = list.size();
        int[][] arr = new int[n][];
        for(int i = 0; i < n; i++){
            List<Integer> row = list.get(i);
            int m = row.size();
            arr[i] = new int[m];
            for(int j = 0; j < m; j++){
                arr[i][j] = row.get(j);
            }

        }
        return arr;
    }

    public List<Integer> getRow(int left, int right){
        List<Integer> row = new ArrayList<>();
        for(int i = left; i <= right; i++){
            row.add(i);
        }
        return row;
    }

}
~~~

暴力枚举

~~~java
class Solution {

    public int[][] findContinuousSequence(int target) {
        List<List<Integer>> res = new ArrayList<>();
        int n = target/2+2;
        for(int i = 1; i < n-1; i++){
            int sum = i;
            List<Integer> row = new ArrayList<>();
            row.add(i);
            for(int j = i+1; j < n; j++){
                sum += j;
                row.add(j);
                if(sum > target){
                    break;
                } else if(sum == target){
                    res.add(row);
                    break;
                }
            }           
        }
        return listToArray(res);
    }

    public int[][] listToArray(List<List<Integer>> list){
        int n = list.size();
        int[][] arr = new int[n][];
        for(int i = 0; i < n; i++){
            List<Integer> row = list.get(i);
            int m = row.size();
            arr[i] = new int[m];
            for(int j = 0; j < m; j++){
                arr[i][j] = row.get(j);
            }

        }
        return arr;
    }

}
~~~

双指针，利用升序的规律，慢慢蠕动，是暴力枚举的一种优化

~~~java
class Solution {

    public int[][] findContinuousSequence(int target) {
        List<List<Integer>> res = new ArrayList<>();
        int left = 1, right = 2;
        while(left < right){
            int sum = (right+left)*(right-left+1)/2;
            if(sum == target){
                res.add(getRow(left, right));
                left++;
            } else if(sum > target){
                left++;
            } else {
                right++;
            }
        }
        return listToArray(res);
    }

    public List<Integer> getRow(int left, int right){
        List<Integer> row = new ArrayList<>();
        for(int i = left; i <= right; i++){
            row.add(i);
        }
        return row;
    }

    public int[][] listToArray(List<List<Integer>> list){
        int n = list.size();
        int[][] arr = new int[n][];
        for(int i = 0; i < n; i++){
            List<Integer> row = list.get(i);
            int m = row.size();
            arr[i] = new int[m];
            for(int j = 0; j < m; j++){
                arr[i][j] = row.get(j);
            }

        }
        return arr;
    }
}
~~~

#### 圆圈中最后剩下的数字

> 约瑟夫环问题，每次重新编号对原有下标减去`k`，然后开始新一轮删除，找答案的过程实际上是一个还原下标的问题

迭代

~~~java
class Solution {
    public int lastRemaining(int n, int m) {
        int res = 0;
        for(int i = 2; i <= n; i++){
        	res = (res+m) % i;
        }
        return res;
    }
}
~~~

递归

~~~java
class Solution {
    public int lastRemaining(int n, int m) {
        int res = recur(n, m);
        return res;
    }

    public int recur(int n, int m){
        if(n == 1){
            return 0;
        }
        int x = recur(n-1, m);
        return (x+m)%n;
    }
}
~~~

### 模拟

#### 顺时针打印矩阵

模拟打印过程即可，要注意最后只剩一条时（有可能竖着，有可能横着），若全遍历完会添加重复，所以在中间要判断一下，若为一条，一去后就退出，不要一去一来

~~~java
class Solution {
    public int[] spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return new int[]{};
        }
        int left = 0, right = matrix[0].length-1, top = 0, bottom = matrix.length-1;
        while(left <= right && top <= bottom){
            for(int i = left; i <= right; i++){
                list.add(matrix[top][i]);
            }
            for(int i = top+1; i <= bottom; i++){
                list.add(matrix[i][right]);
            }
            if(top == bottom || left == right) {
            	break;
            }
            for(int i = right-1; i >= left; i--){
                list.add(matrix[bottom][i]);
            }
            for(int i = bottom-1; i > top; i--){
                list.add(matrix[i][left]);
            }
            left++; right--; top++; bottom--;
        }
        return toArray(list);
    }

    public int[] toArray(List<Integer> list){
        int n = list.size();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = list.get(i);
        }
        return arr;
    }
}
~~~

#### 栈的压入、弹出序列

模拟动态的压栈弹栈过程

~~~java
class Solution {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int pos = 0, n = pushed.length;
        for(int i = 0; i < n; i++){
            if(pushed[i] == popped[pos]){
                pos++;
            } else if(!stack.isEmpty() && stack.peek() == popped[pos]){
                stack.pop();
                pos++;
                i--;
            } else {
                stack.push(pushed[i]);
            }
        }
        for(int i = pos; i < n; i++){
            if(stack.pop() != popped[i]){
                return false;
            }
        }
        return true;
    }
}
~~~

### 字符串

#### 表示数值的字符串*

歪门邪道，应该用有限自动机的办法解，不会捏

~~~java
class Solution {
    public boolean isNumber(String s) {
        s = s.trim();
        try{
            double res = Double.parseDouble(s);
        } catch (Exception e){
            return false;
        }
        if(s.charAt(s.length()-1) != '.' && !Character.isDigit(s.charAt(s.length()-1))){
            return false;
        }
        return true;        
    }
}
~~~

#### 把字符串转换成整数

- 符号问题
- 范围问题

~~~java
class Solution {
    public int strToInt(String str) {
        str = str.trim();
        char[] arr = str.toCharArray();
        int n = arr.length;
        if(n == 0 || (arr[0] != '-' && arr[0] != '+' && !Character.isDigit(arr[0]))){
            return 0;
        }
        boolean flag = false;
        long res = 0;
        for(int i = 0; i < n; i++){
            char cur = arr[i];
            if(i==0 && cur=='-'){
                flag = true;
                continue;
            } else if(i==0 && cur=='+'){
                continue;
            }
            if(!Character.isDigit(cur)){
                break;
            }
            res = res*10 + (cur-'0');
            if(flag && -res < Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
            if(!flag && res > Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }
        }
        res = flag ? -res:res;
        return (int)res;
    }
}
~~~

