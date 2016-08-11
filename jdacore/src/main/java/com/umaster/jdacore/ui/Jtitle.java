package com.umaster.jdacore.ui;

import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umaster.jdacore.Jactivity;
import com.umaster.jdacore.R;
import com.umaster.jdacore.utils.Jutils;

/**
 * Created by wz on 2016/8/8.
 * Email  37717239@qq.com
 * intro：activity标题
 */
public class Jtitle {
    private Jactivity jactivity;
    private static int flag;//标记标题的样式flag
    protected RelativeLayout layout;//jv标题layout
    protected View center;// 中间的view
    protected View left;//左边view
    protected View right;//右边view

    protected String str_left;// 左边的字符
    protected String str_right;// 右边的字符

    private View.OnClickListener listenerLeft;//左边的点击事件
    private View.OnClickListener listenerRight;//右边点击时间
    private View.OnClickListener listenerCenter;//中间点击事件

    protected int leftButtonResource;//左边图片资源id
    protected int rightButtonResource;//右边图片资源id

    public static final int TITLE_ONLY_CENTER = 3;// 两边都没有文字只有中间的标题
    public static final int TITLE_HAVE_LEFT_IMAGE = 1;// 只有左边有图片
    public static final int TITLE_HAVE_RIGHT_IAMGE = 2;// 只有右边有图片

    public static final int TITLE_LEFTIMAGE_RIGHTTEXT = 5;// 左边图片右边字

    public static final int TITLE_LEFTTEXT_RIGHTTEXT = 7;// 左右两边都是文字
    public static final int TITLE_HAVE_BOTHIMAGE = 0;// 左右都是图片

    public static final int TITLE_HAVE_IMAGEARRAY = 6;//左右两边图片按钮数组

    /**
     * 创建空标题栏
     *
     * @param context
     */
    public Jtitle(Jactivity context) {
        init(context);
    }

    /**
     * 初始view
     *
     * @param context
     */
    private void init(Jactivity context) {
        this.jactivity = context;
        Window window = context.getWindow();
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
        layout = (RelativeLayout) context.findViewById(R.id.custom_title_layout);
        layout.removeAllViews();//清理所有view，代码添加view
    }

    /**
     * 生成带左右图片的 title
     *
     * @param context
     * @param left_resource  左边图片资源id，默认为0
     * @param right_resource 右边图片资源id，默认为0
     */
    public Jtitle(int left_resource, Jactivity context, int right_resource) {
        init(context);
        leftButtonResource = left_resource == 0 ? jactivity.getLeftRes() : left_resource;
        rightButtonResource = right_resource == 0 ? jactivity.getRightRes() : right_resource;
        this.listenerLeft = jactivity.getLeftListener();
        this.listenerRight = jactivity.getRightListener();
        this.setupView(jactivity.getTitleStr(), TITLE_HAVE_BOTHIMAGE);
    }

    /**
     * 生成左边是图片，右边是文字的标题
     *
     * @param context
     * @param left_resource 左边图片，默认为0
     */
    public Jtitle(int left_resource, Jactivity context, String right_text) {
        init(context);
        leftButtonResource = left_resource == 0 ? jactivity.getLeftRes() : left_resource;
        this.str_right = right_text;
        this.listenerLeft = jactivity.getLeftListener();
        this.listenerRight = jactivity.getRightListener();
        this.setupView(jactivity.getTitleStr(), TITLE_LEFTIMAGE_RIGHTTEXT);
    }

    /**
     * 两边都是文字的标题
     *
     * @param left_text
     * @param context
     * @param right_text
     */
    public Jtitle(String left_text, Jactivity context, String right_text) {
        init(context);
        this.str_left = left_text;
        this.str_right = right_text;
        this.listenerLeft = jactivity.getLeftListener();
        this.listenerRight = jactivity.getRightListener();
        this.setupView(jactivity.getTitleStr(), TITLE_LEFTTEXT_RIGHTTEXT);
    }

    /**
     * 生成 title 只有左边的图片
     *
     * @param context
     * @param left_resource 左边图片，默认为0
     */
    public Jtitle(int left_resource, Jactivity context) {
        init(context);
        leftButtonResource = left_resource == 0 ? jactivity.getLeftRes() : left_resource;
        this.listenerLeft = jactivity.getLeftListener();
        this.setupView(jactivity.getTitleStr(), TITLE_HAVE_LEFT_IMAGE);
    }

    /**
     * 生成 title 只有右边图片
     *
     * @param context
     * @param right_resource 右边图片，默认为0
     */
    public Jtitle(Jactivity context, int right_resource) {
        init(context);
        rightButtonResource = right_resource == 0 ? jactivity.getLeftRes()
                : right_resource;
        this.listenerLeft = jactivity.getLeftListener();
        this.setupView(jactivity.getTitleStr(), TITLE_HAVE_RIGHT_IAMGE);
    }

    /**
     * 生成 title 只有中间的标题
     *
     * @param context
     */
    public Jtitle(Jactivity context, String noused) {
        init(context);
        this.setupView(jactivity.getTitleStr(), TITLE_ONLY_CENTER);
    }

    /**
     * 获取中间的view
     *
     * @return
     */
    public View getCenter() {
        return this.center;
    }

    /**
     * 获取左边view
     *
     * @return
     */
    public View getLeft() {
        return this.left;
    }

    /**
     * 获取右边view
     *
     * @return
     */
    public View getRight() {
        return this.right;
    }

    /**
     * 获取flag
     *
     * @return
     */
    public int getFlag() {
        return flag;
    }


    protected void setupView(String text, int flag) {
        this.flag = flag;

        RelativeLayout.LayoutParams lpCenter = new RelativeLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lpCenter.addRule(RelativeLayout.CENTER_IN_PARENT);
        center = createNewTextView(text, 18, listenerCenter);
        layout.addView(center, lpCenter);

        switch (flag) {
            case TITLE_HAVE_BOTHIMAGE:
                left = createNewBtn(leftButtonResource, listenerLeft);
                right = createNewBtn(rightButtonResource, listenerRight);
                layout.addView(left, getLayoutParams(RelativeLayout.ALIGN_PARENT_LEFT));
                layout.addView(right, getLayoutParams(RelativeLayout.ALIGN_PARENT_RIGHT));
                break;
            case TITLE_HAVE_LEFT_IMAGE:
                left = createNewBtn(leftButtonResource, listenerLeft);
                layout.addView(left, getLayoutParams(RelativeLayout.ALIGN_PARENT_LEFT));
                break;
            case TITLE_HAVE_RIGHT_IAMGE:
                right = createNewBtn(rightButtonResource, listenerRight);
                layout.addView(right,
                        getLayoutParams(RelativeLayout.ALIGN_PARENT_RIGHT));
                break;
            case TITLE_ONLY_CENTER:
                break;
            case TITLE_LEFTIMAGE_RIGHTTEXT:
                left = createNewBtn(leftButtonResource, listenerLeft);
                right = createNewTextView(str_right, 16, listenerRight);
                layout.addView(left,
                        getLayoutParams(RelativeLayout.ALIGN_PARENT_LEFT));
                layout.addView(right, getTextLayoutParams(RelativeLayout.ALIGN_PARENT_RIGHT));
                break;
            case TITLE_LEFTTEXT_RIGHTTEXT:
                left = createNewTextView(str_left, 16, listenerLeft);
                right = createNewTextView(str_right, 16, listenerRight);
                layout.addView(left, getLayoutParams(RelativeLayout.ALIGN_PARENT_LEFT));
                layout.addView(right, getTextLayoutParams(RelativeLayout.ALIGN_PARENT_RIGHT));
                break;
        }
    }


    /**
     * 两边是图片，曾默认用30dp的图片大小
     *
     * @param align 左边还是右边
     * @return
     */
    private RelativeLayout.LayoutParams getLayoutParams(int align) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                Jutils.dip2px(jactivity, 30), Jutils.dip2px(jactivity, 30));
        lp.leftMargin = Jutils.dip2px(jactivity, 15);
        lp.rightMargin = Jutils.dip2px(jactivity, 15);
        lp.addRule(align);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        return lp;
    }

    /**
     * 如果俩边是字符串，则长度和宽度设置成WRAP_CONTENT,
     *
     * @param align 左边还是右边
     * @return
     */
    private RelativeLayout.LayoutParams getTextLayoutParams(int align) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = Jutils.dip2px(jactivity, 15);
        lp.rightMargin = Jutils.dip2px(jactivity, 15);
        lp.addRule(align);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        return lp;
    }

    /**
     * 生成textview
     *
     * @param text     text内容
     * @param size     尺寸
     * @param listener 点击事件
     * @return
     */
    protected View createNewTextView(String text, float size, View.OnClickListener listener) {
        TextView tv_left = new TextView(this.jactivity);
        tv_left.setTextSize(size);
        tv_left.setTextColor(jactivity.getResources().getColor(R.color.cl_txt_white));
        if (text != null) {
            tv_left.setText(text);
        }else{
            tv_left.setText("");
        }
        if (listener != null) {
            tv_left.setOnClickListener(listener);
        }else{
            tv_left.setOnClickListener(null);
        }
        return tv_left;
    }

    /**
     * 添加按钮具体实现方法
     *
     * @param id
     * @param listener
     * @return
     */
    protected View createNewBtn(int id, View.OnClickListener listener) {
        ImageButton button = new ImageButton(this.jactivity);
        button.setBackgroundResource(id);
        button.setOnClickListener(listener);
        return button;
    }
}
