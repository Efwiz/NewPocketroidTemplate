package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1307Qu extends AbstractC1045Hj {
    private static final C1307Qu j;
    public static InterfaceC3429yL k = new C1250Ou();
    private final Y5 a;
    private int b;
    private C1143Ku c;
    private C1223Nu d;
    private C1223Nu e;
    private C1223Nu f;
    private C1223Nu g;
    private byte h;
    private int i;

    static {
        C1307Qu c1307Qu = new C1307Qu();
        j = c1307Qu;
        c1307Qu.r();
    }

    /* synthetic */ C1307Qu(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C1307Qu g() {
        return j;
    }

    private void r() {
        this.c = C1143Ku.g();
        this.d = C1223Nu.g();
        this.e = C1223Nu.g();
        this.f = C1223Nu.g();
        this.g = C1223Nu.g();
    }

    public static C1280Pu s() {
        return C1280Pu.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1280Pu.f().a(this);
    }

    public final C1223Nu h() {
        return this.g;
    }

    public final C1143Ku i() {
        return this.c;
    }

    public final C1223Nu j() {
        return this.e;
    }

    public final C1223Nu k() {
        return this.f;
    }

    public final C1223Nu l() {
        return this.d;
    }

    public final boolean m() {
        return (this.b & 16) == 16;
    }

    public final boolean n() {
        return (this.b & 1) == 1;
    }

    public final boolean o() {
        return (this.b & 4) == 4;
    }

    public final boolean p() {
        return (this.b & 8) == 8;
    }

    public final boolean q() {
        return (this.b & 2) == 2;
    }

    /* synthetic */ C1307Qu(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.i;
        if (i != -1) {
            return i;
        }
        int iA = (this.b & 1) == 1 ? C2547mb.a(1, this.c) : 0;
        if ((this.b & 2) == 2) {
            iA += C2547mb.a(2, this.d);
        }
        if ((this.b & 4) == 4) {
            iA += C2547mb.a(3, this.e);
        }
        if ((this.b & 8) == 8) {
            iA += C2547mb.a(4, this.f);
        }
        if ((this.b & 16) == 16) {
            iA += C2547mb.a(5, this.g);
        }
        int size = this.a.size() + iA;
        this.i = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1280Pu.f();
    }

    private C1307Qu(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.h = (byte) -1;
        this.i = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.h;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        this.h = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        if ((this.b & 1) == 1) {
            c2547mb.b(1, this.c);
        }
        if ((this.b & 2) == 2) {
            c2547mb.b(2, this.d);
        }
        if ((this.b & 4) == 4) {
            c2547mb.b(3, this.e);
        }
        if ((this.b & 8) == 8) {
            c2547mb.b(4, this.f);
        }
        if ((this.b & 16) == 16) {
            c2547mb.b(5, this.g);
        }
        c2547mb.b(this.a);
    }

    private C1307Qu() {
        this.h = (byte) -1;
        this.i = -1;
        this.a = Y5.a;
    }

    private C1307Qu(C2396kb c2396kb, C2867qh c2867qh) {
        this.h = (byte) -1;
        this.i = -1;
        r();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    if (iN != 0) {
                        if (iN == 10) {
                            C1115Ju c1115JuN = (this.b & 1) == 1 ? this.c.n() : null;
                            C1143Ku c1143Ku = (C1143Ku) c2396kb.a((AbstractC1732c1) C1143Ku.h, c2867qh);
                            this.c = c1143Ku;
                            if (c1115JuN != null) {
                                c1115JuN.a(c1143Ku);
                                this.c = c1115JuN.g();
                            }
                            this.b |= 1;
                        } else if (iN == 18) {
                            C1196Mu c1196MuN = (this.b & 2) == 2 ? this.d.n() : null;
                            C1223Nu c1223Nu = (C1223Nu) c2396kb.a((AbstractC1732c1) C1223Nu.h, c2867qh);
                            this.d = c1223Nu;
                            if (c1196MuN != null) {
                                c1196MuN.a(c1223Nu);
                                this.d = c1196MuN.g();
                            }
                            this.b |= 2;
                        } else if (iN == 26) {
                            C1196Mu c1196MuN2 = (this.b & 4) == 4 ? this.e.n() : null;
                            C1223Nu c1223Nu2 = (C1223Nu) c2396kb.a((AbstractC1732c1) C1223Nu.h, c2867qh);
                            this.e = c1223Nu2;
                            if (c1196MuN2 != null) {
                                c1196MuN2.a(c1223Nu2);
                                this.e = c1196MuN2.g();
                            }
                            this.b |= 4;
                        } else if (iN == 34) {
                            C1196Mu c1196MuN3 = (this.b & 8) == 8 ? this.f.n() : null;
                            C1223Nu c1223Nu3 = (C1223Nu) c2396kb.a((AbstractC1732c1) C1223Nu.h, c2867qh);
                            this.f = c1223Nu3;
                            if (c1196MuN3 != null) {
                                c1196MuN3.a(c1223Nu3);
                                this.f = c1196MuN3.g();
                            }
                            this.b |= 8;
                        } else if (iN != 42) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            C1196Mu c1196MuN4 = (this.b & 16) == 16 ? this.g.n() : null;
                            C1223Nu c1223Nu4 = (C1223Nu) c2396kb.a((AbstractC1732c1) C1223Nu.h, c2867qh);
                            this.g = c1223Nu4;
                            if (c1196MuN4 != null) {
                                c1196MuN4.a(c1223Nu4);
                                this.g = c1196MuN4.g();
                            }
                            this.b |= 16;
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
