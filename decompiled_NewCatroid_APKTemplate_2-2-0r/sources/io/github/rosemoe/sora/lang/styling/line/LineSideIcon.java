package io.github.rosemoe.sora.lang.styling.line;

import android.graphics.drawable.Drawable;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LineSideIcon.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/line/LineSideIcon;", "Lio/github/rosemoe/sora/lang/styling/line/LineAnchorStyle;", Constant.LINE, "", "drawable", "Landroid/graphics/drawable/Drawable;", "(ILandroid/graphics/drawable/Drawable;)V", "getDrawable", "()Landroid/graphics/drawable/Drawable;", "getLine", "()I", "setLine", "(I)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LineSideIcon extends LineAnchorStyle {
    private final Drawable drawable;
    private int line;

    public static /* synthetic */ LineSideIcon copy$default(LineSideIcon lineSideIcon, int i, Drawable drawable, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = lineSideIcon.line;
        }
        if ((i2 & 2) != 0) {
            drawable = lineSideIcon.drawable;
        }
        return lineSideIcon.copy(i, drawable);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Drawable getDrawable() {
        return this.drawable;
    }

    public final LineSideIcon copy(int line, Drawable drawable) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        return new LineSideIcon(line, drawable);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LineSideIcon)) {
            return false;
        }
        LineSideIcon lineSideIcon = (LineSideIcon) other;
        return this.line == lineSideIcon.line && Intrinsics.areEqual(this.drawable, lineSideIcon.drawable);
    }

    public int hashCode() {
        return (Integer.hashCode(this.line) * 31) + this.drawable.hashCode();
    }

    public String toString() {
        return "LineSideIcon(line=" + this.line + ", drawable=" + this.drawable + Constant.AFTER_QUTO;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LineSideIcon(int i, Drawable drawable) {
        super(i);
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        this.line = i;
        this.drawable = drawable;
    }

    public final Drawable getDrawable() {
        return this.drawable;
    }

    @Override // io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle
    public int getLine() {
        return this.line;
    }

    @Override // io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle
    public void setLine(int i) {
        this.line = i;
    }
}
