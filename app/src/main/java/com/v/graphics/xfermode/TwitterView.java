package com.v.graphics.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.v.graphics.R;

/**
 * Created by v on 16/4/14.
 */
public class TwitterView extends View {
    private Paint mBitPaint;
    private Bitmap BmpDST,BmpSRC;
    public TwitterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBitPaint = new Paint();
        BmpDST = BitmapFactory.decodeResource(getResources(), R.drawable.twiter_bg, null);
        BmpSRC = BitmapFactory.decodeResource(getResources(), R.drawable.twiter_light, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(BmpDST,0,0,mBitPaint);
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(BmpSRC, 0, 0, mBitPaint);

        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}