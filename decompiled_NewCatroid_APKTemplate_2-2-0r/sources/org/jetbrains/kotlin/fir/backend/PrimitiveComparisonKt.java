package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.PrimitivesKt;
import org.jetbrains.kotlin.fir.StandardTypes;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: PrimitiveComparison.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001\u001a\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0002\u001a\u000e\u0010\r\u001a\u0004\u0018\u00010\n*\u00020\u000eH\u0002\u001a\f\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u0002\u001a\f\u0010\u000f\u001a\u00020\n*\u00020\nH\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0010"}, d2 = {"left", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "getLeft", "(Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "right", "getRight", "inferPrimitiveNumericComparisonInfo", "Lorg/jetbrains/kotlin/fir/backend/PrimitiveConeNumericComparisonInfo;", "leastCommonPrimitiveNumericType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "t1", "t2", "getPrimitiveTypeOrSupertype", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "promoteIntegerTypeToIntIfRequired", "fir2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveComparisonKt {
    public static final FirExpression getLeft(FirComparisonExpression firComparisonExpression) {
        Intrinsics.checkNotNullParameter(firComparisonExpression, "<this>");
        FirExpression explicitReceiver = firComparisonExpression.getCompareToCall().getExplicitReceiver();
        if (explicitReceiver != null) {
            return explicitReceiver;
        }
        throw new IllegalStateException(("There should be an explicit receiver for " + UtilsKt.render(firComparisonExpression.getCompareToCall())).toString());
    }

    public static final FirExpression getRight(FirComparisonExpression firComparisonExpression) {
        Intrinsics.checkNotNullParameter(firComparisonExpression, "<this>");
        FirExpression firExpression = (FirExpression) CollectionsKt.getOrNull(firComparisonExpression.getCompareToCall().getArgumentList().getArguments(), 0);
        if (firExpression != null) {
            return firExpression;
        }
        throw new IllegalStateException(("There should be a first arg for " + UtilsKt.render(firComparisonExpression.getCompareToCall())).toString());
    }

    public static final PrimitiveConeNumericComparisonInfo inferPrimitiveNumericComparisonInfo(FirComparisonExpression firComparisonExpression) {
        Intrinsics.checkNotNullParameter(firComparisonExpression, "<this>");
        return inferPrimitiveNumericComparisonInfo(getLeft(firComparisonExpression), getRight(firComparisonExpression));
    }

    public static final PrimitiveConeNumericComparisonInfo inferPrimitiveNumericComparisonInfo(FirExpression left, FirExpression right) {
        ConeClassLikeType primitiveTypeOrSupertype;
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(left.getTypeRef());
        ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(right.getTypeRef());
        ConeClassLikeType primitiveTypeOrSupertype2 = getPrimitiveTypeOrSupertype(coneType);
        if (primitiveTypeOrSupertype2 == null || (primitiveTypeOrSupertype = getPrimitiveTypeOrSupertype(coneType2)) == null) {
            return null;
        }
        return new PrimitiveConeNumericComparisonInfo(leastCommonPrimitiveNumericType(primitiveTypeOrSupertype2, primitiveTypeOrSupertype), primitiveTypeOrSupertype2, primitiveTypeOrSupertype);
    }

    private static final ConeClassLikeType leastCommonPrimitiveNumericType(ConeClassLikeType coneClassLikeType, ConeClassLikeType coneClassLikeType2) {
        ConeClassLikeType coneClassLikeTypePromoteIntegerTypeToIntIfRequired = promoteIntegerTypeToIntIfRequired(coneClassLikeType);
        ConeClassLikeType coneClassLikeTypePromoteIntegerTypeToIntIfRequired2 = promoteIntegerTypeToIntIfRequired(coneClassLikeType2);
        if (PrimitivesKt.isDouble(coneClassLikeTypePromoteIntegerTypeToIntIfRequired) || PrimitivesKt.isDouble(coneClassLikeTypePromoteIntegerTypeToIntIfRequired2)) {
            return StandardTypes.INSTANCE.getDouble();
        }
        if (PrimitivesKt.isFloat(coneClassLikeTypePromoteIntegerTypeToIntIfRequired) || PrimitivesKt.isFloat(coneClassLikeTypePromoteIntegerTypeToIntIfRequired2)) {
            return StandardTypes.INSTANCE.getFloat();
        }
        if (PrimitivesKt.isLong(coneClassLikeTypePromoteIntegerTypeToIntIfRequired) || PrimitivesKt.isLong(coneClassLikeTypePromoteIntegerTypeToIntIfRequired2)) {
            return StandardTypes.INSTANCE.getLong();
        }
        if (PrimitivesKt.isInt(coneClassLikeTypePromoteIntegerTypeToIntIfRequired) || PrimitivesKt.isInt(coneClassLikeTypePromoteIntegerTypeToIntIfRequired2)) {
            return StandardTypes.INSTANCE.getInt();
        }
        throw new IllegalStateException(("Unexpected types: t1=" + coneClassLikeType + ", t2=" + coneClassLikeType2).toString());
    }

    private static final ConeClassLikeType promoteIntegerTypeToIntIfRequired(ConeClassLikeType coneClassLikeType) {
        ClassId classId = coneClassLikeType.getLookupTag().getClassId();
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getByte()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getShort())) {
            return StandardTypes.INSTANCE.getInt();
        }
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getLong()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getInt()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getFloat()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getDouble()) ? true : Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getChar())) {
            return coneClassLikeType;
        }
        throw new IllegalStateException(("Primitive number type expected: " + coneClassLikeType).toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0041, code lost:
    
        if (org.jetbrains.kotlin.fir.PrimitivesKt.isPrimitiveNumberType(r0) != false) goto L14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final org.jetbrains.kotlin.fir.types.ConeClassLikeType getPrimitiveTypeOrSupertype(org.jetbrains.kotlin.fir.types.ConeKotlinType r3) {
        /*
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.types.ConeTypeParameterType
            r1 = 0
            if (r0 == 0) goto L36
            org.jetbrains.kotlin.fir.types.ConeTypeParameterType r3 = (org.jetbrains.kotlin.fir.types.ConeTypeParameterType) r3
            org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag r3 = r3.getLookupTag()
            org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol r3 = r3.getTypeParameterSymbol()
            org.jetbrains.kotlin.fir.declarations.FirDeclaration r3 = r3.getFir()
            org.jetbrains.kotlin.fir.declarations.FirTypeParameter r3 = (org.jetbrains.kotlin.fir.declarations.FirTypeParameter) r3
            java.util.List r3 = r3.getBounds()
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            java.util.Iterator r3 = r3.iterator()
        L1f:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L55
            java.lang.Object r0 = r3.next()
            org.jetbrains.kotlin.fir.types.FirTypeRef r0 = (org.jetbrains.kotlin.fir.types.FirTypeRef) r0
            org.jetbrains.kotlin.fir.types.ConeKotlinType r0 = org.jetbrains.kotlin.fir.types.FirTypeUtilsKt.getConeType(r0)
            org.jetbrains.kotlin.fir.types.ConeClassLikeType r0 = getPrimitiveTypeOrSupertype(r0)
            if (r0 == 0) goto L1f
            goto L43
        L36:
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.types.ConeClassLikeType
            if (r0 == 0) goto L45
            r0 = r3
            org.jetbrains.kotlin.fir.types.ConeClassLikeType r0 = (org.jetbrains.kotlin.fir.types.ConeClassLikeType) r0
            boolean r2 = org.jetbrains.kotlin.fir.PrimitivesKt.isPrimitiveNumberType(r0)
            if (r2 == 0) goto L45
        L43:
            r1 = r0
            goto L55
        L45:
            boolean r0 = r3 instanceof org.jetbrains.kotlin.fir.types.ConeFlexibleType
            if (r0 == 0) goto L55
            org.jetbrains.kotlin.fir.types.ConeFlexibleType r3 = (org.jetbrains.kotlin.fir.types.ConeFlexibleType) r3
            org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType r3 = r3.getLowerBound()
            org.jetbrains.kotlin.fir.types.ConeKotlinType r3 = (org.jetbrains.kotlin.fir.types.ConeKotlinType) r3
            org.jetbrains.kotlin.fir.types.ConeClassLikeType r1 = getPrimitiveTypeOrSupertype(r3)
        L55:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.backend.PrimitiveComparisonKt.getPrimitiveTypeOrSupertype(org.jetbrains.kotlin.fir.types.ConeKotlinType):org.jetbrains.kotlin.fir.types.ConeClassLikeType");
    }
}
