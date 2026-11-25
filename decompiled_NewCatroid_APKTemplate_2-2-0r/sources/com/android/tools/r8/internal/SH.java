package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class SH {
    public static SH a(C0702m c0702m, C2409kl c2409kl, DH dh, final AbstractC1476Wj abstractC1476Wj) {
        DH dhA = dh.a(new Predicate() { // from class: com.android.tools.r8.internal.SH$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SH.a(abstractC1476Wj, (DH) obj);
            }
        });
        return (dhA == null || dhA == abstractC1476Wj || !(dhA instanceof H2)) ? new QH() : new RH(c0702m.i0(), c2409kl, dh);
    }

    public abstract void a();

    public abstract void a(AbstractC1305Qs abstractC1305Qs, AbstractC1305Qs abstractC1305Qs2, KE ke);

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC1476Wj abstractC1476Wj, DH dh) {
        dh.getClass();
        return (dh instanceof H2) || dh == abstractC1476Wj;
    }
}
