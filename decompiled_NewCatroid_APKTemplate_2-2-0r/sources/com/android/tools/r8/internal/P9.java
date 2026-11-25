package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P9 implements InterfaceC2697oM {
    private final C0702m a;

    public P9(C0702m c0702m) {
        this.a = c0702m;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2697oM
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2697oM
    public final ArrayList a(com.android.tools.r8.graph.o5 o5Var, C2067gM c2067gM, boolean z, boolean z2, int i) {
        ArrayList arrayList;
        ArrayList arrayList2;
        AbstractC1915eM abstractC1915eMU = null;
        if (this.a.Y().I0().h) {
            arrayList = new ArrayList();
            C0753u c0753uK = o5Var.e().a1().K();
            List<B7> listI0 = c0753uK.I0();
            ArrayList arrayList3 = new ArrayList(listI0.size() * 3);
            for (B7 b7 : listI0) {
                b7.getClass();
                if (b7 instanceof C2051g8) {
                    abstractC1915eMU = b7.s().U();
                } else {
                    if (abstractC1915eMU != null) {
                        C3979v2 c3979v2A = c2067gM.a(abstractC1915eMU);
                        AbstractC1915eM abstractC1915eM = (AbstractC1915eM) c3979v2A.a();
                        AbstractC1915eM abstractC1915eM2 = (AbstractC1915eM) c3979v2A.b();
                        arrayList.add(new C3114uD(abstractC1915eM2.e(), abstractC1915eM));
                        C2051g8 c2051g8 = new C2051g8(new M7(), abstractC1915eM2);
                        arrayList3.add(c2051g8);
                        arrayList3.add(c2051g8.T());
                    }
                    arrayList3.add(b7);
                }
            }
            o5Var.a(new C0753u(o5Var.t(), c0753uK.L0(), c0753uK.K0(), arrayList3, c0753uK.Q0(), c0753uK.J0(), Position.UNKNOWN, C2049g6.a()), this.a);
        } else {
            arrayList = new ArrayList();
            C0753u c0753uK2 = o5Var.e().a1().K();
            List<B7> listI1 = c0753uK2.I0();
            ArrayList arrayList4 = new ArrayList(listI1.size());
            boolean z3 = false;
            for (B7 c2051g9 : listI1) {
                c2051g9.getClass();
                if (c2051g9 instanceof C2051g8) {
                    C2051g8 c2051g8S = c2051g9.s();
                    M7 m7T = c2051g8S.T();
                    C3979v2 c3979v2A2 = c2067gM.a(c2051g8S.U());
                    AbstractC1915eM abstractC1915eM3 = (AbstractC1915eM) c3979v2A2.a();
                    AbstractC1915eM abstractC1915eM4 = (AbstractC1915eM) c3979v2A2.b();
                    arrayList.add(new C3114uD(abstractC1915eM4.e(), abstractC1915eM3));
                    c2051g9 = new C2051g8(m7T, abstractC1915eM4);
                    z3 = true;
                }
                arrayList4.add(c2051g9);
            }
            if (z3 || !z) {
                arrayList2 = arrayList4;
            } else {
                C0684j2 c0684j2A = this.a.E().a((AbstractC1476Wj) null, o5Var.getReference());
                M7 m7 = new M7();
                C3979v2 c3979v2A3 = c2067gM.a(AbstractC1915eM.c.x().a(c0684j2A).a(0).a());
                AbstractC1915eM abstractC1915eM5 = (AbstractC1915eM) c3979v2A3.a();
                AbstractC1915eM abstractC1915eM6 = (AbstractC1915eM) c3979v2A3.b();
                arrayList.add(new C3114uD(abstractC1915eM6.e(), abstractC1915eM5));
                C2051g8 c2051g10 = new C2051g8(m7, abstractC1915eM6);
                ArrayList arrayList5 = new ArrayList(listI1.size() + 2);
                arrayList5.add(c2051g10);
                arrayList5.add(c2051g10.T());
                arrayList5.addAll(arrayList4);
                arrayList2 = arrayList5;
            }
            o5Var.a(new C0753u(o5Var.t(), c0753uK2.L0(), c0753uK2.K0(), arrayList2, c0753uK2.Q0(), c0753uK2.J0(), Position.UNKNOWN, C2049g6.a()), this.a);
        }
        return arrayList;
    }
}
