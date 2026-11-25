package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3285wO extends AbstractC0899Cj {
    private int d;
    private int g;
    private int i;
    private int l;
    private int e = 6;
    private int f = 6;
    private WO h = WO.o();
    private List j = Collections.emptyList();
    private WO k = WO.o();
    private List m = Collections.emptyList();
    private List n = Collections.emptyList();
    private List o = Collections.emptyList();
    private C2075gP p = C2075gP.g();
    private List q = Collections.emptyList();
    private C2232iO r = C2232iO.g();

    private C3285wO() {
    }

    static C3285wO f() {
        return new C3285wO();
    }

    public final void b(int i) {
        this.d |= 1;
        this.e = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C3354xO c3354xOG = g();
        if (c3354xOG.a()) {
            return c3354xOG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C3285wO().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return C3354xO.n();
    }

    public final C3354xO g() {
        C3354xO c3354xO = new C3354xO(this, 0);
        int i = this.d;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c3354xO.d = this.e;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c3354xO.e = this.f;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c3354xO.f = this.g;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c3354xO.g = this.h;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        c3354xO.h = this.i;
        if ((this.d & 32) == 32) {
            this.j = Collections.unmodifiableList(this.j);
            this.d &= -33;
        }
        c3354xO.i = this.j;
        if ((i & 64) == 64) {
            i2 |= 32;
        }
        c3354xO.j = this.k;
        if ((i & 128) == 128) {
            i2 |= 64;
        }
        c3354xO.k = this.l;
        if ((this.d & 256) == 256) {
            this.m = Collections.unmodifiableList(this.m);
            this.d &= -257;
        }
        c3354xO.l = this.m;
        if ((this.d & 512) == 512) {
            this.n = Collections.unmodifiableList(this.n);
            this.d &= -513;
        }
        c3354xO.m = this.n;
        if ((this.d & 1024) == 1024) {
            this.o = Collections.unmodifiableList(this.o);
            this.d &= -1025;
        }
        c3354xO.o = this.o;
        if ((i & 2048) == 2048) {
            i2 |= 128;
        }
        c3354xO.p = this.p;
        if ((this.d & 4096) == 4096) {
            this.q = Collections.unmodifiableList(this.q);
            this.d &= -4097;
        }
        c3354xO.q = this.q;
        if ((i & 8192) == 8192) {
            i2 |= 256;
        }
        c3354xO.r = this.r;
        c3354xO.c = i2;
        return c3354xO;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C3285wO a(C3354xO c3354xO) {
        if (c3354xO == C3354xO.n()) {
            return this;
        }
        if (c3354xO.A()) {
            b(c3354xO.o());
        }
        if (c3354xO.C()) {
            int iQ = c3354xO.q();
            this.d |= 2;
            this.f = iQ;
        }
        if (c3354xO.B()) {
            c(c3354xO.p());
        }
        if (c3354xO.F()) {
            WO woT = c3354xO.t();
            if ((this.d & 8) != 8 || this.h == WO.o()) {
                this.h = woT;
            } else {
                this.h = VO.f().a(this.h).a(woT).h();
            }
            this.d |= 8;
        }
        if (c3354xO.G()) {
            int iU = c3354xO.u();
            this.d |= 16;
            this.i = iU;
        }
        if (!c3354xO.i.isEmpty()) {
            if (this.j.isEmpty()) {
                this.j = c3354xO.i;
                this.d &= -33;
            } else {
                if ((this.d & 32) != 32) {
                    this.j = new ArrayList(this.j);
                    this.d |= 32;
                }
                this.j.addAll(c3354xO.i);
            }
        }
        if (c3354xO.D()) {
            WO woR = c3354xO.r();
            if ((this.d & 64) != 64 || this.k == WO.o()) {
                this.k = woR;
            } else {
                this.k = VO.f().a(this.k).a(woR).h();
            }
            this.d |= 64;
        }
        if (c3354xO.E()) {
            int iS = c3354xO.s();
            this.d |= 128;
            this.l = iS;
        }
        if (!c3354xO.l.isEmpty()) {
            if (this.m.isEmpty()) {
                this.m = c3354xO.l;
                this.d &= -257;
            } else {
                if ((this.d & 256) != 256) {
                    this.m = new ArrayList(this.m);
                    this.d |= 256;
                }
                this.m.addAll(c3354xO.l);
            }
        }
        if (!c3354xO.m.isEmpty()) {
            if (this.n.isEmpty()) {
                this.n = c3354xO.m;
                this.d &= -513;
            } else {
                if ((this.d & 512) != 512) {
                    this.n = new ArrayList(this.n);
                    this.d |= 512;
                }
                this.n.addAll(c3354xO.m);
            }
        }
        if (!c3354xO.o.isEmpty()) {
            if (this.o.isEmpty()) {
                this.o = c3354xO.o;
                this.d &= -1025;
            } else {
                if ((this.d & 1024) != 1024) {
                    this.o = new ArrayList(this.o);
                    this.d |= 1024;
                }
                this.o.addAll(c3354xO.o);
            }
        }
        if (c3354xO.H()) {
            C2075gP c2075gPW = c3354xO.w();
            if ((this.d & 2048) != 2048 || this.p == C2075gP.g()) {
                this.p = c2075gPW;
            } else {
                this.p = C1999fP.f().a(this.p).a(c2075gPW).g();
            }
            this.d |= 2048;
        }
        if (!c3354xO.q.isEmpty()) {
            if (this.q.isEmpty()) {
                this.q = c3354xO.q;
                this.d &= -4097;
            } else {
                if ((this.d & 4096) != 4096) {
                    this.q = new ArrayList(this.q);
                    this.d |= 4096;
                }
                this.q.addAll(c3354xO.q);
            }
        }
        if (c3354xO.z()) {
            C2232iO c2232iOM = c3354xO.m();
            if ((this.d & 8192) != 8192 || this.r == C2232iO.g()) {
                this.r = c2232iOM;
            } else {
                this.r = C2152hO.f().a(this.r).a(c2232iOM).g();
            }
            this.d |= 8192;
        }
        a((AbstractC0958Ej) c3354xO);
        a(e().b(c3354xO.b));
        return this;
    }

    public final void b(WO wo) {
        this.h = wo;
        this.d |= 8;
    }

    public final void c(int i) {
        this.d |= 4;
        this.g = i;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C3354xO c3354xO = null;
        try {
            try {
                a((C3354xO) ((C3207vO) C3354xO.v).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C3354xO c3354xO2 = (C3354xO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c3354xO = c3354xO2;
                    if (c3354xO != null) {
                        a(c3354xO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c3354xO != null) {
                a(c3354xO);
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

    public final void a(C2233iP c2233iP) {
        if ((this.d & 1024) != 1024) {
            this.o = new ArrayList(this.o);
            this.d |= 1024;
        }
        List list = this.o;
        C2303jP c2303jPG = c2233iP.g();
        if (c2303jPG.a()) {
            list.add(c2303jPG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(int i) {
        if ((this.d & 4096) != 4096) {
            this.q = new ArrayList(this.q);
            this.d |= 4096;
        }
        this.q.add(Integer.valueOf(i));
    }

    public final void a(C2232iO c2232iO) {
        this.r = c2232iO;
        this.d |= 8192;
    }
}
