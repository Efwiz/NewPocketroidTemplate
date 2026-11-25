package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1761cO extends AbstractC0958Ej {
    private static final C1761cO J;
    public static InterfaceC3429yL K = new ZN();
    private int A;
    private List B;
    private List C;
    private int D;
    private C2075gP E;
    private List F;
    private C2919rP G;
    private byte H;
    private int I;
    private final Y5 b;
    private int c;
    private int d;
    private int e;
    private int f;
    private List g;
    private List h;
    private List i;
    private int j;
    private List k;
    private int l;
    private List m;
    private List n;
    private int o;
    private List p;
    private List q;
    private List r;
    private List s;
    private List t;
    private List u;
    private int v;
    private int w;
    private WO x;
    private int y;
    private List z;

    static {
        C1761cO c1761cO = new C1761cO();
        J = c1761cO;
        c1761cO.O();
    }

    /* synthetic */ C1761cO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    private void O() {
        this.d = 6;
        this.e = 0;
        this.f = 0;
        this.g = Collections.emptyList();
        this.h = Collections.emptyList();
        this.i = Collections.emptyList();
        this.k = Collections.emptyList();
        this.m = Collections.emptyList();
        this.n = Collections.emptyList();
        this.p = Collections.emptyList();
        this.q = Collections.emptyList();
        this.r = Collections.emptyList();
        this.s = Collections.emptyList();
        this.t = Collections.emptyList();
        this.u = Collections.emptyList();
        this.w = 0;
        this.x = WO.o();
        this.y = 0;
        this.z = Collections.emptyList();
        this.B = Collections.emptyList();
        this.C = Collections.emptyList();
        this.E = C2075gP.g();
        this.F = Collections.emptyList();
        this.G = C2919rP.g();
    }

    public static C1610aO P() {
        return C1610aO.f();
    }

    public final List A() {
        return this.h;
    }

    public final List B() {
        return this.s;
    }

    public final List C() {
        return this.g;
    }

    public final C2075gP D() {
        return this.E;
    }

    public final List E() {
        return this.F;
    }

    public final C2919rP F() {
        return this.G;
    }

    public final boolean G() {
        return (this.c & 4) == 4;
    }

    public final boolean H() {
        return (this.c & 1) == 1;
    }

    public final boolean I() {
        return (this.c & 2) == 2;
    }

    public final boolean J() {
        return (this.c & 8) == 8;
    }

    public final boolean K() {
        return (this.c & 16) == 16;
    }

    public final boolean L() {
        return (this.c & 32) == 32;
    }

    public final boolean M() {
        return (this.c & 64) == 64;
    }

    public final boolean N() {
        return (this.c & 128) == 128;
    }

    public final int r() {
        return this.e;
    }

    public final List s() {
        return this.q;
    }

    public final int t() {
        return this.w;
    }

    public final WO u() {
        return this.x;
    }

    public final int v() {
        return this.y;
    }

    public final List w() {
        return this.k;
    }

    public final List x() {
        return this.r;
    }

    public final List y() {
        return this.u;
    }

    public final List z() {
        return this.i;
    }

    /* synthetic */ C1761cO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    public final int q() {
        return this.d;
    }

    private C1761cO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.j = -1;
        this.l = -1;
        this.o = -1;
        this.v = -1;
        this.A = -1;
        this.D = -1;
        this.H = (byte) -1;
        this.I = -1;
        this.b = abstractC0899Cj.e();
    }

    public static C1761cO o() {
        return J;
    }

    public final int k() {
        return this.f;
    }

    public final List l() {
        return this.p;
    }

    public final List m() {
        return this.n;
    }

    public final List n() {
        return this.m;
    }

    public final List p() {
        return this.t;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return J;
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.I;
        if (i != -1) {
            return i;
        }
        int iB = (this.c & 1) == 1 ? C2547mb.b(1, this.d) : 0;
        int iA = 0;
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            iA = YN.a((Integer) this.i.get(i2), iA);
        }
        int iA2 = iB + iA;
        if (!this.i.isEmpty()) {
            iA2 = iA2 + 1 + C2547mb.a(iA);
        }
        this.j = iA;
        if ((this.c & 2) == 2) {
            iA2 += C2547mb.b(3, this.e);
        }
        if ((this.c & 4) == 4) {
            iA2 += C2547mb.b(4, this.f);
        }
        for (int i3 = 0; i3 < this.g.size(); i3++) {
            iA2 += C2547mb.a(5, (B0) this.g.get(i3));
        }
        for (int i4 = 0; i4 < this.h.size(); i4++) {
            iA2 += C2547mb.a(6, (B0) this.h.get(i4));
        }
        int iA3 = 0;
        for (int i5 = 0; i5 < this.k.size(); i5++) {
            iA3 = YN.a((Integer) this.k.get(i5), iA3);
        }
        int iA4 = iA2 + iA3;
        if (!this.k.isEmpty()) {
            iA4 = iA4 + 1 + C2547mb.a(iA3);
        }
        this.l = iA3;
        for (int i6 = 0; i6 < this.p.size(); i6++) {
            iA4 += C2547mb.a(8, (B0) this.p.get(i6));
        }
        for (int i7 = 0; i7 < this.q.size(); i7++) {
            iA4 += C2547mb.a(9, (B0) this.q.get(i7));
        }
        for (int i8 = 0; i8 < this.r.size(); i8++) {
            iA4 += C2547mb.a(10, (B0) this.r.get(i8));
        }
        for (int i9 = 0; i9 < this.s.size(); i9++) {
            iA4 += C2547mb.a(11, (B0) this.s.get(i9));
        }
        for (int i10 = 0; i10 < this.t.size(); i10++) {
            iA4 += C2547mb.a(13, (B0) this.t.get(i10));
        }
        int iA5 = 0;
        for (int i11 = 0; i11 < this.u.size(); i11++) {
            iA5 = YN.a((Integer) this.u.get(i11), iA5);
        }
        int iA6 = iA4 + iA5;
        if (!this.u.isEmpty()) {
            iA6 = iA6 + 2 + C2547mb.a(iA5);
        }
        this.v = iA5;
        if ((this.c & 8) == 8) {
            iA6 += C2547mb.b(17, this.w);
        }
        if ((this.c & 16) == 16) {
            iA6 += C2547mb.a(18, this.x);
        }
        if ((this.c & 32) == 32) {
            iA6 += C2547mb.b(19, this.y);
        }
        for (int i12 = 0; i12 < this.m.size(); i12++) {
            iA6 += C2547mb.a(20, (B0) this.m.get(i12));
        }
        int iA7 = 0;
        for (int i13 = 0; i13 < this.n.size(); i13++) {
            iA7 = YN.a((Integer) this.n.get(i13), iA7);
        }
        int iA8 = iA6 + iA7;
        if (!this.n.isEmpty()) {
            iA8 = iA8 + 2 + C2547mb.a(iA7);
        }
        this.o = iA7;
        int iA9 = 0;
        for (int i14 = 0; i14 < this.z.size(); i14++) {
            iA9 = YN.a((Integer) this.z.get(i14), iA9);
        }
        int iA10 = iA8 + iA9;
        if (!this.z.isEmpty()) {
            iA10 = iA10 + 2 + C2547mb.a(iA9);
        }
        this.A = iA9;
        for (int i15 = 0; i15 < this.B.size(); i15++) {
            iA10 += C2547mb.a(23, (B0) this.B.get(i15));
        }
        int iA11 = 0;
        for (int i16 = 0; i16 < this.C.size(); i16++) {
            iA11 = YN.a((Integer) this.C.get(i16), iA11);
        }
        int iA12 = iA10 + iA11;
        if (!this.C.isEmpty()) {
            iA12 = iA12 + 2 + C2547mb.a(iA11);
        }
        this.D = iA11;
        if ((this.c & 64) == 64) {
            iA12 += C2547mb.a(30, this.E);
        }
        int iA13 = 0;
        for (int i17 = 0; i17 < this.F.size(); i17++) {
            iA13 = YN.a((Integer) this.F.get(i17), iA13);
        }
        int size = (this.F.size() * 2) + iA12 + iA13;
        if ((this.c & 128) == 128) {
            size += C2547mb.a(32, this.G);
        }
        int size2 = this.b.size() + size + h();
        this.I = size2;
        return size2;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1610aO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1610aO.f().a(this);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.H;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!I()) {
            this.H = (byte) 0;
            return false;
        }
        for (int i = 0; i < this.g.size(); i++) {
            if (!((C1840dP) this.g.get(i)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            if (!((WO) this.h.get(i2)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i3 = 0; i3 < this.m.size(); i3++) {
            if (!((WO) this.m.get(i3)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i4 = 0; i4 < this.p.size(); i4++) {
            if (!((C1998fO) this.p.get(i4)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i5 = 0; i5 < this.q.size(); i5++) {
            if (!((C3354xO) this.q.get(i5)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i6 = 0; i6 < this.r.size(); i6++) {
            if (!((FO) this.r.get(i6)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i7 = 0; i7 < this.s.size(); i7++) {
            if (!((ZO) this.s.get(i7)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        for (int i8 = 0; i8 < this.t.size(); i8++) {
            if (!((C2846qO) this.t.get(i8)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        if (K() && !this.x.a()) {
            this.H = (byte) 0;
            return false;
        }
        for (int i9 = 0; i9 < this.B.size(); i9++) {
            if (!((WO) this.B.get(i9)).a()) {
                this.H = (byte) 0;
                return false;
            }
        }
        if (M() && !this.E.a()) {
            this.H = (byte) 0;
            return false;
        }
        if (!g()) {
            this.H = (byte) 0;
            return false;
        }
        this.H = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        if ((this.c & 1) == 1) {
            c2547mb.d(1, this.d);
        }
        if (this.i.size() > 0) {
            c2547mb.g(18);
            c2547mb.g(this.j);
        }
        for (int i = 0; i < this.i.size(); i++) {
            c2547mb.d(((Integer) this.i.get(i)).intValue());
        }
        if ((this.c & 2) == 2) {
            c2547mb.d(3, this.e);
        }
        if ((this.c & 4) == 4) {
            c2547mb.d(4, this.f);
        }
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            c2547mb.b(5, (B0) this.g.get(i2));
        }
        for (int i3 = 0; i3 < this.h.size(); i3++) {
            c2547mb.b(6, (B0) this.h.get(i3));
        }
        if (this.k.size() > 0) {
            c2547mb.g(58);
            c2547mb.g(this.l);
        }
        for (int i4 = 0; i4 < this.k.size(); i4++) {
            c2547mb.d(((Integer) this.k.get(i4)).intValue());
        }
        for (int i5 = 0; i5 < this.p.size(); i5++) {
            c2547mb.b(8, (B0) this.p.get(i5));
        }
        for (int i6 = 0; i6 < this.q.size(); i6++) {
            c2547mb.b(9, (B0) this.q.get(i6));
        }
        for (int i7 = 0; i7 < this.r.size(); i7++) {
            c2547mb.b(10, (B0) this.r.get(i7));
        }
        for (int i8 = 0; i8 < this.s.size(); i8++) {
            c2547mb.b(11, (B0) this.s.get(i8));
        }
        for (int i9 = 0; i9 < this.t.size(); i9++) {
            c2547mb.b(13, (B0) this.t.get(i9));
        }
        if (this.u.size() > 0) {
            c2547mb.g(130);
            c2547mb.g(this.v);
        }
        for (int i10 = 0; i10 < this.u.size(); i10++) {
            c2547mb.d(((Integer) this.u.get(i10)).intValue());
        }
        if ((this.c & 8) == 8) {
            c2547mb.d(17, this.w);
        }
        if ((this.c & 16) == 16) {
            c2547mb.b(18, this.x);
        }
        if ((this.c & 32) == 32) {
            c2547mb.d(19, this.y);
        }
        for (int i11 = 0; i11 < this.m.size(); i11++) {
            c2547mb.b(20, (B0) this.m.get(i11));
        }
        if (this.n.size() > 0) {
            c2547mb.g(170);
            c2547mb.g(this.o);
        }
        for (int i12 = 0; i12 < this.n.size(); i12++) {
            c2547mb.d(((Integer) this.n.get(i12)).intValue());
        }
        if (this.z.size() > 0) {
            c2547mb.g(178);
            c2547mb.g(this.A);
        }
        for (int i13 = 0; i13 < this.z.size(); i13++) {
            c2547mb.d(((Integer) this.z.get(i13)).intValue());
        }
        for (int i14 = 0; i14 < this.B.size(); i14++) {
            c2547mb.b(23, (B0) this.B.get(i14));
        }
        if (this.C.size() > 0) {
            c2547mb.g(194);
            c2547mb.g(this.D);
        }
        for (int i15 = 0; i15 < this.C.size(); i15++) {
            c2547mb.d(((Integer) this.C.get(i15)).intValue());
        }
        if ((this.c & 64) == 64) {
            c2547mb.b(30, this.E);
        }
        for (int i16 = 0; i16 < this.F.size(); i16++) {
            c2547mb.d(31, ((Integer) this.F.get(i16)).intValue());
        }
        if ((this.c & 128) == 128) {
            c2547mb.b(32, this.G);
        }
        c0927DjJ.a(19000, c2547mb);
        c2547mb.b(this.b);
    }

    private C1761cO() {
        this.j = -1;
        this.l = -1;
        this.o = -1;
        this.v = -1;
        this.A = -1;
        this.D = -1;
        this.H = (byte) -1;
        this.I = -1;
        this.b = Y5.a;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x003c. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    private C1761cO(C2396kb c2396kb, C2867qh c2867qh) {
        boolean z;
        this.j = -1;
        this.l = -1;
        this.o = -1;
        this.v = -1;
        this.A = -1;
        this.D = -1;
        this.H = (byte) -1;
        this.I = -1;
        O();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z2 = false;
        int i = 0;
        while (!z2) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        switch (iN) {
                            case 0:
                                z = true;
                                z2 = z;
                                break;
                            case 8:
                                z = true;
                                this.c |= 1;
                                this.d = c2396kb.g();
                                break;
                            case 16:
                                if ((i & 32) != 32) {
                                    this.i = new ArrayList();
                                    i |= 32;
                                }
                                this.i.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 18:
                                int iC = c2396kb.c(c2396kb.j());
                                if ((i & 32) != 32 && c2396kb.a() > 0) {
                                    this.i = new ArrayList();
                                    i |= 32;
                                }
                                while (c2396kb.a() > 0) {
                                    this.i.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC);
                                break;
                            case 24:
                                this.c |= 2;
                                this.e = c2396kb.g();
                                z = true;
                                break;
                            case 32:
                                this.c |= 4;
                                this.f = c2396kb.g();
                                z = true;
                                break;
                            case 42:
                                if ((i & 8) != 8) {
                                    this.g = new ArrayList();
                                    i |= 8;
                                }
                                this.g.add(c2396kb.a((AbstractC1732c1) C1840dP.n, c2867qh));
                                break;
                            case 50:
                                if ((i & 16) != 16) {
                                    this.h = new ArrayList();
                                    i |= 16;
                                }
                                this.h.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                                break;
                            case 56:
                                if ((i & 64) != 64) {
                                    this.k = new ArrayList();
                                    i |= 64;
                                }
                                this.k.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 58:
                                int iC2 = c2396kb.c(c2396kb.j());
                                if ((i & 64) != 64 && c2396kb.a() > 0) {
                                    this.k = new ArrayList();
                                    i |= 64;
                                }
                                while (c2396kb.a() > 0) {
                                    this.k.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC2);
                                break;
                            case 66:
                                if ((i & 512) != 512) {
                                    this.p = new ArrayList();
                                    i |= 512;
                                }
                                this.p.add(c2396kb.a((AbstractC1732c1) C1998fO.j, c2867qh));
                                break;
                            case 74:
                                if ((i & 1024) != 1024) {
                                    this.q = new ArrayList();
                                    i |= 1024;
                                }
                                this.q.add(c2396kb.a((AbstractC1732c1) C3354xO.v, c2867qh));
                                break;
                            case 82:
                                if ((i & 2048) != 2048) {
                                    this.r = new ArrayList();
                                    i |= 2048;
                                }
                                this.r.add(c2396kb.a((AbstractC1732c1) FO.v, c2867qh));
                                break;
                            case 90:
                                if ((i & 4096) != 4096) {
                                    this.s = new ArrayList();
                                    i |= 4096;
                                }
                                this.s.add(c2396kb.a((AbstractC1732c1) ZO.p, c2867qh));
                                break;
                            case 106:
                                if ((i & 8192) != 8192) {
                                    this.t = new ArrayList();
                                    i |= 8192;
                                }
                                this.t.add(c2396kb.a((AbstractC1732c1) C2846qO.h, c2867qh));
                                break;
                            case 128:
                                if ((i & 16384) != 16384) {
                                    this.u = new ArrayList();
                                    i |= 16384;
                                }
                                this.u.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 130:
                                int iC3 = c2396kb.c(c2396kb.j());
                                if ((i & 16384) != 16384 && c2396kb.a() > 0) {
                                    this.u = new ArrayList();
                                    i |= 16384;
                                }
                                while (c2396kb.a() > 0) {
                                    this.u.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC3);
                                break;
                            case 136:
                                this.c |= 8;
                                this.w = c2396kb.g();
                                z = true;
                                break;
                            case 146:
                                VO voO = (this.c & 16) == 16 ? this.x.O() : null;
                                WO wo = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                                this.x = wo;
                                if (voO != 0) {
                                    voO.a(wo);
                                    this.x = voO.h();
                                }
                                this.c |= 16;
                                z = true;
                                break;
                            case 152:
                                this.c |= 32;
                                this.y = c2396kb.g();
                                z = true;
                                break;
                            case 162:
                                if ((i & 128) != 128) {
                                    this.m = new ArrayList();
                                    i |= 128;
                                }
                                this.m.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                                break;
                            case 168:
                                if ((i & 256) != 256) {
                                    this.n = new ArrayList();
                                    i |= 256;
                                }
                                this.n.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 170:
                                int iC4 = c2396kb.c(c2396kb.j());
                                if ((i & 256) != 256 && c2396kb.a() > 0) {
                                    this.n = new ArrayList();
                                    i |= 256;
                                }
                                while (c2396kb.a() > 0) {
                                    this.n.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC4);
                                break;
                            case 176:
                                if ((i & 262144) != 262144) {
                                    this.z = new ArrayList();
                                    i |= 262144;
                                }
                                this.z.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 178:
                                int iC5 = c2396kb.c(c2396kb.j());
                                if ((i & 262144) != 262144 && c2396kb.a() > 0) {
                                    this.z = new ArrayList();
                                    i |= 262144;
                                }
                                while (c2396kb.a() > 0) {
                                    this.z.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC5);
                                break;
                            case 186:
                                if ((i & 524288) != 524288) {
                                    this.B = new ArrayList();
                                    i |= 524288;
                                }
                                this.B.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                                break;
                            case 192:
                                if ((i & 1048576) != 1048576) {
                                    this.C = new ArrayList();
                                    i |= 1048576;
                                }
                                this.C.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 194:
                                int iC6 = c2396kb.c(c2396kb.j());
                                if ((i & 1048576) != 1048576 && c2396kb.a() > 0) {
                                    this.C = new ArrayList();
                                    i |= 1048576;
                                }
                                while (c2396kb.a() > 0) {
                                    this.C.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC6);
                                break;
                            case 242:
                                C1999fP c1999fPL = (this.c & 64) == 64 ? this.E.l() : null;
                                C2075gP c2075gP = (C2075gP) c2396kb.a((AbstractC1732c1) C2075gP.h, c2867qh);
                                this.E = c2075gP;
                                if (c1999fPL != 0) {
                                    c1999fPL.a(c2075gP);
                                    this.E = c1999fPL.g();
                                }
                                this.c |= 64;
                                z = true;
                                break;
                            case 248:
                                if ((i & 4194304) != 4194304) {
                                    this.F = new ArrayList();
                                    i |= 4194304;
                                }
                                this.F.add(Integer.valueOf(c2396kb.g()));
                                break;
                            case 250:
                                int iC7 = c2396kb.c(c2396kb.j());
                                if ((i & 4194304) != 4194304 && c2396kb.a() > 0) {
                                    this.F = new ArrayList();
                                    i |= 4194304;
                                }
                                while (c2396kb.a() > 0) {
                                    this.F.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC7);
                                break;
                            case 258:
                                C2847qP c2847qPL = (this.c & 128) == 128 ? this.G.l() : null;
                                C2919rP c2919rP = (C2919rP) c2396kb.a((AbstractC1732c1) C2919rP.f, c2867qh);
                                this.G = c2919rP;
                                if (c2847qPL != 0) {
                                    c2847qPL.a(c2919rP);
                                    this.G = c2847qPL.g();
                                }
                                this.c |= 128;
                                z = true;
                                break;
                            default:
                                z = true;
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                    z2 = z;
                                }
                                break;
                        }
                    } catch (IOException e) {
                        throw new C1054Hs(e.getMessage()).a(this);
                    }
                } catch (C1054Hs e2) {
                    throw e2.a(this);
                }
            } catch (Throwable th) {
                if ((i & 32) == 32) {
                    this.i = Collections.unmodifiableList(this.i);
                }
                if ((i & 8) == 8) {
                    this.g = Collections.unmodifiableList(this.g);
                }
                if ((i & 16) == 16) {
                    this.h = Collections.unmodifiableList(this.h);
                }
                if ((i & 64) == 64) {
                    this.k = Collections.unmodifiableList(this.k);
                }
                if ((i & 512) == 512) {
                    this.p = Collections.unmodifiableList(this.p);
                }
                if ((i & 1024) == 1024) {
                    this.q = Collections.unmodifiableList(this.q);
                }
                if ((i & 2048) == 2048) {
                    this.r = Collections.unmodifiableList(this.r);
                }
                if ((i & 4096) == 4096) {
                    this.s = Collections.unmodifiableList(this.s);
                }
                if ((i & 8192) == 8192) {
                    this.t = Collections.unmodifiableList(this.t);
                }
                if ((i & 16384) == 16384) {
                    this.u = Collections.unmodifiableList(this.u);
                }
                if ((i & 128) == 128) {
                    this.m = Collections.unmodifiableList(this.m);
                }
                if ((i & 256) == 256) {
                    this.n = Collections.unmodifiableList(this.n);
                }
                if ((i & 262144) == 262144) {
                    this.z = Collections.unmodifiableList(this.z);
                }
                if ((i & 524288) == 524288) {
                    this.B = Collections.unmodifiableList(this.B);
                }
                if ((i & 1048576) == 1048576) {
                    this.C = Collections.unmodifiableList(this.C);
                }
                if ((i & 4194304) == 4194304) {
                    this.F = Collections.unmodifiableList(this.F);
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
        if ((i & 32) == 32) {
            this.i = Collections.unmodifiableList(this.i);
        }
        if ((i & 8) == 8) {
            this.g = Collections.unmodifiableList(this.g);
        }
        if ((i & 16) == 16) {
            this.h = Collections.unmodifiableList(this.h);
        }
        if ((i & 64) == 64) {
            this.k = Collections.unmodifiableList(this.k);
        }
        if ((i & 512) == 512) {
            this.p = Collections.unmodifiableList(this.p);
        }
        if ((i & 1024) == 1024) {
            this.q = Collections.unmodifiableList(this.q);
        }
        if ((i & 2048) == 2048) {
            this.r = Collections.unmodifiableList(this.r);
        }
        if ((i & 4096) == 4096) {
            this.s = Collections.unmodifiableList(this.s);
        }
        if ((i & 8192) == 8192) {
            this.t = Collections.unmodifiableList(this.t);
        }
        if ((i & 16384) == 16384) {
            this.u = Collections.unmodifiableList(this.u);
        }
        if ((i & 128) == 128) {
            this.m = Collections.unmodifiableList(this.m);
        }
        if ((i & 256) == 256) {
            this.n = Collections.unmodifiableList(this.n);
        }
        if ((i & 262144) == 262144) {
            this.z = Collections.unmodifiableList(this.z);
        }
        if ((i & 524288) == 524288) {
            this.B = Collections.unmodifiableList(this.B);
        }
        if ((i & 1048576) == 1048576) {
            this.C = Collections.unmodifiableList(this.C);
        }
        if ((i & 4194304) == 4194304) {
            this.F = Collections.unmodifiableList(this.F);
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
