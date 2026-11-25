package org.jetbrains.kotlin.build.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: ICReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u001a\u0018\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u001a\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¨\u0006\b"}, d2 = {"debug", "", "Lorg/jetbrains/kotlin/build/report/ICReporter;", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", CompilerOptions.INFO, CommonCompilerArguments.WARN, "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ICReporterKt {
    public static final void warn(ICReporter iCReporter, Function0<String> message) {
        Intrinsics.checkNotNullParameter(iCReporter, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        iCReporter.report(message, ICReporter.ReportSeverity.WARNING);
    }

    public static final void info(ICReporter iCReporter, Function0<String> message) {
        Intrinsics.checkNotNullParameter(iCReporter, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        iCReporter.report(message, ICReporter.ReportSeverity.INFO);
    }

    public static final void debug(ICReporter iCReporter, Function0<String> message) {
        Intrinsics.checkNotNullParameter(iCReporter, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        iCReporter.report(message, ICReporter.ReportSeverity.DEBUG);
    }
}
