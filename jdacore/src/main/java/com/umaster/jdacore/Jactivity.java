package com.umaster.jdacore;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.umaster.jdacore.ui.Jtitle;
import com.umaster.jdacore.utils.JStatusBar;
import com.umaster.jdacore.utils.Jlog;
import com.umaster.jdacore.utils.Jtoast;

import butterknife.ButterKnife;

/**
 * Created by wz on 2016/8/4.
 * Email  37717239@qq.com
 * intro：activity基类
 */
public abstract class Jactivity extends AppCompatActivity {
    protected String TAG;
    public Activity mActivity;
    protected Jtitle mJVTitle;

    /**
     * 获取layoutId
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 设置view标题部分
     *
     * @return null表示没有标题 否则创建一个标题
     */
    public abstract Jtitle createTtitile();

    /**
     * 获取中间标题内容
     *
     * @return
     */
    public abstract String getTitleStr();

    /**
     * 获取左边图片资源，默认是返回，重写定制
     *
     * @return 当title包含左边资源的时候使用
     */
    public int getLeftRes() {
        return R.drawable.title_back;
    }

    /**
     * 获取右边资源
     *
     * @return 当title包含右边的资源的时候使用
     */
    public int getRightRes() {
        return R.drawable.title_home;
    }

    /**
     * 初始化创建
     */
    protected void initCreate() {
        TAG = getClass().getName();
        //设置layoutID
        setContentView(getLayoutId());
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ////设置沉浸式状态栏
        if (Jconfig.IS_IMMERSIVE) {
            JStatusBar.setStatusBarColor(this,Jconfig.COLOR_IMMERSIVE, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needTitleBar()) {
            this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            this.setTheme(R.style.j_title_theme);
        } else {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        initCreate();
        //设置标题栏
        mJVTitle = createTtitile();
    }

    /**
     * 是否需要标题栏
     *
     * @return true 需要标题栏，false 不需要标题栏
     */
    protected abstract boolean needTitleBar();

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        mActivity = null;
        mJVTitle = null;
        super.onDestroy();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;
    }

    /**
     * 显示某个residtoast
     *
     * @param resId string  resid
     */
    protected void toast(int resId) {
        Jtoast.showToast(mActivity, resId);
    }

    /**
     * 显示提示toastinfo
     *
     * @param info string infp
     */
    protected void toast(String info) {
        Jtoast.showToast(mActivity, info);
    }

    /**
     * 左边按钮点击
     *
     * @return
     */
    public View.OnClickListener getLeftListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jlog.d(TAG, "left onclick ....");
            }
        };
    }

    /**
     * 右边按钮点击
     *
     * @return
     */
    public View.OnClickListener getRightListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }
}
