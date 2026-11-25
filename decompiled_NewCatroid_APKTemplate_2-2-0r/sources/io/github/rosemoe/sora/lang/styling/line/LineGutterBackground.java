package io.github.rosemoe.sora.lang.styling.line;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.github.rosemoe.sora.lang.styling.color.ResolvableColor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LineGutterBackground.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/line/LineGutterBackground;", "Lio/github/rosemoe/sora/lang/styling/line/LineAnchorStyle;", Constant.LINE, "", TypedValues.Custom.S_COLOR, "Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;", "(ILio/github/rosemoe/sora/lang/styling/color/ResolvableColor;)V", "getColor", "()Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;", "setColor", "(Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;)V", "getLine", "()I", "setLine", "(I)V", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LineGutterBackground extends LineAnchorStyle {
    private ResolvableColor color;
    private int line;

    public final ResolvableColor getColor() {
        return this.color;
    }

    @Override // io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle
    public int getLine() {
        return this.line;
    }

    public final void setColor(ResolvableColor resolvableColor) {
        Intrinsics.checkNotNullParameter(resolvableColor, "<set-?>");
        this.color = resolvableColor;
    }

    @Override // io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle
    public void setLine(int i) {
        this.line = i;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LineGutterBackground(int i, ResolvableColor color) {
        super(i);
        Intrinsics.checkNotNullParameter(color, "color");
        this.line = i;
        this.color = color;
    }
}
