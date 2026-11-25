package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2233iP extends AbstractC0899Cj {
    private int d;
    private int e;
    private int f;
    private int h;
    private int j;
    private WO g = WO.o();
    private WO i = WO.o();

    private C2233iP() {
    }

    static C2233iP f() {
        return new C2233iP();
    }

    public final void b(int i) {
        this.d |= 2;
        this.f = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2303jP c2303jPG = g();
        if (c2303jPG.a()) {
            return c2303jPG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C2233iP().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return C2303jP.k();
    }

    public final C2303jP g() {
        C2303jP c2303jP = new C2303jP(this, 0);
        int i = this.d;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c2303jP.d = this.e;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c2303jP.e = this.f;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c2303jP.f = this.g;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c2303jP.g = this.h;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        c2303jP.h = this.i;
        if ((i & 32) == 32) {
            i2 |= 32;
        }
        c2303jP.i = this.j;
        c2303jP.c = i2;
        return c2303jP;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2233iP a(C2303jP c2303jP) {
        if (c2303jP == C2303jP.k()) {
            return this;
        }
        if (c2303jP.r()) {
            a(c2303jP.l());
        }
        if (c2303jP.s()) {
            b(c2303jP.m());
        }
        if (c2303jP.t()) {
            WO woN = c2303jP.n();
            if ((this.d & 4) != 4 || this.g == WO.o()) {
                this.g = woN;
            } else {
                this.g = VO.f().a(this.g).a(woN).h();
            }
            this.d |= 4;
        }
        if (c2303jP.u()) {
            int iO = c2303jP.o();
            this.d |= 8;
            this.h = iO;
        }
        if (c2303jP.v()) {
            WO woP = c2303jP.p();
            if ((this.d & 16) != 16 || this.i == WO.o()) {
                this.i = woP;
            } else {
                this.i = VO.f().a(this.i).a(woP).h();
            }
            this.d |= 16;
        }
        if (c2303jP.w()) {
            int iQ = c2303jP.q();
            this.d |= 32;
            this.j = iQ;
        }
        a((AbstractC0958Ej) c2303jP);
        a(e().b(c2303jP.b));
        return this;
    }

    public final void b(WO wo) {
        this.i = wo;
        this.d |= 16;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2303jP c2303jP = null;
        try {
            try {
                a((C2303jP) ((C2153hP) C2303jP.m).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2303jP c2303jP2 = (C2303jP) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2303jP = c2303jP2;
                    if (c2303jP != null) {
                        a(c2303jP);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2303jP != null) {
                a(c2303jP);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.d |= 1;
        this.e = i;
    }

    public final void a(WO wo) {
        this.g = wo;
        this.d |= 4;
    }
}
