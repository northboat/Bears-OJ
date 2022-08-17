---
title: DEBUG、顺序、寻址和分支
date: 2022-4-9
tags:
  - Assembly
---

> 基于x8086 16位寄存器的基础汇编程序编写

## Debug和基本的汇编指令

**Debug**

Debug是DOS、Windows都提供的实模式(8086方式)程序的调试工具

编辑`DOSBox 0.74 Options.bat`文件可以修改窗口大小和设置预设命令

~~~bash
mount c c:\tool\dosbox\virtualc
c:
~~~

在windows的快捷方式属性目标后添加`-noconsole`可以去掉运行时后面的黑框框

使用它，可以查看CPU各种寄存器中的内容、内存的情况和在机器码级别跟踪程序的运行

-  用Debug的R命令查看、改变CPU寄存器的内容
- 用Debug的D命令查看内存中的内容
- 用Debug的E命令改写内存中的内容
- 用Debug的U命令将内存中的机器指令翻译成汇编指令
- 用Debug的T命令执行一条机器指令
- 用Debug的A命令以汇编指令的格式在内存中写入一条机器指令

win10中需要使用DOSBox模拟debug环境

DOSBox挂载虚拟C盘

~~~bash
mount c c:\Programming\DOSBox\VirtualC

//进入C盘
C:
~~~

将`debug.exe、link.exe、masm.exe`等软件通通放在这个虚拟C盘下以供使用

**寻址方式**

立即寻址方式：MOV AX,1234H

寄存器寻址方式： MOV AX,BX

存储器寻址方式：

- 直接寻址方式：

  存储器读操作：MOV AX,DS:[2000H]

  存储器写操作：MOV DS:[4000H],AX

  符号地址寻址：MOV AX,[VALUE]

  段超越：MOV AX,ES:[VALUE]

- 寄存器间接寻址方式：

  操作数的物理地址为：`(DS)×10H＋(SI)/(BX)/(DI)`或`(SS)×10H＋(BP)`

  如：`MOV AX,[BX]`

- 寄存器相对存储方式：

  操作数的物理地址为：`(DS)×10H＋(SI)/(BX)/(DI)+8位（16位）位移量`或`(SS)×10H＋(BP)+8位（16位）位移量`

  如：`MOV AX,[BX+1234H]`

- 基址变址寻址方式：

  操作数的物理地址为：`(DS)×10H+(BX)+(DI)/(SI)`或`(SS)×10H+(BP)+(DI)/(SI)`

  如：`MOV AX,[BX+SI]`

- 相对基址变址寻址方式

  操作数的物理地址为：`(DS)×10H+(BX)+(DI)/(SI)+8位（16位）位移量`或`(SS)×10H+(BP)+(DI)/(SI)+8位（16位）位移量`

  如：`MOV AX,[BX+SI].MASK`

基本汇编指令：

- `mov`指令在寄存器和内存之间进行数据移动
- `add`指令把一个数加到前面
- `sub`指令，两个数相减结果存在前面

### 查看和修改寄存器的值

使用R命令查看和修改8086中的寄存器。将AX值修改为`2021H`，BX值修改为`实验课上课日期H`，CX值修改为`当前时间H`,DX值修改为`学号后4位H`

~~~assembly
debug
-r ax;查看并修改单个寄存器的值
AX 0000
:2021

-r bx
BX 0000
:0401

-r cx
CX 0000
:2239

-r dx
DX 0000
:2143

-r;查看所有寄存器的值
~~~

### Debug显示字符串

实际上就是改写`DosBox`窗口某一位置的像素点为字符串

~~~assembly
-e b800:0724 32 0f 30 0f 32 0f 30 0f 31 0f 32 0f 31 0f 34 0f 33 0f
-e b800:0726 58 0f 5a 0f 54 0f
~~~

其中`-e`后的的参数为坐标，每个字符用`0f`作为结束符

### 查看生产日期

`DOSBox`将自己的生产日期存在内存的`F000-FFF0h`中，使用`d`命令查看内存值

~~~
-d f000:fff0
~~~

### 运算和存储

#### 减法

使用a指令编写汇编代码，使用t指令逐步执行

~~~assembly
a

mov bx, 0010
sub ax, bx
mov [0016], bx

t
t
t
~~~

#### 加法

与减法同理

~~~assembly
a

mov ax, 0034
add ax, 0065
es:
mov [20], ax

t
t
t
~~~

#### 转存

将AX寄存器中的1234H写入数据段的2号单元，读出3号单元的12H传送给BL寄存器

~~~assembly
a

mov ax, 1234
mov [2], ax
mov bl, [3]
~~~

## 顺序程序设计

伪指令

- segment: 划分程序段
- assume: 指定寄存器和对应段名
- start: 表示程序的开始，跟冒号
- end start: 通知汇编程序整个程序结束
- 数据定义伪指令: db定义字节电源，dw定义字单元，dd定义双字单元
- 赋值伪指令: 单元名字 equ 常数/字符串/表达式，equ不允许重复定义

中断调用指令: `int 21H`

传送指令：`mov`

算术指令：

- add
- sub

Dos功能调用: 

~~~assembly
AH=1/2//9/10
INT 21H
~~~

- 1：键盘输入并显示
- 2：屏幕显示一个字符
- 9：屏幕显示字符串
- 10：获取键盘输入字符串

非压缩BCD码加法调整：`AAM`

非压缩BCD码乘法调整：`AAA`

### 输出输入字符串

实现`"Hello World!"`的输入和输出

~~~assembly
;输出输入的字符串
data segment
	buffer db 70,?,70 dup(0)
data ends
code segment
assume cs:code,ds:data
start:
	;获取键盘输入
	mov ax,data
	mov ds,ax
	mov dx,offset buffer
	mov ah,10
	int 21h
	
	;添加换行
	mov buffer[0], 0dh
	;找到字符串的末尾并添加字符串结束符'$'
	mov bl,buffer[1]
	mov bh,0
	add bx,2
	mov buffer[bx],'$'
	;0ah和0dh配合连着使用可以达到换行的效果
	mov buffer[1],0ah
	
	;打印输出dx内容，即buffer
	mov ah,9
	int 21h
	mov ah,4ch
	int 21h
code ends
end start
~~~

### 计算并打印结果

实现Y=2X+3，X是一位十进制数。要求X从键盘输入，在下一行上显示“Y=2X+3=”以及十进制计算结果

- `aam`：在乘法运算后使用`aam`表示向高位进位，如`bl`进到`bh`，`bl`保存低位结果，`bh`保存进位结果，若不进位则只会保留低位结果
- `aaa`：在加法运算后使用`aam`表示向高位进位，若不进位则只会保留低位结果

~~~assembly
data segment
	info db "nmY=2X+3=$";
data ends
code segment
	assume ds:data,cs:code
start:
	mov ax,data
	mov ds,ax
	;将info的首两个字符改为0dh和0ah，达到换行的效果
	mov bx,0
	mov info[bx],0dh
	inc bx
	mov info[bx],0ah
	;获取单个字符输入，存于al
	mov ah,1
	int 21h
	;调用9号功能打印info
	mov dx,offset info
	mov ah,9
	int 21h
	;将输入数字-30，由ascii码变为数字
	mov ah,0
	sub al,30h
	;乘法，imul bx即ax*bx
	mov bx,2
	imul bx
	aam ;乘法进位
	;加法，add ax,3即ax += 3
	add ax,3
	aaa ;加法进位 
	add ax,3030h ;高低位均+30h，变回ascii码输出
	;输出计算结果
	mov bx,ax ;将结果从ax移到bx
	mov dl,bh ;将高位bh移到dl输出
	mov ah,2 ;调用2号功能打印单个字符
	int 21h
	mov dl,bl ;将低位bl移到dl输出
	int 21h
	mov ah,4ch
	int 21h
code ends
end start
~~~

## 查表

数据定义伪指令：

- DB 定义字节单元

- DW定义字单元

- 可以一次定义多个存储单元，以逗号隔开，像数组一样

  如`score dw 4,7,9,12`

地址计数器`$`：表示当前的偏移地址值

寻址指令：

- 直接寻址：`MOV AX,DS:[2000H]`
- 寄存器间接寻址：`MOV AX,[BX] `
- 寄存器相对寻址：`MOV AX,[BX+1234H]`等
- 基址变址寻址：`MOV AX,[BX+SI]`
- 相对基址变址寻址：`MOV AX,[BX+SI+1234H]`等多种形式

### 汇编实现各种寻址方式

用汇编语言定义一个二维数组，这个二维数组中存储的是两个班级的学生的成绩，每个班各有5名学生，成绩用DW方式定义

查找某一个同学的成绩。分别使用直接寻址，寄存器间接寻址，寄存器相对寻址，基址变址寻址，相对基址变址寻址完成查找成绩操作

~~~assembly
;查分数表
data segment
score dw 100,91,98,97,93
      dw 99,94,96,92,95
data ends
code segment
	assume cs:code,ds:data
start:
	mov ax,data
	mov ds,ax; ds用来存放2班3号学生的成绩
	;直接寻址
	mov dx,ds:[0eh]
	mov bx,0eh   
    ;寄存器间接寻址
	mov dx,[bx]
	mov bp,0eh
	;段超越
	mov dx,ds:[bp]
	;寄存器相对寻址
	mov si,0eh
	mov dx,score[si]
	;基址变址寻址
	mov bx,offset score
	mov di,0eh
	mov dx,[bx][di]
	;相对基址变址寻址
	mov bx,10
	mov si,4
	mov dx,score[bx][si]
	mov ah,4ch
	int 21h
code ends
end start
~~~

### 根据学号查询学生姓名

将学生名字按照学号顺序存储在内存中，根据学号将姓名查找出来。

姓名定义有两种方式

- 第一种方式类似于一维字符数组
- 第二种方式类似于指针数组

#### 一维字符数组查询

固定每一个学生姓名的长度为12（换行+字符串），缺的用空格补充

~~~assembly
;通过相对地址寻址的方式查学生姓名表
data segment
	stu0 db 0dh,0ah,'zhuo yu  $'
	stu1 db 0dh,0ah,'li ming  $'
	stu2 db 0dh,0ah,'li hua   $'
	stu3 db 0dh,0ah,'zhou tong$'
	stu4 db 0dh,0ah,'xiao ming$'
	tip  db 0dh,0dh,'Please input stu number:$'
data ends
code segment
	assume cs:code, ds:data
start:
	mov ax, data
	mov ds, ax
	;打印提示信息
	mov dx, offset tip;
	mov ah, 9
	int 21h; 提示输入学号
	;获取输入
	mov ah, 1
	int 21h
	sub al, 30h; ASCII码转数字
	
	;寻址
	mov cl, 12; 每个名字包括换行共占12个字符
	mul cl
	;打印查询到的名字
	mov dx, ax
	mov ah, 9
	int 21h
	mov ah, 4ch
	int 21h
code ends
end start
~~~

#### 指针查询

维护一个table数组，储存各学生姓名字符串的首地址，使用相对地址寻址，即`table[偏移量]`，查询学生姓名字符串

~~~assembly
;table相当于一个指针数组，每个指针占用2个字节，根据输入找到相应指针，相对寻址找到数据
data segment
	table dw stu0,stu1,stu2,stu3,stu4
	stu0  db 0dh,0ah,'huaxue$'; 0dh为换行
	stu1  db 0dh,0ah,'shuxue$'
	stu2  db 0dh,0ah,'yingyu$'
	stu3  db 0dh,0ah,'yuwen$'
	stu4  db 0dh,0ah,'wuli$'; 长度无需一样
	info  db 0dh,'Please input stu no:$'
data ends
code segment
	assume cs:code,ds:data
start:
	mov ax, data
	mov ds, ax
	mov dx, offset info   
	mov ah, 9     
	int 21h
	mov ah, 1; 输入学号
	int 21h
	sub al, 30h; 将学号ascii码转数字
	mov cl, 2; 在table中stu?占用两个字节，一个字（dw）为两个字节
	mul cl; 相对table的偏移地址
	mov bx, ax  
	mov dx, table[bx] ;寄存器相对寻址
	mov ah, 9; 显示名字
	int 21h
	mov ah, 4ch
	int 21h
code ends
end start
~~~

## 分支程序设计

条件判断：

- 比较指令：`cmp ax, 1dh`（compare）

  返回`ax-1dh`，有大于、等于、小于、大于等于、小于等于`0`共五种情况

- 跳转指令

  - 无条件跳转：`jmp`
  - 小于0跳转：`js`（jump smaller）
  - 大于0跳转：`jns`（jump not smaller）
  - 等于0跳转：`je`（jump equal）
  - 不相等跳转：`jne`（jump not equal）
  - 大于等于0跳转：`jnc`
  - 小于等于0跳转：`jbe`

### 进制转换

输出提示信息“DEC=”，输入十进制数0-255。输出提示信息“HEX=”，用取余数的方法将刚才输入的0-255，显示成相应的十六进制数

~~~assembly
;0-255的十进制转十六进制
data segment
    tip db 0DH,0AH,'DEC=$'
    info db 'HEX=$'
    res db 3 dup(0)
data ends
code segment
	assume cs:code,ds:data
start: 
    mov ax,data
    mov ds,ax
let:; 输入提示
    mov dx,offset tip
    mov ah,9    
    int 21h
    mov si,0;储存输入数字位数
let0:; 输入字符并做出判断
    mov ah,1
    int 21h
    cmp al,1bh;判断ESC
    je let5;相等则跳转
    cmp al,0dh;判断回车
    je let1
    sub al,30h;减去ASCII码存入res中
    mov res[si],al
    inc si
    jmp let0;继续输入
let1:
    mov dx,offset info;输出提示信息
    mov ah,9
    int 21h
    mov bx,0
    mov di,0
    cmp si,1;判断位数
    je let2;若为1位数，跳转至let2
    cmp si,2
    je let3;若为2位数，跳转至let3
    ;当为3位数，继续执行let1
    mov al,res[di];di=0，将res最高位放在al中
    inc di;di=1
    mov cl,100
    mul cl;将al*100倍获得百位数
    add bx,ax;将百位结果加在bx中
let3:
    ;若从let1顺序执行，res为三位数，此时di=1，获取输入数字的十位
    ;若从let1跳转过来，res为两位数，此时di=0，获取输入数字的十位
    mov al,res[di]
    ;di=1或di=2
    inc di
    mov cl,10
    mul cl;AX=AL*CL
    add bx,ax;将十位结果加在bx上
let2:
    mov al,res[di];取出输入数字的个位
    mov ah,0
    add bx,ax;结果加在BX上
    
	;10进制转16进制
	;ax÷cl
	;结果中商是16进制的低位，余数为16进制的高位
	;即ah中为16进制低位，al中为16进制高位
    mov ax,bx;将输入数字转存在ax中
    mov cl,16
    div cl;除以
    ;判断高位是否小于等于9，若大于则要转换为对应字母，要多加7
    cmp al,9;
    jbe let6;小于等于则跳转
    add al,7
let6:
    add al,30h;将余数（16进制高位）转为ascii码，准备输出
    mov cl,ah;转存商（16进制低位）到cl中
    mov dl,al;转存余数到dl中
    ;输出余数，即16进制高位
    mov ah,2
    int 21h
    mov al,cl;将cl中的商转存到al
    cmp al,9;判断是否小于等于9，若大于同样要加7转为相应字母的ascii码
    jbe let7;小于等于则跳转
    add al,7
let7:;输出16进制低位
    add al,30h
    mov dl,al
    mov ah,2
    int 21h
    jmp let
let5:;退出程序
    mov ah,4ch
    int 21h
code ends
end start
~~~

### Switch实现

在数据段中建立分支表TABLE，保存分支转移的标号（函数指针），根据用户输入的`0-3`，计算出分支标号在TABLE表中的地址，采用`JMP TABLE[BX]`实现多路转移，即`switch`功能

三个程序分别为：

- 输出HelloWorld
- 输入一个大写（小写）字母，转化为小写（大写）字母并输出。
- 实现Y=2X+3,X是一位十进制数。要求X从键盘输入，在下一行上显示“Y=2X+3=”以及十进制计算结果

左移指令`shl bx, 1`：将`bx`对应的二进制数左移一位，相当于乘以2

跳转指令`jz`：结果为0则跳转

逻辑与/或指令`and/or`：`and/or ax,bx`按位对两个二进制数进行与/或操作

- 使用and指令将字母小写转大写

  ~~~
  0 1 1 0 0 0 0 1 = 61h ('a')
  0 1 0 0 0 0 0 1 = 41h ('A')
  ~~~

  可以发现小写大写字母ascii码二进制只有第六位不同，将小写字母第六位与0进行与操作得0

  ~~~assembly
  and ax,01000001b
  ~~~

- 同理可以使用or指令将大小字母转成小写

  ~~~assembly
  or ax,00100000b
  ~~~

测试指令`test`：`test ax,bx`将二者进行与操作，但不改变值

- test的一个非常普遍的用法是用来测试一方寄存器是否为空

  若`ecx`为零，则这一步test将设置ZF零标志为1，逻辑与结果为0，`jz`跳转

  ~~~assembly
  test ecx, ecx
  jz somewhere
  ~~~

- 若`ax`为大写字母，第六位为0，二者与结果为0，ZF零标志置为1，jz跳转

  ~~~
  test ax, 00100000b
  jz somewhere
  ~~~

传递指令`lea`：`lea ds,info`等同于`mov ds,offset info`，即传递有效地址

~~~assembly
;分支程序
;0退出
;1输出hello world
;2大小写字母转换
;输入x，计算y=2x+3
data segment
    table dw prog0,prog1,prog2,prog3
	info  db 0ah,0dh,'Please input 0-3:$'
	hello db 0ah,0dh,'Hello World!$'   
	func  db 0dh,0dh,'Y=2X+3=$' 
	bye   db 0ah,0dh,'goodbye!$'
data ends
code segment
    assume cs:code,ds:data
start:
    mov ax,data
    mov ds,ax
let0:                 ;输出提示信息，用户输入0-3
    lea dx,info
    mov ah,9            
    int 21h           ;9号功能输出字符串提示用户输入
    
    mov ah,1          ;输入
    int 21h
    sub al,30h        ;减去ASCII码转换输入信息为数字0-3
    mov ah,0
    shl ax,1          ;输入信息*2，用于找table表中对应的标号的偏移地址
    mov bx,ax
    jmp table[bx]     ;根据输入序号，在table中进行相对地址寻址跳转至相应程序段
    
prog1:                ;1号程序:调用9号功能输出HelloWorld
    mov dx,offset hello
	mov ah,9
    int 21h
    jmp let0          ;返回继续输入0-3
    
prog2:                ;2号程序，转换输入字母大小写并输出
	mov dl,0ah
	mov ah,2		 ;调用2号功能输出一个换行
	int 21h      
	mov ah,1          ;调用1号功能输入
	int 21h
	test al,20h       ;测试指令，大小写字母ASCII码第6位不同
	jz let1           ;大写，则转移
	and al,0dfh       ;小写变为大写
	jmp let2
let1:
	or al,20h         ;大写变为小写
let2: ;输出转换后的字母
	mov dl,al
	mov ah,2          
	int 21h
	jmp let0 	;返回继续输入0-3

prog3:  		;3号程序: y=2x+3方程
	mov dl,0ah
	mov ah,2
	int 21h		;输出换行
	mov ah,1	;输入数字x
	int 21h
	mov bl,al	;将输入数字转存于bl
	lea dx,func	;lea传递有效地址
	mov ah,9
	int 21h		;输出'Y=2X+3='
	sub bl,30h
	mov al,2
	imul bl
	aam		;乘法进位
	mov bx,3
	add ax,bx
	aaa		;加法进位
	add ax,3030h	;变成ascii码形式准备输出
	;依次打印高低位显示加法结果
	mov bx,ax	
	mov dl,bh
	mov ah,2
	int 21h		
	mov dl,bl
	int 21h		
    jmp let0        ;返回继续输入0-3      
    
prog0:              ;程序终止
	lea dx,bye
	mov ah,9
	int 21h
    mov ah,4ch
    int 21h
code ends
end start
~~~





