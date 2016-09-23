package com.tony.lifelaugh.LFLHelpLife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tony.lifelaugh.Base.BaseFragment;

/**
 * Created by tony on 2016/9/22.
 */
public class HelpLifeFragment extends BaseFragment {

    public static HelpLifeFragment mHelpLifeFragment;

    public static HelpLifeFragment newInstence(){
        if(mHelpLifeFragment == null){
            mHelpLifeFragment = new HelpLifeFragment();
        }
        return mHelpLifeFragment;
    }
    TextView tv;
    @Override
    public View initView(LayoutInflater inflater) {
        tv = new TextView(getActivity());
        tv.setText("生活助手");
        return tv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    protected boolean lazyLoad() {
        tv.setText("漫画漫画漫画漫画");
        return false;
    }
}
