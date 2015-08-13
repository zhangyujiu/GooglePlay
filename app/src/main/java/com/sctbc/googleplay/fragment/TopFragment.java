package com.sctbc.googleplay.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sctbc.googleplay.R;
import com.sctbc.googleplay.protocol.TopProtocol;
import com.sctbc.googleplay.tools.UiUtils;
import com.sctbc.googleplay.view.DrawableUtils;
import com.sctbc.googleplay.view.LoadingPage;

import java.util.List;
import java.util.Random;

/**
 * 排行界面
 * 作者：ZYJ
 * 时间：2015/8/1 0001 14:16
 */
public class TopFragment extends BaseFragment {


    private List<String> datas;

    @Override
    public View createSuccessView() {
        ScrollView scrollView=new ScrollView(UiUtils.getContext());
        LinearLayout layout=new LinearLayout(UiUtils.getContext());
        scrollView.setBackgroundResource(R.drawable.grid_item_bg_normal);
        layout.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i< datas.size();i++){
            Random random=new Random();
            int red=random.nextInt(220)+22;
            int green=random.nextInt(220)+22;
            int blue=random.nextInt(220)+22;
            int color=Color.rgb(red,green,blue);
            GradientDrawable drawable= DrawableUtils.createShap(color);
            TextView tv=new TextView(UiUtils.getContext());
            tv.setBackgroundDrawable(drawable);
            tv.setText(datas.get(i));
            tv.setTextSize(UiUtils.dip2px(14));
            tv.setTextColor(Color.WHITE);
            layout.addView(tv,new LinearLayout.LayoutParams(-2,-2));
        }
        scrollView.addView(layout);
        return scrollView;
    }

    @Override
    protected LoadingPage.LoadResut load() {
        TopProtocol topProtocol = new TopProtocol();
        datas = topProtocol.load(0);
        return checkData(datas);
    }
}
