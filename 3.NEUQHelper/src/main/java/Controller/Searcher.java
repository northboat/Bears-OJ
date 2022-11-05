package Controller;

import Dao.DBUtilsForQuesAndRes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Searcher {

    private DBUtilsForQuesAndRes jdbc;

    public Searcher() throws SQLException{
        jdbc = new DBUtilsForQuesAndRes();
    }

    public HashMap<String, List<String>> getAns(String question) throws SQLException {
        return jdbc.getAns(question);
    }

    public List<String> getQues(String type) throws SQLException{
        return jdbc.getQues(type);
    }

    public List<String> getPointedAns(String num) throws SQLException{
        return jdbc.getPointedAns(num);
    }
}
