package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3118uH extends D7 {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final ArrayList b;
    private final ArrayList c;
    private final C3194vG d;
    private final C2114gs e;
    private final C3378xe f;

    C3118uH(C0702m c0702m, com.android.tools.r8.androidapi.b bVar) {
        C3378xe c3378xe;
        ArrayList arrayList = new ArrayList();
        this.b = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.c = arrayList2;
        this.a = c0702m;
        Q1 q1 = c0702m.q() ? new Q1(c0702m.h0()) : null;
        if (q1 != null) {
            arrayList.add(q1);
        }
        if (c0702m.Y().A0.a()) {
            this.d = null;
            this.e = null;
            this.f = null;
            return;
        }
        C3194vG c3194vGA = C3194vG.a(c0702m);
        this.d = c3194vGA;
        C2281j4 c2281j4 = new C2281j4(c0702m);
        C1006Ge c1006Ge = c0702m.Y().C1.C() ? new C1006Ge(c0702m) : null;
        if (c1006Ge != null) {
            arrayList.add(c1006Ge);
        }
        C0854Be c0854BeA = C0854Be.a(c0702m);
        if (c0854BeA != null) {
            arrayList.add(c0854BeA);
        }
        if (c0702m.Y().a().e) {
            arrayList2.add(new C2661o2(c0702m, bVar));
        }
        if (c0702m.Y().p0()) {
            arrayList.add(new BZ(c0702m));
        }
        EQ eqA = EQ.a(c0702m);
        if (eqA != null) {
            arrayList.add(eqA);
        }
        WX wx = new WX(c0702m);
        arrayList.add(wx);
        NA na = new NA(c0702m);
        arrayList.add(na);
        C2114gs c2114gsA = C2114gs.a(c0702m, com.android.tools.r8.utils.T2.c(q1, c2281j4, c1006Ge), com.android.tools.r8.utils.T2.c(na, wx, eqA));
        this.e = c2114gsA;
        if (c2114gsA != null) {
            arrayList.add(c2114gsA);
        }
        if (c0702m.t.a()) {
            c3378xe = new C3378xe(c0702m, com.android.tools.r8.utils.T2.c(c2114gsA, c1006Ge, c2281j4), c2114gsA != null ? c2114gsA.a() : AbstractC1048Hm.k());
        } else {
            c3378xe = null;
        }
        this.f = c3378xe;
        if (c3378xe != null) {
            arrayList.add(c3378xe);
        }
        arrayList.add(new C1151Lc(c0702m));
        arrayList.add(new C1541Ys(c0702m));
        if (c0702m.Y().X0()) {
            if (!g && c3194vGA == null) {
                throw new AssertionError();
            }
            arrayList.add(new C1870dt());
        }
        if (c0702m.Y().B1()) {
            arrayList.add(new N5(c0702m));
        }
        if (c2281j4.a()) {
            arrayList.add(c2281j4);
        }
        if (c3194vGA != null) {
            arrayList.add(c3194vGA);
        }
        C2044g20 c2044g20A = C2044g20.a(c0702m);
        if (c2044g20A != null) {
            arrayList.add(c2044g20A);
        }
        arrayList2.add(new F10(c0702m));
    }

    static C3118uH a(C0702m c0702m) {
        boolean z = g;
        if (!z && !c0702m.Y().A0.a()) {
            throw new AssertionError();
        }
        if (!z && !(c0702m.Y().f instanceof ClassFileConsumer)) {
            throw new AssertionError();
        }
        C3118uH c3118uH = new C3118uH(c0702m, new com.android.tools.r8.androidapi.c());
        c3118uH.b.add(new C1541Ys(c0702m));
        return c3118uH;
    }

    static C3118uH b(C0702m c0702m) {
        boolean z = g;
        if (!z && !c0702m.Y().A0.a()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.Y().Y0()) {
            throw new AssertionError();
        }
        C3118uH c3118uH = new C3118uH(c0702m, new com.android.tools.r8.androidapi.c());
        c3118uH.b.add(new C1541Ys(c0702m));
        c3118uH.c.add(new F10(c0702m));
        return c3118uH;
    }

    private void b(com.android.tools.r8.graph.o5 o5Var) {
        boolean z = g;
        if (!z && !this.b.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3118uH.a((C7) obj);
            }
        })) {
            throw new AssertionError("Expected code to be desugared");
        }
        if (!z && !a(o5Var)) {
            throw new AssertionError();
        }
        boolean z2 = false;
        for (B7 b7 : o5Var.e().a1().K().I0()) {
            Iterator it = AbstractC2734ot.c(this.b, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return C3118uH.b((C7) obj);
                }
            }).iterator();
            while (it.hasNext()) {
                if (((C7) it.next()).a(o5Var, b7).b()) {
                    z2 = true;
                }
            }
            for (C7 c7 : AbstractC2734ot.c(this.b, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda9
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return ((C7) obj).b();
                }
            })) {
                if (!g && c7.a(o5Var, b7).b()) {
                    throw new AssertionError();
                }
            }
        }
        if (!g && !z2) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.D7
    public final void a(final C1230Ob c1230Ob, final com.android.tools.r8.graph.o5 o5Var, final G7 g7) {
        if (!o5Var.e().a1().z0()) {
            this.a.Y().e.error(new StringDiagnostic("Unsupported attempt to desugar non-CF code", o5Var.getOrigin(), MethodPosition.create(o5Var)));
        }
        final C0753u c0753uK = o5Var.e().a1().K();
        final C3932m1 c3932m1 = new C3932m1(c0753uK.K0());
        final C3932m1 c3932m2 = new C3932m1(c0753uK.K0());
        final C3932m1 c3932m3 = new C3932m1(c0753uK.L0());
        final C3932m1 c3932m4 = new C3932m1(c0753uK.L0());
        List<B7> listB = com.android.tools.r8.utils.V1.b(c0753uK.I0(), new Function() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c3932m2, c3932m4, g7, o5Var, c1230Ob, c3932m1, c0753uK, c3932m3, (B7) obj);
            }
        });
        if (listB != null) {
            boolean z = g;
            if (!z && c3932m1.a() < c0753uK.K0()) {
                throw new AssertionError();
            }
            if (!z && c3932m3.a() < c0753uK.L0()) {
                throw new AssertionError();
            }
            c0753uK.a(listB);
            c0753uK.m(c3932m1.a());
            c0753uK.n(c3932m3.a());
            return;
        }
        if (g) {
            return;
        }
        b(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C7 c7) {
        return !c7.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public boolean a(final com.android.tools.r8.graph.o5 o5Var, final B7 b7) {
        return AbstractC2734ot.b(this.b, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3118uH.b(b7, o5Var, (C7) obj);
            }
        }) || AbstractC2734ot.b(this.c, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C3118uH.a(b7, o5Var, (C7) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(B7 b7, com.android.tools.r8.graph.o5 o5Var, C7 c7) {
        return c7.a(o5Var, b7).b();
    }

    @Override // com.android.tools.r8.internal.D7
    public final void a(final G7 g7, final C3124uN c3124uN, final com.android.tools.r8.graph.o5 o5Var) {
        if (!o5Var.e().a1().z0()) {
            this.a.Y().e.error(new StringDiagnostic("Unsupported attempt to desugar non-CF code", o5Var.getOrigin(), MethodPosition.create(o5Var)));
        }
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C7 c7 = (C7) obj;
                c7.a(g7, c3124uN, o5Var);
            }
        });
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C7 c7 = (C7) obj;
                c7.a(g7, c3124uN, o5Var);
            }
        });
    }

    @Override // com.android.tools.r8.internal.D7
    public final void a(final com.android.tools.r8.graph.o5 o5Var, final G7 g7) {
        if (!o5Var.e().a1().z0()) {
            this.a.Y().e.error(new StringDiagnostic("Unsupported attempt to desugar non-CF code", o5Var.getOrigin(), MethodPosition.create(o5Var)));
        }
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C7) obj).a(o5Var, g7);
            }
        });
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C7) obj).a(o5Var, g7);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(C3932m1 c3932m1, C3932m1 c3932m2, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, C3932m1 c3932m3, C0753u c0753u, C3932m1 c3932m4, B7 b7) {
        Objects.requireNonNull(c3932m1);
        PA$$ExternalSyntheticLambda0 pA$$ExternalSyntheticLambda0 = new PA$$ExternalSyntheticLambda0(c3932m1);
        Objects.requireNonNull(c3932m2);
        Collection collectionA = a(b7, pA$$ExternalSyntheticLambda0, new PA$$ExternalSyntheticLambda1(c3932m2), g7, o5Var, c1230Ob);
        if (collectionA != null) {
            int iC = c3932m1.c(c0753u.K0());
            if (iC > c3932m3.a()) {
                c3932m3.e(iC);
            }
            int iC2 = c3932m2.c(c0753u.L0());
            if (iC2 > c3932m4.a()) {
                c3932m4.e(iC2);
            }
        } else {
            boolean z = g;
            if (!z && c3932m1.a() != c0753u.K0()) {
                throw new AssertionError();
            }
            if (!z && c3932m2.a() != c0753u.L0()) {
                throw new AssertionError();
            }
        }
        return collectionA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C7 c7) {
        return !c7.b();
    }

    @Override // com.android.tools.r8.internal.D7
    public final Collection a(B7 b7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        Collection collectionA;
        Iterator it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                collectionA = null;
                break;
            }
            C7 c7 = (C7) it.next();
            collectionA = c7.a(o5Var, b7).a(interfaceC1374Ti, ac, g7, o5Var, c1230Ob, this, this.a.a());
            if (collectionA != null) {
                if (!g) {
                    a(b7, o5Var, it, c7);
                    break;
                }
                break;
            }
        }
        if (collectionA != null) {
            return collectionA;
        }
        Iterator it2 = this.c.iterator();
        while (it2.hasNext()) {
            C7 c8 = (C7) it2.next();
            Collection collectionA2 = c8.a(o5Var, b7).a(interfaceC1374Ti, ac, g7, o5Var, c1230Ob, this, this.a.a());
            if (collectionA2 != null) {
                if (!g) {
                    a(b7, o5Var, it2, c8);
                }
                return collectionA2;
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.D7
    public final boolean a(final com.android.tools.r8.graph.o5 o5Var) {
        if (!o5Var.e().q1()) {
            return false;
        }
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        if (wA1.B0()) {
            return false;
        }
        if (wA1.z0()) {
            return AbstractC2734ot.b(wA1.K().I0(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda11
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return this.f$0.a(o5Var, (B7) obj);
                }
            });
        }
        throw new C3405y10("Unexpected attempt to determine if non-CF code needs desugaring");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(B7 b7, com.android.tools.r8.graph.o5 o5Var, C7 c7) {
        return c7.a(o5Var, b7).b();
    }

    private static void a(final B7 b7, final com.android.tools.r8.graph.o5 o5Var, Iterator it, final C7 c7) {
        it.forEachRemaining(new Consumer() { // from class: com.android.tools.r8.internal.uH$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3118uH.a(b7, o5Var, c7, (C7) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(B7 b7, com.android.tools.r8.graph.o5 o5Var, C7 c7, C7 c8) {
        boolean zB = c8.a(o5Var, b7).b();
        if (g || !zB) {
            return;
        }
        if ((c7 instanceof C2114gs) && ((c8 instanceof C1870dt) || (c8 instanceof C3194vG))) {
            return;
        }
        if ((c7 instanceof BZ) && (c8 instanceof C2114gs)) {
            return;
        }
        throw new AssertionError("Desugaring of " + b7 + " in method " + o5Var.w() + " has multiple matches: " + c7.getClass().getName() + " and " + c8.getClass().getName());
    }

    @Override // com.android.tools.r8.internal.D7
    public final void a(InterfaceC3905h3 interfaceC3905h3) throws Throwable {
        C3194vG c3194vG = this.d;
        if (c3194vG != null) {
            if (!g && !(c3194vG instanceof C0919Dd)) {
                throw new AssertionError();
            }
            interfaceC3905h3.accept((C0919Dd) c3194vG);
        }
    }

    @Override // com.android.tools.r8.internal.D7
    public final C1952es a(C2569ms c2569ms) {
        C2114gs c2114gs = this.e;
        if (c2114gs != null) {
            return c2114gs.a(c2569ms);
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.D7
    public final C1952es a(Predicate predicate, C2569ms c2569ms) {
        C2114gs c2114gs = this.e;
        if (c2114gs != null) {
            return c2114gs.a(predicate, c2569ms);
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.D7
    public final void a(Consumer consumer) {
        C3378xe c3378xe = this.f;
        if (c3378xe != null) {
            consumer.accept(c3378xe);
        }
    }
}
