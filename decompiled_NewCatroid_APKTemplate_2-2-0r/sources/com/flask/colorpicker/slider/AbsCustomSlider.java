package com.flask.colorpicker.slider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.flask.colorpicker.R;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbsCustomSlider extends View {
    protected Bitmap bar;
    protected Canvas barCanvas;
    protected int barHeight;
    protected int barOffsetX;
    protected Bitmap bitmap;
    protected Canvas bitmapCanvas;
    protected int handleRadius;
    protected OnValueChangedListener onValueChangedListener;
    protected float value;

    protected abstract void drawBar(Canvas canvas);

    protected abstract void drawHandle(Canvas canvas, float f, float f2);

    protected abstract void onValueChanged(float f);

    public AbsCustomSlider(Context context) {
        super(context);
        this.handleRadius = 20;
        this.barHeight = 5;
        this.value = 1.0f;
    }

    public AbsCustomSlider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handleRadius = 20;
        this.barHeight = 5;
        this.value = 1.0f;
    }

    public AbsCustomSlider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.handleRadius = 20;
        this.barHeight = 5;
        this.value = 1.0f;
    }

    protected void updateBar() {
        this.handleRadius = getDimension(R.dimen.default_slider_handler_radius);
        this.barHeight = getDimension(R.dimen.default_slider_bar_height);
        this.barOffsetX = this.handleRadius;
        if (this.bar == null) {
            createBitmaps();
        }
        drawBar(this.barCanvas);
        invalidate();
    }

    protected void createBitmaps() {
        int width = getWidth();
        int height = getHeight();
        this.bar = Bitmap.createBitmap(width - (this.barOffsetX * 2), this.barHeight, Bitmap.Config.ARGB_8888);
        this.barCanvas = new Canvas(this.bar);
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && bitmap.getWidth() == width && this.bitmap.getHeight() == height) {
            return;
        }
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
        }
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.bitmapCanvas = new Canvas(this.bitmap);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        super.onDraw(canvas);
        if (this.bar == null || (canvas2 = this.bitmapCanvas) == null) {
            return;
        }
        canvas2.drawColor(0, PorterDuff.Mode.CLEAR);
        this.bitmapCanvas.drawBitmap(this.bar, this.barOffsetX, (getHeight() - this.bar.getHeight()) / 2, (Paint) null);
        drawHandle(this.bitmapCanvas, this.handleRadius + (this.value * (getWidth() - (this.handleRadius * 2))), getHeight() / 2.0f);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, (Paint) null);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateBar();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        if (mode != 0) {
            i = (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.getSize(i) : 0;
        }
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode2 != 0) {
            i2 = (mode2 == Integer.MIN_VALUE || mode2 == 1073741824) ? View.MeasureSpec.getSize(i2) : 0;
        }
        setMeasuredDimension(i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x001f  */
    /* JADX WARN: Code duplicated, block: B:14:0x0023  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            if (this.bar != null) {
                float x = (motionEvent.getX() - this.barOffsetX) / this.bar.getWidth();
                this.value = x;
                float fMax = Math.max(0.0f, Math.min(x, 1.0f));
                this.value = fMax;
                onValueChanged(fMax);
                invalidate();
            }
        } else if (action == 1) {
            onValueChanged(this.value);
            OnValueChangedListener onValueChangedListener = this.onValueChangedListener;
            if (onValueChangedListener != null) {
                onValueChangedListener.onValueChanged(this.value);
            }
            invalidate();
        } else if (action == 2) {
            if (this.bar != null) {
                float x2 = (motionEvent.getX() - this.barOffsetX) / this.bar.getWidth();
                this.value = x2;
                float fMax2 = Math.max(0.0f, Math.min(x2, 1.0f));
                this.value = fMax2;
                onValueChanged(fMax2);
                invalidate();
            }
        }
        return true;
    }

    protected int getDimension(int i) {
        return getResources().getDimensionPixelSize(i);
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }
}
