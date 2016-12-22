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
 * Created by v on 16/6/13.
 */
public class ShadowLayerView extends View {
    private Paint mPaint = new Paint();
    private Bitmap mDogBmp;
    private int mRadius = 1,mDx = 10,mDy = 10;
    private boolean mSetShadow = true;
    public ShadowLayerView(Context context) {
        super(context);
        init();
    }

    public ShadowLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShadowLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        setLayerType( LAYER_TYPE_SOFTWARE , null);
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(25);
        mDogBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
    }


    public void changeRadius() {
        mRadius++;
        postInvalidate();
    }

    public void changeDx() {
        mDx+=5;
        postInvalidate();
    }

    public void changeDy() {
        mDy+=5;
        postInvalidate();
    }

    public void clearShadow(){
        mSetShadow = false;
        postInvalidate();
    }

    public void showShadow(){
        mSetShadow = true;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSetShadow) {
            mPaint.setShadowLayer(mRadius, mDx, mDy, Color.GRAY);
        }else {
            mPaint.clearShadowLayer();
        }

        canvas.drawText("启舰大SB",100,100,mPaint);

        canvas.drawCircle(200,200,50,mPaint);

        canvas.drawBitmap(mDogBmp,null,new Rect(200,300,200+mDogBmp.getWidth(),300+mDogBmp.getHeight()),mPaint);
    }
}
