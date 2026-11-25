package com.android.tools.r8.naming;

/* JADX INFO: renamed from: com.android.tools.r8.naming.q0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3658q0 extends AbstractC3661s0 {
    private final C3659r0[] b;

    private C3658q0() {
        this.b = new C3659r0[2];
        for (int i = 0; i <= 1; i++) {
            this.b[i] = new C3659r0(i);
        }
    }

    public final N0 a(int i, int i2) {
        if (i >= 256) {
            return new N0(i, i2);
        }
        int i3 = i2 - i;
        if (i3 < 0) {
            return new N0(i, i2);
        }
        return i3 > 1 ? new N0(i, i2) : this.b[i3].a(i);
    }
}
