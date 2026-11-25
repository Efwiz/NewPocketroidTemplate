package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class MX implements OX {
    static final /* synthetic */ boolean a = true;

    private MX() {
    }

    @Override // com.android.tools.r8.internal.OX
    public final boolean a(IX ix, IX ix2, NX nx) {
        boolean z;
        DX dx;
        C3441yX c3441yXI;
        C3441yX c3441yX;
        C2087gX c2087gXB;
        C2311jX c2311jXB;
        C3217vX c3217vXG;
        if (nx.c.contains(ix) || nx.d.contains(ix) || !ix2.r() || !((((z = ix2 instanceof HX)) || (ix2 instanceof C3363xX)) && (dx = (DX) nx.g.get(ix)) != null && dx.s() && (c3441yXI = dx.p().i()) != null && c3441yXI.s())) {
            return false;
        }
        if (c3441yXI.a(nx.b) && "".equals(c3441yXI.e()) && c3441yXI.s() && ((c3217vXG = c3441yXI.p().g()) == null || !c3217vXG.r() || !c3217vXG.s())) {
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            c3441yX = c3217vXG;
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            c3441yX = c3441yXI;
            return false;
        }
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        c3441yX = c3217vXG;
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        c3441yX = c3441yXI;
        if (!c3441yX.b() && !c3441yX.c()) {
            return false;
        }
        ArrayList arrayListA = AbstractC2763pC.a(c3441yX);
        if (ix2.o() != c3441yX) {
            C3217vX c3217vXG2 = c3441yX.p().g();
            C3217vX c3217vXG3 = ix2.o().g();
            if (c3217vXG2 == null || c3217vXG2 != c3217vXG3 || (!c3217vXG2.b() && !c3217vXG2.c())) {
                return false;
            }
            arrayListA.add(c3217vXG2);
        }
        if (AbstractC2734ot.a((Iterable) arrayListA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.MX$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((InterfaceC3511zX) obj).b();
            }
        })) {
            return false;
        }
        InterfaceC3511zX interfaceC3511zX = (InterfaceC3511zX) arrayListA.get(0);
        if (z) {
            AbstractC2412ko abstractC2412koA = ix2.m().a();
            if (arrayListA.size() == 1) {
                nx.a.put(abstractC2412koA, new C2241iX(interfaceC3511zX.d()));
            } else {
                InterfaceC3511zX interfaceC3511zX2 = (InterfaceC3511zX) arrayListA.get(1);
                if (interfaceC3511zX.b()) {
                    c2311jXB = C2311jX.a(interfaceC3511zX2.d(), interfaceC3511zX.e());
                } else {
                    c2311jXB = interfaceC3511zX2.b() ? C2311jX.b(interfaceC3511zX.d(), interfaceC3511zX2.e()) : C2311jX.a(interfaceC3511zX.d(), interfaceC3511zX2.d());
                }
                nx.a.put(abstractC2412koA, c2311jXB);
            }
        } else {
            if (!a && !(ix2 instanceof C3363xX)) {
                throw new AssertionError();
            }
            InterfaceC3511zX interfaceC3511zXY = ix2.h().y();
            if (arrayListA.size() == 1) {
                interfaceC3511zXY.a(interfaceC3511zX.d());
                nx.a.put(interfaceC3511zXY.a(), new C2007fX(interfaceC3511zX.d()));
            } else {
                InterfaceC3511zX interfaceC3511zX3 = (InterfaceC3511zX) arrayListA.get(1);
                S10 s10A = nx.a();
                interfaceC3511zXY.a(s10A);
                if (interfaceC3511zX.b()) {
                    c2087gXB = C2087gX.a(interfaceC3511zX3.d(), s10A, interfaceC3511zX.e());
                } else {
                    c2087gXB = interfaceC3511zX3.b() ? C2087gX.b(interfaceC3511zX.d(), s10A, interfaceC3511zX3.e()) : C2087gX.a(interfaceC3511zX.d(), interfaceC3511zX3.d(), s10A);
                }
                nx.a.put(interfaceC3511zXY.a(), c2087gXB);
            }
        }
        ((Set) nx.f.get(ix)).remove(ix2);
        ix2.x();
        return true;
    }
}
