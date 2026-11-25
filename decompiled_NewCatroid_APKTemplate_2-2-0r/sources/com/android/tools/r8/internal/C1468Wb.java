package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1468Wb extends AbstractC2793pc {
    C1468Wb(B1 b1) {
        super(b1);
    }

    @Override // com.android.tools.r8.internal.AbstractC1860di
    public final C1468Wb a() {
        return this;
    }

    public static AbstractC1860di a(B1 b1) {
        return b1.isUnknown() ? C2276j10.e() : new C1468Wb(b1);
    }

    public final AbstractC1860di a(C0702m c0702m, B1 b1) {
        B1 b1A = this.a.a(b1, c0702m.b(), true, false);
        this.a = b1A;
        return b1A.isUnknown() ? C2276j10.e() : this;
    }
}
