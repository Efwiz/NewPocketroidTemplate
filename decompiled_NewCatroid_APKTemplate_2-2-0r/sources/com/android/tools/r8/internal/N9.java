package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.IdentityHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N9 implements InterfaceC1011Gi {
    private final IdentityHashMap a = new IdentityHashMap();

    @Override // com.android.tools.r8.internal.InterfaceC1011Gi
    public final void b(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.a.put(c0684j2, c0684j3);
    }

    public final O9 b() {
        return new O9(this.a);
    }
}
