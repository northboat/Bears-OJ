# 剑指Offer

## 一、栈与队列

### 1、用两个栈实现队列

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

### 2、包含min函数的栈

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

### 59、滑动窗口的最大值

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

### 60、队列的最大值

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



## 二、链表

### 3、从尾到头打印链表

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

### 4、反转链表

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

### 5、复杂链表的复制

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

## 三、字符串

### 6、替换空格

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

### 7、左旋转字符串

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

## 四、查找算法

### 8、数组中的重复数字

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

### [9]、在排序数组中查找数字Ⅰ

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

### 10、0~n-1中缺失的数字

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

### 11、二维数组中的查找

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

### 12、第一个只出现一次的字符

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

## 五、搜索与回溯算法

### 13、从上到下打印二叉树 Ⅰ

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

### [14]、从上到下打印二叉树Ⅱ

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

### 15、从上到下打印二叉树Ⅲ

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



### [16]、树的子结构

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

### [17]、二叉树的镜像

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

### [18]、对称二叉树

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



### [30]、矩阵中的路径

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

### [31]、机器人的运动范围

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

### [32]、二叉树中和为某一值的路径

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

### [33]、二叉搜索树与双向链表

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

### 34、二叉搜索树的第k大节点

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

### 39、二叉树的深度

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

### [39]、平衡二叉树

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

### 40、求1+2+3+...+n

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

### [41]、二叉搜索树的最近公共祖先

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

### 42、二叉树的最近公共祖先

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

### [61]、序列化二叉树

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

### [62]、字符串的排列

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



## 六、动态规划

### 19、斐波那契数列

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

### 20、青蛙跳台阶问题

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

### 21、股票的最大利润

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

### 22、礼物的最大价值

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

### 23、最长不含重复字符的子串

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

### 24、把数字翻译成字符串

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

### 63、最长公共子序列

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

### 64、丑数

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

### 64、正则表达式匹配

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



## 七、双指针

 ### 25、删除链表的节点

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

### 26、链表的倒数第k个节点

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

### 27、调整数组顺序使奇数位于偶数前面

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

### 28、剑指 Offer 57：和为s的两个数字

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

### [29]、翻转单词

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



## 八、排序

### 35、把数组排成最小的数

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

### 36、扑克牌中的顺子

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

### 37、最小的k个数

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

### 38、数据流中的中位数

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

## 九、分治算法

### [43]、重建二叉树

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



### 44、数值的整数次方

> 快速幂：
>
> ~~~java
> int x = 2;
> int n = 10; // 10 == 1010
> /*x^n == (x^1*0) * (x^2*1) * (x^4*0) * (x^8*0)
>       == x^2 * x^8
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

### 45、二叉搜索树的后序遍历序列

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

## 十、位运算

### 46、二进制中1的个数

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

### [47]、不用加减乘除做加法

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

### [48]、数组中数字出现的次数Ⅰ

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

### [49]、数组中数字出现的次数Ⅱ

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

## 十一、数学

### 50、数组中出现次数超过一半的数字

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

### 51、构建乘积数组

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

### 52、剪绳子

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

### 53、和为s的连续正数序列

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

### 54、圆圈中最后剩下的数字

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

## 十二、模拟

### 55、顺时针打印矩阵

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

### 56、栈的压入、弹出序列

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

## 十三、字符串

### [57]*、表示数值的字符串

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

### 58、把字符串转换成整数

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
