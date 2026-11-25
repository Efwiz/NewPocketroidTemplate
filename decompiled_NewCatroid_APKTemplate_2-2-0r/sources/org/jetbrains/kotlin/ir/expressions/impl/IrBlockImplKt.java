package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrBlock;

/* JADX INFO: compiled from: IrBlockImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"addIfNotNull", "", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrBlockImpl;", "statement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "inlineStatement", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrBlockImplKt {
    public static final void addIfNotNull(IrBlockImpl irBlockImpl, IrStatement irStatement) {
        Intrinsics.checkNotNullParameter(irBlockImpl, "<this>");
        if (irStatement != null) {
            irBlockImpl.getStatements().add(irStatement);
        }
    }

    public static final void inlineStatement(IrBlockImpl irBlockImpl, IrStatement statement) {
        Intrinsics.checkNotNullParameter(irBlockImpl, "<this>");
        Intrinsics.checkNotNullParameter(statement, "statement");
        if (statement instanceof IrBlock) {
            irBlockImpl.getStatements().addAll(((IrBlock) statement).getStatements());
        } else {
            irBlockImpl.getStatements().add(statement);
        }
    }
}
