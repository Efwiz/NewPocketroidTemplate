package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2144hH extends AbstractC2309jV {
    static final /* synthetic */ boolean b = true;

    private static void b(C0729q2 c0729q2) {
        if (!b && !AbstractC1699bX.a(c0729q2.L1()).allMatch(new Predicate() { // from class: com.android.tools.r8.internal.hH$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2144hH.a((com.android.tools.r8.graph.U0) obj);
            }
        })) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        boolean z = b;
        if (z) {
            return true;
        }
        if (!z && !c0729q2.V1().a()) {
            throw new AssertionError();
        }
        if (z) {
            return true;
        }
        b(c0729q2);
        return true;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoKotlinMetadata";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.U0 u0) {
        return u0.Z().a();
    }
}
