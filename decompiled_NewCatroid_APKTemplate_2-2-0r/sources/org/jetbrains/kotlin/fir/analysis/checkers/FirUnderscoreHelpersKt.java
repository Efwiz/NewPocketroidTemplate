package org.jetbrains.kotlin.fir.analysis.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;

/* JADX INFO: compiled from: FirUnderscoreHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\r"}, d2 = {"isUnderscore", "", "", "(Ljava/lang/CharSequence;)Z", "checkUnderscoreDiagnostics", "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isExpression", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirUnderscoreHelpersKt {
    public static final void checkUnderscoreDiagnostics(KtSourceElement ktSourceElement, CheckerContext context, DiagnosticReporter reporter, boolean z) {
        KtSourceElementKind kind;
        CharSequence rawIdentifier;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (ktSourceElement == null || (kind = ktSourceElement.getKind()) == null) {
            return;
        }
        if (((kind instanceof KtRealSourceElementKind) || (kind instanceof KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess)) && (rawIdentifier = SourceNavigator.INSTANCE.forSource(ktSourceElement).getRawIdentifier(ktSourceElement)) != null && isUnderscore(rawIdentifier)) {
            KtSourceElement ktSourceElement2 = ktSourceElement;
            FirErrors firErrors = FirErrors.INSTANCE;
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement2, z ? firErrors.getUNDERSCORE_USAGE_WITHOUT_BACKTICKS() : firErrors.getUNDERSCORE_IS_RESERVED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    public static final boolean isUnderscore(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) != '_') {
                return false;
            }
        }
        return true;
    }
}
