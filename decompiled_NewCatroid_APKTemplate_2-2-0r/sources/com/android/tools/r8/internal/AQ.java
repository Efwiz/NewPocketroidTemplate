package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.EnumC0691k2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AQ {
    private final C0702m a;

    private AQ(C0702m c0702m) {
        this.a = c0702m;
    }

    public static AQ a(C0702m c0702m) {
        if (c0702m.q() && (c0702m.Y().f instanceof ClassFileConsumer) && c0702m.Y().n1.u0) {
            return new AQ(c0702m);
        }
        return null;
    }

    public final J7 a(J7 j7, com.android.tools.r8.graph.o5 o5Var, AbstractC3649o0 abstractC3649o0) {
        if (!OQ.a(j7.T(), this.a, o5Var)) {
            return j7;
        }
        NQ nqA = OQ.a(j7, this.a, o5Var);
        KQ kqA = nqA.a();
        C0767v2 c0767v2G = nqA.g();
        C0702m c0702m = this.a;
        C0759u2 c0759u2A = kqA.a(c0767v2G, c0702m, c0702m.E(), abstractC3649o0);
        AbstractC1476Wj abstractC1476WjE = this.a.E();
        ArrayList arrayList = new ArrayList();
        for (com.android.tools.r8.graph.Y0 y0 : nqA.c()) {
            if (nqA.f().b(abstractC1476WjE.b((AbstractC1476Wj) null, y0)) != null) {
                arrayList.add(y0);
            }
        }
        com.android.tools.r8.graph.Y0[] y0Arr = new com.android.tools.r8.graph.Y0[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            y0Arr[i] = (com.android.tools.r8.graph.Y0) arrayList.get(i);
        }
        NQ nqA2 = nqA.a(c0759u2A, y0Arr);
        C0699l2 c0699l2 = new C0699l2(EnumC0691k2.f, this.a.a().C5.a, false, null);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new com.android.tools.r8.graph.B2.k(nqA2.g()));
        arrayList2.add(new com.android.tools.r8.graph.B2.j(nqA2.b()));
        for (com.android.tools.r8.graph.Y0 y1 : nqA2.c()) {
            arrayList2.add(new com.android.tools.r8.graph.G2(new C0699l2(EnumC0691k2.e, y1, false, null)));
        }
        return new J7(C0722p1.a(nqA2.d(), nqA2.e(), c0699l2, arrayList2));
    }
}
