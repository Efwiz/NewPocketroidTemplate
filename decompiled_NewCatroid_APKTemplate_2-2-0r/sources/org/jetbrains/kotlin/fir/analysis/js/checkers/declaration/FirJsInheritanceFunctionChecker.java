package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;

/* JADX INFO: compiled from: FirJsInheritanceFunctionChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsInheritanceFunctionChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isNotEffectivelyExternalFunctionButOverridesExternal", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsInheritanceFunctionChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirJsInheritanceFunctionChecker INSTANCE = new FirJsInheritanceFunctionChecker();

    private FirJsInheritanceFunctionChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (isNotEffectivelyExternalFunctionButOverridesExternal(declaration, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final boolean isNotEffectivelyExternalFunctionButOverridesExternal(FirDeclaration firDeclaration, CheckerContext checkerContext) {
        if (!(firDeclaration instanceof FirFunction)) {
            return false;
        }
        FirFunction firFunction = (FirFunction) firDeclaration;
        if (FirJsHelpersKt.isEffectivelyExternal(firFunction.getSymbol(), checkerContext)) {
            return false;
        }
        return FirJsHelpersKt.isOverridingExternalWithOptionalParams(firFunction.getSymbol(), checkerContext);
    }
}
