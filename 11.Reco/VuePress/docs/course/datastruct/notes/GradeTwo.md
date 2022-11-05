# 大二刷题笔记

## 一、栈与递归

### 1、棒球比赛（682）

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

### 2、消除游戏（390）

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

### 3、简化路径（71）

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

### 4、括号的最大嵌套深度（1614）

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

### 5、回文数（9）

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

## 二、链表

### 1、两数相加（2）

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

### 2、链表随机节点（382）

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



## 三、树

### 1、奇偶树（1609）

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

### 2、实现Trie(前缀树)（208）

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

### 3、连接词（472）

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

## 四、顺序表

### 1、Bigram分词（1078）

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

### 2、适龄的朋友（825）

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

### 3、一维数组转二维数组（2022）

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

### 4、替换所有的问号（1576）

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

### 5、按键持续时间最长的键（1629）

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

### 6、累加数（306）

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

### 7、寻找两个正序数组的中位数

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

### 8、递增的三元子序列（334）

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

### 9、搜索旋转排序数组（33）

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

## 五、统计

### 1、统计特殊四元组（1995）

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

### 2、一手顺子（846）

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

### 3、完美数（507）

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

### 4、最长递增子序列的个数（673）

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

### 6、平方数之和（633）

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

### 7、统计元音字母序列的数目（1220）

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

### 8、存在重复元素Ⅱ（219）

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



## 六、图

> 图与递归，深度优先搜索

### 1、猫和老鼠（913）

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

## 七、数与位

### 1、最大回文数乘积（479）

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

### 2、计算力扣银行的钱（1716）

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

### 3、寻找最近的回文数

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

### 4、2的幂（231）

#### 1、蠢逼解法

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

#### 2、取巧解法

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

#### 3、正儿八经解法

假设n为2的倍数，n的二进制只有最高位为1，其余位皆为0，而n-1的二进制比n低一位，且所有位皆为1，对二者进行与运算`&`，必得0

~~~java
class Solution {
    public boolean isPowerOfTwo(int n) {
        return n>0 && (n&(n-1))==0;
    }
}
~~~

### 5、4的幂（342）

#### 1、蠢逼解法

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

#### 2、位解法

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

### 6、3的幂（326）

3的幂的二进制并没有什么特点，所以只能采用蠢逼解法和取巧解法进行解题，以下是取巧解法

~~~java
class Solution {

    public boolean isPowerOfThree(int n) {
        int MAX = (int)Math.pow(3, 19);
        return n>0 && MAX%n==0;
    }
}
~~~

### 7、七进制数（504）

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

### 8、丑数

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

### 9、颠倒二进制位（190）

#### 1、依次颠倒赋值

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

#### 2、位分治

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

#### 3、jdk

底层仍使用的位分治

~~~java
public class Solution {
    public int reverseBits(int n) {
        return Integer.reverse(n);
    }
}
~~~

### 10、UTF-8编码验证（393）

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

### 11、灯泡开关（319）

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

### 12、位1的个数（191）

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

### 13、数字的补数（476）

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

### 14、汉明距离（461）

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

### 15、汉明距离总和（477）

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

### 16、交替位二进制数（693）

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

### 17、可怜的小猪（458）

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

### 18、各位相加（258）

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

### 19、最接近的三数之和（16）

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

## 八、贪心算法

### 1、种花问题（605）

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

## 九、双指针

### 1、盛最多水的容器（11）

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

