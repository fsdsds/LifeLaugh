package com.androidlib.tony.utils;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by tony on 2016/9/27.
 */
public class ProgressUtils {

    public static void showLoadingProgress(ProgressBar mProgressBar){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public static void hideLoadingProgress(ProgressBar mProgressBar){
        mProgressBar.setVisibility(View.GONE);
    }
}
