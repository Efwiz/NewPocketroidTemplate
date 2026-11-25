package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZX {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C0722p1 b;

    public ZX(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer b(C0759u2 c0759u2) {
        return Integer.valueOf(c0759u2.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer c(C0759u2 c0759u2) {
        return Integer.valueOf(c0759u2.d == 0 ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer d(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().compareToIgnoreCase(c0759u3.toString()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer e(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().contains(c0759u3.toString()) ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Integer f(C0759u2 c0759u2, C0759u2 c0759u3) {
        c0759u2.getClass();
        return Integer.valueOf(c0759u2.a(c0759u3.e) ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer g(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.b(c0759u3) ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer h(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.equals(c0759u3) ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer i(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().equalsIgnoreCase(c0759u3.toString()) ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer j(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().contentEquals(c0759u3.toString()) ? 1 : 0);
    }

    /* JADX WARN: Code duplicated, block: B:205:0x0250 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:242:0x0014 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    public final void a(C2409kl c2409kl) {
        S10 s10M;
        AbstractC2412ko abstractC2412ko;
        S10 s10M2;
        Function function;
        BiFunction biFunction;
        BiFunction biFunction2;
        AbstractC2412ko abstractC2412ko2;
        C0978Fc c0978FcA;
        BiFunction biFunction3;
        BiFunction biFunction4;
        if (c2409kl.t().h()) {
            Set setC = OU.c();
            C2561ml c2561ml = new C2561ml(c2409kl);
            while (c2561ml.hasNext()) {
                AbstractC2412ko abstractC2412ko3 = (AbstractC2412ko) c2561ml.next();
                if (abstractC2412ko3.f2()) {
                    C2115gt c2115gtI0 = abstractC2412ko3.i0();
                    if (c2115gtI0.d1()) {
                        C0684j2 c0684j2S2 = c2115gtI0.S2();
                        if (c0684j2S2.B0() != this.b.X1) {
                            continue;
                        } else {
                            C0759u2 c0759u2C0 = c0684j2S2.C0();
                            C0722p1 c0722p1 = this.b;
                            if (c0759u2C0 != c0722p1.e0) {
                                C0722p1.c cVar = c0722p1.k4;
                                if (c0684j2S2 == cVar.u) {
                                    S10 s10M3 = c2115gtI0.U2().m();
                                    if (!s10M3.A() && !s10M3.i()) {
                                        AbstractC2412ko abstractC2412ko4 = s10M3.b;
                                        abstractC2412ko4.getClass();
                                        if (abstractC2412ko4 instanceof C1033Hc) {
                                            C0759u2 c0759u2C = this.b.c(s10M3.b.F().J2().toString().trim());
                                            S10 s10A = c2409kl.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) this.a, WH.b()));
                                            setC.addAll(c2115gtI0.b().a());
                                            c2561ml.a(new C1033Hc(s10A, c0759u2C), (Set) null);
                                        }
                                    }
                                } else {
                                    if (c0684j2S2 == cVar.r) {
                                        function = new Function() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ZX.a((C0759u2) obj);
                                            }
                                        };
                                    } else if (c0684j2S2 == cVar.c) {
                                        function = new Function() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda11
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ZX.b((C0759u2) obj);
                                            }
                                        };
                                    } else if (c0684j2S2 == cVar.b) {
                                        function = new Function() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda12
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return ZX.c((C0759u2) obj);
                                            }
                                        };
                                    } else {
                                        if (c0684j2S2 == cVar.f) {
                                            biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda13
                                                @Override // java.util.function.BiFunction
                                                public final Object apply(Object obj, Object obj2) {
                                                    return ZX.e((C0759u2) obj, (C0759u2) obj2);
                                                }
                                            };
                                        } else if (c0684j2S2 == cVar.g) {
                                            biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda14
                                                @Override // java.util.function.BiFunction
                                                public final Object apply(Object obj, Object obj2) {
                                                    return ZX.f((C0759u2) obj, (C0759u2) obj2);
                                                }
                                            };
                                        } else if (c0684j2S2 == cVar.h) {
                                            biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda1
                                                @Override // java.util.function.BiFunction
                                                public final Object apply(Object obj, Object obj2) {
                                                    return ZX.g((C0759u2) obj, (C0759u2) obj2);
                                                }
                                            };
                                        } else if (c0684j2S2 == cVar.i) {
                                            biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda2
                                                @Override // java.util.function.BiFunction
                                                public final Object apply(Object obj, Object obj2) {
                                                    return ZX.h((C0759u2) obj, (C0759u2) obj2);
                                                }
                                            };
                                        } else if (c0684j2S2 == cVar.j) {
                                            biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda3
                                                @Override // java.util.function.BiFunction
                                                public final Object apply(Object obj, Object obj2) {
                                                    return ZX.i((C0759u2) obj, (C0759u2) obj2);
                                                }
                                            };
                                        } else if (c0684j2S2 == cVar.k) {
                                            biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda4
                                                @Override // java.util.function.BiFunction
                                                public final Object apply(Object obj, Object obj2) {
                                                    return ZX.j((C0759u2) obj, (C0759u2) obj2);
                                                }
                                            };
                                        } else {
                                            if (c0684j2S2 == cVar.l) {
                                                biFunction4 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda5
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(Object obj, Object obj2) {
                                                        return ZX.b((C0759u2) obj, (Integer) obj2);
                                                    }
                                                };
                                            } else if (c0684j2S2 == cVar.m) {
                                                biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda6
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(Object obj, Object obj2) {
                                                        return ZX.a((C0759u2) obj, (C0759u2) obj2);
                                                    }
                                                };
                                            } else if (c0684j2S2 == cVar.n) {
                                                biFunction4 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda7
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(Object obj, Object obj2) {
                                                        return ZX.a((C0759u2) obj, (Integer) obj2);
                                                    }
                                                };
                                            } else if (c0684j2S2 == cVar.o) {
                                                biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda8
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(Object obj, Object obj2) {
                                                        return ZX.b((C0759u2) obj, (C0759u2) obj2);
                                                    }
                                                };
                                            } else if (c0684j2S2 == cVar.p) {
                                                biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda9
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(Object obj, Object obj2) {
                                                        return ZX.c((C0759u2) obj, (C0759u2) obj2);
                                                    }
                                                };
                                            } else if (c0684j2S2 == cVar.q) {
                                                biFunction3 = new BiFunction() { // from class: com.android.tools.r8.internal.ZX$$ExternalSyntheticLambda10
                                                    @Override // java.util.function.BiFunction
                                                    public final Object apply(Object obj, Object obj2) {
                                                        return ZX.d((C0759u2) obj, (C0759u2) obj2);
                                                    }
                                                };
                                            } else {
                                                continue;
                                            }
                                            biFunction2 = biFunction4;
                                            function = null;
                                            biFunction = null;
                                            S10 s10M4 = c2115gtI0.U2().m();
                                            abstractC2412ko2 = s10M4.b;
                                            if (abstractC2412ko2 == null && (abstractC2412ko2 instanceof C1033Hc) && !abstractC2412ko2.F().i1() && !s10M4.A()) {
                                                C0759u2 c0759u2J2 = s10M4.b.F().J2();
                                                if (function != null) {
                                                    if (!c && ((ArrayList) c2115gtI0.h1()).size() != 1) {
                                                        throw new AssertionError();
                                                    }
                                                    c0978FcA = c2409kl.a(((Integer) function.apply(c0759u2J2)).intValue(), (com.android.tools.r8.graph.X) null);
                                                } else if (biFunction == null) {
                                                    boolean z = c;
                                                    if (!z && biFunction2 == null) {
                                                        throw new AssertionError();
                                                    }
                                                    if (!z && ((ArrayList) c2115gtI0.h1()).size() != 2) {
                                                        throw new AssertionError();
                                                    }
                                                    S10 s10M5 = ((S10) ((ArrayList) c2115gtI0.h1()).get(1)).m();
                                                    AbstractC2412ko abstractC2412ko5 = s10M5.b;
                                                    if (abstractC2412ko5 != null && abstractC2412ko5.B1() && !s10M5.A()) {
                                                        c0978FcA = c2409kl.a(((Integer) biFunction2.apply(c0759u2J2, Integer.valueOf(s10M5.b.E().N2()))).intValue(), (com.android.tools.r8.graph.X) null);
                                                    }
                                                } else {
                                                    if (!c && ((ArrayList) c2115gtI0.h1()).size() != 2) {
                                                        throw new AssertionError();
                                                    }
                                                    S10 s10M6 = ((S10) ((ArrayList) c2115gtI0.h1()).get(1)).m();
                                                    AbstractC2412ko abstractC2412ko6 = s10M6.b;
                                                    if (abstractC2412ko6 != null && (abstractC2412ko6 instanceof C1033Hc) && !s10M6.A()) {
                                                        c0978FcA = c2409kl.a(((Integer) biFunction.apply(c0759u2J2, s10M6.b.F().J2())).intValue(), (com.android.tools.r8.graph.X) null);
                                                    }
                                                }
                                                c2561ml.a(c0978FcA, (Set) null);
                                            }
                                        }
                                        biFunction = biFunction3;
                                        function = null;
                                        biFunction2 = null;
                                        S10 s10M7 = c2115gtI0.U2().m();
                                        abstractC2412ko2 = s10M7.b;
                                        if (abstractC2412ko2 == null) {
                                            continue;
                                        }
                                    }
                                    biFunction = null;
                                    biFunction2 = null;
                                    S10 s10M8 = c2115gtI0.U2().m();
                                    abstractC2412ko2 = s10M8.b;
                                    if (abstractC2412ko2 == null) {
                                        continue;
                                    }
                                }
                            } else {
                                if (!c && ((ArrayList) c2115gtI0.h1()).size() != 2 && ((ArrayList) c2115gtI0.h1()).size() != 3) {
                                    throw new AssertionError();
                                }
                                S10 s10M9 = c2115gtI0.U2().m();
                                AbstractC2412ko abstractC2412ko7 = s10M9.b;
                                if (abstractC2412ko7 != null && (abstractC2412ko7 instanceof C1033Hc) && !s10M9.A() && (abstractC2412ko = (s10M = ((S10) ((ArrayList) c2115gtI0.h1()).get(1)).m()).b) != null && abstractC2412ko.B1() && !s10M.A()) {
                                    int iN2 = s10M.b.E().N2();
                                    if (((ArrayList) c2115gtI0.h1()).size() == 3) {
                                        s10M2 = ((S10) ((ArrayList) c2115gtI0.h1()).get(2)).m();
                                        AbstractC2412ko abstractC2412ko8 = s10M2.b;
                                        if (abstractC2412ko8 == null || !abstractC2412ko8.B1() || s10M2.A()) {
                                        }
                                    } else {
                                        s10M2 = null;
                                    }
                                    String string = s10M9.b.F().J2().toString();
                                    int length = s10M2 == null ? string.length() : s10M2.b.E().N2();
                                    if (iN2 >= 0 && length <= string.length() && iN2 <= length) {
                                        String strSubstring = string.substring(iN2, length);
                                        S10 s10A2 = c2409kl.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) this.a, WH.b()));
                                        setC.addAll(c2115gtI0.b().a());
                                        c2561ml.a(new C1033Hc(s10A2, this.b.c(strSubstring)), (Set) null);
                                    }
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
            if (setC.isEmpty()) {
                return;
            }
            new C3030t00(this.a, false).a((Iterable) setC);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer b(C0759u2 c0759u2, Integer num) {
        return Integer.valueOf(c0759u2.toString().indexOf(num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer c(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().compareTo(c0759u3.toString()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer b(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().lastIndexOf(c0759u3.toString()));
    }

    public final void b(C2409kl c2409kl) {
        Set setC = OU.c();
        C2561ml c2561ml = new C2561ml(c2409kl);
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C1721bt) {
                C1721bt c1721btG0 = abstractC2412ko.g0();
                if (c1721btG0.S2() != this.b.k4.s) {
                    continue;
                } else {
                    if (!c && ((ArrayList) c1721btG0.h1()).size() != 1) {
                        throw new AssertionError();
                    }
                    S10 s10 = (S10) ((ArrayList) c1721btG0.h1()).get(0);
                    if (!s10.A()) {
                        S10 s10B = c1721btG0.b();
                        AbstractC3472z00 abstractC3472z00W = s10.w();
                        if (s10B != null && s10.b(this.a)) {
                            setC.addAll(s10B.a());
                            c2561ml.a(new C1033Hc(c2409kl.a(c1721btG0.j(), AbstractC3472z00.b((C0702m<?>) this.a, WH.b())), this.b.c("null")), (Set) null);
                        } else if (abstractC3472z00W.N().e() && abstractC3472z00W.w() && abstractC3472z00W.b().R().equals(this.b.X1)) {
                            if (s10B != null) {
                                setC.addAll(s10B.a());
                                com.android.tools.r8.ir.optimize.F.a(c1721btG0, c2561ml, s10, s10B);
                            } else {
                                c2561ml.s();
                            }
                        }
                    }
                }
            } else if (abstractC2412ko.f2()) {
                C2115gt c2115gtI0 = abstractC2412ko.i0();
                if (c2115gtI0.S2() != this.b.k4.t) {
                    continue;
                } else {
                    if (!c && ((ArrayList) c2115gtI0.h1()).size() != 1) {
                        throw new AssertionError();
                    }
                    S10 s10U2 = c2115gtI0.U2();
                    AbstractC3472z00 abstractC3472z00W2 = s10U2.w();
                    if (abstractC3472z00W2.N().e() && abstractC3472z00W2.w() && abstractC3472z00W2.b().R().equals(this.b.X1)) {
                        S10 s10B2 = c2115gtI0.b();
                        if (s10B2 != null) {
                            setC.addAll(s10B2.a());
                            com.android.tools.r8.ir.optimize.F.a(c2115gtI0, c2561ml, s10U2, s10B2);
                        } else {
                            c2561ml.s();
                        }
                    }
                }
            } else {
                continue;
            }
        }
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) setC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(C0759u2 c0759u2) {
        try {
            return Integer.valueOf(c0759u2.u0());
        } catch (UTFDataFormatException unused) {
            throw new C3405y10();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(C0759u2 c0759u2, C0759u2 c0759u3) {
        return Integer.valueOf(c0759u2.toString().indexOf(c0759u3.toString()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(C0759u2 c0759u2, Integer num) {
        return Integer.valueOf(c0759u2.toString().lastIndexOf(num.intValue()));
    }

    /* JADX WARN: Code duplicated, block: B:115:0x01b2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x018a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:117:0x01b7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:0x000f A[SYNTHETIC] */
    public final void a(C0702m c0702m, C2409kl c2409kl) {
        S10 s10B;
        AbstractC0727q0 abstractC0727q0C;
        C0759u2 c0759u2A;
        C2477lf c2477lf;
        C2477lf c2477lf2;
        C2409kl c2409kl2 = c2409kl;
        Set setC = OU.c();
        C2561ml c2561ml = new C2561ml(c2409kl2);
        while (true) {
            if (c2561ml.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
                if (abstractC2412ko.f2()) {
                    C2115gt c2115gtI0 = abstractC2412ko.i0();
                    C0684j2 c0684j2S2 = c2115gtI0.S2();
                    if (this.b.p4.n.contains(c0684j2S2) && (s10B = c2115gtI0.b()) != null && s10B.y()) {
                        if (!c && ((ArrayList) c2115gtI0.h1()).size() != 1) {
                            throw new AssertionError();
                        }
                        S10 s10M = c2115gtI0.U2().m();
                        AbstractC2412ko abstractC2412ko2 = s10M.b;
                        if (abstractC2412ko2 != null && (abstractC2412ko2 instanceof C3446yc) && !s10M.A()) {
                            C0767v2 c0767v2K2 = s10M.b.A().K2();
                            int iG0 = c0767v2K2.G0();
                            C0767v2 c0767v2B = c0767v2K2.b(this.b);
                            if (c0767v2B.O0() && (abstractC0727q0C = c0702m.c(c0767v2B)) != null) {
                                boolean z = c0702m.q() && ((C3733i) c0702m.i0().f()).a((com.android.tools.r8.graph.O0) abstractC0727q0C);
                                if (c0702m.Y().n1.W || (!z && (c0684j2S2 == this.b.p4.g || !new C2405kh(c0702m, YX.a).b(c2409kl2, s10B)))) {
                                    String strB1 = c0767v2B.b1();
                                    boolean z2 = strB1.indexOf(36) < 0;
                                    C0722p1 c0722p1 = this.b;
                                    C0757u1 c0757u1 = c0722p1.p4;
                                    if (c0684j2S2 != c0757u1.e) {
                                        if (c0684j2S2 != c0757u1.h) {
                                            if (c0684j2S2 == c0757u1.f) {
                                                if (abstractC0727q0C.E1() || abstractC0727q0C.z1()) {
                                                    setC.addAll(c2115gtI0.b().a());
                                                    c2561ml.a(c2409kl.i(), (Set) null);
                                                    c0759u2A = null;
                                                    c2477lf2 = null;
                                                    if (c0759u2A != null) {
                                                        setC.addAll(c2115gtI0.b().a());
                                                        c2409kl2 = c2409kl;
                                                        c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                                    } else {
                                                        c2409kl2 = c2409kl;
                                                        if (c2477lf2 != null) {
                                                            setC.addAll(c2115gtI0.b().a());
                                                            c2561ml.a(c2477lf2, (Set) null);
                                                        }
                                                    }
                                                } else if (z2) {
                                                    if (z) {
                                                        c2477lf = new C2477lf(c2115gtI0.b(), c0767v2B, C3141ua.a(EnumC3070ta.d, iG0));
                                                        c2477lf2 = c2477lf;
                                                        c0759u2A = null;
                                                        if (c0759u2A != null) {
                                                            setC.addAll(c2115gtI0.b().a());
                                                            c2409kl2 = c2409kl;
                                                            c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                                        } else {
                                                            c2409kl2 = c2409kl;
                                                            if (c2477lf2 != null) {
                                                                setC.addAll(c2115gtI0.b().a());
                                                                c2561ml.a(c2477lf2, (Set) null);
                                                            }
                                                        }
                                                    } else {
                                                        c0759u2A = EnumC3070ta.d.a(strB1, abstractC0727q0C, this.b, iG0);
                                                        c2477lf2 = null;
                                                        if (c0759u2A != null) {
                                                            setC.addAll(c2115gtI0.b().a());
                                                            c2409kl2 = c2409kl;
                                                            c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                                        } else {
                                                            c2409kl2 = c2409kl;
                                                            if (c2477lf2 != null) {
                                                                setC.addAll(c2115gtI0.b().a());
                                                                c2561ml.a(c2477lf2, (Set) null);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (c0684j2S2 == c0757u1.g) {
                                                if (abstractC0727q0C.z1()) {
                                                    c0759u2A = this.b.c("");
                                                } else if (z2) {
                                                    if (z) {
                                                        c2477lf = new C2477lf(c2115gtI0.b(), c0767v2B, C3141ua.a(EnumC3070ta.e, iG0));
                                                        c2477lf2 = c2477lf;
                                                        c0759u2A = null;
                                                        if (c0759u2A != null) {
                                                            setC.addAll(c2115gtI0.b().a());
                                                            c2409kl2 = c2409kl;
                                                            c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                                        } else {
                                                            c2409kl2 = c2409kl;
                                                            if (c2477lf2 != null) {
                                                                setC.addAll(c2115gtI0.b().a());
                                                                c2561ml.a(c2477lf2, (Set) null);
                                                            }
                                                        }
                                                    } else {
                                                        c0759u2A = EnumC3070ta.e.a(strB1, abstractC0727q0C, this.b, iG0);
                                                    }
                                                }
                                                c2477lf2 = null;
                                                if (c0759u2A != null) {
                                                    setC.addAll(c2115gtI0.b().a());
                                                    c2409kl2 = c2409kl;
                                                    c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                                } else {
                                                    c2409kl2 = c2409kl;
                                                    if (c2477lf2 != null) {
                                                        setC.addAll(c2115gtI0.b().a());
                                                        c2561ml.a(c2477lf2, (Set) null);
                                                    }
                                                }
                                            } else {
                                                c0759u2A = null;
                                                c2477lf2 = null;
                                                if (c0759u2A != null) {
                                                    setC.addAll(c2115gtI0.b().a());
                                                    c2409kl2 = c2409kl;
                                                    c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                                } else {
                                                    c2409kl2 = c2409kl;
                                                    if (c2477lf2 != null) {
                                                        setC.addAll(c2115gtI0.b().a());
                                                        c2561ml.a(c2477lf2, (Set) null);
                                                    }
                                                }
                                            }
                                        }
                                        c2409kl2 = c2409kl;
                                    } else if (z) {
                                        c2477lf = new C2477lf(c2115gtI0.b(), c0767v2B, C3141ua.a(EnumC3070ta.b, iG0));
                                        c2477lf2 = c2477lf;
                                        c0759u2A = null;
                                        if (c0759u2A != null) {
                                            setC.addAll(c2115gtI0.b().a());
                                            c2409kl2 = c2409kl;
                                            c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                        } else {
                                            c2409kl2 = c2409kl;
                                            if (c2477lf2 != null) {
                                                setC.addAll(c2115gtI0.b().a());
                                                c2561ml.a(c2477lf2, (Set) null);
                                            }
                                        }
                                    } else {
                                        c0759u2A = EnumC3070ta.b.a(strB1, abstractC0727q0C, c0722p1, iG0);
                                        c2477lf2 = null;
                                        if (c0759u2A != null) {
                                            setC.addAll(c2115gtI0.b().a());
                                            c2409kl2 = c2409kl;
                                            c2561ml.a(new C1033Hc(c2409kl2.a(c2115gtI0.j(), AbstractC3472z00.b((C0702m<?>) c0702m, WH.b())), c0759u2A), (Set) null);
                                        } else {
                                            c2409kl2 = c2409kl;
                                            if (c2477lf2 != null) {
                                                setC.addAll(c2115gtI0.b().a());
                                                c2561ml.a(c2477lf2, (Set) null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (setC.isEmpty()) {
                    return;
                }
                new C3030t00(c0702m, false).a((Iterable) setC);
                return;
            }
        }
    }
}
