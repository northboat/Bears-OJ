package Dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBCP {

    static public ComboPooledDataSource users = new ComboPooledDataSource("users");

    static public ComboPooledDataSource searchers = new ComboPooledDataSource("ques&ans");
}
