package com.v.graphics.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by v on 2016/12/13.
 */

public class QuadView extends View {

    private Paint mPaint;
    private int mItemWaveLength = 1000;
    private int dx;

    private int mDrawType;
    private Path mPath = new Path();
    private float mPreX, mPreY;

    public QuadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mDrawType = 4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                mPath.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY = event.getY();
                invalidate();

                //mPath.lineTo(event.getX(), event.getY());
                //postInvalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mDrawType) {
            case 0: {
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(3);

                Path path = new Path();
                path.moveTo(100,300);
                path.quadTo(200,200,300,300);
                path.quadTo(400,400,500,300);

                canvas.drawPath(path,paint);
            }
            break;
            case 1: { // 手指轨迹
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStyle(Paint.Style.STROKE);

                canvas.drawPath(mPath, paint);
            }
            break;
            case 2: { // 手指轨迹, 使用 Path.quadTo() 函数实现过渡
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(2);

                canvas.drawPath(mPath, paint);
            }
            break;
            case 3: { // rQuadTo 和 quad
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.GREEN);

                Path path = new Path();
                path.moveTo(100,300);
                /**
                 * 它们是一个效果
                 *
                 * path.quadTo(200,200,300,300);
                 * path.quadTo(400,400,500,300);
                 */
                path.rQuadTo(100,-100,200,0);
                path.rQuadTo(100,100,200,0);
                canvas.drawPath(path,paint);
            }
            break;
            case 4: { // 实现波浪效果

                mPath.reset();
                int originY = 300;
                int halfWaveLen = mItemWaveLength/2;
                mPath.moveTo(-mItemWaveLength+dx, originY);
                for (int i = -mItemWaveLength;
                     i <= getWidth() + mItemWaveLength; i+=mItemWaveLength){
                    mPath.rQuadTo(halfWaveLen/2,-100,halfWaveLen,0);
                    mPath.rQuadTo(halfWaveLen/2,100,halfWaveLen,0);
                }
                mPath.lineTo(getWidth(), getHeight());
                mPath.lineTo(0, getHeight());
                mPath.close();

                canvas.drawPath(mPath,mPaint);
            }
            break;
            case 5: {

            }
            break;
            default:
                break;
        } // end of switch

    } // end of onDraw

    public void reset(){
        mPath.reset();
        invalidate();
    }

    public void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

} // end of class QuadView
