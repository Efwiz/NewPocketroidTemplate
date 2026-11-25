package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1189Mn;
import com.android.tools.r8.internal.C1793co;
import com.android.tools.r8.internal.C2108gn;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.wZ$$ExternalSyntheticLambda0;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S {
    private final C0702m a;
    private final com.android.tools.r8.ir.optimize.F b;
    private final com.android.tools.r8.ir.optimize.M c;

    S(C0702m c0702m) {
        this.a = c0702m;
        com.android.tools.r8.ir.optimize.F f = new com.android.tools.r8.ir.optimize.F(c0702m);
        this.b = f;
        this.c = new com.android.tools.r8.ir.optimize.M(c0702m, f);
    }

    public final com.android.tools.r8.ir.optimize.F a() {
        return this.b;
    }

    public final com.android.tools.r8.ir.optimize.M b() {
        return this.c;
    }

    public final void a(C2409kl c2409kl, Map map, Map map2) {
        m5 m5Var;
        o5 o5VarH = c2409kl.h();
        Set setC = OU.c();
        com.android.tools.r8.internal.B4 b4S = c2409kl.s();
        while (b4S.hasNext()) {
            InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                int iG2 = next.G2();
                if (iG2 == 28) {
                    C1189Mn c1189MnT = next.T();
                    m5 m5Var2 = (m5) map2.get(c1189MnT.getField());
                    if (m5Var2 != null) {
                        if (c1189MnT.g1()) {
                            interfaceC2641noA.previous();
                            c1189MnT.b().a(m5Var2.getType().X0() ? interfaceC2641noA.a(c2409kl, this.a.Y()) : interfaceC2641noA.a(c2409kl, this.a.Y(), 0L, c1189MnT.a()), setC);
                            interfaceC2641noA.next();
                        }
                        if (c1189MnT.e().P()) {
                            interfaceC2641noA.s();
                        } else {
                            interfaceC2641noA.a(this.a, c1189MnT.e());
                        }
                    }
                } else if (iG2 == 30) {
                    C1793co c1793coV = next.V();
                    if (((m5) map2.get(c1793coV.getField())) != null) {
                        if (c1793coV.e().P()) {
                            interfaceC2641noA.s();
                        } else {
                            interfaceC2641noA.a(this.a, c1793coV.e());
                        }
                    }
                } else if (iG2 == 59) {
                    QW qwF0 = next.F0();
                    m5 m5Var3 = (m5) map2.get(qwF0.getField());
                    if (m5Var3 != null) {
                        if (qwF0.g1()) {
                            interfaceC2641noA.previous();
                            qwF0.b().a(m5Var3.getType().X0() ? interfaceC2641noA.a(c2409kl, this.a.Y()) : interfaceC2641noA.a(c2409kl, this.a.Y(), 0L, qwF0.a()), setC);
                            interfaceC2641noA.next();
                        }
                        a(c2409kl, interfaceC2641noA, o5VarH, map, m5Var3);
                    }
                } else if (iG2 == 60 && (m5Var = (m5) map2.get(next.G0().getField())) != null) {
                    a(c2409kl, interfaceC2641noA, o5VarH, map, m5Var);
                }
            }
        }
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) setC);
    }

    private void a(C2409kl c2409kl, InterfaceC2641no interfaceC2641no, o5 o5Var, Map map, m5 m5Var) {
        if (m5Var.a().a(this.a, o5Var)) {
            interfaceC2641no.d(((C2108gn) C2179hn.J2().a(c2409kl, AbstractC3472z00.k(), null)).a(m5Var.t()).b());
            ((CN) map.computeIfAbsent(m5Var.a(), com.android.tools.r8.utils.Z1.a(new wZ$$ExternalSyntheticLambda0()))).add(o5Var);
        } else {
            interfaceC2641no.s();
        }
    }
}
