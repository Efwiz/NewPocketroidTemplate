package com.android.tools.r8.shaking;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class W0 extends AbstractC3710e1 {
    private boolean i;
    private boolean j;

    private W0() {
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    final boolean a(AbstractC3722g1 abstractC3722g1) {
        Y0 y0 = (Y0) abstractC3722g1;
        return super.a(y0) && this.i == y0.h && this.j == y0.i;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final boolean b(AbstractC3722g1 abstractC3722g1) {
        Y0 y0 = (Y0) abstractC3722g1;
        return super.a(y0) && this.i == y0.h && this.j == y0.i;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 c() {
        return Y0.k;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 d() {
        return Y0.j;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3710e1 n() {
        return this;
    }

    public final boolean o() {
        return this.j;
    }

    public final boolean p() {
        return this.i;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
    public final W0 m() {
        W0 w0 = (W0) super.m();
        w0.j = false;
        w0.i = false;
        return w0;
    }

    private W0(Y0 y0) {
        super(y0);
        this.i = y0.l();
        this.j = y0.k();
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 b() {
        return new Y0(this);
    }
}
