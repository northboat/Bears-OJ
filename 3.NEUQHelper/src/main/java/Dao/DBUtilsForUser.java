package Dao;

import Controller.Account;
import Controller.Student;
import Controller.Teacher;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 要注意注册、登录、修改密码的顺序，连接在各个函数中均有关闭命令（遇挫则关闭）
 */
public class DBUtilsForUser {

    private Connection con = null;


    //判断账号（account）是否存在，存在则返回数据库连接，不存在则返回null
    protected Connection isExist(String nums) throws SQLException {
        con = DBCP.users.getConnection();
        Statement sql = con.createStatement();
        String SQL = "select * from account where num = '" + nums + "'";
        ResultSet set = sql.executeQuery(SQL);
        if(!set.next()){
            con.close();
            return null;
        }
        return con;
    }


    /**登录验证
    * 若账号不存在，返回null，输出账号不存在
    * 若账号存在但未注册，返回null，输出尚未注册
    * 若账号已注册但密码错误，返回null，输出密码错误
    * 登录成功，返回账户信息
     */
    public ResultSet verify(Account a) throws SQLException{
        Connection con = isExist(a.getNums());
        if(con == null){
            System.out.println("账户不存在");
            return null;
        }
        Statement sql = con.createStatement();
        String SQL = "select * from " + a.getTable() + " where nums = '" + a.getNums() + "'";
        ResultSet set = sql.executeQuery(SQL);
        if(!set.next()){
            System.out.println("该账户未注册，请先注册");
            con.close();
            return null;
        }
        String p = set.getString("password");
        //比较字符串用equals()方法
        if(!a.getPassword().equals(p))
        {
            con.close();
            System.out.println("密码错误");
            return null;
        }
        return set;
    }


    /**学生用户注册
    * 账号不存在，返回false，输出账号不存在
    * 账号存在但已注册，返回false，输出账号已注册
    * 账号存在且未注册，录入学生信息，未报错，返回true
    * 账号、密码、名字为必填项
     */
    public boolean registerForStudent(Student stu) throws SQLException{
        Connection con = isExist(stu.getAccount().getNums());
        if(con == null){
            System.out.println("账号不存在");
            return false;
        }
        Statement sql = con.createStatement();
        String SQL = "select * from student where nums = " + stu.getAccount().getNums();
        ResultSet set = sql.executeQuery(SQL);
        if(set.next()){
            System.out.println("账号已被注册");
            con.close();
            return false;
        }

        //获取用户名字
        SQL = "select * from account where num = " + stu.getAccount().getNums();
        ResultSet getName = sql.executeQuery(SQL);
        getName.next();
        String name = getName.getString("name");

        //在数据库中注册信息
        String record = "('" + stu.getAccount().getNums() + "', '" + stu.getAccount().getPassword() + "', '"
                + name + "', '" + stu.getMajor() + "', '" + stu.getGrade() + "', '" +  stu.getGender() + "')";
        SQL = "insert into student values " + record;
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            System.out.println("注册失败，请重试");
            con.close();
            return false;
        }
        System.out.println("注册成功,请登录");
        con.close();
        return true;
    }


    /**老师用户注册
    * 账号不存在，返回false，输出账号不存在
    * 账号存在但已注册，返回false，输出账号已注册
    * 账号存在且未注册，录入学生信息，未报错，返回true
    * 账号、密码、名字为必填项
     */
    public boolean registerForTeacher(Teacher tea) throws SQLException{
        Connection con = isExist(tea.getAccount().getNums());
        if(con == null){
            System.out.println("账号不存在");
            return false;
        }
        Statement sql = con.createStatement();
        String SQL = "select * from student where nums = " + tea.getAccount().getNums();
        ResultSet set = sql.executeQuery(SQL);
        if(set.next()){
            System.out.println("账号已被注册");
            con.close();
            return false;
        }

        //获取用户名字
        SQL = "select * from account where num = " + tea.getAccount().getNums();
        ResultSet getName = sql.executeQuery(SQL);
        getName.next();

        String name = getName.getString("name");
        String record = "('" + tea.getAccount().getNums() + "', '" + tea.getAccount().getPassword() + "', '"
                + name + "', '" + tea.getMajor() + "', '" + tea.getGender() + "', '" +  tea.getPosition() + "')";
        SQL = "insert into teacher values " + record;
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            System.out.println("注册失败，请重试");
            con.close();
            return false;
        }
        System.out.println("注册成功,请登录");
        con.close();
        return true;
    }


    /**修改密码
     * 从表中拿到账号对应密码，与输入旧密码对比
     * 若匹配，修改旧密码为新密码，返回true；反之返回false
     */
    public boolean modifyPassword(String table, String nums, String oldPassword, String newPassword) throws  SQLException{
        Connection con = isExist(nums);
        if(con==null){
            System.out.println("账号错误");
            return false;
        }
        Statement sql = con.createStatement();
        String SQL = "select * from " + table + " where nums = " + nums;
        ResultSet set = sql.executeQuery(SQL);
        set.next();
        String realPassword = set.getString("password");
        if(!oldPassword.equals(realPassword)){
            System.out.println("旧密码错误");
            con.close();
            return false;
        }
        SQL = "update " + table + " set password = '" + newPassword + "'where nums = '" + nums + "'";
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            System.out.println("更新数据失败，请重试");
            con.close();
            return false;
        }
        System.out.println("修改密码成功");
        con.close();
        return true;
    }

    public void exit(){
        try{
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
