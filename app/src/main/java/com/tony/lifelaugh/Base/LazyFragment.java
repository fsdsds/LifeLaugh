package com.tony.lifelaugh.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tony on 2016/9/23.
 */
public abstract class LazyFragment  extends Fragment {

    public boolean isVisible = false;
    public boolean isFirst = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    public abstract View initView(LayoutInflater inflater);

    public abstract void initData(Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {

            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible(){
        //TODO 第一次加载fragment时缓存数据，不用每次都要加载数据
        if(lazyLoad()){
            isFirst = false;
        }
    }

    protected abstract boolean lazyLoad();

    protected void onInvisible(){}
}