package com.android.tools.r8.graph;

import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.c5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0631c5 extends com.android.tools.r8.utils.structural.g {
    final /* synthetic */ C0645e5 e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0631c5(AbstractC3649o0 abstractC3649o0, ToIntFunction toIntFunction, ToIntFunction toIntFunction2, C0645e5 c0645e5) {
        super(abstractC3649o0, toIntFunction, toIntFunction2);
        this.e = c0645e5;
    }

    @Override // com.android.tools.r8.utils.structural.d, com.android.tools.r8.utils.structural.a
    public final int a(Y0 y0, Y0 y1) {
        return Integer.compare(this.e.j.a(y0), this.e.j.a(y1));
    }

    @Override // com.android.tools.r8.utils.structural.d, com.android.tools.r8.utils.structural.a
    public final int a(C0684j2 c0684j2, C0684j2 c0684j3) {
        return Integer.compare(this.e.i.a(c0684j2), this.e.i.a(c0684j3));
    }
}
