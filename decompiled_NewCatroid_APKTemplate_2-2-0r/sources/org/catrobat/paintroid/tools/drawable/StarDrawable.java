package org.catrobat.paintroid.tools.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StarDrawable.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/tools/drawable/StarDrawable;", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "()V", "paint", "Landroid/graphics/Paint;", "path", "Landroid/graphics/Path;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "shapeRect", "Landroid/graphics/RectF;", "drawPaint", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class StarDrawable implements ShapeDrawable {
    private final Path path = new Path();
    private final Paint paint = new Paint();

    @Override // org.catrobat.paintroid.tools.drawable.ShapeDrawable
    public void draw(Canvas canvas, RectF shapeRect, Paint drawPaint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(shapeRect, "shapeRect");
        Intrinsics.checkNotNullParameter(drawPaint, "drawPaint");
        this.paint.set(drawPaint);
        float f = 2;
        float fWidth = shapeRect.width() / f;
        float fHeight = shapeRect.height() / f;
        float fHeight2 = shapeRect.height();
        float fWidth2 = shapeRect.width();
        Path path = this.path;
        path.reset();
        path.moveTo(fWidth, 0.0f);
        float f2 = fWidth2 / 8.0f;
        float f3 = fHeight2 / 8.0f;
        float f4 = fHeight - f3;
        path.lineTo(fWidth + f2, f4);
        path.lineTo(fWidth2, f4);
        float f5 = (1.8f * fWidth2) / 8.0f;
        float f6 = f3 + fHeight;
        path.lineTo(fWidth + f5, f6);
        float f7 = (fWidth2 * 3.0f) / 8.0f;
        path.lineTo(fWidth + f7, fHeight2);
        path.lineTo(fWidth, fHeight + ((2.0f * fHeight2) / 8.0f));
        path.lineTo(fWidth - f7, fHeight2);
        path.lineTo(fWidth - f5, f6);
        path.lineTo(0.0f, f4);
        path.lineTo(fWidth - f2, f4);
        path.lineTo(fWidth, 0.0f);
        path.close();
        path.offset(shapeRect.left, shapeRect.top);
        canvas.drawPath(this.path, this.paint);
    }
}
