package org.jetbrains.kotlin.diagnostics.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;

/* JADX INFO: compiled from: DeduplicatingDiagnosticReporter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"deduplicating", "Lorg/jetbrains/kotlin/diagnostics/impl/DeduplicatingDiagnosticReporter;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeduplicatingDiagnosticReporterKt {
    public static final DeduplicatingDiagnosticReporter deduplicating(DiagnosticReporter diagnosticReporter) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        return new DeduplicatingDiagnosticReporter(diagnosticReporter);
    }
}
