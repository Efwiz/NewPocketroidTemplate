package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.PrimitivesKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002\u001a\u0014\u0010\u0017\u001a\u00020\u0018*\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002\u001a\u0014\u0010\u001b\u001a\u00020\u0002*\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001c\u0010\u0005\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u001c\u0010\b\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0007\"\u001c\u0010\t\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007\"\u0018\u0010\n\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0018\u0010\u000b\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0004\"\u0018\u0010\f\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u0004\"\u001e\u0010\r\u001a\u00020\u000e*\u00020\u000f8BX\u0082\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001e"}, d2 = {"enforcesEmptyIntersection", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;", "getEnforcesEmptyIntersection", "(Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;)Z", "isBuiltin", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;)Z", "isClass", "isFinalClass", "isIdentityLess", "isNotNullPrimitive", "isNullableEnum", "mostOriginalTypeIfSmartCast", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getMostOriginalTypeIfSmartCast$annotations", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getMostOriginalTypeIfSmartCast", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "toKotlinType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", ModuleXmlParser.TYPE, "toArgumentInfo", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/ArgumentInfo;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "toTypeInfo", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirEqualityCompatibilityCheckerKt {
    private static final boolean isBuiltin(FirClassSymbol<?> firClassSymbol) {
        return PrimitivesKt.isPrimitiveType(firClassSymbol) || Intrinsics.areEqual(firClassSymbol.getClassId(), StandardClassIds.INSTANCE.getString()) || firClassSymbol.getClassKind() == ClassKind.ENUM_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getEnforcesEmptyIntersection(TypeInfo typeInfo) {
        return typeInfo.getIsFinalClass() && !typeInfo.getIsEnumClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isNullableEnum(TypeInfo typeInfo) {
        return typeInfo.getIsEnumClass() && ConeTypeUtilsKt.isNullable(typeInfo.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isIdentityLess(TypeInfo typeInfo) {
        return typeInfo.getIsPrimitive() || typeInfo.getIsValueClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isNotNullPrimitive(TypeInfo typeInfo) {
        return typeInfo.getIsPrimitive() && !ConeTypeUtilsKt.isNullable(typeInfo.getType());
    }

    private static final boolean isFinalClass(FirClassSymbol<?> firClassSymbol) {
        Modality modality;
        return isClass(firClassSymbol) && ((modality = firClassSymbol.getResolvedStatus().getModality()) == null || modality == Modality.FINAL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TypeInfo toTypeInfo(ConeKotlinType coneKotlinType, FirSession firSession) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        Set<ConeClassLikeType> setCollectUpperBounds = FirHelpersKt.collectUpperBounds(coneKotlinType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setCollectUpperBounds, 10));
        Iterator<T> it = setCollectUpperBounds.iterator();
        while (it.hasNext()) {
            arrayList.add(ConeTypeUtilsKt.replaceArgumentsWithStarProjections(toKotlinType((ConeClassLikeType) it.next())));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = arrayList2;
        ConeClassLikeType coneClassLikeTypeIntersectTypes = !arrayList3.isEmpty() ? ConeTypeIntersector.INSTANCE.intersectTypes(TypeComponentsKt.getTypeContext(firSession), arrayList3) : null;
        if (coneClassLikeTypeIntersectTypes == null) {
            coneClassLikeTypeIntersectTypes = firSession.getBuiltinTypes().getNullableAnyType().getType();
        }
        ConeKotlinType coneKotlinTypeWithNullability$default = TypeUtilsKt.withNullability$default(coneClassLikeTypeIntersectTypes, ConeNullability.NOT_NULL, TypeComponentsKt.getTypeContext(firSession), null, false, 12, null);
        ArrayList arrayList4 = arrayList2;
        boolean z6 = arrayList4 instanceof Collection;
        if (!z6 || !arrayList4.isEmpty()) {
            Iterator it2 = arrayList4.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                FirClassSymbol<?> classSymbol = TypeUtilsKt.toClassSymbol((ConeClassLikeType) it2.next(), firSession);
                if (classSymbol != null && isFinalClass(classSymbol)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if (!z6 || !arrayList4.isEmpty()) {
            Iterator it3 = arrayList4.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    z2 = false;
                    break;
                }
                FirClassSymbol<?> classSymbol2 = TypeUtilsKt.toClassSymbol((ConeClassLikeType) it3.next(), firSession);
                if (classSymbol2 != null && classSymbol2.getClassKind() == ClassKind.ENUM_CLASS) {
                    z2 = true;
                    break;
                }
            }
        } else {
            z2 = false;
            break;
        }
        if (!z6 || !arrayList4.isEmpty()) {
            Iterator it4 = arrayList4.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    z3 = false;
                    break;
                }
                if (ConeBuiltinTypeUtilsKt.isPrimitiveOrNullablePrimitive((ConeClassLikeType) it4.next())) {
                    z3 = true;
                    break;
                }
            }
        } else {
            z3 = false;
            break;
        }
        if (!z6 || !arrayList4.isEmpty()) {
            Iterator it5 = arrayList4.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    z4 = false;
                    break;
                }
                FirClassSymbol<?> classSymbol3 = TypeUtilsKt.toClassSymbol((ConeClassLikeType) it5.next(), firSession);
                if (classSymbol3 != null && isBuiltin(classSymbol3)) {
                    z4 = true;
                    break;
                }
            }
        } else {
            z4 = false;
            break;
        }
        if (z6 && arrayList4.isEmpty()) {
            z5 = false;
        } else {
            Iterator it6 = arrayList4.iterator();
            while (it6.hasNext()) {
                FirClassSymbol<?> classSymbol4 = TypeUtilsKt.toClassSymbol((ConeClassLikeType) it6.next(), firSession);
                if (classSymbol4 != null && classSymbol4.getRawStatus().isInline()) {
                    z5 = true;
                }
            }
            z5 = false;
        }
        return new TypeInfo(coneClassLikeTypeIntersectTypes, coneKotlinTypeWithNullability$default, z, z2, z3, z4, z5, ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType) instanceof ConeTypeParameterType);
    }

    private static final ConeClassLikeType toKotlinType(ConeClassLikeType coneClassLikeType) {
        return new ConeClassLikeTypeImpl(coneClassLikeType.getLookupTag(), coneClassLikeType.getTypeArguments(), ConeTypeUtilsKt.isNullable(coneClassLikeType), null, 8, null);
    }

    private static final ConeKotlinType getMostOriginalTypeIfSmartCast(FirExpression firExpression) {
        return firExpression instanceof FirSmartCastExpression ? getMostOriginalTypeIfSmartCast(((FirSmartCastExpression) firExpression).getOriginalExpression()) : FirTypeUtilsKt.getConeType(firExpression.getTypeRef());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ArgumentInfo toArgumentInfo(FirExpression firExpression, CheckerContext checkerContext) {
        return new ArgumentInfo(firExpression, FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), TypeExpansionUtilsKt.fullyExpandedType(getMostOriginalTypeIfSmartCast(firExpression), checkerContext.getSession()), checkerContext.getSession());
    }

    private static final boolean isClass(FirClassSymbol<?> firClassSymbol) {
        return !(firClassSymbol.getClassKind() == ClassKind.INTERFACE);
    }
}
