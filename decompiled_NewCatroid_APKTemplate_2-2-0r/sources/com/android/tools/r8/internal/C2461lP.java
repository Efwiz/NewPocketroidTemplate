package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2461lP extends AbstractC0859Bj {
    private int b;
    private int c;
    private int d;
    private int f;
    private int g;
    private EnumC2535mP e = EnumC2535mP.c;
    private EnumC2609nP h = EnumC2609nP.b;

    private C2461lP() {
    }

    static C2461lP f() {
        return new C2461lP();
    }

    public final void b(int i) {
        this.b |= 16;
        this.g = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2700oP c2700oPG = g();
        if (c2700oPG.a()) {
            return c2700oPG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C2461lP().a(g());
    }

    public final void d(int i) {
        this.b |= 2;
        this.d = i;
    }

    public final C2700oP g() {
        C2700oP c2700oP = new C2700oP(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c2700oP.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c2700oP.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c2700oP.e = this.e;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c2700oP.f = this.f;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        c2700oP.g = this.g;
        if ((i & 32) == 32) {
            i2 |= 32;
        }
        c2700oP.h = this.h;
        c2700oP.b = i2;
        return c2700oP;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2461lP a(C2700oP c2700oP) {
        if (c2700oP == C2700oP.g()) {
            return this;
        }
        if (c2700oP.q()) {
            c(c2700oP.k());
        }
        if (c2700oP.r()) {
            d(c2700oP.l());
        }
        if (c2700oP.o()) {
            a(c2700oP.i());
        }
        if (c2700oP.n()) {
            a(c2700oP.h());
        }
        if (c2700oP.p()) {
            b(c2700oP.j());
        }
        if (c2700oP.s()) {
            a(c2700oP.m());
        }
        a(e().b(c2700oP.a));
        return this;
    }

    public final void c(int i) {
        this.b |= 1;
        this.c = i;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2700oP c2700oP = null;
        try {
            try {
                a((C2700oP) ((C2381kP) C2700oP.l).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2700oP c2700oP2 = (C2700oP) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2700oP = c2700oP2;
                    if (c2700oP != null) {
                        a(c2700oP);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2700oP != null) {
                a(c2700oP);
            }
            throw th;
        }
    }

    public final void a(EnumC2535mP enumC2535mP) {
        enumC2535mP.getClass();
        this.b |= 4;
        this.e = enumC2535mP;
    }

    public final void a(int i) {
        this.b |= 8;
        this.f = i;
    }

    public final void a(EnumC2609nP enumC2609nP) {
        enumC2609nP.getClass();
        this.b |= 32;
        this.h = enumC2609nP;
    }
}
