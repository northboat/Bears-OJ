package com.oj.neuqoj.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOUtil {

    public boolean writeAns(String answer, String name, String username, int lang){
        //若为java，加上包名
        if(lang == 20800 || lang == 21100){
            answer = "package workDir;\nimport java.util.*;\n" + answer;
        }

        FileOutputStream out = null;
        //绝对路径：C:\Files\java\javaee\my-oj\Code-Src\
        //服务器路径：/java/oj/
        try {
            out = new FileOutputStream("C:\\Files\\java\\javaee\\my-oj\\Code-Src\\" + name + "/" + username + ".java");
            byte[] b = answer.getBytes();
            out.write(b);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO错误");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Integer.parseInt("nmsl");
    }
}
