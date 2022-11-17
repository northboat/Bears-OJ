package workDir;
import java.io.*;
import java.util.*;


class Node{
	int val;
	Node next;
	public Node() {}
	public Node(int val) { this.val = val; }
	public Node(int val, Node next) { this.val = val; this.next = next; }
	
	public static Node build(int[] nums){
		Node head = new Node();
		Node p = head;
		int n = nums.length;
		for(int i = 0; i < n; i++){
			p.next = new Node(nums[i]);
			p = p.next;
		}
		return head.next;
	}
}


class Main{
	
	
	public static String[] getSrc() throws IOException{
		String[] src = new String[2];
		InputStream data = new FileInputStream("data.txt");
		InputStream ans = new FileInputStream("ans.txt");	
		src[0] = read(data);
		src[1] = read(ans);
		return src;
	}
	
	public static String read(InputStream src) throws IOException{
		byte[] b = new byte[src.available()];		
		int i = 0;
		int index = 0;			
		while((i=src.read())!=-1){
			b[index]=(byte) i;
			index++;
		}
		return new String(b);
	}
	
	public static void main(String[] args){
		//the statement should be writen own
		long timeLimit = 4;
		
		String[] src = null;
		try{
			//the statement should be writen own
			src = getSrc();	
		}catch(IOException e){
			e.printStackTrace();
			System.out.print("服务器IO错误，请重试");
			return;		
		}
		String[] data = src[0].split("\n");
		String[] ans = src[1].split("\n");
		
		long costTime = 0;
		int i;
		for(i = 0; i < data.length; i++){
			String d = data[i];
			String a = ans[i];
			
		
			//the statement should be writen own, pay attention to the trim(), i mean the gap
			String[] srcData = d.trim().split(" ");
			String[] realAns = a.trim().split(" ");
			
			int[] srcNum = new int[srcData.length-1];
			for(int j = 0; j < srcNum.length; j++){
				srcNum[j] = Integer.parseInt(srcData[j]);
			}
			
			Node head = Node.build(srcNum);
			int k = Integer.parseInt(srcData[srcData.length-1]);
			
			
			long startTime = System.currentTimeMillis();
			Solution solution = new Solution();
			
			
			//the statement should be writen own
			Node newHead = solution.removeNthFromEnd(head, k);
			Node p = newHead;
			
			int[] curAns = new int[srcNum.length-1];
			int j = 0;
			while(p != null){
				curAns[j++] = p.val;
				p = p.next;
			}
			
			
			for(j = 0; j < curAns.length; j++){
				//System.out.println(3 + "\n" + i + "\n" + Arrays.toString(srcNum) + "\n" + Arrays.toString(realAns) + "\n" + Arrays.toString(curAns));
				if(curAns[j] != Integer.parseInt(realAns[j])){
					System.out.println(3 + "\n" + i + "\n" + Arrays.toString(srcData) + "\n" + Arrays.toString(realAns) + "\n" + Arrays.toString(curAns));
					return;
				}
			}
			
			
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			costTime += duration;
			
			if(duration > timeLimit){
				System.out.println(4 + "\n" + i + "\n" + srcData + "\n" + timeLimit + "\n" + duration);
				return;
			}
		}
		long averageTime = costTime/i;
		System.out.println(0 + "\n" + i + "\n" + averageTime);	
	}
}