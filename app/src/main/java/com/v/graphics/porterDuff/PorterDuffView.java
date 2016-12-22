package com.v.graphics.porterDuff;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qijian on 16/4/14.
 */
public class PorterDuffView extends View {
    private int width = 400;
    private int height = 400;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;

    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        srcBmp = makeSrc(width, height);
        dstBmp = makeDst(width, height);
        mPaint = new Paint();
    }

    /**
     * 1\save()/restor()保存啥,恢复时就恢复到这个标识位以前的状态,其它的操作都不会被恢复
     * 2\Canvas.CLIP_TO_LAYER_SAVE_FLAG:在保存图层前先把图层根据bounds裁剪
     * 3\Canvas.CLIP_SAVE_FLAG是保存当前画布的大小边界即裁剪数组,与Canvas.CLIP_TO_LAYER_SAVE_FLAG相冲突,
     * 当指定Canvas.CLIP_SAVE_FLAG时,Canvas.CLIP_TO_LAYER_SAVE_FLAG则无效
     * <p/>
     * <p/>
     * <p/>
     *  //这两个标识位是用来标识存储什么内容的,存储的内容只有这两个
     * restore the current matrix when restore() is called
     * public static final int MATRIX_SAVE_FLAG = 0x01;
     * restore the current clip when restore() is called
     * public static final int CLIP_SAVE_FLAG = 0x02;
     *
     * 这三个是用来标识新建bmp时具有哪些属性的.
     * the layer needs to per-pixel alpha
     * public static final int HAS_ALPHA_LAYER_SAVE_FLAG = 0x04; //标识bmp具有透明度,在覆盖时,透明度位置会覆盖上层
     * the layer needs to 8-bits per color component
     * public static final int FULL_COLOR_LAYER_SAVE_FLAG = 0x08;//标识bmp在覆盖时,直接把上层内容给替换掉(先清空再覆盖)
     * clip against the layer's bounds
     * public static final int CLIP_TO_LAYER_SAVE_FLAG = 0x10;//在保存图层前先把图层根据bounds裁剪
     *
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerID = canvas.saveLayer(0, 0, width * 2, height * 2, mPaint, Canvas.FULL_COLOR_LAYER_SAVE_FLAG);

        canvas.drawBitmap(dstBmp, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBmp, width / 2, height / 2, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);
    }


    // create a bitmap with a circle, used for the "dst" image
    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }
}
