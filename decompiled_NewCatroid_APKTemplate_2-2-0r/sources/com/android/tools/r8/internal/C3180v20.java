package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.v20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3180v20 {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C2688oE c;
    private final List d;
    private final C3103u20 e;

    public C3180v20(C0702m c0702m, C2688oE c2688oE, ArrayList arrayList, C3103u20 c3103u20) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c2688oE;
        this.d = arrayList;
        this.e = c3103u20;
    }

    private C0684j2 a(C2931ra c2931ra, com.android.tools.r8.graph.o5 o5Var) {
        C0684j2 reference = o5Var.getReference();
        C0722p1 c0722p1 = this.b;
        String strR0 = reference.f.r0();
        C0767v2 c0767v2T = o5Var.t();
        C0737r2 c0737r2 = reference.h;
        C0767v2 type = this.c.f().getType();
        Objects.requireNonNull(c2931ra);
        com.android.tools.r8.graph.W0 w0A = o5Var.e().a(c0722p1.a(strR0, c0767v2T, c0737r2, type, new Wn$$ExternalSyntheticLambda1(c2931ra)));
        C0725p4 accessFlags = w0A.getAccessFlags();
        accessFlags.K();
        accessFlags.L();
        accessFlags.A();
        c2931ra.a(w0A);
        return w0A.getReference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0725p4 c0725p4) {
        return !c0725p4.W();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(C0725p4 c0725p4) {
        return !c0725p4.X();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean d(C0725p4 c0725p4) {
        return !c0725p4.h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean e(C0725p4 c0725p4) {
        return !c0725p4.q();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean f(C0725p4 c0725p4) {
        return !c0725p4.d0();
    }

    private boolean b() {
        return this.e != null && AbstractC2734ot.a((Iterable) this.d, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    private boolean d() {
        int iA;
        if (this.e != null) {
            return false;
        }
        if (this.d.size() == 1) {
            return true;
        }
        Iterable iterableC = AbstractC2734ot.c(this.d, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.b((com.android.tools.r8.graph.o5) obj);
            }
        });
        if (iterableC instanceof Collection) {
            iA = ((Collection) iterableC).size();
        } else {
            Iterator it = iterableC.iterator();
            long j = 0;
            while (true) {
                S s = (S) it;
                if (!s.hasNext()) {
                    break;
                }
                s.next();
                j++;
            }
            iA = AbstractC0968Es.a(j);
        }
        return iA <= 1;
    }

    final boolean c() {
        return b() || d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(com.android.tools.r8.graph.o5 o5Var) {
        return !o5Var.e().s1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.o5 o5Var) {
        return !o5Var.e().s1();
    }

    private C0725p4 a() {
        Iterable iterableA = AbstractC2734ot.a((Iterable) this.d, new InterfaceC3010sj() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((com.android.tools.r8.graph.o5) obj).getAccessFlags();
            }
        });
        C0725p4 c0725p4P = ((C0725p4) iterableA.iterator().next()).P();
        boolean z = f;
        if (!z && !AbstractC2734ot.a(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.a((C0725p4) obj);
            }
        })) {
            throw new AssertionError();
        }
        if (!z && c0725p4P.a0() && !AbstractC2734ot.a(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C0725p4) obj).a0();
            }
        })) {
            throw new AssertionError();
        }
        if (!z && c0725p4P.c0() && !AbstractC2734ot.a(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C0725p4) obj).c0();
            }
        })) {
            throw new AssertionError();
        }
        if (c0725p4P.W() && AbstractC2734ot.b(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.b((C0725p4) obj);
            }
        })) {
            c0725p4P.h0();
        }
        if (c0725p4P.X() && AbstractC2734ot.b(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.c((C0725p4) obj);
            }
        })) {
            c0725p4P.i0();
        }
        if (c0725p4P.h() && (this.d.size() < this.c.size() || AbstractC2734ot.b(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.d((C0725p4) obj);
            }
        }))) {
            c0725p4P.G();
        }
        if (c0725p4P.q() && AbstractC2734ot.b(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.e((C0725p4) obj);
            }
        })) {
            c0725p4P.M();
        }
        if (c0725p4P.d0() && AbstractC2734ot.b(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.f((C0725p4) obj);
            }
        })) {
            c0725p4P.l0();
        }
        c0725p4P.k0();
        return c0725p4P;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0725p4 c0725p4) {
        return !c0725p4.Z();
    }

    private void a(C2931ra c2931ra, final C0986Fk c0986Fk) {
        com.android.tools.r8.graph.W0 w0A;
        C0684j2 reference = ((com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.d)).getReference();
        C0729q2 c0729q2F = this.c.f();
        C0722p1 c0722p1 = this.b;
        reference.getClass();
        final C0684j2 c0684j2B = reference.b(c0729q2F.C(), c0722p1);
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) AbstractC2734ot.a(this.d, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3180v20.c((com.android.tools.r8.graph.o5) obj);
            }
        });
        if (o5Var == null) {
            o5Var = (com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.d);
        }
        if (o5Var.getAccessFlags().W() && this.e != null) {
            this.d.forEach(new Consumer() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3180v20.a(c0986Fk, c0684j2B, (com.android.tools.r8.graph.o5) obj);
                }
            });
            return;
        }
        for (com.android.tools.r8.graph.o5 o5Var2 : this.d) {
            if (o5Var2.getReference() == o5Var.getReference()) {
                C0684j2 reference2 = o5Var2.getReference();
                c0986Fk.b.a(reference2, c0684j2B);
                c0986Fk.a(reference2, c0684j2B, false);
            } else {
                c0986Fk.b.a(o5Var2.getReference(), c0684j2B);
            }
        }
        if (o5Var.a() == this.c.f()) {
            w0A = o5Var.e();
        } else {
            final AbstractC3938n2 abstractC3938n2D1 = o5Var.e().D1();
            w0A = o5Var.e().a(c0684j2B, new Consumer() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3180v20.a(abstractC3938n2D1, (com.android.tools.r8.graph.W0.a) obj);
                }
            });
        }
        w0A.getAccessFlags().G();
        c2931ra.b(w0A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return o5Var.e().s1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0986Fk c0986Fk, C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        c0986Fk.b(o5Var.getReference(), c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC3938n2 abstractC3938n2, com.android.tools.r8.graph.W0.a aVar) {
        aVar.getClass();
        if (abstractC3938n2.e()) {
            return;
        }
        aVar.a(abstractC3938n2);
    }

    public final void a(AbstractC2151hN abstractC2151hN, C2931ra c2931ra, C0986Fk c0986Fk, MR mr, Consumer consumer) {
        if (!f && this.d.isEmpty()) {
            throw new AssertionError();
        }
        if (c()) {
            a(c2931ra, c0986Fk);
            return;
        }
        C2807pq c2807pq = new C2807pq();
        com.android.tools.r8.graph.o5 o5Var = null;
        Z8 z8 = null;
        for (com.android.tools.r8.graph.o5 o5Var2 : this.d) {
            if (!o5Var2.e().s1()) {
                if (o5Var2.e().p1()) {
                    z8 = (Z8) com.android.tools.r8.utils.structural.s.c(z8, o5Var2.e().Z0());
                }
                c0986Fk.c(o5Var2.getReference(), a(c2931ra, o5Var2));
                c2807pq.a(mr.a(o5Var2.t()), o5Var2.getReference());
                if (o5Var == null) {
                    o5Var = o5Var2;
                }
            }
        }
        if (!f && o5Var == null) {
            throw new AssertionError();
        }
        C0684j2 c0684j2A = this.a.E().a(o5Var.getReference());
        C0722p1 c0722p1 = this.b;
        String str = c0684j2A.C0().r0() + "$bridge";
        C0737r2 c0737r2 = c0684j2A.h;
        C0767v2 c0767v2B0 = c0684j2A.B0();
        Objects.requireNonNull(c2931ra);
        C0684j2 c0684j2A2 = c0722p1.a(c0737r2, c0767v2B0, str, new Wn$$ExternalSyntheticLambda1(c2931ra));
        final com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0684j2 reference = ((com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.d)).getReference();
        C0729q2 c0729q2F = this.c.f();
        C0722p1 c0722p2 = this.b;
        reference.getClass();
        C0684j2 c0684j2B = reference.b(c0729q2F.C(), c0722p2);
        com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.Y1().a(c0684j2B).a(a()).a(new C1561Zm(this.c.a(), c2807pq, c0684j2A, this.e)).a(z8).b(w0E.I0()).a(w0E.Y0()).a();
        if (!o5Var.e().D1().e()) {
            w0A.a(o5Var.e().D1());
        }
        C3407y20 c3407y20 = new C3407y20(o5Var.getReference());
        for (com.android.tools.r8.graph.o5 o5Var3 : this.d) {
            c0986Fk.b(o5Var3.getReference(), c0684j2B);
            c3407y20.a(this.a.a(o5Var3));
        }
        if (this.e != null) {
            c3407y20.a().x();
        }
        c0986Fk.c(c0684j2A2, c0684j2B);
        if (!abstractC2151hN.b()) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                abstractC2151hN.a(((com.android.tools.r8.graph.o5) it.next()).getReference(), new Consumer() { // from class: com.android.tools.r8.internal.v20$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C3180v20.a(w0E, (InterfaceC1997fN) obj);
                    }
                });
            }
        }
        c2931ra.b(w0A);
        if (c3407y20.a().j()) {
            return;
        }
        consumer.accept(c3407y20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.W0 w0, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.a((AbstractC0744s2) w0.getReference());
    }
}
