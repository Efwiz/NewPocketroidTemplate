package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.k1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3745k1 extends AbstractC3710e1 {
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private boolean s;

    private C3745k1() {
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final boolean b(AbstractC3722g1 abstractC3722g1) {
        return a((C3762m1) abstractC3722g1);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 c() {
        return C3762m1.t;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 d() {
        return C3762m1.s;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3710e1 n() {
        return this;
    }

    public final boolean o() {
        return this.i;
    }

    public final boolean p() {
        return this.j;
    }

    public final boolean q() {
        return this.k;
    }

    public final boolean r() {
        return this.l;
    }

    public final boolean s() {
        return this.m;
    }

    public final boolean t() {
        return this.n;
    }

    public final boolean u() {
        return this.o;
    }

    public final boolean v() {
        return this.p;
    }

    public final boolean w() {
        return this.q;
    }

    public final boolean x() {
        return this.r;
    }

    public final boolean y() {
        return this.s;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    /* JADX INFO: renamed from: z, reason: merged with bridge method [inline-methods] */
    public final C3745k1 m() {
        C3745k1 c3745k1 = (C3745k1) super.m();
        c3745k1.i = false;
        c3745k1.j = false;
        c3745k1.k = false;
        c3745k1.l = false;
        c3745k1.m = false;
        c3745k1.n = false;
        c3745k1.o = false;
        c3745k1.p = false;
        c3745k1.q = false;
        c3745k1.r = false;
        c3745k1.s = false;
        return c3745k1;
    }

    private C3745k1(C3762m1 c3762m1) {
        super(c3762m1);
        this.i = c3762m1.k();
        this.j = c3762m1.l();
        this.k = c3762m1.m();
        this.l = c3762m1.n();
        this.m = c3762m1.o();
        this.n = c3762m1.p();
        this.o = c3762m1.q();
        this.p = c3762m1.r();
        this.q = c3762m1.s();
        this.r = c3762m1.t();
        this.s = c3762m1.u();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final boolean a(C3762m1 c3762m1) {
        return super.a((AbstractC3722g1) c3762m1) && this.i == c3762m1.h && this.j == c3762m1.i && this.k == c3762m1.j && this.l == c3762m1.k && this.m == c3762m1.l && this.n == c3762m1.m && this.o == c3762m1.n && this.p == c3762m1.o && this.q == c3762m1.p && this.r == c3762m1.q && this.s == c3762m1.r;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3710e1
    public final AbstractC3722g1 b() {
        return new C3762m1(this);
    }
}
