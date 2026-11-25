package org.jetbrains.kotlin.build.report;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.cli.common.ExitCode;

/* JADX INFO: compiled from: ICReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\b\bf\u0018\u00002\u00020\u0001:\u0001\u001aJ\u001e\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH&J&\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u001e\u0010\u0011\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u0014\u001a\u00020\u0006H&J\u001e\u0010\u0015\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u0016\u001a\u00020\u0006H&J&\u0010\u0017\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/build/report/ICReporter;", "", "report", "", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "", "sourceFiles", "", "Ljava/io/File;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "reportMarkDirty", "affectedFiles", "", "reason", "reportMarkDirtyClass", "classFqName", "reportMarkDirtyMember", "scope", "name", "ReportSeverity", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ICReporter {
    void report(Function0<String> message, ReportSeverity severity);

    void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode);

    void reportMarkDirty(Iterable<? extends File> affectedFiles, String reason);

    void reportMarkDirtyClass(Iterable<? extends File> affectedFiles, String classFqName);

    void reportMarkDirtyMember(Iterable<? extends File> affectedFiles, String scope, String name2);

    /* JADX INFO: compiled from: ICReporter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "", FirebaseAnalytics.Param.LEVEL, "", "(Ljava/lang/String;II)V", "getLevel", "()I", "WARNING", "INFO", "DEBUG", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ReportSeverity {
        WARNING(3),
        INFO(2),
        DEBUG(1);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final int level;

        public static EnumEntries<ReportSeverity> getEntries() {
            return $ENTRIES;
        }

        ReportSeverity(int i) {
            this.level = i;
        }

        public final int getLevel() {
            return this.level;
        }
    }
}
