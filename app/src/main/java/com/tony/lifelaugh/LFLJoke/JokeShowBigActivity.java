package com.tony.lifelaugh.LFLJoke;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.tony.lifelaugh.Base.BaseActivity;
import com.tony.lifelaugh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JokeShowBigActivity extends BaseActivity {

    @BindView(R.id.webview_big)
    WebView webviewBig;
    @BindView(R.id.btn_back)
    Button btnBack;
    String ImageUrl = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_show_big);
        ButterKnife.bind(this);
        if ((getIntent() != null)) {
            ImageUrl = getIntent().getExtras().getString("ImageUrl");
        }
        initWebView();
        webviewBig.loadUrl(ImageUrl);
    }

    private void initWebView() {

        webviewBig.setWebViewClient(new WebViewClient());
        webviewBig.getSettings().setDefaultTextEncodingName("utf-8");
        webviewBig.getSettings().setAppCacheEnabled(true);// 设置启动缓存
        webviewBig.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webviewBig.getSettings().setUseWideViewPort(true);//让webview读取网页设置的viewport，pc版网页
        webviewBig.getSettings().setLoadWithOverviewMode(true);

    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}
