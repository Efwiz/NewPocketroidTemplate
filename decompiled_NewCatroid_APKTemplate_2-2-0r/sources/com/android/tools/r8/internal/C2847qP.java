package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2847qP extends AbstractC0859Bj {
    private int b;
    private List c = Collections.emptyList();

    private C2847qP() {
    }

    static C2847qP f() {
        return new C2847qP();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2919rP c2919rPG = g();
        if (c2919rPG.a()) {
            return c2919rPG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C2847qP().a(g());
    }

    public final C2919rP g() {
        C2919rP c2919rP = new C2919rP(this, 0);
        if ((this.b & 1) == 1) {
            this.c = Collections.unmodifiableList(this.c);
            this.b &= -2;
        }
        c2919rP.b = this.c;
        return c2919rP;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2847qP a(C2919rP c2919rP) {
        if (c2919rP == C2919rP.g()) {
            return this;
        }
        if (!c2919rP.b.isEmpty()) {
            if (this.c.isEmpty()) {
                this.c = c2919rP.b;
                this.b &= -2;
            } else {
                if ((this.b & 1) != 1) {
                    this.c = new ArrayList(this.c);
                    this.b |= 1;
                }
                this.c.addAll(c2919rP.b);
            }
        }
        a(e().b(c2919rP.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2919rP c2919rP = null;
        try {
            try {
                a((C2919rP) ((C2776pP) C2919rP.f).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2919rP c2919rP2 = (C2919rP) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2919rP = c2919rP2;
                    if (c2919rP != null) {
                        a(c2919rP);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2919rP != null) {
                a(c2919rP);
            }
            throw th;
        }
    }

    public final void a(C2461lP c2461lP) {
        if ((this.b & 1) != 1) {
            this.c = new ArrayList(this.c);
            this.b |= 1;
        }
        List list = this.c;
        C2700oP c2700oPG = c2461lP.g();
        if (c2700oPG.a()) {
            list.add(c2700oPG);
            return;
        }
        throw new C1659b10();
    }
}
