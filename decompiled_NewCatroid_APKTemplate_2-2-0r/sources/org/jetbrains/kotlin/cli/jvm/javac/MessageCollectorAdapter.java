package org.jetbrains.kotlin.cli.jvm.javac;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.Writer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.GroupingMessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;

/* JADX INFO: compiled from: JavacLogger.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J \u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/javac/MessageCollectorAdapter;", "Ljava/io/Writer;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;)V", "close", "", "flush", "write", "buffer", "", TypedValues.CycleType.S_WAVE_OFFSET, "", "length", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class MessageCollectorAdapter extends Writer {
    private final MessageCollector messageCollector;
    private final CompilerMessageSeverity severity;

    public MessageCollectorAdapter(MessageCollector messageCollector, CompilerMessageSeverity severity) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Intrinsics.checkNotNullParameter(severity, "severity");
        this.messageCollector = messageCollector;
        this.severity = severity;
    }

    @Override // java.io.Writer
    public void write(char[] buffer, int offset, int length) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        if (length == 1 && buffer[0] == '\n') {
            return;
        }
        MessageCollector.report$default(this.messageCollector, this.severity, new String(buffer, offset, length), null, 4, null);
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        MessageCollector messageCollector = this.messageCollector;
        GroupingMessageCollector groupingMessageCollector = messageCollector instanceof GroupingMessageCollector ? (GroupingMessageCollector) messageCollector : null;
        if (groupingMessageCollector != null) {
            groupingMessageCollector.flush();
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flush();
    }
}
