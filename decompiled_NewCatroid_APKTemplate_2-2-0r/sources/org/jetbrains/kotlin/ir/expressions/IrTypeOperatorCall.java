package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrTypeOperatorCall.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001aJ/\u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001dJ)\u0010\u001e\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u00162\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00160 2\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010!R\u0018\u0010\u0003\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX¦\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000fX¦\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", "argument", "getArgument", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setArgument", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "operator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "getOperator", "()Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "setOperator", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;)V", "typeOperand", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getTypeOperand", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setTypeOperand", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrTypeOperatorCall extends IrExpression {
    public abstract IrExpression getArgument();

    public abstract IrTypeOperator getOperator();

    public abstract IrType getTypeOperand();

    public abstract void setArgument(IrExpression irExpression);

    public abstract void setOperator(IrTypeOperator irTypeOperator);

    public abstract void setTypeOperand(IrType irType);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeOperator2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getArgument().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setArgument(getArgument().transform((IrElementTransformer) transformer, (Object) data));
    }
}
