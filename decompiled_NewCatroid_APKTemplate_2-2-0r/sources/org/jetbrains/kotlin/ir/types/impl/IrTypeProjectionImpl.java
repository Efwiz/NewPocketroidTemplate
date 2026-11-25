package org.jetbrains.kotlin.ir.types.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrSimpleTypeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrTypeProjectionImpl;", "Lorg/jetbrains/kotlin/ir/types/IrTypeProjection;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/types/Variance;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTypeProjectionImpl implements IrTypeProjection {
    private final IrType type;
    private final Variance variance;

    public IrTypeProjectionImpl(IrType type, Variance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        this.type = type;
        this.variance = variance;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public Variance getVariance() {
        return this.variance;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        if (other instanceof IrTypeProjectionImpl) {
            IrTypeProjectionImpl irTypeProjectionImpl = (IrTypeProjectionImpl) other;
            if (Intrinsics.areEqual(getType(), irTypeProjectionImpl.getType()) && getVariance() == irTypeProjectionImpl.getVariance()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return (getType().hashCode() * 31) + getVariance().hashCode();
    }
}
