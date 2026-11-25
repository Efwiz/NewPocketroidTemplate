package com.android.tools.r8.naming;

import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC2251ih;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class E extends AbstractC2251ih {
    final /* synthetic */ H a;

    E(H h) {
        this.a = h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        return this.a.c.b(((W0) obj).getReference(), ((W0) obj2).getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        return this.a.c.b(((W0) obj).getReference());
    }
}
