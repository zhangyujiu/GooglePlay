package com.sctbc.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sctbc.googleplay.holder.BaseHolder;
import com.sctbc.googleplay.holder.MoreHolder;
import com.sctbc.googleplay.manager.ThreadManager;
import com.sctbc.googleplay.tools.UiUtils;

import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/5 0005 11:54
 */
public abstract class DefaultAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {
    protected List<T> datas;
    private static final int DEFAULT_ITEM = 0;
    private static final int MORE_ITEM = 1;
    private ListView lv;

    public DefaultAdapter(List<T> datas,ListView lv) {
        this.datas = datas;
        this.lv=lv;
        lv.setOnItemClickListener(this);
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    /**
     * 根据位置判断当前条目是什么类型
     */
    @Override
    public int getItemViewType(int position) {
        if (position == datas.size()) {
            return MORE_ITEM;
        }
        return getInnerItemViewType(position);
    }
    protected int getInnerItemViewType(int position) {
        return DEFAULT_ITEM;
    }
    /**
     * 当前listview有几种不同的类型
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getCount() {
        return datas.size() + 1;//最后一个条目是加载更多
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;

        switch (getItemViewType(position)) {
            case MORE_ITEM:
                if (convertView == null) {
                    holder = getMoreHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }
                break;
            default:
                if (convertView == null) {
                    holder = getHolder();
                } else {
                    holder = (BaseHolder) convertView.getTag();
                }
                if (position < datas.size()) {
                    holder.setData(datas.get(position));
                }
                break;

        }
        return holder.getContentView();
    }




    protected abstract BaseHolder<T> getHolder();

    private MoreHolder holder;

    public BaseHolder getMoreHolder() {
        if (holder != null) {
            return holder;
        } else {
            holder = new MoreHolder(this,hasMore());
            return holder;
        }

    }

    /**
     * 是否有额外数据
     * @return
     */
    protected boolean hasMore() {
        return true;
    }

    /**
     * 当加载更多条目显示的时候调用该方法
     */
    public void loadMore() {
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                //在子线程加载更多
                final List<T> newData = onLoad();
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newData == null) {
                            //服务器连接失败
                            holder.setData(MoreHolder.LOAD_ERROR);
                        } else if (newData.size() == 0) {
                            //没有更多数据了
                            holder.setData(MoreHolder.HAS_NO_MORE);
                        } else {
                            //加载成功了
                            holder.setData(MoreHolder.HAS_MORE);
                            datas.addAll(newData);
                        }
                        notifyDataSetChanged();//刷新界面
                    }
                });
            }
        });
    }

    /**
     * 加载更多
     * @return
     */
    protected abstract List<T> onLoad();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position=position-lv.getHeaderViewsCount();
       // Toast.makeText(UiUtils.getContext(), "position:"+position, Toast.LENGTH_SHORT).show();
        onInnerItemClick(position);
    }

    protected  void onInnerItemClick(int position){

    }
}
