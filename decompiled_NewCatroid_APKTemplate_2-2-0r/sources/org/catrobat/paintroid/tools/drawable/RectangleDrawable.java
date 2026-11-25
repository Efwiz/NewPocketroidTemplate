package org.catrobat.paintroid.tools.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RectangleDrawable.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/catrobat/paintroid/tools/drawable/RectangleDrawable;", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "()V", "draw", "", "canvas", "Landroid/graphics/Canvas;", "shapeRect", "Landroid/graphics/RectF;", "drawPaint", "Landroid/graphics/Paint;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class RectangleDrawable implements ShapeDrawable {
    @Override // org.catrobat.paintroid.tools.drawable.ShapeDrawable
    public void draw(Canvas canvas, RectF shapeRect, Paint drawPaint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(shapeRect, "shapeRect");
        Intrinsics.checkNotNullParameter(drawPaint, "drawPaint");
        canvas.drawRect(shapeRect, drawPaint);
    }
}
