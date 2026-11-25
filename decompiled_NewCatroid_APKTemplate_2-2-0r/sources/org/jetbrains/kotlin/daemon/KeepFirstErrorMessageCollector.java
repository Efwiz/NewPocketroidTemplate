package org.jetbrains.kotlin.daemon;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.PrintStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: KotlinRemoteReplService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\"\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0006H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/daemon/KeepFirstErrorMessageCollector;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "compilerMessagesStream", "Ljava/io/PrintStream;", "(Ljava/io/PrintStream;)V", "firstErrorLocation", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "getFirstErrorLocation$kotlin_daemon", "()Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "setFirstErrorLocation$kotlin_daemon", "(Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;)V", "firstErrorMessage", "", "getFirstErrorMessage$kotlin_daemon", "()Ljava/lang/String;", "setFirstErrorMessage$kotlin_daemon", "(Ljava/lang/String;)V", "innerCollector", "Lorg/jetbrains/kotlin/cli/common/messages/PrintingMessageCollector;", LineReader.CLEAR, "", "hasErrors", "", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, FirebaseAnalytics.Param.LOCATION, "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KeepFirstErrorMessageCollector implements MessageCollector {
    private CompilerMessageSourceLocation firstErrorLocation;
    private String firstErrorMessage;
    private final PrintingMessageCollector innerCollector;

    public KeepFirstErrorMessageCollector(PrintStream compilerMessagesStream) {
        Intrinsics.checkNotNullParameter(compilerMessagesStream, "compilerMessagesStream");
        this.innerCollector = new PrintingMessageCollector(compilerMessagesStream, MessageRenderer.WITHOUT_PATHS, false);
    }

    /* JADX INFO: renamed from: getFirstErrorMessage$kotlin_daemon, reason: from getter */
    public final String getFirstErrorMessage() {
        return this.firstErrorMessage;
    }

    public final void setFirstErrorMessage$kotlin_daemon(String str) {
        this.firstErrorMessage = str;
    }

    /* JADX INFO: renamed from: getFirstErrorLocation$kotlin_daemon, reason: from getter */
    public final CompilerMessageSourceLocation getFirstErrorLocation() {
        return this.firstErrorLocation;
    }

    public final void setFirstErrorLocation$kotlin_daemon(CompilerMessageSourceLocation compilerMessageSourceLocation) {
        this.firstErrorLocation = compilerMessageSourceLocation;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        if (this.firstErrorMessage == null && severity.isError()) {
            this.firstErrorMessage = message;
            this.firstErrorLocation = location;
        }
        this.innerCollector.report(severity, message, location);
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public boolean hasErrors() {
        return this.innerCollector.hasErrors();
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this.innerCollector.clear();
    }
}
