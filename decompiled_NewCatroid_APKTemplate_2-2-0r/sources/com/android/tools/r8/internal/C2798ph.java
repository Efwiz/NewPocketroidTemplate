package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ph, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2798ph {
    private final Object a;
    private final int b;

    C2798ph(int i, Object obj) {
        this.a = obj;
        this.b = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C2798ph)) {
            return false;
        }
        C2798ph c2798ph = (C2798ph) obj;
        return this.a == c2798ph.a && this.b == c2798ph.b;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.a) * 65535) + this.b;
    }
}
