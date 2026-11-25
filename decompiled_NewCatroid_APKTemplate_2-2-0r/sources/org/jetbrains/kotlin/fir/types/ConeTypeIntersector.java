package org.jetbrains.kotlin.fir.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: ConeTypeIntersector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bJ\u001c\u0010\t\u001a\u00020\n*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0002JJ\u0010\r\u001a\u00020\u000e*\b\u0012\u0004\u0012\u00020\u00040\u000f26\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\n0\u0011H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeIntersector;", "", "()V", "intersectTypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "context", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", SpecTypePair.NAME_types, "", "isStrictSubtypeOf", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "supertype", "removeIfNonSingleErrorOrInRelation", "", "", "predicate", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "candidate", "other", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeIntersector {
    public static final ConeTypeIntersector INSTANCE = new ConeTypeIntersector();

    private ConeTypeIntersector() {
    }

    public final ConeKotlinType intersectTypes(final ConeInferenceContext context, Collection<? extends ConeKotlinType> types) {
        boolean z;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(types, "types");
        int size = types.size();
        if (size == 0) {
            throw new IllegalStateException("Expected some types".toString());
        }
        if (size == 1) {
            return (ConeKotlinType) CollectionsKt.single(types);
        }
        ArrayList arrayList = new ArrayList();
        for (ConeKotlinType coneKotlinType : types) {
            if (coneKotlinType instanceof ConeIntersectionType) {
                arrayList.addAll(((ConeIntersectionType) coneKotlinType).getIntersectedTypes());
            } else {
                arrayList.add(coneKotlinType);
            }
        }
        ArrayList<ConeKotlinType> arrayList2 = arrayList;
        boolean z2 = arrayList2 instanceof Collection;
        if (!z2 || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (((ConeKotlinType) it.next()) instanceof ConeFlexibleType) {
                    if (!z2 || !arrayList2.isEmpty()) {
                        Iterator it2 = arrayList2.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                ConeKotlinType coneKotlinType2 = (ConeKotlinType) it2.next();
                                if (TypeUtilsKt.isRaw(coneKotlinType2) || (coneKotlinType2 instanceof ConeDynamicType)) {
                                    break;
                                }
                            }
                        }
                    }
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                    Iterator it3 = arrayList2.iterator();
                    while (it3.hasNext()) {
                        arrayList3.add(ConeTypeUtilsKt.lowerBoundIfFlexible((ConeKotlinType) it3.next()));
                    }
                    ConeKotlinType coneKotlinTypeIntersectTypes = intersectTypes(context, arrayList3);
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                    Iterator it4 = arrayList2.iterator();
                    while (it4.hasNext()) {
                        arrayList4.add(ConeTypeUtilsKt.upperBoundIfFlexible((ConeKotlinType) it4.next()));
                    }
                    ConeKotlinType coneKotlinTypeIntersectTypes2 = intersectTypes(context, arrayList4);
                    return ConeBuiltinTypeUtilsKt.isNothing(coneKotlinTypeIntersectTypes) ? coneKotlinTypeIntersectTypes2 : TypeUtilsKt.coneFlexibleOrSimpleType(context, coneKotlinTypeIntersectTypes, coneKotlinTypeIntersectTypes2);
                }
            }
        }
        if (!z2 || !arrayList2.isEmpty()) {
            Iterator it5 = arrayList2.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    z = false;
                    break;
                }
                if (!context.isNullableType((ConeKotlinType) it5.next())) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (ConeKotlinType coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default : arrayList2) {
            if (z) {
                coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default = TypeUtilsKt.makeConeTypeDefinitelyNotNullOrNotNull$default(coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default, context, false, 2, null);
            }
            linkedHashSet.add(coneKotlinTypeMakeConeTypeDefinitelyNotNullOrNotNull$default);
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        if (linkedHashSet2.size() == 1) {
            return (ConeKotlinType) CollectionsKt.single(linkedHashSet2);
        }
        List mutableList = CollectionsKt.toMutableList((Collection) linkedHashSet2);
        List list = mutableList;
        removeIfNonSingleErrorOrInRelation(list, new Function2<ConeKotlinType, ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeIntersector.intersectTypes.3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(ConeKotlinType candidate, ConeKotlinType other) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                Intrinsics.checkNotNullParameter(other, "other");
                return Boolean.valueOf(ConeTypeIntersector.INSTANCE.isStrictSubtypeOf(other, context, candidate));
            }
        });
        list.isEmpty();
        ConeKotlinType coneKotlinTypeFindCommonIntersectionType = ConeIntegerLiteralIntersector.INSTANCE.findCommonIntersectionType(list);
        if (coneKotlinTypeFindCommonIntersectionType != null) {
            return coneKotlinTypeFindCommonIntersectionType;
        }
        removeIfNonSingleErrorOrInRelation(list, new Function2<ConeKotlinType, ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.types.ConeTypeIntersector.intersectTypes.6
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(ConeKotlinType candidate, ConeKotlinType other) {
                Intrinsics.checkNotNullParameter(candidate, "candidate");
                Intrinsics.checkNotNullParameter(other, "other");
                return Boolean.valueOf(AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, context, candidate, other, false, 8, null));
            }
        });
        list.isEmpty();
        ConeKotlinType coneKotlinType3 = (ConeKotlinType) CollectionsKt.singleOrNull(mutableList);
        return coneKotlinType3 == null ? new ConeIntersectionType(list, null, 2, null) : coneKotlinType3;
    }

    private final void removeIfNonSingleErrorOrInRelation(Collection<ConeKotlinType> collection, Function2<? super ConeKotlinType, ? super ConeKotlinType, Boolean> function2) {
        Iterator<ConeKotlinType> it = collection.iterator();
        while (it.hasNext()) {
            ConeKotlinType next = it.next();
            if (!(next instanceof ConeErrorType) || collection.size() <= 1) {
                Collection<ConeKotlinType> collection2 = collection;
                if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
                    for (ConeKotlinType coneKotlinType : collection2) {
                        if (coneKotlinType == next || !function2.invoke(next, coneKotlinType).booleanValue()) {
                        }
                    }
                }
            }
            it.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isStrictSubtypeOf(ConeKotlinType coneKotlinType, ConeTypeContext coneTypeContext, ConeKotlinType coneKotlinType2) {
        ConeTypeContext coneTypeContext2 = coneTypeContext;
        ConeKotlinType coneKotlinType3 = coneKotlinType;
        ConeKotlinType coneKotlinType4 = coneKotlinType2;
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) coneTypeContext2, (KotlinTypeMarker) coneKotlinType3, (KotlinTypeMarker) coneKotlinType4, false, 8, (Object) null) && !AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) coneTypeContext2, (KotlinTypeMarker) coneKotlinType4, (KotlinTypeMarker) coneKotlinType3, false, 8, (Object) null);
    }
}
