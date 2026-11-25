package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0011\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeVisibilityError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", NativeSymbol.TYPE_NAME, "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeVisibilityError implements ConeDiagnosticWithSymbol<FirBasedSymbol<?>> {
    private final FirBasedSymbol<?> symbol;

    public ConeVisibilityError(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.symbol = symbol;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSymbol
    public FirBasedSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "HIDDEN: " + ConeDiagnosticsKt.describeSymbol(getSymbol()) + " is invisible";
    }
}
