package org.jetbrains.kotlin.backend.jvm.lower.indy;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: SamDelegatingLambdaBlock.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/NullableDelegatingLambdaBlock;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/SamDelegatingLambdaBlock;", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "ref", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "ifExpr", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ifNotNullBlock", "(Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;)V", "getBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "getRef", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "replaceRefWith", "", "expression", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NullableDelegatingLambdaBlock extends SamDelegatingLambdaBlock {
    private final IrContainerExpression block;
    private final IrExpression ifExpr;
    private final IrContainerExpression ifNotNullBlock;
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
    public NullableDelegatingLambdaBlock(IrContainerExpression block, IrFunctionReference ref, IrExpression ifExpr, IrContainerExpression ifNotNullBlock) {
        super(null);
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(ref, "ref");
        Intrinsics.checkNotNullParameter(ifExpr, "ifExpr");
        Intrinsics.checkNotNullParameter(ifNotNullBlock, "ifNotNullBlock");
        this.block = block;
        this.ref = ref;
        this.ifExpr = ifExpr;
        this.ifNotNullBlock = ifNotNullBlock;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.lower.indy.SamDelegatingLambdaBlock
    public void replaceRefWith(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.ifNotNullBlock.getStatements().set(this.ifNotNullBlock.getStatements().size() - 1, expression);
        this.ifNotNullBlock.setType(expression.getType());
        this.ifExpr.setType(expression.getType());
        getBlock().setType(expression.getType());
    }
}
