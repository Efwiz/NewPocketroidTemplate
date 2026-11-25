package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class WE extends TE {
    protected CN a;
    protected CN b = CN.c();

    @Override // com.android.tools.r8.internal.TE
    public AbstractC2755p6 a() {
        return AbstractC2755p6.a();
    }

    @Override // com.android.tools.r8.internal.TE
    public boolean b(com.android.tools.r8.graph.o5 o5Var) {
        CN cn = this.a;
        return cn != null && cn.b(o5Var);
    }

    @Override // com.android.tools.r8.internal.TE
    public void c(com.android.tools.r8.graph.o5 o5Var) {
        this.b.add(o5Var);
    }
}
