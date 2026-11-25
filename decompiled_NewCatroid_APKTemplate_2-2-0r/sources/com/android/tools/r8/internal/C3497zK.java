package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3497zK extends HW {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.graph.S1 c;
    private final com.android.tools.r8.utils.A1 d;

    C3497zK(C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        this.a = c0702m;
        this.b = c0722p1A;
        this.c = c0722p1A.f4;
        this.d = c0702m.Y();
    }

    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        C0684j2 reference = abstractC0748t0.getReference();
        byte b = reference.C0().e[0];
        if (b == 101) {
            if (reference == this.c.a) {
                S10 s10C = abstractC1305Qs.c(0);
                S10 s10K2 = abstractC1305Qs.K2();
                if (!s10C.b(this.a)) {
                    if (s10C.P()) {
                        int i = C2115gt.m;
                        interfaceC2641no.d(((C2030ft) ((C2030ft) ((C2030ft) new C2030ft().a(this.b.g4.d)).a(abstractC1305Qs.b())).a(AbstractC1639am.a(s10C, s10K2))).b());
                        return;
                    }
                    return;
                }
                if (s10K2.b(this.a)) {
                    interfaceC2641no.c(c2409kl);
                    return;
                }
                if (s10K2.P()) {
                    interfaceC2641no.b(c2409kl);
                    return;
                } else {
                    if (this.d.S()) {
                        int i2 = C1721bt.m;
                        C1646at c1646at = (C1646at) ((C1646at) new C1646at().a(this.c.c)).a(abstractC1305Qs.b());
                        c1646at.getClass();
                        interfaceC2641no.d(((C1646at) c1646at.a(AbstractC1639am.c(s10K2))).b());
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (b == 110) {
            if (reference == this.c.d) {
                S10 s10C2 = abstractC1305Qs.c(0);
                if (s10C2.b(this.a)) {
                    interfaceC2641no.b(c2409kl);
                    return;
                } else {
                    if (s10C2.P()) {
                        interfaceC2641no.c(c2409kl);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (b == 114) {
            com.android.tools.r8.graph.S1 s1 = this.c;
            if (reference == s1.e || reference == s1.f || reference == s1.g || reference == s1.h || reference == s1.i) {
                if (abstractC1305Qs.d1() && abstractC1305Qs.b().A()) {
                    return;
                }
                S10 s10C3 = abstractC1305Qs.c(0);
                if (s10C3.P()) {
                    if (abstractC1305Qs.d1()) {
                        abstractC1305Qs.b().a(s10C3, set);
                    }
                    interfaceC2641no.s();
                    return;
                }
                if (s10C3.b(this.a)) {
                    if (abstractC0748t0.getReference() == this.c.e) {
                        if (this.a.f().h()) {
                            interfaceC2641no.a((C0702m<?>) this.a.h0(), c2409kl, b4, (Set<C3332x4>) set2, (Set<S10>) set);
                            return;
                        }
                        return;
                    } else if (abstractC0748t0.getReference() != this.c.h) {
                        abstractC0748t0.getReference();
                        C0684j2 c0684j2 = this.c.i;
                        return;
                    } else {
                        if (abstractC1305Qs.K2().P()) {
                            if (abstractC1305Qs.d1()) {
                                abstractC1305Qs.b().a(abstractC1305Qs.K2(), set);
                            }
                            interfaceC2641no.s();
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (b != 116) {
            if (b != 104) {
                if (b == 105 && reference == this.c.c) {
                    S10 s10C4 = abstractC1305Qs.c(0);
                    if (s10C4.b(this.a)) {
                        interfaceC2641no.c(c2409kl);
                        return;
                    } else {
                        if (s10C4.P()) {
                            interfaceC2641no.b(c2409kl);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (reference == this.c.b) {
                S10 s10C5 = abstractC1305Qs.c(0);
                if (s10C5.b(this.a)) {
                    interfaceC2641no.a(c2409kl, 0);
                    return;
                } else {
                    if (s10C5.P()) {
                        int i3 = C2115gt.m;
                        C2030ft c2030ft = (C2030ft) ((C2030ft) new C2030ft().a(this.b.g4.f)).a(abstractC1305Qs.b());
                        c2030ft.getClass();
                        interfaceC2641no.d(((C2030ft) c2030ft.a(AbstractC1639am.c(s10C5))).b());
                        return;
                    }
                    return;
                }
            }
            return;
        }
        com.android.tools.r8.graph.S1 s2 = this.c;
        if (reference == s2.j || reference == s2.k) {
            S10 s10C6 = abstractC1305Qs.c(0);
            AbstractC3472z00 abstractC3472z00W = s10C6.w();
            if (!abstractC3472z00W.N().f()) {
                if (abstractC3472z00W.x() && abstractC3472z00W.a(this.b.X1)) {
                    if (abstractC1305Qs.d1()) {
                        set.addAll(abstractC1305Qs.b().a());
                        abstractC1305Qs.b().g(s10C6);
                    }
                    interfaceC2641no.s();
                    return;
                }
                return;
            }
            if (abstractC0748t0.getReference() == this.c.j) {
                if (abstractC1305Qs.d1()) {
                    set.addAll(abstractC1305Qs.b().a());
                }
                interfaceC2641no.a(this.a, c2409kl, "null");
            } else {
                if (!e && abstractC0748t0.getReference() != this.c.k) {
                    throw new AssertionError();
                }
                if (abstractC1305Qs.d1()) {
                    abstractC1305Qs.b().a(abstractC1305Qs.K2(), set);
                }
                interfaceC2641no.s();
            }
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.b.g2;
    }
}
