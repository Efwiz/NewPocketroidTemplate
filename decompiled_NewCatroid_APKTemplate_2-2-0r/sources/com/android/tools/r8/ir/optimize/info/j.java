package com.android.tools.r8.ir.optimize.info;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1008Gg;
import com.android.tools.r8.internal.AbstractC1042Hg;
import com.android.tools.r8.internal.AbstractC1049Hn;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1380Tn;
import com.android.tools.r8.internal.AbstractC1428Un;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1702ba;
import com.android.tools.r8.internal.AbstractC1775ca;
import com.android.tools.r8.internal.AbstractC1954eu;
import com.android.tools.r8.internal.AbstractC2164ha;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2640nn;
import com.android.tools.r8.internal.AbstractC3203vK;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.C0827Af;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1385Ts;
import com.android.tools.r8.internal.C1500Xe;
import com.android.tools.r8.internal.C1552Zd;
import com.android.tools.r8.internal.C1616aU;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1769cV;
import com.android.tools.r8.internal.C1793co;
import com.android.tools.r8.internal.C1805cz;
import com.android.tools.r8.internal.C2126h10;
import com.android.tools.r8.internal.C2332jo;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3012sl;
import com.android.tools.r8.internal.C3041t9;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3406y2;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.E9;
import com.android.tools.r8.internal.EnumC1076Il;
import com.android.tools.r8.internal.FH;
import com.android.tools.r8.internal.GW;
import com.android.tools.r8.internal.I5;
import com.android.tools.r8.internal.IW$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.InterfaceC2488lo;
import com.android.tools.r8.internal.O3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.ir.optimize.P;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.y3;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class j {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C3041t9 b;
    private final C0722p1 c;
    private final A1 d;

    public j(C0702m c0702m, C3012sl c3012sl) {
        this.a = c0702m;
        this.b = c0702m.Y().m1() ? new C3041t9(c0702m, c3012sl) : null;
        this.c = c0702m.a();
        this.d = c0702m.Y();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C0729q2 c0729q2) {
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:240:0x057e  */
    /* JADX WARN: Failed to find 'out' block for switch in B:128:0x02e3. Please report as an issue. */
    public final void a(o5 o5Var, C2409kl c2409kl, n nVar, P p, AbstractC1049Hn abstractC1049Hn, TE te, y3 y3Var) {
        BitSet bitSet;
        AbstractC1380Tn abstractC1380TnA;
        Iterator<C3332x4> it;
        W0 w0E = o5Var.e();
        y3Var.a("Identify bridge info");
        nVar.a(w0E, I5.a(w0E, c2409kl));
        y3Var.c();
        y3Var.a("Identify returns argument");
        o5 o5VarH = c2409kl.h();
        W0 w0E2 = o5VarH.e();
        List<C3332x4> listE = c2409kl.e();
        if (listE.isEmpty()) {
            nVar.c(o5VarH);
        } else {
            C1616aU c1616aUB0 = listE.get(0).m().B0();
            if (!c1616aUB0.K2()) {
                S10 s10L2 = c1616aUB0.L2();
                for (int i = 1; i < listE.size(); i++) {
                    if (listE.get(i).m().B0().L2() != s10L2) {
                        s10L2 = null;
                    }
                }
                if (s10L2 != null) {
                    S10 s10M = s10L2.m();
                    if (!s10M.i()) {
                        AbstractC2412ko abstractC2412ko = s10M.b;
                        if (abstractC2412ko.m1()) {
                            nVar.a(w0E2, abstractC2412ko.q().J2());
                        }
                        B1 b1A = abstractC2412ko.a(this.a, o5VarH);
                        if (b1A.y()) {
                            nVar.a(w0E2, this.a, b1A);
                            C3041t9 c3041t9 = this.b;
                            if (c3041t9 != null) {
                                c3041t9.a(o5VarH, b1A, te);
                            }
                        } else if (s10L2.w().I()) {
                            nVar.a(w0E2, this.a, GW.a(AbstractC3203vK.b(this.a, o5VarH, s10M)));
                        }
                    }
                }
            }
        }
        y3Var.c();
        if (this.d.y) {
            y3Var.a("Compute class inlining constraint");
            nVar.a(o5Var, AbstractC2164ha.a(this.a, o5Var, c2409kl, y3Var));
            y3Var.c();
        }
        y3Var.a("Compute enum unboxer method classification");
        if (!this.a.K()) {
            nVar.a(o5Var, AbstractC1042Hg.a(this.a, o5Var, c2409kl, te));
        } else if (this.a.e0().c()) {
            nVar.a(o5Var);
        } else {
            boolean z = e;
            if (!z) {
                AbstractC1008Gg abstractC1008GgN = o5Var.B().n();
                abstractC1008GgN.getClass();
                if (abstractC1008GgN instanceof E9) {
                    AbstractC1008Gg abstractC1008GgA = AbstractC1042Hg.a(this.a, o5Var, c2409kl, te);
                    if (!z && !(abstractC1008GgA instanceof E9)) {
                        throw new AssertionError();
                    }
                    if (!z && abstractC1008GgA.a().c() != abstractC1008GgN.a().c()) {
                        throw new AssertionError();
                    }
                } else if (!z && !(abstractC1008GgN instanceof C2126h10)) {
                    throw new AssertionError();
                }
            }
        }
        y3Var.c();
        if (this.a.Y().x) {
            y3Var.a("Compute simple inlining constraint");
            nVar.a(o5Var, new C1769cV(this.a, o5Var).a(c2409kl));
            y3Var.c();
        }
        y3Var.a("Compute dynamic return type");
        if (p != null && o5Var.G().X0()) {
            AbstractC1154Lf abstractC1154LfA = p.a(o5Var, c2409kl);
            if (!abstractC1154LfA.h() && !abstractC1154LfA.m()) {
                if (abstractC1154LfA.l()) {
                    W0 w0E3 = o5Var.e();
                    C0702m c0702m = this.a;
                    nVar.a(w0E3, c0702m, c0702m.b().a());
                    nVar.a(this.a, o5Var.e(), abstractC1154LfA);
                } else if (abstractC1154LfA.k()) {
                    nVar.a(this.a, o5Var.e(), abstractC1154LfA);
                } else {
                    C0767v2 c0767v2G = o5Var.G();
                    C0702m c0702m2 = this.a;
                    c0767v2G.getClass();
                    if (abstractC1154LfA.a().b(this.a, AbstractC1154Lf.a(c0702m2, AbstractC3472z00.a(c0767v2G, WH.i(), (C0702m<?>) c0702m2)))) {
                        nVar.a(this.a, o5Var.e(), abstractC1154LfA);
                    }
                }
            }
        }
        y3Var.c();
        if (this.d.A) {
            y3Var.a("Compute initialized classes on normal exits");
            if (this.d.A) {
                ((C3733i) this.a.f()).i();
                Set setA = AbstractC2640nn.a(this.a.i0(), c2409kl);
                if (setA != null && !setA.isEmpty()) {
                    nVar.a(setA, w0E);
                }
            }
            y3Var.c();
        }
        y3Var.a("Compute instance initializer info");
        boolean z2 = e;
        if (!z2 && ((C3733i) this.a.f()).b(w0E)) {
            throw new AssertionError();
        }
        if (w0E.C1()) {
            if (!z2 && abstractC1049Hn == null) {
                throw new AssertionError();
            }
            if (!w0E.f.Z() && !((C3733i) this.a.f()).x.containsKey(w0E.getReference())) {
                FH fh = new FH(abstractC1049Hn);
                o5 o5VarH2 = c2409kl.h();
                if (o5VarH2.a().a(this.d.a)) {
                    abstractC1380TnA = null;
                } else {
                    O3 o3A = O3.a();
                    S10 s10O = c2409kl.o();
                    Iterator<C3332x4> it2 = c2409kl.d.iterator();
                    boolean z3 = false;
                    while (true) {
                        if (it2.hasNext()) {
                            C3332x4 next = it2.next();
                            if (next.D()) {
                                z3 = true;
                            }
                            Iterator<AbstractC2412ko> it3 = next.p().iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    AbstractC2412ko next2 = it3.next();
                                    switch (next2.G2()) {
                                        case 0:
                                        case 4:
                                        case 7:
                                        case 10:
                                        case 11:
                                        case 12:
                                        case 16:
                                        case 20:
                                        case 21:
                                        case 27:
                                        case 29:
                                        case 45:
                                        case 47:
                                        case 53:
                                        case 55:
                                        case 57:
                                        case 58:
                                        case 63:
                                        case 65:
                                        case 67:
                                        case 68:
                                            it = it2;
                                            z3 = z3;
                                            if (next2.c(this.a, o5VarH2)) {
                                                fh.g();
                                            }
                                            it2 = it;
                                            z3 = z3;
                                            break;
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 8:
                                        case 13:
                                        case 14:
                                        case 17:
                                        case 18:
                                        case 19:
                                        case 22:
                                        case 23:
                                        case 26:
                                        case 32:
                                        case 35:
                                        case 37:
                                        case 39:
                                        case 41:
                                        case 42:
                                        case 43:
                                        case 44:
                                        case 46:
                                        case 48:
                                        case 50:
                                        case 51:
                                        case 52:
                                        case 54:
                                        case 60:
                                        case 61:
                                        case 64:
                                        case 66:
                                        default:
                                            fh.d().f().g().h();
                                            it2 = it2;
                                            break;
                                        case 5:
                                        case 9:
                                        case 15:
                                        case 24:
                                        case 56:
                                            break;
                                        case 6:
                                            fh.g();
                                            break;
                                        case 25:
                                        case 31:
                                        case 62:
                                            fh.f();
                                            break;
                                        case 28:
                                        case 59:
                                            it = it2;
                                            z3 = z3;
                                            AbstractC1182Mh abstractC1182MhN = next2.N();
                                            T0 t0S = ((C3733i) this.a.f()).c(abstractC1182MhN.getField()).s();
                                            if (t0S == null) {
                                                abstractC1380TnA = null;
                                            } else {
                                                fh.a(t0S);
                                                if (abstractC1182MhN.c(this.a, o5VarH2)) {
                                                    fh.g();
                                                    if (abstractC1182MhN instanceof QW) {
                                                        fh.d();
                                                    }
                                                }
                                                it2 = it;
                                                z3 = z3;
                                            }
                                            break;
                                        case 30:
                                            it = it2;
                                            z3 = z3;
                                            C1793co c1793coV = next2.V();
                                            if (((C3733i) this.a.f()).c(c1793coV.getField()).s() == null) {
                                                abstractC1380TnA = null;
                                            } else {
                                                S10 s10E = c1793coV.e();
                                                s10E.getClass();
                                                if (s10E.a(o3A, CM.a()) != s10O || c1793coV.b(this.a, o5VarH2, C2332jo.a)) {
                                                    fh.g();
                                                }
                                                S10 s10Value = c1793coV.value();
                                                s10Value.getClass();
                                                S10 s10A = s10Value.a(o3A, CM.a());
                                                if (!s10A.c0()) {
                                                    fh.f();
                                                }
                                                if (a(s10A, s10O, o3A)) {
                                                    fh.h();
                                                }
                                                it2 = it;
                                                z3 = z3;
                                            }
                                            break;
                                        case 33:
                                            C1166Ls c1166LsZ = next2.Z();
                                            C0684j2 c0684j2S2 = c1166LsZ.S2();
                                            C0702m c0702m3 = this.a;
                                            c0702m3.getClass();
                                            it = it2;
                                            W0 w0C = c0684j2S2.c(c0702m3.c(c0684j2S2.e));
                                            if (w0C != null) {
                                                if (w0C.C1() && c1166LsZ.U2().m() == s10O) {
                                                    if (fh.c() && fh.b() != w0C.getReference()) {
                                                    }
                                                    C0722p1 c0722p1 = this.c;
                                                    if (c0684j2S2 == c0722p1.r4.k || c0684j2S2 == c0722p1.g4.g) {
                                                        fh.a(c0684j2S2);
                                                    } else {
                                                        fh.a(w0C.i1().a(c1166LsZ));
                                                        for (int i2 = 1; i2 < ((ArrayList) c1166LsZ.J2()).size(); i2++) {
                                                            S10 s10 = (S10) ((ArrayList) c1166LsZ.J2()).get(i2);
                                                            s10.getClass();
                                                            S10 s10A2 = s10.a(o3A, CM.a());
                                                            if (a(s10A2, s10O, o3A)) {
                                                                fh.h();
                                                            }
                                                            if (!s10A2.c0()) {
                                                                fh.f();
                                                            }
                                                        }
                                                        fh.a(c0684j2S2);
                                                    }
                                                } else {
                                                    z3 = z3;
                                                    fh.d().g();
                                                    Iterator it4 = ((ArrayList) c1166LsZ.h1()).iterator();
                                                    while (it4.hasNext()) {
                                                        if (a((S10) it4.next(), s10O, o3A)) {
                                                            fh.h();
                                                        }
                                                    }
                                                }
                                                it2 = it;
                                                z3 = z3;
                                                break;
                                            }
                                            abstractC1380TnA = null;
                                            break;
                                        case 34:
                                        case 38:
                                        case 40:
                                            AbstractC1305Qs abstractC1305QsB0 = next2.b0();
                                            fh.d().g();
                                            Iterator it5 = ((ArrayList) abstractC1305QsB0.J2()).iterator();
                                            while (it5.hasNext()) {
                                                if (a((S10) it5.next(), s10O, o3A)) {
                                                    fh.h();
                                                }
                                                break;
                                            }
                                            break;
                                        case 36:
                                            C1385Ts c1385TsE0 = next2.e0();
                                            if (c1385TsE0.a(this.a, o5VarH2, C2332jo.a)) {
                                                fh.g();
                                            }
                                            Iterator it6 = ((ArrayList) c1385TsE0.J2()).iterator();
                                            while (it6.hasNext()) {
                                                if (a((S10) it6.next(), s10O, o3A)) {
                                                    fh.h();
                                                }
                                                break;
                                            }
                                            break;
                                        case 49:
                                            if (next2.t0().a(this.a, o5VarH2, C2332jo.a)) {
                                                fh.d().g();
                                            }
                                            break;
                                    }
                                }
                            }
                        } else {
                            if (z3 && fh.e()) {
                                fh.f();
                            }
                            abstractC1380TnA = fh.a();
                        }
                    }
                }
                nVar.a(w0E, AbstractC1428Un.a(abstractC1380TnA));
            }
        }
        y3Var.c();
        y3Var.a("Compute may have side effects");
        a(nVar, w0E, c2409kl);
        y3Var.c();
        y3Var.a("Return value only depends on argument");
        if (this.d.C && C1500Xe.a(this.a.i0(), c2409kl)) {
            nVar.d(w0E);
        }
        y3Var.c();
        y3Var.a("Compute non-null-param-or-throw");
        if (w0E.i1().q() != null) {
            bitSet = null;
        } else {
            List<S10> listC = c2409kl.c();
            BitSet bitSet2 = new BitSet();
            for (int i3 = 0; i3 < listC.size(); i3++) {
                S10 s11 = listC.get(i3);
                if (s11.S() && a(c2409kl, s11)) {
                    bitSet2.set(i3);
                }
            }
            if (bitSet2.isEmpty()) {
                bitSet = null;
            } else {
                nVar.a(w0E, bitSet2);
                bitSet = bitSet2;
            }
        }
        y3Var.c();
        y3Var.a("Compute non-null-param-on-normal-exits");
        Set setC = OU.c();
        setC.addAll(c2409kl.e());
        C0827Af c0827Af = new C0827Af(c2409kl, 2);
        List<S10> listC2 = c2409kl.c();
        BitSet bitSet3 = new BitSet();
        if (bitSet != null) {
            bitSet3.or(bitSet);
        }
        for (int i4 = 0; i4 < listC2.size(); i4++) {
            if (!bitSet3.get(i4)) {
                S10 s12 = listC2.get(i4);
                if (s12.w().I() && a(c2409kl, s12, c0827Af, setC)) {
                    bitSet3.set(i4);
                }
            }
        }
        if (!bitSet3.isEmpty()) {
            nVar.b(c2409kl.u(), bitSet3);
        }
        y3Var.c();
        y3Var.a("Compute unused arguments");
        BitSet bitSet4 = new BitSet(o5Var.e().h1());
        InterfaceC2488lo interfaceC2488loR = c2409kl.j().R();
        for (C3406y2 c3406y2Q = interfaceC2488loR.next().q(); c3406y2Q != null; c3406y2Q = interfaceC2488loR.next().q()) {
            if (!c3406y2Q.b().y()) {
                bitSet4.set(c3406y2Q.J2());
            }
        }
        nVar.a(o5Var, bitSet4);
        y3Var.c();
    }

    private static boolean a(S10 s10, S10 s11, O3 o3) {
        return (s10.i() && s11.C()) || s10.a(o3, CM.a()) == s11;
    }

    private boolean a(final C2409kl c2409kl, final S10 s10) {
        i iVar;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.ir.optimize.info.j$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.a(s10, c2409kl, (AbstractC2412ko) obj, (InterfaceC2488lo) obj2);
            }
        };
        int iA = c2409kl.A();
        try {
            ArrayDeque arrayDeque = new ArrayDeque();
            C3332x4 c3332x4J = c2409kl.j();
            arrayDeque.add(c3332x4J);
            c3332x4J.e(iA);
            while (!arrayDeque.isEmpty()) {
                C3332x4 c3332x4 = (C3332x4) arrayDeque.poll();
                if (!e && !c3332x4.c(iA)) {
                    throw new AssertionError();
                }
                i iVar2 = i.d;
                InterfaceC2488lo interfaceC2488loR = c3332x4.R();
                while (true) {
                    iVar = i.d;
                    if (iVar2 != iVar || !interfaceC2488loR.hasNext()) {
                        break;
                    }
                    iVar2 = (i) biFunction.apply(interfaceC2488loR.next(), interfaceC2488loR);
                }
                if (iVar2 != i.c) {
                    if (iVar2 != i.a) {
                        if (iVar2 == i.b) {
                            boolean z = e;
                            if (!z && c3332x4.u().isEmpty()) {
                                throw new AssertionError();
                            }
                            AbstractC2412ko last = c3332x4.p().getLast();
                            if (!z && !last.N1()) {
                                throw new AssertionError();
                            }
                            C3332x4 c3332x4C = last.Q().c(0);
                            if (!c3332x4C.c(iA)) {
                                arrayDeque.add(c3332x4C);
                                c3332x4C.e(iA);
                            }
                        } else {
                            boolean z2 = e;
                            if (!z2 && iVar2 != iVar) {
                                throw new AssertionError();
                            }
                            if (c3332x4.u().isEmpty()) {
                                AbstractC2412ko last2 = c3332x4.p().getLast();
                                if (!z2 && !last2.u2() && !last2.B2()) {
                                    throw new AssertionError();
                                }
                            } else {
                                for (C3332x4 c3332x5 : c3332x4.z()) {
                                    if (!c3332x5.c(iA)) {
                                        arrayDeque.add(c3332x5);
                                        c3332x5.e(iA);
                                    }
                                }
                            }
                        }
                    }
                }
                c2409kl.b(iA);
                return false;
            }
            c2409kl.b(iA);
            return true;
        } catch (Throwable th) {
            c2409kl.b(iA);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public i a(S10 s10, C2409kl c2409kl, AbstractC2412ko abstractC2412ko, InterfaceC2488lo interfaceC2488lo) {
        AbstractC2412ko abstractC2412koO;
        C3332x4 c3332x4H = abstractC2412ko.h();
        if (!c3332x4H.D() && abstractC2412ko.N1() && abstractC2412ko.Q().N2() && ((S10) ((ArrayList) abstractC2412ko.h1()).get(0)).equals(s10) && (abstractC2412ko.Q().L2() == EnumC1076Il.EQ || abstractC2412ko.Q().L2() == EnumC1076Il.NE)) {
            return i.b;
        }
        if (abstractC2412ko instanceof C1721bt) {
            C1721bt c1721btG0 = abstractC2412ko.g0();
            C0702m c0702m = this.a;
            if (!c0702m.Y().s1().a) {
                C1805cz c1805cz = c0702m.a().z4.c;
                C0684j2 c0684j2A = c0702m.E().a((AbstractC1476Wj) null, c1721btG0.S2());
                if ((c0684j2A.b(c1805cz.c) || c0684j2A.b(c1805cz.d)) && c1721btG0.c(0) == s10 && c0684j2A.B0().H0().startsWith(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                    return i.a;
                }
            }
            C0702m c0702m2 = this.a;
            if (!c0702m2.Y().s1().a) {
                C1805cz c1805cz2 = c0702m2.a().z4.c;
                C0684j2 c0684j2A2 = c0702m2.E().a((AbstractC1476Wj) null, c1721btG0.S2());
                if ((c0684j2A2.b(c1805cz2.a) || c0684j2A2.b(c1805cz2.b)) && c0684j2A2.B0().H0().startsWith(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                    Iterator<C3332x4> it = c3332x4H.y().iterator();
                    while (it.hasNext()) {
                        AbstractC1954eu abstractC1954euM = it.next().m();
                        if (abstractC1954euM.N1() && abstractC1954euM.Q().N2() && ((S10) ((ArrayList) abstractC1954euM.h1()).get(0)).equals(s10) && (abstractC1954euM.Q().L2() == EnumC1076Il.EQ || abstractC1954euM.Q().L2() == EnumC1076Il.NE)) {
                            return i.a;
                        }
                    }
                    return i.d;
                }
            }
        }
        C0722p1 c0722p1A = this.a.a();
        if (abstractC2412ko.p2() && abstractC2412ko.t0().h == c0722p1A.s3 && (abstractC2412koO = interfaceC2488lo.o()) != null && abstractC2412koO.W1() && abstractC2412koO.Z().S2() == c0722p1A.v4.a) {
            interfaceC2488lo.next();
            return i.d;
        }
        if (abstractC2412ko.a(this.a, c2409kl.h(), s10)) {
            if (!c3332x4H.D()) {
                return i.a;
            }
        } else if (abstractC2412ko.c(this.a, c2409kl.h())) {
            if ((abstractC2412ko instanceof C1033Hc) && !abstractC2412ko.i1()) {
                return i.d;
            }
            return i.c;
        }
        return i.d;
    }

    private void a(n nVar, W0 w0, C2409kl c2409kl) {
        boolean z = e;
        if (!z && w0.f.Z()) {
            throw new AssertionError();
        }
        if (this.d.B && !((C3733i) this.a.f()).x.containsKey(w0.getReference())) {
            o5 o5VarH = c2409kl.h();
            if (w0.u1()) {
                int iA = AbstractC1775ca.a(this.a, c2409kl);
                if (AbstractC1702ba.b(iA)) {
                    nVar.b(w0);
                    nVar.e(w0);
                    return;
                } else if (AbstractC1702ba.a(iA)) {
                    nVar.e(w0);
                    return;
                } else {
                    if (z || this.d.W0) {
                        return;
                    }
                    this.a.D().a(o5VarH.a(), new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.j$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return this.f$0.a((C0729q2) obj);
                        }
                    }, new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.j$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return j.b((C0729q2) obj);
                        }
                    });
                    return;
                }
            }
            if (w0.Q1()) {
                return;
            }
            if (w0.C1()) {
                C0729q2 c0729q2A = o5VarH.a();
                if (!c0729q2A.isInterface()) {
                    C0722p1 c0722p1A = this.a.a();
                    W0 w0T = ((C3733i) this.a.f()).e(c0729q2A, this.a.a().g4.h).t();
                    if (w0T != null && w0T.getReference() != c0722p1A.r4.l && w0T.getReference() != c0722p1A.g4.h) {
                        return;
                    }
                }
            }
            for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
                if (abstractC2412ko.a(this.a.a()) && abstractC2412ko.Z().U2().m().b(new IW$$ExternalSyntheticLambda0())) {
                    if (abstractC2412ko.a(this.a, o5VarH, C2332jo.c)) {
                        return;
                    }
                } else if (abstractC2412ko.c(this.a, o5VarH)) {
                    return;
                }
            }
            nVar.b(w0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2) {
        return ((C3733i) this.a.f()).h(c0729q2.getType());
    }

    private boolean a(C2409kl c2409kl, S10 s10, C0827Af c0827Af, Set set) {
        if (!e && !s10.w().I()) {
            throw new AssertionError();
        }
        if (s10.Q()) {
            return true;
        }
        Set setC = OU.c();
        for (final AbstractC2412ko abstractC2412ko : s10.a(C1552Zd.a())) {
            if (abstractC2412ko.u1()) {
                if (!e && abstractC2412ko.h().D() && abstractC2412ko.h().p().stream().filter(new Predicate() { // from class: com.android.tools.r8.ir.optimize.info.j$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return j.a(abstractC2412ko, (AbstractC2412ko) obj);
                    }
                }).findFirst().get() != abstractC2412ko) {
                    throw new AssertionError();
                }
                setC.add(abstractC2412ko.h());
            } else if (abstractC2412ko.a(this.a, c2409kl.h(), s10)) {
                if (abstractC2412ko.h().D()) {
                    setC.addAll(abstractC2412ko.h().u());
                } else {
                    setC.add(abstractC2412ko.h());
                }
            } else if (abstractC2412ko.N1() && abstractC2412ko.Q().N2() && (abstractC2412ko.Q().L2() == EnumC1076Il.EQ || abstractC2412ko.Q().L2() == EnumC1076Il.NE)) {
                setC.add(abstractC2412ko.Q().Q2());
            }
        }
        if (setC.isEmpty()) {
            return false;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C3332x4 c3332x4 = (C3332x4) it.next();
            Iterator it2 = setC.iterator();
            do {
                if (!it2.hasNext()) {
                    Set setC2 = OU.c();
                    ArrayDeque arrayDeque = new ArrayDeque(c3332x4.y());
                    while (!arrayDeque.isEmpty()) {
                        C3332x4 c3332x5 = (C3332x4) arrayDeque.poll();
                        if (c3332x5 != c2409kl.j()) {
                            if (!setC2.add(c3332x5)) {
                                if (arrayDeque.isEmpty()) {
                                }
                            } else {
                                Iterator it3 = setC.iterator();
                                do {
                                    if (!it3.hasNext()) {
                                        arrayDeque.addAll(c3332x5.y());
                                        break;
                                    }
                                } while (!c0827Af.a(c3332x5, (C3332x4) it3.next()));
                            }
                        }
                        return false;
                    }
                    if (e || arrayDeque.isEmpty()) {
                        break;
                    }
                    throw new AssertionError();
                }
            } while (!c0827Af.a(c3332x4, (C3332x4) it2.next()));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 == abstractC2412ko || abstractC2412ko2.g();
    }
}
