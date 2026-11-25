package org.catrobat.paintroid.ui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;

/* JADX INFO: compiled from: Perspective.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201J\u0006\u00102\u001a\u00020\fJ\u000e\u00103\u001a\u00020/2\u0006\u00104\u001a\u000205J\u000e\u00106\u001a\u00020/2\u0006\u00107\u001a\u000205J\u000e\u00108\u001a\u0002052\u0006\u00104\u001a\u000205J\u0010\u00109\u001a\u00020\f2\u0006\u0010:\u001a\u00020\fH\u0002J\u000e\u0010;\u001a\u0002052\u0006\u00107\u001a\u000205J\u000e\u0010<\u001a\u00020/2\u0006\u0010=\u001a\u00020\fJ\u0006\u0010>\u001a\u00020/J\u0016\u0010?\u001a\u00020/2\u0006\u0010@\u001a\u00020\u00032\u0006\u0010A\u001a\u00020\u0003J\u000e\u0010B\u001a\u00020/2\u0006\u0010C\u001a\u00020DJ\u0016\u0010E\u001a\u00020/2\u0006\u0010F\u001a\u00020\f2\u0006\u0010G\u001a\u00020\fR\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\r\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0011\"\u0004\b\u001c\u0010\u0013R$\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u0011\"\u0004\b\u001f\u0010\u0013R\u0011\u0010 \u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b!\u0010\u0011R\u0018\u0010\"\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b#\u0010\u000fR\u0018\u0010$\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b%\u0010\u000fR\u0012\u0010&\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R$\u0010'\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b(\u0010\u000f\u001a\u0004\b)\u0010\u0011\"\u0004\b*\u0010\u0013R\u0012\u0010+\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010,\u001a\u00020\f8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010-\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/catrobat/paintroid/ui/Perspective;", "", "bitmapWidth", "", "bitmapHeight", "(II)V", "callResetScaleAndTransformationOnStartUp", "getCallResetScaleAndTransformationOnStartUp", "()I", "setCallResetScaleAndTransformationOnStartUp", "(I)V", "initialTranslationX", "", "initialTranslationY", "getInitialTranslationY$annotations", "()V", "getInitialTranslationY", "()F", "setInitialTranslationY", "(F)V", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "getMainActivity", "()Lorg/catrobat/paintroid/MainActivity;", "setMainActivity", "(Lorg/catrobat/paintroid/MainActivity;)V", "oldHeight", "getOldHeight", "setOldHeight", "scale", "getScale", "setScale", "scaleForCenterBitmap", "getScaleForCenterBitmap", "surfaceCenterX", "getSurfaceCenterX$annotations", "surfaceCenterY", "getSurfaceCenterY$annotations", "surfaceHeight", "surfaceScale", "getSurfaceScale$annotations", "getSurfaceScale", "setSurfaceScale", "surfaceTranslationX", "surfaceTranslationY", "surfaceWidth", "applyToCanvas", "", "canvas", "Landroid/graphics/Canvas;", "calculateZoomFactor", "convertToCanvasFromSurface", "surfacePoint", "Landroid/graphics/PointF;", "convertToSurfaceFromCanvas", "canvasPoint", "getCanvasPointFromSurfacePoint", "getExactCenterYIgnoreWindowResize", "actualExactCenterY", "getSurfacePointFromCanvasPoint", "multiplyScale", "factor", "resetScaleAndTranslation", "setBitmapDimensions", "width", "height", "setSurfaceFrame", "surfaceFrame", "Landroid/graphics/Rect;", "translate", "dx", "dy", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class Perspective {
    private int bitmapHeight;
    private int bitmapWidth;
    private int callResetScaleAndTransformationOnStartUp;
    private float initialTranslationX;
    private float initialTranslationY;
    private MainActivity mainActivity;
    private float oldHeight;
    public float surfaceCenterX;
    public float surfaceCenterY;
    public int surfaceHeight;
    private float surfaceScale = 1.0f;
    public float surfaceTranslationX;
    public float surfaceTranslationY;
    public int surfaceWidth;

    public static /* synthetic */ void getInitialTranslationY$annotations() {
    }

    public static /* synthetic */ void getSurfaceCenterX$annotations() {
    }

    public static /* synthetic */ void getSurfaceCenterY$annotations() {
    }

    public static /* synthetic */ void getSurfaceScale$annotations() {
    }

    public Perspective(int i, int i2) {
        this.bitmapWidth = i;
        this.bitmapHeight = i2;
    }

    public final float getSurfaceScale() {
        return this.surfaceScale;
    }

    public final void setSurfaceScale(float f) {
        this.surfaceScale = f;
    }

    public final float getInitialTranslationY() {
        return this.initialTranslationY;
    }

    public final void setInitialTranslationY(float f) {
        this.initialTranslationY = f;
    }

    /* JADX INFO: renamed from: getScale, reason: from getter */
    public final float getSurfaceScale() {
        return this.surfaceScale;
    }

    public final synchronized void setScale(float f) {
        this.surfaceScale = Math.max(0.1f, Math.min(100.0f, f));
    }

    public final float getScaleForCenterBitmap() {
        float fIntValue;
        float f;
        Resources resources;
        DisplayMetrics displayMetrics;
        MainActivity mainActivity = this.mainActivity;
        Integer numValueOf = null;
        if (mainActivity != null && (resources = mainActivity.getResources()) != null && (displayMetrics = resources.getDisplayMetrics()) != null) {
            numValueOf = Integer.valueOf(displayMetrics.heightPixels);
        }
        float f2 = 0.0f;
        float fIntValue2 = numValueOf != null ? this.surfaceWidth / numValueOf.intValue() : 0.0f;
        int i = this.bitmapWidth;
        if (fIntValue2 <= i / this.bitmapHeight) {
            fIntValue = this.surfaceWidth;
            f = i;
        } else {
            if (numValueOf != null) {
                fIntValue = numValueOf.intValue();
                f = this.bitmapHeight;
            }
            return Math.max(Math.min(f2, 1.0f), 0.1f);
        }
        f2 = fIntValue / f;
        return Math.max(Math.min(f2, 1.0f), 0.1f);
    }

    public final int getCallResetScaleAndTransformationOnStartUp() {
        return this.callResetScaleAndTransformationOnStartUp;
    }

    public final void setCallResetScaleAndTransformationOnStartUp(int i) {
        this.callResetScaleAndTransformationOnStartUp = i;
    }

    public final float getOldHeight() {
        return this.oldHeight;
    }

    public final void setOldHeight(float f) {
        this.oldHeight = f;
    }

    public final MainActivity getMainActivity() {
        return this.mainActivity;
    }

    public final void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public final synchronized void setSurfaceFrame(Rect surfaceFrame) {
        Intrinsics.checkNotNullParameter(surfaceFrame, "surfaceFrame");
        if (this.surfaceHeight == 0) {
            this.oldHeight = surfaceFrame.bottom;
        }
        this.surfaceWidth = surfaceFrame.right;
        this.surfaceCenterX = surfaceFrame.exactCenterX();
        this.surfaceHeight = surfaceFrame.bottom;
        this.surfaceCenterY = getExactCenterYIgnoreWindowResize(surfaceFrame.exactCenterY());
    }

    public final synchronized void setBitmapDimensions(int width, int height) {
        this.bitmapWidth = width;
        this.bitmapHeight = height;
    }

    public final synchronized void resetScaleAndTranslation() {
        int i;
        this.surfaceScale = 1.0f;
        int i2 = this.surfaceWidth;
        if (i2 == 0 || (i = this.surfaceHeight) == 0) {
            this.surfaceTranslationX = 0.0f;
            this.surfaceTranslationY = 0.0f;
        } else {
            float f = (i2 / 2.0f) - (this.bitmapWidth / 2.0f);
            this.surfaceTranslationX = f;
            this.initialTranslationX = f;
            float f2 = (i / 2.0f) - (this.bitmapHeight / 2.0f);
            this.surfaceTranslationY = f2;
            this.initialTranslationY = f2;
        }
        this.surfaceScale = getScaleForCenterBitmap() * calculateZoomFactor();
    }

    public final synchronized float calculateZoomFactor() {
        Resources resources;
        DisplayMetrics displayMetrics;
        MainActivity mainActivity = this.mainActivity;
        Integer numValueOf = null;
        if (mainActivity != null && (resources = mainActivity.getResources()) != null && (displayMetrics = resources.getDisplayMetrics()) != null) {
            numValueOf = Integer.valueOf(displayMetrics.heightPixels);
        }
        int i = this.bitmapHeight;
        int i2 = this.bitmapWidth;
        if (i > i2) {
            int i3 = this.surfaceHeight;
            if (i > i3) {
                return 1.0f;
            }
            return numValueOf != null ? numValueOf.intValue() / this.bitmapHeight : i3 / i;
        }
        int i4 = this.surfaceWidth;
        if (i2 >= i4) {
            return 1.0f;
        }
        return i4 / i2;
    }

    public final synchronized void multiplyScale(float factor) {
        setScale(this.surfaceScale * factor);
    }

    public final synchronized void translate(float dx, float dy) {
        float f = this.surfaceTranslationX;
        float f2 = this.surfaceScale;
        float f3 = f + (dx / f2);
        this.surfaceTranslationX = f3;
        float f4 = this.surfaceTranslationY + (dy / f2);
        this.surfaceTranslationY = f4;
        float f5 = (this.bitmapWidth / 2.0f) + (((this.surfaceWidth / 2.0f) - 50.0f) / f2);
        float f6 = this.initialTranslationX;
        if (f3 > f5 + f6) {
            this.surfaceTranslationX = f5 + f6;
        } else {
            float f7 = -f5;
            if (f3 < f7 + f6) {
                this.surfaceTranslationX = f7 + f6;
            }
        }
        float f8 = (this.bitmapHeight / 2.0f) + (((this.surfaceHeight / 2.0f) - 50.0f) / f2);
        float f9 = this.initialTranslationY;
        if (f4 > f8 + f9) {
            this.surfaceTranslationY = f8 + f9;
        } else {
            float f10 = -f8;
            if (f4 < f10 + f9) {
                this.surfaceTranslationY = f10 + f9;
            }
        }
    }

    public final synchronized void convertToCanvasFromSurface(PointF surfacePoint) {
        Intrinsics.checkNotNullParameter(surfacePoint, "surfacePoint");
        float f = surfacePoint.x;
        float f2 = this.surfaceCenterX;
        surfacePoint.x = (((f - f2) / this.surfaceScale) + f2) - this.surfaceTranslationX;
        float f3 = surfacePoint.y;
        float f4 = this.surfaceCenterY;
        surfacePoint.y = (((f3 - f4) / this.surfaceScale) + f4) - this.surfaceTranslationY;
    }

    public final synchronized void convertToSurfaceFromCanvas(PointF canvasPoint) {
        Intrinsics.checkNotNullParameter(canvasPoint, "canvasPoint");
        float f = canvasPoint.x + this.surfaceTranslationX;
        float f2 = this.surfaceCenterX;
        canvasPoint.x = ((f - f2) * this.surfaceScale) + f2;
        float f3 = canvasPoint.y + this.surfaceTranslationY;
        float f4 = this.surfaceCenterY;
        canvasPoint.y = ((f3 - f4) * this.surfaceScale) + f4;
    }

    public final synchronized PointF getCanvasPointFromSurfacePoint(PointF surfacePoint) {
        PointF pointF;
        Intrinsics.checkNotNullParameter(surfacePoint, "surfacePoint");
        pointF = new PointF(surfacePoint.x, surfacePoint.y);
        convertToCanvasFromSurface(pointF);
        return pointF;
    }

    public final synchronized PointF getSurfacePointFromCanvasPoint(PointF canvasPoint) {
        PointF pointF;
        Intrinsics.checkNotNullParameter(canvasPoint, "canvasPoint");
        pointF = new PointF(canvasPoint.x, canvasPoint.y);
        convertToSurfaceFromCanvas(pointF);
        return pointF;
    }

    public final synchronized void applyToCanvas(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        float f = this.surfaceScale;
        canvas.scale(f, f, this.surfaceCenterX, this.surfaceCenterY);
        canvas.translate(this.surfaceTranslationX, this.surfaceTranslationY);
    }

    private final float getExactCenterYIgnoreWindowResize(float actualExactCenterY) {
        float f = this.surfaceCenterY;
        return (f != 0.0f && f > actualExactCenterY) ? f : actualExactCenterY;
    }
}
