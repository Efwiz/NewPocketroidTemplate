package org.jetbrains.kotlin.daemon.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Serializable;
import java.rmi.RemoteException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilerServicesFacadeBase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b"}, d2 = {"report", "", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "category", "Lorg/jetbrains/kotlin/daemon/common/ReportCategory;", "severity", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", CrashHianalyticsData.MESSAGE, "", "attachment", "Ljava/io/Serializable;", "daemon-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerServicesFacadeBaseKt {
    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    public static /* synthetic */ void report$default(CompilerServicesFacadeBase compilerServicesFacadeBase, ReportCategory reportCategory, ReportSeverity reportSeverity, String str, Serializable serializable, int i, Object obj) throws RemoteException {
        if ((i & 4) != 0) {
            str = null;
        }
        if ((i & 8) != 0) {
            serializable = null;
        }
        report(compilerServicesFacadeBase, reportCategory, reportSeverity, str, serializable);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    public static final void report(CompilerServicesFacadeBase compilerServicesFacadeBase, ReportCategory category, ReportSeverity severity, String str, Serializable serializable) throws RemoteException {
        Intrinsics.checkNotNullParameter(compilerServicesFacadeBase, "<this>");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(severity, "severity");
        compilerServicesFacadeBase.report(category.getCode(), severity.getCode(), str, serializable);
    }
}
