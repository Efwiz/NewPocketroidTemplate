package io.github.rosemoe.sora.lang.styling.inlayHint;

import android.graphics.Canvas;
import io.github.rosemoe.sora.graphics.Paint;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: InlayHint.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\nJ(\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\nH&J@\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH&J6\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\nR\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006 "}, d2 = {"Lio/github/rosemoe/sora/lang/styling/inlayHint/InlayHint;", "", ModuleXmlParser.TYPE, "Lio/github/rosemoe/sora/lang/styling/inlayHint/InlayHintLayoutType;", "(Lio/github/rosemoe/sora/lang/styling/inlayHint/InlayHintLayoutType;)V", "<set-?>", "", "measureTimestamp", "getMeasureTimestamp", "()J", "", "measuredWidth", "getMeasuredWidth", "()F", "getType", "()Lio/github/rosemoe/sora/lang/styling/inlayHint/InlayHintLayoutType;", "measure", "paint", "Lio/github/rosemoe/sora/graphics/Paint;", "textMetrics", "Landroid/graphics/Paint$FontMetricsInt;", "lineHeight", "", "baseline", "onMeasure", "onRender", "", "canvas", "Landroid/graphics/Canvas;", "colorScheme", "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "render", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InlayHint {
    private long measureTimestamp;
    private float measuredWidth;
    private final InlayHintLayoutType type;

    public abstract float onMeasure(Paint paint, android.graphics.Paint.FontMetricsInt textMetrics, int lineHeight, float baseline);

    public abstract void onRender(Canvas canvas, Paint paint, android.graphics.Paint.FontMetricsInt textMetrics, EditorColorScheme colorScheme, int lineHeight, float baseline, float measuredWidth);

    public InlayHint(InlayHintLayoutType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    public final InlayHintLayoutType getType() {
        return this.type;
    }

    public final float getMeasuredWidth() {
        return this.measuredWidth;
    }

    public final long getMeasureTimestamp() {
        return this.measureTimestamp;
    }

    public final float measure(Paint paint, android.graphics.Paint.FontMetricsInt textMetrics, int lineHeight, float baseline) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(textMetrics, "textMetrics");
        this.measuredWidth = onMeasure(paint, textMetrics, lineHeight, baseline);
        this.measureTimestamp = System.nanoTime();
        return this.measuredWidth;
    }

    public final void render(Canvas canvas, Paint paint, android.graphics.Paint.FontMetricsInt textMetrics, EditorColorScheme colorScheme, int lineHeight, float baseline) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(textMetrics, "textMetrics");
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        onRender(canvas, paint, textMetrics, colorScheme, lineHeight, baseline, this.measuredWidth);
    }
}
