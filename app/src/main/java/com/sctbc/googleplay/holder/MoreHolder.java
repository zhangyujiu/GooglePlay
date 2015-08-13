package com.sctbc.googleplay.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.sctbc.googleplay.R;
import com.sctbc.googleplay.adapter.DefaultAdapter;
import com.sctbc.googleplay.tools.UiUtils;

/**
 * 作者：ZYJ
 * 时间：2015/8/6 0006 16:22
 */
public class MoreHolder extends BaseHolder<Integer> {
    public static final int HAS_NO_MORE = 0;//没有额外数据了
    public static final int LOAD_ERROR = 1;//加载失败
    public static final int HAS_MORE = 2;//有额外数据

    private boolean hasMore;

    private RelativeLayout rl_more_loading, rl_more_error;

    private DefaultAdapter adapter;

    public MoreHolder(DefaultAdapter adapter, boolean hasMore) {
        super();
        this.adapter = adapter;
        this.hasMore=hasMore;
        if(!hasMore){
            setData(HAS_NO_MORE);
        }
    }

    /**
     * 当ViewHolder显示的时候，显示什么样子
     */
    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.load_more);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }

    @Override
    public View getContentView() {
        if(hasMore) {
            loadMore();
        }
        return super.getContentView();
    }

    //请求服务器加载下一批数据
    private void loadMore() {
        //交给adapter，让adapter加载更多数据
        adapter.loadMore();
    }

    @Override
    public void refreshView(Integer data) {
        rl_more_loading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
        rl_more_error.setVisibility(data == LOAD_ERROR ? View.VISIBLE : View.GONE);
    }
}
