package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.IdentityHashMap;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P2 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final IdentityHashMap b = new IdentityHashMap();
    private final ConcurrentHashMap c = new ConcurrentHashMap();

    public P2(C0702m c0702m) {
        this.a = c0702m;
    }

    public final XE a(com.android.tools.r8.graph.o5 o5Var) {
        return (XE) this.b.getOrDefault(o5Var.getReference(), XE.b);
    }

    public final void b() {
        if (!d && !this.c.isEmpty()) {
            throw new AssertionError();
        }
    }

    public final void a() {
        this.b.putAll(this.c);
        this.c.clear();
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0082  */
    public final void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl) {
        AbstractC3053tL abstractC3053tLA;
        AbstractC1332Rs abstractC1332RsC0;
        com.android.tools.r8.graph.I4.c<?> cVarO;
        C1303Qq c1303Qq = new C1303Qq(16);
        InterfaceC2488lo interfaceC2488loR = c2409kl.j().R();
        for (C3406y2 c3406y2Q = interfaceC2488loR.next().q(); c3406y2Q != null; c3406y2Q = interfaceC2488loR.next().q()) {
            if (c3406y2Q.a().H()) {
                abstractC3053tLA = T1.a;
            } else {
                C2984sL c2984sL = new C2984sL();
                if (c3406y2Q.b().y()) {
                    c2984sL.b().d();
                }
                for (AbstractC2412ko abstractC2412ko : c3406y2Q.b().a(C1552Zd.a())) {
                    int iG2 = abstractC2412ko.G2();
                    if (iG2 != 9 && iG2 != 25 && iG2 != 28 && iG2 != 30 && iG2 != 56 && iG2 != 33) {
                        if (iG2 != 34) {
                            switch (iG2) {
                                case 38:
                                    break;
                                case 39:
                                case 40:
                                    abstractC1332RsC0 = abstractC2412ko.c0();
                                    if (abstractC1332RsC0.U2().m() == c3406y2Q.b() && (cVarO = ((C3733i) this.a.f()).h(abstractC1332RsC0.S2()).o()) != null && cVarO.d().e0()) {
                                        c2984sL.c();
                                    }
                                    break;
                                default:
                                    c2984sL.c();
                                    break;
                            }
                        } else {
                            abstractC1332RsC0 = abstractC2412ko.c0();
                            if (abstractC1332RsC0.U2().m() == c3406y2Q.b()) {
                                c2984sL.c();
                            }
                        }
                    }
                    if (c2984sL.e()) {
                        abstractC3053tLA = c2984sL.a();
                    }
                }
                abstractC3053tLA = c2984sL.a();
            }
            if (!(abstractC3053tLA instanceof T1)) {
                c1303Qq.a(c3406y2Q.J2(), abstractC3053tLA);
            }
        }
        if (c1303Qq.isEmpty()) {
            return;
        }
        this.c.put(o5Var.getReference(), new XE(c1303Qq));
    }
}
