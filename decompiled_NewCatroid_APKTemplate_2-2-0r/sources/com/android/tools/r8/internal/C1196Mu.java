package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1196Mu extends AbstractC0859Bj {
    private int b;
    private int c;
    private int d;

    private C1196Mu() {
    }

    static C1196Mu f() {
        return new C1196Mu();
    }

    public final void b(int i) {
        this.b |= 1;
        this.c = i;
    }

    @Override // com.android.tools.r8.internal.A0
    public final B0 c() {
        C1223Nu c1223NuG = g();
        if (c1223NuG.a()) {
            return c1223NuG;
        }
        throw new C1659b10();
    }

    public final Object clone() {
        return new C1196Mu().a(g());
    }

    public final C1223Nu g() {
        C1223Nu c1223Nu = new C1223Nu(this, 0);
        int i = this.b;
        int i2 = (i & 1) == 1 ? 1 : 0;
        c1223Nu.c = this.c;
        if ((i & 2) == 2) {
            i2 |= 2;
        }
        c1223Nu.d = this.d;
        c1223Nu.b = i2;
        return c1223Nu;
    }

    @Override // com.android.tools.r8.internal.AbstractC0859Bj
    public final C1196Mu a(C1223Nu c1223Nu) {
        if (c1223Nu == C1223Nu.g()) {
            return this;
        }
        if (c1223Nu.k()) {
            b(c1223Nu.i());
        }
        if (c1223Nu.j()) {
            a(c1223Nu.h());
        }
        a(e().b(c1223Nu.a));
        return this;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001d  */
    @Override // com.android.tools.r8.internal.A0
    public final A0 a(C2396kb c2396kb, C2867qh c2867qh) throws Throwable {
        C1223Nu c1223Nu = null;
        try {
            try {
                a((C1223Nu) ((C1168Lu) C1223Nu.h).a(c2396kb, c2867qh));
                return this;
            } catch (C1054Hs e) {
                C1223Nu c1223Nu2 = (C1223Nu) e.a();
                try {
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    c1223Nu = c1223Nu2;
                    if (c1223Nu != null) {
                        a(c1223Nu);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            if (c1223Nu != null) {
                a(c1223Nu);
            }
            throw th;
        }
    }

    public final void a(int i) {
        this.b |= 2;
        this.d = i;
    }
}
