package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001f\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeAmbiguouslyResolvedAnnotationArgument;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "symbolFromCompilerPhase", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "symbolFromAnnotationArgumentsPhase", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbolFromAnnotationArgumentsPhase", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getSymbolFromCompilerPhase", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeAmbiguouslyResolvedAnnotationArgument implements ConeDiagnostic {
    private final FirBasedSymbol<?> symbolFromAnnotationArgumentsPhase;
    private final FirBasedSymbol<?> symbolFromCompilerPhase;

    public ConeAmbiguouslyResolvedAnnotationArgument(FirBasedSymbol<?> symbolFromCompilerPhase, FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(symbolFromCompilerPhase, "symbolFromCompilerPhase");
        this.symbolFromCompilerPhase = symbolFromCompilerPhase;
        this.symbolFromAnnotationArgumentsPhase = firBasedSymbol;
    }

    public final FirBasedSymbol<?> getSymbolFromCompilerPhase() {
        return this.symbolFromCompilerPhase;
    }

    public final FirBasedSymbol<?> getSymbolFromAnnotationArgumentsPhase() {
        return this.symbolFromAnnotationArgumentsPhase;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "\n            Annotation symbol resolved differently on compiler annotation and symbols stages:\n              - compiler annotations: " + this.symbolFromCompilerPhase + "\n              - compiler arguments stage: " + this.symbolFromAnnotationArgumentsPhase + "\n        ";
    }
}
