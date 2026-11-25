package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BO extends AbstractC0899Cj {
    private int d;
    private List e = Collections.emptyList();
    private List f = Collections.emptyList();
    private List g = Collections.emptyList();
    private C2075gP h = C2075gP.g();
    private C2919rP i = C2919rP.g();

    private BO() {
    }

    static BO f() {
        return new BO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        CO coG = g();
        if (coG.a()) {
            return coG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new BO().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return CO.k();
    }

    public final CO g() {
        CO co = new CO(this, 0);
        int i = this.d;
        if ((i & 1) == 1) {
            this.e = Collections.unmodifiableList(this.e);
            this.d &= -2;
        }
        co.d = this.e;
        if ((this.d & 2) == 2) {
            this.f = Collections.unmodifiableList(this.f);
            this.d &= -3;
        }
        co.e = this.f;
        if ((this.d & 4) == 4) {
            this.g = Collections.unmodifiableList(this.g);
            this.d &= -5;
        }
        co.f = this.g;
        int i2 = (i & 8) == 8 ? 1 : 0;
        co.g = this.h;
        if ((i & 16) == 16) {
            i2 |= 2;
        }
        co.h = this.i;
        co.c = i2;
        return co;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final BO a(CO co) {
        if (co == CO.k()) {
            return this;
        }
        if (!co.d.isEmpty()) {
            if (this.e.isEmpty()) {
                this.e = co.d;
                this.d &= -2;
            } else {
                if ((this.d & 1) != 1) {
                    this.e = new ArrayList(this.e);
                    this.d |= 1;
                }
                this.e.addAll(co.d);
            }
        }
        if (!co.e.isEmpty()) {
            if (this.f.isEmpty()) {
                this.f = co.e;
                this.d &= -3;
            } else {
                if ((this.d & 2) != 2) {
                    this.f = new ArrayList(this.f);
                    this.d |= 2;
                }
                this.f.addAll(co.e);
            }
        }
        if (!co.f.isEmpty()) {
            if (this.g.isEmpty()) {
                this.g = co.f;
                this.d &= -5;
            } else {
                if ((this.d & 4) != 4) {
                    this.g = new ArrayList(this.g);
                    this.d |= 4;
                }
                this.g.addAll(co.f);
            }
        }
        if (co.q()) {
            C2075gP c2075gPO = co.o();
            if ((this.d & 8) != 8 || this.h == C2075gP.g()) {
                this.h = c2075gPO;
            } else {
                this.h = C1999fP.f().a(this.h).a(c2075gPO).g();
            }
            this.d |= 8;
        }
        if (co.r()) {
            C2919rP c2919rPP = co.p();
            if ((this.d & 16) != 16 || this.i == C2919rP.g()) {
                this.i = c2919rPP;
            } else {
                this.i = C2847qP.f().a(this.i).a(c2919rPP).g();
            }
            this.d |= 16;
        }
        a((AbstractC0958Ej) co);
        a(e().b(co.b));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        CO co = null;
        try {
            try {
                a((CO) ((AO) CO.l).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                CO co2 = (CO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    co = co2;
                    if (co != null) {
                        a(co);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (co != null) {
                a(co);
            }
            throw th;
        }
    }

    public final void a(C3285wO c3285wO) {
        if ((this.d & 1) != 1) {
            this.e = new ArrayList(this.e);
            this.d |= 1;
        }
        List list = this.e;
        C3354xO c3354xOG = c3285wO.g();
        if (c3354xOG.a()) {
            list.add(c3354xOG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(EO eo) {
        if ((this.d & 2) != 2) {
            this.f = new ArrayList(this.f);
            this.d |= 2;
        }
        List list = this.f;
        FO foG = eo.g();
        if (foG.a()) {
            list.add(foG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(YO yo) {
        if ((this.d & 4) != 4) {
            this.g = new ArrayList(this.g);
            this.d |= 4;
        }
        List list = this.g;
        ZO zoG = yo.g();
        if (zoG.a()) {
            list.add(zoG);
            return;
        }
        throw new C1659b10();
    }

    public final void a(C2919rP c2919rP) {
        this.i = c2919rP;
        this.d |= 16;
    }
}
