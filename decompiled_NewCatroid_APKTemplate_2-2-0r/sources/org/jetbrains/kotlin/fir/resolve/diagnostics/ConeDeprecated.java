package org.jetbrains.kotlin.fir.resolve.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B#\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0002X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDeprecated;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", NativeSymbol.TYPE_NAME, "deprecationInfo", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;)V", "getDeprecationInfo", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "reason", "", "getReason", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeDeprecated implements ConeDiagnosticWithSymbol<FirBasedSymbol<?>> {
    private final DeprecationInfo deprecationInfo;
    private final KtSourceElement source;
    private final FirBasedSymbol<?> symbol;

    public ConeDeprecated(KtSourceElement ktSourceElement, FirBasedSymbol<?> symbol, DeprecationInfo deprecationInfo) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(deprecationInfo, "deprecationInfo");
        this.source = ktSourceElement;
        this.symbol = symbol;
        this.deprecationInfo = deprecationInfo;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSymbol
    public FirBasedSymbol<?> getSymbol() {
        return this.symbol;
    }

    public final DeprecationInfo getDeprecationInfo() {
        return this.deprecationInfo;
    }

    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Deprecated: " + this.deprecationInfo.getMessage();
    }
}
