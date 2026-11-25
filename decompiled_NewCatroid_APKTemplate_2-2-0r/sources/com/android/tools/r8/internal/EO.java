package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EO extends AbstractC0899Cj {
    private int d;
    private int g;
    private int i;
    private int l;
    private int p;
    private int q;
    private int e = 518;
    private int f = 2054;
    private WO h = WO.o();
    private List j = Collections.emptyList();
    private WO k = WO.o();
    private List m = Collections.emptyList();
    private List n = Collections.emptyList();
    private C2303jP o = C2303jP.k();
    private List r = Collections.emptyList();

    private EO() {
    }

    static EO f() {
        return new EO();
    }

    public final void b(int i) {
        this.d |= 1;
        this.e = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        FO foG = g();
        if (foG.a()) {
            return foG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new EO().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return FO.m();
    }

    public final void e(int i) {
        this.d |= 4096;
        this.q = i;
    }

    public final FO g() {
        FO fo = new FO(this, 0);
        int i = this.d;
        int i2 = (i & 1) == 1 ? 1 : 0;
        fo.d = this.e;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        fo.e = this.f;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        fo.f = this.g;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        fo.g = this.h;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        fo.h = this.i;
        if ((this.d & 32) == 32) {
            this.j = Collections.unmodifiableList(this.j);
            this.d &= -33;
        }
        fo.i = this.j;
        if ((i & 64) == 64) {
            i2 |= 32;
        }
        fo.j = this.k;
        if ((i & 128) == 128) {
            i2 |= 64;
        }
        fo.k = this.l;
        if ((this.d & 256) == 256) {
            this.m = Collections.unmodifiableList(this.m);
            this.d &= -257;
        }
        fo.l = this.m;
        if ((this.d & 512) == 512) {
            this.n = Collections.unmodifiableList(this.n);
            this.d &= -513;
        }
        fo.m = this.n;
        if ((i & 1024) == 1024) {
            i2 |= 128;
        }
        fo.o = this.o;
        if ((i & 2048) == 2048) {
            i2 |= 256;
        }
        fo.p = this.p;
        if ((i & 4096) == 4096) {
            i2 |= 512;
        }
        fo.q = this.q;
        if ((this.d & 8192) == 8192) {
            this.r = Collections.unmodifiableList(this.r);
            this.d &= -8193;
        }
        fo.r = this.r;
        fo.c = i2;
        return fo;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final EO a(FO fo) {
        if (fo == FO.m()) {
            return this;
        }
        if (fo.z()) {
            b(fo.n());
        }
        if (fo.C()) {
            int iQ = fo.q();
            this.d |= 2;
            this.f = iQ;
        }
        if (fo.B()) {
            d(fo.p());
        }
        if (fo.F()) {
            WO woT = fo.t();
            if ((this.d & 8) != 8 || this.h == WO.o()) {
                this.h = woT;
            } else {
                this.h = VO.f().a(this.h).a(woT).h();
            }
            this.d |= 8;
        }
        if (fo.G()) {
            int iU = fo.u();
            this.d |= 16;
            this.i = iU;
        }
        if (!fo.i.isEmpty()) {
            if (this.j.isEmpty()) {
                this.j = fo.i;
                this.d &= -33;
            } else {
                if ((this.d & 32) != 32) {
                    this.j = new ArrayList(this.j);
                    this.d |= 32;
                }
                this.j.addAll(fo.i);
            }
        }
        if (fo.D()) {
            WO woR = fo.r();
            if ((this.d & 64) != 64 || this.k == WO.o()) {
                this.k = woR;
            } else {
                this.k = VO.f().a(this.k).a(woR).h();
            }
            this.d |= 64;
        }
        if (fo.E()) {
            int iS = fo.s();
            this.d |= 128;
            this.l = iS;
        }
        if (!fo.l.isEmpty()) {
            if (this.m.isEmpty()) {
                this.m = fo.l;
                this.d &= -257;
            } else {
                if ((this.d & 256) != 256) {
                    this.m = new ArrayList(this.m);
                    this.d |= 256;
                }
                this.m.addAll(fo.l);
            }
        }
        if (!fo.m.isEmpty()) {
            if (this.n.isEmpty()) {
                this.n = fo.m;
                this.d &= -513;
            } else {
                if ((this.d & 512) != 512) {
                    this.n = new ArrayList(this.n);
                    this.d |= 512;
                }
                this.n.addAll(fo.m);
            }
        }
        if (fo.I()) {
            C2303jP c2303jPW = fo.w();
            if ((this.d & 1024) != 1024 || this.o == C2303jP.k()) {
                this.o = c2303jPW;
            } else {
                this.o = C2233iP.f().a(this.o).a(c2303jPW).g();
            }
            this.d |= 1024;
        }
        if (fo.A()) {
            c(fo.o());
        }
        if (fo.H()) {
            e(fo.v());
        }
        if (!fo.r.isEmpty()) {
            if (this.r.isEmpty()) {
                this.r = fo.r;
                this.d &= -8193;
            } else {
                if ((this.d & 8192) != 8192) {
                    this.r = new ArrayList(this.r);
                    this.d |= 8192;
                }
                this.r.addAll(fo.r);
            }
        }
        a((AbstractC0958Ej) fo);
        a(e().b(fo.b));
        return this;
    }

    public final void d(int i) {
        this.d |= 4;
        this.g = i;
    }

    public final void b(WO wo) {
        this.h = wo;
        this.d |= 8;
    }

    public final void c(int i) {
        this.d |= 2048;
        this.p = i;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        FO fo = null;
        try {
            try {
                a((FO) ((DO) FO.v).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                FO fo2 = (FO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    fo = fo2;
                    if (fo != null) {
                        a(fo);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (fo != null) {
                a(fo);
            }
            throw th;
        }
    }

    public final void a(C1691bP c1691bP) {
        if ((this.d & 32) != 32) {
            this.j = new ArrayList(this.j);
            this.d |= 32;
        }
        List list = this.j;
        C1840dP c1840dPG = c1691bP.g();
        if (c1840dPG.a()) {
            list.add(c1840dPG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(WO wo) {
        this.k = wo;
        this.d |= 64;
    }

    public final void a(VO vo) {
        if ((this.d & 256) != 256) {
            this.m = new ArrayList(this.m);
            this.d |= 256;
        }
        this.m.add(vo.c());
    }

    public final void a(C2303jP c2303jP) {
        this.o = c2303jP;
        this.d |= 1024;
    }

    public final void a(int i) {
        if ((this.d & 8192) != 8192) {
            this.r = new ArrayList(this.r);
            this.d |= 8192;
        }
        this.r.add(Integer.valueOf(i));
    }
}
