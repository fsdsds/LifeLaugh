package com.tony.lifelaugh.LFLJoke;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tony.lifelaugh.Base.BaseFragment;

/**
 * Created by tony on 2016/9/22.
 */
public class JokeVideoFragment extends BaseFragment {

    public static JokeVideoFragment mJokeVideoFragment;

    public static JokeVideoFragment newInstence(){
        if(mJokeVideoFragment == null){
            mJokeVideoFragment = new JokeVideoFragment();
        }
        return mJokeVideoFragment;
    }
    TextView tv;
    boolean isPrepare;
    @Override
    public View initView(LayoutInflater inflater) {
        tv = new TextView(getActivity());
        return tv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.i("initData","JokeVideoFragment-isFirst:"+isFirst);
        if(isFirst){
            tv.setText("视频");
            getDataFromNet();
        }else{
            tv.setText("第二次视频");
            getDataFromSp();
        }
    }

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        //TODO 获取网络数据

    }

    @Override
    public void getDataFromSp() {
        super.getDataFromSp();
        //TODO 获取缓存数据
    }

}
