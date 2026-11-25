package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.text.util.LocalePreferences;
import androidx.core.view.ViewCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HSVColorPickerView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0014\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001PB\u0011\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B#\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010,\u001a\u00020-H\u0002J \u0010.\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\u00102\u0006\u00100\u001a\u00020\u0010H\u0002J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0002J\u0010\u00105\u001a\u0002022\u0006\u00103\u001a\u000204H\u0002J\u0010\u00106\u001a\u00020+2\u0006\u0010\u0015\u001a\u00020\u0010H\u0002J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0002J\b\u0010;\u001a\u000202H\u0002J\u0010\u0010<\u001a\u0002022\u0006\u00103\u001a\u000204H\u0014J\u0018\u0010=\u001a\u0002022\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\tH\u0014J(\u0010@\u001a\u0002022\u0006\u0010A\u001a\u00020\t2\u0006\u0010B\u001a\u00020\t2\u0006\u0010C\u001a\u00020\t2\u0006\u0010D\u001a\u00020\tH\u0014J\u0010\u0010E\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010F\u001a\u00020\u00102\u0006\u0010G\u001a\u00020\u0010H\u0002J\u0018\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020\u00102\u0006\u0010G\u001a\u00020\u0010H\u0002J\u0018\u0010K\u001a\u00020+2\u0006\u0010\u001f\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010L\u001a\u0002022\b\u0010M\u001a\u0004\u0018\u00010\u001bJ\b\u0010N\u001a\u000202H\u0002J\b\u0010O\u001a\u000202H\u0002R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010$\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006Q"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/HSVColorPickerView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "alpha", "borderColor", "borderPaint", "Landroid/graphics/Paint;", "brightness", "", ResConfigBase.NAME_density, "drawingOffset", "drawingRect", "Landroid/graphics/RectF;", "hue", "huePaint", "huePanelWidth", "hueRect", "hueTrackerPaint", "onColorChangedListener", "Lorg/catrobat/paintroid/colorpicker/HSVColorPickerView$OnColorChangedListener;", "paletteCircleTrackerRadius", "panelSpacing", "rectangleTrackerOffset", LocalePreferences.FirstDayOfWeek.SATURDAY, "satValPaint", "satValRect", "satValTrackerPaint", TypedValues.Custom.S_COLOR, "selectedColor", "getSelectedColor", "()I", "setSelectedColor", "(I)V", "sliderTrackerColor", "startTouchPoint", "Landroid/graphics/Point;", "buildHueColorArray", "", "clamp", "mn", "mx", "drawHuePanel", "", "canvas", "Landroid/graphics/Canvas;", "drawSatValPanel", "hueToPoint", "moveTrackersIfNeeded", "", "event", "Landroid/view/MotionEvent;", "onColorChanged", "onDraw", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onSizeChanged", "w", "h", "oldw", "oldh", "onTouchEvent", "pointToHue", "y", "pointToSatVal", "", "x", "satValToPoint", "setOnColorChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setUpHueRect", "setUpSatValRect", "OnColorChangedListener", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class HSVColorPickerView extends View {
    private int alpha;
    private final int borderColor;
    private Paint borderPaint;
    private float brightness;
    private float density;
    private float drawingOffset;
    private RectF drawingRect;
    private float hue;
    private Paint huePaint;
    private float huePanelWidth;
    private final RectF hueRect;
    private Paint hueTrackerPaint;
    private OnColorChangedListener onColorChangedListener;
    private float paletteCircleTrackerRadius;
    private float panelSpacing;
    private float rectangleTrackerOffset;
    private float sat;
    private Paint satValPaint;
    private final RectF satValRect;
    private Paint satValTrackerPaint;
    private final int sliderTrackerColor;
    private Point startTouchPoint;

    /* JADX INFO: compiled from: HSVColorPickerView.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/HSVColorPickerView$OnColorChangedListener;", "", "colorChanged", "", TypedValues.Custom.S_COLOR, "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    public final int getSelectedColor() {
        return Color.HSVToColor(this.alpha, new float[]{this.hue, this.sat, this.brightness});
    }

    public final void setSelectedColor(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        this.alpha = Color.alpha(i);
        this.hue = fArr[0];
        this.sat = fArr[1];
        this.brightness = fArr[2];
        invalidate();
    }

    public HSVColorPickerView(Context context) {
        super(context);
        this.panelSpacing = 10.0f;
        this.paletteCircleTrackerRadius = 5.0f;
        this.rectangleTrackerOffset = 2.0f;
        this.huePanelWidth = 30.0f;
        this.density = 1.0f;
        this.alpha = 255;
        this.hue = 360.0f;
        this.sliderTrackerColor = -657931;
        this.borderColor = -9539986;
        float f = getContext().getResources().getDisplayMetrics().density;
        this.density = f;
        float f2 = this.paletteCircleTrackerRadius * f;
        this.paletteCircleTrackerRadius = f2;
        this.rectangleTrackerOffset *= f;
        this.huePanelWidth *= f;
        this.panelSpacing *= f;
        this.drawingOffset = f2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.density * 2.0f);
        paint.setAntiAlias(true);
        Unit unit = Unit.INSTANCE;
        this.satValTrackerPaint = paint;
        Paint paint2 = new Paint();
        paint2.setColor(-657931);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(this.density * 2.0f);
        paint2.setAntiAlias(true);
        Unit unit2 = Unit.INSTANCE;
        this.hueTrackerPaint = paint2;
        this.satValPaint = new Paint();
        this.huePaint = new Paint();
        this.borderPaint = new Paint();
        this.drawingRect = new RectF();
        this.satValRect = new RectF();
        this.hueRect = new RectF();
    }

    public HSVColorPickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.panelSpacing = 10.0f;
        this.paletteCircleTrackerRadius = 5.0f;
        this.rectangleTrackerOffset = 2.0f;
        this.huePanelWidth = 30.0f;
        this.density = 1.0f;
        this.alpha = 255;
        this.hue = 360.0f;
        this.sliderTrackerColor = -657931;
        this.borderColor = -9539986;
        float f = getContext().getResources().getDisplayMetrics().density;
        this.density = f;
        float f2 = this.paletteCircleTrackerRadius * f;
        this.paletteCircleTrackerRadius = f2;
        this.rectangleTrackerOffset *= f;
        this.huePanelWidth *= f;
        this.panelSpacing *= f;
        this.drawingOffset = f2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.density * 2.0f);
        paint.setAntiAlias(true);
        Unit unit = Unit.INSTANCE;
        this.satValTrackerPaint = paint;
        Paint paint2 = new Paint();
        paint2.setColor(-657931);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(this.density * 2.0f);
        paint2.setAntiAlias(true);
        Unit unit2 = Unit.INSTANCE;
        this.hueTrackerPaint = paint2;
        this.satValPaint = new Paint();
        this.huePaint = new Paint();
        this.borderPaint = new Paint();
        this.drawingRect = new RectF();
        this.satValRect = new RectF();
        this.hueRect = new RectF();
    }

    public HSVColorPickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.panelSpacing = 10.0f;
        this.paletteCircleTrackerRadius = 5.0f;
        this.rectangleTrackerOffset = 2.0f;
        this.huePanelWidth = 30.0f;
        this.density = 1.0f;
        this.alpha = 255;
        this.hue = 360.0f;
        this.sliderTrackerColor = -657931;
        this.borderColor = -9539986;
        float f = getContext().getResources().getDisplayMetrics().density;
        this.density = f;
        float f2 = this.paletteCircleTrackerRadius * f;
        this.paletteCircleTrackerRadius = f2;
        this.rectangleTrackerOffset *= f;
        this.huePanelWidth *= f;
        this.panelSpacing *= f;
        this.drawingOffset = f2;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.density * 2.0f);
        paint.setAntiAlias(true);
        Unit unit = Unit.INSTANCE;
        this.satValTrackerPaint = paint;
        Paint paint2 = new Paint();
        paint2.setColor(-657931);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(this.density * 2.0f);
        paint2.setAntiAlias(true);
        Unit unit2 = Unit.INSTANCE;
        this.hueTrackerPaint = paint2;
        this.satValPaint = new Paint();
        this.huePaint = new Paint();
        this.borderPaint = new Paint();
        this.drawingRect = new RectF();
        this.satValRect = new RectF();
        this.hueRect = new RectF();
    }

    private final float clamp(float brightness, float mn, float mx) {
        return Math.max(mn, Math.min(mx, brightness));
    }

    private final void drawSatValPanel(Canvas canvas) {
        this.borderPaint.setColor(this.borderColor);
        canvas.drawRect(this.drawingRect.left, this.drawingRect.top, this.satValRect.right + 1.0f, this.satValRect.bottom + 1.0f, this.borderPaint);
        LinearGradient linearGradient = new LinearGradient(this.satValRect.left, this.satValRect.top, this.satValRect.left, this.satValRect.bottom, -1, ViewCompat.MEASURED_STATE_MASK, Shader.TileMode.CLAMP);
        LinearGradient linearGradient2 = new LinearGradient(this.satValRect.left, this.satValRect.top, this.satValRect.right, this.satValRect.bottom, -1, Color.HSVToColor(new float[]{this.hue, 1.0f, 1.0f}), Shader.TileMode.CLAMP);
        Paint paint = this.satValPaint;
        paint.setXfermode(null);
        paint.setShader(linearGradient);
        canvas.drawRect(this.satValRect, paint);
        paint.setShader(linearGradient2);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawRect(this.satValRect, paint);
        Point pointSatValToPoint = satValToPoint(this.sat, this.brightness);
        this.satValTrackerPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawCircle(pointSatValToPoint.x, pointSatValToPoint.y, this.paletteCircleTrackerRadius - (this.density * 1.0f), this.satValTrackerPaint);
        this.satValTrackerPaint.setColor(-2236963);
        canvas.drawCircle(pointSatValToPoint.x, pointSatValToPoint.y, this.paletteCircleTrackerRadius, this.satValTrackerPaint);
    }

    private final void drawHuePanel(Canvas canvas) {
        this.borderPaint.setColor(this.borderColor);
        canvas.drawRect(this.hueRect.left - 1.0f, this.hueRect.top - 1.0f, this.hueRect.right + 1.0f, this.hueRect.bottom + 1.0f, this.borderPaint);
        this.huePaint.setShader(new LinearGradient(this.hueRect.left, this.hueRect.top, this.hueRect.left, this.hueRect.bottom, buildHueColorArray(), (float[]) null, Shader.TileMode.CLAMP));
        canvas.drawRect(this.hueRect, this.huePaint);
        float f = 2 * this.density;
        Point pointHueToPoint = hueToPoint(this.hue);
        RectF rectF = new RectF();
        rectF.left = this.hueRect.left - this.rectangleTrackerOffset;
        rectF.right = this.hueRect.right + this.rectangleTrackerOffset;
        rectF.top = pointHueToPoint.y - f;
        rectF.bottom = pointHueToPoint.y + f;
        canvas.drawRoundRect(rectF, 2.0f, 2.0f, this.hueTrackerPaint);
    }

    private final boolean moveTrackersIfNeeded(MotionEvent event) {
        Point point = this.startTouchPoint;
        Integer numValueOf = point == null ? null : Integer.valueOf(point.x);
        if (numValueOf == null) {
            return false;
        }
        int iIntValue = numValueOf.intValue();
        Point point2 = this.startTouchPoint;
        Integer numValueOf2 = point2 != null ? Integer.valueOf(point2.y) : null;
        if (numValueOf2 == null) {
            return false;
        }
        float f = iIntValue;
        float fIntValue = numValueOf2.intValue();
        if (this.hueRect.contains(f, fIntValue)) {
            this.hue = pointToHue(event.getY());
        } else {
            if (!this.satValRect.contains(f, fIntValue)) {
                return false;
            }
            float[] fArrPointToSatVal = pointToSatVal(event.getX(), event.getY());
            this.sat = fArrPointToSatVal[0];
            this.brightness = fArrPointToSatVal[1];
        }
        return true;
    }

    private final Point satValToPoint(float sat, float brightness) {
        float fHeight = this.satValRect.height();
        float fWidth = this.satValRect.width();
        Point point = new Point();
        point.x = (int) ((sat * fWidth) + this.satValRect.left);
        point.y = (int) (((1.0f - brightness) * fHeight) + this.satValRect.top);
        return point;
    }

    private final Point hueToPoint(float hue) {
        float fHeight = this.hueRect.height();
        Point point = new Point();
        point.x = (int) this.hueRect.left;
        point.y = (int) ((fHeight - ((hue * fHeight) / 360.0f)) + this.hueRect.top);
        return point;
    }

    private final float[] pointToSatVal(float x, float y) {
        float fWidth = this.satValRect.width();
        float fHeight = this.satValRect.height();
        return new float[]{(1.0f / fWidth) * clamp(x - this.satValRect.left, 0.0f, fWidth), 1.0f - ((1.0f / fHeight) * clamp(y - this.satValRect.top, 0.0f, fHeight))};
    }

    private final float pointToHue(float y) {
        float fHeight = this.hueRect.height();
        return 360.0f - ((clamp(y - this.hueRect.top, 0.0f, fHeight) * 360.0f) / fHeight);
    }

    private final int[] buildHueColorArray() {
        int[] iArr = new int[361];
        int i = 360;
        int i2 = 0;
        while (true) {
            int i3 = i - 1;
            int i4 = i2 + 1;
            iArr[i2] = Color.HSVToColor(new float[]{i, 1.0f, 1.0f});
            if (i3 < 0) {
                return iArr;
            }
            i = i3;
            i2 = i4;
        }
    }

    private final void setUpSatValRect() {
        float fHeight = this.drawingRect.height() - 2.0f;
        float fWidth = ((this.drawingRect.width() - 2.0f) - this.panelSpacing) - this.huePanelWidth;
        float f = this.drawingRect.left + 1.0f;
        float f2 = this.drawingRect.top + 1.0f;
        RectF rectF = this.satValRect;
        rectF.set(f, f2, fWidth + f, fHeight + f2);
    }

    private final void setUpHueRect() {
        float f = (this.drawingRect.right - this.huePanelWidth) + 1.0f;
        float f2 = this.drawingRect.top + 1.0f;
        float f3 = this.drawingRect.bottom - 1.0f;
        this.hueRect.set(f, f2, this.drawingRect.right - 1.0f, f3);
    }

    private final void onColorChanged() {
        OnColorChangedListener onColorChangedListener = this.onColorChangedListener;
        if (onColorChangedListener == null) {
            return;
        }
        onColorChangedListener.colorChanged(getSelectedColor());
    }

    public final void setOnColorChangedListener(OnColorChangedListener listener) {
        this.onColorChangedListener = listener;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.drawingRect.width() <= 0.0f || this.drawingRect.height() <= 0.0f) {
            return;
        }
        drawSatValPanel(canvas);
        drawHuePanel(canvas);
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

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        RectF rectF = new RectF();
        rectF.left = this.drawingOffset + getPaddingLeft();
        rectF.right = (w - this.drawingOffset) - getPaddingRight();
        rectF.top = this.drawingOffset + getPaddingTop();
        rectF.bottom = (h - this.drawingOffset) - getPaddingBottom();
        Unit unit = Unit.INSTANCE;
        this.drawingRect = rectF;
        setUpSatValRect();
        setUpHueRect();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart()) - getPaddingEnd();
        setMeasuredDimension(size, size);
    }
}
