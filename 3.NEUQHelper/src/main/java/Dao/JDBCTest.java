package Dao;


import java.util.HashMap;
import java.util.List;


public class JDBCTest {
    public static void main(String[] args) throws Exception{

        //测试JdbcForUser
        /*
        System.out.println("------------user------------");
        JdbcUtilsForUser test = new JdbcUtilsForUser();

        //在表account中查找是否存在202012143，存在将返回一个jdbc连接
        System.out.println(test.isExist("202012144"));

        //注册，若成功将返回true，失败返回false并会在控制台打印出原因
        Account a = new Account("student", "202012110", "123456");
        Student Sun = new Student(a, "孙卓宇", "CS", "大一", "男");
        System.out.println(test.registerForStudent(Sun));

        //登录，登录成功将返回一个包含用户信息的HashMap
        Account account = new Account("student", "202012143", "011026");
        System.out.println(test.verify(account));

        //修改密码，若成功将返回true，失败返回false并会在控制台打印出原因
        System.out.println(test.modifyPassword("student", "202012110", "123456", "000000"));
        test.exit();


        System.out.println("-----------admin-----------");
        JdbcUtilsForAdmin test1 = new JdbcUtilsForAdmin();

        //未登录情况下试图获取学生信息
        System.out.println(test1.showStudentInfo());

        //登录
        System.out.println(test1.verify("NorthBoat", "020812"));

        //获取学生202012143的信息
        HashMap<String, String> map = test1.searchUser("student", "202012143");
        System.out.println(map.toString());

        //获取所有学生信息并打印
        Iterator<Info> it = test1.showStudentInfo();
        while(it.hasNext()){
            Info i = it.next();
            System.out.println(i.getNums() + "\t" + i.getMajor() + "\t" + i.getGender());
        }

        test1.resetAccount("student", "202012110", "孙卓宇");
        test1.exit();
        */

        //测试JdbcForSearch
        DBUtilsForQuesAndRes juf = new DBUtilsForQuesAndRes();
        HashMap<String, List<String>> map = juf.getAns("t");
        if(map!=null){
            for(String str: map.keySet()){
                System.out.println("问题 " + str + "        答案 " + map.get(str));
            }
        }
        if(map==null){
            System.out.println("未搜索到相匹配的答案");
        }

        //发布问题：成功将返回true
        String ques = "nmsl";
        System.out.println(juf.releaseQues(ques, "test"));

        //获取响应type问题
        System.out.println(juf.getQues("test").toString());
    }
}
