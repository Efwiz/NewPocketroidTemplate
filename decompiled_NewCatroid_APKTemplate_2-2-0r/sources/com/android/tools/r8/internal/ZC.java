package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.Set;
import java.util.function.IntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZC extends AbstractC1596aD {
    static final /* synthetic */ boolean f = true;

    private ZC(AbstractC1476Wj abstractC1476Wj, IntFunction intFunction, IntFunction intFunction2) {
        super(abstractC1476Wj, intFunction, intFunction2);
    }

    public static ZC a(AbstractC1476Wj abstractC1476Wj) {
        return new ZC(abstractC1476Wj, new ZC$$ExternalSyntheticLambda0(), new ZC$$ExternalSyntheticLambda1());
    }

    public final void a(AbstractC0727q0 abstractC0727q0, AbstractC1476Wj abstractC1476Wj) {
        if (!f && !AbstractC1596aD.e && this.c != abstractC1476Wj) {
            throw new AssertionError();
        }
        ((Set) this.d).add(abstractC0727q0.getType());
    }

    public final Set a(C0702m c0702m) {
        Set set = (Set) this.a.apply(((Set) this.d).size());
        Iterator it = ((Set) this.d).iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2A = c0702m.E().a(this.c, (C0767v2) it.next());
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2A);
            if (abstractC0727q0C != null) {
                set.add(abstractC0727q0C);
            } else if (!f) {
                throw new AssertionError("Unable to find definition for: " + c0767v2A.L0());
            }
        }
        return set;
    }
}
