package com.tony.lifelaugh.LFLJoke;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.androidlib.tony.utils.JsonUtils;
import com.androidlib.tony.utils.SPUtils;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.cundong.recyclerview.ProgressStyle;
import com.cundong.recyclerview.util.RecyclerViewStateUtils;
import com.cundong.recyclerview.view.LoadingFooter;
import com.tony.lifelaugh.Base.BaseFragment;
import com.tony.lifelaugh.Config.LFLConfig;
import com.tony.lifelaugh.ItemDecoration.DividerItemDecoration;
import com.tony.lifelaugh.JokeTextAdapter;
import com.tony.lifelaugh.Model.BS_Joke_Text;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tony on 2016/9/22.
 */
public class JokeTextFragment extends BaseFragment{

    public static JokeTextFragment mJokeTextFragment;
    private int Page = 1;
    private BS_Joke_Text mBS_Joke;
    private SPUtils mJokeSp;
    private LRecyclerView mLRecyclerView;
    private JokeTextAdapter mJokeTextAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private int isRefresh = -1; //TODO -1:初始 ,1：刷新，2:加载
    private List<BS_Joke_Text.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mJokeList = new ArrayList<>();
    public static JokeTextFragment newInstence(){
        if(mJokeTextFragment == null){
            mJokeTextFragment = new JokeTextFragment();
        }
        return mJokeTextFragment;
    }
    TextView tv;
    @Override
    public View initView(LayoutInflater inflater) {
        mLRecyclerView = new LRecyclerView(mActivity);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mLRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onRefresh() {
                isRefresh = 1;
                getDataFromNet();
            }

            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onBottom() {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mLRecyclerView);
                if (state == LoadingFooter.State.Loading) {
                    return;
                }
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mLRecyclerView,LFLConfig.REQUEST_COUNT, LoadingFooter.State.Loading, null);
                Page += 1;
                isRefresh = 2;
                getDataFromNet();
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }
        });
        if(mJokeTextAdapter == null){
            mJokeTextAdapter = new JokeTextAdapter(mActivity,mJokeList);
        }
        if(mHeaderAndFooterRecyclerViewAdapter == null){
            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mActivity,mJokeTextAdapter);
        }
        mLRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity));
        mLRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        //设置下拉刷新的样式
        mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        return mLRecyclerView;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.i("initData","JokeTextFragment-isFirst:"+isFirst);
        if(isFirst){
            isRefresh = -1;
            getDataFromNet();
        }else{
            getDataFromSp();
        }
    }

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        //TODO 获取网络数据
        Page = isRefresh == 2 ? Page : 1;
        Observable<BS_Joke_Text> observable = service.getJoke(LFLConfig.AppId,LFLConfig.AppSercet,Integer.toString(Page),LFLConfig.BS_TYPE_Joke);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BS_Joke_Text>() {
            @Override
            public void onCompleted() {
                Log.i("Observable", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Observable", "error");
            }

            @Override
            public void onNext(BS_Joke_Text bs_joke) {

                cacheDataToSp(bs_joke);//TODO 缓存数据到SP
                setDataInRecycle(bs_joke);

            }
        });
    }

    private void cacheDataToSp(BS_Joke_Text bs_joke) {


        if(mJokeSp == null){
            mJokeSp = new SPUtils(mActivity,"BS_Joke_Text");//TODO 初始化SP缓存
        }else{
            //TODO 在刷新的时候要先清除掉Sp中的缓存
            if(isRefresh == 1){
                int a = mJokeSp.getInt("Page");
                for (int i = 0; i < a; i++) {
                    mJokeSp.putString("JokeTextJson"+i,"");
                    mJokeSp.putInt("Page",1);
                }
            }
        }
        //TODO 缓存每一页加载的json数据
        mJokeSp.putString("JokeTextJson"+Page,JsonUtils.toJson(bs_joke,1));//TODO 把json存进SP文件
        //TODO 缓存最大页码
        mJokeSp.putInt("Page",Page);

    }

    private void setDataInRecycle(BS_Joke_Text bs_joke) {

        if(isRefresh == -1){
            mJokeList.clear();
            mJokeList.addAll(bs_joke.getShowapi_res_body().getPagebean().getContentlist());
        }else if(isRefresh == 1){
            mJokeList.clear();
            mJokeList.addAll(bs_joke.getShowapi_res_body().getPagebean().getContentlist());
            mHeaderAndFooterRecyclerViewAdapter.removeFooterView(mHeaderAndFooterRecyclerViewAdapter.getFooterView());
            mLRecyclerView.refreshComplete();
        }else if(isRefresh == 2){
            mJokeList.addAll(bs_joke.getShowapi_res_body().getPagebean().getContentlist());
            RecyclerViewStateUtils.setFooterViewState(mLRecyclerView, LoadingFooter.State.TheEnd);
            mLRecyclerView.refreshComplete();

        }
        mJokeTextAdapter.notifyDataSetChanged();
    }



    @Override
    public void getDataFromSp() {
        super.getDataFromSp();
        //TODO 获取缓存数据
        mJokeList.clear();
        Page = mJokeSp.getInt("Page");//得到缓存的页数
        for (int i = 1; i <= Page; i++) {//取得所有页面缓存
            BS_Joke_Text bean = (BS_Joke_Text)JsonUtils.toObject(mJokeSp.getString("JokeTextJson"+i),BS_Joke_Text.class);
            mJokeList.addAll(bean.getShowapi_res_body().getPagebean().getContentlist());
        }
        mJokeTextAdapter.notifyDataSetChanged();
    }



}
