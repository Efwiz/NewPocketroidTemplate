package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapperKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForTypeVariableInSubtyping;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUnificationKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;

/* JADX INFO: compiled from: FirCastDiagnosticsHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001e\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b\u001a\u001e\u0010\r\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b\u001a \u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0003H\u0002\u001a\u0018\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0002\u001a.\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u001a\u001a4\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\b\u0010\u001e\u001a\u0004\u0018\u00010\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a \u0010 \u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0002\u001a\u0016\u0010\u0018\u001a\u00020\u00062\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u0007\u001a\u00020\b\u001a\n\u0010#\u001a\u00020\u0006*\u00020\u0003¨\u0006$"}, d2 = {"checkCasting", "Lorg/jetbrains/kotlin/fir/analysis/checkers/CastingType;", "lhsType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "rhsType", "isSafeCase", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "findStaticallyKnownSubtype", "supertype", "subTypeClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "isCastErased", "subtype", "isExactTypeCast", "candidateType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "targetType", "isFinal", ModuleXmlParser.TYPE, "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "isRefinementUseless", "shouldCheckForExactType", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isRelated", "aType", "bType", "aClassSymbol", "bClassSymbol", "isUpcast", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "isNonReifiedTypeParameter", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCastDiagnosticsHelpersKt {

    /* JADX INFO: compiled from: FirCastDiagnosticsHelpers.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.IS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.NOT_IS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.AS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.SAFE_AS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final CastingType checkCasting(ConeKotlinType lhsType, ConeKotlinType rhsType, boolean z, CheckerContext context) {
        Intrinsics.checkNotNullParameter(lhsType, "lhsType");
        Intrinsics.checkNotNullParameter(rhsType, "rhsType");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(lhsType);
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible2 = ConeTypeUtilsKt.lowerBoundIfFlexible(rhsType);
        FirSession session = context.getSession();
        if (coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeIntersectionType) {
            boolean z2 = false;
            for (ConeKotlinType coneKotlinType : ((ConeIntersectionType) coneSimpleKotlinTypeLowerBoundIfFlexible).getIntersectedTypes()) {
                CastingType castingTypeCheckCasting = checkCasting(coneKotlinType, coneSimpleKotlinTypeLowerBoundIfFlexible2, z, context);
                FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneKotlinType, context.getSession());
                if (regularClassSymbol != null && regularClassSymbol.getClassKind() != ClassKind.INTERFACE && castingTypeCheckCasting == CastingType.Impossible) {
                    return CastingType.Impossible;
                }
                z2 |= castingTypeCheckCasting != CastingType.Impossible;
            }
            return z2 ? CastingType.Possible : CastingType.Impossible;
        }
        ConeSimpleKotlinType coneSimpleKotlinType = coneSimpleKotlinTypeLowerBoundIfFlexible;
        boolean canBeNull = FirTypeUtilsKt.getCanBeNull(coneSimpleKotlinType);
        ConeSimpleKotlinType coneSimpleKotlinType2 = coneSimpleKotlinTypeLowerBoundIfFlexible2;
        boolean canBeNull2 = FirTypeUtilsKt.getCanBeNull(coneSimpleKotlinType2);
        if (ConeBuiltinTypeUtilsKt.isNothing(coneSimpleKotlinType)) {
            return CastingType.Possible;
        }
        if (ConeBuiltinTypeUtilsKt.isNullableNothing(coneSimpleKotlinType) && !canBeNull2) {
            return z ? CastingType.Always : CastingType.Impossible;
        }
        if (ConeBuiltinTypeUtilsKt.isNothing(coneSimpleKotlinType2)) {
            return CastingType.Impossible;
        }
        if (ConeBuiltinTypeUtilsKt.isNullableNothing(coneSimpleKotlinType2)) {
            return canBeNull ? CastingType.Possible : CastingType.Impossible;
        }
        if (canBeNull && canBeNull2) {
            return CastingType.Possible;
        }
        FirRegularClassSymbol regularClassSymbol2 = TypeUtilsKt.toRegularClassSymbol(coneSimpleKotlinType, context.getSession());
        FirRegularClassSymbol regularClassSymbol3 = TypeUtilsKt.toRegularClassSymbol(coneSimpleKotlinType2, context.getSession());
        if (isRelated(coneSimpleKotlinTypeLowerBoundIfFlexible, coneSimpleKotlinTypeLowerBoundIfFlexible2, regularClassSymbol2, regularClassSymbol3, context)) {
            return CastingType.Possible;
        }
        if ((coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeTypeParameterType) || (coneSimpleKotlinTypeLowerBoundIfFlexible2 instanceof ConeTypeParameterType)) {
            return CastingType.Possible;
        }
        if (isFinal(coneSimpleKotlinTypeLowerBoundIfFlexible, session) || isFinal(coneSimpleKotlinTypeLowerBoundIfFlexible2, session)) {
            return CastingType.Impossible;
        }
        if ((regularClassSymbol2 != null && regularClassSymbol2.getClassKind() == ClassKind.INTERFACE) || (regularClassSymbol3 != null && regularClassSymbol3.getClassKind() == ClassKind.INTERFACE)) {
            return CastingType.Possible;
        }
        return CastingType.Impossible;
    }

    private static final boolean isRelated(ConeSimpleKotlinType coneSimpleKotlinType, ConeSimpleKotlinType coneSimpleKotlinType2, FirRegularClassSymbol firRegularClassSymbol, FirRegularClassSymbol firRegularClassSymbol2, CheckerContext checkerContext) {
        ConeClassLikeType coneClassLikeTypeDefaultType;
        ConeClassLikeType coneClassLikeTypeDefaultType2;
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(checkerContext.getSession());
        ConeSimpleKotlinType coneSimpleKotlinType3 = coneSimpleKotlinType;
        ConeSimpleKotlinType coneSimpleKotlinType4 = coneSimpleKotlinType2;
        if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) coneSimpleKotlinType3, (KotlinTypeMarker) coneSimpleKotlinType4, false, 8, (Object) null) || AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) coneSimpleKotlinType4, (KotlinTypeMarker) coneSimpleKotlinType3, false, 8, (Object) null)) {
            return true;
        }
        if (firRegularClassSymbol != null && (coneClassLikeTypeDefaultType2 = ScopeUtilsKt.defaultType(firRegularClassSymbol)) != null) {
            coneSimpleKotlinType = coneClassLikeTypeDefaultType2;
        }
        ConeKotlinType coneKotlinTypeIsRelated$getCorrespondingKotlinClass = isRelated$getCorrespondingKotlinClass(checkerContext, coneSimpleKotlinType);
        if (firRegularClassSymbol2 != null && (coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(firRegularClassSymbol2)) != null) {
            coneSimpleKotlinType2 = coneClassLikeTypeDefaultType;
        }
        ConeKotlinType coneKotlinType = coneKotlinTypeIsRelated$getCorrespondingKotlinClass;
        ConeKotlinType coneKotlinTypeIsRelated$getCorrespondingKotlinClass2 = isRelated$getCorrespondingKotlinClass(checkerContext, coneSimpleKotlinType2);
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) coneKotlinTypeIsRelated$getCorrespondingKotlinClass2, false, 8, (Object) null) || AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) coneKotlinTypeIsRelated$getCorrespondingKotlinClass2, (KotlinTypeMarker) coneKotlinType, false, 8, (Object) null);
    }

    private static final ConeKotlinType isRelated$getCorrespondingKotlinClass(CheckerContext checkerContext, ConeSimpleKotlinType coneSimpleKotlinType) {
        ConeClassLikeType coneClassLikeTypeDefaultType;
        ConeSimpleKotlinType coneSimpleKotlinType2 = coneSimpleKotlinType;
        ClassId correspondingKotlinClass = FirPlatformClassMapperKt.getPlatformClassMapper(checkerContext.getSession()).getCorrespondingKotlinClass(ConeTypeUtilsKt.getClassId(coneSimpleKotlinType2));
        return (correspondingKotlinClass == null || (coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(correspondingKotlinClass, CollectionsKt.emptyList())) == null) ? coneSimpleKotlinType2 : coneClassLikeTypeDefaultType;
    }

    private static final boolean isFinal(ConeSimpleKotlinType coneSimpleKotlinType, FirSession firSession) {
        return !TypeUtilsKt.canHaveSubtypes(coneSimpleKotlinType, firSession);
    }

    public static final boolean isCastErased(ConeKotlinType supertype, ConeKotlinType subtype, CheckerContext context) {
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
        boolean zIsNonReifiedTypeParameter = isNonReifiedTypeParameter(subtype);
        boolean zIsUpcast = isUpcast(context, supertype, subtype);
        if (zIsNonReifiedTypeParameter && !zIsUpcast && (FirTypeUtilsKt.getCanBeNull(subtype) || !Intrinsics.areEqual(TypeUtilsKt.withNullability$default(supertype, ConeNullability.NOT_NULL, typeContext, null, false, 12, null), subtype))) {
            return true;
        }
        if (ConeTypeUtilsKt.isMarkedNullable(supertype) || ConeTypeUtilsKt.isMarkedNullable(subtype)) {
            ConeInferenceContext coneInferenceContext = typeContext;
            return isCastErased(TypeUtilsKt.withNullability$default(supertype, ConeNullability.NOT_NULL, coneInferenceContext, null, false, 12, null), TypeUtilsKt.withNullability$default(subtype, ConeNullability.NOT_NULL, coneInferenceContext, null, false, 12, null), context);
        }
        if (zIsUpcast) {
            return false;
        }
        if (zIsNonReifiedTypeParameter) {
            return true;
        }
        if (subtype instanceof ConeTypeParameterType) {
            return false;
        }
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(subtype, context.getSession());
        if (regularClassSymbol == null) {
            return true;
        }
        return !AbstractTypeChecker.INSTANCE.isSubtypeOf((TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(context.getSession()), (KotlinTypeMarker) findStaticallyKnownSubtype(supertype, regularClassSymbol, context), (KotlinTypeMarker) subtype, false);
    }

    public static final ConeKotlinType findStaticallyKnownSubtype(ConeKotlinType supertype, FirRegularClassSymbol subTypeClassSymbol, CheckerContext context) {
        ArrayList intersectedTypes;
        LinkedHashMap linkedHashMap;
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(subTypeClassSymbol, "subTypeClassSymbol");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeTypeUtilsKt.isMarkedNullable(supertype);
        FirSession session = context.getSession();
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(session);
        ConeClassLikeType coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(subTypeClassSymbol);
        TypeCheckerState typeCheckerStateNewTypeCheckerState = TypeComponentsKt.getTypeContext(context.getSession()).newTypeCheckerState(false, false);
        if (supertype instanceof ConeIntersectionType) {
            intersectedTypes = ((ConeIntersectionType) supertype).getIntersectedTypes();
        } else {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(supertype);
            intersectedTypes = arrayList;
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        Iterator<ConeKotlinType> it = intersectedTypes.iterator();
        while (it.hasNext()) {
            Object obj = (SimpleTypeMarker) CollectionsKt.firstOrNull((List) AbstractTypeChecker.INSTANCE.findCorrespondingSupertypes(typeCheckerStateNewTypeCheckerState, coneClassLikeTypeDefaultType, TypeSystemContextHelpersKt.typeConstructor(it.next(), typeContext)));
            List<FirTypeParameterSymbol> typeParameterSymbols = subTypeClassSymbol.getTypeParameterSymbols();
            if (obj != null) {
                linkedHashMap = new LinkedHashMap();
                if (!TypeUnificationKt.doUnify(context.getSession(), supertype, (ConeKotlinTypeProjection) obj, CollectionsKt.toSet(typeParameterSymbols), linkedHashMap)) {
                    linkedHashMap = new LinkedHashMap();
                }
            } else {
                linkedHashMap = new LinkedHashMap();
            }
            for (FirTypeParameterSymbol firTypeParameterSymbol : typeParameterSymbols) {
                ConeTypeProjection coneTypeProjection = (ConeTypeProjection) linkedHashMap.get(firTypeParameterSymbol);
                ConeStubTypeForTypeVariableInSubtyping type = null;
                if (coneTypeProjection != null) {
                    if (coneTypeProjection instanceof ConeStarProjection) {
                        type = new ConeStubTypeForTypeVariableInSubtyping(new ConeTypeVariable("", null), ConeNullability.NULLABLE);
                    } else {
                        type = ConeTypeProjectionKt.getType(coneTypeProjection);
                    }
                }
                if (type != null) {
                    linkedHashMap2.put(firTypeParameterSymbol, type);
                }
            }
        }
        return new ConeSubstitutorByMap(linkedHashMap2, session).substituteOrSelf(coneClassLikeTypeDefaultType);
    }

    public static final boolean isNonReifiedTypeParameter(ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        return (coneKotlinType instanceof ConeTypeParameterType) && !((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().isReified();
    }

    public static final boolean shouldCheckForExactType(FirTypeOperatorCall expression, CheckerContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperation().ordinal()];
        if (i == 1 || i == 2) {
            return false;
        }
        if (i == 3 || i == 4) {
            return true;
        }
        throw new AssertionError("Should not be here: " + expression.getOperation());
    }

    public static final boolean isRefinementUseless(CheckerContext context, ConeSimpleKotlinType candidateType, ConeKotlinType targetType, boolean z, FirExpression arg) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(candidateType, "candidateType");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        Intrinsics.checkNotNullParameter(arg, "arg");
        if (z) {
            if (arg instanceof FirFunctionCall) {
                FirCallableSymbol<?> resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(arg);
                FirFunctionSymbol firFunctionSymbol = resolvedCallableSymbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedCallableSymbol : null;
                if (firFunctionSymbol != null && FirHelpersKt.isFunctionForExpectTypeFromCastFeature(firFunctionSymbol)) {
                    return false;
                }
            }
            return isExactTypeCast(context, candidateType, targetType);
        }
        return isUpcast(context, candidateType, targetType);
    }

    private static final boolean isExactTypeCast(CheckerContext checkerContext, ConeSimpleKotlinType coneSimpleKotlinType, ConeKotlinType coneKotlinType) {
        return AbstractTypeChecker.INSTANCE.equalTypes(TypeComponentsKt.getTypeContext(checkerContext.getSession()), coneSimpleKotlinType, coneKotlinType, false) && CompilerConeAttributesKt.isExtensionFunctionType(coneSimpleKotlinType) == CompilerConeAttributesKt.isExtensionFunctionType(coneKotlinType);
    }

    private static final boolean isUpcast(CheckerContext checkerContext, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2) {
        return AbstractTypeChecker.INSTANCE.isSubtypeOf((TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(checkerContext.getSession()), (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) coneKotlinType2, false) && CompilerConeAttributesKt.isExtensionFunctionType(coneKotlinType) == CompilerConeAttributesKt.isExtensionFunctionType(coneKotlinType2);
    }
}
