package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.o5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.k0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3744k0 extends AbstractC3709e0 {
    private final o5 a;
    private final AbstractC3837z1 b;

    public C3744k0(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        this.a = o5Var;
        this.b = abstractC3837z1;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.d(this.b, this.a);
    }
}
