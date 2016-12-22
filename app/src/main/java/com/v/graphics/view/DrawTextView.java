package com.v.graphics.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

public class DrawTextView extends View {

	private Context mContext;
	private Paint mPaint;
	private int mDrawType;

	public DrawTextView(Context context) {
		super(context);
		mContext = context;
		mDrawType = 3;
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

		switch (mDrawType) {
			case 0: { // 四线格与基线
				/*
				 	text:要绘制的文字
				 	x：绘制原点x坐标
				 	y：绘制原点y坐标(基线坐标)
				 	paint:用来做画的画笔
					public void drawText(String text, float x, float y, Paint paint)

					1、drawText是中的参数y是基线的位置。
					2、一定要清楚的是，只要x坐标、基线位置、文字大小确定以后，文字的位置就是确定的了。
				 */

				int baseLineY = 200;
				int baseLineX = 0 ;

				//画基线
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

				//写文字
				paint.setColor(Color.GREEN);
				paint.setTextSize(120); //以px为单位
				paint.setTextAlign(Paint.Align.LEFT);
				canvas.drawText("hi, great wall", baseLineX, baseLineY, paint);

			}
			break;
			case 1: { // FontMetrics
				/**
				 * ascent线Y坐标  = baseline线的y坐标 + fontMetric.ascent；
				 * descent线Y坐标 = baseline.y + fontMetric.descent；
				 * top线Y坐标     = baseline.y + fontMetric.top；
				 * bottom线Y坐标  = baseline.y + fontMetric.bottom；
				 */
				int baseLineY = 200;
				int baseLineX = 0 ;

				Paint paint = new Paint();
				//写文字
				paint.setColor(Color.GREEN);
				paint.setTextSize(120); //以 px 为单位
				paint.setTextAlign(Paint.Align.LEFT);
				canvas.drawText("hi, great wall", baseLineX, baseLineY, paint);

				//计算各线在位置
				Paint.FontMetrics fm = paint.getFontMetrics();
				float ascent = baseLineY + fm.ascent;
				float descent = baseLineY + fm.descent;
				float top = baseLineY + fm.top;
				float bottom = baseLineY + fm.bottom;

				//画基线
				paint.setColor(Color.RED);
				canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

				//画top
				paint.setColor(Color.BLUE);
				canvas.drawLine(baseLineX, top, 3000, top, paint);

				//画ascent
				paint.setColor(Color.GREEN);
				canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

				//画descent
				paint.setColor(Color.YELLOW);
				canvas.drawLine(baseLineX, descent, 3000, descent, paint);

				//画bottom
				paint.setColor(Color.RED);
				canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);
			}
			break;
			case 2: { // 所绘文字宽度、高度和最小矩形获取

				String text = "hi, great wall";
				int baseLineY = 200;
				int baseLineX = 0 ;

				//设置paint
				Paint paint = new Paint();
				paint.setTextSize(120); //以px为单位
				paint.setTextAlign(Paint.Align.LEFT);

				//画text所占的区域
				Paint.FontMetricsInt fm = paint.getFontMetricsInt();
				int top = baseLineY + fm.top;
				int bottom = baseLineY + fm.bottom;
				int width = (int)paint.measureText(text);
				Rect rect = new Rect(baseLineX, top, baseLineX+width, bottom);
				paint.setColor(Color.GREEN);
				canvas.drawRect(rect, paint);

				//画最小矩形
				/**
				 * 获取指定字符串所对应的最小矩形，以（0，0）点所在位置为基线
				 * @param text   要测量最小矩形的字符串
				 * @param start  要测量起始字符在字符串中的索引
				 * @param end    所要测量的字符的长度
				 * @param bounds 接收测量结果
				 * public void getTextBounds(String text, int start, int end, Rect bounds);
				 */
				Rect minRect = new Rect();
				paint.getTextBounds(text, 0, text.length(), minRect);
				minRect.top = baseLineY + minRect.top;
				minRect.bottom = baseLineY + minRect.bottom;
				paint.setColor(Color.RED);
				canvas.drawRect(minRect, paint);

				//写文字
				paint.setColor(Color.BLACK);
				canvas.drawText(text, baseLineX, baseLineY, paint);

			}
			break;
			case 3: { // 给定 top 位置写文字

				String text = "hi, great wall";
				int top = 200;
				int baseLineX = 0;

				//设置paint
				Paint paint = new Paint();
				paint.setTextSize(120); //以px为单位
				paint.setTextAlign(Paint.Align.LEFT);

				//画top线
				paint.setColor(Color.YELLOW);
				canvas.drawLine(baseLineX, top, 3000, top, paint);

				//计算出baseLine位置
				Paint.FontMetricsInt fm = paint.getFontMetricsInt();
				int baseLineY = top - fm.top;

				//画基线
				paint.setColor(Color.RED);
				canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

				//写文字
				paint.setColor(Color.GREEN);
				canvas.drawText(text, baseLineX, baseLineY, paint);
			}
			break;
			case 4: { // 给定中间线写文字

				String text = "hi, great wall";
				int center = 200;
				int baseLineX = 0 ;

				//设置paint
				Paint paint = new Paint();
				paint.setTextSize(120); //以px为单位
				paint.setTextAlign(Paint.Align.LEFT);

				//画center线
				paint.setColor(Color.YELLOW);
				canvas.drawLine(baseLineX, center, 3000, center, paint);

				//计算出baseLine位置
				Paint.FontMetricsInt fm = paint.getFontMetricsInt();
				int baseLineY = center + (fm.bottom - fm.top)/2 - fm.bottom;

				//画基线
				paint.setColor(Color.RED);
				canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

				//写文字
				paint.setColor(Color.GREEN);
				canvas.drawText(text, baseLineX, baseLineY, paint);
			}
			break;
			default:
				break;
		} // end of switch

	} // end of onDraw
} // end of class ShapeView
