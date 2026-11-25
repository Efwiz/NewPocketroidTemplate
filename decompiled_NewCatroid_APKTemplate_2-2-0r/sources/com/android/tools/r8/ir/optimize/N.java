package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.A4$$ExternalSyntheticLambda3;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1332Rs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3233vn;
import com.android.tools.r8.internal.AbstractC3477z20;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1793co;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2411kn;
import com.android.tools.r8.internal.C3234vn$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3344xF;
import com.android.tools.r8.internal.C3387xn;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.InterfaceC3457yn;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.V9;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3749l;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.utils.A1;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N implements InterfaceC3556e0, InterfaceC3558f0 {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final A1.i b;
    private final N1 c;
    private final o5 d;
    private final TE e;
    private final InterfaceC3457yn f;
    private int g;

    N(C0702m c0702m, InterfaceC3457yn interfaceC3457yn, o5 o5Var, TE te, int i) {
        this.a = c0702m;
        this.b = c0702m.Y().P0();
        this.f = interfaceC3457yn;
        this.c = ((C3733i) c0702m.f()).f();
        this.d = o5Var;
        this.e = te;
        this.g = i;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final C1229Oa a(AbstractC1305Qs abstractC1305Qs, C1229Oa c1229Oa) {
        return c1229Oa;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final boolean a() {
        return false;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final C0702m b() {
        return this.a;
    }

    public final boolean b(o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        if (!this.e.b(o5Var)) {
            com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
            int iA = this.b.a();
            BitSet bitSetQ = o5Var.e().i1().q();
            int i = 0;
            if (bitSetQ != null) {
                List listJ2 = abstractC1305Qs.J2();
                int iA2 = com.android.tools.r8.utils.M.a(abstractC1305Qs.Z1());
                while (true) {
                    ArrayList arrayList = (ArrayList) listJ2;
                    if (iA2 >= arrayList.size()) {
                        break;
                    }
                    S10 s10 = (S10) arrayList.get(iA2);
                    if ((s10.H() || (s10.w().I() && s10.P())) && bitSetQ.get(iA2)) {
                        i += 4;
                    }
                    iA2++;
                }
            }
            if (this.a.Y().Y0() && abstractC1305Qs.d1() && abstractC1305Qs.b().B()) {
                i++;
            }
            if (wA1.k(iA + i)) {
                return true;
            }
        }
        return o5Var.e().i1().s().a(abstractC1305Qs);
    }

    private static boolean a(AbstractC1305Qs abstractC1305Qs, o5 o5Var, AbstractC3477z20 abstractC3477z20) {
        if (!o5Var.e().u1()) {
            if (!o5Var.e().q1()) {
                abstractC3477z20.g();
                return true;
            }
            int size = ((ArrayList) abstractC1305Qs.J2()).size() - com.android.tools.r8.utils.M.a(abstractC1305Qs.Z1());
            int iF0 = o5Var.getReference().F0();
            if (size == iF0) {
                return false;
            }
            abstractC3477z20.a(size, iF0);
            return true;
        }
        throw new C3405y10("Unexpected attempt to invoke a class initializer (`" + o5Var.w() + "`)");
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x009b, code lost:
    
        if (r2.a1().y0() == false) goto L38;
     */
    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean a(com.android.tools.r8.internal.AbstractC1305Qs r6, com.android.tools.r8.graph.I4.c r7, com.android.tools.r8.graph.o5 r8, com.android.tools.r8.ir.optimize.EnumC3542a0 r9, com.android.tools.r8.internal.AbstractC3477z20 r10) {
        /*
            Method dump skipped, instruction units count: 293
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.ir.optimize.N.a(com.android.tools.r8.internal.Qs, com.android.tools.r8.graph.I4$c, com.android.tools.r8.graph.o5, com.android.tools.r8.ir.optimize.a0, com.android.tools.r8.internal.z20):boolean");
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final o5 a(o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        return AbstractC0748t0.a(abstractC1305Qs.h(this.a, o5Var));
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0262  */
    /* JADX WARN: Code duplicated, block: B:103:0x027a  */
    /* JADX WARN: Code duplicated, block: B:106:0x0290  */
    /* JADX WARN: Code duplicated, block: B:112:0x02b1 A[LOOP:4: B:110:0x02ab->B:112:0x02b1, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:116:0x02c7  */
    /* JADX WARN: Code duplicated, block: B:121:0x02e1  */
    /* JADX WARN: Code duplicated, block: B:124:0x02f7  */
    /* JADX WARN: Code duplicated, block: B:129:0x0315 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:131:0x022a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:132:0x0214 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:133:0x0254 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:136:0x0200 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:137:0x0230 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:0x024e A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:141:0x01ee A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:143:0x01ee A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:146:0x02bb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:147:0x029c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:149:0x02a3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:150:? A[LOOP:2: B:101:0x0274->B:150:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:151:? A[LOOP:3: B:104:0x028a->B:151:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x030b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x02d3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:154:0x0303 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:156:0x02c1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:157:0x02c1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:158:? A[LOOP:6: B:119:0x02db->B:158:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:159:? A[LOOP:7: B:122:0x02f1->B:159:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:24:0x0096  */
    /* JADX WARN: Code duplicated, block: B:26:0x00a6 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:27:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:29:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:31:0x00e8 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:32:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:48:0x013c  */
    /* JADX WARN: Code duplicated, block: B:54:0x0157  */
    /* JADX WARN: Code duplicated, block: B:69:0x019f  */
    /* JADX WARN: Code duplicated, block: B:76:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:79:0x01f4  */
    /* JADX WARN: Code duplicated, block: B:93:0x0246  */
    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final X a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, I4.c cVar, o5 o5Var, o5 o5Var2, V9 v9, C3387xn c3387xn, AbstractC3477z20 abstractC3477z20) {
        boolean z;
        EnumC3542a0 enumC3542a0A;
        W wA;
        C1166Ls c1166LsZ;
        C2409kl c2409klB;
        S10 s10B;
        ArrayList arrayList;
        Iterator<AbstractC2412ko> it;
        int iA;
        Iterator it2;
        Iterator<C3332x4> it3;
        C3332x4 next;
        Iterator<AbstractC2412ko> it4;
        AbstractC2412ko next2;
        Iterator it5;
        C3332x4 c3332x4H;
        Iterator it6;
        Iterator<C3332x4> it7;
        AbstractC2412ko abstractC2412ko;
        Iterator it8;
        boolean z2;
        AbstractC2412ko next3;
        C0684j2 c0684j2S2;
        com.android.tools.r8.graph.T0 t0A;
        W0 w0E;
        boolean z3;
        if (a(abstractC1305Qs, o5Var, abstractC3477z20)) {
            return null;
        }
        C0684j2 reference = o5Var.getReference();
        if (!this.a.a(o5Var).h(this.a.Y())) {
            abstractC3477z20.p();
        } else {
            C3749l c3749lT = this.a.t();
            if (!c3749lT.a(abstractC1305Qs.S2()).f() && !c3749lT.a(cVar.c().getReference()).f() && !c3749lT.a(reference).f()) {
                if (!this.a.Y().n1.B) {
                    com.android.tools.r8.synthesis.D D = this.a.D();
                    C0729q2 c0729q2A = o5Var.a();
                    D.getClass();
                    if (D.i(c0729q2A.d)) {
                    }
                }
                z = false;
            } else {
                z = !o5Var.e().i1().g();
            }
            if (z) {
                if (!o5Var.e().i1().g()) {
                    return null;
                }
                throw new C3405y10("Unexpected attempt to force inline method `" + o5Var.w() + "` in `" + o5Var2.w() + "`.");
            }
            enumC3542a0A = this.f.a(abstractC1305Qs, o5Var, o5Var2, this, this.e);
            if (enumC3542a0A == EnumC3542a0.f) {
                return null;
            }
            if (enumC3542a0A != EnumC3542a0.e && !o5Var.e().M1() && this.e.c()) {
                return new C3544b0();
            }
            if (o5Var.e().a(this.d, enumC3542a0A, (C0680j) this.a.f(), abstractC3477z20) || !a(abstractC1305Qs, cVar, o5Var, enumC3542a0A, abstractC3477z20)) {
                return null;
            }
            if (this.a.Y().t()) {
                w0E = o5Var.e();
                if (!w0E.f.c0() || w0E.a1().y0()) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (z3 && (o5Var2.B().g() || c2409kl.t().z())) {
                    return null;
                }
            }
            wA = abstractC1305Qs.a(o5Var, enumC3542a0A, this, v9, abstractC3477z20);
            if (wA != null || !a(this.a, wA, abstractC1305Qs, o5Var, o5Var2)) {
                return null;
            }
            if (o5Var.e().C1()) {
                c1166LsZ = abstractC1305Qs.Z();
                c2409klB = c3387xn.b(o5Var, c1166LsZ);
                C0767v2 c0767v2T = this.d.t();
                C0767v2 c0767v2T2 = o5Var.t();
                if (!this.d.e().C1() && c0767v2T == c0767v2T2 && c1166LsZ.U2() == c2409kl.o()) {
                    c3387xn.a(c1166LsZ, c2409klB);
                } else {
                    s10B = c2409klB.j().k().q().b();
                    arrayList = new ArrayList();
                    it = c2409klB.r().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            next3 = it.next();
                            if (next3.W1()) {
                                C1166Ls c1166LsZ2 = next3.Z();
                                c0684j2S2 = c1166LsZ2.S2();
                                if (!this.a.a().b(c0684j2S2) && c1166LsZ2.U2().m() == s10B) {
                                    if (c0767v2T2 != c0684j2S2.e) {
                                        abstractC3477z20.a(c1166LsZ2);
                                    } else {
                                        arrayList.add(c1166LsZ2);
                                    }
                                }
                            } else if (next3.S1()) {
                                C1793co c1793coV = next3.V();
                                t0A = ((C3733i) this.a.f()).a(c1793coV.getField());
                                if (t0A != null || t0A.f.h()) {
                                    abstractC3477z20.a(c1793coV);
                                }
                            } else {
                                continue;
                            }
                            if (!z2) {
                                return null;
                            }
                        } else {
                            iA = c2409klB.A();
                            it2 = arrayList.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    AbstractC2412ko abstractC2412ko2 = (C1166Ls) it2.next();
                                    c3332x4H = abstractC2412ko2.h();
                                    it6 = c3332x4H.b(abstractC2412ko2).iterator();
                                    while (true) {
                                        if (it6.hasNext()) {
                                            abstractC2412ko = (AbstractC2412ko) it6.next();
                                            it8 = ((ArrayList) abstractC2412ko.h1()).iterator();
                                            while (true) {
                                                if (it8.hasNext()) {
                                                    if (((S10) it8.next()).m() == s10B) {
                                                        c2409klB.b(iA);
                                                        abstractC3477z20.a(abstractC2412ko);
                                                    }
                                                }
                                            }
                                        } else {
                                            it7 = c3332x4H.y().iterator();
                                            while (it7.hasNext()) {
                                                c2409klB.a(iA, it7.next());
                                            }
                                        }
                                    }
                                } else {
                                    it3 = c2409klB.d.iterator();
                                    while (true) {
                                        if (it3.hasNext()) {
                                            next = it3.next();
                                            if (next.c(iA)) {
                                                it4 = next.p().iterator();
                                                while (true) {
                                                    if (it4.hasNext()) {
                                                        next2 = it4.next();
                                                        it5 = ((ArrayList) next2.h1()).iterator();
                                                        while (true) {
                                                            if (it5.hasNext()) {
                                                                if (((S10) it5.next()).m() == s10B) {
                                                                    c2409klB.b(iA);
                                                                    abstractC3477z20.a(next2);
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        continue;
                                                    }
                                                }
                                            }
                                        } else {
                                            c2409klB.b(iA);
                                            c3387xn.a(c1166LsZ, c2409klB);
                                        }
                                    }
                                }
                            }
                        }
                        z2 = false;
                        if (!z2) {
                            return null;
                        }
                    }
                }
                z2 = true;
                if (!z2) {
                    return null;
                }
            }
            return wA;
        }
        z = true;
        if (z) {
            if (!o5Var.e().i1().g()) {
                return null;
            }
            throw new C3405y10("Unexpected attempt to force inline method `" + o5Var.w() + "` in `" + o5Var2.w() + "`.");
        }
        enumC3542a0A = this.f.a(abstractC1305Qs, o5Var, o5Var2, this, this.e);
        if (enumC3542a0A == EnumC3542a0.f) {
            return null;
        }
        if (enumC3542a0A != EnumC3542a0.e) {
        }
        if (o5Var.e().a(this.d, enumC3542a0A, (C0680j) this.a.f(), abstractC3477z20)) {
            return null;
        }
        if (this.a.Y().t()) {
            w0E = o5Var.e();
            if (w0E.f.c0()) {
                z3 = true;
            } else {
                z3 = true;
            }
            if (z3) {
                return null;
            }
        }
        wA = abstractC1305Qs.a(o5Var, enumC3542a0A, this, v9, abstractC3477z20);
        if (wA != null) {
            return null;
        }
        if (o5Var.e().C1()) {
            c1166LsZ = abstractC1305Qs.Z();
            c2409klB = c3387xn.b(o5Var, c1166LsZ);
            C0767v2 c0767v2T3 = this.d.t();
            C0767v2 c0767v2T4 = o5Var.t();
            if (!this.d.e().C1()) {
                s10B = c2409klB.j().k().q().b();
                arrayList = new ArrayList();
                it = c2409klB.r().iterator();
                while (true) {
                    if (it.hasNext()) {
                        next3 = it.next();
                        if (next3.W1()) {
                            C1166Ls c1166LsZ3 = next3.Z();
                            c0684j2S2 = c1166LsZ3.S2();
                            if (!this.a.a().b(c0684j2S2)) {
                                continue;
                            }
                        } else if (next3.S1()) {
                            C1793co c1793coV2 = next3.V();
                            t0A = ((C3733i) this.a.f()).a(c1793coV2.getField());
                            if (t0A != null) {
                            }
                            abstractC3477z20.a(c1793coV2);
                        } else {
                            continue;
                        }
                    } else {
                        iA = c2409klB.A();
                        it2 = arrayList.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                AbstractC2412ko abstractC2412ko3 = (C1166Ls) it2.next();
                                c3332x4H = abstractC2412ko3.h();
                                it6 = c3332x4H.b(abstractC2412ko3).iterator();
                                while (true) {
                                    if (it6.hasNext()) {
                                        abstractC2412ko = (AbstractC2412ko) it6.next();
                                        it8 = ((ArrayList) abstractC2412ko.h1()).iterator();
                                        while (true) {
                                            if (it8.hasNext()) {
                                                if (((S10) it8.next()).m() == s10B) {
                                                    c2409klB.b(iA);
                                                    abstractC3477z20.a(abstractC2412ko);
                                                }
                                            }
                                        }
                                    } else {
                                        it7 = c3332x4H.y().iterator();
                                        while (it7.hasNext()) {
                                            c2409klB.a(iA, it7.next());
                                        }
                                    }
                                }
                            } else {
                                it3 = c2409klB.d.iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        next = it3.next();
                                        if (next.c(iA)) {
                                            it4 = next.p().iterator();
                                            while (true) {
                                                if (it4.hasNext()) {
                                                    next2 = it4.next();
                                                    it5 = ((ArrayList) next2.h1()).iterator();
                                                    while (true) {
                                                        if (it5.hasNext()) {
                                                            if (((S10) it5.next()).m() == s10B) {
                                                                c2409klB.b(iA);
                                                                abstractC3477z20.a(next2);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            }
                                        }
                                    } else {
                                        c2409klB.b(iA);
                                        c3387xn.a(c1166LsZ, c2409klB);
                                        z2 = true;
                                    }
                                }
                            }
                        }
                    }
                    z2 = false;
                }
            } else {
                s10B = c2409klB.j().k().q().b();
                arrayList = new ArrayList();
                it = c2409klB.r().iterator();
                while (true) {
                    if (it.hasNext()) {
                        next3 = it.next();
                        if (next3.W1()) {
                            C1166Ls c1166LsZ4 = next3.Z();
                            c0684j2S2 = c1166LsZ4.S2();
                            if (!this.a.a().b(c0684j2S2)) {
                                continue;
                            }
                        } else if (next3.S1()) {
                            C1793co c1793coV3 = next3.V();
                            t0A = ((C3733i) this.a.f()).a(c1793coV3.getField());
                            if (t0A != null) {
                            }
                            abstractC3477z20.a(c1793coV3);
                        } else {
                            continue;
                        }
                    } else {
                        iA = c2409klB.A();
                        it2 = arrayList.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                AbstractC2412ko abstractC2412ko4 = (C1166Ls) it2.next();
                                c3332x4H = abstractC2412ko4.h();
                                it6 = c3332x4H.b(abstractC2412ko4).iterator();
                                while (true) {
                                    if (it6.hasNext()) {
                                        abstractC2412ko = (AbstractC2412ko) it6.next();
                                        it8 = ((ArrayList) abstractC2412ko.h1()).iterator();
                                        while (true) {
                                            if (it8.hasNext()) {
                                                if (((S10) it8.next()).m() == s10B) {
                                                    c2409klB.b(iA);
                                                    abstractC3477z20.a(abstractC2412ko);
                                                }
                                            }
                                        }
                                    } else {
                                        it7 = c3332x4H.y().iterator();
                                        while (it7.hasNext()) {
                                            c2409klB.a(iA, it7.next());
                                        }
                                    }
                                }
                            } else {
                                it3 = c2409klB.d.iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        next = it3.next();
                                        if (next.c(iA)) {
                                            it4 = next.p().iterator();
                                            while (true) {
                                                if (it4.hasNext()) {
                                                    next2 = it4.next();
                                                    it5 = ((ArrayList) next2.h1()).iterator();
                                                    while (true) {
                                                        if (it5.hasNext()) {
                                                            if (((S10) it5.next()).m() == s10B) {
                                                                c2409klB.b(iA);
                                                                abstractC3477z20.a(next2);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            }
                                        }
                                    } else {
                                        c2409klB.b(iA);
                                        c3387xn.a(c1166LsZ, c2409klB);
                                        z2 = true;
                                    }
                                }
                            }
                        }
                    }
                    z2 = false;
                }
            }
            if (!z2) {
                return null;
            }
        }
        return wA;
    }

    public final W a(AbstractC1332Rs abstractC1332Rs, o5 o5Var, EnumC3542a0 enumC3542a0, AbstractC3477z20 abstractC3477z20) {
        S10 s10U2 = abstractC1332Rs.U2();
        if (s10U2.w().N().f()) {
            abstractC3477z20.r();
            return null;
        }
        W w = new W(o5Var, abstractC1332Rs, enumC3542a0);
        if (s10U2.w().F()) {
            if (!h && s10U2.w().N().f()) {
                throw new AssertionError();
            }
            if (!this.b.h) {
                abstractC3477z20.s();
                return null;
            }
        }
        return w;
    }

    public final W a(C1721bt c1721bt, o5 o5Var, EnumC3542a0 enumC3542a0, V9 v9, AbstractC3477z20 abstractC3477z20) {
        W w = new W(o5Var, c1721bt, enumC3542a0);
        if (a(c1721bt, this.d, o5Var, v9)) {
            return w;
        }
        if (this.a.k() && this.b.g) {
            w.b();
            return w;
        }
        abstractC3477z20.n();
        return null;
    }

    private boolean a(C1721bt c1721bt, final o5 o5Var, final o5 o5Var2, V9 v9) {
        if (((C3733i) this.a.f()).c(o5Var.t(), o5Var2.t())) {
            return true;
        }
        if (!o5Var.e().E0()) {
            if (((Boolean) this.a.b(Boolean.FALSE, new Function() { // from class: com.android.tools.r8.ir.optimize.N$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return N.a(o5Var2, o5Var, (C2411kn) obj);
                }
            })).booleanValue()) {
                return true;
            }
        }
        return v9.a(o5Var2.t(), c1721bt) || !o5Var2.a().a(this.a, o5Var) || this.a.b0().i.contains(o5Var2.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(o5 o5Var, o5 o5Var2, C2411kn c2411kn) {
        return Boolean.valueOf(c2411kn.a(o5Var.a(), o5Var2));
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final boolean a(W w, AbstractC3477z20 abstractC3477z20) {
        EnumC3542a0 enumC3542a0 = w.c;
        enumC3542a0.getClass();
        boolean z = true;
        if (enumC3542a0 != EnumC3542a0.a && enumC3542a0 != EnumC3542a0.b) {
            z = this.g > 0;
            if (!z) {
                abstractC3477z20.m();
            }
        }
        return z;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final boolean a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, Y y, C3332x4 c3332x4, AbstractC3477z20 abstractC3477z20) {
        EnumC3542a0 enumC3542a0 = y.a;
        enumC3542a0.getClass();
        if (enumC3542a0 == EnumC3542a0.a || enumC3542a0 == EnumC3542a0.b) {
            return false;
        }
        int iB = C3546c0.b(y.b);
        if (this.g < C3546c0.b(y.b)) {
            abstractC3477z20.c(iB, this.g);
        } else if (!a(c2409kl, abstractC1305Qs, y, abstractC3477z20)) {
            if (!c3332x4.D()) {
                return false;
            }
            Iterator<C3332x4> it = y.b.d.iterator();
            int i = 0;
            while (it.hasNext()) {
                Iterator<AbstractC2412ko> it2 = it.next().p().iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    if (it2.next().g()) {
                        i2++;
                    }
                }
                i += i2;
            }
            int iU = c3332x4.U() * i;
            int i3 = this.b.f;
            if (iU < i3) {
                return false;
            }
            abstractC3477z20.b(iU, i3);
        }
        return true;
    }

    private boolean a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, Y y, AbstractC3477z20 abstractC3477z20) {
        if (!c2409kl.t().z() || !y.b.t().z()) {
            return false;
        }
        Set setC = OU.c();
        Set setC2 = OU.c();
        AbstractC3233vn.a(c2409kl, setC, setC2);
        if (setC.isEmpty() && setC2.isEmpty()) {
            return false;
        }
        Iterator it = y.b.b((Predicate) new C3234vn$$ExternalSyntheticLambda0()).iterator();
        while (it.hasNext()) {
            S10 s10M = ((C3344xF) it.next()).e().m();
            if (s10M.b(new A4$$ExternalSyntheticLambda3())) {
                s10M = ((S10) ((ArrayList) abstractC1305Qs.J2()).get(s10M.b.q().J2())).m();
            }
            AbstractC3233vn.a(s10M, setC, setC2);
        }
        int size = setC2.size() + setC.size();
        int i = this.b.e;
        if (size <= i) {
            return false;
        }
        abstractC3477z20.d(size, i);
        return true;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final void a(Y y) {
        this.g -= C3546c0.b(y.b);
    }
}
