package com.PerformanceAnalysisSystem.controller;

import com.PerformanceAnalysisSystem.dao.TeacherDao;
import com.PerformanceAnalysisSystem.pojo.*;
import com.PerformanceAnalysisSystem.service.StudentService;
import com.PerformanceAnalysisSystem.utils.IncreaseNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

//需要模板引擎支持
@Controller
public class PageController {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String pwd,
                        Model model, HttpSession session){

        Map<String, Teacher> teachers = TeacherDao.getTeachers();
        //具体业务
        if(teachers.containsKey(username) && teachers.get(username).getPassword().equals(pwd)){

            StudentService studentService = new StudentService();
            //主页信息
            MainInfo mainInfo = studentService.getMainInfo();
            model.addAttribute("mainInfo", mainInfo);

            session.setAttribute("loginUser", username);
            return "main";
        }

        model.addAttribute("msg", "用户名或者密码错误");
        return "index";
    }


    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        return "index";
    }


    @RequestMapping("/main")
    public String main(Model model, HttpSession session){

        //生成文件反馈消息
        String message = (String)session.getAttribute("message");
        //System.out.println(message);
        if(message != null){
            model.addAttribute("message", message);
            session.removeAttribute("message");
        }
        StudentService studentService = new StudentService();
        //主页信息
        MainInfo mainInfo = studentService.getMainInfo();
        model.addAttribute("mainInfo", mainInfo);
        return "main";
    }


    @RequestMapping("/form")
    public String form(){
        return "manage/form";
    }


    @RequestMapping("/allSubjects")
    public String allSubjects(Model model){

        StudentService studentService = new StudentService();

        //前九学生信息
        Collection<Student> topStu = studentService.getTopStudent();

        //所有学生信息
        Collection<Student> students = studentService.getStudents();

        //各科基本信息
        Collection<SubjectInfo> subjectInfos = studentService.getSubjectsInfo();

        //各科第一名名字
        TopInfo topInfo = studentService.getTopInfo();

        //自增数字：用于显示排名
        model.addAttribute("num", new IncreaseNumber());


        model.addAttribute("topInfo", topInfo);
        model.addAttribute("topStu", topStu);
        model.addAttribute("subjectInfo", subjectInfos);
        model.addAttribute("students", students);
        return "display/allSubjects";
    }


    @RequestMapping("/update")
    public String update(Model model, HttpSession session){
        model.addAttribute("student", (Student)session.getAttribute("student"));
        model.addAttribute("path", (String)session.getAttribute("path"));
        session.removeAttribute("student");
        return "manage/update";
    }

    @RequestMapping("/mainSubjects")
    public String mainSubjects(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentsSortedByMainSubjects();
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/mainSubjects";
    }

    @RequestMapping("/scienceSubjects")
    public String scienceSubjects(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentsSortedByScienceSubjects();
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/scienceSubjects";
    }

    @RequestMapping("/liberalSubjects")
    public String liberalSubjects(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedByLiberalSubjects();
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/liberalSubjects";
    }

    @RequestMapping("/science")
    public String science(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedByScience();
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/science";
    }

    @RequestMapping("/liberal")
    public String liberal(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedByLiberal();
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/liberal";
    }

    @RequestMapping("/chinese")
    public String Chinese(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("chi");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/chinese";
    }

    @RequestMapping("/math")
    public String math(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("math");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/math";
    }

    @RequestMapping("/english")
    public String english(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("en");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/english";
    }

    @RequestMapping("/physics")
    public String physics(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("phy");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/physics";
    }

    @RequestMapping("/chemistry")
    public String chemistry(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("chem");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/chemistry";
    }

    @RequestMapping("/biology")
    public String biology(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("bio");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/biology";
    }

    @RequestMapping("/politics")
    public String politics(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("pol");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/politics";
    }

    @RequestMapping("/history")
    public String history(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("his");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/history";
    }

    @RequestMapping("/geography")
    public String geography(Model model){
        StudentService studentService = new StudentService();
        Collection<Student> students = studentService.getStudentSortedBySingleSubject("geo");
        IncreaseNumber num = new IncreaseNumber();
        model.addAttribute("num", num);
        model.addAttribute("students", students);
        return "display/geography";
    }
}
