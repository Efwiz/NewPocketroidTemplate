package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.DoubleColonLHS;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemTransaction;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckCallableReferenceExpectedType;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckCallableReferenceExpectedType extends CheckerStage {
    public static final CheckCallableReferenceExpectedType INSTANCE = new CheckCallableReferenceExpectedType();

    private CheckCallableReferenceExpectedType() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(final Candidate candidate, final CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        final ConstraintSystemBuilder outerCSBuilder = callInfo.getOuterCSBuilder();
        if (outerCSBuilder == null) {
            return Unit.INSTANCE;
        }
        final ConeKotlinType expectedType = callInfo.getExpectedType();
        if (!(candidate.getSymbol() instanceof FirCallableSymbol)) {
            return Unit.INSTANCE;
        }
        ConeKotlinType coneKotlinType = null;
        if (callInfo.getLhs() instanceof DoubleColonLHS.Type) {
            ConeKotlinType type = callInfo.getLhs().getType();
            if (!(callInfo.getExplicitReceiver() instanceof FirResolvedQualifier)) {
                coneKotlinType = type;
            }
        }
        Pair pairBuildReflectionType = CallableReferenceResolutionKt.buildReflectionType((FirCallableDeclaration) ((FirCallableSymbol) candidate.getSymbol()).getFir(), coneKotlinType, candidate, resolutionContext);
        ConeKotlinType coneKotlinType2 = (ConeKotlinType) pairBuildReflectionType.component1();
        CallableReferenceAdaptation callableReferenceAdaptation = (CallableReferenceAdaptation) pairBuildReflectionType.component2();
        final ConeKotlinType coneKotlinTypeSubstituteOrSelf = candidate.getSubstitutor().substituteOrSelf(coneKotlinType2);
        if (CallableReferenceResolutionKt.needCompatibilityResolveForCallableReference(callableReferenceAdaptation) && !FirLanguageSettingsComponentKt.getLanguageVersionSettings(resolutionContext.getSession()).supportsFeature(LanguageFeature.DisableCompatibilityModeForNewInference)) {
            checkerSink.reportDiagnostic(LowerPriorityToPreserveCompatibilityDiagnostic.INSTANCE);
        }
        candidate.setResultingTypeForCallableReference(coneKotlinTypeSubstituteOrSelf);
        candidate.setCallableReferenceAdaptation$resolve(callableReferenceAdaptation);
        candidate.setOuterConstraintBuilderEffect(new Function1<ConstraintSystemOperation, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckCallableReferenceExpectedType.check.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ConstraintSystemOperation constraintSystemOperation) {
                invoke2(constraintSystemOperation);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ConstraintSystemOperation constraintSystemOperation) {
                Intrinsics.checkNotNullParameter(constraintSystemOperation, "<this>");
                constraintSystemOperation.addOtherSystem(candidate.getSystem().currentStorage());
                ConeArgumentConstraintPosition coneArgumentConstraintPosition = new ConeArgumentConstraintPosition(callInfo.getCallSite());
                if (expectedType != null) {
                    ConeKotlinType coneKotlinType3 = coneKotlinTypeSubstituteOrSelf;
                    final ConstraintSystemBuilder constraintSystemBuilder = outerCSBuilder;
                    if (ConeTypeUtilsKt.contains(coneKotlinType3, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckCallableReferenceExpectedType.check.2.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(ConeKotlinType it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return Boolean.valueOf((it instanceof ConeTypeVariableType) && !constraintSystemBuilder.currentStorage().getAllTypeVariables().containsKey(((ConeTypeVariableType) it).getLookupTag()));
                        }
                    })) {
                        return;
                    }
                    constraintSystemOperation.addSubtypeConstraint(coneKotlinTypeSubstituteOrSelf, expectedType, coneArgumentConstraintPosition);
                }
            }
        });
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = outerCSBuilder.prepareTransaction();
        ConstraintSystemBuilder constraintSystemBuilder = outerCSBuilder;
        Function1<ConstraintSystemOperation, Unit> outerConstraintBuilderEffect = candidate.getOuterConstraintBuilderEffect();
        Intrinsics.checkNotNull(outerConstraintBuilderEffect);
        outerConstraintBuilderEffect.invoke(constraintSystemBuilder);
        boolean hasContradiction = constraintSystemBuilder.getHasContradiction();
        constraintSystemTransactionPrepareTransaction.rollbackTransaction();
        if (!hasContradiction) {
            return Unit.INSTANCE;
        }
        checkerSink.reportDiagnostic(InapplicableCandidate.INSTANCE);
        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
    }
}
