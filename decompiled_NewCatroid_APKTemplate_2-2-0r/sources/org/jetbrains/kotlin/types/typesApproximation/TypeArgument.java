package org.jetbrains.kotlin.types.typesApproximation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: CapturedTypeApproximation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/types/typesApproximation/TypeArgument;", "", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "inProjection", "Lorg/jetbrains/kotlin/types/KotlinType;", "outProjection", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getInProjection", "()Lorg/jetbrains/kotlin/types/KotlinType;", "isConsistent", "", "()Z", "getOutProjection", "getTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeArgument {
    private final KotlinType inProjection;
    private final KotlinType outProjection;
    private final TypeParameterDescriptor typeParameter;

    public TypeArgument(TypeParameterDescriptor typeParameter, KotlinType inProjection, KotlinType outProjection) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(inProjection, "inProjection");
        Intrinsics.checkNotNullParameter(outProjection, "outProjection");
        this.typeParameter = typeParameter;
        this.inProjection = inProjection;
        this.outProjection = outProjection;
    }

    public final TypeParameterDescriptor getTypeParameter() {
        return this.typeParameter;
    }

    public final KotlinType getInProjection() {
        return this.inProjection;
    }

    public final KotlinType getOutProjection() {
        return this.outProjection;
    }

    public final boolean isConsistent() {
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(this.inProjection, this.outProjection);
    }
}
