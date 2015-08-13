package com.sctbc.googleplay.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.sctbc.googleplay.R;
import com.sctbc.googleplay.domain.AppInfo;
import com.sctbc.googleplay.tools.UiUtils;

/**
 * 作者：ZYJ
 * 时间：2015/8/10 0010 17:34
 */
public class DetailDesHolder extends BaseHolder<AppInfo> implements View.OnClickListener {
    @ViewInject(R.id.des_content)
    private TextView des_content;
    @ViewInject(R.id.des_author)
    private TextView des_author;
    @ViewInject(R.id.des_arrow)
    private ImageView des_arrow;
    @ViewInject(R.id.des_layout)
    private RelativeLayout des_layout;

    @Override
    public View initView() {
        View view = UiUtils.inflate(R.layout.detail_des);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        des_content.setText(data.getDes());
        des_author.setText("作者:" + data.getAuthor());
        des_layout.setOnClickListener(this);

        //des_content 起始高度为7行
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) des_content.getLayoutParams();
        layoutParams.height=getShortMeasureHeight();
        des_content.setLayoutParams(layoutParams);
    }

    boolean flag;//true展开了
    @Override
    public void onClick(View v) {
        expand();
    }

    ScrollView scrollView;

    /**
     * 获取到界面的ScrollView
     * @param view
     * @return
     */
    public ScrollView getScrollView(View view){
        ViewParent parent=view.getParent();
        if(parent instanceof ViewGroup){
            ViewGroup group= (ViewGroup) parent;
            if(group instanceof ScrollView){
                return (ScrollView) group;
            }else{
                return getScrollView(group);
            }
        }else{
            return null;
        }
    }
    private void expand() {
        scrollView=getScrollView(des_layout);
        int startHeight;
        int targetHeight;
        if(!flag){
            flag=true;
            startHeight=getShortMeasureHeight();
            targetHeight=getLongMeasureHeight();
        }else{
            flag=false;
            startHeight=getLongMeasureHeight();
            targetHeight=getShortMeasureHeight();
        }
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) des_content.getLayoutParams();
        ValueAnimator animator=ValueAnimator.ofInt(startHeight,targetHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value=(Integer) animation.getAnimatedValue();
                layoutParams.height=value;
                des_content.setLayoutParams(layoutParams);
                scrollView.scrollTo(0,scrollView.getMeasuredHeight());//让scrollView移动到最下面
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(flag){
                    des_arrow.setImageResource(R.mipmap.arrow_up);
                }else{
                    des_arrow.setImageResource(R.mipmap.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setDuration(500);//设置动画持续时间
        animator.start();
    }

    /**
     * 获取textview7行的高度
     *
     * @return
     */
    public int getShortMeasureHeight() {
        //复制一个新的textview来测量，最好不要在之前的textview测量，这样可能影响其他的代码执行
        TextView tv = new TextView(UiUtils.getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);//设置字体大小14dp
        tv.setMaxLines(7);
        tv.setLines(7);//强制有7行
        int width = des_content.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, width);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.AT_MOST, 1000);
        tv.measure(widthMeasureSpec, heightMeasureSpec);
        return tv.getMeasuredHeight();

    }

    /**
     * 获取textview自己本身的高度
     */
    public int getLongMeasureHeight() {
        int width = des_content.getMeasuredWidth();

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.EXACTLY, width);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.AT_MOST, 1000);
        des_content.measure(widthMeasureSpec, heightMeasureSpec);
        return des_content.getMeasuredHeight();
    }
}
