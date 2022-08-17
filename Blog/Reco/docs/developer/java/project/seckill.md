---
title: 基于Redis、RabbitMQ的秒杀系统
date: 2022-5-12
tags:
  - Web
  - Java
categories:
  - WebApp
---

> 秒杀系统后台

## 准备

### 预备知识

**学习目标：怎么去应对高并发**

安全优化：

- 隐藏秒杀地址
- 验证码
- 接口限流

服务优化：

- RabbitMQ消息队列
- 接口优化
- 分布式锁

页面优化：

- 缓存
- 静态化分离

分布式会话：

- 用户登录
- 共享Session

功能开发：

- 商品列表
- 商品详情
- 秒杀
- 订单详情

系统压测：

- JMeter入门
- 自定义变量
- 正式压测

**解决问题：并发读、并发写**

稳、准、快 ——> 高性能、一致性、高可用

### 新建项目

新建SpringBoot项目：

- Spring Web
- Thymeleaf
- MySQL Driver
- Lombok

导入MyBatis-Plus

~~~xml
<!--mybatis-plus-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.1</version>
</dependency>
~~~

导入MD5依赖

~~~xml
<!--md5依赖-->
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.6</version>
</dependency>
~~~

### MD5双层加密

注册的时候，前端输入密码，js先做一层md5加密，掺入随机生成的salt，传入后端后再做一次md5加密

**MD5Util.java**

~~~java
package com.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }


    private static final String salt="1a2b3c4d";

    // 明文到表单密码
    public static String inputPassToFormPass(String inputPass){
        String str = salt.charAt(2)+salt.charAt(1)+inputPass+salt.charAt(4)+salt.charAt(0);
        return md5(str);
    }

    // 表单密码到数据库密码
    public static String formPassToDBPass(String formPass, String salt){
        String str = salt.charAt(2)+salt.charAt(1)+formPass+salt.charAt(4)+salt.charAt(0);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt){
        String form = inputPassToFormPass(inputPass);
        return formPassToDBPass(form, salt);
    }
	
    // 测试
    public static void main(String[] args) {
        //fbe4b969a8c9c22f88f0e4f79ee1c31f
        System.out.println(inputPassToFormPass("123456"));

        //b9ce790c76b354ce40e2c2e82e562d80
        System.out.println(formPassToDBPass("fbe4b969a8c9c22f88f0e4f79ee1c31f", "1a2b3c4d"));

        //b9ce790c76b354ce40e2c2e82e562d80
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }
}
~~~

### MyBatis-Plus逆向工程

新建一个项目：generator

引入依赖

~~~xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>


    <!--mybatis-plus-->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.4.0</version>
    </dependency>
    <!--mybatis逆向工程代码生成-->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-generator</artifactId>
        <version>3.4.0</version>
    </dependency>
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.31</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

    <!--lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
~~~

编写 CodeGenerator.java

- 修改数据库连接
- 配置包名
- 配置模板引擎
- 修改模板类名
- 设置命名策略：下划线转驼峰

~~~java
package com.northboat.generator;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("NorthBoat");
        gc.setOpen(false);
        // xml开启 BaseResultMap
        gc.setBaseResultMap(true);
        // xml开启 BaseColumnList
        gc.setBaseColumnList(true);
        //日期格式，采用Date
        gc.setDateType(DateType.ONLY_DATE);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://39.106.160.174:3306/seckill?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("011026");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // pc.setModuleName(scanner("模块名"));
        pc.setParent("com.seckill")
                .setEntity("pojo")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/entity2.java")
                .setMapper("templates/mapper2.java")
                .setServiceImpl("templates/serviceImpl2.java")
                .setController("templates/controller2.java");

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);

        // 数据库表字段映射到实体的命名策略，下划线转驼峰：如 user ——> User
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");

        // lombok模型
        strategy.setEntityLombokModel(true);

        // 生成 @RestController 控制器
        // strategy.setRestControllerStyle(true);
        // 公共父类
        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);

        // 表前缀
        strategy.setTablePrefix("t_");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
~~~

将`External Libraries/Maven: com.baomidou:mybatis-plus-generator:3.4.0/mybatis-plus-generator-3.4.0.jar/templates`中的模板复制到项目`resources/templates`下，与`generator.java`中的模板名保持一致

执行Main方法，输入表名即可生成相应代码，复制进原项目即可。别忘了`resources/mapper`下的`xml`文件

### 公用返回结果的枚举类

在`vo`包下编写

`1、RespBeanEnum`

~~~java
package com.seckill.vo;

//状态码，信息提示

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200, "SUCCESS"),
    ERROR(500, "SERVER INTERNAL ERROR");

    private final Integer code;
    private final String message;
}
~~~

`2、RespBean`

~~~java
package com.seckill.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    //成功的返回结果
    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    //失败返回结果
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum, Object obj){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
~~~

### 跳转测试

`controller`包下编写测试类

~~~java
package com.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {

    /**
     * 功能描述：测试页面跳转
     * @param model
     * @return
     * @Author: NorthBoat
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "NorthBoat");
        return "hello";
    }
}
~~~

访问`localhost:8080/demo/hello`测试页面跳转是否成功

## 功能实现

### 登录

#### 二次加密

数据库存的是二次加密后的32位码，用户输入密码后前端进行一次加密，后端接收到的是一次加密的密码，对该一次加密后的密码二次加密，再与数据库中密码做对比

#### Cookie存取

`@CookieValue(value="", defaultVaule="", required=true)`

`required`默认为真

#### 分布式Session问题

1. session复制
   - 优点
     - 无需修改代码，只需要修改tomcat配置
   - 缺点
     - 同步传输占用内网带宽
     - 多台tomcat同步性能指数级下降
     - 占用服务端内存
2. 前端储存
   - 优点
     - 不需要占用服务端内存
   - 缺点
     - 存在安全风险
     - 大小收cookie限制
     - 占用外网贷款
3. session连滞
   - 优点
     - 无需修改代码
     - 服务端可以水平扩展
   - 缺点
     - 增加新机器会重新Hash，丢失session
     - 重启同样会丢失session
4. 后端集中存储
   - 优点
     - 安全
     - 容易水平扩展
   - 缺点
     - 增加复杂度
     - 需要修改代码

使用Redis解决分布式Session

