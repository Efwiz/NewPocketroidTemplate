package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C3405y10;
import java.nio.ShortBuffer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface M2 {
    static {
        boolean z = K2.a;
    }

    C0759u2 B();

    int D();

    M0 F();

    C0772w0 P();

    int V();

    /* JADX WARN: Multi-variable type inference failed */
    default int a(M2 m2, com.android.tools.r8.utils.structural.a aVar) {
        L2 l2O = o();
        L2 l2O2 = m2.o();
        if (l2O != l2O2) {
            return l2O.compareTo(l2O2);
        }
        int iOrdinal = l2O.ordinal();
        if (iOrdinal == 0) {
            return P().a(m2.P(), aVar);
        }
        if (iOrdinal == 1 || iOrdinal == 2) {
            return 0;
        }
        if (iOrdinal != 3) {
            throw new C3405y10();
        }
        if (K2.a || (((W) this) instanceof D5)) {
            return v().a(m2.v(), aVar);
        }
        throw new AssertionError();
    }

    int a(o5 o5Var);

    M2 a(o5 o5Var, C0645e5 c0645e5, C0722p1 c0722p1, boolean z);

    AbstractC1476Wj a(C0702m c0702m);

    void a(com.android.tools.r8.dex.V v);

    void a(com.android.tools.r8.dex.r rVar);

    void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer);

    void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var);

    void a(com.android.tools.r8.utils.structural.o oVar);

    int b(o5 o5Var);

    void c(o5 o5Var);

    L2 o();

    C0772w0.a[] s();

    W t();

    D5 v();

    C0772w0.b[] z();
}
