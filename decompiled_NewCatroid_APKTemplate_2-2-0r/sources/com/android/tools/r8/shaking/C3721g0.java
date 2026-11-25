package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.m5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.g0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3721g0 extends AbstractC3709e0 {
    private final m5 a;
    private final InterfaceC0694k5 b;
    private final AbstractC3837z1 c;

    public C3721g0(m5 m5Var, InterfaceC0694k5 interfaceC0694k5, AbstractC3837z1 abstractC3837z1) {
        this.a = m5Var;
        this.b = interfaceC0694k5;
        this.c = abstractC3837z1;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.b(this.a, this.b, this.c);
    }
}
