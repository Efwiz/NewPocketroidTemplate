package org.catrobat.paintroid.tools.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import kotlin.Metadata;

/* JADX INFO: compiled from: ShapeDrawable.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "", "draw", "", "canvas", "Landroid/graphics/Canvas;", "shapeRect", "Landroid/graphics/RectF;", "drawPaint", "Landroid/graphics/Paint;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ShapeDrawable {
    void draw(Canvas canvas, RectF shapeRect, Paint drawPaint);
}
