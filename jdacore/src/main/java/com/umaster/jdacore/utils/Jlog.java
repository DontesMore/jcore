package com.umaster.jdacore.utils;

import android.util.Log;

import com.umaster.jdacore.Jconfig;


/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro： log输入类
 */
public class Jlog {
    private static final String TAG = "Jlog";
    /**
     * 是否开启log测试
     */
    private static boolean isDebug = Jconfig.DEBUG_MODEL;

    private Jlog() {
        /* 不能被实例化 */
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * info 层
     *
     * @param msg
     */
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    /**
     * debug层
     *
     * @param msg
     */
    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    /**
     * err层
     *
     * @param msg
     */
    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    /**
     * warning层
     * @param msg
     */
    public static void w(String msg){
        if(isDebug)
            Log.w(TAG,msg);
    }


    /**
     * verbose层
     * @param msg
     */
    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void w(String tag,String msg){
        if(isDebug)
            Log.w(tag,msg);
    }
    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }


    static long lastTime = 0;
    /**
     * 运行时间性能测试
     * @param i >=0,=0时表示初始点
     */
    public static void testTime(int i) {
        if (i < 0) return;
        long nowTime = System.currentTimeMillis();
        if (i == 0) {
            lastTime = System.currentTimeMillis();
        } else {
            if (lastTime == 0)
                lastTime = System.currentTimeMillis();
            v(TAG, "第 "+i+ " 个点之间消耗时间为 " + (nowTime - lastTime)+" 毫秒");
            lastTime = nowTime;
        }
    }
}
