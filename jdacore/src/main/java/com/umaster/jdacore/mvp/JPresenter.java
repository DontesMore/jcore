package com.umaster.jdacore.mvp;


import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：presenter基类 实现view层的交互
 */
public class JPresenter<V> implements JviewImpl<V> {
    public Reference<V> mvpView;    //  对view进行弱引用
    private CompositeSubscription mCompositeSubscription;//subscrpition
    @Override
    public void attachView(V view) {
        this.mvpView =new WeakReference<V>(view);
    }

    @Override
    public void detachView() {
        if(mvpView!=null){
            mvpView.clear();
            this.mvpView = null;//view置空
        }
        onUnsubscribe();//取消Rxjava的注册
    }
    /**
     * 取消注册，避免rxjava内存泄漏
     */
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
    /**
     * 注册rxjava
     * @param observable
     * @param subscriber
     */
    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}
