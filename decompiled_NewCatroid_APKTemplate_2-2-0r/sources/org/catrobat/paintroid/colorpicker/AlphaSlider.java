package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: AlphaSlider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 72\u00020\u0001:\u000278B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0012H\u0002J \u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000eH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0006\u0010\u001f\u001a\u00020\u0012J\u0006\u0010 \u001a\u00020\u0012J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J\b\u0010%\u001a\u00020\u001cH\u0002J\u0010\u0010&\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014J(\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u0012H\u0014J\u0010\u0010,\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0017J\u0010\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020\u0012H\u0002J\u000e\u0010/\u001a\u00020\u001c2\u0006\u00100\u001a\u00020\u0012J\u000e\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u000203J\u000e\u00104\u001a\u00020\u001c2\u0006\u00105\u001a\u00020\u0012J\b\u00106\u001a\u00020\u001cH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/AlphaSlider;", "Landroid/view/View;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "alphaPaint", "Landroid/graphics/Paint;", "alphaRectangle", "Landroid/graphics/RectF;", "alphaShader", "Landroid/graphics/Shader;", "boardPaint", "checkeredPaint", ResConfigBase.NAME_density, "", "drawingRect", "hueTrackerPaint", "sliderTrackerColor", "", "startTouchPoint", "Landroid/graphics/Point;", "alphaToPoint", "alpha", "clamp", "curr", "min", "max", "drawAlphaRect", "", "canvas", "Landroid/graphics/Canvas;", "getAlphaValue", "getSelectedColor", "moveTrackersIfNeeded", "", "event", "Landroid/view/MotionEvent;", "onColorChanged", "onDraw", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "pointToAlpha", "y", "setAlphaValue", "value", "setOnColorChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/paintroid/colorpicker/AlphaSlider$OnColorChangedListener;", "setSelectedColor", TypedValues.Custom.S_COLOR, "setUpAlphaRect", "Companion", "OnColorChangedListener", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AlphaSlider extends View {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static int alphaValue = 255;
    private static int currentColor;
    private static OnColorChangedListener onColorChangedListener;
    private static int selectedColor;
    private Paint alphaPaint;
    private RectF alphaRectangle;
    private Shader alphaShader;
    private Paint boardPaint;
    private Paint checkeredPaint;
    private float density;
    private RectF drawingRect;
    private Paint hueTrackerPaint;
    private final int sliderTrackerColor;
    private Point startTouchPoint;

    /* JADX INFO: compiled from: AlphaSlider.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/AlphaSlider$OnColorChangedListener;", "", "colorChanged", "", TypedValues.Custom.S_COLOR, "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlphaSlider(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.boardPaint = new Paint();
        this.checkeredPaint = new Paint();
        this.alphaPaint = new Paint();
        this.hueTrackerPaint = new Paint();
        this.drawingRect = new RectF();
        this.alphaRectangle = new RectF();
        this.sliderTrackerColor = -657931;
        this.density = 1.0f;
        this.density = getContext().getResources().getDisplayMetrics().density;
        this.checkeredPaint.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        this.hueTrackerPaint.setColor(-657931);
        this.hueTrackerPaint.setStyle(Paint.Style.STROKE);
        this.hueTrackerPaint.setStrokeWidth(this.density * 2.0f);
        this.hueTrackerPaint.setAntiAlias(true);
    }

    /* JADX INFO: compiled from: AlphaSlider.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/AlphaSlider$Companion;", "", "()V", "alphaValue", "", "getAlphaValue", "()I", "setAlphaValue", "(I)V", "currentColor", "onColorChangedListener", "Lorg/catrobat/paintroid/colorpicker/AlphaSlider$OnColorChangedListener;", "selectedColor", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getAlphaValue() {
            return AlphaSlider.alphaValue;
        }

        public final void setAlphaValue(int i) {
            AlphaSlider.alphaValue = i;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.drawingRect.width() <= 0.0f || this.drawingRect.height() <= 0.0f) {
            return;
        }
        drawAlphaRect(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        RectF rectF = new RectF();
        this.drawingRect = rectF;
        rectF.left = getPaddingLeft() + 5.0f;
        this.drawingRect.right = (w - 5.0f) - getPaddingRight();
        this.drawingRect.top = getPaddingTop() + 10.0f;
        this.drawingRect.bottom = (h + 20.0f) - getPaddingBottom();
        setUpAlphaRect();
    }

    private final void setUpAlphaRect() {
        RectF rectF = this.drawingRect;
        float f = rectF.left + 15.0f;
        float f2 = rectF.bottom - 70.0f;
        float f3 = rectF.bottom - 1.0f;
        this.alphaRectangle.set(f, f2, rectF.right - 15.0f, f3);
    }

    private final void drawAlphaRect(Canvas canvas) {
        RectF rectF = this.alphaRectangle;
        this.boardPaint.setColor(-9539986);
        canvas.drawRect(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f, this.boardPaint);
        canvas.drawRect(rectF, this.checkeredPaint);
        float[] fArr = new float[3];
        Color.colorToHSV(currentColor, fArr);
        LinearGradient linearGradient = new LinearGradient(rectF.left, rectF.top, rectF.right, rectF.top, Color.HSVToColor(255, fArr), 0, Shader.TileMode.CLAMP);
        this.alphaShader = linearGradient;
        this.alphaPaint.setShader(linearGradient);
        canvas.drawRect(rectF, this.alphaPaint);
        float f = (4 * this.density) / 2.0f;
        Point pointAlphaToPoint = alphaToPoint(alphaValue);
        RectF rectF2 = new RectF();
        rectF2.left = pointAlphaToPoint.x - f;
        rectF2.right = pointAlphaToPoint.x + f;
        rectF2.top = rectF.top - 2.0f;
        rectF2.bottom = rectF.bottom + 2.0f;
        canvas.drawRoundRect(rectF2, 2.0f, 2.0f, this.hueTrackerPaint);
    }

    private final Point alphaToPoint(int alpha) {
        RectF rectF = this.alphaRectangle;
        float fWidth = rectF.width();
        Point point = new Point();
        point.x = (int) ((fWidth - ((alpha * fWidth) / 255)) + rectF.left);
        point.y = (int) rectF.top;
        return point;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        boolean zMoveTrackersIfNeeded;
        Intrinsics.checkNotNullParameter(event, "event");
        int action = event.getAction();
        if (action == 0) {
            this.startTouchPoint = new Point((int) event.getX(), (int) event.getY());
            zMoveTrackersIfNeeded = moveTrackersIfNeeded(event);
        } else if (action != 1) {
            zMoveTrackersIfNeeded = action != 2 ? false : moveTrackersIfNeeded(event);
        } else {
            this.startTouchPoint = null;
            zMoveTrackersIfNeeded = moveTrackersIfNeeded(event);
        }
        if (zMoveTrackersIfNeeded) {
            invalidate();
            onColorChanged();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private final boolean moveTrackersIfNeeded(MotionEvent event) {
        Point point = this.startTouchPoint;
        if (point == null) {
            return false;
        }
        if (!this.alphaRectangle.contains(point.x, point.y)) {
            return false;
        }
        alphaValue = pointToAlpha((int) event.getX());
        return true;
    }

    private final float clamp(float curr, float min, float max) {
        return RangesKt.coerceAtLeast(min, RangesKt.coerceAtMost(max, curr));
    }

    private final int pointToAlpha(int y) {
        RectF rectF = this.alphaRectangle;
        int iWidth = (int) rectF.width();
        return 255 - ((((int) clamp(y - rectF.left, 0.0f, iWidth)) * 255) / iWidth);
    }

    public final void setOnColorChangedListener(OnColorChangedListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        onColorChangedListener = listener;
    }

    public final int getSelectedColor() {
        float[] fArr = new float[3];
        Color.colorToHSV(selectedColor, fArr);
        int iHSVToColor = Color.HSVToColor(alphaValue, fArr);
        selectedColor = iHSVToColor;
        return iHSVToColor;
    }

    private final void onColorChanged() {
        OnColorChangedListener onColorChangedListener2 = onColorChangedListener;
        if (onColorChangedListener2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("onColorChangedListener");
            onColorChangedListener2 = null;
        }
        onColorChangedListener2.colorChanged(getSelectedColor());
    }

    public final void setSelectedColor(int color) {
        float[] fArr = new float[3];
        Color.colorToHSV(color, fArr);
        selectedColor = Color.HSVToColor(alphaValue, fArr);
        currentColor = color;
        invalidate();
    }

    public final int getAlphaValue() {
        return alphaValue;
    }

    public final void setAlphaValue(int value) {
        alphaValue = value;
    }
}
