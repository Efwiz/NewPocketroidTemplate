package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1915eM;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class N0 implements I0 {
    static final /* synthetic */ boolean e = true;
    private int a = 0;
    private int b;
    protected C0684j2 c;
    protected AbstractC1915eM d;

    public N0(int i, C0684j2 c0684j2) {
        this.b = i;
        this.c = c0684j2;
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(B0.c cVar) {
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(B0.d dVar) {
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(C0 c0) {
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(D0 d0) {
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(E0 e0) {
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(G0 g0) {
    }

    public int b() {
        return this.a;
    }

    public final AbstractC1915eM c() {
        AbstractC1915eM abstractC1915eM = this.d;
        if (abstractC1915eM == null) {
            return (a() > 0 ? AbstractC1915eM.b.x() : AbstractC1915eM.c.x()).a(a()).a(this.c).a();
        }
        return abstractC1915eM.a().a(a()).a();
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(B0.a aVar) {
        if (!e && aVar.c < 0) {
            throw new AssertionError();
        }
        this.a += aVar.c;
    }

    @Override // com.android.tools.r8.graph.I0
    public final void a(A0 a0) {
        this.b += a0.c;
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(F0 f0) {
        if (!e && f0.x0() == null) {
            throw new AssertionError();
        }
        AbstractC1915eM abstractC1915eMX0 = f0.x0();
        this.c = abstractC1915eMX0.g();
        this.d = abstractC1915eMX0;
    }

    @Override // com.android.tools.r8.graph.I0
    public void a(B0.b bVar) {
        if (!e && bVar.y0() < 0) {
            throw new AssertionError();
        }
        this.a = bVar.y0() + this.a;
        this.b = bVar.x0() + this.b;
    }

    public int a() {
        return this.b;
    }
}
