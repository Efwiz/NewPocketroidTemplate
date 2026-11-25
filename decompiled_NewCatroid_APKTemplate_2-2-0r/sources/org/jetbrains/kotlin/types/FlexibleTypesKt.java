package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.checker.ErrorTypesAreEqualToAnything;

/* JADX INFO: compiled from: flexibleTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0004*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\u0002\u001a\u0012\u0010\b\u001a\u0004\u0018\u00010\u0002*\b\u0012\u0004\u0012\u00020\u00020\t\u001a\u0012\u0010\b\u001a\u0004\u0018\u00010\n*\b\u0012\u0004\u0012\u00020\n0\t\u001a\n\u0010\u000b\u001a\u00020\u0007*\u00020\u0002¨\u0006\f"}, d2 = {"asFlexibleType", "Lorg/jetbrains/kotlin/types/FlexibleType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "isFlexible", "", "isNullabilityFlexible", "lowerIfFlexible", "Lorg/jetbrains/kotlin/types/SimpleType;", "singleBestRepresentative", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "upperIfFlexible", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FlexibleTypesKt {
    public static final boolean isFlexible(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.unwrap() instanceof FlexibleType;
    }

    public static final FlexibleType asFlexibleType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        Intrinsics.checkNotNull(unwrappedTypeUnwrap, "null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
        return (FlexibleType) unwrappedTypeUnwrap;
    }

    public static final boolean isNullabilityFlexible(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        FlexibleType flexibleType = unwrappedTypeUnwrap instanceof FlexibleType ? (FlexibleType) unwrappedTypeUnwrap : null;
        return (flexibleType == null || flexibleType.getLowerBound().getIsMarkedNullable() == flexibleType.getUpperBound().getIsMarkedNullable()) ? false : true;
    }

    public static final KotlinType singleBestRepresentative(Collection<? extends KotlinType> collection) {
        Object next;
        Intrinsics.checkNotNullParameter(collection, "<this>");
        if (collection.size() == 1) {
            return (KotlinType) CollectionsKt.first(collection);
        }
        Collection<? extends KotlinType> collection2 = collection;
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            next = it.next();
            KotlinType kotlinType = (KotlinType) next;
            if (!collection2.isEmpty()) {
                Iterator<T> it2 = collection2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        KotlinType kotlinType2 = (KotlinType) it2.next();
                        if (Intrinsics.areEqual(kotlinType, kotlinType2) || ErrorTypesAreEqualToAnything.INSTANCE.equalTypes(kotlinType, kotlinType2)) {
                        }
                    }
                }
            }
            return (KotlinType) next;
        }
        next = null;
        return (KotlinType) next;
    }

    /* JADX INFO: renamed from: singleBestRepresentative, reason: collision with other method in class */
    public static final TypeProjection m7472singleBestRepresentative(Collection<? extends TypeProjection> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        if (collection.size() == 1) {
            return (TypeProjection) CollectionsKt.first(collection);
        }
        Collection<? extends TypeProjection> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getProjectionKind());
        }
        Set set = CollectionsKt.toSet(arrayList);
        if (set.size() != 1) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it2 = collection2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((TypeProjection) it2.next()).getType());
        }
        KotlinType kotlinTypeSingleBestRepresentative = singleBestRepresentative((Collection<? extends KotlinType>) arrayList2);
        if (kotlinTypeSingleBestRepresentative == null) {
            return null;
        }
        return new TypeProjectionImpl((Variance) CollectionsKt.single(set), kotlinTypeSingleBestRepresentative);
    }

    public static final SimpleType lowerIfFlexible(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            return ((FlexibleType) unwrappedTypeUnwrap).getLowerBound();
        }
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return (SimpleType) unwrappedTypeUnwrap;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final SimpleType upperIfFlexible(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            return ((FlexibleType) unwrappedTypeUnwrap).getUpperBound();
        }
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            return (SimpleType) unwrappedTypeUnwrap;
        }
        throw new NoWhenBranchMatchedException();
    }
}
