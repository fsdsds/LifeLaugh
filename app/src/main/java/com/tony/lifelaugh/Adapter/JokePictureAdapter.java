package com.tony.lifelaugh.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidlib.tony.utils.DisplayUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tony.lifelaugh.Config.LFLConfig;
import com.tony.lifelaugh.LFLJoke.JokeShowBigActivity;
import com.tony.lifelaugh.Model.BS_Joke_Picture;
import com.tony.lifelaugh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/9/23.
 */
public class JokePictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BS_Joke_Picture.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mBS_Joke = new ArrayList<>();
    private LayoutInflater inflater;
    public JokePictureAdapter(Context context, List<BS_Joke_Picture.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mBS_Joke){
        this.context = context;
        this.mBS_Joke = mBS_Joke;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_joke_picture_layout,parent,false);
        return JokeTextViewHolder.newInstence(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
      final  JokeTextViewHolder hold = (JokeTextViewHolder)holder;
        hold.setItemData(mBS_Joke.get(position),context);

        if(!mBS_Joke.get(position).getImage0().substring(mBS_Joke.get(position).getImage0().length()-3,mBS_Joke.get(position).getImage0().length()).equalsIgnoreCase("gif")){
            hold.webview.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if ((event.getAction() == MotionEvent.ACTION_UP)) {
                        Intent intent = new Intent(context,JokeShowBigActivity.class);
                        intent.putExtra("ImageUrl",mBS_Joke.get(position).getImage0());
                        context.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation((Activity) context,hold.webview,"mybtn").toBundle());
                        //IntentUtil.newInstence().startIntent(context,JokeShowBigActivity.class,"ImageUrl",mBS_Joke.get(position).getImage0());
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBS_Joke.size();
    }

    static private class JokeTextViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_time,tv_content;
        private SimpleDraweeView img_touxiang;
        private View view ;
        private SimpleDraweeView img_content;
        private RelativeLayout rela_show;
        public WebView webview;
        private RelativeLayout webview_rela;
        private Context context;
        private static JokeTextViewHolder newInstence(View itemView){
            return new JokeTextViewHolder(itemView);
        }
        public JokeTextViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            img_touxiang = (SimpleDraweeView) view.findViewById(R.id.img_touxiang);
            //img_content = (SimpleDraweeView) view.findViewById(R.id.img_content);
            webview = (WebView) view.findViewById(R.id.img_content);
            webview.setWebViewClient(new WebViewClient());
            webview.getSettings().setDefaultTextEncodingName("utf-8");
            webview.getSettings().setAppCacheEnabled(true);// 设置启动缓存
            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webview.getSettings().setUseWideViewPort(true);//让webview读取网页设置的viewport，pc版网页
            webview.getSettings().setLoadWithOverviewMode(true);
            rela_show = (RelativeLayout) view.findViewById(R.id.rela_show);
            webview_rela = (RelativeLayout) view.findViewById(R.id.rela_webview);
        }

        public void setItemData(BS_Joke_Picture.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean,Context context){
            tv_name.setText(bean.getName());
            tv_time.setText(bean.getCreate_time());
            tv_content.setText(Html.fromHtml(bean.getText().toString().trim()));
            img_touxiang.setImageURI(bean.getProfile_image());
            String image = bean.getImage0();
            String houzui = image.substring(image.length()-3,image.length());

            /*webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//适应内容大小
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放*/

            Log.i(LFLConfig.TAG,"ContentHeight:"+webview.getContentHeight());
            if(houzui.equalsIgnoreCase("gif")){
                rela_show.setVisibility(View.INVISIBLE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW,tv_content.getId());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                webview_rela.setLayoutParams(params);
            }else{
                rela_show.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context,400));
                params.addRule(RelativeLayout.BELOW,tv_content.getId());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                webview_rela.setLayoutParams(params);
            }
            webview.loadUrl(image);

        }

    }

}
