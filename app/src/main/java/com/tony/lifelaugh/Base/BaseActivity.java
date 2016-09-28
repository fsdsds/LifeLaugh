package com.tony.lifelaugh.Base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.androidlib.tony.utils.ActivityManagerUtil;
import com.androidlib.tony.utils.DisplayUtil;
import com.androidlib.tony.utils.EditTextUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tony.lifelaugh.R;


public class BaseActivity extends AppCompatActivity {

    public EditTextUtil mEditTextUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.Toolbar_BgColor);//通知栏所需颜色
        }
        setContentView(R.layout.activity_base);
        Log.i("BaseRootActivity", "onCreate");
        ActivityManagerUtil.newInstance().pushOneActivity(this);
        mEditTextUtil = EditTextUtil.newInstence();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("BaseRootActivity", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("BaseRootActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("BaseRootActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.newInstance().popOneActivity(this);
    }

    //public int hidesoft;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MotionEvent.ACTION_DOWN == ev.getAction()) {
            View view = getCurrentFocus();
            if (view != null && view.getWindowToken() != null) {
                DisplayUtil.newInstence(this).hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);//
    }
}
