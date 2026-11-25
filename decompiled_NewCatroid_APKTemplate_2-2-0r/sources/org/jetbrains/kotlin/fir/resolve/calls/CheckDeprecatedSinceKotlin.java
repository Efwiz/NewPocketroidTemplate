package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\rJ$\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\f\u0010\u0012\u001a\u00020\u000f*\u00020\u0013H\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckDeprecatedSinceKotlin;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isHiddenForThisCallSite", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "isSuperCall", "Lorg/jetbrains/kotlin/fir/FirElement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckDeprecatedSinceKotlin extends ResolutionStage {
    public static final CheckDeprecatedSinceKotlin INSTANCE = new CheckDeprecatedSinceKotlin();

    private CheckDeprecatedSinceKotlin() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        FirCallableSymbol<?> firCallableSymbol = symbol instanceof FirCallableSymbol ? (FirCallableSymbol) symbol : null;
        if (firCallableSymbol == null) {
            return Unit.INSTANCE;
        }
        DeprecationInfo deprecation = DeprecationUtilsKt.getDeprecation(firCallableSymbol, resolutionContext.getSession(), callInfo.getCallSite());
        if ((deprecation != null ? deprecation.getDeprecationLevel() : null) != DeprecationLevelValue.HIDDEN && !isHiddenForThisCallSite(firCallableSymbol, callInfo, candidate)) {
            return Unit.INSTANCE;
        }
        checkerSink.reportDiagnostic(HiddenCandidate.INSTANCE);
        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isHiddenForThisCallSite(FirCallableSymbol<?> symbol, CallInfo callInfo, Candidate candidate) {
        if (isSuperCall(callInfo.getCallSite()) || ((FirCallableDeclaration) symbol.getFir()).getDispatchReceiverType() == null || !(symbol instanceof FirNamedFunctionSymbol)) {
            return false;
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) symbol;
        if (Intrinsics.areEqual((Object) DeprecationUtilsKt.isHiddenEverywhereBesideSuperCalls((FirCallableDeclaration) firNamedFunctionSymbol.getFir()), (Object) true)) {
            return true;
        }
        FirScope originScope = candidate.getOriginScope();
        FirTypeScope firTypeScope = originScope instanceof FirTypeScope ? (FirTypeScope) originScope : null;
        if (firTypeScope == null) {
            return false;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FirTypeScopeKt.processOverriddenFunctions(firTypeScope, firNamedFunctionSymbol, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.CheckDeprecatedSinceKotlin.isHiddenForThisCallSite.1
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (Intrinsics.areEqual((Object) DeprecationUtilsKt.isHiddenEverywhereBesideSuperCalls((FirCallableDeclaration) it.getFir()), (Object) true)) {
                    booleanRef.element = true;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
        return booleanRef.element;
    }

    private final boolean isSuperCall(FirElement firElement) {
        if (firElement instanceof FirQualifiedAccessExpression) {
            FirExpression explicitReceiver = ((FirQualifiedAccessExpression) firElement).getExplicitReceiver();
            if ((explicitReceiver != null ? FirExpressionUtilKt.getCalleeReference(explicitReceiver) : null) instanceof FirSuperReference) {
                return true;
            }
        }
        return false;
    }
}
