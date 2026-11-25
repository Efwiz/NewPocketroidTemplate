package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0772w0;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2937rf {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final boolean b;

    public C2937rf(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.Y().r1 == com.android.tools.r8.utils.A1.j.a;
    }

    public final ArrayList a(com.android.tools.r8.graph.o5 o5Var, C2067gM c2067gM) {
        com.android.tools.r8.graph.J0.a aVar;
        ArrayList arrayList = new ArrayList();
        AbstractC0681j0 abstractC0681j0B = this.a.f().b();
        C0772w0 c0772w0P = o5Var.e().a1().P();
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0702m c0702m = this.a;
        int i = 0;
        if (c0772w0P.H0() == null) {
            C0722p1 c0722p1A = c0702m.a();
            int i2 = com.android.tools.r8.graph.J0.d;
            aVar = new com.android.tools.r8.graph.J0.a(0, new C0759u2[w0E.l1().size()], new com.android.tools.r8.graph.B0[]{c0722p1A.r});
        } else {
            boolean z = c;
            if (!z && w0E.l1().size() != c0772w0P.H0().w0()) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.J0.a aVarA = com.android.tools.r8.graph.J0.a(c0772w0P, c0702m.a());
            if (!z && aVarA == null) {
                throw new AssertionError();
            }
            aVar = aVarA;
        }
        ArrayList arrayList2 = new ArrayList();
        C2865qf c2865qf = new C2865qf(abstractC0681j0B.e, this.a.E().a((AbstractC1476Wj) null, o5Var.getReference()), arrayList2);
        this.a.a();
        C2796pf c2796pf = new C2796pf(c2865qf, arrayList, c2067gM, arrayList2, aVar.e, this.a.E().a((AbstractC1476Wj) null, o5Var.getReference()));
        for (com.android.tools.r8.graph.B0 b0 : aVar.g) {
            b0.a(c2796pf);
        }
        int i3 = c2865qf.b;
        if (i3 == -1) {
            i3 = 0;
        } else if (!C2865qf.g && i3 < 0) {
            throw new AssertionError();
        }
        com.android.tools.r8.graph.J0.a aVar2 = new com.android.tools.r8.graph.J0.a(i3, aVar.f, (com.android.tools.r8.graph.B0[]) arrayList2.toArray(com.android.tools.r8.graph.B0.a));
        boolean z2 = c;
        if (!z2 && this.b && !c2796pf.k) {
            if (!z2 && aVar2.e != aVar.e) {
                throw new AssertionError();
            }
            if (!z2 && aVar2.g.length != aVar.g.length) {
                throw new AssertionError();
            }
            while (true) {
                com.android.tools.r8.graph.B0[] b0Arr = aVar.g;
                if (i >= b0Arr.length) {
                    break;
                }
                if (!c && !aVar2.g[i].equals(b0Arr[i])) {
                    throw new AssertionError();
                }
                i++;
            }
        }
        c0772w0P.a((com.android.tools.r8.graph.J0) aVar2);
        return arrayList;
    }
}
