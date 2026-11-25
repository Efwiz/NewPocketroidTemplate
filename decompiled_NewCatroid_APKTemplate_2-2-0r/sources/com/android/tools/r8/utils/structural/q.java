package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class q extends o {
    private final n a;
    private final t b;

    private q(n nVar, t tVar) {
        this.a = nVar;
        this.b = tVar;
    }

    public static void a(x xVar, n nVar, t tVar, w wVar) {
        wVar.a(xVar, new q(nVar, tVar));
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(boolean z) {
        this.a.a(z);
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(int i) {
        this.a.a(i);
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(float f) {
        this.a.a(f);
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(long j) {
        this.a.a(j);
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(double d) {
        this.a.a(d);
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(C0759u2 c0759u2) {
        this.a.a(c0759u2.e);
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(C0767v2 c0767v2) {
        a(this.b.a(c0767v2).D0());
    }

    @Override // com.android.tools.r8.utils.structural.o
    public final void a(Object obj, y yVar) {
        yVar.a(new p(obj, this));
    }
}
