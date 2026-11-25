package com.android.tools.r8.internal;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0910Cu extends AbstractC0859Bj {
    private int b;
    private Object c = "";
    private YA d;
    private List e;
    private YA f;
    private YA g;
    private List h;
    private List i;

    private C0910Cu() {
        C3178v10 c3178v10 = XA.b;
        this.d = c3178v10;
        this.e = Collections.emptyList();
        this.f = c3178v10;
        this.g = c3178v10;
        this.h = Collections.emptyList();
        this.i = Collections.emptyList();
    }

    static C0910Cu f() {
        return new C0910Cu();
    }

    public final void b(int i) {
        if ((this.b & 4) != 4) {
            this.e = new ArrayList(this.e);
            this.b |= 4;
        }
        this.e.add(Integer.valueOf(i));
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C0938Du c0938DuG = g();
        if (c0938DuG.a()) {
            return c0938DuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C0910Cu().a(g());
    }

    public final void d(String str) {
        str.getClass();
        this.b |= 1;
        this.c = str;
    }

    public final C0938Du g() {
        C0938Du c0938Du = new C0938Du(this, 0);
        int i = (this.b & 1) == 1 ? 1 : 0;
        c0938Du.c = this.c;
        if ((this.b & 2) == 2) {
            this.d = this.d.h();
            this.b &= -3;
        }
        c0938Du.d = this.d;
        if ((this.b & 4) == 4) {
            this.e = Collections.unmodifiableList(this.e);
            this.b &= -5;
        }
        c0938Du.e = this.e;
        if ((this.b & 8) == 8) {
            this.f = this.f.h();
            this.b &= -9;
        }
        c0938Du.g = this.f;
        if ((this.b & 16) == 16) {
            this.g = this.g.h();
            this.b &= -17;
        }
        c0938Du.h = this.g;
        if ((this.b & 32) == 32) {
            this.h = Collections.unmodifiableList(this.h);
            this.b &= -33;
        }
        c0938Du.i = this.h;
        if ((this.b & 64) == 64) {
            this.i = Collections.unmodifiableList(this.i);
            this.b &= -65;
        }
        c0938Du.k = this.i;
        c0938Du.b = i;
        return c0938Du;
    }

    public final int h() {
        return this.f.size();
    }

    public final String i() {
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

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C0910Cu a(C0938Du c0938Du) {
        if (c0938Du == C0938Du.j()) {
            return this;
        }
        if (c0938Du.o()) {
            this.b |= 1;
            this.c = c0938Du.c;
        }
        if (!c0938Du.d.isEmpty()) {
            if (this.d.isEmpty()) {
                this.d = c0938Du.d;
                this.b &= -3;
            } else {
                if ((this.b & 2) != 2) {
                    this.d = new XA(this.d);
                    this.b |= 2;
                }
                this.d.addAll(c0938Du.d);
            }
        }
        if (!c0938Du.e.isEmpty()) {
            if (this.e.isEmpty()) {
                this.e = c0938Du.e;
                this.b &= -5;
            } else {
                if ((this.b & 4) != 4) {
                    this.e = new ArrayList(this.e);
                    this.b |= 4;
                }
                this.e.addAll(c0938Du.e);
            }
        }
        if (!c0938Du.g.isEmpty()) {
            if (this.f.isEmpty()) {
                this.f = c0938Du.g;
                this.b &= -9;
            } else {
                if ((this.b & 8) != 8) {
                    this.f = new XA(this.f);
                    this.b |= 8;
                }
                this.f.addAll(c0938Du.g);
            }
        }
        if (!c0938Du.h.isEmpty()) {
            if (this.g.isEmpty()) {
                this.g = c0938Du.h;
                this.b &= -17;
            } else {
                if ((this.b & 16) != 16) {
                    this.g = new XA(this.g);
                    this.b |= 16;
                }
                this.g.addAll(c0938Du.h);
            }
        }
        if (!c0938Du.i.isEmpty()) {
            if (this.h.isEmpty()) {
                this.h = c0938Du.i;
                this.b &= -33;
            } else {
                if ((this.b & 32) != 32) {
                    this.h = new ArrayList(this.h);
                    this.b |= 32;
                }
                this.h.addAll(c0938Du.i);
            }
        }
        if (!c0938Du.k.isEmpty()) {
            if (this.i.isEmpty()) {
                this.i = c0938Du.k;
                this.b &= -65;
            } else {
                if ((this.b & 64) != 64) {
                    this.i = new ArrayList(this.i);
                    this.b |= 64;
                }
                this.i.addAll(c0938Du.k);
            }
        }
        a(e().b(c0938Du.a));
        return this;
    }

    public final void b(String str) {
        str.getClass();
        if ((this.b & 8) != 8) {
            this.f = new XA(this.f);
            this.b |= 8;
        }
        this.f.add(str);
    }

    public final void c(String str) {
        if ((this.b & 2) != 2) {
            this.d = new XA(this.d);
            this.b |= 2;
        }
        this.d.add(str);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C0938Du c0938Du = null;
        try {
            try {
                a((C0938Du) ((C0870Bu) C0938Du.p).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C0938Du c0938Du2 = (C0938Du) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c0938Du = c0938Du2;
                    if (c0938Du != null) {
                        a(c0938Du);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c0938Du != null) {
                a(c0938Du);
            }
            throw th;
        }
    }

    public final void a(List list) {
        if ((this.b & 2) != 2) {
            this.d = new XA(this.d);
            this.b |= 2;
        }
        A0.a(list, this.d);
    }

    public final void a(String str) {
        if ((this.b & 16) != 16) {
            this.g = new XA(this.g);
            this.b |= 16;
        }
        this.g.add(str);
    }

    public final void a(int i) {
        if ((this.b & 32) != 32) {
            this.h = new ArrayList(this.h);
            this.b |= 32;
        }
        this.h.add(Integer.valueOf(i));
    }

    public final void a(ArrayList arrayList) {
        if ((this.b & 64) != 64) {
            this.i = new ArrayList(this.i);
            this.b |= 64;
        }
        A0.a(arrayList, this.i);
    }
}
