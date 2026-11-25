package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: typeParameterUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\bJ\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00000\u0005R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;", "", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "outerType", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;)V", "getArguments", "()Ljava/util/List;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassifierDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "getOuterType", "()Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;", "segments", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PossiblyInnerType {
    private final List<TypeProjection> arguments;
    private final ClassifierDescriptorWithTypeParameters classifierDescriptor;
    private final PossiblyInnerType outerType;

    /* JADX WARN: Multi-variable type inference failed */
    public PossiblyInnerType(ClassifierDescriptorWithTypeParameters classifierDescriptor, List<? extends TypeProjection> arguments, PossiblyInnerType possiblyInnerType) {
        Intrinsics.checkNotNullParameter(classifierDescriptor, "classifierDescriptor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.classifierDescriptor = classifierDescriptor;
        this.arguments = arguments;
        this.outerType = possiblyInnerType;
    }

    public final ClassifierDescriptorWithTypeParameters getClassifierDescriptor() {
        return this.classifierDescriptor;
    }

    public final List<TypeProjection> getArguments() {
        return this.arguments;
    }

    public final PossiblyInnerType getOuterType() {
        return this.outerType;
    }

    public final ClassDescriptor getClassDescriptor() {
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = this.classifierDescriptor;
        Intrinsics.checkNotNull(classifierDescriptorWithTypeParameters, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        return (ClassDescriptor) classifierDescriptorWithTypeParameters;
    }

    public final List<PossiblyInnerType> segments() {
        PossiblyInnerType possiblyInnerType = this.outerType;
        List<PossiblyInnerType> listSegments = possiblyInnerType != null ? possiblyInnerType.segments() : null;
        if (listSegments == null) {
            listSegments = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection<? extends PossiblyInnerType>) listSegments, this);
    }
}
