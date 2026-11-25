package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SO extends AbstractC0859Bj {
    private int b;
    private TO c = TO.d;
    private WO d = WO.o();
    private int e;

    private SO() {
    }

    static SO f() {
        return new SO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        UO uoG = g();
        if (uoG.a()) {
            return uoG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new SO().a(g());
    }

    public final UO g() {
        UO uo = new UO(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        uo.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        uo.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        uo.e = this.e;
        uo.b = i2;
        return uo;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final SO a(UO uo) {
        if (uo == UO.g()) {
            return this;
        }
        if (uo.k()) {
            a(uo.h());
        }
        if (uo.l()) {
            WO woI = uo.i();
            if ((this.b & 2) != 2 || this.d == WO.o()) {
                this.d = woI;
            } else {
                this.d = VO.f().a(this.d).a(woI).h();
            }
            this.b |= 2;
        }
        if (uo.m()) {
            int iJ = uo.j();
            this.b |= 4;
            this.e = iJ;
        }
        a(e().b(uo.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        UO uo = null;
        try {
            try {
                a((UO) ((RO) UO.i).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                UO uo2 = (UO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    uo = uo2;
                    if (uo != null) {
                        a(uo);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (uo != null) {
                a(uo);
            }
            throw th;
        }
    }

    public final void a(TO to) {
        to.getClass();
        this.b |= 1;
        this.c = to;
    }

    public final void a(WO wo) {
        this.d = wo;
        this.b |= 2;
    }
}
