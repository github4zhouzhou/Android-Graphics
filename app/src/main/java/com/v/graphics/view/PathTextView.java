package com.v.graphics.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

/**
 * Created by v on 2016/12/13.
 */

public class PathTextView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mDrawType;

    public PathTextView(Context context) {
        super(context);
        mContext = context;
        mDrawType = 1;
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
            mPaint.setStyle(Paint.Style.STROKE); //设置填充样式  Style.FILL / Style.FILL_AND_STROKE / Style.STROKE
            mPaint.setStrokeWidth(5);    //设置画笔宽度
        }
        //设置画布背景颜色
        canvas.drawRGB(255, 255, 255);

        switch (mDrawType) {
            case 0: { // 直线路径
                /**
                 * void moveTo (float x1, float y1):直线的开始点；即将直线路径的绘制点定在（x1,y1）的位置；
                 * void lineTo (float x2, float y2)：直线的结束点，又是下一次绘制直线路径的开始点；lineTo（）可以一直用；
                 * void close ():如果连续画了几条直线，但没有形成闭环，调用Close()会将路径首尾点连接起来，形成闭环；
                 */
                Path path = new Path();

                path.moveTo(10, 10); //设定起始点
                path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
                path.lineTo(300, 100);//画第二条直线
                path.lineTo(500, 100);//第三条直线
                path.close();//闭环

                canvas.drawPath(path, mPaint);
            }
            break;
            case 1: { // 矩形路径
                /**
                 *  Path.Direction.CCW：是 counter-clockwise 缩写，指创建逆时针方向的矩形路径；
                 *  Path.Direction.CW：是 clockwise 的缩写，指创建顺时针方向的矩形路径；
                 *  生成方式的区别在于，依据生成方向排版的文字！
                 *  后面我们会讲到文字，文字是可以依据路径排版的，那文字的行走方向就是依据路径的生成方向
                 */

                //第一个逆向生成
                Path ccwRectPath = new Path();
                RectF rect1 =  new RectF(50, 50, 240, 200);
                ccwRectPath.addRect(rect1, Path.Direction.CCW);

                //第二个顺向生成
                Path cwRectPath = new Path();
                RectF rect2 =  new RectF(290, 50, 480, 200);
                cwRectPath.addRect(rect2, Path.Direction.CW);

                //先画出这两个路径
                canvas.drawPath(ccwRectPath, mPaint);
                canvas.drawPath(cwRectPath, mPaint);

                //依据路径写出文字
                String text="This is direction text";
                mPaint.setColor(Color.BLUE);
                mPaint.setTextSize(35);
                canvas.drawTextOnPath(text, ccwRectPath, 0, 0, mPaint); //逆时针生成
                canvas.drawTextOnPath(text, cwRectPath, 80, -18, mPaint);  //顺时针生成
            }
            break;
            case 2: { // 圆角矩形路径

                Path path = new Path();
                RectF rect1 =  new RectF(50, 50, 240, 200);
                path.addRoundRect(rect1, 10, 15 , Path.Direction.CCW);

                RectF rect2 =  new RectF(290, 50, 480, 200);
                float radii[] ={10,15,20,25,30,35,40,45};
                path.addRoundRect(rect2, radii, Path.Direction.CCW);

                canvas.drawPath(path, mPaint);
            }
            break;
            case 3: { // 圆形路径
                Path path = new Path();
                path.addCircle(200, 200, 100, Path.Direction.CCW);
                canvas.drawPath(path, mPaint);
            }
            break;
            case 4: { // 椭圆路径
                Path path = new Path();
                RectF rect =  new RectF(50, 50, 240, 200);
                path.addOval(rect, Path.Direction.CCW);
                canvas.drawPath(path, mPaint);
            }
            break;
            case 5: { // 弧形路径
                mPaint.setStrokeWidth(5);//设置画笔宽度

                Path path = new Path();
                RectF rect =  new RectF(50, 50, 240, 200);
                path.addArc(rect, 0, 100);

                canvas.drawPath(path, mPaint); //画出路径
            }
            break;
            case 6: { // 线段轨迹---待补充
                //void quadTo (float x1, float y1, float x2, float y2)
            }
            break;
            case 10: { // Paint相关设置
                //普通设置
                mPaint.setStrokeWidth (5);//设置画笔宽度
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
                mPaint.setStyle(Paint.Style.FILL);//绘图样式，对于设文字和几何图形都有效
                mPaint.setTextAlign(Paint.Align.CENTER);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
                mPaint.setTextSize(12);//设置文字大小

                //样式设置
                mPaint.setFakeBoldText(true);//设置是否为粗体文字
                mPaint.setUnderlineText(true);//设置下划线
                mPaint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
                mPaint.setStrikeThruText(true);//设置带有删除线效果

                //其它设置
                mPaint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
            }
            break;
            case 11: {
                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(80);//设置文字大小  

                //绘图样式，设置为填充
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Hello World!", 10, 100, mPaint);

                //绘图样式设置为描边
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawText("Hello World!", 10, 200, mPaint);

                //绘图样式设置为填充且描边
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawText("Hello World!", 10, 300, mPaint);
            }
            break;
            case 12: {
                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(80);//设置文字大小  
                mPaint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充     

                //样式设置
                mPaint.setFakeBoldText(true);//设置是否为粗体文字  
                mPaint.setUnderlineText(true);//设置下划线  
                mPaint.setStrikeThruText(true);//设置带有删除线效果  

                //设置字体水平倾斜度，普通斜体字是-0.25，可见往右斜
                mPaint.setTextSkewX((float) -0.25);
                canvas.drawText("Hello World!", 10,100, mPaint);

                //水平倾斜度设置为：0.25，往左斜
                mPaint.setTextSkewX((float) 0.25);
                canvas.drawText("Hello World!", 10,200, mPaint);
            }
            break;
            case 13: { // 文字水平拉伸
                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(80);//设置文字大小  
                mPaint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充     

                //变通样式字体
                canvas.drawText("Hello World!", 10,100, mPaint);

                //水平方向拉伸两倍
                mPaint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变  
                canvas.drawText("Hello World!", 10,200, mPaint);

                //写在同一位置,不同颜色,看下高度是否看的不变
                mPaint.setTextScaleX(1);//先还原拉伸效果  
                canvas.drawText("Hello World!", 10,300, mPaint);

                mPaint.setColor(Color.GREEN);
                mPaint.setTextScaleX(2);//重新设置拉伸效果  
                canvas.drawText("Hello World!", 10,300, mPaint);
            }
            break;
            case 14: { // 指定每个文字位置
                /**
                 * char[] text：要绘制的文字数组
                 * int index:：第一个要绘制的文字的索引
                 * int count：要绘制的文字的个数，用来算最后一个文字的位置，从第一个绘制的文字开始算起
                 * float[] pos：每个字体的位置，同样两两一组，如｛x1,y1,x2,y2,x3,y3……｝
                 */

                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(80);//设置文字大小  
                mPaint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充     

                float []pos = new float[]{80,100,
                        80,200,
                        80,300,
                        80,400};
                canvas.drawPosText("画图示例", pos, mPaint);//两个构造函数
            }
            break;
            case 15: { // 沿路径绘制
                /**
                 * void drawTextOnPath (String text, Path path,
                 *                      float hOffset, float vOffset, Paint mPaint)
                 * void drawTextOnPath (char[] text, int index, int count,
                 *                      Path path,float hOffset, float vOffset, Paint mPaint)
                 * 参数说明：
                 * 有关截取部分字体绘制相关参数（index,count），没难度，就不再讲了.
                 * float hOffset: 与路径起始点的水平偏移距离
                 * float vOffset: 与路径中心的垂直偏移量
                 */

                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(45);//设置文字大小  
                mPaint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充  

                String string="风萧萧兮易水寒，壮士一去兮不复返";

                //先创建两个相同的圆形路径，并先画出两个路径原图
                Path circlePath = new Path();
                circlePath.addCircle(220,200, 180, Path.Direction.CCW);//逆向绘制,还记得吗,上篇讲过的  
                canvas.drawPath(circlePath, mPaint);//绘制出路径原形  

                Path circlePath2 = new Path();
                circlePath2.addCircle(750,200, 180, Path.Direction.CCW);
                canvas.drawPath(circlePath2, mPaint);//绘制出路径原形  

                mPaint.setColor(Color.GREEN);
                //hOffset、vOffset 参数值全部设为0，看原始状态是怎样的
                canvas.drawTextOnPath(string, circlePath, 0, 0, mPaint);
                //第二个路径，改变 hOffset、vOffset 参数值
                canvas.drawTextOnPath(string, circlePath2, 80, 30, mPaint);

            }
            break;
            case 16: { // 字体样式设置（Typeface）
                /*
                在Paint中设置字体样式：

                    mPaint.setTypeface(typeface);

                    Typeface相关
                    
                    概述：Typeface是专门用来设置字体样式的，通过mPaint.setTypeface()来指定。
                    可以指定系统中的字体样式，也可以指定自定义的样式文件中获取。要构建Typeface时，
                    可以指定所用样式的正常体、斜体、粗体等，如果指定样式中，
                    没有相关文字的样式就会用系统默认的样式来显示，一般默认是宋体。
                    
                    创建Typeface：
                    
                     Typeface	create(String familyName, int style) //直接通过指定字体名来加载系统中自带的文字样式
                     Typeface	create(Typeface family, int style)     //通过其它Typeface变量来构建文字样式
                     Typeface	createFromAsset(AssetManager mgr, String path) //通过从Asset中获取外部字体来显示字体样式
                     Typeface	createFromFile(String path)//直接从路径创建
                     Typeface	createFromFile(File path)//从外部路径来创建字体样式
                     Typeface	defaultFromStyle(int style)//创建默认字体
                    
                    上面的各个参数都会用到Style变量,Style的枚举值如下:
                    Typeface.NORMAL  //正常体
                     Typeface.BOLD	 //粗体
                     Typeface.ITALIC	 //斜体
                    Typeface.BOLD_ITALIC //粗斜体
                    
                    使用系统中的字体
                    
                     Typeface	defaultFromStyle(int style)//创建默认字体
                     Typeface	create(String familyName, int style) //直接通过指定字体名来加载系统中自带的文字样式
                 */
            }
            break;
            case 17: { // 使用系统自带字体绘制
                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(60);//设置文字大小  
                mPaint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充  

                String familyName = "宋体";
                Typeface font = Typeface.create(familyName, Typeface.NORMAL);
                mPaint.setTypeface(font);
                canvas.drawText("使用系统自带字体绘制",10,100, mPaint);
            }
            break;
            case 18: { // 自字义字体
                /*
                自定义字体的话，我们就需要从外部字体文件加载我们所需要的字形的，
                从外部文件加载字形所使用的Typeface构造函数如下面三个：
                    //通过从Asset中获取外部字体来显示字体样式
                    Typeface	createFromAsset(AssetManager mgr, String path)
                    Typeface	createFromFile(String path) //直接从路径创建
                    Typeface	createFromFile(File path)   //从外部路径来创建字体样式
                 */

                mPaint.setStrokeWidth (5);//设置画笔宽度  
                mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
                mPaint.setTextSize(60);//设置文字大小  
                mPaint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充  

                AssetManager mgr = mContext.getAssets(); //得到AssetManager
                Typeface typeface=Typeface.createFromAsset(mgr, "fonts/jian_luobo.ttf");//根据路径得到Typeface  
                mPaint.setTypeface(typeface);
                Log.v("msg", typeface.toString());
                canvas.drawText("自字义字体",10,100, mPaint);//两个构造函数
            }
            break;
            case 19: {

            }
            break;
            case 20: {

            }
            break;
            default:
                break;
            
        } // end of switch

    } // end of onDraw

}
