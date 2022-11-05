---
title: TCP Sockets
date: 2022-7-21
tags:
  - Network
---

> 《TCP/IP Sockets in C》第二版
>
> —— C语言 TCP/IP Sockets 编程

## 简介

### 网络、分组和协议

网络由主机和路由器构成

- 主机（host），是运行应用程序的计算机，是网络的真正“用户”
- 路由器（router），也成网关（gateway），其职责是将信息从一条通信信道转发到另一条通信信道，通常不会运行应用程序
- 通信信道（communication channel），是把字节序列从主机转送到另一台主机的通道，可以是有线的以太网，也可是无线的 WIFI

分组：由程序构造和解释的字节序列（信息），包含用于执行其任务的空值信息和用户数据。路由器使用这些控制信息查明如何转发每个分组

协议：关于由通信程序交换的分组及其含义的协定。协议说明如何构造分组、如何解释信息

### 地址和域名

地址：`IP:端口`

IP地址：IPv4 / IPv6

端口号：1-65535

特殊地址：

- 本机地址：127.0.0.1 / 0:0:0:0:0:0:0:1
- 特殊用途预留：10开头 / 192.168开头 / 172开头且第二个数字在16-31之间（IPv4）

域名：`www.baidu.com`

需要明确的是，Internet 协议处理的是地址，而非域名，所以接收到域名时，要首先解析为地址，再进行处理，这个额外步骤是值得的，因为

- 便于记忆
- 提供了某种级别的间接性：如一级域名、二级域名，而不是毫无意义的 IP 地址

什么是 URL，与域名的区别是什么？

- 资源定位器：Universal Resource Locator
- 一般来说，域名指的是一个网站的顶级域名，而 URL 指向某个网站或网页的地址
- URL 中总是包含域名

如何解析域名？

- DNS：Domain Name System
- 本地配置数据库

### 客户与服务器

就像打电话。客户程序发起通信，服务器程序被动等待，然后相应它的客户，二者共同构成应用程序

使用 Sockets API 与其对应方建立通信的的一般形式决定该程序是充当客户还是服务器

二者的区别还是很明显的，如打电话的一方（客户）需要知道被打电话一方（服务器）的手机号（地址），而被打电话的一方不需要，但在通信建立后，如果需要，也可以获取打电话一方的手机号。需要注意的是，一旦连接建立，服务器和客户之间的区别的就消失了

- 总结来说，客户与服务器的区别只在于谁建立初始连接和谁等待连接

服务器的端口分配遵循 IANA 的分配方式（Internet Assigned Number Authority，Internet编号授权委员会），如 80 分给 HTTP，443 分给 HTTPS，应答协议端口 7

### 套接字

套接字即 Sockets，应用程序通过它发送和接收数据，它允许应用程序插入到网络中并与插入到同一个网络的其他应用程序进行通信

套接字是一个抽象层，是一组 API，应用程序通过这组 API 在计算机端口上进行一系列数据交换

- 需要注意的是，一个应用程序可以连接多个套接字，多个应用程序也可以连接同一个套接字（很少但存在），即同时有一对一、一对多、多对一的连接模式存在

其过程基本为，机 A 通过套接字程序将信息写入套接字服务器 W，机 B 从套接字服务器 W 获取机 A 写入的数据

协议：TCP/IP 协议族

套接字类型：流套接字（stream socket）/ 数据报套接字（datagram socket），分别对应 TCP 和 UDP 协议

## 基本的 TCP 套接字

### IPv4 TCP 客户和服务器

IPv4 TCP 客户

Practical.h

~~~c
#include "DieWithMessage.c"
#include "SocketAddrUtility.c"
#include "TCPServerUtility.c"
#include "TCPClientUtility.c"
~~~

DieWithMessage.c

~~~c
#include <stdio.h>
#include <stdlib.h>

void DieWithUserMessage(const char *msg, const char *detail){
    fputs(msg, stderr);
    fputs(": ", stderr);
    fputs(detail, stderr);
    fputc('\n', stderr);
    exit(1);
}

void DieWithSystemMessage(const char *msg){
    perror(msg);
    exit(1);
}
~~~

TCPEchoClient4.c

- 使用函数 socket() 创建套接字，创建用于 IPv4 的基于流（stream）的 TCP 协议的套接字，即客户，其返回值为一个整数，可以视作该 socket 的编号（端口号）
- sockaddr_in 结构体用于存放服务器地址，并进行填充，即设置地址族、Internet 地址和端口号
  - sin_family 设置地址族
  - inet_pton() 函数用于设置 Internet IP 地址的字符串，如 127.0.0.1，设置的值为 servAddr.sin_addr.s_addr，该函数需要指定协议族
  - htons() 设置端口号，这里对端口号进行了二进制格式化处理
- 通过 connect() 函数建立第一步创建的 sock 和第二步设置的服务器地址之间的连接（三次握手）
- 建立连接后，使用 send() 函数向服务器发送字符串，需要指明字符串和其起始位置，若成功将返回发送数据长度
- 使用 recv() 函数接收服务器应答，需要传入 socket，一个用于接收字符串的 char 型数组 buffer，以及字符串的起始位置`[0, buffer.length)`
  - 注意这里用一个 while 循环接收数据直到整个字符串返回，这是因为 TCP 是字节流协议，服务器的 send() 的字节可能不会因为本地的一次 recv() 而全部返回，所以需要反复接收
- 使用 close() 函数销毁 socket，这个函数在包 unistd.h 中

~~~c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "Practical.h"

int main(int argc, char *argv[]){
    // printf("hahaha"); 
    // argc = 3;
    // argv[1] = "127.0.0.1";
    // argv[2] = "hello";
    
    if(argc<3 || argc>4){
        DieWithUserMessage("Parameter(s)", "<Server Address> <Echo Word> [<Server Port>]");
    }
    char *servIP = argv[1];
    char *echoString = argv[2];
    // cout << servIP << endl << echoString << endl;

    in_port_t servPort = (argc==4) ? atoi(argv[3]):7;
    // cout << servPort;
    
    int sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

    if(sock < 0){
        DieWithSystemMessage("socket() failed");
    }
    
    struct sockaddr_in servAddr;
    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin_family = AF_INET;

    int rtnVal = inet_pton(AF_INET, servIP, &servAddr.sin_addr.s_addr);
    if(rtnVal == 0){
        DieWithUserMessage("inet_pton() failed", "invalid address string");
    } else if(rtnVal < 0){
        DieWithSystemMessage("inet_pton() failed");
    }

    servAddr.sin_port = htons(servPort);

    if(connect(sock, (struct sockaddr *) &servAddr, sizeof(servAddr)) < 0){
        DieWithSystemMessage("connect() failed");
    }
    size_t echoStringLen = strlen(echoString);

    ssize_t numBytes = send(sock, echoString, echoStringLen, 0);
    
    if(numBytes < 0){
        DieWithSystemMessage("send() failed");
    } else if(numBytes != echoStringLen){
        DieWithUserMessage("send()", "send unexpedted number of bytes");
    }

    unsigned int totalBytesRcvd = 0;
    fputs("Received: ", stdout);
    while(totalBytesRcvd < echoStringLen){
        // printf("hahaha");
        char buffer[BUFSIZ];
        numBytes = recv(sock, buffer, BUFSIZ-1, 0);
        if(numBytes < 0){
            DieWithSystemMessage("recv() failed");
        } else if(numBytes == 0){
            DieWithUserMessage("recv()", "connection closed prematurely");
        }
        totalBytesRcvd += numBytes;
        buffer[numBytes] = '\0';
        fputs(buffer, stdout);
    }

    fputc('\n', stdout);
    close(sock);
    exit(0);
}
~~~

在运行时传入参数 IP 、数据以及端口，如

~~~bash
./TCPEchoClient4 127.0.0.1 "Hello" 10086
~~~

IPv4 TCP 服务器

TCPServerUtility.c：处理服务器接收到的数据

- 传入的 clntSocket 为客户 socket 的编号
- buffer 字符数组用以缓冲接收客户发送的信息
- 使用 recv() 函数接收一次客户数据，若接收数据字节不为空，进入循环
- 在循环中，交替进行 send 和 recv 的操作，即发回客户传入的信息，同时接收客户的信息，直到客户传入的字节流为空
  - 需要注意的是，不管是 send 还是 recv 操作，只需要一个 socket 就能进行，这是因为客户的 socket 已经和服务端的 socket 进行过连接，即 connect，这条信道是唯一的，而发送和接收的主体为当前执行代码的机器
  - 这也进一步印证了，当通信建立之后，客户和服务器的区分将变得模糊

~~~c
#include <unistd.h> //close

void HandleTCPClient(int clntSocket){
    char buffer[BUFSIZ];

    ssize_t numBytesRcvd = recv(clntSocket, buffer, BUFSIZ, 0);
    if(numBytesRcvd < 0){
        DieWithSystemMessage("recv() failed");
    }

    while(numBytesRcvd > 0){
        ssize_t numBytesSent = send(clntSocket, buffer, numBytesRcvd, 0);
        if(numBytesSent < 0){
            DieWithSystemMessage("send() failed");
        } else if(numBytesRcvd != numBytesSent){
            DieWithUserMessage("send()", "sent unexpected number of bytes");
        }

        numBytesRcvd = recv(clntSocket, buffer, BUFSIZ, 0);
        if(numBytesRcvd < 0){
            DieWithSystemMessage("recv() failed");
        }
    }

    close(clntSocket);
}
~~~

TCPEchoServer4.c

- 通过传入的端口参数使用 socket() 创建一个套接字作为服务端口

- 填充第一步创建的端点接口

  - 地址族
  - 地址（IP），直接设置 servAddr.sin_addr.s_addr = htonl(INADDR_ANY)，因为我们不关心服务器的 IP 地址，所以直接设置其为地址通配符 inaddr any
  - 端口，htons() 设置传入的端口参数

- 使用 bind() 函数绑定第一步创建的套接字和第二步创建的端点接口，即指定该端点为服务器的 socket

- 调用 listen() 函数对服务端端口 socket 进行监听，可设置最大连接数 maxpending

- 在反复处理进入的连接

  - sockaddr_in 结构体 clntAddr 用于储存客户 socket 地址

  - accept() 函数接收客户 socket，第一个参数为接收连接的 socket，第二个参数为 sockaddr_in 用于储存进入的 socket 地址，第三个参数为进入的 socket 地址长度

    accept() 一旦成功，clntAddr 中将包含客户的 Internet 地址和端口，并将地址长度写入 accept 的第三个参数

  - 使用 inet_ntop 和 ntohs 处理客户地址、端口的二进制码，转化为点分四组格式的字符串，用以输出

    这种转化和 inet_pton / htons 是相反的

~~~c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "Practical.h"

static const int MAXPENDING = 5;

int main(int argc, char *argv[]){
    if(argc != 2){
        DieWithUserMessage("Parameter(s)", "<Server Port>");
    }

    in_port_t servPort = atoi(argv[1]);

    int servSock;
    if((servSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0){
        DieWithSystemMessage("socket() failed");
    }

    struct sockaddr_in servAddr;
    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin_family = AF_INET;
    servAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    servAddr.sin_port = htons(servPort);

    if(bind(servSock, (struct sockaddr*) &servAddr, sizeof(servAddr)) < 0){
        DieWithSystemMessage("bind() failed");
    }
    if(listen(servSock, MAXPENDING) < 0){
        DieWithSystemMessage("listen() failed");
    }
    // while(1)
    for(;;){
        struct sockaddr_in clntAddr;
        socklen_t clntAddrLen = sizeof(clntAddr);

        int clntSock = accept(servSock, (struct  sockaddr*) &clntAddr, &clntAddrLen);
        if(clntSock < 0){
            DieWithSystemMessage("accept() failed");
        }

        char clntName[INET_ADDRSTRLEN];
        if(inet_ntop(AF_INET, &clntAddr.sin_addr.s_addr, clntName, sizeof(clntName)) != NULL){
            printf("Handling client %s:%d\n", clntName, ntohs(clntAddr.sin_port));
        } else {
            puts("Unable to get client address");
        }

        HandleTCPClient(clntSock);

    }
}
~~~

指定端口运行 Server

~~~bash
./TCPEchoServer4 10086
~~~

### IPv6 TCP 客户和服务器

IPv6 TCP 客户

TCPEchoClient6.c

~~~c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "Practical.h"

int main(int argc, char *argv[]){
    // printf("hahaha"); 
    // argc = 3;
    // argv[1] = "127.0.0.1";
    // argv[2] = "hello";
    
    if(argc<3 || argc>4){
        DieWithUserMessage("Parameter(s)", "<Server Address> <Echo Word> [<Server Port>]");
    }
    char *servIP = argv[1];
    char *echoString = argv[2];
    // cout << servIP << endl << echoString << endl;

    in_port_t servPort = (argc==4) ? atoi(argv[3]):7;
    // cout << servPort;
    
    int sock = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);

    if(sock < 0){
        DieWithSystemMessage("socket() failed");
    }
    
    struct sockaddr_in6 servAddr;
    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin6_family = AF_INET6;

    int rtnVal = inet_pton(AF_INET6, servIP, &servAddr.sin6_addr);
    if(rtnVal == 0){
        DieWithUserMessage("inet_pton() failed", "invalid address string");
    } else if(rtnVal < 0){
        DieWithSystemMessage("inet_pton() failed");
    }

    servAddr.sin6_port = htons(servPort);

    if(connect(sock, (struct sockaddr *) &servAddr, sizeof(servAddr)) < 0){
        DieWithSystemMessage("connect() failed");
    }
    size_t echoStringLen = strlen(echoString);

    ssize_t numBytes = send(sock, echoString, echoStringLen, 0);
    
    if(numBytes < 0){
        DieWithSystemMessage("send() failed");
    } else if(numBytes != echoStringLen){
        DieWithUserMessage("send()", "send unexpedted number of bytes");
    }

    unsigned int totalBytesRcvd = 0;
    fputs("Received: ", stdout);
    while(totalBytesRcvd < echoStringLen){
        // printf("hahaha");
        char buffer[BUFSIZ];
        numBytes = recv(sock, buffer, BUFSIZ-1, 0);
        if(numBytes < 0){
            DieWithSystemMessage("recv() failed");
        } else if(numBytes == 0){
            DieWithUserMessage("recv()", "connection closed prematurely");
        }
        totalBytesRcvd += numBytes;
        buffer[numBytes] = '\0';
        fputs(buffer, stdout);
    }

    fputc('\n', stdout);
    close(sock);
    exit(0);
}
~~~

修改的地方：

~~~c
int sock = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);

struct sockaddr_in6 servAddr;
memset(&servAddr, 0, sizeof(servAddr));
servAddr.sin6_family = AF_INET6;
int rtnVal = inet_pton(AF_INET6, servIP, &servAddr.sin6_addr);
servAddr.sin6_port = htons(servPort);
~~~

~~~bash
# 八个数
./TCPEchoClient6 0:0:0:0:0:0:0:1 "hello" 9999
~~~

IPv6 TCP 服务器

TCPEchoServer4.c

- bind() failed: Address family not supported by protocol

~~~c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "Practical.h"

static const int MAXPENDING = 5;

int main(int argc, char *argv[]){
    if(argc != 2){
        DieWithUserMessage("Parameter(s)", "<Server Port>");
    }

    in_port_t servPort = atoi(argv[1]);

    int servSock;
    if((servSock = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP)) < 0){
        DieWithSystemMessage("socket() failed");
    }

    struct sockaddr_in6 servAddr;
    memset(&servAddr, 0, sizeof(servAddr));
    servAddr.sin6_family = AF_INET6;
    servAddr.sin6_addr = in6addr_any;
    servAddr.sin6_port = htons(servPort);

    if(bind(servSock, (struct sockaddr*) &servAddr, sizeof(servAddr)) < 0){
        DieWithSystemMessage("bind() failed");
    }
    if(listen(servSock, MAXPENDING) < 0){
        DieWithSystemMessage("listen() failed");
    }
    // while(1)
    for(;;){
        struct sockaddr_in6 clntAddr;
        socklen_t clntAddrLen = sizeof(clntAddr);

        int clntSock = accept(servSock, (struct  sockaddr*) &clntAddr, &clntAddrLen);
        if(clntSock < 0){
            DieWithSystemMessage("accept() failed");
        }

        char clntName[INET6_ADDRSTRLEN];
        if(inet_ntop(AF_INET6, &clntAddr.sin6_addr, clntName, sizeof(clntName)) != NULL){
            printf("Handling client %s:%d\n", clntName, ntohs(clntAddr.sin6_port));
        } else {
            puts("Unable to get client address");
        }

        HandleTCPClient(clntSock);

    }
}
~~~

和 TCPEchoServer4 很像，修改的地方有

serverSocket 要指定使用 IPv6 地址

~~~c
int servSock;
if((servSock = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP)) < 0){
    DieWithSystemMessage("socket() failed");
}
~~~

servAddr 的构造，使用 IPv6 协议族

~~~c
struct sockaddr_in6 servAddr;
memset(&servAddr, 0, sizeof(servAddr));
servAddr.sin6_family = AF_INET6;
servAddr.sin6_addr = in6addr_any;
servAddr.sin6_port = htons(servPort);
~~~

同理，接收到的 socket 地址传参也要相应改变

~~~c
for(;;){
    struct sockaddr_in6 clntAddr;
    //...
	char clntName[INET6_ADDRSTRLEN];
    if(inet_ntop(AF_INET6, &clntAddr.sin6_addr, clntName, sizeof(clntName)) != NULL){
        printf("Handling client %s:%d\n", clntName, ntohs(clntAddr.sin6_port));
    } else {
        puts("Unable to get client address");
    }	
}
~~~

这里的端口仍为十进制，懂？只有 IP 地址区分 IPv4 和IPv6，端口仍是1-65535

~~~bash
./TCPEchoServer6 9999
~~~

## 域名和协议族

### 将名称映射到数字

addrinfo 的结构如下

~~~c
struct addrinfo{
    int ai_flags;
    int ai_family;
    int ai_socktype;
    int ai_protocol;
    socklen_t ai_addrlen;
    struct sockaddr * ai_addr;
    char *ai_canonname;
    struct addrinfo *ai_next;
};
~~~

AddressUtility.c

- 打印地址信息结构体 socketaddr

  ~~~c
  struct sockaddr
  {
    __SOCKADDR_COMMON (sa_);	/* Common data: address family and length.  */
    char sa_data[14];		/* Address data.  */
  };
  
  #define	__SOCKADDR_COMMON(sa_prefix) sa_family_t sa_prefix##family
  ~~~

- 将根据 socketaddr.sa_family 判断是 IPv4/IPv6，并作出不同处理，给空指针 numericAddress 赋予不同类型的值

~~~c
#include <netdb.h>
#include <stdio.h>
#include <arpa/inet.h>

void PrintSocketAddress(const struct sockaddr *address, FILE *stream){
    if(address == NULL || stream == NULL){
        return;
    }
    void *numericAddress;
    char addrBuffer[INET6_ADDRSTRLEN];
    in_port_t port;
    switch(address->sa_family){
        case AF_INET:
            numericAddress = &((struct sockaddr_in *) address)->sin_addr;
            port = ntohs(((struct sockaddr_in *) address)->sin_port);
            break;
        case AF_INET6:
            numericAddress = &((struct sockaddr_in6 *) address)->sin6_addr;
            port = ntohs(((struct sockaddr_in6 *) address) -> sin6_port);
            break;
        default:
            fputs("[unknown type]", stream);
            return;
    }

    if(inet_ntop(address->sa_family, numericAddress, addrBuffer, sizeof(addrBuffer)) == NULL){
        fputs("[invalid address]", stream);
    } else {
        fprintf(stream, "%s", addrBuffer);
        if(port != 0){
            fprintf(stream, "-%u", port);
        }
    }
}
~~~

GetAddrInfo.c

- 其实很简单，就是调用 netdb 库中 getaddrInfo() 函数获取`IP/域名+端口/服务`信息，传入参数依次为 IP/域名、端口/服务、用于储存当前信息的结构体 addrinfo、用于储存所有信息的链表 addrinfo
- PrintSocketAddress() 用以打印获取到的地址信息

~~~c
include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../util/Practical.h"

int main(int argc, char *argv[]){

    // 检查参数长度
    if(argc != 3){
        DieWithUserMessage("Parameter(s)", "<Address/Name> <Port/Service>");
    }

    char *addrString = argv[1];
    char *portString = argv[2];

    
    struct addrinfo addrCriteria;
    memset(&addrCriteria, 0, sizeof(addrCriteria));
    addrCriteria.ai_family = AF_UNSPEC;
    addrCriteria.ai_socktype = SOCK_STREAM;
    addrCriteria.ai_protocol = IPPROTO_TCP;
    // printf("hahaha\n");

    struct addrinfo *addrList;
    int rtnVal = getaddrinfo(addrString, portString, &addrCriteria, &addrList);
    if(rtnVal != 0){
        DieWithUserMessage("getaddrinfo() failed", gai_strerror(rtnVal));
    }

    for(struct addrinfo *addr=addrList; addr!=NULL; addr=addr->ai_next){
        PrintSocketAddress(addr->ai_addr, stdout);
        fputc('\n', stdout);
    }

    freeaddrinfo(addrList);
    exit(0);
}
~~~

~~~bash
./GetAddrInfo 127.0.0.1 time
127.0.0.1-37

./GetAddrInfo www.baidu.com time
183.232.231.174-37
183.232.231.172-37

./GetAddrInfo www.bilibili.com time
117.169.96.198-37
117.169.96.199-37
111.48.57.44-37
111.48.57.46-37
111.48.57.45-37
117.169.96.200-37
2409:8c38:c40:100::243-37
2409:8c38:c40:100::242-37
2409:8c4c:c00:9::23-37
2409:8c4c:c00:9::22-37
2409:8c4c:c00:9::24-37
2409:8c38:c40:100::241-37
~~~

### 通用的 TCP 客户

通用指地址通用，什么是地址通用？如以上的 PrintSocketAddress() 函数就是一个 IPv4 和 IPv6 通用的代码，他会根据进入 socketaddr 的协议族判断其地址类型并作出相应反应

接下来，我们将重写 TCP 协议下的 Socket 客户和服务器，使之地址通用，拥有自选共能

TCPClientUtility.c

- 实现 SetupTCPClientSocket 方法，传入主机名（IP/域名）和服务（服务名/端口号），与之建立连接，返回建立好的 socket 套接字编号
- 这里会持续搜索服务器端口直到建立连接或全部搜索完，失败将返回 -1

~~~c
int SetupTCPClientSocket(const char *host, const char *service){
    struct addrinfo addrCriteria;
    memset(&addrCriteria, 0, sizeof(addrCriteria));
    addrCriteria.ai_family = AF_UNSPEC;
    addrCriteria.ai_socktype = SOCK_STREAM;
    addrCriteria.ai_protocol = IPPROTO_TCP;

    struct addrinfo *servAddr;
    int rtnVal = getaddrinfo(host, service, &addrCriteria, &servAddr);
    if(rtnVal != 0){
        DieWithUserMessage("getaddrinfo() failed", gai_strerror(rtnVal));
    }

    int sock = -1;
    for(struct addrinfo *addr = servAddr; addr != NULL; addr = addr->ai_next){
        sock = socket(addr->ai_family, addr->ai_socktype, addr->ai_protocol);
        if(sock < 0){
            continue;
        }
        // connet 返回 0 表示 socket 创建成功，返回 socket 编码
        if(connect(sock, addr->ai_addr, addr->ai_addrlen) == 0){
            break;
        }
        close(sock);
        sock = -1;
    }

    freeaddrinfo(servAddr);
    return sock;
}
~~~

TCPEchoClient.c

- 与特定客户中建立 socket 的方法不一样，其余完全保持一致
- 在原先的 TCPEchoClient4.c 中，是先建立一个空的 socket，然后填充服务器信息，最后使用 connect 函数对 socket 和服务器进行连接

~~~c
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include "../util/Practical.h"

int main(int argc, char *argv[]){
    
    if(argc<3 || argc>4){
        DieWithUserMessage("Parameter(s)", "<Server Address> <Echo Word> [<Server Port>]");
    }
    char *server = argv[1];
    char *echoString = argv[2];
    // cout << servIP << endl << echoString << endl;

    // echo 应答程序的端口默认为7
    char *service;
    if(argc == 4){
        service = argv[3];
    } else {
        service = "echo";
    }
    
    int sock = SetupTCPClientSocket(server, service);

    if(sock < 0){
        DieWithUserMessage("SetupTCPClientSocket() failed", "unable to connect");
    }
    
    size_t echoStringLen = strlen(echoString);
    ssize_t numBytes = send(sock, echoString, echoStringLen, 0);
    
    if(numBytes < 0){
        DieWithSystemMessage("send() failed");
    } else if(numBytes != echoStringLen){
        DieWithUserMessage("send()", "send unexpedted number of bytes");
    }

    unsigned int totalBytesRcvd = 0;
    fputs("Received: ", stdout);
    while(totalBytesRcvd < echoStringLen){
        // printf("hahaha");
        char buffer[BUFSIZ];
        numBytes = recv(sock, buffer, BUFSIZ-1, 0);
        if(numBytes < 0){
            DieWithSystemMessage("recv() failed");
        } else if(numBytes == 0){
            DieWithUserMessage("recv()", "connection closed prematurely");
        }
        totalBytesRcvd += numBytes;
        buffer[numBytes] = '\0';
        fputs(buffer, stdout);
    }

    fputc('\n', stdout);
    close(sock);
    exit(0);
}
~~~

### 通用的 TCP 服务器

在 TCPServerUtility.c 中添加两个函数（原有 HandleTCPClient）

- SetupTCPServerSocket(const char *service) 根据传入的服务或接口建立一个 TCP Socket 服务器并开始监听该端口

  封装了 socket() bind() listen() 三个步骤

- AcceptTCPConnection(int servSock) 在服务端口上接收连接，封装了 accept() 函数

~~~c
#include <unistd.h> //close
#include <stdio.h>
#include <netdb.h>
#include <string.h>

static const int MAXPENDING = 5;

int SetupTCPServerSocket(const char *service){
    struct addrinfo addrCriteria;
    memset(&addrCriteria, 0, sizeof(addrCriteria));
    addrCriteria.ai_family = AF_UNSPEC;
    addrCriteria.ai_flags = AI_PASSIVE;
    addrCriteria.ai_socktype = SOCK_STREAM;
    addrCriteria.ai_protocol = IPPROTO_TCP;

    struct addrinfo *servAddr;
    int rtnVal = getaddrinfo(NULL, service, &addrCriteria, &servAddr);
    if(rtnVal != 0){
        DieWithUserMessage("getaddrinfo() failed", gai_strerror(rtnVal));
    }

    int servSock = -1;
    for(struct addrinfo *addr = servAddr; addr != NULL; addr = addr->ai_next){
        servSock = socket(servAddr->ai_family, servAddr->ai_socktype, servAddr->ai_protocol);
        if(servSock < 0){
            continue;
        }
        int bindRtn = bind(servSock, servAddr->ai_addr, servAddr->ai_addrlen);
        int listRtn = listen(servSock, MAXPENDING);
        if(bindRtn == 0 && listRtn == 0){
            struct sockaddr_storage localAddr;
            socklen_t addrSize = sizeof(localAddr);
            if(getsockname(servSock, (struct sockaddr *)&localAddr, &addrSize) < 0){
                DieWithSystemMessage("getsockname() failed");
            }
            fputs("Binding to ", stdout);
            PrintSocketAddress((struct sockaddr *) &localAddr, stdout);
            fputc('\n', stdout);
            break;
        }

        close(servSock);
        servSock = -1;
    }

    freeaddrinfo(servAddr);
    return servSock;
}

int AcceptTCPConnection(int servSock){
    struct sockaddr_storage clntAddr;
    socklen_t clntAddrLen = sizeof(clntAddr);

    int clntSock = accept(servSock, (struct sockaddr *)&clntAddr, &clntAddrLen);
    if(clntSock < 0){
        DieWithSystemMessage("accept() failed");
    }

    fputs("Handling client ", stdout);
    PrintSocketAddress((struct sockaddr *) &clntAddr, stdout);
    fputc('\n', stdout);

    return clntSock;
}
~~~

TCPEchoServer.c

- socket() 创建、bind() 绑定、listen() 监听服务器端口
- accept 接收连接（阻塞）

~~~c
#include <stdio.h>
#include <unistd.h>
#include "../util/Practical.h"

int main(int argc, char *argv[]){

    if(argc != 2){
        DieWithUserMessage("Parameter(s)", "<>Server Port/Service");
    }

    char *service = argv[1];

    int servSock = SetupTCPServerSocket(service);
    if(servSock < 0){
        DieWithUserMessage("SetupTCPServerSocket() failed", service);
    }

    for(;;){
        int clntSock = AcceptTCPConnection(servSock);
        HandleTCPClient(clntSock);
        close(clntSock);
    }
}
~~~

### 从数字获取名称

与 getaddrinfo() 相反，提供有函数 getnameinfo()

~~~c
int getnameinfo(const struct sockaddr *address, socklen_t addressLength, char *node, socklen_t nodeLength, char *service, socklen_t serviceLength, int flags)
~~~

