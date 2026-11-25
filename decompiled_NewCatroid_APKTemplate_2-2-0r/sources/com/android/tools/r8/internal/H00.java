package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H00 {
    private final List a;

    public H00(C2075gP c2075gP) {
        List listI = c2075gP.i();
        if (c2075gP.j()) {
            int iH = c2075gP.h();
            List listI2 = c2075gP.i();
            AbstractC0936Ds.b(listI2, "typeTable.typeList");
            ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listI2));
            int i = 0;
            for (Object obj : listI2) {
                int i2 = i + 1;
                if (i < 0) {
                    AbstractC0823Ab.a();
                    throw null;
                }
                WO woC = (WO) obj;
                if (i >= iH) {
                    woC = woC.O().a(true).c();
                }
                arrayList.add(woC);
                i = i2;
            }
            listI = arrayList;
        }
        AbstractC0936Ds.b(listI, "run {\n        val origin… else originalTypes\n    }");
        this.a = listI;
    }

    public final WO a(int i) {
        return (WO) this.a.get(i);
    }
}
