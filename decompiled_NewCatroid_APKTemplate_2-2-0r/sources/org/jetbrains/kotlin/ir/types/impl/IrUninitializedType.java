package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrUninitializedType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrUninitializedType extends IrType {
    public static final IrUninitializedType INSTANCE = new IrUninitializedType();
    private static final List<IrConstructorCall> annotations = CollectionsKt.emptyList();

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        return this == other;
    }

    private IrUninitializedType() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return annotations;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
