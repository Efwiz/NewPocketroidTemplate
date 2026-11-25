package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3534zu extends AbstractC0859Bj {
    private int b;
    private List c = Collections.emptyList();
    private List d = Collections.emptyList();
    private YA e = XA.b;
    private PO f = PO.g();
    private MO g = MO.g();
    private List h = Collections.emptyList();
    private List i = Collections.emptyList();

    private C3534zu() {
    }

    static C3534zu f() {
        return new C3534zu();
    }

    public final void b(C0910Cu c0910Cu) {
        if ((this.b & 1) != 1) {
            this.c = new ArrayList(this.c);
            this.b |= 1;
        }
        List list = this.c;
        C0938Du c0938DuG = c0910Cu.g();
        if (!c0938DuG.a()) {
            throw new C1659b10();
        }
        list.add(c0938DuG);
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C0844Au c0844AuG = g();
        if (c0844AuG.a()) {
            return c0844AuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C3534zu().a(g());
    }

    public final C0844Au g() {
        C0844Au c0844Au = new C0844Au(this, 0);
        int i = this.b;
        if ((i & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
            this.b &= -2;
        }
        c0844Au.c = this.c;
        if ((this.b & 2) == 2) {
            this.d = Collections.unmodifiableList(this.d);
            this.b &= -3;
        }
        c0844Au.d = this.d;
        if ((this.b & 4) == 4) {
            this.e = this.e.h();
            this.b &= -5;
        }
        c0844Au.e = this.e;
        int i2 = (i & 8) == 8 ? 1 : 0;
        c0844Au.f = this.f;
        if ((i & 16) == 16) {
            i2 |= 2;
        }
        c0844Au.g = this.g;
        if ((this.b & 32) == 32) {
            this.h = Collections.unmodifiableList(this.h);
            this.b &= -33;
        }
        c0844Au.h = this.h;
        if ((this.b & 64) == 64) {
            this.i = Collections.unmodifiableList(this.i);
            this.b &= -65;
        }
        c0844Au.i = this.i;
        c0844Au.b = i2;
        return c0844Au;
    }

    public final YA h() {
        return this.e.h();
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C3534zu a(C0844Au c0844Au) {
        if (c0844Au == C0844Au.h()) {
            return this;
        }
        if (!c0844Au.c.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = c0844Au.c;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new ArrayList(this.c);
                    this.b |= 1;
                }
                this.c.addAll(c0844Au.c);
            }
        }
        if (!c0844Au.d.isEmpty()) {
            if (this.d.isEmpty()) {
                this.d = c0844Au.d;
                this.b &= -3;
            } else {
                if ((this.b & 2) != 2) {
                    this.d = new ArrayList(this.d);
                    this.b |= 2;
                }
                this.d.addAll(c0844Au.d);
            }
        }
        if (!c0844Au.e.isEmpty()) {
            if (this.e.isEmpty()) {
                this.e = c0844Au.e;
                this.b &= -5;
            } else {
                if ((this.b & 4) != 4) {
                    this.e = new XA(this.e);
                    this.b |= 4;
                }
                this.e.addAll(c0844Au.e);
            }
        }
        if (c0844Au.p()) {
            PO poN = c0844Au.n();
            if ((this.b & 8) != 8 || this.f == PO.g()) {
                this.f = poN;
            } else {
                this.f = PO.c(this.f).a(poN).g();
            }
            this.b |= 8;
        }
        if (c0844Au.o()) {
            MO moM = c0844Au.m();
            if ((this.b & 16) != 16 || this.g == MO.g()) {
                this.g = moM;
            } else {
                this.g = MO.c(this.g).a(moM).g();
            }
            this.b |= 16;
        }
        if (!c0844Au.h.isEmpty()) {
            if (this.h.isEmpty()) {
                this.h = c0844Au.h;
                this.b &= -33;
            } else {
                if ((this.b & 32) != 32) {
                    this.h = new ArrayList(this.h);
                    this.b |= 32;
                }
                this.h.addAll(c0844Au.h);
            }
        }
        if (!c0844Au.i.isEmpty()) {
            if (this.i.isEmpty()) {
                this.i = c0844Au.i;
                this.b &= -65;
            } else {
                if ((this.b & 64) != 64) {
                    this.i = new ArrayList(this.i);
                    this.b |= 64;
                }
                this.i.addAll(c0844Au.i);
            }
        }
        a(e().b(c0844Au.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C0844Au c0844Au = null;
        try {
            try {
                a((C0844Au) ((C3464yu) C0844Au.m).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C0844Au c0844Au2 = (C0844Au) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c0844Au = c0844Au2;
                    if (c0844Au != null) {
                        a(c0844Au);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c0844Au != null) {
                a(c0844Au);
            }
            throw th;
        }
    }

    public final void a(C0910Cu c0910Cu) {
        if ((this.b & 2) != 2) {
            this.d = new ArrayList(this.d);
            this.b |= 2;
        }
        List list = this.d;
        C0938Du c0938DuG = c0910Cu.g();
        if (c0938DuG.a()) {
            list.add(c0938DuG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(String str) {
        if ((this.b & 4) != 4) {
            this.e = new XA(this.e);
            this.b |= 4;
        }
        this.e.add(str);
    }
}
