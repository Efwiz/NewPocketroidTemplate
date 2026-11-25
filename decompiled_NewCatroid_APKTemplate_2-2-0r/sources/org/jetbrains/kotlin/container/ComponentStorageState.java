package org.jetbrains.kotlin.container;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: Storage.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/container/ComponentStorageState;", "", "(Ljava/lang/String;I)V", "Initial", "Initialized", "Disposing", "Disposed", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ComponentStorageState {
    Initial,
    Initialized,
    Disposing,
    Disposed;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<ComponentStorageState> getEntries() {
        return $ENTRIES;
    }
}
