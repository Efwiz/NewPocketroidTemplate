package org.jetbrains.kotlin.daemon.report;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;

/* JADX INFO: compiled from: DebugMessagesICReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"getSeverity", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DebugMessagesICReporterKt {

    /* JADX INFO: compiled from: DebugMessagesICReporter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ICReporter.ReportSeverity.values().length];
            try {
                iArr[ICReporter.ReportSeverity.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ICReporter.ReportSeverity.INFO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ICReporter.ReportSeverity.DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final ReportSeverity getSeverity(ICReporter.ReportSeverity reportSeverity) {
        Intrinsics.checkNotNullParameter(reportSeverity, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[reportSeverity.ordinal()];
        if (i == 1) {
            return ReportSeverity.WARNING;
        }
        if (i == 2) {
            return ReportSeverity.INFO;
        }
        if (i == 3) {
            return ReportSeverity.DEBUG;
        }
        throw new NoWhenBranchMatchedException();
    }
}
