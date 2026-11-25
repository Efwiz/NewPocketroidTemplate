package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2 {
    private static O3 g = O3.a();
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final P2 d;
    private final SE b = new SE();
    private final Set c = OU.c();
    private final IdentityHashMap e = new IdentityHashMap();
    private final C1678bF f = C1678bF.b();

    C2(C0702m c0702m, P2 p2) {
        this.a = c0702m;
        this.d = p2;
    }

    public final synchronized void a(Set set) {
        this.c.addAll(set);
    }

    public final synchronized void a(IdentityHashMap identityHashMap) {
        this.e.putAll(identityHashMap);
    }

    final C1678bF a() {
        return this.f;
    }

    final boolean a(com.android.tools.r8.graph.o5 o5Var) {
        boolean zContains = this.c.contains(o5Var.getReference());
        if (h || o5Var.e().R0() || !zContains) {
            return zContains;
        }
        throw new AssertionError();
    }

    final void a(com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Argument propagation scanner");
        for (AbstractC1084Is abstractC1084Is : c2409kl.b(new Predicate() { // from class: com.android.tools.r8.internal.C2$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).U1();
            }
        })) {
            if (abstractC1084Is.Y1()) {
                a(abstractC1084Is.b0(), o5Var, y3Var);
            } else if (abstractC1084Is instanceof C1113Js) {
                C0699l2 c0699l2 = abstractC1084Is.Y().Q2().f;
                com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.a.f()).b(c0699l2.u0(), c0699l2.f).o();
                if (cVarO != null && cVarO.d().e0()) {
                    this.f.b(cVarO.s(), C2508m10.k());
                }
            }
        }
        y3Var.c();
    }

    private void a(final AbstractC1305Qs abstractC1305Qs, final com.android.tools.r8.graph.o5 o5Var, final com.android.tools.r8.utils.y3 y3Var) {
        com.android.tools.r8.graph.I4.c<?> cVarO;
        C0684j2 c0684j2S2 = abstractC1305Qs.S2();
        if (c0684j2S2.B0().M0()) {
            return;
        }
        if ((this.a.Y().n1.d && abstractC1305Qs.Z1() && abstractC1305Qs.c0().U2().b(this.a)) || (cVarO = ((C3733i) this.a.f()).h(c0684j2S2).o()) == null || !cVarO.d().e0()) {
            return;
        }
        com.android.tools.r8.graph.o5 o5VarS = cVarO.s();
        if (o5VarS.e().D1().c()) {
            if (!h && !o5VarS.e().D1().d()) {
                throw new AssertionError();
            }
            return;
        }
        if (((ArrayList) abstractC1305Qs.J2()).size() == o5VarS.e().h1() && abstractC1305Qs.d2() == o5VarS.getAccessFlags().o()) {
            if (!abstractC1305Qs.X1() || cVarO.a().isInterface()) {
                if (abstractC1305Qs.e2()) {
                    AbstractC0748t0 abstractC0748t0D = cVarO.d(o5Var.a(), this.a);
                    if (abstractC0748t0D == null) {
                        return;
                    }
                    if (abstractC0748t0D.k()) {
                        o5VarS = abstractC0748t0D.g0();
                    } else {
                        throw new C3405y10("Expected super target of a non-library override to be a program method (resolved program method: " + o5VarS + ", super non-program method: " + abstractC0748t0D + Constant.AFTER_QUTO);
                    }
                }
                final com.android.tools.r8.graph.o5 o5Var2 = o5VarS;
                y3Var.a("Add method state");
                this.f.a(this.a, a(o5Var2, abstractC1305Qs), new Function() { // from class: com.android.tools.r8.internal.C2$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a(abstractC1305Qs, o5Var2, o5Var, y3Var, (YE) obj);
                    }
                }, y3Var);
                y3Var.c();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:22:0x0052  */
    /* JADX WARN: Code duplicated, block: B:32:0x007b  */
    /* JADX WARN: Code duplicated, block: B:33:0x008a  */
    public YE a(AbstractC1305Qs abstractC1305Qs, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, com.android.tools.r8.utils.y3 y3Var, YE ye) {
        C1207Nf c1207NfA;
        YE yeA;
        boolean z = h;
        if (!z && ye.isUnknown()) {
            throw new AssertionError();
        }
        y3Var.a("Compute method state for invoke");
        if (!o5Var.e().f.N()) {
            if (!this.c.contains(a(o5Var, abstractC1305Qs))) {
                if (!z && !ye.d() && !ye.c()) {
                    throw new AssertionError();
                }
                yeA = a(abstractC1305Qs.c0(), o5Var, o5Var2, ye.i());
            } else {
                if (z && !ye.d() && !ye.h()) {
                    throw new AssertionError();
                }
                com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(abstractC1305Qs.h(this.a, o5Var2));
                InterfaceC1935ec interfaceC1935ecG = ye.g();
                if (abstractC1305Qs.Z1()) {
                    S10 s10C = abstractC1305Qs.c(0);
                    C0702m c0702m = this.a;
                    s10C.getClass();
                    c1207NfA = AbstractC1154Lf.a(c0702m, s10C);
                } else {
                    c1207NfA = null;
                }
                yeA = a(abstractC1305Qs, o5Var, o5VarA, o5Var2, interfaceC1935ecG, c1207NfA);
            }
        } else {
            if (z) {
            }
            com.android.tools.r8.graph.o5 o5VarA2 = AbstractC0748t0.a(abstractC1305Qs.h(this.a, o5Var2));
            InterfaceC1935ec interfaceC1935ecG2 = ye.g();
            if (abstractC1305Qs.Z1()) {
                S10 s10C2 = abstractC1305Qs.c(0);
                C0702m c0702m2 = this.a;
                s10C2.getClass();
                c1207NfA = AbstractC1154Lf.a(c0702m2, s10C2);
            } else {
                c1207NfA = null;
            }
            yeA = a(abstractC1305Qs, o5Var, o5VarA2, o5Var2, interfaceC1935ecG2, c1207NfA);
        }
        y3Var.c();
        return yeA;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ZE a(AbstractC1332Rs abstractC1332Rs, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, InterfaceC2319jc interfaceC2319jc) {
        YE yeK;
        S10 s10U2 = abstractC1332Rs.U2();
        C0702m c0702m = this.a;
        s10U2.getClass();
        C1207Nf c1207NfA = AbstractC1154Lf.a(c0702m, s10U2);
        if (c1207NfA.l()) {
            if (h || this.a.a(new Supplier() { // from class: com.android.tools.r8.internal.C2$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Boolean.FALSE;
                }
            })) {
                return C2508m10.k();
            }
            throw new AssertionError("b/250634405");
        }
        com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(abstractC1332Rs.h(this.a, o5Var2));
        C1207Nf c1207NfA2 = a(o5Var, o5VarA, c1207NfA);
        ZE ze = (ZE) interfaceC2319jc;
        ze.getClass();
        if (ze instanceof C2247ic) {
            yeK = interfaceC2319jc.j().a(c1207NfA2);
        } else {
            yeK = C3480z5.k();
        }
        if (yeK.c()) {
            if (h) {
                return C2508m10.k();
            }
            throw new AssertionError();
        }
        if (yeK.isUnknown()) {
            return C3480z5.k();
        }
        return (ZE) C2247ic.a(c1207NfA2, a(abstractC1332Rs, o5Var, o5VarA, o5Var2, yeK.g(), c1207NfA));
    }

    private C1207Nf a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, C1207Nf c1207Nf) {
        C1207Nf c1207NfA;
        if (o5Var2 != null) {
            c1207NfA = AbstractC1154Lf.a(o5Var2.t().c(this.a).b());
        } else {
            c1207NfA = c1207Nf.a(WH.i());
        }
        boolean z = h;
        if (!z && !c1207NfA.o().N().g()) {
            throw new AssertionError();
        }
        C0684j2 c0684j2 = (C0684j2) this.e.get(o5Var.getReference());
        if (c0684j2 != null) {
            return c1207NfA.equals(AbstractC1154Lf.a(this.a, c0684j2.B0().c(this.a).b())) ? AbstractC1154Lf.n() : c1207NfA;
        }
        if (z) {
            return c1207NfA;
        }
        throw new AssertionError("Unexpected virtual method without root: " + o5Var);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x021b  */
    /* JADX WARN: Code duplicated, block: B:101:0x0220  */
    /* JADX WARN: Code duplicated, block: B:19:0x0054  */
    /* JADX WARN: Code duplicated, block: B:35:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:37:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:43:0x010a  */
    /* JADX WARN: Code duplicated, block: B:46:0x011a  */
    /* JADX WARN: Code duplicated, block: B:49:0x012c  */
    /* JADX WARN: Code duplicated, block: B:50:0x013e  */
    /* JADX WARN: Code duplicated, block: B:61:0x015b  */
    /* JADX WARN: Code duplicated, block: B:62:0x0161  */
    /* JADX WARN: Code duplicated, block: B:64:0x0167  */
    /* JADX WARN: Code duplicated, block: B:65:0x0179  */
    /* JADX WARN: Code duplicated, block: B:67:0x017f  */
    /* JADX WARN: Code duplicated, block: B:68:0x018d  */
    /* JADX WARN: Code duplicated, block: B:76:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:78:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:80:0x01c0  */
    /* JADX WARN: Code duplicated, block: B:81:0x01c5  */
    /* JADX WARN: Code duplicated, block: B:83:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:85:0x01dc  */
    /* JADX WARN: Code duplicated, block: B:90:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:91:0x0204  */
    /* JADX WARN: Multi-variable type inference failed */
    private InterfaceC2013fc a(AbstractC1305Qs abstractC1305Qs, com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, com.android.tools.r8.graph.o5 o5Var3, InterfaceC1935ec interfaceC1935ec, C1207Nf c1207Nf) {
        XE xeA;
        int i;
        S10 s10A;
        C0767v2 c0767v2A;
        AbstractC3472z00 abstractC3472z00C;
        B1 b1A;
        AbstractC3122uL c2548mc;
        AbstractC1154Lf abstractC1154LfA;
        WH whN;
        AbstractC3122uL c1497Xb;
        RE reA;
        C0684j2 reference;
        YE yeA;
        boolean zIsUnknown;
        AbstractC3122uL c2716oc;
        ArrayList arrayList = new ArrayList(((ArrayList) abstractC1305Qs.J2()).size());
        if (o5Var2 != null) {
            xeA = this.d.a(o5Var2);
        } else {
            xeA = XE.b;
        }
        boolean z = true;
        if (!abstractC1305Qs.Z1()) {
            i = 0;
        } else {
            if (!h && c1207Nf == null) {
                throw new AssertionError();
            }
            abstractC1305Qs.c0();
            AbstractC3053tL abstractC3053tLA = xeA.a(0);
            ZE ze = (ZE) interfaceC1935ec;
            ze.getClass();
            if (ze instanceof C1853dc) {
                AbstractC3122uL abstractC3122uLA = interfaceC1935ec.b().a(0);
                abstractC3122uLA.getClass();
                if (abstractC3122uLA instanceof C2582n10) {
                    c2716oc = C2582n10.g();
                } else if (abstractC3053tLA.a() || F20.a(this.a, o5Var, c1207Nf).m()) {
                    c2716oc = C2582n10.g();
                } else {
                    c2716oc = new C2716oc(c1207Nf, Collections.emptySet());
                }
            } else if (abstractC3053tLA.a()) {
                c2716oc = C2582n10.g();
            } else {
                c2716oc = new C2716oc(c1207Nf, Collections.emptySet());
            }
            arrayList.add(c2716oc);
            i = 1;
        }
        while (i < ((ArrayList) abstractC1305Qs.J2()).size()) {
            S10 s10C = abstractC1305Qs.c(i);
            xeA.a(i);
            ZE ze2 = (ZE) interfaceC1935ec;
            ze2.getClass();
            if (ze2 instanceof C1853dc) {
                AbstractC3122uL abstractC3122uLA2 = interfaceC1935ec.b().a(i);
                abstractC3122uLA2.getClass();
                if (abstractC3122uLA2 instanceof C2582n10) {
                    c2548mc = C2582n10.g();
                } else {
                    O3 o3 = g;
                    s10C.getClass();
                    s10A = s10C.a(o3, CM.a());
                    c0767v2A = abstractC1305Qs.S2().a(i, abstractC1305Qs instanceof C1721bt);
                    abstractC3472z00C = c0767v2A.c(this.a);
                    if (s10A.H()) {
                        reA = this.b.a(o5Var3, s10A.q().q().J2());
                        C1678bF c1678bF = this.f;
                        if (o5Var3.e().f.N() && !a(o5Var3)) {
                            reference = (C0684j2) this.e.get(o5Var3.getReference());
                        } else {
                            reference = o5Var3.getReference();
                        }
                        yeA = c1678bF.a(reference);
                        if (yeA.c()) {
                            yeA = yeA.j().a(AbstractC1154Lf.n());
                        }
                        if (yeA.h()) {
                            AbstractC3122uL abstractC3122uLA3 = yeA.b().a(reA.a());
                            abstractC3122uLA3.getClass();
                            zIsUnknown = abstractC3122uLA3 instanceof C2582n10;
                        } else {
                            if (h && !yeA.d() && !yeA.isUnknown()) {
                                throw new AssertionError();
                            }
                            zIsUnknown = yeA.isUnknown();
                        }
                        if (zIsUnknown) {
                            c2548mc = C2582n10.g();
                        } else {
                            if (abstractC3472z00C.w()) {
                                c1497Xb = new C1624ac(H5.F(), AbstractC1154Lf.b(), com.android.tools.r8.utils.T2.a(reA));
                            } else if (abstractC3472z00C.r()) {
                                c1497Xb = new C1497Xb(WH.a(), com.android.tools.r8.utils.T2.a(reA));
                            } else {
                                if (h && !abstractC3472z00C.H()) {
                                    throw new AssertionError();
                                }
                                c1497Xb = new C2548mc(H5.F(), com.android.tools.r8.utils.T2.a(reA));
                            }
                            c2548mc = c1497Xb;
                        }
                    } else if (abstractC3472z00C.r()) {
                        whN = s10C.w().N();
                        if (whN.g()) {
                            c2548mc = C2582n10.g();
                        } else {
                            c1497Xb = new C1497Xb(whN, Collections.emptySet());
                            c2548mc = c1497Xb;
                        }
                    } else {
                        b1A = s10C.a(this.a, o5Var3);
                        if (abstractC3472z00C.w()) {
                            abstractC1154LfA = F20.a(this.a, AbstractC1154Lf.a(this.a, s10C), c0767v2A);
                            if (!b1A.isUnknown() && abstractC1154LfA.m()) {
                                c2548mc = C2582n10.g();
                            } else {
                                c2548mc = new C1624ac(b1A, abstractC1154LfA, Collections.emptySet());
                            }
                        } else {
                            if (h && !abstractC3472z00C.H()) {
                                throw new AssertionError();
                            }
                            if (b1A.isUnknown()) {
                                c2548mc = C2582n10.g();
                            } else {
                                c2548mc = new C2548mc(b1A, Collections.emptySet());
                            }
                        }
                    }
                }
            } else {
                O3 o4 = g;
                s10C.getClass();
                s10A = s10C.a(o4, CM.a());
                c0767v2A = abstractC1305Qs.S2().a(i, abstractC1305Qs instanceof C1721bt);
                abstractC3472z00C = c0767v2A.c(this.a);
                if (s10A.H()) {
                    reA = this.b.a(o5Var3, s10A.q().q().J2());
                    C1678bF c1678bF2 = this.f;
                    if (o5Var3.e().f.N()) {
                        reference = o5Var3.getReference();
                    } else {
                        reference = o5Var3.getReference();
                    }
                    yeA = c1678bF2.a(reference);
                    if (yeA.c()) {
                        yeA = yeA.j().a(AbstractC1154Lf.n());
                    }
                    if (yeA.h()) {
                        AbstractC3122uL abstractC3122uLA4 = yeA.b().a(reA.a());
                        abstractC3122uLA4.getClass();
                        zIsUnknown = abstractC3122uLA4 instanceof C2582n10;
                    } else {
                        if (h) {
                        }
                        zIsUnknown = yeA.isUnknown();
                    }
                    if (zIsUnknown) {
                        c2548mc = C2582n10.g();
                    } else {
                        if (abstractC3472z00C.w()) {
                            c1497Xb = new C1624ac(H5.F(), AbstractC1154Lf.b(), com.android.tools.r8.utils.T2.a(reA));
                        } else if (abstractC3472z00C.r()) {
                            c1497Xb = new C1497Xb(WH.a(), com.android.tools.r8.utils.T2.a(reA));
                        } else {
                            if (h) {
                            }
                            c1497Xb = new C2548mc(H5.F(), com.android.tools.r8.utils.T2.a(reA));
                        }
                        c2548mc = c1497Xb;
                    }
                } else if (abstractC3472z00C.r()) {
                    whN = s10C.w().N();
                    if (whN.g()) {
                        c2548mc = C2582n10.g();
                    } else {
                        c1497Xb = new C1497Xb(whN, Collections.emptySet());
                        c2548mc = c1497Xb;
                    }
                } else {
                    b1A = s10C.a(this.a, o5Var3);
                    if (abstractC3472z00C.w()) {
                        abstractC1154LfA = F20.a(this.a, AbstractC1154Lf.a(this.a, s10C), c0767v2A);
                        if (!b1A.isUnknown()) {
                            c2548mc = new C1624ac(b1A, abstractC1154LfA, Collections.emptySet());
                        } else {
                            c2548mc = new C1624ac(b1A, abstractC1154LfA, Collections.emptySet());
                        }
                    } else {
                        if (h) {
                        }
                        if (b1A.isUnknown()) {
                            c2548mc = C2582n10.g();
                        } else {
                            c2548mc = new C2548mc(b1A, Collections.emptySet());
                        }
                    }
                }
            }
            arrayList.add(c2548mc);
            i++;
        }
        if (!abstractC1305Qs.L2().Y0() && !abstractC1305Qs.g1()) {
            z = false;
        }
        return C1853dc.a(arrayList, z);
    }

    private C0684j2 a(com.android.tools.r8.graph.o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        if (o5Var.e().f.N()) {
            return o5Var.getReference();
        }
        abstractC1305Qs.getClass();
        if (abstractC1305Qs instanceof C1248Os) {
            if (h || !a(o5Var)) {
                return (C0684j2) this.e.get(o5Var.getReference());
            }
            throw new AssertionError();
        }
        boolean z = h;
        if (!z && !(abstractC1305Qs instanceof C1798ct) && !abstractC1305Qs.f2()) {
            throw new AssertionError();
        }
        if (a(o5Var)) {
            return o5Var.getReference();
        }
        C0684j2 c0684j2 = (C0684j2) this.e.get(o5Var.getReference());
        if (!z && c0684j2 == null) {
            throw new AssertionError();
        }
        if (z || !a(o5Var) || c0684j2 == o5Var.getReference()) {
            return c0684j2;
        }
        throw new AssertionError();
    }
}
