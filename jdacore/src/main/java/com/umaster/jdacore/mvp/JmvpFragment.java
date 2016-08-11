package com.umaster.jdacore.mvp;


import android.os.Bundle;
import android.view.View;

import com.umaster.jdacore.Jfragment;


/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：mvp模式的ativity基类
 * @param <P> 和view交互的presenter
 */
public abstract class JmvpFragment<P extends JPresenter> extends Jfragment {
    protected P mvpPresenter;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
