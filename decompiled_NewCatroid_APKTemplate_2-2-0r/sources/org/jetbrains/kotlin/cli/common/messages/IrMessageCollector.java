package org.jetbrains.kotlin.cli.common.messages;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;

/* JADX INFO: compiled from: IrMessageCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/IrMessageCollector;", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "report", "", "severity", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Severity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Location;", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrMessageCollector implements IrMessageLogger {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final MessageCollector messageCollector;

    public IrMessageCollector(MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.messageCollector = messageCollector;
    }

    @Override // org.jetbrains.kotlin.ir.util.IrMessageLogger
    public void report(IrMessageLogger.Severity severity, String message, IrMessageLogger.Location location) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        MessageCollector messageCollector = this.messageCollector;
        Companion companion = INSTANCE;
        messageCollector.report(companion.severityToCLISeverity(severity), message, companion.locationToCLILocation(location));
    }

    /* JADX INFO: compiled from: IrMessageCollector.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/messages/IrMessageCollector$Companion;", "", "()V", "locationToCLILocation", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageLocation;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Location;", "severityToCLISeverity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", "severity", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Severity;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: IrMessageCollector.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[IrMessageLogger.Severity.values().length];
                try {
                    iArr[IrMessageLogger.Severity.INFO.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[IrMessageLogger.Severity.WARNING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[IrMessageLogger.Severity.ERROR.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CompilerMessageSeverity severityToCLISeverity(IrMessageLogger.Severity severity) {
            int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
            if (i == 1) {
                return CompilerMessageSeverity.INFO;
            }
            if (i == 2) {
                return CompilerMessageSeverity.WARNING;
            }
            if (i == 3) {
                return CompilerMessageSeverity.ERROR;
            }
            throw new NoWhenBranchMatchedException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final CompilerMessageLocation locationToCLILocation(IrMessageLogger.Location location) {
            if (location != null) {
                return CompilerMessageLocation.INSTANCE.create(location.getFilePath(), location.getLine(), location.getColumn(), null);
            }
            return null;
        }
    }
}
