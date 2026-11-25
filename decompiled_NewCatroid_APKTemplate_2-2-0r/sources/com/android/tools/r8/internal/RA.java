package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class RA extends AbstractC3172uy {
    private C3285wO b;
    private final Q20 c;

    public RA(C1648av c1648av) {
        this.c = new Q20(c1648av);
    }

    protected final void a(C3285wO c3285wO) {
        this.b = c3285wO;
    }

    protected final Q20 b() {
        return this.c;
    }

    protected final C3285wO c() {
        return this.b;
    }

    public final AbstractC3025sy a(int i, String str) {
        AbstractC0936Ds.c(str, "name");
        return new C2586n30(i, new QA(this), this.c, str);
    }
}
