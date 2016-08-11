package com.umaster.jdacore.mvp;

import android.os.Bundle;

import com.umaster.jdacore.Jactivity;


/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：mvp模式的ativity基类
 */
    public abstract class JmvpActivity<P extends JPresenter> extends Jactivity {
    protected P jPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        jPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }
    protected abstract P createPresenter();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (jPresenter != null) {
            jPresenter.detachView();
        }
    }


}
