package org.jetbrains.kotlin.fir.types;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;

/* JADX INFO: compiled from: ConeIntegerLiteralTypeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004H\u0002J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00122\u0006\u0010\u0013\u001a\u00020\tJ\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00042\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0016J\u0016\u0010\u0017\u001a\u00020\u0018*\u00020\u00182\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u0014\u0010\u001b\u001a\u00020\u000e*\u00020\t2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aJ\u001a\u0010\u001c\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 ¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralTypeExtensions;", "", "()V", "commonSuperType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "left", "right", "commonSuperTypeBetweenIntegerTypeAndRegularType", "integerLiteralType", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralType;", "regularType", "commonSuperTypeBetweenTwoConstantTypes", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralConstantType;", "createClassLikeType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "createSupertypeList", "", ModuleXmlParser.TYPE, "findCommonSuperType", SpecTypePair.NAME_types, "", "approximateIntegerLiteralBounds", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getApproximatedTypeImpl", "withNullabilityAndAttributes", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "attributes", "Lorg/jetbrains/kotlin/fir/types/ConeAttributes;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ConeIntegerLiteralTypeExtensions {
    public static final ConeIntegerLiteralTypeExtensions INSTANCE = new ConeIntegerLiteralTypeExtensions();

    private ConeIntegerLiteralTypeExtensions() {
    }

    public final List<ConeClassLikeType> createSupertypeList(ConeIntegerLiteralType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return CollectionsKt.listOf((Object[]) new ConeClassLikeType[]{createClassLikeType(StandardClassIds.INSTANCE.getNumber()), new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(StandardClassIds.INSTANCE.getComparable()), new ConeKotlinTypeProjectionIn[]{new ConeKotlinTypeProjectionIn(type)}, false, null, 8, null)});
    }

    public final ConeClassLikeType createClassLikeType(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), new ConeTypeProjection[0], false, null, 8, null);
    }

    public final ConeClassLikeType getApproximatedTypeImpl(ConeIntegerLiteralType coneIntegerLiteralType, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneIntegerLiteralType, "<this>");
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = coneKotlinType != null ? ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType) : null;
        ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
        ConeClassLikeType coneClassLikeTypeWithNullabilityAndAttributes = coneClassLikeType != null ? withNullabilityAndAttributes(coneClassLikeType, ConeNullability.NOT_NULL, ConeAttributes.INSTANCE.getEmpty()) : null;
        if (coneClassLikeTypeWithNullabilityAndAttributes == null || !coneIntegerLiteralType.getPossibleTypes().contains(coneClassLikeTypeWithNullabilityAndAttributes)) {
            coneClassLikeTypeWithNullabilityAndAttributes = (ConeClassLikeType) CollectionsKt.first(coneIntegerLiteralType.getPossibleTypes());
        }
        return withNullabilityAndAttributes(coneClassLikeTypeWithNullabilityAndAttributes, coneIntegerLiteralType.getNullability(), coneIntegerLiteralType.getAttributes());
    }

    public final SimpleTypeMarker findCommonSuperType(Collection<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        if (types.isEmpty()) {
            return null;
        }
        Iterator<T> it = types.iterator();
        if (!it.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it.next();
        while (it.hasNext()) {
            SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) it.next();
            next = INSTANCE.commonSuperType((SimpleTypeMarker) next, simpleTypeMarker);
        }
        return (SimpleTypeMarker) next;
    }

    private final SimpleTypeMarker commonSuperType(SimpleTypeMarker left, SimpleTypeMarker right) {
        if (left == null || right == null) {
            return null;
        }
        boolean z = left instanceof ConeIntegerLiteralType;
        if (z && !(right instanceof ConeIntegerLiteralType)) {
            return commonSuperTypeBetweenIntegerTypeAndRegularType((ConeIntegerLiteralType) left, right);
        }
        if ((right instanceof ConeIntegerLiteralType) && !z) {
            return commonSuperTypeBetweenIntegerTypeAndRegularType((ConeIntegerLiteralType) right, left);
        }
        if ((left instanceof ConeIntegerLiteralConstantType) && (right instanceof ConeIntegerLiteralConstantType)) {
            return commonSuperTypeBetweenTwoConstantTypes((ConeIntegerLiteralConstantType) left, (ConeIntegerLiteralConstantType) right);
        }
        if (left instanceof ConeIntegerConstantOperatorType) {
            return left;
        }
        if (right instanceof ConeIntegerConstantOperatorType) {
            return right;
        }
        return null;
    }

    private final SimpleTypeMarker commonSuperTypeBetweenIntegerTypeAndRegularType(ConeIntegerLiteralType integerLiteralType, SimpleTypeMarker regularType) {
        if (CollectionsKt.contains(integerLiteralType.getPossibleTypes(), regularType)) {
            return regularType;
        }
        return null;
    }

    private final ConeIntegerLiteralConstantType commonSuperTypeBetweenTwoConstantTypes(ConeIntegerLiteralConstantType left, ConeIntegerLiteralConstantType right) {
        return new ConeIntegerLiteralConstantTypeImpl(left.getValue(), CollectionsKt.intersect(left.getPossibleTypes(), right.getPossibleTypes()), left.getIsUnsigned(), left.getNullability());
    }

    public static /* synthetic */ ConeFlexibleType approximateIntegerLiteralBounds$default(ConeIntegerLiteralTypeExtensions coneIntegerLiteralTypeExtensions, ConeFlexibleType coneFlexibleType, ConeKotlinType coneKotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            coneKotlinType = null;
        }
        return coneIntegerLiteralTypeExtensions.approximateIntegerLiteralBounds(coneFlexibleType, coneKotlinType);
    }

    public final ConeFlexibleType approximateIntegerLiteralBounds(ConeFlexibleType coneFlexibleType, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneFlexibleType, "<this>");
        ConeKotlinType coneKotlinTypeApproximateIntegerLiteralType = ConeIntegerLiteralTypeImplKt.approximateIntegerLiteralType(coneFlexibleType.getLowerBound(), coneKotlinType);
        ConeKotlinType coneKotlinTypeApproximateIntegerLiteralType2 = ConeIntegerLiteralTypeImplKt.approximateIntegerLiteralType(coneFlexibleType.getUpperBound(), coneKotlinType);
        return (coneKotlinTypeApproximateIntegerLiteralType == coneFlexibleType.getLowerBound() && coneKotlinTypeApproximateIntegerLiteralType2 == coneFlexibleType.getUpperBound()) ? coneFlexibleType : new ConeFlexibleType(ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinTypeApproximateIntegerLiteralType), ConeTypeUtilsKt.upperBoundIfFlexible(coneKotlinTypeApproximateIntegerLiteralType2));
    }

    public final ConeClassLikeType withNullabilityAndAttributes(ConeClassLikeType coneClassLikeType, ConeNullability nullability, ConeAttributes attributes) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if ((nullability == coneClassLikeType.getNullability() && Intrinsics.areEqual(attributes, coneClassLikeType.getAttributes())) || (coneClassLikeType instanceof ConeErrorType)) {
            return coneClassLikeType;
        }
        if (coneClassLikeType instanceof ConeClassLikeTypeImpl) {
            return new ConeClassLikeTypeImpl(coneClassLikeType.getLookupTag(), coneClassLikeType.getTypeArguments(), nullability.isNullable(), attributes);
        }
        throw new IllegalStateException("sealed".toString());
    }
}
