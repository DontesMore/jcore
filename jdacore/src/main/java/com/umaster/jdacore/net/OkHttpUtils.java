package com.umaster.jdacore.net;

import com.umaster.jdacore.Jconfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by wz on 2016/8/11.
 * Email  37717239@qq.com
 * intro：OkHttpClient自定义工具类
 */
public class OkHttpUtils {
    private static final String RESPONSE_CACHE = Jconfig.APP_CACHE + "/okhttp/";//缓存路径
    private static final long RESPONSE_CACHE_SIZE = 1024 * 1024 * 2;//缓存大小，暂定2M
    private static final int HTTP_CONNECT_TIMEOUT = 5;//链接超时
    private static final int HTTP_READ_TIMEOUT = 30;//读取超时
    private static OkHttpClient singleton;

    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    File cacheDir = new File(RESPONSE_CACHE, "responsecache");

                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                            .cache(new Cache(cacheDir, RESPONSE_CACHE_SIZE))
                            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);

                    if (Jconfig.DEBUG_MODEL) {// Log信息拦截器
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//把body打印出来
                        builder.addInterceptor(loggingInterceptor);
                    }
                    singleton = builder.build();
                }
            }
        }
        return singleton;
    }
}
