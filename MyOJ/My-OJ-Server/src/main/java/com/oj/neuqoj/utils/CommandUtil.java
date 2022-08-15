package com.oj.neuqoj.utils;

public class CommandUtil {

    public String[][] createCommand(int langType, String username){
        switch (langType){
            case 20800:
            case 21100: return new String[][] {{"javac", "-d", ".", "Main.java", username+".java"},{"java", "workDir/Main"}};
            default: return null;
        }
    }
}
