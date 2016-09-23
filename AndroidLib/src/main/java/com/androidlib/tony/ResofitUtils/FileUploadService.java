package com.androidlib.tony.ResofitUtils;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by tony on 2016/6/17.
 */
public interface FileUploadService {

    @Multipart
    @POST("user/SetAvatar.ashx")
        //"http://120.76.207.106:888/user/SetAvatar.ashx?uid=2&client=2
    Call<ResponseBody> uploadImage(@PartMap Map<String, RequestBody> pamams);


}
