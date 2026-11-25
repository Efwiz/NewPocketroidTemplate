package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.PhaseUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: FirStandardOverrideChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0014J \u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0002J0\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\bH\u0002J$\u0010\u0017\u001a\u00020\r2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00122\b\u0010\u0019\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000f\u001a\u00020\bH\u0002J\u0018\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002J \u0010\u001a\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\bH\u0002J\u001e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\bJ\u0018\u0010 \u001a\u00020\r2\u0006\u0010\t\u001a\u00020!2\u0006\u0010\u000b\u001a\u00020!H\u0016J\u001e\u0010 \u001a\u00020\r2\u0006\u0010\t\u001a\u00020!2\u0006\u0010\u000b\u001a\u00020!2\u0006\u0010\"\u001a\u00020\rJ\u0018\u0010#\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020$H\u0016J\u001e\u0010#\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020$2\u0006\u0010\"\u001a\u00020\rJ\u0018\u0010%\u001a\u00020\r2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'H\u0002J,\u0010)\u001a\u00020\r*\u00020\u00002\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\rH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirStandardOverrideChecker;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractOverrideChecker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "context", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "buildTypeParametersSubstitutorIfCompatible", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "overrideCandidate", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "baseDeclaration", "isCompatibleTypeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "substitutor", "isEqualBound", "overrideBound", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "baseBound", "overrideTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "baseTypeParameter", "isEqualReceiverTypes", "candidateTypeRef", "baseTypeRef", "isEqualTypes", "substitutedCandidateType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "substitutedBaseType", "candidateType", "baseType", "isOverriddenFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "ignoreVisibility", "isOverriddenProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "maybeEqualErrorTypes", "ref1", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "ref2", "commonCallableChecks", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirStandardOverrideChecker extends FirAbstractOverrideChecker {
    private final ConeInferenceContext context;
    private final FirSession session;

    public FirStandardOverrideChecker(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.context = TypeComponentsKt.getTypeContext(session);
    }

    private final boolean isEqualTypes(ConeKotlinType substitutedCandidateType, ConeKotlinType substitutedBaseType) {
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible;
        SimpleTypeMarker simpleTypeMarkerUpperBoundIfFlexible;
        ConeKotlinType coneKotlinType;
        ConeInferenceContext coneInferenceContext = this.context;
        ConeKotlinType coneKotlinType2 = substitutedBaseType;
        boolean zIsFlexible = coneInferenceContext.isFlexible(coneKotlinType2);
        ConeKotlinType coneKotlinType3 = substitutedCandidateType;
        if (zIsFlexible == coneInferenceContext.isFlexible(coneKotlinType3)) {
            return AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, this.context, coneKotlinType3, coneKotlinType2, false, 8, null);
        }
        if (zIsFlexible) {
            simpleTypeMarkerLowerBoundIfFlexible = coneInferenceContext.lowerBoundIfFlexible(coneKotlinType2);
            simpleTypeMarkerUpperBoundIfFlexible = coneInferenceContext.upperBoundIfFlexible(coneKotlinType2);
            coneKotlinType = coneKotlinType3;
        } else {
            simpleTypeMarkerLowerBoundIfFlexible = coneInferenceContext.lowerBoundIfFlexible(coneKotlinType3);
            simpleTypeMarkerUpperBoundIfFlexible = coneInferenceContext.upperBoundIfFlexible(coneKotlinType3);
            coneKotlinType = coneKotlinType2;
        }
        if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) this.context, (KotlinTypeMarker) simpleTypeMarkerLowerBoundIfFlexible, (KotlinTypeMarker) coneKotlinType, false, 8, (Object) null)) {
            if (AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) this.context, (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) simpleTypeMarkerUpperBoundIfFlexible, false, 8, (Object) null)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isEqualTypes(ConeKotlinType candidateType, ConeKotlinType baseType, ConeSubstitutor substitutor) {
        return isEqualTypes(substitutor.substituteOrSelf(candidateType), substitutor.substituteOrSelf(baseType));
    }

    public final boolean isEqualTypes(FirTypeRef candidateTypeRef, FirTypeRef baseTypeRef, ConeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(candidateTypeRef, "candidateTypeRef");
        Intrinsics.checkNotNullParameter(baseTypeRef, "baseTypeRef");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        PhaseUtilsKt.ensureResolvedTypeDeclaration(candidateTypeRef, this.session, FirResolvePhase.TYPES);
        PhaseUtilsKt.ensureResolvedTypeDeclaration(baseTypeRef, this.session, FirResolvePhase.TYPES);
        if ((candidateTypeRef instanceof FirErrorTypeRef) && (baseTypeRef instanceof FirErrorTypeRef)) {
            return maybeEqualErrorTypes((FirErrorTypeRef) candidateTypeRef, (FirErrorTypeRef) baseTypeRef);
        }
        return isEqualTypes(FirTypeUtilsKt.getConeType(candidateTypeRef), FirTypeUtilsKt.getConeType(baseTypeRef), substitutor);
    }

    private final boolean maybeEqualErrorTypes(FirErrorTypeRef ref1, FirErrorTypeRef ref2) {
        FirTypeRef delegatedTypeRef = ref1.getDelegatedTypeRef();
        FirUserTypeRef firUserTypeRef = delegatedTypeRef instanceof FirUserTypeRef ? (FirUserTypeRef) delegatedTypeRef : null;
        if (firUserTypeRef == null) {
            return false;
        }
        FirTypeRef delegatedTypeRef2 = ref2.getDelegatedTypeRef();
        FirUserTypeRef firUserTypeRef2 = delegatedTypeRef2 instanceof FirUserTypeRef ? (FirUserTypeRef) delegatedTypeRef2 : null;
        if (firUserTypeRef2 == null || firUserTypeRef.getQualifier().size() != firUserTypeRef2.getQualifier().size()) {
            return false;
        }
        List<Pair> listZip = CollectionsKt.zip(firUserTypeRef.getQualifier(), firUserTypeRef2.getQualifier());
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                if (!Intrinsics.areEqual(((FirQualifierPart) pair.component1()).getName(), ((FirQualifierPart) pair.component2()).getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean isEqualBound(FirTypeRef overrideBound, FirTypeRef baseBound, FirTypeParameter overrideTypeParameter, FirTypeParameter baseTypeParameter, ConeSubstitutor substitutor) {
        ConeKotlinType coneKotlinTypeSubstituteOrSelf = substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(overrideBound));
        ConeKotlinType coneKotlinTypeSubstituteOrSelf2 = substitutor.substituteOrSelf(FirTypeUtilsKt.getConeType(baseBound));
        if (isEqualTypes(coneKotlinTypeSubstituteOrSelf, coneKotlinTypeSubstituteOrSelf2)) {
            return true;
        }
        List<FirResolvedTypeRef> resolvedBounds = overrideTypeParameter.getSymbol().getResolvedBounds();
        if (!(resolvedBounds instanceof Collection) || !resolvedBounds.isEmpty()) {
            Iterator<T> it = resolvedBounds.iterator();
            while (it.hasNext()) {
                if (isEqualTypes(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()), coneKotlinTypeSubstituteOrSelf2, substitutor)) {
                    List<FirResolvedTypeRef> resolvedBounds2 = baseTypeParameter.getSymbol().getResolvedBounds();
                    if (!(resolvedBounds2 instanceof Collection) || !resolvedBounds2.isEmpty()) {
                        Iterator<T> it2 = resolvedBounds2.iterator();
                        while (it2.hasNext()) {
                            if (isEqualTypes(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it2.next()), coneKotlinTypeSubstituteOrSelf, substitutor)) {
                                return true;
                            }
                        }
                        break;
                    }
                    break;
                }
            }
        }
        return false;
    }

    private final boolean isCompatibleTypeParameters(FirTypeParameterRef overrideCandidate, FirTypeParameterRef baseDeclaration, ConeSubstitutor substitutor) {
        if (Intrinsics.areEqual(overrideCandidate.getSymbol(), baseDeclaration.getSymbol())) {
            return true;
        }
        if (!(overrideCandidate instanceof FirTypeParameter) || !(baseDeclaration instanceof FirTypeParameter)) {
            return false;
        }
        FirTypeParameter firTypeParameter = (FirTypeParameter) overrideCandidate;
        FirTypeParameter firTypeParameter2 = (FirTypeParameter) baseDeclaration;
        if (firTypeParameter.getBounds().size() != firTypeParameter2.getBounds().size()) {
            return false;
        }
        List<Pair> listZip = CollectionsKt.zip(overrideCandidate.getSymbol().getResolvedBounds(), baseDeclaration.getSymbol().getResolvedBounds());
        if ((listZip instanceof Collection) && listZip.isEmpty()) {
            return true;
        }
        for (Pair pair : listZip) {
            if (!isEqualBound((FirResolvedTypeRef) pair.component1(), (FirResolvedTypeRef) pair.component2(), firTypeParameter, firTypeParameter2, substitutor)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractOverrideChecker
    protected ConeSubstitutor buildTypeParametersSubstitutorIfCompatible(FirCallableDeclaration overrideCandidate, FirCallableDeclaration baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(overrideCandidate, FirResolvePhase.TYPES);
        FirLazyDeclarationResolverKt.lazyResolveToPhase(baseDeclaration, FirResolvePhase.TYPES);
        ConeSubstitutor coneSubstitutorBuildSubstitutorForOverridesCheck = FirAbstractOverrideCheckerKt.buildSubstitutorForOverridesCheck(overrideCandidate, baseDeclaration, this.session);
        if (coneSubstitutorBuildSubstitutorForOverridesCheck == null) {
            return null;
        }
        if (!overrideCandidate.getTypeParameters().isEmpty()) {
            List<Pair> listZip = CollectionsKt.zip(overrideCandidate.getTypeParameters(), baseDeclaration.getTypeParameters());
            if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
                for (Pair pair : listZip) {
                    if (!isCompatibleTypeParameters((FirTypeParameterRef) pair.component1(), (FirTypeParameterRef) pair.component2(), coneSubstitutorBuildSubstitutorForOverridesCheck)) {
                        return null;
                    }
                }
            }
        }
        return coneSubstitutorBuildSubstitutorForOverridesCheck;
    }

    private final boolean isEqualReceiverTypes(FirTypeRef candidateTypeRef, FirTypeRef baseTypeRef, ConeSubstitutor substitutor) {
        if (candidateTypeRef == null || baseTypeRef == null) {
            return candidateTypeRef == null && baseTypeRef == null;
        }
        return isEqualTypes(candidateTypeRef, baseTypeRef, substitutor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirOverrideChecker
    public boolean isOverriddenFunction(FirSimpleFunction overrideCandidate, FirSimpleFunction baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        return isOverriddenFunction(overrideCandidate, baseDeclaration, false);
    }

    public final boolean isOverriddenFunction(FirSimpleFunction overrideCandidate, FirSimpleFunction baseDeclaration, boolean ignoreVisibility) {
        FirSimpleFunction firSimpleFunction;
        FirSimpleFunction firSimpleFunction2;
        ConeSubstitutor coneSubstitutorBuildTypeParametersSubstitutorIfCompatible;
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if (overrideCandidate.getValueParameters().size() != baseDeclaration.getValueParameters().size() || (coneSubstitutorBuildTypeParametersSubstitutorIfCompatible = buildTypeParametersSubstitutorIfCompatible((firSimpleFunction = overrideCandidate), (firSimpleFunction2 = baseDeclaration))) == null || !commonCallableChecks(this, firSimpleFunction, firSimpleFunction2, coneSubstitutorBuildTypeParametersSubstitutorIfCompatible, ignoreVisibility)) {
            return false;
        }
        List<Pair> listZip = CollectionsKt.zip(overrideCandidate.getValueParameters(), baseDeclaration.getValueParameters());
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                if (!isEqualTypes(((FirValueParameter) pair.component1()).getReturnTypeRef(), ((FirValueParameter) pair.component2()).getReturnTypeRef(), coneSubstitutorBuildTypeParametersSubstitutorIfCompatible)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirOverrideChecker
    public boolean isOverriddenProperty(FirCallableDeclaration overrideCandidate, FirProperty baseDeclaration) {
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        return isOverriddenProperty(overrideCandidate, baseDeclaration, false);
    }

    public final boolean isOverriddenProperty(FirCallableDeclaration overrideCandidate, FirProperty baseDeclaration, boolean ignoreVisibility) {
        FirProperty firProperty;
        ConeSubstitutor coneSubstitutorBuildTypeParametersSubstitutorIfCompatible;
        Intrinsics.checkNotNullParameter(overrideCandidate, "overrideCandidate");
        Intrinsics.checkNotNullParameter(baseDeclaration, "baseDeclaration");
        if ((overrideCandidate instanceof FirProperty) && (coneSubstitutorBuildTypeParametersSubstitutorIfCompatible = buildTypeParametersSubstitutorIfCompatible(overrideCandidate, (firProperty = baseDeclaration))) != null) {
            return commonCallableChecks(this, overrideCandidate, firProperty, coneSubstitutorBuildTypeParametersSubstitutorIfCompatible, ignoreVisibility);
        }
        return false;
    }

    private final boolean commonCallableChecks(FirStandardOverrideChecker firStandardOverrideChecker, FirCallableDeclaration firCallableDeclaration, FirCallableDeclaration firCallableDeclaration2, ConeSubstitutor coneSubstitutor, boolean z) {
        if ((!z && Visibilities.INSTANCE.isPrivate(firCallableDeclaration2.getStatus().getVisibility())) || firCallableDeclaration.getContextReceivers().size() != firCallableDeclaration2.getContextReceivers().size()) {
            return false;
        }
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firCallableDeclaration, FirResolvePhase.TYPES);
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firCallableDeclaration2, FirResolvePhase.TYPES);
        FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
        FirTypeRef typeRef = receiverParameter != null ? receiverParameter.getTypeRef() : null;
        FirReceiverParameter receiverParameter2 = firCallableDeclaration2.getReceiverParameter();
        if (!firStandardOverrideChecker.isEqualReceiverTypes(typeRef, receiverParameter2 != null ? receiverParameter2.getTypeRef() : null, coneSubstitutor)) {
            return false;
        }
        List<Pair> listZip = CollectionsKt.zip(firCallableDeclaration.getContextReceivers(), firCallableDeclaration2.getContextReceivers());
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                if (!firStandardOverrideChecker.isEqualTypes(((FirContextReceiver) pair.component1()).getTypeRef(), ((FirContextReceiver) pair.component2()).getTypeRef(), coneSubstitutor)) {
                    return false;
                }
            }
        }
        return true;
    }
}
