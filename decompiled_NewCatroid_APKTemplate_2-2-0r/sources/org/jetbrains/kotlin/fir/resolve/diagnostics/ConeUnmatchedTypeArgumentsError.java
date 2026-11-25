package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeUnmatchedTypeArgumentsError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeDiagnosticWithSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "desiredCount", "", "getDesiredCount", "()I", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeNoTypeArgumentsOnRhsError;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeWrongNumberOfTypeArgumentsError;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConeUnmatchedTypeArgumentsError extends ConeDiagnosticWithSymbol<FirClassLikeSymbol<?>> {
    int getDesiredCount();
}
