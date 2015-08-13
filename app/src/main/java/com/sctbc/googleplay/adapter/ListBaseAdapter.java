package com.sctbc.googleplay.adapter;

import android.content.Intent;
import android.widget.ListView;

import com.sctbc.googleplay.DetailActivity;
import com.sctbc.googleplay.domain.AppInfo;
import com.sctbc.googleplay.holder.BaseHolder;
import com.sctbc.googleplay.holder.ListBaseHolder;
import com.sctbc.googleplay.tools.UiUtils;

import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/6 0006 11:44
 */
public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> {
    public ListBaseAdapter(List<AppInfo> datas,ListView lv) {
        super(datas,lv);
    }

    @Override
    protected BaseHolder<AppInfo> getHolder() {
        return new ListBaseHolder();
    }

    @Override
    protected abstract List<AppInfo> onLoad();
    /**
     * ListView的点击事件
     * @param position
     */
    @Override
    protected void onInnerItemClick(int position) {
        super.onInnerItemClick(position);
        Intent intent = new Intent(UiUtils.getContext(), DetailActivity.class);
        AppInfo appInfo=datas.get(position);
        intent.putExtra("packageName",appInfo.getPackageName());
        UiUtils.startActivity(intent);
    }
}
