package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class LO extends AbstractC1045Hj {
    private static final LO h;
    public static InterfaceC3429yL i = new IO();
    private final Y5 a;
    private int b;
    private int c;
    private int d;
    private KO e;
    private byte f;
    private int g;

    static {
        LO lo = new LO();
        h = lo;
        lo.n();
    }

    /* synthetic */ LO(C2396kb c2396kb, C2867qh c2867qh, int i2) {
        this(c2396kb, c2867qh);
    }

    public static LO g() {
        return h;
    }

    private void n() {
        this.c = -1;
        this.d = 0;
        this.e = KO.c;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return JO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return JO.f().a(this);
    }

    public final KO h() {
        return this.e;
    }

    public final int i() {
        return this.c;
    }

    public final int j() {
        return this.d;
    }

    public final boolean k() {
        return (this.b & 4) == 4;
    }

    public final boolean l() {
        return (this.b & 1) == 1;
    }

    public final boolean m() {
        return (this.b & 2) == 2;
    }

    /* synthetic */ LO(AbstractC0859Bj abstractC0859Bj, int i2) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i2 = this.g;
        if (i2 != -1) {
            return i2;
        }
        int iB = (this.b & 1) == 1 ? C2547mb.b(1, this.c) : 0;
        if ((this.b & 2) == 2) {
            iB += C2547mb.b(2, this.d);
        }
        if ((this.b & 4) == 4) {
            iB += C2547mb.a(3, this.e.a());
        }
        int size = this.a.size() + iB;
        this.g = size;
        return size;
    }

    private LO(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.f = (byte) -1;
        this.g = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.f;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!m()) {
            this.f = (byte) 0;
            return false;
        }
        this.f = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        if ((this.b & 1) == 1) {
            c2547mb.d(1, this.c);
        }
        if ((this.b & 2) == 2) {
            c2547mb.d(2, this.d);
        }
        if ((this.b & 4) == 4) {
            c2547mb.c(3, this.e.a());
        }
        c2547mb.b(this.a);
    }

    private LO() {
        this.f = (byte) -1;
        this.g = -1;
        this.a = Y5.a;
    }

    private LO(C2396kb c2396kb, C2867qh c2867qh) {
        this.f = (byte) -1;
        this.g = -1;
        n();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    if (iN != 0) {
                        if (iN == 8) {
                            this.b |= 1;
                            this.c = c2396kb.g();
                        } else if (iN == 16) {
                            this.b |= 2;
                            this.d = c2396kb.g();
                        } else if (iN != 24) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            int iE = c2396kb.e();
                            KO koC = KO.c(iE);
                            if (koC == null) {
                                c2547mbA.g(iN);
                                c2547mbA.g(iE);
                            } else {
                                this.b |= 4;
                                this.e = koC;
                            }
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    try {
                        c2547mbA.b();
                    } catch (IOException unused) {
                    } finally {
                        this.a = x5D.c();
                    }
                    throw th;
                }
            } catch (C1054Hs e) {
                throw e.a(this);
            } catch (IOException e2) {
                throw new C1054Hs(e2.getMessage()).a(this);
            }
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
