package com.android.tools.r8.naming;

/* JADX INFO: renamed from: com.android.tools.r8.naming.y, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3671y implements I, Cloneable {
    static final /* synthetic */ boolean d = true;
    private int a;
    private int b;
    final /* synthetic */ C3672z c;

    public C3671y(C3672z c3672z, int i, int i2) {
        this.c = c3672z;
        this.a = i2;
        this.b = i;
    }

    @Override // com.android.tools.r8.naming.I
    public final int a() {
        int i = this.b;
        this.b = i + 1;
        return i;
    }

    @Override // com.android.tools.r8.naming.I
    public final int b() {
        return this.a;
    }

    @Override // com.android.tools.r8.naming.I
    public final int c() {
        int i = this.a;
        this.a = i + 1;
        return i;
    }

    public final Object clone() {
        return new C3671y(this.c, this.b, this.a);
    }
}
