package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3236vp extends AbstractC0839Ap implements InterfaceC1912eK {
    private C3459yp g;
    final /* synthetic */ C0905Cp h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private C3236vp(C0905Cp c0905Cp) {
        super(c0905Cp, 0);
        this.h = c0905Cp;
    }

    @Override // java.util.Iterator
    public final Object next() {
        C3459yp c3459yp = new C3459yp(this.h, a());
        this.g = c3459yp;
        return c3459yp;
    }

    @Override // com.android.tools.r8.internal.AbstractC0839Ap, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ C3236vp(C0905Cp c0905Cp, int i) {
        this(c0905Cp);
    }
}
