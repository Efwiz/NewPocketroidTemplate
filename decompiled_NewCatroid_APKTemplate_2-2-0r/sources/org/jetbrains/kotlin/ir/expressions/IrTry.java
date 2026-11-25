package org.jetbrains.kotlin.ir.expressions;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrTry.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016J/\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0019J)\u0010\u001a\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u00122\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00120\u001c2\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u001dR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\f¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", "catches", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "getCatches", "()Ljava/util/List;", "finallyExpression", "getFinallyExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setFinallyExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "tryResult", "getTryResult", "setTryResult", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrTry extends IrExpression {
    public abstract List<IrCatch> getCatches();

    public abstract IrExpression getFinallyExpression();

    public abstract IrExpression getTryResult();

    public abstract void setFinallyExpression(IrExpression irExpression);

    public abstract void setTryResult(IrExpression irExpression);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTry2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getTryResult().accept(visitor, data);
        Iterator<T> it = getCatches().iterator();
        while (it.hasNext()) {
            ((IrCatch) it.next()).accept(visitor, data);
        }
        IrExpression finallyExpression = getFinallyExpression();
        if (finallyExpression != null) {
            finallyExpression.accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setTryResult(getTryResult().transform((IrElementTransformer) transformer, (Object) data));
        TransformKt.transformInPlace(getCatches(), transformer, data);
        IrExpression finallyExpression = getFinallyExpression();
        setFinallyExpression(finallyExpression != null ? finallyExpression.transform((IrElementTransformer) transformer, (Object) data) : null);
    }
}
