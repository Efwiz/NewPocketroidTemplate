package org.catrobat.paintroid.tools.options;

import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import org.catrobat.paintroid.tools.ToolType;

/* JADX INFO: compiled from: BrushToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0014\u0015J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H&J\u0010\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH&J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H&¨\u0006\u0016"}, d2 = {"Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "", "getBottomToolOptions", "Landroid/view/View;", "getTopToolOptions", "hideCaps", "", "invalidate", "setBrushChangedListener", "onBrushChangedListener", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushChangedListener;", "setBrushPreviewListener", "onBrushPreviewListener", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushPreviewListener;", "setCurrentPaint", "paint", "Landroid/graphics/Paint;", "setStrokeCapButtonChecked", "strokeCap", "Landroid/graphics/Paint$Cap;", "OnBrushChangedListener", "OnBrushPreviewListener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface BrushToolOptionsView {

    /* JADX INFO: compiled from: BrushToolOptionsView.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushChangedListener;", "", "setCap", "", "strokeCap", "Landroid/graphics/Paint$Cap;", "setStrokeWidth", "strokeWidth", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface OnBrushChangedListener {
        void setCap(Paint.Cap strokeCap);

        void setStrokeWidth(int strokeWidth);
    }

    /* JADX INFO: compiled from: BrushToolOptionsView.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushPreviewListener;", "", TypedValues.Custom.S_COLOR, "", "getColor", "()I", "maskFilter", "Landroid/graphics/MaskFilter;", "getMaskFilter", "()Landroid/graphics/MaskFilter;", "strokeCap", "Landroid/graphics/Paint$Cap;", "getStrokeCap", "()Landroid/graphics/Paint$Cap;", "strokeWidth", "", "getStrokeWidth", "()F", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface OnBrushPreviewListener {
        int getColor();

        MaskFilter getMaskFilter();

        Paint.Cap getStrokeCap();

        float getStrokeWidth();

        ToolType getToolType();
    }

    View getBottomToolOptions();

    View getTopToolOptions();

    void hideCaps();

    void invalidate();

    void setBrushChangedListener(OnBrushChangedListener onBrushChangedListener);

    void setBrushPreviewListener(OnBrushPreviewListener onBrushPreviewListener);

    void setCurrentPaint(Paint paint);

    void setStrokeCapButtonChecked(Paint.Cap strokeCap);
}
