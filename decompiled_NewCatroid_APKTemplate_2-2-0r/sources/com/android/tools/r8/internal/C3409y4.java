package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.y4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3409y4 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final com.android.tools.r8.graph.o5 b;

    public C3409y4(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl.h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(AbstractC2412ko abstractC2412ko) {
        boolean z;
        if (abstractC2412ko.d1()) {
            S10 s10B = abstractC2412ko.b();
            if (s10B.a0() <= 0) {
                Iterator<AbstractC2412ko> it = s10B.h0().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                    } else if (it.next().h() != abstractC2412ko.h()) {
                    }
                }
            }
            z = true;
        } else {
            z = false;
        }
        return z || a(abstractC2412ko);
    }

    public final boolean a(S10 s10, C3332x4 c3332x4, C3332x4 c3332x5) {
        return a(s10, c3332x4.R(), c3332x5.R(), null);
    }

    private AbstractC2412ko a(InterfaceC2488lo interfaceC2488lo, final S10 s10, Predicate predicate) {
        return (AbstractC2412ko) interfaceC2488lo.a(predicate.or(new Predicate() { // from class: com.android.tools.r8.internal.y4$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(s10, (AbstractC2412ko) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(S10 s10, AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.g2()) {
            if (!(abstractC2412ko instanceof N3)) {
                com.android.tools.r8.utils.O3 o3E = null;
                for (S10 s11 : abstractC2412ko.h1()) {
                    N3 n3W = s11.i() ? null : s11.q().w();
                    if (n3W != null) {
                        if (o3E == null) {
                            o3E = com.android.tools.r8.utils.O3.e();
                        }
                        o3E.c(n3W);
                    }
                }
                if (o3E != null) {
                    while (o3E.c()) {
                        for (S10 s12 : ((N3) o3E.f()).h1()) {
                            if (s12 != s10) {
                                N3 n3W2 = s12.i() ? null : s12.q().w();
                                if (n3W2 != null) {
                                    o3E.c(n3W2);
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    private boolean a(S10 s10, InterfaceC2488lo interfaceC2488lo, InterfaceC2488lo interfaceC2488lo2, Set set) {
        AbstractC0748t0 abstractC0748t0H;
        AbstractC2412ko abstractC2412koA = a(interfaceC2488lo, s10, new Predicate() { // from class: com.android.tools.r8.internal.y4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((AbstractC2412ko) obj);
            }
        });
        if (abstractC2412koA.d1()) {
            S10 s10B = abstractC2412koA.b();
            if (s10B.a0() <= 0) {
                Iterator<AbstractC2412ko> it = s10B.h0().iterator();
                while (it.hasNext()) {
                    if (it.next().h() != abstractC2412koA.h()) {
                    }
                }
            }
            return false;
        }
        AbstractC2412ko abstractC2412koA2 = a(interfaceC2488lo2, s10, new Predicate() { // from class: com.android.tools.r8.internal.y4$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((AbstractC2412ko) obj);
            }
        });
        if (!c && abstractC2412koA2 == null) {
            throw new AssertionError();
        }
        if (abstractC2412koA.M1()) {
            C3332x4 c3332x4K2 = abstractC2412koA.P().K2();
            if (abstractC2412koA2.M1()) {
                C3332x4 c3332x4K3 = abstractC2412koA2.P().K2();
                if (c3332x4K2 == c3332x4K3) {
                    C3332x4 c3332x4H = abstractC2412koA.h();
                    C3332x4 c3332x4H2 = abstractC2412koA2.h();
                    if (c3332x4H != c3332x4H2) {
                        List<C3332x4> listY = c3332x4K2.y();
                        int i = -1;
                        int i2 = -1;
                        for (int i3 = 0; i3 < listY.size(); i3++) {
                            C3332x4 c3332x4 = listY.get(i3);
                            if (c3332x4 == c3332x4H) {
                                i2 = i3;
                                if (i >= 0) {
                                    break;
                                }
                            } else {
                                if (c3332x4 == c3332x4H2) {
                                    i = i3;
                                    if (i2 >= 0) {
                                        break;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                        boolean z = c;
                        if (!z && i2 < 0) {
                            throw new AssertionError();
                        }
                        if (!z && i < 0) {
                            throw new AssertionError();
                        }
                        for (ML ml : c3332x4K2.x()) {
                            if (!a(ml.c(i2), ml.c(i))) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                if (c3332x4K3.G()) {
                    return false;
                }
                interfaceC2488lo2 = c3332x4K3.R();
            } else {
                if (c3332x4K2.G()) {
                    return false;
                }
                interfaceC2488lo2.previous();
            }
            if (set == null) {
                C3332x4 c3332x4H3 = abstractC2412koA.h();
                Set setC = OU.c();
                setC.add(c3332x4H3);
                set = setC;
            }
            if (set.add(c3332x4K2)) {
                return a(s10, c3332x4K2.R(), interfaceC2488lo2, set);
            }
            return false;
        }
        Set setC2 = null;
        while (abstractC2412koA2.M1()) {
            C3332x4 c3332x4H4 = abstractC2412koA2.h();
            if (setC2 != null && !setC2.add(c3332x4H4)) {
                return false;
            }
            C3332x4 c3332x4K4 = abstractC2412koA2.P().K2();
            if (c3332x4K4.G()) {
                return false;
            }
            InterfaceC2488lo interfaceC2488loR = c3332x4K4.R();
            AbstractC2412ko abstractC2412koA3 = a(interfaceC2488loR, s10, new Predicate() { // from class: com.android.tools.r8.internal.y4$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a((AbstractC2412ko) obj);
                }
            });
            if (abstractC2412koA3.M1() && setC2 == null) {
                setC2 = OU.c();
                setC2.add(c3332x4H4);
            }
            interfaceC2488lo2 = interfaceC2488loR;
            abstractC2412koA2 = abstractC2412koA3;
        }
        if (abstractC2412koA.a(this.a.a())) {
            C1166Ls c1166LsZ = abstractC2412koA.Z();
            if (abstractC2412koA2.a(this.a.a())) {
                C1166Ls c1166LsZ2 = abstractC2412koA2.Z();
                AbstractC0748t0 abstractC0748t0H2 = c1166LsZ.h(this.a, this.b);
                if (abstractC0748t0H2 != null && !abstractC0748t0H2.e().i1().A() && (abstractC0748t0H = c1166LsZ2.h(this.a, this.b)) != null && !abstractC0748t0H.e().i1().A()) {
                    return a(s10, interfaceC2488lo, interfaceC2488lo2, set);
                }
            }
            return false;
        }
        if (abstractC2412koA.u2()) {
            C1616aU c1616aUB0 = abstractC2412koA.B0();
            if (abstractC2412koA2.u2()) {
                C1616aU c1616aUB1 = abstractC2412koA2.B0();
                if (c1616aUB0.K2()) {
                    if (c || c1616aUB1.K2()) {
                        return true;
                    }
                    throw new AssertionError();
                }
                return a(c1616aUB1.L2(), c1616aUB0.L2());
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.a(this.a.a()) || abstractC2412ko.c(this.a, this.b);
    }

    private static boolean a(S10 s10, S10 s11) {
        S10 s10M = s10.m();
        S10 s10M2 = s11.m();
        if (s10M == s10M2) {
            return true;
        }
        if (s10M.i() || s10M2.i()) {
            return false;
        }
        AbstractC2412ko abstractC2412ko = s10M.b;
        AbstractC2412ko abstractC2412ko2 = s10M2.b;
        boolean z = c;
        if (!z && !abstractC2412ko.d1()) {
            throw new AssertionError();
        }
        if (!z && !abstractC2412ko2.d1()) {
            throw new AssertionError();
        }
        if (abstractC2412ko.b().w().equals(abstractC2412ko2.b().w())) {
            if (abstractC2412ko instanceof C3446yc) {
                if (abstractC2412ko2 instanceof C3446yc) {
                    if (abstractC2412ko.A().K2() == abstractC2412ko2.A().K2()) {
                        return true;
                    }
                }
            } else if (abstractC2412ko.B1()) {
                if (abstractC2412ko2.B1()) {
                    if (abstractC2412ko.E().P2() == abstractC2412ko2.E().P2()) {
                        return true;
                    }
                }
            } else if (abstractC2412ko instanceof C1033Hc) {
                if (abstractC2412ko2 instanceof C1033Hc) {
                    if (abstractC2412ko.F().J2() == abstractC2412ko2.F().J2()) {
                        return true;
                    }
                }
            } else if ((abstractC2412ko instanceof C2477lf) && (abstractC2412ko2 instanceof C2477lf)) {
                if (abstractC2412ko.J().J2() == abstractC2412ko2.J().J2()) {
                    return true;
                }
            }
        }
        return false;
    }
}
