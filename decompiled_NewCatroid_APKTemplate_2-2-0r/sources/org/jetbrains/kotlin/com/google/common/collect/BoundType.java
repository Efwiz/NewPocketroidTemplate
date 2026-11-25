package org.jetbrains.kotlin.com.google.common.collect;

/* JADX INFO: loaded from: classes3.dex */
public enum BoundType {
    OPEN(false),
    CLOSED(true);

    final boolean inclusive;

    BoundType(boolean z) {
        this.inclusive = z;
    }

    static BoundType forBoolean(boolean z) {
        return z ? CLOSED : OPEN;
    }

    BoundType flip() {
        return forBoolean(!this.inclusive);
    }
}
