# Validation

## 一、导入依赖

~~~xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
~~~

## 二、基本使用

在传入参数处添加`@Valid`注解

~~~java
@RequestMapping("/doLogin")
@ResponseBody
public RespBean doLogin(@Valid LoginVo loginVo){
    log.info("{}", loginVo);
    //System.out.println(loginVo.getUsername());
    return userService.doLogin(loginVo);
}
~~~

在对应`vo`处添加规则

~~~java
@Data
public class LoginVo {
    @NotNull
    @Email
    private String username;
    @Length(min = 8, max = 26)
    private String password;
}
~~~

## 三、自定义注解

新建包`validation`，放置注解

### 1、编写注解

`@NotNull`源码

~~~java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package javax.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NotNull.List.class)
@Documented
@Constraint(
    validatedBy = {}
)
public @interface NotNull {
    String message() default "{javax.validation.constraints.NotNull.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        NotNull[] value();
    }
}
~~~

- 其中`String message()`为报错信息

新建`validation`包，新建所需注解文件，参照`@NotNull`源码，将其上注解拷贝，注入`Validation`，注意删掉`@Repeatable`注解

~~~java
package com.seckill.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint( validatedBy = {} )
public @interface IsMobile {

    boolean required() default true;

    String message() default "电话号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
~~~

### 2、编写实现类

真正的实现逻辑由`@Constraint( validatedBy = {} )`注解注入自定义注解

在`vo`包下新建类`IsMobileValidator`实现`ConstraintValidator`接口，在`isValid`方法中实现逻辑

在`initialize`中可以进行很多参数传递：

- 在注解中定义方法接收用户参数
- 在`ConstraintValidator`类的`initialize`方法中调用注解方法传递参数
- 在`isValid`方法中使用参数，编写逻辑

~~~java
package com.seckill.vo;

import com.seckill.utils.ValidatorUtil;
import com.seckill.validation.IsMobile;
import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    // 接收注解收到的逻辑
    private boolean required;

    //初始化
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    // 逻辑实现
    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext constraintValidatorContext) {
        // 如果设置required为true，即为设置必须有值，直接通过util判断即可
        if(required){
            return ValidatorUtil.checkMobile(mobile);
        }
        // 若required为false，即为设置可为空，当mobile为空返回true，不为空则调用util
        if(StringUtils.isEmpty(mobile)){
            return true;
        }
        return ValidatorUtil.checkMobile(mobile);
    }
}

~~~

### 3、注入注解

~~~java
@Constraint( validatedBy = {IsMobileValidator.class} )
~~~

### 4、异常处理

在`controller`接收参数时加上注解，若参数不匹配浏览器将直接报错`400 bad request`，后端报错`org.springframework.validation.BindException`

在`spring-boot`中处理异常通常有两种方式

- `@ControllerAdvice`和`@ExceptionHandler`配合使用
- `ErrorController`类实现

第一种只能处理控制台异常，第二种可以处理404、400等错误，这里使用第一种方式进行处理

新建`excption`包，编写异常处理类和自定义异常类

`GlobalExceptionHandler.java`：异常处理类

~~~java
package com.seckill.exception;

import com.seckill.vo.RespBean;
import com.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException ge = (GlobalException)e;
            return RespBean.error(ge.getRespBeanEnum());
        } else if(e instanceof BindException){
            BindException be = (BindException)e;
            RespBean respBean = RespBean.error(RespBeanEnum.LOGIN_PATTERN_ERROR);
            respBean.setMessage("参数校验异常：" + be.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        return RespBean.error(RespBeanEnum.SERVER_ERROR);
    }
}
~~~

`@ControllerAdvice/RestControllerAdvice`会主动接管项目中出现的所有异常，即当出现异常时，`spring-boot`将主动调用被修饰类中的方法

`@ExceptionHandler`用于修饰方法，限定该方法处理的异常类型

- 二者关系类似于`@Controller`和`@RequestMapping`的关系

`GlobalException`：自定义异常类，方便接收和返回不同的异常，内置`RespBean`

~~~java
package com.seckill.exception;


import com.seckill.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;
}
~~~

具体使用

`UserServiceImpl.java`：`Service`层主动抛出异常

```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private UserMapper userMapper;
    @Resource
    public void setUserMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    /**
     * 登录功能
     * @param loginVo
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        System.out.println(username);

        User user = userMapper.selectById(username);
        if(user == null){
            throw new GlobalException(RespBeanEnum.LOGIN_NOT_FOUND);
        }
        if(!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_PASSWORD_WRONG);
        }
        return RespBean.success();
    }
}
```

`LoginController.java`：`Controller`层进行`@Valid`验证被动抛出异常`org.springframework.validation.BindException`，通过自己编写的`Handler`进行捕获处理，并返回一个`RespBean`

~~~java
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    
    private IUserService userService;
    @Autowired
    public void setUserService(IUserService userService){
        this.userService = userService;
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo){ //参数校验
        log.info("{}", loginVo);
        //模拟前端进行一次MD5加密
        loginVo.setPassword(MD5Util.inputPassToFormPass(loginVo.getPassword()));
        //System.out.println(loginVo.getUsername());
        return userService.doLogin(loginVo);
    }
}
~~~

