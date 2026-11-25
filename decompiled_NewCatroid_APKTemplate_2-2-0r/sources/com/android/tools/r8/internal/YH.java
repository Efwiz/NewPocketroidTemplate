package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YH extends AbstractC1683bI {
    private final C0767v2 c;
    private final C0767v2 d;
    private final C0684j2 e;

    public YH(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3, C0767v2 c0767v4, C0684j2 c0684j2) {
        super(c0702m, c0767v2);
        this.c = c0767v3;
        this.d = c0767v4;
        this.e = c0684j2;
    }

    public final C0753u b() {
        this.a.a();
        ArrayList arrayList = new ArrayList();
        AbstractC1683bI.b(arrayList);
        arrayList.add(C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(this.c)).a());
        C2284j7 c2284j7A = C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(this.c)).a((InterfaceC1348Si) InterfaceC1348Si.h()).a((InterfaceC1348Si) InterfaceC1348Si.a(this.d)).a((InterfaceC1348Si) InterfaceC1348Si.h()).a();
        arrayList.add(new O7(EnumC1812d20.a(this.c), 0));
        arrayList.add(new A6());
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        arrayList.add(new R8(enumC1812d20, 1));
        arrayList.add(new O7(enumC1812d20, 1));
        arrayList.add(new W7(this.d));
        arrayList.add(new R8(EnumC1812d20.a(this.d), 2));
        arrayList.add(new C1818d7(0L, enumC1812d20));
        arrayList.add(new R8(enumC1812d20, 3));
        M7 m7 = new M7();
        M7 m8 = new M7();
        arrayList.add(m8);
        arrayList.add(c2284j7A);
        arrayList.add(new O7(enumC1812d20, 3));
        arrayList.add(new O7(enumC1812d20, 1));
        arrayList.add(new C3185v7(EnumC1076Il.GE, enumC1812d20, m7));
        arrayList.add(new O7(EnumC1812d20.a(this.d), 2));
        arrayList.add(new O7(enumC1812d20, 3));
        arrayList.add(new O7(EnumC1812d20.a(this.c), 0));
        arrayList.add(new O7(enumC1812d20, 3));
        EnumC2523mE enumC2523mE = EnumC2523mE.OBJECT;
        arrayList.add(new B6(enumC2523mE));
        arrayList.add(new I7(184, this.e, false));
        arrayList.add(new D6(enumC2523mE));
        arrayList.add(new O7(enumC1812d20, 3));
        arrayList.add(new C1818d7(1L, enumC1812d20));
        arrayList.add(new C3481z6(C3481z6.a.Add, EnumC2527mI.INT));
        arrayList.add(new R8(enumC1812d20, 3));
        arrayList.add(new C2899r7(m8));
        arrayList.add(m7);
        arrayList.add(c2284j7A.clone());
        arrayList.add(new O7(EnumC1812d20.a(this.d), 2));
        arrayList.add(new C3109u8(EnumC1812d20.a(this.d)));
        return a(arrayList);
    }
}
