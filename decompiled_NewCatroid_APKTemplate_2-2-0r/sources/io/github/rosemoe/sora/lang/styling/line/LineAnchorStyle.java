package io.github.rosemoe.sora.lang.styling.line;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: LineAnchorStyle.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\n\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0000H\u0096\u0002R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u0004¨\u0006\u0010"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/line/LineAnchorStyle;", "", Constant.LINE, "", "(I)V", "customData", "", "getCustomData", "()Ljava/lang/Object;", "setCustomData", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getLine", "()I", "setLine", "compareTo", "other", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LineAnchorStyle implements Comparable<LineAnchorStyle> {
    private Object customData;
    private int line;

    public LineAnchorStyle(int i) {
        this.line = i;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int i) {
        this.line = i;
    }

    public final Object getCustomData() {
        return this.customData;
    }

    public final void setCustomData(Object obj) {
        this.customData = obj;
    }

    @Override // java.lang.Comparable
    public int compareTo(LineAnchorStyle other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return Intrinsics.compare(getLine(), other.getLine());
    }
}
