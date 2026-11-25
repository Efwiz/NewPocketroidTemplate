package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2846qO extends AbstractC0958Ej {
    private static final C2846qO g;
    public static InterfaceC3429yL h = new C2699oO();
    private final Y5 b;
    private int c;
    private int d;
    private byte e;
    private int f;

    static {
        C2846qO c2846qO = new C2846qO();
        g = c2846qO;
        c2846qO.d = 0;
    }

    /* synthetic */ C2846qO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C2846qO k() {
        return g;
    }

    private void n() {
        this.d = 0;
    }

    public static C2775pO o() {
        return C2775pO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.f;
        if (i != -1) {
            return i;
        }
        int size = this.b.size() + ((this.c & 1) == 1 ? C2547mb.b(1, this.d) : 0) + h();
        this.f = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C2775pO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C2775pO.f().a(this);
    }

    public final int l() {
        return this.d;
    }

    public final boolean m() {
        return (this.c & 1) == 1;
    }

    /* synthetic */ C2846qO(AbstractC0899Cj abstractC0899Cj, int i) {
        this(abstractC0899Cj);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final B0 b() {
        return g;
    }

    private C2846qO(AbstractC0899Cj abstractC0899Cj) {
        super(abstractC0899Cj);
        this.e = (byte) -1;
        this.f = -1;
        this.b = abstractC0899Cj.e();
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
        if (!g()) {
            this.e = (byte) 0;
            return false;
        }
        this.e = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        C0927Dj c0927DjJ = j();
        if ((this.c & 1) == 1) {
            c2547mb.d(1, this.d);
        }
        c0927DjJ.a(200, c2547mb);
        c2547mb.b(this.b);
    }

    private C2846qO() {
        this.e = (byte) -1;
        this.f = -1;
        this.b = Y5.a;
    }

    private C2846qO(C2396kb c2396kb, C2867qh c2867qh) {
        this.e = (byte) -1;
        this.f = -1;
        n();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    if (iN != 0) {
                        if (iN != 8) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            this.c |= 1;
                            this.d = c2396kb.g();
                        }
                    }
                    z = true;
                } catch (Throwable th) {
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
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.b = x5D.c();
        }
        i();
    }
}
