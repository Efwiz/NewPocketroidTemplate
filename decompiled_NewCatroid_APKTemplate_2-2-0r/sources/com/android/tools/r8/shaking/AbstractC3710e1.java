package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.e1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3710e1 {
    protected AbstractC3722g1 a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;

    AbstractC3710e1() {
    }

    public final AbstractC3722g1 a() {
        AbstractC3722g1 abstractC3722g1 = this.a;
        if (abstractC3722g1 != null) {
            if (a(abstractC3722g1)) {
                return this.a;
            }
            if (a(d())) {
                return d();
            }
            if (a(c())) {
                return c();
            }
        }
        return b();
    }

    abstract AbstractC3722g1 b();

    abstract boolean b(AbstractC3722g1 abstractC3722g1);

    abstract AbstractC3722g1 c();

    abstract AbstractC3722g1 d();

    public final boolean e() {
        return this.b;
    }

    public final boolean f() {
        return this.c;
    }

    public final boolean g() {
        return this.h;
    }

    public final boolean h() {
        return this.d;
    }

    public final boolean i() {
        return this.e;
    }

    public final boolean j() {
        return this.f;
    }

    public final boolean k() {
        return this.g;
    }

    final AbstractC3710e1 l() {
        this.b = true;
        n();
        this.c = true;
        n();
        this.d = true;
        n();
        this.e = true;
        n();
        this.f = true;
        n();
        this.g = true;
        n();
        this.h = false;
        n();
        return n();
    }

    AbstractC3710e1 m() {
        this.b = false;
        n();
        this.c = false;
        n();
        this.d = false;
        n();
        this.e = false;
        n();
        this.f = false;
        n();
        this.g = false;
        n();
        this.h = false;
        n();
        return n();
    }

    abstract AbstractC3710e1 n();

    AbstractC3710e1(AbstractC3722g1 abstractC3722g1) {
        this.a = abstractC3722g1;
        this.b = abstractC3722g1.a();
        this.c = abstractC3722g1.b();
        this.d = abstractC3722g1.d();
        this.e = abstractC3722g1.e();
        this.f = abstractC3722g1.f();
        this.g = abstractC3722g1.g();
        this.h = abstractC3722g1.c();
    }

    boolean a(AbstractC3722g1 abstractC3722g1) {
        return this.b == abstractC3722g1.a && this.c == abstractC3722g1.b && this.d == abstractC3722g1.c && this.e == abstractC3722g1.d && this.f == abstractC3722g1.e && this.g == abstractC3722g1.f && this.h == abstractC3722g1.g;
    }
}
