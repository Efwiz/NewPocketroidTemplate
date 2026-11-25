package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrSuspendableExpression.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011J/\u0010\u0012\u001a\u00020\u0013\"\u0004\b\u0000\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0014J)\u0010\u0015\u001a\u00020\u0013\"\u0004\b\u0000\u0010\r2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\r0\u00172\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0018R\u0018\u0010\u0003\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrSuspendableExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", "result", "getResult", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setResult", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "suspensionPointId", "getSuspensionPointId", "setSuspensionPointId", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrSuspendableExpression extends IrExpression {
    public abstract IrExpression getResult();

    public abstract IrExpression getSuspensionPointId();

    public abstract void setResult(IrExpression irExpression);

    public abstract void setSuspensionPointId(IrExpression irExpression);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSuspendableExpression2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getSuspensionPointId().accept(visitor, data);
        getResult().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setSuspensionPointId(getSuspensionPointId().transform((IrElementTransformer) transformer, (Object) data));
        setResult(getResult().transform((IrElementTransformer) transformer, (Object) data));
    }
}
