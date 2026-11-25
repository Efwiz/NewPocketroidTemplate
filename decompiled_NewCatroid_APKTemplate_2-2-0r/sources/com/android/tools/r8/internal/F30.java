package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F30 extends AbstractC1029Gy {
    private final YO b = ZO.B();
    final /* synthetic */ Q20 c;
    final /* synthetic */ int d;
    final /* synthetic */ String e;
    final /* synthetic */ InterfaceC1475Wi f;

    F30(int i, InterfaceC1475Wi interfaceC1475Wi, Q20 q20, String str) {
        this.c = q20;
        this.d = i;
        this.e = str;
        this.f = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1200My a(int i, String str, int i2, EnumC1364Sy enumC1364Sy) {
        AbstractC0936Ds.c(str, "name");
        AbstractC0936Ds.c(enumC1364Sy, "variance");
        return new H30(i2, i, new C30(this), enumC1364Sy, this.c, str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1254Oy b(int i) {
        return new A30(this.c, i, new D30(this));
    }

    public final YO c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new B30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final AbstractC1520Xy b() {
        return new N30(this.c, new E30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final void a(C3321wx c3321wx) {
        AbstractC0936Ds.c(c3321wx, "annotation");
        this.b.a(R20.a(c3321wx, this.c.a()));
    }

    @Override // com.android.tools.r8.internal.AbstractC1029Gy
    public final void a() {
        if (this.d != ZO.l().o()) {
            this.b.b(this.d);
        }
        this.b.c(this.c.a(this.e));
        InterfaceC1475Wi interfaceC1475Wi = this.f;
        YO yo = this.b;
        AbstractC0936Ds.b(yo, "t");
        interfaceC1475Wi.a(yo);
    }
}
