package io.github.rosemoe.sora.lang.styling.line;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.github.rosemoe.sora.lang.styling.color.ResolvableColor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LineBackground.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/line/LineBackground;", "Lio/github/rosemoe/sora/lang/styling/line/LineAnchorStyle;", Constant.LINE, "", TypedValues.Custom.S_COLOR, "Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;", "(ILio/github/rosemoe/sora/lang/styling/color/ResolvableColor;)V", "getColor", "()Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;", "setColor", "(Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;)V", "getLine", "()I", "setLine", "(I)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LineBackground extends LineAnchorStyle {
    private ResolvableColor color;
    private int line;

    public static /* synthetic */ LineBackground copy$default(LineBackground lineBackground, int i, ResolvableColor resolvableColor, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = lineBackground.line;
        }
        if ((i2 & 2) != 0) {
            resolvableColor = lineBackground.color;
        }
        return lineBackground.copy(i, resolvableColor);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ResolvableColor getColor() {
        return this.color;
    }

    public final LineBackground copy(int line, ResolvableColor color) {
        Intrinsics.checkNotNullParameter(color, "color");
        return new LineBackground(line, color);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LineBackground)) {
            return false;
        }
        LineBackground lineBackground = (LineBackground) other;
        return this.line == lineBackground.line && Intrinsics.areEqual(this.color, lineBackground.color);
    }

    public int hashCode() {
        return (Integer.hashCode(this.line) * 31) + this.color.hashCode();
    }

    public String toString() {
        return "LineBackground(line=" + this.line + ", color=" + this.color + Constant.AFTER_QUTO;
    }

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
    public LineBackground(int i, ResolvableColor color) {
        super(i);
        Intrinsics.checkNotNullParameter(color, "color");
        this.line = i;
        this.color = color;
    }
}
