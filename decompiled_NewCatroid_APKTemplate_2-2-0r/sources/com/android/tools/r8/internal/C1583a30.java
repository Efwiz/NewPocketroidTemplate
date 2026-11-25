package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a30, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1583a30 extends AbstractC2425ky {
    private final C2380kO b = C2608nO.p();
    final /* synthetic */ Q20 c;
    final /* synthetic */ EnumC2344jy d;
    final /* synthetic */ EnumC2269iy e;
    final /* synthetic */ InterfaceC1475Wi f;

    C1583a30(Q20 q20, EnumC2344jy enumC2344jy, EnumC2269iy enumC2269iy, InterfaceC1475Wi interfaceC1475Wi) {
        this.c = q20;
        this.d = enumC2344jy;
        this.e = enumC2269iy;
        this.f = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC2425ky
    public final AbstractC2191hy a() {
        return new C1887e30(this.c, new Y20(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC2425ky
    public final AbstractC2191hy b() {
        return new C1887e30(this.c, new Z20(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC2425ky
    public final void c() {
        int iOrdinal = this.d.ordinal();
        if (iOrdinal == 0) {
            this.b.a(EnumC2460lO.b);
        } else if (iOrdinal == 1) {
            this.b.a(EnumC2460lO.c);
        } else {
            if (iOrdinal != 2) {
                throw new C2692oH();
            }
            this.b.a(EnumC2460lO.d);
        }
        EnumC2269iy enumC2269iy = this.e;
        int i = enumC2269iy == null ? -1 : X20.a[enumC2269iy.ordinal()];
        if (i != -1) {
            if (i == 1) {
                this.b.a(EnumC2534mO.b);
            } else if (i == 2) {
                this.b.a(EnumC2534mO.c);
            } else {
                if (i != 3) {
                    throw new C2692oH();
                }
                this.b.a(EnumC2534mO.d);
            }
        }
        InterfaceC1475Wi interfaceC1475Wi = this.f;
        C2380kO c2380kO = this.b;
        AbstractC0936Ds.b(c2380kO, "t");
        interfaceC1475Wi.a(c2380kO);
    }

    public final C2380kO d() {
        return this.b;
    }
}
