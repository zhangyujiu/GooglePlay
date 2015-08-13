package com.sctbc.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 作者：ZYJ
 * 时间：2015/8/1 0001 16:24
 */
public class BaseApplication extends Application {

    private static BaseApplication application;
    private static int mainTid;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        mainTid = android.os.Process.myTid();
        handler=new Handler();
    }

    //获取context对象
    public static Context getApplication() {
        return application;
    }

    //获取主线程id
    public static int getMainTid() {
        return mainTid;
    }

    //获取handler
    public static Handler getHandler() {
        return handler;
    }
}
