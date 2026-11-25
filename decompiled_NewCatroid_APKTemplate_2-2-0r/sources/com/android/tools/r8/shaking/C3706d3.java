package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.d3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3706d3 extends C3737i3.a {
    static final /* synthetic */ boolean s = true;
    C3732h3 r = null;

    protected C3706d3() {
    }

    @Override // com.android.tools.r8.shaking.B2.a
    public final B2.a e() {
        return this;
    }

    public final C3712e3 f() {
        if (s || this.r != null) {
            return new C3712e3(this.a, d(), this.d, b(), this.f, this.g, this.h, this.i, this.j, c(), this.l, this.m, this.n, this.r, null);
        }
        throw new AssertionError("Option -if without a subsequent rule.");
    }
}
