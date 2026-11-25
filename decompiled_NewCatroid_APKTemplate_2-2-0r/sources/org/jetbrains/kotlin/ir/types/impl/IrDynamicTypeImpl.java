package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrDynamicTypeImpl;", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "(Lorg/jetbrains/kotlin/types/KotlinType;Ljava/util/List;Lorg/jetbrains/kotlin/types/Variance;)V", "getAnnotations", "()Ljava/util/List;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDynamicTypeImpl extends IrDynamicType {
    private final List<IrConstructorCall> annotations;
    private final Variance variance;

    @Override // org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrTypeProjection
    public Variance getVariance() {
        return this.variance;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public IrDynamicTypeImpl(KotlinType kotlinType, List<? extends IrConstructorCall> annotations, Variance variance) {
        super(kotlinType);
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(variance, "variance");
        this.annotations = annotations;
        this.variance = variance;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        return other instanceof IrDynamicTypeImpl;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return IrDynamicTypeImpl.class.hashCode();
    }
}
