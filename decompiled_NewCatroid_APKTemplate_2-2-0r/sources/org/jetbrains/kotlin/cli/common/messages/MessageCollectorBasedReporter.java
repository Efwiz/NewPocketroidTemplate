package org.jetbrains.kotlin.cli.common.messages;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;

/* JADX INFO: compiled from: DefaultDiagnosticReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/MessageCollectorBasedReporter;", "Lorg/jetbrains/kotlin/cli/common/messages/DiagnosticMessageReporter;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "getMessageCollector", "()Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "report", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "file", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "render", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MessageCollectorBasedReporter extends DiagnosticMessageReporter {
    MessageCollector getMessageCollector();

    @Override // org.jetbrains.kotlin.cli.common.messages.DiagnosticMessageReporter
    default void report(Diagnostic diagnostic, PsiFile file, String render) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(render, "render");
        getMessageCollector().report(AnalyzerWithCompilerReport.INSTANCE.convertSeverity(diagnostic.getSeverity()), render, MessageUtil.psiFileToMessageLocation(file, file.getName(), DiagnosticUtils.getLineAndColumnRange(diagnostic)));
    }
}
