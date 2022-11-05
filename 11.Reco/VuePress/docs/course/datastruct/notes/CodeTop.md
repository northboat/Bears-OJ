# CodeTop

## 一、容易

### 1、反转链表（206）

> 画图！手画链表

- 迭代法

~~~c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* reverseList(ListNode* head) {
        ListNode* pre = nullptr;
        ListNode* cur = head;
        while(cur!=nullptr){
            ListNode* next = cur->next;
            cur->next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
};
~~~

- 递归法

~~~c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
class Solution {
public:
    ListNode* reverseList(ListNode* head) {
        //防止头节点为空以及遍历到链表尾部
        if(head==nullptr || head->next==nullptr){
            return head;
        }
        //保证程序“从后往前”运行，在“归”的过程中实现业务
        ListNode* newHead = reverseList(head->next);
        n newHead;
    }
};
~~~

### 2、两数之和（1）

> 暴力：贪婪算法

~~~java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++){
            for(int j = i+1; j < nums.length; j++){
                if(nums[i]+nums[j] == target){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
~~~

> 利用 HashMap，效率更高

~~~java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]), i};
            }else{
                map.put(nums[i], i);
            }
        }
        return null;
    }
}
~~~

### 3、合并两个有序链表（21）

> 边比较边构造链表

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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p = new ListNode();
        ListNode head = p;
        while(l1!=null && l2!=null){
            if(l1.val < l2.val){
                p.next = l1;
                p = p.next;
                l1 = l1.next;
            }else{
                p.next = l2;
                p = p.next;
                l2 = l2.next;
            }
        }
        while(l1!=null){
            p.next = l1;
            p = p.next;
            l1 = l1.next;
        }
        while(l2!=null){
            p.next = l2;
            p = p.next;
            l2 = l2.next;
        }
        p.next = null;
        return head.next;
    }
}
~~~

### 4、最大子序和（51）

> dp：动态规划，记录已有和状态，小于零舍弃，大于零继续
>
> 返回记录中的最大和

~~~java
class Solution {
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        for(int i = 1; i < n; i++){
            if(dp[i-1] <= 0){
                dp[i] = nums[i];
            }else{
                dp[i] = dp[i-1] + nums[i];
            }
        }
        Arrays.sort(dp);
        return dp[n-1];
    }
}
~~~

### 5、合并两个有序数组（88）

> 归并排序最后一步，即 “Join”

~~~java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = 0, j = 0, count = 0;
        int[] temp = new int[m+n];
        while(i<m && j<n){
            if(nums1[i] < nums2[j]){
                temp[count++] = nums1[i++];
            }else{
                temp[count++] = nums2[j++];
            }
        }
        while(i<m){
            temp[count++] = nums1[i++];
        }
        while(j<n){
            temp[count++] = nums2[j++];
        }
        for(int k = 0; k < count; k++){
            nums1[k] = temp[k];
        }
    }
}
~~~

### 6、字符串相加（415）

> 模拟加法过程，用布尔变量 flag 记录进位情况

~~~java
class Solution {
    public String addStrings(String num1, String num2) {
        StringBuffer res = new StringBuffer();
        int n1 = num1.length();
        int n2 = num2.length();
        int i = n1-1, j = n2-1;
        boolean flag = false;
        while(i>=0 || j>=0){
            int c1 = i>=0?num1.charAt(i--)-'0':0;
            int c2 = j>=0?num2.charAt(j--)-'0':0;
            int c = c1+c2;
            if(flag){
                c++;
            }
            res.append(c%10);
            if(c>=10){
                flag = true;
            }else{
                flag = false;
            }
        }
        if(flag){
            res.append(1);
        }
        //记得倒位
        return res.reverse().toString();
    }
}
~~~

### 7、买卖股票的最佳时机（121）

> 暴力解法：贪婪 ——> 超时

~~~java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int profit = 0;
        for(int i = 0; i < n-1; i++){
            for(int j = i+1; j < n; j++){
                if(prices[j]-prices[i] > profit){
                    profit = prices[j]-prices[i];
                }
            }
        }
        return profit;
    }
}
~~~

> 优化：边遍历边比较，动态更新最大利润

~~~java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int profit = 0;
        int pre = prices[0];
        for(int i = 1; i < n; i++){
            int cur = prices[i];
            if(cur-pre > 0 && cur-pre > profit){
                profit = cur-pre;
            }else if(cur < pre){
                pre = cur;
            }
        }
        return profit;
    }
}
~~~

### 8、相交链表（160）

> 暴力解法：贪婪，效率奇低

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode q = headA;
        while(q != null){
            ListNode p = headB;
            while(p != null){
                if(p == q){
                    return q;
                }
                p = p.next;
            }
            q = q.next;
        }
        return null;
    }
}
~~~

> 用哈希表储存节点，稍微优化

~~~java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet();
        ListNode p = headA;
        while(p != null){
            set.add(p);
            p = p.next;
        }
        p = headB;
        while(p != null){
            if(set.contains(p)){
                return p;
            }
            p = p.next;
        }
        return null;
    }
}
~~~

> 双指针法：效率奇高
>
> 
>
> 情况一：两个链表相交
>
> 链表 headA 和 headB 的长度分别是 m 和 n。假设链表 headA 的不相交部分有 a 个节点，链表 headB 的不相交部分有 b 个节点，两个链表相交的部分有 c 个节点，则有 a+c=m，b+c=n
>
> 1、如果 a = b，则两个指针会同时到达两个链表相交的节点，此时返回相交的节点；
>
> 2、若 a != b，则指针 pA 会遍历完链表 headA，指针 pB 会遍历完链表 headB，两个指针不会同时到达链表的尾节点，然后指针 pA 移到链表 headB 的头节点，指针 pB 移到链表 headA 的头节点，然后两个指针继续移动，在指针 pA 移动了 a+c+b 次、指针 pB 移动了 b+c+a 次之后，两个指针会同时到达两个链表相交的节点，该节点也是两个指针第一次同时指向的节点，此时返回相交的节点
>
> 
>
> 情况二：两个链表不相交
>
> 设链表 headA 和 headB 的长度分别是 m 和 n
>
> 1、如果 m = n，则两个指针会同时到达两个链表的尾节点，然后同时变成空值 null，此时返回 null；
>
> 2、如果 m != n，则由于两个链表没有公共节点，两个指针也不会同时到达两个链表的尾节点，因此两个指针都会遍历完两个链表，在指针 pA 移动了 m+n 次、指针 pB 移动了 n+m 次之后，两个指针会同时变成空值 null，此时返回 null

~~~java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
~~~

### 9、二叉树的中序遍历（94）

> 不多bb，深度优先搜索

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

    private List<Integer> res = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        if(root == null){
            return res;
        }
        inorderTraversal(root.left);
        res.add(root.val);
        inorderTraversal(root.right);
        return res;
    }
}
~~~

### 10、有效的括号（20）

> 栈的简单应用

~~~java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c: s.toCharArray()){
            if(c=='(' || c=='[' || c=='{'){
                stack.push(c);
            }else if(!stack.empty()){
                char e = stack.pop();
                if(e=='(' && c!=')'){
                    return false;
                }
                if(e=='[' && c!=']'){
                    return false;
                }
                if(e=='{' && c!='}'){
                    return false;
                }
            }else if(stack.empty()){
                return false;
            }
        }
        if(stack.empty()){
            return true;
        }
        return false;
    }
}
~~~

### 11、环形链表（141）

~~~java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        ListNode p = head;
        while(p != null){
            if(set.contains(p)){
                return true;
            }else{
                set.add(p);
            }
            p = p.next;
        }
        return false;
    }
}
~~~

### 12、x的平方根（69）

> 暴力解法
>
> 一定要注意转换 i*i 的类型，会超出 Integer.maxValue

~~~java
class Solution {
    public int mySqrt(int x) {
        for(int i = 1; i <= x/2+1; i++){
            if((long)i*i > x){
                return i-1;
            }
        }
        return 1;
    }
}
~~~

> 二分查找：有待研究

~~~java
class Solution {
    public int mySqrt(int x) {
        int res = 0, left = 0, right = x;
        while(left <= right){
            int mid = (left+right)/2;
            if((long)mid*mid <= x){
                res = mid;
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        return res;
    }
}
~~~

### 13、对称二叉树（101）

> 递归

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
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }else{
            return check(root.left, root.right);
        }
    }

    public boolean check(TreeNode q, TreeNode p){
        if(q == null && p == null){
            return true;
        }else if(q == null || p == null){
            return false;
        }else{
            return q.val == p.val && check(q.left, p.right) && check(q.right, p.left);
        } 
    }
}
~~~

### 14、路经总和（112）

> 深度优先搜索

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

> 广度优先搜索
>
> 用队列的数据结构保证二叉树一层一层向下推移，不停的 poll 和 offer

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



## 二、中等

### 1、无重复字符的最长子串（3）

> 窗口滑动，remove() 略去的字符
>

~~~java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int right = 0, n = s.length();
        int res = 0;
        HashSet<Character> set = new HashSet<>();
        for(int left = 0; left < n; left++){
            if(left!=0){
                set.remove(s.charAt(left-1));
            }
            while(right<n && !set.contains(s.charAt(right))){
                set.add(s.charAt(right++));
            }
            if(right-left > res){
                res = right-left;
            }
            if(right==n){
                break;
            }
        }
        return res;
    }
}
~~~

### 2、二叉树的层序遍历（102）

> 层序遍历（BFS）都可以借助队列这一数据结构辅助实现

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
            //对该层依次出列
            for(int i = 0; i < n; i++){           
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



### 3、重排链表（143）

> 1、找到链表的中间元素
>
> 2、反转后半段链表
>
> 3、合并两个链表
>
> 注意：一定要将前后两段链表打断，如将 (1,2,3,4) 中的 2 指向null，否则会形成闭环

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
    public void reorderList(ListNode head) {
        ListNode mid = midNode(head);  
        ListNode l2 = reverse(mid.next);
        //将链表打断使l1、l2完全独立（very important）
        mid.next = null;    
        ListNode l1 = head;       
        merge(l1, l2);
    }

    //找到链表的中间元素：(1,2,3,4)中的2，(1,2,3,4,6)中的3
    public ListNode midNode(ListNode head){
        ListNode slow = head, fast = head;
        while(fast.next!=null && fast.next.next!=null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //将链表反转，递归法（可用迭代法）
    public ListNode reverse(ListNode head){
        if(head==null || head.next==null){
            return head;
        }
        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    //交替合并两个链表，以 l1 为头
    public ListNode merge(ListNode l1, ListNode l2){
        ListNode head = l1;
        ListNode cur = head;
        ListNode c1 = l1.next;
        ListNode c2 = l2;
        while(c1!=null || c2!=null){
            cur.next = c2;
            c2 = c2.next;
            cur = cur.next;
            //中途再判断一次，防止空指针
            if(c1==null && c2==null){
                break;
            }
            cur.next = c1;
            c1 = c1.next;
            cur = cur.next;          
        }
        return head;
    }
}
~~~

### 4、链表求和（面试题 02.05）

- 递归

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
    private int carry = 0;

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1==null && l2==null && carry==0){
            return null;
        }
        int val = (l1!=null?l1.val:0) + (l2!=null?l2.val:0) + carry;
        if(val >= 10){
            carry = 1;
        }else{
            carry = 0;
        }
        ListNode node = new ListNode(val%10);
        if(l1!=null){
            l1 = l1.next;
        }
        if(l2!=null){
            l2 = l2.next;
        }
        node.next = addTwoNumbers(l1, l2);
        return node;
    }
}
~~~

### 5、数组中的第k个最大元素（215）

参考快速排序的思路，找到第 k 大的元素

- 每次划分将找到一个第 n 大的元素：其左均小于等于它，其右均大于它
- 若 n==k，直接返回，若 n>k，对其右元素继续划分，若 n<k，对其左元素继续划分，直到 n==k 或超出边界

~~~java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        return partition(nums, 0, nums.length-1, k);
    }

    private int partition(int[] nums, int left, int right, int k){
    	if(left>right) {
    		return -1;
    	}
        swap(nums, (left+right)/2, right);
        
        int cur = nums[right];
        int count = left;
        for(int i = left; i < right; i++){
            if(nums[i] <= cur){
                swap(nums, i, count);
                count++;
            }
        }
        swap(nums, count, right);  
        
        if(nums.length-count == k){
            return nums[count];
        }  

           
        if(nums.length-count>k) {
        	return partition(nums, count+1, right, k);
        }else {
        	return partition(nums, left, count-1, k);
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
~~~

### 6、二叉树的最近祖先（236）

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

### 7、螺旋矩阵（54）

> 模拟顺时针走向，细心
>
> while 中还要判断一次 ( left < right && top < bottom ) 是为了避免只剩最后一行或最后一列的情况，前两个 for 从上往下 add 了一遍，后两个 for 又从下往上 add 一遍

~~~java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        if(m==0 && n==0){
            return res;
        }
        int left = 0, right = n-1, top = 0, bottom = m-1;
        while(left <= right && top <= bottom){
            for(int i = left; i < right; i++){
                res.add(matrix[top][i]);
            }
            for(int j = top; j <= bottom; j++){
                res.add(matrix[j][right]);
            }
            if(left < right && top < bottom){
                for(int i = right-1; i >= left; i--){
                    res.add(matrix[bottom][i]);
                }
                for(int j = bottom-1; j > top; j--){
                    res.add(matrix[j][left]);
                }
            }
            top++;
            bottom--;
            left++;
            right--;
        }
        return res;
    }
}
~~~

### 8、反转链表Ⅱ（92）

> 1、记录需要反转部分的前驱和后继
>
> 2、将反转部分隔断（令尾节点为空），进行反转
>
> 3、连接前驱+反转后的链表+后继，返回新头节点

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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        int count = 1;
        ListNode pre = null;
        ListNode succ = null;
        ListNode cur = head;
        while(count <= right){
            if(count+1 == left){
                pre = cur;
            }
            if(count == right){
                succ = cur.next;
                cur.next = null;
                break;
            }
            count++;
            cur = cur.next;
        }
        ListNode newHead = null;
        if(pre != null){
            newHead = reverseList(pre.next);
            pre.next = newHead;
        }else{
            newHead = reverseList(head);
        }
        ListNode tail = newHead;
        while(tail.next != null){
            tail = tail.next;
        }
        tail.next = succ;
        if(pre != null){
            return head;
        }else{
            return newHead;
        }
    }

    public ListNode reverseList(ListNode head){
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

### 9、最长回文子串（5）

> dp：动态规划
>
> 动态记录子串状态：是否回文
>
> 状态转移方程：
>
> ~~~java
> while(len > 2 && s.charAt(i)==s.charAt(j)){
>     dp[i][j] = dp[i+1][j-1];
> }
> //即当 s.charAt(i)==s.charAt(j)时，s.substring(i, j) 的回文状态与 s.substring(i+1, j-1) 保持一致
> ~~~

~~~java
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for(int i = 0; i < n; i++){
            dp[i][i] = true;
        }
        int begin = 0, maxLen = 1;
        for(int len = 2; len <= n; len++){
            for(int i = 0; i <= n-len; i++){
                int j = i+len-1;
                char c1 = s.charAt(i);
                char c2 = s.charAt(j);
                if(c1 == c2){
                    if(len == 2){
                        dp[i][j] = true;
                    }else{
                        dp[i][j] = dp[i+1][j-1];
                    }
                }else{
                    dp[i][j] = false;
                }

                if(dp[i][j] && len>maxLen){
                    begin = i;
                    maxLen = len;
                }
            }
        }

        return s.substring(begin, begin+maxLen);
    }
}
~~~

### 10、三数之和（15）

> 双指针
>
> 1、首先将数组排序，除了 0+0+0 的情况，若三数之和为 0 的话，三数必不相同，如此在遍历时，筛掉相同元素向后移可以避免答案重复
>
> 2、按照（1）的思路，三重循环将整个数组遍历一定能找到所有不同的和为 0 的三数组合，这里用到双指针优化
>
> 3、可以发现，因为升序排列，nums[second] <= nums[third]，若当前 sum > 0，当继续往后遍历时，新的 nums[second] 必大于等于当前 nums[second]，sum 只会更大，所以这里只有将 third-1，才有可能出现三数和为 0，如此第二层和第三次循环处于并列关系
>
> 4、而 second 和 third 自然作为前后指针帮助我们找到适合的三数

~~~java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for(int first = 0; first < n; first++){
            //当最小数都大于0，必不存在符合的三数，直接退出遍历
            if(nums[first] > 0){
                break;
            }
            //略过相同的 nums[first]
            if(first > 0 && nums[first] == nums[first-1]){
                continue;
            }
            //后指针
            int third = n-1;
            //前指针
            for(int second = first+1; second <= third; second++){
                //略过相同的 nums[second]
                if(second > first+1 && nums[second] == nums[second-1]){
                    continue;
                }
                //当三数之和大于0，向前移动后指针，并保证后指针大于等于前指针
                while(third > second && nums[first] + nums[second] + nums[third] > 0){
                    third--;
                }
                //当前后指针重合，说明当前数组中三数之和必大于0，退出当前循环，重置指针
                if(second == third){
                    break;
                }
                //若三数之和为0，储存
                if(nums[first] + nums[second] + nums[third] == 0){
                    res.add(Arrays.asList(nums[first], nums[second], nums[third]));
                }
            }
        }
        return res;
    }
}
~~~

### 11、LRU缓存机制（146）

> 哈希表+队列：效率奇低

~~~java
public class LRUCache {

    private int capacity;
    private Queue<Integer> queue = new LinkedList<>();
    private HashMap<Integer, Integer> map = new HashMap<>();

    public LRUCache2(int capacity){
        this.capacity = capacity;
    }

    public void put(int key, int value){
        if(queue.size() > capacity){
            map.remove(queue.poll());
        }
        if(queue.contains(key)){
            queue.remove(key);
            queue.offer(key);
        }
        map.put(key, value);
    }

    public int get(int key){
        if(!queue.contains(key)){
            return -1;
        }
        if(queue.contains(key)){
            queue.remove(key);
            queue.offer(key);
        }
        return map.get(key);
    }
}
~~~

> 哈希表+手写双向链表

~~~java
class DLinkedNode{
        private int key;
        private int value;
        DLinkedNode prev;
        DLinkedNode next;

        public int getValue() {
            return value;
        }

        public DLinkedNode(){
        }

        public DLinkedNode(int k, int v){
            key = k;
            value = v;
        }

        public int getKey() {
            return key;
        }
    }

    private int size;
    private int capacity;
    private HashMap<Integer, DLinkedNode> map = new HashMap<>();
    DLinkedNode head;
    DLinkedNode tail;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(map.get(key)==null){
            return -1;
        }
        int value = map.get(key).getValue();
        update(key, value);
        return value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            update(key, value);
            return;
        }
        offer(new DLinkedNode(key, value));
        if(size > capacity){
            poll();
        }
    }

    public void offer(DLinkedNode node){
        insert(node);
        map.put(node.getKey(), node);
    }

    public void insert(DLinkedNode newNode){
        DLinkedNode temp = head.next;
        head.next = newNode;
        newNode.prev = head;
        newNode.next = temp;
        temp.prev = newNode;
        size++;
    }

    public void remove(DLinkedNode node){
        DLinkedNode prev = node.prev;
        DLinkedNode next = node.next;
        prev.next = next;
        next.prev = prev;
        size--;
    }

    public void poll(){
        map.remove(tail.prev.getKey());
        remove(tail.prev);
    }

    public void update(int key, int value){
        DLinkedNode node = map.get(key);
        remove(node);
        DLinkedNode newNode = new DLinkedNode(key, value);
        insert(newNode);
        map.put(key,newNode);
    }
~~~



## 三、困难

### 1、接雨水（42）

> 单调栈的运用
>
> 当小于等于 stack.peek() 时入栈，反之弹出

~~~java
class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        int left = 0;
        for(int i = 0; i < height.length; i++){
            int right = height[i];
            if(stack.empty()) {
            	if(right == 0) {
            		continue;
            	}
            	else {
            		left = right;
            		stack.push(left);          		
            	}
            }
            else {
            	if(right <= stack.peek()) {
            		stack.push(right);
            	}
            	else {
            		int temp = stack.peek();
            		if(right >= left) {
            			while(!stack.empty() && temp < right) {
            				int deep = left-temp;
            				res += deep;
            				stack.pop();
            				if(!stack.empty()) {
            					temp = stack.peek();
            				}
            			}
            		}
            		else {
            			int count = 0;
            			while(temp < right) {
            				int deep = right-temp;
            				res += deep;
            				count++;
            				stack.pop();
            				temp = stack.peek();
            			}
            			for(int j = 0; j < count; j++) {
            				stack.push(right);
            			}
            		}
            		if(stack.empty()) {
            			left = right;
            			stack.push(left);
            		}else {
            			stack.push(right);
            		}          		
            	}
            }
        }
        return res;
    }
}
~~~

### 2、合并K个升序链表（23）

> 从合并两个链表推广到合并K个：顺序合并
>
> 朴素的思想

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
    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        if(n==0){
            return null;
        }
        ListNode cur = lists[0];
        for(int i = 1; i < n; i++){
            cur = mergeTwoList(cur, lists[i]);
        }
        return cur;
    }

    public ListNode mergeTwoList(ListNode l1, ListNode l2){
        ListNode head = new ListNode();
        ListNode p = head;
        while(l1!=null && l2!=null){
            if(l1.val < l2.val){
                p.next = l1;
                p = p.next;
                l1 = l1.next;
            }else{
                p.next = l2;
                p = p.next;
                l2 = l2.next;
            }
        }
        while(l1 != null){
            p.next = l1;
            p = p.next;
            l1 = l1.next;
        }
        while(l2 != null){
            p.next = l2;
            p = p.next;
            l2 = l2.next;
        }
        p.next = null;
        return head.next;
    }
}
~~~

> Fork/Join：分治合并

