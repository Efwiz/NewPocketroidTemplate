package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pO, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2775pO extends AbstractC0899Cj {
    private int d;
    private int e;

    private C2775pO() {
    }

    static C2775pO f() {
        return new C2775pO();
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C2846qO c2846qO = new C2846qO(this, 0);
        int i = (this.d & 1) == 1 ? 1 : 0;
        c2846qO.d = this.e;
        c2846qO.c = i;
        if (c2846qO.a()) {
            return c2846qO;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        C2775pO c2775pO = new C2775pO();
        C2846qO c2846qO = new C2846qO(this, 0);
        int i = (this.d & 1) == 1 ? 1 : 0;
        c2846qO.d = this.e;
        c2846qO.c = i;
        return c2775pO.a(c2846qO);
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final AbstractC1045Hj d() {
        return C2846qO.k();
    }

    public final C2846qO g() {
        C2846qO c2846qO = new C2846qO(this, 0);
        int i = (this.d & 1) == 1 ? 1 : 0;
        c2846qO.d = this.e;
        c2846qO.c = i;
        if (c2846qO.a()) {
            return c2846qO;
        }
        throw new C1659b10();
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C2775pO a(C2846qO c2846qO) {
        if (c2846qO == C2846qO.k()) {
            return this;
        }
        if (c2846qO.m()) {
            a(c2846qO.l());
        }
        a((AbstractC0958Ej) c2846qO);
        a(e().b(c2846qO.b));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C2846qO c2846qO = null;
        try {
            try {
                a((C2846qO) ((C2699oO) C2846qO.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C2846qO c2846qO2 = (C2846qO) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c2846qO = c2846qO2;
                    if (c2846qO != null) {
                        a(c2846qO);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c2846qO != null) {
                a(c2846qO);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.d |= 1;
        this.e = i;
    }
}
