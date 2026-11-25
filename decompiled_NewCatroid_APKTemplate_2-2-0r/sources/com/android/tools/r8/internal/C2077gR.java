package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2077gR extends AbstractC2383kR implements InterfaceC1912eK {
    final /* synthetic */ C2537mR g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2077gR(C2537mR c2537mR) {
        super(c2537mR, 0);
        this.g = c2537mR;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.g.a[a()];
    }
}
