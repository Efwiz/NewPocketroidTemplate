package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.IntFunction;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class IQ extends KQ {
    public IQ(com.android.tools.r8.graph.Y0[] y0Arr) {
        super(y0Arr);
    }

    @Override // com.android.tools.r8.internal.KQ
    public final C0759u2 a(C0767v2 c0767v2, final C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final AbstractC3649o0 abstractC3649o0) {
        return a(c0767v2, c0702m, abstractC1476Wj, new IntFunction() { // from class: com.android.tools.r8.internal.IQ$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.a(abstractC3649o0, abstractC1476Wj, c0702m, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(AbstractC3649o0 abstractC3649o0, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Q0 q0, int i) {
        com.android.tools.r8.graph.Y0 y0B = abstractC1476Wj.b((AbstractC1476Wj) null, this.a[i]);
        C0722p1 c0722p1A = q0.a();
        abstractC3649o0.getClass();
        return c0722p1A.a(abstractC3649o0.a(c0722p1A, y0B.e), abstractC3649o0.a(c0722p1A, y0B.h), abstractC3649o0.a(y0B)).f.toString();
    }
}
