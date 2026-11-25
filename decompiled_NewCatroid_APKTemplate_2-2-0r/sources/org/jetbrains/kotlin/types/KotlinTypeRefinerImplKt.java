package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor;

/* JADX INFO: compiled from: KotlinTypeRefinerImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002\u001a\u0014\u0010\u0007\u001a\u00020\b*\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\n"}, d2 = {"allDependentTypeConstructors", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getAllDependentTypeConstructors", "(Lorg/jetbrains/kotlin/types/TypeConstructor;)Ljava/util/Collection;", "isExpectClass", "", "restoreAdditionalTypeInformation", "Lorg/jetbrains/kotlin/types/KotlinType;", "prototype", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinTypeRefinerImplKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<TypeConstructor> getAllDependentTypeConstructors(TypeConstructor typeConstructor) {
        if (typeConstructor instanceof NewCapturedTypeConstructor) {
            NewCapturedTypeConstructor newCapturedTypeConstructor = (NewCapturedTypeConstructor) typeConstructor;
            List<UnwrappedType> listMo7438getSupertypes = newCapturedTypeConstructor.mo7438getSupertypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listMo7438getSupertypes, 10));
            Iterator<T> it = listMo7438getSupertypes.iterator();
            while (it.hasNext()) {
                arrayList.add(((UnwrappedType) it.next()).getConstructor());
            }
            return CollectionsKt.plus((Collection<? extends TypeConstructor>) arrayList, newCapturedTypeConstructor.getProjection().getNullableAnyType().getConstructor());
        }
        Collection<KotlinType> collectionMo7438getSupertypes = typeConstructor.mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it2 = collection.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((KotlinType) it2.next()).getConstructor());
        }
        return arrayList2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isExpectClass(TypeConstructor typeConstructor) {
        ClassifierDescriptor this$0 = typeConstructor.getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        return classDescriptor != null && classDescriptor.get$isExpect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final KotlinType restoreAdditionalTypeInformation(KotlinType kotlinType, KotlinType kotlinType2) {
        KotlinType kotlinTypeMakeNullableAsSpecified = TypeUtils.makeNullableAsSpecified(kotlinType, kotlinType2.getIsMarkedNullable());
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullableAsSpecified, "makeNullableAsSpecified(...)");
        return TypeSubstitutionKt.replace$default(kotlinTypeMakeNullableAsSpecified, kotlinType2.getArguments(), null, null, 6, null);
    }
}
