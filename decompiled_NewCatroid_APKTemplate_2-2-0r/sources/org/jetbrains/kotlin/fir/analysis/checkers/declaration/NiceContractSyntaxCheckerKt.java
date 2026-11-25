package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirContractDescriptionOwner;

/* JADX INFO: compiled from: NiceContractSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002¨\u0006\b"}, d2 = {"checkFeatureIsEnabled", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirContractDescriptionOwner;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NiceContractSyntaxCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkFeatureIsEnabled(FirContractDescriptionOwner firContractDescriptionOwner, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        KtSourceElement source = firContractDescriptionOwner.getContractDescription().getSource();
        if (source != null && Intrinsics.areEqual(source.getElementType(), KtNodeTypes.CONTRACT_EFFECT_LIST)) {
            LanguageVersionSettings languageVersionSettings = checkerContext.get$languageVersionSettings();
            if (languageVersionSettings.supportsFeature(LanguageFeature.ContractSyntaxV2)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, source, FirErrors.INSTANCE.getUNSUPPORTED_FEATURE(), TuplesKt.to(LanguageFeature.ContractSyntaxV2, languageVersionSettings), checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
