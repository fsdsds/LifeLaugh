package com.tony.lifelaugh.RetrofitService;

import com.tony.lifelaugh.Model.BS_Joke;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tony on 2016/9/20.
 */
public interface QueryJsonService {


    @GET("255-1")
    Observable<BS_Joke> getJoke(@Query("showapi_appid") String id, @Query("showapi_sign") String sign
    ,@Query("page") String page,@Query("type") String type);

}
