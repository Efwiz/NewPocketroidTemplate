package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrSuspensionPoint.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u0012\"\u0004\b\u0001\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0017J/\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u0002H\u00130\u00152\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u001aJ)\u0010\u001b\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u00132\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00130\u001d2\u0006\u0010\u0016\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u001eR\u0018\u0010\u0003\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0001X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "()V", "result", "getResult", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setResult", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "resumeResult", "getResumeResult", "setResumeResult", "suspensionPointIdParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getSuspensionPointIdParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "setSuspensionPointIdParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrSuspensionPoint extends IrExpression {
    public abstract IrExpression getResult();

    public abstract IrExpression getResumeResult();

    public abstract IrVariable getSuspensionPointIdParameter();

    public abstract void setResult(IrExpression irExpression);

    public abstract void setResumeResult(IrExpression irExpression);

    public abstract void setSuspensionPointIdParameter(IrVariable irVariable);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSuspensionPoint2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getSuspensionPointIdParameter().accept(visitor, data);
        getResult().accept(visitor, data);
        getResumeResult().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        IrElement irElementTransform = getSuspensionPointIdParameter().transform(transformer, data);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrVariable");
        setSuspensionPointIdParameter((IrVariable) irElementTransform);
        setResult(getResult().transform((IrElementTransformer) transformer, (Object) data));
        setResumeResult(getResumeResult().transform((IrElementTransformer) transformer, (Object) data));
    }
}
