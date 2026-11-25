package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ns, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2645ns {
    public static AbstractC2645ns a(C0702m c0702m, C2409kl c2409kl, DH dh, final AbstractC1476Wj abstractC1476Wj) {
        DH dhA = dh.a(new Predicate() { // from class: com.android.tools.r8.internal.ns$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC2645ns.a(abstractC1476Wj, (DH) obj);
            }
        });
        return (dhA == null || dhA == abstractC1476Wj || !(dhA instanceof com.android.tools.r8.shaking.A4)) ? new C1708bg() : new C2809ps(c0702m, c2409kl);
    }

    public abstract void a();

    public abstract void a(AbstractC1305Qs abstractC1305Qs, AbstractC1305Qs abstractC1305Qs2, KE ke, C3332x4 c3332x4);

    public abstract void a(InterfaceC1474Wh interfaceC1474Wh, InterfaceC1474Wh interfaceC1474Wh2, C3332x4 c3332x4);

    public abstract void a(C1616aU c1616aU, C3332x4 c3332x4);

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC1476Wj abstractC1476Wj, DH dh) {
        dh.getClass();
        return (dh instanceof com.android.tools.r8.shaking.A4) || dh == abstractC1476Wj;
    }
}
