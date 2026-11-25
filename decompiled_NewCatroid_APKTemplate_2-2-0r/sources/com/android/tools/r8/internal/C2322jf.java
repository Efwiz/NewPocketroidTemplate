package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0431e1;
import com.android.tools.r8.dex.code.C0524x0;
import com.android.tools.r8.dex.code.C0529y0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0772w0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2322jf {
    static final /* synthetic */ boolean r = true;
    public final C0702m a;
    private final C2409kl b;
    private final C1973f6 c;
    private final com.android.tools.r8.ir.regalloc.f d;
    private final com.android.tools.r8.utils.A1 e;
    private final CE f;
    private AbstractC1939ef[] l;
    private AbstractC1939ef m;
    C3332x4 q;
    private final ArrayList g = new ArrayList();
    private final ArrayList h = new ArrayList();
    private final Set i = OU.c();
    private int j = 0;
    private int k = 0;
    private int n = 0;
    private int o = 0;
    private boolean p = false;

    public C2322jf(C2409kl c2409kl, C2133h6 c2133h6, com.android.tools.r8.ir.regalloc.f fVar, com.android.tools.r8.utils.A1 a1, CE ce) {
        if (!r && c2409kl != null && ce != c2409kl.k()) {
            throw new AssertionError();
        }
        this.a = fVar.c();
        this.b = c2409kl;
        this.c = C2049g6.a(c2133h6);
        this.d = fVar;
        this.e = a1;
        this.f = ce;
        if (e()) {
            this.l = new AbstractC1939ef[1];
        }
    }

    public static boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        C2322jf c2322jf = new C2322jf(null, C2133h6.b(), fVar, fVar.b(), ce);
        abstractC2412ko.a(c2322jf);
        boolean z = r;
        if (!z && c2322jf.l.length != 1) {
            throw new AssertionError();
        }
        AbstractC1939ef abstractC1939ef = c2322jf.l[0];
        abstractC2412ko2.a(c2322jf);
        if (z || c2322jf.l.length == 1) {
            return abstractC1939ef.a(c2322jf.l[0], c2322jf);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC1939ef c(AbstractC2412ko abstractC2412ko) {
        if (r || abstractC2412ko.W0() >= 0) {
            return this.l[abstractC2412ko.W0() / 2];
        }
        throw new AssertionError();
    }

    private boolean e() {
        return this.b == null;
    }

    public final int b(int i, S10 s10) {
        return this.d.b(s10, i);
    }

    public final com.android.tools.r8.ir.regalloc.f d() {
        return this.d;
    }

    public final void b(AbstractC2412ko abstractC2412ko) {
        a(abstractC2412ko, new C1553Ze(abstractC2412ko));
    }

    public final com.android.tools.r8.utils.A1 b() {
        return this.e;
    }

    public final com.android.tools.r8.graph.o5 c() {
        return this.d.d();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v2, types: [com.android.tools.r8.internal.Fq, com.android.tools.r8.internal.G, com.android.tools.r8.internal.Qq, java.util.Map] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.tools.r8.internal.Rd] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [com.android.tools.r8.internal.Qq] */
    public static void a(C2409kl c2409kl) {
        ?? c1303Qq;
        Object objY0;
        C3332x4 c3332x4J2;
        if (!c2409kl.t().i()) {
            return;
        }
        int i = 1;
        int iMax = 1;
        while (true) {
            int i2 = 0;
            if (iMax < c2409kl.d.size()) {
                C3332x4 c3332x4 = c2409kl.d.get(iMax);
                if (!r && c3332x4.y().isEmpty()) {
                    throw new AssertionError();
                }
                if (c3332x4.a0() == 2) {
                    C1343Sd c1343SdI = c3332x4.k().I();
                    C1296Qj c1296QjP = c3332x4.m().P();
                    if (c1343SdI != null && c1296QjP != null && !c1343SdI.Y0().o()) {
                        AbstractC1915eM abstractC1915eMY0 = c1343SdI.Y0();
                        Iterator<C3332x4> it = c3332x4.y().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                C3332x4 next = it.next();
                                if (next == c3332x4 || !abstractC1915eMY0.equals(next.m().Y0())) {
                                    break;
                                }
                                AbstractC1954eu abstractC1954euM = next.m();
                                if (abstractC1954euM.N1()) {
                                    c3332x4J2 = abstractC1954euM.Q().J2();
                                } else if (abstractC1954euM.A2()) {
                                    c3332x4J2 = abstractC1954euM.K0().J2();
                                } else {
                                    continue;
                                }
                                if (c3332x4J2 == c3332x4) {
                                    int i3 = iMax + 1;
                                    if (i3 >= c2409kl.d.size()) {
                                        break;
                                    }
                                    if (AbstractC2967s4.a(c3332x4) != c2409kl.d.get(i3)) {
                                        break;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                c3332x4.g(c1343SdI);
                                com.android.tools.r8.ir.optimize.F.c(c3332x4, c1296QjP.K2());
                                c2409kl.b((Collection<C3332x4>) Collections.singleton(c3332x4));
                                iMax = Math.max(0, iMax - 2);
                                break;
                            }
                        }
                    }
                }
                iMax++;
            } else {
                AbstractC1915eM abstractC1915eMU = AbstractC1915eM.u();
                ArrayList arrayList = new ArrayList();
                int i4 = 0;
                C1343Sd c1343SdI2 = null;
                ?? c1303Qq2 = 0;
                while (i4 < c2409kl.d.size()) {
                    C3332x4 c3332x5 = c2409kl.d.get(i4);
                    if (i4 != 0) {
                        C3332x4 c3332x6 = c2409kl.d.get(i4 - 1);
                        if (!c3332x6.m().M1() || c3332x5.y().size() != i || c3332x5.y().get(0) != c3332x6) {
                            Iterator<C3332x4> it2 = c3332x5.y().iterator();
                            AbstractC1915eM abstractC1915eM = null;
                            while (true) {
                                if (!it2.hasNext()) {
                                    abstractC1915eMU = abstractC1915eM;
                                    break;
                                }
                                C3332x4 next2 = it2.next();
                                if (next2 == c3332x6) {
                                    objY0 = c1343SdI2 != null ? c1343SdI2.Y0() : abstractC1915eMU;
                                } else {
                                    objY0 = next2.m().Y0();
                                }
                                if (abstractC1915eM != null) {
                                    if (!abstractC1915eM.equals(objY0)) {
                                        abstractC1915eMU = AbstractC1915eM.u();
                                        break;
                                    }
                                } else {
                                    abstractC1915eM = objY0;
                                }
                            }
                            c1343SdI2 = null;
                        }
                    }
                    if (c3332x5.q() != null) {
                        c1303Qq = new C1303Qq(c3332x5.q());
                    } else {
                        c1303Qq = new C1303Qq(16);
                    }
                    i4++;
                    C3332x4 c3332x7 = i4 < c2409kl.d.size() ? c2409kl.d.get(i4) : null;
                    c1303Qq2 = c1303Qq2;
                    for (AbstractC2412ko abstractC2412ko : c3332x5.p()) {
                        abstractC2412ko.getClass();
                        if (abstractC2412ko instanceof C1343Sd) {
                            if (c1343SdI2 == null && abstractC1915eMU == abstractC2412ko.Y0()) {
                                arrayList.add(abstractC2412ko.I());
                                if (!r && c3332x5.a0() == 2 && c3332x5.m().Y0() == abstractC1915eMU && c3332x5.m().M1() && AbstractC2967s4.a(c3332x5) == c3332x7) {
                                    throw new AssertionError("Unexpected trivial fallthrough block. This should be removed already.");
                                }
                            } else if (c1343SdI2 != null && c1343SdI2.Y0() == abstractC2412ko.Y0() && c1303Qq.equals(c1303Qq2)) {
                                arrayList.add(c1343SdI2);
                                c1343SdI2 = abstractC2412ko.I();
                            } else {
                                c1343SdI2 = abstractC2412ko.I();
                                c1303Qq2 = new C1303Qq(c1303Qq.size());
                                c1303Qq2.putAll(c1303Qq);
                            }
                        } else {
                            if (!r && !abstractC2412ko.Y0().s()) {
                                throw new AssertionError();
                            }
                            boolean z = abstractC2412ko instanceof C1316Rd;
                            if (z) {
                                abstractC2412ko.H().a(c1303Qq);
                            } else if (!abstractC2412ko.m1() && !z && ((!abstractC2412ko.B1() || abstractC2412ko.b().T() || abstractC2412ko.b().Y()) && (!abstractC2412ko.M1() || abstractC2412ko.P().K2() != c3332x7))) {
                                if (c1343SdI2 != null) {
                                    if (c1343SdI2.Y0() == abstractC2412ko.Y0() && c1303Qq.equals(c1303Qq2)) {
                                        arrayList.add(c1343SdI2);
                                    }
                                    c1343SdI2 = null;
                                    c1303Qq2 = 0;
                                }
                                abstractC1915eMU = abstractC2412ko.Y0();
                            }
                        }
                        i = 1;
                        c1303Qq2 = c1303Qq2;
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                C2561ml c2561ml = new C2561ml(c2409kl);
                while (c2561ml.hasNext() && i2 < arrayList.size()) {
                    if (c2561ml.next() == arrayList.get(i2)) {
                        c2561ml.remove();
                        i2++;
                    }
                }
                if (!r && i2 != arrayList.size()) {
                    throw new AssertionError();
                }
                return;
            }
        }
    }

    public final C0772w0 a() {
        int iZ;
        int i;
        do {
            if (!this.i.isEmpty()) {
                B4 b4S = this.b.s();
                while (b4S.hasNext()) {
                    C3332x4 next = b4S.next();
                    if (this.i.contains(next)) {
                        C1015Gl c1015GlQ = next.m().Q();
                        C3332x4 c3332x4K2 = c1015GlQ.K2();
                        C3332x4 c3332x4A = C3332x4.a(this.b.f.a(), c1015GlQ.Y0(), this.b.t(), c3332x4K2);
                        c1015GlQ.c(c3332x4A);
                        c3332x4A.r().add(next);
                        c3332x4K2.a(next, c3332x4A);
                        c1015GlQ.M2();
                        b4S.add(c3332x4A);
                    }
                }
            }
            a(this.b);
            this.g.clear();
            this.h.clear();
            this.i.clear();
            this.j = 0;
            this.k = 0;
            this.l = new AbstractC1939ef[this.b.w() / 2];
            this.n = 0;
            this.o = 0;
            this.q = null;
            B4 b4S2 = this.b.s();
            if (!r && !b4S2.hasNext()) {
                throw new AssertionError();
            }
            C3332x4 next2 = b4S2.next();
            do {
                this.q = b4S2.hasNext() ? b4S2.next() : null;
                next2.a(this);
                next2 = this.q;
            } while (next2 != null);
            Iterator<AbstractC2412ko> it = this.b.r().iterator();
            iZ = 0;
            i = 0;
            while (it.hasNext()) {
                AbstractC1939ef abstractC1939efC = c(it.next());
                abstractC1939efC.c(iZ);
                iZ += abstractC1939efC.a(this);
                i++;
            }
        } while (!this.i.isEmpty());
        com.android.tools.r8.graph.H0 h0 = new com.android.tools.r8.graph.H0(this.b, this.e);
        ArrayList arrayList = new ArrayList(i);
        int i2 = 0;
        for (AbstractC2412ko abstractC2412ko : this.b.r()) {
            AbstractC1939ef abstractC1939efC2 = c(abstractC2412ko);
            int size = arrayList.size();
            abstractC1939efC2.a(this, arrayList);
            int iZ2 = i2;
            while (size < arrayList.size()) {
                int i3 = size + 1;
                com.android.tools.r8.dex.code.B1 b1 = (com.android.tools.r8.dex.code.B1) arrayList.get(size);
                b1.g(iZ2);
                iZ2 += b1.z();
                size = i3;
            }
            h0.a(i2, iZ2, abstractC2412ko);
            i2 = iZ2;
        }
        if (this.e.D() && (arrayList.get(arrayList.size() - 1) instanceof com.android.tools.r8.dex.code.e4) && this.p) {
            com.android.tools.r8.dex.code.B1 b2 = (com.android.tools.r8.dex.code.B1) arrayList.get(arrayList.size() - 1);
            int iU = b2.u();
            C0431e1 c0431e1 = new C0431e1(b2.z() + 1);
            C0431e1 c0431e2 = new C0431e1(-b2.z());
            c0431e1.g(iU);
            int i4 = iU + 1;
            b2.g(i4);
            int iZ3 = b2.z() + i4;
            c0431e2.g(iZ3);
            arrayList.remove(arrayList.size() - 1);
            arrayList.add(c0431e1);
            arrayList.add(b2);
            arrayList.add(c0431e2);
            iZ = iZ3 + 1;
        }
        for (C2171hf c2171hf : this.g) {
            if (iZ % 2 != 0) {
                com.android.tools.r8.dex.code.R2 r2 = new com.android.tools.r8.dex.code.R2();
                r2.g(iZ);
                arrayList.add(r2);
                iZ++;
            }
            C1458Vr c1458Vr = c2171hf.a;
            c2171hf.b.j(iZ - c(c1458Vr).c());
            int[] iArrM2 = c1458Vr.M2();
            int[] iArr = new int[iArrM2.length];
            for (int i5 = 0; i5 < iArrM2.length; i5++) {
                iArr[i5] = c(c1458Vr.e(i5).k()).c() - c(c1458Vr).c();
            }
            com.android.tools.r8.dex.code.d4 d4VarA = c1458Vr.a(iArr, c(c1458Vr.J2().k()).c() - c(c1458Vr).c());
            d4VarA.g(iZ);
            iZ += d4VarA.z();
            arrayList.add(d4VarA);
        }
        for (C1627af c1627af : this.h) {
            if (iZ % 2 != 0) {
                com.android.tools.r8.dex.code.R2 r3 = new com.android.tools.r8.dex.code.R2();
                r3.g(iZ);
                arrayList.add(r3);
                iZ++;
            }
            BG bg = c1627af.a;
            bg.getClass();
            C0529y0 c0529y0 = new C0529y0(bg.h, bg.i, bg.j);
            c0529y0.g(iZ);
            C0524x0 c0524x0 = c1627af.b;
            c0524x0.j(iZ - c0524x0.u());
            iZ += c0529y0.z();
            arrayList.add(c0529y0);
        }
        C3011sk c3011skL = C3011sk.l();
        ArrayList arrayListA = a(c3011skL);
        int size2 = arrayListA.size();
        C0772w0.a[] aVarArr = new C0772w0.a[size2];
        for (int i6 = 0; i6 < size2; i6++) {
            Cif cif = (Cif) arrayListA.get(i6);
            int i7 = cif.b;
            C0772w0.a aVar = new C0772w0.a(i7, cif.c - i7, -1);
            aVar.d = ((Integer) c3011skL.get(cif.a)).intValue();
            aVarArr[i6] = aVar;
        }
        G4 g4G = c3011skL.g();
        C0772w0.b[] bVarArr = new C0772w0.b[g4G.size()];
        for (int i8 = 0; i8 < g4G.size(); i8++) {
            C3038t6 c3038t6 = (C3038t6) g4G.get(Integer.valueOf(i8));
            ArrayList arrayList2 = new ArrayList();
            int i9 = -1;
            for (int i10 = 0; i10 < c3038t6.b().size(); i10++) {
                C0767v2 c0767v2 = (C0767v2) c3038t6.b().get(i10);
                int iC = c(((C3332x4) c3038t6.a().get(i10)).k()).c();
                if (c0767v2 == this.e.a.m3) {
                    if (!r && i10 != c3038t6.b().size() - 1) {
                        throw new AssertionError();
                    }
                    i9 = iC;
                } else {
                    arrayList2.add(new C0772w0.b.a(iC, c0767v2));
                }
            }
            bVarArr[i8] = new C0772w0.b((C0772w0.b.a[]) arrayList2.toArray(new C0772w0.b.a[0]), i9);
        }
        return new C0772w0(this.d.e(), this.n, this.o, (com.android.tools.r8.dex.code.B1[]) arrayList.toArray(com.android.tools.r8.dex.code.B1.b), aVarArr, bVarArr, h0.a(), this.c.a());
    }

    public final void a(int i) {
        if (i > this.o) {
            this.o = i;
        }
    }

    public final int a(int i, S10 s10) {
        return this.d.a(s10, i);
    }

    public final void a(C1296Qj c1296Qj) {
        if (c1296Qj.K2() != this.q) {
            a(c1296Qj, new C1780cf(c1296Qj));
        } else {
            b(c1296Qj);
        }
    }

    public final void a(C1641ao c1641ao, com.android.tools.r8.dex.code.A1 a1) {
        if (this.e.e()) {
            AbstractC1939ef abstractC1939ef = this.m;
            if (abstractC1939ef instanceof C2016ff) {
                C2016ff c2016ff = (C2016ff) abstractC1939ef;
                int iB = this.d.b(((C3492zF) ((AbstractC1939ef) c2016ff).a).K2(), ((C3492zF) ((AbstractC1939ef) c2016ff).a).W0());
                int iA = this.d.a(((C3492zF) ((AbstractC1939ef) c2016ff).a).J2(), ((C3492zF) ((AbstractC1939ef) c2016ff).a).W0());
                if (iB != iA && iB == a1.e && iA == a1.f) {
                    a(c1641ao, new com.android.tools.r8.dex.code.R2(), a1);
                    return;
                }
            }
        }
        a((AbstractC2412ko) c1641ao, (com.android.tools.r8.dex.code.B1) a1);
    }

    public final void a(C1015Gl c1015Gl) {
        if (!r && this.q != c1015Gl.J2()) {
            throw new AssertionError();
        }
        a(c1015Gl, new C1856df(c1015Gl));
    }

    public final void a(C3492zF c3492zF) {
        a(c3492zF, new C2016ff(c3492zF));
    }

    public final void a(AbstractC2412ko abstractC2412ko) {
        a(abstractC2412ko, new C1707bf(abstractC2412ko, new com.android.tools.r8.dex.code.R2()));
    }

    public final void a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.dex.code.B1 b1) {
        if (!r && abstractC2412ko.M1()) {
            throw new AssertionError();
        }
        a(abstractC2412ko, new C1707bf(abstractC2412ko, b1));
        this.c.a(abstractC2412ko, b1);
    }

    public final void a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.dex.code.B1... b1Arr) {
        if (!r && abstractC2412ko.M1()) {
            throw new AssertionError();
        }
        a(abstractC2412ko, new C2095gf(abstractC2412ko, b1Arr));
    }

    public final void a(C1458Vr c1458Vr, com.android.tools.r8.dex.code.X0 x0) {
        if (!r && this.q != c1458Vr.J2()) {
            throw new AssertionError();
        }
        this.g.add(new C2171hf(c1458Vr, x0));
        a((AbstractC2412ko) c1458Vr, (com.android.tools.r8.dex.code.B1) x0);
    }

    public final void a(BG bg, C0524x0 c0524x0) {
        this.h.add(new C1627af(bg, c0524x0));
        a((AbstractC2412ko) bg, (com.android.tools.r8.dex.code.B1) c0524x0);
    }

    public final void a(C3406y2 c3406y2) {
        this.n = c3406y2.b().e0() + this.n;
        a(c3406y2, new C1553Ze(c3406y2));
    }

    public final void a(C1616aU c1616aU, com.android.tools.r8.dex.code.J j) {
        C3332x4 c3332x4 = this.q;
        if (c3332x4 != null && c1616aU.a(c3332x4.k(), this.d, this.f)) {
            b(c1616aU);
        } else {
            a((AbstractC2412ko) c1616aU, (com.android.tools.r8.dex.code.B1) j);
        }
    }

    private void a(AbstractC2412ko abstractC2412ko, AbstractC1939ef abstractC1939ef) {
        if (e()) {
            if (!r && this.l.length != 1) {
                throw new AssertionError();
            }
            this.l[0] = abstractC1939ef;
            return;
        }
        boolean z = r;
        if (!z && abstractC2412ko == null) {
            throw new AssertionError();
        }
        if (!z && c(abstractC2412ko) != null) {
            throw new AssertionError();
        }
        abstractC1939ef.b(this.k);
        abstractC1939ef.a(this.j);
        this.k += abstractC1939ef.f();
        this.j += abstractC1939ef.e();
        if (!z && abstractC2412ko.W0() < 0) {
            throw new AssertionError();
        }
        if (!(abstractC1939ef instanceof C1553Ze)) {
            this.m = abstractC1939ef;
        }
        this.l[abstractC2412ko.W0() / 2] = abstractC1939ef;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC1939ef a(C3332x4 c3332x4) {
        InterfaceC2488lo interfaceC2488loR = c3332x4.R();
        AbstractC2412ko next = null;
        while (interfaceC2488loR.hasNext()) {
            next = interfaceC2488loR.next();
            AbstractC1939ef abstractC1939efC = c(next);
            if (!(abstractC1939efC instanceof C1553Ze)) {
                return abstractC1939efC;
            }
        }
        boolean z = r;
        if (!z && next == null) {
            throw new AssertionError();
        }
        if (next.u2()) {
            if (!z && !(c(next) instanceof C1553Ze)) {
                throw new AssertionError();
            }
            B4 b4S = this.b.s();
            for (C3332x4 next2 = b4S.next(); next2 != c3332x4; next2 = b4S.next()) {
            }
            return a(b4S.next());
        }
        if (z || next.M1()) {
            return a(next.P().K2());
        }
        throw new AssertionError();
    }

    private ArrayList a(C3011sk c3011sk) {
        AbstractC2412ko abstractC2412ko;
        G4 g4G = c3011sk.g();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Cif cif = null;
        while (true) {
            Cif cif2 = null;
            for (C3332x4 c3332x4 : this.b.d) {
                C3038t6 c3038t6N = c3332x4.n();
                if (!r && !c3038t6N.isEmpty() && !c3332x4.a()) {
                    throw new AssertionError();
                }
                if (!c3038t6N.isEmpty()) {
                    if (c3011sk.containsKey(c3038t6N)) {
                        c3038t6N = (C3038t6) g4G.get(c3011sk.get(c3038t6N));
                    } else {
                        c3011sk.put(c3038t6N, Integer.valueOf(c3011sk.size()));
                    }
                    AbstractC1939ef abstractC1939efC = c(c3332x4.k());
                    AbstractC1939ef abstractC1939efC2 = c(c3332x4.m());
                    Cif cif3 = new Cif(c3038t6N, abstractC1939efC.c(), abstractC1939efC2.c() + abstractC1939efC2.d());
                    arrayList.add(cif3);
                    arrayList2.add(c3332x4);
                    cif2 = cif3;
                } else if (cif2 != null && !c3332x4.a()) {
                    AbstractC1939ef abstractC1939efC3 = c(c3332x4.m());
                    if (abstractC1939efC3 != null) {
                        cif2.c = abstractC1939efC3.c() + abstractC1939efC3.d();
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return arrayList;
            }
            arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.jf$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ((Cif) obj).compareTo((Cif) obj2);
                }
            });
            ArrayList arrayList3 = new ArrayList(arrayList.size());
            int i = 0;
            while (i < arrayList.size()) {
                if (cif != null) {
                    Iterator it = AbstractC2763pC.a((List) ((C3332x4) arrayList2.get(i - 1)).p()).iterator();
                    do {
                        if (!it.hasNext()) {
                            throw new C3405y10("Expected to find a possibly throwing instruction");
                        }
                        abstractC2412ko = (AbstractC2412ko) it.next();
                    } while (!abstractC2412ko.g());
                    AbstractC1939ef abstractC1939efC4 = c(abstractC2412ko);
                    cif.c = abstractC1939efC4.c() + abstractC1939efC4.d();
                }
                cif = (Cif) arrayList.get(i);
                arrayList3.add(cif);
                for (AbstractC2412ko abstractC2412ko2 : ((C3332x4) arrayList2.get(i)).p()) {
                    if (abstractC2412ko2.g()) {
                        cif.b = c(abstractC2412ko2).c();
                        break;
                    }
                }
                while (true) {
                    i++;
                    if (i >= arrayList.size()) {
                        break;
                    }
                    Cif cif4 = (Cif) arrayList.get(i);
                    if (cif.c != cif4.b || !cif.a.equals(cif4.a)) {
                        break;
                    }
                    cif.c = cif4.c;
                }
            }
            for (AbstractC2412ko abstractC2412ko3 : AbstractC2763pC.a((List) ((C3332x4) arrayList2.get(arrayList.size() - 1)).p())) {
                if (abstractC2412ko3.g()) {
                    AbstractC1939ef abstractC1939efC5 = c(abstractC2412ko3);
                    cif.c = abstractC1939efC5.c() + abstractC1939efC5.d();
                    return arrayList3;
                }
            }
            throw new C3405y10("Expected to find a possibly throwing instruction");
        }
    }
}
