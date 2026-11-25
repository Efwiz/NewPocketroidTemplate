package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1610aO extends AbstractC0899Cj {
    private int d;
    private int f;
    private int g;
    private int t;
    private int v;
    private int e = 6;
    private List h = Collections.emptyList();
    private List i = Collections.emptyList();
    private List j = Collections.emptyList();
    private List k = Collections.emptyList();
    private List l = Collections.emptyList();
    private List m = Collections.emptyList();
    private List n = Collections.emptyList();
    private List o = Collections.emptyList();
    private List p = Collections.emptyList();
    private List q = Collections.emptyList();
    private List r = Collections.emptyList();
    private List s = Collections.emptyList();
    private WO u = WO.o();
    private List w = Collections.emptyList();
    private List x = Collections.emptyList();
    private List y = Collections.emptyList();
    private C2075gP z = C2075gP.g();
    private List A = Collections.emptyList();
    private C2919rP B = C2919rP.g();

    private C1610aO() {
    }

    static C1610aO f() {
        return new C1610aO();
    }

    public final void b(VO vo) {
        if ((this.d & 16) != 16) {
            this.i = new ArrayList(this.i);
            this.d |= 16;
        }
        this.i.add(vo.c());
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1761cO c1761cOG = g();
        if (c1761cOG.a()) {
            return c1761cOG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1610aO().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return C1761cO.o();
    }

    public final void e(int i) {
        this.d |= 1;
        this.e = i;
    }

    public final C1761cO g() {
        C1761cO c1761cO = new C1761cO(this, 0);
        int i = this.d;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c1761cO.d = this.e;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c1761cO.e = this.f;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c1761cO.f = this.g;
        if ((this.d & 8) == 8) {
            this.h = Collections.unmodifiableList(this.h);
            this.d &= -9;
        }
        c1761cO.g = this.h;
        if ((this.d & 16) == 16) {
            this.i = Collections.unmodifiableList(this.i);
            this.d &= -17;
        }
        c1761cO.h = this.i;
        if ((this.d & 32) == 32) {
            this.j = Collections.unmodifiableList(this.j);
            this.d &= -33;
        }
        c1761cO.i = this.j;
        if ((this.d & 64) == 64) {
            this.k = Collections.unmodifiableList(this.k);
            this.d &= -65;
        }
        c1761cO.k = this.k;
        if ((this.d & 128) == 128) {
            this.l = Collections.unmodifiableList(this.l);
            this.d &= -129;
        }
        c1761cO.m = this.l;
        if ((this.d & 256) == 256) {
            this.m = Collections.unmodifiableList(this.m);
            this.d &= -257;
        }
        c1761cO.n = this.m;
        if ((this.d & 512) == 512) {
            this.n = Collections.unmodifiableList(this.n);
            this.d &= -513;
        }
        c1761cO.p = this.n;
        if ((this.d & 1024) == 1024) {
            this.o = Collections.unmodifiableList(this.o);
            this.d &= -1025;
        }
        c1761cO.q = this.o;
        if ((this.d & 2048) == 2048) {
            this.p = Collections.unmodifiableList(this.p);
            this.d &= -2049;
        }
        c1761cO.r = this.p;
        if ((this.d & 4096) == 4096) {
            this.q = Collections.unmodifiableList(this.q);
            this.d &= -4097;
        }
        c1761cO.s = this.q;
        if ((this.d & 8192) == 8192) {
            this.r = Collections.unmodifiableList(this.r);
            this.d &= -8193;
        }
        c1761cO.t = this.r;
        if ((this.d & 16384) == 16384) {
            this.s = Collections.unmodifiableList(this.s);
            this.d &= -16385;
        }
        c1761cO.u = this.s;
        if ((i & 32768) == 32768) {
            i2 |= 8;
        }
        c1761cO.w = this.t;
        if ((i & 65536) == 65536) {
            i2 |= 16;
        }
        c1761cO.x = this.u;
        if ((i & 131072) == 131072) {
            i2 |= 32;
        }
        c1761cO.y = this.v;
        if ((this.d & 262144) == 262144) {
            this.w = Collections.unmodifiableList(this.w);
            this.d &= -262145;
        }
        c1761cO.z = this.w;
        if ((this.d & 524288) == 524288) {
            this.x = Collections.unmodifiableList(this.x);
            this.d &= -524289;
        }
        c1761cO.B = this.x;
        if ((this.d & 1048576) == 1048576) {
            this.y = Collections.unmodifiableList(this.y);
            this.d &= -1048577;
        }
        c1761cO.C = this.y;
        if ((2097152 & i) == 2097152) {
            i2 |= 64;
        }
        c1761cO.E = this.z;
        if ((this.d & 4194304) == 4194304) {
            this.A = Collections.unmodifiableList(this.A);
            this.d &= -4194305;
        }
        c1761cO.F = this.A;
        if ((i & 8388608) == 8388608) {
            i2 |= 128;
        }
        c1761cO.G = this.B;
        c1761cO.c = i2;
        return c1761cO;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1610aO a(C1761cO c1761cO) {
        if (c1761cO == C1761cO.o()) {
            return this;
        }
        if (c1761cO.H()) {
            e(c1761cO.q());
        }
        if (c1761cO.I()) {
            f(c1761cO.r());
        }
        if (c1761cO.G()) {
            d(c1761cO.k());
        }
        if (!c1761cO.g.isEmpty()) {
            if (this.h.isEmpty()) {
                this.h = c1761cO.g;
                this.d &= -9;
            } else {
                if ((this.d & 8) != 8) {
                    this.h = new ArrayList(this.h);
                    this.d |= 8;
                }
                this.h.addAll(c1761cO.g);
            }
        }
        if (!c1761cO.h.isEmpty()) {
            if (this.i.isEmpty()) {
                this.i = c1761cO.h;
                this.d &= -17;
            } else {
                if ((this.d & 16) != 16) {
                    this.i = new ArrayList(this.i);
                    this.d |= 16;
                }
                this.i.addAll(c1761cO.h);
            }
        }
        if (!c1761cO.i.isEmpty()) {
            if (this.j.isEmpty()) {
                this.j = c1761cO.i;
                this.d &= -33;
            } else {
                if ((this.d & 32) != 32) {
                    this.j = new ArrayList(this.j);
                    this.d |= 32;
                }
                this.j.addAll(c1761cO.i);
            }
        }
        if (!c1761cO.k.isEmpty()) {
            if (this.k.isEmpty()) {
                this.k = c1761cO.k;
                this.d &= -65;
            } else {
                if ((this.d & 64) != 64) {
                    this.k = new ArrayList(this.k);
                    this.d |= 64;
                }
                this.k.addAll(c1761cO.k);
            }
        }
        if (!c1761cO.m.isEmpty()) {
            if (this.l.isEmpty()) {
                this.l = c1761cO.m;
                this.d &= -129;
            } else {
                if ((this.d & 128) != 128) {
                    this.l = new ArrayList(this.l);
                    this.d |= 128;
                }
                this.l.addAll(c1761cO.m);
            }
        }
        if (!c1761cO.n.isEmpty()) {
            if (this.m.isEmpty()) {
                this.m = c1761cO.n;
                this.d &= -257;
            } else {
                if ((this.d & 256) != 256) {
                    this.m = new ArrayList(this.m);
                    this.d |= 256;
                }
                this.m.addAll(c1761cO.n);
            }
        }
        if (!c1761cO.p.isEmpty()) {
            if (this.n.isEmpty()) {
                this.n = c1761cO.p;
                this.d &= -513;
            } else {
                if ((this.d & 512) != 512) {
                    this.n = new ArrayList(this.n);
                    this.d |= 512;
                }
                this.n.addAll(c1761cO.p);
            }
        }
        if (!c1761cO.q.isEmpty()) {
            if (this.o.isEmpty()) {
                this.o = c1761cO.q;
                this.d &= -1025;
            } else {
                if ((this.d & 1024) != 1024) {
                    this.o = new ArrayList(this.o);
                    this.d |= 1024;
                }
                this.o.addAll(c1761cO.q);
            }
        }
        if (!c1761cO.r.isEmpty()) {
            if (this.p.isEmpty()) {
                this.p = c1761cO.r;
                this.d &= -2049;
            } else {
                if ((this.d & 2048) != 2048) {
                    this.p = new ArrayList(this.p);
                    this.d |= 2048;
                }
                this.p.addAll(c1761cO.r);
            }
        }
        if (!c1761cO.s.isEmpty()) {
            if (this.q.isEmpty()) {
                this.q = c1761cO.s;
                this.d &= -4097;
            } else {
                if ((this.d & 4096) != 4096) {
                    this.q = new ArrayList(this.q);
                    this.d |= 4096;
                }
                this.q.addAll(c1761cO.s);
            }
        }
        if (!c1761cO.t.isEmpty()) {
            if (this.r.isEmpty()) {
                this.r = c1761cO.t;
                this.d &= -8193;
            } else {
                if ((this.d & 8192) != 8192) {
                    this.r = new ArrayList(this.r);
                    this.d |= 8192;
                }
                this.r.addAll(c1761cO.t);
            }
        }
        if (!c1761cO.u.isEmpty()) {
            if (this.s.isEmpty()) {
                this.s = c1761cO.u;
                this.d &= -16385;
            } else {
                if ((this.d & 16384) != 16384) {
                    this.s = new ArrayList(this.s);
                    this.d |= 16384;
                }
                this.s.addAll(c1761cO.u);
            }
        }
        if (c1761cO.J()) {
            g(c1761cO.t());
        }
        if (c1761cO.K()) {
            WO woU = c1761cO.u();
            if ((this.d & 65536) != 65536 || this.u == WO.o()) {
                this.u = woU;
            } else {
                this.u = VO.f().a(this.u).a(woU).h();
            }
            this.d |= 65536;
        }
        if (c1761cO.L()) {
            int iV = c1761cO.v();
            this.d |= 131072;
            this.v = iV;
        }
        if (!c1761cO.z.isEmpty()) {
            if (this.w.isEmpty()) {
                this.w = c1761cO.z;
                this.d &= -262145;
            } else {
                if ((this.d & 262144) != 262144) {
                    this.w = new ArrayList(this.w);
                    this.d |= 262144;
                }
                this.w.addAll(c1761cO.z);
            }
        }
        if (!c1761cO.B.isEmpty()) {
            if (this.x.isEmpty()) {
                this.x = c1761cO.B;
                this.d &= -524289;
            } else {
                if ((this.d & 524288) != 524288) {
                    this.x = new ArrayList(this.x);
                    this.d |= 524288;
                }
                this.x.addAll(c1761cO.B);
            }
        }
        if (!c1761cO.C.isEmpty()) {
            if (this.y.isEmpty()) {
                this.y = c1761cO.C;
                this.d &= -1048577;
            } else {
                if ((this.d & 1048576) != 1048576) {
                    this.y = new ArrayList(this.y);
                    this.d |= 1048576;
                }
                this.y.addAll(c1761cO.C);
            }
        }
        if (c1761cO.M()) {
            C2075gP c2075gPD = c1761cO.D();
            if ((this.d & 2097152) != 2097152 || this.z == C2075gP.g()) {
                this.z = c2075gPD;
            } else {
                this.z = C1999fP.f().a(this.z).a(c2075gPD).g();
            }
            this.d |= 2097152;
        }
        if (!c1761cO.F.isEmpty()) {
            if (this.A.isEmpty()) {
                this.A = c1761cO.F;
                this.d &= -4194305;
            } else {
                if ((this.d & 4194304) != 4194304) {
                    this.A = new ArrayList(this.A);
                    this.d |= 4194304;
                }
                this.A.addAll(c1761cO.F);
            }
        }
        if (c1761cO.N()) {
            C2919rP c2919rPF = c1761cO.F();
            if ((this.d & 8388608) != 8388608 || this.B == C2919rP.g()) {
                this.B = c2919rPF;
            } else {
                this.B = C2847qP.f().a(this.B).a(c2919rPF).g();
            }
            this.d |= 8388608;
        }
        a((AbstractC0958Ej) c1761cO);
        a(e().b(c1761cO.b));
        return this;
    }

    public final void d(int i) {
        this.d |= 4;
        this.g = i;
    }

    public final void f(int i) {
        this.d |= 2;
        this.f = i;
    }

    public final void b(int i) {
        if ((this.d & 16384) != 16384) {
            this.s = new ArrayList(this.s);
            this.d |= 16384;
        }
        this.s.add(Integer.valueOf(i));
    }

    public final void c(int i) {
        if ((this.d & 4194304) != 4194304) {
            this.A = new ArrayList(this.A);
            this.d |= 4194304;
        }
        this.A.add(Integer.valueOf(i));
    }

    public final void g(int i) {
        this.d |= 32768;
        this.t = i;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1761cO c1761cO = null;
        try {
            try {
                a((C1761cO) ((ZN) C1761cO.K).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1761cO c1761cO2 = (C1761cO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1761cO = c1761cO2;
                    if (c1761cO != null) {
                        a(c1761cO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1761cO != null) {
                a(c1761cO);
            }
            throw th;
        }
    }

    public final void a(C1691bP c1691bP) {
        if ((this.d & 8) != 8) {
            this.h = new ArrayList(this.h);
            this.d |= 8;
        }
        List list = this.h;
        C1840dP c1840dPG = c1691bP.g();
        if (c1840dPG.a()) {
            list.add(c1840dPG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(int i) {
        if ((this.d & 64) != 64) {
            this.k = new ArrayList(this.k);
            this.d |= 64;
        }
        this.k.add(Integer.valueOf(i));
    }

    public final void a(VO vo) {
        if ((this.d & 128) != 128) {
            this.l = new ArrayList(this.l);
            this.d |= 128;
        }
        this.l.add(vo.c());
    }

    public final void a(C1921eO c1921eO) {
        if ((this.d & 512) != 512) {
            this.n = new ArrayList(this.n);
            this.d |= 512;
        }
        List list = this.n;
        C1998fO c1998fOG = c1921eO.g();
        if (c1998fOG.a()) {
            list.add(c1998fOG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(C3285wO c3285wO) {
        if ((this.d & 1024) != 1024) {
            this.o = new ArrayList(this.o);
            this.d |= 1024;
        }
        List list = this.o;
        C3354xO c3354xOG = c3285wO.g();
        if (c3354xOG.a()) {
            list.add(c3354xOG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(EO eo) {
        if ((this.d & 2048) != 2048) {
            this.p = new ArrayList(this.p);
            this.d |= 2048;
        }
        List list = this.p;
        FO foG = eo.g();
        if (foG.a()) {
            list.add(foG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(YO yo) {
        if ((this.d & 4096) != 4096) {
            this.q = new ArrayList(this.q);
            this.d |= 4096;
        }
        List list = this.q;
        ZO zoG = yo.g();
        if (zoG.a()) {
            list.add(zoG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(C2775pO c2775pO) {
        if ((this.d & 8192) != 8192) {
            this.r = new ArrayList(this.r);
            this.d |= 8192;
        }
        this.r.add(c2775pO.g());
    }

    public final void a(WO wo) {
        this.u = wo;
        this.d |= 65536;
    }

    public final void a(C2919rP c2919rP) {
        this.B = c2919rP;
        this.d |= 8388608;
    }
}
