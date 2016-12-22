package com.v.graphics.xfermode;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.v.graphics.R;

public class XfermodeView extends View {

	private Paint mPaint;
	private Bitmap mBmp;
	private int mDrawType = 5;
	private int width = 400;
	private int height = 400;
	private Bitmap dstBmp;
	private Bitmap srcBmp;


    Paint paint;
    float cellSize = 0;
    float cellHorizontalOffset = 0;
    float cellVerticalOffset = 0;
    float circleRadius = 0;
    float rectSize = 0;
    int circleColor = 0xffffcc44;//黄色
    int rectColor = 0xff66aaff;//蓝色
    float textSize =  getResources().getDimensionPixelSize(R.dimen.textSize);

    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };

    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };

	public XfermodeView(Context context) {
		super(context);
		init();
	}

	public XfermodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dog);

		srcBmp = makeSrc(width, height);
		dstBmp = makeDst(width, height);

        if(Build.VERSION.SDK_INT >= 11){
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		switch (mDrawType) {
			case 0: {
				drawTargetMode(canvas);
			}
			break;
			case 1: {
				drawFlowerBmp(canvas);
			}
			break;
			case 2: {
				drawTransparencyBmp(canvas);
			}
			break;
			case 3: {
				drawAvoidMode(canvas);
			}
			break;
			case 4: {
				int layerID = canvas.saveLayer(0,0,width*2,height*2,mPaint,Canvas.ALL_SAVE_FLAG);

				canvas.drawBitmap(dstBmp, 0, 0, mPaint);
				mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
				canvas.drawBitmap(srcBmp,0,0, mPaint);
                //canvas.drawBitmap(srcBmp,width/2,height/2, mPaint);
				mPaint.setXfermode(null);

				canvas.restoreToCount(layerID);
			}
			break;
            case 5: {
                //设置背景色
                canvas.drawARGB(255, 255, 255, 255);

                int canvasWidth = canvas.getWidth();
                int canvasHeight = canvas.getHeight();

                for(int row = 0; row < 4; row++){
                    for(int column = 0; column < 4; column++){
                        canvas.save();
                        int layer = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
                        paint.setXfermode(null);
                        int index = row * 4 + column;
                        float translateX = (cellSize + cellHorizontalOffset) * column;
                        float translateY = (cellSize + cellVerticalOffset) * row;
                        canvas.translate(translateX, translateY);
                        //画文字
                        String text = sLabels[index];
                        paint.setColor(Color.BLACK);
                        float textXOffset = cellSize / 2;
                        float textYOffset = textSize + (cellVerticalOffset - textSize) / 2;
                        canvas.drawText(text, textXOffset, textYOffset, paint);
                        canvas.translate(0, cellVerticalOffset);
                        //画边框
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(0xff000000);
                        canvas.drawRect(2, 2, cellSize - 2, cellSize - 2, paint);
                        paint.setStyle(Paint.Style.FILL);
                        //画圆
                        paint.setColor(circleColor);
                        float left = circleRadius + 3;
                        float top = circleRadius + 3;
                        canvas.drawCircle(left, top, circleRadius, paint);
                        paint.setXfermode(sModes[index]);
                        //画矩形
                        paint.setColor(rectColor);
                        float rectRight = circleRadius + rectSize;
                        float rectBottom = circleRadius + rectSize;
                        canvas.drawRect(left, top, rectRight, rectBottom, paint);
                        paint.setXfermode(null);
                        //canvas.restore();
                        canvas.restoreToCount(layer);
                    }
                }
            }
            break;
			default:
				break;
		} // end of switch

	} // end of onDraw

/////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellSize = w / 4.5f;
        cellHorizontalOffset = cellSize / 6;
        cellVerticalOffset = cellSize * 0.426f;
        circleRadius = cellSize / 3;
        rectSize = cellSize * 0.6f;
    }

//////////////////////////////////////////////////////////////////////////////////////////
	// create a bitmap with a circle, used for the "dst" image
	static Bitmap makeDst(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFFFFCC44);
		c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
		return bm;
	}

	// create a bitmap with a rect, used for the "src" image
	static Bitmap makeSrc(int w, int h) {
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bm);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

		p.setColor(0xFF66AAFF);
		c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
		return bm;
	}


/////////////////////////////////////////////////////////////////////////////////
	/**
	 * 使用Mode.TARGET
	 * @param canvas
	 */
	private void drawTargetMode(Canvas canvas){
		int width  = 500;
		int height = width * mBmp.getHeight()/mBmp.getWidth();
		mPaint.setColor(Color.RED);

		int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

		canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
		mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
		canvas.drawRect(0,0,width,height,mPaint);

		canvas.restoreToCount(layerID);
	}

	/**
	 * 利用第二个图片(小花图片)来替换选区
	 * @param canvas
	 */
	private void drawFlowerBmp(Canvas canvas){
		int width  = 500;
		int height = width * mBmp.getHeight()/mBmp.getWidth();

		int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

		canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
		mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.flower),null,new Rect(0,0,width,height),mPaint);

		canvas.restoreToCount(layerID);
	}

	/**
	 * 用透明图片替换选区
	 * @param canvas
	 */
	private void drawTransparencyBmp(Canvas canvas){
		int width  = 500;
		int height = width * mBmp.getHeight()/mBmp.getWidth();
		int layerID = canvas.saveLayer(0,0,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

		canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
		mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
		mPaint.setARGB(0x00,0xff,0xff,0xff);
		canvas.drawRect(0,0,width,height,mPaint);

		canvas.restoreToCount(layerID);
	}

	/**
	 * 使用Mode.AVOID
	 * @param canvas
	 */
	private void drawAvoidMode(Canvas canvas) {
		int width = 500;
		int height = width * mBmp.getHeight() / mBmp.getWidth();
		mPaint.setColor(Color.RED);

		int layerID = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);

		canvas.drawBitmap(mBmp, null, new Rect(0, 0, width, height), mPaint);
		mPaint.setXfermode(new AvoidXfermode(Color.WHITE, 100, AvoidXfermode.Mode.AVOID));
		canvas.drawRect(0, 0, width, height, mPaint);

		canvas.restoreToCount(layerID);
	}

} // end of class ColorMatrixView
