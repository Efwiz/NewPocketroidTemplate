package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface Q0 {
    static {
        int i = P0.a;
    }

    C0722p1 a();

    default AbstractC0727q0 a(C0767v2 c0767v2) {
        return c(c0767v2);
    }

    default AbstractC0727q0 b(C0729q2 c0729q2, C0767v2 c0767v2) {
        return c0767v2 == c0729q2.d ? c0729q2 : a(c0767v2);
    }

    AbstractC0727q0 c(C0767v2 c0767v2);

    default boolean e(C0767v2 c0767v2) {
        return c(c0767v2) != null;
    }

    O f(C0767v2 c0767v2);

    default AbstractC0727q0 a(C0767v2 c0767v2, o5 o5Var) {
        return b(o5Var.a(), c0767v2);
    }

    default AbstractC0748t0 b(C0684j2 c0684j2) {
        W0 w0B;
        AbstractC0727q0 abstractC0727q0C = c(c0684j2.B0());
        if (abstractC0727q0C == null || (w0B = abstractC0727q0C.k.b(c0684j2)) == null) {
            return null;
        }
        return AbstractC0748t0.a(abstractC0727q0C, w0B);
    }

    default C0729q2 a(C0729q2 c0729q2, C0767v2 c0767v2) {
        return C0729q2.a(b(c0729q2, c0767v2));
    }

    default boolean a(C0684j2 c0684j2) {
        return b(c0684j2) != null;
    }

    default AbstractC0727q0 a(AbstractC0642e2 abstractC0642e2) {
        return c(abstractC0642e2.e);
    }

    default C0729q2 b(C0767v2 c0767v2) {
        return C0729q2.a(c(c0767v2));
    }
}
