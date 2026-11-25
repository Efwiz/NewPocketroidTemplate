package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UO extends AbstractC1045Hj {
    private static final UO h;
    public static InterfaceC3429yL i = new RO();
    private final Y5 a;
    private int b;
    private TO c;
    private WO d;
    private int e;
    private byte f;
    private int g;

    static {
        UO uo = new UO();
        h = uo;
        uo.n();
    }

    /* synthetic */ UO(C2396kb c2396kb, C2867qh c2867qh, int i2) {
        this(c2396kb, c2867qh);
    }

    public static UO g() {
        return h;
    }

    private void n() {
        this.c = TO.d;
        this.d = WO.o();
        this.e = 0;
    }

    public static SO o() {
        return SO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i2 = this.g;
        if (i2 != -1) {
            return i2;
        }
        int iA = (this.b & 1) == 1 ? C2547mb.a(1, this.c.a()) : 0;
        if ((this.b & 2) == 2) {
            iA += C2547mb.a(2, this.d);
        }
        if ((this.b & 4) == 4) {
            iA += C2547mb.b(3, this.e);
        }
        int size = this.a.size() + iA;
        this.g = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return SO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return SO.f().a(this);
    }

    public final TO h() {
        return this.c;
    }

    public final WO i() {
        return this.d;
    }

    public final int j() {
        return this.e;
    }

    public final boolean k() {
        return (this.b & 1) == 1;
    }

    public final boolean l() {
        return (this.b & 2) == 2;
    }

    public final boolean m() {
        return (this.b & 4) == 4;
    }

    /* synthetic */ UO(AbstractC0859Bj abstractC0859Bj, int i2) {
        this(abstractC0859Bj);
    }

    private UO(AbstractC0859Bj abstractC0859Bj) {
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
        if (l() && !this.d.a()) {
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
            c2547mb.c(1, this.c.a());
        }
        if ((this.b & 2) == 2) {
            c2547mb.b(2, this.d);
        }
        if ((this.b & 4) == 4) {
            c2547mb.d(3, this.e);
        }
        c2547mb.b(this.a);
    }

    private UO() {
        this.f = (byte) -1;
        this.g = -1;
        this.a = Y5.a;
    }

    private UO(C2396kb c2396kb, C2867qh c2867qh) {
        this.f = (byte) -1;
        this.g = -1;
        n();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        if (iN != 0) {
                            if (iN == 8) {
                                int iE = c2396kb.e();
                                TO toC = TO.c(iE);
                                if (toC == null) {
                                    c2547mbA.g(iN);
                                    c2547mbA.g(iE);
                                } else {
                                    this.b |= 1;
                                    this.c = toC;
                                }
                            } else if (iN == 18) {
                                VO voO = (this.b & 2) == 2 ? this.d.O() : null;
                                WO wo = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                                this.d = wo;
                                if (voO != null) {
                                    voO.a(wo);
                                    this.d = voO.h();
                                }
                                this.b |= 2;
                            } else if (iN != 24) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                this.b |= 4;
                                this.e = c2396kb.g();
                            }
                        }
                        z = true;
                    } catch (IOException e) {
                        throw new C1054Hs(e.getMessage()).a(this);
                    }
                } catch (C1054Hs e2) {
                    throw e2.a(this);
                }
            } catch (Throwable th) {
                try {
                    c2547mbA.b();
                } catch (IOException unused) {
                } finally {
                    this.a = x5D.c();
                }
                throw th;
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
