package org.jetbrains.kotlin.fir.resolve.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnosticWithSource;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeWrongNumberOfTypeArgumentsError;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnosticWithSource;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnmatchedTypeArgumentsError;", "desiredCount", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(ILorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;Lorg/jetbrains/kotlin/KtSourceElement;)V", "getDesiredCount", "()I", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeWrongNumberOfTypeArgumentsError extends ConeDiagnosticWithSource implements ConeUnmatchedTypeArgumentsError {
    private final int desiredCount;
    private final FirClassLikeSymbol<?> symbol;

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnmatchedTypeArgumentsError
    public int getDesiredCount() {
        return this.desiredCount;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSymbol
    public FirClassLikeSymbol<?> getSymbol() {
        return this.symbol;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeWrongNumberOfTypeArgumentsError(int i, FirClassLikeSymbol<?> symbol, KtSourceElement source) {
        super(source);
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(source, "source");
        this.desiredCount = i;
        this.symbol = symbol;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Wrong number of type arguments";
    }
}
