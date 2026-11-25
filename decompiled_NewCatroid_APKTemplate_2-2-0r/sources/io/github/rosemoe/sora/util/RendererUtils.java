package io.github.rosemoe.sora.util;

import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.color.ResolvableColor;
import io.github.rosemoe.sora.lang.styling.span.SpanColorResolver;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RendererUtils.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\n"}, d2 = {"Lio/github/rosemoe/sora/util/RendererUtils;", "", "()V", "getBackgroundColor", "", "span", "Lio/github/rosemoe/sora/lang/styling/Span;", "colorScheme", "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "getForegroundColor", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RendererUtils {
    public static final RendererUtils INSTANCE = new RendererUtils();

    private RendererUtils() {
    }

    @JvmStatic
    public static final int getBackgroundColor(Span span, EditorColorScheme colorScheme) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        SpanColorResolver spanColorResolver = (SpanColorResolver) span.getSpanExt(0);
        if (spanColorResolver == null) {
            return colorScheme.getColor(span.getBackgroundColorId());
        }
        ResolvableColor backgroundColor = spanColorResolver.getBackgroundColor(span);
        if (backgroundColor == null) {
            return colorScheme.getColor(span.getBackgroundColorId());
        }
        return backgroundColor.resolve(colorScheme);
    }

    @JvmStatic
    public static final int getForegroundColor(Span span, EditorColorScheme colorScheme) {
        Intrinsics.checkNotNullParameter(span, "span");
        Intrinsics.checkNotNullParameter(colorScheme, "colorScheme");
        SpanColorResolver spanColorResolver = (SpanColorResolver) span.getSpanExt(0);
        if (spanColorResolver == null) {
            return colorScheme.getColor(span.getForegroundColorId());
        }
        ResolvableColor foregroundColor = spanColorResolver.getForegroundColor(span);
        if (foregroundColor == null) {
            return colorScheme.getColor(span.getForegroundColorId());
        }
        return foregroundColor.resolve(colorScheme);
    }
}
