package io.github.rosemoe.sora.lang.styling.inlayHint;

import android.graphics.Canvas;
import io.github.rosemoe.sora.graphics.Paint;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextInlayHint.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0016J@\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/inlayHint/TextInlayHint;", "Lio/github/rosemoe/sora/lang/styling/inlayHint/InlayHint;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "onMeasure", "", "paint", "Lio/github/rosemoe/sora/graphics/Paint;", "textMetrics", "Landroid/graphics/Paint$FontMetricsInt;", "lineHeight", "", "baseline", "onRender", "", "canvas", "Landroid/graphics/Canvas;", "colorScheme", "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "measuredWidth", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextInlayHint extends InlayHint {
    private final String text;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextInlayHint(String text) {
        super(InlayHintLayoutType.IN_LINE);
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
    }

    public final String getText() {
        return this.text;
    }

    @Override // io.github.rosemoe.sora.lang.styling.inlayHint.InlayHint
    public float onMeasure(Paint paint, android.graphics.Paint.FontMetricsInt textMetrics, int lineHeight, float baseline) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(textMetrics, "textMetrics");
        float spaceWidth = paint.getSpaceWidth() * 0.8f;
        float textSize = paint.getTextSize();
        paint.setTextSizeWrapped(0.75f * textSize);
        float fMeasureText = paint.measureText(this.text) + (spaceWidth * 3);
        paint.setTextSizeWrapped(textSize);
        return fMeasureText;
    }

    @Override // io.github.rosemoe.sora.lang.styling.inlayHint.InlayHint
    public void onRender(Canvas canvas, Paint paint, android.graphics.Paint.FontMetricsInt textMetrics, EditorColorScheme colorScheme, int lineHeight, float baseline, float measuredWidth) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(textMetrics, "textMetrics");
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        float spaceWidth = paint.getSpaceWidth() * 0.8f;
        float textSize = paint.getTextSize();
        paint.setTextSizeWrapped(0.75f * textSize);
        float f = lineHeight / 2.0f;
        float fDescent = (paint.descent() - paint.ascent()) / 2.0f;
        float f2 = f - fDescent;
        float fDescent2 = f2 + paint.descent();
        paint.setColor(colorScheme.getColor(49));
        Paint paint2 = paint;
        canvas.drawRoundRect(spaceWidth, f2, measuredWidth - spaceWidth, f + fDescent, 0.15f, 0.15f, paint2);
        paint.setColor(colorScheme.getColor(50));
        canvas.drawText(this.text, spaceWidth * 1.5f, fDescent2, paint2);
        paint.setTextSizeWrapped(textSize);
    }
}
