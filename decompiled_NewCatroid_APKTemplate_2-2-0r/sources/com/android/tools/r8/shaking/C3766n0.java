package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.o5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.n0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3766n0 extends AbstractC3709e0 {
    private final C0684j2 a;
    private final o5 b;

    public C3766n0(C0684j2 c0684j2, o5 o5Var) {
        this.a = c0684j2;
        this.b = o5Var;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.c(this.a, this.b);
    }
}
