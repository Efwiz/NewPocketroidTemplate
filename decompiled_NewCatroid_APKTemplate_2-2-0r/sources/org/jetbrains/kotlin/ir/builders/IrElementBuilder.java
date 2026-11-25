package org.jetbrains.kotlin.ir.builders;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrElementBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrElementBuilder;", "", "()V", "endOffset", "", "getEndOffset", "()I", "setEndOffset", "(I)V", "startOffset", "getStartOffset", "setStartOffset", "updateFrom", "", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrElementBuilder {
    private int startOffset = -1;
    private int endOffset = -1;

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final void setStartOffset(int i) {
        this.startOffset = i;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    public final void setEndOffset(int i) {
        this.endOffset = i;
    }

    public final void updateFrom(IrElement from) {
        Intrinsics.checkNotNullParameter(from, "from");
        this.startOffset = from.getStartOffset();
        this.endOffset = from.getEndOffset();
    }
}
