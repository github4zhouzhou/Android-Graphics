package com.v.demo.shadow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.v.demo.R;


/**
 * Created by v on 16/7/2.
 */
public class BitmapShadowView_1 extends View {
    private Paint mPaint;
    private Bitmap mBitmap,mAlphaBmp;

    public BitmapShadowView_1(Context context) {
        super(context);
        init();
    }

    public BitmapShadowView_1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BitmapShadowView_1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blog12);
        mPaint.setColor(Color.RED);
        mAlphaBmp = mBitmap.extractAlpha(mPaint,null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = 500;
        int height = width * mAlphaBmp.getWidth()/mAlphaBmp.getHeight();
        mPaint.setColor(Color.GREEN);
        canvas.drawBitmap(mAlphaBmp,null,new Rect(0,0,width,height),mPaint);


//        canvas.drawBitmap(mBitmap.extractAlpha(), 0, 0, mPaint);
//        canvas.drawBitmap(mAlphaBmp, 0, 0, mPaint);

//        canvas.drawBitmap(bitmap,null,new Rect(100,100,100+width,100+height),mPaint);


    }
}
