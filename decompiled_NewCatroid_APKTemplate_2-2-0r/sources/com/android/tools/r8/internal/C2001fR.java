package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2001fR extends AbstractC2383kR implements InterfaceC1912eK {
    private C2235iR g;
    final /* synthetic */ C2537mR h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C2001fR(C2537mR c2537mR) {
        super(c2537mR, 0);
        this.h = c2537mR;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C2235iR c2235iR = new C2235iR(this.h, a());
        this.g = c2235iR;
        return c2235iR;
    }

    @Override // com.android.tools.r8.internal.AbstractC2383kR, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ C2001fR(C2537mR c2537mR, int i) {
        this(c2537mR);
    }
}
