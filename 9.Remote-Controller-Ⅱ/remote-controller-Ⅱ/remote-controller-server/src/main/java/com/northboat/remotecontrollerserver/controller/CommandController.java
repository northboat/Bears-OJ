package com.northboat.remotecontrollerserver.controller;

import com.northboat.remotecontrollerserver.service.impl.CommandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/exec")
public class CommandController {

    private CommandServiceImpl commandService;
    @Autowired
    public void setCommandServiceImpl(CommandServiceImpl commandServiceImpl){
        this.commandService = commandServiceImpl;
    }


    @RequestMapping("/shutdown")
    public String shutdown(Model model, String token){
        String result = commandService.shutdown(token);
        System.out.println(token + "执行关机命令\t执行结果:" + result);
        model.addAttribute("result", result);
        return "index";
    }


    @RequestMapping("/ipconfig")
    public String ipConfig(Model model, String token){
        String result = commandService.ipconfig(token);
        System.out.println(token + "执行ipconfig命令\t执行结果:" + result);
        model.addAttribute("result", result);
        return "index";
    }

    @RequestMapping("/clean")
    public String clean(Model model, String token){
        String result = commandService.clean(token);
        System.out.println(token + "执行清除命令\t执行结果:" + result);
        model.addAttribute("result", result);
        return "index";
    }

}
