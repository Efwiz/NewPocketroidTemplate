package org.jetbrains.kotlin.fir.analysis.checkers.type;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnsupportedDefaultValueInFunctionType;

/* JADX INFO: compiled from: FirUnsupportedDefaultValueInFunctionTypeParameterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirUnsupportedDefaultValueInFunctionTypeParameterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirFunctionalTypeParameterSyntaxChecker;", "()V", "checkPsiOrLightTree", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "report", "defaultValueSource", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUnsupportedDefaultValueInFunctionTypeParameterChecker extends FirFunctionalTypeParameterSyntaxChecker {
    public static final FirUnsupportedDefaultValueInFunctionTypeParameterChecker INSTANCE = new FirUnsupportedDefaultValueInFunctionTypeParameterChecker();

    private FirUnsupportedDefaultValueInFunctionTypeParameterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker
    public void checkPsiOrLightTree(FirFunctionTypeParameter element, KtSourceElement source, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement defaultValueForParameter = FirHelpersKt.getDefaultValueForParameter(source);
        if (defaultValueForParameter == null) {
            return;
        }
        report(defaultValueForParameter, reporter, context);
    }

    private final void report(KtSourceElement defaultValueSource, DiagnosticReporter reporter, CheckerContext context) {
        ConeUnsupportedDefaultValueInFunctionType coneUnsupportedDefaultValueInFunctionType = new ConeUnsupportedDefaultValueInFunctionType(defaultValueSource);
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, coneUnsupportedDefaultValueInFunctionType.getSource(), FirErrors.INSTANCE.getUNSUPPORTED(), coneUnsupportedDefaultValueInFunctionType.getReason(), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
