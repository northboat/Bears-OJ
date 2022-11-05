# frp内网穿透

> 使用frp内网穿透工具进行内网穿透

[frp配置内网穿透教程](https://cloud.tencent.com/developer/article/1837482)

1、手动生成将frps.ini、frps.service、frps三个文件放入外网服务器

- home/frps/frps+frps.ini

- etc/systemd/system/frps.service


2、赋予权限chomd 777 + 上述三个文件

3、systemctl start frps

4、在内网中配置frpc文件并启动frpc服务

