package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A0 extends AbstractC3709e0 {
    private final C0767v2 a;
    private final o5 b;

    A0(C0767v2 c0767v2, o5 o5Var) {
        this.a = c0767v2;
        this.b = o5Var;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.g(this.a, this.b);
    }
}
