package com.v.demo.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.v.demo.R;


/**
 * Created by v on 16/7/3.
 */
public class BitmapShadowView_2 extends View {
    private Paint mPaint;
    private Bitmap mBmp,mShadowBmp;
    private int mDx = 10,mDy = 10;
    private float mRadius = 0;
    private int mShadowColor;

    public BitmapShadowView_2(Context context){
        super(context);
    }

    public BitmapShadowView_2(Context context, AttributeSet attrs) throws Exception{
        super(context, attrs);

        init(context,attrs);
    }

    public BitmapShadowView_2(Context context, AttributeSet attrs, int defStyle) throws Exception{
        super(context, attrs, defStyle);
        init(context,attrs);
    }


    private void init(Context context,AttributeSet attrs) throws Exception {
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        /**
         * 提取属性定义
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BitmapShadowView_2);
        int BitmapID = typedArray.getResourceId(R.styleable.BitmapShadowView_2_src,-1);
        if (BitmapID == -1){
            throw new Exception("BitmapShadowView 需要定义Src属性,而且必须是图像");
        }
        mBmp = BitmapFactory.decodeResource(getResources(),BitmapID);
        mDx = typedArray.getInt(R.styleable.BitmapShadowView_2_shadowDx,0);
        mDy = typedArray.getInt(R.styleable.BitmapShadowView_2_shadowDy,0);
        mRadius = typedArray.getFloat(R.styleable.BitmapShadowView_2_shadowRadius,0);
        mShadowColor = typedArray.getColor(R.styleable.BitmapShadowView_2_shadowColor,Color.BLACK);

        typedArray.recycle();
        /**
         * 其它定义
         */
        mPaint = new Paint();
        mShadowBmp = mBmp.extractAlpha();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = mBmp.getWidth();
        int height = mBmp.getHeight();
        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth: width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight: height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth()-mDx;
        int height = width * mBmp.getHeight()/mBmp.getWidth();

        //绘制阴影
        mPaint.setColor(mShadowColor);
        mPaint.setMaskFilter(new BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(mShadowBmp,null,new Rect(mDx,mDy,width,height),mPaint);

        //绘制原图像
        mPaint.setMaskFilter(null);
        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
    }
}
