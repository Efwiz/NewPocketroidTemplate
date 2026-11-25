package org.jetbrains.kotlin.cfg;

/* JADX INFO: loaded from: classes2.dex */
public enum TailRecursionKind {
    TAIL_CALL(true),
    IN_TRY(false),
    NON_TAIL(false);

    private final boolean doGenerateTailRecursion;

    TailRecursionKind(boolean z) {
        this.doGenerateTailRecursion = z;
    }

    public boolean isDoGenerateTailRecursion() {
        return this.doGenerateTailRecursion;
    }
}
