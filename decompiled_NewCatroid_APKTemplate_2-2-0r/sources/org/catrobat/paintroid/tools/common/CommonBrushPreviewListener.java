package org.catrobat.paintroid.tools.common;

import android.graphics.MaskFilter;
import android.graphics.Paint;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;

/* JADX INFO: compiled from: CommonBrushPreviewListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lorg/catrobat/paintroid/tools/common/CommonBrushPreviewListener;", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushPreviewListener;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "(Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/ToolType;)V", TypedValues.Custom.S_COLOR, "", "getColor", "()I", "maskFilter", "Landroid/graphics/MaskFilter;", "getMaskFilter", "()Landroid/graphics/MaskFilter;", "strokeCap", "Landroid/graphics/Paint$Cap;", "getStrokeCap", "()Landroid/graphics/Paint$Cap;", "strokeWidth", "", "getStrokeWidth", "()F", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CommonBrushPreviewListener implements BrushToolOptionsView.OnBrushPreviewListener {
    private final ToolPaint toolPaint;
    private final ToolType toolType;

    public CommonBrushPreviewListener(ToolPaint toolPaint, ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(toolType, "toolType");
        this.toolPaint = toolPaint;
        this.toolType = toolType;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushPreviewListener
    public ToolType getToolType() {
        return this.toolType;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushPreviewListener
    public float getStrokeWidth() {
        return this.toolPaint.getStrokeWidth();
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushPreviewListener
    public Paint.Cap getStrokeCap() {
        return this.toolPaint.getStrokeCap();
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushPreviewListener
    public int getColor() {
        return this.toolPaint.getColor();
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushPreviewListener
    public MaskFilter getMaskFilter() {
        return this.toolPaint.getBitmapPaint().getMaskFilter();
    }
}
