package workDir;
import java.io.*;
import java.util.*;


class Node{
	int val;
	Node left;
	Node right;
	public Node() {}
	public Node(int val) { this.val = val; this.left = null; this.right = null; }
	public Node(int val, Node left, Node right) { this.val = val; this.left = left; this.right = right; }
	
	public static Node build(int[] vals){
		int n = vals.length;
		if(n == 0){ return null; }
		Node root = new Node(vals[0]);
		Deque<Node> queue = new LinkedList<>();
		queue.offer(root);
		for(int i = 1; i < n; i++){
			Node cur = queue.poll();
			//System.out.println(vals[i]);
			cur.left = new Node(vals[i++]);
			if(i >= n) { break; }
			//System.out.println(vals[i]);
			cur.right = new Node(vals[i]);
			queue.offer(cur.left);
			queue.offer(cur.right);
		}
		return root;
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
			int[] srcNum = new int[srcData.length];
			
			try{
				for(int j = 0; j < srcNum.length; j++){
					srcNum[j] = Integer.parseInt(srcData[j]);
				}
			}catch(Exception e){
				srcNum = new int[0];
			}
			
			
			Node root = Node.build(srcNum);
			
			
			
			long startTime = System.currentTimeMillis();
			Solution solution = new Solution();
			
	
			//the statement should be writen own
			int[] curAns = solution.preorderTraversal(root);
			
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
		
			//System.out.println(3 + "\n" + i + "\n" + Arrays.toString(srcData) + "\n" + Arrays.toString(realAns) + "\n" + Arrays.toString(curAns));
			for(int j = 0; j < curAns.length; j++){
				if(curAns[j] != Integer.parseInt(realAns[j])){
					System.out.println(3 + "\n" + i + "\n" + Arrays.toString(srcData) + "\n" + Arrays.toString(realAns) + "\n" + Arrays.toString(curAns));
					return;
				}
			}
			
			
			
			costTime += duration;
			
			if(duration > timeLimit){
				System.out.println(4 + "\n" + i + "\n" + Arrays.toString(srcData) + "\n" + timeLimit + "\n" + duration);
				return;
			}
		}
		long averageTime = costTime/i;
		System.out.println(0 + "\n" + i + "\n" + averageTime);	
	}
}