package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VO extends AbstractC0899Cj {
    private int d;
    private boolean f;
    private int g;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int o;
    private int q;
    private int r;
    private List e = Collections.emptyList();
    private WO h = WO.o();
    private WO n = WO.o();
    private WO p = WO.o();

    private VO() {
    }

    static VO f() {
        return new VO();
    }

    public final void b(WO wo) {
        this.p = wo;
        this.d |= 2048;
    }

    public final Object clone() {
        return new VO().a(h());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return WO.o();
    }

    public final void e(int i) {
        this.d |= 64;
        this.k = i;
    }

    @Override // com.android.tools.r8.internal.A0
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public final WO c() {
        WO woH = h();
        if (woH.a()) {
            return woH;
        }
        throw new C1659b10();
    }

    public final WO h() {
        WO wo = new WO(this, 0);
        int i = this.d;
        if ((i & 1) == 1) {
            this.e = Collections.unmodifiableList(this.e);
            this.d &= -2;
        }
        wo.d = this.e;
        int i2 = (i & 2) == 2 ? 1 : 0;
        wo.e = this.f;
        if ((i & 4) == 4) {
            i2 |= 2;
        }
        wo.f = this.g;
        if ((i & 8) == 8) {
            i2 |= 4;
        }
        wo.g = this.h;
        if ((i & 16) == 16) {
            i2 |= 8;
        }
        wo.h = this.i;
        if ((i & 32) == 32) {
            i2 |= 16;
        }
        wo.i = this.j;
        if ((i & 64) == 64) {
            i2 |= 32;
        }
        wo.j = this.k;
        if ((i & 128) == 128) {
            i2 |= 64;
        }
        wo.k = this.l;
        if ((i & 256) == 256) {
            i2 |= 128;
        }
        wo.l = this.m;
        if ((i & 512) == 512) {
            i2 |= 256;
        }
        wo.m = this.n;
        if ((i & 1024) == 1024) {
            i2 |= 512;
        }
        wo.n = this.o;
        if ((i & 2048) == 2048) {
            i2 |= 1024;
        }
        wo.o = this.p;
        if ((i & 4096) == 4096) {
            i2 |= 2048;
        }
        wo.p = this.q;
        if ((i & 8192) == 8192) {
            i2 |= 4096;
        }
        wo.q = this.r;
        wo.c = i2;
        return wo;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final VO a(WO wo) {
        if (wo == WO.o()) {
            return this;
        }
        if (!wo.d.isEmpty()) {
            if (this.e.isEmpty()) {
                this.e = wo.d;
                this.d &= -2;
            } else {
                if ((this.d & 1) != 1) {
                    this.e = new ArrayList(this.e);
                    this.d |= 1;
                }
                this.e.addAll(wo.d);
            }
        }
        if (wo.G()) {
            a(wo.t());
        }
        if (wo.D()) {
            c(wo.q());
        }
        if (wo.E()) {
            WO woR = wo.r();
            if ((this.d & 8) != 8 || this.h == WO.o()) {
                this.h = woR;
            } else {
                this.h = new VO().a(this.h).a(woR).h();
            }
            this.d |= 8;
        }
        if (wo.F()) {
            int iS = wo.s();
            this.d |= 16;
            this.i = iS;
        }
        if (wo.B()) {
            a(wo.n());
        }
        if (wo.K()) {
            e(wo.x());
        }
        if (wo.L()) {
            int iY = wo.y();
            this.d |= 128;
            this.l = iY;
        }
        if (wo.J()) {
            d(wo.w());
        }
        if (wo.H()) {
            WO woU = wo.u();
            if ((this.d & 512) != 512 || this.n == WO.o()) {
                this.n = woU;
            } else {
                this.n = new VO().a(this.n).a(woU).h();
            }
            this.d |= 512;
        }
        if (wo.I()) {
            int iV = wo.v();
            this.d |= 1024;
            this.o = iV;
        }
        if (wo.z()) {
            WO woK = wo.k();
            if ((this.d & 2048) != 2048 || this.p == WO.o()) {
                this.p = woK;
            } else {
                this.p = new VO().a(this.p).a(woK).h();
            }
            this.d |= 2048;
        }
        if (wo.A()) {
            int iL = wo.l();
            this.d |= 4096;
            this.q = iL;
        }
        if (wo.C()) {
            b(wo.p());
        }
        a((AbstractC0958Ej) wo);
        a(e().b(wo.b));
        return this;
    }

    public final void c(int i) {
        this.d |= 4;
        this.g = i;
    }

    public final void d(int i) {
        this.d |= 256;
        this.m = i;
    }

    public final void b(int i) {
        this.d |= 8192;
        this.r = i;
    }

    public final void c(WO wo) {
        this.h = wo;
        this.d |= 8;
    }

    public final void d(WO wo) {
        this.n = wo;
        this.d |= 512;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        WO wo = null;
        try {
            try {
                a((WO) ((QO) WO.u).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                WO wo2 = (WO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    wo = wo2;
                    if (wo != null) {
                        a(wo);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (wo != null) {
                a(wo);
            }
            throw th;
        }
    }

    public final void a(SO so) {
        if ((this.d & 1) != 1) {
            this.e = new ArrayList(this.e);
            this.d |= 1;
        }
        List list = this.e;
        UO uoG = so.g();
        if (uoG.a()) {
            list.add(uoG);
            return;
        }
        throw new C1659b10();
    }

    public final VO a(boolean z) {
        this.d |= 2;
        this.f = z;
        return this;
    }

    public final void a(int i) {
        this.d |= 32;
        this.j = i;
    }
}
