package Controller;

import Dao.DBUtilsForUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher{
    private Account account;
    private String name;
    private String major;
    private String gender;
    private String position;

    private DBUtilsForUser jdbc = new DBUtilsForUser();

    public Teacher(Account a){
        account = a;
    }

    public Teacher(Account account, String name, String major, String gender, String position) {
        this.account = account;
        this.name = name;
        this.major = major;
        this.gender = gender;
        this.position = position;
    }

    public Account getAccount(){ return account; };

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ResultSet login() throws SQLException{
        return jdbc.verify(this.account);
    }

    public boolean register() throws SQLException{
        return jdbc.registerForTeacher(this);
    }

    public boolean change(String newPassword) throws SQLException{
        return jdbc.modifyPassword("teacher", this.account.getNums(), this.account.getPassword(), newPassword);
    }

    public void exit(){
        jdbc.exit();
    }
}
