package com.umaster.jdacore.net;

import com.umaster.jdacore.Jconfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：
 */
public class Jnet {
    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Jconfig.API_SERVER_URL)//服务器地址
                    .addConverterFactory(GsonConverterFactory.create())//Gson转化
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//rxjava
                    .client(new OkHttpClient())//采用okhttp配置
                    .build();
        }
        return mRetrofit;
    }

}
