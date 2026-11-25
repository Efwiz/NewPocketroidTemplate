package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.p9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2758p9 extends UF {
    private final com.android.tools.r8.utils.A1 b;

    public C2758p9(C0702m c0702m) {
        this.b = c0702m.Y();
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        return c0729q2.x1() ? EnumC2677o9.a : EnumC2677o9.b;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "CheckAbstractClasses";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        this.b.getClass();
        return false;
    }
}
