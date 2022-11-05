package Controller;

import Dao.DBUtilsForUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student{
    private Account account;
    private String name;
    private String major;
    private String grade;
    private String gender;
    private DBUtilsForUser jdbc = new DBUtilsForUser();

    public Student(Account a){
        account = a;
    }

    public Student(Account account, String name, String major, String grade, String gender) {
        this.account = account;
        this.name = name;
        this.major = major;
        this.grade = grade;
        this.gender = gender;
    }

    public Account getAccount() { return account; };

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getGrade() {
        return grade;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ResultSet login() throws SQLException{
        return jdbc.verify(this.account);
    }

    public boolean register() throws SQLException{
        return jdbc.registerForStudent(this);
    }

    public boolean change(String newPassword) throws SQLException{
        return jdbc.modifyPassword("student", this.account.getNums(), this.account.getPassword(), newPassword);
    }

    public void exit(){
        jdbc.exit();
    }
}
