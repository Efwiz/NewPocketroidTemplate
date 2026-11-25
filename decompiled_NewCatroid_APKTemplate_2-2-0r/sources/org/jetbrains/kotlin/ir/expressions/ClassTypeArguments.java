package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrConstructorCallTypeArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\r\u001a\u00020\tH\u0096\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/ClassTypeArguments;", "Lkotlin/collections/AbstractList;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "irConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)V", "getIrConstructorCall$ir_tree", "()Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "size", "", "getSize", "()I", "get", "index", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassTypeArguments extends AbstractList<IrType> {
    private final IrConstructorCall irConstructorCall;

    public ClassTypeArguments(IrConstructorCall irConstructorCall) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "irConstructorCall");
        this.irConstructorCall = irConstructorCall;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj == null ? true : obj instanceof IrType) {
            return contains((IrType) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(IrType irType) {
        return super.contains(irType);
    }

    /* JADX INFO: renamed from: getIrConstructorCall$ir_tree, reason: from getter */
    public final IrConstructorCall getIrConstructorCall() {
        return this.irConstructorCall;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj == null ? true : obj instanceof IrType) {
            return indexOf((IrType) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(IrType irType) {
        return super.indexOf(irType);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj == null ? true : obj instanceof IrType) {
            return lastIndexOf((IrType) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(IrType irType) {
        return super.lastIndexOf(irType);
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    /* JADX INFO: renamed from: getSize */
    public int get_size() {
        return IrConstructorCallTypeArgumentsKt.getClassTypeArgumentsCount(this.irConstructorCall);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public IrType get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size());
        }
        return this.irConstructorCall.getTypeArgument(index);
    }
}
