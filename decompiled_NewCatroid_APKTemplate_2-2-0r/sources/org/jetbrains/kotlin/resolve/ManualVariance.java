package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: VarianceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ManualVariance;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;Lorg/jetbrains/kotlin/types/Variance;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ManualVariance {
    private final TypeParameterDescriptor descriptor;
    private final Variance variance;

    public ManualVariance(TypeParameterDescriptor descriptor, Variance variance) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(variance, "variance");
        this.descriptor = descriptor;
        this.variance = variance;
    }

    public final TypeParameterDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final Variance getVariance() {
        return this.variance;
    }
}
