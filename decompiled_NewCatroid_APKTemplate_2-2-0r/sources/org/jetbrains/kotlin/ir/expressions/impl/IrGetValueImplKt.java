package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;

/* JADX INFO: compiled from: IrGetValueImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"copyWithOffsets", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "newStartOffset", "", "newEndOffset", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrGetValueImplKt {
    public static final IrGetValue copyWithOffsets(IrGetValue irGetValue, int i, int i2) {
        Intrinsics.checkNotNullParameter(irGetValue, "<this>");
        return new IrGetValueImpl(i, i2, irGetValue.getType(), irGetValue.getSymbol(), irGetValue.getOrigin());
    }
}
