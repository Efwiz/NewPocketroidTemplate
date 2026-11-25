package org.jetbrains.kotlin.ir.expressions;

import com.reandroid.archive.PathTree;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: IrConstantArray.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\b\"\u0004\b\u0001\u0010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\u000b2\u0006\u0010\f\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\rJ/\u0010\u000e\u001a\u00020\u000f\"\u0004\b\u0000\u0010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002H\t0\u000b2\u0006\u0010\f\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\u0010J)\u0010\u0011\u001a\u00020\u000f\"\u0004\b\u0000\u0010\t2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\t0\u00132\u0006\u0010\f\u001a\u0002H\tH\u0016¢\u0006\u0002\u0010\u0014R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "()V", PathTree.NAME_elements, "", "getElements", "()Ljava/util/List;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)V", "transformChildren", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstantArray extends IrConstantValue {
    public abstract List<IrConstantValue> getElements();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitConstantArray2(this, data);
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void acceptChildren(IrElementVisitor<Unit, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Iterator<T> it = getElements().iterator();
        while (it.hasNext()) {
            ((IrConstantValue) it.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.ir.IrElementBase, org.jetbrains.kotlin.ir.IrElement
    public <D> void transformChildren(IrElementTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        TransformKt.transformInPlace(getElements(), transformer, data);
    }
}
