package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor;

/* JADX INFO: compiled from: FirJsNameCharsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsNameCharsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsNameCharsChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJsNameCharsChecker INSTANCE = new FirJsNameCharsChecker();

    private FirJsNameCharsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping)) {
            return;
        }
        if (((declaration instanceof FirPropertyAccessor) || (declaration instanceof FirPrimaryConstructor)) && FirJsHelpersKt.getJsName(declaration.getSymbol(), context.getSession()) == null) {
            return;
        }
        if (declaration instanceof FirConstructor) {
            FirConstructor firConstructor = (FirConstructor) declaration;
            if (FirJsHelpersKt.getJsName(firConstructor.getSymbol(), context.getSession()) == null && FirJsHelpersKt.isExportedObject(firConstructor.getSymbol(), context.getSession())) {
                return;
            }
        }
        String stableNameInJavaScript = FirJsHelpersKt.getStableNameInJavaScript(declaration.getSymbol(), context.getSession());
        if (stableNameInJavaScript == null || Intrinsics.areEqual(FirJsHelpersKt.sanitizeName(stableNameInJavaScript), stableNameInJavaScript)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNAME_CONTAINS_ILLEGAL_CHARS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
