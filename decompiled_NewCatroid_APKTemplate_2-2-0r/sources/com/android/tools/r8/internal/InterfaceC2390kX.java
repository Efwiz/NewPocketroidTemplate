package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2390kX {
    void a(C0702m c0702m, C2409kl c2409kl, C2561ml c2561ml, AbstractC2412ko abstractC2412ko, QX qx);

    default boolean a() {
        return false;
    }

    default boolean b() {
        return false;
    }

    default C2087gX c() {
        return null;
    }

    static S10 a(C0702m c0702m, C2409kl c2409kl, C2561ml c2561ml, AbstractC2412ko abstractC2412ko, String str) {
        if (!abstractC2412ko.h().D()) {
            return c2561ml.a(c0702m, c2409kl, c0702m.a().c(str));
        }
        InterfaceC2641no interfaceC2641noA = c2409kl.j().a(c2409kl);
        while (interfaceC2641noA.hasNext()) {
            if (!interfaceC2641noA.next().m1()) {
                interfaceC2641noA.previous();
                break;
            }
        }
        return interfaceC2641noA.a((C0702m<?>) c0702m, c2409kl, c0702m.a().c(str));
    }

    static void a(C2561ml c2561ml, AbstractC2412ko abstractC2412ko, final QX qx) {
        if (!AbstractC1772cX.a && !qx.a(new Predicate() { // from class: com.android.tools.r8.internal.kX$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InterfaceC2390kX.a(qx, (S10) obj);
            }
        }, abstractC2412ko)) {
            throw new AssertionError();
        }
        if (qx.b(abstractC2412ko) && abstractC2412ko.b() != null) {
            abstractC2412ko.b().g(abstractC2412ko.T0());
        }
        c2561ml.s();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ boolean a(RX rx, S10 s10) {
        if (s10.w().w()) {
            if (((QX) rx).a(s10.w().b().R())) {
                return true;
            }
        }
        return false;
    }
}
