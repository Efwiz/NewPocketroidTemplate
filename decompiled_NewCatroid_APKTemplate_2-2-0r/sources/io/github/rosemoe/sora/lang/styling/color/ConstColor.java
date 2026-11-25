package io.github.rosemoe.sora.lang.styling.color;

import android.graphics.Color;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConstColor.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/color/ConstColor;", "Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;", TypedValues.Custom.S_COLOR, "", "(I)V", "", "(Ljava/lang/String;)V", "resolve", "editor", "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstColor implements ResolvableColor {
    private final int color;

    public ConstColor(int i) {
        this.color = i;
    }

    public ConstColor(String color) {
        Intrinsics.checkNotNullParameter(color, "color");
        this.color = Color.parseColor(color);
    }

    @Override // io.github.rosemoe.sora.lang.styling.color.ResolvableColor
    public int resolve(EditorColorScheme editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        return this.color;
    }
}
