package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1835dM extends AbstractC1915eM {
    private C1835dM(int i, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, boolean z, boolean z2) {
        super(i, c0684j2, abstractC1915eM, z, z2);
    }

    public static C1758cM x() {
        return new C1758cM();
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final AbstractC1915eM.a a() {
        C1758cM c1758cM = (C1758cM) ((C1758cM) new C1758cM().a(this.a)).a(this.b);
        c1758cM.c = this.c;
        C1758cM c1758cM2 = (C1758cM) c1758cM.c();
        c1758cM2.d = ((AbstractC1915eM) this).d;
        C1758cM c1758cM3 = (C1758cM) c1758cM2.c();
        c1758cM3.e = ((AbstractC1915eM) this).e;
        return c1758cM3.c();
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM
    public final int c() {
        return 3;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.dM$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                AbstractC1915eM.m1379$$Nest$sma(zVar);
            }
        };
    }
}
