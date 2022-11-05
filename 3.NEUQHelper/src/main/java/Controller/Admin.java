package Controller;

import Dao.DBUtilsForAdmin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class Admin {
    private String name;
    private String password;
    private DBUtilsForAdmin jdbc = new DBUtilsForAdmin();

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ResultSet login() throws SQLException{
        return jdbc.verify(getName(), getPassword());
    }

    public HashMap<String, String> searchUser(String table, String nums) throws SQLException{
        return jdbc.searchUser(table, nums);
    }

    public boolean resetAccount(String table, String nums, String name) throws SQLException{
        return jdbc.resetAccount(table, nums, name);
    }

    public boolean setAccount(String nums, String name) throws SQLException{
        return jdbc.setAccount(nums, name);
    }

    public Iterator<Info> showStuInfo() throws SQLException{
        return jdbc.showStudentInfo();
    }

    public Iterator<Info> showTeaInfo() throws SQLException{
        return jdbc.showTeacherInfo();
    }

    public void exit(){
        jdbc.exit();
    }
}
