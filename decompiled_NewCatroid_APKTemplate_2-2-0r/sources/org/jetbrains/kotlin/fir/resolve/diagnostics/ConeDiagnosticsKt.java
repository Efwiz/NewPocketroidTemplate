package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002¨\u0006\u0004"}, d2 = {"describeSymbol", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeDiagnosticsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String describeSymbol(FirBasedSymbol<?> firBasedSymbol) {
        if (!(firBasedSymbol instanceof FirClassLikeSymbol)) {
            return firBasedSymbol instanceof FirCallableSymbol ? ((FirCallableSymbol) firBasedSymbol).getCallableId().toString() : String.valueOf(firBasedSymbol);
        }
        String strAsString = ((FirClassLikeSymbol) firBasedSymbol).getClassId().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }
}
