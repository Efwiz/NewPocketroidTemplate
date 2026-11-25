package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0772w0;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3076tf {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final InterfaceC2606nM b;

    public C3076tf(C0702m c0702m, InterfaceC2606nM interfaceC2606nM) {
        this.a = c0702m;
        this.b = interfaceC2606nM;
    }

    public final ArrayList a(com.android.tools.r8.graph.o5 o5Var, C2067gM c2067gM, int i) {
        com.android.tools.r8.graph.J0.a aVar;
        ArrayList arrayList = new ArrayList();
        C0772w0 c0772w0P = o5Var.e().a1().P();
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        C0702m c0702m = this.a;
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
        C3932m1 c3932m1 = new C3932m1(-1);
        C3979v2 c3979v2 = new C3979v2();
        C3006sf c3006sf = new C3006sf(this, aVar.e, this.a.E().a((AbstractC1476Wj) null, o5Var.getReference()), c3932m1, c3979v2, c2067gM, arrayList);
        for (com.android.tools.r8.graph.B0 b0 : aVar.g) {
            b0.a(c3006sf);
        }
        int iU = C1525Yd.a(c0772w0P.i).u();
        if (c3979v2.b() != null) {
            InterfaceC2606nM interfaceC2606nM = this.b;
            int i3 = iU + 1;
            AbstractC1915eM abstractC1915eM = (AbstractC1915eM) c2067gM.a((AbstractC1915eM) c3979v2.b()).a();
            for (int iIntValue = ((Integer) c3979v2.a()).intValue(); iIntValue < i3; iIntValue++) {
                arrayList.add(new C3114uD(interfaceC2606nM.a(iIntValue), abstractC1915eM));
            }
        }
        if (!c && arrayList.isEmpty() && c0772w0P.i.length != 1) {
            throw new AssertionError();
        }
        this.b.a(o5Var, i);
        return arrayList;
    }
}
