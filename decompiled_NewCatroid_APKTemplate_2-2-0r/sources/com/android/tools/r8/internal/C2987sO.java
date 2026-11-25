package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2987sO extends AbstractC0859Bj {
    private int b;
    private int c;
    private int d;
    private int g;
    private EnumC3056tO e = EnumC3056tO.b;
    private WO f = WO.o();
    private List h = Collections.emptyList();
    private List i = Collections.emptyList();

    private C2987sO() {
    }

    static C2987sO f() {
        return new C2987sO();
    }

    public final void b(int i) {
        this.b |= 2;
        this.d = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C3125uO c3125uOG = g();
        if (c3125uOG.a()) {
            return c3125uOG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C2987sO().a(g());
    }

    /* JADX WARN: Failed to analyze thrown exceptions
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1096)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1050)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:117)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
     */
    public final C3125uO g() {
        C3125uO c3125uO = new C3125uO(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c3125uO.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c3125uO.d = this.d;
        if ((i & 4) == 4) {
            i2 |= 4;
        }
        c3125uO.e = this.e;
        if ((i & 8) == 8) {
            i2 |= 8;
        }
        c3125uO.f = this.f;
        if ((i & 16) == 16) {
            i2 |= 16;
        }
        c3125uO.g = this.g;
        if ((this.b & 32) == 32) {
            this.h = Collections.unmodifiableList(this.h);
            this.b &= -33;
        }
        c3125uO.h = this.h;
        if ((this.b & 64) == 64) {
            this.i = Collections.unmodifiableList(this.i);
            this.b &= -65;
        }
        c3125uO.i = this.i;
        c3125uO.b = i2;
        return c3125uO;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2987sO a(C3125uO c3125uO) {
        if (c3125uO == C3125uO.i()) {
            return this;
        }
        if (c3125uO.p()) {
            a(c3125uO.j());
        }
        if (c3125uO.s()) {
            b(c3125uO.n());
        }
        if (c3125uO.o()) {
            a(c3125uO.h());
        }
        if (c3125uO.q()) {
            WO woK = c3125uO.k();
            if ((this.b & 8) != 8 || this.f == WO.o()) {
                this.f = woK;
            } else {
                this.f = VO.f().a(this.f).a(woK).h();
            }
            this.b |= 8;
        }
        if (c3125uO.r()) {
            int iL = c3125uO.l();
            this.b |= 16;
            this.g = iL;
        }
        if (!c3125uO.h.isEmpty()) {
            if (this.h.isEmpty()) {
                this.h = c3125uO.h;
                this.b &= -33;
            } else {
                if ((this.b & 32) != 32) {
                    this.h = new ArrayList(this.h);
                    this.b |= 32;
                }
                this.h.addAll(c3125uO.h);
            }
        }
        if (!c3125uO.i.isEmpty()) {
            if (this.i.isEmpty()) {
                this.i = c3125uO.i;
                this.b &= -65;
            } else {
                if ((this.b & 64) != 64) {
                    this.i = new ArrayList(this.i);
                    this.b |= 64;
                }
                this.i.addAll(c3125uO.i);
            }
        }
        a(e().b(c3125uO.a));
        return this;
    }

    public final void b(C2987sO c2987sO) {
        if ((this.b & 64) != 64) {
            this.i = new ArrayList(this.i);
            this.b |= 64;
        }
        List list = this.i;
        C3125uO c3125uOG = c2987sO.g();
        if (c3125uOG.a()) {
            list.add(c3125uOG);
            return;
        }
        throw new C1659b10();
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C3125uO c3125uO = null;
        try {
            try {
                a((C3125uO) ((C2918rO) C3125uO.m).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C3125uO c3125uO2 = (C3125uO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c3125uO = c3125uO2;
                    if (c3125uO != null) {
                        a(c3125uO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c3125uO != null) {
                a(c3125uO);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.b |= 1;
        this.c = i;
    }

    public final void a(EnumC3056tO enumC3056tO) {
        enumC3056tO.getClass();
        this.b |= 4;
        this.e = enumC3056tO;
    }

    public final void a(WO wo) {
        this.f = wo;
        this.b |= 8;
    }

    public final void a(C2987sO c2987sO) {
        if ((this.b & 32) != 32) {
            this.h = new ArrayList(this.h);
            this.b |= 32;
        }
        List list = this.h;
        C3125uO c3125uOG = c2987sO.g();
        if (c3125uOG.a()) {
            list.add(c3125uOG);
            return;
        }
        throw new C1659b10();
    }
}
