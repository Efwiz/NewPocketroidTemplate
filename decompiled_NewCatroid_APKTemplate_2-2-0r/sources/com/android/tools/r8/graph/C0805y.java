package com.android.tools.r8.graph;

import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.MR;
import com.android.tools.r8.internal.Z00;

/* JADX INFO: renamed from: com.android.tools.r8.graph.y, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0805y implements com.android.tools.r8.utils.structural.u {
    private final MR a;
    private final MR b;

    C0805y(C c) {
        this.a = C.a(c.a);
        this.b = C.a(c.b);
    }

    @Override // com.android.tools.r8.utils.structural.v
    public final int a(Object obj, Object obj2, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this.a.a((M7) obj), this.b.a((M7) obj2));
    }

    @Override // com.android.tools.r8.utils.structural.w
    public final void a(Object obj, com.android.tools.r8.utils.structural.o oVar) {
        throw new Z00();
    }
}
