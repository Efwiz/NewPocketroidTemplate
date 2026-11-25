package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1691bP extends AbstractC0899Cj {
    private int d;
    private int e;
    private int f;
    private boolean g;
    private EnumC1762cP h = EnumC1762cP.d;
    private List i = Collections.emptyList();
    private List j = Collections.emptyList();

    private C1691bP() {
    }

    static C1691bP f() {
        return new C1691bP();
    }

    public final void b(int i) {
        this.d |= 2;
        this.f = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1840dP c1840dPG = g();
        if (c1840dPG.a()) {
            return c1840dPG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1691bP().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return C1840dP.k();
    }

    public final C1840dP g() {
        C1840dP c1840dP = new C1840dP(this, 0);
        int i = this.d;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c1840dP.d = this.e;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c1840dP.e = this.f;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c1840dP.f = this.g;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c1840dP.g = this.h;
        if ((this.d & 16) == 16) {
            this.i = Collections.unmodifiableList(this.i);
            this.d &= -17;
        }
        c1840dP.h = this.i;
        if ((this.d & 32) == 32) {
            this.j = Collections.unmodifiableList(this.j);
            this.d &= -33;
        }
        c1840dP.i = this.j;
        c1840dP.c = i2;
        return c1840dP;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1691bP a(C1840dP c1840dP) {
        if (c1840dP == C1840dP.k()) {
            return this;
        }
        if (c1840dP.r()) {
            a(c1840dP.l());
        }
        if (c1840dP.s()) {
            b(c1840dP.m());
        }
        if (c1840dP.t()) {
            a(c1840dP.n());
        }
        if (c1840dP.u()) {
            a(c1840dP.q());
        }
        if (!c1840dP.h.isEmpty()) {
            if (this.i.isEmpty()) {
                this.i = c1840dP.h;
                this.d &= -17;
            } else {
                if ((this.d & 16) != 16) {
                    this.i = new ArrayList(this.i);
                    this.d |= 16;
                }
                this.i.addAll(c1840dP.h);
            }
        }
        if (!c1840dP.i.isEmpty()) {
            if (this.j.isEmpty()) {
                this.j = c1840dP.i;
                this.d &= -33;
            } else {
                if ((this.d & 32) != 32) {
                    this.j = new ArrayList(this.j);
                    this.d |= 32;
                }
                this.j.addAll(c1840dP.i);
            }
        }
        a((AbstractC0958Ej) c1840dP);
        a(e().b(c1840dP.b));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1840dP c1840dP = null;
        try {
            try {
                a((C1840dP) ((C1611aP) C1840dP.n).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1840dP c1840dP2 = (C1840dP) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1840dP = c1840dP2;
                    if (c1840dP != null) {
                        a(c1840dP);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1840dP != null) {
                a(c1840dP);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.d |= 1;
        this.e = i;
    }

    public final void a(boolean z) {
        this.d |= 4;
        this.g = z;
    }

    public final void a(EnumC1762cP enumC1762cP) {
        enumC1762cP.getClass();
        this.d |= 8;
        this.h = enumC1762cP;
    }

    public final void a(VO vo) {
        if ((this.d & 16) != 16) {
            this.i = new ArrayList(this.i);
            this.d |= 16;
        }
        this.i.add(vo.c());
    }
}
