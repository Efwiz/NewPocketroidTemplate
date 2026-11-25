package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UY extends UF {
    private final EnumC0928Dk b;
    private final com.android.tools.r8.synthesis.D c;

    public UY(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = enumC0928Dk;
        this.c = c0702m.D();
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        com.android.tools.r8.synthesis.D d = this.c;
        d.getClass();
        if (!d.i(c0729q2.d)) {
            return TY.b;
        }
        if (this.c.a(c0729q2, this.b)) {
            return TY.a;
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SyntheticItemsPolicy";
    }
}
