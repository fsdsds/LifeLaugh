package com.androidlib.tony.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tony on 2016/7/31.
 */
public class IntentUtil {
    private static IntentUtil mIntentUtil;

    public static IntentUtil newInstence(){
        if(mIntentUtil==null){
            mIntentUtil = new IntentUtil();
        }
        return mIntentUtil;
    }

    public void startIntent(Context context, Class<?> clzz){
        context.startActivity(new Intent(context,clzz));
    }

    public void startIntent(Context context, Class<?> clzz,String key,String value){
        Intent intent = new Intent(context,clzz);
        intent.putExtra(key,value);
        context.startActivity(intent);
    }

    public void startIntent(Context context, Class<?> clzz,String key,String value,String key1,String value1){
        Intent intent = new Intent(context,clzz);
        intent.putExtra(key,value);
        intent.putExtra(key1,value1);
        context.startActivity(intent);
    }

    public void startIntent(Context context,Class<?> clzz, List<String> list){
        Intent intent = new Intent(context,clzz);
        intent.putExtra("tabs", (Serializable) list);
        ((Activity)context).startActivityForResult(intent,1);
    }
}
