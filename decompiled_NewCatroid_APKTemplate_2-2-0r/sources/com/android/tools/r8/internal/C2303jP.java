package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2303jP extends AbstractC0958Ej {
    private static final C2303jP l;
    public static InterfaceC3429yL m = new C2153hP();
    private final Y5 b;
    private int c;
    private int d;
    private int e;
    private WO f;
    private int g;
    private WO h;
    private int i;
    private byte j;
    private int k;

    static {
        C2303jP c2303jP = new C2303jP();
        l = c2303jP;
        c2303jP.x();
    }

    /* synthetic */ C2303jP(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C2303jP k() {
        return l;
    }

    private void x() {
        this.d = 0;
        this.e = 0;
        this.f = WO.o();
        this.g = 0;
        this.h = WO.o();
        this.i = 0;
    }

    public static C2233iP y() {
        return C2233iP.f();
    }

    public final int l() {
        return this.d;
    }

    public final int m() {
        return this.e;
    }

    public final WO n() {
        return this.f;
    }

    public final int o() {
        return this.g;
    }

    public final WO p() {
        return this.h;
    }

    public final int q() {
        return this.i;
    }

    public final boolean r() {
        return (this.c & 1) == 1;
    }

    public final boolean s() {
        return (this.c & 2) == 2;
    }

    public final boolean t() {
        return (this.c & 4) == 4;
    }

    public final boolean u() {
        return (this.c & 8) == 8;
    }

    public final boolean v() {
        return (this.c & 16) == 16;
    }

    public final boolean w() {
        return (this.c & 32) == 32;
    }

    public final C2233iP z() {
        return C2233iP.f().a(this);
    }

    /* synthetic */ C2303jP(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.k;
        if (i != -1) {
            return i;
        }
        int iB = (this.c & 1) == 1 ? C2547mb.b(1, this.d) : 0;
        if ((this.c & 2) == 2) {
            iB += C2547mb.b(2, this.e);
        }
        if ((this.c & 4) == 4) {
            iB += C2547mb.a(3, this.f);
        }
        if ((this.c & 16) == 16) {
            iB += C2547mb.a(4, this.h);
        }
        if ((this.c & 8) == 8) {
            iB += C2547mb.b(5, this.g);
        }
        if ((this.c & 32) == 32) {
            iB += C2547mb.b(6, this.i);
        }
        int size = this.b.size() + iB + h();
        this.k = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C2233iP.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C2233iP.f().a(this);
    }

    private C2303jP(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.j = (byte) -1;
        this.k = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return l;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.j;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!s()) {
            this.j = (byte) 0;
            return false;
        }
        if (t() && !this.f.a()) {
            this.j = (byte) 0;
            return false;
        }
        if (v() && !this.h.a()) {
            this.j = (byte) 0;
            return false;
        }
        if (!g()) {
            this.j = (byte) 0;
            return false;
        }
        this.j = (byte) 1;
        return true;
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
        if ((this.c & 4) == 4) {
            c2547mb.b(3, this.f);
        }
        if ((this.c & 16) == 16) {
            c2547mb.b(4, this.h);
        }
        if ((this.c & 8) == 8) {
            c2547mb.d(5, this.g);
        }
        if ((this.c & 32) == 32) {
            c2547mb.d(6, this.i);
        }
        c0927DjJ.a(200, c2547mb);
        c2547mb.b(this.b);
    }

    private C2303jP() {
        this.j = (byte) -1;
        this.k = -1;
        this.b = Y5.a;
    }

    private C2303jP(C2396kb c2396kb, C2867qh c2867qh) {
        VO voO;
        this.j = (byte) -1;
        this.k = -1;
        x();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    if (iN != 0) {
                        if (iN == 8) {
                            this.c |= 1;
                            this.d = c2396kb.g();
                        } else if (iN != 16) {
                            if (iN == 26) {
                                voO = (this.c & 4) == 4 ? this.f.O() : null;
                                WO wo = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                                this.f = wo;
                                if (voO != null) {
                                    voO.a(wo);
                                    this.f = voO.h();
                                }
                                this.c |= 4;
                            } else if (iN == 34) {
                                voO = (this.c & 16) == 16 ? this.h.O() : null;
                                WO wo2 = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                                this.h = wo2;
                                if (voO != null) {
                                    voO.a(wo2);
                                    this.h = voO.h();
                                }
                                this.c |= 16;
                            } else if (iN == 40) {
                                this.c |= 8;
                                this.g = c2396kb.g();
                            } else if (iN != 48) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                this.c |= 32;
                                this.i = c2396kb.g();
                            }
                        } else {
                            this.c |= 2;
                            this.e = c2396kb.g();
                        }
                    }
                    z = true;
                } catch (C1054Hs e) {
                    throw e.a(this);
                } catch (IOException e2) {
                    throw new C1054Hs(e2.getMessage()).a(this);
                }
            } catch (Throwable th) {
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
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.b = x5D.c();
        }
        i();
    }
}
