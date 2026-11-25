package org.jetbrains.kotlin.ir;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrElementBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00050\u00072\u0006\u0010\b\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\tJ)\u0010\n\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f2\u0006\u0010\b\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\rJ)\u0010\u000e\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\f2\u0006\u0010\b\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/IrElementBase;", "Lorg/jetbrains/kotlin/ir/IrElement;", "()V", "acceptChildren", "", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrElementBase implements IrElement {
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public <D> IrElement transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return (IrElement) accept(transformer, data);
    }
}
