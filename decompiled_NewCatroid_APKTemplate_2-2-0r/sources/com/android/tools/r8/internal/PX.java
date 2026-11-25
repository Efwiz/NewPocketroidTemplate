package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class PX {
    private static final OX[] a = {new JX(), new LX(), new MX(), new KX()};

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(InterfaceC3511zX interfaceC3511zX, NX nx) {
        if (interfaceC3511zX.b()) {
            return interfaceC3511zX.e();
        }
        ArrayList arrayList = (ArrayList) interfaceC3511zX.a().h1();
        if (arrayList.size() != 2) {
            return null;
        }
        return (String) nx.h.get(((S10) arrayList.get(1)).m());
    }

    static boolean a(IX ix, IX ix2, NX nx) {
        if (ix2.t()) {
            return false;
        }
        OX[] oxArr = a;
        for (int i = 0; i < 4; i++) {
            if (oxArr[i].a(ix, ix2, nx)) {
                return true;
            }
        }
        return false;
    }
}
