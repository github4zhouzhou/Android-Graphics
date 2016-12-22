package com.v.graphics.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

public class TransCanvasView extends View {

	private Context mContext;
	private Paint mPaint;
	private int mDrawType;

	public TransCanvasView(Context context) {
		super(context);
		mContext = context;
		mDrawType = 5;
	}

	private Paint generatePaint(int color,Paint.Style style,int width) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setStyle(style);
		paint.setStrokeWidth(width);
		return paint;
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
		}
		//设置画布背景颜色
		canvas.drawRGB(255, 255, 255);

		switch (mDrawType) { // 平移画布
			case 0: {
				/*
				画布变化总结：
					1、每次调用canvas.drawXXXX系列函数来绘图，都会产生一个全新的Canvas画布。
					2、如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作是不可逆的！
					   每次产生的画布的最新位置都是这些操作后的位置。（关于Save()、Restore()的画布可逆问题的后面再讲）
					3、在Canvas与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
				 */
				//构造两个画笔，一个红色，一个绿色
				Paint paintGreen = generatePaint(Color.GREEN, Style.STROKE, 3);
				Paint paintRed = generatePaint(Color.RED, Style.STROKE, 3);

				//构造一个矩形
				Rect rect1 = new Rect(0,0,400,220);

				//在平移画布前用绿色画下边框
				canvas.drawRect(rect1, paintGreen);

				//平移画布后,再用红色边框重新画下这个矩形
				canvas.translate(100, 100);
				canvas.drawRect(rect1, paintRed);
			}
			break;
			case 1: { // 旋转（Rotate）
				/**
				 * void rotate(float degrees)
				 * void rotate (float degrees, float px, float py)
				 * 第一个构造函数直接输入旋转的度数，正数是顺时针旋转，负数指逆时针旋转，它的旋转中心点是原点（0，0）
				 * 第二个构造函数除了度数以外，还可以指定旋转的中心点坐标（px,py）
				 */
				Paint paintGreen = generatePaint(Color.GREEN, Style.FILL, 5);
				Paint painRed   = generatePaint(Color.RED, Style.STROKE, 5);

				Rect rect1 = new Rect(300,10,500,100);
				canvas.drawRect(rect1, painRed); //画出原轮廓

				canvas.rotate(30);//顺时针旋转画布
				canvas.drawRect(rect1, paintGreen);//画出旋转后的矩形
			}
			break;
			case 2: { // 缩放（scale）

				/*
					public void scale (float sx, float sy)
					float sx: 水平方向伸缩的比例，假设原坐标轴的比例为n,不变时为1，
				  			  在变更的X轴密度为n*sx;所以，sx为小数为缩小，sx为整数为放大
					float sy: 垂直方向伸缩的比例，同样，小数为缩小，整数为放大

					public final void scale(float sx, float sy, float px, float py) {
				 		translate(px, py);
				 		scale(sx, sy);
				 		translate(-px, -py);
				 	}
				 */

				//scale 缩放坐标系密度
				Paint paintGreen = generatePaint(Color.GREEN, Style.STROKE, 5);
				Paint paintRed   = generatePaint(Color.RED, Style.STROKE, 5);

				Rect rect1 = new Rect(10,10,200,100);
				canvas.drawRect(rect1, paintGreen);

				canvas.scale(0.5f, 1);
				//canvas.scale(0.5f, 1, 200, 200);
				canvas.drawRect(rect1, paintRed);
			}
			break;
			case 3: { // 扭曲（skew）
				/**
				 * 其实我觉得译成斜切更合适，在PS中的这个功能就差不多叫斜切。但这里还是直译吧，大家都是这个名字。
				 * 看下它的构造函数：
				 * void skew (float sx, float sy)
				 * 参数说明：
				 * float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
				 * float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值，
				 */

				//skew 扭曲
				Paint paintGreen = generatePaint(Color.GREEN, Style.STROKE, 5);
				Paint paintRed   = generatePaint(Color.RED, Style.STROKE, 5);

				Rect rect1 = new Rect(10,10,200,100);
				canvas.drawRect(rect1, paintGreen);
				canvas.skew(1.732f,0); //X轴倾斜60度，Y轴不变
				canvas.drawRect(rect1, paintRed);
			}
			break;
			case 4: { // 裁剪画布（clip系列函数）
				/**
				 * 裁剪画布是利用Clip系列函数，通过与Rect、Path、Region取交、并、差等集合运算来获得最新的画布形状。
				 * 除了调用Save、Restore函数以外，这个操作是不可逆的，一但Canvas画布被裁剪，就不能再被恢复！
				 *
				 * Clip系列函数如下：
				 * boolean	clipPath(Path path)
				 * boolean	clipPath(Path path, Region.Op op)
				 * boolean	clipRect(Rect rect, Region.Op op)
				 * boolean	clipRect(RectF rect, Region.Op op)
				 * boolean	clipRect(int left, int top, int right, int bottom)
				 * boolean	clipRect(float left, float top, float right, float bottom)
				 * boolean	clipRect(RectF rect)
				 * boolean	clipRect(float left, float top, float right, float bottom, Region.Op op)
				 * boolean	clipRect(Rect rect)
				 * boolean	clipRegion(Region region)
				 * boolean	clipRegion(Region region, Region.Op op)
				 */

				canvas.drawColor(Color.RED);
				canvas.clipRect(new Rect(100, 100, 200, 200));
				canvas.drawColor(Color.GREEN);
			}
			break;
			case 5: { // 画布的保存与恢复（save()、restore()）
				/**
				 * 前面我们讲的所有对画布的操作都是不可逆的，这会造成很多麻烦，
				 * 比如，我们为了实现一些效果不得不对画布进行操作，但操作完了，
				 * 画布状态也改变了，这会严重影响到后面的画图操作。
				 * 如果我们能对画布的大小和状态（旋转角度、扭曲等）进行实时保存和恢复就最好了。
				 *
				 * 这两个函数没有任何的参数，很简单。
				 * Save（）：每次调用Save（）函数，都会把当前的画布的状态进行保存，然后放入特定的栈中；
				 * restore（）：每当调用Restore（）函数，就会把栈中最顶层的画布状态取出来，
				 * 			   并按照这个状态恢复当前的画布，并在这个画布上做画。
				 */

				canvas.drawColor(Color.RED);
				//保存的画布大小为全屏幕大小
				canvas.save();

				canvas.clipRect(new Rect(20, 20, 720, 720));
				canvas.drawColor(Color.GREEN);
				canvas.save();

				canvas.clipRect(new Rect(120, 120, 620, 620));
				canvas.drawColor(Color.BLUE);
				canvas.save();

				canvas.clipRect(new Rect(220, 220, 520, 520));
				canvas.drawColor(Color.BLACK);
				canvas.save();

				canvas.clipRect(new Rect(320, 320, 420, 420));
				canvas.drawColor(Color.WHITE);

				//将栈顶的画布状态取出来，作为当前画布，并画成黄色背景
				//canvas.restore();
				//canvas.drawColor(Color.YELLOW);

				//连续出栈三次，将最后一次出栈的Canvas状态作为当前画布，并画成黄色背景
				//canvas.restore();
				//canvas.restore();
				//canvas.restore();
				//canvas.drawColor(Color.YELLOW);
			}
			break;
			case 7:
				break;
			default:
				break;
		} // end of switch

	} // end of onDraw
} // end of class ShapeView
