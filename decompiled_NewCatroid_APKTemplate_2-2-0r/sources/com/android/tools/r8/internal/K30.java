package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K30 extends AbstractC1338Ry {
    private final C2233iP b = C2303jP.y();
    final /* synthetic */ Q20 c;
    final /* synthetic */ int d;
    final /* synthetic */ String e;
    final /* synthetic */ InterfaceC1475Wi f;

    K30(int i, InterfaceC1475Wi interfaceC1475Wi, Q20 q20, String str) {
        this.c = q20;
        this.d = i;
        this.e = str;
        this.f = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC1338Ry
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new I30(this));
    }

    public final C2233iP b() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1338Ry
    public final AbstractC1254Oy b(int i) {
        return new A30(this.c, i, new J30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1338Ry
    public final void a() {
        if (this.d != C2303jP.k().l()) {
            this.b.a(this.d);
        }
        this.b.b(this.c.a(this.e));
        InterfaceC1475Wi interfaceC1475Wi = this.f;
        C2233iP c2233iP = this.b;
        AbstractC0936Ds.b(c2233iP, "t");
        interfaceC1475Wi.a(c2233iP);
    }
}
