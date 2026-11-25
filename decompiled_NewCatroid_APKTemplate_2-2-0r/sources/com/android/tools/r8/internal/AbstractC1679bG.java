package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1679bG implements ZF {
    AbstractC1679bG() {
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ZF)) {
            return false;
        }
        ZF zf = (ZF) obj;
        return a() == zf.a() && AbstractC3428yK.a(b(), zf.b());
    }

    public final int hashCode() {
        Object objB = b();
        return (objB == null ? 0 : objB.hashCode()) ^ a();
    }

    public final String toString() {
        String strValueOf = String.valueOf(b());
        int iA = a();
        return iA == 1 ? strValueOf : new StringBuilder(strValueOf.length() + 14).append(strValueOf).append(" x ").append(iA).toString();
    }
}
