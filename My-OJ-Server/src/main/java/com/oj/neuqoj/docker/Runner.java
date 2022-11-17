package com.oj.neuqoj.docker;

import java.util.Map;

public interface Runner {
    /**
     *      * 获取运行结果，如果任何一条命令出错则立即返回 并在exitCode 写入错误代码，不再执行下一条命令
     *      * 运行时间和内存此时可以直接返回负数，不用再计算
     *      * 样例届时也会放置在输入文件夹中。
     *      * 友情提示：一条命令结束后 执行echo $?可以得到它的返回值 其他方案也可以
     *      * 以下命令可以得到当前容器内存大小等信息
     *      * docker stats --no-stream --format "{\"container\":\"{{ .Container }}\",\"memory\":{\"raw\":\"{{ .MemUsage }}\",\"percent\":\"{{ .MemPerc }}\"},\"cpu\":\"{{ .CPUPerc }}\"}"
     *      *
     *      * @param workPath  容器工作目录 目前都会传（/usr/codeRun/) ————> 暂时废弃
     *      * @param inputFilePath - 输入文件路径目录，需要拷贝此目录下所有文件到容器的工作目录（即/var/temp/code/20002/所有都拷贝到/usr/codeRun/) ————> 暂时废弃
     *      * @param commandLine - 待执行程序的命令行数组，多条命令，按顺序串行执行，包含完整目录(如javac /usr/codeRun/Hello.java )
     *      * @param imageType -(0 未定 |10520 python |10730 gcc:7.3 |  20800 openjdk:8 | 21100 openjdk:11| 30114 golang:1.14|用于选择镜像)
     *      * @param timeLimit - 每条命令的时间限制(单位ms, 0表示不限制)
     *      * @param memoryLimit - 每条命令的内存限制(单位KB, 0表示不限制)
     *      * @return 一个包含程序运行结果的Map<String, Object>对象（key有 "result":List<String>每条语句执行后的控制台输出,"exitCode":List<Integer>每条语句的运行后的状态代码
     *      * "usedTime: List<Integer>"，usedMemory: List<Integer>" 每条命令执行耗费的内存  其中result顺序要和命令顺序一致，如果没输出则放入“”)
     *      *
     *      */

    public Map<String, Object> judge(String workPath, String inputFilePath, String[][] commandLine,
                                   int imageType, int[] timeLimit, int[] memoryLimit) throws Exception;

}
