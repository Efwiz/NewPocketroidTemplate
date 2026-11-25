package org.jetbrains.kotlin.descriptors.deserialization;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AdditionalClassPartsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u000eJ\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "", "getConstructors", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionsNames", "getSupertypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "None", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AdditionalClassPartsProvider {
    Collection<ClassConstructorDescriptor> getConstructors(ClassDescriptor classDescriptor);

    Collection<SimpleFunctionDescriptor> getFunctions(Name name2, ClassDescriptor classDescriptor);

    Collection<Name> getFunctionsNames(ClassDescriptor classDescriptor);

    Collection<KotlinType> getSupertypes(ClassDescriptor classDescriptor);

    /* JADX INFO: compiled from: AdditionalClassPartsProvider.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider$None;", "Lorg/jetbrains/kotlin/descriptors/deserialization/AdditionalClassPartsProvider;", "()V", "getConstructors", "", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionsNames", "getSupertypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class None implements AdditionalClassPartsProvider {
        public static final None INSTANCE = new None();

        private None() {
        }

        @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
        public Collection<KotlinType> getSupertypes(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
        public Collection<SimpleFunctionDescriptor> getFunctions(Name name2, ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
        public Collection<Name> getFunctionsNames(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.descriptors.deserialization.AdditionalClassPartsProvider
        public Collection<ClassConstructorDescriptor> getConstructors(ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            return CollectionsKt.emptyList();
        }
    }
}
