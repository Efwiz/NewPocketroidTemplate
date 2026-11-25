package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: JvmSafeCallChainFoldingLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ElvisInfo;", "", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "tmpVal", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "elvisLhs", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "elvisRhs", "(Lorg/jetbrains/kotlin/ir/expressions/IrBlock;Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "getElvisLhs", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getElvisRhs", "getTmpVal", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ElvisInfo {
    private final IrBlock block;
    private final IrExpression elvisLhs;
    private final IrExpression elvisRhs;
    private final IrVariable tmpVal;

    public ElvisInfo(IrBlock block, IrVariable tmpVal, IrExpression elvisLhs, IrExpression elvisRhs) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(tmpVal, "tmpVal");
        Intrinsics.checkNotNullParameter(elvisLhs, "elvisLhs");
        Intrinsics.checkNotNullParameter(elvisRhs, "elvisRhs");
        this.block = block;
        this.tmpVal = tmpVal;
        this.elvisLhs = elvisLhs;
        this.elvisRhs = elvisRhs;
    }

    public final IrBlock getBlock() {
        return this.block;
    }

    public final IrVariable getTmpVal() {
        return this.tmpVal;
    }

    public final IrExpression getElvisLhs() {
        return this.elvisLhs;
    }

    public final IrExpression getElvisRhs() {
        return this.elvisRhs;
    }
}
