package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

/* JADX INFO: loaded from: classes4.dex */
public class PointerMath {
    private static final boolean bits32;

    static {
        bits32 = Library.getBitModel() == 32;
    }

    public static final long add(long j, long j2) {
        return bits32 ? (int) (j + j2) : j + j2;
    }
}
