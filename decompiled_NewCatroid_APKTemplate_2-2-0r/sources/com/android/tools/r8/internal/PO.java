package com.android.tools.r8.internal;

import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PO extends AbstractC1045Hj {
    private static final PO e;
    public static InterfaceC3429yL f = new NO();
    private final Y5 a;
    private YA b;
    private byte c;
    private int d;

    static {
        PO po = new PO();
        e = po;
        po.b = XA.b;
    }

    /* synthetic */ PO(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static PO g() {
        return e;
    }

    private void h() {
        this.b = XA.b;
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.d;
        if (i != -1) {
            return i;
        }
        int iA = 0;
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            iA += C2547mb.a(this.b.g(i2));
        }
        int size = this.a.size() + this.b.size() + iA;
        this.d = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return OO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return OO.f().a(this);
    }

    public final OO i() {
        return OO.f().a(this);
    }

    /* synthetic */ PO(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    private PO(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.c = (byte) -1;
        this.d = -1;
        this.a = abstractC0859Bj.e();
    }

    public final String a(int i) {
        return (String) this.b.get(i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.c;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        this.c = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        for (int i = 0; i < this.b.size(); i++) {
            c2547mb.a(1, this.b.g(i));
        }
        c2547mb.b(this.a);
    }

    public static OO c(PO po) {
        return OO.f().a(po);
    }

    private PO() {
        this.c = (byte) -1;
        this.d = -1;
        this.a = Y5.a;
    }

    private PO(C2396kb c2396kb, C2867qh c2867qh) {
        this.c = (byte) -1;
        this.d = -1;
        h();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    if (iN != 0) {
                        if (iN != 10) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            Y5 y5C = c2396kb.c();
                            if (!z2) {
                                this.b = new XA();
                                z2 = true;
                            }
                            this.b.a(y5C);
                        }
                    }
                    z = true;
                } catch (C1054Hs e2) {
                    throw e2.a(this);
                } catch (IOException e3) {
                    throw new C1054Hs(e3.getMessage()).a(this);
                }
            } catch (Throwable th) {
                if (z2) {
                    this.b = this.b.h();
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
            this.b = this.b.h();
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
