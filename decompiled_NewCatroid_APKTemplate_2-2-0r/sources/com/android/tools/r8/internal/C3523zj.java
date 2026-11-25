package com.android.tools.r8.internal;

import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3829y;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3523zj extends C3829y {
    final /* synthetic */ C0831Aj b;

    C3523zj(C0831Aj c0831Aj) {
        this.b = c0831Aj;
    }

    @Override // com.android.tools.r8.shaking.C3829y, com.android.tools.r8.shaking.l4
    public final boolean a(C3733i c3733i, com.android.tools.r8.graph.T0 t0) {
        return !this.b.b(t0.getReference()) && c3733i.e(t0);
    }
}
