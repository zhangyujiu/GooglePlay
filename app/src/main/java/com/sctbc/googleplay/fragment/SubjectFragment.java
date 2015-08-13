package com.sctbc.googleplay.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sctbc.googleplay.R;
import com.sctbc.googleplay.adapter.DefaultAdapter;
import com.sctbc.googleplay.domain.SubjectInfo;
import com.sctbc.googleplay.holder.BaseHolder;
import com.sctbc.googleplay.http.HttpHelper;
import com.sctbc.googleplay.protocol.SubjectProtocol;
import com.sctbc.googleplay.tools.UiUtils;
import com.sctbc.googleplay.view.BaseListView;
import com.sctbc.googleplay.view.LoadingPage;

import java.util.List;

/**
 * 专题界面
 * 作者：ZYJ
 * 时间：2015/8/1 0001 14:16
 */
public class SubjectFragment extends BaseFragment {

    private List<SubjectInfo> datas;

    @Override
    public View createSuccessView() {
        BaseListView listView=new BaseListView(UiUtils.getContext());
        listView.setAdapter(new SubjectAdapter(datas,listView));
        return listView;
    }
    private class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> datas,ListView lv) {
            super(datas,lv);
        }

        @Override
        protected BaseHolder<SubjectInfo> getHolder() {
            return new SubjectHolder();
        }

        @Override
        protected void onInnerItemClick(int position) {
            super.onInnerItemClick(position);
            Toast.makeText(UiUtils.getContext(), datas.get(position).getDes(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<SubjectInfo> onLoad() {
            SubjectProtocol protocol=new SubjectProtocol();
            List<SubjectInfo> load=protocol.load(datas.size());
            datas.addAll(load);
            return load;
        }

    }
    class SubjectHolder extends BaseHolder<SubjectInfo>{
        ImageView item_icon;
        TextView item_txt;

        @Override
        public View initView() {
            View contentView=UiUtils.inflate(R.layout.item_subject);
            this.item_icon=(ImageView) contentView.findViewById(R.id.item_icon);
            this.item_txt=(TextView) contentView.findViewById(R.id.item_txt);
            return contentView;
        }

        @Override
        public void refreshView(SubjectInfo data) {
            this.item_txt.setText(data.getDes());
            bitmapUtils.display(this.item_icon, HttpHelper.URL + "image?name=" + data.getUrl());
        }
    }

    @Override
    protected LoadingPage.LoadResut load() {
        SubjectProtocol protocol=new SubjectProtocol();
        datas = protocol.load(0);
        return checkData(datas);
    }
}
