package org.jetbrains.kotlin.backend.jvm.lower.indy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: SamDelegatingLambdaBlock.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/RegularDelegatingLambdaBlock;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/SamDelegatingLambdaBlock;", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "ref", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)V", "getBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "getRef", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "replaceRefWith", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RegularDelegatingLambdaBlock extends SamDelegatingLambdaBlock {
    private final IrContainerExpression block;
    private final IrFunctionReference ref;

    @Override // org.jetbrains.kotlin.backend.jvm.lower.indy.SamDelegatingLambdaBlock
    public IrContainerExpression getBlock() {
        return this.block;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.indy.SamDelegatingLambdaBlock
    public IrFunctionReference getRef() {
        return this.ref;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RegularDelegatingLambdaBlock(IrContainerExpression block, IrFunctionReference ref) {
        super(null);
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(ref, "ref");
        this.block = block;
        this.ref = ref;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.indy.SamDelegatingLambdaBlock
    public void replaceRefWith(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        getBlock().getStatements().set(getBlock().getStatements().size() - 1, expression);
        getBlock().setType(expression.getType());
    }
}
