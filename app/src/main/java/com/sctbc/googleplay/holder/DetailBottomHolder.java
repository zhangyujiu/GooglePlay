package com.sctbc.googleplay.holder;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sctbc.googleplay.R;
import com.sctbc.googleplay.domain.AppInfo;
import com.sctbc.googleplay.tools.UiUtils;

/**
 * 作者：ZYJ
 * 时间：2015/8/12 0012 10:00
 */
public class DetailBottomHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    @ViewInject(R.id.bottom_favorites)
    private Button bottom_favorites;
    @ViewInject(R.id.bottom_share)
    private Button bottom_share;
    @ViewInject(R.id.progress_btn)
    private Button progress_btn;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.detail_bottom);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        bottom_favorites.setOnClickListener(this);
        bottom_share.setOnClickListener(this);
        progress_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bottom_favorites:
                Toast.makeText(UiUtils.getContext(), "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_share:
                Toast.makeText(UiUtils.getContext(), "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.progress_btn:
                Toast.makeText(UiUtils.getContext(), "下载", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }
}
