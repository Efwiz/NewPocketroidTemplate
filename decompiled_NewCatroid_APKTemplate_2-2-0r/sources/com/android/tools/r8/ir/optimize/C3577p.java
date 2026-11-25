package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2967s4;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C0827Af;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C1015Gl;
import com.android.tools.r8.internal.C1207Nf;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.I2$$ExternalSyntheticLambda1;
import com.android.tools.r8.internal.InterfaceC0967Er;
import com.android.tools.r8.internal.InterfaceC0993Fr;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.L3;
import com.android.tools.r8.internal.M3;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.N3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.Q3;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.VA;
import com.android.tools.r8.internal.VH$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.utils.G3;
import com.android.tools.r8.utils.H3;
import com.android.tools.r8.utils.L1;
import com.android.tools.r8.utils.y3;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.p, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3577p {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public C3577p(C0702m<C3733i> c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 != abstractC2412ko;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2, AbstractC2412ko abstractC2412ko3) {
        return abstractC2412ko3 == abstractC2412ko || abstractC2412ko3 == abstractC2412ko2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 != abstractC2412ko;
    }

    private boolean b(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, C3563i c3563i) {
        I4.c<?> cVarO = ((C3733i) this.a.f()).h(abstractC1305Qs.S2()).o();
        if (cVarO == null) {
            return false;
        }
        AbstractC0748t0 abstractC0748t0H = abstractC1305Qs.h(this.a, c2409kl.h());
        if (abstractC1305Qs.g1() && abstractC1305Qs.a().I()) {
            C3749l c3749lT = this.a.t();
            AbstractC0748t0 abstractC0748t0Q = cVarO.c();
            c3749lT.getClass();
            Q3 q3A = c3749lT.a(abstractC0748t0Q.getReference());
            Q3 q3A2 = abstractC0748t0H != null ? c3749lT.a(abstractC0748t0H.getReference()) : null;
            if (q3A2 != null) {
                q3A = q3A.a(q3A2);
            }
            if (q3A.c().f().e()) {
                c3563i.a(abstractC1305Qs, abstractC1305Qs.b());
            }
        }
        if (abstractC0748t0H == null) {
            return false;
        }
        com.android.tools.r8.ir.optimize.info.h hVarI1 = abstractC0748t0H.e().i1();
        boolean zA = abstractC1305Qs.g1() ? a(abstractC1305Qs, hVarI1.m(), c3563i) : false;
        BitSet bitSetP = hVarI1.p();
        if (bitSetP != null) {
            for (int iZ1 = abstractC1305Qs.Z1(); iZ1 < ((ArrayList) abstractC1305Qs.J2()).size(); iZ1++) {
                if (bitSetP.get(iZ1)) {
                    S10 s10C = abstractC1305Qs.c(iZ1);
                    if (!c3563i.b.contains(s10C) && a(abstractC1305Qs, s10C)) {
                        c3563i.b(abstractC1305Qs, s10C);
                        zA = true;
                    }
                }
            }
        }
        return zA;
    }

    public void a(C2409kl c2409kl, y3 y3Var) {
        a(c2409kl, c2409kl.s(), CM.b(), y3Var);
    }

    public final void a(C2409kl c2409kl, B4 b4, Predicate predicate, y3 y3Var) {
        y3Var.a("Insert assume instructions");
        y3Var.a("Part 1: Compute assumed values");
        C3563i c3563i = new C3563i();
        while (b4.hasNext()) {
            C3332x4 c3332x4A = b4.next();
            if (predicate.test(c3332x4A)) {
                a(c2409kl, b4, c3332x4A, c3563i);
            }
        }
        C3565j c3565j = new C3565j(c3563i.a);
        y3Var.c();
        if (!c3565j.a.isEmpty()) {
            y3Var.a("Part 2: Remove redundant assume instructions");
            a(c3565j);
            y3Var.c();
            y3Var.a("Part 3: Compute dominated users");
            IdentityHashMap identityHashMapA = a(c2409kl, c3565j);
            y3Var.c();
            if (!c3565j.a.isEmpty()) {
                y3Var.a("Part 4: Remove redundant dominated assume instructions");
                c3565j.a(identityHashMapA);
                y3Var.c();
                if (!c3565j.a.isEmpty()) {
                    y3Var.a("Part 5: Materialize assume instructions");
                    b(c2409kl, c3565j);
                    y3Var.c();
                }
            }
        }
        y3Var.c();
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0031  */
    /* JADX WARN: Code duplicated, block: B:48:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:51:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:55:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:74:0x00b7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:75:0x00b9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00ff A[EDGE_INSN: B:76:0x00ff->B:57:0x00ff BREAK  A[LOOP:0: B:3:0x0004->B:77:0x0004], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0004 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x0004 A[SYNTHETIC] */
    private void a(C2409kl c2409kl, B4 b4, C3332x4 c3332x4, C3563i c3563i) {
        boolean z;
        AbstractC0677i3.a<?> aVarL;
        C3332x4 c3332x4A;
        boolean z2;
        InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
        while (interfaceC2641noA.hasNext()) {
            AbstractC2412ko next = interfaceC2641noA.next();
            boolean zA = false;
            if (next.I2()) {
                S10 s10V0 = next.V0();
                if (c3563i.b.contains(s10V0) || !a(next, s10V0)) {
                    z = false;
                } else {
                    c3563i.b(next, s10V0);
                    z = true;
                }
            } else {
                z = false;
            }
            if (next.Y1()) {
                zA = a(c2409kl, next.b0(), c3563i);
            } else {
                if (next.J1()) {
                    AbstractC1182Mh abstractC1182MhN = next.N();
                    if (!abstractC1182MhN.f1() && (aVarL = ((C3733i) this.a.f()).c(abstractC1182MhN.getField()).l()) != null) {
                        AbstractC0735r0 abstractC0735r0R = aVarL.c();
                        if (abstractC0735r0R.getType().X0()) {
                            C3749l c3749lT = this.a.t();
                            c3749lT.getClass();
                            if (c3749lT.a(abstractC0735r0R.getReference()).c().f().e()) {
                                c3563i.a(abstractC1182MhN, abstractC1182MhN.b());
                            }
                        }
                        zA = a(abstractC1182MhN, abstractC0735r0R.e().X0().g(), c3563i);
                    }
                }
                if (!c3332x4.D()) {
                    if (z) {
                        c3332x4A = interfaceC2641noA.a(c2409kl, b4);
                        z2 = b;
                        if (z2 && interfaceC2641noA.hasNext()) {
                            throw new AssertionError();
                        }
                        if (z2 && !interfaceC2641noA.j().M1()) {
                            throw new AssertionError();
                        }
                        if (!z2) {
                            b4.getClass();
                            if (((C3332x4) L1.b(b4)) != c3332x4A) {
                                throw new AssertionError();
                            }
                        }
                        a(c2409kl, b4, c3332x4A, c3563i);
                        return;
                    }
                    if (next.g()) {
                        break;
                    }
                }
            }
            z |= zA;
            if (!c3332x4.D()) {
                if (z) {
                    c3332x4A = interfaceC2641noA.a(c2409kl, b4);
                    z2 = b;
                    if (z2) {
                    }
                    if (z2) {
                    }
                    if (!z2) {
                        b4.getClass();
                        if (((C3332x4) L1.b(b4)) != c3332x4A) {
                            throw new AssertionError();
                        }
                    }
                    a(c2409kl, b4, c3332x4A, c3563i);
                    return;
                }
                if (next.g()) {
                    break;
                    break;
                }
            }
        }
        C1015Gl c1015GlQ = c3332x4.m().Q();
        if (c1015GlQ != null && c1015GlQ.N2() && c1015GlQ.O2().w().I() && c1015GlQ.O2().w().F()) {
            S10 s10O2 = c1015GlQ.O2();
            if (!c3563i.b.contains(s10O2) && a(c1015GlQ, s10O2) && c1015GlQ.Q2().y().size() == 1) {
                c3563i.b(c1015GlQ, s10O2);
            }
        }
    }

    private void b(final C2409kl c2409kl, C3565j c3565j) {
        Set setC = OU.c();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        a(c2409kl, c3565j, setC, identityHashMap, new Predicate() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3577p.a((C3561h) obj);
            }
        });
        a(c2409kl, c3565j, setC, identityHashMap, new Predicate() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3577p.b((C3561h) obj);
            }
        });
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3577p.a(c2409kl, (C3332x4) obj, (Map) obj2);
            }
        });
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) setC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(C3561h c3561h) {
        AbstractC3559g abstractC3559g = c3561h.a;
        abstractC3559g.getClass();
        return abstractC3559g instanceof C3567k;
    }

    private boolean a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, C3563i c3563i) {
        if (!abstractC1305Qs.d1() && abstractC1305Qs.S2().h.f.isEmpty()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC1305Qs.S2();
        if (c0684j2S2.e.M0() && c0684j2S2.b(this.a.a().g4.c)) {
            S10 s10B = abstractC1305Qs.b();
            if (s10B == null || !s10B.B()) {
                return false;
            }
            C0767v2 c0767v2B0 = abstractC1305Qs.S2().B0();
            C0702m c0702m = this.a;
            WH whB = WH.b();
            c0767v2B0.getClass();
            c3563i.a(abstractC1305Qs, s10B, AbstractC1154Lf.a(c0702m, AbstractC3472z00.a(c0767v2B0, whB, (C0702m<?>) c0702m)));
            return true;
        }
        return b(c2409kl, abstractC1305Qs, c3563i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(AbstractC2412ko abstractC2412ko) {
        return new ArrayList();
    }

    private boolean a(AbstractC2412ko abstractC2412ko, AbstractC1154Lf abstractC1154Lf, C3563i c3563i) {
        S10 s10B = abstractC2412ko.b();
        if (abstractC1154Lf.m()) {
            return false;
        }
        if (abstractC1154Lf.k()) {
            c3563i.a(abstractC2412ko, s10B);
            return true;
        }
        C1207Nf c1207NfA = abstractC1154Lf.a();
        C1207Nf c1207NfA2 = AbstractC1154Lf.a(this.a, s10B.w());
        if (!c1207NfA.b(this.a, c1207NfA2)) {
            return false;
        }
        if (!c1207NfA.f().g() && c1207NfA.a(WH.i()).equals(c1207NfA2)) {
            if (!b && !c1207NfA.f().e()) {
                throw new AssertionError();
            }
            c3563i.a(abstractC2412ko, s10B);
        } else {
            c3563i.a(abstractC2412ko, s10B, c1207NfA);
        }
        return true;
    }

    private static void a(final C3565j c3565j) {
        c3565j.a(new H3() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.utils.H3
            public final boolean a(Object obj, Object obj2, Object obj3) {
                return C3577p.a(c3565j, (AbstractC2412ko) obj, (S10) obj2, (C3561h) obj3);
            }
        });
    }

    private IdentityHashMap a(C2409kl c2409kl, C3565j c3565j) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final VA va = new VA(c2409kl);
        final IdentityHashMap identityHashMap2 = new IdentityHashMap();
        c3565j.a(new G3() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return this.f$0.a(identityHashMap, va, identityHashMap2, (AbstractC2412ko) obj, (S10) obj2, (C3561h) obj3);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC3559g a(Map map, VA va, Map map2, final AbstractC2412ko abstractC2412ko, final S10 s10, C3561h c3561h) {
        C3332x4 c3332x4H;
        AbstractC2412ko next;
        C3561h c3561h2;
        L3 l3;
        Map map3 = (Map) map.get(abstractC2412ko);
        if (map3 != null && (c3561h2 = (C3561h) map3.get(s10)) != null) {
            if (c3561h.b == null && c3561h2.a()) {
                return C3575o.a;
            }
            if (c3561h2.a()) {
                c3561h.c = M3.a();
            }
            if (c3561h.b == null && (l3 = c3561h2.b) != null) {
                c3561h.b = l3;
            }
        }
        if (s10 == abstractC2412ko.b()) {
            return C3567k.a;
        }
        C3332x4 c3332x4H2 = abstractC2412ko.h();
        if (s10.h() == c3332x4H2 && c3332x4H2.m().M1() && !abstractC2412ko.h().D()) {
            InterfaceC2488lo interfaceC2488loR = abstractC2412ko.h().R();
            if (!s10.i()) {
                interfaceC2488loR.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C3577p.a(s10, (AbstractC2412ko) obj);
                    }
                });
                interfaceC2488loR.previous();
            }
            do {
                if (interfaceC2488loR.hasNext() && (next = interfaceC2488loR.next()) != abstractC2412ko) {
                    if (((ArrayList) next.h1()).contains(s10)) {
                        break;
                    }
                } else {
                    return C3569l.a;
                }
            } while (!next.S0().contains(s10));
        }
        if (abstractC2412ko.N1()) {
            c3332x4H = abstractC2412ko.Q().Q2();
        } else {
            c3332x4H = abstractC2412ko.h();
            if (c3332x4H.D()) {
                c3332x4H = AbstractC2967s4.a(c3332x4H);
            }
        }
        if (!b && !s10.C() && !s10.h0().stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3577p.a(abstractC2412ko, (AbstractC2412ko) obj);
            }
        }) && !s10.H()) {
            throw new AssertionError();
        }
        final C0827Af c0827AfD = va.a();
        Set set = (Set) map2.computeIfAbsent(c3332x4H, new Function() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3577p.a(c0827AfD, (C3332x4) obj);
            }
        });
        C3557f c3557f = new C3557f(s10);
        for (final AbstractC2412ko abstractC2412ko2 : s10.h0()) {
            if (abstractC2412ko2 != abstractC2412ko && set.contains(abstractC2412ko2.h())) {
                if (abstractC2412ko2.h() == c3332x4H && c3332x4H == c3332x4H2) {
                    AbstractC2412ko abstractC2412ko3 = (AbstractC2412ko) c3332x4H2.R().a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda9
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C3577p.a(abstractC2412ko, abstractC2412ko2, (AbstractC2412ko) obj);
                        }
                    });
                    if (!b && abstractC2412ko3 == null) {
                        throw new AssertionError();
                    }
                    if (abstractC2412ko3 == abstractC2412ko2) {
                        continue;
                    }
                }
                boolean z = C3557f.d;
                if (!z && !c3557f.a.h0().contains(abstractC2412ko2)) {
                    throw new AssertionError();
                }
                if (!z && c3557f.b.contains(abstractC2412ko2)) {
                    throw new AssertionError();
                }
                c3557f.b.add(abstractC2412ko2);
                ((Map) map.computeIfAbsent(abstractC2412ko2, new Function() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda10
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3577p.a((AbstractC2412ko) obj);
                    }
                })).put(s10, c3561h);
            }
        }
        for (ML ml : s10.g0()) {
            boolean z2 = b;
            if (!z2 && !ml.k0().contains(s10)) {
                throw new AssertionError();
            }
            List<S10> listK0 = ml.k0();
            List<C3332x4> listY = ml.h().y();
            if (!z2 && listK0.size() != listY.size()) {
                throw new AssertionError();
            }
            C2732or c2732or = new C2732or(16);
            Iterator<S10> it = listK0.iterator();
            Iterator<C3332x4> it2 = listY.iterator();
            int i = 0;
            while (it.hasNext() && it2.hasNext()) {
                S10 next2 = it.next();
                C3332x4 next3 = it2.next();
                if (next2 == s10 && set.contains(next3)) {
                    c2732or.a(i);
                }
                i++;
            }
            if (!c2732or.isEmpty()) {
                boolean z3 = C3557f.d;
                if (!z3 && !c3557f.a.g0().contains(ml)) {
                    throw new AssertionError();
                }
                if (!z3 && c3557f.c.containsKey(ml)) {
                    throw new AssertionError();
                }
                c3557f.c.put(ml, c2732or);
            }
        }
        if (c3557f.b.isEmpty() && c3557f.c.isEmpty()) {
            return C3571m.a;
        }
        if (!C3557f.d && c3557f.b.size() >= c3557f.a.h0().size() && c3557f.c.size() >= c3557f.a.g0().size()) {
            throw new AssertionError();
        }
        return new C3573n(c3557f.c, c3557f.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(S10 s10, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko != s10.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0827Af c0827Af, C3332x4 c3332x4) {
        return (Set) c0827Af.a(c3332x4, OU.c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(AbstractC2412ko abstractC2412ko) {
        return new IdentityHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C3561h c3561h) {
        AbstractC3559g abstractC3559g = c3561h.a;
        abstractC3559g.getClass();
        return !(abstractC3559g instanceof C3567k);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2409kl c2409kl, C3332x4 c3332x4, Map map) {
        InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
        while (interfaceC2641noA.hasNext() && !map.isEmpty()) {
            List list = (List) map.remove(interfaceC2641noA.next());
            if (list != null) {
                list.forEach(new I2$$ExternalSyntheticLambda1(interfaceC2641noA));
            }
        }
    }

    private void a(final C2409kl c2409kl, C3565j c3565j, final Set set, final IdentityHashMap identityHashMap, final Predicate predicate) {
        c3565j.a(new H3() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.H3
            public final boolean a(Object obj, Object obj2, Object obj3) {
                return this.f$0.a(predicate, c2409kl, set, identityHashMap, (AbstractC2412ko) obj, (S10) obj2, (C3561h) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Predicate predicate, C2409kl c2409kl, Set set, Map map, final AbstractC2412ko abstractC2412ko, final S10 s10, C3561h c3561h) {
        C3332x4 c3332x4H;
        AbstractC3472z00 abstractC3472z00W;
        S10 s10A;
        AbstractC2412ko n3;
        if (!predicate.test(c3561h)) {
            return false;
        }
        C3332x4 c3332x4H2 = abstractC2412ko.h();
        if (abstractC2412ko.N1()) {
            c3332x4H = abstractC2412ko.Q().Q2();
        } else {
            c3332x4H = abstractC2412ko.h();
            if (c3332x4H.D()) {
                c3332x4H = AbstractC2967s4.a(c3332x4H);
            }
        }
        C3332x4 c3332x4 = c3332x4H;
        AbstractC3559g abstractC3559g = c3561h.a;
        L3 l3 = c3561h.b;
        if (l3 != null && l3.a().f().f()) {
            s10A = c2409kl.a((AbstractC3472z00) AbstractC3472z00.m());
        } else {
            if (c3561h.a()) {
                abstractC3472z00W = s10.w().d().P();
            } else {
                abstractC3472z00W = s10.w();
            }
            s10A = c2409kl.a(s10.s(), abstractC3472z00W);
        }
        S10 s11 = s10A;
        abstractC3559g.getClass();
        if (abstractC3559g instanceof C3567k) {
            s10.g(s11);
        } else if (abstractC3559g instanceof C3569l) {
            s10.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C3577p.b(abstractC2412ko, (AbstractC2412ko) obj);
                }
            }, s11);
            if (s10 != s11) {
                Iterator it = s10.g0().iterator();
                while (it.hasNext()) {
                    ((ML) it.next()).a(s10, s11);
                }
                s10.c();
            }
        } else if (abstractC3559g instanceof C3573n) {
            C3573n c3573nA = abstractC3559g.a();
            c3573nA.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C3577p.a(s10, (ML) obj, (InterfaceC0967Er) obj2);
                }
            });
            s10.a(s11, c3573nA.a, c3573nA.b);
        }
        set.addAll(s11.a());
        L3 l4 = c3561h.b;
        if (l4 != null && l4.a().f().f()) {
            n3 = new C0978Fc(s11, 0L);
        } else {
            n3 = new N3(c3561h.b, c3561h.c, s11, s10, abstractC2412ko, this.a);
        }
        n3.b(abstractC2412ko.Y0());
        if (c3332x4 != c3332x4H2) {
            c3332x4.a(c2409kl).add(n3);
            return true;
        }
        ((List) ((Map) map.computeIfAbsent(c3332x4H2, new Function() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3577p.a((C3332x4) obj);
            }
        })).computeIfAbsent(abstractC2412ko, new Function() { // from class: com.android.tools.r8.ir.optimize.p$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3577p.b((AbstractC2412ko) obj);
            }
        })).add(n3);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(S10 s10, ML ml, InterfaceC0967Er interfaceC0967Er) {
        InterfaceC0993Fr it = interfaceC0967Er.iterator();
        while (it.hasNext()) {
            S10 s10C = ml.c(it.t());
            if (s10C != s10) {
                if (!b && !s10C.b(new VH$$ExternalSyntheticLambda0())) {
                    throw new AssertionError();
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C3332x4 c3332x4) {
        return new IdentityHashMap();
    }

    private static boolean a(AbstractC2412ko abstractC2412ko, S10 s10) {
        AbstractC3472z00 abstractC3472z00W = s10.w();
        if (!abstractC3472z00W.I() || !abstractC3472z00W.d().F()) {
            return false;
        }
        if (s10.C()) {
            return true;
        }
        Iterator<AbstractC2412ko> it = s10.h0().iterator();
        while (it.hasNext()) {
            if (it.next() != abstractC2412ko) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C3565j c3565j, AbstractC2412ko abstractC2412ko, S10 s10, C3561h c3561h) {
        AbstractC2412ko abstractC2412ko2;
        if (c3561h.b != null) {
            return false;
        }
        if (!b && !c3561h.a()) {
            throw new AssertionError();
        }
        if (s10.i() || (abstractC2412ko2 = s10.b) == abstractC2412ko) {
            return false;
        }
        Map map = (Map) c3565j.a.get(abstractC2412ko2);
        C3561h c3561h2 = map != null ? (C3561h) map.get(s10) : null;
        if (c3561h2 == null) {
            return false;
        }
        if (c3561h2.a()) {
            return true;
        }
        c3561h.b = c3561h2.b;
        return false;
    }
}
