package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckArguments;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckArguments extends CheckerStage {
    public static final CheckArguments INSTANCE = new CheckArguments();

    private CheckArguments() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        FirLazyDeclarationResolverKt.lazyResolveToPhase(candidate.getSymbol(), FirResolvePhase.STATUS);
        LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
        if (argumentMapping == null) {
            throw new IllegalStateException("Argument should be already mapped while checking arguments!".toString());
        }
        for (FirExpression firExpression : callInfo.getArguments()) {
            ArgumentsKt.resolveArgument(candidate, callInfo, firExpression, argumentMapping.get(firExpression), false, checkerSink, resolutionContext);
        }
        if (!candidate.getSystem().getHasContradiction() || callInfo.getArguments().isEmpty()) {
            if (candidate.getUsesSAM() && !ResolutionStagesKt.isJavaApplicableCandidate(candidate)) {
                ResolutionStagesKt.markCandidateForCompatibilityResolve(checkerSink, resolutionContext);
            }
            return Unit.INSTANCE;
        }
        checkerSink.reportDiagnostic(InapplicableCandidate.INSTANCE);
        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
    }
}
