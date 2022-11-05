package Dao;


import java.sql.*;
import java.util.*;

public class DBUtilsForQuesAndRes {

    private Connection con = null;

    public HashMap<String, List<String>> getAns(String ques) throws SQLException {
        //获取一个连接
        con = DBCP.searchers.getConnection();
        HashMap<String, List<String>> res = new HashMap<>();
        //一个连接对应一条语句！如果中途执行第二条语句，第一条语句的set将被销毁
        Statement sql1 = con.createStatement();
        Statement sql2 = con.createStatement();
        String SQL = "select * from questions where question like '%" + ques + "%'";
        ResultSet question = sql1.executeQuery(SQL);
        while(question.next()){
            String num = question.getString("num");
            String q = question.getString("question");
            res.put(num+". "+q, new ArrayList<String>());
            SQL = "select * from answers where num = " + num;
            ResultSet ans = sql2.executeQuery(SQL);
            while(ans.next()){
                res.get(num+". "+q).add((ans.getString("answer")));
            }
        }
        if(res.keySet().size() == 0){
            con.close();
            return null;
        }
        con.close();
        return res;
    }

    public List<String> getPointedAns(String num) throws SQLException{
        //获取一个连接
        con = DBCP.searchers.getConnection();
        Statement sql = con.createStatement();
        String SQL = "select * from answers where num = '" + num + "'";
        ResultSet ans = sql.executeQuery(SQL);
        List<String> res = new ArrayList<>();
        while(ans.next()){
            res.add(ans.getString("answer"));
        }
        con.close();
        return res;
    }

    public boolean releaseQues(String ques, String type) throws SQLException{
        //获取一个连接
        con = DBCP.searchers.getConnection();
        //获取问题序号
        //设置连接属性：可滚动，可修改数据库
        Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String SQL = "select * from questions";
        ResultSet question = sql.executeQuery(SQL);
        question.last();
        int num = Integer.parseInt(question.getString("num")) + 1;
        //向后插入数据
        String record = "('" + num + "', '" + ques + "', " + "'" + "', '" + type + "')";
        SQL = "insert into questions values " + record;
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            con.close();
            return false;
        }
        con.close();
        return true;
    }

    public boolean releaseAns(String num, String ans) throws SQLException{
        con = DBCP.searchers.getConnection();
        Statement sql = con.createStatement();
        String record = "('" + num + "', '" + ans + "', '', '', '')";
        String SQL = "insert into answers values" + record;
        int ok = sql.executeUpdate(SQL);
        if(ok == 0){
            con.close();
            return false;
        }
        con.close();
        return true;
    }

    public List<String> getQues(String type) throws SQLException{
        con = DBCP.searchers.getConnection();
        List<String> questions = new ArrayList<>();
        Statement sql = con.createStatement();
        String SQL = "select * from questions where type = '" + type +"'";
        ResultSet set = sql.executeQuery(SQL);
        while(set.next()){
            questions.add(set.getString("num") + ". " + set.getString("question"));
        }
        con.close();
        return questions;
    }
}
