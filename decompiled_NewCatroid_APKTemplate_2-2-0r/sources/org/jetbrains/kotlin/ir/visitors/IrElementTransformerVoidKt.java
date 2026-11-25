package org.jetbrains.kotlin.ir.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrElementTransformerVoid.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"transformChildrenVoid", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrElementTransformerVoidKt {
    public static final void transformChildrenVoid(IrElement irElement, IrElementTransformerVoid transformer) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        irElement.transformChildren(transformer, null);
    }
}
