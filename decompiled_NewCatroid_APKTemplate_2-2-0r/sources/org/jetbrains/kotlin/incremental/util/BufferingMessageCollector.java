package org.jetbrains.kotlin.incremental.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: BufferingMessageCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0001J\b\u0010\n\u001a\u00020\u000bH\u0016J\"\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/util/BufferingMessageCollector;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "()V", "messages", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/incremental/util/BufferingMessageCollector$Message;", LineReader.CLEAR, "", "flush", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "hasErrors", "", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "Message", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BufferingMessageCollector implements MessageCollector {
    private final ArrayList<Message> messages = new ArrayList<>();

    /* JADX INFO: compiled from: BufferingMessageCollector.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/util/BufferingMessageCollector$Message;", "", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "(Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;)V", "getLocation", "()Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "getMessage", "()Ljava/lang/String;", "getSeverity", "()Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Message {
        private final CompilerMessageSourceLocation location;
        private final String message;
        private final CompilerMessageSeverity severity;

        public Message(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation compilerMessageSourceLocation) {
            Intrinsics.checkNotNullParameter(severity, "severity");
            Intrinsics.checkNotNullParameter(message, "message");
            this.severity = severity;
            this.message = message;
            this.location = compilerMessageSourceLocation;
        }

        public final CompilerMessageSourceLocation getLocation() {
            return this.location;
        }

        public final String getMessage() {
            return this.message;
        }

        public final CompilerMessageSeverity getSeverity() {
            return this.severity;
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this.messages.clear();
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        this.messages.add(new Message(severity, message, location));
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public boolean hasErrors() {
        ArrayList<Message> arrayList = this.messages;
        if ((arrayList instanceof Collection) && arrayList.isEmpty()) {
            return false;
        }
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            if (((Message) it.next()).getSeverity().isError()) {
                return true;
            }
        }
        return false;
    }

    public final void flush(MessageCollector delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        for (Message message : this.messages) {
            delegate.report(message.getSeverity(), message.getMessage(), message.getLocation());
        }
    }
}
