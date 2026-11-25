package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E9 extends AbstractC1008Gg {
    private int a;

    E9(int i) {
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1008Gg
    public final E9 a() {
        return this;
    }

    public final int c() {
        return this.a;
    }

    public final boolean a(C1721bt c1721bt, S10 s10) {
        for (int i = 0; i < c1721bt.J2().size(); i++) {
            if (c1721bt.c(i) == s10 && i != this.a) {
                return false;
            }
        }
        return c1721bt.f1();
    }

    @Override // com.android.tools.r8.internal.AbstractC1008Gg
    public final AbstractC1008Gg a(com.android.tools.r8.graph.proto.c cVar) {
        if (cVar.a(this.a).c()) {
            return C2126h10.c();
        }
        int iB = cVar.b(this.a);
        return iB != this.a ? new E9(iB) : this;
    }
}
