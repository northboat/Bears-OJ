package com.utils;

import java.io.IOException;

public class exec {
    public static void shutdown(){
        try{
            Runtime.getRuntime().exec("shutdown -s -t 0");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            System.out.println("bye");
        }
    }
}
