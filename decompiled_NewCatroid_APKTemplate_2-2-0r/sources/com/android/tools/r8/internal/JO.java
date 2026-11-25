package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JO extends AbstractC0859Bj {
    private int b;
    private int d;
    private int c = -1;
    private KO e = KO.c;

    private JO() {
    }

    static JO f() {
        return new JO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        LO loG = g();
        if (loG.a()) {
            return loG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new JO().a(g());
    }

    public final LO g() {
        LO lo = new LO(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        lo.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        lo.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        lo.e = this.e;
        lo.b = i2;
        return lo;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final JO a(LO lo) {
        if (lo == LO.g()) {
            return this;
        }
        if (lo.l()) {
            int i = lo.i();
            this.b |= 1;
            this.c = i;
        }
        if (lo.m()) {
            int iJ = lo.j();
            this.b |= 2;
            this.d = iJ;
        }
        if (lo.k()) {
            KO koH = lo.h();
            koH.getClass();
            this.b |= 4;
            this.e = koH;
        }
        a(e().b(lo.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        LO lo = null;
        try {
            try {
                a((LO) ((IO) LO.i).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                LO lo2 = (LO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    lo = lo2;
                    if (lo != null) {
                        a(lo);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (lo != null) {
                a(lo);
            }
            throw th;
        }
    }
}
