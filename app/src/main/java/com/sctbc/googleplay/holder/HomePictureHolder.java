package com.sctbc.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.sctbc.googleplay.http.HttpHelper;
import com.sctbc.googleplay.tools.UiUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：ZYJ
 * 时间：2015/8/7 0007 15:32
 */
public class HomePictureHolder extends BaseHolder<List<String>> {

    private ViewPager viewPager;
    private List<String> datas;
    private AutoRunTask runTask;

    //当new HomePictureHolder()就会调用该方法
    @Override
    public View initView() {
        viewPager = new ViewPager(UiUtils.getContext());
        viewPager.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,UiUtils.dip2px(134)));
        return viewPager;
    }

    @Override
    public void refreshView(List<String> datas) {
        this.datas = datas;
        viewPager.setAdapter(new HomePagerAdapter());
        viewPager.setCurrentItem(1000*datas.size());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        runTask.stop();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        runTask.start();
                        break;
                }
                return false;//ViewPager触摸事件返回值要是false
            }
        });
        runTask = new AutoRunTask();
        runTask.start();

    }
     boolean flag;
     public class AutoRunTask implements Runnable{
         @Override
         public void run() {
             if(flag) {
                 UiUtils.cancel(this);//取消之前任务
                 int currentItem = viewPager.getCurrentItem();
                 currentItem++;
                 viewPager.setCurrentItem(currentItem);
                 //延时执行当前任务
                 UiUtils.postDelayed(this,3000);//递归调用
             }
         }
         public void start(){
             if(!flag){
                 UiUtils.cancel(this);
                 flag=true;
                 UiUtils.postDelayed(this,3000);
             }
         }
         public void stop(){
             if(flag){
                 flag=false;
                 UiUtils.cancel(this);
             }
         }
     }
    class HomePagerAdapter extends PagerAdapter {

        LinkedList<ImageView> convertView=new LinkedList<>();
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view= (ImageView) object;
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int index=position%datas.size();
            ImageView iv;
            if(convertView.size()>0){
                iv=convertView.remove(0);
            }else {
                iv = new ImageView(UiUtils.getContext());
            }
            bitmapUtils.display(iv, HttpHelper.URL+"image?name="+datas.get(index));
            container.addView(iv);  //加载的view对象
            return iv; // 返回的对象
        }
    }
}
