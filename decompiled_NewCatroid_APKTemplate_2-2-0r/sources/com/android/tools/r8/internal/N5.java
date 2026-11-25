package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N5 implements C7 {
    private final C0722p1 a;

    public N5(C0702m c0702m) {
        this.a = c0702m.a();
    }

    private static C3224ve a(final I7 i7, final C0684j2 c0684j2) {
        return new C3005se((C3075te) new C3075te().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.N5$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return N5.a(i7, c0684j2, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Collection a(I7 i7, C0684j2 c0684j2, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return AbstractC1639am.a(i7, new M6(c0684j2.I0(), false));
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0086  */
    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        C0684j2 c0684j2;
        if (!b7.H() && !b7.K() && !b7.I()) {
            return C3224ve.c();
        }
        I7 i7L = b7.l();
        C0684j2 c0684j2T = i7L.T();
        if (c0684j2T.F0() <= 1 && (c0684j2T.F0() != 1 || c0684j2T.k(0).R0())) {
            C0767v2 c0767v2I0 = c0684j2T.I0();
            C0722p1 c0722p1 = this.a;
            if (c0767v2I0 == c0722p1.d3 || !c0722p1.e3.contains(c0684j2T.e) || !this.a.e3.contains(c0684j2T.I0())) {
                c0684j2 = null;
                break;
            }
            Iterator it = this.a.h4.a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    c0684j2 = null;
                    break;
                }
                c0684j2 = (C0684j2) it.next();
                if (c0684j2.f == c0684j2T.f && c0684j2.G0().equals(c0684j2T.G0())) {
                    break;
                }
            }
        } else {
            c0684j2 = null;
            break;
        }
        if (c0684j2 == null) {
            return C3224ve.c();
        }
        C0722p1 c0722p2 = this.a;
        return a(new I7(i7L.U(), c0684j2T.a(c0722p2.b(c0722p2.d3, c0684j2T.H0().f.a), this.a), i7L.V()), c0684j2T);
    }
}
