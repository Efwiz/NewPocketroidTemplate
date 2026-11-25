package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1999fP extends AbstractC0859Bj {
    private int b;
    private List c = Collections.emptyList();
    private int d = -1;

    private C1999fP() {
    }

    static C1999fP f() {
        return new C1999fP();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2075gP c2075gPG = g();
        if (c2075gPG.a()) {
            return c2075gPG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1999fP().a(g());
    }

    public final C2075gP g() {
        C2075gP c2075gP = new C2075gP(this, 0);
        int i = this.b;
        if ((i & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
            this.b &= -2;
        }
        c2075gP.c = this.c;
        int i2 = (i & 2) == 2 ? 1 : 0;
        c2075gP.d = this.d;
        c2075gP.b = i2;
        return c2075gP;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1999fP a(C2075gP c2075gP) {
        if (c2075gP == C2075gP.g()) {
            return this;
        }
        if (!c2075gP.c.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = c2075gP.c;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new ArrayList(this.c);
                    this.b |= 1;
                }
                this.c.addAll(c2075gP.c);
            }
        }
        if (c2075gP.j()) {
            int iH = c2075gP.h();
            this.b |= 2;
            this.d = iH;
        }
        a(e().b(c2075gP.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2075gP c2075gP = null;
        try {
            try {
                a((C2075gP) ((C1922eP) C2075gP.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2075gP c2075gP2 = (C2075gP) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2075gP = c2075gP2;
                    if (c2075gP != null) {
                        a(c2075gP);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2075gP != null) {
                a(c2075gP);
            }
            throw th;
        }
    }
}
