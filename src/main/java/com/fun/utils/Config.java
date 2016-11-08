package com.fun.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yehuan on 2016/11/8.
 *
 */
public class Config {

    private static Properties property=null;

    private static Properties initProperty(){

        property = new Properties();
        try {
            String path = Config.class.getResource("/config.properties").getPath();
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
}
