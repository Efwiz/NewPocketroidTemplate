package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1516Xu extends AbstractC1045Hj {
    private static final C1516Xu g;
    public static InterfaceC3429yL h = new C1334Ru();
    private final Y5 a;
    private List b;
    private List c;
    private int d;
    private byte e;
    private int f;

    static {
        C1516Xu c1516Xu = new C1516Xu();
        g = c1516Xu;
        c1516Xu.j();
    }

    /* synthetic */ C1516Xu(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C1516Xu g() {
        return g;
    }

    private void j() {
        this.b = Collections.emptyList();
        this.c = Collections.emptyList();
    }

    public static C1360Su k() {
        return C1360Su.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1360Su.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1360Su.f().a(this);
    }

    public final List h() {
        return this.c;
    }

    public final List i() {
        return this.b;
    }

    /* synthetic */ C1516Xu(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i = this.f;
        if (i != -1) {
            return i;
        }
        int iA = 0;
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            iA += C2547mb.a(1, (B0) this.b.get(i2));
        }
        int iA2 = 0;
        for (int i3 = 0; i3 < this.c.size(); i3++) {
            iA2 = YN.a((Integer) this.c.get(i3), iA2);
        }
        int iA3 = iA + iA2;
        if (!this.c.isEmpty()) {
            iA3 = iA3 + 1 + C2547mb.a(iA2);
        }
        this.d = iA2;
        int size = this.a.size() + iA3;
        this.f = size;
        return size;
    }

    private C1516Xu(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.d = -1;
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
        this.e = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        for (int i = 0; i < this.b.size(); i++) {
            c2547mb.b(1, (B0) this.b.get(i));
        }
        if (this.c.size() > 0) {
            c2547mb.g(42);
            c2547mb.g(this.d);
        }
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            c2547mb.d(((Integer) this.c.get(i2)).intValue());
        }
        c2547mb.b(this.a);
    }

    private C1516Xu() {
        this.d = -1;
        this.e = (byte) -1;
        this.f = -1;
        this.a = Y5.a;
    }

    private C1516Xu(C2396kb c2396kb, C2867qh c2867qh) {
        this.d = -1;
        this.e = (byte) -1;
        this.f = -1;
        j();
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
                                if ((i & 1) != 1) {
                                    this.b = new ArrayList();
                                    i |= 1;
                                }
                                this.b.add(c2396kb.a((AbstractC1732c1) C1487Wu.n, c2867qh));
                            } else if (iN == 40) {
                                if ((i & 2) != 2) {
                                    this.c = new ArrayList();
                                    i |= 2;
                                }
                                this.c.add(Integer.valueOf(c2396kb.g()));
                            } else if (iN != 42) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                int iC = c2396kb.c(c2396kb.j());
                                if ((i & 2) != 2 && c2396kb.a() > 0) {
                                    this.c = new ArrayList();
                                    i |= 2;
                                }
                                while (c2396kb.a() > 0) {
                                    this.c.add(Integer.valueOf(c2396kb.g()));
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
                if ((i & 1) == 1) {
                    this.b = Collections.unmodifiableList(this.b);
                }
                if ((i & 2) == 2) {
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
        if ((i & 1) == 1) {
            this.b = Collections.unmodifiableList(this.b);
        }
        if ((i & 2) == 2) {
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
