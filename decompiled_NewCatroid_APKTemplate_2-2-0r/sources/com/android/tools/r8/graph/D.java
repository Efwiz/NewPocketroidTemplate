package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.naming.AbstractC3649o0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface D {
    C0753u K();

    default int a(D d, com.android.tools.r8.utils.structural.a aVar) {
        int iN0 = n0();
        int iN1 = d.n0();
        if (iN0 != iN1) {
            return AbstractC0403b.a(iN0, iN1);
        }
        int iB = AbstractC0403b.b(iN0);
        if (iB == 0) {
            return K().a(d.K(), aVar);
        }
        if (iB == 1 || iB == 2) {
            return 0;
        }
        throw new C3405y10();
    }

    void a(o5 o5Var, Z8 z8, C0702m c0702m, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF);

    void a(com.android.tools.r8.utils.structural.o oVar);

    int n0();
}
