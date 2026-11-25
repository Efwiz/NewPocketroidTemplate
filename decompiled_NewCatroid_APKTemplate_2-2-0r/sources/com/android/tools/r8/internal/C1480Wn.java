package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0809y2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1480Wn {
    static final /* synthetic */ boolean i = true;
    private final C0702m a;
    private final CR b;
    private final C0722p1 c;
    private final C2688oE d;
    private final List e;
    private final C1353Sn f;
    private final C0986Fk g;
    private final EnumC0928Dk h;

    C1480Wn(C0702m c0702m, CR cr, C2688oE c2688oE, List list, C0986Fk c0986Fk, EnumC0928Dk enumC0928Dk, C1353Sn c1353Sn) {
        this.a = c0702m;
        this.b = cr;
        this.c = c0702m.a();
        this.d = c2688oE;
        this.e = list;
        this.f = c1353Sn;
        this.g = c0986Fk;
        this.h = enumC0928Dk;
        boolean z = i;
        if (!z && list.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && list.stream().map(new Function() { // from class: com.android.tools.r8.internal.Wn$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((com.android.tools.r8.graph.o5) obj).F();
            }
        }).distinct().count() != 1 && c1353Sn == null) {
            throw new AssertionError();
        }
    }

    private boolean d() {
        return this.e.size() != 1 || this.d.g();
    }

    public final int a() {
        return ((com.android.tools.r8.graph.o5) this.e.iterator().next()).getReference().F0();
    }

    public final List b() {
        return this.e;
    }

    public final int c() {
        return this.e.size();
    }

    private C0684j2 a(boolean z, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2[] c0767v2Arr = o5Var.D().a;
        int iA = com.android.tools.r8.utils.M.a(z) + o5Var.D().size();
        C0767v2[] c0767v2Arr2 = new C0767v2[iA];
        System.arraycopy(c0767v2Arr, 0, c0767v2Arr2, 0, c0767v2Arr.length);
        for (final int i2 = 0; i2 < c0767v2Arr.length; i2++) {
            Set setA = com.android.tools.r8.utils.T2.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.Wn$$ExternalSyntheticLambda7
                @Override // com.android.tools.r8.utils.InterfaceC3877c1
                public final void forEach(Consumer consumer) {
                    this.f$0.a(i2, consumer);
                }
            });
            if (setA.size() > 1) {
                c0767v2Arr2[i2] = AbstractC0809y2.a(setA, this.a);
            }
        }
        if (z) {
            if (!i && com.android.tools.r8.utils.B.b(c0767v2Arr2) != null) {
                throw new AssertionError();
            }
            c0767v2Arr2[iA - 1] = this.c.A1;
        }
        return this.c.a(this.d.f().getType(), c0767v2Arr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final int i2, final Consumer consumer) {
        this.e.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Wn$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1480Wn.a(consumer, i2, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, int i2, com.android.tools.r8.graph.o5 o5Var) {
        consumer.accept(o5Var.b(i2));
    }

    private C0684j2 a(C2931ra c2931ra, C0684j2 c0684j2) {
        C0722p1 c0722p1 = this.c;
        C0737r2 c0737r2H0 = c0684j2.H0();
        C0767v2 c0767v2B0 = c0684j2.B0();
        Objects.requireNonNull(c2931ra);
        return c0722p1.a(c0737r2H0, c0767v2B0, "$r8$init$synthetic", new Wn$$ExternalSyntheticLambda1(c2931ra));
    }

    private C0684j2 a(C2931ra c2931ra, com.android.tools.r8.graph.o5 o5Var) {
        C0722p1 c0722p1 = this.c;
        C0767v2 c0767v2T = o5Var.t();
        C0737r2 c0737r2F = o5Var.F();
        C0767v2 type = this.d.f().getType();
        Objects.requireNonNull(c2931ra);
        C0684j2 c0684j2A = c0722p1.a("$r8$constructor", c0767v2T, c0737r2F, type, new Wn$$ExternalSyntheticLambda1(c2931ra));
        com.android.tools.r8.graph.W0 w0A = o5Var.e().a(c0684j2A);
        w0A.g1().I();
        w0A.getAccessFlags().j0();
        w0A.getAccessFlags().L();
        w0A.getAccessFlags().K();
        w0A.getAccessFlags().A();
        c2931ra.a(w0A);
        return c0684j2A;
    }

    final void a(AbstractC2151hN abstractC2151hN, C2931ra c2931ra, PY py, RY ry) {
        C0725p4 c0725p4P;
        com.android.tools.r8.graph.W c2014fd;
        final com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.e);
        boolean z = this.e.size() > 1 && (this.f == null || this.d.g());
        boolean z2 = i;
        if (!z2 && !this.h.b() && z) {
            throw new AssertionError();
        }
        C0684j2 c0684j2A = a(z, o5Var);
        if (!z2 && !this.h.b() && !c2931ra.a(c0684j2A)) {
            throw new AssertionError();
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        C0722p1 c0722p1 = this.c;
        List listA = this.h.b() ? py.a() : AbstractC1639am.i();
        Objects.requireNonNull(c2931ra);
        C0684j2 c0684j2A2 = c0722p1.a(c0684j2A, listA, new Wn$$ExternalSyntheticLambda1(c2931ra), new Wn$$ExternalSyntheticLambda2(n));
        if (n.c()) {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                abstractC2151hN.a(((com.android.tools.r8.graph.o5) it.next()).getReference(), new Consumer() { // from class: com.android.tools.r8.internal.Wn$$ExternalSyntheticLambda3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C1480Wn.a(n, (InterfaceC1997fN) obj);
                    }
                });
            }
        }
        List listA2 = C3151uh.a(c0684j2A, c0684j2A2);
        if (!i && !this.h.b() && c0684j2A2 != c0684j2A) {
            throw new AssertionError();
        }
        if (this.f != null) {
            C0986Fk c0986Fk = this.g;
            List list = this.e;
            c0986Fk.getClass();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                C0684j2 reference = ((com.android.tools.r8.graph.o5) it2.next()).getReference();
                c0986Fk.b.a(reference, c0684j2A2);
                c0986Fk.a(reference, c0684j2A2, false);
            }
        } else if (!d()) {
            C0986Fk c0986Fk2 = this.g;
            C0684j2 reference2 = o5Var.getReference();
            c0986Fk2.b.a(reference2, c0684j2A2);
            c0986Fk2.a(reference2, c0684j2A2, true);
        } else {
            for (com.android.tools.r8.graph.o5 o5Var2 : this.e) {
                C0684j2 c0684j2A3 = a(c2931ra, o5Var2);
                this.g.b.a(c0684j2A3, c0684j2A3);
                this.g.a(o5Var2.getReference(), c0684j2A3, false);
                abstractC2151hN.a(o5Var2.getReference(), new Consumer() { // from class: com.android.tools.r8.internal.Wn$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        C1480Wn.a(o5Var, (InterfaceC1997fN) obj);
                    }
                });
            }
        }
        C0684j2 c0684j2A4 = a(c2931ra, c0684j2A2);
        if (d()) {
            this.g.a(c0684j2A4, c0684j2A2, true);
        }
        for (com.android.tools.r8.graph.o5 o5Var3 : this.e) {
            ArrayList arrayList = new ArrayList();
            if (z) {
                arrayList.add(new C3008sh(this.b.a(o5Var3.t())));
            }
            arrayList.addAll(listA2);
            this.g.a(o5Var3.getReference(), c0684j2A2, arrayList);
        }
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        boolean z3 = d() || w0E.N0();
        com.android.tools.r8.graph.W0.a aVarA = (z3 ? com.android.tools.r8.graph.W0.Y1() : com.android.tools.r8.graph.W0.S0()).a(c0684j2A2);
        if (z3) {
            c0725p4P = C0725p4.b(4097, true);
        } else {
            c0725p4P = o5Var.getAccessFlags().P();
            c0725p4P.u();
        }
        com.android.tools.r8.graph.W0.a aVarA2 = aVarA.a(c0725p4P);
        int size = listA2.size();
        C1353Sn c1353Sn = this.f;
        Z8 z8 = null;
        if (c1353Sn != null) {
            c2014fd = c1353Sn.a(this.a.E().a((AbstractC1476Wj) null, ((com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.e)).getReference()), c0684j2A4, this.d, z, size);
        } else if (!d()) {
            c2014fd = ((com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.e)).e().a1();
        } else {
            if (!i && this.f != null) {
                throw new AssertionError();
            }
            C2807pq c2807pq = new C2807pq();
            for (com.android.tools.r8.graph.o5 o5Var4 : this.e) {
                c2807pq.a(this.b.a(o5Var4.t()), this.g.a(o5Var4.getReference()));
            }
            c2014fd = new C2014fd(c2807pq, c0684j2A2, this.d.g() ? this.d.a() : null, c0684j2A4);
        }
        com.android.tools.r8.graph.W0.a aVarA3 = aVarA2.a(c2014fd);
        for (com.android.tools.r8.graph.o5 o5Var5 : this.e) {
            if (o5Var5.e().p1()) {
                z8 = (Z8) com.android.tools.r8.utils.structural.s.c(z8, o5Var5.e().Z0());
            }
        }
        com.android.tools.r8.graph.W0 w0A = aVarA3.a(z8).b(w0E.I0()).a(w0E.Y0()).a();
        c2931ra.a(w0A);
        if (this.h.a()) {
            if (this.a.Y().Y0() && !w0A.a1().B0()) {
                ry.b(new com.android.tools.r8.graph.o5(this.d.f(), w0A));
            } else if (!i && !this.a.Y().Y0() && !w0A.a1().A0() && !w0A.a1().E0()) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.N n, InterfaceC1997fN interfaceC1997fN) {
        Set set = (Set) n.a();
        Objects.requireNonNull(interfaceC1997fN);
        set.forEach(new Wn$$ExternalSyntheticLambda0(interfaceC1997fN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.o5 o5Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.getClass();
        interfaceC1997fN.a(o5Var.getReference());
    }
}
