package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class YO extends AbstractC0899Cj {
    private int d;
    private int f;
    private int i;
    private int k;
    private int e = 6;
    private List g = Collections.emptyList();
    private WO h = WO.o();
    private WO j = WO.o();
    private List l = Collections.emptyList();
    private List m = Collections.emptyList();

    private YO() {
    }

    static YO f() {
        return new YO();
    }

    public final void b(int i) {
        this.d |= 1;
        this.e = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        ZO zoG = g();
        if (zoG.a()) {
            return zoG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new YO().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return ZO.l();
    }

    public final ZO g() {
        ZO zo = new ZO(this, 0);
        int i = this.d;
        int i2 = (i & 1) == 1 ? 1 : 0;
        zo.d = this.e;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        zo.e = this.f;
        if ((this.d & 4) == 4) {
            this.g = Collections.unmodifiableList(this.g);
            this.d &= -5;
        }
        zo.f = this.g;
        if ((i & 8) == 8) {
            i2 |= 4;
        }
        zo.g = this.h;
        if ((i & 16) == 16) {
            i2 |= 8;
        }
        zo.h = this.i;
        if ((i & 32) == 32) {
            i2 |= 16;
        }
        zo.i = this.j;
        if ((i & 64) == 64) {
            i2 |= 32;
        }
        zo.j = this.k;
        if ((this.d & 128) == 128) {
            this.l = Collections.unmodifiableList(this.l);
            this.d &= -129;
        }
        zo.k = this.l;
        if ((this.d & 256) == 256) {
            this.m = Collections.unmodifiableList(this.m);
            this.d &= -257;
        }
        zo.l = this.m;
        zo.c = i2;
        return zo;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final YO a(ZO zo) {
        if (zo == ZO.l()) {
            return this;
        }
        if (zo.w()) {
            b(zo.o());
        }
        if (zo.x()) {
            c(zo.p());
        }
        if (!zo.f.isEmpty()) {
            if (this.g.isEmpty()) {
                this.g = zo.f;
                this.d &= -5;
            } else {
                if ((this.d & 4) != 4) {
                    this.g = new ArrayList(this.g);
                    this.d |= 4;
                }
                this.g.addAll(zo.f);
            }
        }
        if (zo.y()) {
            WO woR = zo.r();
            if ((this.d & 8) != 8 || this.h == WO.o()) {
                this.h = woR;
            } else {
                this.h = VO.f().a(this.h).a(woR).h();
            }
            this.d |= 8;
        }
        if (zo.z()) {
            int iS = zo.s();
            this.d |= 16;
            this.i = iS;
        }
        if (zo.u()) {
            WO woM = zo.m();
            if ((this.d & 32) != 32 || this.j == WO.o()) {
                this.j = woM;
            } else {
                this.j = VO.f().a(this.j).a(woM).h();
            }
            this.d |= 32;
        }
        if (zo.v()) {
            int iN = zo.n();
            this.d |= 64;
            this.k = iN;
        }
        if (!zo.k.isEmpty()) {
            if (this.l.isEmpty()) {
                this.l = zo.k;
                this.d &= -129;
            } else {
                if ((this.d & 128) != 128) {
                    this.l = new ArrayList(this.l);
                    this.d |= 128;
                }
                this.l.addAll(zo.k);
            }
        }
        if (!zo.l.isEmpty()) {
            if (this.m.isEmpty()) {
                this.m = zo.l;
                this.d &= -257;
            } else {
                if ((this.d & 256) != 256) {
                    this.m = new ArrayList(this.m);
                    this.d |= 256;
                }
                this.m.addAll(zo.l);
            }
        }
        a((AbstractC0958Ej) zo);
        a(e().b(zo.b));
        return this;
    }

    public final void b(WO wo) {
        this.h = wo;
        this.d |= 8;
    }

    public final void c(int i) {
        this.d |= 2;
        this.f = i;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        ZO zo = null;
        try {
            try {
                a((ZO) ((XO) ZO.p).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                ZO zo2 = (ZO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    zo = zo2;
                    if (zo != null) {
                        a(zo);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (zo != null) {
                a(zo);
            }
            throw th;
        }
    }

    public final void a(C1691bP c1691bP) {
        if ((this.d & 4) != 4) {
            this.g = new ArrayList(this.g);
            this.d |= 4;
        }
        List list = this.g;
        C1840dP c1840dPG = c1691bP.g();
        if (c1840dPG.a()) {
            list.add(c1840dPG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(WO wo) {
        this.j = wo;
        this.d |= 32;
    }

    public final void a(WN wn) {
        if ((this.d & 128) != 128) {
            this.l = new ArrayList(this.l);
            this.d |= 128;
        }
        List list = this.l;
        XN xnG = wn.g();
        if (xnG.a()) {
            list.add(xnG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(int i) {
        if ((this.d & 256) != 256) {
            this.m = new ArrayList(this.m);
            this.d |= 256;
        }
        this.m.add(Integer.valueOf(i));
    }
}
