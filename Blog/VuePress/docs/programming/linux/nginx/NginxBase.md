# Nginx基础

> 高性能的HTTP和反向代理Web服务器
>
> 官方数据写的可支持50000个并发连接数的相应

## 一、正向代理

VPN：挂在客户端上的代理

客户端 —— VPN ——> 服务器

## 二、反向代理

> 挂在服务器上的代理

作为中间代理分发请求给服务器，实现负载均衡

如访问`www.baidu.com`，服务端代理这个域名，将请求分发给不同的服务器去接收请求

## 三、负载均衡

轮询：请求依次打到服务器（循环链表）

加权轮询：优先打到权重较高的服务器，保证服务器性能最大化

IPHash：

- Session保存在Tomcat中，用Redis共享Session
- 通过IP进行计算，固定的IP打到固定的服务器，这样Session就不会丢失，但一旦这台服务器挂了，Session也全没了

动静分离：静态资源直接通过Nginx返回，不走项目，如前端静态网页

## 四、基本使用

### 1、准备

#### 安装

上传安装包

~~~bash
tar -axvf nginx-1.20.2.tar.gz

cd nginx-1.20.2.tar.gz

./configure

make && make install

whereis nginx
/usr/local/nginx

cd /usr/local/nginx
~~~

#### 配置

~~~bash
[root@iZ2zedonrtl5m2vaonbyrpZ nginx]# ll
total 36
drwx------ 2 nobody root 4096 Mar 18 20:10 client_body_temp
drwxr-xr-x 2 root   root 4096 Mar 18 20:24 conf
drwx------ 2 nobody root 4096 Mar 18 20:10 fastcgi_temp
drwxr-xr-x 2 root   root 4096 Mar 18 20:09 html
drwxr-xr-x 2 root   root 4096 Mar 18 20:27 logs
drwx------ 2 nobody root 4096 Mar 18 20:10 proxy_temp
drwxr-xr-x 2 root   root 4096 Mar 18 20:09 sbin
drwx------ 2 nobody root 4096 Mar 18 20:10 scgi_temp
drwx------ 2 nobody root 4096 Mar 18 20:10 uwsgi_temp

cd conf

cat nginx.conf
vim nginx.conf
~~~

#### 常用命令

~~~bash
cd ..
cd sbin
./nginx

./nginx -s stop #停止

./nginx -s quit #安全退出，处理完请求后退出

./nginx -s reload #重新加载配置

ps aux|grep nginx #查看nginx进程
~~~

### 2、使用

#### nginx.conf

~~~bash
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
	# 最大worker数量
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;
    
    #gzip  on;

    server {
        listen       8080;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
}
~~~

#### 配置server

~~~bash
# 配置负载均衡
upstream PerformanceSystem{
	# 邮件发送服务器资源
	server 127.0.0.1:8084 weight=1;
	server 127.0.0.1:8085 weight=1; 
}

server {
	listen       8080;
	server_name  localhost;


	location / {
		root   html;
		index  index.html index.htm;
		# 配置服务
		proxy_pass http://PerformanceSystem;
	}

	error_page   500 502 503 504  /50x.html;
	location = /50x.html {
		root   html;
	}
}
~~~

#### 热部署

~~~bash
./nginx -s reload
~~~

这样将没有任何征兆地更新配置并添加服务

## 五、进阶

动静分离、重写
