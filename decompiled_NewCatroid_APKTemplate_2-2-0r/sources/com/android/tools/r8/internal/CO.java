package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CO extends AbstractC0958Ej {
    private static final CO k;
    public static InterfaceC3429yL l = new AO();
    private final Y5 b;
    private int c;
    private List d;
    private List e;
    private List f;
    private C2075gP g;
    private C2919rP h;
    private byte i;
    private int j;

    static {
        CO co = new CO();
        k = co;
        co.s();
    }

    /* synthetic */ CO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static CO k() {
        return k;
    }

    private void s() {
        this.d = Collections.emptyList();
        this.e = Collections.emptyList();
        this.f = Collections.emptyList();
        this.g = C2075gP.g();
        this.h = C2919rP.g();
    }

    public static BO t() {
        return BO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return BO.f().a(this);
    }

    public final List l() {
        return this.d;
    }

    public final List m() {
        return this.e;
    }

    public final List n() {
        return this.f;
    }

    public final C2075gP o() {
        return this.g;
    }

    public final C2919rP p() {
        return this.h;
    }

    public final boolean q() {
        return (this.c & 1) == 1;
    }

    public final boolean r() {
        return (this.c & 2) == 2;
    }

    /* synthetic */ CO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return BO.f();
    }

    private CO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.i = (byte) -1;
        this.j = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return k;
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.j;
        if (i != -1) {
            return i;
        }
        int iA = 0;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            iA += C2547mb.a(3, (B0) this.d.get(i2));
        }
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            iA += C2547mb.a(4, (B0) this.e.get(i3));
        }
        for (int i4 = 0; i4 < this.f.size(); i4++) {
            iA += C2547mb.a(5, (B0) this.f.get(i4));
        }
        if ((this.c & 1) == 1) {
            iA += C2547mb.a(30, this.g);
        }
        if ((this.c & 2) == 2) {
            iA += C2547mb.a(32, this.h);
        }
        int size = this.b.size() + iA + h();
        this.j = size;
        return size;
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
        for (int i = 0; i < this.d.size(); i++) {
            if (!((C3354xO) this.d.get(i)).a()) {
                this.i = (byte) 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            if (!((FO) this.e.get(i2)).a()) {
                this.i = (byte) 0;
                return false;
            }
        }
        for (int i3 = 0; i3 < this.f.size(); i3++) {
            if (!((ZO) this.f.get(i3)).a()) {
                this.i = (byte) 0;
                return false;
            }
        }
        if (q() && !this.g.a()) {
            this.i = (byte) 0;
            return false;
        }
        if (!g()) {
            this.i = (byte) 0;
            return false;
        }
        this.i = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        for (int i = 0; i < this.d.size(); i++) {
            c2547mb.b(3, (B0) this.d.get(i));
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            c2547mb.b(4, (B0) this.e.get(i2));
        }
        for (int i3 = 0; i3 < this.f.size(); i3++) {
            c2547mb.b(5, (B0) this.f.get(i3));
        }
        if ((this.c & 1) == 1) {
            c2547mb.b(30, this.g);
        }
        if ((this.c & 2) == 2) {
            c2547mb.b(32, this.h);
        }
        c0927DjJ.a(200, c2547mb);
        c2547mb.b(this.b);
    }

    private CO() {
        this.i = (byte) -1;
        this.j = -1;
        this.b = Y5.a;
    }

    private CO(C2396kb c2396kb, C2867qh c2867qh) {
        this.i = (byte) -1;
        this.j = -1;
        s();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        int i = 0;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        if (iN != 0) {
                            if (iN == 26) {
                                int i2 = (i == true ? 1 : 0) & 1;
                                i = i;
                                if (i2 != 1) {
                                    this.d = new ArrayList();
                                    i = (i == true ? 1 : 0) | 1;
                                }
                                this.d.add(c2396kb.a((AbstractC1732c1) C3354xO.v, c2867qh));
                            } else if (iN == 34) {
                                int i3 = (i == true ? 1 : 0) & 2;
                                i = i;
                                if (i3 != 2) {
                                    this.e = new ArrayList();
                                    i = (i == true ? 1 : 0) | 2;
                                }
                                this.e.add(c2396kb.a((AbstractC1732c1) FO.v, c2867qh));
                            } else if (iN != 42) {
                                if (iN == 242) {
                                    C1999fP c1999fPL = (this.c & 1) == 1 ? this.g.l() : null;
                                    C2075gP c2075gP = (C2075gP) c2396kb.a((AbstractC1732c1) C2075gP.h, c2867qh);
                                    this.g = c2075gP;
                                    if (c1999fPL != null) {
                                        c1999fPL.a(c2075gP);
                                        this.g = c1999fPL.g();
                                    }
                                    this.c |= 1;
                                } else if (iN != 258) {
                                    if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                    }
                                } else {
                                    C2847qP c2847qPL = (this.c & 2) == 2 ? this.h.l() : null;
                                    C2919rP c2919rP = (C2919rP) c2396kb.a((AbstractC1732c1) C2919rP.f, c2867qh);
                                    this.h = c2919rP;
                                    if (c2847qPL != null) {
                                        c2847qPL.a(c2919rP);
                                        this.h = c2847qPL.g();
                                    }
                                    this.c |= 2;
                                }
                            } else {
                                int i4 = (i == true ? 1 : 0) & 4;
                                i = i;
                                if (i4 != 4) {
                                    this.f = new ArrayList();
                                    i = (i == true ? 1 : 0) | 4;
                                }
                                this.f.add(c2396kb.a((AbstractC1732c1) ZO.p, c2867qh));
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
                if (((i == true ? 1 : 0) & 1) == 1) {
                    this.d = Collections.unmodifiableList(this.d);
                }
                if (((i == true ? 1 : 0) & 2) == 2) {
                    this.e = Collections.unmodifiableList(this.e);
                }
                if (((i == true ? 1 : 0) & 4) == 4) {
                    this.f = Collections.unmodifiableList(this.f);
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
        if (((i == true ? 1 : 0) & 1) == 1) {
            this.d = Collections.unmodifiableList(this.d);
        }
        if (((i == true ? 1 : 0) & 2) == 2) {
            this.e = Collections.unmodifiableList(this.e);
        }
        if (((i == true ? 1 : 0) & 4) == 4) {
            this.f = Collections.unmodifiableList(this.f);
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
