package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: VarianceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/VarianceConflictDiagnosticData;", "", "containingType", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "occurrencePosition", "Lorg/jetbrains/kotlin/types/Variance;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/types/Variance;)V", "getContainingType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getOccurrencePosition", "()Lorg/jetbrains/kotlin/types/Variance;", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VarianceConflictDiagnosticData {
    private final KotlinType containingType;
    private final Variance occurrencePosition;
    private final TypeParameterDescriptor typeParameter;

    public VarianceConflictDiagnosticData(KotlinType containingType, TypeParameterDescriptor typeParameter, Variance occurrencePosition) {
        Intrinsics.checkNotNullParameter(containingType, "containingType");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(occurrencePosition, "occurrencePosition");
        this.containingType = containingType;
        this.typeParameter = typeParameter;
        this.occurrencePosition = occurrencePosition;
    }

    public final KotlinType getContainingType() {
        return this.containingType;
    }

    public final TypeParameterDescriptor getTypeParameter() {
        return this.typeParameter;
    }

    public final Variance getOccurrencePosition() {
        return this.occurrencePosition;
    }
}
