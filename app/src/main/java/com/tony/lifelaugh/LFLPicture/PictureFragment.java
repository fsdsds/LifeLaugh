package com.tony.lifelaugh.LFLPicture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tony.lifelaugh.Base.BaseFragment;

/**
 * Created by tony on 2016/9/22.
 */
public class PictureFragment extends BaseFragment{

    public static PictureFragment mPictureFragment;

    public static PictureFragment newInstence(){
        if(mPictureFragment == null){
            mPictureFragment = new PictureFragment();
        }
        return mPictureFragment;
    }
    TextView tv;
    @Override
    public View initView(LayoutInflater inflater) {
        tv = new TextView(getActivity());
        tv.setText("图片");
        return tv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected boolean lazyLoad() {
        tv.setText("图片图片图片图片");
        return false;
    }
}
