package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/MapArguments;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MapArguments extends ResolutionStage {
    public static final MapArguments INSTANCE = new MapArguments();

    private MapArguments() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        FirFunctionSymbol firFunctionSymbol = symbol instanceof FirFunctionSymbol ? (FirFunctionSymbol) symbol : null;
        if (firFunctionSymbol == null) {
            checkerSink.reportDiagnostic(HiddenCandidate.INSTANCE);
            return Unit.INSTANCE;
        }
        FirFunction firFunction = (FirFunction) firFunctionSymbol.getFir();
        BodyResolveComponents bodyResolveComponents = resolutionContext.getBodyResolveComponents();
        List<FirExpression> arguments = callInfo.getArguments();
        FirScope originScope = candidate.getOriginScope();
        FirElement callSite = callInfo.getCallSite();
        FirFunctionCall firFunctionCall = callSite instanceof FirFunctionCall ? (FirFunctionCall) callSite : null;
        ArgumentMapping argumentMappingMapArguments = FirArgumentsToParametersMapperKt.mapArguments(bodyResolveComponents, arguments, firFunction, originScope, (firFunctionCall != null ? firFunctionCall.getOrigin() : null) == FirFunctionCallOrigin.Operator);
        candidate.setArgumentMapping(argumentMappingMapArguments.toArgumentToParameterMapping());
        candidate.setNumDefaults(argumentMappingMapArguments.numDefaults());
        Iterator<T> it = argumentMappingMapArguments.getDiagnostics().iterator();
        while (it.hasNext()) {
            checkerSink.reportDiagnostic((ResolutionDiagnostic) it.next());
        }
        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
    }
}
