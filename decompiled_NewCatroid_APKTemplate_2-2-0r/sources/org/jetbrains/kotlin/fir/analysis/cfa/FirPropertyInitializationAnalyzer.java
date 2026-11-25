package org.jetbrains.kotlin.fir.analysis.cfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.cfa.util.PropertyInitializationInfoData;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;

/* JADX INFO: compiled from: FirPropertyInitializationAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/FirPropertyInitializationAnalyzer;", "Lorg/jetbrains/kotlin/fir/analysis/cfa/AbstractFirPropertyInitializationChecker;", "()V", "analyze", "", "data", "Lorg/jetbrains/kotlin/fir/analysis/cfa/util/PropertyInitializationInfoData;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyInitializationAnalyzer extends AbstractFirPropertyInitializationChecker {
    public static final FirPropertyInitializationAnalyzer INSTANCE = new FirPropertyInitializationAnalyzer();

    private FirPropertyInitializationAnalyzer() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.cfa.AbstractFirPropertyInitializationChecker
    public void analyze(PropertyInitializationInfoData data, DiagnosticReporter reporter, CheckerContext context) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(context, "context");
        FirPropertyInitializationAnalyzerKt.checkPropertyAccesses(data, false, context, reporter);
    }
}
