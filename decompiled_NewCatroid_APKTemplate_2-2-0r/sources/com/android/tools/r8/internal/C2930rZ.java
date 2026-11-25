package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3942o1;
import com.android.tools.r8.utils.InterfaceC3947p1;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2930rZ implements A1 {
    private static final O3 h = O3.a();
    static final /* synthetic */ boolean i = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.graph.o5 c;
    private final C3406y2 d;
    private C1166Ls e;
    private Set f = OU.c();
    private Set g = OU.c();

    C2930rZ(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = o5Var;
        this.d = c2409kl.m();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL b(U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL c(U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.i();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(U1 u1, AbstractC3204vL abstractC3204vL) {
        abstractC3204vL.getClass();
        return abstractC3204vL instanceof C2660o10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(int i2, AbstractC3274wL abstractC3274wL) {
        abstractC3274wL.getClass();
        return abstractC3274wL instanceof C2748p10;
    }

    @Override // com.android.tools.r8.internal.A1
    public final AbstractC3327x1 a(Object obj, Object obj2, AbstractC3327x1 abstractC3327x1) {
        return (AbstractC3350xL) abstractC3327x1;
    }

    @Override // com.android.tools.r8.internal.A1
    public final AbstractC3327x1 a(Object obj, C0767v2 c0767v2, Object obj2, Object obj3, AbstractC3327x1 abstractC3327x1) {
        return (AbstractC3350xL) abstractC3327x1;
    }

    @Override // com.android.tools.r8.internal.A1
    public final InterfaceC2998sZ a(AbstractC2412ko abstractC2412ko, AbstractC3350xL abstractC3350xL) {
        AbstractC3350xL abstractC3350xLA;
        if (abstractC2412ko.m1()) {
            C3406y2 c3406y2Q = abstractC2412ko.q();
            S10 s10B = c3406y2Q.b();
            if (a(s10B.w()) && !s10B.C()) {
                this.f.add(s10B);
                this.g.addAll(s10B.a(h));
                abstractC3350xLA = abstractC3350xL.a(c3406y2Q.J2(), AH.c());
            } else {
                abstractC3350xLA = abstractC3350xL.a(c3406y2Q.J2(), C2748p10.c());
            }
            return (c3406y2Q == this.d && abstractC3350xLA.e().a(new InterfaceC3942o1() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.InterfaceC3942o1
                public final boolean a(int i2, Object obj) {
                    return C2930rZ.a(i2, (AbstractC3274wL) obj);
                }
            })) ? new C3227vh() : abstractC3350xLA;
        }
        if (!this.g.contains(abstractC2412ko)) {
            return abstractC3350xL;
        }
        boolean z = i;
        if (!z) {
            abstractC3350xL.getClass();
            if (abstractC3350xL instanceof D5) {
                throw new AssertionError();
            }
        }
        if (!z) {
            abstractC3350xL.getClass();
            if (abstractC3350xL instanceof C2821q10) {
                throw new AssertionError();
            }
        }
        BH bhE = abstractC3350xL.e();
        int iG2 = abstractC2412ko.G2();
        if (iG2 == 9) {
            N3 n3W = abstractC2412ko.w();
            if (n3W.b().C()) {
                bhE = a((AbstractC2412ko) n3W, bhE);
            }
        } else if (iG2 == 10) {
            bhE = a(abstractC2412ko.y(), bhE);
        } else if (iG2 == 25) {
            C1015Gl c1015GlQ = abstractC2412ko.Q();
            if (!c1015GlQ.N2()) {
                bhE = a((AbstractC2412ko) c1015GlQ, bhE);
            } else if (!z) {
                Set set = this.f;
                S10 s10O2 = c1015GlQ.O2();
                O3 o3 = h;
                s10O2.getClass();
                if (!set.contains(s10O2.a(o3, CM.a()))) {
                    throw new AssertionError();
                }
            }
        } else if (iG2 == 28) {
            bhE = a(abstractC2412ko.T(), bhE);
        } else if (iG2 == 30) {
            bhE = a(abstractC2412ko.V(), bhE);
        } else if (iG2 == 38) {
            C1721bt c1721btG0 = abstractC2412ko.g0();
            com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.a.f()).h(c1721btG0.S2()).o();
            if (cVarO == null || cVarO.r().getReference() != this.b.f4.e) {
                bhE = a((AbstractC2412ko) c1721btG0, bhE);
            }
        } else if (iG2 == 40) {
            bhE = a(abstractC2412ko.i0(), bhE);
        } else if (iG2 == 42) {
            bhE = a(abstractC2412ko.m0(), bhE);
        } else if (iG2 == 56) {
            bhE = a(abstractC2412ko.B0(), bhE);
        } else if (iG2 != 33) {
            bhE = iG2 != 34 ? a(abstractC2412ko, bhE) : a(abstractC2412ko.a0(), bhE);
        } else {
            bhE = a(abstractC2412ko.Z(), bhE);
        }
        return bhE != abstractC3350xL ? a(bhE) : bhE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(int i2, AbstractC3274wL abstractC3274wL) {
        abstractC3274wL.getClass();
        return abstractC3274wL instanceof C2748p10;
    }

    private BH a(final C2972s9 c2972s9, BH bh) {
        if (c2972s9.b().C()) {
            return a((AbstractC2412ko) c2972s9, bh);
        }
        return bh.a(c2972s9.e(), new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2930rZ.a(c2972s9, (U1) obj, (AbstractC3204vL) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL a(C2972s9 c2972s9, U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.a(c2972s9.J2());
    }

    private BH a(final C1189Mn c1189Mn, BH bh) {
        if (((C3733i) this.a.f()).c(c1189Mn.getField()).B()) {
            return bh.a(c1189Mn.e(), new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda9
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return C2930rZ.a(c1189Mn, (U1) obj, (AbstractC3204vL) obj2);
                }
            });
        }
        return a((AbstractC2412ko) c1189Mn, bh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL a(C1189Mn c1189Mn, U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.a(c1189Mn.getField());
    }

    private BH a(C1793co c1793co, BH bh) {
        S10 s10Value = c1793co.value();
        O3 o3 = h;
        s10Value.getClass();
        S10 s10A = s10Value.a(o3, CM.a());
        if (a(s10A)) {
            bh = bh.a(s10A);
        }
        S10 s10E = c1793co.e();
        s10E.getClass();
        S10 s10A2 = s10E.a(o3, CM.a());
        if (!a(s10A2)) {
            return bh;
        }
        if (((C3733i) this.a.f()).c(c1793co.getField()).B()) {
            return bh.a(s10A2, new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda11
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return C2930rZ.a((U1) obj, (AbstractC3204vL) obj2);
                }
            });
        }
        return bh.a(s10A2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL a(U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.h();
    }

    private BH a(C1166Ls c1166Ls, BH bh) {
        BH bhA = bh.a(c1166Ls.T2(), new rZ$$ExternalSyntheticLambda5(this));
        S10 s10U2 = c1166Ls.U2();
        O3 o3 = h;
        s10U2.getClass();
        S10 s10A = s10U2.a(o3, CM.a());
        if (!a(s10A)) {
            return bhA;
        }
        if (s10A.Q() && this.c.e().C1()) {
            if (c1166Ls.S2().b(this.b)) {
                com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.a.f()).d(c1166Ls.S2()).o();
                if (cVarO == null) {
                    return bhA.a(s10A);
                }
                if (!cVarO.r().i1().a(c1166Ls).g()) {
                    return bhA.a(s10A);
                }
                C1166Ls c1166Ls2 = this.e;
                if (c1166Ls2 != null && c1166Ls2 != c1166Ls) {
                    return bhA.a(s10A);
                }
                this.e = c1166Ls;
                return bhA;
            }
        }
        return bhA.a(s10A);
    }

    private BH a(final C1248Os c1248Os, BH bh) {
        BH bhA = bh.a(c1248Os.T2(), new rZ$$ExternalSyntheticLambda5(this));
        S10 s10U2 = c1248Os.U2();
        O3 o3 = h;
        s10U2.getClass();
        S10 s10A = s10U2.a(o3, CM.a());
        if (!a(s10A)) {
            return bhA;
        }
        if (((C3733i) this.a.f()).f(c1248Os.S2()).o() == null) {
            return bhA.a(s10A);
        }
        return bhA.a(s10A, new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2930rZ.a(c1248Os, (U1) obj, (AbstractC3204vL) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL a(C1248Os c1248Os, U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.a(c1248Os);
    }

    private BH a(final C2115gt c2115gt, BH bh) {
        BH bhA = bh.a(c2115gt.T2(), new rZ$$ExternalSyntheticLambda5(this));
        S10 s10U2 = c2115gt.U2();
        O3 o3 = h;
        s10U2.getClass();
        S10 s10A = s10U2.a(o3, CM.a());
        if (!a(s10A)) {
            return bhA;
        }
        if (((C3733i) this.a.f()).d(c2115gt.S2()).o() == null) {
            return bhA.a(s10A);
        }
        return bhA.a(s10A, new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda7
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2930rZ.a(c2115gt, (U1) obj, (AbstractC3204vL) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3204vL a(C2115gt c2115gt, U1 u1, AbstractC3204vL abstractC3204vL) {
        return abstractC3204vL.a(c2115gt);
    }

    private static BH a(C3344xF c3344xF, BH bh) {
        return bh.a(c3344xF.e(), new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda10
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2930rZ.b((U1) obj, (AbstractC3204vL) obj2);
            }
        });
    }

    private static BH a(C1616aU c1616aU, BH bh) {
        return bh.a(c1616aU.L2(), new BiFunction() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2930rZ.c((U1) obj, (AbstractC3204vL) obj2);
            }
        });
    }

    private BH a(AbstractC2412ko abstractC2412ko, BH bh) {
        return bh.a(abstractC2412ko.h1(), new rZ$$ExternalSyntheticLambda5(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(S10 s10) {
        if (!i) {
            O3 o3 = h;
            s10.getClass();
            if (s10.a(o3, CM.a()) != s10) {
                throw new AssertionError();
            }
        }
        return s10.H() && this.f.contains(s10);
    }

    private boolean a(AbstractC3472z00 abstractC3472z00) {
        AbstractC0727q0 abstractC0727q0C;
        if (!abstractC3472z00.w() || (abstractC0727q0C = this.a.c(abstractC3472z00.b().R())) == null) {
            return false;
        }
        if (abstractC0727q0C.e0()) {
            C0767v2 c0767v2N1 = abstractC0727q0C.a0().n1();
            while (true) {
                AbstractC0727q0 abstractC0727q0C2 = this.a.c(c0767v2N1);
                if (abstractC0727q0C2 == null) {
                    return false;
                }
                if (!abstractC0727q0C2.e0()) {
                    if (abstractC0727q0C2.getType() != this.b.Z1) {
                        return false;
                    }
                } else {
                    c0767v2N1 = abstractC0727q0C2.n1();
                }
            }
        } else {
            com.android.tools.r8.graph.T tN = abstractC0727q0C.N();
            if (tN.getType() != this.b.Z1 && !tN.isInterface()) {
                return false;
            }
        }
        return true;
    }

    private static InterfaceC2998sZ a(BH bh) {
        final int i2 = 1;
        BH bhA = bh.a(new InterfaceC3947p1() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3947p1
            public final Object a(int i3, Object obj) {
                return C2930rZ.a(i2, i3, (AbstractC3274wL) obj);
            }
        });
        return ((bhA instanceof D5) || (bhA instanceof C2821q10) || !bhA.a(new InterfaceC3942o1() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3942o1
            public final boolean a(int i3, Object obj) {
                return C2930rZ.b(i3, (AbstractC3274wL) obj);
            }
        })) ? bhA : new C3227vh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC3274wL a(int i2, int i3, AbstractC3274wL abstractC3274wL) {
        abstractC3274wL.getClass();
        if (!(abstractC3274wL instanceof C5) && !(abstractC3274wL instanceof C2748p10)) {
            AH ahA = abstractC3274wL.a();
            if (ahA.d() == i2 && ahA.a(new BiPredicate() { // from class: com.android.tools.r8.internal.rZ$$ExternalSyntheticLambda3
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return C2930rZ.d((U1) obj, (AbstractC3204vL) obj2);
                }
            })) {
                return C2748p10.c();
            }
        }
        return abstractC3274wL;
    }
}
