package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0000H\u0086\u0002j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ProcessResult;", "", "(Ljava/lang/String;I)V", "plus", "other", "FOUND", "SCOPE_EMPTY", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ProcessResult {
    FOUND,
    SCOPE_EMPTY;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ProcessResult> getEntries() {
        return $ENTRIES;
    }

    public final ProcessResult plus(ProcessResult other) {
        Intrinsics.checkNotNullParameter(other, "other");
        ProcessResult processResult = FOUND;
        return (this == processResult || other == processResult) ? processResult : this;
    }
}
