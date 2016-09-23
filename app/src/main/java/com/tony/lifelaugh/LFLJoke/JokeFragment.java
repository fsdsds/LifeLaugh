package com.tony.lifelaugh.LFLJoke;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidlib.tony.utils.DisplayUtil;
import com.tony.lifelaugh.Base.BaseFragment;
import com.tony.lifelaugh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/9/22.
 */
public class JokeFragment extends BaseFragment {

    public static JokeFragment mJokeFragment;
    public View view;
    public ViewPager mViewPager;
    public LinearLayout mLinearLayout;
    public JokeTextFragment mJokeTextFragment;
    public JokePictureFragment mJokePictureFragment;
    public JokeVideoFragment mJokeVideoFragment;
    public FragmentPagerAdapter mFragmentPagerAdapter;
    public List<BaseFragment> mFragmentList = new ArrayList<>();
    public String[] tab = {"段子","图片","视频"};

    public Activity mActivity;
    public static JokeFragment newInstence(){
        if(mJokeFragment == null){
            mJokeFragment = new JokeFragment();
        }
        return mJokeFragment;
    }
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_joke_layout,null);
        mActivity = getActivity();
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_joke);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.linear_content);
        for (int i = 0; i < tab.length; i++) {
            initTabView(tab[i]);
        }
        ChangeText(0);
        initFragment();
        initFragmentPagerAdapter();
        setAdapter();
        setViewPagerChangeListener();
        initOnClick();

        return view;
    }

    public void initTabView(String tab){
        TextView tv = new TextView(mActivity);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        tv.setTextColor(getResources().getColor(R.color.tab_unselect));
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mActivity,50), ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(param);
        tv.setText(tab);
        mLinearLayout.addView(tv);
    }

    private void initFragment() {

        mJokeTextFragment = JokeTextFragment.newInstence();
        mJokePictureFragment = JokePictureFragment.newInstence();
        mJokeVideoFragment = JokeVideoFragment.newInstence();
        mFragmentList.add(mJokeTextFragment);
        mFragmentList.add(mJokePictureFragment);
        mFragmentList.add(mJokeVideoFragment);
    }

    private void initFragmentPagerAdapter() {

        mFragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };

    }

    private void setAdapter() {
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }

    private void setViewPagerChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ResetText();
                ChangeText(position);
                
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void ChangeText(int position) {
        TextView tv = ((TextView)mLinearLayout.getChildAt(position));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setTextColor(getResources().getColor(R.color.tab_select));
    }

    private void ResetText() {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if(mLinearLayout.getChildAt(i) instanceof TextView){
                TextView tv = ((TextView)mLinearLayout.getChildAt(i));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                tv.setTextColor(getResources().getColor(R.color.tab_unselect));
            }
        }
    }

    private void initOnClick() {
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            if(mLinearLayout.getChildAt(i) instanceof TextView){
                final int pos = i ;
                TextView tv = ((TextView)mLinearLayout.getChildAt(i));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(pos);
                    }
                });
            }
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

}
