package com.sctbc.googleplay;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.sctbc.googleplay.domain.AppInfo;
import com.sctbc.googleplay.holder.DetailBottomHolder;
import com.sctbc.googleplay.holder.DetailDesHolder;
import com.sctbc.googleplay.holder.DetailInfoHolder;
import com.sctbc.googleplay.holder.DetailSafeHolder;
import com.sctbc.googleplay.holder.DetailScreenHolder;
import com.sctbc.googleplay.protocol.DetailProtocol;
import com.sctbc.googleplay.tools.UiUtils;
import com.sctbc.googleplay.view.LoadingPage;


public class DetailActivity extends BaseActivity {

    private String packageName;
    private AppInfo data;

    @Override
    protected void initView() {
        super.initView();
        Intent intent=getIntent();
        packageName = intent.getStringExtra("packageName");
        LoadingPage loadingPage=new LoadingPage(this) {
            @Override
            public View createSuccessView() {
                return DetailActivity.this.createSuccessView();
            }

            @Override
            protected LoadResut load() {
                return DetailActivity.this.load();
            }
        };
        loadingPage.show();//必须调用show方法，才会请求服务器加载新的界面
        setContentView(loadingPage);
    }
    private FrameLayout bottom_layout,detail_info,detail_safe,detail_des;
    private HorizontalScrollView detail_screen;
    private DetailInfoHolder detailInfoHolder;
    private DetailScreenHolder screenHolder;
    private DetailSafeHolder safeHolder;
    private DetailDesHolder desHolder;
    private DetailBottomHolder bottomHolder;

    /**
     * 加载成功的界面
     * @return
     */
    private View createSuccessView() {
        View view= UiUtils.inflate(R.layout.activity_detail);
        // 添加信息区域
        bottom_layout=(FrameLayout) view.findViewById(R.id.bottom_layout);
        bottomHolder=new DetailBottomHolder();
        bottomHolder.setData(data);
        bottom_layout.addView(bottomHolder.getContentView());

        //  操作 应用程序信息
        detail_info=(FrameLayout) view.findViewById(R.id.detail_info);
        detailInfoHolder=new DetailInfoHolder();
        detailInfoHolder.setData(data);
        detail_info.addView(detailInfoHolder.getContentView());
        //安全标记
        detail_safe=(FrameLayout) view.findViewById(R.id.detail_safe);
        safeHolder=new DetailSafeHolder();
        safeHolder.setData(data);
        detail_safe.addView(safeHolder.getContentView());

        //描述
        detail_des=(FrameLayout) view.findViewById(R.id.detail_des);
        desHolder=new DetailDesHolder();
        desHolder.setData(data);
        detail_des.addView(desHolder.getContentView());

        // 中间5张图片
        detail_screen=(HorizontalScrollView) view.findViewById(R.id.detail_screen);
        screenHolder=new DetailScreenHolder();
        screenHolder.setData(data);
        detail_screen.addView(screenHolder.getContentView());

        return view;
    }

    /**
     * 请求服务器加载数据
     * @return
     */
    private LoadingPage.LoadResut load() {
        DetailProtocol protocol=new DetailProtocol(packageName);
        data = protocol.load(0);
        System.out.println(packageName);
        if(data==null){
            return LoadingPage.LoadResut.error;
        }else{
            return LoadingPage.LoadResut.success;
        }
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
