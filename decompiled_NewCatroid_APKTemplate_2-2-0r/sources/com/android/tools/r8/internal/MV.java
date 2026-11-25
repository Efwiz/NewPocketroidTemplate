package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MV {
    static final /* synthetic */ boolean i = true;
    private final C0702m a;
    private final C2409kl b;
    private final HashMap c = new HashMap();
    private final LinkedList d = new LinkedList();
    private final LinkedList e = new LinkedList();
    private final int f;
    private final BitSet[] g;
    private final BitSet h;

    public MV(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl;
        int iL = c2409kl.l() + 1;
        this.f = iL;
        this.g = new BitSet[iL];
        this.h = new BitSet(iL);
    }

    private void a() {
        final Set setC = OU.c();
        final ArrayList arrayList = new ArrayList();
        this.c.entrySet().stream().filter(new Predicate() { // from class: com.android.tools.r8.internal.MV$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MV.a((Map.Entry) obj);
            }
        }).forEach(new Consumer() { // from class: com.android.tools.r8.internal.MV$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setC, arrayList, (Map.Entry) obj);
            }
        });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((C3332x4) it.next()).h();
        }
        if (!setC.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) setC);
        }
        this.b.y();
    }

    public final void b() {
        C3332x4 c3332x4J = this.b.j();
        Iterator<AbstractC2412ko> it = c3332x4J.p().iterator();
        while (it.hasNext()) {
            b(it.next());
        }
        this.h.set(c3332x4J.v());
        while (true) {
            if (this.e.isEmpty() && this.d.isEmpty()) {
                break;
            }
            while (!this.e.isEmpty()) {
                C3332x4 c3332x4 = (C3332x4) this.e.poll();
                Iterator<ML> it2 = c3332x4.x().iterator();
                while (it2.hasNext()) {
                    a(it2.next());
                }
                if (!this.h.get(c3332x4.v())) {
                    Iterator<AbstractC2412ko> it3 = c3332x4.p().iterator();
                    while (it3.hasNext()) {
                        b(it3.next());
                    }
                    this.h.set(c3332x4.v());
                }
            }
            while (!this.d.isEmpty()) {
                S10 s10 = (S10) this.d.poll();
                Iterator it4 = s10.g0().iterator();
                while (it4.hasNext()) {
                    a((ML) it4.next());
                }
                for (AbstractC2412ko abstractC2412ko : s10.h0()) {
                    if (this.h.get(abstractC2412ko.h().v())) {
                        b(abstractC2412ko);
                    }
                }
            }
        }
        a();
        if (!i && !this.b.b(this.a)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(Map.Entry entry) {
        SA sa = (SA) entry.getValue();
        sa.getClass();
        return sa instanceof C0852Bc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, List list, Map.Entry entry) {
        final S10 s10 = (S10) entry.getKey();
        C0978Fc c0978FcC = ((SA) entry.getValue()).a().c();
        if (s10.b != c0978FcC) {
            s10.a(set);
            if (s10.i()) {
                if (s10.y()) {
                    C3332x4 c3332x4H = s10.l().h();
                    list.add(c3332x4H);
                    C0978Fc c0978FcA = C0978Fc.a(this.b, c0978FcC);
                    InterfaceC2641no interfaceC2641noA = c3332x4H.a(this.b);
                    AbstractC2412ko abstractC2412ko = (AbstractC2412ko) interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.internal.MV$$ExternalSyntheticLambda3
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return MV.a((AbstractC2412ko) obj);
                        }
                    });
                    c0978FcA.b(abstractC2412ko.Y0());
                    if (!(abstractC2412ko instanceof C1343Sd)) {
                        interfaceC2641noA.previous();
                    }
                    interfaceC2641noA.add(c0978FcA);
                    s10.g(c0978FcA.b());
                    return;
                }
                return;
            }
            InterfaceC2641no interfaceC2641noA2 = s10.b.h().a(this.b);
            interfaceC2641noA2.a(new Predicate() { // from class: com.android.tools.r8.internal.MV$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return MV.a(s10, (AbstractC2412ko) obj);
                }
            });
            interfaceC2641noA2.d(c0978FcC);
        }
    }

    private void b(AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.b() != null && !(abstractC2412ko instanceof C1259Pd)) {
            SA saA = abstractC2412ko.a(this.b, new Function() { // from class: com.android.tools.r8.internal.MV$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((S10) obj);
                }
            });
            SA saA2 = a(abstractC2412ko.b());
            if (saA2.a(saA) != saA2) {
                this.c.put(abstractC2412ko.b(), saA);
                this.d.add(abstractC2412ko.b());
            }
        }
        if (abstractC2412ko.g2()) {
            AbstractC1954eu abstractC1954euJ0 = abstractC2412ko.j0();
            C3332x4 c3332x4H = abstractC1954euJ0.h();
            int iV = c3332x4H.v();
            if (abstractC1954euJ0.N1()) {
                C1015Gl c1015GlQ = abstractC1954euJ0.Q();
                if (c1015GlQ.N2()) {
                    SA saA3 = a((S10) ((ArrayList) c1015GlQ.h1()).get(0));
                    saA3.getClass();
                    if (saA3 instanceof C0852Bc) {
                        C3332x4 c3332x4A = c1015GlQ.a(saA3.a().c());
                        BitSet bitSet = this.g[c3332x4A.v()];
                        if (bitSet != null ? bitSet.get(iV) : false) {
                            return;
                        }
                        int iV2 = c3332x4A.v();
                        BitSet bitSet2 = this.g[iV2];
                        if (bitSet2 == null) {
                            bitSet2 = new BitSet(this.f);
                            this.g[iV2] = bitSet2;
                        }
                        bitSet2.set(iV);
                        this.e.add(c3332x4A);
                        return;
                    }
                } else {
                    SA saA4 = a((S10) ((ArrayList) c1015GlQ.h1()).get(0));
                    SA saA5 = a((S10) ((ArrayList) c1015GlQ.h1()).get(1));
                    saA4.getClass();
                    if (saA4 instanceof C0852Bc) {
                        saA5.getClass();
                        if (saA5 instanceof C0852Bc) {
                            C3332x4 c3332x4A2 = c1015GlQ.a(saA4.a().c(), saA5.a().c());
                            BitSet bitSet3 = this.g[c3332x4A2.v()];
                            if (bitSet3 != null ? bitSet3.get(iV) : false) {
                                return;
                            }
                            int iV3 = c3332x4A2.v();
                            BitSet bitSet4 = this.g[iV3];
                            if (bitSet4 == null) {
                                bitSet4 = new BitSet(this.f);
                                this.g[iV3] = bitSet4;
                            }
                            bitSet4.set(iV);
                            this.e.add(c3332x4A2);
                            return;
                        }
                    }
                    boolean z = i;
                    if (!z && (saA4 instanceof C2009fZ)) {
                        throw new AssertionError();
                    }
                    if (!z) {
                        saA5.getClass();
                        if (saA5 instanceof C2009fZ) {
                            throw new AssertionError();
                        }
                    }
                }
            } else if (abstractC1954euJ0.T1()) {
                C1458Vr c1458VrW = abstractC1954euJ0.W();
                SA saA6 = a(c1458VrW.value());
                saA6.getClass();
                if (saA6 instanceof C0852Bc) {
                    C3332x4 c3332x4J2 = (C3332x4) c1458VrW.P2().get(saA6.a().d());
                    if (c3332x4J2 == null) {
                        c3332x4J2 = c1458VrW.J2();
                    }
                    if (!i && c3332x4J2 == null) {
                        throw new AssertionError();
                    }
                    int iV4 = c3332x4J2.v();
                    BitSet bitSet5 = this.g[iV4];
                    if (bitSet5 == null) {
                        bitSet5 = new BitSet(this.f);
                        this.g[iV4] = bitSet5;
                    }
                    bitSet5.set(iV);
                    this.e.add(c3332x4J2);
                    return;
                }
            } else if (abstractC1954euJ0.y2()) {
                C1620aY c1620aYI0 = abstractC1954euJ0.I0();
                SA saA7 = a(c1620aYI0.value());
                saA7.getClass();
                if (saA7 instanceof C0852Bc) {
                    if (!i && !saA7.a().c().T2()) {
                        throw new AssertionError();
                    }
                    C3332x4 c3332x4J3 = c1620aYI0.J2();
                    int iV5 = c3332x4J3.v();
                    BitSet bitSet6 = this.g[iV5];
                    if (bitSet6 == null) {
                        bitSet6 = new BitSet(this.f);
                        this.g[iV5] = bitSet6;
                    }
                    bitSet6.set(iV);
                    this.e.add(c3332x4J3);
                    return;
                }
            } else if (!i && !abstractC1954euJ0.M1() && !abstractC1954euJ0.u2() && !abstractC1954euJ0.B2()) {
                throw new AssertionError();
            }
            for (C3332x4 c3332x4 : c3332x4H.z()) {
                BitSet bitSet7 = this.g[c3332x4.v()];
                if (!(bitSet7 == null ? false : bitSet7.get(iV))) {
                    int iV6 = c3332x4.v();
                    BitSet bitSet8 = this.g[iV6];
                    if (bitSet8 == null) {
                        bitSet8 = new BitSet(this.f);
                        this.g[iV6] = bitSet8;
                    }
                    bitSet8.set(iV);
                    this.e.add(c3332x4);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return !(abstractC2412ko instanceof AF);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(S10 s10, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == s10.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SA a(S10 s10) {
        return (SA) this.c.getOrDefault(s10, C2009fZ.c());
    }

    private void a(ML ml) {
        C3332x4 c3332x4H = ml.h();
        int iV = c3332x4H.v();
        SA saC = C2009fZ.c();
        List<C3332x4> listY = c3332x4H.y();
        int size = listY.size();
        for (int i2 = 0; i2 < size; i2++) {
            int iV2 = listY.get(i2).v();
            BitSet bitSet = this.g[iV];
            if (bitSet == null ? false : bitSet.get(iV2)) {
                saC = saC.a(a(ml.c(i2)));
                saC.getClass();
                if (saC instanceof C3106u5) {
                    break;
                }
            }
        }
        saC.getClass();
        if (saC instanceof C2009fZ) {
            return;
        }
        SA saA = a((S10) ml);
        if (saA.a(saC) != saA) {
            this.d.add(ml);
            this.c.put(ml, saC);
        }
    }
}
