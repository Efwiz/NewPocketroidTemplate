package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1435Uu extends AbstractC0859Bj {
    private int b;
    private int d;
    private int c = 1;
    private Object e = "";
    private EnumC1461Vu f = EnumC1461Vu.b;
    private List g = Collections.emptyList();
    private List h = Collections.emptyList();

    private C1435Uu() {
    }

    static C1435Uu f() {
        return new C1435Uu();
    }

    public final void b(int i) {
        this.b |= 1;
        this.c = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1487Wu c1487WuG = g();
        if (c1487WuG.a()) {
            return c1487WuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1435Uu().a(g());
    }

    public final C1487Wu g() {
        C1487Wu c1487Wu = new C1487Wu(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c1487Wu.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c1487Wu.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c1487Wu.e = this.e;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c1487Wu.f = this.f;
        if ((this.b & 16) == 16) {
            this.g = Collections.unmodifiableList(this.g);
            this.b &= -17;
        }
        c1487Wu.g = this.g;
        if ((this.b & 32) == 32) {
            this.h = Collections.unmodifiableList(this.h);
            this.b &= -33;
        }
        c1487Wu.i = this.h;
        c1487Wu.b = i2;
        return c1487Wu;
    }

    public final int h() {
        return this.c;
    }

    public final int i() {
        return this.h.size();
    }

    public final int j() {
        return this.g.size();
    }

    public final boolean k() {
        return (this.b & 8) == 8;
    }

    public final boolean l() {
        return (this.b & 2) == 2;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1435Uu a(C1487Wu c1487Wu) {
        if (c1487Wu == C1487Wu.g()) {
            return this;
        }
        if (c1487Wu.r()) {
            b(c1487Wu.j());
        }
        if (c1487Wu.q()) {
            a(c1487Wu.i());
        }
        if (c1487Wu.s()) {
            this.b |= 4;
            this.e = c1487Wu.e;
        }
        if (c1487Wu.p()) {
            a(c1487Wu.h());
        }
        if (!c1487Wu.g.isEmpty()) {
            if (this.g.isEmpty()) {
                this.g = c1487Wu.g;
                this.b &= -17;
            } else {
                if ((this.b & 16) != 16) {
                    this.g = new ArrayList(this.g);
                    this.b |= 16;
                }
                this.g.addAll(c1487Wu.g);
            }
        }
        if (!c1487Wu.i.isEmpty()) {
            if (this.h.isEmpty()) {
                this.h = c1487Wu.i;
                this.b &= -33;
            } else {
                if ((this.b & 32) != 32) {
                    this.h = new ArrayList(this.h);
                    this.b |= 32;
                }
                this.h.addAll(c1487Wu.i);
            }
        }
        a(e().b(c1487Wu.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1487Wu c1487Wu = null;
        try {
            try {
                a((C1487Wu) ((C1387Tu) C1487Wu.n).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1487Wu c1487Wu2 = (C1487Wu) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1487Wu = c1487Wu2;
                    if (c1487Wu != null) {
                        a(c1487Wu);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1487Wu != null) {
                a(c1487Wu);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.b |= 2;
        this.d = i;
    }

    public final void a(EnumC1461Vu enumC1461Vu) {
        enumC1461Vu.getClass();
        this.b |= 8;
        this.f = enumC1461Vu;
    }
}
