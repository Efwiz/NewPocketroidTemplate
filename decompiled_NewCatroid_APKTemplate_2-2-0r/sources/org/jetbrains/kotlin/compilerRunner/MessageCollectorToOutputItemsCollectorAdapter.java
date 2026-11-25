package org.jetbrains.kotlin.compilerRunner;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.OutputMessageUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: MessageCollectorToOutputItemsCollectorAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\u0006\u001a\u00020\u0007H\u0096\u0001J\t\u0010\b\u001a\u00020\tH\u0096\u0001J\"\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/compilerRunner/MessageCollectorToOutputItemsCollectorAdapter;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "outputCollector", "Lorg/jetbrains/kotlin/compilerRunner/OutputItemsCollector;", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/compilerRunner/OutputItemsCollector;)V", LineReader.CLEAR, "", "hasErrors", "", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MessageCollectorToOutputItemsCollectorAdapter implements MessageCollector {
    private final MessageCollector delegate;
    private final OutputItemsCollector outputCollector;

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this.delegate.clear();
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public boolean hasErrors() {
        return this.delegate.hasErrors();
    }

    public MessageCollectorToOutputItemsCollectorAdapter(MessageCollector delegate, OutputItemsCollector outputCollector) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(outputCollector, "outputCollector");
        this.delegate = delegate;
        this.outputCollector = outputCollector;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        OutputMessageUtil.Output outputMessage = OutputMessageUtil.parseOutputMessage(message);
        if (outputMessage != null) {
            this.outputCollector.add(outputMessage.sourceFiles, outputMessage.outputFile);
        }
        this.delegate.report(severity, message, location);
    }
}
