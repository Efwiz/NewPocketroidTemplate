package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZH extends AbstractC1683bI {
    private final Iterable c;
    private final C0767v2 d;
    private final C0767v2 e;

    public ZH(C0702m c0702m, C0767v2 c0767v2, Iterable iterable, C0767v2 c0767v3, C0767v2 c0767v4) {
        super(c0702m, c0767v2);
        this.c = iterable;
        this.d = c0767v3;
        this.e = c0767v4;
    }

    public final C0753u b() {
        C0722p1 c0722p1A = this.a.a();
        ArrayList arrayList = new ArrayList();
        C2284j7 c2284j7A = C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(this.d)).a();
        AbstractC1683bI.b(arrayList);
        arrayList.add(c2284j7A);
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.T0 t0 = (com.android.tools.r8.graph.T0) it.next();
            M7 m7 = new M7();
            if (it.hasNext()) {
                arrayList.add(new O7(EnumC1812d20.a(this.d), 0));
                C0767v2 c0767v2 = this.d;
                arrayList.add(new O8(c0722p1A.a(c0767v2, c0767v2, t0.K0())));
                arrayList.add(new C3185v7(EnumC1076Il.NE, EnumC1812d20.OBJECT, m7));
            }
            C0767v2 c0767v3 = this.e;
            arrayList.add(new O8(c0722p1A.a(c0767v3, c0767v3, t0.K0())));
            arrayList.add(new C3109u8(EnumC1812d20.a(this.e)));
            if (it.hasNext()) {
                arrayList.add(m7);
                arrayList.add(c2284j7A.clone());
            }
        }
        return a(arrayList);
    }
}
