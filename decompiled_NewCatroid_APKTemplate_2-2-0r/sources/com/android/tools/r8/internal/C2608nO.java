package com.android.tools.r8.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2608nO extends AbstractC1045Hj {
    private static final C2608nO i;
    public static InterfaceC3429yL j = new C2302jO();
    private final Y5 a;
    private int b;
    private EnumC2460lO c;
    private List d;
    private C3125uO e;
    private EnumC2534mO f;
    private byte g;
    private int h;

    static {
        C2608nO c2608nO = new C2608nO();
        i = c2608nO;
        c2608nO.o();
    }

    /* synthetic */ C2608nO(C2396kb c2396kb, C2867qh c2867qh, int i2) {
        this(c2396kb, c2867qh);
    }

    public static C2608nO h() {
        return i;
    }

    private void o() {
        this.c = EnumC2460lO.b;
        this.d = Collections.emptyList();
        this.e = C3125uO.i();
        this.f = EnumC2534mO.b;
    }

    public static C2380kO p() {
        return C2380kO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int i2 = this.h;
        if (i2 != -1) {
            return i2;
        }
        int iA = (this.b & 1) == 1 ? C2547mb.a(1, this.c.a()) : 0;
        for (int i3 = 0; i3 < this.d.size(); i3++) {
            iA += C2547mb.a(2, (B0) this.d.get(i3));
        }
        if ((this.b & 2) == 2) {
            iA += C2547mb.a(3, this.e);
        }
        if ((this.b & 4) == 4) {
            iA += C2547mb.a(4, this.f.a());
        }
        int size = this.a.size() + iA;
        this.h = size;
        return size;
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C2380kO.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C2380kO.f().a(this);
    }

    public final C3125uO g() {
        return this.e;
    }

    public final List i() {
        return this.d;
    }

    public final EnumC2460lO j() {
        return this.c;
    }

    public final EnumC2534mO k() {
        return this.f;
    }

    public final boolean l() {
        return (this.b & 2) == 2;
    }

    public final boolean m() {
        return (this.b & 1) == 1;
    }

    public final boolean n() {
        return (this.b & 4) == 4;
    }

    /* synthetic */ C2608nO(AbstractC0859Bj abstractC0859Bj, int i2) {
        this(abstractC0859Bj);
    }

    private C2608nO(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.g = (byte) -1;
        this.h = -1;
        this.a = abstractC0859Bj.e();
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
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            if (!((C3125uO) this.d.get(i2)).a()) {
                this.g = (byte) 0;
                return false;
            }
        }
        if (l() && !this.e.a()) {
            this.g = (byte) 0;
            return false;
        }
        this.g = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        c();
        if ((this.b & 1) == 1) {
            c2547mb.c(1, this.c.a());
        }
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            c2547mb.b(2, (B0) this.d.get(i2));
        }
        if ((this.b & 2) == 2) {
            c2547mb.b(3, this.e);
        }
        if ((this.b & 4) == 4) {
            c2547mb.c(4, this.f.a());
        }
        c2547mb.b(this.a);
    }

    private C2608nO() {
        this.g = (byte) -1;
        this.h = -1;
        this.a = Y5.a;
    }

    private C2608nO(C2396kb c2396kb, C2867qh c2867qh) {
        this.g = (byte) -1;
        this.h = -1;
        o();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        char c = 0;
        while (!z) {
            try {
                try {
                    try {
                        int iN = c2396kb.n();
                        if (iN != 0) {
                            if (iN == 8) {
                                int iE = c2396kb.e();
                                EnumC2460lO enumC2460lOC = EnumC2460lO.c(iE);
                                if (enumC2460lOC == null) {
                                    c2547mbA.g(iN);
                                    c2547mbA.g(iE);
                                } else {
                                    this.b |= 1;
                                    this.c = enumC2460lOC;
                                }
                            } else if (iN == 18) {
                                if ((c & 2) != 2) {
                                    this.d = new ArrayList();
                                    c = 2;
                                }
                                this.d.add(c2396kb.a((AbstractC1732c1) C3125uO.m, c2867qh));
                            } else if (iN == 26) {
                                C2987sO c2987sOV = (this.b & 2) == 2 ? this.e.v() : null;
                                C3125uO c3125uO = (C3125uO) c2396kb.a((AbstractC1732c1) C3125uO.m, c2867qh);
                                this.e = c3125uO;
                                if (c2987sOV != null) {
                                    c2987sOV.a(c3125uO);
                                    this.e = c2987sOV.g();
                                }
                                this.b |= 2;
                            } else if (iN != 32) {
                                if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                }
                            } else {
                                int iE2 = c2396kb.e();
                                EnumC2534mO enumC2534mOC = EnumC2534mO.c(iE2);
                                if (enumC2534mOC == null) {
                                    c2547mbA.g(iN);
                                    c2547mbA.g(iE2);
                                } else {
                                    this.b |= 4;
                                    this.f = enumC2534mOC;
                                }
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
