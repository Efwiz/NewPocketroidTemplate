package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N30 extends AbstractC1520Xy {
    private C2461lP b;
    final /* synthetic */ Q20 c;
    final /* synthetic */ InterfaceC1475Wi d;

    N30(Q20 q20, InterfaceC1475Wi interfaceC1475Wi) {
        this.c = q20;
        this.d = interfaceC1475Wi;
    }

    @Override // com.android.tools.r8.internal.AbstractC1520Xy
    public final void a(EnumC1494Wy enumC1494Wy, EnumC1465Vy enumC1465Vy, Integer num, String str) {
        EnumC2609nP enumC2609nP;
        EnumC2535mP enumC2535mP;
        C2461lP c2461lPU = C2700oP.u();
        Q20 q20 = this.c;
        int iOrdinal = enumC1494Wy.ordinal();
        if (iOrdinal == 0) {
            enumC2609nP = EnumC2609nP.b;
        } else if (iOrdinal == 1) {
            enumC2609nP = EnumC2609nP.c;
        } else {
            if (iOrdinal != 2) {
                throw new C2692oH();
            }
            enumC2609nP = EnumC2609nP.d;
        }
        if (enumC2609nP != C2700oP.g().m()) {
            c2461lPU.a(enumC2609nP);
        }
        int iOrdinal2 = enumC1465Vy.ordinal();
        if (iOrdinal2 == 0) {
            enumC2535mP = EnumC2535mP.b;
        } else if (iOrdinal2 == 1) {
            enumC2535mP = EnumC2535mP.c;
        } else {
            if (iOrdinal2 != 2) {
                throw new C2692oH();
            }
            enumC2535mP = EnumC2535mP.d;
        }
        if (enumC2535mP != C2700oP.g().i()) {
            c2461lPU.a(enumC2535mP);
        }
        if (num != null) {
            c2461lPU.a(num.intValue());
        }
        if (str != null) {
            c2461lPU.b(q20.a(str));
        }
        this.b = c2461lPU;
    }

    public final C2461lP b() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1520Xy
    public final void a(int i, int i2, int i3) {
        if (this.b != null) {
            new C2510m20(i, i2, i3).a(new L30(this), new M30(this));
            return;
        }
        throw new IllegalStateException("KmVersionRequirementVisitor.visit has not been called");
    }

    @Override // com.android.tools.r8.internal.AbstractC1520Xy
    public final void a() {
        if (this.b != null) {
            InterfaceC1475Wi interfaceC1475Wi = this.d;
            C2451lG c2451lGB = this.c.b();
            C2461lP c2461lP = this.b;
            AbstractC0936Ds.a(c2461lP);
            interfaceC1475Wi.a(Integer.valueOf(c2451lGB.a(c2461lP)));
            return;
        }
        throw new IllegalStateException("KmVersionRequirementVisitor.visit has not been called");
    }
}
