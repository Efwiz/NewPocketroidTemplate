package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.o5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.i0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3734i0 extends AbstractC3709e0 {
    private final C0729q2 a;
    private final o5 b;
    private final V0 c;
    private final AbstractC3837z1 d;

    public C3734i0(C0729q2 c0729q2, o5 o5Var, V0 v0, AbstractC3837z1 abstractC3837z1) {
        this.a = c0729q2;
        this.b = o5Var;
        this.c = v0;
        this.d = abstractC3837z1;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.a(this.a, this.b, this.c, this.d);
    }
}
