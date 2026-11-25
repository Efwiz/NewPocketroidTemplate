package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/TryWithFinallyInfo;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/TryInfo;", "onExit", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getOnExit", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TryWithFinallyInfo extends TryInfo {
    private final IrExpression onExit;

    public TryWithFinallyInfo(IrExpression onExit) {
        Intrinsics.checkNotNullParameter(onExit, "onExit");
        this.onExit = onExit;
    }

    public final IrExpression getOnExit() {
        return this.onExit;
    }
}
