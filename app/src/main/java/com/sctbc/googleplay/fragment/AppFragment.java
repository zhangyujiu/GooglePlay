package com.sctbc.googleplay.fragment;

import android.view.View;

import com.sctbc.googleplay.adapter.ListBaseAdapter;
import com.sctbc.googleplay.domain.AppInfo;
import com.sctbc.googleplay.protocol.AppProtocol;
import com.sctbc.googleplay.tools.UiUtils;
import com.sctbc.googleplay.view.BaseListView;
import com.sctbc.googleplay.view.LoadingPage;

import java.util.List;

/**
 * 应用页面
 * 作者：ZYJ
 * 时间：2015/8/1 0001 14:16
 */
public class AppFragment extends BaseFragment {


    private List<AppInfo> datas;

    /**
     * 当加载成功的时候显示的界面
     * @return
     */
    @Override
    public View createSuccessView() {
        BaseListView listView=new BaseListView(UiUtils.getContext());
        listView.setAdapter(new ListBaseAdapter(datas,listView) {
            @Override
            protected List<AppInfo> onLoad() {
                AppProtocol protocol=new AppProtocol();
                List<AppInfo> load=protocol.load(datas.size());
                datas.addAll(load);
                return load;
            }
        });
        return listView;
    }


    /**
     * 请求服务器，获取服务器的数据
     * @return
     */
    @Override
    protected LoadingPage.LoadResut load() {
        AppProtocol protocol=new AppProtocol();
        datas = protocol.load(0);
        return checkData(datas);
    }
}
