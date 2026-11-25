package org.catrobat.paintroid.tools.common;

import android.graphics.Paint;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;

/* JADX INFO: compiled from: CommonBrushChangedListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/paintroid/tools/common/CommonBrushChangedListener;", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushChangedListener;", "tool", "Lorg/catrobat/paintroid/tools/Tool;", "(Lorg/catrobat/paintroid/tools/Tool;)V", "setCap", "", "strokeCap", "Landroid/graphics/Paint$Cap;", "setStrokeWidth", "strokeWidth", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CommonBrushChangedListener implements BrushToolOptionsView.OnBrushChangedListener {
    private final Tool tool;

    public CommonBrushChangedListener(Tool tool) {
        Intrinsics.checkNotNullParameter(tool, "tool");
        this.tool = tool;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushChangedListener
    public void setCap(Paint.Cap strokeCap) {
        Intrinsics.checkNotNullParameter(strokeCap, "strokeCap");
        this.tool.changePaintStrokeCap(strokeCap);
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView.OnBrushChangedListener
    public void setStrokeWidth(int strokeWidth) {
        this.tool.changePaintStrokeWidth(strokeWidth);
    }
}
