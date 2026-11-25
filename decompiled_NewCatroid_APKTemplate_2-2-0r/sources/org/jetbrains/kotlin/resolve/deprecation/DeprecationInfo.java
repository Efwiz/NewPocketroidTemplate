package org.jetbrains.kotlin.resolve.deprecation;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DeprecationInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0000H\u0096\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "", "()V", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "propagatesToOverrides", "", "getPropagatesToOverrides", "()Z", "compareTo", "", "other", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeprecationInfo implements Comparable<DeprecationInfo> {
    public abstract DeprecationLevelValue getDeprecationLevel();

    public abstract String getMessage();

    public abstract boolean getPropagatesToOverrides();

    @Override // java.lang.Comparable
    public int compareTo(DeprecationInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int iCompareTo = getDeprecationLevel().compareTo(other.getDeprecationLevel());
        if (iCompareTo == 0 && !getPropagatesToOverrides() && other.getPropagatesToOverrides()) {
            return 1;
        }
        return iCompareTo;
    }
}
