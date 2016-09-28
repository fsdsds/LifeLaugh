package com.tony.lifelaugh.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tony.lifelaugh.Model.BS_Joke_Video;
import com.tony.lifelaugh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/9/23.
 */
public class JokeVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BS_Joke_Video.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mBS_Joke = new ArrayList<>();
    private LayoutInflater inflater;
    public JokeVideoAdapter(Context context, List<BS_Joke_Video.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mBS_Joke){
        this.context = context;
        this.mBS_Joke = mBS_Joke;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_joke_video_layout,parent,false);
        return JokeVideoViewHolder.newInstence(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        JokeVideoViewHolder hold = (JokeVideoViewHolder)holder;
        hold.setItemData(mBS_Joke.get(position),context);
    }

    @Override
    public int getItemCount() {
        return mBS_Joke.size();
    }

    static private class JokeVideoViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_time,tv_content;
        private SimpleDraweeView img_touxiang;
        private View view ;
        public WebView webview;
        private static JokeVideoViewHolder newInstence(View itemView){
            return new JokeVideoViewHolder(itemView);
        }
        public JokeVideoViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            img_touxiang = (SimpleDraweeView) view.findViewById(R.id.img_touxiang);
            //img_content = (SimpleDraweeView) view.findViewById(R.id.img_content);
            webview = (WebView) view.findViewById(R.id.WebView_play);
            webview.setWebViewClient(new WebViewClient());
            webview.getSettings().setDefaultTextEncodingName("utf-8");
            webview.getSettings().setAppCacheEnabled(true);// 设置启动缓存
            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webview.getSettings().setUseWideViewPort(true);//让webview读取网页设置的viewport，pc版网页
            webview.getSettings().setLoadWithOverviewMode(true);
        }

        public void setItemData(BS_Joke_Video.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean,Context context){
            tv_name.setText(bean.getName());
            tv_time.setText(bean.getCreate_time());
            tv_content.setText(Html.fromHtml(bean.getText().toString().trim()));
            img_touxiang.setImageURI(bean.getProfile_image());

            webview.loadUrl(bean.getVideo_uri());
        }

    }

}
