---
title: We Chall
date: 2022-6-30
tags:
  - Misc
---

[WeChall](https://www.wechall.net/)，写着玩的

## Challenges

### Get Sourced

`F12`查看`HTML`源码，答案写在注释里，藏在`body`的右下方

~~~html
<!-- Now this is a comment! -->
<!-- You are looking for this password: html_sourcecode -->       
</body></html>
~~~

### MySQL Ⅰ

查看后端源码发现登录的`SQL`语句是这么写的，同时发现了管理员用户名`admin`

~~~php
$query = "SELECT * FROM users WHERE username='$username' AND password='$password'";
~~~

可以在传入用户名时动手脚

1、传入`admin'#`，这样`sql`语句的字符串将变成

~~~
"select * from users where username='admin''#..."
~~~

于是验证密码的部分被替换为`sql`注释，因为`admin`存在，返回一定为真

2、传入`admin' or '1`

~~~sql
select * from users where username='admin' or '1' and password='$password'
~~~

同样因为掺入了`1`，于是返回始终为真

### Crypto-Caesar Ⅰ

简单的解密，给定一串码

~~~
PDA MQEYG XNKSJ BKT FQILO KRAN PDA HWVU ZKC KB YWAOWN WJZ UKQN QJEMQA OKHQPEKJ EO BWCYZILHHNKW
~~~

合理猜测每个字母加上一个相同的数变为当前码，如`A+2=C, Z+1=A`

于是编写程序列出总共26种可能性，同时将大写转小写便于阅读

代码

~~~python
code = "LZW IMAUC TJGOF XGP BMEHK GNWJ LZW DSRQ VGY GX USWKSJ SFV QGMJ MFAIMW KGDMLAGF AK YZJJKAKWUUKH"
i = 0
while i < 26:
    print(i+1, end='.')
    for c in code:
        if c == ' ':
            print(c, end='')
            continue
        print(chr(97+(ord(c)-65+i)%26),end="")
    i += 1
    print('\n')
~~~

打印结果

~~~
1.lzw imauc tjgof xgp bmehk gnwj lzw dsrq vgy gx uswksj sfv qgmj mfaimw kgdmlagf ak yzjjkakwuukh
2.max jnbvd ukhpg yhq cnfil hoxk max etsr whz hy vtxltk tgw rhnk ngbjnx lhenmbhg bl zakklblxvvli
3.nby kocwe vliqh zir dogjm ipyl nby futs xia iz wuymul uhx siol ohckoy mifoncih cm abllmcmywwmj
4.ocz lpdxf wmjri ajs ephkn jqzm ocz gvut yjb ja xvznvm viy tjpm pidlpz njgpodji dn bcmmndnzxxnk
5.pda mqeyg xnksj bkt fqilo kran pda hwvu zkc kb ywaown wjz ukqn qjemqa okhqpekj eo cdnnoeoayyol
6.qeb nrfzh yoltk clu grjmp lsbo qeb ixwv ald lc zxbpxo xka vlro rkfnrb plirqflk fp deoopfpbzzpm
7.rfc osgai zpmul dmv hsknq mtcp rfc jyxw bme md aycqyp ylb wmsp slgosc qmjsrgml gq efppqgqcaaqn
8.sgd pthbj aqnvm enw itlor nudq sgd kzyx cnf ne bzdrzq zmc xntq tmhptd rnktshnm hr fgqqrhrdbbro
# 找到有意义的一行
9.the quick brown fox jumps over the lazy dog of caesar and your unique solution is ghrrsiseccsp
10.uif rvjdl cspxo gpy kvnqt pwfs uif mbaz eph pg dbftbs boe zpvs vojrvf tpmvujpo jt hisstjtfddtq
11.vjg swkem dtqyp hqz lworu qxgt vjg ncba fqi qh ecguct cpf aqwt wpkswg uqnwvkqp ku ijttukugeeur
12.wkh txlfn eurzq ira mxpsv ryhu wkh odcb grj ri fdhvdu dqg brxu xqltxh vroxwlrq lv jkuuvlvhffvs
13.xli uymgo fvsar jsb nyqtw sziv xli pedc hsk sj geiwev erh csyv yrmuyi wspyxmsr mw klvvwmwiggwt
14.ymj vznhp gwtbs ktc ozrux tajw ymj qfed itl tk hfjxfw fsi dtzw zsnvzj xtqzynts nx lmwwxnxjhhxu
15.znk waoiq hxuct lud pasvy ubkx znk rgfe jum ul igkygx gtj euax atowak yurazout oy mnxxyoykiiyv
16.aol xbpjr iyvdu mve qbtwz vcly aol shgf kvn vm jhlzhy huk fvby bupxbl zvsbapvu pz noyyzpzljjzw
17.bpm ycqks jzwev nwf rcuxa wdmz bpm tihg lwo wn kimaiz ivl gwcz cvqycm awtcbqwv qa opzzaqamkkax
18.cqn zdrlt kaxfw oxg sdvyb xena cqn ujih mxp xo ljnbja jwm hxda dwrzdn bxudcrxw rb pqaabrbnllby
19.dro aesmu lbygx pyh tewzc yfob dro vkji nyq yp mkockb kxn iyeb exsaeo cyvedsyx sc qrbbcscommcz
20.esp bftnv mczhy qzi ufxad zgpc esp wlkj ozr zq nlpdlc lyo jzfc fytbfp dzwfetzy td rsccdtdpnnda
21.ftq cguow ndaiz raj vgybe ahqd ftq xmlk pas ar omqemd mzp kagd gzucgq eaxgfuaz ue stddeueqooeb
22.gur dhvpx oebja sbk whzcf bire gur ynml qbt bs pnrfne naq lbhe havdhr fbyhgvba vf tueefvfrppfc
23.hvs eiwqy pfckb tcl xiadg cjsf hvs zonm rcu ct qosgof obr mcif ibweis gczihwcb wg uvffgwgsqqgd
24.iwt fjxrz qgdlc udm yjbeh dktg iwt apon sdv du rpthpg pcs ndjg jcxfjt hdajixdc xh vwgghxhtrrhe
25.jxu gkysa rhemd ven zkcfi eluh jxu bqpo tew ev squiqh qdt oekh kdygku iebkjyed yi wxhhiyiussif
26.kyv hlztb sifne wfo aldgj fmvi kyv crqp ufx fw trvjri reu pfli lezhlv jfclkzfe zj xyiijzjvttjg
~~~

故解密结果为

~~~
the quick brown fox jumps over the lazy dog of caesar and your unique solution is ghrrsiseccsp
~~~

密码为

~~~
ghrrsiseccsp
~~~

### Stegano Ⅰ

通过`html`代码找到图片源下载，得到`btegano1.bmp`，直接用记事本或者`notepad++`打开，或修改后缀为`.txt`打开，可得到答案

~~~
BMf       6   (               0                   Look what the hex-edit revealed: passwd:steganoI
~~~

可以用`notepad++`等编辑器打开`bmp`文件，在末尾加入文字信息（若用记事本直接修改将损坏文件）

### URL

给定一串`URL`码

~~~
%59%69%70%70%65%68%21%20%59%6F%75%72%20%55%52%4C%20%69%73%20%63%68%61%6C%6C%65%6E%67%65%2F%74%72%61%69%6E%69%6E%67%2F%65%6E%63%6F%64%69%6E%67%73%2F%75%72%6C%2F%73%61%77%5F%6C%6F%74%69%6F%6E%2E%70%68%70%3F%70%3D%64%64%6F%65%70%61%6C%68%65%61%73%68%26%63%69%64%3D%35%32%23%70%61%73%73%77%6F%72%64%3D%66%69%62%72%65%5F%6F%70%74%69%63%73%20%56%65%72%79%20%77%65%6C%6C%20%64%6F%6E%65%21
~~~

URL实际上就是16进制的ASCII码，每个字符通过%分隔开

用python进行解码

~~~python
# 引入库函数
import urllib
import urllib.parse

url = "%59%69%70%70%65%68%21%20%59%6F%75%72%20%55%52%4C%20%69%73%20%63%68%61%6C%6C%65%6E%67%65%2F%74%72%61%69%6E%69%6E%67%2F%65%6E%63%6F%64%69%6E%67%73%2F%75%72%6C%2F%73%61%77%5F%6C%6F%74%69%6F%6E%2E%70%68%70%3F%70%3D%64%64%6F%65%70%61%6C%68%65%61%73%68%26%63%69%64%3D%35%32%23%70%61%73%73%77%6F%72%64%3D%66%69%62%72%65%5F%6F%70%74%69%63%73%20%56%65%72%79%20%77%65%6C%6C%20%64%6F%6E%65%21"
print(urllib.parse.unquote(url))
~~~

当然也可以手动解码，太几把高级了，但解不了中文

~~~python
url = '%59%69%70%70%65%68%21%20%59%6F%75%72%20%55%52%4C%20%69%73%20%63%68%61%6C%6C%65%6E%67%65%2F%74%72%61%69%6E%69%6E%67%2F%65%6E%63%6F%64%69%6E%67%73%2F%75%72%6C%2F%73%61%77%5F%6C%6F%74%69%6F%6E%2E%70%68%70%3F%70%3D%6F%6C%69%6F%62%66%69%6D%70%6C%6D%67%26%63%69%64%3D%35%32%23%70%61%73%73%77%6F%72%64%3D%66%69%62%72%65%5F%6F%70%74%69%63%73%20%56%65%72%79%20%77%65%6C%6C%20%64%6F%6E%65%21'
print (''.join(map(lambda x:chr(int(x, 16)), url[1:].split('%'))))
~~~

- 将字符串通过%分隔成一个个十六进制数`x`
- `int(x, 16)`将`x`从十六进制转成十进制ascii码
- `chr()`函数将ascii码转成字符

直接在控制台用js当然也是可以的，调用`decodeURL()`函数

~~~js
url = '%59%69%70%70%65%68%21%20%59%6F%75%72%20%55%52%4C%20%69%73%20%63%68%61%6C%6C%65%6E%67%65%2F%74%72%61%69%6E%69%6E%67%2F%65%6E%63%6F%64%69%6E%67%73%2F%75%72%6C%2F%73%61%77%5F%6C%6F%74%69%6F%6E%2E%70%68%70%3F%70%3D%64%64%6F%65%70%61%6C%68%65%61%73%68%26%63%69%64%3D%35%32%23%70%61%73%73%77%6F%72%64%3D%66%69%62%72%65%5F%6F%70%74%69%63%73%20%56%65%72%79%20%77%65%6C%6C%20%64%6F%6E%65%21'
'%59%69%70%70%65%68%21%20%59%6F%75%72%20%55%52%4C%20%69%73%20%63%68%61%6C%6C%65%6E%67%65%2F%74%72%61%69%6E%69%6E%67%2F%65%6E%63%6F%64%69%6E%67%73%2F%75%72%6C%2F%73%61%77%5F%6C%6F%74%69%6F%6E%2E%70%68%70%3F%70%3D%64%64%6F%65%70%61%6C%68%65%61%73%68%26%63%69%64%3D%35%32%23%70%61%73%73%77%6F%72%64%3D%66%69%62%72%65%5F%6F%70%74%69%63%73%20%56%65%72%79%20%77%65%6C%6C%20%64%6F%6E%65%21'
answer = decodeURI(url)
'Yippeh! Your URL is challenge%2Ftraining%2Fencodings%2Furl%2Fsaw_lotion.php%3Fp%3Dddoepalheash%26cid%3D52%23password%3Dfibre_optics Very well done!'
~~~

### ASCII

给定一串ASCII码

~~~
84, 104, 101, 32, 115, 111, 108, 117, 116, 105, 111, 110, 32, 105, 115, 58, 32, 98, 109, 97, 108, 114, 110, 114, 97, 103, 103, 115, 114
~~~

进行解码

~~~python
ascii = '84, 104, 101, 32, 115, 111, 108, 117, 116, 105, 111, 110, 32, 105, 115, 58, 32, 98, 109, 97, 108, 114, 110, 114, 97, 103, 103, 115, 114'
arr = ascii.split(", ")
for c in arr:
    print(chr(int(c)), end='')
print()
~~~

