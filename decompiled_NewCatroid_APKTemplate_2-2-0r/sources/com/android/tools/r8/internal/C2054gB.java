package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2054gB implements InterfaceC3176v00 {
    final /* synthetic */ AbstractC1305Qs a;
    final /* synthetic */ DH b;
    final /* synthetic */ C0684j2 c;
    final /* synthetic */ AbstractC1476Wj d;
    final /* synthetic */ C2289jB e;

    C2054gB(C2289jB c2289jB, AbstractC1305Qs abstractC1305Qs, DH dh, C0684j2 c0684j2, AbstractC1476Wj abstractC1476Wj) {
        this.e = c2289jB;
        this.a = abstractC1305Qs;
        this.b = dh;
        this.c = c0684j2;
        this.d = abstractC1476Wj;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3176v00
    public final AbstractC3472z00 a() {
        return this.b.a(this.d, this.c.I0()).c(this.e.a);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3176v00
    public final com.android.tools.r8.graph.X j() {
        return this.a.j();
    }
}
