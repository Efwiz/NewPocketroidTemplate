package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: InlineStatus.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/InlineStatus;", "", "returnAllowed", "", "(Ljava/lang/String;IZ)V", "getReturnAllowed", "()Z", "Inline", "NoInline", "CrossInline", "Unknown", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum InlineStatus {
    Inline(true),
    NoInline(false),
    CrossInline(false),
    Unknown(false);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean returnAllowed;

    public static EnumEntries<InlineStatus> getEntries() {
        return $ENTRIES;
    }

    InlineStatus(boolean z) {
        this.returnAllowed = z;
    }

    public final boolean getReturnAllowed() {
        return this.returnAllowed;
    }
}
