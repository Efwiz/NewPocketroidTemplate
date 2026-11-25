package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1763cQ extends AbstractC2598nE {
    private static final com.android.tools.r8.ir.optimize.info.r c = com.android.tools.r8.ir.optimize.info.r.b();
    static final /* synthetic */ boolean d = true;

    public C1763cQ(C0702m c0702m) {
        super(c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2598nE
    final void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, Y2 y2) {
        boolean zB;
        AbstractC3472z00 abstractC3472z00W = y2.J2().w();
        if (abstractC3472z00W.r()) {
            AbstractC3472z00 abstractC3472z00S = abstractC3472z00W.a().S();
            if (abstractC3472z00S.w()) {
                C1229Oa c1229OaB = abstractC3472z00S.b();
                if (c1229OaB.R().b(this.a)) {
                    zB = true;
                } else {
                    zB = c1229OaB.S().e() ? c1229OaB.S().d().b(this.a) : false;
                }
                if (zB) {
                    C3332x4 c3332x4H = y2.h();
                    AbstractC1915eM abstractC1915eMY0 = y2.Y0();
                    C2853qV c2853qVA = this.a.b().a();
                    C0702m c0702m = this.a;
                    c2853qVA.getClass();
                    AbstractC2412ko abstractC2412koA = c2853qVA.a(c0702m, c2409kl.h(), c2409kl.e, y2);
                    set.addAll(y2.b().a());
                    y2.b().g(abstractC2412koA.b());
                    abstractC2412koA.b(abstractC1915eMY0);
                    if (c3332x4H.D()) {
                        interfaceC2641no.a(c2409kl, b4, this.a.Y()).a(c2409kl).add(abstractC2412koA);
                    } else {
                        interfaceC2641no.add(abstractC2412koA);
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2598nE
    final void a(C2409kl c2409kl, com.android.tools.r8.graph.o5 o5Var, Set set, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs) {
        com.android.tools.r8.graph.I4.c<?> cVarO;
        B1 b1F;
        boolean zA;
        if (abstractC1305Qs.f1()) {
            return;
        }
        C0684j2 c0684j2S2 = abstractC1305Qs.S2();
        if (c0684j2S2.B0().O0() && (cVarO = ((C3733i) this.a.f()).h(c0684j2S2).o()) != null) {
            AbstractC0748t0 abstractC0748t0H = abstractC1305Qs.h(this.a, o5Var);
            C3749l c3749lT = this.a.t();
            AbstractC0748t0 abstractC0748t0C = cVarO.c();
            c3749lT.getClass();
            Q3 q3A = c3749lT.a(abstractC0748t0C.getReference());
            Q3 q3A2 = abstractC0748t0H != null ? c3749lT.a(abstractC0748t0H.getReference()) : null;
            if (q3A2 != null) {
                q3A = q3A.a(q3A2);
            }
            if (a(c2409kl, set, b4, interfaceC2641no, abstractC1305Qs, q3A)) {
                return;
            }
            if (abstractC0748t0H != null) {
                if (abstractC0748t0H instanceof com.android.tools.r8.graph.o5) {
                    zA = ((C3733i) this.a.f()).b(this.a, abstractC0748t0H.getReference());
                } else {
                    zA = this.a.t().a(abstractC0748t0H);
                }
                if (!zA) {
                    return;
                }
            }
            if (c0684j2S2.I0().b(this.a)) {
                b1F = this.a.b().a();
            } else if (abstractC0748t0H != null) {
                b1F = abstractC0748t0H.e().i1().h();
            } else {
                b1F = C2892r10.F();
            }
            if (b1F.g()) {
                AbstractC3292wV abstractC3292wVJ = b1F.j();
                if (abstractC3292wVJ.b(this.a, o5Var)) {
                    C3332x4 c3332x4H = abstractC1305Qs.h();
                    AbstractC1915eM abstractC1915eMY0 = abstractC1305Qs.Y0();
                    AbstractC2412ko abstractC2412koA = abstractC3292wVJ.a(this.a, c2409kl.h(), c2409kl.e, abstractC1305Qs);
                    set.addAll(abstractC1305Qs.b().a());
                    abstractC1305Qs.c(abstractC2412koA);
                    abstractC1305Qs.b().g(abstractC2412koA.b());
                    abstractC1305Qs.f(null);
                    if (abstractC1305Qs.Z1()) {
                        interfaceC2641no.a(this.a, o5Var);
                    } else if ((abstractC1305Qs instanceof C1721bt) && abstractC0748t0H != null) {
                        interfaceC2641no.a(this.a, c2409kl, abstractC0748t0H.t());
                    }
                    abstractC2412koA.b(abstractC1915eMY0);
                    if (c3332x4H.D()) {
                        interfaceC2641no.a(c2409kl, b4, this.a.Y()).a(c2409kl).add(abstractC2412koA);
                    } else {
                        interfaceC2641no.add(abstractC2412koA);
                    }
                    if (abstractC0748t0H != null) {
                        abstractC0748t0H.e().g1().F();
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2598nE
    final void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, C1189Mn c1189Mn) {
        a(c2409kl, set, b4, interfaceC2641no, (AbstractC1182Mh) c1189Mn);
    }

    @Override // com.android.tools.r8.internal.AbstractC2598nE
    final void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, QW qw) {
        a(c2409kl, set, b4, interfaceC2641no, (AbstractC1182Mh) qw);
    }

    private void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC1182Mh abstractC1182Mh) {
        boolean zA;
        B1 b1A;
        final com.android.tools.r8.graph.Y0 field = abstractC1182Mh.getField();
        AbstractC0677i3.a<?> aVarL = ((C3733i) this.a.f()).c(field).l();
        if (aVarL == null) {
            if (((Boolean) this.a.a((Object) Boolean.FALSE, new Function() { // from class: com.android.tools.r8.internal.cQ$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1763cQ.a(field, (C0831Aj) obj);
                }
            })).booleanValue()) {
                interfaceC2641no.d(c2409kl.i());
                return;
            }
            return;
        }
        AbstractC0735r0 abstractC0735r0C = aVarL.c();
        com.android.tools.r8.graph.T0 t0E = abstractC0735r0C.e();
        boolean z = abstractC1182Mh instanceof QW;
        if (t0E.E0() != z) {
            return;
        }
        if (z && abstractC1182Mh.f1()) {
            interfaceC2641no.a(this.a, c2409kl, field.B0());
            return;
        }
        if (abstractC0735r0C instanceof com.android.tools.r8.graph.m5) {
            zA = ((C3733i) this.a.f()).b(this.a, abstractC0735r0C.getReference());
        } else {
            zA = this.a.t().a(abstractC0735r0C);
        }
        if (zA) {
            C3749l c3749lT = this.a.t();
            c3749lT.getClass();
            if (a(c2409kl, set, b4, interfaceC2641no, abstractC1182Mh, c3749lT.a(abstractC0735r0C.getReference()))) {
                return;
            }
            if (field.getType().b(this.a)) {
                b1A = this.a.b().a(0L);
            } else if (((C3733i) this.a.f()).c(t0E)) {
                b1A = t0E.X0().f();
                if (b1A.isUnknown() && !t0E.E0()) {
                    B1 b1A2 = abstractC1182Mh.T().e().a(this.a, c2409kl.h());
                    if (b1A2.v()) {
                        b1A = b1A2.t().a(t0E);
                    }
                }
            } else if (t0E.E0()) {
                b1A = t0E.Y0().a(this.a.b());
                if (!d && !t0E.X0().f().isUnknown() && t0E.Z0() && !b1A.equals(t0E.X0().f())) {
                    throw new AssertionError();
                }
            } else {
                b1A = this.a.b().a(0L);
            }
            if (b1A.g()) {
                AbstractC3292wV abstractC3292wVJ = b1A.j();
                if (!(abstractC3292wVJ.B() && abstractC3292wVJ.p().F() == field) && abstractC3292wVJ.b(this.a, c2409kl.h())) {
                    C3332x4 c3332x4H = abstractC1182Mh.h();
                    com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
                    AbstractC1915eM abstractC1915eMY0 = abstractC1182Mh.Y0();
                    AbstractC2412ko abstractC2412koA = abstractC3292wVJ.a(this.a, c2409kl.h(), c2409kl.e, abstractC1182Mh);
                    set.addAll(abstractC1182Mh.b().a());
                    abstractC1182Mh.b().g(abstractC2412koA.b());
                    if (abstractC1182Mh instanceof C1189Mn) {
                        interfaceC2641no.a(this.a, o5VarH);
                    } else {
                        if (!d && !z) {
                            throw new AssertionError();
                        }
                        interfaceC2641no.a(this.a, c2409kl, abstractC0735r0C.t());
                    }
                    abstractC2412koA.b(abstractC1915eMY0);
                    if (c3332x4H.D()) {
                        interfaceC2641no.a(c2409kl, b4, this.a.Y()).a(c2409kl).add(abstractC2412koA);
                    } else {
                        interfaceC2641no.add(abstractC2412koA);
                    }
                    c.b(t0E);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(com.android.tools.r8.graph.Y0 y0, C0831Aj c0831Aj) {
        return Boolean.valueOf(c0831Aj.b(y0));
    }

    @Override // com.android.tools.r8.internal.AbstractC2598nE
    final void a(C2409kl c2409kl, InterfaceC2641no interfaceC2641no, C1793co c1793co) {
        AbstractC0735r0 abstractC0735r0C = ((C3733i) this.a.f()).c(c1793co.getField()).c();
        if (abstractC0735r0C == null || abstractC0735r0C.getAccessFlags().o()) {
            return;
        }
        if (!abstractC0735r0C.getType().b(this.a) && ((C3733i) this.a.f()).a(abstractC0735r0C.e())) {
            C3749l c3749lT = this.a.t();
            C0702m c0702m = this.a;
            c3749lT.getClass();
            B1 b1D = c3749lT.a(abstractC0735r0C.getReference()).d();
            if (b1D.g() && b1D.j().a(c0702m)) {
                interfaceC2641no.a(this.a, c2409kl.h());
                return;
            }
            return;
        }
        interfaceC2641no.a(this.a, c2409kl.h());
    }

    @Override // com.android.tools.r8.internal.AbstractC2598nE
    final void a(C2409kl c2409kl, InterfaceC2641no interfaceC2641no, RW rw) {
        AbstractC0735r0 abstractC0735r0C = ((C3733i) this.a.f()).c(rw.getField()).c();
        if (abstractC0735r0C == null || !abstractC0735r0C.getAccessFlags().o()) {
            return;
        }
        if (!abstractC0735r0C.getType().b(this.a) && ((C3733i) this.a.f()).a(abstractC0735r0C.e())) {
            C3749l c3749lT = this.a.t();
            C0702m c0702m = this.a;
            c3749lT.getClass();
            B1 b1D = c3749lT.a(abstractC0735r0C.getReference()).d();
            if (b1D.g() && b1D.j().a(c0702m)) {
                interfaceC2641no.a(this.a, c2409kl, abstractC0735r0C.t());
                return;
            }
            return;
        }
        interfaceC2641no.a(this.a, c2409kl, abstractC0735r0C.t());
    }
}
