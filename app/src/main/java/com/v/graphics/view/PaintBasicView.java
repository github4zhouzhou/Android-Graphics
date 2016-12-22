package com.v.graphics.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 总结：
 * Path 类很有用。可以实现各种图形
 * PathEffect 圆角、虚线、离散路径、路径装饰路径
 * 子类：CornerPathEffect、DashPathEffect、DiscretePathEffect、
 * PathDashPathEffect、ComposePathEffect、SumPathEffect
 */

public class PaintBasicView extends View {

	private int dx = 0 ;
	private int dashDx = 10;
	private int mDrawType = 8;

	public PaintBasicView(Context context) {
		super(context);
	}

	public PaintBasicView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		switch (mDrawType) {
			case 0: {
				/**
				 * StrokeCap示例
				 */
				drawStrokeCap(canvas);
			}
			break;
			case 1: {
				/**
				 * stokeJoin示例
				 */
				drawStrokeJoin(canvas);
			}
			break;
			case 2: {
				/**
				 * CornerPathEffect示例
				 */
				drawCornerPathEffect(canvas);
			}
			break;
			case 3: {
				/**
				 * CornerPathEffect DEMO曲线
				 */
				drawCornerPathEffectDemo(canvas);
			}
			break;
			case 4: {
				/**
				 * DashPathEffect DEMO 效果
				 */
				drawDashPathEffectDemo(canvas);
			}
			break;
			case 5: {
				/**
				 * DiscretePathEffect DEMO效果
				 */
				drawDiscretePathEffectDemo(canvas);
			}
			break;
			case 6: {
				/**
				 * PathDashPathEffect效果
				 */
				drawPathDashPathEffect(canvas);
			}
			break;
			case 7: {
				/**
				 * PathDashPathEffect DEMO效果
				 */
				drawPathDashPathEffectDemo(canvas);
			}
			break;
			case 8: {
				/**
				 * ComposePathEffect与SumPathEffect
				 */
				drawComposePathEffectDemo(canvas);
			}
			break;
			case 9: {
				/**
				 * SubpixelText Demo
				 */
				drawSubpixelText(canvas);
			}
			break;
			case 10: {
				drawDashPathEffect(canvas);
			}
			break;
			case 11: {
				drawPathDashPathEffect(canvas);
				dx++;
				invalidate();
			}
			break;
			default:
				break;
		} // end of switch

	} // end of onDraw


	private void drawStrokeCap(Canvas canvas){
		Paint paint = new Paint();
		paint.setStrokeWidth(80);
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);

		paint.setStrokeCap(Paint.Cap.BUTT);
		canvas.drawLine(100,200,400,200,paint);

		paint.setStrokeCap(Paint.Cap.SQUARE);
		canvas.drawLine(100,400,400,400,paint);

		paint.setStrokeCap(Paint.Cap.ROUND);
		canvas.drawLine(100,600,400,600,paint);

		//垂直画出x=100这条线
		paint.reset();
		paint.setStrokeWidth(2);
		paint.setColor(Color.RED);
		canvas.drawLine(100,50,100,750,paint);
	}


	private void drawStrokeJoin(Canvas canvas){
		Paint paint = new Paint();
		paint.setStrokeWidth(40);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);

		Path path  = new Path();
		path.moveTo(100,100);
		path.lineTo(450,100);
		path.lineTo(100,300);
		paint.setStrokeJoin(Paint.Join.MITER);
		canvas.drawPath(path,paint);

		path.moveTo(100,400);
		path.lineTo(450,400);
		path.lineTo(100,600);
		paint.setStrokeJoin(Paint.Join.BEVEL);
		canvas.drawPath(path,paint);

		path.moveTo(100,700);
		path.lineTo(450,700);
		path.lineTo(100,900);
		paint.setStrokeJoin(Paint.Join.ROUND);
		canvas.drawPath(path,paint);
	}


	private Path getPath(){
		Path path = new Path();
		// 定义路径的起点
		path.moveTo(0, 0);

		// 定义路径的各个点
		for (int i = 0; i <= 40; i++) {
			path.lineTo(i*35, (float) (Math.random() * 150));
		}
		return path;
	}

	private Paint getPaint(){
		Paint paint = new Paint();
		paint.setStrokeWidth(4);
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		return paint;
	}


	/**
	 * CornerPathEffect
	 * @param canvas
	 */
	private void drawCornerPathEffectDemo(Canvas canvas){
		Paint paint = getPaint();
		Path path = getPath();
		canvas.drawPath(path,paint);

		paint.setPathEffect(new CornerPathEffect(200));
		canvas.save();
		canvas.translate(0,150);
		canvas.drawPath(path,paint);
	}


	private void drawCornerPathEffect(Canvas canvas){
		Paint paint = getPaint();
		Path path = new Path();
		path.moveTo(100,600);
		path.lineTo(400,100);
		path.lineTo(700,900);

		canvas.drawPath(path,paint);
		paint.setColor(Color.RED);
		paint.setPathEffect(new CornerPathEffect(100));
		canvas.drawPath(path,paint);

		paint.setPathEffect(new CornerPathEffect(200));
		paint.setColor(Color.YELLOW);
		canvas.drawPath(path,paint);
	}

	private void drawDashPathEffectDemo(Canvas canvas){
		Paint paint = getPaint();
		Path path = getPath();

		canvas.translate(0,100);
		/**
		 * 长度必须大于等于2；因为必须有一个实线段和一个空线段来组成虚线。
		 * 个数必须为偶数，如果是基数，最后一个数字将被忽略；这个很好理解，
		 * 因为一组虚线的组成必然是一个实线和一个空线成对组成的。
         * 实线：15，16
         * 虚线：20，17（看不见的地方）
		 */
		paint.setPathEffect(new DashPathEffect(new float[]{15,20,16,17},0));
		canvas.drawPath(path,paint);
	}


	private void drawDiscretePathEffectDemo(Canvas canvas){
		Paint paint = getPaint();
		Path path = getPath();

		canvas.drawPath(path,paint);
		/**
		 * 把原有的路线,在指定的间距处插入一个突刺
		 * 第一个这些突出的“杂点”的间距,值越小间距越短,越密集
		 * 第二个是突出距离
		 */
		canvas.translate(0,200);
		paint.setPathEffect(new DiscretePathEffect(2,5));
		canvas.drawPath(path,paint);

		canvas.translate(0,200);
		paint.setPathEffect(new DiscretePathEffect(6,5));
		canvas.drawPath(path,paint);


		canvas.translate(0,200);
		paint.setPathEffect(new DiscretePathEffect(6,15));
		canvas.drawPath(path,paint);
	}

	private void drawPathDashPathEffectDemo(Canvas canvas){
		Paint paint = getPaint();

		Path path = getPath();
		canvas.drawPath(path,paint);

		canvas.translate(0,200);
		paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.MORPH));
		canvas.drawPath(path,paint);

		canvas.translate(0,200);
		paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.ROTATE));
		canvas.drawPath(path,paint);

		canvas.translate(0,200);
		paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.TRANSLATE));
		canvas.drawPath(path,paint);
	}


	private void drawPathDashPathEffect(Canvas canvas){
		Paint paint = getPaint();

		Path path  = new Path();
		path.moveTo(100,600);
		path.lineTo(400,150);
		path.lineTo(700,900);
		canvas.drawPath(path,paint);
		canvas.drawPath(path,paint);

		canvas.translate(0,200);
		/**
		 * 利用以另一个路径为单位,延着路径盖章.相当于PS的印章工具
         * Path shape:表示印章路径，比如我们下面示例中的三角形加右上角一个点；
         * float advance：表示两个印章路径间的距离,很容易理解，印章间距离越大，间距就越大。
         * float phase：路径绘制偏移距离，与上面DashPathEffect中的float phase参数意义相同。
         * Style style：表示在遇到转角时，如何操作印章以使转角平滑过渡，
         * 取值有：Style.ROTATE，Style.MORPH，Style.TRANSLATE;
         * Style.ROTATE表示通过旋转印章来过渡转角；
         * Style.MORPH表示通过变形印章来过渡转角；
         * Style.TRANSLATE表示通过位移来过渡转角。
         * 这三个效果的具体意义，会通过具体示例来分别讲解。
		 */
		paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.MORPH));
		canvas.drawPath(path,paint);
	}

	private Path getStampPath(){
		Path path  = new Path();
		path.moveTo(0,20);
		path.lineTo(10,0);
		path.lineTo(20,20);
		path.close();

		path.addCircle(0,0,3, Path.Direction.CCW);

		return path;
	}


	private void drawComposePathEffectDemo(Canvas canvas){
		//画原始路径
		Paint paint = getPaint();
		Path path = getPath();
		canvas.drawPath(path,paint);

		//仅应用圆角特效的路径
		canvas.translate(0,200);
		CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
		paint.setPathEffect(cornerPathEffect);
		canvas.drawPath(path,paint);

		//仅应用虚线特效的路径
		canvas.translate(0,200);
		DashPathEffect dashPathEffect = new DashPathEffect(new float[]{2,5,10,10},0);
		paint.setPathEffect(dashPathEffect);
		canvas.drawPath(path,paint);

		//利用ComposePathEffect先应用圆角特效,再应用虚线特效
		canvas.translate(0,200);
		ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect,cornerPathEffect);
		paint.setPathEffect(composePathEffect);
		canvas.drawPath(path,paint);

		//利用SumPathEffect,分别将圆角特效应用于原始路径,然后将生成的两条特效路径合并
		canvas.translate(0,200);
		paint.setStyle(Paint.Style.STROKE);
		SumPathEffect sumPathEffect = new SumPathEffect(cornerPathEffect,dashPathEffect);
		paint.setPathEffect(sumPathEffect);
		canvas.drawPath(path,paint);

	}

	private void drawSubpixelText(Canvas canvas){

		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		String text = "乌龟&梦想";
		paint.setTextSize(200);

		paint.setSubpixelText(false);
		canvas.drawText(text,0,200,paint);

		canvas.translate(0,300);
		paint.setSubpixelText(true);
		canvas.drawText(text,0,200,paint);
	}

	private void drawDashPathEffect(Canvas canvas){
		Paint paint = getPaint();
		Path path = new Path();
		path.moveTo(100,600);
		path.lineTo(400,100);
		path.lineTo(700,900);

		canvas.drawPath(path,paint);
		paint.setColor(Color.RED);

		//使用DashPathEffect画线段
		paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},0));
		canvas.translate(0,100);
		canvas.drawPath(path,paint);

		//画同一条线段,偏移值为15
		paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},dashDx));
		paint.setColor(Color.YELLOW);
		canvas.translate(0,100);
		canvas.drawPath(path,paint);
	}


	public void startAnim(){
		ValueAnimator valueAnimator = ValueAnimator.ofInt(0,230);
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.setDuration(1000);
		valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				dashDx = (int)animation.getAnimatedValue();
				postInvalidate();
			}
		});
		valueAnimator.start();
	}


} // end of class PaintBasicView
