package org.catrobat.paintroid.tools.implementation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.ToolPaint;

/* JADX INFO: compiled from: DefaultToolPaint.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 ,2\u00020\u0001:\u0001,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010*\u001a\u00020+H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR$\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R$\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000eR\u0014\u0010\u001c\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0017R$\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001e\u001a\u00020\u001f8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R$\u0010$\u001a\u00020%2\u0006\u0010$\u001a\u00020%8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006-"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultToolPaint;", "Lorg/catrobat/paintroid/tools/ToolPaint;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bitmapPaint", "Landroid/graphics/Paint;", "checkeredShader", "Landroid/graphics/Shader;", "getCheckeredShader", "()Landroid/graphics/Shader;", TypedValues.Custom.S_COLOR, "", "getColor", "()I", "setColor", "(I)V", "eraseXfermode", "Landroid/graphics/PorterDuffXfermode;", "getEraseXfermode", "()Landroid/graphics/PorterDuffXfermode;", "paint", "getPaint", "()Landroid/graphics/Paint;", "setPaint", "(Landroid/graphics/Paint;)V", "previewColor", "getPreviewColor", "previewPaint", "getPreviewPaint", "strokeCap", "Landroid/graphics/Paint$Cap;", "getStrokeCap", "()Landroid/graphics/Paint$Cap;", "setStrokeCap", "(Landroid/graphics/Paint$Cap;)V", "strokeWidth", "", "getStrokeWidth", "()F", "setStrokeWidth", "(F)V", "setAntialiasing", "", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultToolPaint implements ToolPaint {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean antialiasing = true;
    private final Paint bitmapPaint;
    private final Context context;

    public DefaultToolPaint(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        Paint paint = new Paint();
        paint.reset();
        paint.setAntiAlias(antialiasing);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(25.0f);
        Unit unit = Unit.INSTANCE;
        this.bitmapPaint = paint;
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public Shader getCheckeredShader() {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pocketpaint_checkeredbg);
        if (bitmapDecodeResource != null) {
            return new BitmapShader(bitmapDecodeResource, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
        return null;
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public Paint getPreviewPaint() {
        Paint paint = new Paint();
        paint.set(this.bitmapPaint);
        return paint;
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public int getPreviewColor() {
        return getPreviewPaint().getColor();
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public int getColor() {
        return this.bitmapPaint.getColor();
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public void setColor(int i) {
        this.bitmapPaint.setColor(i);
        getPreviewPaint().set(this.bitmapPaint);
        getPreviewPaint().setXfermode(null);
        if (Color.alpha(i) == 0) {
            getPreviewPaint().setShader(getCheckeredShader());
            getPreviewPaint().setColor(ViewCompat.MEASURED_STATE_MASK);
            this.bitmapPaint.setXfermode(getEraseXfermode());
            this.bitmapPaint.setAlpha(0);
            return;
        }
        this.bitmapPaint.setXfermode(null);
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public float getStrokeWidth() {
        return this.bitmapPaint.getStrokeWidth();
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public void setStrokeWidth(float f) {
        this.bitmapPaint.setStrokeWidth(f);
        getPreviewPaint().setStrokeWidth(f);
        boolean z = antialiasing;
        if (f <= 1.0f) {
            z = false;
        }
        this.bitmapPaint.setAntiAlias(z);
        getPreviewPaint().setAntiAlias(z);
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public Paint.Cap getStrokeCap() {
        Paint.Cap strokeCap = this.bitmapPaint.getStrokeCap();
        Intrinsics.checkNotNullExpressionValue(strokeCap, "bitmapPaint.strokeCap");
        return strokeCap;
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public void setStrokeCap(Paint.Cap strokeCap) {
        Intrinsics.checkNotNullParameter(strokeCap, "strokeCap");
        this.bitmapPaint.setStrokeCap(strokeCap);
        getPreviewPaint().setStrokeCap(strokeCap);
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    /* JADX INFO: renamed from: getPaint, reason: from getter */
    public Paint getBitmapPaint() {
        return this.bitmapPaint;
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public void setPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        this.bitmapPaint.set(paint);
        getPreviewPaint().set(paint);
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public PorterDuffXfermode getEraseXfermode() {
        return new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    /* JADX INFO: compiled from: DefaultToolPaint.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultToolPaint$Companion;", "", "()V", "antialiasing", "", "getAntialiasing", "()Z", "setAntialiasing", "(Z)V", "arePaintEquals", "paint1", "Landroid/graphics/Paint;", "paint2", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getAntialiasing() {
            return DefaultToolPaint.antialiasing;
        }

        public final void setAntialiasing(boolean z) {
            DefaultToolPaint.antialiasing = z;
        }

        public final boolean arePaintEquals(Paint paint1, Paint paint2) {
            Intrinsics.checkNotNullParameter(paint1, "paint1");
            Intrinsics.checkNotNullParameter(paint2, "paint2");
            return paint1.getColor() == paint2.getColor() && paint1.getStrokeCap() == paint2.getStrokeCap() && paint1.isAntiAlias() == paint2.isAntiAlias() && paint1.getStrokeJoin() == paint2.getStrokeJoin() && paint1.getStyle() == paint2.getStyle();
        }
    }

    @Override // org.catrobat.paintroid.tools.ToolPaint
    public void setAntialiasing() {
        this.bitmapPaint.setAntiAlias(antialiasing);
        getPreviewPaint().setAntiAlias(antialiasing);
    }
}
