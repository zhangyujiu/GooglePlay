package com.sctbc.googleplay.view;

import android.graphics.drawable.GradientDrawable;

import com.sctbc.googleplay.tools.UiUtils;

/**
 * 作者：ZYJ
 * 时间：2015/8/13 0013 18:24
 */
public class DrawableUtils {
    public static GradientDrawable createShap(int color) {
        GradientDrawable drawable=new GradientDrawable();
        drawable.setCornerRadius(UiUtils.dip2px(5));//设置4个角的弧度
        drawable.setColor(color);//设置颜色
        return drawable;
    }
}
