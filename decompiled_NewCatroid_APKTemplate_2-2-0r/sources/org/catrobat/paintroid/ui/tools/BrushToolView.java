package org.catrobat.paintroid.ui.tools;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;
import org.catrobat.paintroid.tools.options.BrushToolPreview;

/* JADX INFO: compiled from: BrushToolView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005B\u001b\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J0\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\u0018\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0014J\u0010\u0010 \u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/BrushToolView;", "Landroid/view/View;", "Lorg/catrobat/paintroid/tools/options/BrushToolPreview;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "callback", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushPreviewListener;", "canvasPaint", "Landroid/graphics/Paint;", "checkeredPattern", "changePaintColor", "", TypedValues.Custom.S_COLOR, "", "drawEraserPreview", "canvas", "Landroid/graphics/Canvas;", "drawLineOnCanvas", "startX", "", "startY", "endX", "endY", "drawLinePreview", "onDraw", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "setListener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class BrushToolView extends View implements BrushToolPreview {
    private BrushToolOptionsView.OnBrushPreviewListener callback;
    private final Paint canvasPaint;
    private final Paint checkeredPattern;

    /* JADX INFO: compiled from: BrushToolView.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ToolType.values().length];
            iArr[ToolType.BRUSH.ordinal()] = 1;
            iArr[ToolType.CURSOR.ordinal()] = 2;
            iArr[ToolType.LINE.ordinal()] = 3;
            iArr[ToolType.WATERCOLOR.ordinal()] = 4;
            iArr[ToolType.ERASER.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BrushToolView(Context context) {
        super(context);
        BitmapShader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.canvasPaint = new Paint();
        Paint paint = new Paint();
        this.checkeredPattern = paint;
        paint.setShader(bitmapShader);
    }

    public BrushToolView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        BitmapShader bitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.canvasPaint = new Paint();
        Paint paint = new Paint();
        this.checkeredPattern = paint;
        paint.setShader(bitmapShader);
    }

    private final void changePaintColor(int color) {
        BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener = this.callback;
        Float fValueOf = onBrushPreviewListener == null ? null : Float.valueOf(onBrushPreviewListener.getStrokeWidth());
        BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener2 = this.callback;
        Paint.Cap strokeCap = onBrushPreviewListener2 != null ? onBrushPreviewListener2.getStrokeCap() : null;
        Paint paint = this.canvasPaint;
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        if (fValueOf != null) {
            paint.setStrokeWidth(fValueOf.floatValue());
        }
        if (strokeCap != null) {
            paint.setStrokeCap(strokeCap);
        }
        paint.setAntiAlias(true);
        if (Color.alpha(color) == 0) {
            paint.setShader(this.checkeredPattern.getShader());
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        } else {
            paint.setColor(color);
        }
    }

    private final void drawEraserPreview(Canvas canvas) {
        changePaintColor(0);
        float right = getRight() - (getWidth() / 3.0f);
        float top = (getTop() + getHeight()) - 56.0f;
        float right2 = getRight() - (getWidth() / 16.0f);
        float top2 = (getTop() + getHeight()) - 56.0f;
        this.canvasPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawLine(right, top, right2, top2, this.canvasPaint);
    }

    private final void drawLineOnCanvas(float startX, float startY, float endX, float endY, Canvas canvas) {
        canvas.drawLine(startX, startY, endX, endY, this.canvasPaint);
    }

    private final void drawLinePreview(Canvas canvas) {
        BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener = this.callback;
        Integer numValueOf = onBrushPreviewListener == null ? null : Integer.valueOf(onBrushPreviewListener.getColor());
        if (numValueOf != null) {
            changePaintColor(numValueOf.intValue());
        }
        BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener2 = this.callback;
        if ((onBrushPreviewListener2 == null ? null : onBrushPreviewListener2.getMaskFilter()) != null) {
            Paint paint = this.canvasPaint;
            BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener3 = this.callback;
            paint.setMaskFilter(onBrushPreviewListener3 != null ? onBrushPreviewListener3.getMaskFilter() : null);
        }
        float right = getRight() - (getWidth() / 3.0f);
        float top = (getTop() + getHeight()) - 56.0f;
        float right2 = getRight() - (getWidth() / 16.0f);
        float top2 = (getTop() + getHeight()) - 56.0f;
        if (this.canvasPaint.getColor() == 0) {
            this.canvasPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            drawLineOnCanvas(right, top, right2, top2, canvas);
            this.canvasPaint.setColor(0);
            return;
        }
        drawLineOnCanvas(right, top, right2, top2, canvas);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener = this.callback;
        ToolType toolType = onBrushPreviewListener == null ? null : onBrushPreviewListener.getToolType();
        int i = toolType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[toolType.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            drawLinePreview(canvas);
        } else {
            if (i != 5) {
                return;
            }
            drawEraserPreview(canvas);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, (int) (((double) size) * 0.25d));
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolPreview
    public void setListener(BrushToolOptionsView.OnBrushPreviewListener callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }
}
