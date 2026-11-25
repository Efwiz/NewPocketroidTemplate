package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1954eu;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.S10;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public P(C0702m c0702m) {
        this.a = c0702m;
    }

    public final AbstractC1154Lf a(o5 o5Var, C2409kl c2409kl) {
        if (!b && !o5Var.G().X0()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            AbstractC1954eu abstractC1954euM = it.next().m();
            if (abstractC1954euM.u2()) {
                S10 s10L2 = abstractC1954euM.B0().L2();
                C0702m c0702m = this.a;
                s10L2.getClass();
                arrayList.add(AbstractC1154Lf.a(c0702m, s10L2));
            }
        }
        return AbstractC1154Lf.a(arrayList, this.a);
    }
}
