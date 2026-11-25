package com.android.tools.r8.utils;

import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class Y0 extends C3953q1 {
    Y0(final com.android.tools.r8.shaking.J0 j0) {
        super(j0.b(), new Predicate() { // from class: com.android.tools.r8.utils.Y0$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return j0.a((String) obj);
            }
        });
    }
}
