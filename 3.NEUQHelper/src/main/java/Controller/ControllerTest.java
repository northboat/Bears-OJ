package Controller;

import java.sql.SQLException;
import java.util.Iterator;

public class ControllerTest {
    public static void main(String[] args) throws SQLException {
        Account a = new Account("student", "202012143", "123456");
        Student stu = new Student(a, "熊舟桐", "CS", "大一", "男");
        System.out.println(stu.register());
        System.out.println(stu.login());
        System.out.println(stu.change("011026"));
        stu.change("000000");


        Admin admin = new Admin("NorthBoat", "020812");
        System.out.println(admin.login());
        System.out.println(admin.searchUser("student", "202012143"));
        System.out.println(admin.resetAccount("student", "202012143", "熊舟桐"));
        Iterator<Info> it = admin.showStuInfo();
        while(it.hasNext()){
            Info i = it.next();
            System.out.println(i.getNums() + " " + i.getMajor() + " " + i.getGender());
        }
        System.out.println(admin.showTeaInfo());
    }
}
