package com.fun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yehuan on 2016/11/8.
 */
public class DateUtil {

    public static String format(Date date) {
        return format(date,null);
    }

    public static String format(Date date,String pattern) {
        String p = (pattern==null || pattern.length() < 1) ? "yyyy-MM-dd HH:mm:ss" : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.format(date);
    }

    public static String currentDate(){
        return currentDate(null);
    }

    public static String currentDate(String pattern){
        return format(new Date(),pattern);
    }

//    public static void main(String[] args){
//        System.out.println(currentDate());
//    }

}
