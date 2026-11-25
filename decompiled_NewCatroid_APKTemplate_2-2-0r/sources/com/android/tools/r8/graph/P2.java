package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class P2 extends AbstractC0648f {
    public P2() {
        super(Q2.k(0));
    }

    public final void a(int i) {
        ((Q2) this.a).d(i);
    }

    public final AbstractC0648f b() {
        return this;
    }

    public final AbstractC0648f c() {
        if (AbstractC0648f.b || this.a.i()) {
            return b();
        }
        throw new AssertionError();
    }

    public final AbstractC0648f d() {
        this.a.D();
        return b();
    }

    public final AbstractC0648f e() {
        this.a.E();
        return b();
    }

    public final AbstractC0657g a() {
        return this.a;
    }
}
