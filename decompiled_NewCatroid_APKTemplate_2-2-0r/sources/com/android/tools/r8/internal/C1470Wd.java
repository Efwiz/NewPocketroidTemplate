package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1470Wd {
    static final C1470Wd e = new C1470Wd(-1, false, 0, 0);
    final int a;
    final boolean b;
    private final int c;
    private final int d;

    public C1470Wd(int i, boolean z, int i2, int i3) {
        this.a = i;
        this.b = z;
        this.c = i2;
        this.d = i3;
    }

    public final String toString() {
        int i = this.a;
        boolean z = this.b;
        int i2 = this.d;
        int i3 = this.c;
        return "pc=" + i + ", converted=" + z + ", cost=" + i2 + ", methods=" + i3 + ", saved=" + (i2 - i) + ", overhead=" + C1525Yd.a(i, i3, i2);
    }
}
