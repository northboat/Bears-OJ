package Dao;

        import Controller.Info;

        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.List;

public class DBUtilsForAdmin {

    private Connection con = null;

    /**管理员登录
     * 判断用户名是否存在
     * 判断用户名与密码是否匹配
     * 返回用户名信息
     */
    public ResultSet verify(String name, String password) throws SQLException {
        con = DBCP.users.getConnection();
        Statement sql = con.createStatement();
        String SQL = "select * from admin where name = '" + name + "'";
        ResultSet set = sql.executeQuery(SQL);
        if(!set.next()){
            System.out.println("用户名错误");
            con.close();
            return null;
        }
        String p = set.getString("password");
        if(!password.equals(p)){
            System.out.println("密码错误");
            con.close();
            return null;
        }
        return set;
    }


    /** 获取student表中信息
     * 不返回名字是因为在重置密码时，要求管理员填写用户名字，限制管理员权限
     * 即仅获得账号、专业、性别
     */
    public Iterator<Info> showStudentInfo() throws SQLException{
        con = DBCP.users.getConnection();
        List<Info> list = new ArrayList<>();
        Statement sql = con.createStatement();
        String SQL = "select * from student";
        ResultSet set = sql.executeQuery(SQL);
        while(set.next()){
            Info info = new Info(set.getString("nums"), set.getString("major"), set.getString("gender"));
            list.add(info);
        }
        con.close();
        Iterator<Info> res = list.iterator();
        return res;
    }
    //获取teacher表中信息
    public Iterator<Info> showTeacherInfo() throws SQLException{
        con = DBCP.users.getConnection();
        List<Info> list = new ArrayList<>();
        Statement sql = con.createStatement();
        String SQL = "select * from teacher";
        ResultSet set = sql.executeQuery(SQL);
        while(set.next()){
            Info info = new Info(set.getString("nums"), set.getString("major"), set.getString("gender"));
            list.add(info);
        }
        con.close();
        Iterator<Info> res = list.iterator();
        return res;
    }


    /**详细查找并获取用户信息
     * Stu：账号、专业、性别、年级
     * Tea：账号、专业、性别、职称
     */
    public HashMap<String, String> searchUser(String table, String nums) throws SQLException{
        con = DBCP.users.getConnection();
        HashMap<String, String> res = new HashMap<>();
        Statement sql = con.createStatement();
        String SQL = "select * from " + table + " where nums = " + nums;
        ResultSet set = sql.executeQuery(SQL);
        if(!set.next()){
            con.close();
            return null;
        }
        if(table.equals("student"))
            res.put("身份", "学生");
        else
            res.put("身份", "老师");
        res.put("账号", set.getString("nums"));
        res.put("专业", set.getString("major"));
        res.put("性别", set.getString("gender"));
        if(table.equals("student"))
            res.put("年纪", set.getString("grade"));
        else
            res.put("职称", set.getString("position"));
        con.close();
        return res;
    }


    /**重置用户密码
     * 管理员需要匹配用户姓名
     */
    public boolean resetAccount(String table, String nums, String name) throws SQLException{
        con = DBCP.users.getConnection();
        Statement sql = con.createStatement();
        String SQL = "select * from " + table + " where nums = '" + nums + "'";
        ResultSet set = sql.executeQuery(SQL);
        if(!set.next()){
            System.out.println("查无此人");
            con.close();
            return false;
        }
        if(!name.equals(set.getString("name"))){
            System.out.println("名字错误");
            con.close();
            return false;
        }
        SQL = "update " + table + " set password = '123456' where nums = " + nums;
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            System.out.println("重置失败，请重试");
            con.close();
            return false;
        }
        con.close();
        System.out.println("重置密码成功");
        return true;
    }


    /**
     * 在account表中新建账号
     */
    public boolean setAccount(String nums, String name) throws SQLException{
        con = DBCP.users.getConnection();
        Statement sql = con.createStatement();
        String record = "('" + nums + "', '" + name + "')";
        System.out.println(record);
        String SQL = "insert into account values " + record;
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            con.close();
            return false;
        }
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
