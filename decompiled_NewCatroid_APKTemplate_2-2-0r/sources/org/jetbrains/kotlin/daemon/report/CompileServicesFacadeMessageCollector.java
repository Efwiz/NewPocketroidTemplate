package org.jetbrains.kotlin.daemon.report;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.rmi.RemoteException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSourceLocation;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer;
import org.jetbrains.kotlin.daemon.KotlinCompileDaemon;
import org.jetbrains.kotlin.daemon.common.CompilationOptions;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBase;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBaseKt;
import org.jetbrains.kotlin.daemon.common.ReportCategory;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: CompileServicesFacadeMessageCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\"\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/CompileServicesFacadeMessageCollector;", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "compilationOptions", "Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;)V", "hasErrors", "", "mySeverity", "", LineReader.CLEAR, "", "report", "severity", "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSeverity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/cli/common/messages/CompilerMessageSourceLocation;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompileServicesFacadeMessageCollector implements MessageCollector {
    private boolean hasErrors;
    private final int mySeverity;
    private final CompilerServicesFacadeBase servicesFacade;

    /* JADX INFO: compiled from: CompileServicesFacadeMessageCollector.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CompilerMessageSeverity.values().length];
            try {
                iArr[CompilerMessageSeverity.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CompilerMessageSeverity.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CompilerMessageSeverity.STRONG_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CompilerMessageSeverity.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[CompilerMessageSeverity.OUTPUT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[CompilerMessageSeverity.EXCEPTION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CompileServicesFacadeMessageCollector(CompilerServicesFacadeBase servicesFacade, CompilationOptions compilationOptions) {
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        Intrinsics.checkNotNullParameter(compilationOptions, "compilationOptions");
        this.servicesFacade = servicesFacade;
        this.mySeverity = compilationOptions.getReportSeverity();
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void clear() {
        this.hasErrors = false;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    public void report(CompilerMessageSeverity severity, String message, CompilerMessageSourceLocation location) throws RemoteException {
        ReportSeverity reportSeverity;
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        KotlinCompileDaemon.INSTANCE.getLog().info("Message: " + MessageRenderer.WITHOUT_PATHS.render(severity, message, location));
        int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
        boolean z = true;
        if (i == 5) {
            CompilerServicesFacadeBaseKt.report$default(this.servicesFacade, ReportCategory.OUTPUT_MESSAGE, ReportSeverity.ERROR, message, null, 8, null);
        } else if (i == 6) {
            CompilerServicesFacadeBaseKt.report$default(this.servicesFacade, ReportCategory.EXCEPTION, ReportSeverity.ERROR, message, null, 8, null);
        } else {
            int i2 = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
            if (i2 == 1) {
                reportSeverity = ReportSeverity.ERROR;
            } else if (i2 == 2 || i2 == 3) {
                reportSeverity = ReportSeverity.WARNING;
            } else if (i2 == 4) {
                reportSeverity = ReportSeverity.INFO;
            } else {
                reportSeverity = ReportSeverity.DEBUG;
            }
            if (reportSeverity.getCode() <= this.mySeverity) {
                CompilerServicesFacadeBaseKt.report(this.servicesFacade, ReportCategory.COMPILER_MESSAGE, reportSeverity, message, location);
            }
        }
        if (!this.hasErrors && !severity.isError()) {
            z = false;
        }
        this.hasErrors = z;
    }

    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollector
    /* JADX INFO: renamed from: hasErrors, reason: from getter */
    public boolean getHasErrors() {
        return this.hasErrors;
    }
}
