package org.jetbrains.kotlin.resolve.calls.inference.constraintPosition;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ConstraintPosition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ValidityConstraintForConstituentType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "typeArgument", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "bound", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getBound", "()Lorg/jetbrains/kotlin/types/KotlinType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPositionKind;", "getTypeArgument", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ValidityConstraintForConstituentType implements ConstraintPosition {
    private final KotlinType bound;
    private final KotlinType typeArgument;
    private final TypeParameterDescriptor typeParameter;

    public ValidityConstraintForConstituentType(KotlinType typeArgument, TypeParameterDescriptor typeParameter, KotlinType bound) {
        Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(bound, "bound");
        this.typeArgument = typeArgument;
        this.typeParameter = typeParameter;
        this.bound = bound;
    }

    public final KotlinType getTypeArgument() {
        return this.typeArgument;
    }

    public final TypeParameterDescriptor getTypeParameter() {
        return this.typeParameter;
    }

    public final KotlinType getBound() {
        return this.bound;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition
    public ConstraintPositionKind getKind() {
        return ConstraintPositionKind.TYPE_BOUND_POSITION;
    }
}
