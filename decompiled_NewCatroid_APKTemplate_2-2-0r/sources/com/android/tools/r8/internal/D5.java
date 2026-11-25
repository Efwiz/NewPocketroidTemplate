package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D5 extends AbstractC3350xL {
    private static final D5 a = new D5();

    private D5() {
    }

    static D5 h() {
        return a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    final AbstractC3350xL a(int i, AbstractC3274wL abstractC3274wL) {
        C0905Cp c0905Cp = new C0905Cp(16);
        c0905Cp.a(i, abstractC3274wL);
        return BH.a(c0905Cp);
    }

    @Override // com.android.tools.r8.internal.AbstractC3327x1
    public final boolean equals(Object obj) {
        return obj == a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    final AbstractC3350xL f() {
        return this;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC3350xL
    public final AbstractC3274wL a(int i) {
        return C5.c();
    }
}
