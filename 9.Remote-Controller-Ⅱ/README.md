## Remote-Controller-2
> 回炉重造的远程控制器，一个很简单的通过`Redis`实现通信的小玩具

本地的监听器（jar 包）用 exe4j + inno setup 打包成一个 exe 形式的安装包以供用户使用，实际上就是监听远端 redis 的某个键值对

网页端操纵远端的 redis 数据库，发送信息，本地机读取 redis 信息并作出相应反应
