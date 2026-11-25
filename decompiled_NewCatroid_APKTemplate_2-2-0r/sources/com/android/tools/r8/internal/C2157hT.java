package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0713n4;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.shaking.C3733i;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2157hT extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean n = true;
    private final C3733i e;
    private final com.android.tools.r8.utils.A1 f;
    private final AbstractC1476Wj g;
    private final C1926eT h;
    private final AbstractC2331jn i;
    private final C1844dT j;
    private final C1844dT k;
    private final AbstractC1476Wj l;
    private final com.android.tools.r8.graph.o5 m;

    public C2157hT(C0702m c0702m, C1926eT c1926eT, InterfaceC0694k5 interfaceC0694k5, C1844dT c1844dT) {
        com.android.tools.r8.graph.W wA1;
        super(c0702m, interfaceC0694k5);
        this.e = (C3733i) c0702m.f();
        this.f = c0702m.Y();
        this.h = c1926eT;
        this.i = c0702m.N();
        this.g = c0702m.E();
        this.j = c1926eT.b(interfaceC0694k5.e());
        this.k = c1844dT;
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        if (interfaceC0694k5.h0() && (wA1 = interfaceC0694k5.c().e().a1()) != null) {
            abstractC1476WjE = wA1.a(c0702m);
        }
        this.l = abstractC1476WjE;
        this.m = interfaceC0694k5.h0() ? interfaceC0694k5.c() : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC0727q0 abstractC0727q0) {
        com.android.tools.r8.graph.E accessFlags = abstractC0727q0.getAccessFlags();
        if (accessFlags.i()) {
            return true;
        }
        return accessFlags.l() && !this.e.c(((InterfaceC0694k5) d()).C(), abstractC0727q0.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, new Predicate() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((AbstractC0727q0) obj);
            }
        });
    }

    private void h(C0767v2 c0767v2) {
        a(c0767v2, new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.f((AbstractC0727q0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        C3733i c3733i = this.e;
        AbstractC2331jn abstractC2331jn = this.i;
        AbstractC1476Wj abstractC1476Wj = this.g;
        abstractC1476Wj.getClass();
        a((AbstractC0713n4) c3733i.c(abstractC2331jn.a(abstractC1476Wj.b(AbstractC1476Wj.e(), c0767v2))), false);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void d(C0767v2 c0767v2) {
        AbstractC1476Wj abstractC1476Wj = this.g;
        abstractC1476Wj.getClass();
        h(abstractC1476Wj.b(AbstractC1476Wj.e(), c0767v2));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        C3733i c3733i = this.e;
        AbstractC1476Wj abstractC1476Wj = this.g;
        com.android.tools.r8.graph.o5 o5Var = this.m;
        AbstractC1476Wj abstractC1476Wj2 = this.l;
        abstractC1476Wj.getClass();
        a((AbstractC0713n4) c3733i.h((C0684j2) abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.e, abstractC1476Wj2).b()), true);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        C3733i c3733i = this.e;
        AbstractC1476Wj abstractC1476Wj = this.g;
        com.android.tools.r8.graph.o5 o5Var = this.m;
        AbstractC1476Wj abstractC1476Wj2 = this.l;
        abstractC1476Wj.getClass();
        a((AbstractC0713n4) c3733i.h((C0684j2) abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.f, abstractC1476Wj2).b()), true);
    }

    public final void i(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0713n4) this.e.c(this.g.c((AbstractC1476Wj) null, y0)), false);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(C0767v2 c0767v2) {
        h(AbstractC2964s2.a(this.g, c0767v2));
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        a(this.l, c0767v2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        C3733i c3733i = this.e;
        AbstractC1476Wj abstractC1476Wj = this.g;
        com.android.tools.r8.graph.o5 o5Var = this.m;
        AbstractC1476Wj abstractC1476Wj2 = this.l;
        abstractC1476Wj.getClass();
        a((AbstractC0713n4) c3733i.b((C0684j2) abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.g, abstractC1476Wj2).b(), false), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, CM.b());
    }

    public final com.android.tools.r8.graph.o5 i(C0684j2 c0684j2) {
        com.android.tools.r8.graph.I4 i4H = this.e.h(c0684j2);
        a((AbstractC0713n4) i4H, false);
        if (i4H.x()) {
            return i4H.o().s();
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        C3733i c3733i = this.e;
        AbstractC1476Wj abstractC1476Wj = this.g;
        com.android.tools.r8.graph.o5 o5Var = this.m;
        AbstractC1476Wj abstractC1476Wj2 = this.l;
        abstractC1476Wj.getClass();
        a((AbstractC0713n4) c3733i.b((C0684j2) abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.d, abstractC1476Wj2).b(), true), true);
    }

    public final void a(com.android.tools.r8.graph.I4 i4) {
        a((AbstractC0713n4) i4, false);
    }

    private void a(AbstractC0713n4 abstractC0713n4, boolean z) {
        C1844dT c1844dTB;
        if (!abstractC0713n4.j()) {
            if (abstractC0713n4.i()) {
                AbstractC0677i3 abstractC0677i3E = abstractC0713n4.e();
                if (n || abstractC0677i3E.h()) {
                    return;
                }
                if (!abstractC0677i3E.z() || abstractC0677i3E.q() == null || !abstractC0677i3E.q().e().O0()) {
                    throw new AssertionError();
                }
                return;
            }
            com.android.tools.r8.graph.I4 i4F = abstractC0713n4.f();
            i4F.getClass();
            if ((i4F instanceof com.android.tools.r8.graph.G4) || (i4F instanceof com.android.tools.r8.graph.E4) || i4F.b((AbstractC0727q0) ((InterfaceC0694k5) d()).b(), this.a, (C0680j) this.e)) {
                return;
            }
            this.j.a(this.k);
            return;
        }
        com.android.tools.r8.graph.C5 c5G = abstractC0713n4.g();
        f(c5G.a());
        AbstractC0741s0 abstractC0741s0C = c5G.c();
        if (abstractC0741s0C == null || (c1844dTB = this.h.b(abstractC0741s0C.e())) == null) {
            return;
        }
        AbstractC0657g accessFlags = c5G.c().getAccessFlags();
        if (!accessFlags.i()) {
            if (!accessFlags.l()) {
                return;
            }
            if (this.e.c(((InterfaceC0694k5) d()).C(), c5G.d().getType()) && (!z || !(this.f.f instanceof ClassFileConsumer) || this.e.c(c5G.a().getType(), ((InterfaceC0694k5) d()).C()))) {
                return;
            }
        }
        this.j.a(c1844dTB);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, CM.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g(C0767v2 c0767v2) {
        a(c0767v2, new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, CM.b());
    }

    private void a(C0767v2 c0767v2, Consumer consumer) {
        if (c0767v2.M0()) {
            a(c0767v2.b(this.e.a()), consumer);
            return;
        }
        if (c0767v2.W0() || c0767v2.Y0()) {
            return;
        }
        if (!n && !c0767v2.O0()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C = this.e.c(c0767v2);
        if (abstractC0727q0C != null) {
            consumer.accept(abstractC0727q0C);
        }
    }

    private void a(AbstractC0727q0 abstractC0727q0, Predicate predicate) {
        C1844dT c1844dTB = this.h.b(abstractC0727q0);
        if (c1844dTB == null || !predicate.test(abstractC0727q0)) {
            return;
        }
        this.j.a(c1844dTB);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        C3733i c3733i = this.e;
        AbstractC1476Wj abstractC1476Wj = this.g;
        com.android.tools.r8.graph.o5 o5Var = this.m;
        AbstractC1476Wj abstractC1476Wj2 = this.l;
        abstractC1476Wj.getClass();
        a((AbstractC0713n4) c3733i.h((C0684j2) abstractC1476Wj.a(c0684j2, o5Var.getReference(), EnumC1953et.c, abstractC1476Wj2).b()), true);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(com.android.tools.r8.graph.Y0 y0) {
        i(y0);
    }

    public final void a(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        h(this.g.a(abstractC1476Wj, c0767v2));
    }

    public final void a(com.android.tools.r8.graph.O2 o2) {
        com.android.tools.r8.graph.o5 o5VarI;
        if (o2 == null) {
            return;
        }
        if (o2.a() != null) {
            a(o2.a(), new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((AbstractC0727q0) obj);
                }
            });
        }
        if (o2.b() == null || (o5VarI = i(o2.b())) == null) {
            return;
        }
        a(o5VarI.a(), CM.b());
    }

    public final void a(com.android.tools.r8.graph.J3 j3) {
        j3.a(new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.g((C0767v2) obj);
            }
        });
    }

    public final void a(com.android.tools.r8.graph.W4 w4) {
        if (w4 == null) {
            return;
        }
        a(w4.a(), new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d((AbstractC0727q0) obj);
            }
        });
    }

    public final void a(List list) {
        if (list == null) {
            return;
        }
        list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.X4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.X4 x4) {
        a(x4.a(), new Consumer() { // from class: com.android.tools.r8.internal.hT$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.e((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, CM.b());
    }
}
