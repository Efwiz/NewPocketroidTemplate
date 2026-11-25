package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: DiagnosticReporter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001e\u0010\n\u001a\u00060\u000bR\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\u001a\u0010\u0015\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "diagnosticReporter", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getDiagnosticReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "at", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext$DiagnosticContextImpl;", "sourceElement", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "containingFilePath", "", "checkAndCommitReportsOn", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "DiagnosticContextImpl", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtDiagnosticReporterWithContext extends DiagnosticReporter {
    private final DiagnosticReporter diagnosticReporter;
    private final LanguageVersionSettings languageVersionSettings;

    public final DiagnosticReporter getDiagnosticReporter() {
        return this.diagnosticReporter;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public KtDiagnosticReporterWithContext(DiagnosticReporter diagnosticReporter, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "diagnosticReporter");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.diagnosticReporter = diagnosticReporter;
        this.languageVersionSettings = languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticReporter
    public void report(KtDiagnostic diagnostic, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.diagnosticReporter.report(diagnostic, context);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticReporter
    public void checkAndCommitReportsOn(AbstractKtSourceElement element, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(element, "element");
        this.diagnosticReporter.checkAndCommitReportsOn(element, context);
    }

    public DiagnosticContextImpl at(AbstractKtSourceElement sourceElement, String containingFilePath) {
        Intrinsics.checkNotNullParameter(containingFilePath, "containingFilePath");
        return new DiagnosticContextImpl(this, sourceElement, containingFilePath);
    }

    /* JADX INFO: compiled from: DiagnosticReporter.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0096\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ+\u0010\u0018\u001a\u00020\u0019\"\b\b\u0000\u0010\u001c*\u00020\u00122\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001c0\u001d2\u0006\u0010\u001e\u001a\u0002H\u001c¢\u0006\u0002\u0010\u001fJC\u0010\u0018\u001a\u00020\u0019\"\b\b\u0000\u0010\u001c*\u00020\u0012\"\b\b\u0001\u0010 *\u00020\u00122\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H 0!2\u0006\u0010\u001e\u001a\u0002H\u001c2\u0006\u0010\"\u001a\u0002H ¢\u0006\u0002\u0010#J[\u0010\u0018\u001a\u00020\u0019\"\b\b\u0000\u0010\u001c*\u00020\u0012\"\b\b\u0001\u0010 *\u00020\u0012\"\b\b\u0002\u0010$*\u00020\u00122\u0018\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H \u0012\u0004\u0012\u0002H$0%2\u0006\u0010\u001e\u001a\u0002H\u001c2\u0006\u0010\"\u001a\u0002H 2\u0006\u0010&\u001a\u0002H$¢\u0006\u0002\u0010'R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext$DiagnosticContextImpl;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "sourceElement", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "containingFilePath", "", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticReporterWithContext;Lorg/jetbrains/kotlin/AbstractKtSourceElement;Ljava/lang/String;)V", "getContainingFilePath", "()Ljava/lang/String;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getSourceElement", "()Lorg/jetbrains/kotlin/AbstractKtSourceElement;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "isDiagnosticSuppressed", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "report", "", "factory", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "A", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "a", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;Ljava/lang/Object;)V", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Ljava/lang/Object;Ljava/lang/Object;)V", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "c", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public class DiagnosticContextImpl implements DiagnosticContext {
        private final String containingFilePath;
        private final AbstractKtSourceElement sourceElement;
        final /* synthetic */ KtDiagnosticReporterWithContext this$0;

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
        public boolean isDiagnosticSuppressed(KtDiagnostic diagnostic) {
            Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
            return false;
        }

        public DiagnosticContextImpl(KtDiagnosticReporterWithContext ktDiagnosticReporterWithContext, AbstractKtSourceElement abstractKtSourceElement, String containingFilePath) {
            Intrinsics.checkNotNullParameter(containingFilePath, "containingFilePath");
            this.this$0 = ktDiagnosticReporterWithContext;
            this.sourceElement = abstractKtSourceElement;
            this.containingFilePath = containingFilePath;
        }

        public final AbstractKtSourceElement getSourceElement() {
            return this.sourceElement;
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
        public String getContainingFilePath() {
            return this.containingFilePath;
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticContext
        /* JADX INFO: renamed from: getLanguageVersionSettings */
        public LanguageVersionSettings get$languageVersionSettings() {
            return this.this$0.getLanguageVersionSettings();
        }

        public final void report(KtDiagnosticFactory0 factory) {
            Intrinsics.checkNotNullParameter(factory, "factory");
            AbstractKtSourceElement abstractKtSourceElement = this.sourceElement;
            if (abstractKtSourceElement != null) {
                KtDiagnosticReporterWithContext ktDiagnosticReporterWithContext = this.this$0;
                DiagnosticContextImpl diagnosticContextImpl = this;
                KtDiagnosticReportHelpersKt.reportOn$default(ktDiagnosticReporterWithContext, abstractKtSourceElement, factory, diagnosticContextImpl, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                ktDiagnosticReporterWithContext.checkAndCommitReportsOn(abstractKtSourceElement, diagnosticContextImpl);
            }
        }

        public final <A> void report(KtDiagnosticFactory1<A> factory, A a) {
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(a, "a");
            AbstractKtSourceElement abstractKtSourceElement = this.sourceElement;
            if (abstractKtSourceElement != null) {
                KtDiagnosticReporterWithContext ktDiagnosticReporterWithContext = this.this$0;
                DiagnosticContextImpl diagnosticContextImpl = this;
                KtDiagnosticReportHelpersKt.reportOn$default(ktDiagnosticReporterWithContext, abstractKtSourceElement, factory, a, diagnosticContextImpl, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                ktDiagnosticReporterWithContext.checkAndCommitReportsOn(abstractKtSourceElement, diagnosticContextImpl);
            }
        }

        public final <A, B> void report(KtDiagnosticFactory2<A, B> factory, A a, B b) {
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            AbstractKtSourceElement abstractKtSourceElement = this.sourceElement;
            if (abstractKtSourceElement != null) {
                KtDiagnosticReporterWithContext ktDiagnosticReporterWithContext = this.this$0;
                DiagnosticContextImpl diagnosticContextImpl = this;
                KtDiagnosticReportHelpersKt.reportOn$default(ktDiagnosticReporterWithContext, abstractKtSourceElement, factory, a, b, diagnosticContextImpl, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                ktDiagnosticReporterWithContext.checkAndCommitReportsOn(abstractKtSourceElement, diagnosticContextImpl);
            }
        }

        public final <A, B, C> void report(KtDiagnosticFactory3<A, B, C> factory, A a, B b, C c) {
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            Intrinsics.checkNotNullParameter(c, "c");
            AbstractKtSourceElement abstractKtSourceElement = this.sourceElement;
            if (abstractKtSourceElement != null) {
                KtDiagnosticReporterWithContext ktDiagnosticReporterWithContext = this.this$0;
                DiagnosticContextImpl diagnosticContextImpl = this;
                KtDiagnosticReportHelpersKt.reportOn(ktDiagnosticReporterWithContext, abstractKtSourceElement, (KtDiagnosticFactory3<Object, Object, Object>) factory, a, b, c, diagnosticContextImpl, (64 & 64) != 0 ? null : null);
                ktDiagnosticReporterWithContext.checkAndCommitReportsOn(abstractKtSourceElement, diagnosticContextImpl);
            }
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DiagnosticContextImpl)) {
                return false;
            }
            DiagnosticContextImpl diagnosticContextImpl = (DiagnosticContextImpl) other;
            return Intrinsics.areEqual(this.sourceElement, diagnosticContextImpl.sourceElement) && Intrinsics.areEqual(getContainingFilePath(), diagnosticContextImpl.getContainingFilePath()) && Intrinsics.areEqual(get$languageVersionSettings(), diagnosticContextImpl.get$languageVersionSettings());
        }

        public int hashCode() {
            AbstractKtSourceElement abstractKtSourceElement = this.sourceElement;
            return ((((abstractKtSourceElement != null ? abstractKtSourceElement.hashCode() : 0) * 31) + getContainingFilePath().hashCode()) * 31) + get$languageVersionSettings().hashCode();
        }
    }
}
