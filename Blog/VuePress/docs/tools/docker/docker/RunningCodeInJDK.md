# Running code in jdk

用bash命令编译执行java程序

## 编译

~~~java
package com;
public class HelloWorld{
    public static void main(String[] args){
        System.out.println("HelloWorld");
    }
}
~~~

javac -d . HelloWorld.java：在当前目录下生成一个 com 的文件夹，将.class 文件统一编译到 com 文件夹下

## 运行

java com/HelloWorld：运行 HelloWorld 中的主程序，该主程序将自动链接包（com）中的其他类，完成多个类的统一运行

这种执行将自动导入程序中导入的java自带类包（如ArrayList）

