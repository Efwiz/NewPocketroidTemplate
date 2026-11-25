package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/NoValueForParameter;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)V", "getFunction", "()Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getValueParameter", "()Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoValueForParameter extends ResolutionDiagnostic {
    private final FirFunction function;
    private final FirValueParameter valueParameter;

    public final FirValueParameter getValueParameter() {
        return this.valueParameter;
    }

    public final FirFunction getFunction() {
        return this.function;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoValueForParameter(FirValueParameter valueParameter, FirFunction function) {
        super(CandidateApplicability.INAPPLICABLE_ARGUMENTS_MAPPING_ERROR);
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(function, "function");
        this.valueParameter = valueParameter;
        this.function = function;
    }
}
