package com.sctbc.googleplay;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sctbc.googleplay.fragment.BaseFragment;
import com.sctbc.googleplay.fragment.FragmentFactory;
import com.sctbc.googleplay.holder.MenuHolder;
import com.sctbc.googleplay.tools.UiUtils;


public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager mViewPager;
    private PagerTabStrip pager_tab_strip;
    private String[] tab_names;//标签的名字
    private FrameLayout fl_menu;//菜单的根布局

    @Override
    protected void init() {
        super.init();
        tab_names = UiUtils.getStringArray(R.array.tab_names);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        pager_tab_strip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);

        //设置标签下划线的颜色
        pager_tab_strip.setTabIndicatorColor(getResources().getColor(R.color.indicatorcolor));
        mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //得到fragment
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.show();
            }
        });
        //添加菜单数据
        fl_menu= (FrameLayout) findViewById(R.id.fl_menu);
        MenuHolder holder=new MenuHolder(this);
//        holder.setData();
        fl_menu.addView(holder.getContentView());
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();

        ActionBar actionBar = getSupportActionBar();
        //android studio中需要手动设置actionbar图标，否则不显示
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setIcon(R.mipmap.ic_launcher);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawer_am);//设置返回按钮图标


        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.mipmap.ic_drawer_am, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(MainActivity.this, "抽屉关闭了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(MainActivity.this, "抽屉打开了", Toast.LENGTH_SHORT).show();
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
        //让开关和actionbar建立关系
        drawerToggle.syncState();
    }


    private class MainAdapter extends FragmentStatePagerAdapter {

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        //每个条目返回的fragmnet
        @Override
        public Fragment getItem(int position) {
           return FragmentFactory.createFragment(position);
        }

        //一个有多少个条目
        @Override
        public int getCount() {
            return tab_names.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tab_names[position];
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (Build.VERSION.SDK_INT > 11) {//判断当前手机版本
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                    .getActionView();
            searchView.setOnQueryTextListener(this);//  搜索的监听
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) | super.onOptionsItemSelected(item);
    }

    //当搜索提交的时候
    @Override
    public boolean onQueryTextSubmit(String query) {
//        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return true;
    }

    //当搜索文本发生变化的时候
    @Override
    public boolean onQueryTextChange(String newText) {
//        Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
        return true;
    }
}
