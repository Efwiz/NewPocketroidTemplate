package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1499Xd {
    static final /* synthetic */ boolean d = true;
    final int a;
    int b = 0;
    int c = 0;

    public C1499Xd(int i) {
        if (!d && i < 0) {
            throw new AssertionError();
        }
        this.a = i;
    }

    public final String toString() {
        int i = this.a;
        int i2 = this.b;
        int i3 = this.c;
        return "pc=" + i + ", cost=" + i2 + ", methods=" + i3 + ", saved=" + (i2 - i) + ", overhead=" + C1525Yd.a(i, i3, i2);
    }
}
