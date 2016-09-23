package com.tony.lifelaugh.Config;

import android.os.Environment;

/**
 * Created by tony on 2016/9/20.
 */
public class LFLConfig {

    public static String BaseUrl = "https://route.showapi.com/";
    public static String AppId = "24712";
    public static String AppSercet = "5e6095daf6ab4551900cc5c683f2e6e0";
    public static String BS_TYPE_Joke = "29";
    public static String BS_TYPE_Picture = "10";
    public static String BS_TYPE_Video = "41";
    public static int REQUEST_COUNT = 20;

    //图片缓存的路径和缓存容量
    public final static String BaseDirectoryName = "LifeLaugh/image"; //图片所在的根目录文件夹名字
    public final static int MAXCACHESIZE = 0;
    public final static String PICCOMPRESSEDPATH = Environment.getExternalStorageDirectory() + "/LifeLaugh/image";
    public final static String APKDOWNLOADPATH = Environment.getExternalStorageDirectory() + "/LifeLaugh/file/";
}
