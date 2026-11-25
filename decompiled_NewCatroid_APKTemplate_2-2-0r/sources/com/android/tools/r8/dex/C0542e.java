package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.naming.J0;

/* JADX INFO: renamed from: com.android.tools.r8.dex.e, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0542e extends AbstractC0546i {
    final /* synthetic */ com.android.tools.r8.utils.N c;
    final /* synthetic */ C0548k d;

    C0542e(C0548k c0548k, com.android.tools.r8.utils.N n) {
        this.d = c0548k;
        this.c = n;
    }

    @Override // com.android.tools.r8.dex.AbstractC0546i
    public final C0759u2 a() {
        return this.d.b((J0) this.c.a());
    }
}
