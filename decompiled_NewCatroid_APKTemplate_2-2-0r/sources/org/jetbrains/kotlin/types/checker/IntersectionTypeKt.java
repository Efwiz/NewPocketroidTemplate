package org.jetbrains.kotlin.types.checker;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: IntersectionType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u001a\u0014\u0010\u0000\u001a\u00020\u00042\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u001a\u0014\u0010\u0005\u001a\u00020\u00042\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¨\u0006\b"}, d2 = {"intersectTypes", "Lorg/jetbrains/kotlin/types/SimpleType;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "intersectWrappedTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntersectionTypeKt {
    public static final UnwrappedType intersectWrappedTypes(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        Collection<? extends KotlinType> collection = types;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinType) it.next()).unwrap());
        }
        return m7477intersectTypes((List<? extends UnwrappedType>) arrayList);
    }

    public static final SimpleType intersectTypes(List<? extends SimpleType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        UnwrappedType unwrappedTypeM7477intersectTypes = m7477intersectTypes((List<? extends UnwrappedType>) types);
        Intrinsics.checkNotNull(unwrappedTypeM7477intersectTypes, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        return (SimpleType) unwrappedTypeM7477intersectTypes;
    }

    /* JADX INFO: renamed from: intersectTypes, reason: collision with other method in class */
    public static final UnwrappedType m7477intersectTypes(List<? extends UnwrappedType> types) {
        SimpleType lowerBound;
        Intrinsics.checkNotNullParameter(types, "types");
        int size = types.size();
        if (size == 0) {
            throw new IllegalStateException("Expected some types".toString());
        }
        if (size == 1) {
            return (UnwrappedType) CollectionsKt.single((List) types);
        }
        List<? extends UnwrappedType> list = types;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        boolean z = false;
        boolean z2 = false;
        for (UnwrappedType unwrappedType : list) {
            z = z || KotlinTypeKt.isError(unwrappedType);
            if (unwrappedType instanceof SimpleType) {
                lowerBound = (SimpleType) unwrappedType;
            } else if (unwrappedType instanceof FlexibleType) {
                if (DynamicTypesKt.isDynamic(unwrappedType)) {
                    return unwrappedType;
                }
                lowerBound = ((FlexibleType) unwrappedType).getLowerBound();
                z2 = true;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            arrayList.add(lowerBound);
        }
        ArrayList arrayList2 = arrayList;
        if (z) {
            return ErrorUtils.createErrorType(ErrorTypeKind.INTERSECTION_OF_ERROR_TYPES, types.toString());
        }
        if (!z2) {
            return TypeIntersector.INSTANCE.intersectTypes$descriptors(arrayList2);
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList3.add(FlexibleTypesKt.upperIfFlexible((UnwrappedType) it.next()));
        }
        return KotlinTypeFactory.flexibleType(TypeIntersector.INSTANCE.intersectTypes$descriptors(arrayList2), TypeIntersector.INSTANCE.intersectTypes$descriptors(arrayList3));
    }
}
