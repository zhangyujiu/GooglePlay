package com.sctbc.googleplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.BitmapUtils;
import com.sctbc.googleplay.tools.BitmapHelper;
import com.sctbc.googleplay.tools.ViewUtils;
import com.sctbc.googleplay.view.LoadingPage;

import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/3 0003 12:21
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;
    protected BitmapUtils bitmapUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bitmapUtils = BitmapHelper.getBitmapUtils();
        if (loadingPage == null) {//之前的framLayout已经记录了一个爹了，是之前的ViewPager
            loadingPage = new LoadingPage(getActivity()){

                @Override
                public View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResut load() {
                    return BaseFragment.this.load();
                }
            };
        } else {
            ViewUtils.removeParent(loadingPage);//先移除framLayout之前的爹
        }

        return loadingPage;//拿到当前的ViewPager，添加这个framLayout
    }

    /**
     * 创建成功的界面
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 请求服务器
     * @return
     */
    protected abstract LoadingPage.LoadResut load();

    public void show(){
        if(loadingPage!=null){
            loadingPage.show();
        }
    }
    /**校验返回的数据*/
    public LoadingPage.LoadResut checkData(List datas) {
        if (datas == null) {
            return LoadingPage.LoadResut.error;
        } else {
            if (datas.size() == 0) {
                return LoadingPage.LoadResut.empty;
            } else {
                return LoadingPage.LoadResut.success;
            }
        }
    }
}
