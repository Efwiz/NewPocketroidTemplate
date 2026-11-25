package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3588u implements InterfaceC3880d {
    private final IdentityHashMap b;

    C3588u(IdentityHashMap identityHashMap) {
        IdentityHashMap identityHashMap2 = new IdentityHashMap();
        this.b = identityHashMap2;
        identityHashMap2.putAll(identityHashMap);
    }

    public final synchronized void a(IdentityHashMap identityHashMap) {
        this.b.putAll(identityHashMap);
    }

    @Override // com.android.tools.r8.utils.InterfaceC3880d
    public final void a() {
        this.b.forEach(new u$$ExternalSyntheticLambda0());
    }
}
