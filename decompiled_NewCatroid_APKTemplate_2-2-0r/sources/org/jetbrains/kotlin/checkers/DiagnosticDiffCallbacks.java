package org.jetbrains.kotlin.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic;

/* JADX INFO: compiled from: DiagnosticDiffCallbacks.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H&J \u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H&J(\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/checkers/DiagnosticDiffCallbacks;", "", "missingDiagnostic", "", "diagnostic", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "expectedStart", "", "expectedEnd", "unexpectedDiagnostic", "actualStart", "actualEnd", "wrongParametersDiagnostic", "expectedDiagnostic", "actualDiagnostic", "start", "end", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticDiffCallbacks {
    void missingDiagnostic(TextDiagnostic diagnostic, int expectedStart, int expectedEnd);

    void unexpectedDiagnostic(TextDiagnostic diagnostic, int actualStart, int actualEnd);

    void wrongParametersDiagnostic(TextDiagnostic expectedDiagnostic, TextDiagnostic actualDiagnostic, int start, int end);
}
