package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1990fH extends AbstractC2309jV {
    private final EnumC0928Dk b;
    private final com.android.tools.r8.utils.A1.h c;

    public C1990fH(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = enumC0928Dk;
        this.c = c0702m.Y().O0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2309jV
    public final boolean a(C0729q2 c0729q2) {
        return !c0729q2.isInterface();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoInterfaces";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return this.c.b(this.b);
    }
}
