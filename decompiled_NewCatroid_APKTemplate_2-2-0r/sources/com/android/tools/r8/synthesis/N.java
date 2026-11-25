package com.android.tools.r8.synthesis;

import com.android.tools.r8.internal.InterfaceC3524zk;
import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class N extends M {
    private final boolean d;

    private N(int i, String str, boolean z) {
        super(i, str, false);
        this.d = z;
    }

    @Override // com.android.tools.r8.synthesis.M, com.android.tools.r8.synthesis.L.b
    public final void a(InterfaceC3524zk interfaceC3524zk) {
        interfaceC3524zk.a(b().isEmpty() ? "global" : "fixed", StandardCharsets.UTF_8);
        interfaceC3524zk.a(this.d);
    }

    @Override // com.android.tools.r8.synthesis.M, com.android.tools.r8.synthesis.L.b
    public final boolean e() {
        return b().isEmpty();
    }

    @Override // com.android.tools.r8.synthesis.M, com.android.tools.r8.synthesis.L.b
    public final boolean g() {
        return this.d;
    }

    @Override // com.android.tools.r8.synthesis.M, com.android.tools.r8.synthesis.L.b
    public final boolean h() {
        return false;
    }
}
