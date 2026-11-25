package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\u00032\u0006\u0010\f\u001a\u00020\u000b\u001a&\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\u0006\u0010\t\u001a\u00020\n\u001a.\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\n\u001a\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005\u001a.\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\n\u001a\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u001a&\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u00052\u0006\u0010\t\u001a\u00020\n¨\u0006\u0017"}, d2 = {"collectSyntheticConstructors", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScopes;", "classifierDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "contributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "collectSyntheticExtensionProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "receiverTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "collectSyntheticMemberFunctions", "collectSyntheticStaticFunctions", "functionDescriptors", "contributedFunctions", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SyntheticScopesKt {
    public static final List<PropertyDescriptor> collectSyntheticExtensionProperties(SyntheticScopes syntheticScopes, Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticExtensionProperties(receiverTypes, name2, location));
        }
        return arrayList;
    }

    public static final List<FunctionDescriptor> collectSyntheticMemberFunctions(SyntheticScopes syntheticScopes, Collection<? extends KotlinType> receiverTypes, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticMemberFunctions(receiverTypes, name2, location));
        }
        return arrayList;
    }

    public static final List<FunctionDescriptor> collectSyntheticStaticFunctions(SyntheticScopes syntheticScopes, Collection<? extends FunctionDescriptor> contributedFunctions, LookupLocation location) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(contributedFunctions, "contributedFunctions");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticStaticFunctions(contributedFunctions, location));
        }
        return arrayList;
    }

    public static final List<FunctionDescriptor> collectSyntheticConstructors(SyntheticScopes syntheticScopes, ClassifierDescriptor contributedClassifier, LookupLocation location) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(contributedClassifier, "contributedClassifier");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticConstructors(contributedClassifier, location));
        }
        return arrayList;
    }

    public static final List<PropertyDescriptor> collectSyntheticExtensionProperties(SyntheticScopes syntheticScopes, Collection<? extends KotlinType> receiverTypes, LookupLocation location) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticExtensionProperties(receiverTypes, location));
        }
        return arrayList;
    }

    public static final List<FunctionDescriptor> collectSyntheticMemberFunctions(SyntheticScopes syntheticScopes, Collection<? extends KotlinType> receiverTypes) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(receiverTypes, "receiverTypes");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticMemberFunctions(receiverTypes));
        }
        return arrayList;
    }

    public static final List<FunctionDescriptor> collectSyntheticStaticFunctions(SyntheticScopes syntheticScopes, Collection<? extends DeclarationDescriptor> functionDescriptors) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(functionDescriptors, "functionDescriptors");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticStaticFunctions(functionDescriptors));
        }
        return arrayList;
    }

    public static final List<FunctionDescriptor> collectSyntheticConstructors(SyntheticScopes syntheticScopes, Collection<? extends DeclarationDescriptor> classifierDescriptors) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(classifierDescriptors, "classifierDescriptors");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SyntheticScope) it.next()).getSyntheticConstructors(classifierDescriptors));
        }
        return arrayList;
    }

    public static final List<ConstructorDescriptor> collectSyntheticConstructors(SyntheticScopes syntheticScopes, ConstructorDescriptor constructor) {
        Intrinsics.checkNotNullParameter(syntheticScopes, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Collection<SyntheticScope> scopes = syntheticScopes.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = scopes.iterator();
        while (it.hasNext()) {
            ConstructorDescriptor syntheticConstructor = ((SyntheticScope) it.next()).getSyntheticConstructor(constructor);
            if (syntheticConstructor != null) {
                arrayList.add(syntheticConstructor);
            }
        }
        return arrayList;
    }
}
