package com.tony.lifelaugh.LFLJoke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tony.lifelaugh.Base.BaseFragment;

/**
 * Created by tony on 2016/9/22.
 */
public class JokeTextFragment extends BaseFragment{

    public static JokeTextFragment mJokeTextFragment;

    public static JokeTextFragment newInstence(){
        if(mJokeTextFragment == null){
            mJokeTextFragment = new JokeTextFragment();
        }
        return mJokeTextFragment;
    }
    TextView tv;
    boolean isPrepare;
    @Override
    public View initView(LayoutInflater inflater) {
        tv = new TextView(getActivity());
        isPrepare = true;
        lazyLoad();
        return tv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    protected boolean lazyLoad() {
        if(!isPrepare || !isVisible){
            return false;
        }
        if(isFirst){
            tv.setText("段子");
        }else{
            tv.setText("第二次段子");
        }
        return true;
    }
}
