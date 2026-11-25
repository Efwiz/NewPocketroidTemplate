package com.android.tools.r8.dex;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H {
    final int a;
    final int b;
    final int c;

    public H(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public final int a() {
        return this.b;
    }

    public final int a(C0586x c0586x) {
        if (this.c == 0) {
            return 0;
        }
        c0586x.a((short) this.a);
        c0586x.a((short) 0);
        c0586x.f(this.c);
        c0586x.f(this.b);
        return 1;
    }
}
