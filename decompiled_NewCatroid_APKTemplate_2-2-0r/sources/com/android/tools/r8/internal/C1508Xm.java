package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3932m1;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1508Xm extends AbstractC1453Vm {
    static final /* synthetic */ boolean l = true;
    private final com.android.tools.r8.graph.Y0 d;
    private final int e;
    private final C0684j2 f;
    private final C0684j2 g;
    private final Map h;
    private final Map i;
    private final C0684j2 j;
    private final List k;

    C1508Xm(com.android.tools.r8.graph.Y0 y0, int i, C0684j2 c0684j2, C0684j2 c0684j3, Map map, Map map2, C0684j2 c0684j4, List list) {
        this.d = y0;
        this.e = i;
        this.f = c0684j2;
        this.g = c0684j3;
        this.h = map;
        this.i = map2;
        this.j = c0684j4;
        this.k = list;
    }

    @Override // com.android.tools.r8.internal.AbstractC1453Vm
    public final C0753u a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C1013Gk c1013Gk) {
        int iH1 = o5Var.e().h1();
        int[] iArr = new int[iH1];
        int iJ0 = 0;
        for (int i = 0; i < iH1; i++) {
            iArr[i] = iJ0;
            iJ0 += o5Var.a(i).J0();
        }
        C3932m1 c3932m1 = new C3932m1();
        C1507Xl c1507XlG = AbstractC1639am.g();
        C2051g8 c2051g8 = new C2051g8(new M7(), ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(0).a(this.f).a(AbstractC1915eM.c.x().a(0).a(this.g).a())).a());
        c1507XlG.a(c2051g8);
        c1507XlG.a(c2051g8.T());
        if (this.d != null) {
            int i2 = (iJ0 - 1) - this.e;
            c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
            c1507XlG.a(new O7(EnumC1812d20.INT, i2));
            com.android.tools.r8.graph.Y0 y0B = c1013Gk.b(c1013Gk.k(), this.d);
            c1507XlG.a(new C3482z7(y0B, y0B));
            c3932m1.e(2);
        }
        a(c0702m, o5Var, c1507XlG, this.h, iArr, c3932m1, c1013Gk);
        c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
        C0684j2 c0684j2 = this.j;
        c1013Gk.getClass();
        KE keA = c1013Gk.a(c0684j2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null);
        Iterator it = this.k.iterator();
        int iA = 1;
        int i3 = 0;
        while (it.hasNext()) {
            iA += a(c1507XlG, (InterfaceC0989Fn) it.next(), iArr, ((C0684j2) keA.b()).k(i3));
            i3++;
        }
        Iterator it2 = keA.e().b().iterator();
        while (it2.hasNext()) {
            iA += a(c1507XlG, ((AbstractC3078th) it2.next()).a(c0702m), iArr, ((C0684j2) keA.b()).k(i3));
            i3++;
        }
        if (!l && i3 != ((C0684j2) keA.b()).G0().size()) {
            throw new AssertionError();
        }
        c1507XlG.a(new I7(183, (C0684j2) keA.b(), false));
        if (iA > c3932m1.a()) {
            c3932m1.e(iA);
        }
        a(c0702m, o5Var, c1507XlG, this.i, iArr, c3932m1, c1013Gk);
        c1507XlG.a(new C3186v8());
        return new C1479Wm(this.f.B0(), c3932m1.a(), iJ0, c1507XlG.a(), c1013Gk);
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return "IncompleteMergedInstanceInitializerCode";
    }

    private static void a(final C0702m c0702m, final com.android.tools.r8.graph.o5 o5Var, final C1507Xl c1507Xl, Map map, final int[] iArr, final C3932m1 c3932m1, final C1013Gk c1013Gk) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Xm$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1508Xm.a(c1507Xl, iArr, c1013Gk, o5Var, c0702m, c3932m1, (com.android.tools.r8.graph.Y0) obj, (InterfaceC0989Fn) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C1507Xl c1507Xl, int[] iArr, C1013Gk c1013Gk, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, C3932m1 c3932m1, com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        int iB;
        c1507Xl.a(new O7(EnumC1812d20.OBJECT, 0));
        int iA = a(c1507Xl, interfaceC0989Fn, iArr, y0.getType());
        com.android.tools.r8.graph.Y0 y0B = c1013Gk.b(c1013Gk.k(), y0);
        if (interfaceC0989Fn.h() && (iB = interfaceC0989Fn.f().b()) > 0) {
            C0767v2 c0767v2A = o5Var.a(iB);
            if (c0767v2A.O0() && !((C0680j) c0702m.f()).c(c0767v2A, y0B.getType())) {
                c1507Xl.a(new C3261w8(y0B.getType()));
            }
        }
        c1507Xl.a(new C3482z7(y0B, y0B));
        int i = iA + 1;
        if (i > c3932m1.a()) {
            c3932m1.e(i);
        }
    }

    private static int a(C1507Xl c1507Xl, InterfaceC0989Fn interfaceC0989Fn, int[] iArr, C0767v2 c0767v2) {
        if (interfaceC0989Fn.h()) {
            int iB = interfaceC0989Fn.f().b();
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            c1507Xl.a(new O7(EnumC1812d20.a((char) c0767v2.e.e[0]), iArr[iB]));
            return c0767v2.J0();
        }
        boolean z = l;
        if (!z && !interfaceC0989Fn.g()) {
            throw new AssertionError();
        }
        if (!z && !interfaceC0989Fn.j().A()) {
            throw new AssertionError();
        }
        AbstractC2467lV abstractC2467lVN = interfaceC0989Fn.j().n();
        abstractC2467lVN.getClass();
        if (abstractC2467lVN instanceof C2388kV) {
            c1507Xl.a(new X6(abstractC2467lVN.m().F(), false));
            return 1;
        }
        if (abstractC2467lVN instanceof C2616nV) {
            C2616nV c2616nVO = abstractC2467lVN.o();
            c1507Xl.a(new C2134h7(c2616nVO.F(), c2616nVO.G()));
            return 1;
        }
        if (abstractC2467lVN instanceof C2853qV) {
            if (c0767v2.X0()) {
                if (!z && !abstractC2467lVN.x()) {
                    throw new AssertionError();
                }
                c1507Xl.a(new C1668b7());
                return 1;
            }
            long jH = abstractC2467lVN.e().H();
            EnumC1812d20 enumC1812d21 = EnumC1812d20.OBJECT;
            c1507Xl.a(new C1818d7(jH, EnumC1812d20.a((char) c0767v2.e.e[0])));
            return c0767v2.J0();
        }
        if (!z && !abstractC2467lVN.C()) {
            throw new AssertionError();
        }
        c1507Xl.a(new C1891e7(abstractC2467lVN.q().F()));
        return 1;
    }
}
