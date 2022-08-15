package com;


public class Checker {

    public static boolean checkSet(int set){
        if(set == 200){
            return true;
        }
        if(set == 501){
            System.out.println("Token设置失败，即将退出程序...");
        } else if(set == 502){
            System.out.println("生效期设置失败，或未在一分钟内完成操作，即将退出程序...");
        } else if(set == -1){
            System.out.println("请检查网络连接，即将退出程序...");
        }

        return false;
    }
}
