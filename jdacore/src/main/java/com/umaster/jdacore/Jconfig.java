package com.umaster.jdacore;

import android.graphics.Color;
import android.os.Environment;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：接口管理
 */
public class Jconfig {
    /**
     * 切换debog模式
     */
    public static final boolean DEBUG_MODEL=true;

    /**
     * 服务器地址
     */
    public static  final String API_SERVER_URL = "http://yuer.3uol.com/v2/";

    /**
     * 缓存存储路径
     */
    public  static  final String APP_CACHE = Environment.getExternalStorageState()+"/jcache/";



    /**
     * 是否沉浸式状态栏
     */
    public  static final boolean IS_IMMERSIVE =true;
    /**
     * 沉浸式状态栏的颜色
     */
    public static final int COLOR_IMMERSIVE = Color.parseColor("#65c4aa");
}
