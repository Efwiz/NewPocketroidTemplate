package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3291wU extends UF {
    private final com.android.tools.r8.utils.A1.h b;

    public C3291wU(C0702m c0702m) {
        this.b = c0702m.Y().O0();
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        return c0729q2.getType().b1().replaceAll("^([^$]+)\\$.*", "$1");
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SameFilePolicy";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !this.b.h();
    }
}
