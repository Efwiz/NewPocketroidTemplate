package org.jetbrains.kotlin.cli.common.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.diagnostics.impl.BaseDiagnosticsCollector;

/* JADX INFO: compiled from: FirDiagnosticsCompilerResultsReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"reportToMessageCollector", "", "Lorg/jetbrains/kotlin/diagnostics/impl/BaseDiagnosticsCollector;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "renderDiagnosticName", "", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDiagnosticsCompilerResultsReporterKt {
    public static final void reportToMessageCollector(BaseDiagnosticsCollector baseDiagnosticsCollector, MessageCollector messageCollector, boolean z) {
        Intrinsics.checkNotNullParameter(baseDiagnosticsCollector, "<this>");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        FirDiagnosticsCompilerResultsReporter.INSTANCE.reportToMessageCollector(baseDiagnosticsCollector, messageCollector, z);
    }
}
