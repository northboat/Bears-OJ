# Exception

> 记录碰到过的一些异常以及解决方法

java.util.EmptyStackException：接雨水实现单调栈时 if 条件为加 !stack.empty() ，直接进行 stack.pop() ，造成空栈异常，栈空了，还在做取操作，结果就错了

java.lang.StackOverflowError：归并排序递归调用时，（left >= right）写成 （left > right），造成栈溢出错误