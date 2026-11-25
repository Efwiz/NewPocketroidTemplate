package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementBase;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrCatch.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J5\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0016J/\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u00120\u00142\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u0019J)\u0010\u001a\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00122\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00120\u001c2\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u001dJ)\u0010\u001e\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u00122\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00120\u001c2\u0006\u0010\u0015\u001a\u0002H\u0012H\u0016¢\u0006\u0002\u0010\u001fR\u0018\u0010\u0004\u001a\u00020\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\u00020\u000bX¦\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "Lorg/jetbrains/kotlin/ir/IrElementBase;", "Lorg/jetbrains/kotlin/ir/IrElement;", "()V", "catchParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getCatchParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "setCatchParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "result", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getResult", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setResult", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrCatch extends IrElementBase implements IrElement {
    public abstract IrVariable getCatchParameter();

    public abstract IrExpression getResult();

    public abstract void setCatchParameter(IrVariable irVariable);

    public abstract void setResult(IrExpression irExpression);

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrElement transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitCatch2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> IrCatch transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Object objAccept = accept(transformer, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCatch");
        return (IrCatch) objAccept;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getCatchParameter().accept(visitor, data);
        getResult().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrElement irElementTransform = getCatchParameter().transform(transformer, data);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
        setCatchParameter((IrVariable) irElementTransform);
        setResult(getResult().transform((IrElementTransformer) transformer, (Object) data));
    }
}
