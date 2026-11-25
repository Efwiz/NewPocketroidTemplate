package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.StandardTypes;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeCapturedTypeConstructor;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeConflictingProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;

/* JADX INFO: compiled from: FirJavaGenericVarianceViolationTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001c\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002J\u0014\u0010\u0016\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0017\u001a\u00020\rH\u0002J\u0014\u0010\u0016\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0017\u001a\u00020\rH\u0002J\u0014\u0010\u0016\u001a\u00020\u0018*\u00020\u00182\u0006\u0010\u0017\u001a\u00020\rH\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJavaGenericVarianceViolationTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isTypeConstructorEqualOrSubClassOf", "", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "subType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "superType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "subTypeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "superTypeConstructor", "removeOutProjection", "isCovariant", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaGenericVarianceViolationTypeChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirJavaGenericVarianceViolationTypeChecker INSTANCE = new FirJavaGenericVarianceViolationTypeChecker();

    private FirJavaGenericVarianceViolationTypeChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef typeRef;
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData;
        FirCallableDeclaration original;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(expression.getCalleeReference(), false, 1, null);
        FirFunctionSymbol firFunctionSymbol = resolvedCallableSymbol$default instanceof FirFunctionSymbol ? (FirFunctionSymbol) resolvedCallableSymbol$default : null;
        if (firFunctionSymbol == null) {
            return;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firFunctionSymbol.getFir();
        while (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
            if (originalForSubstitutionOverrideAttr == null) {
                originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
            }
            if (originalForSubstitutionOverrideAttr == null) {
                break;
            } else {
                firCallableDeclaration = originalForSubstitutionOverrideAttr;
            }
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        if (symbol != null) {
            FirFunctionSymbol firFunctionSymbol2 = (FirFunctionSymbol) symbol;
            FirDeclarationOrigin origin = firFunctionSymbol2.getOrigin();
            if (!(origin instanceof FirDeclarationOrigin.Java) && !Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) {
                D fir = firFunctionSymbol2.getFir();
                FirCallableDeclaration firCallableDeclaration2 = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
                if (firCallableDeclaration2 == null || (importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration2)) == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || !DeclarationUtilsKt.isJavaOrEnhancement(original)) {
                    return;
                }
            }
            FirArgumentList argumentList = expression.getArgumentList();
            LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
            if (mapping == null) {
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int iMin = Math.min(expression.getTypeArguments().size(), firFunctionSymbol.getTypeParameterSymbols().size());
            for (int i = 0; i < iMin; i++) {
                FirTypeProjection firTypeProjection = expression.getTypeArguments().get(i);
                FirTypeProjectionWithVariance firTypeProjectionWithVariance = firTypeProjection instanceof FirTypeProjectionWithVariance ? (FirTypeProjectionWithVariance) firTypeProjection : null;
                ConeKotlinType coneType = (firTypeProjectionWithVariance == null || (typeRef = firTypeProjectionWithVariance.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
                if (coneType != null) {
                    linkedHashMap.put(firFunctionSymbol.getTypeParameterSymbols().get(i), coneType);
                }
            }
            ConeSubstitutor coneSubstitutorSubstitutorByMap = SubstitutorsKt.substitutorByMap(linkedHashMap, context.getSession());
            for (Map.Entry<FirExpression, FirValueParameter> entry : mapping.entrySet()) {
                FirExpression key = entry.getKey();
                ConeKotlinType coneKotlinTypeSubstituteOrSelf = coneSubstitutorSubstitutorByMap.substituteOrSelf(FirTypeUtilsKt.getConeType(entry.getValue().getReturnTypeRef()));
                if ((coneKotlinTypeSubstituteOrSelf instanceof ConeFlexibleType) && coneKotlinTypeSubstituteOrSelf.getTypeArguments().length != 0 && !(coneKotlinTypeSubstituteOrSelf instanceof ConeRawType)) {
                    ConeKotlinType coneType2 = FirTypeUtilsKt.getConeType(key.getTypeRef());
                    ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinTypeSubstituteOrSelf;
                    ConeSimpleKotlinType lowerBound = coneFlexibleType.getLowerBound();
                    ConeSimpleKotlinType upperBound = coneFlexibleType.getUpperBound();
                    ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(context.getSession());
                    ConeInferenceContext coneInferenceContext = typeContext;
                    if (!Intrinsics.areEqual(TypeSystemContextHelpersKt.typeConstructor(lowerBound, coneInferenceContext), TypeSystemContextHelpersKt.typeConstructor(upperBound, coneInferenceContext)) && isTypeConstructorEqualOrSubClassOf(typeContext, coneType2, lowerBound)) {
                        ConeKotlinType coneKotlinTypeRemoveOutProjection = removeOutProjection(coneType2, true);
                        ConeSimpleKotlinType coneSimpleKotlinType = lowerBound;
                        ConeKotlinType coneKotlinTypeApproximateToSuperType = TypeComponentsKt.getTypeApproximator(context.getSession()).approximateToSuperType((ConeKotlinType) coneSimpleKotlinType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
                        if (!AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) typeContext, (KotlinTypeMarker) coneKotlinTypeRemoveOutProjection, (KotlinTypeMarker) TypeUtilsKt.withNullability$default(coneKotlinTypeApproximateToSuperType == null ? coneSimpleKotlinType : coneKotlinTypeApproximateToSuperType, ConeNullability.NULLABLE, typeContext, null, false, 12, null), false, 8, (Object) null)) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, key.getSource(), FirJvmErrors.INSTANCE.getJAVA_TYPE_MISMATCH(), coneKotlinTypeSubstituteOrSelf, coneType2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                        }
                    }
                }
            }
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol<*>");
    }

    private final ConeKotlinType removeOutProjection(ConeKotlinType coneKotlinType, boolean z) {
        if (coneKotlinType instanceof ConeFlexibleType) {
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            return new ConeFlexibleType(removeOutProjection(coneFlexibleType.getLowerBound(), z), removeOutProjection(coneFlexibleType.getUpperBound(), z));
        }
        if (coneKotlinType instanceof ConeSimpleKotlinType) {
            return removeOutProjection((ConeSimpleKotlinType) coneKotlinType, z);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final ConeSimpleKotlinType removeOutProjection(ConeSimpleKotlinType coneSimpleKotlinType, boolean z) {
        ArrayList arrayList = null;
        if (coneSimpleKotlinType instanceof ConeCapturedType) {
            ConeCapturedType coneCapturedType = (ConeCapturedType) coneSimpleKotlinType;
            CaptureStatus captureStatus = coneCapturedType.getCaptureStatus();
            ConeKotlinType lowerType = coneCapturedType.getLowerType();
            ConeKotlinType coneKotlinTypeRemoveOutProjection = lowerType != null ? removeOutProjection(lowerType, z) : null;
            ConeNullability nullability = coneSimpleKotlinType.getNullability();
            ConeCapturedTypeConstructor constructor = coneCapturedType.getConstructor();
            ConeTypeProjection coneTypeProjectionRemoveOutProjection = INSTANCE.removeOutProjection(constructor.getProjection(), z);
            List<ConeKotlinType> supertypes = constructor.getSupertypes();
            if (supertypes != null) {
                List<ConeKotlinType> list = supertypes;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(INSTANCE.removeOutProjection((ConeKotlinType) it.next(), z));
                }
                arrayList = arrayList2;
            }
            new ConeCapturedTypeConstructor(coneTypeProjectionRemoveOutProjection, arrayList, constructor.getTypeParameterMarker());
            Unit unit = Unit.INSTANCE;
            return new ConeCapturedType(captureStatus, coneKotlinTypeRemoveOutProjection, nullability, constructor, coneSimpleKotlinType.getAttributes(), coneCapturedType.isProjectionNotNull());
        }
        if (coneSimpleKotlinType instanceof ConeDefinitelyNotNullType) {
            return new ConeDefinitelyNotNullType(removeOutProjection(((ConeDefinitelyNotNullType) coneSimpleKotlinType).getOriginal(), z));
        }
        if (coneSimpleKotlinType instanceof ConeIntersectionType) {
            ConeIntersectionType coneIntersectionType = (ConeIntersectionType) coneSimpleKotlinType;
            Collection<ConeKotlinType> intersectedTypes = coneIntersectionType.getIntersectedTypes();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(intersectedTypes, 10));
            Iterator<T> it2 = intersectedTypes.iterator();
            while (it2.hasNext()) {
                arrayList3.add(INSTANCE.removeOutProjection((ConeKotlinType) it2.next(), z));
            }
            ArrayList arrayList4 = arrayList3;
            ConeKotlinType alternativeType = coneIntersectionType.getAlternativeType();
            return new ConeIntersectionType(arrayList4, alternativeType != null ? removeOutProjection(alternativeType, z) : null);
        }
        if (!(coneSimpleKotlinType instanceof ConeClassLikeTypeImpl)) {
            return coneSimpleKotlinType;
        }
        ConeClassLikeLookupTag lookupTag = ((ConeClassLikeTypeImpl) coneSimpleKotlinType).getLookupTag();
        ConeTypeProjection[] typeArguments = coneSimpleKotlinType.getTypeArguments();
        ArrayList arrayList5 = new ArrayList(typeArguments.length);
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            arrayList5.add(INSTANCE.removeOutProjection(coneTypeProjection, z));
        }
        return new ConeClassLikeTypeImpl(lookupTag, (ConeTypeProjection[]) arrayList5.toArray(new ConeTypeProjection[0]), ConeTypeUtilsKt.isNullable(coneSimpleKotlinType), coneSimpleKotlinType.getAttributes());
    }

    private final ConeTypeProjection removeOutProjection(ConeTypeProjection coneTypeProjection, boolean z) {
        if (coneTypeProjection instanceof ConeKotlinTypeProjectionOut) {
            if (z) {
                coneTypeProjection = ((ConeKotlinTypeProjectionOut) coneTypeProjection).getType();
            }
            return (ConeKotlinTypeProjection) coneTypeProjection;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
            return new ConeKotlinTypeProjectionIn(removeOutProjection(((ConeKotlinTypeProjectionIn) coneTypeProjection).getType(), !z));
        }
        if (coneTypeProjection instanceof ConeStarProjection) {
            return z ? StandardTypes.INSTANCE.getNullableAny() : coneTypeProjection;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeConflictingProjection ? true : coneTypeProjection instanceof ConeKotlinType) {
            return coneTypeProjection;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean isTypeConstructorEqualOrSubClassOf(ConeInferenceContext coneInferenceContext, ConeKotlinType coneKotlinType, ConeSimpleKotlinType coneSimpleKotlinType) {
        return isTypeConstructorEqualOrSubClassOf(coneInferenceContext, coneInferenceContext.typeConstructor(coneKotlinType), coneInferenceContext.typeConstructor((SimpleTypeMarker) coneSimpleKotlinType));
    }

    private final boolean isTypeConstructorEqualOrSubClassOf(ConeInferenceContext coneInferenceContext, TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
        if (Intrinsics.areEqual(typeConstructorMarker, typeConstructorMarker2)) {
            return true;
        }
        Iterator<ConeKotlinType> it = coneInferenceContext.supertypes(typeConstructorMarker).iterator();
        while (it.hasNext()) {
            TypeConstructorMarker typeConstructorMarkerTypeConstructor = coneInferenceContext.typeConstructor(it.next());
            if (Intrinsics.areEqual(typeConstructorMarker2, typeConstructorMarkerTypeConstructor) || isTypeConstructorEqualOrSubClassOf(coneInferenceContext, typeConstructorMarkerTypeConstructor, typeConstructorMarker2)) {
                return true;
            }
        }
        return false;
    }
}
