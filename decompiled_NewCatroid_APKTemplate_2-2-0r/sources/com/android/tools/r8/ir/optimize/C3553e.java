package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.AssertionsConfiguration;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C0827Af;
import com.android.tools.r8.internal.C1015Gl;
import com.android.tools.r8.internal.C1296Qj;
import com.android.tools.r8.internal.C1621aZ;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2537mR;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.RW;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.InterfaceC3900g3;
import com.android.tools.r8.utils.P1;
import com.android.tools.r8.utils.y3;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3553e {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C3549d c;
    private final List d;
    private final C3549d e;
    private final boolean f;

    public C3553e(final C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        this.b = c0722p1A;
        boolean zA = a(c0702m.Y());
        this.f = zA;
        if (zA) {
            this.c = new C3549d(c0702m.Y().T0.a, c0722p1A);
            this.d = (List) c0702m.Y().T0.b.stream().map(new Function() { // from class: com.android.tools.r8.ir.optimize.e$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3553e.a(c0702m, (AssertionsConfiguration) obj);
                }
            }).collect(Collectors.toList());
            this.e = a(c0702m.a().z4.e.a);
        } else {
            this.c = null;
            this.d = null;
            this.e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C3549d a(C0702m c0702m, AssertionsConfiguration assertionsConfiguration) {
        return new C3549d(assertionsConfiguration, c0702m.a());
    }

    public static boolean a(A1 a1) {
        boolean zIsPassthrough;
        com.android.tools.r8.utils.D d = a1.T0;
        if (d == null) {
            return false;
        }
        if (d.b.size() == 0) {
            zIsPassthrough = d.a.isPassthrough();
        } else {
            zIsPassthrough = d.b.size() == 1 && ((AssertionsConfiguration) d.b.get(0)).getScope() == AssertionsConfiguration.a.ALL && ((AssertionsConfiguration) d.b.get(0)).isPassthrough();
        }
        return !zIsPassthrough;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean a(W0 w0, final C2409kl c2409kl) {
        W0 w0Y0;
        C3549d c3549dA = a(w0.J0());
        if (c3549dA.a.isPassthrough()) {
            return false;
        }
        if (w0.u1()) {
            w0Y0 = w0;
        } else {
            AbstractC0727q0 abstractC0727q0C = this.a.c(w0.J0());
            if (abstractC0727q0C == null) {
                return false;
            }
            w0Y0 = abstractC0727q0C.Y0();
        }
        final C2537mR c2537mR = new C2537mR(0);
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final Set setC = OU.c();
        final C2537mR c2537mR2 = new C2537mR(0);
        if (c3549dA.a.isAssertionHandler()) {
            final P1 p1 = new P1(new Supplier() { // from class: com.android.tools.r8.ir.optimize.e$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return C3553e.a(c2409kl);
                }
            });
            c2409kl.d.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.e$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(setC, p1, c2537mR, identityHashMap, c2537mR2, (C3332x4) obj);
                }
            });
        }
        if (!g && c2537mR.size() != identityHashMap.size()) {
            throw new AssertionError();
        }
        int i = 1;
        boolean z = w0Y0 != null && w0Y0.i1().x();
        C2561ml c2561ml = new C2561ml(c2409kl);
        boolean z2 = 0;
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            if (abstractC2412ko.Y1()) {
                AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                if (abstractC1305QsB0.S2() == this.b.p4.a) {
                    if (w0.J0() == this.b.z4.e.a) {
                        if (c2561ml.hasNext() && c3549dA.a.isCompileTimeDisabled()) {
                            AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) c2561ml.next();
                            if (abstractC2412ko2.c() && abstractC2412ko2.G0().getField().e == this.b.z4.e.a && abstractC2412ko2.G0().getField().f == this.b.f1 && abstractC1305QsB0.b().b0() == i && abstractC1305QsB0.b().a0() == 0 && abstractC1305QsB0.b().f0() == abstractC2412ko2) {
                                c2561ml.s();
                                AbstractC2412ko abstractC2412koPrevious = c2561ml.previous();
                                if (!g && abstractC2412koPrevious != abstractC1305QsB0) {
                                    throw new AssertionError();
                                }
                                c2561ml.s();
                            } else {
                                AbstractC2412ko abstractC2412koPrevious2 = c2561ml.previous();
                                boolean z3 = g;
                                if (!z3 && abstractC2412koPrevious2 != abstractC2412ko2) {
                                    throw new AssertionError();
                                }
                                AbstractC2412ko abstractC2412koPrevious3 = c2561ml.previous();
                                if (!z3 && abstractC2412koPrevious3 != abstractC1305QsB0) {
                                    throw new AssertionError();
                                }
                                AbstractC2412ko abstractC2412ko3 = (AbstractC2412ko) c2561ml.next();
                                if (!z3 && abstractC2412ko3 != abstractC1305QsB0) {
                                    throw new AssertionError();
                                }
                                c2561ml.a(c2409kl.a(0, (com.android.tools.r8.graph.X) null), (Set) null);
                            }
                        } else {
                            c2561ml.a(c2409kl.a(c3549dA.a.isCompileTimeEnabled() ? 1 : 0, (com.android.tools.r8.graph.X) null), (Set) null);
                        }
                    } else {
                        c2561ml.a(c2409kl.a(0, abstractC2412ko.j()), (Set) null);
                    }
                }
            } else if (abstractC2412ko.c()) {
                RW rwG0 = abstractC2412ko.G0();
                if (z && a(rwG0)) {
                    c2561ml.remove();
                }
            } else if (abstractC2412ko instanceof QW) {
                QW qwF0 = abstractC2412ko.F0();
                if (z && a(qwF0) && !c3549dA.a.isAssertionHandler()) {
                    c2561ml.a(c2409kl.a(c3549dA.a.isCompileTimeDisabled() ? 1 : 0, abstractC2412ko.j()), (Set) null);
                }
                if (qwF0.getField() == this.b.z4.e.b && !c3549dA.a.isAssertionHandler()) {
                    c2561ml.a(c2409kl.a((this.e.a.isCompileTimeDisabled() ? 1 : 0) ^ i, abstractC2412ko.j()), (Set) null);
                }
            }
            if (c3549dA.a.isAssertionHandler()) {
                if (abstractC2412ko.N1()) {
                    C1015Gl c1015GlQ = abstractC2412ko.Q();
                    z2 = z2;
                    if (c2537mR.containsKey(c1015GlQ)) {
                        c1015GlQ.b((((Boolean) c2537mR.get(c1015GlQ)).booleanValue() ? 1 : 0) ^ i).e0();
                        c1015GlQ.O2().e(c1015GlQ);
                        c2561ml.a(new C1296Qj(), (Set) null);
                        z2 = i;
                    }
                    if (c2537mR2.containsKey(c1015GlQ)) {
                        c1015GlQ.b((((Boolean) c2537mR2.get(c1015GlQ)).booleanValue() ? 1 : 0) ^ i).e0();
                        c1015GlQ.O2().e(c1015GlQ);
                        c2561ml.a(new C1296Qj(), (Set) null);
                        z2 = i;
                    }
                } else if (abstractC2412ko.B2()) {
                    C1621aZ c1621aZL0 = abstractC2412ko.L0();
                    if (identityHashMap.containsKey(c1621aZL0)) {
                        C3332x4 c3332x4H = c1621aZL0.h();
                        C0722p1 c0722p1 = this.b;
                        if (!C3549d.c && !c3549dA.a.isAssertionHandler()) {
                            throw new AssertionError();
                        }
                        c2561ml.a(new C1721bt(c0722p1.a(c3549dA.a.getAssertionHandler()), null, AbstractC1639am.c(c1621aZL0.K2())), (Set) null);
                        C1296Qj c1296Qj = new C1296Qj();
                        c1296Qj.a(c3332x4H);
                        c1296Qj.b(c1621aZL0.Y0());
                        c3332x4H.h((C3332x4) identityHashMap.get(c1621aZL0));
                        c2561ml.add(c1296Qj);
                    }
                }
            }
            i = 1;
        }
        return z2;
    }

    private C3549d a(C0767v2 c0767v2) {
        char cA;
        char cA2;
        byte[] bArr;
        C3549d c3549d = this.c;
        for (C3549d c3549d2 : this.d) {
            int i = AbstractC3545c.a[c3549d2.a.getScope().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    if (c0767v2.e.equals(c3549d2.b)) {
                        c3549d = c3549d2;
                    }
                    C0759u2 c0759u2 = c3549d2.b;
                    C0759u2 c0759u3 = c0767v2.e;
                    if (c0759u3 != c0759u2) {
                        if (c0759u3.d < c0759u2.d) {
                            continue;
                        } else {
                            InterfaceC3900g3 interfaceC3900g3A0 = c0759u2.A0();
                            InterfaceC3900g3 interfaceC3900g3A1 = c0759u3.A0();
                            do {
                                try {
                                    if (interfaceC3900g3A0.hasNext()) {
                                        cA = interfaceC3900g3A0.a();
                                        cA2 = interfaceC3900g3A1.a();
                                        if (cA == ';' && cA2 == '$') {
                                            do {
                                                if (interfaceC3900g3A1.hasNext()) {
                                                }
                                            } while (interfaceC3900g3A1.a() != '/');
                                        }
                                    } else {
                                        if (!g && !interfaceC3900g3A1.hasNext()) {
                                            throw new AssertionError();
                                        }
                                        break;
                                        break;
                                    }
                                } catch (UTFDataFormatException unused) {
                                    continue;
                                }
                            } while (cA == cA2);
                        }
                    }
                } else if (i != 3) {
                    throw new C3405y10();
                }
                c3549d = c3549d2;
                break;
            }
            C0759u2 c0759u4 = c3549d2.b;
            if (c0759u4.d == 0) {
                C0759u2 c0759u5 = c0767v2.e;
                C0759u2 c0759u6 = this.b.F;
                int i2 = 0;
                while (true) {
                    if (c0759u5.e.length - i2 < c0759u6.e.length) {
                        c3549d = c3549d2;
                        break;
                        break;
                    }
                    int i3 = 0;
                    while (true) {
                        bArr = c0759u6.e;
                        if (i3 >= bArr.length - 1 || c0759u5.e[i2 + i3] != bArr[i3]) {
                            break;
                        }
                        i3++;
                    }
                    if (i3 == bArr.length - 1) {
                        break;
                    }
                    i2++;
                }
            } else {
                C0759u2 c0759u7 = c0767v2.e;
                c0759u7.getClass();
                if (c0759u7.a(c0759u4.e)) {
                    c3549d = c3549d2;
                    break;
                    break;
                }
            }
        }
        if (g || c3549d != null) {
            return c3549d;
        }
        throw new AssertionError();
    }

    public final void a(W0 w0, C2409kl c2409kl, M m, y3 y3Var) {
        if (this.f) {
            y3Var.a("Rewrite assertions");
            if (a(w0, c2409kl)) {
                m.a(c2409kl, y3Var);
            }
            if (!g && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
            y3Var.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0827Af a(C2409kl c2409kl) {
        return new C0827Af(c2409kl, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, P1 p1, Map map, Map map2, final Map map3, C3332x4 c3332x4) {
        C1015Gl c1015GlA;
        if (set.contains(c3332x4) || (c1015GlA = a(c3332x4)) == null) {
            return;
        }
        boolean zA = a(c1015GlA.O2().q().F0());
        boolean z = !zA;
        C3332x4 c3332x4B = c1015GlA.b(z);
        C0827Af c0827Af = (C0827Af) p1.d();
        c0827Af.getClass();
        List list = (List) c0827Af.a(c3332x4B, new ArrayList());
        Iterator it = list.iterator();
        C1621aZ c1621aZ = null;
        C1621aZ c1621aZL0 = null;
        while (true) {
            if (!it.hasNext()) {
                c1621aZ = c1621aZL0;
                break;
            }
            C3332x4 c3332x5 = (C3332x4) it.next();
            if (c3332x5.m().u2()) {
                break;
            }
            if (c3332x5.m().B2()) {
                if (c1621aZL0 != null) {
                    break;
                } else {
                    c1621aZL0 = c3332x5.m().L0();
                }
            }
        }
        if (c1621aZ != null) {
            map.put(c1015GlA, Boolean.valueOf(z));
            map2.put(c1621aZ, c1015GlA.b(zA));
            list.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.e$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(map3, (C3332x4) obj);
                }
            });
            set.addAll(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, C3332x4 c3332x4) {
        C1015Gl c1015GlA = a(c3332x4);
        if (c1015GlA != null) {
            map.put(c1015GlA, Boolean.valueOf(!a(c1015GlA.O2().q().F0())));
        }
    }

    private boolean a(AbstractC1182Mh abstractC1182Mh) {
        return abstractC1182Mh.getField().C0() == this.b.B0 && abstractC1182Mh.getField().getType() == this.b.v1;
    }

    private C1015Gl a(C3332x4 c3332x4) {
        if (!c3332x4.m().N1()) {
            return null;
        }
        C1015Gl c1015GlQ = c3332x4.m().Q();
        if (!c1015GlQ.N2() || !c1015GlQ.O2().b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.e$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).w2();
            }
        })) {
            return null;
        }
        QW qwF0 = c1015GlQ.O2().q().F0();
        if ((a(qwF0) || qwF0.getField() == this.b.z4.e.b) && qwF0.value().D() && !qwF0.value().C()) {
            return c1015GlQ;
        }
        return null;
    }
}
