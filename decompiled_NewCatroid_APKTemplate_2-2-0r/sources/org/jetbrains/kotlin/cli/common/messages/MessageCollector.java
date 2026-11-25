package org.jetbrains.kotlin.cli.common.messages;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: MessageCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \r2\u00020\u0001:\u0001\rJ\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J$\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "", LineReader.CLEAR, "", "hasErrors", "", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MessageCollector {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void clear();

    boolean hasErrors();

    void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location);

    /* JADX INFO: compiled from: MessageCollector.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
    }

    static /* synthetic */ void report$default(MessageCollector messageCollector, CompilerMessageSeverity compilerMessageSeverity, String str, CompilerMessageSourceLocation compilerMessageSourceLocation, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: report");
        }
        if ((i & 4) != 0) {
            compilerMessageSourceLocation = null;
        }
        messageCollector.report(compilerMessageSeverity, str, compilerMessageSourceLocation);
    }

    /* JADX INFO: compiled from: MessageCollector.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector$Companion;", "", "()V", "NONE", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "getNONE", "()Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final MessageCollector NONE = new MessageCollector() { // from class: org.jetbrains.kotlin.cli.common.messages.MessageCollector$Companion$NONE$1
            @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
            public void clear() {
            }

            @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
            public boolean hasErrors() {
                return false;
            }

            @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
            public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) {
                Intrinsics.checkNotNullParameter(severity, "severity");
                Intrinsics.checkNotNullParameter(message, "message");
            }
        };

        private Companion() {
        }

        public final MessageCollector getNONE() {
            return NONE;
        }
    }
}
