package com.sctbc.googleplay.tools;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.sctbc.googleplay.BaseActivity;
import com.sctbc.googleplay.BaseApplication;

/**
 * 作者：ZYJ
 * 时间：2015/8/1 0001 16:19
 */
public class UiUtils {

    /**
     * 获取到字符数组
     *
     * @param tabNames 字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    public static Resources getResource() {
        return BaseApplication.getApplication().getResources();
    }

    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     */
    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 把Runnable方法提交到主线程运行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        //在主线程中运行
        if (android.os.Process.myTid() == BaseApplication.getMainTid()) {
            runnable.run();
        } else {
            //获取handler
            BaseApplication.getHandler().post(runnable);
        }
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    public static Drawable getDrawable(int id) {
        return getResource().getDrawable(id);
    }

    /**
     * 延时执行任务
     * @param runnable 任务
     * @param time 延迟时间
     */
    public static void postDelayed(Runnable runnable, int time) {
        BaseApplication.getHandler().postDelayed(runnable,time);//调用Runable里面的run方法
    }

    /**
     * 取消任务
     * @param runnable
     */
    public static void cancel(Runnable runnable) {
        BaseApplication.getHandler().removeCallbacks(runnable);
    }

    /**
     * 打开activity
     * @param intent
     */
    public static void startActivity(Intent intent) {

        //如果不在activity里去打开activity时，需要指定任务栈，设置标签
        if(BaseActivity.activity==null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }else{
            BaseActivity.activity.startActivity(intent);
        }
    }

}
