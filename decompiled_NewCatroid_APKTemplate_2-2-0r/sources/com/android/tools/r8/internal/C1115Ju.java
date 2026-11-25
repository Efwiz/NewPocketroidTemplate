package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ju, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1115Ju extends AbstractC0859Bj {
    private int b;
    private int c;
    private int d;

    private C1115Ju() {
    }

    static C1115Ju f() {
        return new C1115Ju();
    }

    public final void b(int i) {
        this.b |= 1;
        this.c = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1143Ku c1143KuG = g();
        if (c1143KuG.a()) {
            return c1143KuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1115Ju().a(g());
    }

    public final C1143Ku g() {
        C1143Ku c1143Ku = new C1143Ku(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c1143Ku.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c1143Ku.d = this.d;
        c1143Ku.b = i2;
        return c1143Ku;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1115Ju a(C1143Ku c1143Ku) {
        if (c1143Ku == C1143Ku.g()) {
            return this;
        }
        if (c1143Ku.k()) {
            b(c1143Ku.i());
        }
        if (c1143Ku.j()) {
            a(c1143Ku.h());
        }
        a(e().b(c1143Ku.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1143Ku c1143Ku = null;
        try {
            try {
                a((C1143Ku) ((C1086Iu) C1143Ku.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1143Ku c1143Ku2 = (C1143Ku) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1143Ku = c1143Ku2;
                    if (c1143Ku != null) {
                        a(c1143Ku);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1143Ku != null) {
                a(c1143Ku);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.b |= 2;
        this.d = i;
    }
}
