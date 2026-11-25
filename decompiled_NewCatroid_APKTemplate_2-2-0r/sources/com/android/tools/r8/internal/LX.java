package com.android.tools.r8.internal;

import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LX implements OX {
    static final /* synthetic */ boolean a = true;

    private LX() {
    }

    @Override // com.android.tools.r8.internal.OX
    public final boolean a(IX ix, IX ix2, NX nx) {
        DX dx;
        C3441yX c3441yXI;
        String strA;
        if (!nx.c.contains(ix) && !nx.d.contains(ix)) {
            ix2.getClass();
            boolean z = ix2 instanceof HX;
            if ((!z && !(ix2 instanceof C3363xX)) || (dx = (DX) nx.g.get(ix)) == null || !dx.s() || (c3441yXI = dx.p().i()) == null || !c3441yXI.s() || !ix2.r() || ix2.o() != c3441yXI || (strA = PX.a(c3441yXI, nx)) == null) {
                return false;
            }
            if (z) {
                HX hxM = ix2.m();
                nx.a.put(hxM.a(), new C2161hX(strA));
                String str = (String) nx.h.put(hxM.a().b(), strA);
                if (!a && str != null) {
                    throw new AssertionError();
                }
            } else {
                if (!a && !(ix2 instanceof C3363xX)) {
                    throw new AssertionError();
                }
                InterfaceC3511zX interfaceC3511zXY = ix2.h().y();
                interfaceC3511zXY.a(strA);
                nx.a.put(interfaceC3511zXY.a(), new C1848dX(strA));
            }
            ((Set) nx.f.get(ix)).remove(ix2);
            ix2.x();
            return true;
        }
        return false;
    }
}
