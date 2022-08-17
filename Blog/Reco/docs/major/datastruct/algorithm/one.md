---
title: LeetCode in Grade One
date: 2021-4-7
tags:
  - Algorithm
---

## LeetCode

### 数组

#### 原地删除有序数组中的重复元素（26）

~~~java
/*
双指针，初始使两指针指向第二个元素，快指针不断向后移，当前驱和自身不同时，将自身值赋给慢指针，同时使慢指针指向后继
*/  
class Solution {
    public int removeDuplicates(int[] nums) {
        //当数组无元素时直接返回，不然会把0赋给nums[0]
        if(nums.length == 0){
	  		return 0;
        }
        int i = 1, j = 1;
		while(j < nums.length) {
            //当前后不一样，把不一样的赋给慢指针，从前往后覆盖
            //慢指针始终在快指针之前
			if(nums[j-1]!=nums[j]) {
				nums[i] = nums[j];
				i++;
			}
			j++;
		}
		return i;
    }
}

//c++中调用vector.size()函数获得容器长度
~~~

#### 找到一个数组中最多连续的1（485）

~~~java
//数组中之存在0和1
class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {

        int maxCount = 0, count = 0;
		for(int i = 0; i < nums.length; i++) {
			if(nums[i]==1) {
				count++;
			}
			else {
				if(count>maxCount) {
					maxCount = count;
				}
                //碰到0时，清空计数器
				count = 0;
			}
		}
        //当最后一次计数大于之前记录的最大个数，重新赋值
        if(count > maxCount){
            maxCount = count;
        }
		
		return maxCount;
    }
}
~~~

#### 提莫攻击（495）

```java
class Solution {
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int cuttime = 0;
		for(int i = 0; i < timeSeries.length-1; i++) {
			if(timeSeries[i+1]-timeSeries[i] < duration) {
                //累计计算重复时间
				cuttime+=duration-(timeSeries[i+1]-timeSeries[i]);
			}
		}
		int time = duration*timeSeries.length-cuttime;
		return time;
    }
}
//这个故事告诉我们，重复控制乃抓人大忌
```

#### 移除指定元素（27）

~~~java
class Solution {
    public int removeElement(int[] nums, int val) {
        int rear;
		int front = 0;
        //与26题相同，利用快指针遍历数组
        //将不等于val的值赋给慢指针，并将慢指针向后蠕动
		for(rear = 0; rear < nums.length; rear++) {
			if(nums[rear] != val) {
				nums[front] = nums[rear];
				front++;
			}
		}
		return front;
    }
}
~~~

#### 第三大的数（414）

~~~java
import java.util.TreeSet;
import java.util.Iterator;

public class ThirdMax {
	
    //法1
	//手动冒泡排序，返回倒数第三个与最后一个元素不同的数
	public int thirdMax1(int[] nums) {
		
		if(nums.length == 1) {
			return nums[0];
		}
		for(int i = 0; i < nums.length; i++) {
			for(int j = 0; j < nums.length-i-1; j++) {
				if(nums[j+1] < nums[j]) {
					int t = nums[j];
					nums[j] = nums[j+1];
					nums[j+1] = t;
				}
			}
		}
		if(nums.length < 3) {
			return nums[nums.length-1];
		}
        int res = nums[nums.length-1], count = 1;
        for(int i = nums.length-1; i >= 0; i--) {
        	if(nums[i] != res) {
        		res = nums[i];
        		count++;
        	}
        	if(count == 3) {
        		return res;
        	}
        }
        
		return nums[nums.length-1];
	}
    //法2
	//用TreeSet自动排序，返回倒数第三个数，若少于三个则返回最后一个数
	public int thirdMax2(int[] nums) {
		
		int res = -1;
		TreeSet<Integer> t = new TreeSet<>();
		for(int i = 0; i < nums.length; i++) {
			t.add(nums[i]);
		}
		int n = t.size();
		if(n < 3) {
            //获取TreeSet中最后一个元素
			return t.last();
		}
		Iterator<Integer> it = t.iterator();
        //"<="很重要
		for(int i = 0; i <= n-3; i++) {
			res = it.next();
		}
		return res;
	}
~~~

#### 三个数的最大乘积（628）

~~~java
class Solution {
    public int maximumProduct(int[] nums) {
        if(nums.length == 3){
            return nums[0]*nums[1]*nums[2];
        }
        int n = nums.length-1;
        int res;
        Arrays.sort(nums);
        int a = nums[n]*nums[n-1]*nums[n-2];
        int b = nums[0]*nums[1]*nums[n];
        return a > Math.max(b,c) ? a : Math.max(b,c);
    }
}
//先对数组升序排序，三个数的最大乘积只有可能存在与（最大的三个正数）、（最小的两个负数和最大正数）两种情况之间
~~~

#### 错误的集合（645）

~~~java
/**
* nums中储存了1到n的整数，其中有一个数重复（一个数缺失）
* 要求返回重复的数和缺失的数
* 暴力解法
*/

class Solution {
    public int[] findErrorNums(int[] nums) {
        int m = 0, n = 0;
		for(int i = 1; i <= nums.length; i++) {
			int count = 0;
			for(int j = 0; j < nums.length; j++) {
				if(nums[j] == i) {
					count++;
				}
			}
            if(count == 2) {
				m = i;
			}
			else if(count == 0) {
				n = i;
			}
		}
		return new int[] {m, n};
    }
}
~~~

#### 最大子序和（53）

动态规划

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

#### 数组的度（697）

~~~java
import java.util.HashMap;
import java.util.Map;

public class FindShortestSubArray {
	//纯暴力解法：超时
	public int findShortestSubArray1(int[] nums) {
		//找到数组的度
        int n = nums.length;
        int count = 0;
        for(int i = 0; i < n; i++){
            int t = 0;
            for(int j = 0; j < n; j++){
                if(nums[i] == nums[j]){
                    t++;
                }
            }
            if(t > count){
                count = t;
            }
        }
        int res = n;
        for(int i = 0; i < n; i++){
            int t = 0;
            int e = 0;
            for(int j = 0; j < n; j++){
                if(nums[i] == nums[j] && t == 0){
                    e = i > j ? j : i;
                }
                if(nums[i] == nums[j]){
                    t++;
                }
                if(t == count && j-e+1 < res){
                    res = j-e+1;
                    break;
                }
            }
        }
        return res;
    }
	
	//哈希表解法
	public int findShortestSubArray2(int[] nums) {

		int n = nums.length;
		//map中键对应数组元素的值，值（数组）分别储存该元素的出现次数、起始位置、当前位置
		Map<Integer, int[]> m = new HashMap<>();
		for(int i = 0; i < n; i++) {
			//当map中包含该元素，次数+1，更新当前位置
			if(m.containsKey(nums[i])) {
				m.get(nums[i])[0]++;
				m.get(nums[i])[2] = i;
			}
			//当map中不包含该元素，储存该元素键、出现次数、起始位置、当前位置（此时起始==当前）
			else {
				m.put(nums[i], new int[] {1, i, i});
			}
		}
		//找出nums的度
		int dp = 0;
		for(int num: nums) {
			if(m.get(num)[0] > dp) {
				dp = m.get(num)[0];
			}
		}
		int res = n;
		for(int num: nums) {
			if(m.get(num)[0] == dp && m.get(num)[2]-m.get(num)[1] < res) {
				res = m.get(num)[2]-m.get(num)[1]+1;
			}
		}		
		return res;
	}
	
	
	public static void main(String[] args) {
		FindShortestSubArray fss = new FindShortestSubArray();
		int[] nums = {1,2,2,3,1};
		System.out.println(fss.findShortestSubArray2(nums));
	}
}

//暴力解法不可取
//用哈希表灵活储存元素对应的属性：如该题的元素出现次数、元素起末位置
~~~

#### 找到所有数组中消失的数字（448）

~~~java
class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        //用新数组储存旧数组中元素，将下标和原数组元素对应起来，重复的元素被覆盖，缺失的元素对应下标的新数组位置值为0
        //再遍历新数组，找出值为0的位置（下标），即缺失的元素
        int[] f = new int[n+1];
        for(int i = 0; i < n; i++){
            f[nums[i]] = nums[i];
        }
        for(int i = 1; i <= n; i++){
            if(f[i] != i){
                list.add(i);
            }
        }
        return list;
    }
}
~~~

#### 数据中重复的元素（442）

~~~java
class Solution {
    
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        Arrays.sort(nums);
        for(int i = 1; i < n; i++){
            if(nums[i] == nums[i-1]){
                list.add(nums[i]);
            }
        }
        return list;
    }
}
~~~

#### 缺失的第一个正数（41）

困难

~~~java
class Solution {
    
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> m = new HashMap<>();
        for(int i = 0; i < n; i++){
            m.put(nums[i], 1);
        }
        for(int j = 1; j <= n; j++){
        	if(m.containsKey(j) == false) {
        		return j;
        	}
        }
        return n+1;
    }
}

// Map用于快速查找
// 将出现过的整数以键的形式储存在map中，再逐步查找1到n的整数，即是否存在对应的key(containsKey(key))
~~~

#### H指数（274）

不是很懂

~~~java
//对引用数升序排列
//从大到小遍历引用数，当文章数i大于等于引用数cintations[n-i-1]时，退出循环，返回最大的H指数
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        Arrays.sort(citations);
        int i = 0;
        while(i < n && citations[n-i-1] > i) {
        	i++;
        }
        return i;
    }
}
~~~

#### 员工的重要性（690）

充满疑问

~~~java
/*
// Definition for Employee.
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
*/

class Solution {

    private Map<Integer, Employee> map = new HashMap<>();

    public int getImportance(List<Employee> employees, int id) {
        for(Employee e: employees){
            map.put(e.id, e);
        }
        int res = getVal(id);
        return res;
    }

    public int getVal(int id){
        Employee e = map.get(id);
        int total = e.importance;
        List<Integer> list = e.subordinates;
        for(int i: list){
            total += getVal(i);
        }
        return total;
    }

}
~~~

#### 非递减数列（665）

~~~java
//首先找到有几个逆序：0直接返回true，大于1直接返回false，等于1做下一步判断
//找到唯一的一个逆序位置，分别使其等于其前驱、后继，做两次判断，若其中一个数列非递减，则返回true（原因如下）
//对于{4,2,3}，令4等于其后继可顺利排序。而对于{5,7,1,8}，要令1等于其前驱才可顺利排序
class Solution {

    public boolean isSorted(int[] nums){
        int count = 0;
        for(int i = 1; i < nums.length; i++){
            if(nums[i-1] > nums[i]){
                count++;
            }
        }
        if(count == 0)
            return true;
        return false;
    }

    public boolean checkPossibility(int[] nums) {
        int n =nums.length;
        int count = 0;
        int t = 0;
        for(int i = 1; i <= n-1; i++){
            if(nums[i-1] > nums[i]){
                count++;
                t = i-1;
            }
        }
        if(count == 0)
            return true;
        if(count > 1) 
        	return false;
        if(count == 1){
            int temp = nums[t];
            nums[t] = nums[t+1];
            if(isSorted(nums))
                return true;
            nums[t] = temp;
            nums[t+1] = nums[t];
            if(isSorted(nums))
                return true;
        }
        return false;
    }
}
~~~

#### 移动零（283）

~~~java
//不改变非零元素相对位置
//每碰到零将元素逐一置换，直到把该零换到最后一位
//每换完一轮，对数组重新检查,直到检查完所有的0
class Solution {
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        //记录零的个数，确定检查次数
        int count = 0;
        for(int i = 0; i < n; i++){
            if(nums[i] == 0){
                count++;
            }
        }
        for(int i = 0; i < count; i++) {
        	for(int j = 0; j < n-1; j++) {
                //当碰到0，将其逐一置换到最后一位
        	    if(nums[j] == 0) {
        			int t = nums[j];
        			nums[j] = nums[j+1];
        			nums[j+1] = t;
        		}
            }
        }
    }
}
~~~

#### 整数反转（7）

~~~java
//被范围卡死，整数反转后很有可能超过int范围
//在最大范围/10的时候进行判断，进一步将可能无法储存
class Solution {
    public int reverse(int x) {
        int res = 0;
		while(x != 0) {
			int t = x%10;
			if(res > Integer.MAX_VALUE/10 || (res == Integer.MAX_VALUE/10 && t > 7)) {
				return 0;
			}
			if(res < Integer.MIN_VALUE/10 || (res == Integer.MIN_VALUE/10 && t < -8)){
                return 0;
            }
			res = res*10+t;
			x /= 10;
		}
		return res;
    }
}
~~~

#### 杨辉三角（118）

~~~java
import java.util.List;
import java.util.ArrayList;

//生成杨辉三角
//好好学好好看
public class Generate {
	public List<List<Integer>> generate(int numRows) {
        
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0; i < numRows; i++){
        	List<Integer> row = new ArrayList<>();
            for(int j = 0; j <= i; j++){
                if(j != i && j != 0 && i > 1){
                    row.add(list.get(i-1).get(j-1)+list.get(i-1).get(j));
                }
                else{
                    row.add(1);
                }
            }
            list.add(row);
        }
        return list;
    }
	
	public static void main(String[] args) {
		Generate g = new Generate();
		int x = 5;
		for(int i = 0; i < x; i++) {
			for(int j = 0; j <= i; j++) {
				System.out.print(g.generate(x).get(i).get(j));
			}
			System.out.println();
		}
	}
}
~~~

#### 杨辉三角Ⅱ（119）

~~~java
import java.util.List;
import java.util.ArrayList;

public class GetRow {
	public List<Integer> getRow(int rowIndex) {
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0; i <= rowIndex; i++) {
        	List<Integer> row = new ArrayList<>();
        	for(int j = 0; j <= i; j++) {
        		if(j != 0 && i > 1 && j != i) {
        			row.add(list.get(i-1).get(j-1) + list.get(i-1).get(j));
        		}
        		else {
        			row.add(1);
        		}
        	}
        	list.add(row);
        }
        return list.get(rowIndex);
    }
	
	
	public static void main(String[] args) {
		GetRow gr = new GetRow();
		int x = 3;
		System.out.print(gr.getRow(x));
	}
}
~~~

#### 图片平滑器（661）

~~~java
//如何减少代码的重复度！！
//不要习惯用if/else捕捉情况
public class ImageSmoother {
	public int[][] imageSmoother(int[][] img) {
		int R = img.length;
		int C = img[0].length;
        int[][] ans = new int[R][C];
        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++) {
                int count = 0;
                for (int nr = r-1; nr <= r+1; nr++)
                    for (int nc = c-1; nc <= c+1; nc++) {
                        if (nr >= 0 && nr < R && nc >= 0 && nc < C) {
                            ans[r][c] += img[nr][nc];
                            count++;
                        }
                    }
                ans[r][c] /= count;
            }
        
        return ans;
    }
	
	public static void main(String[] args) {
		ImageSmoother is = new ImageSmoother();
		int[][] nums = {{1,1,1}, {1,0,1},{1,1,1}};
		int n = nums.length;
		int[][] res = is.imageSmoother(nums);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(res[i][j] + " ");
			}
		}
	}
}
~~~

#### 数组异或操作（1486）

~~~java
//异或操作
//先将相应十进制数转为二进制
//对于两个二进制数，每位不同则该位1，相同则为0
//将结果重新转化为十进制，返回答案
class Solution {
    public int xorOperation(int n, int start) {
        int res = 0;
        for(int i = 0; i < n; i++){
            res ^= start + 2*i;
        }
        return res;
    }
}
~~~

#### 范围求和Ⅱ（598）

~~~java
public class MaxCount {
    //暴力解法：超出内存限制
	public int maxCount1(int m, int n, int[][] ops) {
        int res = 0;
        int[][] nums = new int[m][n];
        int R = ops.length;
        for(int t = 0; t < R; t++) {
        	for(int i = 0; i < ops[t][0]; i++) {
        		for(int j = 0; j < ops[t][1]; j++) {
        			nums[i][t] += 1;
        		}
        	}
        }
        for(int i = 0; i < nums.length; i++) {
        	for(int j = 0; j < nums[0].length; j++) {
        		if(nums[i][j] == nums[0][0]) {
        			res += 1;
        		}
        	}
        }
        return res;
    }
	//找到最小范围右下角角标，相乘得面积
	public int maxCount2(int m, int n, int[][] ops) {
		int minR = ops[0][0];
		int minC = ops[0][1];
		for(int[] nums: ops) {
			if(nums[0] < minR) {
				minR = nums[0];
			}
			if(nums[1] < minC) {
				minC = nums[1];
			}
		}
		return minR*minC;
	}
}
~~~

#### 甲板上得战舰（419）

~~~java
//每当碰到X时，只需判断其左边或上方是否也为X
//是：则为某战舰的一部分，不计数
//否：则为某战舰的开头，计数
public class CountBattleships {
	public int countBattleships(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        int count = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
            	System.out.print(board[i][j] + " ");
                if(board[i][j] == 'X'){
                    if((i > 0 && board[i-1][j] == 'X') || (j > 0 && board[i][j-1] == 'X'))
                    		continue;
                    count++;
                }                
            }
            System.out.println();
        }

        return count;
    }
	
	public static void main(String[] args) {
		char[][] board = {{'X','.','.','X'},{'.','.','.','X'},{'.','.','.','X'}};
		CountBattleships cb = new CountBattleships();
		System.out.print(cb.countBattleships(board));
		
	}
}
~~~

#### 旋转数组（189）

~~~java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        int[] dist = new int[n];
        for(int i = 0; i < n; i++){
            //取余很精髓
            dist[(i+k)%n] = nums[i];
        }
        for(int j = 0; j < n; j++){
            nums[j] = dist[j];
        }
    }
}
~~~

#### 旋转函数（396）

~~~java
public class MaxRotateFunction {
	public int maxRotateFunction(int[] nums) {
        int n = nums.length;
        //定义max为最小值
        int max = Integer.MIN_VALUE, t = 0;
        int[] temp = new int[n];
        for(int i = 0; i < n; i++){
            //每层循环需要重置t值
        	t = 0;
            for(int j = 0; j < n; j++)
            {
                temp[j] = nums[(j+i)%n];
                t += j*temp[j];	
                System.out.print(temp[j] + " ");
            }
            System.out.println();
            if(t > max)
            {
            	max = t;
            }              
        }

        return max;
    }
	
	public static void main(String[] args) {
		MaxRotateFunction mrf = new MaxRotateFunction();
		int[] nums = {4,3,2,6};
		System.out.println(mrf.maxRotateFunction(nums));
	}
}

~~~

#### 螺旋矩阵（54）

~~~java
import java.util.ArrayList;
import java.util.List;


public class SpiralOrder {
	public List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        List<Integer> res = new ArrayList<>();
        if(matrix == null || n == 0 || m == 0){
            return res;
        }
        int left = 0, right = m-1, top = 0, bottom = n-1;
        while(left <= right && top <= bottom){
            for(int c = left; c <= right; c++) {
            	res.add(matrix[top][c]);
            }
            for(int r = top+1; r <= bottom; r++) {
            	res.add(matrix[r][right]);
            }
            //这里的条件判断很重要，不然会导致重复元素录入
            if(top < bottom && left < right) {
            	for(int c = right-1; c >= left; c--) {
                	res.add(matrix[bottom][c]);
                }
                for(int r = botton-1; r >= top+1; r--) {
                	res.add(matrix[r][left]);
                }	
            }
            left++;
            right--;
            top++;
            bottom--;
        }  
        return res;
    }
	
	public static void main(String[] args) {
		SpiralOrder so = new SpiralOrder();
		int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
		System.out.print(so.spiralOrder(matrix).toString());
	}
}
~~~

#### 螺旋矩阵Ⅱ（59）

~~~java
//与上一题同理，以螺旋的方式旋转矩阵
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int m = 1;
        int top = 0, botton = n-1, left = 0, right = n-1;
        while(top <= botton && left <= right){
            for(int c = left; c <= right; c++){
                matrix[top][c] = m++;
            }
            for(int r = top+1; r <= botton; r++){
                matrix[r][right] = m++;
            }
            if(top < botton && left < right){
                for(int c = right-1; c >= left; c--){
                    matrix[botton][c] = m++;
                }
                for(int r = botton-1; r >= top+1; r--){
                    matrix[r][left] = m++;
                }
            }
            top++;
            left++;
            right--;
            botton--;
        }
        return matrix;
    }
}
~~~

#### 对角线遍历（498）

有点难度

~~~java
import java.util.*;

public class FindDiagonalOrder {
	public int[] findDiagonalOrder(int[][] mat) {
        int M = mat.length;
        int N = mat[0].length;
        List<Integer> t = new ArrayList<>();
        int res[] = new int[M*N];
        int n = 0;
        //r+c为行列之和，以对角线的形式向右移，每移动一格，其和+1
        //当r+c=M+N时，即意味着行列到达了右下角，即遍历完毕
        //每次循环遍历两条对角线，这样解决了向上或是向下的方向问题，即每次循环上下各一次
        while(n <= M+N){
        	//对向上的对角线遍历
        	//令行数最大，列数最小，依次向右上方元素遍历，直到到达右端或是上端
            int r = n < M ? n : M-1;
            int c = n-r;
            while(r >= 0 && c < N) {
            	t.add(mat[r][c]);
            	r--;
            	c++;
            }
        	n++;
        	//判断是否到达右下角
        	if(n == M+N)
        		break;
        	//对向下的对角线遍历
        	//令列数最大，行数最小，依次向左下方遍历，直到到达左端或是下端
        	c = N > n ? n : N-1;
        	r = n-c;
        	while(c >= 0 && r < M) {
        		t.add(mat[r][c]);
        		c--;
        		r++;
        	}
        	n++;
        }
        for(int i = 0; i < M*N; i++) {
        	res[i] = t.get(i);
        }

        return res;
    }
	
	public static void main(String[] args) {
		FindDiagonalOrder fdo = new FindDiagonalOrder();
		int[][] mat = {{1,2,3}, {4,5,6}, {7,8,9}};
		int[] res = fdo.findDiagonalOrder(mat);
		for(int i = 0; i < 9; i++) {
			System.out.print(res[i] + " ");
		}
	}
}
~~~

#### 重塑矩阵（566）

~~~java
class Solution {
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length;
        int n = mat[0].length;
        if(r * c != m * n)
            return mat;
        int[][] res = new int[r][c];
        int[] t = new int[m*n];
        int count = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                t[count] = mat[i][j];
                count++;
            }
        }
        count = 0;
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                res[i][j] = t[count];
                count++;
            }
        }

        return res;
    }
}
~~~

#### 旋转图像（48）

~~~java
//经过分析发现，将矩阵旋转90°，相当于将第一行放在最后一列，第二行放在倒数第二列...以此类推
public class Rotate {
	public void rotate(int[][] matrix) {
        int n = matrix.length;
        int[][] t = new int[n][n];
        for(int c = n-1, i = 0; c >= 0; c--, i++) {
        	for(int r = 0; r < n; r++) {
        		t[r][c] = matrix[i][r];
        	}
        }
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < n; j++) {
        		matrix[i][j] = t[i][j];
        	}
        }
    }
	
	public static void main(String[] args) {
		int[][] matrix = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
		Rotate r = new Rotate();
		r.rotate(matrix);
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		
	}
}
~~~

#### 矩阵置零（73）

~~~java
//若使用hashmap
//当同一行出现两个0，后出现的0将把前出现的0所put进键值对中值给替换掉

public class SetZeroes {
	public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] column = new boolean[n];
        for(int i = 0; i < m; i++) {
        	for(int j = 0; j < n; j++) {
        		if(matrix[i][j] == 0) {
        			row[i] = true;
        			column[j] = true;
        		}
        	}
        }
        for(int i = 0; i < m; i++) {
        	if(row[i]) {
        		for(int j = 0; j < n; j++) {
        			matrix[i][j] = 0;
        		}
        	}
        }
        for(int j = 0; j < n; j++) {
        	if(column[j]) {
        		for(int i = 0; i < m; i++) {
        			matrix[i][j] = 0;
        		}
        	}
        }
    }
	
	public static void main(String[] args) {
		int[][] matrix = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
		SetZeroes sz = new SetZeroes();
		sz.setZeroes(matrix);
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
~~~

#### 声明游戏（289）

~~~java
class Solution {
    public void gameOfLife(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        int[][] res = new int[m][n];
        int count;
        //遍历数组
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                count = 0;
                //遍历该元素四周共九个元素
                for(int k = i-1; k <= i+1; k++){
                    for(int l = j-1; l <= j+1; l++){
                        //记录活细胞数（元素值为1的数，包括其本身）
                        if(k >= 0 && k < m && l >= 0 && l < n && board[k][l] == 1){
                            count++;
                        }
                    }
                }
                //当自身为1
                if(board[i][j] == 1){
                    if(count == 3 || count == 4){
                        res[i][j] = 1;
                    }
                    else{
                        res[i][j] = 0;
                    }
                }
                //当自身为0
                if(board[i][j] == 0){
                    if(count == 3){
                        res[i][j] = 1;
                    }
                    else{
                        res[i][j] = 0;
                    }
                }
            }
        }
        //将目标数组赋值给原数组
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                board[i][j] = res[i][j];
            }
        }
    }
}
~~~

#### 区域和检索-数组不可变（303）

~~~java
//暴力解法
class NumArray {

    private int[] nums;

    public NumArray(int[] nums) {
        this.nums = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            this.nums[i] = nums[i];
        }
 
    }
    
    public int sumRange(int left, int right) {
        int res = 0;
        for(int i = left, j = 0; i <= right; i++, j++){
            res += nums[i];
        }
        return res;
    }
}

//优化解法：前缀和
lass NumArray {

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

#### 二维区域和检索-矩阵不可变（304）

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

#### 除自身意外数组的乘积（238）

~~~java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = 1;
        for(int i = 1; i < n; i++){
            res[0] *= nums[i];
            res[i] = 1;
        }
        for(int i = 1; i < n; i++){
            //小小用了一下前缀积
            if(nums[i] != 0){
                res[i] = res[i-1] / nums[i] * nums[i-1];
            }
            else{
                for(int j = 0; j < n; j++){
                    if(j != i){
                        res[i] *= nums[j];
                    }
                }
            }
        }
        return res;
    }
}
//纯暴力解法会超时
~~~

#### 最小操作次数使数组元素相等（453）

~~~java
/*
*  我愿称之为yyds算法
*  将数组排序后，每次让除最大元素的其他元素加上(max-min)
*  使得这一轮的最小元素和最大元素相等
*  同时理所当然的，次轮的最大元素为前一轮的第二大元素
*  即上一轮的倒数第二个元素（排序过）
*  以此一轮一轮相加，最终可使得数组元素都相等
*  加过的数值和就相当于Σ[nums[i]-nums[0](1<=i<=n-1)]
*/

class Solution {
    public int minMoves(int[] nums) {
        int n = nums.length;
        int count = 0;
        Arrays.sort(nums);
        for(int i = n-1; i > 0; i--){
            count += nums[i] - nums[0];
        }
        return count;
    }
}
~~~

### 字符串

#### 解码方法（91）

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

#### 检测大写字母（520）

~~~java
/*
以下情况字符串为正确：
1、全部字母都是大写，比如"USA"。
2、单词中所有字母都不是大写，比如"leetcode"。
3、如果单词不只含有一个字母，只有首字母大写， 比如 "Google"。
*/
public class DetectCapitalUse {
public boolean detectCapitalUse(String word) {
        int n = word.length();
    	//当字符串长度为1，一定正确
        if(n == 1) {
        	return true;
        }
        int c1 = 0;
        int c2 = 0;
    	//记录大小写字符的个数
        for(int i = 0; i < n; i++) {
        	if(word.charAt(i) >= 65 && word.charAt(i) <= 90) {
        		c1++;
        	}
        	if(word.charAt(i) >= 97 && word.charAt(i) <= 122) {
        		c2++;
        	}
        }
    	//如果全为大写或全为小写，正确
        if(c1 == n || c2 == n) {
        	return true;
        }
    	//如果首字母大写，其余字母小写，正确
        if(c1 == 1 && c2 == n-1 && word.charAt(0) >= 65 && word.charAt(0) <= 90) {
        	return true;
        }
        return false;
    }
}
~~~

#### 验证回文串（125）

~~~java
public class IsPalindrome {
    //用Character.isLetterOrDight方法判断是否为字母或数字
    //由于不区分大小写，在储存时统一转化为小写字母
    //用StringBuilder的reverse方法获得倒置的字符串
    //判断二者是否相等，相等则为回文串
	public boolean isPalindrome1(String s) {
        StringBuilder str1 = new StringBuilder();
        int n = s.length();
        for(int i = 0; i < n; i++) {
        	if(Character.isLetterOrDigit(s.charAt(i))) {
        		str1.append(Character.toLowerCase(s.charAt(i)));
        	}
        }
        StringBuilder str2 = new StringBuilder(str1).reverse();
        return str1.toString().equals(str2.toString());
    }
    
    //同样用Character.isLetterOrDight方法判断是否为字母或数字并储存到StringBuilder中
    //利用双指针分别从前往后，从后往前遍历StringBuilder，若全相同，则为回文串
    public boolean isPalindrome2(String s) {
        StringBuilder str = new StringBuilder();
        int n = s.length();
        for(int i = 0; i < n; i++) {
        	if(Character.isLetterOrDigit(s.charAt(i))) {
        		str.append(Character.toLowerCase(s.charAt(i)));
        	}
        }
        int m = str.length()
        for(int i = 0, j = m-1; i < m/2; i++, j--){
            if(str.charAt(i) != str.charAt(j)){
                return false;
            }
        }
        return true;
    }
}
~~~

#### 最长公共前缀（14）

~~~java
//思路要清晰：先实现二者的比较，再扩大至数组的比较
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0){
            return "";
        }
        int n = strs.length;
        String res = strs[0];
        //用res储存前者的最长公共前缀，依次和后者比较并返回这二者的最长公共前缀
        //当比较完整个字符串数组或最长公共前缀为0时退出循环，返回答案
        for(int i = 1; i < n; i++){
            res = subLongestCommonPrefix(res, strs[i]);
            if(res.length() == 0)
                break;
        }
        return res;
    }

    //获得两个数组的最长公共前缀
    public String subLongestCommonPrefix(String str1, String str2){
        int index = 0, count = 0;
        int n = Math.min(str1.length(), str2.length());
        while(index < n){
            if(str1.charAt(index) != str2.charAt(index))
                break;
            count++;
            index++;
        }
        return str1.substring(0, count);
    }
}
~~~

#### 字符串中的单词数（434）

仓促完成

~~~java
class Solution {
    public int countSegments(String s) {
        int n = s.length();
        if(n == 0){
            return 0;
        }
        int res = 0;
        for(int i = 0; i < n; i++){
            //当前一个字符为空格，当前字符不为空格，为一个单词的首字母
            //当单词在开头，即首字母在第一位，也计一个单词（不然会漏掉）
            if((i == 0 || s.charAt(i-1) == ' ' ) && s.charAt(i) != ' '){
                res++;
            }
        }
        return res;
    }
}
~~~

#### 最后一个单词的长度（58）

~~~java
class Solution {
    public int lengthOfLastWord(String s) {
        int res = 0;
        int n = s.length();
        int i = 0;
        while(i < n){
            int temp = 0;
            //记录每一个单词的长度，碰到新单词便用新单词长度覆盖就单词长度
            if((i == 0 || s.charAt(i-1) == ' ') && s.charAt(i) != ' '){
                while(i < n){
                    if(s.charAt(i) == ' ')
                        break;
                    temp++;
                    i++;
                }
            }
            //当temp不为0时，判断读到了单词，将长度更新
            if(temp != 0)
                res = temp;
            i++;
        }
        //将字符串更新完，自然得到最后一个单词长度
        return res;
    }
}
~~~

#### 反转字符串（344）

~~~java
class Solution {
    public void reverseString(char[] s) {
        int n = s.length;
        for(int i = 0; i < n/2; i++){
            char temp = s[i];
            s[i] = s[n-i-1];
            s[n-i-1] = temp;
        }
    }
}
~~~

#### 反转字符串Ⅱ（541）

~~~java
public class ReverseStr {
	
	public String reverseStr(String s, int k) {
        int n = s.length();
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < n; i+=k){
            //这个算法判断很精髓
            //判断是第双数还是第单数个k，如当k=2，ab为双数个，需要反转，cd为单数个，不需要反转
            //当为双数个时，进行反转
            if((i/k)%2 == 0){
                for(int j = i+k-1; j >= i; j--){
                    if(j < n)
                        res.append(s.charAt(j));
                }
            }
            //当为单数个时，如实录入
            else{
                for(int j = i; j < i+k; j++){
                    if(j < n)
                        res.append(s.charAt(j));
                }
            }
        }
        String ans = res.toString();
        return ans;
    }
	
	public static void main(String[] args) {
		String str = "abcdef";
		ReverseStr rs  = new ReverseStr();
		System.out.println(rs.reverseStr(str, 2));
	}
}
~~~

#### 反转字符串中的单词Ⅲ（557）

~~~java
class Solution {
    public String reverseWords(String s) {
        int n = s.length();
        StringBuilder res = new StringBuilder();
        int i = 0;
        while(i < n){
            //当碰到单词首字母
            if((i == 0 || s.charAt(i-1) == ' ') && s.charAt(i) != ' '){
                int j = i;
                //开始计数，得到单词末尾的下标j
                while(j < n && s.charAt(j) != ' '){
                    j++;
                }
                //从后往前将单词录入StringBuilder
                for(int m = j-1; m >= i; m--){
                    res.append(s.charAt(m));
                }
                //若j不为字符串末尾，录入一个空格（当j为n时，不再录入空格）
                if(j+1 < n){
                    res.append(' ');
                }
                //将i定位到当前单词末尾后的空格
                i = j;
            }
            i++;           
        }
        String ans = res.toString();
        return ans;
    }
}
~~~

#### 翻转字符串里的单词（151）

~~~java
public class ReverseWords {
	public String reverseWords(String s) {
		//去掉s前后的空格，简化添加空格的判定条件
		String str = s.trim();
        int n = str.length();
        StringBuilder res = new StringBuilder();
        int i = n-1;
        while(i >= 0){
            int j = i;
            if(str.charAt(i) != ' ' && (i == n-1 || str.charAt(i+1) == ' ')){
                while(j >= 0 && str.charAt(j) != ' '){
                    j--;
                }
                for(int m = j+1; m <= i; m++){
                    res.append(str.charAt(m));
                }
                //当未达到字符串头部时，添加一个空格，准备迎接下一个单词
                if(j != -1) {
                	res.append(' ');
                }
                i = j;
            }
            i--;
        }
        String ans = res.toString();
        return ans;
    }
	
	public static void main(String[] args) {
		ReverseWords rw = new ReverseWords();
		String s = "  hello world   ";
		System.out.println(rw.reverseWords(s) + 'a');
	}
}

~~~

#### 字符串中的第一个唯一字符（387）

~~~java
//用hashmap解，一个字符对应其出现次数
//找到第一个次数为1的字符，返回其下标
//找不到则返回-1
class Solution {
    public int firstUniqChar(String s) {
        int n = s.length();
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < n; i++){
            char c = s.charAt(i);
            if(map.containsKey(c)){
                map.put(c, map.get(c)+1);
            }
            else{
                map.put(c, 1);
            }
        }
        for(int i = 0; i < n; i++){
            if(map.get(s.charAt(i)) == 1){
                return i;
            }
        }
        return -1;
    }
}
~~~

#### 找不同（389）

~~~java
//同样采用哈希表
//当s中出现c，令键c的值+1或为1，当t中出现字符c，令键c的值-1或为(-1)
//遍历哈希表，返回值为-1所对应的键
class Solution {
    public char findTheDifference(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = s.length();
        for(int i = 0; i < n; i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if(map.containsKey(c1)){
                map.put(c1, map.get(c1)+1);
            }
            else{
                map.put(c1, 1);
            }
            if(map.containsKey(c2)){
                map.put(c2, map.get(c2)-1);
            }
            else{
                map.put(c2, -1);
            }
        }
        char res = t.charAt(n);
        if(map.containsKey(res)){
            map.put(res, map.get(res)-1);
        }
        else{
            return res;
        }
        for(char c: map.keySet()){
            if(map.get(c) < 0){
                res = c;
                break;
            }
        }
        return res;
    }
}
~~~

#### 赎金信（383）

~~~java
//哈希大法好（一遍过）
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n1 = ransomNote.length();
        int n2 = magazine.length();
        for(int i = 0; i < n1; i++){
            char c = ransomNote.charAt(i);
            if(map.containsKey(c)){
                map.put(c, map.get(c)+1);
            }
            else{
                map.put(c, 1);
            }
        }
        for(int i = 0; i < n2; i++){
            char c = magazine.charAt(i);
            if(map.containsKey(c)){
                map.put(c, map.get(c)-1);
            }
            else{
                map.put(c, -1);
            }
        }
        for(char c: map.keySet()){
            if(map.get(c) > 0){
                return false;
            }
        }
        return true;
    }
}
~~~

#### 有效的字母异位词（242）

~~~java
//永远的HashMap
class Solution {
    public boolean isAnagram(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = s.length();
        if(n != t.length())
            return false;
        for(int i = 0; i < n; i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if(map.containsKey(c1))
                map.put(c1, map.get(c1)+1);
            else
                map.put(c1, 1);
            if(map.containsKey(c2))
                map.put(c2, map.get(c2)-1);
            else
                map.put(c2, -1);
        }
        for(char c: map.keySet()){
            if(map.get(c) != 0)
                return false;
        }
        return true;
    }
}
~~~

#### 字母异位词分组（49）

有难度

~~~java
/*
* String.toCharArray()
* Arrays.sort(char[] arr)
* Map.getOrDefault(Object key, Object default)
* Map.values()
* new ArrayList<Object>(Object)
*/

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        //用一个hashmap，键为排序后的单词，值为排序前单词组成的List
        HashMap<String, List<String>> map = new HashMap<>();
        for(String str: strs){
            //将单词转化为数组便于排序
            char[] arr = str.toCharArray();
            //对单词进行排序，由于只含小写字母，异位词排序后将一模吊样
            Arrays.sort(arr);
            //将排序后的单词作为键
            String key = new String(arr);
            //取出键对应的List，若无，则new一个ArrayList
            List<String> value = map.getOrDefault(key, new ArrayList<String>());
            //在该list中添加当前单词（未排序过的）
            value.add(str);
            //将更新了的list重新放进map里
            map.put(key, value);
        }
        return new ArrayList<List<String>>(map.values());
    }
}
~~~

#### 根据字符出现频率排序（451）

有难度

~~~java
class Solution {
    public String frequencySort(String s) {
        //用hashmap记录每个字符对应的出现次数
        HashMap<Character, Integer> map = new HashMap<>();
        for(Character c: s.toCharArray()){
            map.put(c, map.getOrDefault(c, 0)+1);
        }
        //大顶堆（优先队列）：用匿名类重写比较器
        Queue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>(){
            public int compare(Map.Entry<Character, Integer> e1, Map.Entry<Character, Integer> e2){
                return (int)e2.getValue()-e1.getValue();
            }
        });
        //将hashmap记录的entry放入大顶堆，大顶堆将根据上述规则自动排序
        for(Map.Entry<Character, Integer> e: map.entrySet()){
            queue.add(e);
        }
        StringBuilder res = new StringBuilder();
        //当queue不为空，弹出队首元素，根据出现次数，将键值循环加入stringbuilder
        while(queue.peek() != null){
            Map.Entry e = queue.poll();
            for(int i = 0; i < (int)e.getValue(); i++){
                res.append(e.getKey());
            }
        }
        return res.toString();
    }
}

~~~

#### 从英文中重建数字（423）

我们需要寻找一些独特的标志。注意到，所有的偶数都包含一个独特的字母：

“z” 只在 “zero” 中出现。
“w” 只在 “two” 中出现。
“u” 只在 “four” 中出现。
“x” 只在 “six” 中出现。
“g” 只在 “eight” 中出现。
因此，从偶数开始是一个很好的思路。

这也是计算 3，5 和 7 的关键，因为有些单词只在一个奇数和一个偶数中出现（而且偶数已经被计算过了）：

“h” 只在 “three” 和 “eight” 中出现。
“f” 只在 “five” 和 “four” 中出现。
“s” 只在 “seven” 和 “six” 中出现。
接下来只需要处理 9和1，思路依然相同。

“i” 在 “nine”，“five”，“six” 和 “eight” 中出现。
“n” 在 “one”，“seven” 和 “nine” 中出现。

~~~java
import java.util.HashMap;

public class OriginalDigits {
	
    public String originalDigits(String s) {
        StringBuilder ans = new StringBuilder();
        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Integer> res = new HashMap<>();
        char[] str = s.toCharArray();
        for(char c: str) {
        	map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int num0 = map.getOrDefault('z', 0);
        int num2 = map.getOrDefault('w', 0);
        int num4 = map.getOrDefault('u', 0);
        int num6 = map.getOrDefault('x', 0);
        int num8 = map.getOrDefault('g', 0);
        res.put('0', num0);
        res.put('2', num2);
        res.put('4', num4);
        res.put('6', num6);
        res.put('8', num8);
        
        if(num0 != 0) {
        	map.put('e', map.get('e')-num0);
            map.put('r', map.get('r')-num0);
            map.put('o', map.get('o')-num0);
        }
        if(num2 != 0) {
        	map.put('t', map.get('t')-num2);
            map.put('o', map.get('o')-num2);
        }
        if(num4 != 0) {
        	map.put('f', map.get('f')-num4);
            map.put('o', map.get('o')-num4);
            map.put('r', map.get('r')-num4);
        }
        if(num6 != 0) {
        	map.put('s', map.get('s')-num6);
            map.put('i', map.get('i')-num6);
        }
        if(num8 != 0) {
        	map.put('e', map.get('e')-num8);
            map.put('i', map.get('i')-num8);
            map.put('h', map.get('h')-num8);
            map.put('t', map.get('t')-num8);
        }
        
        
        
        int num3 = map.getOrDefault('h', 0);
        int num5 = map.getOrDefault('f', 0);
        int num7 = map.getOrDefault('s', 0);
        res.put('3', num3);
        res.put('5', num5);
        res.put('7', num7);
        
        //减去了5（five）、6（six）、8（eight）中的i，剩下全是9（nine）中的i
        map.put('i', map.getOrDefault('i', 0)-num5); 
        int num9 = map.getOrDefault('i', 0);
        res.put('9', num9);    
        //一个nine中含两个n，减去9中的两个n，剩下全是one中的n
        map.put('n', map.getOrDefault('n', 0)-num7-num9*2);
        int num1 = map.getOrDefault('n', 0);        
        res.put('1', num1);
        
        char[] arr = {'0','1','2','3','4','5','6','7','8','9'};
        for(int i = 0; i < 10; i++) {
        	char c = arr[i];
        	for(int j = 0; j < res.getOrDefault(c, 0); j++) {
        		ans.append(c);
        	}
        }
        
        return ans.toString();
    }
    
    public static void main(String[] args) {
    	OriginalDigits od = new OriginalDigits();
    	String s = "nnei";
    	System.out.println(od.originalDigits(s));
	}
}
~~~

#### 机器人能否返回原点（657）

~~~java
class Solution {
    public boolean judgeCircle(String moves) {
        HashMap<Character, Integer> map = new HashMap<>();
        for(char c: moves.toCharArray()){
            //碰到R，列向右移一格
            if(c == 'R'){
                map.put('c', map.getOrDefault('c', 0)+1);
            }
            //碰到L，列向左移一格
            if(c == 'L'){
                map.put('c', map.getOrDefault('c', 0)-1);
            }
            //碰到U，行向上移一格
            if(c == 'U'){
                map.put('r', map.getOrDefault('r', 0)+1);
            }
            //碰到D，行向下移一格
            if(c == 'D'){
                map.put('r', map.getOrDefault('r', 0)-1);
            }
        }
        if(map.getOrDefault('c', 0)==0 && map.getOrDefault('r', 0)==0)
            return true;
        return false;
    }
}
~~~

#### 学生出勤记录Ⅰ（551）

~~~java
class Solution {
    public boolean checkRecord(String s) {
        int n = s.length();
        int c1 = 0, c2 = 0;
        for(int i = 0; i < n; i++){
            if(s.charAt(i) == 'A')
                c1++;
            //当为第一个L时，重置L数量为1，开始计数
            if((i == 0 || s.charAt(i-1)!='L') && s.charAt(i) == 'L')
                c2 = 1;
            //当前者为L，当前也为L，判断为连续的L，对c2进行累计
            if(i > 0 && s.charAt(i-1) == 'L' && s.charAt(i) == 'L')
                c2++;
            //每轮判断A数量和连续的L数量是否超过限制，超过则返回false
            if(c1 > 1 || c2 > 2)
                return false;
        }
        //遍历完成仍未返回false，则返回true
        return true;
    }
}
~~~

#### 计数二进制子串（696）

我们可以将字符串 s 按照 00 和 11 的连续段分组，储存在counts 数组中，例如 s = 00111011，可以得到这样的counts 数组：counts={2,3,1,2}，即代表两个0，两个1，一个0，两个1（实际上是0还是1并不重要，重要的是不同）

这里counts 数组中两个相邻的数一定代表的是两种不同的字符。假设 counts 数组中两个相邻的数字为 u 或者 v，它们对应着u个0和v个1，或者 u 个1和v个0。它们能组成的满足条件的子串数目为min{u,v}，即一对相邻的数字对答案的贡献。

我们只要遍历所有相邻的数对，求它们的贡献总和，即可得到答案。

~~~java
import java.util.ArrayList;
import java.util.List;

public class CountBinarySubstrings {
	public int countBinarySubstrings(String s) {
        int n = s.length();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < n; i++){
            char c = s.charAt(i);
            if(i==0 || c!=s.charAt(i-1)){
                int count = 1;
                int j = i+1;
                while(j < n && s.charAt(j) == c){
                    count++;
                    j++;
                }
                list.add(count);
            }
        }
        int res = 0;
        int m = list.size();
        for(int i = 0; i < m-1; i++){
            res += Math.min(list.get(i), list.get(i+1));
        }
        return res;
    }
	
	public static void main(String[] args) {
		CountBinarySubstrings cbs = new CountBinarySubstrings();
		String s = "00110011";
		System.out.print(cbs.countBinarySubstrings(s));
	}
}
~~~

#### 猜数字游戏（299）

~~~java
class Solution {
    public String getHint(String secret, String guess) {
        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();
        int A = 0, B = 0;
        int n = secret.length();
        for(int i = 0; i < n; i++){
            char c1 = secret.charAt(i);
            char c2 = guess.charAt(i);
            //若猜对，公牛+1，并排除在奶牛计算范围之外
            if(c1==c2){
                A++;
            }
            else{
                map1.put(c1, map1.getOrDefault(c1, 0)+1);
                map2.put(c2, map2.getOrDefault(c2, 0)+1);
            }
        }
        for(Character c: map1.keySet()){
            if(map2.containsKey(c)){
                int n1 = map1.get(c);
                int n2 = map2.get(c);
                //若答案c数量大于猜测c数量，奶牛+=猜测c数
                //若答案c数量小于猜测c数量，奶牛+=答案c数
                B += n2>n1?n1:n2;
            }
        }
        StringBuffer res  = new StringBuffer();
        res.append(A+"");
        res.append('A');
        res.append(B+"");
        res.append('B');
        return res.toString();
    }
}
~~~

#### Fizz Buzz（412）

？

~~~java
class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        for(int i = 1; i <= n; i++){
            if(i%3==0 && i%5==0){
                list.add("FizzBuzz");
            }
            else if(i%3==0){
                list.add("Fizz");
            }
            else if(i%5==0){
                list.add("Buzz");
            }
            else{
                list.add(i+"");
            }
        }
        return list;
    }
}
~~~

#### 相对名次（506）

~~~java
//用score.clone()将数组备份，再用Arrays.sort()进行排序
//二重遍历对比排序前后数组，排出相对名次
class Solution {
    public String[] findRelativeRanks(int[] score) {
        int n = score.length;
        int[] t = score.clone();
        Arrays.sort(t);
        String[] res = new String[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(score[i] == t[j]){     
                    if(j==n-1)
                        res[i] = "Gold Medal";
                    else if(j==n-2)
                        res[i] = "Silver Medal";
                    else if(j==n-3)
                        res[i] = "Bronze Medal";
                    else
                        res[i] = (n-j)+"";
                    break;
                }
            } 
        }
        return res;
    }
}
~~~

#### 最小时间差（539）

~~~java
class Solution {
    public int findMinDifference(List<String> timePoints) {
        List<Integer> times = new ArrayList<>();
        int res = 60*12;
        //储存从00:00到每个时间点的绝对时间
        for(String str: timePoints)
            times.add(Integer.parseInt(str.substring(0, 2))*60 + Integer.parseInt(str.substring(3)));
        for(int i = 0; i < times.size(); i++){
            for(int j = i+1; j < times.size(); j++){
                //获取各自绝对时间的相对时间差
                int diff = times.get(i)>times.get(j)?(times.get(i)-times.get(j)):(times.get(j)-times.get(i));
                //计算真正的时间差，如18:00到00:00的绝对时间的相对时间差为18*60，但真正时间差为(24-18)*60
                int realDiff = diff>=(60*12)?(60*24-diff):diff;
                if(realDiff < res)
                    res = realDiff;
            }
        }
        return res;
    }
}
~~~

#### 复数乘法（537）

~~~java
class Solution {
    public String complexNumberMultiply(String num1, String num2) {
        int r1 = Integer.parseInt(num1.substring(0, num1.indexOf('+')));
        int r2 = Integer.parseInt(num2.substring(0, num2.indexOf('+')));
        int i1 = Integer.parseInt(num1.substring(num1.indexOf('+')+1, num1.indexOf('i')));
        int i2 = Integer.parseInt(num2.substring(num2.indexOf('+')+1, num2.indexOf('i')));
        int r = r1*r2-i1*i2;
        int i = r1*i2+r2*i1;
        StringBuffer res = new StringBuffer();
        res.append(r+"+"+i+"i");
        return res.toString();
    }
}
~~~

#### 分数加减运算（592）

~~~java
//纯分割字符串，转换整型解题
/**
 *	1、将字符串根据 '+'或'-'或最后一个元素进行分割，注意留住 '-'号，去掉 '+'号，排除首位是 '-'的情况
 *	2、由(1)可以得到分割的一个个分数，以（n/m）的形式存在List中，再对各个分数用 '/'分割，得到各分子分母，存到String数组中（长度==list.size）
 *	3、求分母的最小公倍数，clone()一个新分母数组，用前缀和求得其最小公倍数
 *  4、求通分后分子和，先把各个分子乘上相应倍数，再用前缀和求的其和
 *	5、此时我们已经得到答案的分子分母，再对分子分母求最大公倍数，并除以这个最大公倍数
 *	6、最后将分子分母组合成（分子/分母）的字符串返回，得结果
 */
public class HelloNEUQer {
    //求最大公约数
    public int gcd(int n1, int n2){
        int gcd = 1;
        int t1 = n1<0?(-1*n1):n1;
        int t2 = n2<0?(-1*n2):n2;
        for(int i = 2; i <= (t1>t2?t2:t1); i++){
            if(n1%i==0 && n2%i==0)
                gcd = i;
        }
        return gcd;
    }

    //求最小公倍数
    public String lcm(String c1, String c2){
        int n1 = Integer.parseInt(c1);
        int n2 = Integer.parseInt(c2);
        int gcd = gcd(n1, n2);
        int lcm = n1*n2/gcd;
        return lcm+"";
    }

    //通分分子变动
    public String fun(String num, String deno, String lcm){
        int n = Integer.parseInt(num);
        int d = Integer.parseInt(deno);
        int l = Integer.parseInt(lcm);
        int dist = n*(l/d);
        return dist+"";
    }

    //字符串加法
    public String cal(String s1, String s2){
        int n1 = Integer.parseInt(s1);
        int n2 = Integer.parseInt(s2);
        return (n1+n2)+"";
    }

    //字符串除法
    public String div(String str, int n){
        int num = Integer.parseInt(str);
        int res = num/n;
        return res+"";
    }

    public String fractionAddition(String expression) {
        List<String> fraction = new ArrayList<>();
        int n = expression.length();
        int j = 0;
        //存入分数字符串
        for(int i = 0; i < n; i++){
            char c = expression.charAt(i);
            if(i!=0 && (c=='+' || c=='-' || i==n-1)){
                if(i==n-1){
                    fraction.add(expression.substring(j));
                }
                else{
                    fraction.add(expression.substring(j, i));
                }
                if(c=='-'){
                    j = i;
                }
                else{
                    j = i+1;
                }
            }
        }
        int m = fraction.size();
        String[] numerator = new String[m];
        String[] denominator = new String[m];
        for(int i = 0; i < m; i++){
            String t = fraction.get(i);
            numerator[i] = t.substring(0, t.indexOf('/'));
            denominator[i] = t.substring(t.indexOf('/')+1);
        }
        String[] temp = denominator.clone();
        for(int i = 1; i < m; i++){
            temp[i] = lcm(temp[i], temp[i-1]);
        }
        String lcm = temp[m-1];
        for(int i = 0; i < m; i++){
            numerator[i] = fun(numerator[i], denominator[i], lcm);
        }
        for(int i = 1; i < m; i++){
            numerator[i] = cal(numerator[i-1], numerator[i]);
        }
        String sum = numerator[m-1];
        int gcd = gcd(Integer.parseInt(sum), Integer.parseInt(lcm));
        sum = div(sum, gcd);
        lcm = div(lcm, gcd);
        String res = sum+'/'+lcm;
        if(sum.equals("0")){
            res = "0/1";
        }
        return res;
    }

    public static void main(String[] args) throws IOException{
        //Runtime.getRuntime().exec("shutdown");
        HelloNEUQer h = new HelloNEUQer();
        System.out.println(h.fractionAddition("-1/4-4/5-1/4"));
    }
}
~~~

#### 求解方程（640）

~~~java
//解析一个方程（字符串）
class Solution {
    public String solveEquation(String equation) {
        //将方程分为左右两个多项式
        String left = equation.substring(0, equation.indexOf('='));
        String right = equation.substring(left.length()+1);
        //将x全移在方程左边，计算其系数
        int x = getX(left)-getX(right);
        //将常数全移在方程右边
        int c = getC(right)-getC(left);
        //对方程的解进行判断（无穷多解或无解）
        if(x==0 && c!=0){
            return "No solution";
        }else if(x==0 && c==0){
            return "Infinite solutions";
        }
        int res = c/x;
        return "x=" + res;
    }

    //计算一个多项式中x的系数
    public int getX(String str){
        int n = str.length();
        //初始化系数为0
        int x = 0;
        for(int i = 0; i < n; i++){
            char c = str.charAt(i);
            //当碰到x
            if(c=='x'){
                StringBuffer s = new StringBuffer();
                int j = i-1;
                //从x往前遍历，当碰到运算符 +、- 或遍历到多项式首时停止
                for(; j >= 0; j--){
                    char c1 = str.charAt(j);
                    if(c1 == '+' || c1 == '-')
                        break;
                    s.append(c1);
                }
                //若x前无系数（即省略了系数1），人为将1补上
                if(s.length()==0)
                    s.append('1');
                //判断系数的正负，同时将系数倒过来（因为是从后往前遍历的）
                //不能把+、-号也加进StringBuffer中，在parseInt时会解析错误
                if(i==0 || (j>=0 && str.charAt(j)=='+') || (j==-1 && str.charAt(0)!='-')){
                    x += Integer.parseInt(s.reverse().toString());
                }else {
                    x -= Integer.parseInt(s.reverse().toString());
                }
            }
        }
        return x;
    }

    //计算一个多项式的常数和
    public int getC(String str){
        //初始化常数和为0
        int C = 0;
        int n = str.length();
        //遍历多项式
        for(int i = 0; i < n; i++){
            char c = str.charAt(i);
            StringBuffer s = new StringBuffer();
            //当碰到数字，从当前向后遍历，知道碰到运算符停止，同时将各数字append进StringBuffer中
            if(Character.isDigit(c)){
                int j = i;
                for(; j < n; j++){
                    char c1 = str.charAt(j);
                    if(c1=='+' || c1=='-')
                        break;
                    //若该项为含x的项，清空StringBuffer（x的系数当然不算常数），并退出本次循环
                    if(c1=='x'){
                        s = new StringBuffer();
                        break;
                    }
                    s.append(c1);
                }
                //若StringBuffer中有元素，即录进了常数
                if(s.length()>0){
                    //判断常数正负号，加或减在C上
                    if(i == 0 || str.charAt(i-1)=='+'){
                        C += Integer.parseInt(s.toString());
                    }else{
                        C -= Integer.parseInt(s.toString());
                    }
                }
                i = j;
            }
        }
        return C;
    }
}
~~~

#### 外观数列（38）

我变强了

~~~java
//按照要求去构造一个外观数列
//循环构造到第m行，注意减一
class Solution {
    public String countAndSay(int m) {
        String root = "1";
        for(int i = 0; i < m-1; i++){
            int n = root.length();
            StringBuffer row = new StringBuffer();
            for(int j = 0; j < n; j++){
                int count = 1;
                while(j+1<n && root.charAt(j)==root.charAt(j+1)){
                    count++;
                    j++;
                }
                row.append(count+""+root.charAt(j));
            }
            root = row.toString();
        }
        return root;
    }
}
~~~

#### 压缩字符串（443）

~~~java
//将{a,a,b,b,c,c,c} ——> {a,2,b,2,c,3}
//用StringBuffer求解
class Solution {
    public int compress(char[] chars) {
        int n = chars.length;
        StringBuffer str = new StringBuffer();
        for(int i = 0; i < n; i++){
            str.append(chars[i]);
            int count = 1;
            while(i+1 < n && chars[i]==chars[i+1]){
                count++;
                i++;
            }
            if(count > 1)
                str.append(count+"");
        }
        for(int i = 0; i < str.length(); i++){
            chars[i] = str.charAt(i);
        }
        return str.length();
    }
}
~~~

#### 字符串转换整数(atoi)（8）

~~~java
//加强版Integer.parseInt(String s)
//将字符串中先出现的合法数字转换为整数返回
//取巧用catch(NumberFormatException e)去捕捉当字符串中数字超过整型最大或最小，然后输出Integer.MAX_VALUE
class Solution {
    public int myAtoi(String s) {
        String str = s.trim();
        int i = 0, n = str.length();
        if(n==0)
            return 0;
        if(!Character.isDigit(str.charAt(0)) && str.charAt(0)!='-' && str.charAt(0)!='+'){
            return 0;
        }
        StringBuffer ans = new StringBuffer();
        if(Character.isDigit(str.charAt(0))){
            while(i<n && Character.isDigit(str.charAt(i))){
                ans.append(str.charAt(i));
                i++;
            }
            try{
                int res = Integer.parseInt(ans.toString());
                return res;
            }catch(NumberFormatException e){
                return Integer.MAX_VALUE;
            }
        }
        else{
            i = 1;
            if(n==i)
                return 0;
            if(!Character.isDigit(str.charAt(i)))
                return 0;
            while(i<n && Character.isDigit(str.charAt(i))){
                ans.append(str.charAt(i));
                i++;
            }
            try{
                int res = Integer.parseInt(ans.toString());
                return str.charAt(0)=='-'?-1*res:res;
            }catch (NumberFormatException e){
                return str.charAt(0)=='-'?Integer.MIN_VALUE:Integer.MAX_VALUE;
            }
        }
    }
}
~~~

#### 罗马数字转整数（13）

~~~java
//用hashmap对罗马数字转义为整型
//判断相邻的两个罗马数字
//若前者大于后者，直接录入前者，后者与后后者再比较，如此类推
//若后者大于前者，录入（后-前），如IV=5-1=4
class Solution {

    private HashMap<Character, Integer> roman;

    public void initRoman(){
        roman = new HashMap<>();
        roman.put('I', 1);
        roman.put('V', 5);
        roman.put('X', 10);
        roman.put('L', 50);
        roman.put('C', 100);
        roman.put('D', 500);
        roman.put('M', 1000);
    }
    
    public int romanToInt(String s) {
        initRoman();
        int n = s.length();
        int res = 0;
        for(int i = 0; i < n; i++){
            int c1 = (int)roman.get(s.charAt(i));
            int c2 = 0;
            if(i+1 < n){
                c2 = (int)roman.get(s.charAt(i+1));
            }
            System.out.println(i);
            if(c1 >= c2){
                res += c1;
            }else{
                res += (c2-c1);
                i++;
            }
        }
        return res;
    }
}
~~~

#### 整数转罗马数字（12）

~~~java
//单独对4、9、40、90、400、900进行判断
class Solution {
    public String intToRoman(int num) {
        StringBuffer res = new StringBuffer();
        while(num >= 1000){
            num -= 1000;
            res.append('M');
        }

        int i = 0;
        while(num >= 100){
            num -= 100;
            //计算100的个数
            i++;;
        }
        if(i==9){	//若为900
            res.append("CM");
        }else if(i==4){		//若为400
            res.append("CD");
        }else if(i>=5){		//若大于等于500
            res.append('D');
            for(int j = 0; j < i-5; j++){
                res.append('C');
            }
        }else{		////若小于500
            for(int j = 0; j < i; j++){
                res.append('C');
            }    
        }

        i = 0;
        while(num >= 10){
            num -= 10;
            //计算10数量，与上同理
            i++;
        } 
        if(i==9)
            res.append("XC");
        else if(i==4){
            res.append("XL");
        }else if(i>=5){
            res.append('L');
            for(int j = 0; j < i-5; j++){
                res.append('X');
            }
        }else{
            for(int j = 0; j < i; j++){
                res.append('X');
            }    
        }

        i = 0;
        while(num > 0){
            num -= 1;
            i++;
        }
        if(i==9)
            res.append("IX");
        else if(i==4){
            res.append("IV");
        }else if(i>=5){
            res.append('V');
            for(int j = 0; j < i-5; j++){
                res.append('I');
            }
        }else{
            for(int j = 0; j < i; j++){
                res.append('I');
            }    
        }

        return res.toString();
    }
}
~~~

#### 比较版本号（165）

~~~java
//快慢指针滑动窗口
class Solution {
    public int compareVersion(String version1, String version2) {
        int m1 = version1.length();
        int m2 = version2.length();
        char[] v1 = version1.toCharArray();
        char[] v2 = version2.toCharArray();
        int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
        while(j1 < m1 || j2 < m2){              
            while(i1<m1-1 && v1[i1]=='0')	i1++;
            while(j1<m1 && v1[j1]!='.')	j1++;
            while(i2<m2-1 && v2[i2]=='0')	i2++;
            while(j2<m2 && v2[j2]!='.')	j2++;
            
            
            System.out.println(i2 + ":" + j2);
            int n1, n2;
            if(i1==j1 && i1!=m1-1)
            	n1 = 0;
            else if(i1 != m1-1)
            	n1 = Integer.parseInt(version1.substring(i1, j1));
            else
            	n1 = Integer.parseInt(version1.substring(i1));
            if(i2==j2 && i2!=m2-1)
            	n2 = 0;
            else if(i2 != m2-1)
            	n2 = Integer.parseInt(version2.substring(i2, j2));
            else
            	n2 = Integer.parseInt(version2.substring(i2));
            
            System.out.println(n1);
            if(n1>n2)
                return 1;
            if(n1<n2)
                return -1;
            
            j1++;
            j2++;
            i1 = j1;
            i2 = j2;
        }
        return 0;
    }
}
~~~

#### 神奇字符串（481）

~~~java
//构造两个字符串，左脚踩右脚 ——> 登天
//找到一致的向后延伸的规律
class Solution {
    public int magicalString(int n) {
        StringBuffer str1 = new StringBuffer();
        StringBuffer str2 = new StringBuffer();

        str1.append("122");
        str2.append("12");

        for(int i = 2; str1.length() < n; i++){
            int m = str1.length()-1;
            char c = str1.charAt(i);
            str2.append(c);
            if(c=='2'){
                if(str1.charAt(m)!=str1.charAt(m-1) && str2.charAt(i-1)!='1'){
                    str1.append(str1.charAt(m));
                }else{
                	if(str1.charAt(m)=='1') {
                		str1.append('2');
                		str1.append('2');
                	}else {
                		str1.append('1');
                		str1.append('1');
                	}                
                }
            }else if(c=='1'){
                if(str1.charAt(str1.length()-1)=='1'){
                    str1.append('2');
                }else{
                    str1.append('1');
                }
            }
        }
        int res = 0;
        for(int i = 0; i < n; i++){
            if(str1.charAt(i)=='1'){
                res++;
            }
        }
        return res;
    }
}
~~~

#### 判断子序列（392）

~~~java
//要判断字母顺序是否一致，不能用哈希表
public class IsSubsequence {
	public boolean isSubsequence(String s, String t) {
		if(s.length()==0) {
			return true;
		}
        int i = 0;
        for(char c: t.toCharArray()){
            if(c==s.charAt(i)){
                i++;
            }
            if(i==s.length()){
                return true;
            }
        }
        System.out.println(i);
        
        return false;
    }
~~~

#### 通过删除字母匹配到字典里最长单词（524）

~~~java
class Solution {
    public String findLongestWord(String s, List<String> dictionary) {
        String res = "";
        int length = 0;
        for(String str: dictionary){
            int i = 0;
            for(char c: s.toCharArray()){
                //当s中字母与当前字典字符串中元素匹配，将该字符串字母向后移
                if(i<str.length() && str.charAt(i)==c){
                    i++;
                }
            }
            //若移到当前字符串的最后一位，即匹配成功，先比较字符串长度
            if(i==str.length() && i>=length){
                if(i > length) {
                    length = i;
                    res = str;
                }else if(res.compareTo(str)>0){	//若长度一样，比较字典序
                    length = i;
                    res = str;
                }
            }
        }
        return res;
    }
}
~~~

字典序

~~~java
public class StringComparisonDemo {

    public static void main(String[] args) {
        String foo = "ABC";

        // 前面和后面每个字符完全一样，返回 0
        String bar01 = "ABC";
        System.out.println(foo.compareTo(bar01));

        // 前面每个字符完全一样，返回：后面就是字符串长度差
        String bar02 = "ABCD";
        String bar03 = "ABCDE";
        System.out.println(foo.compareTo(bar02)); // -1 (前面相等,foo 长度小 1)
        System.out.println(foo.compareTo(bar03)); // -2 (前面相等,foo 长度小 2)

        // 前面每个字符不完全一样，返回：出现不一样的字符 ASCII 差
        String bar04 = "ABD";
        String bar05 = "aABCD";
        System.out.println(foo.compareTo(bar04)); // -1  (foo 的 'C' 字符 ASCII 码值为 67，bar04 的 'D' 字符 ASCII 码值为 68。返回 67-68=-1)
        System.out.println(foo.compareTo(bar05)); // -32 (foo 的 'A' 字符 ASCII 码值为 65，bar04 的 'a' 字符 ASCII 码值为 97。返回 65-97=-32)

        String bysocket01 = "泥瓦匠";
        String bysocket02 = "瓦匠";
        System.out.println(bysocket01.compareTo(bysocket02));// -2049 （泥 和 瓦的 Unicode 差值）
    }
}
~~~

#### 最长特殊序列（521）

~~~java
//某嘻哈程序员出题
//若两字符串不一样，其自身必为另一字符串的“特殊序列”
class Solution {
    public int findLUSlength(String a, String b) {
        if(a.equals(b)){
            return -1;
        }
        return a.length()>b.length()?a.length():b.length();
    }
}
~~~

#### 最长特殊序列Ⅱ（522）

~~~java
class Solution {
    public int findLUSlength(String[] strs) {
        int length = -1;
        int n = strs.length;
        for(int i = 0; i < n; i++){
            int j;
            for(j = 0; j < n; j++){
                if(j!=i && (strs[i].equals(strs[j]) || isSon(strs[i], strs[j]))){
                    break;
                }
            }
            if(j==n && strs[i].length()>length){
                length = strs[i].length();
            }
        }
        return length;
    }

    public boolean isSon(String str1, String str2){
        int n = str1.length();
        int i = 0;
        for(char c: str2.toCharArray()){
            if(str1.charAt(i)==c){
                i++;
            }
            if(i==n){
                return true;
            }
        }
        return false;
    }
}
~~~

#### 加一（66）

~~~java
class Solution {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        int i = n-1;
        while(i>=0 && digits[i]==9){
            digits[i] = 0;
            i--;
        }
        //当为9、99、999这样的数时，位数+1
        //构造一个首项为1其余为0的新数组
        if(i==-1){
            int[] res = new int[n+1];
            res[0] = 1;
            for(int j = 1; j < n+1; j++){
                res[j] = 0;
            }
            return res;
        }
        //对第一个不为9的位数+1
        else{
            digits[i]++;
        }
        return digits;
    }
}
~~~

#### 二进制求和（67）

~~~java
class Solution {
    public String addBinary(String a, String b) {
		StringBuffer res = new StringBuffer();
         //记录是否进位
		boolean t = false;
		int n = a.length()>b.length()?a.length():b.length();
		
		for(int i = 0; i < n; i++) {
              //从末位开始相加，若超出位数范围则为0
			int n1 = i<a.length()?a.charAt(a.length()-i-1)-'0':0;
			int n2 = i<b.length()?b.charAt(b.length()-i-1)-'0':0;
			int c = n1 + n2;
			if(t) {
				c++;
			}
			if(c>=2) {
                  //若满2，则标记t为true，下次运算时进位
				t = true;
                  //若恰为2，当前位数记为0，若为3，则记为1
				if(c==2) {
					res.append('0');
				}else {
					res.append('1');
				}
			}else {
                  //若未满2，直接记录c的字符形式
				t = false;
				res,append(c+"");
			}		
		}
         //若结束时t仍为true，说明该二进制数在最高位仍需进一位
		if(t) {
			res.append('1');
		}
         //将字符串翻转得到从高位到低位排序的数
		return res.reverse().toString();
    }
}
~~~

#### 字符串相加（415）

~~~java
//与40完全一样的思路
class Solution {
    public String addStrings(String num1, String num2) {
        int n = num1.length()>num2.length()?num1.length():num2.length();
        StringBuffer res = new StringBuffer();
        boolean t = false;
        for(int i = 0; i < n; i++){
            int n1 = i<num1.length()?num1.charAt(num1.length()-i-1)-'0':0;
            int n2 = i<num2.length()?num2.charAt(num2.length()-i-1)-'0':0;
            int c = n1 + n2;
            if(t){
                c++;
            }
            if(c>=10){
                t = true;
                res.append((c-10)+"");
            }else{
                t = false;
                res.append(c+"");
            }
        }
        if(t){
            res.append('1');
        }
        return res.reverse().toString();
    }
}
~~~

#### 字符串相乘（43）

~~~java
class Solution {
    public String multiply(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();
        int[] res = new int[n1+n2];
        for(int i = 0; i < n1; i++){
            int n = num1.charAt(n1-i-1)-'0';
            int c = 0, t = 0, j;
            for(j = 0; j < n2; j++){
                int m = num2.charAt(n2-j-1)-'0';
                //模拟乘法运算，当前位相乘加上一次进位
                c = m*n+t;
                //对当前该位上数字作加法，获取本次进位
                t = (c+res[n1+n2-i-j-1])/10;
                //获取进位之后的当前位数字
                res[n1+n2-i-j-1] = (c+res[n1+n2-i-j-1])%10;                
            }
            //若最高位产生进位，对最高位赋值
            if(t!=0) {
            	res[n1+n2-i-j-1] += t;
            }
        }
        StringBuffer ans = new StringBuffer();
        int k = 0;
        //去掉前导0
        while(k < n1+n2 && res[k]==0){
            k++;
        }
        for(; k < n1+n2; k++){
            ans.append(res[k]);
        }
        //若res中全为0ans.length()将为0，即结果为0
        if(ans.length()==0){
            return "0";
        }
        return ans.toString();
    }
}
~~~

#### 累加数（306）

~~~java
class Solution {
    public boolean isAdditiveNumber(String num) {
       int n = num.length();
       int i = 0;
       for(int j = i+1; j < n; j++){
           for(int k = j+1; k < n; k++){
               if(dfs(num, i, j, k))    return true;
           }
       }
       return false;
    }

    public String add(String a, String b){
        int n1 = a.length()-1;
        int n2 = b.length()-1;
        StringBuffer sum = new StringBuffer();
        int t = 0;
        while(n1>=0 || n2>= 0){
            int c1 = n1>=0?a.charAt(n1--)-'0':0;
            int c2 = n2>=0?b.charAt(n2--)-'0':0;
            int s = c1+c2+t;
            t = s/10;
            sum.append(s%10);
        }
        if(t!=0)
            sum.append(t);
        return sum.reverse().toString();
    }

    //DFS深度优先搜索
    public boolean dfs(String s, int i, int j, int k){
        if(s.charAt(i)=='0' && j-i>1)    return false;
        if(s.charAt(j)=='0' && k-j>1)    return false;
        String a = s.substring(i, j);
        String b = s.substring(j, k);
        String sum = add(a, b);
        int n = sum.length();
        if(k+n > s.length())    return false;
        String dist = s.substring(k, k+n);
        if(!sum.equals(dist))    return false;
        if(k+n==s.length())    return true;
        return dfs(s, j, k, k+n);
    }
}
~~~

#### 密匙格式化（482）

~~~java
public class LicenseKeyFormatting {
	
	public String licenseKeyFormatting(String s, int k) {
        StringBuffer str = new StringBuffer();
        for(char c: s.toUpperCase().toCharArray()) {
        	if(c!='-') {
        		str.append(c);
        	}
        }
        int n = str.length();
        int count = 0;
        for(int i = 0; i < n; i++){
            char c = str.charAt(n-i-1);
            if(c!='-'){
                count++;
            }  
            //避免在末尾加-
            if(count%k==0 && i!=n-1){
                str.insert(n-i-1, '-');
            }
            System.out.println(str.toString());
        }
        return str.toString();
    }
	
	public static void main(String[] args) {
		LicenseKeyFormatting lkf = new LicenseKeyFormatting();
		System.out.println(lkf.licenseKeyFormatting("2-5g-3-J", 2));
	}
}
~~~

#### Z字形变换（6）

~~~java
//找规律，找到各行数字和周期t之间的关系
public String convert(String s, int numRows) {
        int t = 2*numRows-2;
        int n = s.length();
        StringBuffer res = new StringBuffer();
        int count = 0;
        while(res.length()<s.length()) {
        	for(int j = 0; j < n; j++){
        		if((j-count)%t==0 || (j+count)%t==0){
                	System.out.println(res.toString());
                    res.append(s.charAt(j));
                }
            }
            count++;
        }        
        return res.toString();
    }
~~~

#### 实现strStr()（28）

~~~java
class Solution {
    public int strStr(String haystack, String needle) {
        int n1 = haystack.length();
        int n2 = needle.length();
        if(n1 < n2){
            return -1;
        }
        if(haystack.equals(needle) || n2==0){
            return 0;
        }
        for(int i = 0; i < n1; i++){
            if(haystack.charAt(i)==needle.charAt(0)){
                String h;
                if(i+n2 <= n1)
                    h = haystack.substring(i, i+n2);
                else
                    break;
                if(h.equals(needle)){
                    return i;
                }
            }
        }
        return -1;
    }
}
~~~

#### 重复叠加字符串匹配（686）

~~~java
class Solution {
    public boolean contains(StringBuffer s, String b) {
		for(int i = 0; i < s.length(); i++){
            if(s.charAt(i)==b.charAt(0) && i+b.length()<=s.length()){
                String e = s.substring(i, i+b.length());
                if(e.equals(b)){
                    return true;
                }
            }
        }
		return false;
	}
	
	public int repeatedStringMatch(String a, String b) {
        StringBuffer s = new StringBuffer(a);
        int count = 1;
        while(s.length()<=b.length()){
            if(s.toString().equals(b)){
                return count;
            }
            s.append(a);
            count++;
        }
        if(contains(s, b)){
        	return count;
        }else {
        	s.append(a);
        	if(contains(s, b)) {
        		return count+1;
        	}
        }      
        return -1;
    }
}
~~~

#### 最长回文子串（5）

暴力解法：超出内存限制

~~~java
class Solution {
    public boolean isPalindrome(String s){
        int n = s.length();
        int c = n/2, i;
        if(n%2==1){
            for(i = 1; i <= c; i++){
                if(s.charAt(c+i)!=s.charAt(c-i)){
                    break;
                }
            }
            if(i==c+1){
                return true;
            }
            return false;
        }else{
            for(i = 1; i <= c; i++){
                if(s.charAt(c-i)!=s.charAt(c+i-1)){
                    break;
                }
            }
            if(i==c+1){
                return true;
            }
            return false;
        }
    }

    public String longestPalindrome(String s) {
        int n = s.length();
        String res = "", str;
        for(int i = 0; i < n; i++){
            for(int j = i+1; j <= n; j++){
                str = s.substring(i, j);
                if(isPalindrome(str) && str.length()>res.length()){
                    res = str;
                }
            }
        }
        return res;
    }
}
~~~

动态规划：DP

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

#### 回文子串（647）

动态规划，与上题相似

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

#### 环绕字符串中唯一的子字符串（467）

HashSet暴力解法，超出时间限制

~~~java
class Solution {
    public boolean isSon(String s){
        int n = s.length();
        if(n==1){
            return true;
        }
        for(int i = 0; i < n-1; i++){
            if(s.charAt(i)=='z'){
                if(s.charAt(i+1)=='a'){
                    continue;
                }
            }
            if(s.charAt(i+1)-s.charAt(i)!=1){
                return false;
            }
        }
        return true;
    }

    public int findSubstringInWraproundString(String p) {
        int n = p.length();
        HashSet<String> set = new HashSet<>();
        for(int i = 0; i < n; i++){
            for(int j = i+1; j <= n; j++){
                if(isSon(p.substring(i, j))){
                    set.add(p.substring(i, j));
                }
            }
        }
        return set.size();
    }
}
~~~



### 栈与队列

#### 用栈实现队列（232）

```c
//优解
class MyQueue {
public:
    stack<int> stackA, stackB;
    /** Initialize your data structure here. */
    MyQueue()  {}
    
    /** Push element x to the back of queue. */
    void push(int x) 
    {
        stackA.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    int pop() 
    {
        if(stackB.empty())
        {
           int size = stackA.size();
           for(int i = 0; i < size; i++)
           {
               stackB.push(stackA.top());
               stackA.pop();
           }
           int top = stackB.top();
           stackB.pop();
           return top;
        }
        else
        {
            int top = stackB.top();
            stackB.pop();
            return top;
        }   
    }
    
    /** Get the front element. */
    int peek() 
    {
        if(stackB.empty())
        {
            int size = stackA.size();
            for(int i = 0; i < size; i++)
        {
            stackB.push(stackA.top());
            stackA.pop();
        }
        return stackB.top();
    }
    else
        return stackB.top();
    }
    
    /** Returns whether the queue is empty. */
    bool empty()
    {
        if(stackA.empty()&&stackB.empty())
   	    {
      	  return true;
  	    }
    	return false;
    }
};
```

 ### 树

#### 递增顺序搜索树（897）

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

#### 二叉搜索树的范围和（938）

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

#### 二叉树的中序遍历（94）

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

### 链表

#### 链表的中间节点（876）

> 巧妙使用快慢指针找到中间节点
>
> 题目要求双数节点数返回中间后一个节点

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
    ListNode* middleNode(ListNode* head) {
        ListNode* slow = head;
        ListNode* fast = head;
        while(fast->next!=nullptr && fast->next->next!=nullptr){
            fast = fast->next->next;
            slow = slow->next;
        }
        if(fast->next!=nullptr){
            slow = slow->next;
        }
        return slow;
    }
};
~~~

#### 合并两个排序的链表（剑指Offer25）

> 边比较边构造链表
>
> 注意处理一个链表为空另一不为空的情况

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
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode cur = head;
        ListNode c1 = l1, c2 = l2;
        while(c1!=null || c2!=null){
            if(c1==null){
                while(c2!=null){
                    cur.next = c2;
                    cur = cur.next;
                    c2 = c2.next;
                }
                break;
            }
            if(c2==null){
                while(c1!=null){
                    cur.next = c1;
                    cur = cur.next;
                    c1 = c1.next;
                }
                break;
            }
            if(c1.val>c2.val){
                cur.next = c2;
                cur = cur.next;
                c2 = c2.next;
            }else{
                cur.next = c1;
                cur = cur.next;
                c1 = c1.next;
            }
        }
        return head.next;
    }
}
~~~

## CodeTop

### 容易

#### 反转链表（206）

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

#### 两数之和（1）

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

#### 合并两个有序链表（21）

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

#### 最大子序和（51）

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

#### 合并两个有序数组（88）

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

#### 字符串相加（415）

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

#### 买卖股票的最佳时机（121）

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

#### 相交链表（160）

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

#### 二叉树的中序遍历（94）

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

#### 有效的括号（20）

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

#### 环形链表（141）

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

#### x的平方根（69）

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

#### 对称二叉树（101）

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

#### 路经总和（112）

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



### 中等

#### 无重复字符的最长子串（3）

> 窗口滑动，remove() 略去的字符

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

#### 二叉树的层序遍历（102）

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



#### 重排链表（143）

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

#### 链表求和（面试题 02.05）

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

#### 数组中的第k个最大元素（215）

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

#### 二叉树的最近祖先（236）

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

#### 螺旋矩阵（54）

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

#### 反转链表Ⅱ（92）

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

#### 最长回文子串（5）

> dp：动态规划
>
> 动态记录子串状态：是否回文
>
> 状态转移方程：
>
> ~~~java
> while(len > 2 && s.charAt(i)==s.charAt(j)){
>  dp[i][j] = dp[i+1][j-1];
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

#### 三数之和（15）

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

#### LRU缓存机制（146）

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



### 困难

#### 接雨水（42）

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

#### 合并K个升序链表（23）

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

## 字节2018笔试

> From Now Coder

### 纠错

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

### 设计

对于广告投放引擎， 广告库索引服务是基础服务，每次广告请求会从广告索引中找出匹配的广告创意列表。假设每一次请求会携带 地域、运营商、设备机型、网络接入方式 等信息，每个广告策略都可以设置 地域、运营商、设备机型、网络接入方式 的投放定向（即只能投放到定向匹配的请求， 比如只投放特定地域）。每个广告策略下包含N(N>=1)个广告创意。设计一个广告库索引模块， 需要支持以下几点：

1. 支持多线程广告请求可以快速的找到匹配的所有广告创意
2. 支持广告库数据的热更新
3. 支持十万级广告策略，百万级广告创意
4. 支持高并发请求

请给出广告库索引服务整体系统设计以及所使用到的数据结构设计

### 算法题Ⅰ

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

### 算法题Ⅱ

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

### 智力

附加题

存在`n+1`个房间，每个房间依次为房间`1 2 3...i`，每个房间都存在一个传送门，`i`房间的传送门可以把人传送到房间`pi(1<=pi<=i)`,现在路人甲从房间`1`开始出发(当前房间`1`即第一次访问)，每次移动他有两种移动策略：
  A. 如果访问过当前房间`i`偶数次，那么下一次移动到房间`i+1`
  B. 如果访问过当前房间`i`奇数次，那么移动到房间`pi`
现在路人甲想知道移动到房间`n+1`一共需要多少次移动
