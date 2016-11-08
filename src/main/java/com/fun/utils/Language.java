package com.fun.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yehuan on 2016/11/7.
 *
 */
public class Language {


    private static Properties property=null;

    private static Properties initProperty(){

        String path = Language.class.getResource("/").getPath()+"language/"+ Config.get("lang")
                +".properties";
        property = new Properties();
        try {
            InputStream in = new FileInputStream(path);
            property.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }

    public static String get(String key){
        if(property == null){
            property = initProperty();
        }
        return property.getProperty(key);
    }


    public static void main(String[] args){

        String s = Language.get("ui.start");
        System.out.println(s);
    }

}
