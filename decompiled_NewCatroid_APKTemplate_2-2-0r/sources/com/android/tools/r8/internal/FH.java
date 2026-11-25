package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FH {
    static final /* synthetic */ boolean e = true;
    private final AbstractC1049Hn a;
    private int b = 7;
    private AbstractC2744p c = C1501Xf.e();
    private C0684j2 d;

    public FH(AbstractC1049Hn abstractC1049Hn) {
        this.a = abstractC1049Hn;
    }

    public final AbstractC1380Tn a() {
        if (this.a.b() && this.b == 0) {
            AbstractC2744p abstractC2744p = this.c;
            abstractC2744p.getClass();
            if ((abstractC2744p instanceof C2198i10) && this.d == null) {
                return C1779ce.h();
            }
        }
        return new GH(this.b, this.a, this.c, this.d);
    }

    public final C0684j2 b() {
        return this.d;
    }

    public final boolean c() {
        return this.d != null;
    }

    public final FH d() {
        this.c = C2198i10.e();
        return this;
    }

    public final boolean e() {
        return (this.b & (-3)) == 0;
    }

    public final FH f() {
        this.b &= -2;
        return this;
    }

    public final FH g() {
        this.b &= -3;
        return this;
    }

    public final void h() {
        this.b &= -5;
    }

    public final void a(com.android.tools.r8.graph.T0 t0) {
        if (this.c.d()) {
            AbstractC2744p abstractC2744p = this.c;
            abstractC2744p.getClass();
            if (abstractC2744p instanceof C1501Xf) {
                this.c = new C2092gc(t0);
            } else {
                this.c.a().b(t0);
            }
        }
        if (!e && !this.c.a(t0)) {
            throw new AssertionError();
        }
    }

    public final void a(AbstractC1380Tn abstractC1380Tn) {
        AbstractC2744p abstractC2744pF = abstractC1380Tn.f();
        AbstractC2744p abstractC2744p = this.c;
        abstractC2744p.getClass();
        if (!(abstractC2744p instanceof C2198i10)) {
            abstractC2744pF.getClass();
            if (!(abstractC2744pF instanceof C1501Xf)) {
                if (abstractC2744pF instanceof C2198i10) {
                    this.c = C2198i10.e();
                } else {
                    C2092gc c2092gcA = abstractC2744pF.a();
                    AbstractC2744p abstractC2744p2 = this.c;
                    abstractC2744p2.getClass();
                    if (abstractC2744p2 instanceof C1501Xf) {
                        this.c = new C2092gc().a(c2092gcA);
                    } else {
                        this.c.a().a(c2092gcA);
                    }
                }
            }
        }
        if (abstractC1380Tn.d()) {
            f();
        }
        if (abstractC1380Tn.e()) {
            g();
        }
        if (abstractC1380Tn.g()) {
            return;
        }
        h();
    }

    public final FH a(C0684j2 c0684j2) {
        if (!e && c() && this.d != c0684j2) {
            throw new AssertionError();
        }
        this.d = c0684j2;
        return this;
    }
}
