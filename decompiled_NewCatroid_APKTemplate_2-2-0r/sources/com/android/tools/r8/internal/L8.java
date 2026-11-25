package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class L8 {
    final L8 a;
    final int b;

    private L8(int i, L8 l8) {
        this.a = l8;
        this.b = i;
    }

    public H8 a(int i) {
        return this.a.a(i);
    }

    public int b() {
        return this.a.b();
    }

    public H8 c() {
        return this.a.c();
    }

    public int d() {
        return this.a.d();
    }

    void a(E8 e8) {
        this.a.a(e8);
    }

    public H8 b(int i) {
        return this.a.b(i);
    }

    E8 a() {
        E8 e8 = new E8(b() + 1, d());
        a(e8);
        return e8;
    }
}
