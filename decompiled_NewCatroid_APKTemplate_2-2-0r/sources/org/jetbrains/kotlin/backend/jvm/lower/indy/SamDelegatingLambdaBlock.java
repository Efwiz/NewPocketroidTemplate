package org.jetbrains.kotlin.backend.jvm.lower.indy;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: SamDelegatingLambdaBlock.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0002\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/SamDelegatingLambdaBlock;", "", "()V", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "getBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "ref", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "getRef", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "replaceRefWith", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/NullableDelegatingLambdaBlock;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/RegularDelegatingLambdaBlock;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SamDelegatingLambdaBlock {
    public /* synthetic */ SamDelegatingLambdaBlock(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract IrContainerExpression getBlock();

    public abstract IrFunctionReference getRef();

    public abstract void replaceRefWith(IrExpression expression);

    private SamDelegatingLambdaBlock() {
    }
}
