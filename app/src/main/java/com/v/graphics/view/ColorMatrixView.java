package com.v.graphics.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.v.graphics.R;

/**
 * 总结：ColorMatrix 就是通过矩阵相乘来改变 RGBA 值的。
 * 为了方便，它提供了一些常用方法。
 * 平移、缩放、旋转等其实都可以用基础矩阵相乘法实现。
 * colorMatrix.setScale(1,1.3f,1,1) 等价于
 * 1， 0，  0， 0， 0，
 * 0， 1.3，0， 0， 0，
 * 0， 0，  1， 0， 0，
 * 0， 0，  0， 1， 0，
 */

public class ColorMatrixView extends View {

	private Paint mPaint = new Paint();
	private Bitmap bitmap;// 位图
	private int mDrawType = 0;

	public ColorMatrixView(Context context) {
		super(context);
		mPaint.setAntiAlias(true);
		// 获取位图
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);
	}

	public ColorMatrixView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint.setAntiAlias(true);
		// 获取位图
		bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		switch (mDrawType) {
			case 0: {
				drawRect(canvas);
			}
			break;
			case 1: {
				drawBitmap(canvas);
			}
			break;
			default:
				break;
		} // end of switch

	} // end of onDraw

	private void drawRect(Canvas canvas){
		mPaint.setARGB(255,200,100,100);
		// 绘制原始位图
		canvas.drawRect(0,0,500,600,mPaint);

		canvas.translate(550,0);
		// 生成色彩矩阵
		ColorMatrix colorMatrix = new ColorMatrix(new float[]{
				0, 0, 0, 0, 0,
				0, 0, 0, 0, 0,
				0, 0, 1, 0, 0,
				0, 0, 0, 1, 0,
		});
		mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
		canvas.drawRect(0,0,500,600,mPaint);
	}

	private void drawBitmap(Canvas canvas){
		canvas.drawBitmap(bitmap, null, new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

		canvas.save();
		canvas.translate(510, 0);
		// 生成色彩矩阵
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0,       0,    0, 1, 0,
//        });
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setScale(1,1.3f,1,1);
		mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));


		canvas.drawBitmap(bitmap, null, new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

		canvas.restore();
//        canvas.translate(0,500);
//        ColorMatrix colorMatrix2 = new ColorMatrix(new float[]{
//                0.3086f, 0.6094f, 0.0820f, 0, 0,
//                0.3086f, 0.6094f, 0.0820f, 0, 0,
//                0.3086f, 0.6094f, 0.0820f, 0, 0,
//                0    , 0    , 0    , 1, 0
//        });
//
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
//        canvas.drawBitmap(bitmap, null, new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);
//
//        canvas.translate(510,0);
//        ColorMatrix colorMatrix3 = new ColorMatrix(new float[]{
//                0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0,
//                0, 0, 1, 0, 0,
//                0, 0, 0, 1, 0,
//        });
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix3));
//        canvas.drawBitmap(bitmap, null, new Rect(0, 0, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);

	}

} // end of class ColorMatrixView
