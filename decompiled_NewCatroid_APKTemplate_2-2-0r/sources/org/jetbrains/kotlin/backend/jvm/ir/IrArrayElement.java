package org.jetbrains.kotlin.backend.jvm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: IrArrayBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ir/IrArrayElement;", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isSpread", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Z)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()Z", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IrArrayElement {
    private final IrExpression expression;
    private final boolean isSpread;

    public IrArrayElement(IrExpression expression, boolean z) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.expression = expression;
        this.isSpread = z;
    }

    public final IrExpression getExpression() {
        return this.expression;
    }

    /* JADX INFO: renamed from: isSpread, reason: from getter */
    public final boolean getIsSpread() {
        return this.isSpread;
    }
}
