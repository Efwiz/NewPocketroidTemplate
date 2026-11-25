package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeOuterClassArgumentsRequired;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeOuterClassArgumentsRequired implements ConeDiagnostic {
    private final String reason;
    private final FirClassLikeSymbol<?> symbol;

    public ConeOuterClassArgumentsRequired(FirClassLikeSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.symbol = symbol;
        this.reason = "Type arguments should be specified for an outer class";
    }

    public final FirClassLikeSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return this.reason;
    }
}
