package org.jetbrains.kotlin.build;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ExecutionStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/build/ExecutionStrategy;", "", "Ljava/io/Serializable;", "(Ljava/lang/String;I)V", "DAEMON", "IN_PROCESS", "OUT_OF_PROCESS", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ExecutionStrategy implements Serializable {
    DAEMON,
    IN_PROCESS,
    OUT_OF_PROCESS;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ExecutionStrategy> getEntries() {
        return $ENTRIES;
    }
}
