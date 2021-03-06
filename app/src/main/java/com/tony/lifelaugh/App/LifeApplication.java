package com.tony.lifelaugh.App;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tony.lifelaugh.Config.LFLConfig;

/**
 * Created by tony on 2016/9/20.
 */
public class LifeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBmob();
        initFresco();
    }

    private void initFresco() {

        /**
         * 设置图片缓存的位置，缓存大小等信息
         */
        DiskCacheConfig chacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName(LFLConfig.BaseDirectoryName)
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory().getAbsoluteFile())  //图片缓存文件夹存在的位置
                .setMaxCacheSize(LFLConfig.MAXCACHESIZE)
                .build();

        /**
         * 缓存的配置信息
         */
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(chacheConfig)
                .build();

        Fresco.initialize(this, imagePipelineConfig);

    }

    private void initBmob() {
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        /*BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("77f747131ab19adb8081b9fbec39f8d6")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);*/
    }
}
