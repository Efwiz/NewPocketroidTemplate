package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XN extends AbstractC1045Hj {
    private static final XN g;
    public static InterfaceC3429yL h = new ON();
    private final Y5 a;
    private int b;
    private int c;
    private List d;
    private byte e;
    private int f;

    static {
        XN xn = new XN();
        g = xn;
        xn.k();
    }

    /* synthetic */ XN(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static XN h() {
        return g;
    }

    private void k() {
        this.c = 0;
        this.d = Collections.emptyList();
    }

    public static WN l() {
        return WN.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.f;
        if (i != -1) {
            return i;
        }
        int iB = (this.b & 1) == 1 ? C2547mb.b(1, this.c) : 0;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            iB += C2547mb.a(2, (B0) this.d.get(i2));
        }
        int size = this.a.size() + iB;
        this.f = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return WN.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return WN.f().a(this);
    }

    public final List g() {
        return this.d;
    }

    public final int i() {
        return this.c;
    }

    public final boolean j() {
        return (this.b & 1) == 1;
    }

    public final WN m() {
        return WN.f().a(this);
    }

    /* synthetic */ XN(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    private XN(AbstractC0859Bj abstractC0859Bj) {
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
        for (int i = 0; i < this.d.size(); i++) {
            if (!((VN) this.d.get(i)).a()) {
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
        if ((this.b & 1) == 1) {
            c2547mb.d(1, this.c);
        }
        for (int i = 0; i < this.d.size(); i++) {
            c2547mb.b(2, (B0) this.d.get(i));
        }
        c2547mb.b(this.a);
    }

    private XN() {
        this.e = (byte) -1;
        this.f = -1;
        this.a = Y5.a;
    }

    private XN(C2396kb c2396kb, C2867qh c2867qh) {
        this.e = (byte) -1;
        this.f = -1;
        k();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        char c = 0;
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
                            if ((c & 2) != 2) {
                                this.d = new ArrayList();
                                c = 2;
                            }
                            this.d.add(c2396kb.a((AbstractC1732c1) VN.h, c2867qh));
                        }
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((c & 2) == 2) {
                        this.d = Collections.unmodifiableList(this.d);
                    }
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
        if ((c & 2) == 2) {
            this.d = Collections.unmodifiableList(this.d);
        }
        try {
            c2547mbA.b();
        } catch (IOException unused2) {
        } finally {
            this.a = x5D.c();
        }
    }
}
