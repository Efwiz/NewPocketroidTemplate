package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2148hL {
    static final /* synthetic */ boolean o = true;
    final com.android.tools.r8.graph.o5 a;
    final List b;
    int c;
    int d;
    int e;
    ArrayList f;
    ArrayList g;
    ArrayList h;
    int i;
    C0767v2 j;
    S10 k;
    int l;
    int m = -1;
    final /* synthetic */ C2225iL n;

    AbstractC2148hL(C2225iL c2225iL, com.android.tools.r8.graph.o5 o5Var, List list) {
        this.n = c2225iL;
        this.a = o5Var;
        this.b = list;
        a(0);
    }

    private boolean b(S10 s10) {
        if (!s10.w().r()) {
            return true;
        }
        if (this.n.d.Y().n1.B0 && (this.n.d.Y().f instanceof ClassFileConsumer)) {
            return true;
        }
        AbstractC3472z00 abstractC3472z00R = s10.w().a().R();
        if (abstractC3472z00R.H()) {
            return true;
        }
        if (abstractC3472z00R.w()) {
            return abstractC3472z00R.b().S().f();
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:110:0x021e  */
    /* JADX WARN: Code duplicated, block: B:112:0x0236  */
    /* JADX WARN: Code duplicated, block: B:113:0x0243  */
    /* JADX WARN: Code duplicated, block: B:114:0x0248  */
    /* JADX WARN: Code duplicated, block: B:116:0x0266  */
    /* JADX WARN: Code duplicated, block: B:117:0x0273  */
    /* JADX WARN: Code duplicated, block: B:134:0x029a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:140:0x0293 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:141:0x0289 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:142:0x0036 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:143:0x02a5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:144:0x02a0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x00d0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x00e4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:155:0x00bc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:156:0x00bc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:16:0x0040  */
    /* JADX WARN: Code duplicated, block: B:27:0x0071  */
    /* JADX WARN: Code duplicated, block: B:28:0x007f  */
    /* JADX WARN: Code duplicated, block: B:34:0x009e  */
    /* JADX WARN: Code duplicated, block: B:37:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:40:0x00c2  */
    /* JADX WARN: Code duplicated, block: B:52:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:61:0x0118  */
    /* JADX WARN: Code duplicated, block: B:62:0x0126  */
    /* JADX WARN: Code duplicated, block: B:63:0x012c  */
    /* JADX WARN: Code duplicated, block: B:76:0x0151 A[LOOP:2: B:76:0x0151->B:96:0x01f9, LOOP_START, PHI: r2
      0x0151: PHI (r2v5 int) = (r2v0 int), (r2v6 int) binds: [B:75:0x014f, B:96:0x01f9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:78:0x0157  */
    /* JADX WARN: Code duplicated, block: B:80:0x0163  */
    /* JADX WARN: Code duplicated, block: B:81:0x016e  */
    /* JADX WARN: Code duplicated, block: B:91:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:93:0x01c2 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:95:0x01ce  */
    protected final void a() {
        boolean zA;
        int i;
        S10 s10;
        List listH1;
        S10 s11;
        ArrayList arrayList;
        S10 s10B;
        C0767v2 c0767v2A;
        int iZ;
        S10 s10B2;
        C0767v2 c0767v2;
        int iZ2;
        S10 s10M;
        S10 s10B3;
        C0767v2 c0767v3;
        int iZ3;
        Iterator it;
        int i2;
        ArrayList arrayList2;
        N3 n3W;
        int iZ4;
        int i3;
        int i4;
        while (this.d < this.b.size()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) this.b.get(this.d);
            if (abstractC2412ko.y1()) {
                int i5 = this.d;
                if (i5 == this.c) {
                    a(i5 + 1);
                }
            } else {
                if (!(abstractC2412ko instanceof N3)) {
                    zA = a(abstractC2412ko);
                    i = 1;
                }
                if (zA) {
                    this.e += i;
                    if (abstractC2412ko instanceof N3) {
                        n3W = abstractC2412ko.w();
                        if (this.k != null && n3W.N2().m() == this.k) {
                            iZ4 = this.l + (n3W.b().Z() - 1);
                            this.l = iZ4;
                            if (o && iZ4 < 0) {
                                throw new AssertionError();
                            }
                            if (iZ4 == 0) {
                                this.k = null;
                                this.j = this.n.e.D1;
                            }
                        }
                    } else {
                        s10 = this.k;
                        listH1 = abstractC2412ko.h1();
                        if (abstractC2412ko.v1() && abstractC2412ko.x().K2()) {
                            arrayList2 = (ArrayList) listH1;
                            if (arrayList2.get(1) == s10) {
                                S10 s12 = (S10) arrayList2.get(0);
                                arrayList2.set(0, (S10) arrayList2.get(1));
                                arrayList2.set(1, s12);
                            }
                        }
                        s11 = this.k;
                        if (s11 != null) {
                            it = ((ArrayList) listH1).iterator();
                            while (it.hasNext()) {
                                if (((S10) it.next()).m() == this.k) {
                                    i2 = this.l - 1;
                                    this.l = i2;
                                    if (o && i2 < 0) {
                                        throw new AssertionError();
                                    }
                                    if (i2 == 0) {
                                        this.k = null;
                                        this.j = this.n.e.D1;
                                    }
                                }
                            }
                        }
                        if (abstractC2412ko.p2()) {
                            if (o && this.k != null) {
                                throw new AssertionError();
                            }
                            s10B3 = abstractC2412ko.b();
                            c0767v3 = abstractC2412ko.t0().h;
                            iZ3 = s10B3.Z();
                            this.l = iZ3;
                            if (iZ3 == 0) {
                                this.k = null;
                                this.j = this.n.e.D1;
                            } else {
                                this.k = s10B3;
                                this.j = c0767v3;
                            }
                        } else {
                            if (o && !abstractC2412ko.U1() && !abstractC2412ko.y1() && !abstractC2412ko.n1()) {
                                throw new AssertionError();
                            }
                            arrayList = (ArrayList) listH1;
                            if (arrayList.size() > 0) {
                                for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                    s10M = ((S10) arrayList.get(i6)).m();
                                    if (s10M == s11) {
                                        this.h.add(-1);
                                    } else if (!abstractC2412ko.Z1() || (abstractC2412ko instanceof C1433Us)) {
                                        int iIndexOf = this.f.indexOf(s10M);
                                        if (i6 == 0 || iIndexOf == -1) {
                                            this.f.add(s10M);
                                            this.i = s10M.e0() + this.i;
                                            this.g.add(a(s10M, abstractC2412ko.b0(), i6));
                                            this.h.add(Integer.valueOf(this.g.size() - 1));
                                        } else {
                                            this.h.add(Integer.valueOf(iIndexOf));
                                        }
                                    } else {
                                        this.f.add(s10M);
                                        if (abstractC2412ko.Y1()) {
                                            this.g.add(a(s10M, abstractC2412ko.b0(), i6));
                                        } else {
                                            this.g.add(abstractC2412ko.x().J2().a(this.n.e));
                                        }
                                        this.h.add(Integer.valueOf(this.g.size() - 1));
                                    }
                                }
                            }
                            if (!abstractC2412ko.y1() && abstractC2412ko.b() != null) {
                                if (o && this.k != null) {
                                    throw new AssertionError();
                                }
                                if (abstractC2412ko.Y1()) {
                                    s10B2 = abstractC2412ko.b();
                                    c0767v2 = abstractC2412ko.b0().S2().h.e;
                                    iZ2 = s10B2.Z();
                                    this.l = iZ2;
                                    if (iZ2 == 0) {
                                        this.k = null;
                                        this.j = this.n.e.D1;
                                    } else {
                                        this.k = s10B2;
                                        this.j = c0767v2;
                                    }
                                } else {
                                    s10B = abstractC2412ko.b();
                                    c0767v2A = abstractC2412ko.x().J2().a(this.n.e);
                                    iZ = s10B.Z();
                                    this.l = iZ;
                                    if (iZ == 0) {
                                        this.k = null;
                                        this.j = this.n.e.D1;
                                    } else {
                                        this.k = s10B;
                                        this.j = c0767v2A;
                                    }
                                }
                            }
                        }
                    }
                    if (this.e >= this.n.d.Y().T.c) {
                        a(this.c, this.d + 1);
                    } else {
                        this.d++;
                    }
                } else {
                    i3 = this.d;
                    i4 = this.c;
                    if (i3 > i4) {
                        a(i4, i3);
                    } else {
                        a(i3 + 1);
                    }
                }
            }
            i = 0;
            zA = true;
            if (zA) {
                this.e += i;
                if (abstractC2412ko instanceof N3) {
                    n3W = abstractC2412ko.w();
                    if (this.k != null) {
                        iZ4 = this.l + (n3W.b().Z() - 1);
                        this.l = iZ4;
                        if (o) {
                        }
                        if (iZ4 == 0) {
                            this.k = null;
                            this.j = this.n.e.D1;
                        }
                    }
                } else {
                    s10 = this.k;
                    listH1 = abstractC2412ko.h1();
                    if (abstractC2412ko.v1()) {
                        arrayList2 = (ArrayList) listH1;
                        if (arrayList2.get(1) == s10) {
                            S10 s13 = (S10) arrayList2.get(0);
                            arrayList2.set(0, (S10) arrayList2.get(1));
                            arrayList2.set(1, s13);
                        }
                    }
                    s11 = this.k;
                    if (s11 != null) {
                        it = ((ArrayList) listH1).iterator();
                        while (it.hasNext()) {
                            if (((S10) it.next()).m() == this.k) {
                                i2 = this.l - 1;
                                this.l = i2;
                                if (o) {
                                }
                                if (i2 == 0) {
                                    this.k = null;
                                    this.j = this.n.e.D1;
                                }
                            }
                        }
                    }
                    if (abstractC2412ko.p2()) {
                        if (o) {
                        }
                        s10B3 = abstractC2412ko.b();
                        c0767v3 = abstractC2412ko.t0().h;
                        iZ3 = s10B3.Z();
                        this.l = iZ3;
                        if (iZ3 == 0) {
                            this.k = null;
                            this.j = this.n.e.D1;
                        } else {
                            this.k = s10B3;
                            this.j = c0767v3;
                        }
                    } else {
                        if (o) {
                        }
                        arrayList = (ArrayList) listH1;
                        if (arrayList.size() > 0) {
                            while (i6 < arrayList.size()) {
                                s10M = ((S10) arrayList.get(i6)).m();
                                if (s10M == s11) {
                                    this.h.add(-1);
                                } else if (abstractC2412ko.Z1()) {
                                    int iIndexOf2 = this.f.indexOf(s10M);
                                    if (i6 == 0) {
                                        this.f.add(s10M);
                                        this.i = s10M.e0() + this.i;
                                        this.g.add(a(s10M, abstractC2412ko.b0(), i6));
                                        this.h.add(Integer.valueOf(this.g.size() - 1));
                                    } else {
                                        this.f.add(s10M);
                                        this.i = s10M.e0() + this.i;
                                        this.g.add(a(s10M, abstractC2412ko.b0(), i6));
                                        this.h.add(Integer.valueOf(this.g.size() - 1));
                                    }
                                } else {
                                    int iIndexOf3 = this.f.indexOf(s10M);
                                    if (i6 == 0) {
                                        this.f.add(s10M);
                                        this.i = s10M.e0() + this.i;
                                        this.g.add(a(s10M, abstractC2412ko.b0(), i6));
                                        this.h.add(Integer.valueOf(this.g.size() - 1));
                                    } else {
                                        this.f.add(s10M);
                                        this.i = s10M.e0() + this.i;
                                        this.g.add(a(s10M, abstractC2412ko.b0(), i6));
                                        this.h.add(Integer.valueOf(this.g.size() - 1));
                                    }
                                }
                            }
                        }
                        if (!abstractC2412ko.y1()) {
                            if (o) {
                            }
                            if (abstractC2412ko.Y1()) {
                                s10B2 = abstractC2412ko.b();
                                c0767v2 = abstractC2412ko.b0().S2().h.e;
                                iZ2 = s10B2.Z();
                                this.l = iZ2;
                                if (iZ2 == 0) {
                                    this.k = null;
                                    this.j = this.n.e.D1;
                                } else {
                                    this.k = s10B2;
                                    this.j = c0767v2;
                                }
                            } else {
                                s10B = abstractC2412ko.b();
                                c0767v2A = abstractC2412ko.x().J2().a(this.n.e);
                                iZ = s10B.Z();
                                this.l = iZ;
                                if (iZ == 0) {
                                    this.k = null;
                                    this.j = this.n.e.D1;
                                } else {
                                    this.k = s10B;
                                    this.j = c0767v2A;
                                }
                            }
                        }
                    }
                }
                if (this.e >= this.n.d.Y().T.c) {
                    a(this.c, this.d + 1);
                } else {
                    this.d++;
                }
            } else {
                i3 = this.d;
                i4 = this.c;
                if (i3 > i4) {
                    a(i4, i3);
                } else {
                    a(i3 + 1);
                }
            }
        }
        if (this.e > 0) {
            a(this.c, this.d);
        }
    }

    protected abstract void a(int i, int i2, ZK zk);

    private boolean a(AbstractC2412ko abstractC2412ko) {
        AbstractC2412ko abstractC2412ko2;
        int i = this.l;
        if (this.k != null && AbstractC2734ot.b(abstractC2412ko.h1(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.hL$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a((S10) obj);
            }
        })) {
            i--;
        }
        if (abstractC2412ko.b() != null && i > 0) {
            return false;
        }
        if (abstractC2412ko.p2()) {
            if (abstractC2412ko.b().S()) {
                this.m = this.d;
            }
            return true;
        }
        if (abstractC2412ko.n1()) {
            return true;
        }
        if (!abstractC2412ko.Y1()) {
            return false;
        }
        AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
        boolean zB = this.n.e.b(abstractC1305QsB0.S2());
        if (abstractC1305QsB0.a(this.n.f, this.a) != com.android.tools.r8.ir.optimize.V.d) {
            return false;
        }
        int i2 = this.i;
        if (abstractC1305QsB0.O2()) {
            for (int i3 = 0; i3 < ((ArrayList) abstractC1305QsB0.J2()).size(); i3++) {
                S10 s10M = abstractC1305QsB0.c(i3).m();
                if (s10M != this.k) {
                    if (!b(s10M)) {
                        return false;
                    }
                    if ((abstractC1305QsB0 instanceof C1721bt) || s10M != abstractC1305QsB0.c0().U2().m() || !this.f.contains(s10M)) {
                        int iE0 = s10M.e0();
                        i2 += iE0;
                    }
                }
            }
        }
        if (i2 > 5) {
            return false;
        }
        if (zB) {
            int i4 = this.c;
            int i5 = this.d;
            if (i4 == i5) {
                return false;
            }
            if (!o && i5 <= 0) {
                throw new AssertionError();
            }
            int i6 = 0;
            do {
                i6++;
                abstractC2412ko2 = (AbstractC2412ko) this.b.get(this.d - i6);
            } while (abstractC2412ko2.y1());
            if (abstractC2412ko2.p2()) {
                if (abstractC1305QsB0 == AbstractC2871ql.a(this.n.e, abstractC2412ko2.t0().b())) {
                    S10 s10 = this.k;
                    if (s10 != null && s10 == abstractC2412ko2.b()) {
                        this.m = -1;
                    } else {
                        if (o) {
                            return false;
                        }
                        throw new AssertionError();
                    }
                }
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(S10 s10) {
        return s10.m() == this.k;
    }

    private static C0767v2 a(AbstractC1305Qs abstractC1305Qs, int i) {
        C0737r2 c0737r2T2;
        int i2 = (abstractC1305Qs.Z1() || (abstractC1305Qs instanceof C1433Us)) ? 1 : 0;
        if (i2 != 0 && i == 0) {
            return abstractC1305Qs.S2().e;
        }
        if (abstractC1305Qs instanceof C1433Us) {
            c0737r2T2 = abstractC1305Qs.f0().T2();
        } else {
            c0737r2T2 = abstractC1305Qs.S2().h;
        }
        return c0737r2T2.f.a[i - i2];
    }

    private C0767v2 a(S10 s10, AbstractC1305Qs abstractC1305Qs, int i) {
        boolean z = o;
        if (!z && !b(s10)) {
            throw new AssertionError();
        }
        C0722p1 c0722p1 = this.n.d.Y().a;
        C0767v2 c0767v2 = c0722p1.Z1;
        AbstractC3472z00 abstractC3472z00W = s10.w();
        if (abstractC3472z00W.w()) {
            C1229Oa c1229OaB = s10.w().b();
            if (c1229OaB.R() == c0767v2 && c1229OaB.S().e()) {
                return c1229OaB.S().d();
            }
            return c1229OaB.R();
        }
        if (abstractC3472z00W.r()) {
            return s10.w().a().a(c0722p1);
        }
        if (abstractC3472z00W.E()) {
            return a(abstractC1305Qs, i);
        }
        if (!z && !abstractC3472z00W.H()) {
            throw new AssertionError();
        }
        if (!z) {
            XM xmC = abstractC3472z00W.c();
            xmC.getClass();
            if (!(xmC instanceof C3037t5) && !(xmC instanceof Z5) && !(xmC instanceof VU) && !(xmC instanceof C2361k9) && !(xmC instanceof C1484Wr) && !(xmC instanceof C0858Bi) && !(xmC instanceof C1984fD) && !(xmC instanceof C0981Ff)) {
                throw new AssertionError();
            }
        }
        C0767v2 c0767v2A = abstractC3472z00W.c().a(c0722p1);
        if (abstractC3472z00W instanceof C1484Wr) {
            if (z || c0767v2A == c0722p1.A1) {
                return a(abstractC1305Qs, i);
            }
            throw new AssertionError();
        }
        if (z || c0767v2A == a(abstractC1305Qs, i)) {
            return c0767v2A;
        }
        throw new AssertionError();
    }

    private void a(int i, int i2) {
        if (!o && ((AbstractC2412ko) this.b.get(i)).y1()) {
            throw new AssertionError();
        }
        int i3 = this.m;
        if (i3 != -1) {
            if (i3 == i) {
                a(i2);
                return;
            } else {
                a(i3);
                return;
            }
        }
        int i4 = i2;
        while (((AbstractC2412ko) this.b.get(i4 - 1)).y1()) {
            i4--;
        }
        if (this.e < this.n.d.Y().T.b) {
            a(i + 1);
        } else {
            a(i, i4, new ZK(this.n, this.b, this.g, this.h, this.j, i, i4));
            a(i2);
        }
    }

    private void a(int i) {
        this.c = i;
        this.d = i;
        this.e = 0;
        this.f = new ArrayList(5);
        this.g = new ArrayList(5);
        this.h = new ArrayList(5);
        this.i = 0;
        this.j = this.n.e.D1;
        this.k = null;
        this.l = 0;
        this.m = -1;
    }
}
