package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3125uO extends AbstractC1045Hj {
    private static final C3125uO l;
    public static InterfaceC3429yL m = new C2918rO();
    private final Y5 a;
    private int b;
    private int c;
    private int d;
    private EnumC3056tO e;
    private WO f;
    private int g;
    private List h;
    private List i;
    private byte j;
    private int k;

    static {
        C3125uO c3125uO = new C3125uO();
        l = c3125uO;
        c3125uO.t();
    }

    /* synthetic */ C3125uO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C3125uO i() {
        return l;
    }

    private void t() {
        this.c = 0;
        this.d = 0;
        this.e = EnumC3056tO.b;
        this.f = WO.o();
        this.g = 0;
        this.h = Collections.emptyList();
        this.i = Collections.emptyList();
    }

    public static C2987sO u() {
        return C2987sO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C2987sO.f().a(this);
    }

    public final List g() {
        return this.h;
    }

    public final EnumC3056tO h() {
        return this.e;
    }

    public final int j() {
        return this.c;
    }

    public final WO k() {
        return this.f;
    }

    public final int l() {
        return this.g;
    }

    public final List m() {
        return this.i;
    }

    public final int n() {
        return this.d;
    }

    public final boolean o() {
        return (this.b & 4) == 4;
    }

    public final boolean p() {
        return (this.b & 1) == 1;
    }

    public final boolean q() {
        return (this.b & 8) == 8;
    }

    public final boolean r() {
        return (this.b & 16) == 16;
    }

    public final boolean s() {
        return (this.b & 2) == 2;
    }

    public final C2987sO v() {
        return C2987sO.f().a(this);
    }

    /* synthetic */ C3125uO(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C2987sO.f();
    }

    private C3125uO(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.j = (byte) -1;
        this.k = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.k;
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
            iB += C2547mb.a(4, this.f);
        }
        if ((this.b & 16) == 16) {
            iB += C2547mb.b(5, this.g);
        }
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            iB += C2547mb.a(6, (B0) this.h.get(i2));
        }
        for (int i3 = 0; i3 < this.i.size(); i3++) {
            iB += C2547mb.a(7, (B0) this.i.get(i3));
        }
        int size = this.a.size() + iB;
        this.k = size;
        return size;
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
        if (q() && !this.f.a()) {
            this.j = (byte) 0;
            return false;
        }
        for (int i = 0; i < this.h.size(); i++) {
            if (!((C3125uO) this.h.get(i)).a()) {
                this.j = (byte) 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            if (!((C3125uO) this.i.get(i2)).a()) {
                this.j = (byte) 0;
                return false;
            }
        }
        this.j = (byte) 1;
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
            c2547mb.b(4, this.f);
        }
        if ((this.b & 16) == 16) {
            c2547mb.d(5, this.g);
        }
        for (int i = 0; i < this.h.size(); i++) {
            c2547mb.b(6, (B0) this.h.get(i));
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            c2547mb.b(7, (B0) this.i.get(i2));
        }
        c2547mb.b(this.a);
    }

    private C3125uO() {
        this.j = (byte) -1;
        this.k = -1;
        this.a = Y5.a;
    }

    private C3125uO(C2396kb c2396kb, C2867qh c2867qh) {
        this.j = (byte) -1;
        this.k = -1;
        t();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        int i = 0;
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
                        } else if (iN == 24) {
                            int iE = c2396kb.e();
                            EnumC3056tO enumC3056tOC = EnumC3056tO.c(iE);
                            if (enumC3056tOC == null) {
                                c2547mbA.g(iN);
                                c2547mbA.g(iE);
                            } else {
                                this.b |= 4;
                                this.e = enumC3056tOC;
                            }
                        } else if (iN == 34) {
                            VO voO = (this.b & 8) == 8 ? this.f.O() : null;
                            WO wo = (WO) c2396kb.a((AbstractC1732c1) WO.u, c2867qh);
                            this.f = wo;
                            if (voO != null) {
                                voO.a(wo);
                                this.f = voO.h();
                            }
                            this.b |= 8;
                        } else if (iN == 40) {
                            this.b |= 16;
                            this.g = c2396kb.g();
                        } else if (iN == 50) {
                            if ((i & 32) != 32) {
                                this.h = new ArrayList();
                                i |= 32;
                            }
                            this.h.add(c2396kb.a((AbstractC1732c1) m, c2867qh));
                        } else if (iN != 58) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            if ((i & 64) != 64) {
                                this.i = new ArrayList();
                                i |= 64;
                            }
                            this.i.add(c2396kb.a((AbstractC1732c1) m, c2867qh));
                        }
                    }
                    z = true;
                } catch (C1054Hs e) {
                    throw e.a(this);
                } catch (IOException e2) {
                    throw new C1054Hs(e2.getMessage()).a(this);
                }
            } catch (Throwable th) {
                if ((i & 32) == 32) {
                    this.h = Collections.unmodifiableList(this.h);
                }
                if ((i & 64) == 64) {
                    this.i = Collections.unmodifiableList(this.i);
                }
                try {
                    c2547mbA.b();
                } catch (IOException unused) {
                } finally {
                    this.a = x5D.c();
                }
                throw th;
            }
        }
        if ((i & 32) == 32) {
            this.h = Collections.unmodifiableList(this.h);
        }
        if ((i & 64) == 64) {
            this.i = Collections.unmodifiableList(this.i);
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
