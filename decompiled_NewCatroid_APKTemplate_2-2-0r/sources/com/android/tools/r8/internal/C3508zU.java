package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3508zU extends UF {
    private final com.android.tools.r8.shaking.N1 b;
    private final com.android.tools.r8.synthesis.D c;

    public C3508zU(C0702m c0702m) {
        this.b = c0702m.f().f();
        this.c = c0702m.D();
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        if (this.b.a(c0729q2, this.c)) {
            return this.b.b(c0729q2, this.c);
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SameMainDexGroup";
    }
}
