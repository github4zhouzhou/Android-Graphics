package com.v.graphics.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.view.View;

public class RegionView extends View {

	private Context mContext;
	private Paint mPaint;
	private int mDrawType;

	public RegionView(Context context) {
		super(context);
		mContext = context;
		mDrawType = 4;
	}

	/**
	 * 对于特定的区域，我们都可以使用多个矩形来表示其大致形状。
	 * 事实上，如果矩形足够小，一定数量的矩形就能够精确表示区域的形状，
	 * 也就是说，一定数量的矩形所合成的形状，也可以代表区域的形状。
	 * RegionIterator类，实现了获取组成区域的矩形集的功能，其实RegionIterator类非常简单，
	 * 总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
	 *
	 * RegionIterator(Region region) : 根据区域构建对应的矩形集
	 * boolean next(Rect r) : 获取下一个矩形，结果保存在参数Rect r 中
	 *
	 * 由于在Canvas中没有直接绘制Region的函数，我们想要绘制一个区域，
	 * 就只能通过利用RegionIterator构造矩形集来逼近的显示区域
     */
	private void drawRegion(Canvas canvas, Region rgn, Paint mPaint) {
		RegionIterator iterrator = new RegionIterator(rgn);
		Rect r = new Rect();

		while (iterrator.next(r)) {
			canvas.drawRect(r, mPaint);
		}
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
			case 0: { // 构造Region
				/*
				1、基本构造函数

					 public Region()  //创建一个空的区域
					 public Region(Region region) //拷贝一个region的范围
					 public Region(Rect r)  //创建一个矩形的区域
					 public Region(int left, int top, int right, int bottom) //创建一个矩形的区域

					 上面的四个构造函数，第一个还要配合其它函数使用，暂时不提。
					 第二个构造函数是通过其它的Region来复制一个同样的Region变量
					 第三个，第四个才是正规常的，根据一个矩形或矩形的左上角和右下角点构造出一个矩形区域

				2、间接构造

					 public void setEmpty()  //置空
					 public boolean set(Region region)
					 public boolean set(Rect r)
					 public boolean set(int left, int top, int right, int bottom)
					 public boolean setPath(Path path, Region clip)//后面单独讲

					这是Region所具有的一系列Set方法，我这里全部列了出来，下面一一对其讲解：
					注意：无论调用Set系列函数的Region是不是有区域值，当调用Set系列函数后，
						原来的区域值就会被替换成Set函数里的区域。

					SetEmpty（）：从某种意义上讲置空也是一个构造函数，
					即将原来的一个区域变量变成了一个空变量，要再利用其它的Set方法重新构造区域。

					set(Region region)：利用新的区域值来替换原来的区域
					set(Rect r)：利用矩形所代表的区域替换原来的区域

					set(int left, int top, int right, int bottom)：
					同样，根据矩形的两个点构造出矩形区域来替换原来的区域值

					setPath(Path path, Region clip)：
					根据路径的区域与某区域的交集，构造出新区域，这个后面具体讲解
				 */
			}
			break;
			case 1: { // 画线

				mPaint.setColor(Color.RED);
				mPaint.setStyle(Style.FILL);
				mPaint.setStrokeWidth(2);

				Region rgn = new Region(10,10,100,100);

				//set(Region region)：利用新的区域值来替换原来的区域
				rgn.set(100, 100, 200, 200);
				drawRegion(canvas, rgn, mPaint);
			}
			break;
			case 2: { // 使用SetPath（）构造不规则区域
				/**
				 * boolean setPath (Path path, Region clip)
				 * 参数说明：
				 * Path path：用来构造的区域的路径
				 * Region clip：与前面的path所构成的路径取交集，并将两交集设置为最终的区域
				 */
				mPaint.setColor(Color.RED);
				mPaint.setStyle(Style.STROKE);
				mPaint.setStrokeWidth(2);
				//构造一个椭圆路径  
				Path ovalPath = new Path();
				RectF rect =  new RectF(50, 50, 200, 500);
				ovalPath.addOval(rect, Path.Direction.CCW);
				//SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集  
				Region rgn = new Region();
				rgn.setPath(ovalPath, new Region(50, 50, 200, 200));
				//画出路径  
				drawRegion(canvas, rgn, mPaint);
			}
				break;
			case 3: { // 区域的合并、交叉等操作
				/*
				无论是区域还是矩形，都会涉及到与另一个区域的一些操作，比如取交集、取并集等，涉及到的函数有：

					public final boolean union(Rect r)
					public boolean op(Rect r, Op op) {
					public boolean op(int left, int top, int right, int bottom, Op op)
					public boolean op(Region region, Op op)
					public boolean op(Rect rect, Region region, Op op)

				除了Union(Rect r)是指定合并操作以外，其它四个op（）构造函数，都是指定与另一个区域的操作。
				其中最重要的指定Op的参数，Op的参数有下面四个：


				假设用region1  去组合region2
					public enum Op {
							DIFFERENCE(0), //最终区域为 region1 与 region2 不同的区域
							INTERSECT(1),  //最终区域为 region1 与 region2 相交的区域
							UNION(2),      //最终区域为 region1 与 region2 组合一起的区域
							XOR(3),        //最终区域为 region1 与 region2 相交之外的区域
							REVERSE_DIFFERENCE(4), //最终区域为 region2 与 region1 不同的区域
							REPLACE(5);    //最终区域为为 region2 的区域
				 */

			}
				break;
			case 4: { // 交集

				//构造两个矩形
				Rect rect1 = new Rect(100,100,400,200);
				Rect rect2 = new Rect(200,0,300,300);

				//构造一个画笔，画出矩形轮廓
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				paint.setStyle(Style.STROKE);
				paint.setStrokeWidth(2);

				canvas.drawRect(rect1, paint);
				canvas.drawRect(rect2, paint);



				//构造两个Region
				Region region = new Region(rect1);
				Region region2= new Region(rect2);

				//取两个区域的交集
				region.op(region2, Region.Op.INTERSECT);

				//再构造一个画笔,填充Region操作结果
				Paint paintFill = new Paint();
				paintFill.setColor(Color.GREEN);
				paintFill.setStyle(Style.FILL);
				drawRegion(canvas, region, paintFill);
			}
			break;
			case 5: { // 其他的一些方法
				/*
					//几个判断方法
					public native boolean isEmpty();//判断该区域是否为空
					public native boolean isRect(); //是否是一个矩阵
					public native boolean isComplex();//是否是多个矩阵组合


					//一系列的getBound方法，返回一个Region的边界
					public Rect getBounds()
					public boolean getBounds(Rect r)
					public Path getBoundaryPath()
					public boolean getBoundaryPath(Path path)


					//一系列的判断是否包含某点 和是否相交
					public native boolean contains(int x, int y);//是否包含某点
					public boolean quickContains(Rect r)   //是否包含某矩形
					public native boolean quickContains(int left, int top, int right,int bottom) //是否没有包含某矩阵形
					public boolean quickReject(Rect r) //是否没和该矩形相交
					public native boolean quickReject(int left, int top, int right, int bottom); //是否没和该矩形相交
					public native boolean quickReject(Region rgn);  //是否没和该矩形相交

					//几个平移变换的方法
					public void translate(int dx, int dy)
					public native void translate(int dx, int dy, Region dst);
					public void scale(float scale) //hide
					public native void scale(float scale, Region dst);//hide
				 */

			}
			break;
			
			default:
				break;
		} // end of switch

	} // end of onDraw
} // end of class RegionView
