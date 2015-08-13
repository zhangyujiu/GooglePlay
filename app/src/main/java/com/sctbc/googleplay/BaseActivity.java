package com.sctbc.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/1 0001 15:28
 */
public class BaseActivity extends ActionBarActivity{

    //管理所有运行的activity
    public final static List<BaseActivity> mActivitys=new LinkedList<BaseActivity>();//LinkedList增删速度快，ArrayList查询速度快
    public static BaseActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivitys){
            mActivitys.add(this);
        }

        init();
        initView();
        initActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity=this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity=null;
    }

    protected void initActionBar() {

    }

    protected void initView() {

    }

    protected void init() {

    }
    public void killAll(){
        List<BaseActivity> copy;
        //复制了一份mActivity的集合（在遍历集合时，不能删除集合的元素）
        synchronized (mActivitys) {
             copy= new LinkedList<BaseActivity>(mActivitys);
        }
        for(BaseActivity activity:copy){
            mActivitys.remove(this);
        }
        //杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivitys) {
            mActivitys.remove(this);
        }
    }
}
