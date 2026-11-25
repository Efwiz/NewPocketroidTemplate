package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2320jd extends AbstractC1428Un {
    private final GH a;

    C2320jd(GH gh) {
        this.a = gh;
    }

    @Override // com.android.tools.r8.internal.AbstractC1428Un
    public final AbstractC1428Un a(C0702m c0702m, com.android.tools.r8.graph.proto.c cVar) {
        GH ghA = this.a.a(c0702m, cVar);
        return ghA != this.a ? new C2320jd(ghA) : this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1428Un
    public final AbstractC1380Tn b() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1428Un
    public final AbstractC1380Tn c() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1428Un
    public final AbstractC1428Un a(C0702m c0702m, DH dh, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.u5 u5Var) {
        GH ghA = this.a.a(c0702m, dh, abstractC1476Wj, u5Var);
        return ghA != this.a ? new C2320jd(ghA) : this;
    }
}
