package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0816z2;
import com.android.tools.r8.ir.optimize.C3585t;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class IW extends AbstractC2098gi {
    static final /* synthetic */ boolean k = true;
    private final JW i;
    private final IdentityHashMap j;

    private IW(C0702m c0702m, C2409kl c2409kl, com.android.tools.r8.ir.optimize.info.n nVar) {
        super(c0702m, c2409kl, nVar);
        this.j = new IdentityHashMap();
        this.i = OW.a(c2409kl.h().a());
    }

    public static OW a(C0702m c0702m, C2409kl c2409kl, C3585t c3585t, com.android.tools.r8.ir.optimize.info.n nVar, com.android.tools.r8.utils.y3 y3Var) {
        boolean z = k;
        if (!z && !c0702m.f().i()) {
            throw new AssertionError();
        }
        if (!z && !c0702m.q()) {
            throw new AssertionError();
        }
        if (!z && !c2409kl.h().e().u1()) {
            throw new AssertionError();
        }
        y3Var.a("Analyze class initializer");
        IW iw = new IW(c0702m.i0(), c2409kl, nVar);
        iw.a(c3585t);
        OW owA = iw.i.a();
        y3Var.c();
        return owA;
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final IW b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final boolean b(com.android.tools.r8.graph.T0 t0) {
        return t0.E0() && t0.J0() == this.c.t() && ((C3733i) this.a.f()).b(t0, this.c.e());
    }

    /* JADX WARN: Code duplicated, block: B:105:0x0149 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:106:0x0113 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:107:0x0107 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:110:0x017c A[EDGE_INSN: B:110:0x017c->B:96:0x017c BREAK  A[LOOP:1: B:52:0x00cd->B:118:0x00cd], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:0x00e5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:117:0x00e1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:120:0x00cd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x00d3  */
    private AbstractC2706oV b(S10 s10) {
        int iR2;
        Iterator it;
        com.android.tools.r8.graph.T0 t0;
        AbstractC2412ko abstractC2412ko;
        int iG2;
        com.android.tools.r8.graph.T0 t0C;
        C1736c3 c1736c3V;
        int iN2;
        AbstractC2412ko abstractC2412ko2 = s10.b;
        abstractC2412ko2.getClass();
        AbstractC2706oV abstractC2706oVA = null;
        if (!(abstractC2412ko2 instanceof AG) && !abstractC2412ko2.b2()) {
            return null;
        }
        B1 b1 = (B1) this.j.get(s10);
        if (b1 != null) {
            if (b1.B()) {
                AbstractC2706oV abstractC2706oVP = b1.p();
                AbstractC3121uK abstractC3121uKT = abstractC2706oVP.t();
                abstractC3121uKT.getClass();
                if (abstractC3121uKT instanceof C1858dh) {
                    return abstractC2706oVP;
                }
            }
            return null;
        }
        AG agR0 = s10.b.r0();
        C1385Ts c1385TsE0 = s10.b.e0();
        if (!k && agR0 == null && c1385TsE0 == null) {
            throw new AssertionError();
        }
        if ((agR0 != null ? agR0.h : c1385TsE0.Q2()).b(this.a.a()) == this.c.a().d && !s10.z() && !s10.C()) {
            if (agR0 != null) {
                S10 s10J2 = agR0.J2();
                iR2 = s10J2.J() ? s10J2.n().E().N2() : -1;
            } else {
                iR2 = c1385TsE0.R2();
            }
            if (iR2 >= 1) {
                C0767v2[] c0767v2Arr = new C0767v2[iR2];
                AbstractC3121uK[] abstractC3121uKArr = new AbstractC3121uK[iR2];
                if (c1385TsE0 != null) {
                    List listH1 = c1385TsE0.h1();
                    int i = 0;
                    while (true) {
                        if (i < iR2) {
                            if (a(abstractC3121uKArr, c0767v2Arr, i, (S10) ((ArrayList) listH1).get(i))) {
                                i++;
                            }
                        } else {
                            it = s10.a(C1552Zd.a()).iterator();
                            t0 = null;
                            while (true) {
                                if (!it.hasNext()) {
                                    abstractC2412ko = (AbstractC2412ko) it.next();
                                    iG2 = abstractC2412ko.G2();
                                    if (iG2 != 8) {
                                        c1736c3V = abstractC2412ko.v();
                                        if (c1736c3V.J2() == s10 || !c1736c3V.K2().J() || (iN2 = c1736c3V.K2().n().E().N2()) < 0 || iN2 >= iR2 || !a(abstractC3121uKArr, c0767v2Arr, iN2, c1736c3V.value())) {
                                            break;
                                        }
                                    } else if (iG2 != 9) {
                                        if (iG2 == 60 || (t0C = this.c.a().c(abstractC2412ko.G0().getField())) == null || t0 != null) {
                                            break;
                                        }
                                        t0 = t0C;
                                    } else if (abstractC2412ko.b().C()) {
                                        break;
                                    }
                                } else {
                                    if (t0 != null || com.android.tools.r8.utils.B.b(null, abstractC3121uKArr)) {
                                        break;
                                        break;
                                    }
                                    if (!k || !com.android.tools.r8.utils.B.b(null, c0767v2Arr)) {
                                        C1 c1B = this.a.b();
                                        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) t0.getReference();
                                        C1858dh c1858dh = new C1858dh(abstractC3121uKArr, c0767v2Arr);
                                        c1B.getClass();
                                        abstractC2706oVA = C1.a(y0, c1858dh);
                                        break;
                                    }
                                    throw new AssertionError();
                                }
                            }
                        }
                    }
                } else {
                    it = s10.a(C1552Zd.a()).iterator();
                    t0 = null;
                    while (true) {
                        if (!it.hasNext()) {
                            if (t0 != null) {
                                break;
                            }
                            if (!k) {
                            }
                            C1 c1B2 = this.a.b();
                            com.android.tools.r8.graph.Y0 y1 = (com.android.tools.r8.graph.Y0) t0.getReference();
                            C1858dh c1858dh2 = new C1858dh(abstractC3121uKArr, c0767v2Arr);
                            c1B2.getClass();
                            abstractC2706oVA = C1.a(y1, c1858dh2);
                            break;
                        }
                        abstractC2412ko = (AbstractC2412ko) it.next();
                        iG2 = abstractC2412ko.G2();
                        if (iG2 != 8) {
                            c1736c3V = abstractC2412ko.v();
                            if (c1736c3V.J2() == s10) {
                                break;
                            }
                        } else if (iG2 != 9) {
                            if (iG2 == 60) {
                                break;
                            }
                            t0 = t0C;
                        } else if (abstractC2412ko.b().C()) {
                            break;
                            break;
                        }
                    }
                }
            }
        }
        this.j.put(s10, abstractC2706oVA == null ? C2892r10.F() : abstractC2706oVA);
        return abstractC2706oVA;
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final void a(C3585t c3585t) {
        super.a(c3585t);
        c3585t.a(new BiConsumer() { // from class: com.android.tools.r8.internal.IW$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((com.android.tools.r8.graph.T0) obj, (com.android.tools.r8.graph.B2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.B2 b2) {
        if (this.g.containsKey(t0) || !((C3733i) this.a.f()).a(t0, this.c.e())) {
            return;
        }
        C1 c1B = this.a.b();
        if (b2.c1()) {
            this.d.a(t0, this.a, c1B.a(b2.I0().j1()));
        } else if (b2.e1()) {
            this.d.a(t0, this.a, c1B.a((C0759u2) b2.K0().j1()));
        } else if (!(b2 instanceof C0816z2) && !k) {
            throw new AssertionError(b2.getClass().getName());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final boolean a(com.android.tools.r8.graph.T0 t0) {
        return t0.E0() && t0.J0() == this.c.t() && ((C3733i) this.a.f()).a(t0, this.c.e());
    }

    @Override // com.android.tools.r8.internal.AbstractC2098gi
    final void a(com.android.tools.r8.graph.T0 t0, AbstractC1182Mh abstractC1182Mh, S10 s10) {
        a(t0, s10, a(t0, s10), false);
    }

    final void a(com.android.tools.r8.graph.T0 t0, S10 s10, B1 b1, boolean z) {
        this.i.a(t0, b1, this.a.a());
        if (((C3733i) this.a.f()).b((com.android.tools.r8.graph.O0) t0)) {
            return;
        }
        this.d.a(t0, this.a, b1);
        if (t0.getType().X0()) {
            C0767v2 type = t0.getType();
            C0702m c0702m = this.a;
            type.getClass();
            C1207Nf c1207NfA = AbstractC1154Lf.a(c0702m, AbstractC3472z00.a(type, WH.i(), (C0702m<?>) c0702m));
            C0702m c0702m2 = this.a;
            s10.getClass();
            C1207Nf c1207NfA2 = AbstractC1154Lf.a(c0702m2, s10);
            if (c1207NfA2.b(this.a, c1207NfA)) {
                if (z && c1207NfA2.f().e()) {
                    if (!k && !c1207NfA2.o().I()) {
                        throw new AssertionError();
                    }
                    c1207NfA2 = c1207NfA2.a(WH.i());
                }
                this.d.a(t0, c1207NfA2);
            }
        }
    }

    public final void a(com.android.tools.r8.graph.T0 t0, S10 s10, com.android.tools.r8.graph.B2 b2) {
        if (b2 != com.android.tools.r8.graph.I2.b) {
            return;
        }
        a(t0, s10, UH.a(a(t0, s10)), true);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x0063  */
    private B1 a(com.android.tools.r8.graph.T0 t0, S10 s10) {
        AbstractC2706oV abstractC2706oVB;
        S10 s10M = s10.m();
        B1 b1A = s10M.a(this.a, this.c);
        if (b1A.g()) {
            return b1A;
        }
        boolean z = k;
        if (!z && s10M.m() != s10M) {
            throw new AssertionError();
        }
        if (!this.c.a().B1()) {
            abstractC2706oVB = null;
        } else {
            if (!z && s10M.m() != s10M) {
                throw new AssertionError();
            }
            if (s10M.i()) {
                abstractC2706oVB = null;
            } else {
                AbstractC2412ko abstractC2412ko = s10M.b;
                abstractC2412ko.getClass();
                if ((abstractC2412ko instanceof AG) || abstractC2412ko.b2()) {
                    abstractC2706oVB = b(s10M);
                } else if (s10M.b.p2()) {
                    abstractC2706oVB = a(s10M);
                } else {
                    abstractC2706oVB = null;
                }
            }
        }
        if (abstractC2706oVB != null) {
            return abstractC2706oVB;
        }
        C1 c1B = this.a.b();
        com.android.tools.r8.graph.Y0 reference = t0.getReference();
        AbstractC3121uK abstractC3121uKB = AbstractC3203vK.b(this.a, this.c, s10M);
        c1B.getClass();
        return C1.a(reference, abstractC3121uKB);
    }

    private boolean a(AbstractC3121uK[] abstractC3121uKArr, C0767v2[] c0767v2Arr, int i, S10 s10) {
        B1 b1A;
        S10 s10M = s10.m();
        if (s10M.i()) {
            return false;
        }
        AbstractC2412ko abstractC2412koQ = s10M.q();
        abstractC2412koQ.getClass();
        if (abstractC2412koQ instanceof QW) {
            return false;
        }
        AbstractC3121uK abstractC3121uKB = abstractC2412koQ.p2() ? AbstractC3203vK.b(this.a, this.c, abstractC2412koQ.b()) : null;
        if (abstractC3121uKB != null && !abstractC3121uKB.e()) {
            C3733i c3733i = (C3733i) this.a.f();
            com.android.tools.r8.graph.Y0 y0 = this.a.a().r4.b;
            com.android.tools.r8.graph.o5 o5Var = this.c;
            c3733i.getClass();
            com.android.tools.r8.graph.T0 t0S = c3733i.a(y0.e, y0, o5Var).s();
            if (t0S == null || (b1A = abstractC3121uKB.a(t0S)) == null || !(b1A instanceof C2853qV) || b1A.e().G() != i || abstractC3121uKArr[i] != null) {
                return false;
            }
            if (!k && !abstractC2412koQ.p2()) {
                throw new AssertionError();
            }
            c0767v2Arr[i] = abstractC2412koQ.t0().J2();
            abstractC3121uKArr[i] = abstractC3121uKB;
            return true;
        }
        return false;
    }

    private AbstractC2706oV a(S10 s10) {
        AbstractC2706oV abstractC2706oVB;
        AbstractC0727q0 abstractC0727q0C;
        if (!k && !s10.b(new IW$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
        DG dgT0 = s10.b.t0();
        if ((dgT0.h != this.c.t() && ((abstractC0727q0C = this.a.c(dgT0.h)) == null || abstractC0727q0C.f != this.c.t())) || s10.z() || s10.C()) {
            return null;
        }
        com.android.tools.r8.graph.T0 t0 = null;
        for (AbstractC2412ko abstractC2412ko : s10.h0()) {
            int iG2 = abstractC2412ko.G2();
            if (iG2 == 8) {
                C1736c3 c1736c3V = abstractC2412ko.v();
                if (c1736c3V.value().m() == s10 && (abstractC2706oVB = b(c1736c3V.J2())) != null) {
                    AbstractC3121uK abstractC3121uKT = abstractC2706oVB.t();
                    abstractC3121uKT.getClass();
                    if (abstractC3121uKT instanceof C1858dh) {
                    }
                }
                return null;
            }
            if (iG2 != 33) {
                if (iG2 != 60) {
                    return null;
                }
                com.android.tools.r8.graph.T0 t0C = this.c.a().c(abstractC2412ko.G0().getField());
                if (t0C != null && t0C.f.R()) {
                    if (t0 != null) {
                        return null;
                    }
                    t0 = t0C;
                }
            } else {
                C1166Ls c1166LsZ = abstractC2412ko.Z();
                if (!this.a.a().b(c1166LsZ.S2()) || c1166LsZ.U2() != s10) {
                    return null;
                }
            }
        }
        if (t0 == null) {
            return null;
        }
        C1 c1B = this.a.b();
        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) t0.getReference();
        AbstractC3121uK abstractC3121uKB = AbstractC3203vK.b(this.a, this.c, s10);
        c1B.getClass();
        return C1.a(y0, abstractC3121uKB);
    }
}
