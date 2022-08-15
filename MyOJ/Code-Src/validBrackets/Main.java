package workDir;
import java.io.*;
import java.util.*;

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
			boolean realAns = Boolean.parseBoolean(a.trim());
			String srcData = d.trim();
				
			
			long startTime = System.currentTimeMillis();
			Solution solution = new Solution();
			
			//the statement should be writen own
			boolean curAns = solution.validBrackets(srcData);
			if(curAns != realAns){
				System.out.println(3 + "\n" + i + "\n" + srcData + "\n" + realAns + "\n" + curAns);
				return;
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