package com.androidlib.tony.ResofitUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 进度回调辅助类
 */
public class HttpClientHelper {
    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressResponseListener(final ProgressResponseListener progressListener) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //增加拦截器
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response originalResponse = chain.proceed(chain.request());

                Request request = chain.request();
                List<String> segments = request.url().pathSegments();
                String filename = segments.get(segments.size()-1);
                ProgressResponseBody body = new ProgressResponseBody(originalResponse.body(), progressListener);
                //从request中取出对应的header即我们设置的文件保存地址,然后保存到我们自定义的response中
                body.setSavePath(request.header(FileConverter.SAVE_PATH));
                body.setFileName(filename);

                //包装响应体并返回
                return originalResponse.newBuilder()
                        .body(body)
                        .build();
            }
        });
        return client.build();
    }


    /**
     * 包装OkHttpClient，用于上传文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressRequestListener(final ProgressRequestListener progressListener) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //增加拦截器
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .method(original.method(), new ProgressRequestseBody(original.body(), progressListener))
                        .build();
                return chain.proceed(request);
            }
        });
        return client.build();
    }

}
