package org.jetbrains.kotlin.ir;

import kotlin.Metadata;
import kotlin.Unit;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J5\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH&¢\u0006\u0002\u0010\u000eJ/\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH&¢\u0006\u0002\u0010\u0011J)\u0010\u0012\u001a\u00020\u0000\"\u0004\b\u0000\u0010\n2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\n0\u00142\u0006\u0010\r\u001a\u0002H\nH&¢\u0006\u0002\u0010\u0015J)\u0010\u0016\u001a\u00020\u0010\"\u0004\b\u0000\u0010\n2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\n0\u00142\u0006\u0010\r\u001a\u0002H\nH&¢\u0006\u0002\u0010\u0017R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/IrElement;", "", "endOffset", "", "getEndOffset", "()I", "startOffset", "getStartOffset", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transform", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/IrElement;", "transformChildren", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrElement {
    <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data);

    <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data);

    int getEndOffset();

    int getStartOffset();

    <D> IrElement transform(IrElementTransformer<? super D> transformer, D data);

    <D> void transformChildren(IrElementTransformer<? super D> transformer, D data);
}
