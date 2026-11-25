package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.m5;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.h0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3729h0 extends AbstractC3709e0 {
    private final m5 a;
    private final L0 b;

    public C3729h0(L0 l0, m5 m5Var) {
        this.a = m5Var;
        this.b = l0;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.a(this.a, this.b);
    }
}
