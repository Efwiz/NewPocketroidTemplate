package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.InterfaceC0694k5;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C extends D {
    final /* synthetic */ S1 d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C(C0702m c0702m, S1 s1) {
        super(c0702m);
        this.d = s1;
    }

    @Override // com.android.tools.r8.shaking.D
    final boolean b(InterfaceC0694k5 interfaceC0694k5) {
        return this.d.a(interfaceC0694k5.getReference(), new C$$ExternalSyntheticLambda0());
    }
}
