package org.catrobat.catroid.ui.recyclerview.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/util/IndentedBrickLayout.class
  classes14.dex
 */
/* JADX INFO: compiled from: IndentedBrickLayout.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/util/IndentedBrickLayout;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "rawDepth", "", "(Landroid/content/Context;I)V", ConfigConstants.CONFIG_KEY_DEPTH, "indentWidth", "linePaint", "Landroid/graphics/Paint;", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "setDepth", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IndentedBrickLayout extends LinearLayout {
    public static final int MAX_DEPTH = 5;
    private int depth;
    private final int indentWidth;
    private final Paint linePaint;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IndentedBrickLayout(Context context, int i) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        setOrientation(0);
        setWillNotDraw(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.indentWidth = (int) (14 * f);
        int color = ContextCompat.getColor(context, R.color.separator);
        Paint paint = new Paint(1);
        paint.setColor(color);
        paint.setStrokeWidth(f * 1.25f);
        paint.setStyle(Paint.Style.STROKE);
        this.linePaint = paint;
        setDepth(i);
    }

    public final void setDepth(int rawDepth) {
        int iMin = Math.min(rawDepth, 5);
        this.depth = iMin;
        setPadding(iMin * this.indentWidth, 0, 0, 0);
        invalidate();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        if (this.depth > 0) {
            int height = getHeight();
            int i = this.depth;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = this.indentWidth;
                float f = (i2 * i3) + (i3 / 2.0f);
                canvas.drawLine(f, 0.0f, f, height, this.linePaint);
            }
        }
    }
}
