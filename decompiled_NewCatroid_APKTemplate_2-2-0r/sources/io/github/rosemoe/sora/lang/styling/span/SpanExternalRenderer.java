package io.github.rosemoe.sora.lang.styling.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;

/* JADX INFO: compiled from: SpanExternalRenderer.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J.\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u000bH&J\b\u0010\r\u001a\u00020\u000bH&¨\u0006\u000e"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/span/SpanExternalRenderer;", "Lio/github/rosemoe/sora/lang/styling/span/SpanExt;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "colorScheme", "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "preOrPost", "", "requirePostDraw", "requirePreDraw", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SpanExternalRenderer extends SpanExt {
    void draw(Canvas canvas, Paint paint, EditorColorScheme colorScheme, boolean preOrPost);

    boolean requirePostDraw();

    boolean requirePreDraw();
}
