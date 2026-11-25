package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1998fO extends AbstractC0958Ej {
    private static final C1998fO i;
    public static InterfaceC3429yL j = new C1839dO();
    private final Y5 b;
    private int c;
    private int d;
    private List e;
    private List f;
    private byte g;
    private int h;

    static {
        C1998fO c1998fO = new C1998fO();
        i = c1998fO;
        c1998fO.p();
    }

    /* synthetic */ C1998fO(C2396kb c2396kb, C2867qh c2867qh, int i2) {
        this(c2396kb, c2867qh);
    }

    public static C1998fO k() {
        return i;
    }

    private void p() {
        this.d = 6;
        this.e = Collections.emptyList();
        this.f = Collections.emptyList();
    }

    public static C1921eO q() {
        return C1921eO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1921eO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1921eO.f().a(this);
    }

    public final int l() {
        return this.d;
    }

    public final List m() {
        return this.e;
    }

    public final List n() {
        return this.f;
    }

    public final boolean o() {
        return (this.c & 1) == 1;
    }

    /* synthetic */ C1998fO(AbstractC0899Cj abstractC0899Cj, int i2) {
        this(abstractC0899Cj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i2 = this.h;
        if (i2 != -1) {
            return i2;
        }
        int iB = (this.c & 1) == 1 ? C2547mb.b(1, this.d) : 0;
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            iB += C2547mb.a(2, (B0) this.e.get(i3));
        }
        int iA = 0;
        for (int i4 = 0; i4 < this.f.size(); i4++) {
            iA = YN.a((Integer) this.f.get(i4), iA);
        }
        int size = this.b.size() + (this.f.size() * 2) + iB + iA + h();
        this.h = size;
        return size;
    }

    private C1998fO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.g = (byte) -1;
        this.h = -1;
        this.b = abstractC0899Cj.e();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.g;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            if (!((C2303jP) this.e.get(i2)).a()) {
                this.g = (byte) 0;
                return false;
            }
        }
        if (!g()) {
            this.g = (byte) 0;
            return false;
        }
        this.g = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return i;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        if ((this.c & 1) == 1) {
            c2547mb.d(1, this.d);
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            c2547mb.b(2, (B0) this.e.get(i2));
        }
        for (int i3 = 0; i3 < this.f.size(); i3++) {
            c2547mb.d(31, ((Integer) this.f.get(i3)).intValue());
        }
        c0927DjJ.a(19000, c2547mb);
        c2547mb.b(this.b);
    }

    private C1998fO() {
        this.g = (byte) -1;
        this.h = -1;
        this.b = Y5.a;
    }

    private C1998fO(C2396kb c2396kb, C2867qh c2867qh) {
        this.g = (byte) -1;
        this.h = -1;
        p();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        int i2 = 0;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        if (iN != 0) {
                            if (iN == 8) {
                                this.c |= 1;
                                this.d = c2396kb.g();
                            } else if (iN == 18) {
                                if ((i2 & 2) != 2) {
                                    this.e = new ArrayList();
                                    i2 |= 2;
                                }
                                this.e.add(c2396kb.a((AbstractC1732c1) C2303jP.m, c2867qh));
                            } else if (iN == 248) {
                                if ((i2 & 4) != 4) {
                                    this.f = new ArrayList();
                                    i2 |= 4;
                                }
                                this.f.add(Integer.valueOf(c2396kb.g()));
                            } else if (iN != 250) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                int iC = c2396kb.c(c2396kb.j());
                                if ((i2 & 4) != 4 && c2396kb.a() > 0) {
                                    this.f = new ArrayList();
                                    i2 |= 4;
                                }
                                while (c2396kb.a() > 0) {
                                    this.f.add(Integer.valueOf(c2396kb.g()));
                                }
                                c2396kb.b(iC);
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
                if ((i2 & 2) == 2) {
                    this.e = Collections.unmodifiableList(this.e);
                }
                if ((i2 & 4) == 4) {
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
        if ((i2 & 2) == 2) {
            this.e = Collections.unmodifiableList(this.e);
        }
        if ((i2 & 4) == 4) {
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
