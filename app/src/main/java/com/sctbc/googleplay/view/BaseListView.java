package com.sctbc.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.sctbc.googleplay.R;
import com.sctbc.googleplay.tools.UiUtils;

/**
 * 作者：ZYJ
 * 时间：2015/8/5 0005 10:47
 */
public class BaseListView extends ListView {
    public BaseListView(Context context) {
        super(context);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setSelector(R.drawable.nothing);//设置点击显示的颜色
        this.setCacheColorHint(R.drawable.nothing);//设置拖拽显示的颜色
        this.setDivider(UiUtils.getDrawable(R.drawable.nothing));//设置每个条目的间隔分割线
    }
}
