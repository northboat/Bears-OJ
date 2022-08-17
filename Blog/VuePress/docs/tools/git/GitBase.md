# Git基础

## 基础命令

添加文件到暂存区

~~~bash
//添加单个文件
git add 文件名.后缀
//添加所有变化
git add .
~~~

提交文件到版本库

~~~bash
//将暂存区文件全部提交到版本库
git commit -m "提交描述"
//提交撤回，即退回到上个版本，文件返回暂存区
git reset --soft HEAD^
git reset --soft HEAD~2	//退回到上两个版本
~~~

将版本库提交到远程仓库

~~~bash
git push
~~~

切换分支

~~~bash
//查看所有分支
git branch -a
//切换分支
git checkout 分支名
~~~

## 新版本Token

生成token

1. 账号setting
2. 开发者设置
3. 设置token属性，生成token
4. 复制token

在登陆时密码用token代替，就有push权限了

若不提示输入密码，可使用以下命令

~~~bash
git config --system --unset credential.helper
~~~

或在本地执行以下代码，获取仓库push权限，一次使用，token有效期内都有用

~~~bash
git remote set-url origin 
https://<your_token>@github.com/<USERNAME>/<REPO>.git
~~~

## ssl报错10056

报错：Git报错解决：fatal: unable to access ‘http:...’ OpenSSL SSL_read: Connection was reset

产生原因：一般是这是因为服务器的SSL证书没有经过第三方机构的签署，所以才报错

使用以下命令接触ssl验证后再次git即可解决

~~~bash
git config --global http.sslVerify "false"
~~~

## 报错 port 443 timed out

连接超时：port443 timed out，并且ping不通github.com

解决办法：去网站搜github.com和github.global.ssl.Fastly.net的ip地址，添加到windows/system32/drivers/etc/hosts中

## 报错 broken pipe

~~~bash
fatal: sha1 file '<stdout>' write error: Broken pipeB/s fatal: the remote end hung up unexpectedly
~~~

文件过大导致

git默认文件大小小于等于100MB

可修改缓存大小强行上传

~~~bash
git config http.postBuffer 52428800 
~~~

