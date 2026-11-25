package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2075gP extends AbstractC1045Hj {
    private static final C2075gP g;
    public static InterfaceC3429yL h = new C1922eP();
    private final Y5 a;
    private int b;
    private List c;
    private int d;
    private byte e;
    private int f;

    static {
        C2075gP c2075gP = new C2075gP();
        g = c2075gP;
        c2075gP.k();
    }

    /* synthetic */ C2075gP(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C2075gP g() {
        return g;
    }

    private void k() {
        this.c = Collections.emptyList();
        this.d = -1;
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.f;
        if (i != -1) {
            return i;
        }
        int iB = 0;
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            iB += C2547mb.a(1, (B0) this.c.get(i2));
        }
        if ((this.b & 1) == 1) {
            iB += C2547mb.b(2, this.d);
        }
        int size = this.a.size() + iB;
        this.f = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1999fP.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1999fP.f().a(this);
    }

    public final int h() {
        return this.d;
    }

    public final List i() {
        return this.c;
    }

    public final boolean j() {
        return (this.b & 1) == 1;
    }

    public final C1999fP l() {
        return C1999fP.f().a(this);
    }

    /* synthetic */ C2075gP(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    private C2075gP(AbstractC0859Bj abstractC0859Bj) {
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
        for (int i = 0; i < this.c.size(); i++) {
            if (!((WO) this.c.get(i)).a()) {
                this.e = (byte) 0;
                return false;
            }
        }
        this.e = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        for (int i = 0; i < this.c.size(); i++) {
            c2547mb.b(1, (B0) this.c.get(i));
        }
        if ((this.b & 1) == 1) {
            c2547mb.d(2, this.d);
        }
        c2547mb.b(this.a);
    }

    private C2075gP() {
        this.e = (byte) -1;
        this.f = -1;
        this.a = Y5.a;
    }

    private C2075gP(C2396kb c2396kb, C2867qh c2867qh) {
        this.e = (byte) -1;
        this.f = -1;
        k();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        if (iN != 0) {
                            if (iN == 10) {
                                if (!z2) {
                                    this.c = new ArrayList();
                                    z2 = true;
                                }
                                this.c.add(c2396kb.a((AbstractC1732c1) WO.u, c2867qh));
                            } else if (iN != 16) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                this.b |= 1;
                                this.d = c2396kb.g();
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
                if (z2) {
                    this.c = Collections.unmodifiableList(this.c);
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
        if (z2) {
            this.c = Collections.unmodifiableList(this.c);
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
