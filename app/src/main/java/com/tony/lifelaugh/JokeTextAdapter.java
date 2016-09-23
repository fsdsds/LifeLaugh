package com.tony.lifelaugh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tony.lifelaugh.Model.BS_Joke_Text;
import com.tony.lifelaugh.Widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/9/23.
 */
public class JokeTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BS_Joke_Text.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mBS_Joke = new ArrayList<>();
    private LayoutInflater inflater;
    public JokeTextAdapter(Context context,List<BS_Joke_Text.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mBS_Joke){
        this.context = context;
        this.mBS_Joke = mBS_Joke;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_joke_text_layout,parent,false);
        return JokeTextViewHolder.newInstence(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JokeTextViewHolder hold = (JokeTextViewHolder)holder;
        hold.setItemData(mBS_Joke.get(position));
    }

    @Override
    public int getItemCount() {
        return mBS_Joke.size();
    }

    static private class JokeTextViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name,tv_time,tv_content;
        private CircleImageView img_touxiang;
        private View view ;
        private static JokeTextViewHolder newInstence(View itemView){
            return new JokeTextViewHolder(itemView);
        }
        public JokeTextViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            img_touxiang = (CircleImageView) view.findViewById(R.id.img_touxiang);
        }

        public void setItemData(BS_Joke_Text.ShowapiResBodyBean.PagebeanBean.ContentlistBean bean){
            tv_name.setText(bean.getName());
            tv_time.setText(bean.getCreate_time());
            tv_content.setText(bean.getText());
            img_touxiang.setImageURI(bean.getProfile_image());
        }

    }

}
