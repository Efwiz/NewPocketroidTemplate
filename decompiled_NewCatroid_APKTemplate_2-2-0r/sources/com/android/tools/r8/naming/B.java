package com.android.tools.r8.naming;

import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.dex.code.B1;
import com.android.tools.r8.dex.code.C0430e0;
import com.android.tools.r8.dex.code.C0447h2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0816z2;
import com.android.tools.r8.graph.F5;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AQ;
import com.android.tools.r8.internal.AbstractC2525mG;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C1891e7;
import com.android.tools.r8.internal.C2134h7;
import com.android.tools.r8.internal.J7;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3826x2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.InterfaceC3947p1;
import com.android.tools.r8.utils.V1;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C3826x2 b;
    private final AQ c;
    private final AbstractC3649o0 d;

    B(C0702m c0702m, AbstractC3649o0 abstractC3649o0) {
        this.a = c0702m;
        this.b = c0702m.Y().D0().a();
        this.c = AQ.a(c0702m);
        this.d = abstractC3649o0;
    }

    private void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.B$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    final void b(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.B$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0729q2) obj);
            }
        }, executorService);
    }

    final void c(ExecutorService executorService) {
        if (!this.b.c()) {
            a(executorService);
        }
        b(executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        if (this.b.a(c0729q2.d)) {
            for (com.android.tools.r8.graph.T0 t0 : c0729q2.N1()) {
                if (!e && !t0.f.o()) {
                    throw new AssertionError();
                }
                B2 b2Y0 = t0.Y0();
                if (b2Y0.e1()) {
                    t0.a((B2) new B2.j(a((C0759u2) b2Y0.K0().j1())));
                }
            }
            c0729q2.f(new Consumer() { // from class: com.android.tools.r8.naming.B$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((W0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2) {
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.N1()) {
            if (!e && !t0.f.o()) {
                throw new AssertionError();
            }
            B2 b2Y0 = t0.Y0();
            if (b2Y0 instanceof C0816z2) {
                C0816z2 c0816z2 = (C0816z2) b2Y0;
                AbstractC2525mG abstractC2525mGJ1 = c0816z2.j1();
                AbstractC0744s2 abstractC0744s2 = (AbstractC0744s2) c0816z2.k1();
                C0702m c0702m = this.a;
                t0.a((B2) new B2.j(abstractC2525mGJ1.a(abstractC0744s2, c0702m, c0702m.E(), this.d)));
            }
        }
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.naming.B$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(W0 w0) {
        com.android.tools.r8.graph.W wA1;
        if (w0.X1() || (wA1 = w0.a1()) == null) {
            return;
        }
        if (wA1.B0()) {
            for (B1 b1 : wA1.P().i) {
                if (b1.F()) {
                    C0430e0 c0430e0D = b1.d();
                    c0430e0D.f = a(c0430e0D.N());
                }
            }
            return;
        }
        if (wA1.z0()) {
            for (B7 b7 : wA1.K().I0()) {
                b7.getClass();
                if (b7 instanceof C1891e7) {
                    C1891e7 c1891e7E = b7.e();
                    c1891e7E.a(a(c1891e7E.T()));
                }
            }
            return;
        }
        if (!e && !wA1.A0() && !wA1.C0()) {
            throw new AssertionError();
        }
    }

    private C0759u2 a(C0759u2 c0759u2) {
        C0767v2 c0767v2A;
        AbstractC0727q0 abstractC0727q0D;
        C0759u2 c0759u2B;
        String string = c0759u2.toString();
        String strH = C3970u0.E(string) ? C3970u0.H(string) : null;
        if (strH == null) {
            return c0759u2;
        }
        C0767v2 c0767v2E = this.a.a().e(strH);
        return (this.a.E().a(c0767v2E) != c0767v2E || (abstractC0727q0D = ((C3733i) this.a.f()).d((c0767v2A = AbstractC2964s2.a(this.a.E(), c0767v2E)))) == null || abstractC0727q0D.I1() || (c0759u2B = this.d.b(c0767v2A)) == null) ? c0759u2 : this.a.a().c(C3970u0.b(c0759u2B.toString()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final o5 o5Var) {
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        boolean z = e;
        if (!z && wA1 == null) {
            throw new AssertionError();
        }
        if (wA1.B0()) {
            B1[] b1Arr = wA1.P().i;
            for (int i = 0; i < b1Arr.length; i++) {
                B1 b1 = b1Arr[i];
                b1.getClass();
                if (b1 instanceof C0447h2) {
                    C0447h2 c0447h2H = b1.h();
                    AbstractC2525mG abstractC2525mGO = c0447h2H.O();
                    AbstractC0744s2 abstractC0744s2N = c0447h2H.N();
                    C0702m c0702m = this.a;
                    C0430e0 c0430e0 = new C0430e0(c0447h2H.e, abstractC2525mGO.a(abstractC0744s2N, c0702m, c0702m.E(), this.d));
                    c0430e0.g(b1.u());
                    b1Arr[i] = c0430e0;
                }
            }
            return;
        }
        if (wA1.z0()) {
            List<B7> listI0 = wA1.K().I0();
            wA1.K().a(V1.a(listI0, new InterfaceC3947p1() { // from class: com.android.tools.r8.naming.B$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.InterfaceC3947p1
                public final Object a(int i2, Object obj) {
                    return this.f$0.a(o5Var, i2, (B7) obj);
                }
            }, listI0));
        } else if (!z && !(wA1 instanceof com.android.tools.r8.graph.Z) && !(wA1 instanceof F5)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public B7 a(o5 o5Var, int i, B7 b7) {
        b7.getClass();
        if (b7 instanceof C2134h7) {
            C2134h7 c2134h7F = b7.f();
            AbstractC2525mG abstractC2525mGU = c2134h7F.U();
            AbstractC0744s2 abstractC0744s2T = c2134h7F.T();
            C0702m c0702m = this.a;
            return new C1891e7(abstractC2525mGU.a(abstractC0744s2T, c0702m, c0702m.E(), this.d));
        }
        AQ aq = this.c;
        return (aq == null || !(b7 instanceof J7)) ? b7 : aq.a(b7.n(), o5Var, this.d);
    }
}
