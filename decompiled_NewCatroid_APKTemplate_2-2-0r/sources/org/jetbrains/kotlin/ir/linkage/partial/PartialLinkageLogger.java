package org.jetbrains.kotlin.ir.linkage.partial;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;

/* JADX INFO: compiled from: PartialLinkageUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;", "", "irLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "logLevel", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogLevel;", "(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogLevel;)V", "irLoggerSeverity", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Severity;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", "Location", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageLogger {
    private final IrMessageLogger irLogger;
    private final IrMessageLogger.Severity irLoggerSeverity;

    /* JADX INFO: compiled from: PartialLinkageUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PartialLinkageLogLevel.values().length];
            try {
                iArr[PartialLinkageLogLevel.INFO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PartialLinkageLogLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PartialLinkageLogLevel.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageUtils.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\n\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011J\b\u0010\u0012\u001a\u00020\u0003H\u0016R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger$Location;", "", "moduleName", "", "filePath", "lineNumber", "", "columnNumber", "(Ljava/lang/String;Ljava/lang/String;II)V", "getColumnNumber", "()I", "getFilePath", "()Ljava/lang/String;", "getLineNumber", "getModuleName", "render", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Location {
        private final int columnNumber;
        private final String filePath;
        private final int lineNumber;
        private final String moduleName;

        public Location(String moduleName, String filePath, int i, int i2) {
            Intrinsics.checkNotNullParameter(moduleName, "moduleName");
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            this.moduleName = moduleName;
            this.filePath = filePath;
            this.lineNumber = i;
            this.columnNumber = i2;
        }

        public final int getColumnNumber() {
            return this.columnNumber;
        }

        public final String getFilePath() {
            return this.filePath;
        }

        public final int getLineNumber() {
            return this.lineNumber;
        }

        public final String getModuleName() {
            return this.moduleName;
        }

        public final StringBuilder render() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.moduleName);
            if (this.filePath.length() > 0) {
                sb.append(" @ ").append(this.filePath);
                if (this.lineNumber != -1 && this.columnNumber != -1) {
                    sb.append(':').append(this.lineNumber).append(':').append(this.columnNumber);
                }
            }
            return sb;
        }

        public String toString() {
            String string = render().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    }

    public PartialLinkageLogger(IrMessageLogger irLogger, PartialLinkageLogLevel logLevel) {
        IrMessageLogger.Severity severity;
        Intrinsics.checkNotNullParameter(irLogger, "irLogger");
        Intrinsics.checkNotNullParameter(logLevel, "logLevel");
        this.irLogger = irLogger;
        int i = WhenMappings.$EnumSwitchMapping$0[logLevel.ordinal()];
        if (i == 1) {
            severity = IrMessageLogger.Severity.INFO;
        } else if (i == 2) {
            severity = IrMessageLogger.Severity.WARNING;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            severity = IrMessageLogger.Severity.ERROR;
        }
        this.irLoggerSeverity = severity;
    }

    public final void log(String message, Location location) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(location, "location");
        IrMessageLogger irMessageLogger = this.irLogger;
        IrMessageLogger.Severity severity = this.irLoggerSeverity;
        String string = location.render().append(": ").append(message).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        irMessageLogger.report(severity, string, null);
    }
}
