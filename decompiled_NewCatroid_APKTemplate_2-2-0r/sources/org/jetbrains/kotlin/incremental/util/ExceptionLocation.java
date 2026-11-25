package org.jetbrains.kotlin.incremental.util;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: messageCollectorUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/util/ExceptionLocation;", "", "readableName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getReadableName", "()Ljava/lang/String;", "INCREMENTAL_COMPILATION", "DAEMON", "OUT_OF_PROCESS_COMPILATION", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ExceptionLocation {
    INCREMENTAL_COMPILATION("Incremental compilation"),
    DAEMON("Daemon compilation"),
    OUT_OF_PROCESS_COMPILATION("Out of process compilation");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String readableName;

    public static EnumEntries<ExceptionLocation> getEntries() {
        return $ENTRIES;
    }

    ExceptionLocation(String str) {
        this.readableName = str;
    }

    public final String getReadableName() {
        return this.readableName;
    }
}
