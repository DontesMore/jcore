package com.umaster.jdacore;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：
 */
public class Jfragment extends Fragment {
    protected String TAG;
    protected Jactivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG=getClass().getName();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = (Jactivity) getActivity();
    }

    protected void toast(int resId) {
        mActivity.toast(resId);
    }
    protected void toast(String info) {
        mActivity.toast(info);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
