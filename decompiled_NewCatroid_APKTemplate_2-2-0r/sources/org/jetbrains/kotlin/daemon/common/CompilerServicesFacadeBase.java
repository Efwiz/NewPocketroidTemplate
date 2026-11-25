package org.jetbrains.kotlin.daemon.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import kotlin.Metadata;

/* JADX INFO: compiled from: CompilerServicesFacadeBase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "Ljava/rmi/Remote;", "report", "", "category", "", "severity", CrashHianalyticsData.MESSAGE, "", "attachment", "Ljava/io/Serializable;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompilerServicesFacadeBase extends Remote {
    void report(int category, int severity, String message, Serializable attachment) throws RemoteException;
}
