package com.tony.lifelaugh.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlib.tony.ResofitUtils.ServiceGenerator;
import com.cundong.recyclerview.LRecyclerView;
import com.tony.lifelaugh.Config.LFLConfig;
import com.tony.lifelaugh.RetrofitService.QueryJsonService;

/**
 * Created by tony on 2016/9/21.
 */
public abstract class BaseFragment extends Fragment {

    public boolean isFirst = true;
    public QueryJsonService service;
    public Activity mActivity;
    public LRecyclerView mLRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        return initView(inflater);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    public abstract View initView(LayoutInflater inflater);

    public abstract void initData(Bundle savedInstanceState);

    public void getDataFromSp(){

    }

    public void getDataFromNet(){
        ServiceGenerator.initBuild(LFLConfig.BaseUrl);
        if(service == null) {
            service = ServiceGenerator.createService(QueryJsonService.class);
        }
    }

}
