package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C0827Af;
import com.android.tools.r8.internal.C1248Os;
import com.android.tools.r8.internal.C1798ct;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3133uU;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.N3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final A1 b;

    public O(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.Y();
    }

    /* JADX WARN: Code duplicated, block: B:116:0x02e0  */
    /* JADX WARN: Code duplicated, block: B:159:0x03e0  */
    /* JADX WARN: Code duplicated, block: B:25:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:59:0x0162  */
    /* JADX WARN: Code duplicated, block: B:69:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:72:0x01b7  */
    /* JADX WARN: Code duplicated, block: B:79:0x01e3  */
    /* JADX WARN: Code duplicated, block: B:81:0x01e9  */
    public final void a(C2409kl c2409kl) {
        Set set;
        C3579q c3579q;
        B4 b4;
        C1248Os c1248OsA0;
        AbstractC0748t0 abstractC0748t0H;
        AbstractC0727q0 abstractC0727q0A;
        S10 s10A;
        boolean z;
        Set set2;
        C3579q c3579q2;
        C3332x4 c3332x4A;
        C0684j2 reference;
        I4.c<?> cVarO;
        C0767v2 c0767v2R;
        I4.c<?> cVarO2;
        C0684j2 c0684j2S2;
        AbstractC0748t0 abstractC0748t0A;
        AbstractC0727q0 abstractC0727q0A2;
        C0684j2 c0684j2B;
        AbstractC0748t0 abstractC0748t0H2;
        C3133uU c3133uUC0;
        Set setC = OU.c();
        C3579q c3579q3 = new C3579q(this.a, c2409kl, OU.c());
        o5 o5VarH = c2409kl.h();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        boolean z2 = true;
        C0827Af c0827Af = new C0827Af(c2409kl, 1);
        IdentityHashMap identityHashMap2 = new IdentityHashMap();
        Set setC2 = OU.c();
        B4 b4S = c2409kl.s();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                if (next2.u1()) {
                    N3 n3W = next2.w();
                    AbstractC2412ko abstractC2412koM2 = n3W.M2();
                    abstractC2412koM2.getClass();
                    if ((abstractC2412koM2 instanceof C1248Os) && !abstractC2412koM2.a0().U2().A() && identityHashMap.containsKey(abstractC2412koM2.a0())) {
                        if (abstractC2412koM2.a0().U2() == n3W.N2()) {
                            C2115gt c2115gt = (C2115gt) identityHashMap.get(abstractC2412koM2.a0());
                            S10 s10U2 = c2115gt.U2();
                            if (s10U2.i()) {
                                c3133uUC0 = null;
                            } else {
                                AbstractC2412ko abstractC2412ko = s10U2.b;
                                abstractC2412ko.getClass();
                                if (abstractC2412ko instanceof C3133uU) {
                                    c3133uUC0 = s10U2.b.C0();
                                    if (!setC2.contains(c3133uUC0)) {
                                        c3133uUC0 = null;
                                    }
                                } else {
                                    c3133uUC0 = null;
                                }
                            }
                            if (c3133uUC0 != null) {
                                S10 s10E = c3133uUC0.e();
                                c3579q = c3579q3;
                                set = setC;
                                b4 = b4S;
                                if (s10U2.w().a(s10E.w(), this.a) && c0827Af.a(next, c2115gt.h())) {
                                    boolean z3 = c;
                                    if (!z3 && n3W.N2() != s10E) {
                                        throw new AssertionError();
                                    }
                                    if (!z3 && s10E.A()) {
                                        throw new AssertionError();
                                    }
                                    s10E.a(s10U2, AbstractC1048Hm.c(n3W), AbstractC2178hm.s());
                                }
                                o5VarH = o5VarH;
                                c3579q2 = c3579q;
                                set2 = set;
                                b4S = b4;
                                z = true;
                            }
                        }
                        z = true;
                    } else {
                        z = z2;
                    }
                    set2 = setC;
                    c3579q2 = c3579q3;
                } else {
                    set = setC;
                    c3579q = c3579q3;
                    b4 = b4S;
                    if (next2 instanceof C1798ct) {
                        C1798ct c1798ctH0 = next2.h0();
                        if (!this.b.n1.J || (abstractC0748t0H2 = c1798ctH0.h(this.a, o5VarH)) == null) {
                            c0684j2S2 = c1798ctH0.S2();
                            C3733i c3733i = (C3733i) this.a.f();
                            C0702m c0702m = this.a;
                            c3733i.getClass();
                            abstractC0748t0A = c3733i.a(c0684j2S2, o5VarH.a(), c0702m, (C0680j) c0702m.f());
                            if (abstractC0748t0A != null || ((abstractC0748t0A.a().isInterface() && abstractC0748t0A.t() != o5VarH.a().f) || AbstractC0639e.a(abstractC0748t0A, abstractC0748t0A.a(), o5VarH, this.a).b())) {
                                abstractC0727q0A2 = null;
                            } else if (abstractC0748t0A.a().f0()) {
                                C0702m c0702m2 = this.a;
                                C0767v2 c0767v2B0 = c0684j2S2.B0();
                                c0702m2.getClass();
                                abstractC0727q0A2 = c0702m2.b(o5VarH.a(), c0767v2B0);
                                while (abstractC0727q0A2 != null && abstractC0727q0A2.e0() && abstractC0727q0A2 != abstractC0748t0A.a()) {
                                    abstractC0727q0A2 = this.a.b(abstractC0727q0A2.a0(), abstractC0727q0A2.f);
                                }
                            } else {
                                abstractC0727q0A2 = abstractC0748t0A.a();
                            }
                            if (abstractC0727q0A2 != null) {
                                C0722p1 c0722p1A = this.a.a();
                                c0684j2S2.getClass();
                                c0684j2B = c0684j2S2.b(abstractC0727q0A2.C(), c0722p1A);
                                if (c0684j2B != c0684j2S2 && ((C3733i) this.a.f()).f().a(o5VarH, c0684j2B, this.a.D())) {
                                    interfaceC2641noA.d(new C1798ct(c0684j2B, c1798ctH0.b(), c1798ctH0.J2(), abstractC0727q0A2.isInterface()));
                                }
                            }
                        } else {
                            C0684j2 c0684j2S3 = c1798ctH0.S2();
                            C0702m c0702m3 = this.a;
                            S10 s10U3 = c1798ctH0.U2();
                            C0702m c0702m4 = this.a;
                            s10U3.getClass();
                            AbstractC0748t0 abstractC0748t0A2 = C2115gt.a(c0702m3, o5VarH, AbstractC1154Lf.a(c0702m4, s10U3), c0684j2S3);
                            if (abstractC0748t0A2 == null || abstractC0748t0A2.getReference() != abstractC0748t0H2.getReference()) {
                                c0684j2S2 = c1798ctH0.S2();
                                C3733i c3733i2 = (C3733i) this.a.f();
                                C0702m c0702m5 = this.a;
                                c3733i2.getClass();
                                abstractC0748t0A = c3733i2.a(c0684j2S2, o5VarH.a(), c0702m5, (C0680j) c0702m5.f());
                                if (abstractC0748t0A != null) {
                                    abstractC0727q0A2 = null;
                                } else if (abstractC0748t0A.a().f0()) {
                                    C0702m c0702m6 = this.a;
                                    C0767v2 c0767v2B1 = c0684j2S2.B0();
                                    c0702m6.getClass();
                                    abstractC0727q0A2 = c0702m6.b(o5VarH.a(), c0767v2B1);
                                    while (abstractC0727q0A2 != null) {
                                        abstractC0727q0A2 = this.a.b(abstractC0727q0A2.a0(), abstractC0727q0A2.f);
                                    }
                                } else {
                                    abstractC0727q0A2 = abstractC0748t0A.a();
                                }
                                if (abstractC0727q0A2 != null) {
                                    C0722p1 c0722p1A2 = this.a.a();
                                    c0684j2S2.getClass();
                                    c0684j2B = c0684j2S2.b(abstractC0727q0A2.C(), c0722p1A2);
                                    if (c0684j2B != c0684j2S2) {
                                        interfaceC2641noA.d(new C1798ct(c0684j2B, c1798ctH0.b(), c1798ctH0.J2(), abstractC0727q0A2.isInterface()));
                                    }
                                }
                            } else {
                                interfaceC2641noA.d(new C2115gt(c0684j2S3, c1798ctH0.b(), c1798ctH0.J2()));
                            }
                        }
                    } else if (next2.f2()) {
                        C2115gt c2115gtI0 = next2.i0();
                        C0684j2 c0684j2S4 = c2115gtI0.S2();
                        S10 s10U4 = c2115gtI0.U2();
                        if (!s10U4.w().w() || (cVarO = ((C3733i) this.a.f()).a(c0684j2S4.B0(), c0684j2S4).o()) == null || cVarO.b(o5VarH, this.a).b() || (c0767v2R = s10U4.w().b().R()) == c0684j2S4.e || (cVarO2 = ((C3733i) this.a.f()).a(c0767v2R, c0684j2S4).o()) == null || cVarO2.b(o5VarH, this.a).b()) {
                            reference = c0684j2S4;
                        } else if (cVarO2.r().b(this.a, cVarO.r()) && (cVarO2.r().L1() || I4.c.a(cVarO.r(), cVarO2.r()))) {
                            AbstractC0727q0 abstractC0727q0D = cVarO2.d();
                            if (!abstractC0727q0D.e0() || abstractC0727q0D.isInterface()) {
                                reference = c0684j2S4;
                            } else {
                                reference = cVarO2.r().getReference();
                            }
                        } else {
                            reference = c0684j2S4;
                        }
                        if (reference != c0684j2S4) {
                            interfaceC2641noA.d(new C2115gt(reference, c2115gtI0.b(), c2115gtI0.J2()));
                        }
                    } else if ((next2 instanceof C1248Os) && (abstractC0748t0H = (c1248OsA0 = next2.a0()).h(this.a, o5VarH)) != null && (abstractC0727q0A = abstractC0748t0H.a()) != null && !abstractC0727q0A.isInterface() && !AbstractC0639e.a(abstractC0727q0A, o5VarH, this.a).b()) {
                        if (((C3733i) this.a.f()).f().a(o5VarH, abstractC0748t0H.getReference(), this.a.D())) {
                            C2115gt c2115gt2 = new C2115gt(abstractC0748t0H.getReference(), c1248OsA0.b(), c1248OsA0.h1());
                            interfaceC2641noA.d(c2115gt2);
                            identityHashMap.put(c1248OsA0, c2115gt2);
                            if (abstractC0727q0A.getType() != c1248OsA0.S2().e) {
                                S10 s10U5 = c1248OsA0.U2();
                                AbstractC3472z00 abstractC3472z00W = s10U5.w();
                                o5VarH = o5VarH;
                                AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(abstractC0727q0A.getType(), abstractC3472z00W.N(), (C0702m<?>) this.a);
                                if (!abstractC3472z00W.a(abstractC3472z00A, this.a)) {
                                    if (identityHashMap2.containsKey(s10U5) && ((Map) identityHashMap2.get(s10U5)).containsKey(abstractC0727q0A.getType())) {
                                        s10A = (S10) ((Map) identityHashMap2.get(s10U5)).get(abstractC0727q0A.getType());
                                        C3332x4 c3332x4H = s10A.b.h();
                                        if (c3332x4H.D()) {
                                            if (c3332x4H.H()) {
                                                c3332x4H = c3332x4H.A();
                                            } else {
                                                if (!c) {
                                                    throw new AssertionError();
                                                }
                                                c3332x4H = null;
                                            }
                                        }
                                        if (c3332x4H == null || !c0827Af.a(next, c3332x4H)) {
                                            s10A = null;
                                        }
                                    } else {
                                        s10A = null;
                                    }
                                    if (s10A == null) {
                                        s10A = c2409kl.a(abstractC3472z00A);
                                        if (!s10U5.A()) {
                                            identityHashMap2.putIfAbsent(s10U5, new IdentityHashMap());
                                            ((Map) identityHashMap2.get(s10U5)).put(abstractC0727q0A.getType(), s10A);
                                        }
                                        C3133uU c3133uU = new C3133uU(s10A, s10U5, abstractC0727q0A.getType());
                                        c3133uU.b(c1248OsA0.Y0());
                                        setC2.add(c3133uU);
                                        boolean z4 = c;
                                        if (!z4 && interfaceC2641noA.j() != c2115gt2) {
                                            throw new AssertionError();
                                        }
                                        interfaceC2641noA.previous();
                                        if (next.D()) {
                                            b4S = b4;
                                            c3332x4A = interfaceC2641noA.a(c2409kl, b4S, this.b);
                                        } else {
                                            b4S = b4;
                                            c3332x4A = next;
                                        }
                                        if (c3332x4A != next) {
                                            z = true;
                                            next.a(c2409kl, next.p().size() - 1).add(c3133uU);
                                            c0827Af = new C0827Af(c2409kl, 1);
                                            InterfaceC2641no interfaceC2641noA2 = c3332x4A.a(c2409kl);
                                            if (!z4 && interfaceC2641noA2.o() != c2115gt2) {
                                                throw new AssertionError();
                                            }
                                            interfaceC2641noA2.next();
                                            interfaceC2641noA = interfaceC2641noA2;
                                        } else {
                                            z = true;
                                            interfaceC2641noA.add(c3133uU);
                                            if (!z4 && interfaceC2641noA.o() != c2115gt2) {
                                                throw new AssertionError();
                                            }
                                            interfaceC2641noA.next();
                                        }
                                    } else {
                                        b4S = b4;
                                        z = true;
                                    }
                                    set2 = set;
                                    set2.addAll(s10U5.a());
                                    c3579q2 = c3579q;
                                    c3579q2.a(s10U5);
                                    if (s10U5.A()) {
                                        s10U5.e(c2115gt2);
                                        c2115gt2.b(s10U5, s10A);
                                    } else {
                                        s10U5.a(s10A, AbstractC1048Hm.c(c2115gt2), AbstractC2178hm.s());
                                    }
                                }
                            }
                            c3579q2 = c3579q;
                            set2 = set;
                            b4S = b4;
                            z = true;
                        }
                    }
                    o5VarH = o5VarH;
                    c3579q2 = c3579q;
                    set2 = set;
                    b4S = b4;
                    z = true;
                }
                c3579q3 = c3579q2;
                setC = set2;
                b4S = b4S;
                z2 = z;
                o5VarH = o5VarH;
            }
            o5VarH = o5VarH;
        }
        Set set3 = setC;
        C3579q c3579q4 = c3579q3;
        c3579q4.a((Set) null);
        set3.addAll(c3579q4.b());
        if (!set3.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) set3);
        }
        if (!c && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }
}
