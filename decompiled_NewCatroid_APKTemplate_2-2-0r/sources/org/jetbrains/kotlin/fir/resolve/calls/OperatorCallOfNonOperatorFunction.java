package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/OperatorCallOfNonOperatorFunction;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "function", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)V", "getFunction", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorCallOfNonOperatorFunction extends ResolutionDiagnostic {
    private final FirNamedFunctionSymbol function;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OperatorCallOfNonOperatorFunction(FirNamedFunctionSymbol function) {
        super(CandidateApplicability.CONVENTION_ERROR);
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
    }

    public final FirNamedFunctionSymbol getFunction() {
        return this.function;
    }
}
