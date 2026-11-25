package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1840dP extends AbstractC0958Ej {
    private static final C1840dP m;
    public static InterfaceC3429yL n = new C1611aP();
    private final Y5 b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private EnumC1762cP g;
    private List h;
    private List i;
    private int j;
    private byte k;
    private int l;

    static {
        C1840dP c1840dP = new C1840dP();
        m = c1840dP;
        c1840dP.v();
    }

    /* synthetic */ C1840dP(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C1840dP k() {
        return m;
    }

    private void v() {
        this.d = 0;
        this.e = 0;
        this.f = false;
        this.g = EnumC1762cP.d;
        this.h = Collections.emptyList();
        this.i = Collections.emptyList();
    }

    public static C1691bP w() {
        return C1691bP.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1691bP.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1691bP.f().a(this);
    }

    public final int l() {
        return this.d;
    }

    public final int m() {
        return this.e;
    }

    public final boolean n() {
        return this.f;
    }

    public final List o() {
        return this.i;
    }

    public final List p() {
        return this.h;
    }

    public final EnumC1762cP q() {
        return this.g;
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

    /* synthetic */ C1840dP(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    private C1840dP(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.j = -1;
        this.k = (byte) -1;
        this.l = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.l;
        if (i != -1) {
            return i;
        }
        int iB = (this.c & 1) == 1 ? C2547mb.b(1, this.d) : 0;
        if ((this.c & 2) == 2) {
            iB += C2547mb.b(2, this.e);
        }
        if ((this.c & 4) == 4) {
            iB += C2547mb.c(3) + 1;
        }
        if ((this.c & 8) == 8) {
            iB += C2547mb.a(4, this.g.a());
        }
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            iB += C2547mb.a(5, (B0) this.h.get(i2));
        }
        int iA = 0;
        for (int i3 = 0; i3 < this.i.size(); i3++) {
            iA = YN.a((Integer) this.i.get(i3), iA);
        }
        int iA2 = iB + iA;
        if (!this.i.isEmpty()) {
            iA2 = iA2 + 1 + C2547mb.a(iA);
        }
        this.j = iA;
        int size = this.b.size() + iA2 + h();
        this.l = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return m;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.k;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!r()) {
            this.k = (byte) 0;
            return false;
        }
        if (!s()) {
            this.k = (byte) 0;
            return false;
        }
        for (int i = 0; i < this.h.size(); i++) {
            if (!((WO) this.h.get(i)).a()) {
                this.k = (byte) 0;
                return false;
            }
        }
        if (!g()) {
            this.k = (byte) 0;
            return false;
        }
        this.k = (byte) 1;
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
            c2547mb.a(this.f);
        }
        if ((this.c & 8) == 8) {
            c2547mb.c(4, this.g.a());
        }
        for (int i = 0; i < this.h.size(); i++) {
            c2547mb.b(5, (B0) this.h.get(i));
        }
        if (this.i.size() > 0) {
            c2547mb.g(50);
            c2547mb.g(this.j);
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            c2547mb.d(((Integer) this.i.get(i2)).intValue());
        }
        c0927DjJ.a(1000, c2547mb);
        c2547mb.b(this.b);
    }

    private C1840dP() {
        this.j = -1;
        this.k = (byte) -1;
        this.l = -1;
        this.b = Y5.a;
    }

    private C1840dP(C2396kb c2396kb, C2867qh c2867qh) {
        this.j = -1;
        this.k = (byte) -1;
        this.l = -1;
        v();
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
                            this.c |= 1;
                            this.d = c2396kb.g();
                        } else if (iN == 16) {
                            this.c |= 2;
                            this.e = c2396kb.g();
                        } else if (iN == 24) {
                            this.c |= 4;
                            this.f = c2396kb.b();
                        } else if (iN == 32) {
                            int iE = c2396kb.e();
                            EnumC1762cP enumC1762cPC = EnumC1762cP.c(iE);
                            if (enumC1762cPC == null) {
                                c2547mbA.g(iN);
                                c2547mbA.g(iE);
                            } else {
                                this.c |= 8;
                                this.g = enumC1762cPC;
                            }
                        } else if (iN == 42) {
                            if ((i & 16) != 16) {
                                this.h = new ArrayList();
                                i |= 16;
                            }
                            this.h.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                        } else if (iN == 48) {
                            if ((i & 32) != 32) {
                                this.i = new ArrayList();
                                i |= 32;
                            }
                            this.i.add(Integer.valueOf(c2396kb.g()));
                        } else if (iN != 50) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            int iC = c2396kb.c(c2396kb.j());
                            if ((i & 32) != 32 && c2396kb.a() > 0) {
                                this.i = new ArrayList();
                                i |= 32;
                            }
                            while (c2396kb.a() > 0) {
                                this.i.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC);
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((i & 16) == 16) {
                        this.h = Collections.unmodifiableList(this.h);
                    }
                    if ((i & 32) == 32) {
                        this.i = Collections.unmodifiableList(this.i);
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
        if ((i & 16) == 16) {
            this.h = Collections.unmodifiableList(this.h);
        }
        if ((i & 32) == 32) {
            this.i = Collections.unmodifiableList(this.i);
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
