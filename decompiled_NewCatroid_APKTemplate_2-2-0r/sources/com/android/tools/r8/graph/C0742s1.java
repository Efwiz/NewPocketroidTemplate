package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.s1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0742s1 extends AbstractC0728q1 {
    public final Y0 a;
    public final C0684j2 b;
    public final C0684j2 c;
    public final C0684j2 d;

    private C0742s1(C0722p1 c0722p1) {
        this.a = c0722p1.a(c0722p1.N1, c0722p1.m2, "TYPE");
        this.b = c0722p1.a(c0722p1.N1, c0722p1.b(c0722p1.w1, new C0767v2[0]), "byteValue");
        this.c = c0722p1.a(c0722p1.N1, c0722p1.b(c0722p1.X1, new C0767v2[0]), "toString");
        C0767v2 c0767v2 = c0722p1.N1;
        this.d = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.w1), "valueOf");
    }

    @Override // com.android.tools.r8.graph.AbstractC0728q1
    public final Y0 a() {
        return this.a;
    }
}
