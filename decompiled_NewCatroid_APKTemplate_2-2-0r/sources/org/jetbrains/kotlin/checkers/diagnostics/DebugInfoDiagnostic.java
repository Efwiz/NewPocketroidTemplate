package org.jetbrains.kotlin.checkers.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: DiagnosticForTests.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/DebugInfoDiagnostic;", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractDiagnosticForTests;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DebugInfoDiagnostic extends AbstractDiagnosticForTests {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DebugInfoDiagnostic(KtElement element, DiagnosticFactory<?> factory) {
        super(element, factory);
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(factory, "factory");
    }
}
