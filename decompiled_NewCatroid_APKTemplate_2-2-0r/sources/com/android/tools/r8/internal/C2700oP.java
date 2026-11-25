package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2700oP extends AbstractC1045Hj {
    private static final C2700oP k;
    public static InterfaceC3429yL l = new C2381kP();
    private final Y5 a;
    private int b;
    private int c;
    private int d;
    private EnumC2535mP e;
    private int f;
    private int g;
    private EnumC2609nP h;
    private byte i;
    private int j;

    static {
        C2700oP c2700oP = new C2700oP();
        k = c2700oP;
        c2700oP.t();
    }

    /* synthetic */ C2700oP(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C2700oP g() {
        return k;
    }

    private void t() {
        this.c = 0;
        this.d = 0;
        this.e = EnumC2535mP.c;
        this.f = 0;
        this.g = 0;
        this.h = EnumC2609nP.b;
    }

    public static C2461lP u() {
        return C2461lP.f();
    }

    public final int h() {
        return this.f;
    }

    public final EnumC2535mP i() {
        return this.e;
    }

    public final int j() {
        return this.g;
    }

    public final int k() {
        return this.c;
    }

    public final int l() {
        return this.d;
    }

    public final EnumC2609nP m() {
        return this.h;
    }

    public final boolean n() {
        return (this.b & 8) == 8;
    }

    public final boolean o() {
        return (this.b & 4) == 4;
    }

    public final boolean p() {
        return (this.b & 16) == 16;
    }

    public final boolean q() {
        return (this.b & 1) == 1;
    }

    public final boolean r() {
        return (this.b & 2) == 2;
    }

    public final boolean s() {
        return (this.b & 32) == 32;
    }

    /* synthetic */ C2700oP(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.j;
        if (i != -1) {
            return i;
        }
        int iB = (this.b & 1) == 1 ? C2547mb.b(1, this.c) : 0;
        if ((this.b & 2) == 2) {
            iB += C2547mb.b(2, this.d);
        }
        if ((this.b & 4) == 4) {
            iB += C2547mb.a(3, this.e.a());
        }
        if ((this.b & 8) == 8) {
            iB += C2547mb.b(4, this.f);
        }
        if ((this.b & 16) == 16) {
            iB += C2547mb.b(5, this.g);
        }
        if ((this.b & 32) == 32) {
            iB += C2547mb.a(6, this.h.a());
        }
        int size = this.a.size() + iB;
        this.j = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C2461lP.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C2461lP.f().a(this);
    }

    private C2700oP(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.i = (byte) -1;
        this.j = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.i;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        this.i = (byte) 1;
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
        if ((this.b & 8) == 8) {
            c2547mb.d(4, this.f);
        }
        if ((this.b & 16) == 16) {
            c2547mb.d(5, this.g);
        }
        if ((this.b & 32) == 32) {
            c2547mb.c(6, this.h.a());
        }
        c2547mb.b(this.a);
    }

    private C2700oP() {
        this.i = (byte) -1;
        this.j = -1;
        this.a = Y5.a;
    }

    private C2700oP(C2396kb c2396kb, C2867qh c2867qh) {
        this.i = (byte) -1;
        this.j = -1;
        t();
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
                                this.b |= 1;
                                this.c = c2396kb.g();
                            } else if (iN == 16) {
                                this.b |= 2;
                                this.d = c2396kb.g();
                            } else if (iN == 24) {
                                int iE = c2396kb.e();
                                EnumC2535mP enumC2535mPC = EnumC2535mP.c(iE);
                                if (enumC2535mPC == null) {
                                    c2547mbA.g(iN);
                                    c2547mbA.g(iE);
                                } else {
                                    this.b |= 4;
                                    this.e = enumC2535mPC;
                                }
                            } else if (iN == 32) {
                                this.b |= 8;
                                this.f = c2396kb.g();
                            } else if (iN == 40) {
                                this.b |= 16;
                                this.g = c2396kb.g();
                            } else if (iN != 48) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                int iE2 = c2396kb.e();
                                EnumC2609nP enumC2609nPC = EnumC2609nP.c(iE2);
                                if (enumC2609nPC == null) {
                                    c2547mbA.g(iN);
                                    c2547mbA.g(iE2);
                                } else {
                                    this.b |= 32;
                                    this.h = enumC2609nPC;
                                }
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
