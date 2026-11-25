package org.jetbrains.kotlin.ir.types.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0096\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrStarProjectionImpl;", "Lorg/jetbrains/kotlin/ir/types/IrStarProjection;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrStarProjectionImpl implements IrStarProjection {
    public static final IrStarProjectionImpl INSTANCE = new IrStarProjectionImpl();

    @Override // org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        return this == other;
    }

    private IrStarProjectionImpl() {
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
