package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1819d8 implements A1 {
    private final InterfaceC3107u6 a;
    private final com.android.tools.r8.graph.o5 b;
    final /* synthetic */ C1892e8 c;

    C1819d8(C1892e8 c1892e8, InterfaceC3107u6 interfaceC3107u6, com.android.tools.r8.graph.o5 o5Var) {
        this.c = c1892e8;
        this.a = interfaceC3107u6;
        this.b = o5Var;
    }

    @Override // com.android.tools.r8.internal.A1
    public final InterfaceC2998sZ a(Object obj, AbstractC3327x1 abstractC3327x1) {
        return ((B7) obj).a((AbstractC2359k7) abstractC3327x1, this.c.a, this.a);
    }

    @Override // com.android.tools.r8.internal.A1
    public final AbstractC3327x1 b(Object obj, AbstractC3327x1 abstractC3327x1) {
        AbstractC2359k7 c1523Yb = new C1523Yb();
        int iJ0 = 0;
        if (this.b.e().B1()) {
            c1523Yb = this.b.e().C1() ? c1523Yb.a(0, InterfaceC1348Si.g(), this.a) : c1523Yb.a(0, InterfaceC1348Si.b(this.b.t()), this.a);
            iJ0 = 1;
        }
        for (C0767v2 c0767v2 : this.b.D()) {
            c1523Yb = c1523Yb.a(iJ0, InterfaceC1348Si.a(c0767v2), this.a);
            iJ0 += c0767v2.J0();
        }
        return c1523Yb;
    }

    @Override // com.android.tools.r8.internal.A1
    public final AbstractC3327x1 a(Object obj, Object obj2, AbstractC3327x1 abstractC3327x1) {
        return (AbstractC2359k7) abstractC3327x1;
    }

    @Override // com.android.tools.r8.internal.A1
    public final AbstractC3327x1 a(Object obj, C0767v2 c0767v2, Object obj2, Object obj3, AbstractC3327x1 abstractC3327x1) {
        return ((AbstractC2359k7) abstractC3327x1).b(this.a, c0767v2);
    }
}
