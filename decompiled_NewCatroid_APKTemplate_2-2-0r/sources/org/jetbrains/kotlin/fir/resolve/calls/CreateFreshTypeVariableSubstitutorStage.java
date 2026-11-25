package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeExplicitTypeParameterConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirPlaceholderProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;

/* JADX INFO: compiled from: CreateFreshTypeVariableSubstitutorStage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CreateFreshTypeVariableSubstitutorStage;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateFreshTypeVariableSubstitutorStage extends ResolutionStage {
    public static final CreateFreshTypeVariableSubstitutorStage INSTANCE = new CreateFreshTypeVariableSubstitutorStage();

    private CreateFreshTypeVariableSubstitutorStage() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        ConeClassLikeType type;
        Object objYield2;
        FirAnnotationContainer fir = candidate.getSymbol().getFir();
        FirLazyDeclarationResolverKt.lazyResolveToPhase(candidate.getSymbol(), FirResolvePhase.STATUS);
        if (fir instanceof FirTypeParameterRefsOwner) {
            FirTypeParameterRefsOwner firTypeParameterRefsOwner = (FirTypeParameterRefsOwner) fir;
            if (!firTypeParameterRefsOwner.getTypeParameters().isEmpty()) {
                NewConstraintSystemImpl builder = candidate.getSystem().getBuilder();
                Pair pairCreateToFreshVariableSubstitutorAndAddInitialConstraints = CreateFreshTypeVariableSubstitutorStageKt.createToFreshVariableSubstitutorAndAddInitialConstraints(firTypeParameterRefsOwner, builder, resolutionContext.getSession());
                ConeSubstitutor coneSubstitutor = (ConeSubstitutor) pairCreateToFreshVariableSubstitutorAndAddInitialConstraints.component1();
                List<? extends ConeTypeVariable> list = (List) pairCreateToFreshVariableSubstitutorAndAddInitialConstraints.component2();
                candidate.setSubstitutor(coneSubstitutor);
                candidate.setFreshVariables(list);
                if (!builder.getHasContradiction()) {
                    if (Intrinsics.areEqual(candidate.getTypeArgumentMapping(), TypeArgumentMapping.NoExplicitArguments.INSTANCE)) {
                        return Unit.INSTANCE;
                    }
                    List<FirTypeParameterRef> typeParameters = firTypeParameterRefsOwner.getTypeParameters();
                    int size = typeParameters.size();
                    for (int i = 0; i < size; i++) {
                        FirTypeParameterRef firTypeParameterRef = typeParameters.get(i);
                        ConeTypeVariable coneTypeVariable = list.get(i);
                        FirTypeProjection firTypeProjection = candidate.getTypeArgumentMapping().get(i);
                        if (firTypeProjection instanceof FirTypeProjectionWithVariance) {
                            builder.addEqualityConstraint(coneTypeVariable.getDefaultType(), TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(((FirTypeProjectionWithVariance) firTypeProjection).getTypeRef()), resolutionContext.getSession()), new ConeExplicitTypeParameterConstraintPosition(firTypeProjection));
                        } else if (firTypeProjection instanceof FirStarProjection) {
                            ConeTypeVariableType defaultType = coneTypeVariable.getDefaultType();
                            FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) CollectionsKt.firstOrNull((List) firTypeParameterRef.getSymbol().getResolvedBounds());
                            if (firResolvedTypeRef == null || (type = FirTypeUtilsKt.getConeType(firResolvedTypeRef)) == null) {
                                type = resolutionContext.getSession().getBuiltinTypes().getNullableAnyType().getType();
                            }
                            builder.addEqualityConstraint(defaultType, type, SimpleConstraintSystemConstraintPosition.INSTANCE);
                        } else {
                            boolean z = firTypeProjection instanceof FirPlaceholderProjection;
                        }
                    }
                    if (builder.getHasContradiction()) {
                        Iterator<ConstraintSystemError> it = builder.getErrors().iterator();
                        while (it.hasNext()) {
                            checkerSink.reportDiagnostic(new InferenceError(it.next()));
                        }
                        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
                    }
                    return Unit.INSTANCE;
                }
                checkerSink.reportDiagnostic(InapplicableCandidate.INSTANCE);
                return (checkerSink.getNeedYielding() && (objYield2 = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield2 : Unit.INSTANCE;
            }
        }
        candidate.setSubstitutor(ConeSubstitutor.Empty.INSTANCE);
        candidate.setFreshVariables(CollectionsKt.emptyList());
        return Unit.INSTANCE;
    }
}
