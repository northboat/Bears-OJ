前后端未分离，当处理了数据需要重新展示时，业务函数处理完，讲成功信息存入`HttpSession`中，然后`redirect:/pagename`，在`pagename`函数中解析`Session`信息，移除，然后通过`Model`返回前端展示

如重置密码的业务

```java
// 重置密码
@GetMapping("/reset/{name}")
public String reset(@PathVariable("name") String name, HttpSession session){
    userMapper.resetPassword(name);
    session.setAttribute("resetSuccess", 1);
    return "redirect:/user";
}
```

负责页面跳转的接口`/user`

```java
@RequestMapping("/user")
public String user(Model model, HttpSession session){
    List<User> users = userMapper.getAllUsers();
    model.addAttribute("users", users);
    // 接收 Session 信息
    if(session.getAttribute("resetSuccess") != null){
        model.addAttribute("resetSuccess", 1);
        // 立马移除 Session 信息
        session.removeAttribute("resetSuccess");
    }
    return "display/user";
}
```

最后返回到前端页面