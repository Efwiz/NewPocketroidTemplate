package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrExpressionBody.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J/\u0010\u0014\u001a\u00020\u0015\"\u0004\b\u0000\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0016J)\u0010\u0017\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00192\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u001aJ)\u0010\u001b\u001a\u00020\u0015\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u00192\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u001cR\u0018\u0010\u0003\u001a\u00020\u0004X¦\u000e¢\u0006\f\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "()V", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrExpressionBody extends IrBody {
    public abstract IrExpression getExpression();

    public abstract IrFactory getFactory();

    public abstract void setExpression(IrExpression irExpression);

    @Override // org.jetbrains.kotlin.ir.expressions.IrBody, org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrElement transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBody, org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrBody transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitExpressionBody2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBody, org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> IrExpressionBody transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Object objAccept = accept(transformer, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpressionBody");
        return (IrExpressionBody) objAccept;
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        getExpression().accept(visitor, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        setExpression(getExpression().transform((IrElementTransformer) transformer, (Object) data));
    }
}
