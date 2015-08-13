package com.sctbc.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 作者：ZYJ
 * 时间：2015/8/12 0012 11:33
 */
public class RatioLayout extends FrameLayout {
    //按照宽高比例去显示
    private float ratio;//比例值

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        float ratio=attrs.getAttributeFloatValue("http://schemas.android.com/apk/com.sctbc.googleplay","ratio",2.43f);
        setRatio(ratio);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //测量当前布局

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //widthMeasureSpec 宽度的规则包含了两部分——模式、值
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);//模式
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);//宽度的大小
        int width=widthSize-getPaddingLeft()-getPaddingRight();//去掉左右两边的padding

        int heightMode=MeasureSpec.getMode(heightMeasureSpec);//模式
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);//高度的大小
        int height=heightSize-getPaddingTop()-getPaddingBottom();//去掉上下两边的padding

        if(widthMode==MeasureSpec.EXACTLY&&heightMode!=MeasureSpec.EXACTLY){
            //修正一下高度的值  高度=宽度/比例
            height= (int) (width/ratio+0.5);
        }else if(widthMode!=MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
            width= (int) (height*ratio+0.5);
        }

        //重新制作了新的规则
        widthMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,width+getPaddingLeft()+getPaddingRight());
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,height+getPaddingTop()+getPaddingBottom());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
