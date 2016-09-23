package com.androidlib.tony.ResofitUtils;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * Created by somon on 2016/8/7.
 */
public interface FileDownloadService {


    @GET
    Call<File> download(@Url String fileUrl, @Header(FileConverter.SAVE_PATH) String path);
}
