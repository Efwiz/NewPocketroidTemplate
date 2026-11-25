package org.jetbrains.kotlin.descriptors.deserialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;

/* JADX INFO: compiled from: PlatformDependentDeclarationFilter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\b\tJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "", "isFunctionAvailable", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "All", "NoPlatformDependent", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PlatformDependentDeclarationFilter {
    boolean isFunctionAvailable(ClassDescriptor classDescriptor, SimpleFunctionDescriptor functionDescriptor);

    /* JADX INFO: compiled from: PlatformDependentDeclarationFilter.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter$All;", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "()V", "isFunctionAvailable", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class All implements PlatformDependentDeclarationFilter {
        public static final All INSTANCE = new All();

        @Override // org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter
        public boolean isFunctionAvailable(ClassDescriptor classDescriptor, SimpleFunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return true;
        }

        private All() {
        }
    }

    /* JADX INFO: compiled from: PlatformDependentDeclarationFilter.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter$NoPlatformDependent;", "Lorg/jetbrains/kotlin/descriptors/deserialization/PlatformDependentDeclarationFilter;", "()V", "isFunctionAvailable", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoPlatformDependent implements PlatformDependentDeclarationFilter {
        public static final NoPlatformDependent INSTANCE = new NoPlatformDependent();

        private NoPlatformDependent() {
        }

        @Override // org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter
        public boolean isFunctionAvailable(ClassDescriptor classDescriptor, SimpleFunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return !functionDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME());
        }
    }
}
