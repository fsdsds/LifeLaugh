package com.tony.lifelaugh.GuideActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tony.lifelaugh.Base.BaseActivity;
import com.tony.lifelaugh.Base.BaseFragment;
import com.tony.lifelaugh.LFLComic.ComicFragment;
import com.tony.lifelaugh.LFLHelpLife.HelpLifeFragment;
import com.tony.lifelaugh.LFLJoke.JokeFragment;
import com.tony.lifelaugh.LFLPicture.PictureFragment;
import com.tony.lifelaugh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.radio_joke)
    RadioButton radioJoke;
    @BindView(R.id.radio_comic)
    RadioButton radioComic;
    @BindView(R.id.radio_picture)
    RadioButton radioPicture;
    @BindView(R.id.radio_life)
    RadioButton radioLife;
    @BindView(R.id.Main_raido)
    RadioGroup MainRaido;

    public JokeFragment mJokeFragment;
    public ComicFragment mComicFragment;
    public HelpLifeFragment mHelpLifeFragment;
    public PictureFragment mPictureFragment;
    public BaseFragment TempleFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //TODO 设置RadioButton的改变监听
        setRadioListener();
        initFragment();
    }

    private void initFragment() {

        mJokeFragment = JokeFragment.newInstence();
        mComicFragment = ComicFragment.newInstence();
        mHelpLifeFragment = HelpLifeFragment.newInstence();
        mPictureFragment = PictureFragment.newInstence();
        TempleFragment = mJokeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, mJokeFragment).commit();
    }

    private void setRadioListener() {

        MainRaido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                initRadioText();
                switch (group.getCheckedRadioButtonId()){
                    case R.id.radio_joke:
                        ChangeRadioButton(radioJoke,mJokeFragment);
                        break;
                    case R.id.radio_comic:
                        ChangeRadioButton(radioComic,mComicFragment);
                        break;
                    case R.id.radio_picture:
                        ChangeRadioButton(radioPicture,mPictureFragment);
                        break;
                    case R.id.radio_life:
                        ChangeRadioButton(radioLife,mHelpLifeFragment);
                        break;
                }
            }
        });
        radioJoke.setChecked(true);
    }

    public void ChangeRadioButton(RadioButton radioButton, BaseFragment fragment) {
        radioButton.setTextColor(getResources().getColor(R.color.DownMenuCheckFontColor));
        switchFragment(fragment);
    }

    private void switchFragment(BaseFragment fg) {
        if (fg != TempleFragment) {
            if (!fg.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(TempleFragment)
                        .add(R.id.frame_content, fg).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(TempleFragment)
                        .show(fg).commit();
            }
            TempleFragment = fg;
        }
    }

    private void initRadioText() {
        radioJoke.setTextColor(getResources().getColor(R.color.DownMenuUnCheckFontColor));
        radioComic.setTextColor(getResources().getColor(R.color.DownMenuUnCheckFontColor));
        radioPicture.setTextColor(getResources().getColor(R.color.DownMenuUnCheckFontColor));
        radioLife.setTextColor(getResources().getColor(R.color.DownMenuUnCheckFontColor));
    }
}
