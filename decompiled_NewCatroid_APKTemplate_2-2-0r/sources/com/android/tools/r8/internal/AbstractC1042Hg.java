package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1042Hg {
    /* JADX WARN: Code duplicated, block: B:26:0x0074  */
    public static AbstractC1008Gg a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2409kl c2409kl, TE te) {
        S10 s10B;
        if (!c0702m.Y().w) {
            return C2126h10.c();
        }
        if (!o5Var.getAccessFlags().o() || o5Var.D().isEmpty()) {
            return C2126h10.c();
        }
        AbstractC1008Gg abstractC1008GgN = o5Var.B().n();
        C0722p1 c0722p1A = c0702m.a();
        InterfaceC2488lo interfaceC2488loR = c2409kl.j().R();
        for (int i = 0; i < o5Var.D().size(); i++) {
            C3406y2 c3406y2Q = interfaceC2488loR.next().q();
            C0767v2 c0767v2B = o5Var.b(i);
            if (c0702m.K()) {
                if (c0767v2B == c0722p1A.A1) {
                    abstractC1008GgN.getClass();
                    if ((abstractC1008GgN instanceof E9) && abstractC1008GgN.a().c() == i) {
                        s10B = c3406y2Q.b();
                        if (!s10B.z() && !s10B.C()) {
                            Iterator it = s10B.a(C1552Zd.a()).iterator();
                            boolean z = false;
                            while (true) {
                                if (!it.hasNext()) {
                                    if (te.c() && !z) {
                                        break;
                                    }
                                    return new E9(i);
                                }
                                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) it.next();
                                int iG2 = abstractC2412ko.G2();
                                if (iG2 == 9) {
                                    if (abstractC2412ko.b().z() || abstractC2412ko.b().C()) {
                                        break;
                                    }
                                } else if (iG2 != 25) {
                                    if (iG2 != 56) {
                                        break;
                                    }
                                } else {
                                    C1015Gl c1015GlQ = abstractC2412ko.Q();
                                    if (!c1015GlQ.N2() || (c1015GlQ.L2() != EnumC1076Il.EQ && c1015GlQ.L2() != EnumC1076Il.NE)) {
                                        break;
                                    }
                                    z = true;
                                }
                            }
                        }
                    }
                } else {
                    continue;
                }
            } else if (c0767v2B != c0722p1A.Z1) {
                continue;
            } else {
                s10B = c3406y2Q.b();
                if (!s10B.z()) {
                    continue;
                }
            }
        }
        return C2126h10.c();
    }
}
