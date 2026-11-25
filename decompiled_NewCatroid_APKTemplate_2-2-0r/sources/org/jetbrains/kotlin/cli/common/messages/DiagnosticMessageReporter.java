package org.jetbrains.kotlin.cli.common.messages;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.Diagnostic;

/* JADX INFO: compiled from: DiagnosticMessageReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/DiagnosticMessageReporter;", "", "report", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "render", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticMessageReporter {
    void report(Diagnostic diagnostic, PsiFile file, String render);
}
