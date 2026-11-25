package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0572k$$ExternalSyntheticLambda8;
import com.android.tools.r8.dex.code.C0488p3;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.C3988x2;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2854qW {
    private final C0702m a;
    private final C3012sl b;
    private final C0722p1 c;
    private final com.android.tools.r8.utils.A1 d;
    private final C2995sW e;
    private final C2927rW f;

    private C2854qW(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C3012sl(c0702m);
        C0722p1 c0722p1A = c0702m.a();
        this.c = c0722p1A;
        com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        this.d = a1Y;
        this.e = new C2995sW(c0722p1A);
        this.f = a1Y.F0();
    }

    public static void a(C0702m c0702m, ExecutorService executorService) {
        if (c0702m.Y().F0().e()) {
            C2854qW c2854qW = new C2854qW(c0702m);
            c2854qW.a(executorService);
            if (c0702m.c(c2854qW.e.b) != null) {
                return;
            }
            if (c2854qW.f.c() && C0729q2.a(c0702m.c(c2854qW.c.e(C3970u0.H(c2854qW.f.a())))) == null) {
                return;
            }
            C0729q2 c0729q2A = AbstractC2876qo.a(c2854qW.c);
            if (c2854qW.f.d()) {
                c0729q2A.b(c2854qW.c.c("writeToLogcat")).a((com.android.tools.r8.graph.B2) com.android.tools.r8.graph.B2.b.a(true));
                c0729q2A.b(c2854qW.c.c("logcatTag")).a((com.android.tools.r8.graph.B2) new com.android.tools.r8.graph.B2.j(c2854qW.c.c(c2854qW.f.b())));
            }
            AbstractC1639am abstractC1639amA = AbstractC1639am.a(AbstractC2805po.a(c2854qW.c), c0729q2A);
            c2854qW.b.a(abstractC1639amA, VE.a(), executorService);
            c0702m.d(new C0664h(c0702m.f().g().a(c0702m.e().c().a(abstractC1639amA).b()), c0702m.f().f()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(boolean z, com.android.tools.r8.graph.o5 o5Var) {
        a(o5Var.e().u1() && z, o5Var);
    }

    private void a(ExecutorService executorService) {
        C3879c3.a(this.a.f().d(), new Consumer() { // from class: com.android.tools.r8.internal.qW$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        if (c0729q2.getType() == this.e.a || c0729q2.getType() == this.e.b) {
            return;
        }
        final boolean z = false;
        if (!c0729q2.p1()) {
            com.android.tools.r8.androidapi.h hVarA = this.a.d().a(this.d);
            C0488p3 c0488p3 = new C0488p3();
            c0488p3.g(0);
            c0729q2.a(com.android.tools.r8.graph.W0.Y1().a(C0725p4.b(4104, true)).a(hVarA).b(hVarA).a(Z8.f).a(new C0772w0(0, 0, 0, new com.android.tools.r8.dex.code.B1[]{c0488p3}, C0772w0.a.e, C0772w0.b.c, null)).a(this.c.a(c0729q2.getType())).a());
            z = true;
        }
        c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.qW$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(z, (com.android.tools.r8.graph.o5) obj);
            }
        }, new C0572k$$ExternalSyntheticLambda8());
    }

    private void a(boolean z, com.android.tools.r8.graph.o5 o5Var) {
        CE.a aVarB = new CE.a(this.d).b();
        C0702m c0702m = this.a;
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C2409kl c2409klA = w0E.q1() ? w0E.a1().a(o5Var, c0702m, o5Var.getOrigin(), aVarB) : null;
        InterfaceC2641no interfaceC2641noA = c2409klA.j().a(c2409klA);
        interfaceC2641noA.d(C3988x2.a(new A4$$ExternalSyntheticLambda3()));
        if (o5Var.e().u1()) {
            S10 s10A = interfaceC2641noA.a(this.a, c2409klA, this.c.c(o5Var.t().q0()));
            int i = C1721bt.m;
            interfaceC2641noA.add(((C1646at) ((C1646at) ((C1646at) new C1646at().a(this.e.c)).a(AbstractC1639am.c(s10A))).a(AbstractC1915eM.v())).b());
        }
        if (!z) {
            S10 s10A2 = interfaceC2641noA.a(this.a, c2409klA, this.c.c(o5Var.getReference().q0()));
            int i2 = C1721bt.m;
            interfaceC2641noA.add(((C1646at) ((C1646at) ((C1646at) new C1646at().a(this.e.c)).a(AbstractC1639am.c(s10A2))).a(AbstractC1915eM.v())).b());
        }
        this.b.D.a(c2409klA, com.android.tools.r8.utils.y3.b());
        o5Var.a(new C3455yl(this.a, this.b.D).a(c2409klA, C2133h6.b(), com.android.tools.r8.utils.y3.b()), this.a);
    }
}
