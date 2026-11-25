package org.jetbrains.kotlin.com.google.gwt.dev.js.rhino;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CodePosition.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u0000H\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", "", Constant.LINE, "", TypedValues.CycleType.S_WAVE_OFFSET, "(II)V", "getLine", "()I", "getOffset", "compareTo", "other", "toString", "", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodePosition implements Comparable<CodePosition> {
    private final int line;
    private final int offset;

    public CodePosition(int i, int i2) {
        this.line = i;
        this.offset = i2;
    }

    public final int getLine() {
        return this.line;
    }

    public final int getOffset() {
        return this.offset;
    }

    @Override // java.lang.Comparable
    public int compareTo(CodePosition other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int i = this.line;
        int i2 = other.line;
        if (i < i2) {
            return -1;
        }
        if (i > i2) {
            return 1;
        }
        return Intrinsics.compare(this.offset, other.offset);
    }

    public String toString() {
        return "(" + this.line + ", " + this.offset + ')';
    }
}
