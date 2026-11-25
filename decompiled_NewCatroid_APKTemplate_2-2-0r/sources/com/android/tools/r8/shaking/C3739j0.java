package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.j0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3739j0 extends AbstractC3709e0 {
    private final C0729q2 a;
    private final L0 b;

    public C3739j0(C0729q2 c0729q2, L0 l0) {
        this.a = c0729q2;
        this.b = l0;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.b(this.a, this.b);
    }
}
