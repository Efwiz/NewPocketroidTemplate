package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC3477z20;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.S10;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.f0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
interface InterfaceC3558f0 {
    C1229Oa a(AbstractC1305Qs abstractC1305Qs, C1229Oa c1229Oa);

    void a(Y y);

    /* JADX WARN: Code duplicated, block: B:10:0x0048  */
    default boolean a(C0702m c0702m, W w, AbstractC1305Qs abstractC1305Qs, o5 o5Var, o5 o5Var2) {
        C0729q2 c0729q2A;
        if (abstractC1305Qs.Z1()) {
            S10 s10U2 = abstractC1305Qs.c0().U2();
            if (s10U2.w().w() && a(abstractC1305Qs, s10U2.w().b()).b(o5Var.t().c(b()).b(), b())) {
                c0729q2A = null;
            } else {
                c0729q2A = o5Var.a();
            }
        } else {
            c0729q2A = null;
        }
        if (c0729q2A == null) {
            return true;
        }
        if (AbstractC0639e.a(c0729q2A, o5Var2, c0702m).b()) {
            return false;
        }
        w.a(c0729q2A);
        return true;
    }

    boolean a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, Y y, C3332x4 c3332x4, AbstractC3477z20 abstractC3477z20);

    boolean a(W w, AbstractC3477z20 abstractC3477z20);

    C0702m b();
}
