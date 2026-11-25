package org.catrobat.paintroid.tools;

import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;

/* JADX INFO: compiled from: ToolPaint.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010&\u001a\u00020'H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\u00020\u0007X¦\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\u0011X¦\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\tR\u0012\u0010\u0018\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0013R\u0018\u0010\u001a\u001a\u00020\u001bX¦\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0018\u0010 \u001a\u00020!X¦\u000e¢\u0006\f\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%¨\u0006("}, d2 = {"Lorg/catrobat/paintroid/tools/ToolPaint;", "", "checkeredShader", "Landroid/graphics/Shader;", "getCheckeredShader", "()Landroid/graphics/Shader;", TypedValues.Custom.S_COLOR, "", "getColor", "()I", "setColor", "(I)V", "eraseXfermode", "Landroid/graphics/PorterDuffXfermode;", "getEraseXfermode", "()Landroid/graphics/PorterDuffXfermode;", "paint", "Landroid/graphics/Paint;", "getPaint", "()Landroid/graphics/Paint;", "setPaint", "(Landroid/graphics/Paint;)V", "previewColor", "getPreviewColor", "previewPaint", "getPreviewPaint", "strokeCap", "Landroid/graphics/Paint$Cap;", "getStrokeCap", "()Landroid/graphics/Paint$Cap;", "setStrokeCap", "(Landroid/graphics/Paint$Cap;)V", "strokeWidth", "", "getStrokeWidth", "()F", "setStrokeWidth", "(F)V", "setAntialiasing", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ToolPaint {
    Shader getCheckeredShader();

    int getColor();

    PorterDuffXfermode getEraseXfermode();

    Paint getPaint();

    int getPreviewColor();

    Paint getPreviewPaint();

    Paint.Cap getStrokeCap();

    float getStrokeWidth();

    void setAntialiasing();

    void setColor(int i);

    void setPaint(Paint paint);

    void setStrokeCap(Paint.Cap cap);

    void setStrokeWidth(float f);
}
