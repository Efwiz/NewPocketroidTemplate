package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.e30, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1887e30 extends AbstractC2191hy {
    private final C2987sO b = C3125uO.u();
    final /* synthetic */ Q20 c;
    final /* synthetic */ InterfaceC1475Wi d;

    C1887e30(Q20 q20, InterfaceC1475Wi interfaceC1475Wi) {
        this.c = q20;
        this.d = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final void a(int i, Integer num) {
        if (i != C3125uO.i().j()) {
            this.b.a(i);
        }
        if (num != null) {
            this.b.b(num.intValue());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final void b() {
        InterfaceC1475Wi interfaceC1475Wi = this.d;
        C2987sO c2987sO = this.b;
        AbstractC0936Ds.b(c2987sO, "t");
        interfaceC1475Wi.a(c2987sO);
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final AbstractC2191hy c() {
        return new C1887e30(this.c, new C1814d30(this));
    }

    public final C2987sO d() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final void a(Object obj) {
        if (AbstractC0936Ds.a(obj, Boolean.TRUE)) {
            this.b.a(EnumC3056tO.b);
        } else if (AbstractC0936Ds.a(obj, Boolean.FALSE)) {
            this.b.a(EnumC3056tO.c);
        } else {
            if (obj == null) {
                this.b.a(EnumC3056tO.d);
                return;
            }
            throw new IllegalArgumentException("Only true, false or null constant values are allowed for effects (was=" + obj + ')');
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final AbstractC1254Oy a(int i) {
        return new A30(this.c, i, new C1738c30(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC2191hy
    public final AbstractC2191hy a() {
        return new C1887e30(this.c, new C1664b30(this));
    }
}
