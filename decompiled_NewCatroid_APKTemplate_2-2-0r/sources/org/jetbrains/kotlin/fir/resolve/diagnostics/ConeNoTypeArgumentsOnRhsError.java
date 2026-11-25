package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeNoTypeArgumentsOnRhsError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnmatchedTypeArgumentsError;", "desiredCount", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(ILorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)V", "getDesiredCount", "()I", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeNoTypeArgumentsOnRhsError implements ConeUnmatchedTypeArgumentsError {
    private final int desiredCount;
    private final FirClassLikeSymbol<?> symbol;

    public ConeNoTypeArgumentsOnRhsError(int i, FirClassLikeSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.desiredCount = i;
        this.symbol = symbol;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnmatchedTypeArgumentsError
    public int getDesiredCount() {
        return this.desiredCount;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSymbol
    public FirClassLikeSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "No type arguments on RHS";
    }
}
