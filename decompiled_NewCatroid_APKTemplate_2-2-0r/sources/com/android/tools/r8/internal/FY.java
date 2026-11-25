package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class FY {
    static final /* synthetic */ boolean g = true;
    private final AbstractC0727q0 a;
    private DY b;
    private final IdentityHashMap c;
    private final IdentityHashMap d;
    private final HashMap e;
    private final HashMap f;

    private FY(AbstractC0727q0 abstractC0727q0) {
        this.c = new IdentityHashMap();
        this.d = new IdentityHashMap();
        this.e = new HashMap();
        this.f = new HashMap();
        this.a = abstractC0727q0;
    }

    final void a(BiConsumer biConsumer) {
        Iterator it = this.d.values().iterator();
        while (it.hasNext()) {
            biConsumer.accept(this.a, (com.android.tools.r8.graph.T0) it.next());
        }
    }

    final void b(BiConsumer biConsumer) {
        Iterator it = this.c.values().iterator();
        while (it.hasNext()) {
            biConsumer.accept(this.a, (com.android.tools.r8.graph.W0) it.next());
        }
    }

    final void a(C0684j2 c0684j2, EY.c cVar) {
        if (!g && c0684j2.B0() != this.a.d) {
            throw new AssertionError();
        }
        HashMap map = this.e;
        EY.c cVar2 = EY.c.i;
        EY.c cVar3 = (EY.c) map.getOrDefault(c0684j2, cVar2);
        HashMap map2 = this.e;
        if (cVar == cVar2) {
            cVar = cVar3;
        } else if (cVar3 != cVar2) {
            int iA = cVar.a(cVar3);
            cVar = new EY.c(cVar.e || cVar3.e, cVar.f || cVar3.f, cVar.g || cVar3.g, cVar.a || cVar3.a, iA & 255, iA >> 16);
        }
        map2.put(c0684j2, cVar);
    }

    final void a(com.android.tools.r8.graph.Y0 y0, EY.a aVar) {
        if (!g && y0.B0() != this.a.d) {
            throw new AssertionError();
        }
        HashMap map = this.f;
        EY.a aVar2 = EY.a.e;
        EY.a aVar3 = (EY.a) map.getOrDefault(y0, aVar2);
        HashMap map2 = this.f;
        if (aVar == aVar2) {
            aVar = aVar3;
        } else if (aVar3 != aVar2) {
            int iA = aVar.a(aVar3);
            aVar = new EY.a(iA & 255, iA >> 16, aVar.a || aVar3.a);
        }
        map2.put(y0, aVar);
    }
}
