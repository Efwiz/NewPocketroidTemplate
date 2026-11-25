package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.m1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3762m1 extends AbstractC3740j1 {
    private static final C3762m1 s;
    private static final C3762m1 t;
    static final /* synthetic */ boolean u = true;
    private final boolean h;
    private final boolean i;
    private final boolean j;
    private final boolean k;
    private final boolean l;
    private final boolean m;
    private final boolean n;
    private final boolean o;
    private final boolean p;
    private final boolean q;
    private final boolean r;

    private C3762m1(C3745k1 c3745k1) {
        super(c3745k1);
        this.h = c3745k1.o();
        this.i = c3745k1.p();
        this.j = c3745k1.q();
        this.k = c3745k1.r();
        this.l = c3745k1.s();
        this.m = c3745k1.t();
        this.n = c3745k1.u();
        this.o = c3745k1.v();
        this.p = c3745k1.w();
        this.q = c3745k1.x();
        this.r = c3745k1.y();
    }

    public static C3762m1 i() {
        return t;
    }

    public static C3754l1 x() {
        return t.w();
    }

    public static C3762m1 y() {
        return s;
    }

    public final boolean e(com.android.tools.r8.utils.A1 a1) {
        return j(a1);
    }

    public final boolean f(com.android.tools.r8.utils.A1 a1) {
        return b((K0) a1) && this.h;
    }

    public final boolean g(com.android.tools.r8.utils.A1 a1) {
        return b((K0) a1) && this.j;
    }

    public final boolean h(com.android.tools.r8.utils.A1 a1) {
        return b((K0) a1) && this.k;
    }

    final C3745k1 j() {
        return new C3745k1(this);
    }

    final boolean k() {
        return this.h;
    }

    final boolean l() {
        return this.i;
    }

    final boolean m() {
        return this.j;
    }

    final boolean n() {
        return this.k;
    }

    final boolean o() {
        return this.l;
    }

    final boolean p() {
        return this.m;
    }

    final boolean q() {
        return this.n;
    }

    final boolean r() {
        return this.o;
    }

    final boolean s() {
        return this.p;
    }

    final boolean t() {
        return this.q;
    }

    final boolean u() {
        return this.r;
    }

    public final boolean v() {
        return equals(s);
    }

    public final C3754l1 w() {
        if (u || !equals(s)) {
            return new C3754l1(this);
        }
        throw new AssertionError();
    }

    public final boolean e(K0 k0) {
        return b(k0) && this.i;
    }

    public final boolean i(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && a1.j1() && this.l;
    }

    public final boolean j(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && !h() && this.m;
    }

    public final boolean k(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && this.n;
    }

    public final boolean l(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && this.o;
    }

    public final boolean m(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && this.p;
    }

    public final boolean n(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && this.q;
    }

    public final boolean o(com.android.tools.r8.utils.A1 a1) {
        return e((K0) a1) && b((K0) a1) && d((K0) a1) && this.r;
    }

    static {
        k1IA k1ia = null;
        s = (C3762m1) new C3745k1().m().a();
        C3745k1 c3745k1 = (C3745k1) new C3745k1().l();
        c3745k1.i = true;
        c3745k1.j = true;
        c3745k1.k = true;
        c3745k1.l = true;
        c3745k1.m = true;
        c3745k1.n = true;
        c3745k1.o = true;
        c3745k1.p = true;
        c3745k1.q = true;
        c3745k1.r = true;
        c3745k1.s = true;
        t = (C3762m1) c3745k1.a();
    }
}
