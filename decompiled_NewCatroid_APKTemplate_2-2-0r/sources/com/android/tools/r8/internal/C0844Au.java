package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Au, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0844Au extends AbstractC1045Hj {
    private static final C0844Au l;
    public static InterfaceC3429yL m = new C3464yu();
    private final Y5 a;
    private int b;
    private List c;
    private List d;
    private YA e;
    private PO f;
    private MO g;
    private List h;
    private List i;
    private byte j;
    private int k;

    static {
        C0844Au c0844Au = new C0844Au();
        l = c0844Au;
        c0844Au.q();
    }

    /* synthetic */ C0844Au(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C0844Au h() {
        return l;
    }

    private void q() {
        this.c = Collections.emptyList();
        this.d = Collections.emptyList();
        this.e = XA.b;
        this.f = PO.g();
        this.g = MO.g();
        this.h = Collections.emptyList();
        this.i = Collections.emptyList();
    }

    public static C3534zu r() {
        return C3534zu.f();
    }

    public final List g() {
        return this.h;
    }

    public final YA i() {
        return this.e;
    }

    public final List j() {
        return this.d;
    }

    public final List k() {
        return this.i;
    }

    public final List l() {
        return this.c;
    }

    public final MO m() {
        return this.g;
    }

    public final PO n() {
        return this.f;
    }

    public final boolean o() {
        return (this.b & 2) == 2;
    }

    public final boolean p() {
        return (this.b & 1) == 1;
    }

    /* synthetic */ C0844Au(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C3534zu.f().a(this);
    }

    private C0844Au(AbstractC0859Bj abstractC0859Bj) {
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
        int iA = 0;
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            iA += C2547mb.a(1, (B0) this.c.get(i2));
        }
        for (int i3 = 0; i3 < this.d.size(); i3++) {
            iA += C2547mb.a(2, (B0) this.d.get(i3));
        }
        int iA2 = 0;
        for (int i4 = 0; i4 < this.e.size(); i4++) {
            iA2 += C2547mb.a(this.e.g(i4));
        }
        int size = this.e.size() + iA + iA2;
        if ((this.b & 1) == 1) {
            size += C2547mb.a(4, this.f);
        }
        if ((this.b & 2) == 2) {
            size += C2547mb.a(5, this.g);
        }
        for (int i5 = 0; i5 < this.h.size(); i5++) {
            size += C2547mb.a(6, (B0) this.h.get(i5));
        }
        for (int i6 = 0; i6 < this.i.size(); i6++) {
            size += C2547mb.a(16, (B0) this.i.get(i6));
        }
        int size2 = this.a.size() + size;
        this.k = size2;
        return size2;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C3534zu.f();
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
        for (int i = 0; i < this.c.size(); i++) {
            if (!((C0938Du) this.c.get(i)).a()) {
                this.j = (byte) 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            if (!((C0938Du) this.d.get(i2)).a()) {
                this.j = (byte) 0;
                return false;
            }
        }
        if (o() && !this.g.a()) {
            this.j = (byte) 0;
            return false;
        }
        for (int i3 = 0; i3 < this.h.size(); i3++) {
            if (!((XN) this.h.get(i3)).a()) {
                this.j = (byte) 0;
                return false;
            }
        }
        for (int i4 = 0; i4 < this.i.size(); i4++) {
            if (!((C1761cO) this.i.get(i4)).a()) {
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
        for (int i = 0; i < this.c.size(); i++) {
            c2547mb.b(1, (B0) this.c.get(i));
        }
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            c2547mb.b(2, (B0) this.d.get(i2));
        }
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            c2547mb.a(3, this.e.g(i3));
        }
        if ((this.b & 1) == 1) {
            c2547mb.b(4, this.f);
        }
        if ((this.b & 2) == 2) {
            c2547mb.b(5, this.g);
        }
        for (int i4 = 0; i4 < this.h.size(); i4++) {
            c2547mb.b(6, (B0) this.h.get(i4));
        }
        for (int i5 = 0; i5 < this.i.size(); i5++) {
            c2547mb.b(16, (B0) this.i.get(i5));
        }
        c2547mb.b(this.a);
    }

    private C0844Au() {
        this.j = (byte) -1;
        this.k = -1;
        this.a = Y5.a;
    }

    private C0844Au(C2396kb c2396kb, C2867qh c2867qh) {
        this.j = (byte) -1;
        this.k = -1;
        q();
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
                            if (iN == 10) {
                                int i2 = (i == true ? 1 : 0) & 1;
                                i = i;
                                if (i2 != 1) {
                                    this.c = new ArrayList();
                                    i = (i == true ? 1 : 0) | 1;
                                }
                                this.c.add(c2396kb.a((AbstractC1732c1) C0938Du.p, c2867qh));
                            } else if (iN == 18) {
                                int i3 = (i == true ? 1 : 0) & 2;
                                i = i;
                                if (i3 != 2) {
                                    this.d = new ArrayList();
                                    i = (i == true ? 1 : 0) | 2;
                                }
                                this.d.add(c2396kb.a((AbstractC1732c1) C0938Du.p, c2867qh));
                            } else if (iN != 26) {
                                if (iN == 34) {
                                    OO ooI = (this.b & 1) == 1 ? this.f.i() : null;
                                    PO po = (PO) c2396kb.a((AbstractC1732c1) PO.f, c2867qh);
                                    this.f = po;
                                    if (ooI != null) {
                                        ooI.a(po);
                                        this.f = ooI.g();
                                    }
                                    this.b |= 1;
                                } else if (iN == 42) {
                                    HO hoI = (this.b & 2) == 2 ? this.g.i() : null;
                                    MO mo = (MO) c2396kb.a((AbstractC1732c1) MO.f, c2867qh);
                                    this.g = mo;
                                    if (hoI != null) {
                                        hoI.a(mo);
                                        this.g = hoI.g();
                                    }
                                    this.b |= 2;
                                } else if (iN == 50) {
                                    int i4 = (i == true ? 1 : 0) & 32;
                                    i = i;
                                    if (i4 != 32) {
                                        this.h = new ArrayList();
                                        i = (i == true ? 1 : 0) | 32;
                                    }
                                    this.h.add(c2396kb.a((AbstractC1732c1) XN.h, c2867qh));
                                } else if (iN != 130) {
                                    if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                    }
                                } else {
                                    int i5 = (i == true ? 1 : 0) & 64;
                                    i = i;
                                    if (i5 != 64) {
                                        this.i = new ArrayList();
                                        i = (i == true ? 1 : 0) | 64;
                                    }
                                    this.i.add(c2396kb.a((AbstractC1732c1) C1761cO.K, c2867qh));
                                }
                            } else {
                                Y5 y5C = c2396kb.c();
                                int i6 = (i == true ? 1 : 0) & 4;
                                i = i;
                                if (i6 != 4) {
                                    this.e = new XA();
                                    i = (i == true ? 1 : 0) | 4;
                                }
                                this.e.a(y5C);
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
                    this.c = Collections.unmodifiableList(this.c);
                }
                if (((i == true ? 1 : 0) & 2) == 2) {
                    this.d = Collections.unmodifiableList(this.d);
                }
                if (((i == true ? 1 : 0) & 4) == 4) {
                    this.e = this.e.h();
                }
                if (((i == true ? 1 : 0) & 32) == 32) {
                    this.h = Collections.unmodifiableList(this.h);
                }
                if (((i == true ? 1 : 0) & 64) == 64) {
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
        if (((i == true ? 1 : 0) & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
        }
        if (((i == true ? 1 : 0) & 2) == 2) {
            this.d = Collections.unmodifiableList(this.d);
        }
        if (((i == true ? 1 : 0) & 4) == 4) {
            this.e = this.e.h();
        }
        if (((i == true ? 1 : 0) & 32) == 32) {
            this.h = Collections.unmodifiableList(this.h);
        }
        if (((i == true ? 1 : 0) & 64) == 64) {
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
