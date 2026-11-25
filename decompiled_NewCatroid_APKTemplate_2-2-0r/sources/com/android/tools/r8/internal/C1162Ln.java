package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3585t;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ln, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1162Ln extends AbstractC2098gi {
    static final /* synthetic */ boolean m = true;
    private final C1017Gn i;
    private final C1079In j;
    private final AbstractC0748t0 k;
    private final C1166Ls l;

    private C1162Ln(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar, AbstractC0748t0 abstractC0748t0, C1166Ls c1166Ls) {
        super(c0702m, c2409kl, nVar);
        this.i = AbstractC1049Hn.a();
        this.j = c0702m.O();
        this.k = abstractC0748t0;
        this.l = c1166Ls;
    }

    private void e() {
        if (this.k.t() == this.c.t()) {
            return;
        }
        this.k.e().i1().a(this.l).b().a((com.android.tools.r8.graph.Q0) this.a, new BiConsumer() { // from class: com.android.tools.r8.internal.Ln$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj, (InterfaceC0989Fn) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final C1162Ln a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final boolean b(com.android.tools.r8.graph.T0 t0) {
        throw new C3405y10("Used by static analysis only.");
    }

    public static AbstractC1049Hn a(C0702m c0702m, C2409kl c2409kl, C3585t c3585t, com.android.tools.r8.ir.optimize.info.n nVar, com.android.tools.r8.utils.y3 y3Var) {
        AbstractC0748t0 abstractC0748t0H;
        AbstractC1049Hn abstractC1049HnA;
        y3Var.a("Analyze instance initializer");
        boolean z = m;
        if (!z && !c0702m.f().i()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.q()) {
            throw new AssertionError();
        }
        if (!z && !c2409kl.h().e().C1()) {
            throw new AssertionError();
        }
        C1166Ls c1166LsA = AbstractC2871ql.a(c0702m.a(), c2409kl.o());
        if (c1166LsA == null || (abstractC0748t0H = c1166LsA.h(c0702m, c2409kl.h())) == null) {
            abstractC1049HnA = C1554Zf.c();
        } else {
            C1162Ln c1162Ln = new C1162Ln(c0702m.i0(), c2409kl, nVar, abstractC0748t0H, c1166LsA);
            c1162Ln.a(c3585t);
            c1162Ln.e();
            abstractC1049HnA = c1162Ln.i.a();
        }
        y3Var.c();
        return abstractC1049HnA;
    }

    final void b(com.android.tools.r8.graph.T0 t0, InterfaceC0989Fn interfaceC0989Fn) {
        if (interfaceC0989Fn.isUnknown()) {
            return;
        }
        C1017Gn c1017Gn = this.i;
        c1017Gn.getClass();
        c1017Gn.a(t0.getReference(), interfaceC0989Fn);
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final boolean a(com.android.tools.r8.graph.T0 t0) {
        return !t0.E0() && t0.J0() == this.c.t();
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0073  */
    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final void a(com.android.tools.r8.graph.T0 t0, AbstractC1182Mh abstractC1182Mh, S10 s10) {
        boolean zA;
        C1793co c1793coV = abstractC1182Mh.V();
        if (t0.f.h() || ((C3733i) this.a.f()).a(t0, this.c.e())) {
            zA = true;
        } else if (!((C3733i) this.a.f()).d(t0)) {
            zA = false;
        } else if (this.l.S2().e == this.c.t()) {
            C3332x4 c3332x4H = c1793coV.h();
            C3332x4 c3332x4H2 = this.l.h();
            if (c3332x4H != c3332x4H2) {
                zA = d().a(c3332x4H, c3332x4H2);
            } else {
                Iterator<AbstractC2412ko> it = c3332x4H.p().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        throw new C3405y10();
                    }
                    AbstractC2412ko next = it.next();
                    if (next == c1793coV) {
                        zA = false;
                    } else if (next == this.l) {
                        zA = true;
                    }
                }
            }
        } else {
            zA = true;
        }
        if (zA) {
            b(t0, a(t0, s10));
        }
    }

    final void a(C1166Ls c1166Ls, S10 s10) {
        com.android.tools.r8.graph.o5 o5VarA;
        if (c1166Ls.U2() == s10 && c1166Ls.S2().B0() == this.c.t() && (o5VarA = AbstractC0748t0.a(c1166Ls.h(this.a, this.c))) != null) {
            AbstractC1049Hn abstractC1049HnB = o5VarA.e().i1().a(c1166Ls).b();
            AbstractC0727q0 abstractC0727q0A = o5VarA.a();
            C0702m c0702m = this.a;
            abstractC0727q0A.getClass();
            ArrayList<com.android.tools.r8.graph.T0> arrayList = new ArrayList();
            while (abstractC0727q0A != null && abstractC0727q0A.d != c0702m.a().Z1) {
                arrayList.addAll(abstractC0727q0A.w1());
                abstractC0727q0A = c0702m.c(abstractC0727q0A.f);
            }
            for (com.android.tools.r8.graph.T0 t0 : arrayList) {
                InterfaceC0989Fn interfaceC0989FnA = abstractC1049HnB.a(t0);
                if (interfaceC0989FnA.h()) {
                    interfaceC0989FnA = a(t0, c1166Ls.c(interfaceC0989FnA.f().b()));
                }
                a(t0, c1166Ls, interfaceC0989FnA);
            }
        }
    }

    private InterfaceC0989Fn a(com.android.tools.r8.graph.T0 t0, S10 s10) {
        S10 s10M = s10.m();
        if (s10M.b(new A4$$ExternalSyntheticLambda3())) {
            return this.j.a(s10M.b.q().J2());
        }
        B1 b1A = s10.a(this.a, this.c);
        if (b1A.g()) {
            return b1A.j();
        }
        C0767v2 c0767v2E1 = t0.e1();
        if (c0767v2E1.O0()) {
            C1229Oa c1229OaA = s10.a(this.a, (AbstractC3472z00) null, WH.i());
            AbstractC3472z00 abstractC3472z00A = s10.a(this.a);
            AbstractC3472z00 abstractC3472z00A2 = AbstractC3472z00.a(c0767v2E1, WH.i(), (C0702m<?>) this.a);
            if (c1229OaA != null || !abstractC3472z00A.equals(abstractC3472z00A2)) {
                this.j.getClass();
                return C1079In.a(c1229OaA, abstractC3472z00A);
            }
        }
        return C2351k10.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0, InterfaceC0989Fn interfaceC0989Fn) {
        if (t0.f.h() || ((C3733i) this.a.f()).a(t0, this.k.e())) {
            if (interfaceC0989Fn.h()) {
                b(t0, a(t0, this.l.c(interfaceC0989Fn.f().b())));
            } else {
                if (!m && !interfaceC0989Fn.g() && !interfaceC0989Fn.a()) {
                    throw new AssertionError();
                }
                C1017Gn c1017Gn = this.i;
                c1017Gn.getClass();
                c1017Gn.a(t0.getReference(), interfaceC0989Fn);
            }
        }
    }
}
