package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;

/* JADX INFO: compiled from: FirExplicitBackingFieldsUnsupportedChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirExplicitBackingFieldsUnsupportedChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBackingFieldChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitBackingFieldsUnsupportedChecker extends FirDeclarationChecker<FirBackingField> {
    public static final FirExplicitBackingFieldsUnsupportedChecker INSTANCE = new FirExplicitBackingFieldsUnsupportedChecker();

    private FirExplicitBackingFieldsUnsupportedChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirBackingField declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((declaration instanceof FirDefaultPropertyBackingField) || FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ExplicitBackingFields)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.ExplicitBackingFields, FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession())), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
