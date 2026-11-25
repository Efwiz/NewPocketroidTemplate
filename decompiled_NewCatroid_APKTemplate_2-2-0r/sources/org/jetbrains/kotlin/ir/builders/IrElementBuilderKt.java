package org.jetbrains.kotlin.ir.builders;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrElementBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"setSourceRange", "", "Lorg/jetbrains/kotlin/ir/builders/IrElementBuilder;", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrElementBuilderKt {
    public static final void setSourceRange(IrElementBuilder irElementBuilder, IrElement from) {
        Intrinsics.checkNotNullParameter(irElementBuilder, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        irElementBuilder.setStartOffset(from.getStartOffset());
        irElementBuilder.setEndOffset(from.getEndOffset());
    }
}
