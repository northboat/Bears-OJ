package com.oj.neuqoj.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String parseDate(Date d){
        return sdf.format(d);
    }
}
