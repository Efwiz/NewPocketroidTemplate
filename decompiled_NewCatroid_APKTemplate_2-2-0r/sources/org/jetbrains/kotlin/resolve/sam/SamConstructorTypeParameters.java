package org.jetbrains.kotlin.resolve.sam;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: samConstructorUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConstructorTypeParameters;", "", "descriptors", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "(Ljava/util/List;Lorg/jetbrains/kotlin/types/TypeSubstitutor;)V", "getDescriptors", "()Ljava/util/List;", "getSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamConstructorTypeParameters {
    private final List<TypeParameterDescriptor> descriptors;
    private final TypeSubstitutor substitutor;

    /* JADX WARN: Multi-variable type inference failed */
    public SamConstructorTypeParameters(List<? extends TypeParameterDescriptor> descriptors, TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        this.descriptors = descriptors;
        this.substitutor = substitutor;
    }

    public final List<TypeParameterDescriptor> getDescriptors() {
        return this.descriptors;
    }

    public final TypeSubstitutor getSubstitutor() {
        return this.substitutor;
    }
}
