package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SN extends AbstractC0859Bj {
    private int b;
    private long d;
    private float e;
    private double f;
    private int g;
    private int h;
    private int i;
    private int l;
    private int m;
    private TN c = TN.b;
    private XN j = XN.h();
    private List k = Collections.emptyList();

    private SN() {
    }

    static SN f() {
        return new SN();
    }

    public final void b(int i) {
        this.b |= 32;
        this.h = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        UN unG = g();
        if (unG.a()) {
            return unG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new SN().a(g());
    }

    public final void d(int i) {
        this.b |= 1024;
        this.m = i;
    }

    public final void e(int i) {
        this.b |= 16;
        this.g = i;
    }

    public final UN g() {
        UN un = new UN(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        un.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        un.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        un.e = this.e;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        un.f = this.f;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        un.g = this.g;
        if ((i & 32) == 32) {
            i2 |= 32;
        }
        un.h = this.h;
        if ((i & 64) == 64) {
            i2 |= 64;
        }
        un.i = this.i;
        if ((i & 128) == 128) {
            i2 |= 128;
        }
        un.j = this.j;
        if ((this.b & 256) == 256) {
            this.k = Collections.unmodifiableList(this.k);
            this.b &= -257;
        }
        un.k = this.k;
        if ((i & 512) == 512) {
            i2 |= 256;
        }
        un.l = this.l;
        if ((i & 1024) == 1024) {
            i2 |= 512;
        }
        un.m = this.m;
        un.b = i2;
        return un;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final SN a(UN un) {
        if (un == UN.k()) {
            return this;
        }
        if (un.B()) {
            a(un.r());
        }
        if (un.z()) {
            a(un.p());
        }
        if (un.y()) {
            a(un.o());
        }
        if (un.v()) {
            a(un.l());
        }
        if (un.A()) {
            e(un.q());
        }
        if (un.u()) {
            b(un.j());
        }
        if (un.w()) {
            c(un.m());
        }
        if (un.s()) {
            XN xnG = un.g();
            if ((this.b & 128) != 128 || this.j == XN.h()) {
                this.j = xnG;
            } else {
                this.j = WN.f().a(this.j).a(xnG).g();
            }
            this.b |= 128;
        }
        if (!un.k.isEmpty()) {
            if (this.k.isEmpty()) {
                this.k = un.k;
                this.b &= -257;
            } else {
                if ((this.b & 256) != 256) {
                    this.k = new ArrayList(this.k);
                    this.b |= 256;
                }
                this.k.addAll(un.k);
            }
        }
        if (un.t()) {
            a(un.h());
        }
        if (un.x()) {
            d(un.n());
        }
        a(e().b(un.a));
        return this;
    }

    public final void c(int i) {
        this.b |= 64;
        this.i = i;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        UN un = null;
        try {
            try {
                a((UN) ((RN) UN.q).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                UN un2 = (UN) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    un = un2;
                    if (un != null) {
                        a(un);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (un != null) {
                a(un);
            }
            throw th;
        }
    }

    public final void a(TN tn) {
        tn.getClass();
        this.b |= 1;
        this.c = tn;
    }

    public final void a(long j) {
        this.b |= 2;
        this.d = j;
    }

    public final void a(float f) {
        this.b |= 4;
        this.e = f;
    }

    public final void a(double d) {
        this.b |= 8;
        this.f = d;
    }

    public final void a(XN xn) {
        this.j = xn;
        this.b |= 128;
    }

    public final void a(SN sn) {
        if ((this.b & 256) != 256) {
            this.k = new ArrayList(this.k);
            this.b |= 256;
        }
        List list = this.k;
        UN unG = sn.g();
        if (unG.a()) {
            list.add(unG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(int i) {
        this.b |= 512;
        this.l = i;
    }
}
