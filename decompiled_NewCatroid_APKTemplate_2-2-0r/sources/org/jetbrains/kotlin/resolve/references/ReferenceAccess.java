package org.jetbrains.kotlin.resolve.references;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: ReferenceAccess.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/references/ReferenceAccess;", "", "isRead", "", "isWrite", "(Ljava/lang/String;IZZ)V", "()Z", "READ", "WRITE", "READ_WRITE", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum ReferenceAccess {
    READ(true, false),
    WRITE(false, true),
    READ_WRITE(true, true);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean isRead;
    private final boolean isWrite;

    public static EnumEntries<ReferenceAccess> getEntries() {
        return $ENTRIES;
    }

    ReferenceAccess(boolean z, boolean z2) {
        this.isRead = z;
        this.isWrite = z2;
    }

    /* JADX INFO: renamed from: isRead, reason: from getter */
    public final boolean getIsRead() {
        return this.isRead;
    }

    /* JADX INFO: renamed from: isWrite, reason: from getter */
    public final boolean getIsWrite() {
        return this.isWrite;
    }
}
