package org.jetbrains.kotlin.backend.common.serialization.mangle;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: MangleMode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007j\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "", "signature", "", "fqn", "(Ljava/lang/String;IZZ)V", "getFqn", "()Z", "getSignature", "SIGNATURE", "FQNAME", "FULL", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum MangleMode {
    SIGNATURE(true, false),
    FQNAME(false, true),
    FULL(true, true);

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final boolean fqn;
    private final boolean signature;

    public static EnumEntries<MangleMode> getEntries() {
        return $ENTRIES;
    }

    MangleMode(boolean z, boolean z2) {
        this.signature = z;
        this.fqn = z2;
    }

    public final boolean getFqn() {
        return this.fqn;
    }

    public final boolean getSignature() {
        return this.signature;
    }
}
