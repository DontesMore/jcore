package com.umaster.jdacore;

import android.app.Application;

/**
 * Created by wz on 2016/8/8.
 * Email  37717239@qq.com
 * intro：app基类，这里主要管理activity的出入栈
 */
public class Jappliaction extends Application {
    protected String TAG;
    public static Jappliaction appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = getClass().getName();
        appContext = this;
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static Jappliaction getInstance() {
        return appContext;
    }
}
