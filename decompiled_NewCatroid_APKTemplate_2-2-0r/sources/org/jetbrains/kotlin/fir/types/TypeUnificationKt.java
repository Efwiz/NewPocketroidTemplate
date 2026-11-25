package org.jetbrains.kotlin.fir.types;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: TypeUnification.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a<\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\n\u001a\u0014\u0010\u000b\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"doUnify", "", "Lorg/jetbrains/kotlin/fir/FirSession;", "originalTypeProjection", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "typeWithParametersProjection", "targetTypeParameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "result", "", "removeQuestionMark", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "replaceType", "newType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeUnificationKt {

    /* JADX INFO: compiled from: TypeUnification.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProjectionKind.values().length];
            try {
                iArr[ProjectionKind.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionKind.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProjectionKind.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean doUnify(FirSession firSession, ConeTypeProjection originalTypeProjection, ConeTypeProjection typeWithParametersProjection, Set<FirTypeParameterSymbol> targetTypeParameters, Map<FirTypeParameterSymbol, ConeTypeProjection> result) {
        ConeNullability nullability;
        ConeNullability nullability2;
        ConeTypeParameterLookupTag lookupTag;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible2;
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        Intrinsics.checkNotNullParameter(originalTypeProjection, "originalTypeProjection");
        Intrinsics.checkNotNullParameter(typeWithParametersProjection, "typeWithParametersProjection");
        Intrinsics.checkNotNullParameter(targetTypeParameters, "targetTypeParameters");
        Intrinsics.checkNotNullParameter(result, "result");
        ConeKotlinType type = ConeTypeProjectionKt.getType(originalTypeProjection);
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = (type == null || (coneSimpleKotlinTypeLowerBoundIfFlexible2 = ConeTypeUtilsKt.lowerBoundIfFlexible(type)) == null) ? null : TypeExpansionUtilsKt.fullyExpandedType(coneSimpleKotlinTypeLowerBoundIfFlexible2, firSession);
        ConeKotlinType type2 = ConeTypeProjectionKt.getType(typeWithParametersProjection);
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType2 = (type2 == null || (coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(type2)) == null) ? null : TypeExpansionUtilsKt.fullyExpandedType(coneSimpleKotlinTypeLowerBoundIfFlexible, firSession);
        if (coneSimpleKotlinTypeFullyExpandedType instanceof ConeIntersectionType) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (ConeKotlinType coneKotlinType : ((ConeIntersectionType) coneSimpleKotlinTypeFullyExpandedType).getIntersectedTypes()) {
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                if (!doUnify(firSession, coneKotlinType, typeWithParametersProjection, targetTypeParameters, linkedHashMap2)) {
                    return false;
                }
                for (Map.Entry entry : linkedHashMap2.entrySet()) {
                    FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) entry.getKey();
                    TypeArgumentMarker typeArgumentMarker = (ConeTypeProjection) entry.getValue();
                    TypeArgumentMarker typeArgumentMarker2 = (ConeTypeProjection) linkedHashMap.get(firTypeParameterSymbol);
                    if (typeArgumentMarker2 == null || ((typeArgumentMarker instanceof KotlinTypeMarker) && (typeArgumentMarker2 instanceof KotlinTypeMarker) && AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(firSession), (KotlinTypeMarker) typeArgumentMarker, (KotlinTypeMarker) typeArgumentMarker2, false, 8, (Object) null))) {
                        linkedHashMap.put(firTypeParameterSymbol, typeArgumentMarker);
                    }
                }
            }
            for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                result.put((FirTypeParameterSymbol) entry2.getKey(), (ConeTypeProjection) entry2.getValue());
            }
            return true;
        }
        if (originalTypeProjection.getKind() == typeWithParametersProjection.getKind() && originalTypeProjection.getKind() != ProjectionKind.INVARIANT && originalTypeProjection.getKind() != ProjectionKind.STAR) {
            Intrinsics.checkNotNull(coneSimpleKotlinTypeFullyExpandedType);
            Intrinsics.checkNotNull(coneSimpleKotlinTypeFullyExpandedType2);
            return doUnify(firSession, coneSimpleKotlinTypeFullyExpandedType, coneSimpleKotlinTypeFullyExpandedType2, targetTypeParameters, result);
        }
        if ((coneSimpleKotlinTypeFullyExpandedType != null ? coneSimpleKotlinTypeFullyExpandedType.getNullability() : null) == ConeNullability.NULLABLE) {
            if ((coneSimpleKotlinTypeFullyExpandedType2 != null ? coneSimpleKotlinTypeFullyExpandedType2.getNullability() : null) == ConeNullability.NULLABLE) {
                return doUnify(firSession, removeQuestionMark(originalTypeProjection, TypeComponentsKt.getTypeContext(firSession)), removeQuestionMark(typeWithParametersProjection, TypeComponentsKt.getTypeContext(firSession)), targetTypeParameters, result);
            }
        }
        if (originalTypeProjection.getKind() != typeWithParametersProjection.getKind() && typeWithParametersProjection.getKind() != ProjectionKind.INVARIANT) {
            return true;
        }
        if (coneSimpleKotlinTypeFullyExpandedType2 instanceof ConeDefinitelyNotNullType) {
            return doUnify(firSession, originalTypeProjection, replaceType(typeWithParametersProjection, ((ConeDefinitelyNotNullType) coneSimpleKotlinTypeFullyExpandedType2).getOriginal()), targetTypeParameters, result);
        }
        if (!(originalTypeProjection instanceof ConeStarProjection)) {
            if ((coneSimpleKotlinTypeFullyExpandedType != null ? coneSimpleKotlinTypeFullyExpandedType.getNullability() : null) != ConeNullability.NULLABLE) {
                if ((coneSimpleKotlinTypeFullyExpandedType2 != null ? coneSimpleKotlinTypeFullyExpandedType2.getNullability() : null) == ConeNullability.NULLABLE) {
                    return true;
                }
            }
        }
        ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeFullyExpandedType2 instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeFullyExpandedType2 : null;
        FirTypeParameterSymbol typeParameterSymbol = (coneTypeParameterType == null || (lookupTag = coneTypeParameterType.getLookupTag()) == null) ? null : lookupTag.getTypeParameterSymbol();
        if (typeParameterSymbol != null && targetTypeParameters.contains(typeParameterSymbol)) {
            if (result.containsKey(typeParameterSymbol) && !Intrinsics.areEqual(result.get(typeParameterSymbol), originalTypeProjection)) {
                return false;
            }
            result.put(typeParameterSymbol, originalTypeProjection);
            return true;
        }
        if (!Intrinsics.areEqual((coneSimpleKotlinTypeFullyExpandedType == null || (nullability2 = coneSimpleKotlinTypeFullyExpandedType.getNullability()) == null) ? null : Boolean.valueOf(nullability2.isNullable()), (coneSimpleKotlinTypeFullyExpandedType2 == null || (nullability = coneSimpleKotlinTypeFullyExpandedType2.getNullability()) == null) ? null : Boolean.valueOf(nullability.isNullable())) || originalTypeProjection.getKind() != typeWithParametersProjection.getKind()) {
            return true;
        }
        ConeLookupTagBasedType coneLookupTagBasedType = coneSimpleKotlinTypeFullyExpandedType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneSimpleKotlinTypeFullyExpandedType : null;
        ConeClassifierLookupTag lookupTag2 = coneLookupTagBasedType != null ? coneLookupTagBasedType.getLookupTag() : null;
        ConeLookupTagBasedType coneLookupTagBasedType2 = coneSimpleKotlinTypeFullyExpandedType2 instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneSimpleKotlinTypeFullyExpandedType2 : null;
        if (!Intrinsics.areEqual(lookupTag2, coneLookupTagBasedType2 != null ? coneLookupTagBasedType2.getLookupTag() : null) || coneSimpleKotlinTypeFullyExpandedType == null || coneSimpleKotlinTypeFullyExpandedType2 == null || coneSimpleKotlinTypeFullyExpandedType.getTypeArguments().length != coneSimpleKotlinTypeFullyExpandedType2.getTypeArguments().length || coneSimpleKotlinTypeFullyExpandedType.getTypeArguments().length == 0) {
            return true;
        }
        for (Pair pair : ArraysKt.zip(coneSimpleKotlinTypeFullyExpandedType.getTypeArguments(), coneSimpleKotlinTypeFullyExpandedType2.getTypeArguments())) {
            if (!doUnify(firSession, (ConeTypeProjection) pair.component1(), (ConeTypeProjection) pair.component2(), targetTypeParameters, result)) {
                return false;
            }
        }
        return true;
    }

    private static final ConeTypeProjection removeQuestionMark(ConeTypeProjection coneTypeProjection, ConeTypeContext coneTypeContext) {
        ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
        if (type == null || !type.getNullability().isNullable()) {
            throw new IllegalArgumentException(("Expected nullable type, got " + type).toString());
        }
        return replaceType(coneTypeProjection, TypeUtilsKt.withNullability$default(type, ConeNullability.NOT_NULL, coneTypeContext, null, false, 12, null));
    }

    private static final ConeTypeProjection replaceType(ConeTypeProjection coneTypeProjection, ConeKotlinType coneKotlinType) {
        int i = WhenMappings.$EnumSwitchMapping$0[coneTypeProjection.getKind().ordinal()];
        if (i == 1) {
            return coneKotlinType;
        }
        if (i == 2) {
            return new ConeKotlinTypeProjectionIn(coneKotlinType);
        }
        if (i == 3) {
            return new ConeKotlinTypeProjectionOut(coneKotlinType);
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        throw new IllegalStateException("Should not be a star projection".toString());
    }
}
