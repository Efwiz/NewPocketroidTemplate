package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0754u0;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3852j extends AbstractC3849g implements InterfaceC3854l {
    C3852j(L.b bVar, C3846e c3846e, C0754u0 c0754u0) {
        super(bVar, c3846e, c0754u0);
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final int a(AbstractC3855m abstractC3855m, com.android.tools.r8.utils.structural.t tVar) {
        return ((C0754u0) this.d).a((C0754u0) ((C3852j) abstractC3855m).d, tVar);
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3849g, com.android.tools.r8.synthesis.AbstractC3855m
    public final InterfaceC3854l b() {
        return this;
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3849g, com.android.tools.r8.synthesis.AbstractC3855m
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    public final boolean i() {
        return ((C0754u0) this.d).J1() && ((C0754u0) this.d).e.h() && ((C0754u0) this.d).e.q();
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final U j() {
        return new C3853k(f(), d(), ((C0754u0) this.d).getType());
    }

    public final String toString() {
        return "SyntheticClasspathClass{ clazz = " + ((C0754u0) this.d).d.r0() + ", kind = " + f() + ", context = " + d() + " }";
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final void a(com.android.tools.r8.utils.structural.m mVar, com.android.tools.r8.utils.structural.t tVar) {
        ((C0754u0) this.d).a(mVar, tVar);
    }
}
