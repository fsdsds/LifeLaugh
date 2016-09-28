package com.tony.lifelaugh.RetrofitService;

import com.tony.lifelaugh.Model.BS_Joke_Picture;
import com.tony.lifelaugh.Model.BS_Joke_Text;
import com.tony.lifelaugh.Model.BS_Joke_Video;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tony on 2016/9/20.
 */
public interface QueryJsonService {


    @GET("255-1")//https://route.showapi.com/255-1?showapi_appid=24712&showapi_sign=5e6095daf6ab4551900cc5c683f2e6e0&page=1&type=29
    Observable<BS_Joke_Text> getTextJoke(@Query("showapi_appid") String id, @Query("showapi_sign") String sign
    , @Query("page") String page, @Query("type") String type);

    @GET("255-1")//https://route.showapi.com/255-1?showapi_appid=24712&showapi_sign=5e6095daf6ab4551900cc5c683f2e6e0&page=1&type=10
    Observable<BS_Joke_Picture> getPictureJoke(@Query("showapi_appid") String id, @Query("showapi_sign") String sign
            , @Query("page") String page, @Query("type") String type);

    @GET("255-1")//https://route.showapi.com/255-1?showapi_appid=24712&showapi_sign=5e6095daf6ab4551900cc5c683f2e6e0&page=1&type=41
    Observable<BS_Joke_Video> getVideoJoke(@Query("showapi_appid") String id, @Query("showapi_sign") String sign
            , @Query("page") String page, @Query("type") String type);

}
