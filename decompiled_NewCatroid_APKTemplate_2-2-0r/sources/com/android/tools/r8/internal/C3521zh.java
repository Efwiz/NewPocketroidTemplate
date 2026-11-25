package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.ir.optimize.C3585t;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3521zh {
    private final C0702m a;
    private final C0951Eh b;
    private final C0983Fh c;
    private final C1503Xh d;
    private final C1530Yh e;

    public C3521zh(C0702m c0702m) {
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        this.a = c0702m;
        this.c = a1Y.s ? new C0983Fh() : null;
        this.b = new C0951Eh(c0702m);
        this.d = new C1503Xh(c0702m);
        this.e = new C1530Yh(c0702m);
    }

    public final C0951Eh a() {
        return this.b;
    }

    public final void a(C3585t c3585t) {
        C0951Eh c0951Eh = this.b;
        if (c0951Eh != null) {
            c0951Eh.a(c3585t);
        }
    }

    public void a(C2409kl c2409kl, C2133h6.a aVar, com.android.tools.r8.ir.optimize.info.n nVar, TE te) {
        C0951Eh c0951Eh;
        if (te.c()) {
            if (c2409kl.t().l() || c2409kl.t().B()) {
                for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
                    if (abstractC2412ko.K1()) {
                        AbstractC1182Mh abstractC1182MhN = abstractC2412ko.N();
                        com.android.tools.r8.graph.m5 m5VarQ = ((C0680j) this.a.f()).c(abstractC1182MhN.getField()).q();
                        if (m5VarQ != null) {
                            C0951Eh c0951Eh2 = this.b;
                            if (c0951Eh2 != null) {
                                c2409kl.h();
                                c0951Eh2.a(abstractC1182MhN, m5VarQ);
                            }
                            if (this.c != null) {
                                C0983Fh.a(abstractC1182MhN, m5VarQ.e(), nVar);
                            }
                            C1503Xh c1503Xh = this.d;
                            if (c1503Xh != null) {
                                c1503Xh.a(abstractC1182MhN, aVar, c2409kl.h());
                            }
                            C1530Yh c1530Yh = this.e;
                            if (c1530Yh != null) {
                                c1530Yh.a(abstractC1182MhN, m5VarQ, aVar);
                            }
                        }
                    } else if (abstractC2412ko.p2()) {
                        DG dgT0 = abstractC2412ko.t0();
                        C0729q2 c0729q2A = C0729q2.a(this.a.c(dgT0.h));
                        if (c0729q2A != null && (c0951Eh = this.b) != null) {
                            c0951Eh.a(dgT0, c0729q2A, c2409kl.h());
                        }
                    }
                }
            }
        }
    }

    public C3521zh(C0702m<? extends C0680j> c0702m, C0951Eh c0951Eh, C0983Fh c0983Fh, C1503Xh c1503Xh, C1530Yh c1530Yh) {
        this.a = c0702m;
        this.b = c0951Eh;
        this.c = c0983Fh;
        this.d = c1503Xh;
        this.e = c1530Yh;
    }
}
