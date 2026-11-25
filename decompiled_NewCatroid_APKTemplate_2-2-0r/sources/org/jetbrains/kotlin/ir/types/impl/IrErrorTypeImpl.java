package org.jetbrains.kotlin.ir.types.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: IrTypeBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B/\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0010\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/impl/IrErrorTypeImpl;", "Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "isMarkedNullable", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Ljava/util/List;Lorg/jetbrains/kotlin/types/Variance;Z)V", "getAnnotations", "()Ljava/util/List;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrErrorTypeImpl extends IrErrorType {
    private final List<IrConstructorCall> annotations;
    private final Variance variance;

    public /* synthetic */ IrErrorTypeImpl(KotlinType kotlinType, List list, Variance variance, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinType, list, variance, (i & 8) != 0 ? false : z);
    }

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
    public IrErrorTypeImpl(KotlinType kotlinType, List<? extends IrConstructorCall> annotations, Variance variance, boolean z) {
        super(kotlinType, IrErrorClassImpl.INSTANCE.getSymbol(), z);
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(variance, "variance");
        this.annotations = annotations;
        this.variance = variance;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public boolean equals(Object other) {
        return other instanceof IrErrorTypeImpl;
    }

    @Override // org.jetbrains.kotlin.ir.types.IrType, org.jetbrains.kotlin.ir.types.IrTypeArgument
    public int hashCode() {
        return IrErrorTypeImpl.class.hashCode();
    }
}
