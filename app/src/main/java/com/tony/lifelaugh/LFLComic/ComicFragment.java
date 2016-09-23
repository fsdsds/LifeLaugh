package com.tony.lifelaugh.LFLComic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tony.lifelaugh.Base.BaseFragment;

/**
 * Created by tony on 2016/9/22.
 */
public class ComicFragment extends BaseFragment {

    public static ComicFragment mComicFragment;

    public static ComicFragment newInstence(){
        if(mComicFragment == null){
            mComicFragment = new ComicFragment();
        }
        return mComicFragment;
    }
    TextView tv;
    @Override
    public View initView(LayoutInflater inflater) {
        tv = new TextView(getActivity());
        tv.setText("漫画");
        return tv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


}
