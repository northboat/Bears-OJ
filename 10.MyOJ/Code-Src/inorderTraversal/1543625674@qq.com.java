package workDir;
import java.util.*;
/*
class Node{
	int val;
	Node left;
	Node right;
	public Node() {}
	public Node(int val) { this.val = val; this.left = null; this.right = null; }
	public Node(int val, Node left, Node right) { this.val = val; this.left = left; this.right = right; }
}
*/


class Solution{
	
	private List<Integer> list;
	
	public int[] inorderTraversal(Node root){
		list = new ArrayList<>();
		dfs(root);
		int n = list.size();
		int[] res = new int[n];
		for(int i = 0; i < n; i++){
			res[i] = list.get(i);
		}
		return res;
	}
	
	public void dfs(Node node){
		if(node == null){ return; }
		dfs(node.left);
		list.add(node.val);
		dfs(node.right);
	}
}