package org.jetbrains.kotlin.diagnostics;

import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;

/* JADX INFO: loaded from: classes4.dex */
public interface DiagnosticSink {
    public static final DiagnosticSink DO_NOTHING = new DiagnosticSink() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticSink.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "diagnostic", "org/jetbrains/kotlin/diagnostics/DiagnosticSink$1", "report"));
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
        public void report(Diagnostic diagnostic) {
            if (diagnostic == null) {
                $$$reportNull$$$0(0);
            }
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
        public boolean wantsDiagnostics() {
            return false;
        }
    };
    public static final DiagnosticSink THROW_EXCEPTION = new DiagnosticSink() { // from class: org.jetbrains.kotlin.diagnostics.DiagnosticSink.2
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "diagnostic", "org/jetbrains/kotlin/diagnostics/DiagnosticSink$2", "report"));
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
        public boolean wantsDiagnostics() {
            return true;
        }

        @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
        public void report(Diagnostic diagnostic) {
            if (diagnostic == null) {
                $$$reportNull$$$0(0);
            }
            if (diagnostic.getSeverity() != Severity.ERROR) {
                return;
            }
            throw new IllegalStateException(diagnostic.getFactory().getName() + ": " + DefaultErrorMessages.render(diagnostic) + " " + PsiDiagnosticUtils.atLocation(diagnostic.getPsiFile(), diagnostic.getTextRanges().get(0)));
        }
    };

    public interface DiagnosticsCallback {
        void callback(Diagnostic diagnostic);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "callback";
        objArr[1] = "org/jetbrains/kotlin/diagnostics/DiagnosticSink";
        if (i != 1) {
            objArr[2] = "setCallback";
        } else {
            objArr[2] = "setCallbackIfNotSet";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    void report(Diagnostic diagnostic);

    default void resetCallback() {
    }

    default boolean setCallbackIfNotSet(DiagnosticsCallback diagnosticsCallback) {
        if (diagnosticsCallback != null) {
            return false;
        }
        $$$reportNull$$$0(1);
        return false;
    }

    boolean wantsDiagnostics();

    @Deprecated
    default void setCallback(DiagnosticsCallback diagnosticsCallback) {
        if (diagnosticsCallback == null) {
            $$$reportNull$$$0(0);
        }
        setCallbackIfNotSet(diagnosticsCallback);
    }
}
