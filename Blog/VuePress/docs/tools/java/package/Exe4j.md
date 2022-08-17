# Exe4j

> 打包Jar为可执行EXE文件

## 一、准备设置

勾选`"JAR in EXE" mode`

给应用取个小名，设置输出路径

`Excutable type`选择`Console application`或其他

设置应用名称、图标、是否允许一次运行多个程序

点击`Advanced Options - 32bit or 64bit`，勾选`Generate 64-bit executable`

## 二、JAR包设置

点击右侧`+`号添加`JAR`包（勾选`Archive`通过目录打开）

点击右下`...`选择`Main`函数入口

设置运行环境（JDK）版本

一路下一步即可

中间有一些细节配置，可仔细阅读

## 三、善后

在准备设置中设置的输出目录找到对应`exe`文件，双击运行即可

注意，该`exe`需要机器自带符合版本要求的`JDK`环境

