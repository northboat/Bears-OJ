# Sort

> ###### 排序数组（912）

## 1、冒泡排序

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

## 2、选择排序

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



## 3、插入排序

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

## 4、二分插入排序

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

## 5、快速排序

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

## 6、堆排序

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

## 7、归并排序

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







