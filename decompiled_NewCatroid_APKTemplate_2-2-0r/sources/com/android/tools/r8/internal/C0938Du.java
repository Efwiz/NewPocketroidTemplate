package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Du, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0938Du extends AbstractC1045Hj {
    private static final C0938Du o;
    public static InterfaceC3429yL p = new C0870Bu();
    private final Y5 a;
    private int b;
    private Object c;
    private YA d;
    private List e;
    private int f;
    private YA g;
    private YA h;
    private List i;
    private int j;
    private List k;
    private int l;
    private byte m;
    private int n;

    static {
        C0938Du c0938Du = new C0938Du();
        o = c0938Du;
        c0938Du.p();
    }

    /* synthetic */ C0938Du(C2396kb c2396kb, C2867qh c2867qh, int i) {
        this(c2396kb, c2867qh);
    }

    public static C0938Du j() {
        return o;
    }

    private void p() {
        this.c = "";
        C3178v10 c3178v10 = XA.b;
        this.d = c3178v10;
        this.e = Collections.emptyList();
        this.g = c3178v10;
        this.h = c3178v10;
        this.i = Collections.emptyList();
        this.k = Collections.emptyList();
    }

    public static C0910Cu q() {
        return C0910Cu.f();
    }

    public final YA i() {
        return this.h;
    }

    public final List k() {
        return this.e;
    }

    public final YA l() {
        return this.g;
    }

    public final String m() {
        Object obj = this.c;
        if (obj instanceof String) {
            return (String) obj;
        }
        Y5 y5 = (Y5) obj;
        y5.getClass();
        try {
            String strF = y5.f();
            if (y5.c()) {
                this.c = strF;
            }
            return strF;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public final YA n() {
        return this.d;
    }

    public final boolean o() {
        return (this.b & 1) == 1;
    }

    /* synthetic */ C0938Du(AbstractC0859Bj abstractC0859Bj, int i) {
        this(abstractC0859Bj);
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 d() {
        return C0910Cu.f();
    }

    @Override // com.android.tools.r8.internal.B0
    public final A0 e() {
        return C0910Cu.f().a(this);
    }

    public final List g() {
        return this.i;
    }

    public final List h() {
        return this.k;
    }

    private C0938Du(AbstractC0859Bj abstractC0859Bj) {
        super(0);
        this.f = -1;
        this.j = -1;
        this.l = -1;
        this.m = (byte) -1;
        this.n = -1;
        this.a = abstractC0859Bj.e();
    }

    @Override // com.android.tools.r8.internal.B0
    public final int c() {
        int size;
        Y5 y5A;
        int i = this.n;
        if (i != -1) {
            return i;
        }
        if ((this.b & 1) == 1) {
            Object obj = this.c;
            if (obj instanceof String) {
                y5A = Y5.a((String) obj);
                this.c = y5A;
            } else {
                y5A = (Y5) obj;
            }
            size = y5A.size() + C2547mb.b(y5A.size()) + C2547mb.c(1);
        } else {
            size = 0;
        }
        int iA = 0;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            iA += C2547mb.a(this.d.g(i2));
        }
        int size2 = this.d.size() + size + iA;
        int iA2 = 0;
        for (int i3 = 0; i3 < this.e.size(); i3++) {
            iA2 = YN.a((Integer) this.e.get(i3), iA2);
        }
        int iA3 = size2 + iA2;
        if (!this.e.isEmpty()) {
            iA3 = iA3 + 1 + C2547mb.a(iA2);
        }
        this.f = iA2;
        int iA4 = 0;
        for (int i4 = 0; i4 < this.g.size(); i4++) {
            iA4 += C2547mb.a(this.g.g(i4));
        }
        int size3 = this.g.size() + iA3 + iA4;
        int iA5 = 0;
        for (int i5 = 0; i5 < this.h.size(); i5++) {
            iA5 += C2547mb.a(this.h.g(i5));
        }
        int size4 = this.h.size() + size3 + iA5;
        int iA6 = 0;
        for (int i6 = 0; i6 < this.k.size(); i6++) {
            iA6 = YN.a((Integer) this.k.get(i6), iA6);
        }
        int iA7 = size4 + iA6;
        if (!this.k.isEmpty()) {
            iA7 = iA7 + 1 + C2547mb.a(iA6);
        }
        this.l = iA6;
        int iA8 = 0;
        for (int i7 = 0; i7 < this.i.size(); i7++) {
            iA8 = YN.a((Integer) this.i.get(i7), iA8);
        }
        int iA9 = iA7 + iA8;
        if (!this.i.isEmpty()) {
            iA9 = iA9 + 1 + C2547mb.a(iA8);
        }
        this.j = iA8;
        int size5 = this.a.size() + iA9;
        this.n = size5;
        return size5;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3046tE
    public final boolean a() {
        byte b = this.m;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (!o()) {
            this.m = (byte) 0;
            return false;
        }
        this.m = (byte) 1;
        return true;
    }

    @Override // com.android.tools.r8.internal.B0
    public final void a(C2547mb c2547mb) throws IOException {
        Y5 y5A;
        c();
        if ((this.b & 1) == 1) {
            Object obj = this.c;
            if (obj instanceof String) {
                y5A = Y5.a((String) obj);
                this.c = y5A;
            } else {
                y5A = (Y5) obj;
            }
            c2547mb.a(1, y5A);
        }
        for (int i = 0; i < this.d.size(); i++) {
            c2547mb.a(2, this.d.g(i));
        }
        if (this.e.size() > 0) {
            c2547mb.g(26);
            c2547mb.g(this.f);
        }
        for (int i2 = 0; i2 < this.e.size(); i2++) {
            c2547mb.d(((Integer) this.e.get(i2)).intValue());
        }
        for (int i3 = 0; i3 < this.g.size(); i3++) {
            c2547mb.a(4, this.g.g(i3));
        }
        for (int i4 = 0; i4 < this.h.size(); i4++) {
            c2547mb.a(5, this.h.g(i4));
        }
        if (this.k.size() > 0) {
            c2547mb.g(50);
            c2547mb.g(this.l);
        }
        for (int i5 = 0; i5 < this.k.size(); i5++) {
            c2547mb.d(((Integer) this.k.get(i5)).intValue());
        }
        if (this.i.size() > 0) {
            c2547mb.g(58);
            c2547mb.g(this.j);
        }
        for (int i6 = 0; i6 < this.i.size(); i6++) {
            c2547mb.d(((Integer) this.i.get(i6)).intValue());
        }
        c2547mb.b(this.a);
    }

    private C0938Du() {
        this.f = -1;
        this.j = -1;
        this.l = -1;
        this.m = (byte) -1;
        this.n = -1;
        this.a = Y5.a;
    }

    private C0938Du(C2396kb c2396kb, C2867qh c2867qh) {
        this.f = -1;
        this.j = -1;
        this.l = -1;
        this.m = (byte) -1;
        this.n = -1;
        p();
        X5 x5D = Y5.d();
        C2547mb c2547mbA = C2547mb.a(x5D, 1);
        boolean z = false;
        int i = 0;
        while (!z) {
            try {
                try {
                    int iN = c2396kb.n();
                    switch (iN) {
                        case 0:
                            break;
                        case 10:
                            Y5 y5C = c2396kb.c();
                            this.b |= 1;
                            this.c = y5C;
                            continue;
                        case 18:
                            Y5 y5C2 = c2396kb.c();
                            if ((i & 2) != 2) {
                                this.d = new XA();
                                i |= 2;
                            }
                            this.d.a(y5C2);
                            continue;
                        case 24:
                            if ((i & 4) != 4) {
                                this.e = new ArrayList();
                                i |= 4;
                            }
                            this.e.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 26:
                            int iC = c2396kb.c(c2396kb.j());
                            if ((i & 4) != 4 && c2396kb.a() > 0) {
                                this.e = new ArrayList();
                                i |= 4;
                            }
                            while (c2396kb.a() > 0) {
                                this.e.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC);
                            continue;
                        case 34:
                            Y5 y5C3 = c2396kb.c();
                            if ((i & 8) != 8) {
                                this.g = new XA();
                                i |= 8;
                            }
                            this.g.a(y5C3);
                            continue;
                        case 42:
                            Y5 y5C4 = c2396kb.c();
                            if ((i & 16) != 16) {
                                this.h = new XA();
                                i |= 16;
                            }
                            this.h.a(y5C4);
                            continue;
                        case 48:
                            if ((i & 64) != 64) {
                                this.k = new ArrayList();
                                i |= 64;
                            }
                            this.k.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 50:
                            int iC2 = c2396kb.c(c2396kb.j());
                            if ((i & 64) != 64 && c2396kb.a() > 0) {
                                this.k = new ArrayList();
                                i |= 64;
                            }
                            while (c2396kb.a() > 0) {
                                this.k.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC2);
                            continue;
                        case 56:
                            if ((i & 32) != 32) {
                                this.i = new ArrayList();
                                i |= 32;
                            }
                            this.i.add(Integer.valueOf(c2396kb.g()));
                            continue;
                        case 58:
                            int iC3 = c2396kb.c(c2396kb.j());
                            if ((i & 32) != 32 && c2396kb.a() > 0) {
                                this.i = new ArrayList();
                                i |= 32;
                            }
                            while (c2396kb.a() > 0) {
                                this.i.add(Integer.valueOf(c2396kb.g()));
                            }
                            c2396kb.b(iC3);
                            continue;
                        default:
                            if (!a(c2396kb, c2547mbA, c2867qh, iN)) {
                                break;
                            }
                            break;
                    }
                    z = true;
                } catch (Throwable th) {
                    if ((i & 2) == 2) {
                        this.d = this.d.h();
                    }
                    if ((i & 4) == 4) {
                        this.e = Collections.unmodifiableList(this.e);
                    }
                    if ((i & 8) == 8) {
                        this.g = this.g.h();
                    }
                    if ((i & 16) == 16) {
                        this.h = this.h.h();
                    }
                    if ((i & 64) == 64) {
                        this.k = Collections.unmodifiableList(this.k);
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
            } catch (C1054Hs e) {
                throw e.a(this);
            } catch (IOException e2) {
                throw new C1054Hs(e2.getMessage()).a(this);
            }
        }
        if ((i & 2) == 2) {
            this.d = this.d.h();
        }
        if ((i & 4) == 4) {
            this.e = Collections.unmodifiableList(this.e);
        }
        if ((i & 8) == 8) {
            this.g = this.g.h();
        }
        if ((i & 16) == 16) {
            this.h = this.h.h();
        }
        if ((i & 64) == 64) {
            this.k = Collections.unmodifiableList(this.k);
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
