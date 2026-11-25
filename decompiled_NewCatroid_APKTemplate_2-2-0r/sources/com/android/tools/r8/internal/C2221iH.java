package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2221iH extends AbstractC2309jV {
    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return !AbstractC2734ot.b(c0729q2.M1(), new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.iH$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((com.android.tools.r8.graph.W0) obj).E1();
            }
        });
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoNativeMethods";
    }
}
