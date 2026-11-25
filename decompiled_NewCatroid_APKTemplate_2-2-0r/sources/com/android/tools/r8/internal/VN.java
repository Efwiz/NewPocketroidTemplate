package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VN extends AbstractC1045Hj {
    private static final VN g;
    public static InterfaceC3429yL h = new PN();
    private final Y5 a;
    private int b;
    private int c;
    private UN d;
    private byte e;
    private int f;

    static {
        VN vn = new VN();
        g = vn;
        vn.l();
    }

    /* synthetic */ VN(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static VN g() {
        return g;
    }

    private void l() {
        this.c = 0;
        this.d = UN.k();
    }

    public static QN m() {
        return QN.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.f;
        if (i != -1) {
            return i;
        }
        int iB = (this.b & 1) == 1 ? C2547mb.b(1, this.c) : 0;
        if ((this.b & 2) == 2) {
            iB += C2547mb.a(2, this.d);
        }
        int size = this.a.size() + iB;
        this.f = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return QN.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return QN.f().a(this);
    }

    public final int h() {
        return this.c;
    }

    public final UN i() {
        return this.d;
    }

    public final boolean j() {
        return (this.b & 1) == 1;
    }

    public final boolean k() {
        return (this.b & 2) == 2;
    }

    /* synthetic */ VN(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    private VN(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.e = (byte) -1;
        this.f = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.e;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!j()) {
            this.e = (byte) 0;
            return false;
        }
        if (!k()) {
            this.e = (byte) 0;
            return false;
        }
        if (!this.d.a()) {
            this.e = (byte) 0;
            return false;
        }
        this.e = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        if ((this.b & 1) == 1) {
            c2547mb.d(1, this.c);
        }
        if ((this.b & 2) == 2) {
            c2547mb.b(2, this.d);
        }
        c2547mb.b(this.a);
    }

    private VN() {
        this.e = (byte) -1;
        this.f = -1;
        this.a = Y5.a;
    }

    private VN(C2396kb c2396kb, C2867qh c2867qh) {
        this.e = (byte) -1;
        this.f = -1;
        l();
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
                        } else if (iN != 18) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            SN snE = (this.b & 2) == 2 ? this.d.E() : null;
                            UN un = (UN) c2396kb.a((AbstractC1732c1) UN.q, c2867qh);
                            this.d = un;
                            if (snE != null) {
                                snE.a(un);
                                this.d = snE.g();
                            }
                            this.b |= 2;
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
