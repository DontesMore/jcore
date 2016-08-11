package com.umaster.jdacore.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

/**
 * Created by wz on 2016/8/5.
 * Email  37717239@qq.com
 * intro：图片加载器 目前选择universalimageloader
 */
public class Jimgloader {
    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        // File cacheDir = CacheManager.imageLoaderCache;  // 获取到缓存的目录地址
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .memoryCacheExtraOptions(300, 600)
                // Can slow ImageLoader, use it carefully (Better don't use it)设置缓存的详细信息，最好不要设置这个
                //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
                // 线程池内加载的数量
                .threadPoolSize(2)
                // 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // 当你显示一个图像的一个小图片后来你试图显示这个图像（从相同的URI）在一个更大的图片,因此解码图像的更大的尺寸将被缓存在内存中作为一个较小的大小的先前解码的图像。
                // 所以默认行为是允许在内存中缓存多个大小的一个图像。你可以通过调用这个方法来拒绝它：所以当某些图像会被缓存在内存中，然后缓存的大小（如果它存在） 将从内存缓存中删除。
                //.denyCacheImageMultipleSizesInMemory()
                // 硬盘缓存30MB
                .diskCacheSize(30 * 1024 * 1024)
                .memoryCacheSize(3 * 1024 * 1024)  // 内存缓存的最大值 设置3M
                // 将保存的时候的URI名称用MD5
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 加密
                //.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())  // 将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(200)  // 缓存的File数量
                //.diskCache(new UnlimitedDiskCache(new File(Jconfig.APP_CACHE,"")))  // 自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 8 * 1000, 30 * 1000))  // connectTimeout (8 s), readTimeout (30 s)超时时间
                // Remove for release app
                //.writeDebugLogs()
                .build();
        // Initialize ImageLoader with configuration.
        L.writeLogs(false);
        ImageLoader.getInstance().init(config);  // 全局初始化此配置
    }

    private static ImageLoader imageLoader;

    public static ImageLoader getInstance() {
        if (imageLoader == null)
            imageLoader = ImageLoader.getInstance();
        return imageLoader ;
    }

    /**
     * 显示图片
     *
     * @param uri        图片地址
     * @param imageAware
     */
    public static void disPlay(String uri, ImageView imageAware) {
        imageLoader.displayImage(uri, imageAware, getDefaultOptions());
    }

    /**
     * 显示图片
     *
     * @param uri         图片地址
     * @param imageAware
     * @param default_pic 默认图片resid
     */
    public static void disPlay(String uri, ImageView imageAware, int default_pic) {
        imageLoader.displayImage(uri, imageAware, getDefaultOptions(default_pic));
    }

    /**
     * 加载图片，并且把图片转换成灰色
     *
     * @param uri         图片地址
     * @param imageAware  imageview
     * @param default_pic 默认图片
     */
    public static void disPlayGrayImage(String uri, ImageView imageAware, int default_pic) {
        imageLoader.displayImage(uri, imageAware, getDefaultOptions(default_pic), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }
            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (bitmap != null)
                    try {
                        ((ImageView) view).setImageBitmap(Jimgutils.greyBitmap(bitmap));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                bitmap.recycle();
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }

    public static void disPlayHeader(String uri, ImageView imageView, int default_pic) {
        imageLoader.displayImage(uri, imageView, getHeaderViewOptions(default_pic));
    }

    public static void disPlay(String uri, ImageView imageAware, int default_pic, ImageLoadingListener imageLoadingListener) {
        if (null != imageLoadingListener) {
            imageLoader.displayImage(uri, imageAware, getDefaultOptions(default_pic), imageLoadingListener);
        } else {
            imageLoader.displayImage(uri, imageAware, getDefaultOptions(default_pic));
        }
    }

    public static DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)  // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)  // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build(); // 载入图片前稍做延时可以提高整体滑动的流畅度
        return options;
    }

    public static DisplayImageOptions getDefaultOptions(int default_pic) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(default_pic)
                .showImageForEmptyUri(default_pic).showImageOnFail(default_pic)
                .cacheInMemory(true)  // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)  // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)  // 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }


    /**
     * 列表图片加载配置
     * 根据图片需要展示的不同处理方式进行不同配置
     */
    public static DisplayImageOptions getDefaultOptions(int loading_def_img, int empty_def_img, int fail_def_img) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(loading_def_img)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(empty_def_img)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(fail_def_img)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)
                // 保留Exif信息
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)  // 设置图片的解码配置
                .considerExifParams(true)
                .resetViewBeforeLoading(true)  // 设置图片在下载前是否重置，复位
                .build();
        return options;
    }

    /**
     * EXACTLY:图像将完全按比例缩小的目标大小
     * EXACTLY_STRETCHED:图片会缩放到目标大小完全
     * IN_SAMPLE_INT:图像将被二次采样的整数倍
     * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
     * NONE:图片不会调整
     *
     * @param loading_def_img
     * @return
     */
    public static DisplayImageOptions getHeaderViewOptions(int loading_def_img) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loading_def_img)  // 设置图片在下载期间显示的图片  
                .showImageForEmptyUri(loading_def_img)  // 设置图片Uri为空或是错误的时候显示的图片  
                .showImageOnFail(loading_def_img)  // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)  // 设置下载的图片是否缓存在内存中  
                .cacheOnDisc(true)  // 设置下载的图片是否缓存在SD卡中  
                .considerExifParams(true)  // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)  // 设置图片以如何的编码方式显示  
                .bitmapConfig(Bitmap.Config.ARGB_8888)  // 设置图片的解码类型
                //.resetViewBeforeLoading(true)  // 设置图片在下载前是否重置，复位  
                //.displayer(new RoundedBitmapDisplayer(20))  // 是否设置为圆角，弧度为多少  
                //.displayer(new FadeInBitmapDisplayer(100))  // 是否图片加载好后渐入的动画时间  
                .build();  // 构建完成  
        return options;
    }

    public static Bitmap localImg(String uri) {
        return imageLoader.loadImageSync(uri);
    }

    /**
     * 清理緩存
     */
    public static void clear() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }

    /**
     * 删除某个图片的缓存
     *
     * @param url
     */
    public static void removeImage(String url) {
        DiskCacheUtils.removeFromCache(url, imageLoader.getDiskCache());
        MemoryCacheUtils.removeFromCache(url, imageLoader.getMemoryCache());
    }

    public static void resume() {
        imageLoader.resume();
    }

    /**
     * 暂停加载
     */
    public static void pause() {
        imageLoader.pause();
    }

    /**
     * 停止加载
     */
    public static void stop() {
        imageLoader.stop();
    }

    /**
     * 销毁加载
     */
    public static void destroy() {
        imageLoader.destroy();
    }


}
