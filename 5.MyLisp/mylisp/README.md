# MyLisp

## 一、语法

#### 1、交互式编程

> num：数值
>
> mylisp能够识别[-1000000000,1000000000]区间的整数

~~~
lispy> 2
2

lispy> 3
3
~~~



> str：字符串
>
> 用""括起来的语句，与c类似

~~~
lispy> "wdnmd"
"wdnmd"

lispy> "hahaha"
"hahaha"
~~~



> err：错误
>
> 一定程度上识别错误并给出提示，十分鸡肋

~~~
lispy> -10000000000
Error: Invalid number.

lispy> hahaha
Error: undefine symbol 'hahaha'.

lispy> * (+ 1 2) {4}
Error: Function '*' passed incorrect number of arguments. Got 1, Expected 965455.
~~~



> s-expression：s表达式
>
> 以 ()为标识，与·c语言的()功能类似，可以理解为分割、提升优先级，自身并无意义

~~~
lispy> + 1 2
3

lispy> (+ 1 2)
3

lispy> (* (+ 1 2) (/ 18 9))
6
~~~

- 在mylisp1.0中，计算式采用前缀表达式



> q-expression
>
> 以{}为标识，主要用于储存用户自定义标识符，不能直接用于计算

~~~
lispy> {a}
{a}

lispy> {(+ 1 2)}
{(+ 1 2)}
~~~

- s/q-expression 自动忽略空格



> builtin_functions：内建函数们

1、head：取出q-expression中的首元素

~~~
lispy> head {1 2 3 4}
1

lispy> head {+ 1 2}
{+}

lispy> head {kadls dsa dks}
{kadls}
~~~

2、tail：取出q-expression中除首元素以外所有元素（按顺序）

~~~
lispy> tail {1 2 3 4}
{2 3 4}

lispy> tail {hahaha wdnmd oh}
{wdnmd oh}
~~~

3、join：合并两个q-expression

~~~
lispy> join {1} {2}
{1 2}

lispy> join {nmlg} {b}
{nmlg b}
~~~

4、eval：计算一个q-expression

~~~
lispy> eval {+ 1 2}
3

lispy> eval (join {+ 1} {2})
3

lispy> eval (tail {1 + 2 3})
5


~~~

- 这样的语句是不被允许的，因为head{a b c} -> {a}，而eval无法计算{+} {b} {c}，而应该是单独一个q-expression

  ~~~
  lispy> eval {head{+ - * /} head{9 5} head{3 1 6}}
  Error: Function 'head' passed incorrect number of arguments, Got 5. Expected 1.
  
  lispy> head {9 5}
  {9}
  ~~~

5、list：将一个句子转换成q-expresion

~~~
lispy> list 1 2 3 4
{1 2 3 4}
~~~

6、clear：清屏

7、error：主动抛出异常

~~~
lispy> error (/ 9 0)
Error: Division By Zero.
~~~

8、print：打印输出

~~~
lispy> print {1 2 3 4}
{1 2 3 4}
()

lispy> print "hahaha"
"hahaha"
()
~~~

- ()表示执行成功，注意s表达式无法被打印

9、+   -    *    /：算术运算符（再次提醒计算需要前缀表达式）

10、=：赋值

11、==    !=：等于/不等于，真返回1，假返回0

12、if：条件判断，真返回，否返回0

13、>=    >    <=    <：比较运算符（前缀前缀），可比较字符串、表达式、数值，可跨类型比较（一定不等）

14、def：定义（全局赋值）

15、load：加载文件



> lambda函数：用户自定义函数
>
> 以 \ 为标识





#### 2、加载文件

让mylisp与文件放于同一路径，执行load函数

~~~
lispy> load "HelloWorld.pls"
"Hello, World!"
()
~~~

HelloWorld.pls：

~~~
print "Hello, World!" ;打印helloworld
~~~

其中;作为注释分隔符