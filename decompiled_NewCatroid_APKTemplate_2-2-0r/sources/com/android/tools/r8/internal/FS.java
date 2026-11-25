package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FS extends EZ {
    private final InterfaceC1833dK a;
    private final Map b;

    FS(InterfaceC1833dK interfaceC1833dK, LinkedHashMap linkedHashMap) {
        this.a = interfaceC1833dK;
        this.b = linkedHashMap;
    }

    @Override // com.android.tools.r8.internal.EZ
    public final Object a(C1486Wt c1486Wt) throws IOException {
        if (c1486Wt.t() == 9) {
            c1486Wt.q();
            return null;
        }
        Object objA = this.a.a();
        try {
            c1486Wt.d();
            while (c1486Wt.i()) {
                ES es = (ES) this.b.get(c1486Wt.p());
                if (es == null || !es.c) {
                    c1486Wt.v();
                } else {
                    Object objA2 = es.f.a(c1486Wt);
                    if (objA2 != null || !es.i) {
                        es.d.set(objA, objA2);
                    }
                }
            }
            c1486Wt.h();
            return objA;
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (IllegalStateException e2) {
            throw new C1542Yt(e2);
        }
    }

    @Override // com.android.tools.r8.internal.EZ
    public final void a(C1799cu c1799cu, Object obj) throws IOException {
        if (obj == null) {
            c1799cu.l();
            return;
        }
        c1799cu.e();
        try {
            for (ES es : this.b.values()) {
                if (es.b && es.d.get(obj) != obj) {
                    c1799cu.b(es.a);
                    (es.e ? es.f : new GZ(es.g, es.f, es.h.b())).a(c1799cu, es.d.get(obj));
                }
            }
            c1799cu.g();
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }
}
