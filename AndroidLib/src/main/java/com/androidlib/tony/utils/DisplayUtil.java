package com.androidlib.tony.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by tony on 2016/7/31.
 */
public class DisplayUtil {

    private static DisplayUtil mDisplayUtil;
    private static DisplayMetrics metrix;
    private static Context context;
    public static DisplayUtil newInstence(Context ct){
        if(mDisplayUtil==null){
            mDisplayUtil = new DisplayUtil();
            metrix = new DisplayMetrics();
            context = ct;
            ((Activity)ct).getWindowManager().getDefaultDisplay().getMetrics(metrix);
        }
        return mDisplayUtil;
    }

    public int getDisplayWidth(){
        return metrix != null ? metrix.widthPixels : 0 ;
    }

    public int getDisplayHeight(){
        return metrix != null ? metrix.heightPixels : 0 ;
    }

    public int hideSoftInput(IBinder token) {
        int flag = -1;
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            flag = im.hideSoftInputFromWindow(token,InputMethodManager.HIDE_NOT_ALWAYS) ? 0 : 1 ;
        }
        return flag;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }
}
