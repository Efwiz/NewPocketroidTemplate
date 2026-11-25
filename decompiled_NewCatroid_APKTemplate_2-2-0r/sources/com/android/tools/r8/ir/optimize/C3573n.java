package com.android.tools.r8.ir.optimize;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.n, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3573n extends AbstractC3559g {
    private final Set a;
    private final Map b;

    C3573n(IdentityHashMap identityHashMap, Set set) {
        this.a = set;
        this.b = identityHashMap;
    }

    @Override // com.android.tools.r8.ir.optimize.AbstractC3559g
    final C3573n a() {
        return this;
    }
}
