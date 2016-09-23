package com.androidlib.tony.utils;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * Created by tony on 2016/7/31.
 */
public class ActivityManagerUtil {

    private static ActivityManagerUtil instance;
    private Stack<Activity> activityStack;//activity栈
    private ActivityManagerUtil() {
    }
    //单例模式
    public static ActivityManagerUtil newInstance() {
        if (instance == null) {
            instance = new ActivityManagerUtil();
        }
        return instance;
    }
    //把一个activity压入栈中
    public void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
        Log.d("MyActivityManager ", "size = " + activityStack.size());
        Log.d("MyActivityManager ", "size = " + actvity.getClass());
    }
    //获取栈顶的activity，先进后出原则
    public Activity getLastActivity() {
        return activityStack.lastElement();
    }
    //移除一个activity
    public void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                Log.d("MyActivityManager ", "size = " + activityStack.size());
                Log.d("MyActivityManager ", "size = " + activity.getClass());
                activity = null;
            }
        }

    }
    //退出所有activity
    public  void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                Log.d("MyActivityManager ", "size = " + activity.getClass());
                if (activity == null) break;
                popOneActivity(activity);
                Log.d("MyActivityManager ", "size = " + activityStack.size());
            }
        }
    }
}
