package org.jetbrains.kotlin.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: IrElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"transformStatement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrElementsKt {
    public static final IrStatement transformStatement(IrStatement irStatement, IrElementTransformerVoid transformer) {
        Intrinsics.checkNotNullParameter(irStatement, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrElement irElementTransform = irStatement.transform(transformer, null);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
        return (IrStatement) irElementTransform;
    }
}
