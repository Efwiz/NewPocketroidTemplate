package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0798x1;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1346Sg {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final Map b;
    private final C0722p1 c;
    private final com.android.tools.r8.utils.A1 d;
    private final C3226vg e;
    private final C1319Rg f;
    private final C1502Xg g;

    C1346Sg(C0702m c0702m, G4 g4, C1319Rg c1319Rg, C3226vg c3226vg, C1502Xg c1502Xg) {
        this.a = c0702m;
        this.b = g4;
        this.c = c0702m.a();
        this.d = c0702m.Y();
        this.f = c1319Rg;
        this.e = c3226vg;
        this.g = c1502Xg;
    }

    /* JADX WARN: Code duplicated, block: B:253:0x05f7  */
    /* JADX WARN: Code duplicated, block: B:256:0x0612  */
    /* JADX WARN: Code duplicated, block: B:261:0x062f  */
    /* JADX WARN: Code duplicated, block: B:264:0x0693  */
    /* JADX WARN: Code duplicated, block: B:270:0x06bd  */
    /* JADX WARN: Code duplicated, block: B:273:0x06d2  */
    /* JADX WARN: Code duplicated, block: B:276:0x06db  */
    /* JADX WARN: Code duplicated, block: B:277:0x06df  */
    /* JADX WARN: Code duplicated, block: B:280:0x06eb  */
    /* JADX WARN: Code duplicated, block: B:282:0x06f7  */
    /* JADX WARN: Code duplicated, block: B:286:0x0711  */
    /* JADX WARN: Code duplicated, block: B:288:0x0723  */
    /* JADX WARN: Code duplicated, block: B:290:0x0735  */
    /* JADX WARN: Code duplicated, block: B:291:0x0742  */
    /* JADX WARN: Code duplicated, block: B:294:0x078a  */
    /* JADX WARN: Code duplicated, block: B:295:0x0794  */
    /* JADX WARN: Code duplicated, block: B:298:0x079c  */
    /* JADX WARN: Code duplicated, block: B:300:0x07ae  */
    /* JADX WARN: Code duplicated, block: B:302:0x07b2  */
    /* JADX WARN: Code duplicated, block: B:307:0x07c7  */
    /* JADX WARN: Code duplicated, block: B:310:0x07cf  */
    /* JADX WARN: Code duplicated, block: B:312:0x07d9  */
    /* JADX WARN: Code duplicated, block: B:315:0x07ea  */
    /* JADX WARN: Code duplicated, block: B:317:0x07f2  */
    /* JADX WARN: Code duplicated, block: B:323:0x080f  */
    /* JADX WARN: Code duplicated, block: B:325:0x0815  */
    /* JADX WARN: Code duplicated, block: B:329:0x082e  */
    /* JADX WARN: Code duplicated, block: B:331:0x083c  */
    /* JADX WARN: Code duplicated, block: B:332:0x083e  */
    /* JADX WARN: Code duplicated, block: B:337:0x0853  */
    /* JADX WARN: Code duplicated, block: B:338:0x0855  */
    /* JADX WARN: Code duplicated, block: B:349:0x0879  */
    /* JADX WARN: Code duplicated, block: B:351:0x0881  */
    /* JADX WARN: Code duplicated, block: B:395:0x0609 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:396:0x0618 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:397:0x061c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:408:0x0709 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:420:0x069f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:421:0x06b7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:423:0x068d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:424:0x068d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:42:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:85:0x01c2 A[EDGE_INSN: B:85:0x01c2->B:86:0x01c4 BREAK  A[LOOP:6: B:78:0x018b->B:84:0x01ba], PHI: r12
      0x01c2: PHI (r12v5 com.android.tools.r8.internal.S10) = 
      (r12v3 com.android.tools.r8.internal.S10)
      (r12v3 com.android.tools.r8.internal.S10)
      (r12v67 com.android.tools.r8.internal.S10)
     binds: [B:75:0x017f, B:77:0x0189, B:416:0x01c2] A[DONT_GENERATE, DONT_INLINE]] */
    final Set a(C2409kl c2409kl, TE te, com.android.tools.r8.graph.proto.k kVar) {
        Set set;
        Set set2;
        C1721bt c1721btG0;
        AbstractC0748t0 abstractC0748t0H;
        int i;
        S10 s10A;
        Set set3;
        Set set4;
        com.android.tools.r8.graph.o5 o5Var;
        FG fgU0;
        V2 v2S;
        C1969f3 c1969f3A;
        AbstractC3472z00 abstractC3472z00R;
        C0767v2 c0767v2R;
        boolean z;
        C1969f3 c1969f3A2;
        C1189Mn c1189MnT;
        com.android.tools.r8.graph.Y0 field;
        com.android.tools.r8.graph.o5 o5VarA;
        C1721bt c1721bt;
        QW qwF0;
        com.android.tools.r8.graph.Y0 field2;
        C0767v2 c0767v2;
        C1721bt c1721btB;
        C2115gt c2115gtI0;
        C3332x4 c3332x4H;
        Set set5;
        S10 s10C;
        C0767v2 c0767v2A;
        C0767v2 c0767v2R2;
        if (this.e.c()) {
            return OU.c();
        }
        if (!h) {
            c2409kl.c(this.a);
        }
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        VE veB = te.b();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        InterfaceC2488lo interfaceC2488loR = c2409kl.j().R();
        int iH = kVar.a().h() + com.android.tools.r8.utils.M.a(!c2409kl.h().e().E0()) + c2409kl.h().getReference().F0();
        int i2 = 0;
        for (int i3 = 0; i3 < iH; i3++) {
            com.android.tools.r8.graph.proto.b bVarA = kVar.a().a(i3);
            if (!bVarA.c()) {
                AbstractC2412ko next = interfaceC2488loR.next();
                if (!h && !next.m1()) {
                    throw new AssertionError();
                }
                if (bVarA instanceof com.android.tools.r8.graph.proto.l) {
                    C0767v2 c0767v2B = bVarA.b().h().b(this.c);
                    C0767v2 c0767v3 = this.e.b(c0767v2B) ? c0767v2B : null;
                    if (c0767v3 != null) {
                        identityHashMap.put(next, c0767v3);
                    }
                }
            }
        }
        Set setC = OU.c();
        B4 b4S = c2409kl.s();
        Set setC2 = OU.c();
        Set setC3 = OU.c();
        S10 s10A2 = null;
        while (b4S.hasNext()) {
            C3332x4 next2 = b4S.next();
            setC2.add(next2);
            for (ML ml : next2.x()) {
                AbstractC3472z00 abstractC3472z00W = ml.w();
                if (abstractC3472z00W.w()) {
                    c0767v2R2 = abstractC3472z00W.b().R();
                    if (!this.e.b(c0767v2R2)) {
                        c0767v2R2 = null;
                    }
                } else {
                    c0767v2R2 = null;
                }
                if (c0767v2R2 != null) {
                    for (int i4 = i2; i4 < ml.k0().size(); i4++) {
                        if (ml.c(i4).w().E()) {
                            if (s10A2 == null) {
                                InterfaceC2641no interfaceC2641noA = c2409kl.j().a(c2409kl);
                                while (interfaceC2641noA.hasNext() && interfaceC2641noA.o().m1()) {
                                    interfaceC2641noA.next();
                                }
                                s10A2 = interfaceC2641noA.a(c2409kl, this.d, i2);
                            }
                            ml.a(i4, s10A2);
                        }
                    }
                }
            }
            InterfaceC2641no interfaceC2641noA2 = next2.a(c2409kl);
            while (interfaceC2641noA2.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) interfaceC2641noA2.next();
                if (setC3.contains(abstractC2412ko)) {
                    interfaceC2641noA2.s();
                } else {
                    abstractC2412ko.getClass();
                    if (abstractC2412ko instanceof C2179hn) {
                        C0767v2 c0767v2K2 = abstractC2412ko.R().K2();
                        if (!this.e.b(c0767v2K2)) {
                            c0767v2K2 = null;
                        }
                        if (c0767v2K2 != null) {
                            interfaceC2641noA2.s();
                        }
                    } else {
                        int i5 = 2;
                        if (!abstractC2412ko.N1()) {
                            s10A2 = s10A2;
                            break;
                        }
                        C1015Gl c1015GlQ = abstractC2412ko.Q();
                        if (c1015GlQ.N2()) {
                            s10A2 = s10A2;
                            break;
                        }
                        while (true) {
                            if (i2 >= i5) {
                                s10A2 = s10A2;
                                break;
                            }
                            if (a(c1015GlQ.a(i2), identityHashMap) != null) {
                                int i6 = 1 - i2;
                                if (c1015GlQ.a(i6).w().E()) {
                                    interfaceC2641noA2.previous();
                                    s10A2 = s10A2;
                                    c1015GlQ.a(i6, interfaceC2641noA2.a(c2409kl, this.d, 0));
                                    interfaceC2641noA2.next();
                                    break;
                                }
                            }
                            i2++;
                            s10A2 = s10A2;
                            i5 = 2;
                        }
                        if (abstractC2412ko.Z1()) {
                            AbstractC1332Rs abstractC1332RsC0 = abstractC2412ko.c0();
                            C0767v2 c0767v2A2 = a(abstractC1332RsC0.U2(), identityHashMap);
                            C0684j2 c0684j2S2 = abstractC1332RsC0.S2();
                            if (c0767v2A2 != null) {
                                C0798x1 c0798x1 = this.c.r4;
                                set2 = setC3;
                                if (c0684j2S2 == c0798x1.d || c0684j2S2.b(c0798x1.j)) {
                                    a(interfaceC2641noA2, abstractC1332RsC0, this.g.a().e(this.a, o5VarH, veB));
                                } else if (c0684j2S2.b(this.c.r4.i)) {
                                    a(interfaceC2641noA2, abstractC1332RsC0, this.g.a().d(this.a, o5VarH, veB));
                                } else {
                                    C0798x1 c0798x2 = this.c.r4;
                                    if (c0684j2S2 == c0798x2.g || c0684j2S2 == c0798x2.h) {
                                        a(interfaceC2641noA2, abstractC1332RsC0, this.g.a().c(this.a, o5VarH, veB));
                                    } else if (c0684j2S2 == c0798x2.e) {
                                        interfaceC2641noA2.d(new C1721bt(this.g.a(c0767v2A2).a(this.a, this.c.r4.a, o5VarH, veB).getReference(), abstractC1332RsC0.b(), abstractC1332RsC0.J2()));
                                    } else if (c0684j2S2.b(c0798x2.f)) {
                                        C0684j2 c0684j2D = this.f.d(c0684j2S2);
                                        if ((abstractC1332RsC0 instanceof C1798ct) || c0684j2D == c0684j2S2) {
                                            interfaceC2641noA2.d(new C1721bt(this.g.a(c0767v2A2).a(this.a, this.c.r4.a, o5VarH, veB).getReference(), abstractC1332RsC0.b(), abstractC1332RsC0.J2()));
                                        }
                                        set = setC2;
                                    } else {
                                        if (c0684j2S2 == this.c.g4.e) {
                                            if (!h && abstractC1332RsC0.d1() && abstractC1332RsC0.b().y()) {
                                                throw new AssertionError();
                                            }
                                            a(interfaceC2641noA2, abstractC1332RsC0, this.g.a().a(this.a, o5VarH, veB));
                                        }
                                        set = setC2;
                                    }
                                }
                                setC3 = set2;
                            } else {
                                set2 = setC3;
                                C0722p1 c0722p1 = this.c;
                                if ((c0684j2S2 == c0722p1.W3.k || c0684j2S2 == c0722p1.X3.k) && (c0767v2A = a((s10C = abstractC1332RsC0.c(1)), identityHashMap)) != null) {
                                    com.android.tools.r8.graph.o5 o5VarA2 = this.g.a(c0767v2A).a(this.a, o5VarH, veB);
                                    int i7 = C1721bt.m;
                                    C1721bt c1721btB2 = ((C1646at) ((C1646at) ((C1646at) ((C1646at) new C1646at().a(o5VarA2.getReference())).a(AbstractC1639am.c(s10C))).a(this.a, c2409kl)).b(abstractC1332RsC0)).b();
                                    C0722p1 c0722p2 = this.c;
                                    com.android.tools.r8.graph.Z1 z1 = c0722p2.W3;
                                    Set set6 = setC2;
                                    C2115gt c2115gt = new C2115gt(c0684j2S2 == z1.k ? z1.l : c0722p2.X3.l, abstractC1332RsC0.f((S10) null), AbstractC1639am.a(abstractC1332RsC0.U2(), c1721btB2.b()));
                                    c2115gt.b(abstractC1332RsC0.Y0());
                                    interfaceC2641noA2.d(c1721btB2);
                                    if (next2.D()) {
                                        interfaceC2641noA2.a(c2409kl, b4S, this.a.Y()).a(c2409kl).add(c2115gt);
                                    } else {
                                        interfaceC2641noA2.add(c2115gt);
                                    }
                                    setC3 = set2;
                                    setC2 = set6;
                                } else {
                                    set = setC2;
                                }
                            }
                            i2 = 0;
                        } else {
                            set = setC2;
                            set2 = setC3;
                            if ((abstractC2412ko instanceof C1721bt) && (abstractC0748t0H = (c1721btG0 = abstractC2412ko.g0()).h(this.a, o5VarH)) != null) {
                                C0684j2 reference = abstractC0748t0H.getReference();
                                C0767v2 c0767v2B0 = reference.B0();
                                C0722p1 c0722p3 = this.c;
                                if (c0767v2B0 != c0722p3.e2) {
                                    C0767v2 c0767v2B1 = reference.B0();
                                    C0722p1 c0722p4 = this.c;
                                    if (c0767v2B1 == c0722p4.g2) {
                                        com.android.tools.r8.graph.S1 s1 = c0722p4.f4;
                                        if (reference == s1.e) {
                                            boolean z2 = h;
                                            if (!z2 && ((ArrayList) c1721btG0.J2()).size() != 1) {
                                                throw new AssertionError();
                                            }
                                            if (a(c1721btG0.c(0), identityHashMap) != null) {
                                                if (!z2 && c1721btG0.d1() && c1721btG0.b().y()) {
                                                    throw new AssertionError();
                                                }
                                                a(interfaceC2641noA2, c1721btG0, this.g.a().a(this.a, o5VarH, veB));
                                            }
                                        } else if (reference == s1.f) {
                                            if (!h && ((ArrayList) c1721btG0.J2()).size() != 2) {
                                                throw new AssertionError();
                                            }
                                            if (a(c1721btG0.c(0), identityHashMap) != null) {
                                                a(interfaceC2641noA2, c1721btG0, this.g.a().b(this.a, o5VarH, veB));
                                            }
                                        }
                                    } else {
                                        C0767v2 c0767v2B2 = reference.B0();
                                        C0722p1 c0722p5 = this.c;
                                        if (c0767v2B2 != c0722p5.X1) {
                                            C0767v2 c0767v2B3 = reference.B0();
                                            C0722p1 c0722p6 = this.c;
                                            if (c0767v2B3 == c0722p6.z2) {
                                                com.android.tools.r8.graph.G1 g1 = c0722p6.u4;
                                                if (reference != g1.a && reference == g1.b) {
                                                    if (!h && ((ArrayList) c1721btG0.J2()).size() != 1) {
                                                        throw new AssertionError();
                                                    }
                                                    i = 0;
                                                    S10 s10C2 = c1721btG0.c(0);
                                                    if (a(s10C2, identityHashMap) != null) {
                                                        c1721btG0.b().g(s10C2);
                                                        interfaceC2641noA2.s();
                                                    }
                                                }
                                            } else {
                                                i = 0;
                                                if ((abstractC0748t0H instanceof com.android.tools.r8.graph.o5) && this.b.containsKey(abstractC0748t0H.getReference())) {
                                                    com.android.tools.r8.graph.o5 o5VarS = ((C3733i) this.a.f()).d((C0684j2) this.b.get(abstractC0748t0H.getReference())).s();
                                                    if (o5VarS != null) {
                                                        AbstractC1008Gg abstractC1008GgN = o5VarS.B().n();
                                                        abstractC1008GgN.getClass();
                                                        if (abstractC1008GgN instanceof E9) {
                                                            C0767v2 c0767v2A3 = a(c1721btG0.c(abstractC1008GgN.a().c()), identityHashMap);
                                                            if (c0767v2A3 != null) {
                                                                C1721bt c1721btB3 = ((C1646at) ((C1646at) ((C1646at) new C1646at().a(o5VarS.getReference())).a(c1721btG0.J2())).a(c1721btG0.Y0())).b();
                                                                interfaceC2641noA2.d(c1721btB3);
                                                                identityHashMap.put(c1721btB3, c0767v2A3);
                                                                veB.b(o5VarS, o5VarH);
                                                            }
                                                        } else if (!h) {
                                                            throw new AssertionError();
                                                        }
                                                    } else if (!h) {
                                                        throw new AssertionError();
                                                    }
                                                }
                                            }
                                        } else if (reference == c0722p5.k4.s) {
                                            if (!h && ((ArrayList) c1721btG0.J2()).size() != 1) {
                                                throw new AssertionError();
                                            }
                                            C0767v2 c0767v2A4 = a(c1721btG0.c(0), identityHashMap);
                                            if (c0767v2A4 != null) {
                                                interfaceC2641noA2.d(new C1721bt(this.g.a(c0767v2A4).a(this.a, o5VarH, veB).getReference(), c1721btG0.b(), c1721btG0.J2()));
                                            }
                                        }
                                    }
                                } else if (reference == c0722p3.r4.c) {
                                    if (c1721btG0.c(0).I()) {
                                        C0767v2 c0767v2K3 = c1721btG0.c(0).n().A().K2();
                                        if (this.e.b(c0767v2K3)) {
                                            com.android.tools.r8.graph.o5 o5VarB = this.g.a(c0767v2K3).b(this.a, o5VarH, veB);
                                            S10 s10B = c1721btG0.b();
                                            if (s10B != null) {
                                                s10A = c2409kl.a((AbstractC3472z00) AbstractC3472z00.k());
                                                setC.addAll(s10B.g0());
                                            } else {
                                                s10A = null;
                                            }
                                            C1721bt c1721bt2 = new C1721bt(o5VarB.getReference(), s10A, Collections.singletonList((S10) ((ArrayList) c1721btG0.h1()).get(1)));
                                            interfaceC2641noA2.d(c1721bt2);
                                            identityHashMap.put(c1721bt2, c0767v2K3);
                                        }
                                    } else {
                                        i = 0;
                                    }
                                }
                            }
                            if (abstractC2412ko instanceof QW) {
                                qwF0 = abstractC2412ko.F0();
                                field2 = qwF0.getField();
                                c0767v2 = field2.e;
                                if (this.e.b(c0767v2)) {
                                    if (qwF0.f1()) {
                                        interfaceC2641noA2.s();
                                    } else {
                                        setC.addAll(qwF0.b().g0());
                                        if (this.e.c(field2)) {
                                            interfaceC2641noA2.previous();
                                            S10 s10A3 = interfaceC2641noA2.a(c2409kl, this.d, this.e.a(c0767v2));
                                            interfaceC2641noA2.next();
                                            int i8 = C1721bt.m;
                                            C1646at c1646at = (C1646at) ((C1646at) new C1646at().a(this.g.a().a(o5VarH, veB).getReference())).a(this.a, c2409kl);
                                            c1646at.getClass();
                                            c1721btB = ((C1646at) c1646at.a(AbstractC1639am.c(s10A3))).b();
                                            interfaceC2641noA2.d(c1721btB);
                                            identityHashMap.put(c1721btB, c0767v2);
                                            for (AbstractC2412ko abstractC2412ko2 : c1721btB.b().a(C1552Zd.a())) {
                                                if (abstractC2412ko2.f2()) {
                                                    c2115gtI0 = abstractC2412ko2.i0();
                                                    if (!c2115gtI0.S2().b(this.a.a().g4.c)) {
                                                        if (c2115gtI0.d1()) {
                                                            c2115gtI0.b().g(c1721btB.b());
                                                        }
                                                        c3332x4H = c2115gtI0.h();
                                                        if (c3332x4H != c1721btB.h()) {
                                                            set5 = set;
                                                            if (!set5.contains(c3332x4H)) {
                                                                c3332x4H.g(c2115gtI0);
                                                            }
                                                            set = set5;
                                                        } else {
                                                            set5 = set;
                                                        }
                                                        Set set7 = set2;
                                                        set7.add(c2115gtI0);
                                                        set2 = set7;
                                                        set = set5;
                                                    }
                                                }
                                            }
                                        } else {
                                            set4 = set2;
                                            set3 = set;
                                            if (this.e.b(field2)) {
                                                C0978Fc c0978FcA = c2409kl.a(this.e.a(field2), (com.android.tools.r8.graph.X) null);
                                                interfaceC2641noA2.d(c0978FcA);
                                                identityHashMap.put(c0978FcA, c0767v2);
                                            }
                                        }
                                        if (abstractC2412ko instanceof C1189Mn) {
                                            c1189MnT = abstractC2412ko.T();
                                            if (this.e.b(c1189MnT.getField().e)) {
                                                field = c1189MnT.getField();
                                                if (this.e.a(field.e, field).e()) {
                                                    o5VarA = this.g.a().e(this.a, o5VarH, veB);
                                                } else {
                                                    o5VarA = this.g.a(field.B0()).a(this.a, field, o5VarH, veB);
                                                }
                                                o5Var = o5VarH;
                                                c1721bt = new C1721bt(o5VarA.getReference(), c2409kl.a(AbstractC3472z00.a(o5VarA.G(), WH.i(), (C0702m<?>) this.a)), AbstractC1639am.c(c1189MnT.e()));
                                                interfaceC2641noA2.d(c1721bt);
                                                if (this.e.b(c1189MnT.getField().h)) {
                                                    identityHashMap.put(c1721bt, c1189MnT.getField().h);
                                                }
                                            } else {
                                                o5Var = o5VarH;
                                            }
                                        } else {
                                            o5Var = o5VarH;
                                        }
                                        if (abstractC2412ko.o1()) {
                                            v2S = abstractC2412ko.s();
                                            c1969f3A = v2S.J2().w().a();
                                            if (c1969f3A == null) {
                                                if (c1969f3A.U() != 1) {
                                                    abstractC3472z00R = c1969f3A.R();
                                                    if (abstractC3472z00R.w()) {
                                                        c0767v2R = abstractC3472z00R.b().R();
                                                        if (!this.e.b(c0767v2R)) {
                                                        }
                                                    } else if (!(c1969f3A.R() instanceof C1484Wr) && !v2S.J2().i()) {
                                                        c0767v2R = (C0767v2) identityHashMap.get(v2S.J2().q());
                                                    }
                                                }
                                                if (c0767v2R != null) {
                                                    if (v2S.d1()) {
                                                        setC.addAll(v2S.b().g0());
                                                    }
                                                    v2S = v2S.L2();
                                                    interfaceC2641noA2.d(v2S);
                                                    identityHashMap.put(v2S, c0767v2R);
                                                }
                                                z = h;
                                                if (!z) {
                                                    c1969f3A2 = v2S.J2().w().a();
                                                    if (c1969f3A2 == null) {
                                                        if (!z && !v2S.J2().w().E()) {
                                                            throw new AssertionError();
                                                        }
                                                    } else if (!z && v2S.d() == EnumC2523mE.OBJECT && c1969f3A2.U() <= 1 && !c1969f3A2.R().I()) {
                                                        throw new AssertionError();
                                                    }
                                                }
                                            } else if (!h && !v2S.J2().w().E()) {
                                                throw new AssertionError();
                                            }
                                            c0767v2R = null;
                                            if (c0767v2R != null) {
                                                if (v2S.d1()) {
                                                    setC.addAll(v2S.b().g0());
                                                }
                                                v2S = v2S.L2();
                                                interfaceC2641noA2.d(v2S);
                                                identityHashMap.put(v2S, c0767v2R);
                                            }
                                            z = h;
                                            if (!z) {
                                                c1969f3A2 = v2S.J2().w().a();
                                                if (c1969f3A2 == null) {
                                                    if (!z) {
                                                        throw new AssertionError();
                                                    }
                                                } else if (!z) {
                                                    throw new AssertionError();
                                                }
                                            }
                                        }
                                        if (abstractC2412ko instanceof FG) {
                                            fgU0 = abstractC2412ko.u0();
                                            if (h && !this.e.b(fgU0.h)) {
                                                throw new AssertionError();
                                            }
                                            interfaceC2641noA2.d(c2409kl.a(fgU0.J2() + 1, (com.android.tools.r8.graph.X) null));
                                        }
                                        setC3 = set4;
                                        setC2 = set3;
                                        o5VarH = o5Var;
                                        i2 = 0;
                                    }
                                }
                                i2 = i;
                                s10A2 = s10A2;
                                setC3 = set2;
                                setC2 = set;
                            }
                            set4 = set2;
                            set3 = set;
                            if (abstractC2412ko instanceof C1189Mn) {
                                c1189MnT = abstractC2412ko.T();
                                if (this.e.b(c1189MnT.getField().e)) {
                                    field = c1189MnT.getField();
                                    if (this.e.a(field.e, field).e()) {
                                        o5VarA = this.g.a().e(this.a, o5VarH, veB);
                                    } else {
                                        o5VarA = this.g.a(field.B0()).a(this.a, field, o5VarH, veB);
                                    }
                                    o5Var = o5VarH;
                                    c1721bt = new C1721bt(o5VarA.getReference(), c2409kl.a(AbstractC3472z00.a(o5VarA.G(), WH.i(), (C0702m<?>) this.a)), AbstractC1639am.c(c1189MnT.e()));
                                    interfaceC2641noA2.d(c1721bt);
                                    if (this.e.b(c1189MnT.getField().h)) {
                                        identityHashMap.put(c1721bt, c1189MnT.getField().h);
                                    }
                                } else {
                                    o5Var = o5VarH;
                                }
                            } else {
                                o5Var = o5VarH;
                            }
                            if (abstractC2412ko.o1()) {
                                v2S = abstractC2412ko.s();
                                c1969f3A = v2S.J2().w().a();
                                if (c1969f3A == null) {
                                    if (c1969f3A.U() != 1) {
                                        abstractC3472z00R = c1969f3A.R();
                                        if (abstractC3472z00R.w()) {
                                            c0767v2R = abstractC3472z00R.b().R();
                                            if (!this.e.b(c0767v2R)) {
                                            }
                                        } else if (!(c1969f3A.R() instanceof C1484Wr)) {
                                        }
                                    }
                                    if (c0767v2R != null) {
                                        if (v2S.d1()) {
                                            setC.addAll(v2S.b().g0());
                                        }
                                        v2S = v2S.L2();
                                        interfaceC2641noA2.d(v2S);
                                        identityHashMap.put(v2S, c0767v2R);
                                    }
                                    z = h;
                                    if (!z) {
                                        c1969f3A2 = v2S.J2().w().a();
                                        if (c1969f3A2 == null) {
                                            if (!z) {
                                                throw new AssertionError();
                                            }
                                        } else if (!z) {
                                            throw new AssertionError();
                                        }
                                    }
                                } else if (!h) {
                                    throw new AssertionError();
                                }
                                c0767v2R = null;
                                if (c0767v2R != null) {
                                    if (v2S.d1()) {
                                        setC.addAll(v2S.b().g0());
                                    }
                                    v2S = v2S.L2();
                                    interfaceC2641noA2.d(v2S);
                                    identityHashMap.put(v2S, c0767v2R);
                                }
                                z = h;
                                if (!z) {
                                    c1969f3A2 = v2S.J2().w().a();
                                    if (c1969f3A2 == null) {
                                        if (!z) {
                                            throw new AssertionError();
                                        }
                                    } else if (!z) {
                                        throw new AssertionError();
                                    }
                                }
                            }
                            if (abstractC2412ko instanceof FG) {
                                fgU0 = abstractC2412ko.u0();
                                if (h) {
                                }
                                interfaceC2641noA2.d(c2409kl.a(fgU0.J2() + 1, (com.android.tools.r8.graph.X) null));
                            }
                            setC3 = set4;
                            setC2 = set3;
                            o5VarH = o5Var;
                            i2 = 0;
                        }
                        i = 0;
                        if (abstractC2412ko instanceof QW) {
                            qwF0 = abstractC2412ko.F0();
                            field2 = qwF0.getField();
                            c0767v2 = field2.e;
                            if (this.e.b(c0767v2)) {
                                if (qwF0.f1()) {
                                    interfaceC2641noA2.s();
                                } else {
                                    setC.addAll(qwF0.b().g0());
                                    if (this.e.c(field2)) {
                                        interfaceC2641noA2.previous();
                                        S10 s10A4 = interfaceC2641noA2.a(c2409kl, this.d, this.e.a(c0767v2));
                                        interfaceC2641noA2.next();
                                        int i9 = C1721bt.m;
                                        C1646at c1646at2 = (C1646at) ((C1646at) new C1646at().a(this.g.a().a(o5VarH, veB).getReference())).a(this.a, c2409kl);
                                        c1646at2.getClass();
                                        c1721btB = ((C1646at) c1646at2.a(AbstractC1639am.c(s10A4))).b();
                                        interfaceC2641noA2.d(c1721btB);
                                        identityHashMap.put(c1721btB, c0767v2);
                                        while (r9.hasNext()) {
                                            if (abstractC2412ko2.f2()) {
                                                c2115gtI0 = abstractC2412ko2.i0();
                                                if (!c2115gtI0.S2().b(this.a.a().g4.c)) {
                                                    if (c2115gtI0.d1()) {
                                                        c2115gtI0.b().g(c1721btB.b());
                                                    }
                                                    c3332x4H = c2115gtI0.h();
                                                    if (c3332x4H != c1721btB.h()) {
                                                        set5 = set;
                                                        if (!set5.contains(c3332x4H)) {
                                                            c3332x4H.g(c2115gtI0);
                                                        }
                                                        set = set5;
                                                    } else {
                                                        set5 = set;
                                                    }
                                                    Set set8 = set2;
                                                    set8.add(c2115gtI0);
                                                    set2 = set8;
                                                    set = set5;
                                                }
                                            }
                                        }
                                    } else {
                                        set4 = set2;
                                        set3 = set;
                                        if (this.e.b(field2)) {
                                            C0978Fc c0978FcA2 = c2409kl.a(this.e.a(field2), (com.android.tools.r8.graph.X) null);
                                            interfaceC2641noA2.d(c0978FcA2);
                                            identityHashMap.put(c0978FcA2, c0767v2);
                                        }
                                    }
                                    if (abstractC2412ko instanceof C1189Mn) {
                                        c1189MnT = abstractC2412ko.T();
                                        if (this.e.b(c1189MnT.getField().e)) {
                                            field = c1189MnT.getField();
                                            if (this.e.a(field.e, field).e()) {
                                                o5VarA = this.g.a().e(this.a, o5VarH, veB);
                                            } else {
                                                o5VarA = this.g.a(field.B0()).a(this.a, field, o5VarH, veB);
                                            }
                                            o5Var = o5VarH;
                                            c1721bt = new C1721bt(o5VarA.getReference(), c2409kl.a(AbstractC3472z00.a(o5VarA.G(), WH.i(), (C0702m<?>) this.a)), AbstractC1639am.c(c1189MnT.e()));
                                            interfaceC2641noA2.d(c1721bt);
                                            if (this.e.b(c1189MnT.getField().h)) {
                                                identityHashMap.put(c1721bt, c1189MnT.getField().h);
                                            }
                                        } else {
                                            o5Var = o5VarH;
                                        }
                                    } else {
                                        o5Var = o5VarH;
                                    }
                                    if (abstractC2412ko.o1()) {
                                        v2S = abstractC2412ko.s();
                                        c1969f3A = v2S.J2().w().a();
                                        if (c1969f3A == null) {
                                            if (c1969f3A.U() != 1) {
                                                abstractC3472z00R = c1969f3A.R();
                                                if (abstractC3472z00R.w()) {
                                                    c0767v2R = abstractC3472z00R.b().R();
                                                    if (!this.e.b(c0767v2R)) {
                                                    }
                                                } else if (!(c1969f3A.R() instanceof C1484Wr)) {
                                                }
                                            }
                                            if (c0767v2R != null) {
                                                if (v2S.d1()) {
                                                    setC.addAll(v2S.b().g0());
                                                }
                                                v2S = v2S.L2();
                                                interfaceC2641noA2.d(v2S);
                                                identityHashMap.put(v2S, c0767v2R);
                                            }
                                            z = h;
                                            if (!z) {
                                                c1969f3A2 = v2S.J2().w().a();
                                                if (c1969f3A2 == null) {
                                                    if (!z) {
                                                        throw new AssertionError();
                                                    }
                                                } else if (!z) {
                                                    throw new AssertionError();
                                                }
                                            }
                                        } else if (!h) {
                                            throw new AssertionError();
                                        }
                                        c0767v2R = null;
                                        if (c0767v2R != null) {
                                            if (v2S.d1()) {
                                                setC.addAll(v2S.b().g0());
                                            }
                                            v2S = v2S.L2();
                                            interfaceC2641noA2.d(v2S);
                                            identityHashMap.put(v2S, c0767v2R);
                                        }
                                        z = h;
                                        if (!z) {
                                            c1969f3A2 = v2S.J2().w().a();
                                            if (c1969f3A2 == null) {
                                                if (!z) {
                                                    throw new AssertionError();
                                                }
                                            } else if (!z) {
                                                throw new AssertionError();
                                            }
                                        }
                                    }
                                    if (abstractC2412ko instanceof FG) {
                                        fgU0 = abstractC2412ko.u0();
                                        if (h) {
                                        }
                                        interfaceC2641noA2.d(c2409kl.a(fgU0.J2() + 1, (com.android.tools.r8.graph.X) null));
                                    }
                                    setC3 = set4;
                                    setC2 = set3;
                                    o5VarH = o5Var;
                                    i2 = 0;
                                }
                            }
                            i2 = i;
                            s10A2 = s10A2;
                            setC3 = set2;
                            setC2 = set;
                        }
                        set4 = set2;
                        set3 = set;
                        if (abstractC2412ko instanceof C1189Mn) {
                            c1189MnT = abstractC2412ko.T();
                            if (this.e.b(c1189MnT.getField().e)) {
                                field = c1189MnT.getField();
                                if (this.e.a(field.e, field).e()) {
                                    o5VarA = this.g.a().e(this.a, o5VarH, veB);
                                } else {
                                    o5VarA = this.g.a(field.B0()).a(this.a, field, o5VarH, veB);
                                }
                                o5Var = o5VarH;
                                c1721bt = new C1721bt(o5VarA.getReference(), c2409kl.a(AbstractC3472z00.a(o5VarA.G(), WH.i(), (C0702m<?>) this.a)), AbstractC1639am.c(c1189MnT.e()));
                                interfaceC2641noA2.d(c1721bt);
                                if (this.e.b(c1189MnT.getField().h)) {
                                    identityHashMap.put(c1721bt, c1189MnT.getField().h);
                                }
                            } else {
                                o5Var = o5VarH;
                            }
                        } else {
                            o5Var = o5VarH;
                        }
                        if (abstractC2412ko.o1()) {
                            v2S = abstractC2412ko.s();
                            c1969f3A = v2S.J2().w().a();
                            if (c1969f3A == null) {
                                if (c1969f3A.U() != 1) {
                                    abstractC3472z00R = c1969f3A.R();
                                    if (abstractC3472z00R.w()) {
                                        c0767v2R = abstractC3472z00R.b().R();
                                        if (!this.e.b(c0767v2R)) {
                                        }
                                    } else if (!(c1969f3A.R() instanceof C1484Wr)) {
                                    }
                                }
                                if (c0767v2R != null) {
                                    if (v2S.d1()) {
                                        setC.addAll(v2S.b().g0());
                                    }
                                    v2S = v2S.L2();
                                    interfaceC2641noA2.d(v2S);
                                    identityHashMap.put(v2S, c0767v2R);
                                }
                                z = h;
                                if (!z) {
                                    c1969f3A2 = v2S.J2().w().a();
                                    if (c1969f3A2 == null) {
                                        if (!z) {
                                            throw new AssertionError();
                                        }
                                    } else if (!z) {
                                        throw new AssertionError();
                                    }
                                }
                            } else if (!h) {
                                throw new AssertionError();
                            }
                            c0767v2R = null;
                            if (c0767v2R != null) {
                                if (v2S.d1()) {
                                    setC.addAll(v2S.b().g0());
                                }
                                v2S = v2S.L2();
                                interfaceC2641noA2.d(v2S);
                                identityHashMap.put(v2S, c0767v2R);
                            }
                            z = h;
                            if (!z) {
                                c1969f3A2 = v2S.J2().w().a();
                                if (c1969f3A2 == null) {
                                    if (!z) {
                                        throw new AssertionError();
                                    }
                                } else if (!z) {
                                    throw new AssertionError();
                                }
                            }
                        }
                        if (abstractC2412ko instanceof FG) {
                            fgU0 = abstractC2412ko.u0();
                            if (h) {
                            }
                            interfaceC2641noA2.d(c2409kl.a(fgU0.J2() + 1, (com.android.tools.r8.graph.X) null));
                        }
                        setC3 = set4;
                        setC2 = set3;
                        o5VarH = o5Var;
                        i2 = 0;
                    }
                }
            }
        }
        if (!h) {
            c2409kl.c(this.a);
        }
        return setC;
    }

    private static void a(InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, com.android.tools.r8.graph.o5 o5Var) {
        C1721bt c1721bt = new C1721bt(o5Var.getReference(), abstractC1305Qs.f1() ? null : abstractC1305Qs.b(), abstractC1305Qs.J2());
        if (!h && c1721bt.d1() && c1721bt.S2().I0().Y0()) {
            throw new AssertionError();
        }
        interfaceC2641no.d(c1721bt);
    }

    private C0767v2 a(S10 s10, IdentityHashMap identityHashMap) {
        AbstractC3472z00 abstractC3472z00W = s10.w();
        abstractC3472z00W.getClass();
        if (abstractC3472z00W instanceof C1484Wr) {
            if (s10.i()) {
                return null;
            }
            return (C0767v2) identityHashMap.get(s10.q());
        }
        if (!abstractC3472z00W.w()) {
            return null;
        }
        C0767v2 c0767v2R = abstractC3472z00W.b().R();
        if (this.e.b(c0767v2R)) {
            return c0767v2R;
        }
        return null;
    }
}
