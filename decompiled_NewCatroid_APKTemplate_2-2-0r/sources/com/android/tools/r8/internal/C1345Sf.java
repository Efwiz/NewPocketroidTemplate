package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1345Sf extends AbstractC3104u3 {
    private static final C1345Sf a = new C1345Sf();
    static final /* synthetic */ boolean b = true;

    private C1345Sf() {
    }

    static C1345Sf d() {
        return a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final C3049tH a() {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final AbstractC3104u3 a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final AbstractC3104u3 a(com.android.tools.r8.graph.u5 u5Var) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final AbstractC3104u3 b(C0702m c0702m) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final boolean c() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final boolean isEmpty() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3104u3
    public final void a(C0702m c0702m) {
        if (!b && !c0702m.Y().q0().b().isEmpty()) {
            throw new AssertionError();
        }
    }
}
