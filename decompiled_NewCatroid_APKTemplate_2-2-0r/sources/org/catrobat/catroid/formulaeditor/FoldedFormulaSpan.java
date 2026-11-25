package org.catrobat.catroid.formulaeditor;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FoldedFormulaSpan.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FoldedFormulaSpan extends ReplacementSpan {
    private final float textSize;

    public FoldedFormulaSpan(float textSize) {
        this.textSize = textSize;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return ((int) paint.measureText(" ... ")) + 16;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color = paint.getColor();
        float fMeasureText = paint.measureText(" ... ");
        paint.setColor(-16763564);
        canvas.drawRoundRect(new RectF(4.0f + x, top + 2, fMeasureText + x + 12.0f, bottom - 2), 8.0f, 8.0f, paint);
        paint.setColor(-5709836);
        canvas.drawText(" ... ", x + 8.0f, y, paint);
        paint.setColor(color);
    }
}
