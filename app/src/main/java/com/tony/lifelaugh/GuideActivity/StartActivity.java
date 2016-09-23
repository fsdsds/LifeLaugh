package com.tony.lifelaugh.GuideActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.androidlib.tony.utils.IntentUtil;
import com.tony.lifelaugh.Base.BaseActivity;
import com.tony.lifelaugh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends BaseActivity {

    @BindView(R.id.startActivity_img_logo)
    ImageView startActivityImgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splan);
        ButterKnife.bind(this);
        startActivityImgLogo.setImageResource(R.mipmap.start);
        CutDownTime();
    }

    private void CutDownTime() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GuideRedirect();
                    }
                });
            }
        },5000);

    }

    private void GuideRedirect() {
        //ToDo 调到引导页
        //ToDo 1、没有记住登录状态则跳到登录页 2、记住登录状态则直接跳到主页
        IntentUtil.newInstence().startIntent(this,MainActivity.class);
        finish();
        }
    }

