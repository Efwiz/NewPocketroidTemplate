package org.jetbrains.kotlin.ir.expressions;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrErrorCallExpression.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J/\u0010\u0014\u001a\u00020\u0015\"\u0004\b\u0000\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0016J)\u0010\u0017\u001a\u00020\u0015\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00192\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u001aR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "()V", "arguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getArguments", "()Ljava/util/List;", "explicitReceiver", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setExplicitReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrErrorCallExpression extends IrErrorExpression {
    public abstract List<IrExpression> getArguments();

    public abstract IrExpression getExplicitReceiver();

    public abstract void setExplicitReceiver(IrExpression irExpression);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.expressions.IrErrorExpression, org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitErrorCallExpression2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        IrExpression explicitReceiver = getExplicitReceiver();
        if (explicitReceiver != null) {
            explicitReceiver.accept(visitor, data);
        }
        Iterator<T> it = getArguments().iterator();
        while (it.hasNext()) {
            ((IrExpression) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrExpression explicitReceiver = getExplicitReceiver();
        setExplicitReceiver(explicitReceiver != null ? explicitReceiver.transform((IrElementTransformer) transformer, (Object) data) : null);
        TransformKt.transformInPlace(getArguments(), transformer, data);
    }
}
