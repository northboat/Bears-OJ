---
title: UDP & Data Transmission
date: 2022-7-24
tags:
  - Network
---

## 使用 UDP 套接字

TCP 像是打电话，在通信前需要连接（三次握手 connect），通信后需要断开连接（四次挥手 close），而 UDP 像是发送邮件，既不需要连接更不需要断开连接，通过 sendto() 函数直接发送到某一个 UDP 服务器/客户，通过 recvfrom() 函数直接接收某一个客户/服务器发送的信息

另外，UDP 套接字提供的通信传输是一种尽力而为的服务（点到点），它不保证通过 UDP 套接字发送的信息将会到达目的地，这意味着 UDP 套接字程序需要准备处理消息的丢失和重排

网络模型

- OIS 七层模型
- TCP/IP 四层模型
- TCP/IP 五层模型

<img src="../../../../.vuepress/public/img/netmodel.png">

OSI 模型与 TCP/IP 模型对照

<img src="../../../../.vuepress/public/img/osimodel.png">

点到点、端到端服务：在TCP/IP协议中，数据网络层（网际网层/IP层）及以下提供点到点服务，传输层提供端到端服务。端到端与点到点是针对网络中传输的两端设备间的关系而言的

- end to end：指的是在数据传输前，经过各种各样的交换设备，在两端设备问建立一条链路，就僚它们是直接相连的一样，链路建立后，发送端就可以发送数据，直至数据发送完毕，接收端确认接收成功。TCP 属于端到端协议

  - 优点：链路建立后，发送端知道接收设备一定能收到，而且经过中间交换设备时不需要进行存储转发，因此传输延迟小

  - 缺点：

    直到接收端收到数据为止，发送端的设备一直要参与传输。如果整个传输的延迟很长，那么对发送端的设备造成很大的浪费

    如果接收设备关机或故障，那么端到端传输不可能实现

- point to point：指的是发送端把数据传给与它直接相连的设备，这台设备在合适的时候又把数据传给与之直接相连的下一台设备，通过一台一台直接相连的设备，把数据传到接收端

  - 优点：

    发送端设备送出数据后，它的任务已经完成，不需要参与整个传输过程，这样不会浪费发送端设备的资源

    即使接收端设备关机或故障，点到点传输也可以采用存储转发技术进行缓冲

  - 缺点：发送端发出数据后，不知道接收端能否收到或何时能收到数据，比如 UDP

### UDP 客户

UDPEchoClient.c

~~~c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include "../util/Practical.h"

#define MAXSTRINGLENGTH 1024

int main(int argc, char *argv[]){

    if(argc < 3 || argc > 4){
        DieWithUserMessage("Parameter(s)",
            "<Server Address/Name> <Echo Word> [<Server Port/Service>]");
    }

    char *server = argv[1];
    char *echoString = argv[2];

    size_t echoStringLen = strlen(echoString);
    if(echoStringLen > MAXSTRINGLENGTH){
        DieWithUserMessage("echoString", "string too long");
    }

    char *serverPort;
    if(argc == 4){
        serverPort = argv[3];
    } else {
        serverPort = "echo";
    }

    struct addrinfo addrCriteria;
    memset(&addrCriteria, 0, sizeof(addrCriteria));
    addrCriteria.ai_family = AF_UNSPEC; // 任意IP地址
    addrCriteria.ai_socktype = SOCK_DGRAM; // datagram 报文套接字，并非流式
    addrCriteria.ai_protocol = IPPROTO_UDP;

    struct addrinfo *servAddr;
    int rtnVal = getaddrinfo(server, serverPort, &addrCriteria, &servAddr);
    if(rtnVal != 0){
        DieWithUserMessage("getaddrinfo() failed", gai_strerror(rtnVal));
    }

    int sock = socket(servAddr->ai_family, servAddr->ai_socktype, servAddr->ai_protocol); // UDP
    if(sock < 0){
        DieWithSystemMessage("socket() failed");
    }

    ssize_t numBytes = sendto(sock, echoString, echoStringLen, 0,
        servAddr->ai_addr, servAddr->ai_addrlen);
    if(numBytes < 0){
        DieWithSystemMessage("sendto() failed");
    } else if(numBytes != echoStringLen){
        DieWithUserMessage("sendto() error", "sent unexpected number of bytes");
    }

    struct sockaddr_storage fromAddr;
    socklen_t fromAddrLen = sizeof(fromAddr);
    char buffer[MAXSTRINGLENGTH+1];
    numBytes = recvfrom(sock, buffer, MAXSTRINGLENGTH, 0,
        (struct sockaddr *) &fromAddr, &fromAddrLen);
    if(numBytes < 0){
        DieWithSystemMessage("recvfrom() failed");
    } else if(numBytes != echoStringLen){
        DieWithUserMessage("recvfrom() error", "received unexpected number of bytes");
    }

    if(!SockAddrsEqual(servAddr->ai_addr, (struct sockaddr *) &fromAddr)){
        DieWithUserMessage("recvfrom()", "recvived a packet from unknown source");
    }

    freeaddrinfo(servAddr);

    buffer[echoStringLen] = '\0';
    printf("Recvived: %s\n", buffer);

    close(sock);
    exit(0);
}
~~~

### UDP 服务器

UDPEchoServer.c

~~~c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "../util/Practical.h"
#define MAXSTRINGLENGTH 1024


int main(int argc, char *argv[]){
    if(argc != 2){
        DieWithUserMessage("Parameter(s)", "<Server Port>");
    }

    char *service = argv[1];

    int servSock;
    if((servSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0){
        DieWithSystemMessage("socket() failed");
    }

    struct addrinfo addrCriteria;
    memset(&addrCriteria, 0, sizeof(addrCriteria));
    addrCriteria.ai_family = AF_UNSPEC; // 任意地址族
    addrCriteria.ai_socktype = SOCK_DGRAM; // datagram 报文套接字，并非流式
    addrCriteria.ai_protocol = IPPROTO_UDP;
    addrCriteria.ai_flags = AI_PASSIVE;

    struct addrinfo *servAddr;
    int rtnVal = getaddrinfo(NULL, service, &addrCriteria, &servAddr);
    if(rtnVal != 0){
        DieWithUserMessage("getaddrinfo() failed", gai_strerror(rtnVal));
    }

    int sock = socket(servAddr->ai_family, servAddr->ai_socktype, servAddr->ai_protocol);
    if(sock < 0){
        DieWithSystemMessage("socket() failed");
    }

    if(bind(sock, servAddr->ai_addr, servAddr->ai_addrlen) < 0){
        DieWithSystemMessage("bing() failed");
    }

    freeaddrinfo(servAddr);

    // while(1)
    for(;;){
        struct sockaddr_storage clntAddr;
        socklen_t clntAddrLen = sizeof(clntAddr);

        char buffer[MAXSTRINGLENGTH];
        ssize_t numBytesRcvd = recvfrom(sock, buffer, MAXSTRINGLENGTH, 0,
            (struct sockaddr *) &clntAddr, &clntAddrLen);
        if(numBytesRcvd < 0){
            DieWithSystemMessage("recvfrom() failed");
        }

        fputs("Handling client ", stdout);
        PrintSocketAddress((struct sockaddr *) &clntAddr, stdout);
        fputc('\n', stdout);
        
        ssize_t numBytesSent = sendto(sock, buffer, numBytesRcvd, 0,
            (struct sockaddr *) &clntAddr, sizeof(clntAddr));
        if(numBytesSent < 0){
            DieWithSystemMessage("sendto() failed");
        } else if(numBytesRcvd != numBytesSent){
            DieWithUserMessage("sendto() ", "sent unexpected number of bytes");
        }
    }
}
~~~

## 发送和接收数据

协议：关于在通信信道上交换的信息（数据）的形式和含义的协定

TCP/IP 协议在传输用户数据的字节时，将不会检查或修改它们，但信息必须以块的形式发送和接收，这些块的长度是 8 的倍数，因此我们将消息视作字节（byte）的序列

### 编码整数

在某种意义上，所有类型的信息最终都将被编码乘固定大小的整数

#### 整数大小

Sizing.c

~~~c
#include <limits.h>
#include <stdlib.h>
#include <stdio.h>
#include <cstdint>

int main(){
    printf("CHAR_BIT is %d\n\n", CHAR_BIT); //char数据字节大小
    printf("sizeof char is %d\n\n", sizeof(char));
    printf("sizeof short is %d\n\n", sizeof(short));
    printf("sizeof int is %d\n\n", sizeof(int));
    printf("sizeof long is %d\n\n", sizeof(long));
    printf("sizeof long long is %d\n\n", sizeof(long long));
    
    printf("sizeof int8_t is %d\n\n", sizeof(int8_t));
    printf("sizeof int16_t is %d\n\n", sizeof(int16_t));
    printf("sizeof int32_t is %d\n\n", sizeof(int32_t));
    printf("sizeof int64_t is %d\n\n", sizeof(int64_t));

    printf("sizeof uint8_t is %d\n\n", sizeof(uint8_t));
    printf("sizeof uint16_t is %d\n\n", sizeof(uint16_t));
    printf("sizeof uint32_t is %d\n\n", sizeof(uint32_t));
    printf("sizeof uint64_t is %d\n\n", sizeof(uint64_t));
}
~~~

~~~bash
CHAR_BIT is 8
sizeof char is 1
sizeof short is 2
sizeof int is 4
sizeof long is 8
sizeof long long is 8

sizeof int8_t is 1
sizeof int16_t is 2
sizeof int32_t is 4
sizeof int64_t is 8

sizeof uint8_t is 1
sizeof uint16_t is 2
sizeof uint32_t is 4
sizeof uint64_t is 8
~~~

#### 字节排序

大端顺序和小端顺序：对于字节序列 AB（十六进制）

- 大端顺序：1011
- 小端顺序：1110

Internet 中大多数都使用大端顺序，于是大端顺序也被称为**网络字节顺序**；由硬件使用的字节顺序（可能大端可能小端），被称为**本机字节顺序**。在进行信息传递时，必须保证客户和服务器采用同种字节顺序，这里就涉及到了字节顺序的转换

在 Socket API 中提供了类似与 htons()/htonl() 的字节顺序转换函数，并且总是统一使用网络字节顺序，即大端顺序

- hton() 意为 host to network short，htonl() 意为 host to network long
- 相对应有 ntohs()/ntohl()，network to host

#### 符号性和符号扩展



### 构造、成帧和解析消息

## 超越基本的套接字编程

### 套接字选项

### 信号

### 非阻塞 I/0

### 多任务处理

### 多路复用

### 多个接收者

