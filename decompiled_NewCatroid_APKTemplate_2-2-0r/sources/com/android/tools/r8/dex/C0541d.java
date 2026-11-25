package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.naming.J0;

/* JADX INFO: renamed from: com.android.tools.r8.dex.d, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0541d extends AbstractC0546i {
    final /* synthetic */ U c;
    final /* synthetic */ com.android.tools.r8.utils.N d;
    final /* synthetic */ C0722p1 e;

    C0541d(U u, com.android.tools.r8.utils.N n, C0722p1 c0722p1) {
        this.c = u;
        this.d = n;
        this.e = c0722p1;
    }

    @Override // com.android.tools.r8.dex.AbstractC0546i
    public final C0759u2 a() {
        this.c.a(((J0) this.d.a()).b());
        return this.e.c(this.c.toString());
    }
}
