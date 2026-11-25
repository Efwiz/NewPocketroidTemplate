package com.android.tools.r8.naming;

/* JADX INFO: renamed from: com.android.tools.r8.naming.s0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3661s0 {
    final N0[] a = new N0[256];

    public static C3658q0 b() {
        return new C3658q0();
    }

    public final N0 a(int i) {
        return (i < 0 || i >= 256) ? new N0(i) : this.a[i];
    }

    public static C3651p0 a() {
        return new C3651p0();
    }
}
