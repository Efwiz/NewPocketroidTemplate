package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: compiled from: DiagnosticReporter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u001a\u0010\t\u001a\u00020\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "", "()V", "checkAndCommitReportsOn", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "context", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticContext;", "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DiagnosticReporter {
    public void checkAndCommitReportsOn(AbstractKtSourceElement element, DiagnosticContext context) {
        Intrinsics.checkNotNullParameter(element, "element");
    }

    public abstract void report(KtDiagnostic diagnostic, DiagnosticContext context);
}
