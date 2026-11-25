package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZO extends AbstractC0958Ej {
    private static final ZO o;
    public static InterfaceC3429yL p = new XO();
    private final Y5 b;
    private int c;
    private int d;
    private int e;
    private List f;
    private WO g;
    private int h;
    private WO i;
    private int j;
    private List k;
    private List l;
    private byte m;
    private int n;

    static {
        ZO zo = new ZO();
        o = zo;
        zo.A();
    }

    /* synthetic */ ZO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    private void A() {
        this.d = 6;
        this.e = 0;
        this.f = Collections.emptyList();
        this.g = WO.o();
        this.h = 0;
        this.i = WO.o();
        this.j = 0;
        this.k = Collections.emptyList();
        this.l = Collections.emptyList();
    }

    public static YO B() {
        return YO.f();
    }

    public static ZO l() {
        return o;
    }

    public final List k() {
        return this.k;
    }

    public final WO m() {
        return this.i;
    }

    public final int n() {
        return this.j;
    }

    public final int o() {
        return this.d;
    }

    public final int p() {
        return this.e;
    }

    public final List q() {
        return this.f;
    }

    public final WO r() {
        return this.g;
    }

    public final int s() {
        return this.h;
    }

    public final List t() {
        return this.l;
    }

    public final boolean u() {
        return (this.c & 16) == 16;
    }

    public final boolean v() {
        return (this.c & 32) == 32;
    }

    public final boolean w() {
        return (this.c & 1) == 1;
    }

    public final boolean x() {
        return (this.c & 2) == 2;
    }

    public final boolean y() {
        return (this.c & 4) == 4;
    }

    public final boolean z() {
        return (this.c & 8) == 8;
    }

    /* synthetic */ ZO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return YO.f().a(this);
    }

    private ZO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.m = (byte) -1;
        this.n = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return YO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.n;
        if (i != -1) {
            return i;
        }
        int iB = (this.c & 1) == 1 ? C2547mb.b(1, this.d) : 0;
        if ((this.c & 2) == 2) {
            iB += C2547mb.b(2, this.e);
        }
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            iB += C2547mb.a(3, (B0) this.f.get(i2));
        }
        if ((this.c & 4) == 4) {
            iB += C2547mb.a(4, this.g);
        }
        if ((this.c & 8) == 8) {
            iB += C2547mb.b(5, this.h);
        }
        if ((this.c & 16) == 16) {
            iB += C2547mb.a(6, this.i);
        }
        if ((this.c & 32) == 32) {
            iB += C2547mb.b(7, this.j);
        }
        for (int i3 = 0; i3 < this.k.size(); i3++) {
            iB += C2547mb.a(8, (B0) this.k.get(i3));
        }
        int iA = 0;
        for (int i4 = 0; i4 < this.l.size(); i4++) {
            iA = YN.a((Integer) this.l.get(i4), iA);
        }
        int size = this.b.size() + (this.l.size() * 2) + iB + iA + h();
        this.n = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.m;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!x()) {
            this.m = (byte) 0;
            return false;
        }
        for (int i = 0; i < this.f.size(); i++) {
            if (!((C1840dP) this.f.get(i)).a()) {
                this.m = (byte) 0;
                return false;
            }
        }
        if (y() && !this.g.a()) {
            this.m = (byte) 0;
            return false;
        }
        if (u() && !this.i.a()) {
            this.m = (byte) 0;
            return false;
        }
        for (int i2 = 0; i2 < this.k.size(); i2++) {
            if (!((XN) this.k.get(i2)).a()) {
                this.m = (byte) 0;
                return false;
            }
        }
        if (!g()) {
            this.m = (byte) 0;
            return false;
        }
        this.m = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return o;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        if ((this.c & 1) == 1) {
            c2547mb.d(1, this.d);
        }
        if ((this.c & 2) == 2) {
            c2547mb.d(2, this.e);
        }
        for (int i = 0; i < this.f.size(); i++) {
            c2547mb.b(3, (B0) this.f.get(i));
        }
        if ((this.c & 4) == 4) {
            c2547mb.b(4, this.g);
        }
        if ((this.c & 8) == 8) {
            c2547mb.d(5, this.h);
        }
        if ((this.c & 16) == 16) {
            c2547mb.b(6, this.i);
        }
        if ((this.c & 32) == 32) {
            c2547mb.d(7, this.j);
        }
        for (int i2 = 0; i2 < this.k.size(); i2++) {
            c2547mb.b(8, (B0) this.k.get(i2));
        }
        for (int i3 = 0; i3 < this.l.size(); i3++) {
            c2547mb.d(31, ((Integer) this.l.get(i3)).intValue());
        }
        c0927DjJ.a(200, c2547mb);
        c2547mb.b(this.b);
    }

    private ZO() {
        this.m = (byte) -1;
        this.n = -1;
        this.b = Y5.a;
    }

    private ZO(C2396kb c2396kb, C2867qh c2867qh) {
        VO voO;
        this.m = (byte) -1;
        this.n = -1;
        A();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        int i = 0;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        switch (iN) {
                            case 0:
                                break;
                            case 8:
                                this.c |= 1;
                                this.d = c2396kb.g();
                                continue;
                            case 16:
                                this.c |= 2;
                                this.e = c2396kb.g();
                                continue;
                            case 26:
                                if ((i & 4) != 4) {
                                    this.f = new ArrayList();
                                    i |= 4;
                                }
                                this.f.add(c2396kb.a((AbstractC1732c1) C1840dP.n, c2867qh));
                                continue;
                            case 34:
                                voO = (this.c & 4) == 4 ? this.g.O() : null;
                                WO wo = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                                this.g = wo;
                                if (voO != null) {
                                    voO.a(wo);
                                    this.g = voO.h();
                                }
                                this.c |= 4;
                                continue;
                            case 40:
                                this.c |= 8;
                                this.h = c2396kb.g();
                                continue;
                            case 50:
                                voO = (this.c & 16) == 16 ? this.i.O() : null;
                                WO wo2 = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                                this.i = wo2;
                                if (voO != null) {
                                    voO.a(wo2);
                                    this.i = voO.h();
                                }
                                this.c |= 16;
                                continue;
                            case 56:
                                this.c |= 32;
                                this.j = c2396kb.g();
                                continue;
                            case 66:
                                if ((i & 128) != 128) {
                                    this.k = new ArrayList();
                                    i |= 128;
                                }
                                this.k.add(c2396kb.a((AbstractC1732c1) XN.h, c2867qh));
                                continue;
                            case 248:
                                if ((i & 256) != 256) {
                                    this.l = new ArrayList();
                                    i |= 256;
                                }
                                this.l.add(Integer.valueOf(c2396kb.g()));
                                continue;
                            case 250:
                                int iC = c2396kb.c(c2396kb.j());
                                if ((i & 256) != 256 && c2396kb.a() > 0) {
                                    this.l = new ArrayList();
                                    i |= 256;
                                }
                                while (c2396kb.a() > 0) {
                                    this.l.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC);
                                continue;
                            default:
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                    break;
                                }
                                break;
                        }
                        z = true;
                    } catch (IOException e) {
                        throw new C1054Hs(e.getMessage()).a(this);
                    }
                } catch (C1054Hs e2) {
                    throw e2.a(this);
                }
            } catch (Throwable th) {
                if ((i & 4) == 4) {
                    this.f = Collections.unmodifiableList(this.f);
                }
                if ((i & 128) == 128) {
                    this.k = Collections.unmodifiableList(this.k);
                }
                if ((i & 256) == 256) {
                    this.l = Collections.unmodifiableList(this.l);
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
        if ((i & 4) == 4) {
            this.f = Collections.unmodifiableList(this.f);
        }
        if ((i & 128) == 128) {
            this.k = Collections.unmodifiableList(this.k);
        }
        if ((i & 256) == 256) {
            this.l = Collections.unmodifiableList(this.l);
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
