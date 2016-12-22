package com.v.graphics.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class ShapeView extends View {

	private Context mContext;
	private Paint mPaint;
	private int mDrawType;

	public ShapeView(Context context) {
		super(context);
		mContext = context;
		mDrawType = 0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/**
		 * Paint 和 Canvas
		 * 像我们平时画图一样，需要两个工具，纸和笔。Paint就是相当于笔，而Canvas就是纸，这里叫画布。
		 * 所以，凡有跟要要画的东西的设置相关的，比如大小，粗细，画笔颜色，透明度，字体的样式等等，都是在Paint里设置；
		 * 同样，凡是要画出成品的东西，比如圆形，矩形，文字等相关的都是在Canvas里生成
		 */
		if (mPaint == null) {
			mPaint = new Paint();

			mPaint.setAntiAlias(true);   //抗锯齿功能
			mPaint.setColor(Color.RED);  //设置画笔颜色
			mPaint.setStyle(Style.FILL); //设置填充样式  Style.FILL / Style.FILL_AND_STROKE / Style.STROKE
			mPaint.setStrokeWidth(5);    //设置画笔宽度
			mPaint.setShadowLayer(10, 15, 15, Color.GREEN); //设置阴影
		}
		//设置画布背景颜色
		canvas.drawRGB(255, 255, 255);

		mDrawType = 6;

		switch (mDrawType) {
			case 0: {
				//画圆
				canvas.drawCircle(190, 200, 150, mPaint);
			}
				break;
			case 1: { // 画线
				mPaint.setColor(Color.BLUE);
				mPaint.setStyle(Style.STROKE);
				canvas.drawLine(150, 100, 240, 200, mPaint);

				//（（10，10）、（100，100），（200，200）、（400，400））两两连成一条直线
				float []pts = {10,10,100,100,200,200,400,400};
				mPaint.setColor(Color.GREEN);
				canvas.drawLines(pts, mPaint);
			}
				break;
			case 2: { // 画点
				mPaint.setStrokeWidth(15);
				canvas.drawPoint(100, 100, mPaint);


				float []ptsPoint = {10,10,100,100,200,200,400,400};
				/**
				 * float[] pts: 点的合集，与上面直线一直，样式为｛x1,y1,x2,y2,x3,y3,……｝
				 * int offset: 集合中跳过的数值个数，注意不是点的个数！一个点是两个数值；
				 * count: 参与绘制的数值的个数，指pts[]里的数值个数，而不是点的个数，因为一个点是两个数值
				 */
				canvas.drawPoints(ptsPoint, 2, 4, mPaint);
			}
				break;
			case 3: { // 画矩形
				canvas.drawRect(10, 10, 100, 100, mPaint);//直接构造

				RectF rect = new RectF(120, 10, 210, 100);
				canvas.drawRect(rect, mPaint);//使用RectF构造

				Rect rect2 =  new Rect(230, 10, 320, 100);
				canvas.drawRect(rect2, mPaint);//使用Rect构造
			}
				break;
			case 4: { // 画圆角矩形
				RectF rect = new RectF(100, 10, 300, 100);
				/**
				 * RectF rect:要画的矩形
				 * float rx:生成圆角的椭圆的X轴半径
				 * float ry:生成圆角的椭圆的Y轴半径
				 */
				canvas.drawRoundRect(rect, 20, 10, mPaint);
			}
				break;
			case 5: { // 画椭圆
				mPaint.setStyle(Style.STROKE);
				RectF rect = new RectF(100, 10, 300, 100);
				canvas.drawRect(rect, mPaint); //画矩形

				mPaint.setColor(Color.GREEN); //更改画笔颜色
				canvas.drawOval(rect, mPaint); //同一个矩形画椭圆
			}
				break;
			case 6: {

				mPaint.setStyle(Style.STROKE);//填充样式改为描边
				mPaint.setStrokeWidth(5);//设置画笔宽度

				/**
				 * RectF oval:生成椭圆的矩形
				 * float startAngle：弧开始的角度，以X轴正方向为0度
				 * float sweepAngle：弧持续的角度
				 * boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
				 */
				RectF rect1 = new RectF(100, 10, 300, 100);
				canvas.drawArc(rect1, 0, 90, true, mPaint);

				RectF rect2 = new RectF(400, 10, 600, 100);
				canvas.drawArc(rect2, 0, 90, false, mPaint);

				// 改变风格，再看一下效果
				mPaint.setStyle(Style.FILL);

				RectF rect3 = new RectF(100, 210, 300, 300);
				canvas.drawArc(rect3, 0, 90, true, mPaint);

				RectF rect4 = new RectF(400, 210, 600, 300);
				canvas.drawArc(rect4, 0, 90, false, mPaint);
			}
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			default:
				break;
		} // end of switch

	} // end of onDraw
} // end of class ShapeView
