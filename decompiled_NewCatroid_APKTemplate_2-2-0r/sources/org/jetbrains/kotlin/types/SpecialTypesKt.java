package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.checker.NewCapturedType;

/* JADX INFO: compiled from: SpecialTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u001a\f\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\u0002\u001a\u000e\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\tH\u0002\u001a\u0014\u0010\b\u001a\u00020\n*\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u0001\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\u0007*\u00020\u0002H\u0002\u001a\u0014\u0010\r\u001a\u00020\u0007*\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u0001\u001a\u0012\u0010\u000e\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007\u001a\n\u0010\u0010\u001a\u00020\u0011*\u00020\u0011\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0012"}, d2 = {"isDefinitelyNotNullType", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)Z", "getAbbreviatedType", "Lorg/jetbrains/kotlin/types/AbbreviatedType;", "getAbbreviation", "Lorg/jetbrains/kotlin/types/SimpleType;", "makeDefinitelyNotNullOrNotNull", "Lorg/jetbrains/kotlin/types/IntersectionTypeConstructor;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "useCorrectedNullabilityForTypeParameters", "makeIntersectionTypeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "withAbbreviation", "abbreviatedType", "withNotNullProjection", "Lorg/jetbrains/kotlin/types/checker/NewCapturedType;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SpecialTypesKt {
    public static final AbbreviatedType getAbbreviatedType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof AbbreviatedType) {
            return (AbbreviatedType) unwrappedTypeUnwrap;
        }
        return null;
    }

    public static final SimpleType getAbbreviation(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        AbbreviatedType abbreviatedType = getAbbreviatedType(kotlinType);
        if (abbreviatedType != null) {
            return abbreviatedType.getAbbreviation();
        }
        return null;
    }

    public static final SimpleType withAbbreviation(SimpleType simpleType, SimpleType abbreviatedType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        Intrinsics.checkNotNullParameter(abbreviatedType, "abbreviatedType");
        return KotlinTypeKt.isError(simpleType) ? simpleType : new AbbreviatedType(simpleType, abbreviatedType);
    }

    public static final boolean isDefinitelyNotNullType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.unwrap() instanceof DefinitelyNotNullType;
    }

    public static /* synthetic */ SimpleType makeSimpleTypeDefinitelyNotNullOrNotNull$default(SimpleType simpleType, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return makeSimpleTypeDefinitelyNotNullOrNotNull(simpleType, z);
    }

    public static final SimpleType makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleType simpleType, boolean z) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$default = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$default(DefinitelyNotNullType.INSTANCE, simpleType, z, false, 4, null);
        if (definitelyNotNullTypeMakeDefinitelyNotNull$default != null) {
            return definitelyNotNullTypeMakeDefinitelyNotNull$default;
        }
        SimpleType simpleTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull = makeIntersectionTypeDefinitelyNotNullOrNotNull(simpleType);
        return simpleTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull == null ? simpleType.makeNullableAsSpecified(false) : simpleTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull;
    }

    public static final NewCapturedType withNotNullProjection(NewCapturedType newCapturedType) {
        Intrinsics.checkNotNullParameter(newCapturedType, "<this>");
        return new NewCapturedType(newCapturedType.getCaptureStatus(), newCapturedType.getConstructor(), newCapturedType.getLowerType(), newCapturedType.getAttributes(), newCapturedType.getIsMarkedNullable(), true);
    }

    public static /* synthetic */ UnwrappedType makeDefinitelyNotNullOrNotNull$default(UnwrappedType unwrappedType, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return makeDefinitelyNotNullOrNotNull(unwrappedType, z);
    }

    public static final UnwrappedType makeDefinitelyNotNullOrNotNull(UnwrappedType unwrappedType, boolean z) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        UnwrappedType unwrappedTypeMakeDefinitelyNotNull$default = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$default(DefinitelyNotNullType.INSTANCE, unwrappedType, z, false, 4, null);
        return (unwrappedTypeMakeDefinitelyNotNull$default == null && (unwrappedTypeMakeDefinitelyNotNull$default = makeIntersectionTypeDefinitelyNotNullOrNotNull(unwrappedType)) == null) ? unwrappedType.makeNullableAsSpecified(false) : unwrappedTypeMakeDefinitelyNotNull$default;
    }

    private static final SimpleType makeIntersectionTypeDefinitelyNotNullOrNotNull(KotlinType kotlinType) {
        IntersectionTypeConstructor intersectionTypeConstructorMakeDefinitelyNotNullOrNotNull;
        TypeConstructor constructor = kotlinType.getConstructor();
        IntersectionTypeConstructor intersectionTypeConstructor = constructor instanceof IntersectionTypeConstructor ? (IntersectionTypeConstructor) constructor : null;
        if (intersectionTypeConstructor == null || (intersectionTypeConstructorMakeDefinitelyNotNullOrNotNull = makeDefinitelyNotNullOrNotNull(intersectionTypeConstructor)) == null) {
            return null;
        }
        return intersectionTypeConstructorMakeDefinitelyNotNullOrNotNull.createType();
    }

    private static final IntersectionTypeConstructor makeDefinitelyNotNullOrNotNull(IntersectionTypeConstructor intersectionTypeConstructor) {
        KotlinType kotlinType;
        Collection<KotlinType> collectionMo7438getSupertypes = intersectionTypeConstructor.mo7438getSupertypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        boolean z = false;
        while (true) {
            kotlinType = null;
            if (!it.hasNext()) {
                break;
            }
            UnwrappedType unwrappedTypeMakeDefinitelyNotNullOrNotNull$default = (KotlinType) it.next();
            if (TypeUtils.isNullableType(unwrappedTypeMakeDefinitelyNotNullOrNotNull$default)) {
                unwrappedTypeMakeDefinitelyNotNullOrNotNull$default = makeDefinitelyNotNullOrNotNull$default(unwrappedTypeMakeDefinitelyNotNullOrNotNull$default.unwrap(), false, 1, null);
                z = true;
            }
            arrayList.add(unwrappedTypeMakeDefinitelyNotNullOrNotNull$default);
        }
        ArrayList arrayList2 = arrayList;
        if (!z) {
            return null;
        }
        UnwrappedType alternativeType = intersectionTypeConstructor.getAlternative();
        if (alternativeType != null) {
            if (TypeUtils.isNullableType(alternativeType)) {
                alternativeType = makeDefinitelyNotNullOrNotNull$default(alternativeType.unwrap(), false, 1, null);
            }
            kotlinType = alternativeType;
        }
        return new IntersectionTypeConstructor(arrayList2).setAlternative(kotlinType);
    }
}
