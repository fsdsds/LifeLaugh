package com.tony.lifelaugh.LFLJoke;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tony.lifelaugh.Base.BaseFragment;
import com.tony.lifelaugh.Config.LFLConfig;
import com.tony.lifelaugh.Model.BS_Joke;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tony on 2016/9/22.
 */
public class JokeTextFragment extends BaseFragment{

    public static JokeTextFragment mJokeTextFragment;
    private String Page = "1";
    public static JokeTextFragment newInstence(){
        if(mJokeTextFragment == null){
            mJokeTextFragment = new JokeTextFragment();
        }
        return mJokeTextFragment;
    }
    TextView tv;
    @Override
    public View initView(LayoutInflater inflater) {
        tv = new TextView(getActivity());
        return tv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.i("initData","JokeTextFragment-isFirst:"+isFirst);
        if(isFirst){
            tv.setText("段子");
            getDataFromNet();
        }else{
            tv.setText("第二次段子");
            getDataFromSp();
        }
    }

    @Override
    public void getDataFromNet() {
        super.getDataFromNet();
        //TODO 获取网络数据

        Observable<BS_Joke> observable = service.getJoke(LFLConfig.AppId,LFLConfig.AppSercet,Page,LFLConfig.BS_TYPE_Joke);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BS_Joke>() {
            @Override
            public void onCompleted() {
                Log.i("Observable", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Observable", "error");
            }

            @Override
            public void onNext(BS_Joke bs_joke) {
                Log.i("Observable", "title:" + bs_joke.getShowapi_res_body().getPagebean().getContentlist().get(0).getText());
            }
        });
    }

    @Override
    public void getDataFromSp() {
        super.getDataFromSp();
        //TODO 获取缓存数据



    }
}
