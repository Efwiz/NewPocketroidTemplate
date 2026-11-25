package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Q extends AbstractC3849g implements T {
    Q(L.b bVar, C3846e c3846e, C0729q2 c0729q2) {
        super(bVar, c3846e, c0729q2);
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final int a(AbstractC3855m abstractC3855m, com.android.tools.r8.utils.structural.t tVar) {
        return ((C0729q2) this.d).a((C0729q2) ((Q) abstractC3855m).d, tVar);
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3849g, com.android.tools.r8.synthesis.AbstractC3855m
    public final T c() {
        return this;
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3849g, com.android.tools.r8.synthesis.AbstractC3855m
    public final boolean h() {
        return true;
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    public final boolean i() {
        return ((C0729q2) this.d).J1() && ((C0729q2) this.d).e.q() && (((C0729q2) this.d).e.h() || ((C0729q2) this.d).x1());
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final U j() {
        return new S(f(), d(), ((C0729q2) this.d).getType());
    }

    public final String toString() {
        return "SyntheticProgramClass{ clazz = " + ((C0729q2) this.d).d.r0() + ", kind = " + f() + ", context = " + d() + " }";
    }

    @Override // com.android.tools.r8.synthesis.T
    public final C0729q2 a() {
        return (C0729q2) this.d;
    }

    @Override // com.android.tools.r8.synthesis.T
    public final void a(Consumer consumer, Consumer consumer2) {
        consumer2.accept(this);
    }

    @Override // com.android.tools.r8.synthesis.AbstractC3855m
    final void a(com.android.tools.r8.utils.structural.m mVar, com.android.tools.r8.utils.structural.t tVar) {
        ((C0729q2) this.d).a(mVar, tVar);
    }
}
