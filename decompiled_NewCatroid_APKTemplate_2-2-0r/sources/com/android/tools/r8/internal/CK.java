package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CK {
    private final CN a = CN.b();
    private final VE b;
    private final C1257Pb c;

    CK(VE ve, C1257Pb c1257Pb) {
        this.b = ve;
        this.c = c1257Pb;
    }

    public final CK a(com.android.tools.r8.graph.o5 o5Var) {
        this.a.add(o5Var);
        return this;
    }

    public final EK a() {
        CN cn = this.a;
        return new EK(this.c, this.b, cn);
    }
}
