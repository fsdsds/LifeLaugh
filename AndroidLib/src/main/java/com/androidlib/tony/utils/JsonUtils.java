package com.androidlib.tony.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by tony on 2016/9/23.
 */
public class JsonUtils {
    public static String toJson(Object obj,int method) {
        // TODO Auto-generated method stub
        if (method==1) {
            Gson gson = new Gson();
            String obj2 = gson.toJson(obj);
            return obj2;
        }else if(method==2){
            Gson gson2=new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            String obj2=gson2.toJson(obj);
            return obj2;
        }
        return "";
    }

    public static Object toObject(String json,Class<?> clzz){
         Gson gson = new Gson();
         Object object =  gson.fromJson(json,clzz);
        return object;
    }


}
