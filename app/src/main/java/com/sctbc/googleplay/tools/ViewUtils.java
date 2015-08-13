package com.sctbc.googleplay.tools;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 作者：ZYJ
 * 时间：2015/8/3 0003 11:51
 */
public class ViewUtils {
    public static void removeParent(View v) {
        //先找到爹，在通过爹去移除孩子
        ViewParent parent = v.getParent();
        //所有的控件都有爹，爹一般情况下就是ViewGroup
        if(parent instanceof ViewGroup){
            ViewGroup group= (ViewGroup) parent;
            group.removeView(v);
        }
    }
}
