package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1280Pu extends AbstractC0859Bj {
    private int b;
    private C1143Ku c = C1143Ku.g();
    private C1223Nu d = C1223Nu.g();
    private C1223Nu e = C1223Nu.g();
    private C1223Nu f = C1223Nu.g();
    private C1223Nu g = C1223Nu.g();

    private C1280Pu() {
    }

    static C1280Pu f() {
        return new C1280Pu();
    }

    public final void b(C1223Nu c1223Nu) {
        this.e = c1223Nu;
        this.b |= 4;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1307Qu c1307QuG = g();
        if (c1307QuG.a()) {
            return c1307QuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1280Pu().a(g());
    }

    public final void d(C1223Nu c1223Nu) {
        this.d = c1223Nu;
        this.b |= 2;
    }

    public final C1307Qu g() {
        C1307Qu c1307Qu = new C1307Qu(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c1307Qu.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c1307Qu.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c1307Qu.e = this.e;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c1307Qu.f = this.f;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        c1307Qu.g = this.g;
        c1307Qu.b = i2;
        return c1307Qu;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1280Pu a(C1307Qu c1307Qu) {
        if (c1307Qu == C1307Qu.g()) {
            return this;
        }
        if (c1307Qu.n()) {
            C1143Ku c1143KuI = c1307Qu.i();
            if ((this.b & 1) != 1 || this.c == C1143Ku.g()) {
                this.c = c1143KuI;
            } else {
                this.c = C1115Ju.f().a(this.c).a(c1143KuI).g();
            }
            this.b |= 1;
        }
        if (c1307Qu.q()) {
            C1223Nu c1223NuL = c1307Qu.l();
            if ((this.b & 2) != 2 || this.d == C1223Nu.g()) {
                this.d = c1223NuL;
            } else {
                this.d = C1196Mu.f().a(this.d).a(c1223NuL).g();
            }
            this.b |= 2;
        }
        if (c1307Qu.o()) {
            C1223Nu c1223NuJ = c1307Qu.j();
            if ((this.b & 4) != 4 || this.e == C1223Nu.g()) {
                this.e = c1223NuJ;
            } else {
                this.e = C1196Mu.f().a(this.e).a(c1223NuJ).g();
            }
            this.b |= 4;
        }
        if (c1307Qu.p()) {
            C1223Nu c1223NuK = c1307Qu.k();
            if ((this.b & 8) != 8 || this.f == C1223Nu.g()) {
                this.f = c1223NuK;
            } else {
                this.f = C1196Mu.f().a(this.f).a(c1223NuK).g();
            }
            this.b |= 8;
        }
        if (c1307Qu.m()) {
            C1223Nu c1223NuH = c1307Qu.h();
            if ((this.b & 16) != 16 || this.g == C1223Nu.g()) {
                this.g = c1223NuH;
            } else {
                this.g = C1196Mu.f().a(this.g).a(c1223NuH).g();
            }
            this.b |= 16;
        }
        a(e().b(c1307Qu.a));
        return this;
    }

    public final void c(C1223Nu c1223Nu) {
        this.f = c1223Nu;
        this.b |= 8;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1307Qu c1307Qu = null;
        try {
            try {
                a((C1307Qu) ((C1250Ou) C1307Qu.k).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1307Qu c1307Qu2 = (C1307Qu) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1307Qu = c1307Qu2;
                    if (c1307Qu != null) {
                        a(c1307Qu);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1307Qu != null) {
                a(c1307Qu);
            }
            throw th;
        }
    }

    public final void a(C1143Ku c1143Ku) {
        this.c = c1143Ku;
        this.b |= 1;
    }

    public final void a(C1223Nu c1223Nu) {
        this.g = c1223Nu;
        this.b |= 16;
    }
}
