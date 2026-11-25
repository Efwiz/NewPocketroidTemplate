package org.jetbrains.kotlin.fir.analysis.js.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.checkers.FirPlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;

/* JADX INFO: compiled from: FirJsPlatformDiagnosticSuppressor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/FirJsPlatformDiagnosticSuppressor;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirPlatformDiagnosticSuppressor;", "()V", "shouldReportNoBody", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsPlatformDiagnosticSuppressor implements FirPlatformDiagnosticSuppressor {
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirPlatformDiagnosticSuppressor
    public boolean shouldReportNoBody(FirCallableDeclaration declaration, CheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        return !FirJsPlatformDiagnosticSuppressorKt.isLexicallyInsideJsNative(declaration, context);
    }
}
