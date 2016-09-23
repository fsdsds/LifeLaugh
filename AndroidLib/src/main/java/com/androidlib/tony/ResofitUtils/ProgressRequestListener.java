package com.androidlib.tony.ResofitUtils;

/**
 * 监听上传进度
 */
public interface ProgressRequestListener {
    /**
     * @param progress     已经上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onRequestProgress(long progress, long total, boolean done);

}

/**
 * 监听下载进度
 */
interface ProgressResponseListener{
    /**
     *
     * @param progress     已经上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onResponseProgress(long progress, long total, boolean done);


}
