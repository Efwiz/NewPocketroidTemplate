package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3312wp extends AbstractC0839Ap implements InterfaceC0841Ar {
    final /* synthetic */ C0905Cp g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3312wp(C0905Cp c0905Cp) {
        super(c0905Cp, 0);
        this.g = c0905Cp;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Integer.valueOf(this.g.b[a()]);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return this.g.b[a()];
    }
}
