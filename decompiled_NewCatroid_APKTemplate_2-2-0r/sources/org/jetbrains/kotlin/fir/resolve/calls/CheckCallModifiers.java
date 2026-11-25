package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckCallModifiers;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckCallModifiers extends CheckerStage {
    public static final CheckCallModifiers INSTANCE = new CheckCallModifiers();

    private CheckCallModifiers() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        if (callInfo.getCallSite() instanceof FirFunctionCall) {
            FirBasedSymbol<?> symbol = candidate.getSymbol();
            FirNamedFunctionSymbol firNamedFunctionSymbol = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
            if (firNamedFunctionSymbol == null) {
                return Unit.INSTANCE;
            }
            if (((FirFunctionCall) callInfo.getCallSite()).getOrigin() == FirFunctionCallOrigin.Infix && !((FirMemberDeclaration) firNamedFunctionSymbol.getFir()).getStatus().isInfix()) {
                checkerSink.reportDiagnostic(new InfixCallOfNonInfixFunction(firNamedFunctionSymbol));
            } else if (((FirFunctionCall) callInfo.getCallSite()).getOrigin() == FirFunctionCallOrigin.Operator && !((FirMemberDeclaration) firNamedFunctionSymbol.getFir()).getStatus().isOperator()) {
                checkerSink.reportDiagnostic(new OperatorCallOfNonOperatorFunction(firNamedFunctionSymbol));
            } else if (callInfo.isImplicitInvoke() && !((FirMemberDeclaration) firNamedFunctionSymbol.getFir()).getStatus().isOperator()) {
                checkerSink.reportDiagnostic(new OperatorCallOfNonOperatorFunction(firNamedFunctionSymbol));
            }
        }
        return Unit.INSTANCE;
    }
}
