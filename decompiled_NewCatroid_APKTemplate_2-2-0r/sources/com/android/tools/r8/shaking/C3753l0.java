package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.o5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.l0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3753l0 extends AbstractC3709e0 {
    private final o5 a;
    private final InterfaceC0694k5 b;

    public C3753l0(o5 o5Var, InterfaceC0694k5 interfaceC0694k5) {
        this.a = o5Var;
        this.b = interfaceC0694k5;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.a(this.a, this.b);
    }
}
