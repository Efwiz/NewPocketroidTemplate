package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.o5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.m0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3761m0 extends AbstractC3709e0 {
    private final C0684j2 a;
    private final InterfaceC0694k5 b;
    private final AbstractC3837z1 c;

    C3761m0(C0684j2 c0684j2, o5 o5Var, AbstractC3837z1 abstractC3837z1) {
        this.a = c0684j2;
        this.b = o5Var;
        this.c = abstractC3837z1;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.c(this.a, this.b, this.c);
    }
}
