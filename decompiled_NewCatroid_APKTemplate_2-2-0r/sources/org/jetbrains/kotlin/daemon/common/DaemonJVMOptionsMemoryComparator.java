package org.jetbrains.kotlin.daemon.common;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptionsMemoryComparator;", "Ljava/util/Comparator;", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "()V", "compare", "", "left", "right", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DaemonJVMOptionsMemoryComparator implements Comparator<DaemonJVMOptions> {
    @Override // java.util.Comparator
    public int compare(DaemonJVMOptions left, DaemonJVMOptions right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return DaemonParamsKt.compareDaemonJVMOptionsMemory(left, right);
    }
}
