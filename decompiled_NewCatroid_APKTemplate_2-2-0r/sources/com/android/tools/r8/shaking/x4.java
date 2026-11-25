package com.android.tools.r8.shaking;

import com.android.tools.r8.dex.C0563k$$ExternalSyntheticLambda21;
import com.android.tools.r8.graph.AbstractC0614b5;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0701l4;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0662g4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.G5;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.AbstractC2151hN;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC3304wh;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.IH;
import com.android.tools.r8.internal.InterfaceC1997fN;
import com.android.tools.r8.internal.K4;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda2;
import com.android.tools.r8.internal.Wj$$ExternalSyntheticLambda3;
import com.android.tools.r8.ir.optimize.C3566j0;
import com.android.tools.r8.utils.AbstractC3896g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class x4 {
    static final /* synthetic */ boolean s = true;
    private final AbstractC0681j0 a;
    private final C3733i b;
    private final C0702m c;
    private final com.android.tools.r8.utils.A1 d;
    private final B5 e;
    private final ExecutorService f;
    private final C3566j0 g;
    private final com.android.tools.r8.utils.y3 h;
    private ArrayList i;
    private final com.android.tools.r8.androidapi.b j;
    private final y4 p;
    private final N1 r;
    private final com.android.tools.r8.ir.optimize.info.r k = com.android.tools.r8.ir.optimize.info.r.b();
    private final LinkedHashSet l = new LinkedHashSet();
    private final K4 m = K4.b();
    private final K4 n = K4.b();
    private final Set o = OU.c();
    private final ArrayList q = new ArrayList();

    public x4(com.android.tools.r8.graph.N2 n2, C0702m c0702m, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        this.a = n2;
        C3733i c3733i = (C3733i) c0702m.f();
        this.b = c3733i;
        this.c = c0702m;
        this.d = c0702m.Y();
        this.r = c3733i.f();
        B5 b5R = c3733i.r();
        this.e = b5R;
        this.f = executorService;
        this.g = new C3566j0(c0702m, b5R);
        this.p = new y4(c0702m.a());
        this.j = c0702m.d();
        this.h = y3Var;
        Collection<C0729q2> collectionE = n2.e();
        c(collectionE);
        b(collectionE);
    }

    private void a(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            AbstractC0744s2 abstractC0744s2 = (AbstractC0744s2) it.next();
            abstractC0744s2.getClass();
            if (abstractC0744s2 instanceof C0767v2) {
                a(abstractC0744s2.w0());
            } else if (abstractC0744s2.x0()) {
                com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2.t0();
                a(y0T0.e);
                a(y0T0.h);
            } else {
                if (!s && !abstractC0744s2.y0()) {
                    throw new AssertionError();
                }
                C0684j2 c0684j2V0 = abstractC0744s2.v0();
                a(c0684j2V0.e);
                a(c0684j2V0.h.e);
                for (C0767v2 c0767v2 : c0684j2V0.h.f.a) {
                    a(c0767v2);
                }
            }
        }
    }

    private void b(Collection collection) {
        C0729q2 c0729q2A;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            C0767v2 c0767v2D = this.e.d(c0729q2.d);
            if (c0767v2D != null && (c0729q2A = C0729q2.a(this.c.c(c0767v2D))) != null && a(c0729q2, c0729q2A, this.o) && a(c0729q2, c0729q2A) && !b(c0729q2, c0729q2A)) {
                this.l.add(c0729q2);
            }
        }
    }

    private void c(Collection collection) {
        ArrayList arrayList = new ArrayList();
        this.b.w().c(new C0563k$$ExternalSyntheticLambda21(arrayList), this.d);
        this.b.w().b(new Wj$$ExternalSyntheticLambda2(arrayList), this.d);
        this.b.w().a(new Wj$$ExternalSyntheticLambda3(arrayList), this.d);
        a(arrayList);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Iterator<com.android.tools.r8.graph.W0> it2 = c0729q2.M1().iterator();
            while (it2.hasNext()) {
                if (it2.next().f.Z()) {
                    a(c0729q2.d);
                }
            }
        }
        Iterator it3 = this.b.C().iterator();
        while (it3.hasNext()) {
            a(((C0684j2) it3.next()).e);
        }
        a(this.b.u());
    }

    private void a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C;
        C0767v2 c0767v2B = c0767v2.b(this.c.a());
        if (!c0767v2B.O0() || this.b.a((AbstractC0744s2) c0767v2B) || (abstractC0727q0C = this.b.c(c0767v2B)) == null || !abstractC0727q0C.e0()) {
            return;
        }
        this.o.add(c0767v2B);
    }

    private boolean b(C0729q2 c0729q2, C0729q2 c0729q3) {
        com.android.tools.r8.graph.W0 w0D;
        char c;
        if (c0729q2.d.a(c0729q3.d)) {
            char c2 = 2;
            if (c0729q2.e.j()) {
                c = 0;
            } else {
                c = c0729q2.J1() ? (char) 2 : (char) 1;
            }
            if (c0729q3.e.j()) {
                c2 = 0;
            } else if (!c0729q3.J1()) {
                c2 = 1;
            }
            return c > c2;
        }
        if (!c0729q3.J1()) {
            return true;
        }
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.W0()) {
            if (!t0.getAccessFlags().m() && !t0.O0()) {
                return true;
            }
        }
        for (com.android.tools.r8.graph.W0 w0 : c0729q2.M1()) {
            if (!w0.getAccessFlags().m() && !w0.O0()) {
                return true;
            }
            if (w0.getAccessFlags().m() && (w0D = c0729q3.d(w0.getReference())) != null && !w0D.getAccessFlags().m()) {
                return true;
            }
        }
        return c0729q2.d(new Function() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((o5) obj);
            }
        }).f();
    }

    private boolean c(C0729q2 c0729q2, C0729q2 c0729q3) {
        Iterator<com.android.tools.r8.graph.W0> it = c0729q2.P1().iterator();
        while (it.hasNext()) {
            if (c0729q3.b(it.next().getReference()) != null) {
                return true;
            }
        }
        if (!c0729q2.isInterface() || c0729q3.isInterface()) {
            return false;
        }
        ArrayList<com.android.tools.r8.graph.W0> arrayList = new ArrayList();
        for (com.android.tools.r8.graph.W0 w0 : c0729q2.P1()) {
            if (!w0.f.W()) {
                arrayList.add(w0);
            }
        }
        for (final com.android.tools.r8.graph.W0 w1 : arrayList) {
            AbstractC0701l4.a aVarA = this.b.b(w1.J0(), w1.getReference()).e(c0729q3, this.c).a();
            if (!s && aVarA == null) {
                throw new AssertionError();
            }
            if (aVarA == null) {
                return true;
            }
            if (aVarA.a(w1)) {
                final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N(Boolean.FALSE);
                aVarA.a(new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        x4.a(w1, n, (InterfaceC0669h4) obj);
                    }
                }, new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        x4.a((C0662g4) obj);
                    }
                });
                if (((Boolean) n.a()).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(C0729q2 c0729q2, final C0729q2 c0729q3, Set set) {
        boolean z = s;
        if (!z && c0729q3 == null) {
            throw new AssertionError();
        }
        AbstractC0614b5 abstractC0614b5Z = this.b.z();
        if (!abstractC0614b5Z.c(c0729q2) && !abstractC0614b5Z.d(c0729q2) && !abstractC0614b5Z.b(c0729q2) && !this.b.g(c0729q2) && !set.contains(c0729q2.d) && !this.b.m(c0729q2.d)) {
            if (!z) {
                Stream streamA = AbstractC1699bX.a(AbstractC0984Fi.a(c0729q2.W0(), c0729q2.M1()));
                final C3733i c3733i = this.b;
                Objects.requireNonNull(c3733i);
                if (!streamA.noneMatch(new Predicate() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return c3733i.b((com.android.tools.r8.graph.U0) obj);
                    }
                })) {
                    throw new AssertionError();
                }
            }
            if (!AbstractC3304wh.a(c0729q2, c0729q3, this.c)) {
                return false;
            }
            if ((this.c.j().a().contains(c0729q2.d) && this.b.g(c0729q3)) || c0729q2.e.T()) {
                return false;
            }
            if (!c0729q2.isInterface() && c0729q3.e(this.c)) {
                C3733i c3733i2 = this.b;
                if (!c3733i2.c(c0729q2.d, c3733i2.a().u5)) {
                    return false;
                }
            }
            if ((AbstractC2734ot.c(c0729q3.h2()) || !c0729q2.c(new Function() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(c0729q3, (o5) obj);
                }
            }).f()) && c0729q2.b1() == null && c0729q2.e1().isEmpty() && c0729q3.g1() == c0729q2.g1()) {
                return (c0729q2.isInterface() && !c0729q3.isInterface() && c0729q2.d(new Function() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda12
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((o5) obj);
                    }
                }).f()) ? false : true;
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.D3 b(o5 o5Var) {
        r4 r4Var = new r4(this.c, o5Var);
        o5Var.a(r4Var);
        if (((Boolean) r4Var.f()).booleanValue()) {
            return com.android.tools.r8.utils.D3.d();
        }
        return com.android.tools.r8.utils.D3.e();
    }

    public final A4 b() {
        this.h.a("merge");
        G5.b(this.c).a(this.l, new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        });
        this.h.c();
        C2750p20 c2750p20 = new C2750p20(this.m, this.n);
        this.c.a(c2750p20);
        if (c2750p20.c()) {
            return null;
        }
        this.h.a("fixup");
        A4 a4B = new w4(this.c, this.p, c2750p20, this.q).b();
        AbstractC3735i1 abstractC3735i1V = this.c.v();
        abstractC3735i1V.a(new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C3730h1) obj);
            }
        });
        this.h.c();
        boolean z = s;
        if (!z && a4B == null) {
            throw new AssertionError();
        }
        if (!z) {
            a(a4B);
        }
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(this.c);
        abstractC2151hNB.getClass();
        if (!(abstractC2151hNB instanceof IH)) {
            for (final v4 v4Var : this.q) {
                abstractC2151hNB.a(v4Var.e, new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        x4.a(v4Var, (InterfaceC1997fN) obj);
                    }
                });
            }
        }
        abstractC2151hNB.a(this.c);
        this.c.b((DH) a4B);
        abstractC3735i1V.a(new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C3730h1) obj);
            }
        });
        return a4B;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.utils.D3 a(o5 o5Var) {
        s4 s4Var = new s4(this.c, o5Var);
        o5Var.a(s4Var);
        return com.android.tools.r8.utils.D3.a(!((Boolean) s4Var.f()).booleanValue());
    }

    private boolean a(final C0729q2 c0729q2, C0729q2 c0729q3) {
        boolean z;
        boolean z2;
        if (!s && !a(c0729q2, c0729q3, this.o)) {
            throw new AssertionError();
        }
        if (this.m.containsValue(c0729q2.getType())) {
            return false;
        }
        if ((!c0729q2.p1() || !c0729q3.p1()) && !c0729q3.a(this.c, new Predicate() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return x4.a(c0729q2, (C0767v2) obj);
            }
        }) && (!c0729q2.isInterface() || !c0729q2.b(this.c))) {
            if (((C3733i) this.c.f()).j(c0729q2.d)) {
                z = true;
                break;
            }
            Iterator<com.android.tools.r8.graph.W0> it = c0729q2.V0().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                com.android.tools.r8.graph.W0 next = it.next();
                if (next.E0() && next.f.c0()) {
                    z = true;
                    break;
                }
            }
            if (((C3733i) this.c.f()).j(c0729q3.d)) {
                z2 = true;
                break;
            }
            Iterator<com.android.tools.r8.graph.W0> it2 = c0729q3.V0().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z2 = false;
                    break;
                }
                com.android.tools.r8.graph.W0 next2 = it2.next();
                if (next2.E0() && next2.f.c0()) {
                    z2 = true;
                    break;
                }
            }
            if ((z && z2) || c0729q3.b1() != null || !c0729q3.e1().isEmpty() || c(c0729q2, c0729q3)) {
                return false;
            }
            if (c0729q2.d == c0729q3.f) {
                com.android.tools.r8.utils.W0 w0A = com.android.tools.r8.utils.W0.a();
                HashSet hashSet = new HashSet();
                for (C0767v2 c0767v2 : c0729q3.g.a) {
                    Iterator<com.android.tools.r8.graph.T0> it3 = this.b.c(c0767v2).N1().iterator();
                    while (it3.hasNext()) {
                        hashSet.add(w0A.c(it3.next().getReference()));
                    }
                }
                Iterator<com.android.tools.r8.graph.T0> it4 = c0729q2.w1().iterator();
                while (it4.hasNext()) {
                    if (hashSet.contains(w0A.c(it4.next().getReference()))) {
                        return false;
                    }
                }
            }
            if (this.c.Y().a().d()) {
                com.android.tools.r8.androidapi.b bVar = this.j;
                Iterable iterableH0 = c0729q2.H0();
                bVar.getClass();
                com.android.tools.r8.androidapi.h hVarA = AbstractC3896g.a(c0729q2, bVar.a(iterableH0, com.android.tools.r8.androidapi.h.f()));
                com.android.tools.r8.androidapi.b bVar2 = this.j;
                Iterable iterableH1 = c0729q3.H0();
                bVar2.getClass();
                if (!hVarA.equals(AbstractC3896g.a(c0729q3, bVar2.a(iterableH1, com.android.tools.r8.androidapi.h.f())))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C3730h1 c3730h1) {
        for (v4 v4Var : this.q) {
            o5 o5VarA = AbstractC0748t0.a(this.c.b(v4Var.d));
            final o5 o5VarA2 = AbstractC0748t0.a(this.c.b(v4Var.f));
            if (o5VarA != null && o5VarA2 != null) {
                c3730h1.a(o5VarA, new Consumer() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(o5VarA2, (C3754l1) obj);
                    }
                });
            } else if (!s) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0729q2 c0729q2, C0767v2 c0767v2) {
        return c0767v2 == c0729q2.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a() {
        if (this.i == null) {
            this.i = new t4(this).a();
        }
        return this.i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3730h1 c3730h1) {
        c3730h1.a(u5.a().e(this.m.keySet()).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(v4 v4Var, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.a((AbstractC0744s2) v4Var.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(o5 o5Var, C3754l1 c3754l1) {
        c3754l1.a(this.c.a(o5Var).w());
    }

    private void a(A4 a4) {
        if (!s) {
            a4.a(this.b.w(), this.d);
        }
        Iterator<C0729q2> it = this.b.d().iterator();
        while (it.hasNext()) {
            for (com.android.tools.r8.graph.W0 w0 : it.next().M1()) {
                C0684j2 reference = w0.getReference();
                C0684j2 c0684j2A = a4.a((AbstractC1476Wj) null, reference);
                C0684j2 c0684j2C = a4.c((AbstractC1476Wj) null, c0684j2A);
                if (w0.q1() && (w0.a1() instanceof v4)) {
                    C0684j2 c0684j2 = ((v4) w0.a1()).f;
                    C0684j2 c0684j2A2 = a4.a((AbstractC1476Wj) null, c0684j2);
                    boolean z = s;
                    if (!z && c0684j2A != c0684j2A2) {
                        throw new AssertionError();
                    }
                    if (!z && c0684j2 != c0684j2C) {
                        throw new AssertionError();
                    }
                } else if (!s && reference != c0684j2C) {
                    throw new AssertionError();
                }
                boolean z2 = s;
                if (!z2 && this.m.containsKey(reference.h.e)) {
                    throw new AssertionError();
                }
                if (!z2) {
                    Stream stream = Arrays.stream(reference.h.f.a);
                    final K4 k4 = this.m;
                    Objects.requireNonNull(k4);
                    if (!stream.noneMatch(new Predicate() { // from class: com.android.tools.r8.shaking.x4$$ExternalSyntheticLambda9
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return k4.containsKey((C0767v2) obj);
                        }
                    })) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.N n, InterfaceC0669h4 interfaceC0669h4) {
        AbstractC0727q0 abstractC0727q0A;
        if (interfaceC0669h4.e() == w0 || (abstractC0727q0A = interfaceC0669h4.a()) == null || !abstractC0727q0A.isInterface()) {
            return;
        }
        n.a(Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0662g4 c0662g4) {
        if (!s) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(C0729q2 c0729q2) {
        if (this.l.contains(c0729q2)) {
            C0729q2 c0729q2A0 = this.c.c(this.e.d(c0729q2.d)).a0();
            boolean z = s;
            if (!z && !a(c0729q2, c0729q2A0, this.o)) {
                throw new AssertionError();
            }
            if (!z && this.m.containsKey(c0729q2A0.d)) {
                throw new AssertionError();
            }
            if (this.m.containsValue(c0729q2.d) || this.m.containsValue(c0729q2A0.d)) {
                if (!a(c0729q2, c0729q2A0)) {
                    return;
                }
            } else if (!z && !a(c0729q2, c0729q2A0)) {
                throw new AssertionError();
            }
            Object[] objArr = 0;
            if (!new q4(this, c0729q2.d, c0729q2A0.d).b() && this.r.a(c0729q2, c0729q2A0, this.c.D())) {
                p4 p4Var = new p4(this, c0729q2, c0729q2A0);
                try {
                    if (p4Var.a()) {
                        this.p.a(p4Var.c);
                        this.q.addAll(p4Var.d);
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0050, code lost:
    
        if (r0.a(r1, r5, r6, r1.D()) != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0055, code lost:
    
        if ((r0 instanceof com.android.tools.r8.graph.Z) != false) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005b, code lost:
    
        return com.android.tools.r8.utils.D3.e();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.android.tools.r8.utils.D3 a(com.android.tools.r8.graph.C0729q2 r5, com.android.tools.r8.graph.o5 r6) {
        /*
            r4 = this;
            com.android.tools.r8.graph.m r0 = r4.c
            com.android.tools.r8.utils.A1 r0 = r0.Y()
            com.android.tools.r8.utils.A1$i r0 = r0.P0()
            boolean r0 = r0.a
            if (r0 == 0) goto L5c
            com.android.tools.r8.graph.U0 r0 = r6.e()
            com.android.tools.r8.graph.W0 r0 = (com.android.tools.r8.graph.W0) r0
            com.android.tools.r8.graph.W r0 = r0.a1()
            boolean r1 = r0.z0()
            if (r1 == 0) goto L53
            com.android.tools.r8.graph.u r0 = r0.K()
            com.android.tools.r8.graph.m r1 = r4.c
            com.android.tools.r8.shaking.u4 r2 = new com.android.tools.r8.shaking.u4
            com.android.tools.r8.graph.v2 r3 = r6.t()
            r2.<init>(r4, r3, r5)
            java.lang.Iterable r3 = r5.h2()
            java.util.Iterator r3 = r3.iterator()
            java.lang.Object r3 = r3.next()
            com.android.tools.r8.graph.o5 r3 = (com.android.tools.r8.graph.o5) r3
            com.android.tools.r8.ir.optimize.V r0 = r0.a(r6, r1, r2, r3)
            com.android.tools.r8.ir.optimize.V r1 = com.android.tools.r8.ir.optimize.V.c
            if (r0 != r1) goto L44
            goto L5c
        L44:
            com.android.tools.r8.shaking.N1 r0 = r4.r
            com.android.tools.r8.graph.m r1 = r4.c
            com.android.tools.r8.synthesis.D r2 = r1.D()
            boolean r5 = r0.a(r1, r5, r6, r2)
            if (r5 == 0) goto L57
            goto L5c
        L53:
            boolean r5 = r0 instanceof com.android.tools.r8.graph.Z
            if (r5 == 0) goto L5c
        L57:
            com.android.tools.r8.utils.C3 r5 = com.android.tools.r8.utils.D3.e()
            return r5
        L5c:
            com.android.tools.r8.utils.A3 r5 = com.android.tools.r8.utils.D3.d()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.shaking.x4.a(com.android.tools.r8.graph.q2, com.android.tools.r8.graph.o5):com.android.tools.r8.utils.D3");
    }
}
