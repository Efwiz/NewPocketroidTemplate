package org.jetbrains.kotlin.fir.lightTree;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtOffsetsOnlySourceElement;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.builder.FirSyntaxErrors;

/* JADX INFO: compiled from: LightTreeParsingErrorListener.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"toKotlinParsingErrorListener", "Lorg/jetbrains/kotlin/fir/lightTree/LightTreeParsingErrorListener;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "light-tree2fir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightTreeParsingErrorListenerKt {
    /* JADX WARN: Type inference failed for: r0v3, types: [org.jetbrains.kotlin.fir.lightTree.LightTreeParsingErrorListenerKt$toKotlinParsingErrorListener$diagnosticContext$1] */
    public static final LightTreeParsingErrorListener toKotlinParsingErrorListener(final DiagnosticReporter diagnosticReporter, final KtSourceFile sourceFile, final LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        final ?? r0 = new DiagnosticContext(sourceFile, languageVersionSettings) { // from class: org.jetbrains.kotlin.fir.lightTree.LightTreeParsingErrorListenerKt$toKotlinParsingErrorListener$diagnosticContext$1
            final /* synthetic */ LanguageVersionSettings $languageVersionSettings;
            private final String containingFilePath;

            @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
            public boolean isDiagnosticSuppressed(KtDiagnostic diagnostic) {
                Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
                return false;
            }

            {
                this.$languageVersionSettings = languageVersionSettings;
                this.containingFilePath = sourceFile.getPath();
            }

            @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
            public String getContainingFilePath() {
                return this.containingFilePath;
            }

            @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
            /* JADX INFO: renamed from: getLanguageVersionSettings, reason: from getter */
            public LanguageVersionSettings get$languageVersionSettings() {
                return this.$languageVersionSettings;
            }
        };
        return new LightTreeParsingErrorListener() { // from class: org.jetbrains.kotlin.fir.lightTree.LightTreeParsingErrorListenerKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.fir.lightTree.LightTreeParsingErrorListener
            public final void onError(int i, int i2, String str) {
                LightTreeParsingErrorListenerKt.toKotlinParsingErrorListener$lambda$0(diagnosticReporter, r0, i, i2, str);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toKotlinParsingErrorListener$lambda$0(DiagnosticReporter this_toKotlinParsingErrorListener, LightTreeParsingErrorListenerKt$toKotlinParsingErrorListener$diagnosticContext$1 diagnosticContext, int i, int i2, String str) {
        Intrinsics.checkNotNullParameter(this_toKotlinParsingErrorListener, "$this_toKotlinParsingErrorListener");
        Intrinsics.checkNotNullParameter(diagnosticContext, "$diagnosticContext");
        KtOffsetsOnlySourceElement ktOffsetsOnlySourceElement = new KtOffsetsOnlySourceElement(i, i2);
        KtDiagnosticFactory1<String> syntax = FirSyntaxErrors.INSTANCE.getSYNTAX();
        if (str == null) {
            str = "";
        }
        KtDiagnosticReportHelpersKt.reportOn$default(this_toKotlinParsingErrorListener, ktOffsetsOnlySourceElement, syntax, str, diagnosticContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
