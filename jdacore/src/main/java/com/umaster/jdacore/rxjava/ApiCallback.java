package com.umaster.jdacore.rxjava;
public interface ApiCallback<T> {
    void onStart();//api开始请求
    void onSuccess(T model);//成功回调
    void onFailure(int status, String message);//失败回调
    void onCompleted();//结束请求
}
