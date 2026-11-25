package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1926eT {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.r5 b;
    private final IdentityHashMap c = new IdentityHashMap();
    private final Set d;
    private final C1844dT e;

    public C1926eT(C0702m c0702m, com.android.tools.r8.graph.r5 r5Var) {
        Set setC = OU.c();
        this.d = setC;
        this.a = c0702m;
        this.b = r5Var;
        C1844dT c1844dTA = a(c0702m.c(c0702m.a().Z1));
        this.e = c1844dTA;
        setC.add(c1844dTA);
    }

    private C1844dT a(com.android.tools.r8.graph.O0 o0) {
        C1844dT c1844dT = new C1844dT(o0);
        this.c.put(o0, c1844dT);
        return c1844dT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(C2157hT c2157hT, C0767v2 c0767v2) {
        c2157hT.a(this.a.E(), c0767v2);
    }

    public final boolean b() {
        boolean z = false;
        for (C0729q2 c0729q2 : this.b) {
            boolean zA = ((C3733i) this.a.f()).a(this.a, c0729q2);
            boolean z2 = !zA;
            C1844dT c1844dTA = a((com.android.tools.r8.graph.O0) c0729q2);
            if (!zA) {
                this.d.add(c1844dTA);
            }
            Iterator it = c0729q2.L1().iterator();
            while (it.hasNext()) {
                c1844dTA.a(a((com.android.tools.r8.graph.U0) it.next()));
            }
            z |= z2;
        }
        return !z;
    }

    public final void a(ExecutorService executorService) {
        final com.android.tools.r8.graph.r5 r5Var = this.b;
        Objects.requireNonNull(r5Var);
        C3879c3.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                r5Var.b(consumer);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
        this.b.a(new Consumer() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.m5) obj);
            }
        });
    }

    final C1844dT b(com.android.tools.r8.graph.O0 o0) {
        boolean zI1;
        C0702m c0702m = this.a;
        if (o0.A0()) {
            zI1 = o0.t0().I1();
        } else {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(o0.v0().J0());
            zI1 = abstractC0727q0C == null || abstractC0727q0C.I1();
        }
        if (zI1) {
            if (o0.C().H0().equals(this.b.b())) {
                return this.e;
            }
            return null;
        }
        return (C1844dT) this.c.get(o0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0729q2 c0729q2) {
        final C2157hT c2157hT = new C2157hT(this.a, this, c0729q2, this.e);
        if (c0729q2.f != null) {
            c2157hT.a(this.a.E(), c0729q2.n1());
        }
        c0729q2.g.forEach(new Consumer() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2157hT, (C0767v2) obj);
            }
        });
        new C1766cT(this.a, c2157hT).a(c0729q2.s0());
        if (c0729q2.D1()) {
            if (c0729q2.G1()) {
                c0729q2.g(new Consumer() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.b(c2157hT, (C0767v2) obj);
                    }
                });
            } else {
                if (!f && !c0729q2.H1()) {
                    throw new AssertionError();
                }
                c2157hT.a(this.a.E(), c0729q2.g1());
            }
        }
        c0729q2.e1().forEach(new Consumer() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2157hT c2157hT2 = c2157hT;
                C0729q2 c0729q3 = c0729q2;
                c2157hT2.a((com.android.tools.r8.graph.J3) obj);
            }
        });
        c2157hT.a(c0729q2.b1());
        c2157hT.a(c0729q2.h1());
        c2157hT.a(c0729q2.i1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C2157hT c2157hT, C0767v2 c0767v2) {
        c2157hT.a(this.a.E(), c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2157hT c2157hT, C0767v2 c0767v2) {
        c2157hT.a(this.a.E(), c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.m5 m5Var) {
        C2157hT c2157hT = new C2157hT(this.a, this, m5Var, this.e);
        c2157hT.a(this.a.E(), m5Var.getReference().getType());
        new C1766cT(this.a, c2157hT).a(m5Var.e().s0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        final C2157hT c2157hT = new C2157hT(this.a, this, o5Var, this.e);
        w0E.m1().d(new Consumer() { // from class: com.android.tools.r8.internal.eT$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(c2157hT, (C0767v2) obj);
            }
        });
        if (o5Var.a().f != null) {
            AbstractC0727q0 abstractC0727q0B = this.a.b(o5Var.a(), o5Var.a().n1());
            if (abstractC0727q0B != null) {
                c2157hT.a(((C3733i) this.a.f()).g(abstractC0727q0B, o5Var.getReference()));
            }
        }
        C1766cT c1766cT = new C1766cT(this.a, c2157hT);
        c1766cT.a(w0E.s0());
        c1766cT.a(w0E.j1());
        if (w0E.q1()) {
            w0E.a1().a(o5Var, c2157hT);
        }
    }

    public final ArrayList a() {
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) this.d);
        while (o3D.c()) {
            Iterator it = ((C1844dT) o3D.f()).b.iterator();
            while (it.hasNext()) {
                o3D.c((C1844dT) it.next());
            }
        }
        Set setB = o3D.b();
        ArrayList arrayList = new ArrayList();
        for (C0729q2 c0729q2 : this.b) {
            if (!setB.contains(b(c0729q2))) {
                arrayList.add(c0729q2);
            }
        }
        return arrayList;
    }
}
