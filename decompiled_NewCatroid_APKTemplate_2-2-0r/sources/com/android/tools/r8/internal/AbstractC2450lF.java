package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2450lF {
    private static final AbstractC2450lF a = new C2219iF();
    private static final AbstractC2450lF b = new C2293jF();
    private static final AbstractC2450lF c = new C2369kF();

    private AbstractC2450lF() {
    }

    public static AbstractC2450lF a(boolean z) {
        return z ? b : c;
    }

    public boolean b() {
        return this instanceof C2219iF;
    }

    public boolean c() {
        return this instanceof C2369kF;
    }

    public final boolean equals(Object obj) {
        return this == obj;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    public static AbstractC2450lF a() {
        return a;
    }
}
