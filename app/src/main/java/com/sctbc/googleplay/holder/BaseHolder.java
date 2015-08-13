package com.sctbc.googleplay.holder;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.sctbc.googleplay.tools.BitmapHelper;

/**
 * 作者：ZYJ
 * 时间：2015/8/6 0006 10:07
 */
public abstract class BaseHolder<T> {

    private View contentView;
    private T data;
    protected BitmapUtils bitmapUtils;
    public BaseHolder(){
        bitmapUtils = BitmapHelper.getBitmapUtils();
        contentView=initView();
        contentView.setTag(this);
    }

    public abstract View initView();

    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    public abstract void refreshView(T data);

    public View getContentView() {
        return contentView;
    }
}
