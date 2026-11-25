package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UN extends AbstractC1045Hj {
    private static final UN p;
    public static InterfaceC3429yL q = new RN();
    private final Y5 a;
    private int b;
    private TN c;
    private long d;
    private float e;
    private double f;
    private int g;
    private int h;
    private int i;
    private XN j;
    private List k;
    private int l;
    private int m;
    private byte n;
    private int o;

    static {
        UN un = new UN();
        p = un;
        un.C();
    }

    /* synthetic */ UN(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    private void C() {
        this.c = TN.b;
        this.d = 0L;
        this.e = 0.0f;
        this.f = 0.0d;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = XN.h();
        this.k = Collections.emptyList();
        this.l = 0;
        this.m = 0;
    }

    public static SN D() {
        return SN.f();
    }

    public static UN k() {
        return p;
    }

    public final boolean A() {
        return (this.b & 16) == 16;
    }

    public final boolean B() {
        return (this.b & 1) == 1;
    }

    public final SN E() {
        return SN.f().a(this);
    }

    public final XN g() {
        return this.j;
    }

    public final int h() {
        return this.l;
    }

    public final List i() {
        return this.k;
    }

    public final int j() {
        return this.h;
    }

    public final double l() {
        return this.f;
    }

    public final int m() {
        return this.i;
    }

    public final int n() {
        return this.m;
    }

    public final float o() {
        return this.e;
    }

    public final long p() {
        return this.d;
    }

    public final int q() {
        return this.g;
    }

    public final TN r() {
        return this.c;
    }

    public final boolean s() {
        return (this.b & 128) == 128;
    }

    public final boolean t() {
        return (this.b & 256) == 256;
    }

    public final boolean u() {
        return (this.b & 32) == 32;
    }

    public final boolean v() {
        return (this.b & 8) == 8;
    }

    public final boolean w() {
        return (this.b & 64) == 64;
    }

    public final boolean x() {
        return (this.b & 512) == 512;
    }

    public final boolean y() {
        return (this.b & 4) == 4;
    }

    public final boolean z() {
        return (this.b & 2) == 2;
    }

    /* synthetic */ UN(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.o;
        if (i != -1) {
            return i;
        }
        int iA = (this.b & 1) == 1 ? C2547mb.a(1, this.c.a()) : 0;
        if ((this.b & 2) == 2) {
            iA += C2547mb.a(C2547mb.b(this.d)) + C2547mb.c(2);
        }
        if ((this.b & 4) == 4) {
            iA += C2547mb.c(3) + 4;
        }
        if ((this.b & 8) == 8) {
            iA += C2547mb.c(4) + 8;
        }
        if ((this.b & 16) == 16) {
            iA += C2547mb.b(5, this.g);
        }
        if ((this.b & 32) == 32) {
            iA += C2547mb.b(6, this.h);
        }
        if ((this.b & 64) == 64) {
            iA += C2547mb.b(7, this.i);
        }
        if ((this.b & 128) == 128) {
            iA += C2547mb.a(8, this.j);
        }
        for (int i2 = 0; i2 < this.k.size(); i2++) {
            iA += C2547mb.a(9, (B0) this.k.get(i2));
        }
        if ((this.b & 512) == 512) {
            iA += C2547mb.b(10, this.m);
        }
        if ((this.b & 256) == 256) {
            iA += C2547mb.b(11, this.l);
        }
        int size = this.a.size() + iA;
        this.o = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return SN.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return SN.f().a(this);
    }

    private UN(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.n = (byte) -1;
        this.o = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.n;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (s() && !this.j.a()) {
            this.n = (byte) 0;
            return false;
        }
        for (int i = 0; i < this.k.size(); i++) {
            if (!((UN) this.k.get(i)).a()) {
                this.n = (byte) 0;
                return false;
            }
        }
        this.n = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        if ((this.b & 1) == 1) {
            c2547mb.c(1, this.c.a());
        }
        if ((this.b & 2) == 2) {
            c2547mb.e(this.d);
        }
        if ((this.b & 4) == 4) {
            c2547mb.a(this.e);
        }
        if ((this.b & 8) == 8) {
            c2547mb.a(this.f);
        }
        if ((this.b & 16) == 16) {
            c2547mb.d(5, this.g);
        }
        if ((this.b & 32) == 32) {
            c2547mb.d(6, this.h);
        }
        if ((this.b & 64) == 64) {
            c2547mb.d(7, this.i);
        }
        if ((this.b & 128) == 128) {
            c2547mb.b(8, this.j);
        }
        for (int i = 0; i < this.k.size(); i++) {
            c2547mb.b(9, (B0) this.k.get(i));
        }
        if ((this.b & 512) == 512) {
            c2547mb.d(10, this.m);
        }
        if ((this.b & 256) == 256) {
            c2547mb.d(11, this.l);
        }
        c2547mb.b(this.a);
    }

    private UN() {
        this.n = (byte) -1;
        this.o = -1;
        this.a = Y5.a;
    }

    private UN(C2396kb c2396kb, C2867qh c2867qh) {
        this.n = (byte) -1;
        this.o = -1;
        C();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        char c = 0;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    switch (iN) {
                        case 0:
                            break;
                        case 8:
                            int iE = c2396kb.e();
                            TN tnC = TN.c(iE);
                            if (tnC == null) {
                                c2547mbA.g(iN);
                                c2547mbA.g(iE);
                            } else {
                                this.b |= 1;
                                this.c = tnC;
                                continue;
                            }
                            break;
                        case 16:
                            this.b |= 2;
                            this.d = c2396kb.l();
                            continue;
                        case 29:
                            this.b |= 4;
                            this.e = c2396kb.f();
                            continue;
                        case 33:
                            this.b |= 8;
                            this.f = c2396kb.d();
                            continue;
                        case 40:
                            this.b |= 16;
                            this.g = c2396kb.g();
                            continue;
                        case 48:
                            this.b |= 32;
                            this.h = c2396kb.g();
                            continue;
                        case 56:
                            this.b |= 64;
                            this.i = c2396kb.g();
                            continue;
                        case 66:
                            WN wnM = (this.b & 128) == 128 ? this.j.m() : null;
                            XN xn = (XN) c2396kb.a((AbstractC1732c1) XN.h, c2867qh);
                            this.j = xn;
                            if (wnM != null) {
                                wnM.a(xn);
                                this.j = wnM.g();
                            }
                            this.b |= 128;
                            continue;
                        case 74:
                            if ((c & 256) != 256) {
                                this.k = new ArrayList();
                                c = 256;
                            }
                            this.k.add(c2396kb.a((AbstractC1732c1) q, c2867qh));
                            continue;
                        case 80:
                            this.b |= 512;
                            this.m = c2396kb.g();
                            continue;
                        case 88:
                            this.b |= 256;
                            this.l = c2396kb.g();
                            continue;
                        default:
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                break;
                            }
                            break;
                    }
                    z = true;
                } catch (C1054Hs e) {
                    throw e.a(this);
                } catch (IOException e2) {
                    throw new C1054Hs(e2.getMessage()).a(this);
                }
            } catch (Throwable th) {
                if ((c & 256) == 256) {
                    this.k = Collections.unmodifiableList(this.k);
                }
                try {
                    c2547mbA.b();
                } catch (IOException unused) {
                } finally {
                    this.a = x5D.c();
                }
                throw th;
            }
        }
        if ((c & 256) == 256) {
            this.k = Collections.unmodifiableList(this.k);
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
