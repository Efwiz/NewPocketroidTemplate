package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q00 {
    static final /* synthetic */ boolean j = true;
    private final M00 a;
    private final M00 b;
    private final M00 c;
    private final M00 d;
    private final C0702m e;
    private final C2409kl f;
    private HashMap g;
    private IdentityHashMap h = new IdentityHashMap();
    private boolean i = false;

    public Q00(C0702m c0702m, C2409kl c2409kl) {
        this.e = c0702m;
        this.f = c2409kl;
        C0722p1 c0722p1A = c0702m.a();
        M00IA m00ia = null;
        this.a = new M00(c0722p1A.A1);
        this.b = new M00(c0722p1A.z1);
        this.c = new M00(c0722p1A.B1);
        this.d = new M00(c0722p1A.y1);
    }

    public final M00 a(C0767v2 c0767v2) {
        if (!c0767v2.W0()) {
            return new M00(c0767v2);
        }
        if (c0767v2.T0()) {
            return this.c;
        }
        if (c0767v2.P0()) {
            return this.d;
        }
        if (c0767v2.Q0()) {
            return this.b;
        }
        if (j || c0767v2.N0() || c0767v2.e.w0() == 'B' || c0767v2.e.w0() == 'C' || c0767v2.e.w0() == 'S' || c0767v2.R0()) {
            return this.a;
        }
        throw new AssertionError();
    }

    public final P00 b(S10 s10) {
        if (s10 instanceof C2723oi) {
            s10 = ((C2723oi) s10).j0();
        }
        if (s10 instanceof C2617nW) {
            return ((C2617nW) s10).k0();
        }
        int i = L00.a[s10.d0().ordinal()];
        if (i == 1) {
            return (P00) this.g.get(s10);
        }
        if (i == 2) {
            return this.a;
        }
        if (i == 3) {
            return this.b;
        }
        if (i == 4) {
            return this.c;
        }
        if (i == 5) {
            return this.d;
        }
        throw new C3405y10("Unexpected type: " + s10.d0() + " for value: " + s10);
    }

    public final C0767v2 a(S10 s10) {
        boolean z = j;
        if (!z && !this.i) {
            throw new AssertionError();
        }
        if (!z && !s10.d0().a()) {
            throw new AssertionError();
        }
        P00 p00 = (P00) this.g.get(s10);
        if (p00 == null) {
            return null;
        }
        return p00.a();
    }

    public final C0767v2 a(Set set) {
        if (!j && set.isEmpty()) {
            throw new AssertionError();
        }
        if (set.size() == 1) {
            return (C0767v2) set.iterator().next();
        }
        Iterator it = set.iterator();
        AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a((C0767v2) it.next(), WH.i(), (C0702m<?>) this.e);
        while (it.hasNext()) {
            abstractC3472z00A = abstractC3472z00A.a(this.e, AbstractC3472z00.a((C0767v2) it.next(), WH.i(), (C0702m<?>) this.e));
        }
        if (abstractC3472z00A.w()) {
            return abstractC3472z00A.b().R();
        }
        if (abstractC3472z00A.r()) {
            return abstractC3472z00A.a().a(this.e.a());
        }
        throw new C1367Tb("Unexpected join " + abstractC3472z00A + " of types: " + String.join(", ", (Iterable<? extends CharSequence>) set.stream().map(new Q00$$ExternalSyntheticLambda1()).collect(Collectors.toList())));
    }

    public final void a() {
        C0767v2 c0767v2A;
        P00 p00A;
        this.i = true;
        this.g = new HashMap();
        ArrayList<C0978Fc> arrayList = new ArrayList();
        Set setC = OU.c();
        InterfaceC2488lo interfaceC2488loQ = this.f.q();
        int i = this.f.u().f.o() ? 0 : -1;
        AbstractC2412ko next = null;
        while (interfaceC2488loQ.hasNext()) {
            next = interfaceC2488loQ.next();
            if (!next.m1()) {
                break;
            }
            C0684j2 reference = this.f.h().getReference();
            if (i < 0) {
                if (reference.b(this.e)) {
                    p00A = new O00(next.q(), reference.B0());
                } else {
                    p00A = a(reference.B0());
                }
            } else {
                p00A = a(reference.h.f.a[i]);
            }
            S10 s10B = next.b();
            if (s10B.d0().a()) {
                this.g.put(s10B, p00A);
                a(s10B, setC);
            }
            i++;
        }
        while (next != null) {
            boolean z = j;
            if (!z && next.m1()) {
                throw new AssertionError();
            }
            if (next.b() != null) {
                if (next.p2()) {
                    this.g.put(next.b(), (N00) this.h.computeIfAbsent(next.t0(), new Function() { // from class: com.android.tools.r8.internal.Q00$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return new N00((DG) obj);
                        }
                    }));
                    a(next.b(), setC);
                } else if (next.H2().a()) {
                    S10 s10B2 = next.b();
                    if (next.c1()) {
                        if (next.B1()) {
                            if (!z && !next.E().T2()) {
                                throw new AssertionError();
                            }
                            if (s10B2.Z() == s10B2.a0()) {
                                arrayList.add(next.E());
                            }
                        }
                        this.g.put(s10B2, a(next.a(this.e, this)));
                        a(s10B2, setC);
                    }
                }
            }
            next = interfaceC2488loQ.hasNext() ? interfaceC2488loQ.next() : null;
        }
        while (!setC.isEmpty()) {
            S10 s10 = (S10) setC.iterator().next();
            setC.remove(s10);
            if (!j && !s10.d0().a()) {
                throw new AssertionError();
            }
            P00 p00 = (P00) this.g.get(s10);
            C0767v2 c0767v2A2 = p00 == null ? null : p00.a();
            if (s10.i()) {
                c0767v2A = s10.l().a(this.e, this);
            } else {
                c0767v2A = s10.b.a(this.e, this);
            }
            if (c0767v2A2 != c0767v2A) {
                this.g.put(s10, a(c0767v2A));
                a(s10, setC);
            }
        }
        this.i = false;
        for (C0978Fc c0978Fc : arrayList) {
            P00 p01 = null;
            for (ML ml : c0978Fc.b().g0()) {
                if (p01 == null) {
                    p01 = (P00) this.g.get(ml);
                } else if (p01.a() != ((P00) this.g.get(ml)).a()) {
                    p01 = null;
                    break;
                }
            }
            if (p01 != null) {
                this.g.put(c0978Fc.b(), p01);
            }
        }
    }

    private static void a(S10 s10, Set set) {
        set.addAll(s10.g0());
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            if (abstractC2412ko.b() != null && abstractC2412ko.H2().a() && !abstractC2412ko.c1()) {
                set.add(abstractC2412ko.b());
            }
        }
    }
}
