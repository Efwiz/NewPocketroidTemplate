package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0706m3;
import com.android.tools.r8.graph.C0719o4;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.synthesis.C3851i;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1566Zr {
    public static final Z8 c = Z8.g;
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final Predicate b;

    public C1566Zr(C0702m c0702m) {
        this.a = c0702m;
        this.b = a(c0702m);
    }

    public static String a(String str) {
        return AbstractC0402a.a(str, 1, 0) + "$-CC;";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0754u0 c0754u0) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3851i c3851i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C0754u0 c0754u0) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C3851i c3851i) {
    }

    public static boolean d(C0767v2 c0767v2) {
        return c0767v2.e.toString().endsWith("$-CC;");
    }

    final C0767v2 c(C0767v2 c0767v2) {
        return b(this.a.a(), c0767v2);
    }

    final boolean e(C0767v2 c0767v2) {
        return this.a.Y().C1.m().containsKey(c0767v2);
    }

    final boolean f(C0767v2 c0767v2) {
        return this.b.test(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W d(C0684j2 c0684j2) {
        return com.android.tools.r8.graph.L3.H0();
    }

    final C0767v2 b(C0767v2 c0767v2) {
        C2556mg c2556mg = (C2556mg) this.a.Y().C1.m().get(c0767v2);
        if (c2556mg == null) {
            return null;
        }
        return c2556mg.b();
    }

    final com.android.tools.r8.graph.o5 c(final com.android.tools.r8.graph.o5 o5Var, final InterfaceC1720bs interfaceC1720bs) {
        C0684j2 c0684j2A = a(o5Var.getReference(), "$private$", this.a.a());
        final com.android.tools.r8.graph.W0 w0E = o5Var.e();
        return C2569ms.a(o5Var.a(), c0684j2A.C0(), c0684j2A.H0(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.a(w0E, (com.android.tools.r8.synthesis.H) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1720bs.h(o5Var, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    final com.android.tools.r8.graph.o5 d(final com.android.tools.r8.graph.o5 o5Var, final InterfaceC1720bs interfaceC1720bs) {
        if (!d && o5Var.e().u1()) {
            throw new AssertionError();
        }
        if (o5Var.a().p1()) {
            a(o5Var.a(), interfaceC1720bs);
        }
        C0722p1 c0722p1A = this.a.a();
        C0684j2 c0684j2B = o5Var.getReference().b(a(c0722p1A, o5Var.t()), c0722p1A);
        final com.android.tools.r8.graph.W0 w0E = o5Var.e();
        return C2569ms.a(o5Var.a(), c0684j2B.C0(), c0684j2B.H0(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.b(w0E, (com.android.tools.r8.synthesis.H) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1720bs.k(o5Var, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    public static C0767v2 b(C0722p1 c0722p1, C0767v2 c0767v2) {
        if (!d && !c0767v2.e.toString().endsWith("$-CC;")) {
            throw new AssertionError();
        }
        String string = c0767v2.e.toString();
        return c0722p1.e(string.substring(0, string.length() - 5) + ";");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W e(C0684j2 c0684j2) {
        return com.android.tools.r8.graph.L3.H0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W c(C0684j2 c0684j2) {
        return com.android.tools.r8.graph.F5.H0();
    }

    private static boolean c(C0729q2 c0729q2) {
        return c0729q2.p1() && c0729q2.Y().c(new Predicate() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1566Zr.b((com.android.tools.r8.graph.W0) obj);
            }
        });
    }

    final C2404kg b(AbstractC0727q0 abstractC0727q0, AbstractC0748t0 abstractC0748t0) {
        if (abstractC0748t0 == null) {
            return null;
        }
        boolean z = d;
        if (!z && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        if (!(abstractC0748t0 instanceof C0644e4) && !e(abstractC0748t0.t()) && !this.a.Y().C1.o().containsKey(abstractC0748t0.getReference())) {
            return null;
        }
        if (!z && !abstractC0748t0.t().O0()) {
            throw new AssertionError();
        }
        if (!abstractC0748t0.a().isInterface()) {
            abstractC0748t0 = this.a.g().b(abstractC0727q0, abstractC0748t0.getReference());
        }
        if (abstractC0748t0 == null) {
            return null;
        }
        return this.a.Y().C1.a(abstractC0748t0.getReference());
    }

    final C0684j2 b(C2630ne c2630ne) {
        return a(c2630ne, C1797cs.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W b(C0684j2 c0684j2) {
        return com.android.tools.r8.graph.F5.H0();
    }

    final AbstractC0748t0 b(AbstractC0748t0 abstractC0748t0, InterfaceC1720bs interfaceC1720bs) {
        abstractC0748t0.getClass();
        if (abstractC0748t0 instanceof com.android.tools.r8.graph.o5) {
            return d(abstractC0748t0.g0(), interfaceC1720bs);
        }
        com.android.tools.r8.graph.T tN = abstractC0748t0.a().N();
        C0722p1 c0722p1A = this.a.a();
        return a(abstractC0748t0.getReference().b(a(c0722p1A, abstractC0748t0.t()), c0722p1A), tN, this.a);
    }

    final C0684j2 b(AbstractC0748t0 abstractC0748t0) {
        return a(abstractC0748t0.getReference(), "$private$", this.a.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda28
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1566Zr.c(c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.synthesis.H h) {
        C0725p4 c0725p4P = w0.getAccessFlags().P();
        c0725p4P.u();
        h.a(c0725p4P).a(w0.e1()).a(w0.s0()).a(w0.j1()).b().a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1566Zr.e(c0684j2);
            }
        });
    }

    public final com.android.tools.r8.graph.o5 b(com.android.tools.r8.graph.o5 o5Var, InterfaceC1720bs interfaceC1720bs) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        boolean z = d;
        if (!z && !o5Var.a().isInterface()) {
            throw new AssertionError();
        }
        if (!z && !w0E.F1()) {
            throw new AssertionError();
        }
        if (!z && w0E.a1() == null) {
            throw new AssertionError();
        }
        if (!z && com.android.tools.r8.graph.L3.a(w0E.a1())) {
            throw new AssertionError();
        }
        if (w0E.E0()) {
            return d(o5Var, interfaceC1720bs);
        }
        if (w0E.O0()) {
            return c(o5Var, interfaceC1720bs);
        }
        return a(o5Var, interfaceC1720bs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(com.android.tools.r8.graph.W0 w0) {
        return w0.E0() && !w0.u1();
    }

    private static com.android.tools.r8.graph.T0 b(C0729q2 c0729q2) {
        Iterator it = com.android.tools.r8.utils.I1.a(c0729q2.N1(), new Predicate() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1566Zr.a((com.android.tools.r8.graph.T0) obj);
            }
        }).iterator();
        if (it.hasNext()) {
            return (com.android.tools.r8.graph.T0) it.next();
        }
        return null;
    }

    final boolean a(AbstractC0727q0 abstractC0727q0) {
        if (!d && !abstractC0727q0.f0() && !this.a.Y().C1.d()) {
            throw new AssertionError();
        }
        if (e(abstractC0727q0.d) || this.a.Y().C1.r().contains(abstractC0727q0.d)) {
            return true;
        }
        C0702m c0702m = this.a;
        return c0702m.t.a(c0702m, abstractC0727q0.d);
    }

    static boolean a(com.android.tools.r8.graph.W0 w0) {
        boolean z = d;
        if (!z && w0.f.Y()) {
            throw new AssertionError();
        }
        if (!z && w0.f.o()) {
            throw new AssertionError();
        }
        if (w0.f.W()) {
            return false;
        }
        if (!w0.f.Z()) {
            if (w0.f.m()) {
                return true;
            }
            throw new Z00("Non public default interface methods are not yet supported.");
        }
        throw new Z00("Native default interface methods are not yet supported.");
    }

    public final void a(C2630ne c2630ne, com.android.tools.r8.synthesis.C c2) {
        com.android.tools.r8.synthesis.L.b bVarA = c2.a(this.a.D().b());
        if (!d && !c2630ne.a(this.a).equals(bVarA)) {
            throw new AssertionError();
        }
    }

    final C0684j2 a(C0767v2 c0767v2, C2630ne c2630ne) {
        if (!d) {
            a(c2630ne, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda14
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.l;
                }
            });
        }
        return this.a.a().a(c0767v2, this.a.a().c(c2630ne.c()), c2630ne.d());
    }

    final C0684j2 a(C2630ne c2630ne) {
        if (d || c2630ne.a(this.a) == null) {
            return c2630ne.c();
        }
        throw new AssertionError();
    }

    public static C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        if (d || c0767v2.O0()) {
            return c0722p1.d(a(c0767v2.e.toString()));
        }
        throw new AssertionError();
    }

    final C0754u0 a(C0767v2 c0767v2) {
        return this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.h;
            }
        }, c0767v2, this.a, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C3851i) obj).h();
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.a((C0754u0) obj);
            }
        });
    }

    final C2630ne a(AbstractC0727q0 abstractC0727q0, AbstractC0748t0 abstractC0748t0) {
        if (abstractC0748t0 == null) {
            return null;
        }
        C0684j2 c0684j2 = (C0684j2) this.a.Y().C1.o().get(abstractC0748t0.getReference());
        if (c0684j2 != null) {
            return new C2630ne(c0684j2, null);
        }
        C2404kg c2404kgB = b(abstractC0727q0, abstractC0748t0);
        if (c2404kgB == null) {
            return null;
        }
        return c2404kgB.c();
    }

    final C0684j2 a(C2630ne c2630ne, InterfaceC1720bs interfaceC1720bs) {
        if (c2630ne.a(this.a) == null) {
            return c2630ne.c();
        }
        if (!d) {
            a(c2630ne, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.k;
                }
            });
        }
        return a(this.a.g().b(c2630ne.c(), true).c(), interfaceC1720bs).getReference();
    }

    final AbstractC0748t0 a(C2630ne c2630ne, final InterfaceC2632ng interfaceC2632ng) {
        boolean z = d;
        if (!z) {
            a(c2630ne, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda19
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.l;
                }
            });
        }
        AbstractC0748t0 abstractC0748t0C = this.a.g().b(c2630ne.c(), true).c();
        if (!z) {
            a(c2630ne, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda20
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.l;
                }
            });
        }
        abstractC0748t0C.getClass();
        if (abstractC0748t0C instanceof com.android.tools.r8.graph.o5) {
            if (!z && !this.a.Y().C1.d()) {
                throw new AssertionError();
            }
            C0729q2 c0729q2A = this.a.D().a(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda21
                @Override // com.android.tools.r8.synthesis.C
                public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                    return l.l;
                }
            }, abstractC0748t0C.g0().a(), this.a);
            C0684j2 c0684j2A = a(c0729q2A.d, c2630ne);
            if (z || c0729q2A.f(c0684j2A) != null) {
                return c0729q2A.f(c0684j2A);
            }
            throw new AssertionError();
        }
        C0684j2 c0684j2A2 = a(this.a.a().Z1, c2630ne);
        com.android.tools.r8.synthesis.D D = this.a.D();
        C0759u2 c0759u2C0 = c0684j2A2.C0();
        C0737r2 c0737r2H0 = c0684j2A2.H0();
        com.android.tools.r8.synthesis.C c2 = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda23
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.l;
            }
        };
        com.android.tools.r8.graph.T tN = abstractC0748t0C.a().N();
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.a((C3851i) obj);
            }
        };
        Objects.requireNonNull(interfaceC2632ng);
        return D.a(c0759u2C0, c0737r2H0, c2, tN, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC2632ng.d((C0754u0) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda26
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.a((com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda30
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1566Zr.b(c0684j2);
            }
        });
    }

    final AbstractC0748t0 a(AbstractC0748t0 abstractC0748t0) {
        return a(abstractC0748t0, C1797cs.a);
    }

    final AbstractC0748t0 a(AbstractC0748t0 abstractC0748t0, InterfaceC1720bs interfaceC1720bs) {
        abstractC0748t0.getClass();
        if (abstractC0748t0 instanceof com.android.tools.r8.graph.o5) {
            return a(abstractC0748t0.g0(), interfaceC1720bs);
        }
        return a(a(abstractC0748t0.getReference(), "$default$", this.a.a()), abstractC0748t0.a().N(), this.a);
    }

    final com.android.tools.r8.graph.o5 a(final com.android.tools.r8.graph.o5 o5Var, final InterfaceC1720bs interfaceC1720bs) {
        final com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0684j2 c0684j2A = a(o5Var.getReference(), "$default$", this.a.a());
        return C2569ms.a(o5Var.a(), c0684j2A.C0(), c0684j2A.H0(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, w0E, (com.android.tools.r8.synthesis.H) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                interfaceC1720bs.a(o5Var, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.synthesis.H h) {
        C0725p4 c0725p4P = o5Var.getAccessFlags().P();
        c0725p4P.v();
        h.a(c0725p4P).a(C0706m3.g.d()).b().a(w0.s0().a(this.a.a())).a(w0.j1().v0()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1566Zr.a(c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
        return com.android.tools.r8.graph.L3.H0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.synthesis.H h) {
        C0725p4 c0725p4P = w0.getAccessFlags().P();
        if (!d && !c0725p4P.j()) {
            throw new AssertionError();
        }
        c0725p4P.u();
        c0725p4P.v();
        h.a(c0725p4P).a(w0.e1()).a(w0.s0()).b().a(w0.j1()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1566Zr.d(c0684j2);
            }
        });
    }

    private static C0684j2 a(C0684j2 c0684j2, String str, C0722p1 c0722p1) {
        C0767v2[] c0767v2Arr = c0684j2.h.f.a;
        C0767v2[] c0767v2Arr2 = new C0767v2[c0767v2Arr.length + 1];
        c0767v2Arr2[0] = c0684j2.e;
        System.arraycopy(c0767v2Arr, 0, c0767v2Arr2, 1, c0767v2Arr.length);
        return c0722p1.a(a(c0722p1, c0684j2.e), c0722p1.b(c0684j2.h.e, c0767v2Arr2), c0722p1.c(str + c0684j2.f.toString()));
    }

    public static C0684j2 a(C0722p1 c0722p1, C0684j2 c0684j2) {
        return a(c0684j2, "$default$", c0722p1);
    }

    private static com.android.tools.r8.graph.S a(C0684j2 c0684j2, com.android.tools.r8.graph.T t, C0702m c0702m) {
        return c0702m.D().a(c0684j2.C0(), c0684j2.H0(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.k;
            }
        }, t, c0702m, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.b((C3851i) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.b((C0754u0) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.b((com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    private void a(final C0729q2 c0729q2, final InterfaceC1720bs interfaceC1720bs) {
        if (!d && !c(c0729q2)) {
            throw new AssertionError();
        }
        C2569ms.a(c0729q2, this.a.a().Z0, this.a.a().b(this.a.a().D1, new C0767v2[0]), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0729q2, (com.android.tools.r8.synthesis.H) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1566Zr.a(interfaceC1720bs, c0729q2, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(InterfaceC1720bs interfaceC1720bs, C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        interfaceC1720bs.l(c0729q2.X1(), o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.T0 t0) {
        return (t0.O0() || t0.X0().i()) ? false : true;
    }

    private com.android.tools.r8.graph.T0 a(final C0729q2 c0729q2) {
        C0722p1 c0722p1A = this.a.a();
        com.android.tools.r8.graph.T0.a aVarA = com.android.tools.r8.graph.T0.d1().a(c0722p1A.a(c0729q2.getType(), c0722p1A.A1, "$desugar$clinit", new Predicate() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1566Zr.a(c0729q2, (com.android.tools.r8.graph.Y0) obj);
            }
        }));
        int i = com.android.tools.r8.graph.Q2.f;
        return aVarA.a((com.android.tools.r8.graph.Q2) ((com.android.tools.r8.graph.P2) ((com.android.tools.r8.graph.P2) ((com.android.tools.r8.graph.P2) new com.android.tools.r8.graph.P2().c()).d()).e()).a()).a(com.android.tools.r8.graph.B2.g.c).d().a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0729q2 c0729q2, com.android.tools.r8.graph.Y0 y0) {
        return c0729q2.a(y0) == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C0729q2 c0729q2, com.android.tools.r8.synthesis.H h) {
        com.android.tools.r8.synthesis.H hA = h.a((C0725p4) ((C0719o4) ((C0719o4) C0725p4.O().d().e()).f()).a());
        Z8 z8U1 = c0729q2.U1();
        Z8 z8 = c;
        if (!z8U1.e(z8)) {
            z8U1 = z8;
        }
        hA.a(z8U1).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda29
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0729q2, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0729q2 c0729q2, C0684j2 c0684j2) {
        D8 d8;
        if (this.a.k()) {
            return new C0753u(c0684j2.e, 1, 0, AbstractC1639am.a(new C3335x7(c0729q2.getType()), new D8(D8.a.Pop), new C3186v8()));
        }
        com.android.tools.r8.graph.T0 t0B = b(c0729q2);
        if (t0B == null) {
            t0B = a(c0729q2);
            c0729q2.b(t0B);
        }
        boolean zZ0 = t0B.getType().Z0();
        C0767v2 c0767v2 = c0684j2.e;
        int i = zZ0 ? 2 : 1;
        O8 o8 = new O8(t0B.getReference(), t0B.getReference());
        if (zZ0) {
            d8 = new D8(D8.a.Pop2);
        } else {
            d8 = new D8(D8.a.Pop);
        }
        return new C0753u(c0767v2, i, 0, AbstractC1639am.a(o8, d8, new C3186v8()));
    }

    private Predicate a(final C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        final com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        final C0759u2 c0759u2C = c0722p1A.c("$-CC;");
        return new Predicate() { // from class: com.android.tools.r8.internal.Zr$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0702m, c0759u2C, a1Y, (C0767v2) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0702m c0702m, C0759u2 c0759u2, com.android.tools.r8.utils.A1 a1, C0767v2 c0767v2) {
        return c0702m.t.a(c0702m, c0767v2) || c0767v2.D0().b(c0759u2) || this.a.Y().C1.b(c0767v2) || a1.C1.a(c0767v2) || c0702m.u().a(c0767v2);
    }

    public final void a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, C0767v2 c0767v2) {
        if (this.b.test(c0767v2)) {
            return;
        }
        this.a.Y().a(abstractC0727q0, abstractC0727q1, c0767v2);
    }
}
