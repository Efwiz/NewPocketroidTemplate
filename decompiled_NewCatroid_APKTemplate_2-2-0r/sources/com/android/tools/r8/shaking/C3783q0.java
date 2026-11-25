package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.EnumC0608b0;
import com.android.tools.r8.graph.InterfaceC0694k5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.q0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3783q0 extends AbstractC3709e0 {
    private final InterfaceC0694k5 a;
    private final C0633d0 b;
    private final EnumC0608b0 c;

    C3783q0(InterfaceC0694k5 interfaceC0694k5, C0633d0 c0633d0, EnumC0608b0 enumC0608b0) {
        this.a = interfaceC0694k5;
        this.b = c0633d0;
        this.c = enumC0608b0;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.a(this.a, this.b, this.c);
    }
}
