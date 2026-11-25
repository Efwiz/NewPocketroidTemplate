package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.b1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3691b1 extends AbstractC3710e1 {
    private boolean i;

    private C3691b1() {
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    final boolean a(AbstractC3722g1 abstractC3722g1) {
        C3704d1 c3704d1 = (C3704d1) abstractC3722g1;
        return super.a(c3704d1) && this.i == c3704d1.h;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final boolean b(AbstractC3722g1 abstractC3722g1) {
        C3704d1 c3704d1 = (C3704d1) abstractC3722g1;
        return super.a(c3704d1) && this.i == c3704d1.h;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 c() {
        return C3704d1.j;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 d() {
        return C3704d1.i;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3710e1 n() {
        return this;
    }

    public final boolean o() {
        return this.i;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
    public final C3691b1 m() {
        C3691b1 c3691b1 = (C3691b1) super.m();
        c3691b1.i = false;
        return c3691b1;
    }

    private C3691b1(C3704d1 c3704d1) {
        super(c3704d1);
        this.i = c3704d1.k();
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 b() {
        return new C3704d1(this);
    }
}
