package com.v.demo.shadow;

import android.content.Context;
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
 * 在xml中使用的这个控件,如果需要查看完整效果,需要在main.xml中把另一个控件给注释掉
 */
public class ExtractAlphaView extends View {
    private Paint mPaint;
    private Bitmap mBitmap,mAlphaBmp;
    public ExtractAlphaView(Context context) {
        super(context);
        init();
    }

    public ExtractAlphaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExtractAlphaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blog12);
        mAlphaBmp = mBitmap.extractAlpha();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = 200;
        int height = width * mAlphaBmp.getWidth()/mAlphaBmp.getHeight();

        //绘制阴影
        mPaint.setColor(Color.RED);
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(mAlphaBmp,null,new Rect(10,10,width,height),mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawBitmap(mAlphaBmp,null,new Rect(10,height+10,width,2*height),mPaint);

        //绘制原图像
        mPaint.setMaskFilter(null);
        canvas.drawBitmap(mBitmap,null,new Rect(0,0,width,height),mPaint);
        canvas.drawBitmap(mBitmap,null,new Rect(0,height,width,2*height),mPaint);
    }
}
