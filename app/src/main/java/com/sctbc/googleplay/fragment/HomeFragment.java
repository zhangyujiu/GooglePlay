package com.sctbc.googleplay.fragment;

import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.sctbc.googleplay.R;
import com.sctbc.googleplay.adapter.ListBaseAdapter;
import com.sctbc.googleplay.domain.AppInfo;
import com.sctbc.googleplay.holder.HomePictureHolder;
import com.sctbc.googleplay.protocol.HomeProtocol;
import com.sctbc.googleplay.view.BaseListView;
import com.sctbc.googleplay.view.LoadingPage;

import java.util.List;

/**
 * 主页面
 * 作者：ZYJ
 * 时间：2015/8/1 0001 14:16
 */
public class HomeFragment extends BaseFragment {

    private List<AppInfo> datas;
    private List<String> pictures;//顶部ViewPager显示的图片数据


    //当Fragment挂载activity的时候调用
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    /*创建成功的界面*/
    public View createSuccessView() {
        BaseListView listView=new BaseListView(getActivity());
        HomePictureHolder holder=new HomePictureHolder();
        holder.setData(pictures);
        View contentView=holder.getContentView();
        listView.addHeaderView(contentView);
        listView.setAdapter(new ListBaseAdapter(datas,listView) {
            @Override
            protected List<AppInfo> onLoad() {
                HomeProtocol protocol = new HomeProtocol();
                List<AppInfo> load=protocol.load(datas.size());
                datas.addAll(load);
                return load;
            }

        });

        bitmapUtils.configDefaultLoadingImage(R.mipmap.ic_launcher);//设置加载中显示的图片
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);//设置如果图片加载失败显示的图片
        //第二个参数 慢慢滑动的时候是否加载图片 false加载
        //第三个参数 飞速滑动的时候是否加载图片 false加载
        listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,false,true));
        return listView;
    }

    public LoadingPage.LoadResut load() {
        HomeProtocol protocol = new HomeProtocol();
        datas = protocol.load(0);
        pictures = protocol.getPictures();
        return checkData(datas);
    }

}
