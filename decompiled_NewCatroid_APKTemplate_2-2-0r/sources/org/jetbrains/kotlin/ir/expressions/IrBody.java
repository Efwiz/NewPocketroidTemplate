package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementBase;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: IrBody.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J)\u0010\u0004\u001a\u00020\u0000\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0006\u0010\b\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "Lorg/jetbrains/kotlin/ir/IrElementBase;", "Lorg/jetbrains/kotlin/ir/IrElement;", "()V", "transform", "D", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBody extends IrElementBase implements IrElement {
    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public /* bridge */ /* synthetic */ IrElement transform(IrElementTransformer irElementTransformer, Object obj) {
        return transform((IrElementTransformer<? super Object>) irElementTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> IrBody transform(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Object objAccept = accept(transformer, data);
        Intrinsics.checkNotNull(objAccept, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
        return (IrBody) objAccept;
    }
}
