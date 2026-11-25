package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class JX implements OX {
    private JX() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.OX
    public final boolean a(IX ix, IX ix2, NX nx) {
        InterfaceC3511zX interfaceC3511zXJ;
        String strA;
        String strA2;
        C3217vX c3217vXG = ix2.g();
        if (c3217vXG != null && c3217vXG.r() && (interfaceC3511zXJ = ix2.o().j()) != 0) {
            IX ix3 = (IX) interfaceC3511zXJ;
            if (ix3.s()) {
                if ((ix3.u() && nx.d.contains(ix)) || (strA = PX.a(c3217vXG, nx)) == null || (strA2 = PX.a(interfaceC3511zXJ, nx)) == null) {
                    return false;
                }
                String str = strA2 + strA;
                interfaceC3511zXJ.a(str);
                nx.a.put(interfaceC3511zXJ.a(), new C1848dX(str));
                nx.a.put(c3217vXG.a(), C1930eX.d());
                ix2.x();
                return true;
            }
        }
        return false;
    }
}
