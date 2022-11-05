package Controller;

import Dao.DBUtilsForQuesAndRes;

import java.sql.SQLException;


public class Releaser {

    private DBUtilsForQuesAndRes jdbc;

    public Releaser(){
        jdbc = new DBUtilsForQuesAndRes();
    }

    public boolean releaseQues(String ques, String type) throws SQLException{
        return jdbc.releaseQues(ques, type);
    }

    public boolean releaseAns(String num, String ans) throws SQLException{
        return jdbc.releaseAns(num, ans);
    }
}
