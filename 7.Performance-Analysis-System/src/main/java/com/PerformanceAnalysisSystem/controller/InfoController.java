package com.PerformanceAnalysisSystem.controller;


import com.PerformanceAnalysisSystem.dao.StudentDao;
import com.PerformanceAnalysisSystem.dao.TeacherDao;
import com.PerformanceAnalysisSystem.pojo.Grades;
import com.PerformanceAnalysisSystem.pojo.Student;
import com.PerformanceAnalysisSystem.pojo.Teacher;
import com.PerformanceAnalysisSystem.service.StudentService;
import com.PerformanceAnalysisSystem.utils.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Controller
public class InfoController {

    //搜索功能：根据id或name进行搜索，用Character.isDigit判断搜索内容
    @RequestMapping("/search")
    public String search(@RequestParam("searchContent") String content,
                         Model model){

        boolean flag = false;
        for(char c: content.toCharArray()){
            if(!Character.isDigit(c)){
                flag = true;
                break;
            }
        }
        if(flag){
            Collection<Student> students = StudentDao.getStudentsByName(content);
            if(students.size() == 0){
                model.addAttribute("msg", "查无此人");
                return "display/404";
            }
            StudentService studentService = new StudentService();
            for(Student stu: students){
                studentService.setRanks(stu);
            }
            model.addAttribute("students", students);
            return "display/search";
        } else{
            List<Student> students = new ArrayList<>();
            Student student = StudentDao.getStudentById(Integer.valueOf(content));
            if(student == null){
                //System.out.println("nmsl");
                model.addAttribute("msg", "查无此人");
                return "display/404";
            }
            StudentService studentService = new StudentService();
            studentService.setRanks(student);
            students.add(student);
            model.addAttribute("students", students);
            return "display/search";
        }
    }


    //修改功能
    //获取信息，跳转
    @GetMapping("/update/{path}/{id}")
    public String update(@PathVariable("id") Integer id,
                         @PathVariable("path") String path,
                         HttpSession session){

        session.setAttribute("id", id);
        session.setAttribute("student", StudentDao.getStudentById(id));
        session.setAttribute("path", path);
        //System.out.println(student);
        //System.out.println(request);
        return "redirect:/update";
    }

    //修改学生信息
    @RequestMapping("/updateInfo")
    public String update(HttpSession session, @RequestParam("chi") String chi,
                         @RequestParam("math") String math, @RequestParam("en") String en,
                         @RequestParam("phy") String phy, @RequestParam("chem") String chem,
                         @RequestParam("bio") String bio, @RequestParam("pol") String pol,
                         @RequestParam("his") String his, @RequestParam("geo") String geo){

        String path = (String)session.getAttribute("path");
        int id = (Integer)session.getAttribute("id");

        StudentDao.update(id, new Grades(Integer.valueOf(chi), Integer.valueOf(math), Integer.valueOf(en),
                                         Integer.valueOf(phy), Integer.valueOf(chem), Integer.valueOf(bio), Integer.valueOf(pol),
                                         Integer.valueOf(his), Integer.valueOf(geo)));

        System.out.println("已修改：" + StudentDao.getStudentById(id));

        session.removeAttribute("id");
        session.removeAttribute("path");
        return "redirect:/" + path;
    }


    //删除功能，删除后重定向回当前页面（搜索栏则返回总览页面）
    @GetMapping("/drop/{path}/{id}")
    public String drop(@PathVariable("id") Integer id,
                       @PathVariable("path") String path){
        System.out.println("删除：" + StudentDao.getStudentById(id));
        StudentDao.delete(id);
        //虽说是重定向，但是是定向到某个请求上，重新加载一波数据后再跳转到页面
        return "redirect:/"+path;
    }


    //添加功能，添加成功后仍停留在form页面，提示用户添加成功
    @RequestMapping("/add")
    public String add(@RequestParam("id") String id, @RequestParam("name") String name,
                      @RequestParam("gender") String gender, @RequestParam("chi") String chi,
                      @RequestParam("math") String math, @RequestParam("en") String en,
                      @RequestParam("phy") String phy, @RequestParam("chem") String chem,
                      @RequestParam("bio") String bio, @RequestParam("pol") String pol,
                      @RequestParam("his") String his, @RequestParam("geo") String geo, Model model){

        if(name.equals("") || gender.equals("") || chi.equals("") || math.equals("") || en.equals("") ||
                phy.equals("") || chem.equals("") || bio.equals("") || pol.equals("") || his.equals("") || geo.equals("")) {

            model.addAttribute("msg", "请按照格式填写信息");
            return "manage/form";
        }

        if(!id.equals("")){
            for(char c: id.toCharArray()){
                if(!Character.isDigit(c)){
                    model.addAttribute("msg", "请按照格式填写学号");
                    return "manage/form";
                }
            }
        }

        Integer ID = null;
        if(!id.equals("")){
            ID = Integer.parseInt(id);
        }
        try{
            StudentDao.add(new Student(ID, name, Integer.valueOf(gender),
                           new Grades(Integer.valueOf(chi), Integer.valueOf(math), Integer.valueOf(en),
                                      Integer.valueOf(phy), Integer.valueOf(chem), Integer.valueOf(bio),
                                      Integer.valueOf(pol), Integer.valueOf(his), Integer.valueOf(geo))));
            System.out.println("已添加：" + StudentDao.getStudentById(ID));
        }catch (NumberFormatException e){
            model.addAttribute("msg", "请按照格式填写信息");
            return "manage/form";
        }
        model.addAttribute("msg", "录入成功!");
        return "manage/form";
    }


    //生成文件
    @GetMapping("/save/{subject}")
    public String save(@PathVariable("subject")String subject,
                       HttpSession session){
        try{
            IOUtils.write(subject);
            session.setAttribute("message", "生成文件成功，路径为D:\\" + subject + ".txt");
        }catch (IOException e){
            session.setAttribute("message", "生成文件失败");
        }
        return "redirect:/main";
    }

    //合并文件功能
    @PostMapping(value = "/merge",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String merge(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        @RequestPart("file") MultipartFile file, Model model){

        //检查用户权限
        Map<String, Teacher> teachers = TeacherDao.getTeachers();
        if(teachers.containsKey(account) && teachers.get(account).getPassword().equals(password)){
            try{
                IOUtils.merge(file);
                model.addAttribute("msg", "合并信息成功!");
            }catch (Exception e){
                model.addAttribute("msg", "合并信息失败，请检查文件格式");
            }
            return "manage/form";
        }

        model.addAttribute("msg", "请检查账号密码");
        return "manage/form";

    }

}
