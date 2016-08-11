package com.umaster.jdacore.mvp;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：presenter类的接口，presenter和view交互，提供绑定和取消view的两个接口操作
 */
public interface JviewImpl<V> {
    /**
     * 绑定view
     * @param view
     */
    void attachView(V view);

    /**
     * 取消绑定view
     */
    void detachView();
}