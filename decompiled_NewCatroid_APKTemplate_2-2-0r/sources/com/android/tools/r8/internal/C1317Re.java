package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.synthesis.AbstractC3847f;
import com.android.tools.r8.synthesis.C3851i;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Re, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1317Re implements N6 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C0948Ee c;
    private final C3518ze d;

    public C1317Re(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = new C0948Ee(c0702m);
        this.d = new C3518ze(c0702m, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(C0753u c0753u, C0684j2 c0684j2) {
        return c0753u;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.synthesis.P p) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3851i c3851i) {
    }

    public final C3518ze b() {
        return this.d;
    }

    public final boolean b(C0767v2 c0767v2) {
        return this.a.D().a(c0767v2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda22
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.o;
            }
        }) || this.a.D().a(c0767v2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda23
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.p;
            }
        });
    }

    public final boolean a(C0767v2 c0767v2, C0684j2 c0684j2, C0684j2 c0684j3, com.android.tools.r8.graph.o5 o5Var) {
        if (c0767v2.M0()) {
            if (e || c0684j2 == null) {
                return a(c0767v2.b(this.a.a()), c0684j2, c0684j3, o5Var);
            }
            throw new AssertionError();
        }
        if (c0684j2 != null) {
            return true;
        }
        C0702m c0702m = this.a;
        if (!c0702m.t.a(c0702m, c0767v2)) {
            return false;
        }
        if (this.a.Y().C1.l().containsKey(c0767v2) || this.a.Y().C1.A().containsKey(c0767v2)) {
            return true;
        }
        a(c0767v2, c0684j3, o5Var);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(final S6 s6, final C1150Lb c1150Lb, C0729q2 c0729q2, Iterable iterable) {
        final C0729q2 c0729q2A = a(c0729q2, EnumC1290Qe.a);
        com.android.tools.r8.graph.T0 t0A = a(c0729q2A);
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(s6, c1150Lb, c0729q2A, (C0684j2) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        };
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add((com.android.tools.r8.graph.W0) biFunction.apply((C0684j2) it.next(), t0A.getReference()));
        }
        c0729q2A.b((Collection) arrayList);
        c0729q2A.b((Collection) AbstractC1639am.a(this.d.b(t0A.getReference()), this.d.a(t0A.getReference())));
        C0729q2 c0729q2A2 = a(c0729q2, EnumC1290Qe.b);
        com.android.tools.r8.graph.T0 t0A2 = a(c0729q2A2);
        BiFunction biFunction2 = new BiFunction() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.b(s6, c1150Lb, c0729q2A, (C0684j2) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        };
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = iterable.iterator();
        while (it2.hasNext()) {
            arrayList2.add((com.android.tools.r8.graph.W0) biFunction2.apply((C0684j2) it2.next(), t0A2.getReference()));
        }
        c0729q2A2.b((Collection) arrayList2);
        c0729q2A2.b((Collection) AbstractC1639am.a(this.d.b(t0A2.getReference()), this.d.a(t0A2.getReference())));
    }

    private C0684j2 a(C0684j2 c0684j2, final InterfaceC1398Ue interfaceC1398Ue) {
        if (this.a.g().a(c0684j2, false).x()) {
            return c0684j2;
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        C0759u2 c0759u2C0 = c0684j2.C0();
        C0737r2 c0737r2H0 = c0684j2.H0();
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.f;
            }
        };
        C0767v2 c0767v2B0 = c0684j2.B0();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1317Re.a((C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC1398Ue);
        com.android.tools.r8.graph.S sA = D.a(c0759u2C0, c0737r2H0, c, c0767v2B0, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1398Ue.b((C0754u0) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1317Re.a((com.android.tools.r8.synthesis.H) obj);
            }
        });
        if (e || sA.getReference() == c0684j2) {
            return c0684j2;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W0 b(S6 s6, final C1150Lb c1150Lb, final C0729q2 c0729q2, C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0) {
        return this.d.a(c0684j2, y0, s6, new Supplier() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return c1150Lb.a(c0729q2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a((com.android.tools.r8.synthesis.G) null);
    }

    public final C0684j2 a(boolean z, InterfaceC1398Ue interfaceC1398Ue, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, C0767v2 c0767v2, C0684j2 c0684j2) {
        C0684j2 c0684j3;
        if (c0684j2 != null) {
            if (e || !c0767v2.M0()) {
                return a(c0684j2, interfaceC1398Ue);
            }
            throw new AssertionError();
        }
        C0767v2 c0767v2A = z ? c0767v2 : C3378xe.a(this.a, c0767v2);
        C0767v2 c0767v2A2 = z ? C3378xe.a(this.a, c0767v2) : c0767v2;
        if (c0767v2.M0()) {
            C0722p1 c0722p1 = this.b;
            C0767v2 c0767v2B = c0767v2.b(c0722p1);
            int iG0 = c0767v2.G0();
            return a(c0767v2A, c0767v2A2, interfaceC1398Ue, o5Var, supplier, a(c0767v2A == c0767v2, interfaceC1398Ue, o5Var, supplier, iG0 <= 1 ? c0767v2B : c0722p1.a(iG0 - 1, c0767v2B), (C0684j2) null));
        }
        C0684j2 c0684j2A = a(c0767v2, c0767v2A, c0767v2A2);
        if (c0684j2A != null) {
            return c0684j2A;
        }
        AbstractC0727q0 abstractC0727q0A = a(c0767v2);
        if (abstractC0727q0A.B1()) {
            return this.c.a(abstractC0727q0A, c0767v2A, c0767v2A2, interfaceC1398Ue);
        }
        boolean z2 = e;
        if (!z2 && !this.a.Y().C1.A().containsKey(c0767v2)) {
            throw new AssertionError(c0767v2);
        }
        C1260Pe c1260PeA = a(abstractC0727q0A, interfaceC1398Ue);
        if (c0767v2 == c0767v2A) {
            c0684j3 = c1260PeA.a;
        } else {
            c0684j3 = c1260PeA.b;
        }
        if (!z2 && c0767v2A != c0684j3.a(0, true)) {
            throw new AssertionError();
        }
        if (z2 || c0767v2A2 == c0684j3.I0()) {
            return c0684j3;
        }
        throw new AssertionError();
    }

    private C0684j2 a(final C0767v2 c0767v2, final C0767v2 c0767v3, InterfaceC1471We interfaceC1471We, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, final C0684j2 c0684j2) {
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda14
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.Q;
            }
        }, (C1314Rb) supplier.get(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0767v3, c0767v2, c0684j2, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        interfaceC1471We.v(o5VarB, o5Var);
        return o5VarB.getReference();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0767v2 c0767v2, final C0767v2 c0767v3, final C0684j2 c0684j2, com.android.tools.r8.synthesis.H h) {
        h.a(this.b.b(c0767v2, c0767v3)).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j3) {
                return this.f$0.a(c0767v3, c0767v2, c0684j2, c0684j3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(C0767v2 c0767v2, C0767v2 c0767v3, C0684j2 c0684j2, C0684j2 c0684j3) {
        return new YH(this.a, c0684j3.B0(), c0767v2, c0767v3, c0684j2).b();
    }

    public final C0684j2 a(boolean z, InterfaceC1445Ve interfaceC1445Ve, com.android.tools.r8.graph.o5 o5Var, Supplier supplier, C0767v2 c0767v2, C0684j2 c0684j2) {
        if (c0684j2 != null) {
            if (e || !c0767v2.M0()) {
                return c0684j2;
            }
            throw new AssertionError();
        }
        C0767v2 c0767v2A = z ? c0767v2 : C3378xe.a(this.a, c0767v2);
        C0767v2 c0767v2A2 = z ? C3378xe.a(this.a, c0767v2) : c0767v2;
        if (c0767v2.M0()) {
            C0722p1 c0722p1 = this.b;
            C0767v2 c0767v2B = c0767v2.b(c0722p1);
            int iG0 = c0767v2.G0();
            return a(c0767v2A, c0767v2A2, interfaceC1445Ve, o5Var, supplier, a(c0767v2A == c0767v2, interfaceC1445Ve, o5Var, supplier, iG0 <= 1 ? c0767v2B : c0722p1.a(iG0 - 1, c0767v2B), (C0684j2) null));
        }
        C0684j2 c0684j2A = a(c0767v2, c0767v2A, c0767v2A2);
        if (c0684j2A != null) {
            return c0684j2A;
        }
        AbstractC0727q0 abstractC0727q0A = a(c0767v2);
        if (abstractC0727q0A.B1()) {
            return this.c.a(abstractC0727q0A, c0767v2A, c0767v2A2);
        }
        boolean z2 = e;
        if (!z2 && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A = a(abstractC0727q0A, EnumC1290Qe.a);
        C0729q2 c0729q2A2 = a(abstractC0727q0A, EnumC1290Qe.b);
        com.android.tools.r8.graph.Y0 reference = a(c0729q2A).getReference();
        com.android.tools.r8.graph.Y0 reference2 = a(c0729q2A2).getReference();
        C0684j2 c0684j2A2 = a(c0729q2A, reference2.h, reference.h);
        C0684j2 c0684j2A3 = a(c0729q2A2, reference.h, reference2.h);
        if (c0767v2 != c0767v2A) {
            c0684j2A2 = c0684j2A3;
        }
        if (z2 || c0767v2A == c0684j2A2.a(0, true)) {
            return c0684j2A2;
        }
        throw new AssertionError();
    }

    private C0684j2 a(C0767v2 c0767v2, C0767v2 c0767v3, C0767v2 c0767v4) {
        C3004sd c3004sd = this.a.Y().C1.l().get(c0767v2);
        if (c3004sd == null) {
            return null;
        }
        C0684j2 c0684j2B = c0767v2 == c0767v3 ? c3004sd.b() : c3004sd.a();
        if (!e && (c0767v2 != c0767v3 ? c0767v2 != c0684j2B.a(0, true) : c0767v2 != c0684j2B.I0())) {
            throw new AssertionError();
        }
        return this.b.a(c0684j2B.B0(), this.b.b(c0767v4, c0767v3), c0684j2B.C0());
    }

    private void a(C0767v2 c0767v2, C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        C0702m c0702m = this.a;
        C0767v2 c0767v2B = c0702m.t.b(c0702m, c0767v2);
        StringDiagnostic stringDiagnostic = new StringDiagnostic("Invoke to " + c0684j2.e + "#" + c0684j2.f + " may not work correctly at runtime (No conversion registered for type " + c0767v2B + ").", o5Var != null ? o5Var.getOrigin() : Origin.unknown(), o5Var != null ? new MethodPosition(o5Var.y()) : Position.UNKNOWN);
        if (!this.a.Y().U0()) {
            this.a.Y().e.info(stringDiagnostic);
        } else {
            this.a.Y().e.a(stringDiagnostic);
            throw null;
        }
    }

    private AbstractC0727q0 a(C0767v2 c0767v2) {
        boolean z = e;
        if (!z && c0767v2.M0()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (!z && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        if (!z && !abstractC0727q0C.f0() && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        if (z || !abstractC0727q0C.e.h() || abstractC0727q0C.B1()) {
            return abstractC0727q0C;
        }
        throw new AssertionError();
    }

    private C1260Pe a(AbstractC0727q0 abstractC0727q0, InterfaceC1398Ue interfaceC1398Ue) {
        boolean z = e;
        if (!z && interfaceC1398Ue == null) {
            throw new AssertionError();
        }
        if (abstractC0727q0.e0()) {
            if (!z && !this.a.Y().C1.d()) {
                throw new AssertionError();
            }
            C0729q2 c0729q2A = a(abstractC0727q0, EnumC1290Qe.a);
            C0729q2 c0729q2A2 = a(abstractC0727q0, EnumC1290Qe.b);
            com.android.tools.r8.graph.Y0 reference = a(c0729q2A).getReference();
            com.android.tools.r8.graph.Y0 reference2 = a(c0729q2A2).getReference();
            return new C1260Pe(a(c0729q2A, reference2.h, reference.h), a(c0729q2A2, reference.h, reference2.h));
        }
        if (!z && !abstractC0727q0.I1()) {
            throw new AssertionError();
        }
        List listA = this.a.Y().C1.A().get(abstractC0727q0.d).a();
        if (!z && listA == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.T tN = abstractC0727q0.N();
        C0767v2 c0767v2 = abstractC0727q0.d;
        C0767v2 c0767v2A = C3378xe.a(this.a, c0767v2);
        EnumC1290Qe enumC1290Qe = EnumC1290Qe.a;
        final C3518ze c3518ze = this.d;
        Objects.requireNonNull(c3518ze);
        C0754u0 c0754u0A = a(enumC1290Qe, c0767v2A, c0767v2, tN, interfaceC1398Ue, listA, new BiFunction() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda11
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return c3518ze.b((C0684j2) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        });
        EnumC1290Qe enumC1290Qe2 = EnumC1290Qe.b;
        final C3518ze c3518ze2 = this.d;
        Objects.requireNonNull(c3518ze2);
        return new C1260Pe(a(c0754u0A, c0767v2A, c0767v2), a(a(enumC1290Qe2, c0767v2, c0767v2A, tN, interfaceC1398Ue, listA, new BiFunction() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda20
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return c3518ze2.a((C0684j2) obj, (com.android.tools.r8.graph.Y0) obj2);
            }
        }), c0767v2, c0767v2A));
    }

    private C0729q2 a(AbstractC0727q0 abstractC0727q0, EnumC1290Qe enumC1290Qe) {
        if (abstractC0727q0.B1()) {
            return this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda24
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.u;
                }
            }, abstractC0727q0, this.a);
        }
        return this.a.D().a(enumC1290Qe.a(), abstractC0727q0, this.a);
    }

    private C0684j2 a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2, C0767v2 c0767v3) {
        C0722p1 c0722p1 = this.b;
        return abstractC0727q0.b(c0722p1.a(abstractC0727q0.d, c0722p1.b(c0767v2, c0767v3), this.b.j0)).getReference();
    }

    private static com.android.tools.r8.graph.T0 a(C0729q2 c0729q2) {
        if (e || c0729q2.w1().size() == 1) {
            return c0729q2.w1().get(0);
        }
        throw new AssertionError();
    }

    private void a(EnumC1290Qe enumC1290Qe, final C0729q2 c0729q2, List list, C0729q2 c0729q3, C0729q2 c0729q4) {
        com.android.tools.r8.graph.W0 next;
        final com.android.tools.r8.graph.Y0 reference = a(c0729q3).getReference();
        final com.android.tools.r8.graph.Y0 reference2 = a(c0729q4).getReference();
        final ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0C = this.a.c((C0767v2) it.next());
            if (!e && abstractC0727q0C == null) {
                throw new AssertionError();
            }
            Iterator<com.android.tools.r8.graph.W0> it2 = a(abstractC0727q0C, enumC1290Qe).V0().iterator();
            do {
                next = it2.next();
            } while (!next.E0());
            if (!e && next.K0() != this.b.j0) {
                throw new AssertionError();
            }
            arrayList.add(next.getReference());
        }
        final C0737r2 c0737r2B = this.b.b(reference2.h, reference.h);
        com.android.tools.r8.synthesis.D D = this.a.D();
        C0759u2 c0759u2 = this.b.j0;
        com.android.tools.r8.synthesis.C cA = enumC1290Qe.a();
        c0729q2.getClass();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1317Re.a((com.android.tools.r8.synthesis.P) obj);
            }
        };
        Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2B, reference, reference2, c0729q2, arrayList, (com.android.tools.r8.synthesis.H) obj);
            }
        };
        D.getClass();
        D.a(c0759u2, c0737r2B, cA, c0729q2, c0702m, consumer, consumer2, C3912j0.b());
    }

    private C0729q2 a(final C0767v2 c0767v2, final C0729q2 c0729q2, final EnumC1290Qe enumC1290Qe, final InterfaceC1445Ve interfaceC1445Ve) {
        boolean z = e;
        if (!z && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        if (!z && interfaceC1445Ve == null) {
            throw new AssertionError();
        }
        final C0767v2 c0767v2A = C3378xe.a(this.a, c0767v2);
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C cA = enumC1290Qe.a();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda26
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(enumC1290Qe, c0767v2, c0767v2A, c0729q2, (com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(interfaceC1445Ve);
        return D.a(cA, c0729q2, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1445Ve.f((C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(EnumC1290Qe enumC1290Qe, C0767v2 c0767v2, C0767v2 c0767v3, C0729q2 c0729q2, com.android.tools.r8.synthesis.P p) {
        enumC1290Qe.getClass();
        EnumC1290Qe enumC1290Qe2 = EnumC1290Qe.a;
        C0767v2 c0767v4 = enumC1290Qe == enumC1290Qe2 ? c0767v3 : c0767v2;
        if (enumC1290Qe != enumC1290Qe2) {
            c0767v2 = c0767v3;
        }
        a(c0767v4, c0767v2, c0729q2.isInterface(), p);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private C0754u0 a(EnumC1290Qe enumC1290Qe, final C0767v2 c0767v2, final C0767v2 c0767v3, final com.android.tools.r8.graph.T t, final InterfaceC1398Ue interfaceC1398Ue, final List list, final BiFunction biFunction) {
        if (!e && interfaceC1398Ue == null) {
            throw new AssertionError();
        }
        com.android.tools.r8.synthesis.D D = this.a.D();
        com.android.tools.r8.synthesis.C cA = enumC1290Qe.a();
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) t;
        abstractC0727q0.getClass();
        com.android.tools.r8.graph.T tN = abstractC0727q0.N();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0767v2, c0767v3, t, list, biFunction, (C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC1398Ue);
        return D.a(cA, tN, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1398Ue.c((C0754u0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0767v2 c0767v2, final C0767v2 c0767v3, com.android.tools.r8.graph.T t, Iterable iterable, BiFunction biFunction, C3851i c3851i) {
        com.android.tools.r8.graph.T0 t0A = a(c0767v2, c0767v3, t.isInterface(), c3851i);
        c3851i.a(new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0767v2, c0767v3, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add((com.android.tools.r8.graph.W0) biFunction.apply((C0684j2) it.next(), t0A.getReference()));
        }
        c3851i.b((AbstractCollection) arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, C0767v2 c0767v3, com.android.tools.r8.synthesis.H h) {
        a(h, this.b.b(c0767v2, c0767v3), (C0753u) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0737r2 c0737r2, com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1, C0729q2 c0729q2, List list, com.android.tools.r8.synthesis.H h) {
        if (!e && !c0729q2.e0()) {
            throw new AssertionError();
        }
        a(h, c0737r2, new C1603aI(this.a, y1, y0, list).b());
    }

    private void a(com.android.tools.r8.synthesis.H h, C0737r2 c0737r2, final C0753u c0753u) {
        h.a(this.b.j0).a(c0737r2).a(C0725p4.a(4105, false)).b().a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1317Re.a(c0753u, c0684j2);
            }
        });
    }

    private com.android.tools.r8.graph.T0 a(C0767v2 c0767v2, C0767v2 c0767v3, boolean z, AbstractC3847f abstractC3847f) {
        final C0767v2 c0767v4 = z ? this.b.Z1 : c0767v2;
        List listSingletonList = z ? Collections.singletonList(c0767v2) : Collections.emptyList();
        C0767v2 c0767v2E = abstractC3847f.e();
        C0722p1 c0722p1 = this.b;
        final com.android.tools.r8.graph.T0 t0A = com.android.tools.r8.graph.T0.d1().a(c0722p1.a(c0767v2E, c0767v3, c0722p1.k0)).a(com.android.tools.r8.graph.Q2.g(4113)).d().a();
        abstractC3847f.b(listSingletonList).a(c0767v4).a(Collections.singletonList(t0A)).a(new Consumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(t0A, c0767v4, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        return t0A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final com.android.tools.r8.graph.T0 t0, final C0767v2 c0767v2, com.android.tools.r8.synthesis.H h) {
        com.android.tools.r8.synthesis.H hA = h.a(this.b.Y0);
        C0722p1 c0722p1 = this.b;
        hA.a(c0722p1.b(c0722p1.D1, t0.getType())).a(C0725p4.a(4098, true)).b().a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda21
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(t0, c0767v2, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(com.android.tools.r8.graph.T0 t0, C0767v2 c0767v2, C0684j2 c0684j2) {
        return new O20(this.a, t0.getReference(), c0767v2).b();
    }

    final com.android.tools.r8.graph.W0 a(C0684j2 c0684j2, C0753u c0753u) {
        com.android.tools.r8.androidapi.h hVarA;
        C0725p4 c0725p4B = C0725p4.b(4097, false);
        if (this.a.q()) {
            hVarA = com.android.tools.r8.androidapi.h.w();
        } else {
            com.android.tools.r8.androidapi.b bVarD = this.a.d();
            C0722p1 c0722p1 = this.b;
            com.android.tools.r8.androidapi.j jVarF = com.android.tools.r8.androidapi.h.f();
            bVarD.getClass();
            hVarA = bVarD.a(c0684j2.a(c0722p1), jVarF);
        }
        com.android.tools.r8.graph.W0.a aVarB = com.android.tools.r8.graph.W0.Y1().a(c0684j2).a(c0725p4B).a(c0753u).b(hVarA);
        if (c0753u == null) {
            hVarA = com.android.tools.r8.androidapi.h.w();
        }
        return aVarB.a(hVarA).a();
    }

    @Override // com.android.tools.r8.internal.N6
    public final String a() {
        return "$wrapper";
    }

    @Override // com.android.tools.r8.internal.N6
    public final void a(final C1150Lb c1150Lb, final S6 s6) {
        C2215iD c2215iD = this.a.Y().C1;
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        c2215iD.A().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda18
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(s6, identityHashMap, (C0767v2) obj, (P20) obj2);
            }
        });
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda19
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(s6, c1150Lb, (C0729q2) obj, (Iterable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(S6 s6, Map map, C0767v2 c0767v2, P20 p20) {
        AbstractC0727q0 abstractC0727q0A = a(c0767v2);
        if (abstractC0727q0A.e0()) {
            if (abstractC0727q0A.B1()) {
                this.c.a(abstractC0727q0A, s6);
                return;
            }
            map.put(abstractC0727q0A.a0(), p20.a());
            List<C0767v2> listB = p20.b();
            boolean z = e;
            if (!z && s6 == null) {
                throw new AssertionError();
            }
            if (!z && !abstractC0727q0A.e0()) {
                throw new AssertionError();
            }
            C0767v2 c0767v3 = abstractC0727q0A.d;
            C0729q2 c0729q2A0 = abstractC0727q0A.a0();
            EnumC1290Qe enumC1290Qe = EnumC1290Qe.a;
            C0729q2 c0729q2A = a(c0767v3, c0729q2A0, enumC1290Qe, s6);
            EnumC1290Qe enumC1290Qe2 = EnumC1290Qe.b;
            C0729q2 c0729q2A2 = a(c0767v3, c0729q2A0, enumC1290Qe2, s6);
            a(enumC1290Qe, c0729q2A0, listB, c0729q2A, c0729q2A2);
            a(enumC1290Qe2, c0729q2A0, listB, c0729q2A2, c0729q2A);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W0 a(S6 s6, final C1150Lb c1150Lb, final C0729q2 c0729q2, C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0) {
        return this.d.b(c0684j2, y0, s6, new Supplier() { // from class: com.android.tools.r8.internal.Re$$ExternalSyntheticLambda25
            @Override // java.util.function.Supplier
            public final Object get() {
                return c1150Lb.a(c0729q2);
            }
        });
    }
}
