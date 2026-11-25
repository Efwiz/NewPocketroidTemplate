package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3354xO extends AbstractC0958Ej {
    private static final C3354xO u;
    public static InterfaceC3429yL v = new C3207vO();
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
    private List o;
    private C2075gP p;
    private List q;
    private C2232iO r;
    private byte s;
    private int t;

    static {
        C3354xO c3354xO = new C3354xO();
        u = c3354xO;
        c3354xO.I();
    }

    /* synthetic */ C3354xO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    private void I() {
        this.d = 6;
        this.e = 6;
        this.f = 0;
        this.g = WO.o();
        this.h = 0;
        this.i = Collections.emptyList();
        this.j = WO.o();
        this.k = 0;
        this.l = Collections.emptyList();
        this.m = Collections.emptyList();
        this.o = Collections.emptyList();
        this.p = C2075gP.g();
        this.q = Collections.emptyList();
        this.r = C2232iO.g();
    }

    public static C3285wO J() {
        return C3285wO.f();
    }

    public static C3354xO n() {
        return u;
    }

    public final boolean A() {
        return (this.c & 1) == 1;
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
        return (this.c & 128) == 128;
    }

    public final List k() {
        return this.m;
    }

    public final List l() {
        return this.l;
    }

    public final C2232iO m() {
        return this.r;
    }

    public final int o() {
        return this.d;
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

    public final List v() {
        return this.i;
    }

    public final C2075gP w() {
        return this.p;
    }

    public final List x() {
        return this.o;
    }

    public final List y() {
        return this.q;
    }

    public final boolean z() {
        return (this.c & 256) == 256;
    }

    /* synthetic */ C3354xO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    private C3354xO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.n = -1;
        this.s = (byte) -1;
        this.t = -1;
        this.b = abstractC0899Cj.e();
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
        for (int i3 = 0; i3 < this.o.size(); i3++) {
            iB += C2547mb.a(6, (B0) this.o.get(i3));
        }
        if ((this.c & 16) == 16) {
            iB += C2547mb.b(7, this.h);
        }
        if ((this.c & 64) == 64) {
            iB += C2547mb.b(8, this.k);
        }
        if ((this.c & 1) == 1) {
            iB += C2547mb.b(9, this.d);
        }
        for (int i4 = 0; i4 < this.l.size(); i4++) {
            iB += C2547mb.a(10, (B0) this.l.get(i4));
        }
        int iA = 0;
        for (int i5 = 0; i5 < this.m.size(); i5++) {
            iA = YN.a((Integer) this.m.get(i5), iA);
        }
        int iA2 = iB + iA;
        if (!this.m.isEmpty()) {
            iA2 = iA2 + 1 + C2547mb.a(iA);
        }
        this.n = iA;
        if ((this.c & 128) == 128) {
            iA2 += C2547mb.a(30, this.p);
        }
        int iA3 = 0;
        for (int i6 = 0; i6 < this.q.size(); i6++) {
            iA3 = YN.a((Integer) this.q.get(i6), iA3);
        }
        int size = (this.q.size() * 2) + iA2 + iA3;
        if ((this.c & 256) == 256) {
            size += C2547mb.a(32, this.r);
        }
        int size2 = this.b.size() + size + h();
        this.t = size2;
        return size2;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C3285wO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C3285wO.f().a(this);
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
        for (int i3 = 0; i3 < this.o.size(); i3++) {
            if (!((C2303jP) this.o.get(i3)).a()) {
                this.s = (byte) 0;
                return false;
            }
        }
        if (H() && !this.p.a()) {
            this.s = (byte) 0;
            return false;
        }
        if (z() && !this.r.a()) {
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
        for (int i2 = 0; i2 < this.o.size(); i2++) {
            c2547mb.b(6, (B0) this.o.get(i2));
        }
        if ((this.c & 16) == 16) {
            c2547mb.d(7, this.h);
        }
        if ((this.c & 64) == 64) {
            c2547mb.d(8, this.k);
        }
        if ((this.c & 1) == 1) {
            c2547mb.d(9, this.d);
        }
        for (int i3 = 0; i3 < this.l.size(); i3++) {
            c2547mb.b(10, (B0) this.l.get(i3));
        }
        if (this.m.size() > 0) {
            c2547mb.g(90);
            c2547mb.g(this.n);
        }
        for (int i4 = 0; i4 < this.m.size(); i4++) {
            c2547mb.d(((Integer) this.m.get(i4)).intValue());
        }
        if ((this.c & 128) == 128) {
            c2547mb.b(30, this.p);
        }
        for (int i5 = 0; i5 < this.q.size(); i5++) {
            c2547mb.d(31, ((Integer) this.q.get(i5)).intValue());
        }
        if ((this.c & 256) == 256) {
            c2547mb.b(32, this.r);
        }
        c0927DjJ.a(19000, c2547mb);
        c2547mb.b(this.b);
    }

    private C3354xO() {
        this.n = -1;
        this.s = (byte) -1;
        this.t = -1;
        this.b = Y5.a;
    }

    private C3354xO(C2396kb c2396kb, C2867qh c2867qh) {
        this.n = -1;
        this.s = (byte) -1;
        this.t = -1;
        I();
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
                            int i3 = (i == true ? 1 : 0) & 1024;
                            i = i;
                            if (i3 != 1024) {
                                this.o = new ArrayList();
                                i = (i == true ? 1 : 0) | 1024;
                            }
                            this.o.add(c2396kb.a((AbstractC1732c1) C2303jP.m, c2867qh));
                            continue;
                        case 56:
                            this.c |= 16;
                            this.h = c2396kb.g();
                            continue;
                        case 64:
                            this.c |= 64;
                            this.k = c2396kb.g();
                            continue;
                        case 72:
                            this.c |= 1;
                            this.d = c2396kb.g();
                            continue;
                        case 82:
                            int i4 = (i == true ? 1 : 0) & 256;
                            i = i;
                            if (i4 != 256) {
                                this.l = new ArrayList();
                                i = (i == true ? 1 : 0) | 256;
                            }
                            this.l.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                            continue;
                        case 88:
                            int i5 = (i == true ? 1 : 0) & 512;
                            i = i;
                            if (i5 != 512) {
                                this.m = new ArrayList();
                                i = (i == true ? 1 : 0) | 512;
                            }
                            this.m.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 90:
                            int iC = c2396kb.c(c2396kb.j());
                            int i6 = (i == true ? 1 : 0) & 512;
                            i = i;
                            if (i6 != 512 && c2396kb.a() > 0) {
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
                        case 242:
                            C1999fP c1999fPL = (this.c & 128) == 128 ? this.p.l() : null;
                            C2075gP c2075gP = (C2075gP) c2396kb.a((AbstractC1732c1) C2075gP.h, c2867qh);
                            this.p = c2075gP;
                            if (c1999fPL != null) {
                                c1999fPL.a(c2075gP);
                                this.p = c1999fPL.g();
                            }
                            this.c |= 128;
                            continue;
                        case 248:
                            int i7 = (i == true ? 1 : 0) & 4096;
                            i = i;
                            if (i7 != 4096) {
                                this.q = new ArrayList();
                                i = (i == true ? 1 : 0) | 4096;
                            }
                            this.q.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 250:
                            int iC2 = c2396kb.c(c2396kb.j());
                            int i8 = (i == true ? 1 : 0) & 4096;
                            i = i;
                            if (i8 != 4096 && c2396kb.a() > 0) {
                                i = i;
                                this.q = new ArrayList();
                                i = (i == true ? 1 : 0) | 4096;
                            }
                            i = i;
                            while (c2396kb.a() > 0) {
                                this.q.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC2);
                            continue;
                        case 258:
                            C2152hO c2152hOK = (this.c & 256) == 256 ? this.r.k() : null;
                            C2232iO c2232iO = (C2232iO) c2396kb.a((AbstractC1732c1) C2232iO.f, c2867qh);
                            this.r = c2232iO;
                            if (c2152hOK != null) {
                                c2152hOK.a(c2232iO);
                                this.r = c2152hOK.g();
                            }
                            this.c |= 256;
                            continue;
                        default:
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                break;
                            }
                            break;
                    }
                    z = true;
                } catch (Throwable th) {
                    if (((i == true ? 1 : 0) & 32) == 32) {
                        this.i = Collections.unmodifiableList(this.i);
                    }
                    if (((i == true ? 1 : 0) & 1024) == 1024) {
                        this.o = Collections.unmodifiableList(this.o);
                    }
                    if (((i == true ? 1 : 0) & 256) == 256) {
                        this.l = Collections.unmodifiableList(this.l);
                    }
                    if (((i == true ? 1 : 0) & 512) == 512) {
                        this.m = Collections.unmodifiableList(this.m);
                    }
                    if (((i == true ? 1 : 0) & 4096) == 4096) {
                        this.q = Collections.unmodifiableList(this.q);
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
        if (((i == true ? 1 : 0) & 32) == 32) {
            this.i = Collections.unmodifiableList(this.i);
        }
        if (((i == true ? 1 : 0) & 1024) == 1024) {
            this.o = Collections.unmodifiableList(this.o);
        }
        if (((i == true ? 1 : 0) & 256) == 256) {
            this.l = Collections.unmodifiableList(this.l);
        }
        if (((i == true ? 1 : 0) & 512) == 512) {
            this.m = Collections.unmodifiableList(this.m);
        }
        if (((i == true ? 1 : 0) & 4096) == 4096) {
            this.q = Collections.unmodifiableList(this.q);
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
