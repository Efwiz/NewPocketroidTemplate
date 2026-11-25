package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WO extends AbstractC0958Ej {
    private static final WO t;
    public static InterfaceC3429yL u = new QO();
    private final Y5 b;
    private int c;
    private List d;
    private boolean e;
    private int f;
    private WO g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private WO m;
    private int n;
    private WO o;
    private int p;
    private int q;
    private byte r;
    private int s;

    static {
        WO wo = new WO();
        t = wo;
        wo.M();
    }

    /* synthetic */ WO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    private void M() {
        this.d = Collections.emptyList();
        this.e = false;
        this.f = 0;
        WO wo = t;
        this.g = wo;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = wo;
        this.n = 0;
        this.o = wo;
        this.p = 0;
        this.q = 0;
    }

    public static VO N() {
        return VO.f();
    }

    public static WO o() {
        return t;
    }

    public final boolean A() {
        return (this.c & 2048) == 2048;
    }

    public final boolean B() {
        return (this.c & 16) == 16;
    }

    public final boolean C() {
        return (this.c & 4096) == 4096;
    }

    public final boolean D() {
        return (this.c & 2) == 2;
    }

    public final boolean E() {
        return (this.c & 4) == 4;
    }

    public final boolean F() {
        return (this.c & 8) == 8;
    }

    public final boolean G() {
        return (this.c & 1) == 1;
    }

    public final boolean H() {
        return (this.c & 256) == 256;
    }

    public final boolean I() {
        return (this.c & 512) == 512;
    }

    public final boolean J() {
        return (this.c & 128) == 128;
    }

    public final boolean K() {
        return (this.c & 32) == 32;
    }

    public final boolean L() {
        return (this.c & 64) == 64;
    }

    public final VO O() {
        return VO.f().a(this);
    }

    public final WO k() {
        return this.o;
    }

    public final int l() {
        return this.p;
    }

    public final List m() {
        return this.d;
    }

    public final int n() {
        return this.i;
    }

    public final int p() {
        return this.q;
    }

    public final int q() {
        return this.f;
    }

    public final WO r() {
        return this.g;
    }

    public final int s() {
        return this.h;
    }

    public final boolean t() {
        return this.e;
    }

    public final WO u() {
        return this.m;
    }

    public final int v() {
        return this.n;
    }

    public final int w() {
        return this.l;
    }

    public final int x() {
        return this.j;
    }

    public final int y() {
        return this.k;
    }

    public final boolean z() {
        return (this.c & 1024) == 1024;
    }

    /* synthetic */ WO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return VO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return VO.f().a(this);
    }

    private WO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.r = (byte) -1;
        this.s = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.s;
        if (i != -1) {
            return i;
        }
        int iB = (this.c & 4096) == 4096 ? C2547mb.b(1, this.q) : 0;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            iB += C2547mb.a(2, (B0) this.d.get(i2));
        }
        if ((this.c & 1) == 1) {
            iB += C2547mb.c(3) + 1;
        }
        if ((this.c & 2) == 2) {
            iB += C2547mb.b(4, this.f);
        }
        if ((this.c & 4) == 4) {
            iB += C2547mb.a(5, this.g);
        }
        if ((this.c & 16) == 16) {
            iB += C2547mb.b(6, this.i);
        }
        if ((this.c & 32) == 32) {
            iB += C2547mb.b(7, this.j);
        }
        if ((this.c & 8) == 8) {
            iB += C2547mb.b(8, this.h);
        }
        if ((this.c & 64) == 64) {
            iB += C2547mb.b(9, this.k);
        }
        if ((this.c & 256) == 256) {
            iB += C2547mb.a(10, this.m);
        }
        if ((this.c & 512) == 512) {
            iB += C2547mb.b(11, this.n);
        }
        if ((this.c & 128) == 128) {
            iB += C2547mb.b(12, this.l);
        }
        if ((this.c & 1024) == 1024) {
            iB += C2547mb.a(13, this.o);
        }
        if ((this.c & 2048) == 2048) {
            iB += C2547mb.b(14, this.p);
        }
        int size = this.b.size() + iB + h();
        this.s = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return t;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.r;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        for (int i = 0; i < this.d.size(); i++) {
            if (!((UO) this.d.get(i)).a()) {
                this.r = (byte) 0;
                return false;
            }
        }
        if (E() && !this.g.a()) {
            this.r = (byte) 0;
            return false;
        }
        if (H() && !this.m.a()) {
            this.r = (byte) 0;
            return false;
        }
        if (z() && !this.o.a()) {
            this.r = (byte) 0;
            return false;
        }
        if (!g()) {
            this.r = (byte) 0;
            return false;
        }
        this.r = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        if ((this.c & 4096) == 4096) {
            c2547mb.d(1, this.q);
        }
        for (int i = 0; i < this.d.size(); i++) {
            c2547mb.b(2, (B0) this.d.get(i));
        }
        if ((this.c & 1) == 1) {
            c2547mb.a(this.e);
        }
        if ((this.c & 2) == 2) {
            c2547mb.d(4, this.f);
        }
        if ((this.c & 4) == 4) {
            c2547mb.b(5, this.g);
        }
        if ((this.c & 16) == 16) {
            c2547mb.d(6, this.i);
        }
        if ((this.c & 32) == 32) {
            c2547mb.d(7, this.j);
        }
        if ((this.c & 8) == 8) {
            c2547mb.d(8, this.h);
        }
        if ((this.c & 64) == 64) {
            c2547mb.d(9, this.k);
        }
        if ((this.c & 256) == 256) {
            c2547mb.b(10, this.m);
        }
        if ((this.c & 512) == 512) {
            c2547mb.d(11, this.n);
        }
        if ((this.c & 128) == 128) {
            c2547mb.d(12, this.l);
        }
        if ((this.c & 1024) == 1024) {
            c2547mb.b(13, this.o);
        }
        if ((this.c & 2048) == 2048) {
            c2547mb.d(14, this.p);
        }
        c0927DjJ.a(200, c2547mb);
        c2547mb.b(this.b);
    }

    private WO() {
        this.r = (byte) -1;
        this.s = -1;
        this.b = Y5.a;
    }

    private WO(C2396kb c2396kb, C2867qh c2867qh) {
        VO voO;
        this.r = (byte) -1;
        this.s = -1;
        M();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    switch (iN) {
                        case 0:
                            break;
                        case 8:
                            this.c |= 4096;
                            this.q = c2396kb.g();
                            continue;
                        case 18:
                            if (!z2) {
                                this.d = new ArrayList();
                                z2 = true;
                            }
                            this.d.add(c2396kb.a((AbstractC1732c1) UO.i, c2867qh));
                            continue;
                        case 24:
                            this.c |= 1;
                            this.e = c2396kb.b();
                            continue;
                        case 32:
                            this.c |= 2;
                            this.f = c2396kb.g();
                            continue;
                        case 42:
                            voO = (this.c & 4) == 4 ? this.g.O() : null;
                            WO wo = (WO) c2396kb.a((AbstractC1732c1) u, c2867qh);
                            this.g = wo;
                            if (voO != null) {
                                voO.a(wo);
                                this.g = voO.h();
                            }
                            this.c |= 4;
                            continue;
                        case 48:
                            this.c |= 16;
                            this.i = c2396kb.g();
                            continue;
                        case 56:
                            this.c |= 32;
                            this.j = c2396kb.g();
                            continue;
                        case 64:
                            this.c |= 8;
                            this.h = c2396kb.g();
                            continue;
                        case 72:
                            this.c |= 64;
                            this.k = c2396kb.g();
                            continue;
                        case 82:
                            voO = (this.c & 256) == 256 ? this.m.O() : null;
                            WO wo2 = (WO) c2396kb.a((AbstractC1732c1) u, c2867qh);
                            this.m = wo2;
                            if (voO != null) {
                                voO.a(wo2);
                                this.m = voO.h();
                            }
                            this.c |= 256;
                            continue;
                        case 88:
                            this.c |= 512;
                            this.n = c2396kb.g();
                            continue;
                        case 96:
                            this.c |= 128;
                            this.l = c2396kb.g();
                            continue;
                        case 106:
                            voO = (this.c & 1024) == 1024 ? this.o.O() : null;
                            WO wo3 = (WO) c2396kb.a((AbstractC1732c1) u, c2867qh);
                            this.o = wo3;
                            if (voO != null) {
                                voO.a(wo3);
                                this.o = voO.h();
                            }
                            this.c |= 1024;
                            continue;
                        case 112:
                            this.c |= 2048;
                            this.p = c2396kb.g();
                            continue;
                        default:
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                break;
                            }
                            break;
                    }
                    z = true;
                } catch (Throwable th) {
                    if (z2) {
                        this.d = Collections.unmodifiableList(this.d);
                    }
                    try {
                        c2547mbA.b();
                    } catch (IOException unused) {
                    } finally {
                        this.b = x5D.c();
                    }
                    i();
                    throw th;
                }
            } catch (C1054Hs e) {
                throw e.a(this);
            } catch (IOException e2) {
                throw new C1054Hs(e2.getMessage()).a(this);
            }
        }
        if (z2) {
            this.d = Collections.unmodifiableList(this.d);
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.b = x5D.c();
        }
        i();
    }
}
