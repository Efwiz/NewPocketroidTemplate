package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SyntheticScopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0001\u0018J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H&J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00062\u0006\u0010\f\u001a\u00020\rH&J,\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\rH&J\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006H&J,\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\rH&J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H&J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "", "getSyntheticConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "getSyntheticConstructors", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "classifierDescriptors", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "contributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getSyntheticExtensionProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "receiverTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getSyntheticMemberFunctions", "getSyntheticStaticFunctions", "functionDescriptors", "contributedFunctions", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticScope {
    ConstructorDescriptor getSyntheticConstructor(ConstructorDescriptor constructor);

    Collection<FunctionDescriptor> getSyntheticConstructors(Collection<? extends DeclarationDescriptor> classifierDescriptors);

    Collection<FunctionDescriptor> getSyntheticConstructors(ClassifierDescriptor contributedClassifier, LookupLocation location);

    Collection<PropertyDescriptor> getSyntheticExtensionProperties(Collection<? extends KotlinType> receiverTypes, LookupLocation location);

    Collection<PropertyDescriptor> getSyntheticExtensionProperties(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location);

    Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes);

    Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location);

    Collection<FunctionDescriptor> getSyntheticStaticFunctions(Collection<? extends DeclarationDescriptor> functionDescriptors);

    Collection<FunctionDescriptor> getSyntheticStaticFunctions(Collection<? extends FunctionDescriptor> contributedFunctions, LookupLocation location);

    /* JADX INFO: compiled from: SyntheticScopes.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016J\u001c\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007H\u0016J\u001e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016J,\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0007H\u0016J,\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0007H\u0016J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope$Default;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "()V", "getSyntheticConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "getSyntheticConstructors", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "classifierDescriptors", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "contributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getSyntheticExtensionProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "receiverTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getSyntheticMemberFunctions", "getSyntheticStaticFunctions", "functionDescriptors", "contributedFunctions", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class Default implements SyntheticScope {
        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public ConstructorDescriptor getSyntheticConstructor(ConstructorDescriptor constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<PropertyDescriptor> getSyntheticExtensionProperties(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<FunctionDescriptor> getSyntheticStaticFunctions(Collection<? extends FunctionDescriptor> contributedFunctions, LookupLocation location) {
            Intrinsics.checkNotNullParameter(contributedFunctions, "contributedFunctions");
            Intrinsics.checkNotNullParameter(location, "location");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<FunctionDescriptor> getSyntheticConstructors(ClassifierDescriptor contributedClassifier, LookupLocation location) {
            Intrinsics.checkNotNullParameter(contributedClassifier, "contributedClassifier");
            Intrinsics.checkNotNullParameter(location, "location");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<PropertyDescriptor> getSyntheticExtensionProperties(Collection<? extends KotlinType> receiverTypes, LookupLocation location) {
            Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
            Intrinsics.checkNotNullParameter(location, "location");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<FunctionDescriptor> getSyntheticMemberFunctions(Collection<? extends KotlinType> receiverTypes) {
            Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<FunctionDescriptor> getSyntheticStaticFunctions(Collection<? extends DeclarationDescriptor> functionDescriptors) {
            Intrinsics.checkNotNullParameter(functionDescriptors, "functionDescriptors");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope
        public Collection<FunctionDescriptor> getSyntheticConstructors(Collection<? extends DeclarationDescriptor> classifierDescriptors) {
            Intrinsics.checkNotNullParameter(classifierDescriptors, "classifierDescriptors");
            return CollectionsKt.emptyList();
        }
    }
}
