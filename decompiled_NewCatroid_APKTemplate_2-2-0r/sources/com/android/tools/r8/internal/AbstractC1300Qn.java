package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1300Qn {
    public static C1353Sn a(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, C2688oE c2688oE, AbstractC1243On abstractC1243On) {
        if (!abstractC1243On.c()) {
            return a(c0702m, interfaceC2802pl, c2688oE, abstractC1243On.a().a);
        }
        C1327Rn c1327Rn = new C1327Rn(c0702m.a(), abstractC1243On.b());
        C0684j2 c0684j2B = abstractC1243On.b().b(c2688oE.e(), c0702m.a());
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i < c0684j2B.a(false); i++) {
            arrayList.add(c0702m.O().a(i));
        }
        c1327Rn.a(c0684j2B, arrayList);
        return c1327Rn.a();
    }

    public static C1353Sn a(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, C2688oE c2688oE, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.m5 m5VarD;
        InterfaceC0989Fn interfaceC0989FnA;
        C1327Rn c1327Rn = new C1327Rn(c0702m.a(), o5Var.getReference());
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(interfaceC2802pl.a(o5Var).j());
        while (o3H.c()) {
            for (AbstractC2412ko abstractC2412ko : ((C3332x4) o3H.f()).p()) {
                int iG2 = abstractC2412ko.G2();
                if (iG2 != 5 && iG2 != 9 && iG2 != 12 && iG2 != 20) {
                    if (iG2 == 24) {
                        if (!o3H.c(abstractC2412ko.P().K2())) {
                            return null;
                        }
                    } else if (iG2 == 30) {
                        C1793co c1793coV = abstractC2412ko.V();
                        if (!c1793coV.e().m().Q()) {
                            return null;
                        }
                        com.android.tools.r8.graph.Y0 y0C = c0702m.E().c((AbstractC1476Wj) null, c1793coV.getField());
                        if (y0C.B0() != o5Var.t() || (m5VarD = o5Var.a().d(y0C)) == null || (interfaceC0989FnA = a(c0702m, c1793coV.value())) == null) {
                            return null;
                        }
                        c1327Rn.a(c2688oE.a(m5VarD).getReference(), interfaceC0989FnA);
                    } else {
                        if (iG2 == 33) {
                            C1166Ls c1166LsZ = abstractC2412ko.Z();
                            if (!c1166LsZ.U2().m().Q()) {
                                return null;
                            }
                            C0684j2 c0684j2S2 = c1166LsZ.S2();
                            AbstractC1476Wj abstractC1476WjE = c0702m.E();
                            abstractC1476WjE.getClass();
                            C0684j2 c0684j2 = (C0684j2) abstractC1476WjE.a(c0684j2S2, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b();
                            if (c0684j2.b(c0702m.a()) && c0684j2.B0() == c2688oE.e()) {
                                ArrayList arrayList = new ArrayList(((ArrayList) c1166LsZ.J2()).size() - 1);
                                Iterator it = AbstractC2734ot.d(c1166LsZ.J2()).iterator();
                                while (it.hasNext()) {
                                    InterfaceC0989Fn interfaceC0989FnA2 = a(c0702m, (S10) it.next());
                                    if (interfaceC0989FnA2 == null) {
                                        return null;
                                    }
                                    arrayList.add(interfaceC0989FnA2);
                                }
                                if (!c1327Rn.a(c0684j2S2, arrayList)) {
                                }
                            }
                            return null;
                        }
                        if (iG2 != 56 && iG2 != 15 && iG2 != 16) {
                            return null;
                        }
                    }
                }
            }
        }
        if (c1327Rn.b()) {
            return c1327Rn.a();
        }
        return null;
    }

    private static InterfaceC0989Fn a(C0702m c0702m, S10 s10) {
        S10 s10M = s10.m();
        if (s10M.i()) {
            return null;
        }
        AbstractC2412ko abstractC2412koQ = s10M.q();
        if (abstractC2412koQ.m1()) {
            return c0702m.O().a(s10M.q().q().J2());
        }
        if (abstractC2412koQ.B1()) {
            return c0702m.b().a(abstractC2412koQ.E().P2());
        }
        if (abstractC2412koQ instanceof C1033Hc) {
            return c0702m.b().a(abstractC2412koQ.F().J2());
        }
        return null;
    }
}
