package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1921eO extends AbstractC0899Cj {
    private int d;
    private int e = 6;
    private List f = Collections.emptyList();
    private List g = Collections.emptyList();

    private C1921eO() {
    }

    static C1921eO f() {
        return new C1921eO();
    }

    public final void b(int i) {
        this.d |= 1;
        this.e = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1998fO c1998fOG = g();
        if (c1998fOG.a()) {
            return c1998fOG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1921eO().a(g());
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return C1998fO.k();
    }

    public final C1998fO g() {
        C1998fO c1998fO = new C1998fO(this, 0);
        int i = (this.d & 1) == 1 ? 1 : 0;
        c1998fO.d = this.e;
        if ((this.d & 2) == 2) {
            this.f = Collections.unmodifiableList(this.f);
            this.d &= -3;
        }
        c1998fO.e = this.f;
        if ((this.d & 4) == 4) {
            this.g = Collections.unmodifiableList(this.g);
            this.d &= -5;
        }
        c1998fO.f = this.g;
        c1998fO.c = i;
        return c1998fO;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1921eO a(C1998fO c1998fO) {
        if (c1998fO == C1998fO.k()) {
            return this;
        }
        if (c1998fO.o()) {
            b(c1998fO.l());
        }
        if (!c1998fO.e.isEmpty()) {
            if (this.f.isEmpty()) {
                this.f = c1998fO.e;
                this.d &= -3;
            } else {
                if ((this.d & 2) != 2) {
                    this.f = new ArrayList(this.f);
                    this.d |= 2;
                }
                this.f.addAll(c1998fO.e);
            }
        }
        if (!c1998fO.f.isEmpty()) {
            if (this.g.isEmpty()) {
                this.g = c1998fO.f;
                this.d &= -5;
            } else {
                if ((this.d & 4) != 4) {
                    this.g = new ArrayList(this.g);
                    this.d |= 4;
                }
                this.g.addAll(c1998fO.f);
            }
        }
        a((AbstractC0958Ej) c1998fO);
        a(e().b(c1998fO.b));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1998fO c1998fO = null;
        try {
            try {
                a((C1998fO) ((C1839dO) C1998fO.j).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1998fO c1998fO2 = (C1998fO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1998fO = c1998fO2;
                    if (c1998fO != null) {
                        a(c1998fO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1998fO != null) {
                a(c1998fO);
            }
            throw th;
        }
    }

    public final void a(C2303jP c2303jP) {
        if ((this.d & 2) != 2) {
            this.f = new ArrayList(this.f);
            this.d |= 2;
        }
        this.f.add(c2303jP);
    }

    public final void a(int i) {
        if ((this.d & 4) != 4) {
            this.g = new ArrayList(this.g);
            this.d |= 4;
        }
        this.g.add(Integer.valueOf(i));
    }
}
