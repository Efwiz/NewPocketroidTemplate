package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FO extends AbstractC0958Ej {
    private static final FO u;
    public static InterfaceC3429yL v = new DO();
    private final Y5 b;
    private int c;
    private int d;
    private int e;
    private int f;
    private WO g;
    private int h;
    private List i;
    private WO j;
    private int k;
    private List l;
    private List m;
    private int n;
    private C2303jP o;
    private int p;
    private int q;
    private List r;
    private byte s;
    private int t;

    static {
        FO fo = new FO();
        u = fo;
        fo.J();
    }

    /* synthetic */ FO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    private void J() {
        this.d = 518;
        this.e = 2054;
        this.f = 0;
        this.g = WO.o();
        this.h = 0;
        this.i = Collections.emptyList();
        this.j = WO.o();
        this.k = 0;
        this.l = Collections.emptyList();
        this.m = Collections.emptyList();
        this.o = C2303jP.k();
        this.p = 0;
        this.q = 0;
        this.r = Collections.emptyList();
    }

    public static EO K() {
        return EO.f();
    }

    public static FO m() {
        return u;
    }

    public final boolean A() {
        return (this.c & 256) == 256;
    }

    public final boolean B() {
        return (this.c & 4) == 4;
    }

    public final boolean C() {
        return (this.c & 2) == 2;
    }

    public final boolean D() {
        return (this.c & 32) == 32;
    }

    public final boolean E() {
        return (this.c & 64) == 64;
    }

    public final boolean F() {
        return (this.c & 8) == 8;
    }

    public final boolean G() {
        return (this.c & 16) == 16;
    }

    public final boolean H() {
        return (this.c & 512) == 512;
    }

    public final boolean I() {
        return (this.c & 128) == 128;
    }

    public final List k() {
        return this.m;
    }

    public final List l() {
        return this.l;
    }

    public final int n() {
        return this.d;
    }

    public final int o() {
        return this.p;
    }

    public final int p() {
        return this.f;
    }

    public final int q() {
        return this.e;
    }

    public final WO r() {
        return this.j;
    }

    public final int s() {
        return this.k;
    }

    public final WO t() {
        return this.g;
    }

    public final int u() {
        return this.h;
    }

    public final int v() {
        return this.q;
    }

    public final C2303jP w() {
        return this.o;
    }

    public final List x() {
        return this.i;
    }

    public final List y() {
        return this.r;
    }

    public final boolean z() {
        return (this.c & 1) == 1;
    }

    /* synthetic */ FO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    private FO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.n = -1;
        this.s = (byte) -1;
        this.t = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return EO.f().a(this);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.t;
        if (i != -1) {
            return i;
        }
        int iB = (this.c & 2) == 2 ? C2547mb.b(1, this.e) : 0;
        if ((this.c & 4) == 4) {
            iB += C2547mb.b(2, this.f);
        }
        if ((this.c & 8) == 8) {
            iB += C2547mb.a(3, this.g);
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            iB += C2547mb.a(4, (B0) this.i.get(i2));
        }
        if ((this.c & 32) == 32) {
            iB += C2547mb.a(5, this.j);
        }
        if ((this.c & 128) == 128) {
            iB += C2547mb.a(6, this.o);
        }
        if ((this.c & 256) == 256) {
            iB += C2547mb.b(7, this.p);
        }
        if ((this.c & 512) == 512) {
            iB += C2547mb.b(8, this.q);
        }
        if ((this.c & 16) == 16) {
            iB += C2547mb.b(9, this.h);
        }
        if ((this.c & 64) == 64) {
            iB += C2547mb.b(10, this.k);
        }
        if ((this.c & 1) == 1) {
            iB += C2547mb.b(11, this.d);
        }
        for (int i3 = 0; i3 < this.l.size(); i3++) {
            iB += C2547mb.a(12, (B0) this.l.get(i3));
        }
        int iA = 0;
        for (int i4 = 0; i4 < this.m.size(); i4++) {
            iA = YN.a((Integer) this.m.get(i4), iA);
        }
        int iA2 = iB + iA;
        if (!this.m.isEmpty()) {
            iA2 = iA2 + 1 + C2547mb.a(iA);
        }
        this.n = iA;
        int iA3 = 0;
        for (int i5 = 0; i5 < this.r.size(); i5++) {
            iA3 = YN.a((Integer) this.r.get(i5), iA3);
        }
        int size = this.b.size() + (this.r.size() * 2) + iA2 + iA3 + h();
        this.t = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return EO.f();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return u;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.s;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!B()) {
            this.s = (byte) 0;
            return false;
        }
        if (F() && !this.g.a()) {
            this.s = (byte) 0;
            return false;
        }
        for (int i = 0; i < this.i.size(); i++) {
            if (!((C1840dP) this.i.get(i)).a()) {
                this.s = (byte) 0;
                return false;
            }
        }
        if (D() && !this.j.a()) {
            this.s = (byte) 0;
            return false;
        }
        for (int i2 = 0; i2 < this.l.size(); i2++) {
            if (!((WO) this.l.get(i2)).a()) {
                this.s = (byte) 0;
                return false;
            }
        }
        if (I() && !this.o.a()) {
            this.s = (byte) 0;
            return false;
        }
        if (!g()) {
            this.s = (byte) 0;
            return false;
        }
        this.s = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        if ((this.c & 2) == 2) {
            c2547mb.d(1, this.e);
        }
        if ((this.c & 4) == 4) {
            c2547mb.d(2, this.f);
        }
        if ((this.c & 8) == 8) {
            c2547mb.b(3, this.g);
        }
        for (int i = 0; i < this.i.size(); i++) {
            c2547mb.b(4, (B0) this.i.get(i));
        }
        if ((this.c & 32) == 32) {
            c2547mb.b(5, this.j);
        }
        if ((this.c & 128) == 128) {
            c2547mb.b(6, this.o);
        }
        if ((this.c & 256) == 256) {
            c2547mb.d(7, this.p);
        }
        if ((this.c & 512) == 512) {
            c2547mb.d(8, this.q);
        }
        if ((this.c & 16) == 16) {
            c2547mb.d(9, this.h);
        }
        if ((this.c & 64) == 64) {
            c2547mb.d(10, this.k);
        }
        if ((this.c & 1) == 1) {
            c2547mb.d(11, this.d);
        }
        for (int i2 = 0; i2 < this.l.size(); i2++) {
            c2547mb.b(12, (B0) this.l.get(i2));
        }
        if (this.m.size() > 0) {
            c2547mb.g(106);
            c2547mb.g(this.n);
        }
        for (int i3 = 0; i3 < this.m.size(); i3++) {
            c2547mb.d(((Integer) this.m.get(i3)).intValue());
        }
        for (int i4 = 0; i4 < this.r.size(); i4++) {
            c2547mb.d(31, ((Integer) this.r.get(i4)).intValue());
        }
        c0927DjJ.a(19000, c2547mb);
        c2547mb.b(this.b);
    }

    private FO() {
        this.n = -1;
        this.s = (byte) -1;
        this.t = -1;
        this.b = Y5.a;
    }

    private FO(C2396kb c2396kb, C2867qh c2867qh) {
        this.n = -1;
        this.s = (byte) -1;
        this.t = -1;
        J();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        int i = 0;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    switch (iN) {
                        case 0:
                            break;
                        case 8:
                            this.c |= 2;
                            this.e = c2396kb.g();
                            continue;
                        case 16:
                            this.c |= 4;
                            this.f = c2396kb.g();
                            continue;
                        case 26:
                            VO voO = (this.c & 8) == 8 ? this.g.O() : null;
                            WO wo = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                            this.g = wo;
                            if (voO != null) {
                                voO.a(wo);
                                this.g = voO.h();
                            }
                            this.c |= 8;
                            continue;
                        case 34:
                            int i2 = (i == true ? 1 : 0) & 32;
                            i = i;
                            if (i2 != 32) {
                                this.i = new ArrayList();
                                i = (i == true ? 1 : 0) | 32;
                            }
                            this.i.add(c2396kb.a((AbstractC1732c1) C1840dP.n, c2867qh));
                            continue;
                        case 42:
                            VO voO2 = (this.c & 32) == 32 ? this.j.O() : null;
                            WO wo2 = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                            this.j = wo2;
                            if (voO2 != null) {
                                voO2.a(wo2);
                                this.j = voO2.h();
                            }
                            this.c |= 32;
                            continue;
                        case 50:
                            C2233iP c2233iPZ = (this.c & 128) == 128 ? this.o.z() : null;
                            C2303jP c2303jP = (C2303jP) c2396kb.a((AbstractC1732c1) C2303jP.m, c2867qh);
                            this.o = c2303jP;
                            if (c2233iPZ != null) {
                                c2233iPZ.a(c2303jP);
                                this.o = c2233iPZ.g();
                            }
                            this.c |= 128;
                            continue;
                        case 56:
                            this.c |= 256;
                            this.p = c2396kb.g();
                            continue;
                        case 64:
                            this.c |= 512;
                            this.q = c2396kb.g();
                            continue;
                        case 72:
                            this.c |= 16;
                            this.h = c2396kb.g();
                            continue;
                        case 80:
                            this.c |= 64;
                            this.k = c2396kb.g();
                            continue;
                        case 88:
                            this.c |= 1;
                            this.d = c2396kb.g();
                            continue;
                        case 98:
                            int i3 = (i == true ? 1 : 0) & 256;
                            i = i;
                            if (i3 != 256) {
                                this.l = new ArrayList();
                                i = (i == true ? 1 : 0) | 256;
                            }
                            this.l.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                            continue;
                        case 104:
                            int i4 = (i == true ? 1 : 0) & 512;
                            i = i;
                            if (i4 != 512) {
                                this.m = new ArrayList();
                                i = (i == true ? 1 : 0) | 512;
                            }
                            this.m.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 106:
                            int iC = c2396kb.c(c2396kb.j());
                            int i5 = (i == true ? 1 : 0) & 512;
                            i = i;
                            if (i5 != 512 && c2396kb.a() > 0) {
                                i = i;
                                this.m = new ArrayList();
                                i = (i == true ? 1 : 0) | 512;
                            }
                            i = i;
                            while (c2396kb.a() > 0) {
                                this.m.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC);
                            continue;
                        case 248:
                            int i6 = (i == true ? 1 : 0) & 8192;
                            i = i;
                            if (i6 != 8192) {
                                this.r = new ArrayList();
                                i = (i == true ? 1 : 0) | 8192;
                            }
                            this.r.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 250:
                            int iC2 = c2396kb.c(c2396kb.j());
                            int i7 = (i == true ? 1 : 0) & 8192;
                            i = i;
                            if (i7 != 8192 && c2396kb.a() > 0) {
                                i = i;
                                this.r = new ArrayList();
                                i = (i == true ? 1 : 0) | 8192;
                            }
                            i = i;
                            while (c2396kb.a() > 0) {
                                this.r.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC2);
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
                if (((i == true ? 1 : 0) & 32) == 32) {
                    this.i = Collections.unmodifiableList(this.i);
                }
                if (((i == true ? 1 : 0) & 256) == 256) {
                    this.l = Collections.unmodifiableList(this.l);
                }
                if (((i == true ? 1 : 0) & 512) == 512) {
                    this.m = Collections.unmodifiableList(this.m);
                }
                if (((i == true ? 1 : 0) & 8192) == 8192) {
                    this.r = Collections.unmodifiableList(this.r);
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
        }
        if (((i == true ? 1 : 0) & 32) == 32) {
            this.i = Collections.unmodifiableList(this.i);
        }
        if (((i == true ? 1 : 0) & 256) == 256) {
            this.l = Collections.unmodifiableList(this.l);
        }
        if (((i == true ? 1 : 0) & 512) == 512) {
            this.m = Collections.unmodifiableList(this.m);
        }
        if (((i == true ? 1 : 0) & 8192) == 8192) {
            this.r = Collections.unmodifiableList(this.r);
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
