package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1487Wu extends AbstractC1045Hj {
    private static final C1487Wu m;
    public static InterfaceC3429yL n = new C1387Tu();
    private final Y5 a;
    private int b;
    private int c;
    private int d;
    private Object e;
    private EnumC1461Vu f;
    private List g;
    private int h;
    private List i;
    private int j;
    private byte k;
    private int l;

    static {
        C1487Wu c1487Wu = new C1487Wu();
        m = c1487Wu;
        c1487Wu.t();
    }

    /* synthetic */ C1487Wu(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C1487Wu g() {
        return m;
    }

    private void t() {
        this.c = 1;
        this.d = 0;
        this.e = "";
        this.f = EnumC1461Vu.b;
        this.g = Collections.emptyList();
        this.i = Collections.emptyList();
    }

    public static C1435Uu u() {
        return C1435Uu.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C1435Uu.f().a(this);
    }

    public final EnumC1461Vu h() {
        return this.f;
    }

    public final int i() {
        return this.d;
    }

    public final int j() {
        return this.c;
    }

    public final int k() {
        return this.i.size();
    }

    public final List l() {
        return this.i;
    }

    public final String m() {
        Object obj = this.e;
        if (obj instanceof String) {
            return (String) obj;
        }
        Y5 y5 = (Y5) obj;
        y5.getClass();
        try {
            String strF = y5.f();
            if (y5.c()) {
                this.e = strF;
            }
            return strF;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public final int n() {
        return this.g.size();
    }

    public final List o() {
        return this.g;
    }

    public final boolean p() {
        return (this.b & 8) == 8;
    }

    public final boolean q() {
        return (this.b & 2) == 2;
    }

    public final boolean r() {
        return (this.b & 1) == 1;
    }

    public final boolean s() {
        return (this.b & 4) == 4;
    }

    public final C1435Uu v() {
        return C1435Uu.f().a(this);
    }

    /* synthetic */ C1487Wu(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C1435Uu.f();
    }

    private C1487Wu(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.h = -1;
        this.j = -1;
        this.k = (byte) -1;
        this.l = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        Y5 y5A;
        int i = this.l;
        if (i != -1) {
            return i;
        }
        int iB = (this.b & 1) == 1 ? C2547mb.b(1, this.c) : 0;
        if ((this.b & 2) == 2) {
            iB += C2547mb.b(2, this.d);
        }
        if ((this.b & 8) == 8) {
            iB += C2547mb.a(3, this.f.a());
        }
        int iA = 0;
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            iA = YN.a((Integer) this.g.get(i2), iA);
        }
        int iA2 = iB + iA;
        if (!this.g.isEmpty()) {
            iA2 = iA2 + 1 + C2547mb.a(iA);
        }
        this.h = iA;
        int iA3 = 0;
        for (int i3 = 0; i3 < this.i.size(); i3++) {
            iA3 = YN.a((Integer) this.i.get(i3), iA3);
        }
        int size = iA2 + iA3;
        if (!this.i.isEmpty()) {
            size = size + 1 + C2547mb.a(iA3);
        }
        this.j = iA3;
        if ((this.b & 4) == 4) {
            Object obj = this.e;
            if (obj instanceof String) {
                y5A = Y5.a((String) obj);
                this.e = y5A;
            } else {
                y5A = (Y5) obj;
            }
            size += y5A.size() + C2547mb.b(y5A.size()) + C2547mb.c(6);
        }
        int size2 = this.a.size() + size;
        this.l = size2;
        return size2;
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
        this.k = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        Y5 y5A;
        c();
        if ((this.b & 1) == 1) {
            c2547mb.d(1, this.c);
        }
        if ((this.b & 2) == 2) {
            c2547mb.d(2, this.d);
        }
        if ((this.b & 8) == 8) {
            c2547mb.c(3, this.f.a());
        }
        if (this.g.size() > 0) {
            c2547mb.g(34);
            c2547mb.g(this.h);
        }
        for (int i = 0; i < this.g.size(); i++) {
            c2547mb.d(((Integer) this.g.get(i)).intValue());
        }
        if (this.i.size() > 0) {
            c2547mb.g(42);
            c2547mb.g(this.j);
        }
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            c2547mb.d(((Integer) this.i.get(i2)).intValue());
        }
        if ((this.b & 4) == 4) {
            Object obj = this.e;
            if (obj instanceof String) {
                y5A = Y5.a((String) obj);
                this.e = y5A;
            } else {
                y5A = (Y5) obj;
            }
            c2547mb.a(6, y5A);
        }
        c2547mb.b(this.a);
    }

    private C1487Wu() {
        this.h = -1;
        this.j = -1;
        this.k = (byte) -1;
        this.l = -1;
        this.a = Y5.a;
    }

    private C1487Wu(C2396kb c2396kb, C2867qh c2867qh) {
        this.h = -1;
        this.j = -1;
        this.k = (byte) -1;
        this.l = -1;
        t();
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
                            this.b |= 1;
                            this.c = c2396kb.g();
                        } else if (iN == 16) {
                            this.b |= 2;
                            this.d = c2396kb.g();
                        } else if (iN == 24) {
                            int iE = c2396kb.e();
                            EnumC1461Vu enumC1461VuC = EnumC1461Vu.c(iE);
                            if (enumC1461VuC == null) {
                                c2547mbA.g(iN);
                                c2547mbA.g(iE);
                            } else {
                                this.b |= 8;
                                this.f = enumC1461VuC;
                            }
                        } else if (iN == 32) {
                            if ((i & 16) != 16) {
                                this.g = new ArrayList();
                                i |= 16;
                            }
                            this.g.add(Integer.valueOf(c2396kb.g()));
                        } else if (iN == 34) {
                            int iC = c2396kb.c(c2396kb.j());
                            if ((i & 16) != 16 && c2396kb.a() > 0) {
                                this.g = new ArrayList();
                                i |= 16;
                            }
                            while (c2396kb.a() > 0) {
                                this.g.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC);
                        } else if (iN == 40) {
                            if ((i & 32) != 32) {
                                this.i = new ArrayList();
                                i |= 32;
                            }
                            this.i.add(Integer.valueOf(c2396kb.g()));
                        } else if (iN == 42) {
                            int iC2 = c2396kb.c(c2396kb.j());
                            if ((i & 32) != 32 && c2396kb.a() > 0) {
                                this.i = new ArrayList();
                                i |= 32;
                            }
                            while (c2396kb.a() > 0) {
                                this.i.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC2);
                        } else if (iN != 50) {
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                            }
                        } else {
                            Y5 y5C = c2396kb.c();
                            this.b |= 4;
                            this.e = y5C;
                        }
                    }
                    z = true;
                } catch (C1054Hs e) {
                    throw e.a(this);
                } catch (IOException e2) {
                    throw new C1054Hs(e2.getMessage()).a(this);
                }
            } catch (Throwable th) {
                if ((i & 16) == 16) {
                    this.g = Collections.unmodifiableList(this.g);
                }
                if ((i & 32) == 32) {
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
        if ((i & 16) == 16) {
            this.g = Collections.unmodifiableList(this.g);
        }
        if ((i & 32) == 32) {
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
