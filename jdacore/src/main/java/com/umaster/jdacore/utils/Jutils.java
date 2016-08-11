package com.umaster.jdacore.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

/**
 * Created by wz on 2016/8/8.
 * Email  37717239@qq.com
 * intro：没有分类的工具类方法
 */
public class Jutils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



    /**
     * 获取目录文件大小
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 计算缓存的大小,
     * @return
     */
    public static String getCacheSize(Context context) {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = context.getApplicationContext().getFilesDir();// /data/data/package_name/files
        File cacheDir = context.getCacheDir();// /data/data/package_name/cache
        File imgloaderCacheDir= ImageLoader.getInstance().getDiskCache().getDirectory();//imagloader 缓存
        fileSize += getDirSize(filesDir);
        fileSize += getDirSize(cacheDir);
        fileSize+=getDirSize(imgloaderCacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = getExternalCacheDir(context);// "<sdcard>/Android/data/<package_name>/cache/"
            fileSize += getDirSize(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = formatFileSize(fileSize);
        return cacheSize;
    }
    /**
     * 将二进制长度转换成文件大小
     *
     * @param length
     * @return
     */
    public static String formatFileSize(long length) {
        String result = null;
        int sub_string = 0;
        if (length >= 1073741824) {
            sub_string = String.valueOf((float) length / 1073741824).indexOf(
                    ".");
            result = ((float) length / 1073741824 + "000").substring(0,
                    sub_string + 3) + "GB";
        } else if (length >= 1048576) {
            sub_string = String.valueOf((float) length / 1048576).indexOf(".");
            result = ((float) length / 1048576 + "000").substring(0,
                    sub_string + 3) + "MB";
        } else if (length >= 1024) {
            sub_string = String.valueOf((float) length / 1024).indexOf(".");
            result = ((float) length / 1024 + "000").substring(0,
                    sub_string + 3) + "KB";
        } else if (length < 1024)
            result = Long.toString(length) + "B";
        return result;
    }

    /**
     * 清除app缓存
     */
    public static void clearAppCache(Context context) {
        Jimgloader.clear();//图片缓存
        // 清除数据缓存
        clearCacheFolder(context.getFilesDir(), System.currentTimeMillis());
        clearCacheFolder(context.getCacheDir(), System.currentTimeMillis());
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            clearCacheFolder(getExternalCacheDir(context),
                    System.currentTimeMillis());
        }
    }
    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 获取缓存文件夹
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        // return context.getExternalCacheDir(); API level 8
        // e.g. "<sdcard>/Android/data/<package_name>/cache/"
        return context.getExternalCacheDir();
    }

    /**
     * 清除缓存目录
     * @return
     */
    public static int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /**
     * 获取服务是否开启
     *
     * @param context   上下文
     * @param className 完整包名的服务类名
     * @return true: 是<br>false: 否
     */
    public static boolean isRunningService(Context context, String className) {
        // 进程的管理者,活动的管理者
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的服务，最多获取1000个
        List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
        // 遍历集合
        for (RunningServiceInfo runningServiceInfo : runningServices) {
            ComponentName service = runningServiceInfo.service;
            if (className.equals(service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * HTML字符转义
     * <p>对输入参数中的敏感字符进行过滤替换,防止用户利用JavaScript等方式输入恶意代码</p>
     *
     * @param html html文本
     * @return 过滤后的文本
     */
    public static String htmlEscape(String html) {
        return html.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll(" ", "&nbsp;")
                .replaceAll("'", "&#39;")
                .replaceAll("\"", "&quot;")
                .replaceAll("\n", "<br/>");
    }
}
