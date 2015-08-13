package com.sctbc.googleplay.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sctbc.googleplay.adapter.DefaultAdapter;
import com.sctbc.googleplay.domain.CategoryInfo;
import com.sctbc.googleplay.holder.BaseHolder;
import com.sctbc.googleplay.holder.CategoryContentHolder;
import com.sctbc.googleplay.holder.CategoryTitleHolder;
import com.sctbc.googleplay.protocol.CategoryProtocol;
import com.sctbc.googleplay.tools.UiUtils;
import com.sctbc.googleplay.view.BaseListView;
import com.sctbc.googleplay.view.LoadingPage;

import java.util.List;

/**
 * 分类页面
 * 作者：ZYJ
 * 时间：2015/8/1 0001 14:16
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfo> datas;
    private static final int TITLE_ITEM=2;

    //创建成功的界面
    @Override
    public View createSuccessView() {
        BaseListView lv=new BaseListView(UiUtils.getContext());
        lv.setAdapter(new CategoryAdapter(datas,lv));
        return lv;
    }
    private class CategoryAdapter extends DefaultAdapter<CategoryInfo>{

        private int position;
        public CategoryAdapter(List<CategoryInfo> datas, ListView lv) {
            super(datas, lv);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;//又额外多了一种条目类型  1.标题 2.内容 3.加载更多（不显示）
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.position=position;
            return super.getView(position, convertView, parent);
        }

        @Override
        protected int getInnerItemViewType(int position) {
            if(datas.get(position).isTitile()){
                return TITLE_ITEM;
            }else {
                return super.getInnerItemViewType(position);
            }
        }

        @Override
        protected BaseHolder<CategoryInfo> getHolder() {
            if(!datas.get(position).isTitile()) {
                return new CategoryContentHolder();
            }else{
                return new CategoryTitleHolder();
            }
        }

        @Override
        protected boolean hasMore() {//当前方法如果为false，onLoad就不会被调用了
            return false;
        }

        @Override
        protected List<CategoryInfo> onLoad() {
            return null;
        }
    }

    // 请求服务器
    @Override
    protected LoadingPage.LoadResut load() {
        CategoryProtocol protocol=new CategoryProtocol();
        datas = protocol.load(0);
        return checkData(datas);
    }
}
