package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class S0 extends U0 {
    static final /* synthetic */ boolean c = true;
    final C0729q2 b;

    S0(C0729q2 c0729q2) {
        if (!c && c0729q2.isInterface() && !c0729q2.y1()) {
            throw new AssertionError();
        }
        this.b = c0729q2;
    }

    @Override // com.android.tools.r8.shaking.U0
    public final C0729q2 a() {
        return this.b;
    }
}
