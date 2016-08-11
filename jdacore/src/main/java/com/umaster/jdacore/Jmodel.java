package com.umaster.jdacore;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：model基类
 */
public class Jmodel implements Serializable, Comparable<Jmodel> {
    private  final long serialVersionUID = 1L;
    public String TAG;
    public Jmodel(){
        TAG=getClass().getName();
    }
    @Override
    public int compareTo(Jmodel another) {
        return 0;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
