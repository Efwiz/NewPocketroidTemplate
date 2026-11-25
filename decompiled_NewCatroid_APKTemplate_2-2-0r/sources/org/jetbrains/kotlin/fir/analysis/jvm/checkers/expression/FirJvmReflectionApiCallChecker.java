package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.AbstractFirReflectionApiCallChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirJvmReflectionApiCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014J\"\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmReflectionApiCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/AbstractFirReflectionApiCallChecker;", "()V", "isWholeReflectionApiAvailable", "", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "report", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmReflectionApiCallChecker extends AbstractFirReflectionApiCallChecker {
    public static final FirJvmReflectionApiCallChecker INSTANCE = new FirJvmReflectionApiCallChecker();

    private FirJvmReflectionApiCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.AbstractFirReflectionApiCallChecker
    protected boolean isWholeReflectionApiAvailable(CheckerContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return FirSymbolProviderKt.getSymbolProvider(context.getSession()).getClassLikeSymbolByClassId(JvmAbi.INSTANCE.getREFLECTION_FACTORY_IMPL()) != null;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.AbstractFirReflectionApiCallChecker
    protected void report(KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJvmErrors.INSTANCE.getNO_REFLECTION_IN_CLASS_PATH(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
