package com.android.tools.r8.shaking;

import com.android.tools.r8.dex.C0559k$$ExternalSyntheticLambda18;
import com.android.tools.r8.graph.C0759u2;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B {
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    B() {
    }

    public final String a(C0759u2 c0759u2) {
        return (String) this.a.computeIfAbsent(c0759u2, new C0559k$$ExternalSyntheticLambda18());
    }
}
