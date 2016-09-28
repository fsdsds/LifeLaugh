package com.tony.lifelaugh.LFLJoke;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.androidlib.tony.utils.JsonUtils;
import com.androidlib.tony.utils.NetworkUtils;
import com.androidlib.tony.utils.ProgressUtils;
import com.androidlib.tony.utils.SPUtils;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.cundong.recyclerview.ProgressStyle;
import com.cundong.recyclerview.util.RecyclerViewStateUtils;
import com.cundong.recyclerview.view.LoadingFooter;
import com.tony.lifelaugh.Base.BaseFragment;
import com.tony.lifelaugh.Config.LFLConfig;
import com.tony.lifelaugh.ItemDecoration.DividerItemDecoration;
import com.tony.lifelaugh.Adapter.JokeTextAdapter;
import com.tony.lifelaugh.Model.BS_Joke_Text;
import com.tony.lifelaugh.R;

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
    private Button btn_try_network;
    private JokeTextAdapter mJokeTextAdapter;
    private RelativeLayout include;
    private ProgressBar loading_progress;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private int isRefresh = -1; //TODO -1:初始 ,1：刷新，2:加载
    private int firstVis = 0;
    private int lastOffset = 0;
    private LinearLayoutManager manager;
    private List<BS_Joke_Text.ShowapiResBodyBean.PagebeanBean.ContentlistBean> mJokeList = new ArrayList<>();
    public static JokeTextFragment newInstence(){
        if(mJokeTextFragment == null){
            mJokeTextFragment = new JokeTextFragment();
        }
        return mJokeTextFragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        Log.i(LFLConfig.TAG,"initView");
        View view = inflater.inflate(R.layout.recycleview_layout,null);
        include = (RelativeLayout) view.findViewById(R.id.include_nonetwork);
        btn_try_network = (Button) include.findViewById(R.id.btn_try_network);
        btn_try_network.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isFirst = true;
                        initData(null);
                    }
                }
        );
        loading_progress = (ProgressBar) view.findViewById(R.id.loading_progress);
        mLRecyclerView = (LRecyclerView) view.findViewById(R.id.mLRecyclerView);
        initRecycleView();
        return view;
    }

    private void initRecycleView() {

        mLRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mLRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                manager = (LinearLayoutManager) mLRecyclerView.getLayoutManager();
                View topView = manager.getChildAt(0);          //获取可视的第一个view
                lastOffset = topView.getTop();                                   //获取与该view的顶部的偏移量
                firstVis = manager.getPosition(topView);
                Log.i(LFLConfig.TAG,"firstVis:"+firstVis);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mLRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isConnected(mActivity)) {
                    isRefresh = 1;
                    getDataFromNet();
                }else{
                    mLRecyclerView.refreshComplete();
                    include.setVisibility(View.VISIBLE);
                    mLRecyclerView.setVisibility(View.GONE);
                }
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


                if (NetworkUtils.isConnected(mActivity)) {
                    Page += 1;
                    isRefresh = 2;
                    getDataFromNet();
                }else{
                    RecyclerViewStateUtils.setFooterViewState(getActivity(), mLRecyclerView,LFLConfig.REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
                    //mHeaderAndFooterRecyclerViewAdapter.removeFooterView(mHeaderAndFooterRecyclerViewAdapter.getFooterView());
                }
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.i(LFLConfig.TAG,"JokeTextFragment-isFirst:"+isFirst);

        //TODO 根据网络显示不同的布局界面
        if(NetworkUtils.isConnected(mActivity)){
            //TODO 有网络，隐藏重试界面，显示列表控件
            include.setVisibility(View.GONE);
            mLRecyclerView.setVisibility(View.VISIBLE);
            //TODO 第一次加载fragment，从网络获取数据
            if(isFirst){
                //TODO 只有从网络加载数据才会显示进度条
                ProgressUtils.showLoadingProgress(loading_progress);
                isRefresh = -1;
                getDataFromNet();
            }else{
                //TODO 若不是第一次加载fragment，从SP缓存中加载数据
                getDataFromSp();
            }
        }else{
            //TODO 无网络，隐藏列表控件，显示重试界面
            include.setVisibility(View.VISIBLE);
            mLRecyclerView.setVisibility(View.GONE);

        }

    }

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        //TODO 获取网络数据
        Page = isRefresh == 2 ? Page : 1;
        Observable<BS_Joke_Text> observable = service.getTextJoke(LFLConfig.AppId,LFLConfig.AppSercet,Integer.toString(Page),LFLConfig.BS_TYPE_Joke);
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
                ProgressUtils.hideLoadingProgress(loading_progress);
                isFirst = false;
            }
        });
    }

    /**
     * 段子实体对象
     * @param bs_joke
     */
    private void cacheDataToSp(BS_Joke_Text bs_joke) {


        if(mJokeSp == null){
            mJokeSp = new SPUtils(mActivity,"BS_Joke_Text");//TODO 初始化SP缓存
        }else{
            //TODO 如果sp存在，在刷新的时候要先清除掉Sp中的缓存
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
        Log.i(LFLConfig.TAG,"setDataToSp-缓存："+mJokeSp.getString("JokeTextJson"+Page));
        Log.i(LFLConfig.TAG,"setDataToSp-页数："+mJokeSp.getInt("Page"));
    }

    /**
     * 段子实体对象
     * @param bs_joke
     */
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

        //TODO 第一次加载数据，或无网络重试把adapter赋null
        if(isFirst){
            mJokeTextAdapter =null;
            mHeaderAndFooterRecyclerViewAdapter = null;
        }

        if(mJokeTextAdapter == null){
            mJokeTextAdapter = new JokeTextAdapter(mActivity,mJokeList);
        }else{
            mJokeTextAdapter.notifyDataSetChanged();
        }
        if(mHeaderAndFooterRecyclerViewAdapter == null){
            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mActivity,mJokeTextAdapter);
            mLRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity));
            mLRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            //设置下拉刷新的样式
            mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        }
    }

    @Override
    public void getDataFromSp() {
        super.getDataFromSp();
        //TODO 获取缓存数据
        mJokeList.clear();
        //TODO 执行了initData();但无网络所以没有缓存
        if(mJokeSp != null){
            Log.i(LFLConfig.TAG,"getDataFromSp-缓存："+mJokeSp.getString("JokeTextJson"+Page));
            Log.i(LFLConfig.TAG,"getDataFromSp-页数："+mJokeSp.getInt("Page"));
            //TODO 得到缓存的页数
            Page = mJokeSp.getInt("Page");
            //TODO 取得所有页面缓存
            for (int i = 1; i <= Page; i++) {
                BS_Joke_Text bean = (BS_Joke_Text)JsonUtils.toObject(mJokeSp.getString("JokeTextJson"+i),BS_Joke_Text.class);
                mJokeList.addAll(bean.getShowapi_res_body().getPagebean().getContentlist());
            }

            mJokeTextAdapter = null;
            mHeaderAndFooterRecyclerViewAdapter = null;
            if(mJokeTextAdapter == null){
                mJokeTextAdapter = new JokeTextAdapter(mActivity,mJokeList);
            }

            if(mHeaderAndFooterRecyclerViewAdapter == null){
                mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mActivity,mJokeTextAdapter);
                mLRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity));
                mLRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
                //TODO 设置下拉刷新的样式
                mLRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            }
            //TODO 使列表恢复用户之前阅读的那一行
            Log.i(LFLConfig.TAG,"firstVis："+firstVis+",lastOffset:"+lastOffset);
            if ((manager == null)) {
                manager = (LinearLayoutManager) mLRecyclerView.getLayoutManager();
            }
            manager.scrollToPositionWithOffset(firstVis, lastOffset);

        }else{
            //TODO 执行了initData();但无网络所以没有缓存,重新从网路获取数据
            getDataFromNet();
        }
    }
}
