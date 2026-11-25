package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XX extends HW {
    private final C0702m a;
    private final C0722p1 b;

    XX(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        C0684j2 reference = abstractC0748t0.getReference();
        C0722p1.c cVar = this.b.k4;
        if (reference == cVar.i) {
            AbstractC1332Rs abstractC1332RsC0 = abstractC1305Qs.c0();
            if (this.a.f().i()) {
                com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
                S10 s10M = abstractC1332RsC0.U2().m();
                S10 s10M2 = abstractC1332RsC0.c(1).m();
                if (a(o5VarH, s10M, s10M2) || a(o5VarH, s10M2, s10M)) {
                    interfaceC2641no.a(c2409kl, 0);
                    return;
                }
                return;
            }
            return;
        }
        if (reference == cVar.s) {
            C1721bt c1721btG0 = abstractC1305Qs.g0();
            S10 s10C = c1721btG0.c(0);
            AbstractC3472z00 abstractC3472z00W = s10C.w();
            if (abstractC3472z00W.N().f()) {
                interfaceC2641no.a(this.a, c2409kl, "null");
                if (c1721btG0.d1()) {
                    set.addAll(c1721btG0.b().a());
                    return;
                }
                return;
            }
            if (abstractC3472z00W.x() && abstractC3472z00W.a(this.b.X1)) {
                if (c1721btG0.d1()) {
                    set.addAll(c1721btG0.b().a());
                    c1721btG0.b().g(s10C);
                }
                interfaceC2641no.s();
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.b.X1;
    }

    private boolean a(com.android.tools.r8.graph.o5 o5Var, S10 s10, S10 s11) {
        AbstractC0748t0 abstractC0748t0H;
        if (s10.i() || s11.i()) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = s10.b;
        if (!abstractC2412ko.f2() || (abstractC0748t0H = abstractC2412ko.i0().h(this.a, o5Var)) == null || abstractC0748t0H.getReference() != this.b.p4.e) {
            return false;
        }
        AbstractC2412ko abstractC2412ko2 = s11.b;
        abstractC2412ko2.getClass();
        if (!(abstractC2412ko2 instanceof C2477lf)) {
            return false;
        }
        C2477lf c2477lfJ = s11.b.J();
        AbstractC0744s2 abstractC0744s2J2 = c2477lfJ.J2();
        abstractC0744s2J2.getClass();
        return (abstractC0744s2J2 instanceof C0767v2) && this.a.f().o().p(abstractC0744s2J2.w0()) && !c2477lfJ.K2().e();
    }
}
