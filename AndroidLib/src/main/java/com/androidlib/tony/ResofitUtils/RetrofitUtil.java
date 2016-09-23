package com.androidlib.tony.ResofitUtils;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tony
 * 上傳下載的邏輯
 */
public class RetrofitUtil {

    private static RetrofitUtil mRetrofitUtil;

    public static RetrofitUtil newInstence() {
        if (mRetrofitUtil == null) {
            mRetrofitUtil = new RetrofitUtil();
        }
        return mRetrofitUtil;
    }

    public Retrofit getRetrofit(String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//把Call<>转换成Observable
                .build();
        return retrofit;
    }

    /**
     * 帶進度條的上傳
     *
     * @param path
     * @param isImg
     */
    public void uploadWithProgress(String path, boolean isImg) {
        FileUploadService uploadService = ServiceGenerator.createReqeustService(FileUploadService.class, requestListener);
        File file = new File(path);
        //application/octet-stream代表的是文件的形式传输的，这样做的好处是可以传输多种格式的文件，不管你是jpeg还是png都可以通过这种方式传送过去
        //multipart/form-data
//        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        if (isImg) {
//            MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
            Map<String, RequestBody> map = new HashMap<>();
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), (  ""));
            map.put("uid", requestBody1);

            RequestBody requestBody2 = RequestBody.create(MediaType.parse("text/plain"), "2");
            map.put("client", requestBody2);

            RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/png"), file);
            map.put("photo\"; filename=\"" + file.getName() + "", requestBody3);

            Call<ResponseBody> call = uploadService.uploadImage(map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    try {
                        Log.i("ResofitUpload", response.message() + "-" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }
    /**
     * 不帶進度條的上傳
     *
     * @param path
     * @param isImg
     */

  /*  public void upload(String path, boolean isImg, String name) {
        FileUploadService uploadService = ServiceGenerator.createService(FileUploadService.class);

        List<DBUserInfoBean> ids = DataSupport.select("uid").find(DBUserInfoBean.class);
        DBUserInfoBean dbUserInfoBean = ids.get(0);
        int id = dbUserInfoBean.getUid();
        if (isImg) {
            File file = new File(path);

            //application/octet-stream代表的是文件的形式传输的，这样做的好处是可以传输多种格式的文件，不管你是jpeg还是png都可以通过这种方式传送过去
            //multipart/form-data
            RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("imgFile", file.getName(), requestFile);
            Call<ResponseBody> call = uploadService.uploadImage(id + "", "2", body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    try {
                        Log.i("ResofitUpload", response.message() + "-" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
//            MultipartBody.Part body = MultipartBody.Part.createFormData("videoFile", file.getName(), requestFile);
//            Call<ResponseBody> call = uploadService.uploadString(id + "", name, "2");
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call,
//                                       Response<ResponseBody> response) {
//                    Log.d("ResofitUpload", "response.isSuccessful():" + response.isSuccessful());
//                    try {
//                        Log.i("ResofitUpload", response.message() + "-" + response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    t.printStackTrace();
//                }
//            });
        }

    }*/





    /**
     * 上傳監聽
     */
    ProgressRequestListener requestListener = new ProgressRequestListener() {
        @Override
        public void onRequestProgress(long progress, long total, boolean done) {
            Listener.getProgress(progress, total, done);
        }
    };

    public static void setUploadProgressListener(onUploadProgressListener progressListener) {
        Listener = progressListener;
    }

    public static onUploadProgressListener Listener;

    public interface onUploadProgressListener {
        void getProgress(long progress, long total, boolean done);
    }


    /**
     * 下載監聽
     */

    ProgressResponseListener responseListener = new ProgressResponseListener() {

        @Override
        public void onResponseProgress(long progress, long total, boolean done) {
            downLoadlistener.getProgress(progress, total, done);
        }
    };

    public static void setDownloadProgressListener(onDownloadProgressListener progresslistener) {
        downLoadlistener = progresslistener;
    }

    public static onDownloadProgressListener downLoadlistener;

    public interface onDownloadProgressListener {
        void getProgress(long progress, long total, boolean done);

    }
}
