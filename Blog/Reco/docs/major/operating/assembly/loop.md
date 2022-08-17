---
title: 循环、子程序、模块化和中断
date: 2022-4-9
tags:
  - Assembly
---

## 循环程序设计

两种循环结构：

- DO WHILE：用`CMP`指令和条件转移指令构成
- DO UNTIL：用`loop,loope,loopne`这三种指令实现

串处理指令：

- `movsb`
- `cmps`
- `scas`
- `lods`
- `stos`

和串处理指令联合使用的重复前缀指令：

- `rep`
- `repz`
- `repnz`

伪指令：`EQU`

操作符：`'$'`

交换指令：`xchg`

跳转指令：

- 大于等于：`jge`（jump greater or equal）
- 小于跳转：`jb`

### 统计Ⅰ

分别统计3个班级中成绩优秀的人数和不及格的人数。三个班级的分数为一个`3xn`的二维数组。分别用两个一维数组存放各班90分以上和60分以下的人数（计数）

- 由于总共三个班，用于存储的一维数组定义为`good db 3 dup(0)`，`good[0]`表示第一个班的优秀成绩数量，以此类推
- 在寻找二维数组位置时，使用`score[外层循环数*8][内层循环数]`的形式进行定位

~~~assembly
data segment
	score db 23,45,60,90,91,88,66,77
		  db 95,86,90,95,100,0,42,23
		  db 65,64,59,91,92,78,59,44
	good  db 3 dup(0)
	fail  db 3 dup(0)
	sn 	  db 0
	cn    db 0
data ends
code segment
	assume cs:code, ds:data
start:
	mov ax, data
	mov ds, ax
	
outer:			;外层循环
	cmp cn, 3	;与班级数比较
	jae exit	;若大于等于则跳转，即外层循环完退出程序
	mov sn, 0	;初始化内层循环
	
inner:			;内层循环
	cmp sn, 8	;与班级人数比较
	jae trans2	;大于等于则跳转至下一层外层循环
	mov al, cn
	mov cl, 8	;班级号乘8获取班级成绩首地址
	mul cl
	mov bx, ax	;将班级首地址存入bx
	mov al, sn	;将班级序列号转存入al
	mov ah, 0
	mov si, ax	;将班级序列号转存入si
	cmp score[bx][si], 90
	jb  less	;小于则跳转,大于等于则继续
	;处理大于等于90分数，即优秀成绩
	mov al, cn	;外层循环数=班级号
	mov ah, 0
	mov di, ax	;第几个班
	inc good[di]
	jmp trans1	;转入下一次内层循环过渡
	
less:	;处理小于90分数
	cmp score[bx][si], 60	;与60比较
	jae trans1	;大于等于则跳转，进行下一层内层循环
	;处理小于60分，即不及格成绩
	mov al, cn
	mov ah, 0
	mov di, ax
	inc fail[di]
	
trans1:			;内层循环跳转过渡
	inc sn
	jmp inner
trans2:			;外层循环跳转过度
	inc cn
	jmp outer
exit:
	mov ah, 4ch
	int 21h

code ends
end start
~~~

### 统计Ⅱ

分别统计3个班级中成绩优秀的成绩和不及格的成绩，与统计Ⅰ同理，但存的是分数，用于存储一维数组需要更多的字节空间

- 共三个班，每个班8个分数，于是用于存储分数的数组需要定义为`good db 24 dup(0)`，以防越界
- 在寻找一维数组当前储存位置时，需要维护一个字节变量`dn`，每增加一个成绩，`dn++`，实时更新下标

~~~assembly
data segment
	score db 23,45,60,90,91,88,66,77
		  db 95,86,90,95,100,0,42,23
		  db 65,64,59,91,92,78,59,44
	good  db 24 dup(0)
	fail  db 24 dup(0)
	sn 	  db 0
	cn    db 0
	dn	  db 0
	bn	  db 0
data ends
code segment
	assume cs:code, ds:data
start:
	mov ax, data
	mov ds, ax
	
outer:			;外层循环
	cmp cn, 3	;与班级数比较
	jae exit	;若大于等于则跳转，即外层循环完完成存储
	mov sn, 0	;初始化内层循环
	
inner:			;内层循环
	cmp sn, 8	;与班级人数比较
	jae trans2	;大于等于则跳转至下一层外层循环
	mov al, cn	;cn为第几个班
	mov cl, 8	;班级号乘8获取班级成绩首地址
	mul cl
	mov bx, ax	;将班级首地址存入bx
	mov al, sn	;sn为班级第几号学生
	mov ah, 0
	mov si, ax	;班级序列号存入si
	cmp score[bx][si], 90
	jb  less	;小于则跳转,大于等于则继续
	;处理大于等于90的分数
	;dn是good数组的下标，在数据段初始化为0
	mov al, dn
	mov ah, 0
	mov di, ax	;将good数组当前下标存入di
	mov al, score[bx][si];将分数存入al
	mov good[di], al	;将分数存入good数组
	inc dn		;dn++
	jmp trans1	;转入下一次内层循环
	
less:	;处理小于90分数
	cmp score[bx][si], 60	;与60比较
	jae trans1	;大于等于则跳转，进行下一层内层循环
	;bn是fail数组的下标，数据段初始化为0
	mov al, bn	
	mov ah, 0
	mov di, ax
	mov al, score[bx][si]	
	mov fail[di], al	;将不及格分数存入fail数组
	inc bn
	
trans1:			;内层循环跳转过渡
	inc sn
	jmp inner
trans2:			;外层循环跳转过度
	inc cn
	jmp outer
exit:
	mov ah, 4ch
	int 21h

code ends
end start
~~~

### 冒泡排序

在统计Ⅱ的基础上，对优秀成绩和不及格成绩的两个一维数组进行降序排序

- 统计的步骤和统计Ⅱ完全一致，在跳出外层循环后进入`sort`段，对两个收集到的一维数组进行排序

这个冒泡排序不太一样，他总是在相邻的两个变量之间比较，从第0位比较到最后第n位，这样的比较共n轮，实现排序

~~~assembly
data segment
	score db 23,45,60,90,91,88,66,77
		  db 95,86,90,95,100,0,42,23
		  db 65,64,59,91,92,78,59,44
	good  db 24 dup(0)
	fail  db 24 dup(0)
	sn 	  db 0
	cn    db 0
	dn	  db 0
	bn	  db 0
data ends
code segment
	assume cs:code, ds:data
start:
	mov ax, data
	mov ds, ax
	
outer:			;外层循环
	cmp cn, 3	;与班级数比较
	jae sort	;若大于等于则跳转，即外层循环完开始排序
	mov sn, 0
	
inner:			;内层循环
	cmp sn, 8	;与班级人数比较
	jae trans2	;大于等于则跳转至下一层外层循环
	mov al, cn
	mov cl, 8	;班级号乘8获取班级成绩首地址
	mul cl
	mov bx, ax	;将班级首地址存入bx
	mov al, sn	;sn为班级第几号学生
	mov ah, 0
	mov si, ax	;班级第几个学生存入si
	cmp score[bx][si], 90
	jb  less	;小于则跳转,大于等于则继续，处理大于等于90分数
	mov al, dn	;dn是good数组的下标
	mov ah, 0
	mov di, ax	;good数组下标
	mov al, score[bx][si]
	mov good[di], al
	inc dn
	jmp trans1	;转入下一次内层循环
	
less:	;处理小于90分数
	cmp score[bx][si], 60	;与60比较
	jae trans1	;大于等于则跳转，进行下一层内层循环
	mov al, bn	;bn是fail数组的下标
	mov ah, 0
	mov di, ax
	mov al, score[bx][si]	
	mov fail[di], al	;将不及格分数存入fail数组
	inc bn
	
trans1:			;内层循环跳转过渡
	inc sn
	jmp inner
trans2:			;外层循环跳转过度
	inc cn
	jmp outer
	
sort:
	;排列优秀成绩
	;dn为下标数，即外层循环执行次数
	;将外层循环执行次数转存到cx中
	;实际上这里的循环是loop(cx)，当cx=0，退出
	mov cl, dn
	mov ch, 0
	dec cx
loop1:	
	;cx入栈
	push cx
	;初始化内层循环，从第一个开始比较
	mov bx, 0
loop2:
	mov al, good[bx]
	cmp al, good[bx+1]
	jge next1;jump greater or equal 大于等于跳转
	xchg al, good[bx+1];两个地址交换值
	mov good[bx], al
next1:
	add bx, 1
	loop loop2
	pop cx
	loop loop1
	
	;排列不及格成绩
	mov cl, bn
	mov ch, 0
	dec cx
loop3:
	push cx
	mov bx, 0
loop4:
	mov al, fail[bx]
	cmp al, fail[bx+1]
	jge next2
	xchg al, fail[bx+1]
	mov fail[bx], al
next2:
	add bx, 1
	loop loop4
	pop cx
	loop loop3
	
exit:
	mov ah, 4ch
	int 21h

code ends
end start
~~~

## 子程序设计

伪指令：

- `proc`
- `endp`
- `near`
- `far`

子程序调用：

- `call`
- `ret`

子程序传递参数的三种方式：寄存器，内存单元，堆栈

设计一个程序，实现`inputn`输入姓名，`inputs`输入成绩， `sort`按成绩排序，`print`打印排序名单四个功能，使用`proc`的方式实现

- 利用10号功能实现字符串输入，实现输入姓名和成绩的录入
- 利用9号功能实现打印功能

### 简易成绩管理系统

#### 数据段定义和主程序

~~~assembly
data segment 
	nameA db 5 dup(13,?,13 dup('$'))
	scoreAsc db 5 dup(4 dup('$'))
	rank dw 0,1,2,3,4
	scoreN dw 5 dup(0)
	n db 0  	;n代表学号
	sn dw 5 	;sn代表学生数量
	info1 db 0dh,0ah,'name:$'
	info2 db 0dh,0ah,'score:$'
	nl db 15
	sl db 4
	x db 3 dup(?)
data ends

stack segment stack
	dw 512 dup(0)
stack ends
code segment
	assume cs:code,ds:data
start:
	mov ax,data
	mov ds,ax
	;初始化录入次数
	mov cx,sn
loopm:
	lea dx,info1;打印输入姓名提示信息
	mov ah,9
	int 21h 
	call inName
	lea dx,info2;打印输入成绩提示信息
	int 21h
	call inScore
	inc n;记录学号
	loop loopm
	call sort
	call dis
	mov ah,4ch
	int 21h
code ends
end start
~~~

#### 输入子程序

##### 输入姓名

~~~assembly
inName proc ;输入名字
	push ax
	push bx
	push cx
	push dx
	mov al,n
	mov cl,nl
	mul cl
	mov dx,offset nameA
	add dx,ax
	mov ah,10
	int 21h
	mov bx,dx
	mov byte ptr [bx],0dh
	inc bx 
	mov byte ptr [bx],0ah
	pop dx
	pop cx
	pop bx
	pop ax
	ret
inName endp
~~~

##### 输入成绩

~~~assembly
inScore proc ;输入成绩
	push ax
	push bx
	push cx
	push dx
	push bp
	push si
	push di
	mov al,n
	mov cl,sl
	mul cl
	mov bp,offset scoreAsc
	add bp,ax
let0:
	mov ah,1
	int 21h
	cmp al,0dh
	je let1
	mov ds:[bp][si],al
	sub al,30h
	mov x[si],al
	inc si
	jmp let0
let1:
	mov bx,0
	mov di,0
	cmp si,1
	je let2
	cmp si,2
	je let3
	mov al,x[di]
	mov cl,100
	mul cl
	add bx,ax
	inc di
let3:
	mov al,x[di]
	mov cl,10
	mul cl
	add bx,ax
	inc di
let2:
	add bl,x[di]
	mov al,n
	mov cl,2
	mul cl
	mov si,ax
	mov scoreN[si],bx
	pop di
	pop si
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	ret
inScore endp
~~~

#### 排序子程序

~~~assembly
sort proc ;排序
	push ax
	push bx
	push cx
	mov cx,sn
	dec cx
	loopS1:
	push cx
	mov bx,0
	loopS2:
	mov ax,scoreN[bx]
	cmp ax,scoreN[bx+2]
	jae cSort
	xchg ax,scoreN[bx+2]
	mov scoreN[bx],ax
	mov ax,rank[bx]
	xchg ax,rank[bx+2]
	mov rank[bx],ax
	cSort:
	add bx,2
	loop loopS2
	pop cx
	loop loopS1
	pop cx
	pop bx
	pop ax
	ret
sort endp
~~~

#### 输出子程序

~~~assembly
dis proc	;打印结果
	push ax
	push bx
	push cx
	push dx
	push di
	mov cx,sn
	mov di,1
	mov bx,0
loopDis:
	mov dx,di
	add dl,30h	
	mov ah,2
	int 21h
	mov ax,rank[bx]
	mov dl, nl
	mul dl
	mov dx,offset nameA
	add dx,ax
	mov ah,9
	int 21h
	mov dl,0dh
	mov ah,2
	int 21h
	mov dl,0ah
	int 21h
	mov ax,rank[bx]
	mov dl,sl
	mul dl
	mov dx,offset scoreAsc
	add dx,ax
	mov ah,9
	int 21h
	mov dl,0dh
	mov ah,2
	int 21h
	mov dl,0ah
	int 21h
	add bx,2
	inc di
	loop loopDis
	pop di
	pop dx
	pop cx
	pop bx
	pop ax
	ret
dis endp
~~~

## 模块化程序设计

将子程序设计中的各个功能拆分为不同文件，在主程序中进行调用，实现与二相同的功能

### 递归阶乘

`ret n`：子程序返回，同时弹出堆栈中n个字节

~~~assembly
;return n*fac(n-1);
;使用变量ax储存乘法结果
stack segment stack
	dw 512 dup(?)
stack ends
code segment
	assume cs:code,ss:stack
start:
	mov ax,5
	push ax		;ax入栈
	call fac
	mov ah,4ch
	int 21h
fac proc
	mov bp,sp	;保存栈顶指针
	mov bx,[bp+2]	;取出栈顶元素
	cmp bx,1	;如果是1的话就返回
	jne next	;小于等于则跳转
	mov ax,1
	jmp exit
next:
	dec bx		;bx-1入栈，进入下一层递归
	push bx
	call fac
	mov bp,sp
	mov bx,[bp+2]
	mul bl		;ax=al*bl al=fac(n-1) bl=n
exit:
	ret 2		;出栈，弹出两个字节
fac endp
code ends
end start
~~~

### 分文件编写成绩管理

`public`：将此模块的过程定义为全局变量以供其他模块使用

`extrn`：引入外部程序

`include`：伪指令，调入宏

#### 主程序

~~~assembly
extrn sort:near, inName:near, inScore:near, dis:near
data segment common 'data'
	nameA db 5 dup(13,?,13 dup('$'))
	scoreAsc db 5 dup(4 dup('$'))
	rank dw 0,1,2,3,4
	scoreN dw 5 dup(0)
	n db 0  	;n代表学号
	sn dw 5 	;sn代表学生数量
	info db 0dh,0ah,'name:$'
	info1 db 0dh,0ah,'score:$'
	nl db 15
	sl db 4
	x db 3 dup(?)
data ends

stack segment stack
	dw 512 dup(0)
stack ends

code segment public 'code'
	assume cs:code,ds:data
start:
	mov ax,data
	mov ds,ax
	mov cx,sn
	loopm:
	mov dx,offset info
	mov ah,9
	int 21h 
	call inName
	mov dx,offset info1
	int 21h
	call inScore
	inc n
	loop loopm
	call sort
	call dis
	mov ah,4ch
	int 21h
code ends
end start
~~~

#### 输入程序

##### 输入姓名

~~~assembly
public inName
data segment common 'data'
	nameA db 5 dup(13,?,13 dup('$'))
	scoreAsc db 5 dup(4 dup('$'))
	rank dw 0,1,2,3,4
	scoreN dw 5 dup(0)
	n db 0  	;n代表学号
	sn dw 5 	;sn代表学生数量
	info db 0dh,0ah,'name:$'
	info1 db 0dh,0ah,'score:$'
	nl db 15
	sl db 4
	x db 3 dup(?)
data ends

code segment public 'code'
	assume cs:code,ds:data
inName proc ;输入名字
	push ax
	push bx
	push cx
	push dx
	mov al,n
	mov cl,nl
	mul cl
	mov dx,offset nameA
	add dx,ax
	mov ah,10
	int 21h
	mov bx,dx
	mov byte ptr [bx],0dh
	inc bx 
	mov byte ptr [bx],0ah
	pop dx
	pop cx
	pop bx
	pop ax
	ret
inName endp
code ends
end
~~~

##### 输入成绩

~~~assembly
public inScore
data segment common 'data'
	nameA db 5 dup(13,?,13 dup('$'))
	scoreAsc db 5 dup(4 dup('$'))
	rank dw 0,1,2,3,4
	scoreN dw 5 dup(0)
	n db 0  	;n代表学号
	sn dw 5 	;sn代表学生数量
	info db 0dh,0ah,'name:$'
	info1 db 0dh,0ah,'score:$'
	nl db 15
	sl db 4
	x db 3 dup(?)
data ends

code segment public 'code'
	assume cs:code,ds:data
inScore proc ;输入成绩
	push ax
	push bx
	push cx
	push dx
	push bp
	push si
	push di
	mov al,n
	mov cl,sl
	mul cl
	mov bp,offset scoreAsc
	add bp,ax
let0:
	mov ah,1
	int 21h
	cmp al,0dh
	je let1
	mov ds:[bp][si],al
	sub al,30h
	mov x[si],al
	inc si
	jmp let0
let1:
	mov bx,0
	mov di,0
	cmp si,1
	je let2
	cmp si,2
	je let3
	mov al,x[di]
	mov cl,100
	mul cl
	add bx,ax
	inc di
let3:
	mov al,x[di]
	mov cl,10
	mul cl
	add bx,ax
	inc di
let2:
	add bl,x[di]
	mov al,n
	mov cl,2
	mul cl
	mov si,ax
	mov scoreN[si],bx
	pop di
	pop si
	pop bp
	pop dx
	pop cx
	pop bx
	pop ax
	ret
inScore endp
code ends
end
~~~

#### 排序程序

~~~assembly
public sort
data segment common 'data'
	nameA db 5 dup(13,?,13 dup('$'))
	scoreAsc db 5 dup(4 dup('$'))
	rank dw 0,1,2,3,4
	scoreN dw 5 dup(0)
	n db 0  	;n代表学号
	sn dw 5 	;sn代表学生数量
	info db 0dh,0ah,'name:$'
	info1 db 0dh,0ah,'score:$'
	nl db 15
	sl db 4
	x db 3 dup(?)
data ends

code segment public 'code'
	assume cs:code,ds:data
sort proc ;排序
	push ax
	push bx
	push cx
	mov cx,sn
	dec cx
	loopS1:
	push cx
	mov bx,0
	loopS2:
	mov ax,scoreN[bx]
	cmp ax,scoreN[bx+2]
	jae cSort
	xchg ax,scoreN[bx+2]
	mov scoreN[bx],ax
	mov ax,rank[bx]
	xchg ax,rank[bx+2]
	mov rank[bx],ax
	cSort:
	add bx,2
	loop loopS2
	pop cx
	loop loopS1
	pop cx
	pop bx
	pop ax
	ret
sort endp
code ends
end
~~~

#### 打印程序

~~~assembly
public dis
data segment common 'data'
	nameA db 5 dup(13,?,13 dup('$'))
	scoreAsc db 5 dup(4 dup('$'))
	rank dw 0,1,2,3,4
	scoreN dw 5 dup(0)
	n db 0  	;n代表学号
	sn dw 5 	;sn代表学生数量
	info db 0dh,0ah,'name:$'
	info1 db 0dh,0ah,'score:$'
	nl db 15
	sl db 4
	x db 3 dup(?)
data end

code segment public 'code'
	assume cs:code,ds:data
dis proc	;打印结果
	push ax
	push bx
	push cx
	push dx
	push di
	mov cx,sn
	mov di,1
	mov bx,0
loopDis:
	mov dx,di
	add dl,30h	
	mov ah,2
	int 21h
	mov ax,rank[bx]
	mov dl, nl
	mul dl
	mov dx,offset nameA
	add dx,ax
	mov ah,9
	int 21h
	mov dl,0dh
	mov ah,2
	int 21h
	mov dl,0ah
	int 21h
	mov ax,rank[bx]
	mov dl,sl
	mul dl
	mov dx,offset scoreAsc
	add dx,ax
	mov ah,9
	int 21h
	mov dl,0dh
	mov ah,2
	int 21h
	mov dl,0ah
	int 21h
	add bx,2
	inc di
	loop loopDis
	pop di
	pop dx
	pop cx
	pop bx
	pop ax
	ret
dis endp
code ends
end
~~~

### 宏调用

~~~assembly
enter macro
	mov dl, 0dh
	mov ah, 2
	int 21h
	mov dl, 0ah
	mov ah, 2
	int 21h
endm
~~~

在主程序中执行即可实现换行功能

~~~assembly
include enter
~~~

- 子程序和宏调用的区别：子程序实际上是跳转执行，而宏调用是将宏这段代码复写插入至`include`之后

## 中断程序设计

`int9.asm`

~~~assembly
code segment
assume cs:code
start:
	mov ax,0
	mov es,ax
	push es:[0024h]
	pop es:[0202h]
	push es:[0026h]
	pop es:[0204h]
	mov ah,25h
	mov al,9
	push cs
	pop ds
	mov dx,offset int9
	int 21h
	mov ah,31h
	mov al,0
	mov dx,int9end-int9start+16
	int 21h
int9 proc near
int9start:
	push ax
	push bx
	push cx
	push es
	in al,60h
	mov bx,0
	mov es,bx
	pushf
	call dword ptr es:[0202h]
	cmp al,1eh
	jne int9ret
	mov ax,0b800h
	mov es,ax
	mov bx,1
	mov cx,2000
discoloration:
	inc byte ptr es:[bx]
	add bx,2
	loop discoloration
int9ret:
	pop es
	pop cx
	pop bx
	pop ax
	iret
int9end:
	nop
int9 endp
code ends
end start	
~~~

`int7ch.asm`

~~~assembly
data segment
	msg db 0ah,0dh,"2005 202012143 Zhou tong$"
data ends
code segment
assume cs:code,ds:data
start:
	mov ah,25h
	mov al,7ch
	push cs
	pop ds
	mov dx,offset int7c
	int 21h
	mov ah,31h
	mov al,0
	mov dx,int7cend-int7cstart+16
	int 21h
int7c proc near
int7cstart:
	push ax
	push ds
	push dx
	mov ax,data
	mov ds,ax
	lea dx,msg
	mov ah,9
	int 21h
	pop dx
	pop ds
	pop ax
	iret
int7cend:nop
int7c endp
code ends
end start
~~~

`run7ch.asm`

~~~assembly
assume cs:code
code segment
start:
	int 7ch
	mov ah,4ch
	int 21h
code ends
end start
~~~

