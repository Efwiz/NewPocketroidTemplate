package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ym, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1535Ym extends C0753u {
    final /* synthetic */ C1013Gk n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1535Ym(C0767v2 c0767v2, int i, int i2, ArrayList arrayList, C1013Gk c1013Gk) {
        super(c0767v2, i, i2, arrayList);
        this.n = c1013Gk;
    }

    @Override // com.android.tools.r8.graph.W, com.android.tools.r8.graph.M2
    public final AbstractC1476Wj a(C0702m c0702m) {
        return this.n;
    }
}
